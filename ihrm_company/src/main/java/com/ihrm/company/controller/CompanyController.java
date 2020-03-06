package com.ihrm.company.controller;

import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.company.service.CompanyService;
import com.ihrm.domain.company.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/company")
@CrossOrigin
public class CompanyController {

    @Autowired
    private CompanyService companyService;


    /**
     * 保存企业
     * @param company
     * @return
     */
    @PostMapping(value = "")
    public Result save(@RequestBody Company company){
        companyService.add(company);
        return new Result(ResultCode.SUCCESS);

    }

    /**
     * 根据id更新企业
     * @param id
     * @param company
     * @return
     */
    @PutMapping(value = "/{id}")
    public Result update(@PathVariable(value = "id") String id,
                         @RequestBody Company company){
        company.setId(id);
        companyService.update(company);
        return new Result(ResultCode.SUCCESS);

    }


    /**
     * 根据id删除企业
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public Result delete(@PathVariable(value = "id") String id){
        companyService.delete(id);
        return new Result(ResultCode.SUCCESS);

    }


    /**
     * 根据id查询企业
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    public Result findById(@PathVariable String id){
        Company company = companyService.findById(id);
        Result result = new Result(ResultCode.SUCCESS);
        result.setData(company);
        return result;
    }


    /**
     * 查询所有企业
     * @return
     */
    @GetMapping(value = "")
    public Result findAll(){
        List<Company> companyList = companyService.findAll();
        Result result = new Result(ResultCode.SUCCESS);
        result.setData(companyList);
        return result;
    }


}
