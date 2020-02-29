package com.t248.lmf.crm.repository;

import com.t248.lmf.crm.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrdersRepostitor extends JpaRepository<Orders,Long> , JpaSpecificationExecutor<Orders>{
    public Orders findByOdrId(Long odrId);
    @Query(nativeQuery = true,value = "SELECT `odr_id`,`odr_customer_no`,`odr_addr`,`odr_status`,MAX(`odr_date`) AS odr_date\n" +
            "FROM `orders`\n" +
            "WHERE TIMESTAMPADD(MONTH, 6, `odr_date`)<=NOW()\n" +
            "GROUP BY `odr_customer_no`")
    public List<Orders> getOrders();
}
