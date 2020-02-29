package com.t248.lmf.crm.repository;

import com.t248.lmf.crm.entity.Activity;
import com.t248.lmf.crm.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ActivityRepostitor extends JpaRepository<Activity,Long> , JpaSpecificationExecutor<Activity>{
    public Activity findByAtvId(Long atvId);


    public List<Activity> findByCustomer(Customer customer);
}
