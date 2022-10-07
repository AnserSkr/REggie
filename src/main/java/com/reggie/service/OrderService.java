package com.reggie.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.reggie.common.Result;
import com.reggie.dto.OrderDto;
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

    /**
     * 根据page，pageSize获取Order以及OrderDto
     * @param page
     * @param pageSize
     * @return
     */
    Page<OrderDto> getOrderDtoAndPage(Integer page, Integer pageSize);
    /**
     * 根据page以及PageSize查询到所有的Order
     * @param page
     * @param pageSize
     * @param wrapper
     * @return
     */
    public Page<OrderDto> getAllOrder(Integer page, Integer pageSize, Wrapper wrapper);

    /**
     * 根据OrderId获取Order的所有信息，并逆向生成新的购物车
     * @param orderId
     * @return
     */
    void OrderAgainById(Long orderId);
}
