package com.reggie.web;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.reggie.common.Result;
import com.reggie.dto.DishDto;
import com.reggie.entity.Category;
import com.reggie.entity.Dish;
import com.reggie.service.CategoryService;
import com.reggie.service.DishFlavorService;
import com.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 98248
 * @Date: 2022/9/25 - 09 - 25 - 17:19
 * @Description: com.reggie.web
 * @version: 1.0
 * 菜品管理
 */
@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {
    @Autowired
    DishService dishService;

    @Autowired
    DishFlavorService dishFlavorService;

    @Autowired
    CategoryService categoryService;
    @GetMapping("/page")
    public Result<Page> selctAllAndPagination(int page, int pageSize, String name){
        IPage<Dish> dishPage = new Page<>(page, pageSize);
        // 设置条件构造器
        LambdaQueryWrapper<Dish> dishWrapper = new LambdaQueryWrapper<>();
            //当传入的name不为空，则执行模糊查询
        dishWrapper.like(name!=null,Dish::getName,name);
            //以插入时间为基准进行排序
        dishWrapper.orderBy(true,true,Dish::getUpdateTime);
        dishService.page(dishPage,dishWrapper);
        //根据Dish集合，获取对应的DishDto集合
        List<Dish> dishList = dishPage.getRecords();
        //由于DIsh的数据信息已经查询好并排好序了，因此获取到的DishDto也是同样的顺序。

        //根基Dish数组中每一个Dish的信息，获取到对应的DIshDto对象
        List<DishDto> dishDtos = dishList.stream().map(dish -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(dish, dishDto);
            //根据Dish中的catetoryID获取到Category对象
            Category category = categoryService.getById(dish.getCategoryId());
            dishDto.setCategoryName(category.getName());
            return dishDto;
        }).collect(Collectors.toList());

        //将categoryName数据获取到封装到分页对象中
        Page<DishDto> dishDtoPage = new Page<>();
            //拷贝DishPage的数据到DishDtoPage中，但不拷贝records
        BeanUtils.copyProperties(dishPage,dishDtoPage,"records");

        dishDtoPage.setRecords(dishDtos);
        if(dishPage.getRecords()!=null){
            return Result.success(dishDtoPage);
        }
        return Result.error("未知错误请稍后再试");
    }

    /**
     * 根据前端传递的DIshDto数据插入数据
     * @param dishDto
     * @return
     */
    @PostMapping
    public Result<String> addDish(@RequestBody DishDto dishDto){
        log.info("封装内容{}",dishDto);
        boolean b = dishService.addDish(dishDto);
        if(b){
            return Result.success("菜品插入成功");
        }
        return Result.error("未知错误，插入失败");
    }

    /**
     *  根据ID查询到封装有DIsh和flavors的DIshDto信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<DishDto> getDish(@PathVariable Long id){
        //根据DIsh的ID值获取到对应的DishDto对象
        DishDto dishDto = dishService.getFlavorsByDishId(id);

        //根据Dish中的CategoryID获取CategoryName分类名称
        Category category = categoryService.getById(dishDto.getCategoryId());
        dishDto.setCategoryName(category.getName());

        return Result.success(dishDto);
    }

    /**
     * 更新菜品信息，同时更新口味信息
     * @param dishDto
     * @return
     */
    @PutMapping
    public Result<String> updateDish(@RequestBody DishDto dishDto){
        boolean b = dishService.updateDish(dishDto);
        if(b){
            return Result.success("菜品更新成功");
        }
        return Result.error("更新失败，请稍后再试");
    }

    /**
     * 根据传入DishIds数组删除DIsh信息以及对应的Flavor信息
     * @param ids
     * @return
     */
    @DeleteMapping
    public Result<String> deleteDish(String ids){
        log.info("数据格式",ids);
        ArrayList<Long> idss = parseStrsToLongList(ids);
        boolean b = dishService.deleteDishByIds(idss);
        if(b){
            return Result.success("删除成功");
        }
        return Result.error("未知错误，删除失败");
    }

    /**
     * 根据传入的ID更新
     * @param ids
     * @param status
     * @return
     */
    @PostMapping("/status/{status}")
    public Result<String> updateDishStatus(String ids,@PathVariable Integer status){
        // log.info("数据展示：{}",ids);
        // log.info("状态：{}",status);
        ArrayList<Long> dishIds = parseStrsToLongList(ids);
        boolean b = dishService.updateDishStatus(dishIds, status);
        if(b){
            return Result.success("售卖状态修改成功");
        }
        return Result.error("售卖状态修改失败");
    }

    /**
     * 将String数组转换为List<Long>
     * @param ids
     * @return
     */
    private ArrayList<Long> parseStrsToLongList(String ids) {
        String[] split = ids.split(",");
        ArrayList<Long> idss = new ArrayList<>();
        for (String s : split) {
            Long dishId = Long.valueOf(s);
            idss.add(dishId);
        }
        return idss;
    }

    /**
     * 根基CategoryID或name查询分类下所有的Dish信息
     * @param categoryId
     * @return
     */
    // @GetMapping("/list")
    // public Result<List<Dish>> getDishByCategoryId(Long categoryId,String name){
    //     // log.info("categoryID:{}",categoryId);
    //     log.info("name:{}",name);
    //     LambdaQueryWrapper<Dish> dishWrapper = new LambdaQueryWrapper<>();
    //     dishWrapper.eq(categoryId!=null,Dish::getCategoryId,categoryId);
    //     //只查询起售的菜品
    //     dishWrapper.eq(Dish::getStatus,1);
    //     dishWrapper.like(name!=null,Dish::getName,"%"+name+"%");
    //     //查询数据按照更新时间和sort排序
    //     dishWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
    //     List<Dish> dishList = dishService.list(dishWrapper);
    //
    //     if(dishList!=null){
    //         return Result.success(dishList);
    //     }
    //     return Result.error("未知错误，查询失败");
    // }
    @GetMapping("/list")
    public Result<List<DishDto>> getDishByCategoryId(Long categoryId, String name){
        // log.info("categoryID:{}",categoryId);
        log.info("name:{}",name);
        List<Dish> dishList = dishService.getAllDishByCatrgoryId(categoryId, name);
        // 将查询道德dishDto数组转换为DishDto数组
        List<DishDto> dishDtos = dishList.stream().map(dish -> {
            DishDto dishDto = dishService.transToDishDto(dish);
            return dishDto;
        }).collect(Collectors.toList());

        if(dishDtos!=null){
            return Result.success(dishDtos);
        }
        return Result.error("未知错误，查询失败");
    }
}
