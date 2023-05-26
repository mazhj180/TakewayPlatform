package com.mazhj.reggie.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mazhj.reggie.backend.mapper.EmployeeMapper;
import com.mazhj.reggie.backend.pojo.Employee;
import com.mazhj.reggie.backend.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

}
