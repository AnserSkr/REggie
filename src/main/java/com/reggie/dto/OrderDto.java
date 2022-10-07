package com.reggie.dto;

import com.reggie.entity.OrderDetail;
import com.reggie.entity.Orders;
import lombok.Data;

import java.util.List;

/**
 * @author 98248
 * @Date: 2022/10/7 - 10 - 07 - 14:29
 * @Description: com.reggie.dto
 * @version: 1.0
 */
@Data
public class OrderDto extends Orders {
    private String userName;
    private List<OrderDetail> orderDetails;
}
