package com.reggie.entity;

import lombok.Data;
import org.apache.ibatis.javassist.SerialVersionUID;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author 98248
 * @Date: 2022/10/3 - 10 - 03 - 13:42
 * @Description: com.reggie.entity
 * @version: 1.0
 */
@Data
public class OrderDetail implements Serializable {
    private static final Long serialVersionUID = 1L;

    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 图片
     */
    private String image;

    /**
     * 订单Id
     */
    private Long orderId;

    /**
     * 菜品ID
     */
    private Long dishId;

    /**
     * 套餐Id
     */
    private Long setmealId;

    /**
     * 口味
     */
    private String dishFlavor;

    /**
     * 数量
     */
    private Integer number;

    /**
     * 金额
     */
    private BigDecimal amount;

}
