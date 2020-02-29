package com.t248.lmf.crm.config.quartz;

import com.t248.lmf.crm.service.CustomerService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.annotation.Resource;

public class CrmJob implements Job {

    @Resource
    CustomerService customerService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        customerService.jiangca();
    }
}
