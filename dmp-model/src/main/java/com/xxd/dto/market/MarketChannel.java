package com.xxd.dto.market;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;

/**
 * @author gongzhifei
 */
@Entity
@Table(name = "dmp_market_channel")
public class MarketChannel {

    public interface ChannelSimpleView{};

    @JsonView(ChannelSimpleView.class)
    private Integer id;
    @JsonView(ChannelSimpleView.class)
    private String channelName;
    private String createTime;
    private String updateTime;
    private String founder;
    private String modifier;

    @Id
    @GeneratedValue
    @Column(name = "channel_id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "channel_name")
    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    @Basic
    @Column(name = "create_time")
    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "update_time")
    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Basic
    @Column(name = "founder")
    public String getFounder() {
        return founder;
    }

    public void setFounder(String founder) {
        this.founder = founder;
    }

    @Basic
    @Column(name = "modifier")
    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }
}
