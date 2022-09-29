package com.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.reggie.common.CustomException;
import com.reggie.dto.SetmealDto;
import com.reggie.entity.Setmeal;
import com.reggie.entity.SetmealDish;
import com.reggie.mapper.SetmealMapper;
import com.reggie.service.SetmealDishService;
import com.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 98248
 * @Date: 2022/9/24 - 09 - 24 - 23:29
 * @Description: com.reggie.service.impl
 * @version: 1.0
 */
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    SetmealDishService setmealDishService;

    /**
     * 根据SetmealDto插入新的Setmeal数据，同时维护setmeal对应的菜品信息setmealDish
     * @param setmealDto
     * @return
     */
    @Override
    @Transactional
    public boolean addSetmeal(SetmealDto setmealDto) {
        // 首先保存基础的setmeal套餐数据，保存成功后会将setMeal的Id值存入其中
        boolean save = this.save(setmealDto);
        //如果套餐保存成功，则继续保存套餐的菜品信息
        if(save){
            //获取所有的菜品信息，并将setmealID数据存入其中
            List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
            Long setmealId = setmealDto.getId();
            setmealDishes = setmealDishes.stream().map(setmealDishe -> {
                setmealDishe.setSetmealId(setmealId);
                return setmealDishe;
            }).collect(Collectors.toList());
            return  setmealDishService.saveBatch(setmealDishes);
        }
        return false;
    }

    /**
     * 根据page和pageSize执行分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @Override
    public Page<Setmeal> selectAllAndPagition(int page, int pageSize, String name){
        //执行分页
        Page<Setmeal> setmealPage = new Page<>(page,pageSize);
        //根据套餐名称模糊查询
        LambdaQueryWrapper<Setmeal> setmealWrapper = new LambdaQueryWrapper<>();
        setmealWrapper.like(name!=null,Setmeal::getName,name);
        //根据更新时间排序
        setmealWrapper.orderBy(true,true,Setmeal::getUpdateTime);
        //查询数据
        this.page(setmealPage,setmealWrapper);
        return setmealPage;
    }

    /**
     * 根据DishDto更新Dish，并删除原有setmealDIsh，插入新的SetmealDish
     * @param setmealDto
     * @return
     */
    @Override
    @Transactional
    public boolean updateSetmeal(SetmealDto setmealDto) {
        // 根据传递的setmealId更新setmeal
        boolean b = this.updateById(setmealDto);
        //如果setmeal更新完毕，则更新setmeal的setmealDish
        if(b){
            Long setmealId = setmealDto.getId();
            //首先删除所有setmealDIsh信息
            boolean remove = setmealDishService.deleteAllBySetmealId(setmealId);
            // 删除成功后，插入所有的setmealDish信息
            if(remove){//如果删除失败直接返回false
                //执行插入操作,
                return setmealDishService.addSetmealDishBySetmealId(setmealDto.getSetmealDishes(),setmealId);
            }else {
                return false;
            }
        }
        return false;
    }

    /**
     * 根据setmealID删除setmeal，同时删除与setmealId绑定的setmealDish
     *
     * @param setmealIds
     * @return
     */
    @Override
    @Transactional
    public boolean deleteBatchByIds(List<Long> setmealIds) {
        //判断setmeal的状态，如果有正在售卖的套餐，则不能删除
        LambdaQueryWrapper<Setmeal> setmealWrapper = new LambdaQueryWrapper<>();
        setmealWrapper.eq(Setmeal::getStatus,1);
        int count = this.count(setmealWrapper);
        if(count >0){
            throw new CustomException("套餐正在售卖中，不能删除");
        }
        //之后再执行批量删除setmeal
        boolean b = this.removeByIds(setmealIds);
        //如果删除成功,循环遍历setmeal，删除setmealdish数据
        if(b){
            for (Long setmealId : setmealIds) {
                setmealDishService.deleteAllBySetmealId(setmealId);
            }
            return true;
        }
        return false;
    }

    /**
     * 根据SetmealIds修改所有的setmeal的Status信息
     *
     * @param setmealIds
     * @param status
     * @return 返回是否执行成功
     */
    @Override
    public boolean updateStatusByIds(List<Long> setmealIds, Integer status) {
        // 首先根据setmealIds获取到需要更细你的setmeal数组
        List<Setmeal> setmealList = this.listByIds(setmealIds);
        // 修改setmeal的status
        setmealList = setmealList.stream().map(setmeal -> {
            setmeal.setStatus(status);
            return setmeal;
        }).collect(Collectors.toList());
        //执行setmeal的状态修改,并返回是否执行成功
        return this.updateBatchById(setmealList);
    }
}
