package com.t248.lmf.crm.service;

import com.t248.lmf.crm.entity.CstService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CstServiceService {
    public void saveService(CstService service);
    public Page<CstService> findServiceList(String svrCustName,String svrType, String svrTitle, Pageable pageable,String type1,String type2);
    public void delService(Long svrId);
    public CstService getCstService(Long svrId);


}
