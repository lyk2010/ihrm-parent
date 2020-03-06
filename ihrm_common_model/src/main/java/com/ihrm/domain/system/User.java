package com.ihrm.domain.system;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.crazycake.shiro.AuthCachePrincipal;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户实体类
 *
 * AuthCachePrincipal:redis和shiro插件提供的接口
 *   作用：可以把数据存入redis.
 *
 */
@ApiModel(value = "User",description = "实体类用户")
@Entity
@Getter
@Setter
@Table(name = "bs_user")
public class User implements Serializable,AuthCachePrincipal {

    @ApiModelProperty(name = "id",value = "用户唯一id",notes = "用于唯一索引")
    @Id
    private String id;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 用户名称
     */
    @ApiModelProperty(name = "username",value = "用户名称",example = "张三")
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 启用状态 0为禁用 1为启用
     */
    private Integer enableState;

    /**
     * 创建时间
     */
    private Date createTime;

    private String companyId;

    private String companyName;

    /**
     * 部门ID
     */
    private String departmentId;

    /**
     * 入职时间
     */
    private Date timeOfEntry;

    /**
     * 聘用形式
     */
    private Integer formOfEmployment;

    /**
     * 工号
     */
    private String workNumber;

    /**
     * 管理形式
     */
    private String formOfManagement;

    /**
     * 工作城市
     */
    private String workingCity;

    /**
     * 转正时间
     */
    private Date correctionTime;

    /**
     * 在职状态 1.在职  2.离职
     */
    private Integer inServiceStatus;

    private String departmentName;

    /**
     * level
     *      string
     *             saasAdmin：SaaS管理员俱备所有权限
     *             coAdmin:企业管理员（创建租户企业的时候）
     *             user：普通用户（需要分配角色）
     *
     */
    private String level;


    /**
     * @JsonIgnore:该注解作用：忽略json转化
     */
    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "pe_user_role",joinColumns = {@JoinColumn(name = "user_id",referencedColumnName = "id")},
                inverseJoinColumns = {@JoinColumn(name = "role_id",referencedColumnName = "id")})
    private Set<Role> roles = new HashSet<>();//用户与角色  多对多

    @Override
    public String getAuthCacheKey() {
        return null;
    }
}
