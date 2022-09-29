package com.reggie.common;

/**
 * @author 98248
 * @Date: 2022/9/23 - 09 - 23 - 23:49
 * @Description: com.reggie.common
 * @version: 1.0
 * 基于ThreadLocal封装的工具类，用于保存和获取当前登录用户的ID
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal =new ThreadLocal<>();

    /**
     * 为ThreadLocal变量设置值
     * @param id
     */
    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    /**
     * 获取ThreadLocal变量内的值
     * @return
     */
    public static Long getCurrentId(){
        return threadLocal.get();
    }
}
