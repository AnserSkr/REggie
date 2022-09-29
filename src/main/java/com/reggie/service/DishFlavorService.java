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
    boolean addFlavorsByDishId(DishDto dishDto);
    boolean deleteFloversByDishId(Long DishId);
}
