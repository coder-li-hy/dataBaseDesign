package com.smartcompany.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.smartcompany.common.R;
import com.smartcompany.entity.Level;
import com.smartcompany.service.ILevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 具体职位表 前端控制器
 * </p>
 *
 * @author lihy
 * @since 2024-09-02
 */
@RestController
@RequestMapping("/level")
public class LevelController {
    @Autowired
    private ILevelService levelService;


    @GetMapping("list/{id}")
    public R<List<Level>> list(@PathVariable("id") Integer id) {
        //1.根据职位类型id来查询所有类型中的职务
        LambdaQueryWrapper<Level> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Level::getPid, id);
        List<Level> levels = levelService.list(queryWrapper);
        return R.success(levels);
    }
}
