package com.t248.lmf.crm.repository;

import com.t248.lmf.crm.entity.Customer;
import com.t248.lmf.crm.entity.Linkman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface LinkmanRepostitor extends JpaRepository<Linkman,Long> , JpaSpecificationExecutor<Linkman>{
    public Linkman findByLkmId(Long lkmId);

    public List<Linkman> findByCustomer(Customer customer);
}
