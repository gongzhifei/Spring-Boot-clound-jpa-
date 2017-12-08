package com.xxd.controller.market;

import com.xxd.dto.Body;
import com.xxd.dto.ResponseBody;
import com.xxd.dto.market.condition.EffectTrackCondition;
import com.xxd.dto.user.UserInfo;
import com.xxd.properties.Constants;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


/**
 * 效果跟踪
 * @author gongzhifei
 */
@RestController
@RequestMapping("/financial/market/effectTrack")
public class EffectTrackController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 用户列表
     * @param effectTrackCondition
     * @return
     */
    @ApiOperation("效果跟踪用户列表")
    @GetMapping("/userList")
    public ResponseBody<Object> userList(EffectTrackCondition effectTrackCondition){
        try{
            Body<Boolean,EffectTrackCondition> inBody = new Body<>();
            UserInfo user = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            effectTrackCondition.setUserId(user.getId());
            inBody.setData(effectTrackCondition);
            ResponseBody<Object> result = restTemplate.postForEntity(Constants.EFFECT_TRACT_USERLIST,inBody,ResponseBody.class).getBody();
            return result;
        }catch (Exception ex){
            logger.error("获取效果跟踪用户列表数据失败!",ex);
        }
        return null;
    }

    /**
     * 效果跟踪堆积图数据查询
     * @param effectTrackCondition
     * @return
     */
    @ApiOperation("效果跟踪堆积图数据查询")
    @PostMapping("/stackedGraph")
    public Map<String, Object> stacked(EffectTrackCondition effectTrackCondition) {
        try {
            Body<Boolean,EffectTrackCondition> inBody = new Body<>();
            UserInfo user = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            effectTrackCondition.setUserId(user.getId());
            inBody.setData(effectTrackCondition);
            Map<String, Object> result = restTemplate.postForEntity(Constants.EFFECT_TRACT_STACKEDGRAPH,inBody,Map.class).getBody();
            return result;
        } catch (Exception e) {
            logger.error("查询效果跟踪堆积图数据失败!", e);
        }
        return null;
    }

    /**
     * 效果跟踪环形图
     * @param effectTrackCondition
     * @return
     */
    @ApiOperation("效果跟踪环形图")
    @PostMapping(value = "/ringChart")
    public Map<String, Object> effectPieChart(EffectTrackCondition effectTrackCondition) {
        try {
            Body<Boolean,EffectTrackCondition> inBody = new Body<>();
            UserInfo user = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            effectTrackCondition.setUserId(user.getId());
            inBody.setData(effectTrackCondition);
            Map<String, Object> result = restTemplate.postForEntity(Constants.EFFECT_TRACT_RINGCHART,inBody,Map.class).getBody();
            return result;
        } catch (Exception e) {
            logger.error("查询效果跟踪环形图数据失败!", e);
        }
        return null;
    }
}