package com.t248.lmf.crm.service;

import com.t248.lmf.crm.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerService {
    public Page<Customer> findCustomer(String custName, String custNo, String custRegion, String custManagerName, String custLevel, Pageable pageable);
    public List<Customer> getCustomers();
    public Customer getCustomer(Long custId);
    public Customer getCustomer(String custNo);
    public void delCustomer(String custNo);


    public void saveCustomer(Customer customer);
    public List<Linkman> findLinkManList(String lkmCustNo);
    public Linkman getlink(Long lkmId);
    public void savelink(Linkman linkman);
    public void dellink(Long lkmId);

    public List<Activity> findActivityList(String atvCustNo);
    public Activity getActivity(Long atvId);
    public void saveActivity(Activity activity);
    public void delActivity(Long atvId);

    public Page<Orders> findOrdersLine(String custNo,Pageable pageable);
    public Orders findOrders(Long odrId);

    public Page<CstLost> findLost(String custName, String custManagerName, String custStatus, Pageable pageable);
    public CstLost getLost(Long lstId);
    public void save(CstLost cstLost);

    public void jiangca();

}
