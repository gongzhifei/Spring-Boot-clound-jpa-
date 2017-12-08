package com.xxd.dto.market;

import javax.persistence.*;
import java.sql.Date;

/**
 * @author gongzhifei
 */
@Entity
@Table(name = "market_date_base")
public class MarketDateBase {
    private String date;
    private String week;
    private String month;

    @Id
    @Column(name = "date")
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Basic
    @Column(name = "week")
    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    @Basic
    @Column(name = "month")
    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }


}
