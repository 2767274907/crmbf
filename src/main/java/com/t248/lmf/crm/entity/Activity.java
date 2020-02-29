package com.t248.lmf.crm.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "cst_activity")
public class Activity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long atvId;
//  @Column(name = "atv_cust_no")
//  private String atvCustNo;
  private String atvCustName;
  @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
  private Date atvDate;
  private String atvPlace;
  private String atvTitle;
  private String atvDesc;

  @ManyToOne(targetEntity = Customer.class,fetch = FetchType.LAZY,cascade={CascadeType.ALL})
  @JsonIgnore
  @JoinColumn(name = "atv_cust_no")
  private Customer customer;


  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public Long getAtvId() {
    return atvId;
  }

  public void setAtvId(Long atvId) {
    this.atvId = atvId;
  }


//  public String getAtvCustNo() {
//    return atvCustNo;
//  }
//
//  public void setAtvCustNo(String atvCustNo) {
//    this.atvCustNo = atvCustNo;
//  }


  public String getAtvCustName() {
    return atvCustName;
  }

  public void setAtvCustName(String atvCustName) {
    this.atvCustName = atvCustName;
  }


  public Date getAtvDate() {
    return atvDate;
  }

  public void setAtvDate(Date atvDate) {
    this.atvDate = atvDate;
  }


  public String getAtvPlace() {
    return atvPlace;
  }

  public void setAtvPlace(String atvPlace) {
    this.atvPlace = atvPlace;
  }


  public String getAtvTitle() {
    return atvTitle;
  }

  public void setAtvTitle(String atvTitle) {
    this.atvTitle = atvTitle;
  }


  public String getAtvDesc() {
    return atvDesc;
  }

  public void setAtvDesc(String atvDesc) {
    this.atvDesc = atvDesc;
  }

}
