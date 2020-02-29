package com.t248.lmf.crm.config;

import com.t248.lmf.crm.filter.*;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

//@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean filterRegist() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new AuthorizationFilter());
        registration.setName("AuthorizationFilter");
        registration.addUrlPatterns("/main");
        registration.setOrder(5);
        return registration;
    }

}
