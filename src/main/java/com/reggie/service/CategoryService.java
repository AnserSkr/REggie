package com.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.reggie.entity.Category;

/**
 * @author 98248
 * @Date: 2022/9/24 - 09 - 24 - 11:54
 * @Description: com.reggie.service
 * @version: 1.0
 */
public interface CategoryService extends IService<Category> {
    Category MySelectOnById(Long id);
    boolean remove(Long id);

    String getNameById(Long categoryId);
}
