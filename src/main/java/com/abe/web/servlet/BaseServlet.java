package com.abe.web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author: Abe
 * @Date: 2020/10/8
 *
 * Servlet基类
 *      该Servlet不需要被访问，所以不加虚拟了路径注解
 */
public abstract class BaseServlet extends HttpServlet {

    private ObjectMapper mapper = new ObjectMapper();

    /**
     * baseServlet方法分发
     */
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        // 1.获取请求路径
        String uri = req.getRequestURI();   // 例如：/travel/user/add
        // 2.获取方法名称
        String methodName = uri.substring(uri.lastIndexOf('/') + 1);
        // 3.获取方法对象（反射）
        /*this指向真正的实例化对象，即BaseServlet下面的继承类：com.abe.web.servlet.UserServlet@5fccaa6f*/
        /*this: 谁调用我，我代表谁*/
        try {
            Method method = this.getClass().getMethod(methodName,
                    HttpServletRequest.class, HttpServletResponse.class);
            // 4.执行方法
            method.invoke(this, req, resp);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将输入对象序列化为json，并回写客户端浏览器
     * @param object 输入对象
     * @param response Http响应对象
     */
    public void writeValue(Object object, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(), object);
    }

    /**
     * 将输入对象序列化为json并返回给调用者
     * @param object 输入对象
     * @return json字符串
     */
    public String writeValueAsString(Object object) throws JsonProcessingException {
        return mapper.writeValueAsString(object);
    }

    /**
     * 向客户端浏览器回传Cookie
     * @param cookie Cookie对象
     * @param maxAge Cookie生命周期
     * @param response Http响应对象
     */
    public void writeCookie(Cookie cookie, int maxAge, HttpServletResponse response) {
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }
}
