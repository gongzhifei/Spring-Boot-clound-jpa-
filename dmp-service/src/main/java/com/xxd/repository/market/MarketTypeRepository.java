package com.xxd.repository.market;

import com.xxd.dto.market.MarketType;
import org.springframework.data.repository.CrudRepository;

/**
 * @author gongzhifei
 */
public interface MarketTypeRepository extends CrudRepository<MarketType,Integer> {

    /**
     * 根据Id查询MakretType
     * @param id
     * @return
     */
    MarketType findById(Integer id);



}
