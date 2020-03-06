package com.ihrm.system.service;

import com.ihrm.domain.system.Role;

import java.util.List;

public interface RoleService {

    public Role add(Role role) throws Exception;

    public List<Role> findAll();

    public Role findById(String roleId);

    public void update(Role role) throws Exception;

    public void deleteById(String roleId) throws Exception;

    public void assignPerms(String roleId, List<String> permIds);
}
