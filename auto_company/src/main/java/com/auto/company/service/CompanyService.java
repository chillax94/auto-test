package com.auto.company.service;

import com.auto.company.dao.CompanyDao;
import com.auto.domain.company.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    @Autowired
    private CompanyDao companyDao;

    /**
     * 根据id查询企业
     */
    public Company findById(String id) {
        return companyDao.findById(id).get();
    }

    /**
     * 查询企业列表
     */
    public List<Company> findAll() {
        return companyDao.findAll();
    }
}
