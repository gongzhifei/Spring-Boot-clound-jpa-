package com.xxd.controller.market;

import com.xxd.dto.Body;
import com.xxd.dto.ResponseBody;
import com.xxd.dto.market.MarketApp;
import com.xxd.dto.market.condition.MarketAppCondition;
import com.xxd.properties.Constants;

import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * 应用包管理
 *
 * @author gongzhifei
 */
@RestController
@RequestMapping("/financial/market/package")
public class AppPackageController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RestTemplate restTemplate;


    /**
     * 应用包查询
     *
     * @param marketAppCondition
     * @return
     */
    @ApiOperation(value = "查询应用包所有信息")
    @GetMapping
    public ResponseBody<MarketApp> query(MarketAppCondition marketAppCondition) {
        try{
            Body<Boolean,MarketAppCondition> inBody = new Body<>();
            inBody.setData(marketAppCondition);
            ResponseBody<MarketApp> result = restTemplate.postForEntity(Constants.APP_PACKAGE_QUERY,inBody,ResponseBody.class).getBody();
            return result;
        }catch (Exception ex){
            logger.error("发送DMP服务获取应用包信息失败！", ex);
        }
        return null;
    }

    /**
     * 应用包创建
     *
     * @param marketApp
     * @return
     */
    @ApiOperation(value = "添加APP应用包")
    @PostMapping
    public ResponseBody create(@Valid @RequestBody MarketApp marketApp) {
        try{
            Body<Boolean,MarketApp> inBody = new Body<>();
            inBody.setData(marketApp);
            ResponseBody result = restTemplate.postForEntity(Constants.APP_PACKAGE_CREATE,inBody,ResponseBody.class).getBody();
            return result;
        }catch (Exception ex){
            logger.error("",ex);
        }
        return null;
    }

    @ApiOperation(value = "应用包修改")
    @PutMapping
    public ResponseBody update(@RequestParam("id") Integer id, @RequestParam("source") String source, @RequestParam("isUse") String isUse) {
        try {
            Map<String,Object> param = new HashMap<>();
            param.put("id",id);
            param.put("source",source);
            param.put("isUse",isUse);
            ResponseBody result = restTemplate.postForEntity(Constants.APP_PACKAGE_UPDATE,param,ResponseBody.class).getBody();
            return result;
        } catch (Exception ex) {
            logger.error("修改应用包信息失败！", ex);
        }
        return null;
    }

}
