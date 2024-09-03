package com.smartcompany.service.impl;

import com.smartcompany.entity.Request;
import com.smartcompany.mapper.RequestMapper;
import com.smartcompany.service.IRequestService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户请求申请表 服务实现类
 * </p>
 *
 * @author lihy
 * @since 2024-09-02
 */
@Service
public class RequestServiceImpl extends ServiceImpl<RequestMapper, Request> implements IRequestService {

}
