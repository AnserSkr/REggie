package com.reggie.web.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Date: 2022/9/21 - 09 - 21 - 17:49
 * @Description: com.reggie.web.interceptor
 * @version: 1.0
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object emp = request.getSession().getAttribute("emp");
        if(emp==null) {
            response.sendRedirect(request.getContextPath()+"/backend/page/login/login.html");
            return false;
        }
        return true;
    }

}
