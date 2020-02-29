package com.t248.lmf.crm.service;

import com.t248.lmf.crm.entity.Customer;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AnalyzeService {
    Page<Customer> getCustomer(String custName, Pageable pageable);

    public XSSFWorkbook showKH();
    public XSSFWorkbook showFW();
    public XSSFWorkbook showGC();
}
