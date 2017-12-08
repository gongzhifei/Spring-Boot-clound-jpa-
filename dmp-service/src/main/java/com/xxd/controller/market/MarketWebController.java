package com.xxd.controller.market;

import com.xxd.dto.Body;
import com.xxd.dto.ResponseBody;
import com.xxd.dto.market.MarketWeb;
import com.xxd.dto.market.condition.MarketWebCondition;
import com.xxd.exception.ReturnMessageException;
import com.xxd.service.market.MarketWebService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

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
    private MarketWebService marketWebService;

    /**
     * 分页查询推广链接
     * @param inbody
     * @return
     */
    @ApiOperation("分页查询推广链接")
    @PostMapping("/query")
    public ResponseBody<MarketWeb> query(@RequestBody Body<Boolean,MarketWebCondition> inbody){
        try{
            Page<MarketWeb> page = marketWebService.query(inbody.getData());
            return new ResponseBody<MarketWeb>(page.getContent(),page.getNumber(),page.getTotalElements(),true,"查询推广链接成功!");
        }catch (Exception ex){
            logger.error("查询推广链接失败!", ex);
            throw new ReturnMessageException(false, "查询推广链接失败!");
        }
    }

    /**
     * 添加推广链接
     * @param inBody
     * @return
     */
    @ApiOperation("新增推广链接")
    @PostMapping("/create")
    public Map<String,Object> create(@RequestBody Body<Boolean,MarketWeb> inBody){
        Map<String,Object> result = new HashMap<String,Object>();
        try{
            marketWebService.save(inBody.getData());
            result.put("success",true);
            result.put("message","新增推广链接成功!");
        }catch (ReturnMessageException ex){
            throw ex;
        } catch (Exception ex){
            throw new ReturnMessageException(false,"新增推广链接失败!");
        }
        return result;
    }

    @ApiOperation("修改推广链接")
    @PostMapping("/update")
    public Map<String,Object> update(@RequestBody Body<Boolean,MarketWeb> inBody){
        Map<String,Object> result = new HashMap<String,Object>();
        try{
            marketWebService.update(inBody.getData());
            result.put("success",true);
            result.put("message","新增推广链接成功!");
        }catch (ReturnMessageException ex){
            throw ex;
        } catch (Exception ex){
            throw new ReturnMessageException(false,"新增推广链接失败!");
        }
        return result;
    }



}
