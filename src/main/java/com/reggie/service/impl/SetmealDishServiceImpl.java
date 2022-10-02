package com.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.reggie.entity.SetmealDish;
import com.reggie.entity.SetmealDishDto;
import com.reggie.mapper.SetmealDishMapper;
import com.reggie.service.DishService;
import com.reggie.service.SetmealDishService;
import com.reggie.service.SetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 98248
 * @Date: 2022/9/27 - 09 - 27 - 23:18
 * @Description: com.reggie.service.impl
 * @version: 1.0
 */
@Service
public class SetmealDishServiceImpl extends ServiceImpl<SetmealDishMapper,SetmealDish> implements SetmealDishService {
    @Autowired
    DishService dishService;

    /**
     * 根据setmealID删除对应的所有setmealDish数据
     *
     * @param setmealId
     * @return
     */
    @Override
    public boolean deleteAllBySetmealId(Long setmealId) {
        //首先删除所有setmealDIsh信息
        LambdaQueryWrapper<SetmealDish> setmealDishWrapper = new LambdaQueryWrapper<>();
        setmealDishWrapper.eq(SetmealDish::getSetmealId,setmealId);
        return this.remove(setmealDishWrapper);
    }

    /**
     * 将setmealID插入setmealDish中，并保存
     *
     * @param setmealDishes
     * @param setmealId
     * @return
     */
    @Override
    public boolean addSetmealDishBySetmealId(List<SetmealDish> setmealDishes, Long setmealId) {
        // 将setmealID插入每一个setmealDish中
        setmealDishes = setmealDishes.stream().map(setmealDish -> {
            setmealDish.setSetmealId(setmealId);
            return setmealDish;
        }).collect(Collectors.toList());
        // 执行批量插入操作
        return this.saveBatch(setmealDishes);
    }

    /**
     * 根据setmealId获取全部的setmealDish信息,同时封装入setmealDIshDto
     * @param setmealId
     * @return
     */
    @Override
    @Transactional
    public List<SetmealDishDto> getAllBySetmealId(Long setmealId) {
        //首先查询到所有的setmealDIsh
        LambdaQueryWrapper<SetmealDish> setmealDishWrapper = new LambdaQueryWrapper<>();
        setmealDishWrapper.eq(setmealId!=null,SetmealDish::getSetmealId,setmealId);
        List<SetmealDish> setmealDishes = this.list(setmealDishWrapper);
        //根据每一个setmealDIsh的dishId获取到image的路径
        List<SetmealDishDto> setmealDishDtos = setmealDishes.stream().map(setmealDish -> {
            SetmealDishDto setmealDishDto = new SetmealDishDto();
            BeanUtils.copyProperties(setmealDish, setmealDishDto);
            setmealDishDto.setImage(dishService.getImage(setmealDish.getDishId()));
            return setmealDishDto;
        }).collect(Collectors.toList());
        return setmealDishDtos;
    }
}
