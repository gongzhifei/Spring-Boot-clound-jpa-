package com.xxd.controller.market;

import com.xxd.dto.Body;
import com.xxd.dto.market.condition.ReachOrInvestCondition;
import com.xxd.exception.ReturnMessageException;
import com.xxd.service.market.ReachOrInvestService;
import io.swagger.annotations.ApiModelProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 *获取注册人数或投资人数
 * @author gongzhifei
 */
@RestController
@RequestMapping("/financial/market")
public class ReachOrInvestNumController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ReachOrInvestService reachOrInvestService;

    @ApiModelProperty("获取抵达/激活、投资人数")
    @PostMapping("/reachOrInvest")
    public Map<String, Object> reachOrInvest(@RequestBody Body<Boolean,ReachOrInvestCondition> inBody){
        Map<String, Object> result = new HashMap<String,Object>();
        try {
            result = reachOrInvestService.getReachOrInvest(inBody.getData());
        } catch (Exception e) {
            logger.error("获取抵达/激活 or 投资人数失败", e);
            throw new ReturnMessageException(false,"获取抵达/激活、投资人数失败!");
        }
        return result;
    }

}
