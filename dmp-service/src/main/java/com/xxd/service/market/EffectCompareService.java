package com.xxd.service.market;

import com.xxd.dto.market.condition.EffectCompareCondition;
import com.xxd.dto.user.UserInfo;
import com.xxd.exception.ReturnMessageException;
import com.xxd.repository.market.UserTypeRepository;
import com.xxd.repository.report.SwitchTableRepository;
import com.xxd.util.BaseJdbcTemplate;
import com.xxd.util.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author gongzhifei
 */
@Service
public class EffectCompareService {

    @Resource(name = "reportJdbcTemplate")
    private BaseJdbcTemplate reportJdbcTemplate;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SwitchTableRepository switchTableRepository;

    @Autowired
    private UserTypeRepository userTypeRepository;

    public PageInfo effectCompare(EffectCompareCondition compareCondition) {
        try {
            List<Integer> userTypeIds = userTypeRepository.findByUserId(compareCondition.getUserId());
            List<String> typeIds = new ArrayList<String>();
            List<String> channelIds = new ArrayList<String>();
            List<String> sources = new ArrayList<String>();
            if (StringUtils.isNotBlank(compareCondition.getTypeId())) {
                typeIds = Arrays.asList(compareCondition.getTypeId().split(","));
            }
            if (StringUtils.isNotEmpty(compareCondition.getChannelId())) {
                channelIds = Arrays.asList(compareCondition.getChannelId().split(","));
            }
            if (StringUtils.isNotEmpty(compareCondition.getSource())) {
                sources = Arrays.asList(compareCondition.getSource().split(","));
            }
            String tableName = switchTableRepository.findByTableName("day_union");
            Map<String, Object> paramMap = new HashMap<String, Object>();
            String sql = "select ${fields} from ${tableName} a,manager.dmp_market_type b,manager.dmp_market_channel c where a.type_id = b.id and " +
                    "a.channel_id = c.channel_id and a.type_id in (:userTypeIds) ";
            sql = StringUtils.replace(sql, "${tableName}", tableName);
            String sqlCount = new String("select count(*) from ");
            String field = "";
            if (StringUtils.isNotEmpty(compareCondition.getStartDate()) && StringUtils.isNotEmpty(compareCondition.getEndDate())) {
                sql += "and a.date between :startDate and :endDate ";
                paramMap.put("startDate", compareCondition.getStartDate());
                paramMap.put("endDate", compareCondition.getEndDate() + " 23:59:59");
            }
            if (typeIds.size() > 0 && channelIds.size() == 1 && sources.size() == 0) {
                sql += "and a.type_id in (:typeIds) and a.channel_Id in (:channelIds) group by a.type_id,a.channel_id ";
                field = "a.date as date,a.type_id as typeId,a.channel_id as channelId,b.type_name as typeName,c.channel_name as channelName,'' as source, " +
                        "SUM(a.registered_num) as registerNum,sum(a.first_investment_usernum) as firstInvestmentUserNum," +
                        "SUM(a.first_investment_addaccount) as firstInvestmentAddAccount,SUM(a.first_investment_nh) as firstInvestmentNh," +
                        "SUM(a.ztz_nh) as ztzNh,SUM(a.investment_addaccount) as investmentAddAccount,SUM(a.sm_num) as  smNum," +
                        "SUM(a.bk_num) as bkNum ,'false' as activation,'false' as investNum ";
            } else if (typeIds.size() > 0 && channelIds.size() == 0 && sources.size() == 0) {
                sql += "and a.type_id in (:typeIds)  GROUP BY a.type_id";
                field = "a.date as date,a.type_id as typeId,b.type_name as typeName,'' as channelId,'' as channelName,'' as source, " +
                        "sum(a.registered_num) as registerNum,sum(a.first_investment_usernum) as firstInvestmentUserNum," +
                        "sum(a.first_investment_addaccount) as firstInvestmentAddAccount,sum(a.first_investment_nh) as firstInvestmentNh," +
                        "sum(a.ztz_nh) as ztzNh,sum(a.investment_addaccount) as investmentAddAccount,sum(a.sm_num) as  smNum," +
                        "sum(a.bk_num) as bkNum ,'false' as activation,'false' as investNum ";
            } else if (typeIds.size() == 1 && channelIds.size() > 0 && sources.size() == 0) {
                sql += " and a.type_id in (:typeIds) and a.channel_id in (:channelIds) GROUP BY a.type_id,a.channel_id";
                field = "a.date as date,a.type_id as typeId,b.type_name as typeName,a.channel_id as channelId,c.channel_name as channelName,'' as source, " +
                        "sum(a.registered_num) as registerNum,sum(a.first_investment_usernum) as firstInvestmentUserNum," +
                        "sum(a.first_investment_addaccount) as firstInvestmentAddAccount,sum(a.first_investment_nh) as firstInvestmentNh," +
                        "sum(a.ztz_nh) as ztzNh,sum(a.investment_addaccount) as investmentAddAccount,sum(a.sm_num) as  smNum," +
                        "sum(a.bk_num) as bkNum ,'false' as activation,'false' as investNum ";
            } else if (typeIds.size() == 0 && channelIds.size() > 0 && sources.size() == 0) {
                sql += " and a.channel_id in (:channelIds) GROUP BY a.channel_id";
                field = "a.date as date,a.channel_id as channelId,c.channel_name as channelName,'' as typeId,'' as typeName,'' as source, " +
                        "sum(a.registered_num) as registerNum,sum(a.first_investment_usernum) as firstInvestmentUserNum," +
                        "sum(a.first_investment_addaccount) as firstInvestmentAddAccount,sum(a.first_investment_nh) as firstInvestmentNh," +
                        "sum(a.ztz_nh) as ztzNh,sum(a.investment_addaccount) as investmentAddAccount,sum(a.sm_num) as  smNum," +
                        "sum(a.bk_num) as bkNum  ,'false' as activation,'false' as investNum ";
            } else if (typeIds.size() == 1 && channelIds.size() == 1 & sources.size() > 0) {
                sql += " and a.type_id in (:typeIds) and a.channel_id in (:channelIds) and source in (:sources) GROUP BY a.source";
                field = "a.date as date,a.type_id as typeId,a.channel_id as channelId,b.type_name as typeName,c.channel_name as channelName,a.source as source," +
                        "sum(a.registered_num) as registerNum,sum(a.first_investment_usernum) as firstInvestmentUserNum," +
                        "sum(a.first_investment_addaccount) as firstInvestmentAddAccount,sum(a.first_investment_nh) as firstInvestmentNh," +
                        "sum(a.ztz_nh) as ztzNh,sum(a.investment_addaccount) as investmentAddAccount,sum(a.sm_num) as  smNum," +
                        "sum(a.bk_num) as bkNum ,'false' as activation,'false' as investNum ";
            } else if (typeIds.size() == 1 && channelIds.size() == 0 && sources.size() > 0) {
                sql += " and a.type_id in (:typeIds) and source in (:sources) group by a.source";
                field = "a.date as date,a.type_id as typeId,b.type_name as typeName,'' as channelId,'' as channelName,a.source as source," +
                        "sum(a.registered_num) as registerNum,sum(a.first_investment_usernum) as firstInvestmentUserNum," +
                        "sum(a.first_investment_addaccount) as firstInvestmentAddAccount,sum(a.first_investment_nh) as firstInvestmentNh," +
                        "sum(a.ztz_nh) as ztzNh,sum(a.investment_addaccount) as investmentAddAccount,sum(a.sm_num) as  smNum," +
                        "sum(a.bk_num) as bkNum ,'false' as activation,'false' as investNum  ";
            } else if (typeIds.size() == 0 && channelIds.size() == 1 && sources.size() > 0) {
                sql += " and a.channel_id in (:channelIds) and source in (:sources) group by a.source";
                field = "a.date as date,a.channel_id as channelId,c.channel_name as channelName,'' as typeId,'' as typeName,a.source as source," +
                        "sum(a.registered_num) as registerNum,sum(a.first_investment_usernum) as firstInvestmentUserNum," +
                        "sum(a.first_investment_addaccount) as firstInvestmentAddAccount,sum(a.first_investment_nh) as firstInvestmentNh," +
                        "sum(a.ztz_nh) as ztzNh,sum(a.investment_addaccount) as investmentAddAccount,sum(a.sm_num) as  smNum," +
                        "sum(a.bk_num) as bkNum ,'false' as activation,'false' as investNum ";
            }
            sql = StringUtils.replace(sql, "${fields}", field);
            sql += " order by a.date desc ";
            sqlCount = sqlCount + "(" + sql + ") b";
            paramMap.put("userTypeIds", userTypeIds);
            paramMap.put("typeIds", typeIds);
            paramMap.put("channelIds", channelIds);
            paramMap.put("sources", sources);
            PageInfo page = new PageInfo(compareCondition.getPageNo(), compareCondition.getPageSize());
            page = reportJdbcTemplate.queryForPageMysql(page, sql, sqlCount, paramMap);
            return page;
        } catch (ReturnMessageException e) {
            throw new ReturnMessageException(false,"效果对比查询失败!");
        }
    }

