package com.ihrm.system.service.ServiceImpl;

import com.ihrm.common.utils.IdWorker;
import com.ihrm.domain.system.Role;
import com.ihrm.domain.system.User;
import com.ihrm.system.dao.RoleDao;
import com.ihrm.system.dao.UserDao;
import com.ihrm.system.service.UserService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private IdWorker idWorker;


    @Autowired
    private RoleDao roleDao;

    /**
     * 添加用户
     *
     * @param user
     */
    @Override
    public void add(User user) {
        //设置主键的值
        String id = idWorker.nextId() + "";
        String password = new Md5Hash("123456", user.getMobile(), 3).toString();
        user.setLevel("user");
        user.setPassword(password);//设置初始密码
        user.setEnableState(1);
        user.setId(id);
        userDao.save(user);

    }


    /**
     * 更新用户
     *
     * @param user
     */
    @Override
    public void update(User user) {
        //1.根据id查询当前用户
        User dept = userDao.findById(user.getId()).get();
        dept.setUsername(user.getUsername());
        dept.setPassword(user.getPassword());
        dept.setDepartmentId(user.getDepartmentId());
        dept.setDepartmentName(user.getDepartmentName());
        userDao.save(dept);


    }

    /**
     * 根据id删除用户
     *
     * @param id
     */
    @Override
    public void delete(String id) {
        userDao.deleteById(id);

    }


    /**
     * 根据id查询用户
     *
     * @param id
     * @return
     */
    @Override
    public User findById(String id) {
        return userDao.findById(id).get();
    }


    /**
     * 查询所有用户
     * 参数：map集合的形式
     * hasDept
     * departmentId
     * companyId
     *
     * @param map
     * @return
     */
    @Override
    public Page<User> findAll(Map<String, Object> map, int page, int size) {
        //1.需要查询条件
        Specification<User> spec = new Specification<User>() {
            @Override
            /**
             * 动态拼接查询条件
             */
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                //根据请求的companyId是否为空构造查询条件
                if (!StringUtils.isEmpty(map.get("companyId"))) {
                    list.add(cb.equal(root.get("companyId").as(String.class), (String) map.get("companyId")));
                }
                //根据请求的departmentId是否为空构造查询条件
                if (!StringUtils.isEmpty(map.get("departmentId"))) {
                    list.add(cb.equal(root.get("departmentId").as(String.class), (String) map.get("departmentId")));
                }
                if (!StringUtils.isEmpty(map.get("hasDept"))) {
                    //根据请求的hasDept判断 是否分配部门 0未分配(departmentId == null)   1已分配(departmentId != null)
                    if ("0".equals((String) map.get("hasDept"))) {
                        list.add(cb.isNull(root.get("departmentId")));
                    } else {
                        list.add(cb.isNotNull(root.get("departmentId")));
                    }
                }
                return cb.and(list.toArray(new Predicate[list.size()]));
            }
        };

        //2.分页
        Page<User> userPage = userDao.findAll(spec, PageRequest.of(page - 1, size));
        return userPage;
    }


    /**
     * 分配角色
     */
    @Override
    public void assignRoles(String userId, List<String> roleIds) {
        //1.根据id查询用户
        User user = userDao.findById(userId).get();
        //2.设置用户的角色集合
        Set<Role> roles = new HashSet<>();
        for (String roleId : roleIds) {
            Role role = roleDao.findById(roleId).get();
            roles.add(role);
        }
        //设置用户和角色集合关系
        user.setRoles(roles);
        //3.更新用户
        userDao.save(user);
    }


    /**
     * 通过mobile查询用户
     *
     * @param mobile
     * @return
     */
    @Override
    public User findByMobile(String mobile) {
        return userDao.findByMobile(mobile);
    }


    @Override
    public User findByName(String username) {
        return userDao.findByUsername(username);
    }
}
