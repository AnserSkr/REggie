package com.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.reggie.dto.SetmealDto;
import com.reggie.entity.Category;
import com.reggie.entity.Setmeal;

import java.util.List;

/**
 * @author 98248
 * @Date: 2022/9/24 - 09 - 24 - 23:27
 * @Description: com.reggie.service
 * @version: 1.0
 */
public interface SetmealService extends IService<Setmeal> {
    /**
     * 新增套餐
     * @param setmealDto
     * @return
     */
    boolean addSetmeal(SetmealDto setmealDto);

    /**
     * 根据传入的信息进行分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    Page<Setmeal> selectAllAndPagition(int page, int pageSize, String name);

    /**
     * 根据接收的setmealDto更新setmeal
     * @param setmealDto
     * @return
     */
    boolean updateSetmeal(SetmealDto setmealDto);

    /**
     * 根据setmealID删除setmeal，同时删除与setmealId绑定的setmealDish
     * @param setmealIds
     * @return
     */
    boolean deleteBatchByIds(List<Long> setmealIds);

    /**
     * 根据SetmealIds修改所有的setmeal的Status信息
     * @param setmealIds
     * @return
     */
    boolean updateStatusByIds(List<Long> setmealIds,Integer status);

    /**
     * 根据categoryId获取到分类下所有启用的套餐
     * @param categoryId
     * @return
     */
    List<Setmeal> getAllByCategoryId(Long categoryId);
}
