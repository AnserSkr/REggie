package com.reggie.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.reggie.entity.Category;
import com.reggie.entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author 98248
 * @Date: 2022/9/24 - 09 - 24 - 12:11
 * @Description: com.reggie.service
 * @version: 1.0
 */
@SpringBootTest
@Slf4j
public class CateGoryServiceTest {
    @Autowired
    CategoryService categoryService;

    @Test
    //测试Mybatis中自定操作是否生效
    public void testMybatis(){
        Long id = 0L;
        Category category = categoryService.MySelectOnById(id);
        log.info("查询数据{}",category);
    }

    @Test
    public void testMybatis_plus(){
        Long id = 0L;
        Category one = categoryService.getOne(new LambdaQueryWrapper<Category>().eq(Category::getId, id));
        log.info("Mybatis-plus对象：{}",one);
    }
}
