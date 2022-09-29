package com.reggie;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

import java.util.Collection;

/**
 * @Date: 2022/9/16 - 09 - 16 - 17:51
 * @Description: com.reggie
 * @version: 1.0
 */
@Slf4j
@SpringBootApplication
@ServletComponentScan
public class ReggieApplication{
    public static void main(String[] args) {
        SpringApplication.run(ReggieApplication.class);
        log.info("项目启动成功");
    }
}
