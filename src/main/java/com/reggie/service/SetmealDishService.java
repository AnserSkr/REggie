package com.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.reggie.dto.SetmealDto;
import com.reggie.entity.SetmealDish;
import com.reggie.entity.SetmealDishDto;

import java.util.List;

/**
 * @author 98248
 * @Date: 2022/9/27 - 09 - 27 - 23:17
 * @Description: com.reggie.service
 * @version: 1.0
 */
public interface SetmealDishService extends IService<SetmealDish> {
    /**
     * 根据setmealID删除对应的所有setmealDish数据。
     * @param setmealId
     * @return
     */
    boolean deleteAllBySetmealId(Long setmealId);

    /**
     * 将setmealID插入setmealDish中，并保存
     * @param setmealDishes
     * @param setmealId
     * @return
     */
    boolean addSetmealDishBySetmealId(List<SetmealDish> setmealDishes,Long setmealId);

    /**
     * 根据setmealId获取全部的setmealDish信息
     * @param setmealId
     * @return
     */
    List<SetmealDishDto> getAllBySetmealId(Long setmealId);
}
