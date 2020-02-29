package com.t248.lmf.crm.service;

import com.t248.lmf.crm.entity.Chance;
import com.t248.lmf.crm.entity.Customer;
import com.t248.lmf.crm.entity.Plan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ChanceService {
    public Page<Chance> findChances(String type,String type2,String chcCustName,String chcTitle,String chcLinkman, Pageable pageable);
    public void save(Chance chance);
    public void update(Chance chance);
    public Chance getChance(Long chcId);
    public void del(Long chcId) throws Exception;

    public void planupd(Long plaId,String plaTodo,String plaResult);
    public void plandel(Long plaId);
    public void planSave(Plan plan);

    public void saveCustomer(Customer customer);
}
