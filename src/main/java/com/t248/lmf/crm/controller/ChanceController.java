package com.t248.lmf.crm.controller;

import com.t248.lmf.crm.entity.Chance;
import com.t248.lmf.crm.entity.Dict;
import com.t248.lmf.crm.entity.Role;
import com.t248.lmf.crm.entity.User;
import com.t248.lmf.crm.repository.DictRepostitor;
import com.t248.lmf.crm.repository.UserRepository;
import com.t248.lmf.crm.service.ChanceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ChanceController {

    @Resource
    private ChanceService chanceService;
    @Resource
    private DictRepostitor dictRepostitor;
    @Resource
    private UserRepository userRepository;
    @RequestMapping(value = "/sal/list")
    public String list(@RequestParam(required = false) String chcCustName,
                       @RequestParam(required = false) String chcTitle, Model model,
                       @RequestParam(required = false, defaultValue = "1") int pageIndex){
        Sort sort = Sort.by(Sort.Direction.ASC,"chcId");
        Pageable pageable = PageRequest.of(pageIndex-1,5,sort);

        Page<Chance> chancePager = chanceService.findChances("未分配","eq",chcCustName,chcTitle,null,pageable);
        model.addAttribute("chancePager",chancePager);
        model.addAttribute("chcCustName",chcCustName);
        model.addAttribute("chcTitle",chcTitle);

        return "sal/list";
    }

    @RequestMapping("/sal/add")
    public String add(Model model){
        List<Dict> dqDictis = dictRepostitor.findDictsByDictType("地区");
        model.addAttribute("dqDictis",dqDictis);

        List<Dict> lvDictis = dictRepostitor.findDictsByDictType("客户等级");
        model.addAttribute("lvDictis",lvDictis);

        List<User> users = userRepository.findByRole(new Role(2L));
        model.addAttribute("users",users);
        return "sal/add";
    }

    @RequestMapping("/sal/save")
    public String save(Chance chance, HttpSession session){
        User user = (User) session.getAttribute("User");
        chance.setChcCreateId(user.getUsrId());
        chance.setChcCreateBy(user.getUsrName());
        chance.setChcCreateDate(new Date());
        System.out.println(chance.toString());
        if (chance.getChcId()==null){
            chance.setChcStatus("未分配");
        }
        chanceService.save(chance);
//        return "forward:/sal/list";
        return "redirect:/sal/list";
    }

    @RequestMapping("/sal/edit")
    public String edit(Model model,Long chcId){
        List<Dict> dqDictis = dictRepostitor.findDictsByDictType("地区");
        model.addAttribute("dqDictis",dqDictis);

        List<Dict> lvDictis = dictRepostitor.findDictsByDictType("客户等级");
        model.addAttribute("lvDictis",lvDictis);

        Chance chance = chanceService.getChance(chcId);
        model.addAttribute("chance",chance);
        return "sal/edit";
    }

    @RequestMapping("/sal/update")
    public String update(Chance chance, HttpSession session){
        System.out.println(chance.getChcDueDate());
        if(chance.getChcDueId()!=null){
            chance.setChcDueTo(userRepository.findByUsrId(chance.getChcDueId()).getUsrName());
            System.out.println( chance.getChcDueTo());
            chance.setChcStatus("开发中");
        }else{
            chance.setChcStatus("未分配");
            chance.setChcDueDate(null);
        }
        chanceService.update(chance);
        return "redirect:/sal/list";
    }

    @RequestMapping("/sal/del")
    @ResponseBody
    public Object del(Long chcId){
        Map<String,String> map = new HashMap<String,String>();
        try{
            System.out.println("\n\n"+chcId+"\n\n");
            chanceService.del(chcId);
            map.put("delResult","true");
        }catch(Exception e){
            e.printStackTrace();
            map.put("delResult","false");
        }
        return map;
    }

    @RequestMapping("/sal/upd")
    public String upd(Long chcId,Model model){
        Chance chance = chanceService.getChance(chcId);
        model.addAttribute("chance",chance);
        model.addAttribute("lv",dictRepostitor.findDictsByDictValue(chance.getChcClientGrade()));

        List<User> users = userRepository.findByRole(new Role(2L));
        model.addAttribute("users",users);
        return "sal/upd";
    }

}
