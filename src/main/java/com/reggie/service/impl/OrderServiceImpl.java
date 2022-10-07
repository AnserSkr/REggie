package com.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.reggie.common.BaseContext;
import com.reggie.common.CustomException;
import com.reggie.common.Result;
import com.reggie.dto.OrderDto;
import com.reggie.entity.*;
import com.reggie.mapper.OrderMapper;
import com.reggie.service.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author 98248
 * @Date: 2022/10/3 - 10 - 03 - 21:19
 * @Description: com.reggie.service.impl
 * @version: 1.0
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Orders> implements OrderService {
    @Autowired
    AddressBookService addressBookService;
    @Autowired
    UserService userService;
    @Autowired
    OrderDetailService orderDetailService;
    @Autowired
    ShoppingCartService shoppingCartService;
    /**
     * 根据接收来的前端数据封装的order进行Order的添加操作，同步插入OrderDetail
     * @param orders
     * @return
     */
    @Override
    @Transactional
    public boolean addOrderByincept(Orders orders) {
        //获取用户Id,并查询到UserId对应的User数据
        Long userId = BaseContext.getCurrentId();
        User user = userService.getById(userId);
        //获取数据库中选中的下单地址数据信息
        AddressBook addressBook = addressBookService.getById(orders.getAddressBookId());
        //用于检测user以及addressBook是否存在
        if(user==null){
            throw new CustomException("下单用户出现故障，无法下单");
        }else if(addressBook==null){
            throw new CustomException("下单地址信息有误，无法下单");
        }
        //封装需要插入的订单order对象，
            // 使用MP提供的工具生成订单号，此数值唯一
        long number = IdWorker.getId();
        orders.setId(number);
        orders.setNumber(String.valueOf(number));
        //未付款状态为1,此处为了方便测试，将状态修改为付款2
        // orders.setStatus(1);
        orders.setStatus(2);
        orders.setUserId(userId);
        orders.setOrderTime(LocalDateTime.now());
        orders.setCheckoutTime(LocalDateTime.now());
        orders.setPhone(addressBook.getPhone());
        orders.setUserName(user.getName());
        orders.setConsignee(addressBook.getConsignee());
        orders.setAddress((addressBook.getProvinceName()==null?"":addressBook.getProvinceName())
                +(addressBook.getCityName()==null?"":addressBook.getCityName())
                +(addressBook.getDistrictName()==null?"":addressBook.getDistrictName())
                +(addressBook.getDetail()==null?"":addressBook.getDetail()));

        //封装OrderDetail同时计算订单总金额
        List<ShoppingCart> shoppingCarts = shoppingCartService.getAllByUserId(userId);
        AtomicInteger amount = new AtomicInteger(0);
        //将每一个shoppingCart数据封装到orderDetail中
        List<OrderDetail> orderDetails = shoppingCarts.stream().map(shoppingCart -> {
            OrderDetail orderDetail = new OrderDetail();
            amount.addAndGet(shoppingCart.getAmount().multiply(new BigDecimal(shoppingCart.getNumber())).intValue());
            BeanUtils.copyProperties(shoppingCart, orderDetail, "id");
            orderDetail.setOrderId(orders.getId());
            return orderDetail;
        }).collect(Collectors.toList());

        //将计算后的订单总金额放入ordeas
        orders.setAmount(new BigDecimal(amount.get()));

        //执行orders的插入操作操作
        boolean save = this.save(orders);

        //orders插入成功后插入OrderDetail,并返回插入的状态
        if(save){
            boolean b = orderDetailService.saveBatch(orderDetails);
            if(b){
                // 操作成功之后就可以清空购物车信息了
                return shoppingCartService.delAllShoppingCartByUserId(userId);
            }
            return false;
        }
        return false;
    }

    /**
     * 根据page，pageSize获取Order以及OrderDto
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public Page<OrderDto> getOrderDtoAndPage(Integer page, Integer pageSize) {
        //根据UserId获取到当前用户的所有订单
        Long userId = BaseContext.getCurrentId();
        Page<Orders> orderPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Orders> orderWrapper = new LambdaQueryWrapper<>();
        orderWrapper.eq(userId!=null,Orders::getUserId,userId);
        this.page(orderPage, orderWrapper);

        if(orderPage.getRecords().size()!=0){
            Page<OrderDto> orderDtoPage = getOrderDtoPage(orderPage);
            return orderDtoPage;
        }
        return null;
    }

    /**
     * 根据orderPage中的Order对象封装为orderDto
     * @param orderPage
     * @return
     */
    @NotNull
    private Page<OrderDto> getOrderDtoPage(Page<Orders> orderPage) {
        //根据用户的订单信息获取到OrderDetail数据，并完成封装
        Page<OrderDto> orderDtoPage = new Page<>();
        BeanUtils.copyProperties(orderPage,orderDtoPage,"records");
        //将order对象封装为OrderDto对象
        List<OrderDto> orderDtos = orderPage.getRecords().stream().map(orders -> {
            OrderDto orderDto = new OrderDto();
            BeanUtils.copyProperties(orders, orderDto);
            //根据OrderId查询到所有的OrderDetail数据，并放入OrderDto
            LambdaQueryWrapper<OrderDetail> orderDetaiWrapper = new LambdaQueryWrapper<>();
            orderDetaiWrapper.eq(OrderDetail::getOrderId, orders.getId());
            List<OrderDetail> orderDetails = orderDetailService.list(orderDetaiWrapper);
            orderDto.setOrderDetails(orderDetails);
            //根据UserId查询到下单用户名
            User user = userService.getById(orders.getUserId());
            if(user.getName()==null){
                orderDto.setUserName("未设置用户名");
            }else {
                orderDto.setUserName(user.getName());
            }
            return orderDto;
        }).collect(Collectors.toList());
        orderDtoPage.setRecords(orderDtos);
        return orderDtoPage;
    }

    /**
     * 根据page以及PageSize查询到所有的Order
     * @param page
     * @param pageSize
     * @param wrapper
     * @return
     */
    @Override
    public Page<OrderDto> getAllOrder(Integer page, Integer pageSize, Wrapper wrapper){
        Page<Orders> ordersPage = new Page<>();
        this.page(ordersPage,wrapper);
        if(ordersPage.getRecords().size()!=0){
            Page<OrderDto> orderDtoPage = getOrderDtoPage(ordersPage);
            if(orderDtoPage!=null){
                return orderDtoPage;
            }
        }
        return null;
    }


    /**
     * 根据OrderId获取Order的所有信息，并逆向生成新的购物车
     * @param orderId
     * @return
     */
    @Override
    @Transactional
    public void OrderAgainById(Long orderId) {
        //根据OrderId获取订单下的详细信息到OrderDetail
        LambdaQueryWrapper<OrderDetail> orderDetailWrapper = new LambdaQueryWrapper<>();
        orderDetailWrapper.eq(OrderDetail::getOrderId,orderId);
        List<OrderDetail> orderDetails = orderDetailService.list(orderDetailWrapper);
        // 将每一个订单中的菜品信息，复制到新的购物车中
        for (OrderDetail orderDetail : orderDetails) {
            //将orderDetail的信息复制到ShoppingCart中
            ShoppingCart shoppingCart = new ShoppingCart();
            BeanUtils.copyProperties(orderDetail,shoppingCart,new String[]{"id","orderId"});
            //执行购物车的插入操作
            shoppingCartService.addShoppingCart(shoppingCart);
        }
    }
}
