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
@Table(name = "co_company")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Company implements Serializable {

    @Id
    private String id;

    //@Column(name = "name")如果属性和数据库的列名满足驼峰命名可不写
    private String name;

    private String managerId;

    private String version;

    private Date renewalDate;

    private Date expirationDate;

    private String companyArea;

    private String companyAddress;

    private String businessLicenseId;

    private String legalRepresentative;

    private String companyPhone;

    private String mailbox;

    private String companySize;

    private String industry;

    private String remarks;

    private String auditState;

    private Integer state;

    private Double balance;

    private Date createTime;




}
