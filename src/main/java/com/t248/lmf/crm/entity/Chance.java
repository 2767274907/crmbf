package com.t248.lmf.crm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 机会表
 */
@Entity
@Table(name = "sal_chance")
public class Chance implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="chc_id")
  private Long chcId;
  @Column(name="chc_source")
  private String chcSource;
  @Column(name="chc_cust_name")
  private String chcCustName;
  @Column(name="chc_title")
  private String chcTitle;
  @Column(name="chc_rate")
  private Long chcRate;
  @Column(name="chc_linkman")
  private String chcLinkman;
  @Column(name="chc_tel")
  private String chcTel;
  @Column(name="chc_desc")
  private String chcDesc;
  @Column(name="chc_create_id")
  private Long chcCreateId;
  @Column(name="chc_create_by")
  private String chcCreateBy;
  @Column(name="chc_create_date")
  private Date chcCreateDate;
  @Column(name="chc_due_id")
  private Long chcDueId;
  @Column(name="chc_due_to")
  private String chcDueTo;
  @Column(name="chc_due_date")
  @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
  private Date chcDueDate;
  @Column(name="chc_status")
  private String chcStatus;
  @Column(name="chc_client_grade")
  private String chcClientGrade;
  @Column(name="chc_area")
  private String chcArea;

  @OneToMany(targetEntity = Plan.class,fetch = FetchType.EAGER)
  @JoinColumn(name = "pla_chc_id")
  @JsonIgnore
  private Set<Plan> plans = new HashSet<Plan>();

//  @ManyToOne(targetEntity = User.class)
//  @JoinColumn(name = "chc_due_id")
//  private User user;

  @Override
  public String toString() {
    return "Chance{" +
            "chcId=" + chcId +
            ", chcSource='" + chcSource + '\'' +
            ", chcCustName='" + chcCustName + '\'' +
            ", chcTitle='" + chcTitle + '\'' +
            ", chcRate=" + chcRate +
            ", chcLinkman='" + chcLinkman + '\'' +
            ", chcTel='" + chcTel + '\'' +
            ", chcDesc='" + chcDesc + '\'' +
            ", chcCreateId=" + chcCreateId +
            ", chcCreateBy='" + chcCreateBy + '\'' +
            ", chcCreateDate=" + chcCreateDate +
            ", chcDueTo='" + chcDueTo + '\'' +
            ", chcDueDate=" + chcDueDate +
            ", chcStatus='" + chcStatus + '\'' +
            '}';
  }

  public Set<Plan> getPlans() {
    return plans;
  }

  public void setPlans(Set<Plan> plans) {
    this.plans = plans;
  }

  public Chance() {
  }

//  public User getUser() {
//    return user;
//  }
//
//  public void setUser(User user) {
//    this.user = user;
//  }

  public Long getChcId() {
    return chcId;
  }

  public void setChcId(Long chcId) {
    this.chcId = chcId;
  }

  public String getChcSource() {
    return chcSource;
  }

  public void setChcSource(String chcSource) {
    this.chcSource = chcSource;
  }

  public String getChcCustName() {
    return chcCustName;
  }

  public void setChcCustName(String chcCustName) {
    this.chcCustName = chcCustName;
  }

  public String getChcTitle() {
    return chcTitle;
  }

  public String getChcClientGrade() {
    return chcClientGrade;
  }

  public void setChcClientGrade(String chcClientGrade) {
    this.chcClientGrade = chcClientGrade;
  }

  public String getChcArea() {
    return chcArea;
  }

  public void setChcArea(String chcArea) {
    this.chcArea = chcArea;
  }

  public void setChcTitle(String chcTitle) {
    this.chcTitle = chcTitle;
  }

  public Long getChcRate() {
    return chcRate;
  }

  public void setChcRate(Long chcRate) {
    this.chcRate = chcRate;
  }

  public String getChcLinkman() {
    return chcLinkman;
  }

  public void setChcLinkman(String chcLinkman) {
    this.chcLinkman = chcLinkman;
  }

  public String getChcTel() {
    return chcTel;
  }

  public void setChcTel(String chcTel) {
    this.chcTel = chcTel;
  }

  public String getChcDesc() {
    return chcDesc;
  }

  public void setChcDesc(String chcDesc) {
    this.chcDesc = chcDesc;
  }

  public Long getChcCreateId() {
    return chcCreateId;
  }

  public void setChcCreateId(Long chcCreateId) {
    this.chcCreateId = chcCreateId;
  }

  public String getChcCreateBy() {
    return chcCreateBy;
  }

  public void setChcCreateBy(String chcCreateBy) {
    this.chcCreateBy = chcCreateBy;
  }

  public Date getChcCreateDate() {
    return chcCreateDate;
  }

  public void setChcCreateDate(Date chcCreateDate) {
    this.chcCreateDate = chcCreateDate;
  }

  public Long getChcDueId() {
    return chcDueId;
  }

  public void setChcDueId(Long chcDueId) {
    this.chcDueId = chcDueId;
  }

  public String getChcDueTo() {
    return chcDueTo;
  }

  public void setChcDueTo(String chcDueTo) {
    this.chcDueTo = chcDueTo;
  }

  public Date getChcDueDate() {
    return chcDueDate;
  }

  public void setChcDueDate(Date chcDueDate) {
    this.chcDueDate = chcDueDate;
  }

  public String getChcStatus() {
    return chcStatus;
  }

  public void setChcStatus(String chcStatus) {
    this.chcStatus = chcStatus;
  }
}
