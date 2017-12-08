package com.xxd.controller.market;

import com.xxd.service.market.MarketTypeService;
import com.xxd.service.market.MarketWebService;
import io.swagger.annotations.ApiModelProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gongzhifei
 */
@RestController
@RequestMapping(value = "/financial/market/type")
public class MarketTypeController {

    @Autowired
    private MarketTypeService marketTypeService;

    @Autowired
    private MarketWebService marketWebService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 获取typeID
     * @param flag
     * @param userId
     * @return
     */
    @ApiModelProperty("获取typeId flag:1 web、2 app")
    @GetMapping("/{flag}")
    public Map<String, Object> query(@PathVariable String flag, Integer userId) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Map<String, Object>> marketTypeChannelList = marketTypeService.getTypes(userId, flag);
            result.put("success", true);
            result.put("message", "查询用户类型和渠道成功!");
            result.put("data", marketTypeChannelList);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "查询用户类型和渠道失败!");
            logger.error("查询用户类型出错!", e);
        }
        return result;
    }

    /**
     * 根据typeId获取用户渠道
     * @return
     */
    @ApiModelProperty("报表渠道下拉框获取")
    @GetMapping(value = "/channel/{typeId}")
    public Map<String,Object> getChannel(@PathVariable String typeId, Integer userId){
        Map<String, Object> result = new HashMap<>();
        try{
            List<Map<String,Object>> marketTypeChannelList =  marketTypeService.getChannels(typeId,userId);
            result.put("success",true);
            result.put("message","查询用户渠道成功!");
            result.put("data",marketTypeChannelList);
        }catch (Exception e){
            result.put("success",false);
            result.put("message","查询用渠道失败!");
            logger.error("查询渠道出错!",e);
        }
        return result;
    }


    /**
     * 获取来源
     * @param typeId
     * @param channelId
     * @return
     */
    @GetMapping(value = "/Source/{typeId}/{channelId}")
    public Map<String, Object>  getSource(@PathVariable Integer typeId,@PathVariable Integer channelId,Integer userId){
        Map<String, Object> result = new HashMap<>();
        try{
            List<String> sourceList = marketWebService.findByTypeAndChannel(typeId, channelId,userId);
            result.put("success",true);
            result.put("message","查询来源成功!");
            result.put("data",sourceList);
            return result;
        }catch (Exception e){
            result.put("success",false);
            result.put("message","查询来源失败!");
            logger.error("查询来源失败!",e);
        }
        return result;
    }

}
