package com.t248.lmf.crm.service;

import com.t248.lmf.crm.entity.Right;
import com.t248.lmf.crm.entity.Role;
import com.t248.lmf.crm.entity.SysRoleRight;
import com.t248.lmf.crm.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface IUserService {

    public User login(String usrName, String usrPassword);
    public User addUser(User user);
    public void deleteUser(Long usrId);
    public User updateUser(User user);
    public User getUser(Long usrId);
    public List<User> findAllUsers();
    public List<User> findByRole(Role role);

    public Page<User> findPageByMap(Map param, Pageable pageable);

    public Page<User> findUsers(String userName, Long roleId, Pageable pageable);

    public User getUserByName(String usrName);

    public List<Role> findAllRoles();


//    public List<Right> findRightsByRole(Role role);
    public List<Right> findByRightParentCode(String rightParentCode);

    public List<SysRoleRight> getSysRoleRightByRole(Role role);
}
