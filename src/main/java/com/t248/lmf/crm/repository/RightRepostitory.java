package com.t248.lmf.crm.repository;

import com.t248.lmf.crm.entity.Right;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RightRepostitory extends JpaRepository<Right,String> {
//    public List<Right> findRightByRoles(Role role);


    public List<Right> findByRightParentCode(String rightParentCode);
}
