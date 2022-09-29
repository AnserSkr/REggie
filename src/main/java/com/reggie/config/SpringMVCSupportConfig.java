package com.reggie.config;

import com.reggie.common.JacksonObjectMapper;
import com.reggie.web.interceptor.LoginInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * @Date: 2022/9/16 - 09 - 16 - 17:53
 * @Description: com.reggie.config
 * @version: 1.0
 */
@Slf4j
@Configuration
@EnableTransactionManagement
public class SpringMVCSupportConfig extends WebMvcConfigurationSupport {
    /**
     * 为静态资源路径设置映射
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");
        log.info("设置静态资源映射");
    }


    /**
     * 设置拦截器保证一些功能
     * @param registry
     */
    // @Autowired
    // LoginInterceptor loginInterceptor;
    // @Override
    // protected void addInterceptors(InterceptorRegistry registry) {
    //     registry.addInterceptor(loginInterceptor).addPathPatterns("/*");
    // }

    /**
     * 扩展消息转换器
     * @param converters
     */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        log.info("扩展的消息转换器");
        //创建消息转换器
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        //将自定消息转换器传入
        mappingJackson2HttpMessageConverter.setObjectMapper(new JacksonObjectMapper());

        //注册消息转换器同时，设置索引，让自己的消息转换器放在最前面，否则默认的消息转换器会在最前面，用不上我们的转换器
        converters.add(0,mappingJackson2HttpMessageConverter);
    }
}
