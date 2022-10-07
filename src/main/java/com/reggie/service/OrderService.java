package com.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.reggie.entity.Orders;

/**
 * @author 98248
 * @Date: 2022/10/3 - 10 - 03 - 21:19
 * @Description: com.reggie.service
 * @version: 1.0
 */
public interface OrderService extends IService<Orders> {
    /**
     * 根据接收来的前端数据封装的order进行Order的添加操作
     * @param orders
     * @return
     */
    boolean addOrderByincept(Orders orders);
}
