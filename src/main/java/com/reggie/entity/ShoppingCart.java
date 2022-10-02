package com.reggie.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author 98248
 * @Date: 2022/10/1 - 10 - 01 - 21:29
 * @Description: com.reggie.entity
 * @version: 1.0
 */
@Data
public class ShoppingCart implements Serializable {
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
     * 用户ID
     */
    private Long userId;
    /**
     * 菜品ID
     */
    private Long dishId;
    /**
     * 套餐ID
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

    // @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

}
