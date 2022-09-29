package com.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.reggie.common.Result;
import com.reggie.dto.DishDto;
import com.reggie.entity.Dish;
import com.reggie.entity.DishFlavor;
import com.reggie.mapper.DishMapper;
import com.reggie.service.DishFlavorService;
import com.reggie.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 98248
 * @Date: 2022/9/24 - 09 - 24 - 23:28
 * @Description: com.reggie.service.impl
 * @version: 1.0
 */
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    DishFlavorService dishFlavorService;

    /**
     * 新增菜品，并将菜品口味一并存储
     *
     * @param dishDto
     * @return
     */
    @Override
    @Transactional
    public boolean addDish(DishDto dishDto) {
        // 保存菜品信息
        boolean save = this.save(dishDto);
        if(save){
            //根据DishID存放flavors口味信息
            return dishFlavorService.addFlavorsByDishId(dishDto);
        }
        return false;
    }

    /**
     * 根据Dish ID值获取DishFlavors并封装为DishDto
     * @param id
     * @return
     */
    @Override
    @Transactional
    public DishDto getFlavorsByDishId(Long id) {
        // 获取DIsh数据并保存到DishDto中
        Dish dish = this.getById(id);
        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish,dishDto);

        //根据DIsh的Id值查询Dish的口味信息
        LambdaQueryWrapper<DishFlavor> dishFlavorWrapper = new LambdaQueryWrapper<>();
        dishFlavorWrapper.eq(id!=null,DishFlavor::getDishId,id);
        List<DishFlavor> flavors = dishFlavorService.list(dishFlavorWrapper);

        //将查询到的DishFlavors口味信息放入到DishDto中
        dishDto.setFlavors(flavors);
        return dishDto;
    }

    /**
     * 更新Dish信息以及对应的Dish口味信息
     * @param dishDto
     * @return
     */
    @Override
    @Transactional
    public boolean updateDish(DishDto dishDto) {
        // 更新Dish信息
        boolean b = this.updateById(dishDto);
        //Dish内容更新成功后，更新DIsh对应的口味信息
        if(b){
            //清楚原来的Dish口味信息
            LambdaQueryWrapper<DishFlavor> dishFlavorWrapper = new LambdaQueryWrapper<>();
            dishFlavorWrapper.eq(DishFlavor::getDishId,dishDto.getId());
            dishFlavorService.remove(dishFlavorWrapper);
            //插入新的DishFlavors信息
            return dishFlavorService.addFlavorsByDishId(dishDto);
        }
        return false;
    }


    /**
     * 根据DIshId数组批量删除DIshId
     * @param dishIds
     * @return
     */
    @Override
    @Transactional
    public boolean deleteDishByIds(List<Long> dishIds){
        // 根绝DIshids批量删除Dish
        boolean b = this.removeByIds(dishIds);
        if(b){
            // 获取DIshid数据，并批量删除Dishd对应的Flavors
            for (Long dishId : dishIds) {
                dishFlavorService.deleteFloversByDishId(dishId);
            }
            return true;
        }
        return false;
    }

    /**
     * 根据前端传递的DishIds数组以及status更新DIsh的状态
     * @param dishIds
     * @return
     */
    @Override
    public boolean updateDishStatus(List<Long> dishIds,Integer status) {
        //根据DishIds查询到所有的Dish并重设status
        List<Dish> dishList = this.listByIds(dishIds);
        //重设每一个DIsh的status值
        dishList = dishList.stream().map(dish -> {
            dish.setStatus(status);
            return dish;
        }).collect(Collectors.toList());
        //更新Dish的status信息
        // return this.updateBatchById(dishList);
        return this.updateBatchById(dishList);
    }
}
