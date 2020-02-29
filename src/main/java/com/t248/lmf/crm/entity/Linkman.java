package com.t248.lmf.crm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "cst_linkman")
public class Linkman implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "lkm_id")
  private Long lkmId;
//  @Column(name = "lkm_cust_no")
//  private String lkmCustNo;
  @Column(name = "lkm_cust_name")
  private String lkmCustName;
  @Column(name = "lkm_name")
  private String lkmName;
  @Column(name = "lkm_sex")
  private String lkmSex;
  @Column(name = "lkm_postion")
  private String lkmPostion;
  @Column(name = "lkm_tel")
  private String lkmTel;
  @Column(name = "lkm_mobile")
  private String lkmMobile;
  @Column(name = "lkm_memo")
  private String lkmMemo;


  @ManyToOne(targetEntity = Customer.class,fetch = FetchType.LAZY,cascade={CascadeType.ALL})
  @JsonIgnore
  @JoinColumn(name = "lkm_cust_no")
  private Customer customer;

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public Long getLkmId() {
    return lkmId;
  }

  public void setLkmId(Long lkmId) {
    this.lkmId = lkmId;
  }


//  public String getLkmCustNo() {
//    return lkmCustNo;
//  }
//
//  public void setLkmCustNo(String lkmCustNo) {
//    this.lkmCustNo = lkmCustNo;
//  }


  public String getLkmCustName() {
    return lkmCustName;
  }

  public void setLkmCustName(String lkmCustName) {
    this.lkmCustName = lkmCustName;
  }


  public String getLkmName() {
    return lkmName;
  }

  public void setLkmName(String lkmName) {
    this.lkmName = lkmName;
  }


  public String getLkmSex() {
    return lkmSex;
  }

  public void setLkmSex(String lkmSex) {
    this.lkmSex = lkmSex;
  }


  public String getLkmPostion() {
    return lkmPostion;
  }

  public void setLkmPostion(String lkmPostion) {
    this.lkmPostion = lkmPostion;
  }


  public String getLkmTel() {
    return lkmTel;
  }

  public void setLkmTel(String lkmTel) {
    this.lkmTel = lkmTel;
  }


  public String getLkmMobile() {
    return lkmMobile;
  }

  public void setLkmMobile(String lkmMobile) {
    this.lkmMobile = lkmMobile;
  }


  public String getLkmMemo() {
    return lkmMemo;
  }

  public void setLkmMemo(String lkmMemo) {
    this.lkmMemo = lkmMemo;
  }

}
