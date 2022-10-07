package com.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.reggie.entity.ShoppingCart;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    /**
     * 添加shoppingCart
     * @param shoppingCart
     * @return
     */
    ShoppingCart addShoppingCart(ShoppingCart shoppingCart);

    /**
     * 根据传入的dishId或setmealId进项菜品数量的-1
     * @param idMap
     * @return
     */
    ShoppingCart subShoppingCartById(Map<String,Long> idMap);

    /**
     * 根据UserId删除所有的shoppingCart
     * @param userId
     * @return
     */
    boolean delAllShoppingCartByUserId(Long userId);

}
