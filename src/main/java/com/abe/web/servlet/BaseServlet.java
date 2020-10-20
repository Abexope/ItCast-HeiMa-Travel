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
import java.nio.charset.StandardCharsets;

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

    /**
     * 重载方法，向客户端浏览器回传Cookie
     * @param cookie Cookie对象
     * @param maxAge Cookie生命周期
     * @param path Cookie作用域
     * @param response Http响应对象
     */
    public void writeCookie(Cookie cookie, int maxAge, String path, HttpServletResponse response) {
        cookie.setMaxAge(maxAge);
        cookie.setPath(path);
        response.addCookie(cookie);
    }

    /**
     * 字符串数字类型转换方法
     *      将数字字符串转换为数字，例如："5" -> 5
     * @param numStr 数字字符串
     * @return 整型数字
     * @throws ClassCastException
     *      类型转换异常，避免传入的字符串内容不是数字
     */
    protected int parseInt(String numStr, int defaultValue) throws ClassCastException {
        return (numStr != null && numStr.length() > 0 && !"null".equalsIgnoreCase(numStr) && !"NaN".equalsIgnoreCase(numStr))
                ? Integer.parseInt(numStr) : defaultValue;      // 注意：浏览器提交的空值到后台会被识别为<字符串"null">
    }

    /**
     * 非数字字符串参数处理方法
     * @param str 字符串对象
     * @return 处理后的字符串对象
     */
    protected String parseStr(String str) {
        /*tomcat7存在中文乱码问题*/
        if (str != null && str.length() > 0 && !"null".equalsIgnoreCase(str) && !"NaN".equalsIgnoreCase(str)) {
            str = new String(str.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            return str;
        }
        return null;
    }
}
