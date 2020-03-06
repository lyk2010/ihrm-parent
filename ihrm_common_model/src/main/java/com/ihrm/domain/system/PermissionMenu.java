package com.ihrm.domain.system;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 菜单资源
 */
@Entity
@Getter
@Setter
@Table(name = "pe_permission_menu")
public class PermissionMenu implements Serializable {

    /**
     * 主键id
     */
    @Id
    private String id;

    private String menuIcon;//展示图标

    private String menuOrder;//排序号
}
