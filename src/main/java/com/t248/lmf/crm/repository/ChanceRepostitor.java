package com.t248.lmf.crm.repository;

import com.t248.lmf.crm.entity.Chance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface ChanceRepostitor extends JpaRepository<Chance,Long> , JpaSpecificationExecutor<Chance>{
    public Chance findByChcId(Long chcId);

    @Transactional
    @Query(value = "delete from sal_chance where chc_id = ?1 ",nativeQuery = true)
    @Modifying
    public void delete(Long chcId);
}
