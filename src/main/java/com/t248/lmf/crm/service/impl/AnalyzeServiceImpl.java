package com.t248.lmf.crm.service.impl;

import com.t248.lmf.crm.entity.Customer;
import com.t248.lmf.crm.entity.Orders;
import com.t248.lmf.crm.entity.OrdersLine;
import com.t248.lmf.crm.repository.CustomerRepostitor;
import com.t248.lmf.crm.repository.DictRepostitor;
import com.t248.lmf.crm.service.AnalyzeService;
import com.t248.lmf.crm.vo.DictInfo;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class AnalyzeServiceImpl implements AnalyzeService {

    @Resource
    CustomerRepostitor customerRepostitor;
    @Resource
    DictRepostitor dictRepostitor;

    @Override
    public Page<Customer> getCustomer(String custName, Pageable pageable) {
        Specification<Customer> specification = new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(custName!=null&&!custName.equals("")){
                    predicates.add(criteriaBuilder.like(root.get("custName"),"%"+custName+"%"));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return customerRepostitor.findAll(specification,pageable);
    }

    @Override
    public XSSFWorkbook showKH() {
        List<Customer> list = customerRepostitor.findAll();//查出数据库数据
        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("Goods");//创建一张表
        Row titleRow = sheet.createRow(0);//创建第一行，起始为0
        titleRow.createCell(0).setCellValue("序号");//第一列
        titleRow.createCell(1).setCellValue("客户名称");
        titleRow.createCell(2).setCellValue("订单金额（元）");
        int cell = 1;
        for (Customer customer : list) {
            Row row = sheet.createRow(cell);//从第二行开始保存数据
            row.createCell(0).setCellValue(customer.getCustId());
            row.createCell(1).setCellValue(customer.getCustName());//将数据库的数据遍历出来
            Double ds = new Double(0);
            for (Orders orders:customer.getOrderss()){
                for (OrdersLine line: orders.getOrdersLines()) {
                    ds+=line.getOddPrice();
                }
            }
            row.createCell(2).setCellValue(ds);
            cell++;
        }
        return wb;
    }

    @Override
    public XSSFWorkbook showFW() {
        List<DictInfo> list = dictRepostitor.getDicttype();//查出数据库数据
        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("Goods");//创建一张表
        Row titleRow = sheet.createRow(0);//创建第一行，起始为0
        titleRow.createCell(0).setCellValue("编号");//第一列
        titleRow.createCell(1).setCellValue("条目");
        titleRow.createCell(2).setCellValue("服务数量");
        int cell = 1;
        for (DictInfo dict : list) {
            Row row = sheet.createRow(cell);//从第二行开始保存数据
            row.createCell(0).setCellValue(cell);
            row.createCell(1).setCellValue(dict.getName());//将数据库的数据遍历出来
            row.createCell(2).setCellValue(dict.getCount());
            cell++;
        }
        return wb;
    }

    @Override
    public XSSFWorkbook showGC() {
        List<DictInfo> list = dictRepostitor.getDictLeve();//查出数据库数据
        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("Goods");//创建一张表
        Row titleRow = sheet.createRow(0);//创建第一行，起始为0
        titleRow.createCell(0).setCellValue("编号");//第一列
        titleRow.createCell(1).setCellValue("等级");
        titleRow.createCell(2).setCellValue("客户数量");
        int cell = 1;
        for (DictInfo dict : list) {
            Row row = sheet.createRow(cell);//从第二行开始保存数据
            row.createCell(0).setCellValue(dict.getId());
            row.createCell(1).setCellValue(dict.getName());//将数据库的数据遍历出来
            row.createCell(2).setCellValue(dict.getCount());
            cell++;
        }
        return wb;
    }
}
