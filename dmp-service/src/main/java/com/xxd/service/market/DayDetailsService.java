package com.xxd.service.market;

import com.xxd.dto.market.condition.DayDetailsCondition;
import com.xxd.exception.ReturnMessageException;
import com.xxd.repository.market.UserTypeRepository;
import com.xxd.repository.report.SwitchTableRepository;
import com.xxd.util.BaseJdbcTemplate;
import com.xxd.util.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gongzhifei
 */
@Service
public class DayDetailsService {

    @Resource(name = "reportJdbcTemplate")
    private BaseJdbcTemplate reportJdbcTemplate;

    @Autowired
    private SwitchTableRepository switchTableRepository;

    @Autowired
    private UserTypeRepository userTypeRepository;

    public PageInfo details(DayDetailsCondition detailsCondition) {
        try {
            String tableName = switchTableRepository.findByTableName("day");
            String sql = "SELECT a.id AS id,a.type_id AS typeId,a.channel_id AS channelId," +
                    "a.source AS source,c.type_name AS typeName,d.channel_name AS channelName," +
                    "b.utm_medium AS utmMedium,b.utm_term AS utmTerm,b.utm_content AS utmContent," +
                    "b.utm_campaign AS utmCampaign,a.date AS date,a.reach AS reach,a.activate AS activate," +
                    "a.first_investment_usernum AS firstInvestmentUserNum,a.sm_num AS smNum," +
                    "a.bk_num AS bkNum,first_investment_addaccount AS firstInvestmentAddAccount," +
                    "a.first_investment_nh AS firstInvestmentNh,a.ztz_nh AS ztzNh,a.investment_addaccount AS investmentAddAccount," +
                    "a.channel AS utmSource from ((${tableName} a LEFT JOIN manager.dmp_market_web b on a.channel = b.utm_source) " +
                    "inner join manager.dmp_market_type c on a.type_id = c.id) INNER JOIN manager.dmp_market_channel d on a.channel_id = d.channel_id " +
                    "where a.type_id in(:typeIds)";
            String sqlCount = "SELECT count(*) FROM ${tableName} a force index(date) where a.type_id in (:typeIds)";
            String webSql = "select utm_source as utmSource from manager.dmp_market_web where 1=1 ";
            sql = StringUtils.replace(sql, "${tableName}", tableName);
            sqlCount = StringUtils.replace(sqlCount, "${tableName}", tableName);
            Map<String, Object> paramMap = new HashMap<String, Object>();
            Map<String, Object> paramWeb = new HashMap<String, Object>();
            List<Integer> typeIds = userTypeRepository.findByUserId(detailsCondition.getUserId());
            paramMap.put("typeIds", typeIds);
            if (StringUtils.isNotEmpty(detailsCondition.getUtmSource())) {
                sql += " and a.channel = (:utmSource)";
                sqlCount += " and a.channel = (:utmSource)";
                paramMap.put("utmSource", detailsCondition.getUtmSource());
            } else {
                if (detailsCondition.getTypeId()!=null) {
                    sql += " and a.type_id = (:typeId)";
                    sqlCount += " and a.type_id = (:typeId)";
                    paramMap.put("typeId", detailsCondition.getTypeId());
                }
                if (detailsCondition.getChannelId()!=null) {
                    sql += " and a.channel_Id = (:channelId) ";
                    sqlCount += " and a.channel_Id = (:channelId) ";
                    paramMap.put("channelId", detailsCondition.getChannelId());
                }
                if (StringUtils.isNotEmpty(detailsCondition.getSource())) {
                    sql += " and a.source = :source ";
                    sqlCount += " and a.source = :source ";
                    paramMap.put("source", detailsCondition.getSource());
                }
                if (StringUtils.isNotEmpty(detailsCondition.getUtmMedium())) {
                    sql += " and b.utm_medium like :utmMedium ";
                    webSql += " and utm_medium like :utmMedium ";
                    paramWeb.put("utmMedium", detailsCondition.getUtmMedium() + "%");
                    paramMap.put("utmMedium", detailsCondition.getUtmMedium() + "%");
                }
                if (StringUtils.isNotEmpty(detailsCondition.getUtmTerm())) {
                    sql += " and b.utm_term like :utmTerm ";
                    webSql += " and utm_term like :utmTerm ";
                    paramWeb.put("utmTerm", detailsCondition.getUtmTerm() + "%");
                    paramMap.put("utmTerm", detailsCondition.getUtmTerm() + "%");
                }
                if (StringUtils.isNotEmpty(detailsCondition.getUtmContent())) {
                    sql += " and b.utm_content like :utmContent ";
                    webSql += " and utm_content like :utmContent ";
                    paramWeb.put("utmContent", detailsCondition.getUtmContent() + "%");
                    paramMap.put("utmContent", detailsCondition.getUtmContent() + "%");
                }
                if (StringUtils.isNotEmpty(detailsCondition.getUtmCampaign())) {
                    sql += " and b.utm_campaign like :utmCampaign ";
                    webSql += " and utm_campaign like :utmCampaign ";
                    paramWeb.put("utmCampaign", detailsCondition.getUtmCampaign() + "%");
                    paramMap.put("utmCampaign", detailsCondition.getUtmCampaign() + "%");
                }
            }
            if (StringUtils.isNotEmpty(detailsCondition.getStartDate()) && StringUtils.isNotEmpty(detailsCondition.getEndDate())) {
                detailsCondition.setEndDate(detailsCondition.getEndDate() + " 23:59:59");
                sql += " and a.date BETWEEN (:startDate) and (:endDate) ";
                sqlCount += " and a.date BETWEEN (:startDate) and (:endDate) ";
                paramMap.put("startDate", detailsCondition.getStartDate());
                paramMap.put("endDate", detailsCondition.getEndDate());
            }
            if (StringUtils.isNotEmpty(detailsCondition.getUtmMedium()) || StringUtils.isNotEmpty(detailsCondition.getUtmTerm()) || StringUtils.isNotEmpty(detailsCondition.getUtmContent()) || StringUtils.isNotEmpty(detailsCondition.getUtmCampaign())) {
                List<String> utmSourceList = reportJdbcTemplate.queryForList(webSql, paramWeb, String.class);
                sqlCount += " and channel in (:utmSources)";
                paramMap.put("utmSources", utmSourceList);
            }
            sql += " order by a.date desc";
            PageInfo page = new PageInfo(detailsCondition.getPageNo(), detailsCondition.getPageSize());
            page = reportJdbcTemplate.queryForPageMysql(page, sql, sqlCount, paramMap);
            return page;
        } catch (ReturnMessageException e) {
            throw new ReturnMessageException(false,"分日明细查询失败");
        }
    }

}
