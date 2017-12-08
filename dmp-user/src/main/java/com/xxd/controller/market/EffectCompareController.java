package com.xxd.controller.market;

import com.xxd.dto.Body;
import com.xxd.dto.ResponseBody;
import com.xxd.dto.market.condition.EffectCompareCondition;

import com.xxd.dto.user.UserInfo;
import com.xxd.properties.Constants;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * 效果对比
 *
 * @author gongzhifei
 */
@RestController
@RequestMapping("/financial/market/effectCompare")
public class EffectCompareController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 效果对比表单
     *
     * @param effectCompareCondition
     * @param
     * @return
     */
    @ApiOperation("效果对比表单")
    @GetMapping("/compareList")
    public ResponseBody<Object> compareList(EffectCompareCondition effectCompareCondition) {
        try {
            Body<Boolean, EffectCompareCondition> inBody = new Body<>();
            UserInfo user = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            effectCompareCondition.setUserId(user.getId());
            inBody.setData(effectCompareCondition);
            ResponseBody<Object> result = restTemplate.postForEntity(Constants.EFFECT_COMPARE_LIST, inBody,ResponseBody.class).getBody();
            return result;
        } catch (Exception e) {
            logger.error("发送DMP服务获取效果对比数据失败!", e);
        }
        return null;
    }

    /**
     * 效果对比散点图
     *
     * @param effectCompareCondition
     * @return
     */
    @ApiOperation("效果对比散点图")
    @GetMapping("/scatter")
    public Map<String, Object> scatter(EffectCompareCondition effectCompareCondition) {
        try {
            Body<Boolean, EffectCompareCondition> inBody = new Body<Boolean, EffectCompareCondition>();
            UserInfo user = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            effectCompareCondition.setUserId(user.getId());
            inBody.setData(effectCompareCondition);
            Map<String, Object> result = restTemplate.postForEntity(Constants.EFFECT_COMPARE_SCATTER,inBody, Map.class).getBody();
            return result;
        } catch (Exception e) {
            logger.debug("发送DMP服务获取效果对比散点图失败!", e);
        }
        return null;
    }

}
