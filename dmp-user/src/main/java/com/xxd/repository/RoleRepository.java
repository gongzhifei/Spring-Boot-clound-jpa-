package com.xxd.repository;

import com.xxd.dto.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author gongzhifei
 */
public interface RoleRepository extends JpaRepository<Role,Integer> {


    List<Role> findByIdIn(List<Integer> ids);

}
