package com.xxd.dto.market.condition;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author gongzhifei
 */
public class ReachOrInvestCondition {

    @ApiModelProperty("时间间隔：1：日、2：周、3：月")
    private String type;

    @ApiModelProperty("时间")
    private String date;

    @ApiModelProperty("选择标志：1：激活/抵达人数、2：投资人数")
    private String flag;

    @ApiModelProperty("开始时间")
    private String startDate;

    @ApiModelProperty("结束时间")
    private String endDate;

    @ApiModelProperty("类型ID")
    private String typeId;

    @ApiModelProperty("渠道ID")
    private String channelId;

    @ApiModelProperty("source来源")
    private String source;

    private Integer userId;

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
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
