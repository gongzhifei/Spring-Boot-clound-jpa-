package com.xxd.repository.market;

import com.xxd.dto.market.MarketDateBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author gongzhifei
 */
public interface MarketDateBaseRepository extends JpaRepository<MarketDateBase,String> {

    @Query(nativeQuery = true,value = "select :condition from market_date_base a where date between :startDate and :endDate :sort")
    List<String> findByDateBetween(@Param("condition") String condition, @Param("startDate")String startDate, @Param("endDate") String Date, @Param("sort") String sort);

    /**
     * 根据时间类型查找日期
     * @param condition
     * @param date
     * @return
     */
    @Query(nativeQuery = true,value = "select date from market_date_base where :condition = :date ")
    List<String> findByDate(@Param("condition")String condition,@Param("date")String date);

}
