package com.xxd.dto.user;


import javax.persistence.*;

/**
 * @author gongzhifei
 */
@Entity
@Table(name = "dmp_user_role")
public class UserRole {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "userid")
    private Integer userId;

    @Column(name = "roleid")
    private Integer roleId;

    @Column(name = "type")
    private int type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
