package com.smartcompany.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smartcompany.entity.EP;
import com.smartcompany.mapper.EPMapper;
import com.smartcompany.service.IEPService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 员工职务关系表 服务实现类
 * </p>
 *
 * @author lihy
 * @since 2024-09-02
 */
@Service
public class EPServiceImpl extends ServiceImpl<EPMapper, EP> implements IEPService {

}
