package com.mazhj.reggie.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mazhj.reggie.backend.pojo.Employee;
import com.mazhj.reggie.backend.service.EmployeeService;
import com.mazhj.reggie.common.Res;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    /**
     * 员工登录
     * @param request
     * @param session
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public Res<Employee> login(HttpServletRequest request, HttpSession session, Employee employee){
        System.out.println(request.getRequestURI());

        String password = employee.getPassword();
        //md5加密
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        //根据用户名查询数据库
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Employee::getUsername,employee.getUsername());
        Employee employee1 = employeeService.getOne(wrapper);

        if (employee1 == null) { //用户不存在
            return Res.error("用户名或密码错误");
        }
        if (!employee1.getPassword().equals(password)) { // 密码错误
            return Res.error("用户名或密码错误");
        }
        if(employee1.getStatus() == 0){ //账户禁用
            return Res.error("账号已禁用");
        }
        session.setAttribute("Employee",employee1);

        return Res.success(employee1);
    }

    /**
     * 退出登录
     * @param session
     * @return
     */
    @PostMapping("/logout")
    public Res<String> logout(HttpSession session){
        session.removeAttribute("Employee");
        return Res.success("退出成功");
    }

    @GetMapping("/page")
    public Res<Page> page(int page,int pageSize,String name){
        Page<Employee> pageInfo = new Page<>(page,pageSize);
        //条件构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(name),Employee::getName,name);
        queryWrapper.orderByDesc(Employee::getUpdateTime);

        employeeService.page(pageInfo,queryWrapper);

        return Res.success(pageInfo);
    }
}
