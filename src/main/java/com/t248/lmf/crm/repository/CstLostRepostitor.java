package com.t248.lmf.crm.repository;

import com.t248.lmf.crm.entity.CstLost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CstLostRepostitor extends JpaRepository<CstLost,Long> , JpaSpecificationExecutor<CstLost>{
    public CstLost findByLstId(Long lstId);
    public CstLost findByLstCustNo(String lstCustNo);
}
