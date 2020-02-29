package com.t248.lmf.crm.controller;

import com.t248.lmf.crm.service.ChanceService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
public class PlanController {

    @Resource
    private ChanceService chanceService;

    @RequestMapping("/plan/upd")
    @ResponseBody
    public Object upd(String plaTodo,String plaResult,Long plaId){
        Map<String,String> map = new HashMap<String,String>();
        try{
            System.out.println("\n\n"+plaId+"\n\n");
            System.out.println("\n\n"+plaTodo+"\n\n");
            System.out.println("\n\n"+plaResult+"\n\n");
            chanceService.planupd(plaId,plaTodo,plaResult);
            map.put("delResult","true");
        }catch(Exception e){
            e.printStackTrace();
            map.put("delResult","false");
        }
        return map;
    }

    @RequestMapping("/plan/del")
    @ResponseBody
    public Object del(Long plaId){
        Map<String,String> map = new HashMap<String,String>();
        try{
            System.out.println("\n\n"+plaId+"\n\n");
            chanceService.plandel(plaId);
            map.put("delResult","true");
        }catch(Exception e){
            e.printStackTrace();
            map.put("delResult","false");
        }
        return map;
    }


}
