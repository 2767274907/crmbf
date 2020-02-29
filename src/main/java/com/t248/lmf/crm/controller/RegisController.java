package com.t248.lmf.crm.controller;

import com.t248.lmf.crm.entity.User;
import com.t248.lmf.crm.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
public class RegisController {


    @Resource
    private IUserService userService;

    @RequestMapping("/regist")
    public String regist(){
        return "regist";
    }

    @RequestMapping("/regist/user")
    @ResponseBody
    public Map getUser(String userName){
        User user = userService.getUserByName(userName);
        Map map = new HashMap();
        map.put("flag","true");
        return map;
    }




}
