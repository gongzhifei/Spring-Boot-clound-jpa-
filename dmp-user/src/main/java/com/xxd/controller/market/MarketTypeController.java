package com.xxd.controller.market;

import com.xxd.dto.user.UserInfo;
import com.xxd.properties.Constants;
import io.swagger.annotations.ApiModelProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gongzhifei
 */
@RestController
@RequestMapping(value = "/financial/market/type")
public class MarketTypeController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RestTemplate restTemplate;

    /**
     * 获取下拉框typeId
     * @param flag
     * @return
     */
    @ApiModelProperty("获取下拉框TYPEID")
    @GetMapping("/{flag}")
    public Map<String,Object> query(@PathVariable String flag){
        try{
            UserInfo user = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Map<String,Object> result = restTemplate.getForEntity(Constants.MARKET_TYPE_ID_QUERY,Map.class,new Object [] {flag,user.getId()}).getBody();
            return result;
        }catch (Exception e){
            logger.error("发送DMP服务获取TYPEID失败!",e);
        }
        return null;
    }

    /**
     * 根据typeId获取用户渠道
     * @return
     */
    @ApiModelProperty("报表渠道下拉框获取")
    @GetMapping(value = "/channel/{typeId}")
    public Map<String,Object> getChannel(@PathVariable String typeId){
        try{
            UserInfo user = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Map<String,Object> result = restTemplate.getForEntity(Constants.MARKET_CHANNEL_ID_QUERY,Map.class,new Object[]{typeId,user.getId()}).getBody();
            return result;
        }catch (Exception e){
            logger.error("发送DMP服务根据typeId获取渠道信息失败!",e);
        }
        return null;
    }

    /**
     * 获取来源
     * @param typeId
     * @param channelId
     * @return
     */
    @ApiModelProperty("获取source来源")
    @GetMapping(value = "/source/{typeId}/{channelId}")
    public Map<String, Object>  getSource(@PathVariable String typeId,@PathVariable String channelId){
        try{
            UserInfo user = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Map<String,Object> result = restTemplate.getForEntity(Constants.MARKET_SOURCE_QUERY,Map.class,new Object [] {typeId,channelId,user.getId()}).getBody();
            return result;
        }catch (Exception e){
            logger.error("发送DMP服务查询source来源失败!",e);
        }
        return null;
    }

}
