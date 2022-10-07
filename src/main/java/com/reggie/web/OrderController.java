package com.reggie.web;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.reggie.common.Result;
import com.reggie.dto.OrderDto;
import com.reggie.entity.Orders;
import com.reggie.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author 98248
 * @Date: 2022/10/3 - 10 - 03 - 21:20
 * @Description: com.reggie.web
 * @version: 1.0
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderService orderService;

    /**
     * 根据前端传递过来的数据进行下单
     * @param order
     * @return
     */
    @PostMapping("/submit")
    public Result<String> addOrder(@RequestBody Orders order){
        boolean b = orderService.addOrderByincept(order);
        if(b){
            return Result.success("下单成功");
        }
        return Result.error("下单失败");
    }

    /**
     * 根据传入的oage和pageSize查询出对应的Orders数组，并将Order的OrderDetail获取到一起封装到OderDto中
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/userPage")
    public Result<Page<OrderDto>> userPage(Integer page, Integer pageSize){
        Page<OrderDto> orderDtoPage = orderService.getOrderDtoAndPage(page, pageSize);
        if(orderDtoPage!=null){
            return Result.success(orderDtoPage);
        }
        return Result.error("查询失败");
    }

    /**
     * 后台查询所有订单，当有条件是根据条件查询
     * @param page
     * @param pageSize
     * @param number
     * @param beginTime
     * @param endTime
     * @return
     */
    @GetMapping("/page")
    public Result<Page<OrderDto>> orders(Integer page, Integer pageSize, Long number,
                                         LocalDateTime beginTime,LocalDateTime endTime){
        //创建好查询条件
        LambdaQueryWrapper<Orders> ordersWrapper = new LambdaQueryWrapper<>();
        ordersWrapper.eq(number!=null,Orders::getNumber,"%"+number+"%");
        ordersWrapper.gt(beginTime!=null,Orders::getOrderTime,beginTime);
        ordersWrapper.gt(endTime!=null,Orders::getOrderTime,endTime);
        //将查询条件传入并执行查询
        Page<OrderDto> allOrder = orderService.getAllOrder(page, pageSize, ordersWrapper);
        if(allOrder!=null){
            return Result.success(allOrder);
        }
        return Result.error("查询失败");
    }

    /**
     * 根据前端传递的OrderId获取到Order的所有菜品信息并放入购物车
     * @param map
     * @return
     */
    @PostMapping("/again")
    public Result<String> OrderAgain(@RequestBody Map<String,Long> map){
        Long orderId = map.get("id");
        log.info("前端的Id{}",orderId);
        // 根据OrderId获取到Order的信息
        orderService.OrderAgainById(orderId);
        // return Result.success("成功");
        return null;
    }

    /**
     * 修改订单状态
     * @param orders
     * @return
     */
    @PutMapping
    public Result<String> sendAndOverOrder(@RequestBody Orders orders){
        log.info("orderId{}",orders.getId());
        log.info("status{}",orders.getStatus());
        boolean b = orderService.updateById(orders);
        if(b){
            return Result.success("状态修改成功");
        }
        return Result.error("状态修改失败");
    }
}
