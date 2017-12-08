package com.xxd.dto.market.condition;

import io.swagger.annotations.ApiModelProperty;


/**
 * @author gongzhifei
 */
public class EffectTrackCondition extends PageSize {

    @ApiModelProperty("类型ID")
    private Integer typeId;

    @ApiModelProperty("渠道ID")
    private Integer channelId;

    @ApiModelProperty("source")
    private String source;

    @ApiModelProperty("分组间隔 1：天 2：周 3：月")
    private int flag;

    @ApiModelProperty("类型：1:入金 2：年化 3：投资")
    private int type;

    @ApiModelProperty("开始时间")
    private String startDate;

    @ApiModelProperty("结束时间")
    private String endDate;

    @ApiModelProperty("单独时间")
    private String date;

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

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
