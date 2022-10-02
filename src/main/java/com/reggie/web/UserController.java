package com.reggie.web;

import com.reggie.common.Result;
import com.reggie.entity.User;
import com.reggie.service.UserService;
import com.reggie.utils.SMSUtils;
import com.reggie.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Random;

/**
 * @author 98248
 * @Date: 2022/9/29 - 09 - 29 - 22:57
 * @Description: com.reggie.web
 * @version: 1.0
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    /**
     * 根据前端接收到的phone和code，进行Session的code匹配，成功则登录
     * @param request
     * @param map
     * @return
     */
    @PostMapping("/login")
    public Result<User> login(HttpServletRequest request,@RequestBody Map<String,String> map){
        log.info("{}",map);
        //首先校验code是否正确
        String code = map.get("code");
        boolean equals = code.equals(request.getSession().getAttribute("code"));
        // 如果验证码匹配成功，获取到Phone对应的User数据并返回
        if(equals){
            String phone = (String) map.get("phone");
            User user = userService.getUserByPhone(phone);
            request.getSession().setAttribute("user",user.getId());
            return Result.success(user);
        }
        return Result.error("验证码输入错误");

    }

    @GetMapping("/sendMsg")
    public Result<String> sendMsg(HttpServletRequest request,String phone) throws Exception {
        //首先根据phone是否是新用户
        User user = userService.getUserByPhone(phone);
        //phone对应的User用户已经存在,生成验证码并发送
        if(user!=null){
            return sendMsg(request);
        }else {//如果用户不存在，则新建用户，然后发送验证码
            User newUser = new User();
            newUser.setPhone(phone);
            newUser.setStatus(1);
            boolean save = userService.save(newUser);
            //用户新建成功之后，发送验证码
            if(save){
                return sendMsg(request);
            }
        }
        return Result.error("验证码发送失败");
    }

    /**
     * 随机发送4/6位验证你码，并存入session中
     * @param request
     * @throws Exception
     */
    private Result<String> sendMsg(HttpServletRequest request) throws Exception {
        Random random = new Random();
        Integer[] i = new Integer[]{4,6};
        String code = ValidateCodeUtils.generateValidateCode(i[random.nextInt(2)]).toString();
        // SMSUtils.sendMessage(code);
        log.info("验证码为：{}",code);
        request.getSession().setAttribute("code", code);
        return Result.success("验证码发送成功");
    }
}
