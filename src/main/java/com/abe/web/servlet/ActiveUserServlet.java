package com.abe.web.servlet;

import com.abe.service.UserService;
import com.abe.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: Abe
 * @Date: 2020/10/7
 */
@WebServlet("/activeUserServlet")
public class ActiveUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1.获取激活码
        String code = request.getParameter("code");
        if (code != null) {     // 获取激活码
            // 2.调用service完成激活操作
            UserService service = new UserServiceImpl();
            boolean flag = service.active(code);
            // 3.判断标记
            String msg = null;
            if (flag) {     // 激活成功
                msg = "激活成功，请<a href='login.html'>登录</a>";
            } else {        // 激活失败
                msg = "激活失败，请联系管理员";
            }
            // 4.回写响应内容
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
