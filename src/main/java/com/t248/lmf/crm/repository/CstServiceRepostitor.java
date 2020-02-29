package com.t248.lmf.crm.repository;

import com.t248.lmf.crm.entity.CstService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CstServiceRepostitor extends JpaRepository<CstService,Long> , JpaSpecificationExecutor<CstService>{
}
