package com.reggie.web;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.reggie.common.Result;
import com.reggie.entity.Employee;
import com.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Date: 2022/9/16 - 09 - 16 - 23:25
 * @Description: com.reggie.web
 * @version: 1.0
 */
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployController {
    @Autowired
    EmployeeService employeeService;

    /**
     * 员工登录
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public Result<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        //1.根据账号名称查询账号是否注册
        LambdaQueryWrapper<Employee> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(lambdaQueryWrapper);
        if(emp ==null){
            return Result.error("账号未注册，请检查后再登录");
        }
        // 2.检查员工账号是否被禁用
        if(emp.getStatus()==0){
            return Result.error("员工账户已被管理员禁用，请联系管理员");
        }
        //3.对传递过来的明文密码进行MD5加密，并判断密码是否正确
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if(!emp.getPassword().equals(password)){
            return Result.error("密码错误，请检查后再登录");
        }
        // 4.账户名密码正确，账户未被禁用，存入Session中并返回
        request.getSession().setAttribute("employee",emp.getId());
        System.out.println();
        return Result.success(emp);
    }

    /**
     * 员工退出
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public Result<String> logout(HttpServletRequest request){
        request.getSession().removeAttribute("employee");
        return Result.success("退出成功");
    }

    /**
     * 新增员工操作
     * @param employee
     * @return
     */
    @PostMapping
    public Result<String> addEmployee(HttpServletRequest request, @RequestBody Employee employee)throws Exception{
        // log.info("新增员工",employee.toString());
        //1.为员工的各项属性设置初始值
        // 员工初始密码123456，并做Md5加密处理
        String password = DigestUtils.md5DigestAsHex("123456".getBytes());
        employee.setPassword(password);
        // // 员工创建时间,更新时间
        // employee.setCreateTime(LocalDateTime.now());
        // employee.setUpdateTime(LocalDateTime.now());
        // // 员工的创建者
        // Long empID = (Long) request.getSession().getAttribute("employee");
        // employee.setCreateUser(empID);
        // employee.setUpdateUser(empID);

        // 将员工对象保存到数据库中
        boolean save = employeeService.save(employee);
        if(save){
            return Result.success("新增员工成功");
        }else {
            return Result.error("新增员工错误");
        }
    }

    /**
     * 员工分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public Result<IPage>  SelectAllAndPagination(Integer page,Integer pageSize,String name){
        log.info("当前页:"+page+" 每页大小 "+pageSize);
        //构建分页构造器，传入分页参数
        IPage<Employee> Ipage = new Page(page,pageSize);
        //条件构造器，用于构造查询条件
        LambdaQueryWrapper<Employee> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            //添加模糊查询条件
        lambdaQueryWrapper.likeRight(!StringUtils.isEmpty(name),Employee::getName,name);
            //添加配许条件
        lambdaQueryWrapper.orderByDesc(Employee::getName);
        //执行查询,并传入查询条件
        employeeService.page(Ipage,lambdaQueryWrapper);

        if(Ipage.getRecords() !=null ){
            return Result.success(Ipage);
        }
        return Result.error("查询失败");
    }

    /**
     * 根据员工ID修改信息
     * @param request
     * @param employee
     * @return
     */
    @PutMapping
    public Result<String> updateEmploy(HttpServletRequest request,@RequestBody Employee employee){
        log.info(employee.toString());

        // //标记更新者与更新时间,status已经保存到了employee中
        // Long empID = (Long) request.getSession().getAttribute("employee");
        // employee.setUpdateUser(empID);
        // employee.setUpdateTime(LocalDateTime.now());
        // long id = Thread.currentThread().getId();
        // log.info("线程ID:{}",id);
        //执行修改
        boolean b = employeeService.updateById(employee);
        if(b){
            return Result.success("修改成功");
        }
        return Result.error("修改失败");
    }

    /**
     * 根据ID查询employ，为修改员工提供数据支持
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Employee> selectById(@PathVariable Long id){
        LambdaQueryWrapper<Employee> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(null != id,Employee::getId,id);
        Employee one = employeeService.getOne(lambdaQueryWrapper);
        if(one!=null){ return Result.success(one);}
        return Result.error("员工不存在");
    }
}
