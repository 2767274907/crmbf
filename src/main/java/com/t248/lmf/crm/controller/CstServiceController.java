package com.t248.lmf.crm.controller;


import com.t248.lmf.crm.entity.CstService;
import com.t248.lmf.crm.entity.Customer;
import com.t248.lmf.crm.entity.Role;
import com.t248.lmf.crm.entity.User;
import com.t248.lmf.crm.repository.DictRepostitor;
import com.t248.lmf.crm.service.CstServiceService;
import com.t248.lmf.crm.service.CustomerService;
import com.t248.lmf.crm.service.IUserService;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CstServiceController {

    @Resource
    CustomerService customerService;

    @Resource
    CstServiceService serviceService;

    @Resource
    IUserService userService;

    @Resource
    DictRepostitor dictRepostitor;


    @RequestMapping("/service/addsiv")
    public String addsvc(Model model){
        List<Customer> customers = customerService.getCustomers();
        model.addAttribute("customers",customers);
        model.addAttribute("svrTypes",dictRepostitor.findDictsByDictType("服务类型"));
        return "service/addsiv";
    }

    @RequestMapping("/service/save")
    public String savesvc(CstService cstService){
        if(cstService.getSvrId()==null){
            Customer customer = customerService.getCustomer(cstService.getSvrCustNo());
            cstService.setSvrCustName(customer.getCustName());
            serviceService.saveService(cstService);
            return "redirect:/service/addsiv";
        }else{
            if(cstService.getSvrSatisfy()!=null&&cstService.getSvrSatisfy()>=3){
                System.out.println("已归档");
                cstService.setSvrStatus("已归档");
                serviceService.saveService(cstService);
                return "redirect:/service/listfk";
            }else if(cstService.getSvrSatisfy()!=null&&cstService.getSvrSatisfy()<3){
                cstService.setSvrStatus("已分配");
                System.out.println("已分配");
                serviceService.saveService(cstService);
                return "redirect:/service/listfk";
            }
            serviceService.saveService(cstService);
            return "redirect:/service/listcl";
        }
    }

    @RequestMapping("/service/listsiv")
    public String svcList(@RequestParam(required = false) String svrCustName,
                          @RequestParam(required = false) String svrType,
                          @RequestParam(required = false) String svrTitle,  Model model,
                          @RequestParam(required = false, defaultValue = "1") int pageIndex){
        //排序条件
        Sort sort = Sort.by(Sort.Direction.ASC,"svrId");
        //分页所需要的类
        Pageable pageable = PageRequest.of(pageIndex-1,5,sort);

        Page<CstService> cstServicePage = serviceService.findServiceList(svrCustName,svrType,svrTitle,pageable,"新创建","eq");

        List<User> userList = userService.findByRole(new Role(2L));
        model.addAttribute("cstServicePage",cstServicePage);
        model.addAttribute("svrCustName",svrCustName);
        model.addAttribute("svrTitle",svrTitle);
        model.addAttribute("svrTypes",dictRepostitor.findDictsByDictType("服务类型"));
        model.addAttribute("svrType",svrType);
        model.addAttribute("userList",userList);
        return "service/listsiv";
    }

    @RequestMapping("/service/updsiv")
    @ResponseBody
    public Map updsiv(Long svrDueId,Long svrId){
        Map<String,String> map = new HashMap<String,String>();
        try{
            System.out.println("\n\n"+svrDueId+"-----"+svrId+"\n\n");
            CstService cstService = serviceService.getCstService(svrId);
            User user = userService.getUser(svrDueId);
            cstService.setSvrDueId(user.getUsrId());
            cstService.setSvrDueTo(user.getUsrName());
            cstService.setSvrDueDate(new Date());
            cstService.setSvrStatus("已分配");
            serviceService.saveService(cstService);
            map.put("delResult","true");
        }catch(Exception e){
            e.printStackTrace();
            map.put("delResult","false");
        }
        return map;
    }

    @RequestMapping("/service/delsiv")
    @ResponseBody
    public Map delsiv(Long svrId){
        Map<String,String> map = new HashMap<String,String>();
        try{
            System.out.println("\n\n"+svrId+"\n\n");
            serviceService.delService(svrId);
            map.put("delResult","true");
        }catch(Exception e){
            e.printStackTrace();
            map.put("delResult","false");
        }
        return map;
    }




    @RequestMapping("/service/listcl")
    public String clList(@RequestParam(required = false) String svrCustName,
                          @RequestParam(required = false) String svrType,
                          @RequestParam(required = false) String svrTitle,  Model model,
                          @RequestParam(required = false, defaultValue = "1") int pageIndex){
        //排序条件
        Sort sort = Sort.by(Sort.Direction.ASC,"svrId");
        //分页所需要的类
        Pageable pageable = PageRequest.of(pageIndex-1,5,sort);

        Page<CstService> cstServicePage = serviceService.findServiceList(svrCustName,svrType,svrTitle,pageable,"已分配","eq");

        List<User> userList = userService.findByRole(new Role(2L));
        model.addAttribute("cstServicePage",cstServicePage);
        model.addAttribute("svrCustName",svrCustName);
        model.addAttribute("svrTitle",svrTitle);
        model.addAttribute("svrTypes",dictRepostitor.findDictsByDictType("服务类型"));
        model.addAttribute("svrType",svrType);
        model.addAttribute("userList",userList);
        return "service/listcl";
    }

    @RequestMapping("/service/editcl")
    public String updcl(Long svrId,Model model){
        CstService cstService = serviceService.getCstService(svrId);
        model.addAttribute("cstService",cstService);
        return "service/editcl";
    }



    @RequestMapping("/service/listfk")
    public String fkList(@RequestParam(required = false) String svrCustName,
                         @RequestParam(required = false) String svrType,
                         @RequestParam(required = false) String svrTitle,  Model model,
                         @RequestParam(required = false, defaultValue = "1") int pageIndex){
        //排序条件
        Sort sort = Sort.by(Sort.Direction.ASC,"svrId");
        //分页所需要的类
        Pageable pageable = PageRequest.of(pageIndex-1,5,sort);

        Page<CstService> cstServicePage = serviceService.findServiceList(svrCustName,svrType,svrTitle,pageable,"已处理","eq");

        List<User> userList = userService.findByRole(new Role(2L));
        model.addAttribute("cstServicePage",cstServicePage);
        model.addAttribute("svrCustName",svrCustName);
        model.addAttribute("svrTitle",svrTitle);
        model.addAttribute("svrTypes",dictRepostitor.findDictsByDictType("服务类型"));
        model.addAttribute("svrType",svrType);
        model.addAttribute("userList",userList);
        return "service/listfk";
    }


    @RequestMapping("/service/editfk")
    public String updfk(Long svrId,Model model){
        CstService cstService = serviceService.getCstService(svrId);
        model.addAttribute("cstService",cstService);

        String [] xingxing  =  new String[]{"★","★★","★★★","★★★★","★★★★★"};
        model.addAttribute("xingxing",xingxing);
        return "service/editfk";
    }

    @RequestMapping("/service/listgd")
    public String gdList(@RequestParam(required = false) String svrCustName,
                         @RequestParam(required = false) String svrType,
                         @RequestParam(required = false) String svrTitle,  Model model,
                         @RequestParam(required = false, defaultValue = "1") int pageIndex){
        //排序条件
        Sort sort = Sort.by(Sort.Direction.ASC,"svrId");
        //分页所需要的类
        Pageable pageable = PageRequest.of(pageIndex-1,5,sort);

        Page<CstService> cstServicePage = serviceService.findServiceList(svrCustName,svrType,svrTitle,pageable,"已归档","eq");

        List<User> userList = userService.findByRole(new Role(2L));
        model.addAttribute("cstServicePage",cstServicePage);
        model.addAttribute("svrCustName",svrCustName);
        model.addAttribute("svrTitle",svrTitle);
        model.addAttribute("svrTypes",dictRepostitor.findDictsByDictType("服务类型"));
        model.addAttribute("svrType",svrType);
        model.addAttribute("userList",userList);
        return "service/listgd";
    }

    @RequestMapping("/service/editgd")
    public String updgd(Long svrId,Model model){
        CstService cstService = serviceService.getCstService(svrId);
        model.addAttribute("cstService",cstService);

        String [] xingxing  =  new String[]{"★","★★","★★★","★★★★","★★★★★"};
        model.addAttribute("xingxing",xingxing);
        return "service/editgd";
    }

}
