package com.xxd.dto.user;

import javax.persistence.*;

/**
 * @author gongzhifei
 */
@Entity
@Table(name = "dmp_role_menu")
public class RoleMenu {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "roleid")
    private Integer roleId;

    @Column(name = "menuid")
    private Integer menuId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }
}
