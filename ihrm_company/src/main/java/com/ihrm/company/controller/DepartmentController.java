package com.ihrm.company.controller;

import com.ihrm.common.controller.BaseController;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.company.service.CompanyService;
import com.ihrm.company.service.DepartmentService;
import com.ihrm.domain.company.Company;
import com.ihrm.domain.company.Department;
import com.ihrm.domain.company.response.DeptListResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/company")
@CrossOrigin
public class DepartmentController extends BaseController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private CompanyService companyService;


    /**departmentService
     * 保存部门
     * @param department
     * @return
     */
    @PostMapping(value = "/department")
    public Result save(@RequestBody Department department){
        //1.设置保存的企业id（目前使用固定值1）
        department.setCompanyId(companyId);
        //2.调用service完成保存
        departmentService.add(department);
        //3.构造返回结果
        return new Result(ResultCode.SUCCESS);

    }

    /**
     * 根据id更新部门
     * @param id
     * @param department
     * @return
     */
    @PutMapping(value = "/department/{id}")
    public Result update(@PathVariable(value = "id") String id,
                         @RequestBody Department department){
        department.setId(id);
        departmentService.update(department);
        return new Result(ResultCode.SUCCESS);

    }


    /**
     * 根据id删除企业
     * @param id
     * @return
     */
    @DeleteMapping(value = "/department/{id}")
    public Result delete(@PathVariable(value = "id") String id){
        departmentService.delete(id);
        return new Result(ResultCode.SUCCESS);

    }


    /**
     * 根据id查询企业
     * @param id
     * @return
     */
    @GetMapping(value = "/department/{id}")
    public Result findById(@PathVariable(value = "id") String id){
        Department department = departmentService.findById(id);
        return new Result(ResultCode.SUCCESS,department);

    }


    /**
     *
     * @return
     */
    @GetMapping(value = "/department")
    public Result findAll(){
        //1.指定企业id
        Company company = companyService.findById(companyId);
        //2.完成查询
        List<Department> list = departmentService.findAll(companyId);
        //3.构造返回结果
        DeptListResult departmentList = new DeptListResult(company,list);
        return new Result(ResultCode.SUCCESS,departmentList);
    }


}
