package com.xxd.controller.market;

import com.xxd.dto.Body;
import com.xxd.dto.ResponseBody;
import com.xxd.dto.market.condition.EffectTrackCondition;
import com.xxd.exception.ReturnMessageException;
import com.xxd.service.market.EffectTrackService;
import com.xxd.util.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


/**
 * 效果跟踪
 * @author gongzhifei
 */
@RestController
@RequestMapping("/financial/market/effectTrack")
public class EffectTrackController {

    @Autowired
    private EffectTrackService effectTrackService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 用户列表
     * @param inbody
     * @return
     */
    @ApiOperation("效果跟踪用户列表")
    @PostMapping("/userList")
    public ResponseBody<Object> userList(@RequestBody Body<Boolean,EffectTrackCondition> inbody){
        try{
            PageInfo page = effectTrackService.userList(inbody.getData());
            return new ResponseBody<Object>(page.getResult(),page.getPageIndex(),Long.valueOf(page.getTotalCount()),true,"获取效果跟踪用户列表数据成功!");
        }catch (Exception ex){
            logger.error("获取效果跟踪用户列表数据失败!",ex);
            throw new ReturnMessageException(false,"获取效果跟踪用户列表数据失败!");
        }
    }

    /**
     * 效果跟踪堆积图数据查询
     * @param inBody
     * @return
     */
    @ApiOperation("效果跟踪堆积图数据查询")
    @RequestMapping(value = "/stackedGraph", method = RequestMethod.POST)
    public Map<String, Object> stacked(@RequestBody Body<Boolean,EffectTrackCondition> inBody) {
        Map<String, Object> result = new HashMap<String,Object>();
        try {
            Map<String, Object> data = effectTrackService.stacked(inBody.getData());
            result.put("success", true);
            result.put("message", "获取效果跟踪堆积图数据成功!");
            result.put("data", data);

        } catch (Exception e) {
            logger.error("查询效果跟踪堆积图数据失败!", e);
            throw new ReturnMessageException(false,"查询效果跟踪堆积图数据失败!");
        }
        return result;
    }

    /**
     * 效果跟踪环形图
     * @param inBody
     * @return
     */
    @ApiOperation("效果跟踪环形图")
    @RequestMapping(value = "/ringChart", method = RequestMethod.POST)
    public Map<String, Object> effectPieChart(@RequestBody Body<Boolean,EffectTrackCondition> inBody) {
        Map<String, Object> result = new HashMap<String,Object>();
        try {
            Map<String, Object> mapList = effectTrackService.ringChart(inBody.getData());
            result.put("success", true);
            result.put("message", "获取效果跟踪环形图数据成功!");
            result.put("data", mapList);
        } catch (Exception e) {
            logger.error("查询效果跟踪环形图数据失败!", e);
            result.put("success", false);
            result.put("message", "获取效果跟踪环形图数据失败!");
        }
        return result;
    }
}