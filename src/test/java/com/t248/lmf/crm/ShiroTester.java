package com.t248.lmf.crm;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

import java.util.Date;

public class ShiroTester {
    @Test
    public void testLoginAndLogout(){
        IniRealm iniRealm = new IniRealm("classpath:shiro.ini");

        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(iniRealm);

        SecurityUtils.setSecurityManager(defaultSecurityManager);

        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("admin","123456");

        try{
            subject.login(token);
        }catch (UnknownAccountException uae){
            System.out.println("未知账户异常"+uae);
        }catch (IncorrectCredentialsException ice){
            System.out.println("密码错误异常"+ice);
        }catch (LockedAccountException lae){
            System.out.println("账户锁定异常"+lae);
        }catch (ExcessiveAttemptsException eae){
            System.out.println("过多尝试异常"+eae);
        }catch (AuthenticationException ae){
            System.out.println("异常"+ae);
        }

        boolean isAuthenticated = subject.isAuthenticated();
        System.out.println("是否通过验证："+isAuthenticated);
        subject.logout();
        isAuthenticated = subject.isAuthenticated();
        System.out.println("是否通过验证："+isAuthenticated);


    }

    @Test
    public void test2(){
        //123456  加密字符串
        //salt：盐
        //1 ：加密次数
        Md5Hash md5Hash = new Md5Hash(new Date().getTime()+"","123",1);
        System.out.println(md5Hash.toString());
    }

    @Test
    public void test3(){
        System.out.println(new Date().getTime());
    }
}
