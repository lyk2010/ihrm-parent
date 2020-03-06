package com.ihrm.domain.company;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "co_department")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department implements Serializable {

    @Id
    private String id;

    /**
     * 父级id
     */
    private String parentId;

    private String companyId;

    private String name;

    private String code;

    private String category;

    private String managerId;

    private String city;

    private String introduce;

    private Date createTime;

    private String manager;


}
