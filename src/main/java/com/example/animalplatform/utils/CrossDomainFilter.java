package com.example.animalplatform.utils;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;



public class CrossDomainFilter implements Filter {
    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse servletResponse,
                         FilterChain chain) throws IOException, ServletException {

        if (!(request instanceof HttpServletRequest)) {
            throw new ServletException("This filter can "
                    + " only process HttpServletRequest requests");
        }
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "origin, x-requested-with, content-type, accept");
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {

    }
}