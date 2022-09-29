package com.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.reggie.dto.SetmealDto;
import com.reggie.entity.Setmeal;

import java.util.Set;

/**
 * @author 98248
 * @Date: 2022/9/28 - 09 - 28 - 10:23
 * @Description: com.reggie.service
 * @version: 1.0
 */
public interface SetmealDtoService {
    //将setmealPage转换为setmealDtoPage
    Page<SetmealDto> transforSetmealDtoPage(Page<Setmeal> setmealPage);
    //根据查询到的sesetmealDto
    SetmealDto getDtoBySetmeal(Setmeal setmeal);
}
