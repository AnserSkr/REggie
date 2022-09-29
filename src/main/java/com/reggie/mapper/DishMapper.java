package com.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.reggie.dto.DishDto;
import com.reggie.entity.Dish;

import java.util.List;

/**
 * @author 98248
 * @Date: 2022/9/24 - 09 - 24 - 23:26
 * @Description: com.reggie.mapper
 * @version: 1.0
 */
public interface DishMapper extends BaseMapper<Dish> {
    //如果传入名称则执行模糊查询
    List<DishDto> listAllDish(String name);
}
