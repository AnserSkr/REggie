package com.reggie.common;

/**
 * @author 98248
 * @Date: 2022/9/24 - 09 - 24 - 23:48
 * @Description: com.reggie.common
 * @version: 1.0
 */
public class CustomException extends RuntimeException{
    public CustomException(String message){
        super(message);
    }
}
