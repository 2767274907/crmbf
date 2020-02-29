package com.t248.lmf.crm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 客户信息表
 */
@Entity
@Table(name = "cst_customer")
public class Customer implements Serializable {


  public Customer(){}

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "cust_id")
  private Long custId;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "cust_no")
  private String custNo;
  @Column(name = "cust_name")
  private String custName;
  @Column(name = "cust_region")
  private String custRegion;
//  @Column(name = "cust_manager_id")
//  private Long custManagerId;
  @Column(name = "cust_manager_name")
  private String custManagerName;
  @Column(name = "cust_level")
  private Long custLevel;
  @Column(name = "cust_level_label")
  private String custLevelLabel;
  @Column(name = "cust_satisfy")
  private Long custSatisfy;
  @Column(name = "cust_credit")
  private Long custCredit;
  @Column(name = "cust_addr")
  private String custAddr;
  @Column(name = "cust_zip")
  private String custZip;
  @Column(name = "cust_tel")
  private String custTel;
  @Column(name = "cust_fax")
  private String custFax;
  @Column(name = "cust_website")
  private String custWebsite;
  @Column(name = "cust_licence_no")
  private String custLicenceNo;
  @Column(name = "cust_chieftain")
  private String custChieftain;
  @Column(name = "cust_bankroll")
  private Long custBankroll;
  @Column(name = "cust_turnover")
  private Long custTurnover;
  @Column(name = "cust_bank")
  private String custBank;
  @Column(name = "cust_bank_account")
  private String custBankAccount;
  @Column(name = "cust_local_tax_no")
  private String custLocalTaxNo;
  @Column(name = "cust_national_tax_no")
  private String custNationalTaxNo;
  @Column(name = "cust_status")
  private String custStatus;

  @ManyToOne(targetEntity = User.class,fetch = FetchType.EAGER)
  @JoinColumn(name = "cust_manager_id")
  private User user;

  @OneToMany(targetEntity = Linkman.class,fetch = FetchType.LAZY,mappedBy = "customer",cascade={CascadeType.ALL})
  @JsonIgnore
  private Set<Linkman> linkmens = new HashSet<Linkman>();

  @OneToMany(targetEntity = Activity.class,fetch = FetchType.LAZY,mappedBy = "customer",cascade={CascadeType.ALL})
  @JsonIgnore
  private Set<Activity> activitys = new HashSet<Activity>();


  @OneToMany(targetEntity = Orders.class,fetch = FetchType.EAGER,mappedBy = "customer",cascade={CascadeType.ALL})
  @JsonIgnore
  private Set<Orders> orderss = new HashSet<Orders>();

  public Set<Orders> getOrderss() {
    return orderss;
  }

  public void setOrderss(Set<Orders> orderss) {
    this.orderss = orderss;
  }

  public Set<Activity> getActivitys() {
    return activitys;
  }

  public void setActivitys(Set<Activity> activitys) {
    this.activitys = activitys;
  }

  public Set<Linkman> getLinkmens() {
    return linkmens;
  }

  public void setLinkmens(Set<Linkman> linkmens) {
    this.linkmens = linkmens;
  }

  public Long getCustId() {
    return custId;
  }

  public void setCustId(Long custId) {
    this.custId = custId;
  }

  public String getCustNo() {
    return custNo;
  }

  public void setCustNo(String custNo) {
    this.custNo = custNo;
  }

  public String getCustName() {
    return custName;
  }

  public void setCustName(String custName) {
    this.custName = custName;
  }

  public String getCustRegion() {
    return custRegion;
  }

  public void setCustRegion(String custRegion) {
    this.custRegion = custRegion;
  }

  public String getCustManagerName() {
    return custManagerName;
  }

  public void setCustManagerName(String custManagerName) {
    this.custManagerName = custManagerName;
  }

  public Long getCustLevel() {
    return custLevel;
  }

  public void setCustLevel(Long custLevel) {
    this.custLevel = custLevel;
  }

  public String getCustLevelLabel() {
    return custLevelLabel;
  }

  public void setCustLevelLabel(String custLevelLabel) {
    this.custLevelLabel = custLevelLabel;
  }

  public Long getCustSatisfy() {
    return custSatisfy;
  }

  public void setCustSatisfy(Long custSatisfy) {
    this.custSatisfy = custSatisfy;
  }

  public Long getCustCredit() {
    return custCredit;
  }

  public void setCustCredit(Long custCredit) {
    this.custCredit = custCredit;
  }

  public String getCustAddr() {
    return custAddr;
  }

  public void setCustAddr(String custAddr) {
    this.custAddr = custAddr;
  }

  public String getCustZip() {
    return custZip;
  }

  public void setCustZip(String custZip) {
    this.custZip = custZip;
  }

  public String getCustTel() {
    return custTel;
  }

  public void setCustTel(String custTel) {
    this.custTel = custTel;
  }

  public String getCustFax() {
    return custFax;
  }

  public void setCustFax(String custFax) {
    this.custFax = custFax;
  }

  public String getCustWebsite() {
    return custWebsite;
  }

  public void setCustWebsite(String custWebsite) {
    this.custWebsite = custWebsite;
  }

  public String getCustLicenceNo() {
    return custLicenceNo;
  }

  public void setCustLicenceNo(String custLicenceNo) {
    this.custLicenceNo = custLicenceNo;
  }

  public String getCustChieftain() {
    return custChieftain;
  }

  public void setCustChieftain(String custChieftain) {
    this.custChieftain = custChieftain;
  }

  public Long getCustBankroll() {
    return custBankroll;
  }

  public void setCustBankroll(Long custBankroll) {
    this.custBankroll = custBankroll;
  }

  public Long getCustTurnover() {
    return custTurnover;
  }

  public void setCustTurnover(Long custTurnover) {
    this.custTurnover = custTurnover;
  }

  public String getCustBank() {
    return custBank;
  }

  public void setCustBank(String custBank) {
    this.custBank = custBank;
  }

  public String getCustBankAccount() {
    return custBankAccount;
  }

  public void setCustBankAccount(String custBankAccount) {
    this.custBankAccount = custBankAccount;
  }

  public String getCustLocalTaxNo() {
    return custLocalTaxNo;
  }

  public void setCustLocalTaxNo(String custLocalTaxNo) {
    this.custLocalTaxNo = custLocalTaxNo;
  }

  public String getCustNationalTaxNo() {
    return custNationalTaxNo;
  }

  public void setCustNationalTaxNo(String custNationalTaxNo) {
    this.custNationalTaxNo = custNationalTaxNo;
  }

  public String getCustStatus() {
    return custStatus;
  }

  public void setCustStatus(String custStatus) {
    this.custStatus = custStatus;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
