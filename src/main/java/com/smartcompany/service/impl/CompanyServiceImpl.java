package com.smartcompany.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smartcompany.entity.Company;
import com.smartcompany.service.ICompanyService;
import com.smartcompany.mapper.CompanyMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 公司信息表 服务实现类
 * </p>
 *
 * @author lihy
 * @since 2024-09-02
 */
@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements ICompanyService {

}
