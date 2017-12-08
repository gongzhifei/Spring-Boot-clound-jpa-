package com.xxd.repository;

import com.xxd.dto.user.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author gongzhifei
 */
public interface MenuRepository extends JpaRepository<Menu,Integer> {

    /***
     * 查询url不为空的菜单
     * @return
     */
    @Query(value = "select o FROM Menu o where o.url <> '' ")
    List<Menu> findAllByUrlIsNotNull();

    /**
     * 根据ID查询菜单
     * @param ids
     * @return
     */
    List<Menu> findByIdIn(List<Integer> ids);

}
