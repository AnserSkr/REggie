package com.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.reggie.dto.DishDto;
import com.reggie.entity.DishFlavor;
import com.reggie.mapper.DishFlavorMapper;
import com.reggie.service.DishFlavorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 98248
 * @Date: 2022/9/25 - 09 - 25 - 23:48
 * @Description: com.reggie.service.impl
 * @version: 1.0
 */
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
    /**
     * 根据DishDto中的数据插入或更新Dish的Flavors口味S
     * @param dishDto
     * @return
     */
    @Override
    public boolean addFlavorsByDishId(DishDto dishDto) {
        //保存菜品的口味
        Long dishId = dishDto.getId();
        // 获取菜品口味集合
        List<DishFlavor> flavors = dishDto.getFlavors();
        // 同时将DishID设置到每一个口味数据中
        flavors = flavors.stream().map(item -> {
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());
        // 执行保存菜品口味操作
        return this.saveBatch(flavors);
    }

    /**
     * 根据单个DishId删除对应的dishFlavors
     * @param DishId
     * @return 返回true表示删除成功
     */
    @Override
    public boolean deleteFloversByDishId(Long DishId) {
        //根据DIshid删除flovers口味信息
        LambdaQueryWrapper<DishFlavor> dishFlavorWrapper = new LambdaQueryWrapper<>();
        dishFlavorWrapper.eq(DishFlavor::getDishId,DishId);
        boolean remove = this.remove(dishFlavorWrapper);
        return remove;
    }

    /**
     * 根据DIshId获取所有口味信息
     *
     * @param dishId
     * @return
     */
    @Override
    public List<DishFlavor> getFlavorsByDIshId(Long dishId) {
        LambdaQueryWrapper<DishFlavor> dishFlavorWrapper = new LambdaQueryWrapper<>();
        dishFlavorWrapper.eq(DishFlavor::getDishId,dishId);
        List<DishFlavor> dishFlavors = this.list(dishFlavorWrapper);
        return dishFlavors;
    }
}
