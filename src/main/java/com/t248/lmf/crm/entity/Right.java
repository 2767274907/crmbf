package com.t248.lmf.crm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 权限表
 */
@Entity
@Table(name = "sys_right")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer","handler"})
public class Right implements Serializable {
    @Id
    @Column(name = "right_code")
    private String rightCode;
    @Column(name = "right_parent_code")
    private String rightParentCode;
    @Column(name = "right_type")
    private String rightType;
    @Column(name = "right_text")
    private String rightText;
    @Column(name = "right_url")
    private String rightUrl;
    @Column(name = "right_tip")
    private String rightTip;
    @Column(name = "icon")
    private String icon;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Right(){}
    public Right(String rightCode){
        this.rightCode=rightCode;
    }

    public Right(String rightCode, String rightParentCode, String rightType, String rightText, String rightUrl, String rightTip) {
        this.rightCode = rightCode;
        this.rightParentCode = rightParentCode;
        this.rightType = rightType;
        this.rightText = rightText;
        this.rightUrl = rightUrl;
        this.rightTip = rightTip;
    }

    @OneToMany(targetEntity = SysRoleRight.class,mappedBy = "right",fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<SysRoleRight> sysRoleRights = new HashSet<SysRoleRight>();

    public String getRightCode() {
        return rightCode;
    }

    public void setRightCode(String rightCode) {
        this.rightCode = rightCode;
    }

    public String getRightParentCode() {
        return rightParentCode;
    }

    public void setRightParentCode(String rightParentCode) {
        this.rightParentCode = rightParentCode;
    }

    public String getRightType() {
        return rightType;
    }

    public void setRightType(String rightType) {
        this.rightType = rightType;
    }

    public String getRightText() {
        return rightText;
    }

    public void setRightText(String rightText) {
        this.rightText = rightText;
    }

    public String getRightUrl() {
        return rightUrl;
    }

    public void setRightUrl(String rightUrl) {
        this.rightUrl = rightUrl;
    }

    public String getRightTip() {
        return rightTip;
    }

    public void setRightTip(String rightTip) {
        this.rightTip = rightTip;
    }

    public Set<SysRoleRight> getSysRoleRights() {
        return sysRoleRights;
    }

    public void setSysRoleRights(Set<SysRoleRight> sysRoleRights) {
        this.sysRoleRights = sysRoleRights;
    }
}
