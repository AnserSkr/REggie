package com.reggie.web;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.reggie.common.Result;
import com.reggie.entity.Category;
import com.reggie.entity.Employee;
import com.reggie.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 98248
 * @Date: 2022/9/24 - 09 - 24 - 14:50
 * @Description: com.reggie.web
 * @version: 1.0
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    /**
     * 新增菜品或者套餐的分类
     * @param category
     * @return
     */
    @PostMapping
    public Result<String> addCategory(@RequestBody Category category) throws Exception{
        boolean save = categoryService.save(category);
        if(save){
            return Result.success("分类添加成功");
        }else {
            return Result.error("分类添加失败");
        }
    }

    /**
     * 分类的分页查询
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public Result<IPage> selectAllAndPagination(int page,int pageSize){
        IPage<Category> Ipage = new Page<>(page,pageSize);
        //创建一个条件构造器用于分类的排序
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderBy(true,true,Category::getSort);
        categoryService.page(Ipage,wrapper);
        if(Ipage.getRecords()!=null){
            return Result.success(Ipage);
        }
        //查询失败，大概率服务器访问数据库出现了问题
        return Result.error("操作有误请重新查询");
    }

    /**
     * 修改分类信息
     * @param category
     * @return
     */
    @PutMapping
    public Result<String> updateCategory(@RequestBody Category category){
        boolean b = categoryService.updateById(category);
        if(b){
            return Result.success("修改成功");
        }
        return Result.error("修改失败");
    }

    /**
     * 删除分类信息
     * @param ids
     * @return
     */
    @DeleteMapping
    public Result<String> deleteCategory(Long ids){
        // boolean b = categoryService.removeById(ids);
        boolean b = categoryService.remove(ids);
        if(b){
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }

    @GetMapping("/list")
    public Result<List<Category>> list(Integer type){
        LambdaQueryWrapper<Category> categoryWrapper = new LambdaQueryWrapper<>();
        categoryWrapper.eq(type!=null,Category::getType,type);
        // 给展示顺序排序,按sort的升序以及updateTime降序拍列
        categoryWrapper.orderBy(true,true,Category::getSort);
        categoryWrapper.orderBy(true,false,Category::getUpdateTime);
        List<Category> list = categoryService.list(categoryWrapper);
        if(list!=null){
            return Result.success(list);
        }
        return Result.error("未知错误，查询失败");
    }
}
