package com.t248.lmf.crm;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;

import java.util.Arrays;

public class TestAuthorization {

    @Test
    public void testAuthorizationCustomRealm(){

        Factory<SecurityManager> factory =
                new IniSecurityManagerFactory("classpath:shiro.ini");

        SecurityManager securityManager = factory.getInstance();

        SecurityUtils.setSecurityManager(securityManager);

        Subject subject =SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("zhangsan","111111");

        try{
            subject.login(token);
        }catch (AuthenticationException e){
            e.printStackTrace();
        }

        System.out.println("认证状态："+subject.isAuthenticated());


        boolean ishasRole = subject.hasRole("role1");
        System.out.println("单个角色判断："+ishasRole);

        boolean hasAllRoles = subject.hasAllRoles(Arrays.asList("role1","role2"));
        System.out.println("多个角色判断："+hasAllRoles);

        boolean isPermitted = subject.isPermitted("user:L05");
        System.out.println("单个角色判断："+isPermitted);

        boolean isPermittedAll = subject.isPermittedAll("user:L05","user:L06");
        System.out.println("多个角色判断："+isPermittedAll);



    }



}
