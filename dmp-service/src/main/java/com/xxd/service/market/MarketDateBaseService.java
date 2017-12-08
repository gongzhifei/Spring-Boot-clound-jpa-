package com.xxd.service.market;

import com.xxd.repository.market.MarketDateBaseRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gongzhifei
 */
@Component
public class MarketDateBaseService {

    @Autowired
    private MarketDateBaseRepository marketDateBaseRepository;

    /**
     * 查询数据库 3：年:2：月:1：日 时间
     * @param flag
     * @param startDate
     * @param endDate
     * @return
     */
    public List<String> findByDate(int flag,String startDate,String endDate){
        List<String> dateList = new ArrayList<String>();
        String condition = "";
        if(flag==2){
            condition = "a.week";
            dateList = marketDateBaseRepository.findByDateBetween(condition,startDate,endDate,"group by a.week order by a.week asc");
        }else if(flag==3){
            condition = "a.month";
            dateList = marketDateBaseRepository.findByDateBetween(condition,startDate,endDate,"group by a.month order by a.month asc");
        }
        return dateList;
    }

    /**
     * 根据输入类型 周 月 返回日期List
     * @param type 1:日 2：周 3：月
     * @param date 输入的日、周 或 月数据
     * @return
     */
    public List<String> getDate(String type,String date){
        List<String> dates = new ArrayList<String>();
        if(StringUtils.equals(type,"1")){
            dates.add(date);
        }else if(StringUtils.equals(type,"2")){
            dates = marketDateBaseRepository.findByDate("week",date);
        }else if(StringUtils.equals(type,"3")){
            dates = marketDateBaseRepository.findByDate("month",date);
        }
        return dates;
    }

}
