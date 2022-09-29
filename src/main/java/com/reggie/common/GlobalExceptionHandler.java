package com.reggie.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author 98248
 * @Date: 2022/9/22 - 09 - 22 - 22:10
 * @Description: com.reggie.common
 * @version: 1.0
 */
//对增加了RestController以及Controller注解的类进行AOP的拦截
@RestControllerAdvice(annotations = {RestController.class, Controller.class})
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Result<String> doSQLException(SQLIntegrityConstraintViolationException ex){
        log.error(ex.getMessage());
        if(ex.getMessage().contains("Duplicate entry")){
            //将字符串按” “分割，并存入数组中
            String[] split = ex.getMessage().split(" ");
            String msg = split[2]+" 已存在，请换一个";
            return Result.error(msg);
        }
        return Result.error("未知错误");
    }
    @ExceptionHandler(CustomException.class)
    public Result<String> doCustomException(CustomException ex){
        log.info(ex.getMessage());
        return Result.error(ex.getMessage());
    }
}
