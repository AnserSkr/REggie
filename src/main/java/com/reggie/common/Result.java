package com.reggie.common;

import com.baomidou.mybatisplus.extension.api.R;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date: 2022/9/20 - 09 - 20 - 23:45
 * @Description: com.reggie.common
 * @version: 1.0
 *
 * 该类为通用的返回结果，服务端的响应都会封装为此对象
 */
@Data
public class Result<T> {
    /**
     * 状态编码：1成功，0或其他数字为失败
     */
    private Integer code;
    /**
     * 提示消息
     *
     */
    private String msg;
    /**
     * 查询到的数据对象的JSON字符串
     */
    private T data;
    /**
     * 动态数据
     */
    private Map map  = new HashMap();

    /**
     * 查询成功时响应的参数
     * @param object
     * @param <T>
     * @return
     */
    public static <T> Result<T> success(T object){
        Result<T> result = new Result<T>();
        result.data = object;
        result.code = 1;
        return result;
    }

    public static <T> Result<T> error(String msg){
        Result result = new Result();
        result.code = 0;
        result.msg = msg;
        return result;
    }

    public Result<T> add(String key,Object value){
        this.map.put(key,value);
        return this;
    }
}
