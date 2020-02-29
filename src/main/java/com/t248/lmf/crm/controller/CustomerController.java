package com.t248.lmf.crm.controller;

import com.t248.lmf.crm.entity.*;
import com.t248.lmf.crm.repository.DictRepostitor;
import com.t248.lmf.crm.service.AnalyzeService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CustomerController {

    @Resource
    CustomerService customerService;
    @Resource
    private DictRepostitor dictRepostitor;
    @Resource
    IUserService userService;

    @Resource
    AnalyzeService analyzeService;


    @RequestMapping("/customer/list")
    public String list(@RequestParam(required = false)String custName,
                       @RequestParam(required = false)String custNo,
                       @RequestParam(required = false)String custRegion,
                       @RequestParam(required = false)String custManagerName,
                       @RequestParam(required = false)String custLevel,
                       Model model,
                       @RequestParam(required = false, defaultValue = "1") int pageIndex){

        Sort sort = Sort.by(Sort.Direction.ASC,"custId");
        Pageable pageable = PageRequest.of(pageIndex-1,5,sort);
        Page<Customer> customerPage = customerService.findCustomer(custName,custNo,custRegion,custManagerName,custLevel,pageable);

        model.addAttribute("customerPage",customerPage);

        List<Dict> dqDictis = dictRepostitor.findDictsByDictType("地区");
        model.addAttribute("dqDictis",dqDictis);

        List<Dict> lvDictis = dictRepostitor.findDictsByDictType("客户等级");
        model.addAttribute("lvDictis",lvDictis);

        model.addAttribute("custName",custName);
        model.addAttribute("custNo",custNo);
        model.addAttribute("custRegion",custRegion);
        model.addAttribute("custManagerName",custManagerName);
        model.addAttribute("custLevel",custLevel);
        return "customer/list";
    }

    @RequestMapping("customer/edit")
    public String edit(Long custId, Model model){
        Customer customer = customerService.getCustomer(custId);
        model.addAttribute("customer",customer);

        String [] xingxing  =  new String[]{"★","★★","★★★","★★★★","★★★★★"};
        model.addAttribute("xingxing",xingxing);

        List<Dict> dqDictis = dictRepostitor.findDictsByDictType("地区");
        model.addAttribute("dqDictis",dqDictis);

        List<Dict> lvDictis = dictRepostitor.findDictsByDictType("客户等级");
        model.addAttribute("lvDictis",lvDictis);
        return "customer/edit";
    }

    @RequestMapping("/customer/del")
    @ResponseBody
    public Map delcust(String custNo){
        Map<String,String> map = new HashMap<String,String>();
        try{
            System.out.println("\n\n"+custNo+"\n\n");
            customerService.delCustomer(custNo);
            map.put("delResult","true");
        }catch(Exception e){
            e.printStackTrace();
            map.put("delResult","false");
        }
        return map;
    }

    @RequestMapping("customer/save")
    public String save(Customer customer,Long usrId){
        customer.setUser(userService.getUser(usrId));
        customer.setCustStatus("1");
        customer.setCustLevelLabel(dictRepostitor.findDictsByDictValue(customer.getCustLevel()+"").getDictItem());
        customerService.saveCustomer(customer);
        return  "redirect:/customer/list";
    }







    @RequestMapping("/customer/lkm/list")
    public String linkList(String custNo,Model model){
        List<Linkman> linkManList = customerService.findLinkManList(custNo);
        System.out.println("\n\n\n\n"+linkManList.size()+"\n\n\n\n");
        Customer customer = customerService.getCustomer(custNo);
        model.addAttribute("customer",customer);
        model.addAttribute("linkManList",linkManList);
        return "customer/lkm/list";
    }

    @RequestMapping("/customer/lkm/edit")
    public String editLkm(Long lkmId,Model model){
        Linkman linkMan = customerService.getlink(lkmId);
        model.addAttribute("linkMan",linkMan);
        return "customer/lkm/edit";
    }

    @RequestMapping("/customer/lkm/add")
    public String addLkm(String custNo,Model model){
        Customer customer = customerService.getCustomer(custNo);
        model.addAttribute("customer",customer);
        return "customer/lkm/add";
    }

    @RequestMapping("/customer/lkm/save")
    public String saveLkm(Linkman linkman,String lkmCustNo){
        linkman.setCustomer(customerService.getCustomer(lkmCustNo));
        customerService.savelink(linkman);
        return "redirect:/customer/lkm/list?custNo="+lkmCustNo;
    }

    @RequestMapping("/customer/lkm/del")
    @ResponseBody
    public Map delLkm(Long lkmId){
        Map<String,String> map = new HashMap<String,String>();
        try{
            System.out.println("\n\n"+lkmId+"\n\n");
            customerService.dellink(lkmId);
            map.put("delResult","true");
        }catch(Exception e){
            e.printStackTrace();
            map.put("delResult","false");
        }
        return map;
    }





    @RequestMapping("/customer/atv/list")
    public String atvList(String custNo,Model model){
        List<Activity> activityList = customerService.findActivityList(custNo);
        Customer customer = customerService.getCustomer(custNo);
        model.addAttribute("customer",customer);
        model.addAttribute("activityList",activityList);
        return "customer/atv/list";
    }

    @RequestMapping("/customer/atv/edit")
    public String editAtv(Long atvId,Model model){
        Activity activity = customerService.getActivity(atvId);
        model.addAttribute("activity",activity);
        return "customer/atv/edit";
    }

    @RequestMapping("/customer/atv/add")
    public String addAtv(String custNo,Model model){
        Customer customer = customerService.getCustomer(custNo);
        model.addAttribute("customer",customer);
        return "customer/atv/add";
    }

    @RequestMapping("/customer/atv/save")
    public String saveatv(Activity activity,String atvCustNo){
        activity.setCustomer(customerService.getCustomer(atvCustNo));
        customerService.saveActivity(activity);
        return "redirect:/customer/atv/list?custNo="+atvCustNo;
    }

    @RequestMapping("/customer/atv/del")
    @ResponseBody
    public Map delAtv(Long atvId){
        Map<String,String> map = new HashMap<String,String>();
        try{
            System.out.println("\n\n"+atvId+"\n\n");
            customerService.delActivity(atvId);
            map.put("delResult","true");
        }catch(Exception e){
            e.printStackTrace();
            map.put("delResult","false");
        }
        return map;
    }



    @RequestMapping("/customer/odr/list")
    public String ordersList(String custNo,
                             @RequestParam(required = false, defaultValue = "1") int pageIndex,
                             Model model){

        Sort sort = Sort.by(Sort.Direction.ASC,"odrId");
        Pageable pageable = PageRequest.of(pageIndex-1,5,sort);

        Page<Orders> ordersPage = customerService.findOrdersLine(custNo,pageable);

        Customer customer = customerService.getCustomer(custNo);
        model.addAttribute("customer",customer);
        model.addAttribute("ordersPage",ordersPage);
        return "customer/odr/list";
    }
    @RequestMapping("/customer/odr/edit")
    public String editOdr(Long odrId,Model model){
        Orders orders = customerService.findOrders(odrId);
        model.addAttribute("orders",orders);
        return "customer/odr/edit";
    }





    @RequestMapping("/lost/list")
    public String listls(@RequestParam(required = false)String custName,
                       @RequestParam(required = false)String custManagerName,
                       @RequestParam(required = false)String custStatus,
                       Model model,
                       @RequestParam(required = false, defaultValue = "1") int pageIndex){

        Sort sort = Sort.by(Sort.Direction.ASC,"lstId");
        Pageable pageable = PageRequest.of(pageIndex-1,5,sort);

        Page<CstLost> cstLosts = customerService.findLost(custName,custManagerName,custStatus,pageable);

        model.addAttribute("cstLosts",cstLosts);

        model.addAttribute("custName",custName);
        model.addAttribute("custManagerName",custManagerName);
        model.addAttribute("custStatus",custStatus);
        return "lost/list";
    }
    @RequestMapping("/lost/save")
    public String saveatv(CstLost cstLost){
        customerService.save(cstLost);
        return "redirect:/lost/list";
    }
    @RequestMapping("/lost/edit1")
    public String edit1(Long lstId,Model model){
        CstLost cstLost = customerService.getLost(lstId);
        model.addAttribute("cstLost",cstLost);
        return "lost/edit1";
    }
    @RequestMapping("/lost/edit2")
    public String edit2(Long lstId,Model model){
        CstLost cstLost = customerService.getLost(lstId);
        model.addAttribute("cstLost",cstLost);
        return "lost/edit2";
    }



}
