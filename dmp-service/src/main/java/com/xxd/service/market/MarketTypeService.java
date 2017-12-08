package com.xxd.service.market;

import com.xxd.dto.market.MarketChannel;
import com.xxd.exception.ReturnMessageException;
import com.xxd.repository.market.MarketChannelRepository;
import com.xxd.repository.market.UserTypeRepository;
import com.xxd.util.BaseJdbcTemplate;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author gongzhifei
 */
@Service
public class MarketTypeService {

    @Resource(name = "managerJdbcTemplate")
    private BaseJdbcTemplate jdbcTemplate;

    @Autowired
    private UserTypeRepository userTypeRepository;

    @Autowired
    private MarketChannelRepository marketChannelRepository;

    public List<Map<String,Object>> getTypes(Integer userId, String flag){
        List<Integer> typeIds = userTypeRepository.findByUserId(userId);
        String sql = "select type_id as typeId,type_name as typeName from v_dmp_market_type_channel where type_id in (:typeIds)";
        if("1".equals(flag)){
            sql+=" and type='web'";
        }else if("2".equals(flag)){
            sql += " and type='app'";
        }
        sql += " group by type_id,type_name";
        Map<String,Object> param = new HashMap<>();
        param.put("typeIds",typeIds);
        return jdbcTemplate.queryForList(sql,param);
    }

    public List<Map<String,Object>> getChannels(String typeId,Integer userId){
        try{
            String sql = "select channel_id as channelId,channel_name as channelName from (select DISTINCT channel_id,type_id,channel_name from v_dmp_market_type_channel) a " +
                    " where type_id in (:userTypeIds) ";
            Map<String,Object> param = new HashMap<>();
            param.put("userTypeIds",userTypeRepository.findByUserId(userId));
            if(StringUtils.isNotEmpty(typeId)){
                String [] typeIds = typeId.split(",");
                List<String> list = new ArrayList<>();
                list = Arrays.asList(typeIds);
                sql += "and type_id in (:typeIds) group by channel_id HAVING count(1) = "+list.size()+"";
                param.put("typeIds",list);
            }else{
                sql +=" group by channel_id";
            }

            return jdbcTemplate.queryForList(sql,param);
        }catch (ReturnMessageException e){
            throw new ReturnMessageException(false,"查询渠道出错!");
        }
    }

    public List<MarketChannel> query(){
        return marketChannelRepository.findAll();
    }

}
