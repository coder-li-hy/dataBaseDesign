package com.smartcompany.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartcompany.Utils.JwtUtils;
import com.smartcompany.common.R;
import com.smartcompany.entity.Request;
import com.smartcompany.entity.Response;
import com.smartcompany.service.IRequestService;
import com.smartcompany.service.IResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author lihy
 * @since 2024-09-02
 */
@RestController
@RequestMapping("/response")
public class ResponseController {
    @Autowired
    private IResponseService responseService;

    @Autowired
    private IRequestService requestService;

    /**
     * 根据用户id对响应进行分页查询
     *
     * @param page
     * @param pageSize
     * @param id
     * @return
     */
    @GetMapping
    public R<Page> page(@RequestParam Integer page, @RequestParam Integer pageSize, @RequestParam(required = false) Integer id) {
        //1.构造分页参数
        Page<Response> pageInfo = new Page<>(page, pageSize);
        //2.构造分页查询条件
        LambdaQueryWrapper<Response> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(id != null, Response::getEid, id);
        responseService.page(pageInfo, queryWrapper);
        return R.success(pageInfo);
    }


    /**
     * 根据id查询响应
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<Response> getById(@PathVariable Integer id) {
        Response response = responseService.getById(id);
        return R.success(response);
    }

    @PutMapping
    @Transactional
    public R<String> update(@RequestBody Response response) {
        //进行response表的更新
        responseService.updateById(response);
        //删除所有同类请求
        //进行request表的对应更新
        //首先需要找到对应的request
        LambdaQueryWrapper<Request> requestWrapper = new LambdaQueryWrapper<>();
        requestWrapper.eq(Request::getEid, response.getOri());
        //找到来源用户的所有待审请求
        requestWrapper.eq(Request::getType, response.getType());
        requestWrapper.eq(Request::getLel, response.getLel());
        requestWrapper.eq(Request::getStatus,0);
        List<Integer> ids = requestService.list(requestWrapper).stream().map(request -> {
            return request.getId();
        }).collect((Collectors.toList()));
        //若是类型相同，来源相同，等级相同，则视这两个请求响应为一对，更新request表
        requestService.removeBatchByIds(ids);
        Request request = new Request();
        request.setEid(response.getOri());
        request.setType(response.getType());
        request.setLel(response.getLel());
        request.setIntro(response.getIntro());
        request.setStatus(response.getStatus());
        request.setTarget(response.getEid());
        if (response.getStatus() == 1) {
            //若审核通过
            //生成jwt令牌并赋值给对应request
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", response.getOri());
            request.setJwt(JwtUtils.generateJwt(claims));
        }
        else
            request.setJwt("无");
        requestService.save(request);
        return R.success("更新成功");
    }
}
