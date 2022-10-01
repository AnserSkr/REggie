package com.reggie.web;

import com.reggie.common.BaseContext;
import com.reggie.common.Result;
import com.reggie.entity.AddressBook;
import com.reggie.entity.User;
import com.reggie.service.AddressBookService;
import com.reggie.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 98248
 * @Date: 2022/10/1 - 10 - 01 - 16:03
 * @Description: com.reggie.web.interceptor
 * @version: 1.0
 */
@Slf4j
@RestController
@RequestMapping("/addressBook")
public class AddressBookController {
    @Autowired
    AddressBookService addressBookService;

    /**
     * 根据登录的UserId查询其所有的AddressBook
     * @return
     */
    @GetMapping("/list")
    public Result<List<AddressBook>> list(){
        // 从线程共享变量ThreadLocal中获取userId
        Long userId = BaseContext.getCurrentId();
        //根据userID查询所有的地址薄信息
        List<AddressBook> addressBooks = addressBookService.getAllByUserID(userId);
        return Result.success(addressBooks);
    }

    /**
     * 根据前端传递的addressBook来新增默认地址
     * @param addressBook
     * @return
     */
    @PostMapping
    public Result<String> saveAddressBook(@RequestBody AddressBook addressBook){
        log.info("接收数据{}",addressBook);
        //从共享线程变量中获取到UserID
        addressBook.setUserId(BaseContext.getCurrentId());
        //新增addressBook到数据库
        boolean save = addressBookService.save(addressBook);
        if(save){
            return Result.success("新增地址成功");
        }
        return Result.error("新增地址失败");
    }

    /**
     * 根据前端传递的AddressBook的ID信息设为默认地址
     * @param addressBook
     * @return
     */
    @PutMapping("/default")
    public Result<String> setdefaultAddress(@RequestBody AddressBook addressBook){
        log.info("获取数据{}",addressBook);
        boolean b = addressBookService.setDefaultAddressBook(addressBook);
        if(b){
            return Result.success("默认地址设置成功");
        }
        return Result.error("默认地址设置失败");
    }
}
