package com.t248.lmf.crm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name ="sys_user")
public class User implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="usr_id")
  private long usrId;
  @Column(name="usr_name")
  private String usrName;
  @Column(name="usr_password")
  private String usrPassword;
//  @Column(name="usr_role_id")
//  private long usrRoleId;
  @Column(name="usr_flag")
  @JoinColumn
  private long usrFlag;

  @ManyToOne(targetEntity = Role.class,fetch = FetchType.LAZY)
  @JoinColumn(name = "usr_role_id")
  private Role role;

  @OneToMany(targetEntity = Customer.class,fetch = FetchType.LAZY,mappedBy = "user")
  @JsonIgnore
  private Set<Customer> customers = new HashSet<Customer>();

  public User() {
  }
  public User(long usrId) {
    this.usrId=usrId;
  }

  public User(String usrName, String usrPassword, long usrFlag, Role role) {
    this.usrName = usrName;
    this.usrPassword = usrPassword;
    this.usrFlag = usrFlag;
    this.role = role;
  }


  public Set<Customer> getCustomers() {
    return customers;
  }

  public void setCustomers(Set<Customer> customers) {
    this.customers = customers;
  }

  @Override
  public String toString() {
    return "User{" +
            "usrId=" + usrId +
            ", usrName='" + usrName + '\'' +
            ", usrPassword='" + usrPassword + '\'' +
            ", usrFlag=" + usrFlag +
            ", role=" + role.getRoleName() +
            '}';
  }

  public long getUsrId() {
    return usrId;
  }

  public void setUsrId(long usrId) {
    this.usrId = usrId;
  }

  public String getUsrName() {
    return usrName;
  }

  public void setUsrName(String usrName) {
    this.usrName = usrName;
  }

  public String getUsrPassword() {
    return usrPassword;
  }

  public void setUsrPassword(String usrPassword) {
    this.usrPassword = usrPassword;
  }

  public long getUsrFlag() {
    return usrFlag;
  }

  public void setUsrFlag(long usrFlag) {
    this.usrFlag = usrFlag;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }
}
