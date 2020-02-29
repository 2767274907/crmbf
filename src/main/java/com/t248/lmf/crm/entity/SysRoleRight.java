package com.t248.lmf.crm.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "sys_role_right")
public class SysRoleRight implements Serializable {
  public SysRoleRight(){}
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long rfId;
//  private long rfRoleId;
//  private String rfRightCode;

  @ManyToOne(targetEntity = Role.class,fetch = FetchType.EAGER)
  @JoinColumn(name = "rf_role_id")
  private Role role;

  @ManyToOne(targetEntity = Right.class,fetch = FetchType.EAGER)
  @JoinColumn(name = "rf_right_code")
  private Right right;


  public long getRfId() {
    return rfId;
  }

  public void setRfId(long rfId) {
    this.rfId = rfId;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public Right getRight() {
    return right;
  }

  public void setRight(Right right) {
    this.right = right;
  }
}
