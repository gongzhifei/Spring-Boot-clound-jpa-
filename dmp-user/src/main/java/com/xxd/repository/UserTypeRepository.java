package com.xxd.repository;

import com.xxd.dto.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author gongzhifei
 */
public interface UserTypeRepository extends JpaRepository<UserType,Integer> {

    /**
     * 根据用户ID查询用户所有的typeid
     * @param userId
     * @return
     */
    @Query(nativeQuery = true,value = "select typeid from dmp_user_type where userid=?1")
    List<Integer> findByUserId(Integer userId);

}
