package com.abe.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: Abe
 * @Date: 2020/10/8
 *
 * Category通用Servlet
 */
@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {
    public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("categoryServlet的add方法");
    }

    public void find(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("categoryServlet的find方法");
    }
}
