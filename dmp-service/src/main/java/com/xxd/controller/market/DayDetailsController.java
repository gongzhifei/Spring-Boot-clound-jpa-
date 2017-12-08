package com.xxd.controller.market;

import com.xxd.dto.Body;
import com.xxd.dto.ResponseBody;
import com.xxd.dto.market.condition.DayDetailsCondition;
import com.xxd.exception.ReturnMessageException;
import com.xxd.service.market.DayDetailsService;
import com.xxd.util.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author gongzhifei
 */
@RestController
@RequestMapping("/financial/market")
public class DayDetailsController {

    @Autowired
    private DayDetailsService detailsService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ApiOperation("分日明细查询")
    @PostMapping("/dailydetail")
    public ResponseBody<Object> query(@RequestBody Body<Boolean,DayDetailsCondition> inbody) {
        try {
            PageInfo page = detailsService.details(inbody.getData());
            return new ResponseBody<Object>(page.getResult(),page.getPageIndex(),Long.valueOf(page.getTotalCount()),true,"获取分日明细数据成功!");
        } catch (Exception ex) {
            logger.error("查询分日明细出错!", ex);
            throw new ReturnMessageException(false, "查询分日明细失败");
        }
    }
}
