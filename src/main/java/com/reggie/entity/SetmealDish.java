package com.reggie.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author 98248
 * @Date: 2022/9/27 - 09 - 27 - 20:08
 * @Description: com.reggie.entity
 * @version: 1.0
 */
@Data
public class SetmealDish implements Serializable {
    private static final Long serialVersionUid = 1L;

    private Long id;

    //套餐ID
    private Long setmealId;

    //菜品Id
    private Long dishId;

    //菜品名称(冗余字段)
    private String name;

    //菜品价格
    private BigDecimal price;

    //菜品份数
    private Integer copies;

    //排序
    private Integer sort;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

    //是否删除
    private Integer isDeleted;

}
