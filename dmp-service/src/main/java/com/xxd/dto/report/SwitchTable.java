package com.xxd.dto.report;

import javax.persistence.*;

/**
 * @author gongzhifei
 */
@Entity
@Table(name = "dmp_switch_table")
public class SwitchTable {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "tablename")
    private String tableName;

    @Column(name = "alias")
    private String alias;

    @Column(name = "isuse")
    private String isUse;

    @Column(name = "date")
    private String date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getIsUse() {
        return isUse;
    }

    public void setIsUse(String isUse) {
        this.isUse = isUse;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
