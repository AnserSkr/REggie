package com.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.reggie.common.BaseContext;
import com.reggie.common.Result;
import com.reggie.entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 98248
 * @Date: 2022/9/21 - 09 - 21 - 21:17
 * @Description: com.reggie.filter
 * @version: 1.0
 */
@Slf4j
@WebFilter(filterName = "lockCheckFilter",urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    //路径匹配器，支持String格式的通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        long id = Thread.currentThread().getId();
        log.info("线程ID:{}",id);
        //1.获取请求的URI地址,并设置不进行过滤的内容
        String requestURI = httpServletRequest.getRequestURI();
        log.info("拦截到请求："+requestURI);
        //定义不需要进行过滤的连接
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**"
        };
        // 2.使用AntPathMatcher路径匹配器，对字符串**或*的通配符与requestUrl进行比对处理，该操作封装到check()中
        boolean check = check(urls, requestURI);
        // 3.如果不需要处理则放行
        if(check){
            log.info("本次请求不需要处理"+requestURI);
            chain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }
        Long empId = (Long) httpServletRequest.getSession().getAttribute("employee");
        // 4.如果连接需要被过滤，判断是否登录
        if(empId != null){
            log.info("用户已登录{}",empId);
            BaseContext.setCurrentId(empId);
            chain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }
        // 5.如果未登录，则响应数据到前端，由响应拦截器跳转到指定页面
        log.info("用户未登录，即将跳转页面");
        httpServletResponse.getWriter().write(JSON.toJSONString(Result.error("NOTLOGIN")));
        return;

    }

    /**
     * 路径匹配方法，检查本次请求是否需要被执行
     * @param urls
     * @param requestUrl
     * @return
     */
    public boolean check(String[] urls,String requestUrl){
        for(String url:urls){
            //匹配成功会返回true
            boolean match = PATH_MATCHER.match(url, requestUrl);
            if(match){
                return true;
            }
        }
        return false;
    }
}
