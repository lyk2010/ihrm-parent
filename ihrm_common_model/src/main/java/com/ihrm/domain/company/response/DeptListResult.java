package com.ihrm.domain.company.response;

import com.ihrm.domain.company.Company;
import com.ihrm.domain.company.Department;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DeptListResult {

    private String companyId;

    private String companyName;

    private String companyManager;

    private List<Department> depts;

    public DeptListResult(Company company,List<Department> depts){
        this.companyId = company.getId();
        this.companyName = company.getName();
        this.companyManager = company.getLegalRepresentative();
        this.depts = depts;

    }

}
