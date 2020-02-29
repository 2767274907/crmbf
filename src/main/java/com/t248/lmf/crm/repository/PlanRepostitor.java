package com.t248.lmf.crm.repository;

import com.t248.lmf.crm.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PlanRepostitor extends JpaRepository<Plan,Long> , JpaSpecificationExecutor<Plan>{
}
