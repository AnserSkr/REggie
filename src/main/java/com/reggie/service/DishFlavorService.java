package com.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.reggie.dto.DishDto;
import com.reggie.entity.DishFlavor;

import java.util.List;

/**
 * @author 98248
 * @Date: 2022/9/25 - 09 - 25 - 23:47
 * @Description: com.reggie.service
 * @version: 1.0
 */
public interface DishFlavorService extends IService<DishFlavor> {
    /**
     * 根据DishDto中的数据插入或更新Dish的Flavors口味S
     * @param dishDto
     * @return
     */
    boolean addFlavorsByDishId(DishDto dishDto);
    /**
     * 根据单个DishId删除对应的dishFlavors
     * @param DishId
     * @return 返回true表示删除成功
     */
    boolean deleteFloversByDishId(Long DishId);

    /**
     * 根据DIshId获取所有口味信息
     * @param dishId
     * @return
     */
    List<DishFlavor> getFlavorsByDIshId(Long dishId);
}
