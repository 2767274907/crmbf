package com.t248.lmf.crm.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name ="product")
public class Product implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long prodId;
  private String prodName;
  private String prodType;
  private String prodBatch;
  private String prodUnit;
  private Double prodPrice;
  private String prodMemo;

  @OneToMany(targetEntity = Storage.class,fetch = FetchType.LAZY,mappedBy = "product",cascade={CascadeType.ALL})
  private Set<Storage> storages = new HashSet<>();

  public Set<Storage> getStorages() {
    return storages;
  }

  public void setStorages(Set<Storage> storages) {
    this.storages = storages;
  }

  public Long getProdId() {
    return prodId;
  }

  public void setProdId(Long prodId) {
    this.prodId = prodId;
  }


  public String getProdName() {
    return prodName;
  }

  public void setProdName(String prodName) {
    this.prodName = prodName;
  }


  public String getProdType() {
    return prodType;
  }

  public void setProdType(String prodType) {
    this.prodType = prodType;
  }


  public String getProdBatch() {
    return prodBatch;
  }

  public void setProdBatch(String prodBatch) {
    this.prodBatch = prodBatch;
  }


  public String getProdUnit() {
    return prodUnit;
  }

  public void setProdUnit(String prodUnit) {
    this.prodUnit = prodUnit;
  }


  public Double getProdPrice() {
    return prodPrice;
  }

  public void setProdPrice(Double prodPrice) {
    this.prodPrice = prodPrice;
  }


  public String getProdMemo() {
    return prodMemo;
  }

  public void setProdMemo(String prodMemo) {
    this.prodMemo = prodMemo;
  }

}
