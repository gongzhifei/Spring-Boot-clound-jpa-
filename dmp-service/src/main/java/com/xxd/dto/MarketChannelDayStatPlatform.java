package com.xxd.dto;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * @author gongzhifei
 */
@Entity
@Table(name = "market_channel_day_stat_platform_a")
public class MarketChannelDayStatPlatform {
    private Integer id;
    private Date date;
    private String channel;
    private Integer typeId;
    private Integer channelId;
    private String source;
    private Integer reach;
    private Integer activate;
    private Integer registeredNum;
    private Integer smNum;
    private Integer bkNum;
    private Integer firstInvestmentUsernum;
    private BigDecimal firstInvestmentAddaccount;
    private Integer investmentUsernum;
    private BigDecimal investmentAddaccount;
    private Integer firstInvestmentNh;
    private BigDecimal ztzNh;
    private Integer qtdsStUsernum;
    private BigDecimal qtdsStAmount;
    private Integer yjdjStUsernum;
    private BigDecimal yjdjStAmount;
    private Integer bbgsStUsernum;
    private BigDecimal bbgsStAmount;
    private Integer yypStUsernum;
    private BigDecimal yypStAmount;
    private Integer xybStUsernum;
    private BigDecimal xybStAmount;
    private Integer rryStUsernum;
    private BigDecimal rryStAmount;
    private Integer sbStUsernum;
    private BigDecimal sbStAmount;
    private Integer qtdsFtUsernum;
    private BigDecimal qtdsFtAmount;
    private Integer yjdjFtUsernum;
    private BigDecimal yjdjFtAmount;
    private Integer bbgsFtUsernum;
    private BigDecimal bbgsFtAmount;
    private Integer yypFtUsernum;
    private BigDecimal yypFtAmount;
    private Integer xybFtUsernum;
    private BigDecimal xybFtAmount;
    private Integer rryFtUsernum;
    private BigDecimal rryFtAmount;
    private Integer sbFtUsernum;
    private BigDecimal sbFtAmount;
    private BigDecimal qtdsStnh;
    private BigDecimal yjdjStnh;
    private BigDecimal yypStnh;
    private BigDecimal xybStnh;
    private BigDecimal sbStnh;
    private BigDecimal qtdsFtnh;
    private BigDecimal yjdjFtnh;
    private BigDecimal yypFtnh;
    private BigDecimal xybFtnh;
    private BigDecimal sbFtnh;
    private BigDecimal hqnh;
    private String type;

