package com.reggie.web;

import com.reggie.common.Result;
import com.reggie.entity.Orders;
import com.reggie.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 98248
 * @Date: 2022/10/3 - 10 - 03 - 21:20
 * @Description: com.reggie.web
 * @version: 1.0
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping("/submit")
    public Result<String> addOrder(@RequestBody Orders order){
        boolean b = orderService.addOrderByincept(order);
        if(b){
            return Result.success("下单成功");
        }
        return Result.error("下单失败");
    }
}
