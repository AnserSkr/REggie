package com.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.reggie.dto.SetmealDto;
import com.reggie.entity.Setmeal;
import com.reggie.entity.SetmealDish;
import com.reggie.service.CategoryService;
import com.reggie.service.SetmealDishService;
import com.reggie.service.SetmealDtoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 98248
 * @Date: 2022/9/28 - 09 - 28 - 10:26
 * @Description: com.reggie.service.impl
 * @version: 1.0
 */
@Service
public class SetmealDtoServiceImpl implements SetmealDtoService {

    @Autowired
    CategoryService categoryService;

    @Autowired
    SetmealDishService setmealDishService;
    /**
     * 将前端传递的setmealPage中的setmeal对应的categoryName查询到并封装到setmealDtoPage
     * @param setmealPage
     * @return
     */
    @Override
    public Page<SetmealDto> transforSetmealDtoPage(Page<Setmeal> setmealPage) {
        //将查询每一个setmealPage拷贝到SetmealDto中
        Page<SetmealDto> setmealDtoPage = new Page<>();
        BeanUtils.copyProperties(setmealPage,setmealDtoPage,"records");
        //查询每一个setmeal的category的Name并将之存储到其中
        List<SetmealDto> setmealDtos = setmealPage.getRecords().stream().map(setmeal -> {
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(setmeal, setmealDto);
            String categoryName = categoryService.getNameById(setmeal.getCategoryId());
            setmealDto.setCategoryName(categoryName);
            return setmealDto;
        }).collect(Collectors.toList());
        //将保存由Setmeal和对应CatrgoryName的数据存入setmealDtoPage
        setmealDtoPage.setRecords(setmealDtos);
        return setmealDtoPage;
    }

    /**
     * 根基setmeal的setmealID值查询所对应的setmealDish信息，并封装到setmealDto中
     * @param setmeal
     * @return
     */
    @Override
    public SetmealDto getDtoBySetmeal(Setmeal setmeal) {
        //根据setmeal的id获取到对应的setmealDish数组
        LambdaQueryWrapper<SetmealDish> setmealDishWrapper = new LambdaQueryWrapper<>();
        setmealDishWrapper.eq(SetmealDish::getSetmealId,setmeal.getId());
        List<SetmealDish> setmealDishes = setmealDishService.list(setmealDishWrapper);
        //将setmeal以及setmeal对应的setmealDish数组封装到setmealDto中
        if(setmealDishes!=null){
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(setmeal,setmealDto);
            setmealDto.setSetmealDishes(setmealDishes);
            return setmealDto;
        }
        return null;
    }
}
