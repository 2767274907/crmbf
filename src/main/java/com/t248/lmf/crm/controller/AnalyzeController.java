package com.t248.lmf.crm.controller;

import com.github.abel533.echarts.*;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Orient;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.series.Bar;
import com.github.abel533.echarts.series.Pie;
import com.t248.lmf.crm.entity.CstLost;
import com.t248.lmf.crm.entity.Customer;
import com.t248.lmf.crm.entity.Orders;
import com.t248.lmf.crm.entity.OrdersLine;
import com.t248.lmf.crm.repository.DictRepostitor;
import com.t248.lmf.crm.service.AnalyzeService;
import com.t248.lmf.crm.service.CustomerService;
import com.t248.lmf.crm.util.ViewPDF;
import com.t248.lmf.crm.vo.DictInfo;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

@Controller
public class AnalyzeController {
    @Resource
    AnalyzeService analyzeService;
    @Resource
    CustomerService customerService;
    @Resource
    DictRepostitor dictRepostitor;

    @RequestMapping("/analyze/gxlist")
    public String gxlist(@RequestParam(required = false)String custName,
                         Model model,
                         @RequestParam(required = false, defaultValue = "1") int pageIndex){

        Sort sort = Sort.by(Sort.Direction.ASC,"custId");
        Pageable pageable = PageRequest.of(pageIndex-1,5,sort);
        Page<Customer> customerPage = analyzeService.getCustomer(custName,pageable);

        Map<String,Double> preMap = new HashMap<String,Double>();
        for (Customer customer:
             customerPage.getContent()) {
            Double ds = new Double(0);
            for (Orders orders:customer.getOrderss()){
                for (OrdersLine line:
                     orders.getOrdersLines()) {
                    ds+=line.getOddPrice();
                }
            }
            if(ds<=0){
                ds=null;
                System.out.println(00000);
            }
            preMap.put(customer.getCustId()+"",ds);
        }
        model.addAttribute("customerPage",customerPage);
        model.addAttribute("preMap",preMap);
        model.addAttribute("custName",custName);
        return "analyze/gxlist";
    }
    @RequestMapping("/analyze/gclist")
    public String gclist(Model model,
                         @RequestParam(required = false, defaultValue = "1") int pageIndex){
        List<DictInfo> list = dictRepostitor.getDictLeve();
        model.addAttribute("list",list);
        return "analyze/gclist";
    }
    @RequestMapping("/analyze/fwlist")
    public String fwlist(Model model,
                         @RequestParam(required = false, defaultValue = "1") int pageIndex){
        List<DictInfo> list = dictRepostitor.getDicttype();
        model.addAttribute("list",list);
        return "analyze/fwlist";
    }

    @RequestMapping("/analyze/pie")
    @ResponseBody
    public Option getEchartsPieOption(){
        List<Customer> list = customerService.getCustomers();
        Option option = new Option();
        //标题
        Title title = new Title();
        title.setText("客户贡献统计表");
        title.setX("center");
        option.setTitle(title);

        //提示框
        Tooltip tooltip = new Tooltip();
        tooltip.setTrigger(Trigger.item);
        tooltip.formatter("{a} <br/>{b} : {c} ({d}%)");
        option.setTooltip(tooltip);

        //Legend
        Legend legend = new Legend();
        List<String> legendDataList = new ArrayList<>();
        legend.setOrient(Orient.vertical);
        legend.setLeft("left");
        legend.setData(legendDataList);
        option.setLegend(legend);

        //饼状图
        Pie pie = new Pie();
        //对数据进行简单处理
        List<Map> mapList = new ArrayList<>();
        for (Customer customer:
                list) {
            Double ds = new Double(0);
            Map<String,String> addMap = new HashMap<>();
            addMap.put("name",customer.getCustName());
            for (Orders orders:customer.getOrderss()){
                for (OrdersLine line:
                        orders.getOrdersLines()) {
                    ds+=line.getOddPrice();
                }
            }
            addMap.put("value",String.valueOf(ds));
            mapList.add(addMap);
        }
        //封装pie
        //将数据放在图表中
        pie.setData(mapList);
//        pie.setName("上映年代");
        //大小
        pie.setRadius("55%");
        //偏移量
        String [] centerArray = {"50%","60%"};
        pie.setCenter(centerArray);

        option.series(pie);

        return option;
    }

