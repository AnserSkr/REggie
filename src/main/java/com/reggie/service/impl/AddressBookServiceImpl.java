package com.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.reggie.common.BaseContext;
import com.reggie.entity.AddressBook;
import com.reggie.mapper.AddressBookMapper;
import com.reggie.service.AddressBookService;
import okhttp3.Address;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 98248
 * @Date: 2022/10/1 - 10 - 01 - 16:02
 * @Description: com.reggie.service.impl
 * @version: 1.0
 */
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {

    /**
     * 根据userId查询对的AddressBook
     * @param userId
     * @return
     */
    @Override
    public List<AddressBook> getAllByUserID(Long userId) {
        LambdaQueryWrapper<AddressBook> addressBookWrapper = new LambdaQueryWrapper<>();
        addressBookWrapper.eq(AddressBook::getUserId,userId);
        List<AddressBook> addressBooks = this.list(addressBookWrapper);
        return addressBooks;
    }

    /**
     * 设置默认的地址
     *
     * @param addressBook
     * @return
     */
    @Override
    public boolean setDefaultAddressBook(AddressBook addressBook) {
        // 从线程共享变量中获取UserId
        Long userId = BaseContext.getCurrentId();
        //设置userId下所有的地址信息为非默认地址
        LambdaUpdateWrapper<AddressBook> addressBookWrapper = new LambdaUpdateWrapper<>();
        addressBookWrapper.eq(AddressBook::getUserId,userId);
        addressBookWrapper.set(AddressBook::getIsDefault,0);
        this.update(addressBookWrapper);
        //设置指定的address为默认地址
        addressBook.setIsDefault(1);
        boolean b = this.updateById(addressBook);
        return b;
    }

    /**
     * 根据userID，获取用户的默认地址信息
     * @param userId
     * @return
     */
    @Override
    public AddressBook getDefaultAddressByUserId(Long userId) {
        LambdaQueryWrapper<AddressBook> addressBookWrapper = new LambdaQueryWrapper<>();
        addressBookWrapper.eq(AddressBook::getUserId,userId);
        addressBookWrapper.eq(AddressBook::getIsDefault,1);
        AddressBook addressBook = this.getOne(addressBookWrapper);
        return addressBook;
    }
}
