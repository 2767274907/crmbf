package com.t248.lmf.crm.repository;

import com.t248.lmf.crm.entity.Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StorageRepostitor extends JpaRepository<Storage,Long> , JpaSpecificationExecutor<Storage>{
}
