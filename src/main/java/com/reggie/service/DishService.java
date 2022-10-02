package com.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.reggie.dto.DishDto;
import com.reggie.entity.Dish;
import com.reggie.entity.DishFlavor;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 98248
 * @Date: 2022/9/24 - 09 - 24 - 23:27
 * @Description: com.reggie.service
 * @version: 1.0
 */
public interface DishService extends IService<Dish> {
    /**
     * 根据categoryId和name获取到所有的dish数据
     * @param categoryId
     * @param name
     * @return
     */
    List<Dish> getAllDishByCatrgoryId(Long categoryId,String name);

    /**
     * 新增菜品，并将菜品口味一并存储
     * @param dishDto
     * @return
     */
    boolean addDish(DishDto dishDto);

    /**
     * 根据Dish ID值获取DishFlavors并封装为DishDto
     * @param id
     * @return
     */
    DishDto getFlavorsByDishId(Long id);

    /**
     * 根据前端发送的DishDto数据更新对应的Dish以及Flavors口味信息
     * @param dishDto
     * @return
     */
    boolean updateDish(DishDto dishDto);

    /**
     * 根据前端发送的DishIds数据删除Dish以及对应的Flavors信息
     * @param dishIds
     * @return
     */
    boolean deleteDishByIds(List<Long> dishIds);

    /**
     * 根据DishId修改DIsh的status
     * @param dishIds
     * @param status
     * @return
     */
    boolean updateDishStatus(List<Long> dishIds,Integer status);

    /**
     * 将dish转换为dishDto，并查询flavors信息
     * @param dish
     * @return
     */
    DishDto transToDishDto(Dish dish);

    /**
     * 根基DIshId获取到对应的菜品Image路径
     * @param dishId
     * @return
     */
    String getImage(Long dishId);
}
