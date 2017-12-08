package com.xxd.service.market;

import com.xxd.dto.market.MarketType;
import com.xxd.dto.market.MarketWeb;
import com.xxd.dto.market.condition.MarketWebCondition;
import com.xxd.exception.ReturnMessageException;
import com.xxd.repository.market.MarketAppRepository;
import com.xxd.repository.market.MarketTypeRepository;
import com.xxd.repository.market.MarketWebRepository;
import com.xxd.repository.market.UserTypeRepository;
import com.xxd.util.BaseJdbcTemplate;
import com.xxd.util.DateUtil;
import com.xxd.util.GenerString;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author gongzhifei
 */
@Service
public class MarketWebService {

    @Autowired
    private MarketWebRepository marketWebRepository;

    @Autowired
    private MarketAppRepository marketAppRepository;

    @Autowired
    private MarketTypeRepository marketTypeRepository;

    @Autowired
    private UserTypeRepository userTypeRepository;

    @Resource(name = "managerJdbcTemplate")
    private BaseJdbcTemplate managerJdbcTemplate;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 分页查询推广链接
     * @param marketWebCondition
     * @return
     */
    public Page<MarketWeb> query(final MarketWebCondition marketWebCondition) throws Exception {
        Specification<MarketWeb> specification = new Specification<MarketWeb>() {
            @Override
            public Predicate toPredicate(Root<MarketWeb> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(marketWebCondition.getTypeId()!=null){
                    Predicate typeId = cb.equal(root.get("typeId"),marketWebCondition.getTypeId());
                    list.add(typeId);
                }
                if(marketWebCondition.getChannelId()!=null){
                    Predicate channelId = cb.equal(root.get("channelId"),marketWebCondition.getChannelId());
                    list.add(channelId);
                }
                if(StringUtils.isNotBlank(marketWebCondition.getSource())){
                    Predicate source = cb.equal(root.get("source"),marketWebCondition.getSource());
                    list.add(source);
                }
                if(StringUtils.isNotBlank(marketWebCondition.getUtmMedium())){
                    Predicate utmMedium = cb.equal(root.get("utmMedium"),marketWebCondition.getUtmMedium());
                    list.add(utmMedium);
                }
                if(StringUtils.isNotBlank(marketWebCondition.getUtmTerm())){
                    Predicate utmTerm = cb.equal(root.get("utmTerm"),marketWebCondition.getUtmTerm());
                    list.add(utmTerm);
                }
                if(StringUtils.isNotBlank(marketWebCondition.getUtmContent())){
                    Predicate utmContent = cb.equal(root.get("utmContent"),marketWebCondition.getUtmContent());
                    list.add(utmContent);
                }
                if(StringUtils.isNotBlank(marketWebCondition.getUtmCampaign())){
                    Predicate utmCampaign = cb.equal(root.get("utmCampaign"),marketWebCondition.getUtmCampaign());
                    list.add(utmCampaign);
                }
                if(marketWebCondition.getStartDate()!=null&&marketWebCondition.getEndDate()!=null){
                    Predicate date = null;
                    try {
                        date = cb.between(root.get("createTime"), DateUtil.formatStringToDate(marketWebCondition.getStartDate(),DateUtil.Y_M_D),DateUtil.formatStringToDate(marketWebCondition.getEndDate()+" 23:59:59",DateUtil.Y_M_D_HMS));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    list.add(date);
                }
                Predicate[] predicates = new Predicate[list.size()];
                predicates = list.toArray(predicates);
                return cb.and(predicates);
            }
        };
        Pageable pageable = new PageRequest(marketWebCondition.getPageNo()-1,marketWebCondition.getPageSize());
        return marketWebRepository.findAll(specification,pageable);
    }

    public void save(MarketWeb marketWeb) throws Exception {

        String targetUrl = marketWeb.getTargetUrl();
        if(marketWeb.getTargetUrl().startsWith("http://")){
            marketWeb.setTargetUrl("https://"+ marketWeb.getTargetUrl().substring(7));
        }
        if(!marketWeb.getTargetUrl().startsWith("https://")){
            marketWeb.setTargetUrl("https://"+targetUrl);
        }
        if(marketWeb.getTargetUrl().indexOf("xinxindai.com")==-1){
            throw new ReturnMessageException(false,"非我司域名,无法监控");
        }
        marketWeb.setSource(marketWeb.getSource().toUpperCase());
        MarketWeb market = marketWebRepository.findByUtmField(marketWeb.getSource(), marketWeb.getUtmMedium(), marketWeb.getUtmTerm(), marketWeb.getUtmContent(), marketWeb.getUtmCampaign());
        if (market != null) {
            throw new ReturnMessageException(false,"插入失败，数据重复!");
        }
        String utmSource = "";
        String str = marketWeb.getSource() + marketWeb.getUtmMedium() + marketWeb.getUtmTerm() + marketWeb.getUtmContent() + marketWeb.getUtmCampaign() + marketWeb.getChannelId() + marketWeb.getTypeId();
        do {
            utmSource = GenerString.generateRandomStr(str);
            String strUtmSource = marketAppRepository.findByUtmSource(utmSource);
            if (StringUtils.isEmpty(strUtmSource)) {
                break;
            }
            str = marketWeb.getSource() + marketWeb.getUtmMedium() + marketWeb.getUtmTerm() + marketWeb.getUtmContent() + marketWeb.getUtmCampaign() + marketWeb.getChannelId() + marketWeb.getTypeId() + DateUtil.getNow("yyyy-MM-dd HH:mm:ss");
        } while (1 == 1);

        marketWeb.setIsUse("1");
        marketWeb.setUtmSource(utmSource);
        String url = generUrl(marketWeb);
        marketWeb.setAdvertisingUrl(url);
        marketWeb.setCreateTime(DateUtil.getNow(DateUtil.Y_M_D_HMS));
        marketWeb.setUpdateTime(DateUtil.getNow(DateUtil.Y_M_D_HMS));
        marketWebRepository.save(marketWeb);
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(MarketWeb marketTypeDto) throws Exception {
        try{
            marketTypeDto.setSource(marketTypeDto.getSource().toUpperCase());
            //验证source、四个utm是否已重复
            MarketWeb market = marketWebRepository.findByUtmField(marketTypeDto.getSource(),marketTypeDto.getUtmMedium(),marketTypeDto.getUtmTerm(),marketTypeDto.getUtmContent(),marketTypeDto.getUtmCampaign());
            if (market != null&&market.getId()==(marketTypeDto.getId())) {
                throw new ReturnMessageException(false,"插入失败，数据重复!");
            }
            marketWebRepository.update(marketTypeDto.getIsUse(),marketTypeDto.getSource(),marketTypeDto.getUtmMedium(),marketTypeDto.getUtmTerm(),marketTypeDto.getUtmContent(),marketTypeDto.getUtmCampaign(),new Date(),marketTypeDto.getId());
        }catch (ReturnMessageException ex){
            throw new ReturnMessageException(false,"修改渠道链接失败!");
        }

    }


    private String generUrl(MarketWeb marketWeb) {
        MarketType marketType = marketTypeRepository.findById(marketWeb.getTypeId());
        StringBuffer url = new StringBuffer();
        try {
            String tarUrl = marketWeb.getTargetUrl();
            int index = tarUrl.indexOf("?");
            if (index != -1) {
                int index1 = tarUrl.indexOf("?", index + 1);
                if (index1 != -1) {
                    return "输入路径错误";
                }
                String[] str = tarUrl.split("\\?");
                if ("运营活动".equals(marketType.getTypeName())) {
                    url.append(str[0] + "?xxd_utm_source=" + URLEncoder.encode(marketWeb.getUtmSource(), "UTF-8") + "&" + str[1]);
                } else {
                    url.append(str[0] + "?utm_source=" + URLEncoder.encode(marketWeb.getUtmSource(), "UTF-8") + "&" + str[1]);
                }
            } else {
                if ("运营活动".equals(marketType.getTypeName())) {
                    url.append(marketWeb.getTargetUrl() + "?xxd_utm_source=");
                    url.append(URLEncoder.encode(marketWeb.getUtmSource(), "UTF-8"));
                } else {
                    url.append(marketWeb.getTargetUrl() + "?utm_source=");
                    url.append(URLEncoder.encode(marketWeb.getUtmSource(), "UTF-8"));
                }
            }

            return url.toString();
        } catch (UnsupportedEncodingException e) {
            logger.debug("{}路径URLencode出错!", url.toString(), e);
        }
        return url.toString();
    }

    public List<String> findByTypeAndChannel(Integer typeId, Integer channelId,Integer userId) {
        List<Integer> typeIds = new ArrayList<Integer>();
        List<Long> channelIds = new ArrayList<Long>();
        if(typeId!=null){
            typeIds.add(typeId);
        }else{
            typeIds = userTypeRepository.findByUserId(userId);
        }
        if(channelId!=null){
            channelIds.add(Long.valueOf(channelId));
        }else{
            String sql = "select channel_id from v_dmp_market_type_channel where type_id in (:typeIds) group by channel_id";
            Map<String,Object> param = new HashMap<String, Object>();
            param.put("typeIds",typeIds);
            channelIds = managerJdbcTemplate.queryForList(sql,param,Long.class);
        }
        return getSource(typeIds, channelIds);
    }

    public List<String> getSource(List<Integer> typeIds,List<Long> channelIds){
        try{
            Map<String,Object> param = new HashMap<String, Object>();
            param.put("typeIds",typeIds);
            param.put("channelId",channelIds);
            String sql = "select source from dmp_market_web where type_id  in (:typeIds) and channel_id in (:channelId) group by source " +
                    "union select source from dmp_market_app where type_id in(:typeIds) and channel_id in (:channelId) group by source";
            return managerJdbcTemplate.queryForList(sql,param,String.class);
        }catch (ReturnMessageException e){
            throw new ReturnMessageException(false,"查询source失败!");
        }
    }


}
