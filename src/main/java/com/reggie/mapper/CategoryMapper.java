package com.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.reggie.entity.Category;

/**
 * @author 98248
 * @Date: 2022/9/24 - 09 - 24 - 11:52
 * @Description: com.reggie.mapper
 * @version: 1.0
 */
public interface CategoryMapper extends BaseMapper<Category> {
    Category MySelectOneById(Long id);
}
