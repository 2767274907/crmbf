package com.t248.lmf.crm.service.impl;

import com.t248.lmf.crm.entity.*;
import com.t248.lmf.crm.repository.*;
import com.t248.lmf.crm.service.CustomerService;
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
public class CustomerServiceImpl implements CustomerService {

    @Resource
    CustomerRepostitor customerRepostitor;

    @Override
    public Page<Customer> findCustomer(String custName, String custNo, String custRegion, String custManagerName, String custLevel, Pageable pageable) {
        Specification<Customer> specification = new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(custName!=null&&!custName.equals("")){
                    predicates.add(criteriaBuilder.like(root.get("custName"),"%"+custName+"%"));
                }
                if(custNo!=null&& !custNo.equals("")){
                    predicates.add(criteriaBuilder.like(root.get("custNo"),"%"+custNo+"%"));
                    System.out.println(custNo);
                }
                if(custRegion!=null&& !custRegion.equals("")&&!custRegion.equals("0")){
                    predicates.add(criteriaBuilder.equal(root.get("custRegion"),custRegion));
                    System.out.println(custRegion);
                }
                if(custLevel!=null&& !custLevel.equals("")&&!custLevel.equals("0")){
                    predicates.add(criteriaBuilder.equal(root.get("custLevel"),custLevel));
                    System.out.println("custLevel:----"+custLevel);
                }
                if(custManagerName!=null&& !custManagerName.equals("")){
                    predicates.add(criteriaBuilder.like(root.get("custManagerName"),"%"+custManagerName+"%"));
                    System.out.println(custManagerName);
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return customerRepostitor.findAll(specification,pageable);
    }

    @Override
    public List<Customer> getCustomers() {
        return customerRepostitor.findAll();
    }

    @Override
    public Customer getCustomer(Long custId) {
        return customerRepostitor.findByCustId(custId);
    }
    @Override
    public Customer getCustomer(String custNo) {
        return customerRepostitor.findByCustNo(custNo);
    }

    @Override
    public void delCustomer(String custNo) {
        customerRepostitor.deleteByCustNo(custNo);
    }

    @Override
    public void saveCustomer(Customer customer) {
        customerRepostitor.save(customer);
    }

    @Resource
    private LinkmanRepostitor linkmanRepostitor;

    @Override
    public List<Linkman> findLinkManList(String lkmCustNo) {
        return linkmanRepostitor.findByCustomer(customerRepostitor.findByCustNo(lkmCustNo));
    }

    @Override
    public Linkman getlink(Long lkmId) {
        return linkmanRepostitor.findByLkmId(lkmId);
    }

    @Override
    public void savelink(Linkman linkman) {
        linkmanRepostitor.save(linkman);
    }

    @Override
    public void dellink(Long lkmId) {
        linkmanRepostitor.deleteById(lkmId);
    }



    @Resource
    private ActivityRepostitor activityRepostitor;

    @Override
    public List<Activity> findActivityList(String atvCustNo) {
        return activityRepostitor.findByCustomer(customerRepostitor.findByCustNo(atvCustNo));
    }

    @Override
    public Activity getActivity(Long atvId) {
        return activityRepostitor.findByAtvId(atvId);
    }

    @Override
    public void saveActivity(Activity activity) {
        activityRepostitor.save(activity);
    }


    @Override
    public void delActivity(Long atvId) {
        activityRepostitor.deleteById(atvId);
    }

    @Resource
    private OrdersRepostitor ordersRepostitor;

    @Override
    public Page<Orders> findOrdersLine(String custNo, Pageable pageable) {
        Specification<Orders> specification = new Specification<Orders>() {
            @Override
            public Predicate toPredicate(Root<Orders> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(custNo!=null&&!custNo.equals("")){
                    predicates.add(criteriaBuilder.equal(root.get("customer"),customerRepostitor.findByCustNo(custNo)));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return ordersRepostitor.findAll(specification,pageable);
    }

    @Override
    public Orders findOrders(Long odrId) {
        return ordersRepostitor.findByOdrId(odrId);
    }

    @Resource
    private CstLostRepostitor cstLostRepostitor;

    @Override
    public Page<CstLost> findLost(String custName, String custManagerName, String custStatus, Pageable pageable) {
        Specification<CstLost> specification = new Specification<CstLost>() {
            @Override
            public Predicate toPredicate(Root<CstLost> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(custName!=null&&!custName.equals("")){
                    predicates.add(criteriaBuilder.like(root.get("lstCustName"),"%"+custName+"%"));
                }
                if(custStatus!=null&& !custStatus.equals("")&&!custStatus.equals("0")){
                    predicates.add(criteriaBuilder.equal(root.get("lstStatus"),custStatus));
                    System.out.println("custStatus:----"+custStatus);
                }
                if(custManagerName!=null&& !custManagerName.equals("")){
                    predicates.add(criteriaBuilder.like(root.get("lstCustManagerName"),"%"+custManagerName+"%"));
                    System.out.println(custManagerName);
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return cstLostRepostitor.findAll(specification,pageable);
    }

    @Override
    public CstLost getLost(Long lstId) {
        return cstLostRepostitor.findByLstId(lstId);
    }

    @Override
    public void save(CstLost cstLost) {
        cstLostRepostitor.save(cstLost);
    }

    @Override
    public void jiangca(){
        System.out.println("开始查找客户最后的下单时间，然后添加");
        List<Orders> list = ordersRepostitor.getOrders();
        for(Orders o:list){
            Customer customer = o.getCustomer();
            System.out.println(customer);
            if(cstLostRepostitor.findByLstCustNo(customer.getCustNo())==null){
                CstLost lost = new CstLost();
                lost.setLstCustNo(customer.getCustNo());
                lost.setLstCustName(customer.getCustName());
                lost.setLstCustManagerId(customer.getUser().getUsrId());
                lost.setLstCustManagerName(customer.getCustManagerName());
                lost.setLstLastOrderDate(o.getOdrDate());
                cstLostRepostitor.save(lost);
            }
        }
        System.out.println("完成");
    }
}
