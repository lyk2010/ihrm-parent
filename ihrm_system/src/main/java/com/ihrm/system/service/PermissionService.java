package com.ihrm.system.service;

import com.ihrm.domain.system.Permission;

import java.util.List;
import java.util.Map;

public interface PermissionService {

    public void save(Map<String,Object> map) throws Exception;

    public void update(Map<String,Object> map) throws Exception;

    public void delete(String id) throws Exception;

    public Map findById(String id) throws Exception;

    public List<Permission> findAll(Map<String, Object> map);
}
