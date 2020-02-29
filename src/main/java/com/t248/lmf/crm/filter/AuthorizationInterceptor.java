package com.t248.lmf.crm.filter;

import org.springframework.web.servlet.HandlerInterceptor;

public class AuthorizationInterceptor implements HandlerInterceptor {

//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        if (request.getSession().getAttribute("User") == null) {
//            response.setContentType("text/html;charset=UTF-8");
//            PrintWriter out = response.getWriter();
//            out.print("<script>alert('请先登录，然后在操作（Interception控制）');location.href='" + request.getContextPath() + "/login';</script>");
//            return false;
//        } else {
//            return false;
//        }
//    }

}
