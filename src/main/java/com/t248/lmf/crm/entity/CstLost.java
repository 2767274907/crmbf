package com.t248.lmf.crm.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "cst_lost")
@Entity
public class CstLost  implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long lstId;
  private String lstCustNo;
  private String lstCustName;
  private long lstCustManagerId;
  private String lstCustManagerName;
  @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
  private Date lstLastOrderDate;
  @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
  private Date lstLostDate;
  private String lstDelay;
  private String lstReason;
  private String lstStatus;


  public long getLstId() {
    return lstId;
  }

  public void setLstId(long lstId) {
    this.lstId = lstId;
  }


  public String getLstCustNo() {
    return lstCustNo;
  }

  public void setLstCustNo(String lstCustNo) {
    this.lstCustNo = lstCustNo;
  }


  public String getLstCustName() {
    return lstCustName;
  }

  public void setLstCustName(String lstCustName) {
    this.lstCustName = lstCustName;
  }


  public long getLstCustManagerId() {
    return lstCustManagerId;
  }

  public void setLstCustManagerId(long lstCustManagerId) {
    this.lstCustManagerId = lstCustManagerId;
  }


  public String getLstCustManagerName() {
    return lstCustManagerName;
  }

  public void setLstCustManagerName(String lstCustManagerName) {
    this.lstCustManagerName = lstCustManagerName;
  }


  public Date getLstLastOrderDate() {
    return lstLastOrderDate;
  }

  public void setLstLastOrderDate(Date lstLastOrderDate) {
    this.lstLastOrderDate = lstLastOrderDate;
  }


  public Date getLstLostDate() {
    return lstLostDate;
  }

  public void setLstLostDate(Date lstLostDate) {
    this.lstLostDate = lstLostDate;
  }


  public String getLstDelay() {
    return lstDelay;
  }

  public void setLstDelay(String lstDelay) {
    this.lstDelay = lstDelay;
  }


  public String getLstReason() {
    return lstReason;
  }

  public void setLstReason(String lstReason) {
    this.lstReason = lstReason;
  }


  public String getLstStatus() {
    return lstStatus;
  }

  public void setLstStatus(String lstStatus) {
    this.lstStatus = lstStatus;
  }

}
