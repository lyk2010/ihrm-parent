package com.ihrm.system.service;

import com.ihrm.domain.system.User;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface UserService {

    /**
     * 保存用户
     */
    public void add(User user);

    /**
     * 更新用户
     */
    public void update(User user);

    /**
     * 删除用户
     */
    public void delete(String id);

    /**
     * 根据id查询用户
     */
    public User findById(String id);

    /**
     * 查询所有用户
     */
    public Page<User> findAll(Map<String,Object> map,int page,int size);

    /**
     * 分配角色
     * @param userId
     * @param roleIds
     */
    public void assignRoles(String userId, List<String> roleIds);

    /**
     * 根据mobile查询用户
     * @param mobile
     * @return
     */
    public User findByMobile(String mobile);


    public User findByName(String username);
}
