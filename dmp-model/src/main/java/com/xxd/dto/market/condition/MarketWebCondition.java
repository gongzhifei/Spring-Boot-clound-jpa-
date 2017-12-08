package com.xxd.dto.market.condition;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author gongzhifei
 */
public class MarketWebCondition extends PageSize {

    @ApiModelProperty(value = "类型ID")
    private Integer typeId;

    @ApiModelProperty(value = "渠道ID")
    private Integer channelId;

    @ApiModelProperty(value = "广告来源")
    private String source;

    @ApiModelProperty(value = "utmSource生成六位唯一码")
    private String utmSource;

    @ApiModelProperty(value = "广告媒介")
    private String utmMedium;

    @ApiModelProperty(value = "广告关键词")
    private String utmTerm;

    @ApiModelProperty(value = "广告内容")
    private String utmContent;

    @ApiModelProperty(value = "广告名称")
    private String utmCampaign;

    @ApiModelProperty(value = "开始时间")
    private String startDate;

    @ApiModelProperty(value = "终止时间")
    private String endDate;


    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUtmSource() {
        return utmSource;
    }

    public void setUtmSource(String utmSource) {
        this.utmSource = utmSource;
    }

    public String getUtmMedium() {
        return utmMedium;
    }

    public void setUtmMedium(String utmMedium) {
        this.utmMedium = utmMedium;
    }

    public String getUtmTerm() {
        return utmTerm;
    }

    public void setUtmTerm(String utmTerm) {
        this.utmTerm = utmTerm;
    }

    public String getUtmContent() {
        return utmContent;
    }

    public void setUtmContent(String utmContent) {
        this.utmContent = utmContent;
    }

    public String getUtmCampaign() {
        return utmCampaign;
    }

    public void setUtmCampaign(String utmCampaign) {
        this.utmCampaign = utmCampaign;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
