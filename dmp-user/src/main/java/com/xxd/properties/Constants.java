package com.xxd.properties;

/**
 * 交互路径
 *
 * @author gongzhifei
 */
public class Constants {

    public static final String host = "http://dmp-service";

    /**
     * App包查询
     */
    public static final String APP_PACKAGE_QUERY = host + "/financial/market/package/query";

    /**
     * App包创建
     */
    public static final String APP_PACKAGE_CREATE = host + "/financial/market/package";

    /**
     * App包修改
     */
    public static final String APP_PACKAGE_UPDATE = host + "/financial/market/package/update";

    /**
     * 渠道管理查询
     */
    public static final String CHANNEL_MANAGE_QUERY = host + "/financial/market/channel";

    /**
     * 渠道管理新增
     */
    public static final String CHANNEL_MANAGE_CREATE = host + "/financial/market/channel/{channelName}?username={username}";

    /**
     * 效果对比列表数据
     */
    public static final String EFFECT_COMPARE_LIST = host + "/financial/market/effectCompare/compareList";

    /**
     * 效果对比散点图
     */
    public static final String EFFECT_COMPARE_SCATTER = host + "/financial/market/effectCompare/scatter";

    /**
     * 效果跟踪用户列表
     */
    public static final String EFFECT_TRACT_USERLIST = host + "/financial/market/effectTrack/userList";

    /**
     * 效果跟踪堆积图数据
     */
    public static final String EFFECT_TRACT_STACKEDGRAPH = host + "/financial/market/effectTrack/stackedGraph";

    /**
     * 效果跟踪环形图
     */
    public static final String EFFECT_TRACT_RINGCHART = host + "/financial/market/effectTrack/ringChart";

    /**
     * 推广链接查询
     */
    public static final String MARKET_LINKS_QUERY = host + "/financial/market/links/query";

    /**
     * 创建推广链接
     */
    public static final String MARKET_LINKS_CREATE = host + "/financial/market/links/create";

    /**
     * 推广链接修改
     */
    public static final String MARKET_LINKS_UPDATE = host + "/financial/market/links/update";

    /**
     * 获取投资人数或抵达、激活
     */
    public static final String REACH_OR_INVEST = host + "/financial/market/reachOrInvest";

    /**
     * 获取typeId
     */
    public static final String MARKET_TYPE_ID_QUERY = host + "/financial/market/type/{flag}?userId={userId}";

    /**
     * 报表渠道下拉框数据
     */
    public static final String MARKET_CHANNEL_ID_QUERY = host + "/financial/market/type/channel/{typeId}?userId={userId}";

    /**
     * 管理界面渠道下拉框数据
     */
    public static final String MARKET_MANAGE_CHANNEL_ID_QUERY = host + "/financial/market/channel/queryName";

    /**
     * 获取source来源
     */
    public static final String MARKET_SOURCE_QUERY = host + "/financial/market/type/Source/{typeId}/{channelId}?userId={userId}";

}
