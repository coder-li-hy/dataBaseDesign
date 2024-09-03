package com.smartcompany.controller;


import com.smartcompany.common.R;
import com.smartcompany.entity.Position;
import com.smartcompany.service.IPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lihy
 * @since 2024-09-02
 */
@RestController
@RequestMapping("/position")
public class PositionController {
    @Autowired
    private IPositionService positionService;

    /**
     * 获取所有职务类型
     * @return
     */
    @GetMapping("/list")
    public R<List<Position>> list(){
        List<Position> list = positionService.list();
        return R.success(list);
    }
}
