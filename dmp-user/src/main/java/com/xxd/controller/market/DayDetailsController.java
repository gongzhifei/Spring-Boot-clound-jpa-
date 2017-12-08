package com.xxd.controller.market;

import com.xxd.dto.Body;
import com.xxd.dto.ResponseBody;
import com.xxd.dto.market.condition.DayDetailsCondition;
import com.xxd.dto.user.UserInfo;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author gongzhifei
 */
@RestController
@RequestMapping("/financial/market")
public class DayDetailsController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RestTemplate restTemplate;

    @Value("${xxd.serviceUrl}")
    private String serviceUrl;

    @ApiOperation("分日明细查询")
    @GetMapping("/dailydetail")
    public ResponseBody<Object> query(DayDetailsCondition detailsCondition) {
        try {
            UserInfo user = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            detailsCondition.setUserId(user.getId());
            Body<Boolean,DayDetailsCondition> inBody = new Body<>();
            inBody.setData(detailsCondition);
            inBody.setSuccess(true);
            ResponseBody<Object> map = restTemplate.postForEntity(serviceUrl+"/financial/market/dailydetail",inBody,ResponseBody.class).getBody();
            return map;
        } catch (Exception ex) {
            logger.error("发送DMP服务获取信息失败！",ex);
        }
        return null;
    }
}
