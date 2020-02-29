package com.t248.lmf.crm.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "storage")
public class Storage  implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long stkId;
//  private long stkProdId;
  private String stkWarehouse;
  private String stkWare;
  private long stkCount;
  private String stkMemo;

  @ManyToOne(targetEntity = Product.class,fetch = FetchType.EAGER)
  @JoinColumn(name = "stk_prod_id")
  Product product;

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public long getStkId() {
    return stkId;
  }

  public void setStkId(long stkId) {
    this.stkId = stkId;
  }

//
//  public long getStkProdId() {
//    return stkProdId;
//  }
//
//  public void setStkProdId(long stkProdId) {
//    this.stkProdId = stkProdId;
//  }


  public String getStkWarehouse() {
    return stkWarehouse;
  }

  public void setStkWarehouse(String stkWarehouse) {
    this.stkWarehouse = stkWarehouse;
  }


  public String getStkWare() {
    return stkWare;
  }

  public void setStkWare(String stkWare) {
    this.stkWare = stkWare;
  }


  public long getStkCount() {
    return stkCount;
  }

  public void setStkCount(long stkCount) {
    this.stkCount = stkCount;
  }


  public String getStkMemo() {
    return stkMemo;
  }

  public void setStkMemo(String stkMemo) {
    this.stkMemo = stkMemo;
  }

}
