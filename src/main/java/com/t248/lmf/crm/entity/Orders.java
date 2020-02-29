package com.t248.lmf.crm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Orders implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long odrId;
//  @Column(name = "odr_customer_no")
//  private String odrCustomerNo;
  private Date odrDate;
  private String odrAddr;
  private String odrStatus;

  @OneToMany(targetEntity = OrdersLine.class,fetch = FetchType.EAGER,mappedBy = "orders",cascade={CascadeType.ALL})
  @JsonIgnore
  private Set<OrdersLine> ordersLines;

  @ManyToOne(targetEntity = Customer.class,fetch = FetchType.EAGER)
  @JsonIgnore
  @JoinColumn(name = "odr_customer_no")
  private Customer customer;


  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public Set<OrdersLine> getOrdersLines() {
    return ordersLines;
  }

  public void setOrdersLines(Set<OrdersLine> ordersLines) {
    this.ordersLines = ordersLines;
  }

  public Long getOdrId() {
    return odrId;
  }

  public void setOdrId(Long odrId) {
    this.odrId = odrId;
  }


//  public String getOdrCustomerNo() {
//    return odrCustomerNo;
//  }
//
//  public void setOdrCustomerNo(String odrCustomerNo) {
//    this.odrCustomerNo = odrCustomerNo;
//  }


  public Date getOdrDate() {
    return odrDate;
  }

  public void setOdrDate(Date odrDate) {
    this.odrDate = odrDate;
  }


  public String getOdrAddr() {
    return odrAddr;
  }

  public void setOdrAddr(String odrAddr) {
    this.odrAddr = odrAddr;
  }


  public String getOdrStatus() {
    return odrStatus;
  }

  public void setOdrStatus(String odrStatus) {
    this.odrStatus = odrStatus;
  }

}
