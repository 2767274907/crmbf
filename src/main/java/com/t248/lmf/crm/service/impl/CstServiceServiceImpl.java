package com.t248.lmf.crm.service.impl;

import com.t248.lmf.crm.entity.CstService;
import com.t248.lmf.crm.repository.CstServiceRepostitor;
import com.t248.lmf.crm.service.CstServiceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class CstServiceServiceImpl implements CstServiceService {

    @Resource
    CstServiceRepostitor serviceRepostitor;

    @Override
    public void saveService(CstService cstService) {
        serviceRepostitor.save(cstService);
    }

    @Override
    public Page<CstService> findServiceList(String svrCustName,String svrType, String svrTitle, Pageable pageable,String type1,String type2) {
        Specification<CstService> specification = new Specification<CstService>() {
            @Override
            public Predicate toPredicate(Root<CstService> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(svrCustName!=null&&!svrCustName.equals("")){
                    predicates.add(criteriaBuilder.like(root.get("svrCustName"),"%"+svrCustName+"%"));
                }
                if(svrTitle!=null&& !svrTitle.equals("")){
                    predicates.add(criteriaBuilder.like(root.get("svrTitle"),"%"+svrTitle+"%"));
                }
                if(svrType!=null&& !svrType.equals("0")){
                    predicates.add(criteriaBuilder.equal(root.get("svrType"),svrType));
                }
                if (type1!=null&&!type1.equals("")){
                    if(type2.equals("eq")){
                        predicates.add(criteriaBuilder.equal(root.get("svrStatus"),type1));
                    }else{
                        predicates.add(criteriaBuilder.notEqual(root.get("svrStatus"),type1));
                    }
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return serviceRepostitor.findAll(specification,pageable);
    }

    @Override
    public void delService(Long svrId) {
        serviceRepostitor.deleteById(svrId);
    }

    @Override
    public CstService getCstService(Long svrId) {
        return serviceRepostitor.getOne(svrId);
    }
}
