package com.xxd.controller.market;

import com.xxd.dto.Body;
import com.xxd.dto.ResponseBody;
import com.xxd.dto.market.condition.EffectCompareCondition;
import com.xxd.exception.ReturnMessageException;
import com.xxd.service.market.EffectCompareService;
import com.xxd.util.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 效果对比
 * @author gongzhifei
 */
@RestController
@RequestMapping("/financial/market/effectCompare")
public class EffectCompareController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EffectCompareService effectCompareService;

    /**
     *效果对比表单
     * @param inbody
     * @return
     */
    @ApiOperation("效果对比表单")
    @PostMapping("/compareList")
    public ResponseBody<Object> compareList(@RequestBody Body<Boolean,EffectCompareCondition> inbody){
        try {
            PageInfo page = effectCompareService.effectCompare(inbody.getData());
            return new ResponseBody<Object>(page.getResult(),page.getPageIndex(),Long.valueOf(page.getTotalCount()),true,"获取效果对比数据成功!");
        } catch (Exception e) {
            logger.error("查询对比数据失败!", e);
            throw new ReturnMessageException(false,"获取效果对比数据失败!");
        }
    }

    /**
     * 效果对比散点图
     * @param inBody
     * @return
     */
    @ApiOperation("效果对比散点图")
    @PostMapping("/scatter")
    public Map<String, Object> scatter(@RequestBody Body<Boolean,EffectCompareCondition> inBody){
        Map<String, Object> result = new HashMap<String, Object>();
        try{
            List<List<String []>> data = effectCompareService.scatter(inBody.getData());
            result.put("success", true);
            result.put("message", "获取效果对比散点图数据成功!");
            result.put("data",data);
        }catch (Exception e){
            logger.debug("查询效果对比散点图失败!",e);
            throw new ReturnMessageException(false,"获取效果对比散点图数据失败!");
        }
        return result;
    }

}
