package com.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.reggie.entity.ShoppingCart;
import com.reggie.mapper.ShoppingCartMapper;
import com.reggie.service.ShoppingCartService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 98248
 * @Date: 2022/10/1 - 10 - 01 - 21:36
 * @Description: com.reggie.service.impl
 * @version: 1.0
 */
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
    /**
     * 根据userId查询到所有的shoppingCart
     * @param userId
     * @return
     */
    @Override
    public List<ShoppingCart> getAllByUserId(Long userId) {
        LambdaQueryWrapper<ShoppingCart> shoppingWrapper = new LambdaQueryWrapper<>();
        shoppingWrapper.eq(ShoppingCart::getUserId,userId);
        List<ShoppingCart> shoppingCarts = this.list(shoppingWrapper);
        return shoppingCarts;
    }
}
