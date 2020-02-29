package com.t248.lmf.crm.controller;

import com.t248.lmf.crm.entity.Dict;
import com.t248.lmf.crm.entity.Product;
import com.t248.lmf.crm.entity.Storage;
import com.t248.lmf.crm.service.DictService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
public class BasicController {

    @Resource
    DictService dictService;

    @RequestMapping("/basic/gl/list/{type}")
    public String djList(@RequestParam(required = false) String dictItem, Model model,
                         @PathVariable String type,
                         @RequestParam(required = false, defaultValue = "1") int pageIndex){
        String t = "";
        String path = "";
        if(type.equals("kh")){
            path = "basic/khdj/list";
            t = "客户等级";
        }else if(type.equals("fw")){
            path = "basic/fwlx/list";
            t = "服务类型";
        }else if(type.equals("dq")){
            path = "basic/khdq/list";
            t = "地区";
        }

        Sort sort = Sort.by(Sort.Direction.ASC,"dictValue");
        Pageable pageable = PageRequest.of(pageIndex-1,5,sort);
        Page<Dict> dicts = dictService.getDicts(dictItem,t,pageable);

        model.addAttribute("dictItem",dictItem);
        model.addAttribute("dicts",dicts);
        return path;
    }

    @RequestMapping("/basic/gl/add/{type}")
    public String djadd(@PathVariable String type){
        String path = "";
        if(type.equals("kh")){
            path = "basic/khdj/add";
        }else if(type.equals("fw")){
            path = "basic/fwlx/add";
        }else if(type.equals("dq")){
            path = "basic/khdq/add";
        }
        return path;
    }

    @RequestMapping("/basic/gl/edit/{type}")
    public String djedit(@PathVariable String type,Model model,Long dictId){
        Dict dict = dictService.getDict(dictId);
        String path = "";
        if(type.equals("kh")){
            path = "basic/khdj/edit";
        }else if(type.equals("fw")){
            path = "basic/fwlx/edit";
        }else if(type.equals("dq")){
            path = "basic/khdq/edit";
        }
        model.addAttribute("dict",dict);
        return path;
    }

    @RequestMapping("/basic/gl/save/{type}")
    public String djsave(@PathVariable String type,Dict dict){
        String path = "";
        dict.setDictIsEditable(1L);
        if(type.equals("kh")){
            if(dict.getDictId()==null){
                dict.setDictValue(dictService.getvaluemax()+1+"");
                dict.setDictType("客户等级");
            }
            path = "redirect:/basic/gl/list/kh";
        }else if(type.equals("fw")){
            dict.setDictType("服务类型");
            dict.setDictValue(dict.getDictItem());
            path = "redirect:/basic/gl/list/fw";
        }else if(type.equals("dq")){
            dict.setDictType("地区");
            dict.setDictValue(dict.getDictItem());
            path = "redirect:/basic/gl/list/dq";
        }
        dictService.save(dict);
        return path;
    }

    @RequestMapping("/basic/gl/del")
    @ResponseBody
    public Map djdel(Model model, Long dictId){
        Map<String,String> map = new HashMap<String,String>();
        try{
            System.out.println("\n\n"+dictId+"\n\n");
            dictService.deleteDict(dictId);
            map.put("delResult","true");
        }catch(Exception e){
            e.printStackTrace();
            map.put("delResult","false");
        }
        return map;
    }


    @RequestMapping("/basic/cpxx/list")
    public String cpList(@RequestParam(required = false) String prodName,
                         @RequestParam(required = false) String prodType,
                         @RequestParam(required = false) String prodBatch,
                         Model model,
                         @RequestParam(required = false, defaultValue = "1") int pageIndex){

        Sort sort = Sort.by(Sort.Direction.ASC,"prodId");
        Pageable pageable = PageRequest.of(pageIndex-1,5,sort);
        Page<Product> productPage = dictService.getProducts(prodName,prodType,prodBatch,pageable);

        model.addAttribute("productPage",productPage);
        model.addAttribute("prodName",prodName);
        model.addAttribute("prodType",prodType);
        model.addAttribute("prodBatch",prodBatch);
        return "basic/product/cpxxlist";
    }

    @RequestMapping("/basic/kc/list")
    public String kcList(@RequestParam(required = false) String prodName,
                         @RequestParam(required = false) String stkWarehouse,
                         Model model,
                         @RequestParam(required = false, defaultValue = "1") int pageIndex){

        Sort sort = Sort.by(Sort.Direction.ASC,"stkId");
        Pageable pageable = PageRequest.of(pageIndex-1,5,sort);
        Page<Storage> storagePage = dictService.getStorage(prodName,stkWarehouse,pageable);

        model.addAttribute("storagePage",storagePage);
        model.addAttribute("prodName",prodName);
        model.addAttribute("stkWarehouse",stkWarehouse);
        return "basic/product/kclist";
    }

}
