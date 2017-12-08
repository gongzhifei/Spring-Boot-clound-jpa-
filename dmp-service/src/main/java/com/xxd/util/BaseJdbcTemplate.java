package com.xxd.util;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 * @author gongzhifei
 */
public class BaseJdbcTemplate extends JdbcTemplate {

    public BaseJdbcTemplate(){
    }

    public BaseJdbcTemplate(DataSource dataSource) {
        super.setDataSource(dataSource);
        super.afterPropertiesSet();
    }


    /**
     * ORACLE count查询SQL模版
     */
    public static final String COUNT_TEMPLATE = "SELECT count(1) FROM (${sql}) XX";

    /**
     * ORACLE 分页查询SQL模版
     */
    private static final String ORACLE_PAGESQL_TEMPLATE = "SELECT * FROM (SELECT XX.*,rownum ROW_NUM FROM (${sql}) XX where rownum <= ${endNum} ) ZZ where ZZ.ROW_NUM >= ${startNum}";

    private static final String MYSQL_PAGESQL_TEMPLATE = "${sql} LIMIT ${startNum},${endNum}";

    public PageInfo queryForPageMysql(PageInfo pageInfo, String sql, String sqlCount, Map<String, Object> args) {
        try {
            if (StringUtils.isBlank(sqlCount)) {
                sqlCount = "select count(*) from "+ "("+sql+") a";
            }
            NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(this);
            int totalCount = jdbc.queryForObject(sqlCount,args,Integer.class);
            int startNum = pageInfo.getPageSize() * (pageInfo.getPageIndex() - 1);
            int endNum = pageInfo.getPageSize();
            if (endNum > totalCount) {
                endNum = totalCount;
            }
            String pageSql = MYSQL_PAGESQL_TEMPLATE;
            pageSql = StringUtils.replace(pageSql,   "${sql}", sql);
            pageSql = StringUtils.replace(pageSql, "${startNum}", String
                    .valueOf(startNum));
            pageSql = StringUtils.replace(pageSql, "${endNum}", String
                    .valueOf(endNum));
            List<Map<String, Object>> result = queryForList(pageSql, args);
            pageInfo.setResult(result);
            pageInfo.setTotalCount(totalCount);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        return pageInfo;
    }

    /**
     * list 查询
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> queryForList(String sql, RowMapper<T> rowMapper)
            throws DataAccessException {
        return query(sql, rowMapper);
    }

    /**
     * list 查询
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> queryForList(String sql, Object[] args, RowMapper<T> rowMapper)
            throws DataAccessException {
        return query(sql, args, rowMapper);
    }


    /**
     * JDBC查询所有记录
     *
     * @param <T>
     * @param rowMapper
     * @param sql
     * @param args
     * @return
     */
    public <T> List<T> queryForAll(RowMapper<T> rowMapper, String sql, Object[] args) throws DataAccessException {
        return query(sql, args, rowMapper);
    }

    public List<Map<String, Object>> queryForList(String sql, Map<String, Object> param) throws DataAccessException {
        NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(this);
        return jdbc.queryForList(sql, param);
    }

    public Map<String, Object> queryForMap(String sql, Map<String, Object> param) throws DataAccessException {
        NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(this);
        return jdbc.queryForMap(sql,param);
    }

    public <T> List<T> queryForList(String sql, Map<String, Object> param,Class<T> elementType) throws DataAccessException {
        NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(this);
        return jdbc.queryForList(sql,param,elementType);
    }

    public <T> List<T> queryForList(String sql, Map<String, Object> param,RowMapper rowMapper) throws DataAccessException {
        NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(this);
        return jdbc.query(sql,param,rowMapper);
    }


}