    @RequestMapping("/analyze/bar")
    @ResponseBody
    public Option getEchartsBar()
    {
        List<DictInfo> list = dictRepostitor.getDictLeve();
        //echarts option 对象
        Option option = new Option();
        option.title("客户构成统计图").tooltip(Trigger.axis).legend("人数");
        //y轴为值轴
        option.yAxis(new ValueAxis().boundaryGap(0d, 1));

        //x轴为类目轴
        CategoryAxis category = new CategoryAxis();
        //柱状数据
        Bar bar = new Bar("人数");
        //循环数据
        for (DictInfo m : list) {
            //设置类目
            category.data(m.getName());
            //类目对应的柱状图
            bar.data(m.getCount());
        }

        Grid grid = new Grid();
        grid.setLeft("20%");
        grid.setWidth("60%");
        option.setGrid(grid);
        option.xAxis(category);
        option.series(bar);

        return option;
    }
    @RequestMapping("/analyze/fw")
    @ResponseBody
    public Option getEchartsfw()
    {
        List<DictInfo> list = dictRepostitor.getDicttype();
        //echarts option 对象
        Option option = new Option();
        option.title("客户服务统计图").tooltip(Trigger.axis).legend("服务数量");
        //y轴为值轴
        option.yAxis(new ValueAxis().boundaryGap(0d, 1));

        //x轴为类目轴
        CategoryAxis category = new CategoryAxis();
        //柱状数据
        Bar bar = new Bar("服务数量");
        //循环数据
        for (DictInfo m : list) {
            //设置类目
            category.data(m.getName());
            //类目对应的柱状图
            bar.data(m.getCount());
        }

        Grid grid = new Grid();
        grid.setLeft("20%");
        grid.setWidth("60%");
        option.setGrid(grid);
        option.xAxis(category);
        option.series(bar);

        return option;
    }


    @RequestMapping("/analyze/lslist")
    public String listls(@RequestParam(required = false)String custName,
                         @RequestParam(required = false)String custManagerName,
                         Model model,
                         @RequestParam(required = false, defaultValue = "1") int pageIndex){

        Sort sort = Sort.by(Sort.Direction.ASC,"lstId");
        Pageable pageable = PageRequest.of(pageIndex-1,5,sort);

        Page<CstLost> cstLosts = customerService.findLost(custName,custManagerName,"确认流失",pageable);

        model.addAttribute("cstLosts",cstLosts);

        model.addAttribute("custName",custName);
        model.addAttribute("custManagerName",custManagerName);
        return "analyze/lslist";
    }

    @RequestMapping(value = "/analyze/Excel/{type}",method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    public void gxExcel(HttpServletResponse response,@PathVariable String type){
        XSSFWorkbook wb = null;
        String fileName = "";
        if(type.equals("gx")){
            wb = analyzeService.showKH();
            fileName = "客户贡献分析.xlsx";
        }else if(type.equals("gc")){
            wb = analyzeService.showGC();
            fileName = "客户构成分析.xlsx";
        }else if(type.equals("fw")){
            wb = analyzeService.showFW();
            fileName = "客户服务分析.xlsx";
        }
        OutputStream outputStream =null;
        try {
            fileName = URLEncoder.encode(fileName,"UTF-8");
            //设置ContentType请求信息格式
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            outputStream = response.getOutputStream();
            wb.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value="/analyze/PDF/{type}",method = RequestMethod.GET,produces="application/json;charset=UTF-8")
    public ModelAndView printPdf(@PathVariable String type) throws Exception{
        Map<String, Object> model = new HashMap<>();
        if(type.equals("gx")){
            List<Customer> customers = customerService.getCustomers();
            model.put("sheet", customers);
        }else if(type.equals("gc")){
            List<DictInfo> dictLeve = dictRepostitor.getDictLeve();
            model.put("sheet", dictLeve);
        }else if(type.equals("fw")){
            List<DictInfo> dicttype = dictRepostitor.getDicttype();
            model.put("sheet", dicttype);
        }
        return new ModelAndView(new ViewPDF(), model);
    }
}
