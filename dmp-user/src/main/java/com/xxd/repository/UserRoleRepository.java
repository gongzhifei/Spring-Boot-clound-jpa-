package com.xxd.repository;

import com.xxd.dto.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author gongzhifei
 */
public interface UserRoleRepository extends JpaRepository<UserRole,Integer> {

    /**
     * 查找用户所有角色ID
     * @param userId
     * @return
     */
    @Query(nativeQuery = true,value = "select roleid from dmp_user_role where userid=?1")
    List<Integer> queryByRoleId(Integer userId);

}
