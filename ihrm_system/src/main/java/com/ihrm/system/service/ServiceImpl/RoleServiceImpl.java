package com.ihrm.system.service.ServiceImpl;

import com.ihrm.common.utils.IdWorker;
import com.ihrm.common.utils.PermissionConstants;
import com.ihrm.domain.system.Permission;
import com.ihrm.domain.system.Role;
import com.ihrm.system.dao.PermissionDao;
import com.ihrm.system.dao.RoleDao;
import com.ihrm.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private IdWorker idWorker;


    @Autowired
    private PermissionDao permissionDao;

    @Override
    public Role add(Role role) {
        String roleId = idWorker.nextId() + "";
        role.setId(roleId);
        return roleDao.save(role);
    }

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    public Role findById(String roleId) {
        return roleDao.findById(roleId).get();
    }

    @Override
    public void update(Role role) {
        Role role1 = roleDao.findById(role.getId()).get();
        role1.setDescription(role.getDescription());
        role1.setName(role.getName());
        roleDao.save(role1);
    }

    @Override
    public void deleteById(String roleId) {
        roleDao.deleteById(roleId);
    }

    @Override
    public void assignPerms(String roleId, List<String> permIds) {
        //1.获取分配的角色对象
        Role role = roleDao.findById(roleId).get();
        //2.构造角色的权限集合
        Set<Permission> perms = new HashSet<>();
        for (String permId : permIds) {
            Permission permission = permissionDao.findById(permId).get();
            //需要根据父id和类型查询API权限列表
            List<Permission> apiList = permissionDao.findByTypeAndPid(PermissionConstants.PY_API, permission.getId());
            perms.addAll(apiList);//自动赋予api权限
            perms.add(permission);//当前菜单或按钮的权限
        }
        //3.设置角色和权限的关系
        role.setPermissions(perms);
        //4.更新角色
        roleDao.save(role);
    }
}
