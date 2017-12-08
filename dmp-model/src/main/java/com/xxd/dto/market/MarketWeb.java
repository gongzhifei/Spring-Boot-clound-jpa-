package com.xxd.dto.market;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author gongzhifei
 */
@Entity
@Table(name = "dmp_market_web", schema = "authority")
public class MarketWeb {

    private int id;
    @NotNull(message = "类型不能为空必须为数字")
    private Integer typeId;

    @NotNull(message = "渠道不能为空必须为数字")
    private Integer channelId;

    @NotBlank(message = "source不能为空")
    private String source;

    private String utmMedium;

    private String utmTerm;

    private String utmContent;

    private String utmCampaign;

    private String targetUrl;

    private String utmSource;

    private String isUse;

    private String createTime;

    private String updateTime;

    private String advertisingUrl;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "type_id")
    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    @Basic
    @Column(name = "channel_id")
    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    @Basic
    @Column(name = "source")
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Basic
    @Column(name = "utm_medium")
    public String getUtmMedium() {
        return utmMedium;
    }

    public void setUtmMedium(String utmMedium) {
        this.utmMedium = utmMedium;
    }

    @Basic
    @Column(name = "utm_term")
    public String getUtmTerm() {
        return utmTerm;
    }

    public void setUtmTerm(String utmTerm) {
        this.utmTerm = utmTerm;
    }

    @Basic
    @Column(name = "utm_content")
    public String getUtmContent() {
        return utmContent;
    }

    public void setUtmContent(String utmContent) {
        this.utmContent = utmContent;
    }

    @Basic
    @Column(name = "utm_campaign")
    public String getUtmCampaign() {
        return utmCampaign;
    }

    public void setUtmCampaign(String utmCampaign) {
        this.utmCampaign = utmCampaign;
    }

    @Basic
    @Column(name = "target_url")
    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    @Basic
    @Column(name = "utm_source")
    public String getUtmSource() {
        return utmSource;
    }

    public void setUtmSource(String utmSource) {
        this.utmSource = utmSource;
    }

    @Basic
    @Column(name = "is_use")
    public String getIsUse() {
        return isUse;
    }

    public void setIsUse(String isUse) {
        this.isUse = isUse;
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
    @Column(name = "advertising_url")
    public String getAdvertisingUrl() {
        return advertisingUrl;
    }

    public void setAdvertisingUrl(String advertisingUrl) {
        this.advertisingUrl = advertisingUrl;
    }

}
