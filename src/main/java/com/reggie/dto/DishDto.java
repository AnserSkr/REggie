package com.reggie.dto;

import com.reggie.entity.Dish;
import com.reggie.entity.DishFlavor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 98248
 * @Date: 2022/9/26 - 09 - 26 - 11:03
 * @Description: com.reggie.dto
 * @version: 1.0
 */
@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();
    /**
     * 分类或套餐名称
     */
    private String categoryName;

    private Integer copies;
}
