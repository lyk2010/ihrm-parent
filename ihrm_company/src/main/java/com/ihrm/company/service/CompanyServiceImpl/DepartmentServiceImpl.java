package com.ihrm.company.service.CompanyServiceImpl;

import com.ihrm.common.service.BaseService;
import com.ihrm.common.utils.IdWorker;
import com.ihrm.company.dao.DepartmentDao;
import com.ihrm.company.service.DepartmentService;
import com.ihrm.domain.company.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl extends BaseService implements DepartmentService {

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private IdWorker idWorker;

    @Override
    public void add(Department department) {
        //设置主键id的值
        String id = idWorker.nextId() + "";
        department.setId(id);
        departmentDao.save(department);

    }

    @Override
    public void update(Department department) {
        Department dept = departmentDao.findById(department.getId()).get();
        dept.setName(department.getName());
        dept.setCode(department.getCode());
        dept.setIntroduce(department.getIntroduce());
        departmentDao.save(dept);
    }

    @Override
    public void delete(String id) {
        departmentDao.deleteById(id);
    }

    @Override
    public Department findById(String id) {
        return departmentDao.findById(id).get();
    }

    @Override
    public List<Department> findAll(String companyId) {
        /**
         *构造查询条件
         *  说明：1.该方法只查询companyId
         *       2.很多地方都需要根据companyId查询
         *       3.很多的对象中都具有companyId
         *   参数说明：
         *      root:包含了所有的对象数据
         *      cq:一般不用
         *      cb:构造查询条件
         *
         */
//        Specification<Department> spec = new Specification<Department>() {
//            @Override
//            public Predicate toPredicate(Root<Department> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
//                //根据企业id查询
//                return cb.equal(root.get("companyId").as(String.class), companyId);
//            }
//        };

        return departmentDao.findAll(getSpec(companyId));
    }
}
