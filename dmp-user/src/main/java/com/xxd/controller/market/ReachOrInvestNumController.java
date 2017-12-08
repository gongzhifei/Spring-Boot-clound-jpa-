package com.xxd.controller.market;

import com.xxd.dto.Body;
import com.xxd.dto.ResponseBody;
import com.xxd.dto.market.condition.ReachOrInvestCondition;
import com.xxd.dto.user.UserInfo;
import com.xxd.properties.Constants;
import io.swagger.annotations.ApiModelProperty;
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
 *获取注册人数或投资人数
 * @author gongzhifei
 */
@RestController
@RequestMapping("/financial/market")
public class ReachOrInvestNumController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RestTemplate restTemplate;

    @ApiModelProperty("获取抵达/激活、投资人数")
    @GetMapping("/reachOrInvest")
    public Map<String,Object> reachOrInvest(ReachOrInvestCondition reachOrInvestCondition){
        try {
            Body<Boolean,ReachOrInvestCondition> inBody = new Body<>();
            UserInfo user = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            reachOrInvestCondition.setUserId(user.getId());
            inBody.setData(reachOrInvestCondition);
            Map<String,Object> result = restTemplate.postForEntity(Constants.REACH_OR_INVEST, inBody,Map.class).getBody();
            return result;
        } catch (Exception e) {
            logger.error("获取抵达/激活 or 投资人数失败", e);
        }
        return null;
    }

}
