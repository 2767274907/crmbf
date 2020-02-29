package com.t248.lmf.crm.repository;

import com.t248.lmf.crm.entity.Role;
import com.t248.lmf.crm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role,Long>, JpaSpecificationExecutor<Role> {

    public Role findRoleByUsers(User user);

    public List<Role> findRolesByRoleNameLike(String roleName);

}
