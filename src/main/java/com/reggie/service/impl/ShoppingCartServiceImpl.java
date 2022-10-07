package com.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.reggie.common.BaseContext;
import com.reggie.entity.ShoppingCart;
import com.reggie.mapper.ShoppingCartMapper;
import com.reggie.service.ShoppingCartService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    /**
     * 添加shoppingCart,并将操作后的shoppingCart返回到前端
     * @param shoppingCart
     * @return
     */
    @Override
    public ShoppingCart addShoppingCart(ShoppingCart shoppingCart) {
        // 首先为shoppingCart设置UserId
        Long userId = BaseContext.getCurrentId();
        shoppingCart.setUserId(userId);
        // 首先进行判断，查看是新增菜品还是新增套餐
        Long dishId = shoppingCart.getDishId();
        LambdaQueryWrapper<ShoppingCart> shoppingCartWrapper = new LambdaQueryWrapper<>();
        //判断是新增套餐还是新增菜品
        if(dishId!=null){
            // 若新增菜品，从数据库中查看菜品是否已经存在
            shoppingCartWrapper.eq(ShoppingCart::getDishId,dishId);
            ShoppingCart shoppingCartDemo = this.getOne(shoppingCartWrapper);
            //若菜品存在,使number+1,并更新口味信息
            if(shoppingCartDemo!=null){
                Integer number = shoppingCartDemo.getNumber();
                shoppingCartDemo.setNumber(++number);
                shoppingCartDemo.setDishFlavor(shoppingCart.getDishFlavor());
                boolean b = this.updateById(shoppingCartDemo);
                return shoppingCartDemo;
            }else {//若菜品不存在，则设定number为1
                shoppingCart.setNumber(1);
                this.save(shoppingCart);
                return shoppingCart;
            }
        }
        // 新增套餐,则先从数据库中获取套餐信息
        shoppingCartWrapper.eq(ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
        ShoppingCart shoppingCartDemo = this.getOne(shoppingCartWrapper);
        //若已经存在套餐信息,则更新number+1
        if(shoppingCartDemo!=null){
            Integer number = shoppingCartDemo.getNumber();
            shoppingCartDemo.setNumber(++number);
            this.updateById(shoppingCartDemo);
            return shoppingCartDemo;
        }else {
            shoppingCart.setNumber(1);
            this.save(shoppingCart);
            return shoppingCart;
        }
    }

    /**
     * 根据传入的dishId或setmealId进项菜品数量的-1
     * @param idMap
     * @return
     */
    @Override
    public ShoppingCart subShoppingCartById(Map<String, Long> idMap) {
        // 获取UserId
        Long userId = BaseContext.getCurrentId();
        // 首先从map中获取到对应的id数据,两者仅有一个存在
        Long setmealId = idMap.get("setmealId");
        Long dishId = idMap.get("dishId");
        // 构造条件构造器,如果两个id有一个存在,那么就根据id获取对应的shoppingCart数据
        LambdaQueryWrapper<ShoppingCart> shoppingCartWrapper = new LambdaQueryWrapper<>();
        shoppingCartWrapper.eq(ShoppingCart::getUserId,userId);
        shoppingCartWrapper.eq(dishId!=null,ShoppingCart::getDishId,dishId);
        shoppingCartWrapper.eq(setmealId!=null,ShoppingCart::getSetmealId,setmealId);
        ShoppingCart shoppingCart = this.getOne(shoppingCartWrapper);
        if(shoppingCart!=null){
            //获取原始的购物车菜品信息
            Integer number = shoppingCart.getNumber();
            --number;
            //如果购物车菜品数量-1后等于0,则从购物车中删除菜品
            if(number<=0){
                this.removeById(shoppingCart.getId());
                ShoppingCart shoppingCart1 = new ShoppingCart();
                shoppingCart1.setNumber(0);
                return shoppingCart1;
            }
            // 如果购物车菜品数量-1后!<=0,则执行number的更新操作
            shoppingCart.setNumber(number);
            this.updateById(shoppingCart);
            return shoppingCart;
        }
        //未根据setmealId或dishId获取到shoppingCart,则不进行操作.
        return null;
    }

    /**
     * 根据UserId删除所有的shoppingCart
     * @param userId
     * @return
     */
    @Override
    public boolean delAllShoppingCartByUserId(Long userId) {
        LambdaQueryWrapper<ShoppingCart> shoppingCartWrapper = new LambdaQueryWrapper<>();
        shoppingCartWrapper.eq(ShoppingCart::getUserId,userId);
        boolean remove = this.remove(shoppingCartWrapper);
        return remove;
    }
}