    public List<List<String[]>> scatter(EffectCompareCondition compareCondition){
        try {
            List<Integer> userTypeIds = userTypeRepository.findByUserId(compareCondition.getUserId());
            List<String> typeIds = new ArrayList<String>();
            List<String> channelIds = new ArrayList<String>();
            List<String> sources = new ArrayList<String>();
            if (StringUtils.isNotEmpty(compareCondition.getTypeId())) {
                typeIds = Arrays.asList(compareCondition.getTypeId().split(","));
            }
            if (StringUtils.isNotEmpty(compareCondition.getChannelId())) {
                channelIds = Arrays.asList(compareCondition.getChannelId().split(","));
            }
            if (StringUtils.isNotEmpty(compareCondition.getSource())) {
                sources = Arrays.asList(compareCondition.getSource().split(","));
            }
            String tableName = switchTableRepository.findByTableName("day_union");
            Map<String, Object> paramMap = new HashMap<String, Object>();
            String sql = "select ${field} from ${tableName} a,manager.dmp_market_type b,manager.dmp_market_channel c where a.type_id = b.id and a.channel_id = c.channel_id and a.type_id in (:userTypeIds) ";
            sql = StringUtils.replace(sql, "${tableName}", tableName);
            String field = "";
            if (StringUtils.isNotEmpty(compareCondition.getStartDate()) && StringUtils.isNotEmpty(compareCondition.getEndDate())) {
                sql += "and a.date between :startDate and :endDate ";
                paramMap.put("startDate", compareCondition.getStartDate());
                paramMap.put("endDate", compareCondition.getEndDate() + " 23:59:59");
            }
            if (typeIds.size() > 0 && channelIds.size() == 1 && sources.size() == 0) {
                sql += "and a.type_id in (:typeIds) and a.channel_Id in (:channelIds) group by a.type_id ";
                field = "a.id as id,a.type_id as typeId,a.channel_id as channelId,b.type_name as typeName,c.channel_name as channelName,'' as source," +
                        "SUM(a.registered_num) as registerNum,sum(a.first_investment_usernum) as firstInvestmentUserNum,SUM(a.first_investment_addaccount) as firstInvestmentAddAccount";
            } else if (typeIds.size() > 0 && channelIds.size() == 0 && sources.size() == 0) {
                sql += "and a.type_id in (:typeIds)  GROUP BY a.type_id";
                field = "a.id as id,a.type_id as typeId,b.type_name as typeName,'' as channelId,'' as channelName,'' as source," +
                        "SUM(a.registered_num) as registerNum,sum(a.first_investment_usernum) as firstInvestmentUserNum,SUM(a.first_investment_addaccount) as firstInvestmentAddAccount";
            } else if (typeIds.size() == 1 && channelIds.size() > 0 && sources.size() == 0) {
                sql += " and a.type_id in (:typeIds) and a.channel_id in (:channelIds) GROUP BY a.channel_id";
                field = "a.id as id,a.type_id as typeId,b.type_name as typeName,a.channel_id as channelId,c.channel_name as channelName,'' as source," +
                        "SUM(a.registered_num) as registerNum,sum(a.first_investment_usernum) as firstInvestmentUserNum,SUM(a.first_investment_addaccount) as firstInvestmentAddAccount";
            } else if (typeIds.size() == 0 && channelIds.size() > 0 && sources.size() == 0) {
                sql += " and a.channel_id in (:channelIds) GROUP BY a.channel_id";
                field = "a.id as id,a.channel_id as channelId,c.channel_name as channelName,'' as typeId,'' as typeName,'' as source," +
                        "SUM(a.registered_num) as registerNum,sum(a.first_investment_usernum) as firstInvestmentUserNum,SUM(a.first_investment_addaccount) as firstInvestmentAddAccount";
            } else if (typeIds.size() == 1 && channelIds.size() == 1 & sources.size() > 0) {
                sql += " and a.type_id in (:typeIds) and a.channel_id in (:channelIds) and source in (:sources) GROUP BY a.source";
                field = "a.id as id,a.type_id as typeId,a.channel_id as channelId,b.type_name as typeName,c.channel_name as channelName,a.source as source," +
                        "SUM(a.registered_num) as registerNum,sum(a.first_investment_usernum) as firstInvestmentUserNum,SUM(a.first_investment_addaccount) as firstInvestmentAddAccount";
            } else if (typeIds.size() == 1 && channelIds.size() == 0 && sources.size() > 0) {
                sql += " and a.type_id in (:typeIds) and source in (:sources) group by a.source";
                field = "a.id as id,a.type_id as typeId,b.type_name as typeName,'' as channelId,'' as channelName,a.source as source," +
                        "SUM(a.registered_num) as registerNum,sum(a.first_investment_usernum) as firstInvestmentUserNum,SUM(a.first_investment_addaccount) as firstInvestmentAddAccount";
            } else if (typeIds.size() == 0 && channelIds.size() == 1 && sources.size() > 0) {
                sql += " and a.channel_id in (:channelIds) and source in (:sources) group by a.source";
                field = "a.id as id,a.channel_id as channelId,c.channel_name as channelName,'' as typeId,'' as typeName,a.source as source," +
                        "SUM(a.registered_num) as registerNum,sum(a.first_investment_usernum) as firstInvestmentUserNum,SUM(a.first_investment_addaccount) as firstInvestmentAddAccount";
            }
            sql = StringUtils.replace(sql, "${field}", field);
            sql += " order by firstInvestmentAddAccount desc limit ";
            paramMap.put("userTypeIds", userTypeIds);
            paramMap.put("typeIds", typeIds);
            paramMap.put("channelIds", channelIds);
            paramMap.put("sources", sources);
            String firstSql = sql +"0,3";
            String dataSql = sql +"3,1000000";
            List<Map<String, Object>> firstList = reportJdbcTemplate.queryForList(firstSql, paramMap);
            List<Map<String, Object>> dataList = reportJdbcTemplate.queryForList(dataSql, paramMap);
            List<String[]> first = convertList(firstList);
            List<String[]> data = convertList(dataList);
            List<List<String[]>> result = new ArrayList<List<String[]>>();
            result.add(first);
            result.add(data);
            return result;
        } catch (ReturnMessageException e) {
            throw new ReturnMessageException(false,"查询散点图失败!");
        }

    }

    private List<String []> convertList(List<Map<String,Object>> mapList){
        List<String[]> result = new ArrayList<String []>();
        for (int i = 0; i < mapList.size(); i++) {
            Map<String, Object> map = mapList.get(i);
            String[] array = new String[9];
            BigDecimal register = (BigDecimal) map.get("registerNum");
            array[0] = register.toString();
            BigDecimal firstUserNum = (BigDecimal) map.get("firstInvestmentUserNum");
            array[1] = firstUserNum.toString();
            BigDecimal amount = (BigDecimal) map.get("firstInvestmentAddAccount");
            array[2] = amount.toString();
            array[3] = map.get("id").toString();
            array[4] = map.get("typeId") != null ? map.get("typeId").toString() : "";
            array[5] = map.get("channelId") != null ? map.get("channelId").toString() : "";
            array[6] = map.get("source").toString();
            array[7] = map.get("typeName") != null ? map.get("typeName").toString() : "";
            array[8] = map.get("channelName") != null ? map.get("channelName").toString() : "";
            result.add(array);
        }
        return result;
    }

}
