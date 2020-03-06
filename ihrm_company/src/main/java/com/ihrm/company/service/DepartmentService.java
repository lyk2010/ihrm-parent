package com.ihrm.company.service;

import com.ihrm.domain.company.Department;

import java.util.List;

public interface DepartmentService {

    /**
     * 保存部门
     */
    public void add(Department department);

    /**
     * 更新部门
     */
    public void update(Department department);

    /**
     * 删除部门
     */
    public void delete(String id);

    /**
     * 根据id查询部门
     */
    public Department findById(String id);

    /**
     * 查询所有部门
     */
    public List<Department> findAll(String companyId);
}
