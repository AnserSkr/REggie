package com.reggie.web;

import com.reggie.common.BaseContext;
import com.reggie.common.Result;
import com.reggie.entity.ShoppingCart;
import com.reggie.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 98248
 * @Date: 2022/10/1 - 10 - 01 - 21:37
 * @Description: com.reggie.web
 * @version: 1.0
 */
@Slf4j
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCaetController {
    @Autowired
    ShoppingCartService shoppingCartService;

    @GetMapping("/list")
    public Result<List<ShoppingCart>> getShoppingCart(){
        // 获取登录用户的userId
        Long userId = BaseContext.getCurrentId();
        // 根据UserID查询到User下对应的ShoppingCart
        List<ShoppingCart> shoppingCarts = shoppingCartService.getAllByUserId(userId);
        if(shoppingCarts!=null){
            return Result.success(shoppingCarts);
        }
        return Result.error("购物车查询失败");
    }
}
