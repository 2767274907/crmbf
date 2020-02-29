package com.t248.lmf.crm.config.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.t248.lmf.crm.entity.Right;
import com.t248.lmf.crm.service.IRoleService;
import com.t248.lmf.crm.service.IUserService;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class ShiorConfig {

    @Resource
    private IUserService userServer;

    @Resource
    private IRoleService roleService;

    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
//    @Value("${spring.redis.password}")
//    private String password;
    @Value("${spring.redis.timeout}")
    private int timeout;

    /**
     * 创建ShiroFilterFactoryBean
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager){
        System.out.println("ShiroConfiguration.shiroFilter():配置权限控制规则");

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //如果为授权会自动寻找Web工程项目下的/login.jsp页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        //登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/main");
        //未授权界面
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        /**
         * 常见过滤器：
         * anon：无需（登录）认证即可访问
         * authc：必须认证才可以访问
         * user：如果使用Remember Me的功能可以直接访问
         * perms：该资源必须得到资源权限才可以访问
         * role：该资源必须得到角色权限才可以访问
         */
        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<String,String>();

        filterChainDefinitionMap.put("/css/**","anon");
        filterChainDefinitionMap.put("/fonts/**","anon");
        filterChainDefinitionMap.put("/images/**","anon");
        filterChainDefinitionMap.put("/js/**","anon");
        filterChainDefinitionMap.put("/localcss/**","anon");
        filterChainDefinitionMap.put("/dologin","anon");
        filterChainDefinitionMap.put("/regist","anon");
        //配置退出过滤器，其中的具体的退出代码Shiro已替我们实现了
        filterChainDefinitionMap.put("/logout","logout");

        //配置测试权限（真实权限因从数据库中获得）
//        filterChainDefinitionMap.put("/user/list","perms[用户管理]");
//        filterChainDefinitionMap.put("/user/add","perms[用户添加]");
//        filterChainDefinitionMap.put("/role/list","perms[角色管理]");
        List<Right> rights = roleService.findAllRights();
        for (Right right : rights){
//            if (right.getRightType().equals("Folder")&&(right.getRightType().equals("Button"))){
            if (!right.getRightUrl().trim().equals("")){
                System.out.println("过滤器拦截的url："+right.getRightUrl()+",以及对应需" +
                        "要访问的权限："+right.getRightText());
                filterChainDefinitionMap.put(right.getRightUrl(),"perms["+right.getRightText()+"]");
            }
        }
        //过滤链定义，从上向下的顺序执行，一般将/**放在最下边--》：此为一个坑，一不小心代码就不好使了
        //authc：所有的url都必须通过认证才可以访问；anon：所有url都可以匿名访问
        filterChainDefinitionMap.put("/**","authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

    /**
     * 创建Realm
     * @return
     */
    @Bean
    public MyShiroRealm myShiroRealm(){
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        //告诉reaml，使用credentialsMatcher加密算法类来验证秘闻
        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        //启动缓存及设置缓存名称
        myShiroRealm.setCachingEnabled(true);
        myShiroRealm.setAuthorizationCachingEnabled(true);
        myShiroRealm.setAuthorizationCacheName("authorizationCache");
        return myShiroRealm;
    }

    /**
     * 创建DefaultWebSecurityManager
     * @return
     */
    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //设置reaml
        securityManager.setRealm(myShiroRealm());
        //自定义缓存实现 使用redis
        securityManager.setCacheManager(cacheManager());
        //自定义session管理 使用redis
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    @Bean(name="shiroDialect")
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }

    /**
     * 配置shiro redisManager
     * 使用开源的shiro-redis插件
     *
     * @return
     */
    public RedisManager redisManager(){
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);
//        redisManager.setPassword(password);
        redisManager.setTimeout(timeout);
        return redisManager;
    }

    /**
     * cacheManager缓存redis实现
     * 使用开源的shiro-redis插件
     *
     * @return
     */
    public RedisCacheManager cacheManager(){
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());

        redisCacheManager.setPrincipalIdFieldName("usrName");

        redisCacheManager.setExpire(1800);
        return redisCacheManager;
    }

    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     * 使用开源的shiro-redis插件
     *
     * @return
     */
    @Bean
    public RedisSessionDAO redisSessionDAO(){
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }

    /**
     * Session Manager
     * 使用开源的shiro-redis插件
     *
     * @return
     */
    @Bean
    public DefaultWebSessionManager sessionManager(){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO());
        return sessionManager;
    }

    /**
     * 告诉shiro是经过加密的
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        System.out.println("hashedCredentialsMatcher...............");
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        //使用md5算法进行加密
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        //设置散列次数：也就是加密几次
        hashedCredentialsMatcher.setHashIterations(1);
        return hashedCredentialsMatcher;
    }

}
