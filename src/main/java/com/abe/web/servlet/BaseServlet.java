package com.abe.web.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author: Abe
 * @Date: 2020/10/8
 *
 * Servlet基类
 */
//@WebServlet("/BaseServlet")     // 该Servlet不需要被访问，所以不加虚拟了路径注解
public abstract class BaseServlet extends HttpServlet {

    /**
     * baseServlet方法分发
     */
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        // System.out.println("BaseServlet中的service方法被执行");

        // 1.获取请求路径
        String uri = req.getRequestURI();   // 例如：/travel/user/add
//        System.out.println("请求uri：" + uri);
        // 2.获取方法名称
        String methodName = uri.substring(uri.lastIndexOf('/') + 1);
//        System.out.println("方法名称：" + methodName);
        // 3.获取方法对象（反射）
//        System.out.println(this);
        /*this指向真正的实例化对象，即BaseServlet下面的继承类：com.abe.web.servlet.UserServlet@5fccaa6f*/
        /*this: 谁调用我，我代表谁*/
        try {
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
//            method.setAccessible(true);
            /*暴力反射：采用declared系列方法，并设置accessible为true，不是最优的实现方式*/
            // 4.执行方法
            method.invoke(this, req, resp);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}
