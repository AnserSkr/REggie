package com.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.reggie.entity.User;
import com.reggie.mapper.UserMapper;
import com.reggie.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author 98248
 * @Date: 2022/9/29 - 09 - 29 - 22:56
 * @Description: com.reggie.service.impl
 * @version: 1.0
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    /**
     * 根据phone判断用户是否已经存在
     *
     * @param phone
     * @return
     */
    @Override
    public User getUserByPhone(String phone) {
        LambdaQueryWrapper<User> userWrapper = new LambdaQueryWrapper<>();
        userWrapper.eq(phone!=null,User::getPhone,phone);
        User user = this.getOne(userWrapper);
        return user;
    }
}
