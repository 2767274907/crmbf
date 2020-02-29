package com.t248.lmf.crm.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AuthorizationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest HttpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        if (HttpRequest.getSession().getAttribute("User") == null) {
            httpResponse.setContentType("text/html;charset=UTF-8");
            PrintWriter out = httpResponse.getWriter();
            out.print("<script>alert('请先登录，然后在操作（Filter控制）');location.href='" + HttpRequest.getContextPath() + "/login.jsp';</script>");
        } else {
            chain.doFilter(HttpRequest, httpResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
