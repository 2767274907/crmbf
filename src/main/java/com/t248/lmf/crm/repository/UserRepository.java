package com.t248.lmf.crm.repository;

import com.t248.lmf.crm.entity.Role;
import com.t248.lmf.crm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface UserRepository extends JpaRepository<User,Long>, JpaSpecificationExecutor<User> {
    public User findByUsrNameAndUsrPassword(String usrName, String usrPassword);

    public User findByUsrId(Long usrId);

    public User findByUsrName(String usrName);

    public List<User> findByRole(Role role);

    @Transactional
    @Query(value = "delete from sys_user where usr_id = ?1 ",nativeQuery = true)
    @Modifying
    public void del(Long usrId);

//    @Query("select u.usrId as usrId,u.usrName as usrName,u.usrPassword as usrPassword," +
//            "u.usrRoleId as usrRolid,u.usrFlag as usrFlag,r.roleName as roleName" +
//            " from User u,Role r where u.usrRoleId=r.roleId and u.usrId=?1")
//    public UserInfo getUserInfo(Long usrId);

}
