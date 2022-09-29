package com.reggie.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author 98248
 * @Date: 2022/9/24 - 09 - 24 - 23:14
 * @Description: com.reggie.entity
 * @version: 1.0
 */
@Data
public class Setmeal implements Serializable {
    private static final Long serialVersionUid = 1L;

    private Long id;
    /**
     * 分类ID
     */
    private Long categoryId;
    /**
     * 套餐名称
     */
    private String name;
    /**
     * 套餐价格
     */
    private BigDecimal price;
    /**
     * 套餐状态，0  停售  1起售
     */
    private Integer status;
    /**
     * 套餐编码
     */
    private String code;
    /**
     * 套餐描述信息
     */
    private String description;
    /**
     * 套餐图片
     */
    private String image;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

    /**
     * 套餐是否被逻辑删除
     */
    private Integer isDeleted;
}
