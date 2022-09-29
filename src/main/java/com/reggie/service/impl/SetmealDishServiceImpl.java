package com.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.reggie.entity.SetmealDish;
import com.reggie.mapper.SetmealDishMapper;
import com.reggie.service.SetmealDishService;
import com.reggie.service.SetmealService;
import org.springframework.stereotype.Service;

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
}
