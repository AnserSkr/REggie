package com.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.reggie.common.CustomException;
import com.reggie.entity.Category;
import com.reggie.entity.Dish;
import com.reggie.entity.Setmeal;
import com.reggie.mapper.CategoryMapper;
import com.reggie.service.CategoryService;
import com.reggie.service.DishService;
import com.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.events.Event;

import java.util.List;

/**
 * @author 98248
 * @Date: 2022/9/24 - 09 - 24 - 12:00
 * @Description: com.reggie.service.impl
 * @version: 1.0
 */
@Service
@Slf4j
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    // @Autowired
    // CategoryMapper categoryMapper;
    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;

    @Override
    public Category MySelectOnById(Long id) {
        // Category category = categoryMapper.MySelectOneById(id);
        Category category = baseMapper.MySelectOneById(id);
        return category;
    }

    /**
     * 根据ID删除分类，删除之前检查分类下是否有菜品
     * @param id
     */
    @Override
    public boolean remove(Long id) {
        //检查当前分类是否关联了菜品，如果关联，抛出一个业务异常
        LambdaQueryWrapper<Dish> dishWrapper = new LambdaQueryWrapper<>();
        dishWrapper.eq(Dish::getCategoryId,id);
        int dishCount = dishService.count(dishWrapper);
        if(dishCount!=0){
            //关联有菜品，抛出业务异常
            throw new CustomException("当前分类下关联了菜品，不能删除");
        }
        //检查当前分类是否关联了套餐菜品，如果已经关联，抛出一个业务异常
        LambdaQueryWrapper<Setmeal> setmealWrapper = new LambdaQueryWrapper<>();
        setmealWrapper.eq(Setmeal::getCategoryId,id);
        int setmealCount = setmealService.count(setmealWrapper);
        if(setmealCount!=0){
            //套餐关联有菜品，抛出业务异常
            throw new CustomException("当前套餐下关联了菜品，不能删除");
        }
        //正常删除分类
        // int i = baseMapper.deleteById(id);
        return super.removeById(id);
    }

    /**
     * 根据ID查询到Name值
     * @param categoryId
     * @return
     */

    @Override
    public String getNameById(Long categoryId) {
        Category category = this.getById(categoryId);
        if(category!=null){
            return category.getName();
        }
        return null;
    }
}
