package com.xxd.controller.market;

import com.xxd.dto.Body;
import com.xxd.dto.ResponseBody;
import com.xxd.dto.market.MarketWeb;
import com.xxd.dto.market.condition.MarketWebCondition;
import com.xxd.properties.Constants;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * 推广链接管理
 * @author gongzhifei
 */
@RestController
@RequestMapping(value = "/financial/market/links")
public class MarketWebController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 分页查询推广链接
     * @param marketWebCondition
     * @return
     */
    @ApiOperation("分页查询推广链接")
    @GetMapping
    public ResponseBody<MarketWeb> query(MarketWebCondition marketWebCondition){
        try{
            Body<Boolean,MarketWebCondition> inBody = new Body<>();
            inBody.setData(marketWebCondition);
            ResponseBody<MarketWeb> result = restTemplate.postForEntity(Constants.MARKET_LINKS_QUERY, inBody,ResponseBody.class).getBody();
            return result;
        }catch (Exception ex){
            logger.error("发送DMP服务查询推广链接失败!", ex);
        }
        return null;
    }

    /**
     * 添加推广链接
     * @param marketWeb
     * @return
     */
    @ApiOperation("新增推广链接")
    @PostMapping
    public Map<String,Object> create(MarketWeb marketWeb){
        try{
            Body<Boolean,MarketWeb> inBody = new Body<>();
            inBody.setData(marketWeb);
            Map<String,Object> result = restTemplate.postForEntity(Constants.MARKET_LINKS_CREATE,inBody,Map.class).getBody();
            return result;
        } catch (Exception ex){
            logger.error("发送DMP服务添加推广链接失败!",ex);
        }
        return null;
    }

    @ApiOperation("修改推广链接")
    @PutMapping
    public Map<String,Object> update(MarketWeb marketWeb){
        try{
            Body<Boolean,MarketWeb> inBody = new Body<>();
            inBody.setData(marketWeb);
            Map<String,Object> result =restTemplate.postForEntity(Constants.MARKET_LINKS_UPDATE,inBody,Map.class).getBody();
            return result;
        } catch (Exception ex){
            logger.error("发送DMP服务修改推广链接失败!",ex);
        }
        return null;
    }



}
