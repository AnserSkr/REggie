package com.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.reggie.entity.ShoppingCart;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 98248
 * @Date: 2022/10/1 - 10 - 01 - 21:36
 * @Description: com.reggie.service
 * @version: 1.0
 */

public interface ShoppingCartService extends IService<ShoppingCart> {

    /**
     * 根据userId查询到所有的shoppingCart
     * @param userId
     * @return
     */
    List<ShoppingCart> getAllByUserId(Long userId);
}
