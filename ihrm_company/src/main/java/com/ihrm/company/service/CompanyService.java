package com.ihrm.company.service;

import com.ihrm.domain.company.Company;

import java.util.List;

public interface CompanyService {

    /**
     * 保存企业
     */
    public void add(Company company);

    /**
     * 更新企业
     */
    public void update(Company company);

    /**
     * 删除企业
     */
    public void delete(String id);

    /**
     * 根据id查询企业
     */
    public Company findById(String id);

    /**
     * 查询所有企业列表
     */
    public List<Company> findAll();
}
