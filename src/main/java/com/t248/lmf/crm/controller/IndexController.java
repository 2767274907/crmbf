package com.t248.lmf.crm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping("/login")
    public String index(){
        return "login";
    }

    @RequestMapping("/main")
    public String main(){
        return "main";
    }

    @RequestMapping("/403")
    public String unauthorizedRole(){
        System.out.println("------------没有权限------------");
        return "403";
    }

}
