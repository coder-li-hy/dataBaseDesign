package com.smartcompany.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartcompany.common.R;
import com.smartcompany.dto.EmployeeDto;
import com.smartcompany.entity.*;
import com.smartcompany.service.IELService;
import com.smartcompany.service.IEmployeeService;
import com.smartcompany.service.ILevelService;
import com.smartcompany.service.IPositionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author lihy
 * @since 2024-09-02
 */
@RestController
@RequestMapping("/employee")
@Slf4j
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private IPositionService positionService;

    @Autowired
    private ILevelService levelService;

    @Autowired
    private IELService elService;

    /**
     * 根据传入参数和分页信息进行分页查询
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @PostMapping
    public R<Page> page(@RequestParam int page, @RequestParam int pageSize, @RequestParam(required = false) String name) {
        log.info("根据传入的页面参数进行分页查询 page:{},pageSize:{}", page, pageSize);
        //创建分页查询对象
        Page<Employee> pageInfo = new Page<>(page, pageSize);
        //条件构造器对象
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name != null, Employee::getName, name); //如果name不为空，则进行name模糊查询
        //根据分页信息进行查询
        employeeService.page(pageInfo, queryWrapper);
        return R.success(pageInfo);
    }


    /**
     * 根据id对员工信息进行查询
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @Transactional
    public R<EmployeeDto> getById(@PathVariable Integer id) {
        log.info("根据id查询员工信息以及对应的职位信息");
        EmployeeDto employeeDto = new EmployeeDto();
        //1.获取员工基础信息
        Employee employee = employeeService.getById(id);
        //进行属性的拷贝
        BeanUtil.copyProperties(employee, employeeDto);
        //2.获取员工职位信息
        LambdaQueryWrapper<EL> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EL::getEid, id);
        List<EL> list = elService.list(queryWrapper);
        List<Level> levels = list.stream().map(el -> {
            //返回level对象
            return levelService.getById(el.getLid());
        }).collect(Collectors.toList());
        List<Position> positions = levels.stream().map(level -> {
            //返回position对象
            return positionService.getById(level.getPid());
        }).collect(Collectors.toList());
        //3.将职位信息封装到employeeDto中
        employeeDto.setPosition(positions);
        employeeDto.setLevel(levels);
        return R.success(employeeDto);
    }

    /**
     * 根据传入参数对员工信息进行修改
     *
     * @param employee
     * @return
     */
    @PutMapping
    @Transactional
    public R<String> update(@RequestBody EmployeeDto employee) {
        log.info("根据id修改员工信息");
        //1.进行非空判断
        if (employee == null) {
            return R.error("修改失败");
        }
        //2.进行修改操作
        employeeService.updateById(employee);
        //3.提取position
        List<Position> positions = employee.getPosition();
        //4.提取level
        List<Level> levels = employee.getLevel();
        List<Integer> Lid = levels.stream().map(level -> {
            return level.getId();
        }).collect(Collectors.toList());
        //修改对应用户的e-l表进行职位信息指向修改
        LambdaUpdateWrapper<EL> updateWrapper = new LambdaUpdateWrapper<>();
        Lid.stream().forEach(id -> {
            updateWrapper.eq(EL::getEid, employee.getId()).set(EL::getLid, id);
        });
        return R.success("修改成功");
    }

    /**
     * 根据传入参数对员工信息进行添加
     *
     * @param employee
     * @return
     */
    @PostMapping("/add")
    public R<String> add(@RequestBody EmployeeDto employee) {
        log.info("根据传入的参数修改员工信息");
        //1.进行非空判断
        if (employee == null) {
            return R.error("添加失败");
        }
        //2.进行修改操作
        employeeService.save(employee);
        //3.提取position
        List<Position> positions = employee.getPosition();
        //4.提取level
        List<Level> levels = employee.getLevel();
        List<Integer> Lid = levels.stream().map(level -> {
            return level.getId();
        }).collect(Collectors.toList());
        //修改对应用户的e-l表进行职位信息指向添加
        Lid.stream().forEach(id -> {
            EL el = new EL();
            el.setEid(employee.getId());
            el.setLid(id);
            elService.save(el);
        });
        return R.success("添加成功");
    }
}

