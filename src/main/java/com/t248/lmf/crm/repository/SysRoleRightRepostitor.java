package com.t248.lmf.crm.repository;

import com.t248.lmf.crm.entity.Role;
import com.t248.lmf.crm.entity.SysRoleRight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.transaction.Transactional;
import java.util.List;

public interface SysRoleRightRepostitor extends JpaRepository<SysRoleRight,Long> , JpaSpecificationExecutor<SysRoleRight>{

    public List<SysRoleRight> findByRole(Role role);

    @Transactional
    public void deleteByRole(Role role);
}
