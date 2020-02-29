package com.t248.lmf.crm.repository;

import com.t248.lmf.crm.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductRepostitor extends JpaRepository<Product,Long> , JpaSpecificationExecutor<Product>{
}
