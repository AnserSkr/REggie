package com.reggie.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author 98248
 * @Date: 2022/9/24 - 09 - 24 - 21:41
 * @Description: com.reggie.entity
 * @version: 1.0
 */
@Data
public class Dish implements Serializable {
    private static final Long serialVersionUID = 1L;

    private Long id;

    /**
     * 菜品名称
     */
    private String name;
    /**
     * 菜品分类 id
     */
    private Long categoryId;

    /**
     * 菜品价格
     */
    private BigDecimal price;
    /**
     * 商品码
     */
    private String code;
    /**
     * 图片路径
     */
    private String image;
    /**
     * 菜品描述信息
     */
    private String description;
    /**
     * 菜品状态  0 停售  1  起售
     */
    private Integer status;
    /**
     * 菜品的顺序码
     */
    private Integer sort;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

    /**
     * 是否逻辑删除
     */
    private Integer isDeleted;
}
