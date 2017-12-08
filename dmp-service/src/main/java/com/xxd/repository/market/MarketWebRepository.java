package com.xxd.repository.market;

import com.xxd.dto.market.MarketWeb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

/**
 * @author gongzhifei
 */
public interface MarketWebRepository extends JpaRepository<MarketWeb, Integer>, JpaSpecificationExecutor<MarketWeb> {

    /**
     * 查询四个Utm字段和source是否存在
     * @param source
     * @param utmMedium
     * @param utmTerm
     * @param utmContent
     * @param utmCampaign
     * @return
     */
    @Query(nativeQuery = true, value = "select * from dmp_market_web where source =:source and utm_medium =:utmMedium and utm_term=:utmTerm and utm_content=:utmContent and utm_campaign=:utmCampaign")
    MarketWeb findByUtmField(@Param("source") String source, @Param("utmMedium") String utmMedium, @Param("utmTerm") String utmTerm, @Param("utmContent") String utmContent, @Param("utmCampaign") String utmCampaign);

    MarketWeb findById(Integer id);

    /**
     * 修改推广链接
     * @param isUse
     * @param source
     * @param utmMedium
     * @param utmTerm
     * @param utmContent
     * @param utmCampaign
     * @param updateTime
     * @param id
     */
    @Modifying
    @Query(nativeQuery = true,value = "update dmp_market_web set is_use=?1,source=?2,utm_medium=?3,utm_term=?4,utm_content=?5,utm_campaign=?6,update_time=?7 where id=?8")
    void update(String isUse, String source, String utmMedium, String utmTerm, String utmContent, String utmCampaign, Date updateTime,Integer id);

}
