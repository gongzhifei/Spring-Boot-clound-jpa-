package com.xxd.repository.market;


import com.xxd.dto.market.MarketChannel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author gongzhifei
 */
public interface MarketChannelRepository extends JpaRepository<MarketChannel,Integer>,JpaSpecificationExecutor<MarketChannel> {
}
