package com.ihrm.domain.system;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "pe_role")
public class Role implements Serializable {

    @Id
    private String id;

    /**
     * 企业名称
     */
    private String name;

    /**
     * 说明
     */
    private String description;

    /**
     * 企业id
     */
    private String companyId;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")//不维护中间表
    private Set<User> users = new HashSet<>(0);//角色与用户 多对多


    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "pe_role_permission",
                joinColumns = {@JoinColumn(name = "role_id",referencedColumnName = "id")},
                inverseJoinColumns = {@JoinColumn(name = "permission_id",referencedColumnName = "id")})
    private Set<Permission> permissions = new HashSet<>(0);//角色与模块 多对多

}
