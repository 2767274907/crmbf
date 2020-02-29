package com.t248.lmf.crm.service;

import com.t248.lmf.crm.entity.Right;
import com.t248.lmf.crm.entity.Role;
import com.t248.lmf.crm.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IRoleService {
    public List<Role> findAllRoles();
    public Role getRolebyUser(User user);
    public Role getRoleByroleId(Long roleId);
    public void saveRole(Role role);
    public Page<Role> findRoles(String roleName, Pageable pageable);
    public List<Right> findAllRights();
    public List<Role> findRolesByNameLink(String roleName);
    public void deleRole(Long roleId);
    public void delSysRoleRight(Long roleId);
}
