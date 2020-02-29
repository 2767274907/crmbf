package com.t248.lmf.crm.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "orders_line")
public class OrdersLine  implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long oddId;
//  private Long oddOrderId;
//  private Long oddProdId;
  private Long oddCount;
  private String oddUnit;
  private double oddPrice;

  @ManyToOne(targetEntity = Product.class,fetch = FetchType.EAGER)
  @JoinColumn(name = "odd_prod_id")
  private Product product;

  @ManyToOne(targetEntity = Orders.class,fetch = FetchType.EAGER)
  @JoinColumn(name = "odd_order_id")
  private Orders orders;


  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public Orders getOrders() {
    return orders;
  }

  public void setOrders(Orders orders) {
    this.orders = orders;
  }

  public Long getOddId() {
    return oddId;
  }

  public void setOddId(Long oddId) {
    this.oddId = oddId;
  }


//  public Long getOddOrderId() {
//    return oddOrderId;
//  }
//
//  public void setOddOrderId(Long oddOrderId) {
//    this.oddOrderId = oddOrderId;
//  }


//  public Long getOddProdId() {
//    return oddProdId;
//  }
//
//  public void setOddProdId(Long oddProdId) {
//    this.oddProdId = oddProdId;
//  }
//
//
  public Long getOddCount() {
    return oddCount;
  }

  public void setOddCount(Long oddCount) {
    this.oddCount = oddCount;
  }


  public String getOddUnit() {
    return oddUnit;
  }

  public void setOddUnit(String oddUnit) {
    this.oddUnit = oddUnit;
  }


  public double getOddPrice() {
    return oddPrice;
  }

  public void setOddPrice(double oddPrice) {
    this.oddPrice = oddPrice;
  }

}
