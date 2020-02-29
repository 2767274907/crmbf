package com.t248.lmf.crm.repository;

import com.t248.lmf.crm.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.transaction.Transactional;

public interface CustomerRepostitor extends JpaRepository<Customer,Long> , JpaSpecificationExecutor<Customer>{
    public Customer findByCustId(Long custId);
    public Customer findByCustNo(String custNo);
    @Transactional
    public void deleteByCustNo(String custNo);

}
