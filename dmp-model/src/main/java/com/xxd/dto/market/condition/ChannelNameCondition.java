package com.xxd.dto.market.condition;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author gongzhifei
 */
public class ChannelNameCondition extends PageSize {

    @ApiModelProperty("渠道名称：支持模糊查询")
    private String channelName;


    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
}
