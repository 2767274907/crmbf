package com.t248.lmf.crm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 角色表
 */
@Entity
@Table(name = "sys_role")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer","handler"})
public class Role implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "role_id")
  private Long roleId;
  @Column(name="role_name")
  private String roleName;
  @Column(name="role_desc")
  private String roleDesc;
  @Column(name="role_flag")
  private Long roleFlag;

  @OneToMany(targetEntity = User.class,fetch = FetchType.LAZY,cascade = CascadeType.REMOVE,mappedBy = "role")
  @JsonIgnore
  private Set<User> users = new HashSet<User>();

  @OneToMany(targetEntity = SysRoleRight.class,cascade={CascadeType.ALL},mappedBy = "role")
  @JsonIgnore
  private Set<SysRoleRight> sysRoleRights = new HashSet<SysRoleRight>();

  public Set<SysRoleRight> getSysRoleRights() {
    return sysRoleRights;
  }

  public void setSysRoleRights(Set<SysRoleRight> sysRoleRights) {
    this.sysRoleRights = sysRoleRights;
  }

  public Role(String roleName, String roleDesc, Long roleFlag) {
    this.roleName = roleName;
    this.roleDesc = roleDesc;
    this.roleFlag = roleFlag;
  }

  public Role() {
  }
  public Role(Long roleId) {
    this.roleId=roleId;
  }

  @Override
  public String toString() {
    users.forEach(i-> System.out.println(i.toString()));
    return "Role{" +
            "roleId=" + roleId +
            ", roleName='" + roleName + '\'' +
            ", roleDesc='" + roleDesc + '\'' +
            ", roleFlag=" + roleFlag +
            '}';
  }

  public Long getRoleId() {
    return roleId;
  }

  public void setRoleId(Long roleId) {
    this.roleId = roleId;
  }

  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

  public String getRoleDesc() {
    return roleDesc;
  }

  public void setRoleDesc(String roleDesc) {
    this.roleDesc = roleDesc;
  }

  public Long getRoleFlag() {
    return roleFlag;
  }

  public void setRoleFlag(Long roleFlag) {
    this.roleFlag = roleFlag;
  }

  public Set<User> getUsers() {
    return users;
  }

  public void setUsers(Set<User> users) {
    this.users = users;
  }
}



