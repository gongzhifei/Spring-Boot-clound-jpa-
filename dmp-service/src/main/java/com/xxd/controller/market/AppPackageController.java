package com.xxd.controller.market;

import com.xxd.dto.Body;
import com.xxd.dto.ResponseBody;
import com.xxd.dto.market.MarketApp;
import com.xxd.dto.market.condition.MarketAppCondition;
import com.xxd.exception.ReturnMessageException;
import com.xxd.service.market.MarketAppService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 应用包管理
 *
 * @author gongzhifei
 */
@RestController
@RequestMapping("/financial/market/package")
public class AppPackageController {

    @Autowired
    private MarketAppService marketAppService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 应用包查询
     * @param inBody
     * @return
     */
    @ApiOperation(value = "查询应用包所有信息")
    @PostMapping("/query")
    public ResponseBody<MarketApp> query(@RequestBody Body<Boolean,MarketAppCondition> inBody) {
        try{
            MarketAppCondition marketAppCondition = inBody.getData();
            Page<MarketApp> page = marketAppService.query(marketAppCondition);
            return new ResponseBody<MarketApp>(page.getContent(),page.getNumber(),page.getTotalElements(),true,"应用包信息查询成功!");
        }catch (Exception ex){
            logger.error("应用包信息查询失败！", ex);
            throw new ReturnMessageException(false, "应用包信息查询失败!");
        }
    }

    /**
     * 应用包创建
     * @param inBody
     * @return
     * @throws ReturnMessageException
     */
    @ApiOperation(value = "添加APP应用包")
    @PostMapping
    public ResponseBody create(@RequestBody Body<Boolean,MarketApp> inBody) throws ReturnMessageException {
        try {
            MarketApp marketApp = inBody.getData();
            marketAppService.save(marketApp);
            return new ResponseBody(true,"保存应用包成功!");
        } catch (ReturnMessageException ex) {
            logger.error(ex.getContent());
            throw ex;
        } catch (Exception ex) {
            logger.error("保存应用包信息失败！", ex);
            throw new ReturnMessageException(false, "保存应用包信息失败!");
        }
    }

    @ApiOperation(value = "应用包修改")
    @PostMapping("/update")
    public ResponseBody update(@RequestBody Map<String,Object> param) {
        try {
            Integer id= (Integer) param.get("id");
            String source= (String) param.get("source");
            String isUse= (String) param.get("isUse");
            System.out.println("id:"+id+",source:"+source+",isUse:"+isUse);
            marketAppService.update(id,source,isUse);
            return new ResponseBody(true,"修改应用包成功!");
        } catch (ReturnMessageException ex) {
            logger.error(ex.getContent());
            throw ex;
        } catch (Exception ex) {
            logger.error("修改应用包信息失败！", ex);
            throw new ReturnMessageException(false, "修改应用包信息失败!");
        }
    }

}
