package com.xxd.dto.market.condition;

import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;

/**
 * @author gongzhifei
 */
public class MarketAppCondition extends PageSize {

    @ApiModelProperty(value = "类型ID")
    private Integer typeId;

    @ApiModelProperty(value = "渠道ID")
    private Integer channelId;

    @ApiModelProperty(value = "来源")
    private String source;

    @ApiModelProperty(value = "包名")
    private String packageName;

    @ApiModelProperty(value = "utmSource生成六位唯一码")
    private String utmSource;

    @ApiModelProperty(value = "开始时间")
    private String startDate;

    @ApiModelProperty(value = "终止时间")
    private String endtDate;

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

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getUtmSource() {
        return utmSource;
    }

    public void setUtmSource(String utmSource) {
        this.utmSource = utmSource;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndtDate() {
        return endtDate;
    }

    public void setEndtDate(String endtDate) {
        this.endtDate = endtDate;
    }
}
