package com.t248.lmf.crm.controller;

import com.t248.lmf.crm.entity.Right;
import com.t248.lmf.crm.entity.Role;
import com.t248.lmf.crm.entity.User;
import com.t248.lmf.crm.service.IRoleService;
import com.t248.lmf.crm.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Controller
public class UserController {
    @Resource
    private IUserService userService;

    @Resource
    private IRoleService roleService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private String SHIRO_LOGIN_COUNT="shiro_login_count_";

    private String SHIRO_IS_LOCK = "shiro_is_lock_";

    @RequestMapping("/dologin")
    public String dologin(String usrName, String usrPassword, Model model, HttpSession session){
//        User user = userService.login(usrName, usrPassword);
//        if(user!=null){
//            session.setAttribute("User",user);
//            return "main";
//        }else{
//            model.addAttribute("msg","用户名或密码错误");
//            return "login";
//        }
        User user = null;
        try{
            AuthenticationToken token = new UsernamePasswordToken(usrName,usrPassword);
            //限制登录次数
            ValueOperations<String,String> opsForValue = stringRedisTemplate.opsForValue();
            opsForValue.increment(SHIRO_LOGIN_COUNT+usrName,1);
            if(Integer.parseInt(opsForValue.get(SHIRO_LOGIN_COUNT+usrName))>5){
                opsForValue.set(SHIRO_IS_LOCK+usrName,"LOCK");
                stringRedisTemplate.expire(SHIRO_IS_LOCK+usrName,1, TimeUnit.HOURS);
            }
            if ("LOCK".equals(opsForValue.get(SHIRO_IS_LOCK+usrName))){
                throw new DisabledAccountException("由于输入错误大于5次，账号冻结1小时");
            }
            SecurityUtils.getSubject().login(token);
            //登录成功清除次数
            opsForValue.set(SHIRO_LOGIN_COUNT+usrName,"0");
            user = (User)SecurityUtils.getSubject().getPrincipal();
            session.setAttribute("User",user);
        }catch (IncorrectCredentialsException i){
            i.printStackTrace();
            model.addAttribute("msg","密码错误："+i.getMessage());
            return "login";
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("msg",e.getMessage());
            return "login";
        }
        Map<String,List<Right>> map = new HashMap<>();
        List<Right> rights = userService.findByRightParentCode("ROOT_MENU");
        for (Right r :
                rights) {
            map.put(r.getRightCode(),userService.findByRightParentCode(r.getRightCode()));
        }
        session.setAttribute("rights",rights);
        session.setAttribute("map",map);
        return "main";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("User");

        SecurityUtils.getSubject().logout();
        return "login";
    }

    @RequestMapping("/user/list")
    public String findUsers(@RequestParam(required = false,defaultValue = "0") Long roleId,
                            @RequestParam(required = false,defaultValue = "1") int pageIndex,
                                        String usrName, Model model){
        //Sort sort = new Sort(Sort.Direction.ASC,"usrId");此写法只适用于2.1版本前的。
        //在下使用的是2.2.*版本的
        Sort sort = Sort.by(Sort.Direction.ASC,"usrId");
        Pageable pageable = PageRequest.of(pageIndex-1,5,sort);

        Page<User> userPager = userService.findUsers(usrName,roleId,pageable);
        model.addAttribute("userPager",userPager);
        model.addAttribute("usrName",usrName);
        model.addAttribute("roleId",roleId);
        List<Role> roles = roleService.findAllRoles();
        model.addAttribute("roles",roles);
        return "user/list";
    }

    @RequestMapping("/user/add")
    public String add(Model model){
        List<Role> roles = roleService.findAllRoles();
        model.addAttribute("roles",roles);
        return "user/add";
    }

    @RequestMapping("/user/save")
    public String save(User user){
        userService.addUser(user);
        return "redirect:/user/list";
    }

    @RequestMapping("/user/update")
    public String update(User user){
        userService.updateUser(user);
        return "redirect:/user/list";
    }

    @RequestMapping("/user/edit")
    public String edit(Long usrId,Model model){
        User user = userService.getUser(usrId);
        model.addAttribute("user",user);
        List<Role> roles = roleService.findAllRoles();
        model.addAttribute("roles",roles);
        return "user/edit";
    }

    @RequestMapping(value = "/user/del")
    @ResponseBody
    public Map del(Long usrId){
        userService.deleteUser(usrId);
        Map map = new HashMap();
        map.put("delResult","true");
        return map;
    }

}
