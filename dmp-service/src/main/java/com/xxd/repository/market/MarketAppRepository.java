package com.xxd.repository.market;

import com.xxd.dto.market.MarketApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

/**
 * @author gongzhifei
 */
public interface MarketAppRepository extends JpaRepository<MarketApp,Integer>,JpaSpecificationExecutor<MarketApp> {
    /**
     * 根据UtmSouce查找MarketAppEntity
     * @param utmSource
     * @return 返回一个MarketAppEntity
     */
//    MarketAppEntity findByUtmSource(String utmSource);

    /**
     * 根据包名查询
     * @param packageName
     * @return
     */
    MarketApp findByPackageName(String packageName);

    /**
     * 查找 MarketWeb和MarketApp中utmSource是否存在
     * @param utmSource
     * @return
     */
    @Query(nativeQuery = true, value = "select utm_source from dmp_market_web a where a.utm_source = ?1 UNION " +
            "select utm_source from dmp_market_app b where b.utm_source = ?1")
    String findByUtmSource(String utmSource);

    /**
     * 修改应用包
     * @param source
     * @param updateTime
     * @param isUse
     * @param id
     */
    @Modifying
    @Query(nativeQuery = true, value = "update dmp_market_app set source=?1," +
            "update_time=?2,is_use=?3 where id=?4")
    void update(String source, Date updateTime, String isUse, Integer id);


}
