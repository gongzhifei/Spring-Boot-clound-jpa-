package com.xxd.service.market;

import com.xxd.dto.market.condition.ReachOrInvestCondition;
import com.xxd.exception.ReturnMessageException;
import com.xxd.repository.market.UserTypeRepository;
import com.xxd.service.redis.RedisService;
import com.xxd.util.BaseJdbcTemplate;
import com.xxd.util.DateUtil;
import com.xxd.util.GenerString;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author gongzhifei
 */
@Service
public class ReachOrInvestService {

    @Resource(name = "managerJdbcTemplate")
    private BaseJdbcTemplate baseJdbcTemplate;

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserTypeRepository userTypeRepository;

    @Autowired
    private MarketDateBaseService marketDateBaseService;

    public Map<String, Object> getReachOrInvest(ReachOrInvestCondition reachOrInvestCondition) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            String count = "";
            String type = reachOrInvestCondition.getType();
            List<String> dates = marketDateBaseService.getDate(type, reachOrInvestCondition.getDate());
            if (StringUtils.isNotEmpty(reachOrInvestCondition.getStartDate()) && StringUtils.isNotEmpty(reachOrInvestCondition.getEndDate())) {
                String[] date = DateUtil.twoDaysApart(DateUtil.formatStringToDate(reachOrInvestCondition.getStartDate(), "yyyy-MM-dd"), DateUtil.formatStringToDate(reachOrInvestCondition.getEndDate(), "yyyy-MM-dd"));
                dates = Arrays.asList(date);
            }
            count = this.getReachOrInvestCount(reachOrInvestCondition.getTypeId(), reachOrInvestCondition.getChannelId(), reachOrInvestCondition.getSource(), dates, reachOrInvestCondition.getFlag(),reachOrInvestCondition.getUserId());
            result.put("success", true);
            result.put("message", "获取抵达/激活、投资人数成功!");
            result.put("data", count);
            return result;
        } catch (Exception e) {
            throw e;
        }
    }


    private String getReachOrInvestCount(String typeId, String channelId, String sources, List<String> dates, String flag,Integer userId) {
        try {
            List<Integer> typeList = new ArrayList<Integer>();
            List<String> channelList = new ArrayList<String>();
            List<String> sourceList = new ArrayList<String>();
            String sql = "select a.type_id as type,a.channel_id as channel,a.source as source " +
                    "from  (SELECT DISTINCT type_id,channel_id,source FROM dmp_market_app UNION " +
                    "SELECT DISTINCT type_id,channel_id,source FROM dmp_market_web) a where type_id in (:typeIds)";
            Map<String, Object> paramMap = new HashMap<String, Object>();
            if (StringUtils.isEmpty(typeId)) {
                typeList = userTypeRepository.findByUserId(userId);
            } else {
                String[] typeIds = typeId.split(",");
                for (int i = 0; i < typeIds.length; i++) {
                    String id = typeIds[i];
                    typeList.add(Integer.valueOf((id)));
                }
            }
            paramMap.put("typeIds", typeList);
            if (StringUtils.isNotEmpty(channelId)) {
                String[] channelIds = channelId.split(",");
                channelList = Arrays.asList(channelIds);
                sql += "and channel_id in (:channels)";
                paramMap.put("channels", channelList);
            }
            if (StringUtils.isNotEmpty(sources)) {
                sourceList = Arrays.asList(sources.split(","));
                sql += "and source in (:sources)";
                paramMap.put("sources", sourceList);
            }
            List<Map<String, Object>> maps = baseJdbcTemplate.queryForList(sql, paramMap);
            String[] keys = new String[maps.size() * dates.size()];
            int count = 0;
            for (int j = 0; j < dates.size(); j++) {
                String date = dates.get(j);
                for (int i = 0; i < maps.size(); i++) {
                    Map<String, Object> map = maps.get(i);
                    String type = (String) map.get("type");
                    String channel = (String) map.get("channel");
                    String source = (String) map.get("source");

                    String str = type + channel + source + date;
                    String md5 = GenerString.getMd5(str);
                    String key = "";
                    if (StringUtils.equals(flag, "1")) {  //激活
                        key = "JH_" + md5;
                    } else {
                        key = "TZ_" + md5;
                    }
                    keys[count] = key;
                    count++;
                }
            }
            ZSetOperations<String, ?> z = redisService.getZSetOperations();
            z.unionAndStore("tempKey", Arrays.asList(keys), "tempKey");
            Long result = z.zCard("tempKey");
            redisService.delete("tempKey");
            return String.valueOf(result);
        } catch (ReturnMessageException ex) {
            throw new ReturnMessageException(false, "获取激活/投资人数失败!");
        }
    }

}
