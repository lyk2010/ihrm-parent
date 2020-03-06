package com.ihrm.domain.system;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@Entity
@NoArgsConstructor
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name = "pe_permission")
public class Permission implements Serializable {


    @Id
    private String id;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限类型 1为菜单 2为功能 3为API
     */
    private Integer type;


    /**
     * 权限标志
     */
    private String code;

    /**
     * 权限描述
     */
    private String description;

    /**
     * 父id
     */
    private String pid;

    /**
     * 可见状态
     */
    private Integer enVisible;

    public Permission(String name,Integer type,String code,String description) {
        this.name = name;
        this.type = type;
        this.code = code;
        this.description = description;
    }
}
