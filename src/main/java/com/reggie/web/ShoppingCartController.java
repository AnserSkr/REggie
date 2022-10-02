package com.reggie.web;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.reggie.common.BaseContext;
import com.reggie.common.Result;
import com.reggie.entity.ShoppingCart;
import com.reggie.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author 98248
 * @Date: 2022/10/1 - 10 - 01 - 21:37
 * @Description: com.reggie.web
 * @version: 1.0
 */
@Slf4j
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {
    @Autowired
    ShoppingCartService shoppingCartService;

    /**
     * 根据登录用户的ID获取用户的购物车信息
     * @return
     */
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

    /**
     * 根据前端传递的shoppingCart信息进行添加操作
     * @param shoppingCart
     * @return
     */
    @PostMapping("/add")
    public Result<ShoppingCart> addShoppingCart(@RequestBody ShoppingCart shoppingCart){
        log.info("shoppingCate: {}",shoppingCart);

        ShoppingCart shoppingCart1 = shoppingCartService.addShoppingCart(shoppingCart);
        if(shoppingCart1.getId()!=null){
            return Result.success(shoppingCart1);
        }
        return Result.error("添加购物车失败");
    }

    /**
     * 使用map接收前端传递的setmealId和dishId数据,并进行菜品的数量-1
     * @param idMap
     * @return
     */
    @PostMapping("/sub")
    public Result<ShoppingCart> subShoppingCart(@RequestBody Map<String,Long> idMap){
        // log.info("setmealID {}",idMap.get("setmealId"));
        // log.info("dishID {}",idMap.get("dishId"));
        //执行购物车菜品的数量-1操作
        ShoppingCart shoppingCart = shoppingCartService.subShoppingCartById(idMap);
        if(shoppingCart!=null){
            //菜品数量更改,操作成功
            return Result.success(shoppingCart);
        }
        return Result.error("操作失败");
    }

    /**
     * 根据UserId清空购物车
     * @return
     */
    @DeleteMapping("/clean")
    public Result<String> cleanShoppingCart(){
        // 删除UserID下的所有ShoppingCart
        boolean b = shoppingCartService.delAllShoppingCartByUserId();
        if(b){
            return Result.success("购物车清楚成功");
        }
        return Result.error("购物车删除失败");
    }
}
