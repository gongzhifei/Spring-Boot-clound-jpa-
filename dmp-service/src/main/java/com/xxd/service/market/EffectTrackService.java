package com.xxd.service.market;

import com.xxd.dto.market.condition.EffectTrackCondition;
import com.xxd.dto.user.UserInfo;
import com.xxd.exception.ReturnMessageException;
import com.xxd.properties.RestUrlProperties;
import com.xxd.repository.market.UserTypeRepository;
import com.xxd.repository.report.SwitchTableRepository;
import com.xxd.util.BaseJdbcTemplate;
import com.xxd.util.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author gongzhifei
 */
@Component
public class EffectTrackService {

    @Resource(name = "reportJdbcTemplate")
    private BaseJdbcTemplate reportJdbcTemplate;

    @Autowired
    private MarketDateBaseService marketDateBaseService;

    @Autowired
    private SwitchTableRepository switchTableRepository;

    @Autowired
    private MarketWebService marketWebService;

    @Autowired
    private UserTypeRepository userTypeRepository;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public PageInfo userList(EffectTrackCondition effectTrackCondition) {
        try {
            //type 1 天 2 周 3 月
            int flag = effectTrackCondition.getFlag();
            String tableAlias = getTableAlias(flag);
            Map<String, Object> paramMap = new HashMap<String,Object>();
            String tableName = switchTableRepository.findByTableName(tableAlias);
            List<Integer> userTypeIds = userTypeRepository.findByUserId(effectTrackCondition.getUserId());
            String sql = "select ${date} as date,sum(a.registered_num) as register,sum(a.first_investment_usernum) as firstInvestmentUserNum," +
                    "sum(a.first_investment_addaccount) as firstInvestmentAddAccount,sum(a.first_investment_nh) as firstInvestmentNh," +
                    "sum(a.investment_addaccount) as investmentAddAccount,'false' as activation,'false' as investNum," +
                    "sum(a.ztz_nh) as ztzNh,sum(a.sm_num) as smNum,sum(a.bk_num) as bkNum from " + tableName + " a where a.type_id in (:userTypeIds) ";
            if (effectTrackCondition.getTypeId() != null) {
                sql += " and type_id =:typeId";
            }
            if (effectTrackCondition.getChannelId() != null) {
                sql += " and a.channel_id = :channelId";
            }
            if (StringUtils.isBlank(effectTrackCondition.getSource())) {
                List<String> source = marketWebService.findByTypeAndChannel(effectTrackCondition.getTypeId(), effectTrackCondition.getChannelId(),effectTrackCondition.getUserId());
                paramMap.put("source", source);
            } else {
                paramMap.put("source", Arrays.asList(effectTrackCondition.getSource()));
            }
            sql += " and a.source in (:source)";
            List<String> dateList = new ArrayList<String>();
            if (effectTrackCondition.getFlag() == 1) {
                if (effectTrackCondition.getStartDate() != null && effectTrackCondition.getEndDate() != null) {
                    sql += " and a.date between :startDate and :endDate";
                }
                sql = StringUtils.replace(sql, "${date}", "a.date");
                sql += " group by a.date desc";
            } else if (effectTrackCondition.getFlag() == 2) {
                if (StringUtils.isNotBlank(effectTrackCondition.getStartDate()) && StringUtils.isNotBlank(effectTrackCondition.getEndDate())) {
                    dateList = marketDateBaseService.findByDate(effectTrackCondition.getFlag(), effectTrackCondition.getStartDate(), effectTrackCondition.getEndDate());
                    sql += " and a.week between :startDate and :endDate";
                    effectTrackCondition.setStartDate(dateList.get(0));
                    effectTrackCondition.setEndDate(dateList.size() == 1 ? dateList.get(0) : dateList.get(dateList.size() - 1));
                }
                sql = StringUtils.replace(sql, "${date}", "a.week");
                sql += " group by a.week desc";
            } else if (effectTrackCondition.getFlag() == 3) {
                if (StringUtils.isNotBlank(effectTrackCondition.getStartDate()) && StringUtils.isNotBlank(effectTrackCondition.getEndDate())) {
                    dateList = marketDateBaseService.findByDate(effectTrackCondition.getFlag(), effectTrackCondition.getStartDate(), effectTrackCondition.getEndDate());
                    sql += " and a.month between :startDate and :endDate";
                    effectTrackCondition.setStartDate(dateList.get(0));
                    effectTrackCondition.setEndDate(dateList.size() == 1 ? dateList.get(0) : dateList.get(dateList.size() - 1));
                }
                sql = StringUtils.replace(sql, "${date}", "a.month");
                sql += " group by a.month desc";
            }
            paramMap.put("userTypeIds", userTypeIds);
            paramMap.put("typeId", effectTrackCondition.getTypeId());
            paramMap.put("channelId", effectTrackCondition.getChannelId());
            paramMap.put("startDate", effectTrackCondition.getStartDate());
            paramMap.put("endDate", effectTrackCondition.getEndDate() + " 23:59:59");
            PageInfo page = new PageInfo(effectTrackCondition.getPageNo(), effectTrackCondition.getPageSize());
            page = reportJdbcTemplate.queryForPageMysql(page, sql, null, paramMap);
            return page;
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * 查询 投资、年化、入金堆积图
     *
     * @param effectTrackCondition
     * @return
     */
    public Map<String, Object> stacked(EffectTrackCondition effectTrackCondition) {
        try {
            int flag = effectTrackCondition.getFlag();
            String tableAlias = getTableAlias(flag);
            List<Integer> userTypeIds = userTypeRepository.findByUserId(effectTrackCondition.getUserId());
            String tableName = switchTableRepository.findByTableName(tableAlias);
            String sql = "select ${field} from " + tableName + " a where type_id in (:userTypeIds)";
            String firstSql = "";
            String reSql = "";
            String firstField = "";
            String reField = "";
            //type 1:入金 2：年化 3：投资
            if (effectTrackCondition.getType() == 1) {
                firstField = "${date} as date,SUM((qtds_st_amount+yjdj_st_amount+bbgs_st_amount+yyp_st_amount+xyb_st_amount+rry_st_amount+sb_st_amount)) as initInvestAmount";
                reField = "${date} as date,SUM((qtds_ft_amount+yjdj_ft_amount+bbgs_ft_amount+yyp_ft_amount+xyb_ft_amount+rry_ft_amount+sb_ft_amount)) as repeatInvestAmount";
            } else if (effectTrackCondition.getType() == 2) {
                firstField = "${date} as date,SUM((qtds_stnh+yjdj_stnh+yyp_stnh+xyb_stnh+sb_stnh)) as initInvestAnnualized";
                reField = "${date} as date,SUM((qtds_ftnh+yjdj_ftnh+yyp_ftnh+xyb_ftnh+sb_ftnh)) as reInvestAnnualized";
            } else if (effectTrackCondition.getType() == 3) {
                firstField = "${date} as date,SUM((qtds_st_usernum+yjdj_st_usernum+bbgs_st_usernum+yyp_st_usernum+xyb_st_usernum+rry_st_usernum+sb_st_usernum)) as initInvestNumber";
                reField = "${date} as date,SUM((qtds_ft_usernum+yjdj_ft_usernum+bbgs_ft_usernum+yyp_ft_usernum+xyb_ft_usernum+rry_ft_usernum+sb_ft_usernum)) as reInvestNumber";
            }
            if (effectTrackCondition.getTypeId() != null) {
                sql += " and type_id =:typeId";
            }
            if (effectTrackCondition.getChannelId() != null) {
                sql += " and a.channel_id = :channelId";
            }
            Map<String, Object> paramMap = new HashMap<String,Object>();
            if (StringUtils.isBlank(effectTrackCondition.getSource())) {
                List<String> source = marketWebService.findByTypeAndChannel(effectTrackCondition.getTypeId(), effectTrackCondition.getChannelId(),effectTrackCondition.getUserId());
                paramMap.put("source", source);
            } else {
                paramMap.put("source", Arrays.asList(effectTrackCondition.getSource().split(",")));
            }
            sql += " and a.source in (:source)";
            List<String> dateList = new ArrayList<String>();
            if (StringUtils.isNotBlank(effectTrackCondition.getStartDate()) && StringUtils.isNotBlank(effectTrackCondition.getEndDate())) {
                if ((flag == 1)) {
                    sql += " and a.date between :startDate and :endDate";
                    firstField = StringUtils.replace(firstField, "${date}", "date");
                    reField = StringUtils.replace(reField, "${date}", "date");
                } else if (flag == 2) {
                    dateList = marketDateBaseService.findByDate(effectTrackCondition.getFlag(), effectTrackCondition.getStartDate(), effectTrackCondition.getEndDate());
                    sql += " and a.week between :startDate and :endDate";
                    effectTrackCondition.setStartDate(dateList.get(0));
                    effectTrackCondition.setEndDate(dateList.size() == 1 ? dateList.get(0) : dateList.get(dateList.size() - 1));
                    firstField = StringUtils.replace(firstField, "${date}", "week");
                    reField = StringUtils.replace(reField, "${date}", "week");
                } else if (flag == 3) {
                    dateList = marketDateBaseService.findByDate(effectTrackCondition.getFlag(), effectTrackCondition.getStartDate(), effectTrackCondition.getEndDate());
                    sql += " and a.month between :startDate and :endDate";
                    effectTrackCondition.setStartDate(dateList.get(0));
                    effectTrackCondition.setEndDate(dateList.size() == 1 ? dateList.get(0) : dateList.get(dateList.size() - 1));
                    firstField = StringUtils.replace(firstField, "${date}", "month");
                    reField = StringUtils.replace(reField, "${date}", "month");
                }
            }
            sql += " GROUP BY date ";
            firstSql = StringUtils.replace(sql, "${field}", firstField);
            reSql = StringUtils.replace(sql, "${field}", reField);
            paramMap.put("typeId", effectTrackCondition.getTypeId());
            paramMap.put("channelId", effectTrackCondition.getChannelId());
            paramMap.put("userTypeIds", userTypeIds);
            paramMap.put("startDate", effectTrackCondition.getStartDate());
            paramMap.put("endDate", effectTrackCondition.getEndDate() + " 23:59:59");
            List<Map<String, Object>> firstList = reportJdbcTemplate.queryForList(firstSql, paramMap);
            List<Map<String, Object>> repeatList = reportJdbcTemplate.queryForList(reSql, paramMap);
            Map<String, Object> map = new HashMap<String,Object>();
            map.put("InitInvestment", firstList);
            map.put("repeatInvestment", repeatList);
            return map;
        } catch (ReturnMessageException ex) {
            throw new ReturnMessageException(false, "获取投资、年化、入金环形图数据失败!");
        }
    }

    /**
     * 效果跟踪环形图
     *
     * @param effectTrackCondition
     * @return
     */
    public Map<String, Object> ringChart(EffectTrackCondition effectTrackCondition) {
        try {
            int flag = effectTrackCondition.getFlag();
            String tableAlias = getTableAlias(flag);
            String tableName = switchTableRepository.findByTableName(tableAlias);
            List<Integer> userTypeIds = userTypeRepository.findByUserId(effectTrackCondition.getUserId());
            String sql = "select ${field} from " + tableName + " a where type_id in (:userTypeIds)";
            if (effectTrackCondition.getTypeId() != null) {
                sql += " and type_id =:typeId";
            }
            if (effectTrackCondition.getChannelId() != null) {
                sql += " and a.channel_id =:channelId";
            }
            Map<String, Object> paramMap = new HashMap<String,Object>();
            if (StringUtils.isBlank(effectTrackCondition.getSource())) {
                List<String> source = marketWebService.findByTypeAndChannel(effectTrackCondition.getTypeId(), effectTrackCondition.getChannelId(),effectTrackCondition.getUserId());
                paramMap.put("source", source);
            } else {
                paramMap.put("source", Arrays.asList(effectTrackCondition.getSource().split(",")));
            }
            sql += " and a.source in (:source)";
            List<String> dateList = new ArrayList<String>();
            if (flag == 1) {
                sql += " and a.date =:date";
            } else if (flag == 2) {
                sql += " and a.week =:date";
            } else if (flag == 3) {
                sql += " and a.month =:date";
            }
            String[] proName = null;
            String firstSql = "";
            String reSql = "";
            String firstField = "";
            String reField = "";
            //type 1:入金 2：年化 3：投资
            if (effectTrackCondition.getType() == 1) {
                firstField = "SUM((a.qtds_st_amount+a.yjdj_st_amount+a.bbgs_st_amount+a.yyp_st_amount+a.xyb_st_amount+a.rry_st_amount+a.sb_st_amount)) as totleMoney," +
                        "SUM(a.qtds_st_amount) as qtdsSTAmount,SUM(a.yjdj_st_amount) as yjdjSTAmount,SUM(a.bbgs_st_amount) as bbgsSTAmount,SUM(a.yyp_st_amount) as yypSTAmount," +
                        " SUM(a.xyb_st_amount) as xybSTAmount,SUM(a.rry_st_amount) as rrySTAmount,SUM(a.sb_st_amount) as sbSTAmount";
                reField = "SUM((a.qtds_ft_amount+a.yjdj_ft_amount+a.bbgs_ft_amount+a.yyp_ft_amount+a.xyb_ft_amount+a.rry_ft_amount+a.sb_ft_amount)) as totleMoney," +
                        "SUM(a.qtds_ft_amount) as qtdsFTAmount,SUM(a.yjdj_ft_amount) as yjdjFTAmount,SUM(a.bbgs_ft_amount) as bbgsFTAmount," +
                        "SUM(a.yyp_ft_amount) as yypFTAmount,SUM(a.xyb_ft_amount) as xybFTAmount,SUM(a.rry_ft_amount) as rryFTAmount,SUM(a.sb_ft_amount) as sbFTAmount";
                proName = new String[]{"七天大胜", "月进斗金", "步步高升", "月月派", "新元宝", "日日盈", "散标直投"};
            } else if (effectTrackCondition.getType() == 2) {
                firstField = "SUM((a.qtds_stnh+a.yjdj_stnh+a.yyp_stnh+a.xyb_stnh+a.sb_stnh)) as totleMoney ,SUM(a.qtds_stnh) as qtdsSTNh," +
                        "SUM(a.yjdj_stnh) as yjdjSTNh,SUM(a.yyp_stnh) as yypSTNh,SUM(a.xyb_stnh) as xybSTNh,SUM(a.sb_stnh) as sbSTNh";
                reField = "SUM((a.qtds_ftnh+a.yjdj_ftnh+a.yyp_ftnh+a.xyb_ftnh+a.sb_ftnh)) as totleMoney, SUM(a.qtds_ftnh) as qtdsFTNh,SUM(a.yjdj_ftnh) as " +
                        "yjdjFTNh,SUM(a.yyp_ftnh) as yypFTNh,SUM(a.xyb_ftnh) as xybFTNh,SUM(a.sb_ftnh) as sbftNh";
                proName = new String[]{"七天大胜", "月进斗金", "月月派", "新元宝", "散标直投"};
            } else if (effectTrackCondition.getType() == 3) {
                firstField = "SUM((a.qtds_st_usernum+a.yjdj_st_usernum+a.bbgs_st_usernum+a.yyp_st_usernum+a.xyb_st_usernum+a.rry_st_usernum+a.sb_st_usernum)) as totleMoney," +
                        "SUM(a.qtds_st_usernum) as qtdsSTUserNum,SUM(a.yjdj_st_usernum) as yjdjSTUserNum,SUM(a.bbgs_st_usernum) as bbgsSTUserNum,SUM(a.yyp_st_usernum) as yypSTUserNum," +
                        "SUM(a.xyb_st_usernum) as xybSTUserNum,SUM(a.rry_st_usernum) as rrySTUserNum,SUM(a.sb_st_usernum) as sbSTUserNum";
                reField = "SUM((a.qtds_ft_usernum+a.yjdj_ft_usernum+a.bbgs_ft_usernum+a.yyp_ft_usernum+a.xyb_ft_usernum+a.rry_ft_usernum+a.sb_ft_usernum)) as totleMoney," +
                        "SUM(a.qtds_ft_usernum) as qtdsFTUserNum,SUM(a.yjdj_ft_usernum) as yjdjFTUserNum,SUM(a.bbgs_ft_usernum) as bbgsFTUserNum," +
                        "SUM(a.yyp_ft_usernum) as yypFTUserNum,SUM(a.xyb_ft_usernum) as xybFTUserNum,SUM(a.rry_ft_usernum) as rryFTUserNum, SUM(a.sb_ft_usernum) as sbFTUserNum";
                proName = new String[]{"七天大胜", "月进斗金", "步步高升", "月月派", "新元宝", "日日盈", "散标直投"};
            }
            firstSql = StringUtils.replace(sql, "${field}", firstField);
            reSql = StringUtils.replace(sql, "${field}", reField);
            paramMap.put("typeId", effectTrackCondition.getTypeId());
            paramMap.put("channelId", effectTrackCondition.getChannelId());
            paramMap.put("userTypeIds", userTypeIds);
            paramMap.put("date", effectTrackCondition.getDate());
            List<Map<String, Object>> firs = reportJdbcTemplate.queryForList(firstSql, paramMap);
            List<Map<String, Object>> re = reportJdbcTemplate.queryForList(reSql, paramMap);

            Collection<Object> firstCollection = firs.get(0).values();
            List<Object> firstObject = new ArrayList<Object>(firstCollection);

            Collection<Object> repeatCollection = re.get(0).values();
            List<Object> reObject = new ArrayList<Object>(repeatCollection);
            List<Map<String, Object>> firstList = assemblyData(proName, firstObject);
            List<Map<String, Object>> repeatList = assemblyData(proName, reObject);
            Map<String, Object> firstMap = toMap(firstList, firstObject.get(0));
            Map<String, Object> repeatMap = toMap(repeatList, reObject.get(0));
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("date", effectTrackCondition.getDate());
            result.put("firstTou", firstMap);
            result.put("repeatTou", repeatMap);

            return result;
        } catch (Exception e) {
            throw new ReturnMessageException(false,"查询环形图失败!");
        }
    }


    /**
     * 返回表别名
     *
     * @param type 1 天 2 周 3 月
     * @return
     */
    private String getTableAlias(int type) {
        String tableAlias = "day_union";
        if (type == 1) {
            tableAlias = "day_union";
        } else if (type == 2) {
            tableAlias = "week_union";
        } else if (type == 3) {
            tableAlias = "month_union";
        }
        return tableAlias;
    }

    public List<Map<String, Object>> assemblyData(String[] proName, List<Object> firstList) {
        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        for (int i = 0; i < proName.length; i++) {
            Map<String, Object> initMap = new HashMap<String, Object>();
            initMap.put("name", proName[i]);
            initMap.put("value", firstList.get(i + 1));
            result.add(initMap);
        }
        return result;
    }

    private Map<String, Object> toMap(Object o1, Object o2) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", o1);
        map.put("totleMoney", o2);
        return map;
    }

}
