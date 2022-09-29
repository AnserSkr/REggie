package com.reggie.dto;

import com.reggie.entity.Setmeal;
import com.reggie.entity.SetmealDish;
import lombok.Data;

import java.util.List;

/**
 * @author 98248
 * @Date: 2022/9/27 - 09 - 27 - 20:17
 * @Description: com.reggie.dto
 * @version: 1.0
 */
@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
