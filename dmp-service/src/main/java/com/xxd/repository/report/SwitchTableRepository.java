package com.xxd.repository.report;

import com.xxd.dto.report.SwitchTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author gongzhifei
 */
public interface SwitchTableRepository extends JpaRepository<SwitchTable,Integer> {

    /**
     * 根据别名查询表名
     * @param alias
     * @return
     */
    @Query(value = "select o.tableName from SwitchTable o where o.alias=:alias and o.isUse= 1")
    String findByTableName(@Param("alias")String alias);

}
