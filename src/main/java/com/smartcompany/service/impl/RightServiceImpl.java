package com.smartcompany.service.impl;

import com.smartcompany.entity.Rights;
import com.smartcompany.mapper.RightMapper;
import com.smartcompany.service.IRightService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author lihy
 * @since 2024-09-02
 */
@Service
public class RightServiceImpl extends ServiceImpl<RightMapper, Rights> implements IRightService {

}
