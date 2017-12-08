package com.xxd.service.market;

import com.xxd.dto.market.MarketChannel;
import com.xxd.dto.market.condition.ChannelNameCondition;
import com.xxd.repository.market.MarketChannelRepository;
import com.xxd.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * @author gongzhifei
 */
@Service
public class MarketChannelService {

    @Autowired
    MarketChannelRepository marketChannelRepository;

    public Page<MarketChannel> query(ChannelNameCondition channelNameCondition) {
        Specification<MarketChannel> specification = new Specification<MarketChannel>() {
            @Override
            public Predicate toPredicate(Root<MarketChannel> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                Predicate predicate = null;
                if (StringUtils.isNotBlank(channelNameCondition.getChannelName())) {
                    predicate = cb.like(root.<String>get("channelName"), "%"+channelNameCondition.getChannelName()+"%");
                }
                return predicate;
            }
        };
        Pageable pageable = new PageRequest(channelNameCondition.getPageNo()-1, channelNameCondition.getPageSize());
        return marketChannelRepository.findAll(specification, pageable);
    }

    public void save(String channelName, String username) throws Exception {
        MarketChannel channel = new MarketChannel();
        channel.setChannelName(channelName);
        channel.setFounder(username);
        channel.setModifier(username);
        channel.setCreateTime(DateUtil.getNow(DateUtil.Y_M_D_HMS));
        channel.setUpdateTime(DateUtil.getNow(DateUtil.Y_M_D_HMS));
        marketChannelRepository.save(channel);
    }

}
