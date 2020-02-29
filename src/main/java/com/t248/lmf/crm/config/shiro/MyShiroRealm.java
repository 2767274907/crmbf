package com.t248.lmf.crm.config.shiro;

import com.t248.lmf.crm.entity.Role;
import com.t248.lmf.crm.entity.SysRoleRight;
import com.t248.lmf.crm.entity.User;
import com.t248.lmf.crm.service.IRoleService;
import com.t248.lmf.crm.service.IUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;
import java.util.List;


public class MyShiroRealm extends AuthorizingRealm {

    @Resource
    private IUserService userService;

    @Resource
    private IRoleService roleService;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("身份认证：MyShiroRealm.doGetAuthenticationInfo()");
        //获取用户输入的账号

        UsernamePasswordToken token=(UsernamePasswordToken)authenticationToken;
        String usrName=token.getUsername();
        String usrPassword=new String(token.getPassword());
        System.out.println("usrName:"+usrName);
        System.out.println("usrPassword:"+usrPassword);
        //通过usrName从数据库中查询User对象，如果找到，没找到
        //实际项目中，可以根据实际情况做缓存，如果不做，shiro自己也是有时间间隔制，两分钟内不会重复执行该方法。

        User user=userService.getUserByName(usrName);
        System.out.println("---->>user="+user);
        if (user==null) {
            throw new UnknownAccountException("账号不存在");
        }/*else if(! user.getUsrPassword().equals(usrPassword)){
            throw new IncorrectCredentialsException("密码不正确");
        }*/

        //认证成功，给用户添加权限
        Role role = roleService.getRolebyUser(user);
        List<SysRoleRight> sysRoleRights = userService.getSysRoleRightByRole(role);
        role.getSysRoleRights().addAll(sysRoleRights);
        user.setRole(role);

        //认证信息
        SimpleAuthenticationInfo authenticationInfo=new SimpleAuthenticationInfo(
                user,
                user.getUsrPassword(),
                ByteSource.Util.bytes("salt"),
                getName());//getName-realm name
        return authenticationInfo;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("权限认证--->MyShiorRealm.doGetAuthenticationInfo");

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
//        authorizationInfo.addStringPermission("用户管理");
//        authorizationInfo.addStringPermission("指派角色");
        User user = (User)principalCollection.getPrimaryPrincipal();
        System.out.println(user.toString());
        for (SysRoleRight sysRoleRight : user.getRole().getSysRoleRights()){
            System.out.println("用户授权的权限："+sysRoleRight.getRight().getRightText());
            authorizationInfo.addStringPermission(sysRoleRight.getRight().getRightText());
        }
        return authorizationInfo;
    }
}
