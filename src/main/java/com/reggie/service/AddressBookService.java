package com.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.reggie.entity.AddressBook;

import java.util.List;

/**
 * @author 98248
 * @Date: 2022/10/1 - 10 - 01 - 16:01
 * @Description: com.reggie.service
 * @version: 1.0
 */
public interface AddressBookService extends IService<AddressBook> {
    /**
     * 根据userId查询对的AddressBook
     * @param userId
     * @return
     */
    List<AddressBook> getAllByUserID(Long userId);
    /**
     * 设置默认的地址
     * @param addressBook
     * @return
     */
    boolean setDefaultAddressBook(AddressBook addressBook);

    /**
     * 获取默认地址信息
     * @return
     */
    AddressBook getDefaultAddressByUserId(Long userId);
}
