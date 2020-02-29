package com.t248.lmf.crm.service.impl;

import com.t248.lmf.crm.entity.Chance;
import com.t248.lmf.crm.entity.Customer;
import com.t248.lmf.crm.entity.Plan;
import com.t248.lmf.crm.repository.ChanceRepostitor;
import com.t248.lmf.crm.repository.CustomerRepostitor;
import com.t248.lmf.crm.repository.PlanRepostitor;
import com.t248.lmf.crm.service.ChanceService;
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
public class ChanceServiceImpl implements ChanceService {

    @Resource
    private ChanceRepostitor chanceRepostitor;

    @Override
    public Page<Chance> findChances(String type1,String type2,String chcCustName, String chcTitle,String chcLinkman, Pageable pageable) {
        Specification<Chance> specification = new Specification<Chance>() {
            @Override
            public Predicate toPredicate(Root<Chance> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(chcCustName!=null&&!chcCustName.equals("")){
                    predicates.add(criteriaBuilder.like(root.get("chcCustName"),"%"+chcCustName+"%"));
                }
                if(chcTitle!=null&& !chcTitle.equals("")){
                    predicates.add(criteriaBuilder.like(root.get("chcTitle"),"%"+chcTitle+"%"));
                }
                if(chcLinkman!=null&& !chcLinkman.equals("")){
                    predicates.add(criteriaBuilder.like(root.get("chcLinkman"),"%"+chcLinkman+"%"));
                    System.out.println(chcLinkman);
                }
                if (type1!=null&&!type1.equals("")){
                    if(type2.equals("eq")){
                        predicates.add(criteriaBuilder.equal(root.get("chcStatus"),type1));
                    }else{
                        predicates.add(criteriaBuilder.notEqual(root.get("chcStatus"),type1));
                    }
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return chanceRepostitor.findAll(specification,pageable);
    }

    @Override
    public void save(Chance chance) {
        chanceRepostitor.save(chance);
    }

    @Override
    public void update(Chance chance) {
        Chance update = chanceRepostitor.findByChcId(chance.getChcId());
        update.setChcCustName(chance.getChcCustName());
        update.setChcLinkman(chance.getChcLinkman());
        update.setChcSource(chance.getChcSource());
        update.setChcRate(chance.getChcRate());
        update.setChcTel(chance.getChcTel());
        update.setChcArea(chance.getChcArea());
        update.setChcClientGrade(chance.getChcClientGrade());
        update.setChcTitle(chance.getChcTitle());
        update.setChcDesc(chance.getChcDesc());
        update.setChcDueDate(chance.getChcDueDate());
        update.setChcDueId(chance.getChcDueId());
        update.setChcDueTo(chance.getChcDueTo());
        update.setChcStatus(chance.getChcStatus());
        chanceRepostitor.save(update);
    }


    @Override
    public Chance getChance(Long chcId) {
        return chanceRepostitor.findByChcId(chcId);
    }

    @Override
    public void del(Long chcId) throws Exception {
        chanceRepostitor.delete(chcId);
    }

    @Resource
    private PlanRepostitor planRepostitor;
    @Override
    public void planupd(Long plaId,String plaTodo,String plaResult) {
        Plan plan = planRepostitor.getOne(plaId);
        System.out.println(plan.getPlaTodo()+"-----plaId："+plaId+"plaRodo："+plaTodo+"plaResult:"+plaResult);
        System.out.println(plan.getPlaResult());
        System.out.println(plan.getPlaId());
        if(plaTodo!=null&&!plaTodo.trim().equals("")){
            plan.setPlaTodo(plaTodo);
        }
        if(plaResult!=null&&!plaResult.trim().equals("")){
            plan.setPlaResult(plaResult);
        }
        planRepostitor.save(plan);
    }

    @Override
    public void plandel(Long plaId) {
        planRepostitor.deleteById(plaId);
    }

    @Override
    public void planSave(Plan plan) {
        planRepostitor.save(plan);
    }


    @Resource
    private CustomerRepostitor customerRepostitor;

    @Override
    public void saveCustomer(Customer customer) {
        customerRepostitor.save(customer);
    }


}
