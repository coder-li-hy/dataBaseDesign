package com.smartcompany.service.impl;

import com.smartcompany.entity.Employee;
import com.smartcompany.mapper.EmployeeMapper;
import com.smartcompany.service.IEmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lihy
 * @since 2024-09-02
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

}
