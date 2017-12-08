package com.xxd.controller.market;

import com.xxd.dto.Body;
import com.xxd.dto.ResponseBody;
import com.xxd.dto.market.MarketChannel;
import com.xxd.dto.market.condition.ChannelNameCondition;
import com.xxd.dto.user.UserInfo;
import com.xxd.properties.Constants;
import io.swagger.annotations.ApiModelProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author gongzhifei
 */
@RestController
@RequestMapping("/financial/market/channel")
public class ChannelManageController {

    @Autowired
    private RestTemplate restTemplate;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 渠道管理
     * @return
     */
    @GetMapping
    public ResponseBody<MarketChannel> query(ChannelNameCondition channelNameCondition){
        try{
            Body<Boolean,ChannelNameCondition> body = new Body<>();
            body.setData(channelNameCondition);
            ResponseBody<MarketChannel> result = restTemplate.postForEntity(Constants.CHANNEL_MANAGE_QUERY,body,ResponseBody.class).getBody();
            return result;
        }catch (Exception ex){
            logger.error("查询渠道管理信息失败!",ex);
        }
        return null;
    }

    /**
     * 新增渠道
     * @param channelName
     * @return
     */
    @GetMapping(value = "/{channelName}")
    public Map<String,Object> create(@PathVariable String channelName){
        try{
            UserInfo user = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = user.getUsername();
            Map<String,Object> result = restTemplate.getForEntity(Constants.CHANNEL_MANAGE_CREATE,Map.class,new Object []{channelName,username}).getBody();
            return result;
        }catch (Exception e){
            logger.error("发送DMP服务新增渠道失败!",e);
        }
        return null;
    }

    /**
     * web、app管理页面渠道
     * @return
     */
    @ApiModelProperty("管理界面渠道下拉框数据")
    @GetMapping(value = "/queryName")
    public Map<String,Object> getManagerChannel(){
        try{
            Map<String,Object> result = restTemplate.getForEntity(Constants.MARKET_MANAGE_CHANNEL_ID_QUERY,Map.class).getBody();
            return result;
        }catch (Exception e){
            logger.error("发送DMP服务查询渠道出错!",e);
        }
        return null;
    }

}
