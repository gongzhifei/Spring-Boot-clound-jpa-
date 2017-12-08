package com.xxd.dto.market.condition;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author gongzhifei
 */
public class DayDetailsCondition extends PageSize {

    @ApiModelProperty("类型ID")
    private Integer typeId;

    @ApiModelProperty("渠道ID")
    private Integer channelId;

    @ApiModelProperty("source")
    private String source;

    @ApiModelProperty("utmSource生成六位唯一码")
    private String utmSource;

    @ApiModelProperty("广告媒介")
    private String utmMedium;

    @ApiModelProperty("广告媒介")
    private String utmTerm;

    @ApiModelProperty("广告内容")
    private String utmContent;

    @ApiModelProperty("广告名称")
    private String utmCampaign;

    @ApiModelProperty("起始时间")
    private String startDate;

    @ApiModelProperty("终止")
    private String endDate;

    @ApiModelProperty("用户ID")
    private Integer userId;

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