    @Id
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Id
    @Column(name = "date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Column(name = "channel")
    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    @Column(name = "type_id")
    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    @Column(name = "channel_id")
    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    @Column(name = "source")
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Column(name = "reach")
    public Integer getReach() {
        return reach;
    }

    public void setReach(Integer reach) {
        this.reach = reach;
    }

    @Column(name = "activate")
    public Integer getActivate() {
        return activate;
    }

    public void setActivate(Integer activate) {
        this.activate = activate;
    }

    @Column(name = "registered_num")
    public Integer getRegisteredNum() {
        return registeredNum;
    }

    public void setRegisteredNum(Integer registeredNum) {
        this.registeredNum = registeredNum;
    }

    @Column(name = "sm_num")
    public Integer getSmNum() {
        return smNum;
    }

    public void setSmNum(Integer smNum) {
        this.smNum = smNum;
    }

    @Column(name = "bk_num")
    public Integer getBkNum() {
        return bkNum;
    }

    public void setBkNum(Integer bkNum) {
        this.bkNum = bkNum;
    }

    @Column(name = "first_investment_usernum")
    public Integer getFirstInvestmentUsernum() {
        return firstInvestmentUsernum;
    }

    public void setFirstInvestmentUsernum(Integer firstInvestmentUsernum) {
        this.firstInvestmentUsernum = firstInvestmentUsernum;
    }

    @Column(name = "first_investment_addaccount")
    public BigDecimal getFirstInvestmentAddaccount() {
        return firstInvestmentAddaccount;
    }

    public void setFirstInvestmentAddaccount(BigDecimal firstInvestmentAddaccount) {
        this.firstInvestmentAddaccount = firstInvestmentAddaccount;
    }

    @Column(name = "investment_usernum")
    public Integer getInvestmentUsernum() {
        return investmentUsernum;
    }

    public void setInvestmentUsernum(Integer investmentUsernum) {
        this.investmentUsernum = investmentUsernum;
    }

    @Column(name = "investment_addaccount")
    public BigDecimal getInvestmentAddaccount() {
        return investmentAddaccount;
    }

    public void setInvestmentAddaccount(BigDecimal investmentAddaccount) {
        this.investmentAddaccount = investmentAddaccount;
    }

    @Column(name = "first_investment_nh")
    public Integer getFirstInvestmentNh() {
        return firstInvestmentNh;
    }

    public void setFirstInvestmentNh(Integer firstInvestmentNh) {
        this.firstInvestmentNh = firstInvestmentNh;
    }

    @Column(name = "ztz_nh")
    public BigDecimal getZtzNh() {
        return ztzNh;
    }

    public void setZtzNh(BigDecimal ztzNh) {
        this.ztzNh = ztzNh;
    }

    @Column(name = "qtds_st_usernum")
    public Integer getQtdsStUsernum() {
        return qtdsStUsernum;
    }

    public void setQtdsStUsernum(Integer qtdsStUsernum) {
        this.qtdsStUsernum = qtdsStUsernum;
    }

    @Column(name = "qtds_st_amount")
    public BigDecimal getQtdsStAmount() {
        return qtdsStAmount;
    }

    public void setQtdsStAmount(BigDecimal qtdsStAmount) {
        this.qtdsStAmount = qtdsStAmount;
    }

    @Column(name = "yjdj_st_usernum")
    public Integer getYjdjStUsernum() {
        return yjdjStUsernum;
    }

    public void setYjdjStUsernum(Integer yjdjStUsernum) {
        this.yjdjStUsernum = yjdjStUsernum;
    }

    @Column(name = "yjdj_st_amount")
    public BigDecimal getYjdjStAmount() {
        return yjdjStAmount;
    }

    public void setYjdjStAmount(BigDecimal yjdjStAmount) {
        this.yjdjStAmount = yjdjStAmount;
    }

    @Column(name = "bbgs_st_usernum")
    public Integer getBbgsStUsernum() {
        return bbgsStUsernum;
    }

    public void setBbgsStUsernum(Integer bbgsStUsernum) {
        this.bbgsStUsernum = bbgsStUsernum;
    }

    @Column(name = "bbgs_st_amount")
    public BigDecimal getBbgsStAmount() {
        return bbgsStAmount;
    }

    public void setBbgsStAmount(BigDecimal bbgsStAmount) {
        this.bbgsStAmount = bbgsStAmount;
    }

    @Column(name = "yyp_st_usernum")
    public Integer getYypStUsernum() {
        return yypStUsernum;
    }

    public void setYypStUsernum(Integer yypStUsernum) {
        this.yypStUsernum = yypStUsernum;
    }

    @Column(name = "yyp_st_amount")
    public BigDecimal getYypStAmount() {
        return yypStAmount;
    }

    public void setYypStAmount(BigDecimal yypStAmount) {
        this.yypStAmount = yypStAmount;
    }

    @Column(name = "xyb_st_usernum")
    public Integer getXybStUsernum() {
        return xybStUsernum;
    }

    public void setXybStUsernum(Integer xybStUsernum) {
        this.xybStUsernum = xybStUsernum;
    }

    @Column(name = "xyb_st_amount")
    public BigDecimal getXybStAmount() {
        return xybStAmount;
    }

    public void setXybStAmount(BigDecimal xybStAmount) {
        this.xybStAmount = xybStAmount;
    }

    @Column(name = "rry_st_usernum")
    public Integer getRryStUsernum() {
        return rryStUsernum;
    }

    public void setRryStUsernum(Integer rryStUsernum) {
        this.rryStUsernum = rryStUsernum;
    }

    @Column(name = "rry_st_amount")
    public BigDecimal getRryStAmount() {
        return rryStAmount;
    }

    public void setRryStAmount(BigDecimal rryStAmount) {
        this.rryStAmount = rryStAmount;
    }

    @Column(name = "sb_st_usernum")
    public Integer getSbStUsernum() {
        return sbStUsernum;
    }

    public void setSbStUsernum(Integer sbStUsernum) {
        this.sbStUsernum = sbStUsernum;
    }

    @Column(name = "sb_st_amount")
    public BigDecimal getSbStAmount() {
        return sbStAmount;
    }

    public void setSbStAmount(BigDecimal sbStAmount) {
        this.sbStAmount = sbStAmount;
    }

    @Column(name = "qtds_ft_usernum")
    public Integer getQtdsFtUsernum() {
        return qtdsFtUsernum;
    }

    public void setQtdsFtUsernum(Integer qtdsFtUsernum) {
        this.qtdsFtUsernum = qtdsFtUsernum;
    }

    @Column(name = "qtds_ft_amount")
    public BigDecimal getQtdsFtAmount() {
        return qtdsFtAmount;
    }

    public void setQtdsFtAmount(BigDecimal qtdsFtAmount) {
        this.qtdsFtAmount = qtdsFtAmount;
    }

    @Column(name = "yjdj_ft_usernum")
    public Integer getYjdjFtUsernum() {
        return yjdjFtUsernum;
    }

    public void setYjdjFtUsernum(Integer yjdjFtUsernum) {
        this.yjdjFtUsernum = yjdjFtUsernum;
    }

    @Column(name = "yjdj_ft_amount")
    public BigDecimal getYjdjFtAmount() {
        return yjdjFtAmount;
    }

    public void setYjdjFtAmount(BigDecimal yjdjFtAmount) {
        this.yjdjFtAmount = yjdjFtAmount;
    }

    @Column(name = "bbgs_ft_usernum")
    public Integer getBbgsFtUsernum() {
        return bbgsFtUsernum;
    }

    public void setBbgsFtUsernum(Integer bbgsFtUsernum) {
        this.bbgsFtUsernum = bbgsFtUsernum;
    }

    @Column(name = "bbgs_ft_amount")
    public BigDecimal getBbgsFtAmount() {
        return bbgsFtAmount;
    }

    public void setBbgsFtAmount(BigDecimal bbgsFtAmount) {
        this.bbgsFtAmount = bbgsFtAmount;
    }

    @Column(name = "yyp_ft_usernum")
    public Integer getYypFtUsernum() {
        return yypFtUsernum;
    }

    public void setYypFtUsernum(Integer yypFtUsernum) {
        this.yypFtUsernum = yypFtUsernum;
    }

    @Column(name = "yyp_ft_amount")
    public BigDecimal getYypFtAmount() {
        return yypFtAmount;
    }

    public void setYypFtAmount(BigDecimal yypFtAmount) {
        this.yypFtAmount = yypFtAmount;
    }

    @Column(name = "xyb_ft_usernum")
    public Integer getXybFtUsernum() {
        return xybFtUsernum;
    }

    public void setXybFtUsernum(Integer xybFtUsernum) {
        this.xybFtUsernum = xybFtUsernum;
    }

    @Column(name = "xyb_ft_amount")
    public BigDecimal getXybFtAmount() {
        return xybFtAmount;
    }

    public void setXybFtAmount(BigDecimal xybFtAmount) {
        this.xybFtAmount = xybFtAmount;
    }

    @Column(name = "rry_ft_usernum")
    public Integer getRryFtUsernum() {
        return rryFtUsernum;
    }

    public void setRryFtUsernum(Integer rryFtUsernum) {
        this.rryFtUsernum = rryFtUsernum;
    }

    @Column(name = "rry_ft_amount")
    public BigDecimal getRryFtAmount() {
        return rryFtAmount;
    }

    public void setRryFtAmount(BigDecimal rryFtAmount) {
        this.rryFtAmount = rryFtAmount;
    }

    @Basic
    @Column(name = "sb_ft_usernum")
    public Integer getSbFtUsernum() {
        return sbFtUsernum;
    }

    public void setSbFtUsernum(Integer sbFtUsernum) {
        this.sbFtUsernum = sbFtUsernum;
    }

    @Column(name = "sb_ft_amount")
    public BigDecimal getSbFtAmount() {
        return sbFtAmount;
    }

    public void setSbFtAmount(BigDecimal sbFtAmount) {
        this.sbFtAmount = sbFtAmount;
    }

    @Column(name = "qtds_stnh")
    public BigDecimal getQtdsStnh() {
        return qtdsStnh;
    }

    public void setQtdsStnh(BigDecimal qtdsStnh) {
        this.qtdsStnh = qtdsStnh;
    }

    @Column(name = "yjdj_stnh")
    public BigDecimal getYjdjStnh() {
        return yjdjStnh;
    }

    public void setYjdjStnh(BigDecimal yjdjStnh) {
        this.yjdjStnh = yjdjStnh;
    }

    @Column(name = "yyp_stnh")
    public BigDecimal getYypStnh() {
        return yypStnh;
    }

    public void setYypStnh(BigDecimal yypStnh) {
        this.yypStnh = yypStnh;
    }

    @Column(name = "xyb_stnh")
    public BigDecimal getXybStnh() {
        return xybStnh;
    }

    public void setXybStnh(BigDecimal xybStnh) {
        this.xybStnh = xybStnh;
    }

    @Column(name = "sb_stnh")
    public BigDecimal getSbStnh() {
        return sbStnh;
    }

    public void setSbStnh(BigDecimal sbStnh) {
        this.sbStnh = sbStnh;
    }

    @Column(name = "qtds_ftnh")
    public BigDecimal getQtdsFtnh() {
        return qtdsFtnh;
    }

    public void setQtdsFtnh(BigDecimal qtdsFtnh) {
        this.qtdsFtnh = qtdsFtnh;
    }

    @Column(name = "yjdj_ftnh")
    public BigDecimal getYjdjFtnh() {
        return yjdjFtnh;
    }

    public void setYjdjFtnh(BigDecimal yjdjFtnh) {
        this.yjdjFtnh = yjdjFtnh;
    }

    @Column(name = "yyp_ftnh")
    public BigDecimal getYypFtnh() {
        return yypFtnh;
    }

    public void setYypFtnh(BigDecimal yypFtnh) {
        this.yypFtnh = yypFtnh;
    }

    @Column(name = "xyb_ftnh")
    public BigDecimal getXybFtnh() {
        return xybFtnh;
    }

    public void setXybFtnh(BigDecimal xybFtnh) {
        this.xybFtnh = xybFtnh;
    }

    @Column(name = "sb_ftnh")
    public BigDecimal getSbFtnh() {
        return sbFtnh;
    }

    public void setSbFtnh(BigDecimal sbFtnh) {
        this.sbFtnh = sbFtnh;
    }

    @Column(name = "hqnh")
    public BigDecimal getHqnh() {
        return hqnh;
    }

    public void setHqnh(BigDecimal hqnh) {
        this.hqnh = hqnh;
    }

    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
