package com.xxd.controller.market;

import com.fasterxml.jackson.annotation.JsonView;
import com.xxd.dto.Body;
import com.xxd.dto.ResponseBody;
import com.xxd.dto.market.MarketApp;
import com.xxd.dto.market.MarketChannel;
import com.xxd.dto.market.condition.ChannelNameCondition;
import com.xxd.exception.ReturnMessageException;
import com.xxd.service.market.MarketChannelService;
import com.xxd.service.market.MarketTypeService;
import com.xxd.service.market.MarketWebService;
import io.swagger.annotations.ApiModelProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gongzhifei
 */
@RestController
@RequestMapping("/financial/market/channel")
public class ChannelManageController {

    @Autowired
    private MarketChannelService marketChannelService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MarketTypeService marketTypeService;

    @PostMapping
    public ResponseBody<MarketChannel> query(@RequestBody Body<Boolean,ChannelNameCondition> param){
        try{
            Page<MarketChannel> page = marketChannelService.query(param.getData());
            return new ResponseBody<MarketChannel>(page.getContent(),page.getNumber(),page.getTotalElements(),true,"查询渠道信息成功!");
        }catch (ReturnMessageException ex){

            throw new ReturnMessageException(false,"查询渠道信息失败!");
        }
    }

    /**
     * 新增渠道
     * @param channelName
     * @param username
     * @return
     */
    @GetMapping(value = "/{channelName}")
    public Map<String,Object> create(@PathVariable String channelName, String username){
        Map<String,Object> result = new HashMap<>();
        try{
            marketChannelService.save(channelName,username);
            result.put("success",true);
            result.put("message", "保存渠道信息成功!");
        }catch (Exception e){
            logger.error("保存渠道信息出错!",e);
            result.put("success",false);
            result.put("message", "保存渠道信息失败!");
        }
        return result;
    }

    /**
     * web、app管理页面渠道
     * @return
     */
    @ApiModelProperty("管理界面渠道下拉框数据")
    @GetMapping(value = "/queryName")
    @JsonView(MarketChannel.ChannelSimpleView.class)
    public Map<String,Object> queryName(){
        Map<String, Object> result = new HashMap<>();
        try{
            List<MarketChannel> channel = marketTypeService.query();
            result.put("success",true);
            result.put("message","查询渠道成功!");
            result.put("data",channel);
        }catch (Exception e){
            result.put("success",false);
            result.put("message","查询渠道失败!");
            logger.error("查询渠道出错!",e);
        }
        return result;
    }

}
