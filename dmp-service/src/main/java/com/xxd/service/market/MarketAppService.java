package com.xxd.service.market;

import com.xxd.dto.market.MarketType;
import com.xxd.dto.market.MarketApp;
import com.xxd.dto.market.condition.MarketAppCondition;
import com.xxd.exception.ReturnMessageException;
import com.xxd.repository.market.MarketAppRepository;
import com.xxd.repository.market.MarketTypeRepository;
import com.xxd.util.DateUtil;
import com.xxd.util.GenerString;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author gongzhifei
 */
@Service
public class MarketAppService {

    @Autowired
    private MarketAppRepository marketAppRepository;

    @Autowired
    private MarketTypeRepository marketTypeRepository;

    /**
     * 分页查询所有应用包信息
     * @param marketAppCondition
     * @return
     */
    public Page<MarketApp> query(final MarketAppCondition marketAppCondition){
        /**
         * root 查询的数据类型
         * criteriaQuery 添加查询条件
         * criteriaBuilder 构建Predicate
         */
        Specification<MarketApp> marketAppEntitySpecification = new Specification<MarketApp>() {
            @Override
            public Predicate toPredicate(Root<MarketApp> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(marketAppCondition.getTypeId()!=null){
                    Predicate typeId = cb.equal(root.get("typeId"),marketAppCondition.getTypeId());
                    list.add(typeId);
                }
                if(marketAppCondition.getChannelId()!=null){
                    Predicate channelId = cb.equal(root.get("channelId"),marketAppCondition.getChannelId());
                    list.add(channelId);
                }
                if(StringUtils.isNotBlank(marketAppCondition.getSource())){
                    Predicate source = cb.equal(root.get("source"),marketAppCondition.getSource());
                    list.add(source);
                }
                if(StringUtils.isNotBlank(marketAppCondition.getPackageName())){
                    Predicate packageName = cb.like(root.<String>get("packageName"),"%"+marketAppCondition.getPackageName()+"%");
                    list.add(packageName);
                }
                if(StringUtils.isNotBlank(marketAppCondition.getUtmSource())){
                    Predicate utmSource = cb.equal(root.get("utmSource"),marketAppCondition.getUtmSource());
                    list.add(utmSource);
                }
                if (StringUtils.isNotBlank(marketAppCondition.getStartDate()) && StringUtils.isNotBlank(marketAppCondition.getEndtDate())) {
                    Predicate date = null;
                    try {
                        date = cb.between(root.get("createTime"), DateUtil.formatStringToDate(marketAppCondition.getStartDate(),"yyyy-MM-dd"),DateUtil.formatStringToDate(marketAppCondition.getEndtDate()+" 23:59:59","yyyy-MM-dd HH:mm:ss"));
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
        Pageable p = new PageRequest(marketAppCondition.getPageNo()-1,marketAppCondition.getPageSize());
        Page<MarketApp> page = marketAppRepository.findAll(marketAppEntitySpecification,p);
        return page;
    }

    /**
     * 保存应用包信息
     * @param marketApp
     * @return
     * @throws ReturnMessageException
     */
    public MarketApp save(MarketApp marketApp) throws Exception {
         marketApp.setSource(marketApp.getSource().toUpperCase());
        MarketApp market = marketAppRepository.findByPackageName(marketApp.getPackageName());
        if (market != null) {
            throw new ReturnMessageException(false,"包名重复请重新添加");
        }
        String str = String.valueOf(marketApp.getTypeId()) + String.valueOf(marketApp.getChannelId()) + marketApp.getSource() + marketApp.getPackageName();
        String utmSource = "";
        do {
            utmSource = GenerString.generateRandomStr(str);
            String app = marketAppRepository.findByUtmSource(utmSource);
            if (StringUtils.isEmpty(app)) {
                break;
            }
            try {
                str = String.valueOf(marketApp.getTypeId()) + String.valueOf(marketApp.getChannelId()) + marketApp.getSource() + marketApp.getPackageName() + DateUtil.getNow("yyyy-MM-dd HH:mm:ss");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (1 == 1);

        marketApp.setCreateTime(DateUtil.getNow("yyyy-MM-dd HH:mm:ss"));
        marketApp.setUpdateTime(DateUtil.getNow("yyyy-MM-dd HH:mm:ss"));
        marketApp.setIsUse("1");
        MarketType marketType = marketTypeRepository.findById(marketApp.getTypeId());
        marketApp.setUtmSource(utmSource);

        if ("下载跳转".equals(marketType.getTypeName())) {
            marketApp.setDownloadUrl("http://download-cdn.xinxindai.com/" + marketApp.getPackageName());
        } else {
            marketApp.setDownloadUrl(marketApp.getPackageName());
        }
        return marketAppRepository.save(marketApp);
    }

    /**
     * 应用包修改
     * @param id
     * @param source
     * @param isUse
     */
    @Transactional(rollbackFor = Exception.class)
    public void update(Integer id,String source,String isUse) throws ReturnMessageException{
        marketAppRepository.update(source.toUpperCase(),new Date(),isUse,id);
    }

}
