package com.smartcompany.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smartcompany.common.R;
import com.smartcompany.entity.*;
import com.smartcompany.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户请求申请表 前端控制器
 * </p>
 *
 * @author lihy
 * @since 2024-09-02
 */
@RestController
@RequestMapping("/request")
@Slf4j
public class RequestController {

    @Autowired
    private IRequestService requestService;

    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private IELService elService;

    @Autowired
    private ILRService lrService;

    @Autowired
    private IRightService rightService;

    @Autowired
    private IResponseService responseService;

    /**
     * 根据用户id对申请表进行分页查询
     * @param page
     * @param pageSize
     * @param id
     * @return
     */
    @GetMapping
    public R<Page> page(@RequestParam Integer page, @RequestParam Integer pageSize,@RequestParam(required = false,defaultValue = "1") Integer id){
        log.info("根据传入的页面参数进行分页查询 page:{},pageSize:{}", page, pageSize);

        //新建分页参数
        Page<Request> pageInfo = new Page<>(page, pageSize);
        LambdaQueryWrapper<Request> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(id != null,Request::getId,id);
        requestService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }

    /**
     * 根据传入参数进行请求的修改
     * @param request
     * @return
     */
    @PutMapping
    @Transactional
    public R<String> update(@RequestBody Request request){
        //先删该请求
        requestService.removeById(request);
        //再删同类和未被批准请求
        //获取准备删除请求
        LambdaQueryWrapper<Request> requestWrapper= new LambdaQueryWrapper<>();
        requestWrapper.eq(Request::getEid,request.getEid());
        requestWrapper.eq(Request::getType,request.getType());
        requestWrapper.eq(Request::getLel,request.getLel());
        requestWrapper.eq(Request::getTarget,request.getTarget());
        requestWrapper.eq(Request::getStatus,0);
        List<Integer> ids1 = requestService.list(requestWrapper).stream().map(request1 -> {
            return request1.getId();
        }).collect(Collectors.toList());
        //清除所有同类回复
        requestService.removeBatchByIds(ids1);
        requestService.save(request);
        //首先要清除该请求的所有对应回复
        LambdaQueryWrapper<Response> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Response::getOri, request.getEid());
        queryWrapper.eq(Response::getType, request.getType());
        queryWrapper.eq(Response::getLel, request.getLel());
        queryWrapper.eq(Response::getStatus, request.getStatus());
        responseService.remove(queryWrapper);
        List<Integer> ids = responseService.list(queryWrapper).stream().map(response -> {
            return response.getId();
        }).collect(Collectors.toList());
        responseService.removeBatchByIds(ids);
        //若是类型相同，来源相同，等级相同，状态相同则视这两个请求响应为一对，更新response表
        Response response = new Response();
        response.setOri(request.getTarget());
        response.setType(request.getType());
        response.setLel(request.getLel());
        response.setStatus(request.getStatus());
        response.setIntro(request.getIntro());
        response.setEid(request.getTarget());
        responseService.save(response);
        return R.success("修改成功");
    }

    /**
     * 根据传入的id进行删除
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @Transactional
    public R<String> delete(@PathVariable Integer id){
        //获取准备删除请求
        Request request = requestService.getById(id);
        //首先要清除该请求的所有对应回复
        LambdaQueryWrapper<Response> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Response::getOri, request.getEid());
        queryWrapper.eq(Response::getType, request.getType());
        queryWrapper.eq(Response::getLel, request.getLel());
        queryWrapper.eq(Response::getStatus, request.getStatus());
        List<Integer> ids = responseService.list(queryWrapper).stream().map(response -> {
            return response.getId();
        }).collect(Collectors.toList());
        //若是类型相同，来源相同，等级相同，则视这两个请求响应为一对，更新response表
        responseService.removeBatchByIds(ids);
        requestService.removeById(id);
        return R.success("删除成功");
    }

    /**
     * 根据传入的id进行查询
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<Request> getById(@PathVariable Integer id){
        return R.success(requestService.getById(id));
    }

    /**
     * 用户发起请求并且进行验权
     * @param request
     * @return
     */
    @PostMapping
    @Transactional
    public R<String> post(@RequestBody Request request){
        //获取准备删除请求,清除同类待审核请求
        LambdaQueryWrapper<Request> requestWrapper= new LambdaQueryWrapper<>();
        requestWrapper.eq(Request::getEid,request.getEid());
        requestWrapper.eq(Request::getType,request.getType());
        requestWrapper.eq(Request::getLel,request.getLel());
        requestWrapper.eq(Request::getTarget,request.getTarget());
        requestWrapper.eq(Request::getStatus,0);
        List<Integer> ids1 = requestService.list(requestWrapper).stream().map(request1 -> {
            return request1.getId();
        }).collect(Collectors.toList());
        System.out.println(request);
        //查询有没有符合请求类型和等级
        //1.查看提交上来的目标用户id
        Integer targetId = request.getTarget();
        //2.根据id对目标用户的职务进行查询
        LambdaQueryWrapper<EL> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EL::getEid,targetId);
        List<EL> levelIds = elService.list(queryWrapper);
        boolean flag=false;
        for (int k = 0; k < levelIds.size(); k++) {
            EL Lid = levelIds.get(k);
            LambdaQueryWrapper<LR> lrWrapper = new LambdaQueryWrapper<LR>();
            lrWrapper.eq(LR::getLid,Lid.getLid());
            //获取该职务的所有权限类型id
            List<LR> rids = lrService.list(lrWrapper);
            for (int i=0;i<rids.size();i++) {
                LR rid= rids.get(i);
                LambdaQueryWrapper<Rights> rightWrapper = new LambdaQueryWrapper<>();
                rightWrapper.eq(Rights::getId,rid.getRid());
                List<Rights> rights = rightService.list(rightWrapper);
                for (int j=0;j<rights.size();j++) {
                    Rights right = rights.get(j);
                    //检查权限类型
                    if (right.getType().equals(request.getType())){
                        //检查权限等级
                        if (rid.getLel()>=request.getLel()){
                            //向当前用户的请求表添加请求
                            requestService.save(request);
                            //向目标用户的响应表中写入数据
                            Response response = new Response();
                            response.setEid(request.getTarget());
                            response.setIntro(request.getIntro());
                            response.setType(request.getType());
                            response.setLel(request.getLel());
                            response.setOri(request.getEid());
                            responseService.save(response);
                            flag=true;
                            break;
                        }
                    }
                }
                if (flag) {
                    break;
                }
            }

        }
        if(flag)
            return R.success("请求成功");
        else
            return R.error("请求失败，当前请求等级不符合要求");
    }

}
