package com.xxd.dto.market;


import javax.persistence.*;

/**
 * @author gongzhifei
 */
@Entity
@Table(name = "dmp_user_type")
public class UserType {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "userid")
    private Integer userId;

    @Column(name = "typeid")
    private Integer typeId;

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

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }
}
