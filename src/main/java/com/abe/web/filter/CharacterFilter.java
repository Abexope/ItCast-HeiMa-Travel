package com.abe.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: Abe
 * @Date: 2020/10/7
 *
 * 字符编码过滤器
 */
@WebFilter("/*")
public class CharacterFilter implements Filter {

    public void init(FilterConfig config) throws ServletException {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        // 接口向下转型
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        // 获取请求方法
        String method = request.getMethod();
        // 解决post请求中文乱码问题
        if (method.equalsIgnoreCase("post")) {
            request.setCharacterEncoding("utf-8");
        }
        // 处理响应乱码
        response.setContentType("utf-8");
        chain.doFilter(request, response);
    }

    public void destroy() {
    }
}
