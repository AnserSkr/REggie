package com.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.reggie.entity.AddressBook;
import com.reggie.entity.User;

/**
 * @author 98248
 * @Date: 2022/9/29 - 09 - 29 - 22:55
 * @Description: com.reggie.service
 * @version: 1.0
 */
public interface UserService extends IService<User> {
    /**
     * 根据phone判断用户是否已经存在
     * @param phone
     * @return
     */
    User getUserByPhone(String phone);
}
