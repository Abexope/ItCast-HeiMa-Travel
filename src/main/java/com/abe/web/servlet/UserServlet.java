package com.abe.web.servlet;

import com.abe.domain.ResultInfo;
import com.abe.domain.User;
import com.abe.service.UserService;
import com.abe.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @Author: Abe
 * @Date: 2020/10/8
 *
 * User通用Servlet
 *      将与User对象相关的Servlet业务代码整合到此类中
 */
@WebServlet("/user/*")      // 公共虚拟路径
public class UserServlet extends BaseServlet {

    /*将 UserService 对象提取至成员变量*/
    private UserService service = new UserServiceImpl();

    /**
     * 用户注册方法
     */
    public void register(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 0.验证码校验
        HttpSession session = request.getSession();
        // 获取客户端浏览器提交的验证码
        String checkCode_browser = request.getParameter("check");
        session.removeAttribute("check");   // 客户端验证码获取即从Session中移除，保证验证码的一次性
        // 获取服务器程序 CheckCodeServlet 生成的验证码
        String checkCode_server = (String) session.getAttribute("CheckCode_Server");
        // 验证码比对
        if (!checkCode_server.equalsIgnoreCase(checkCode_browser)) {        // 比对失败
            ResultInfo resultInfo = new ResultInfo();
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("验证码错误！");

            // 将resultInfo序列化为json字符串
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(resultInfo);

            // 将json数据回写给客户端浏览器
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
            return;
        }

        // 1.获取数据
        Map<String, String[]> parameterMap = request.getParameterMap();

        // 2.封装对象
        User user = new User();
        try {
            BeanUtils.populate(user, parameterMap);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        // 3.调用service完成注册
        // UserService service = new UserServiceImpl();
        boolean flag = service.register(user);
        ResultInfo resultInfo = new ResultInfo();

        // 4.响应结果
        if (flag) {     // 注册成功
            resultInfo.setFlag(true);
        } else {        // 注册失败
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("注册失败！");
        }

        // 将resultInfo序列化为json字符串
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(resultInfo);

        // 将json数据回写给客户端浏览器
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }

    /**
     * 用户注册方法
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ResultInfo resultInfo = new ResultInfo();
        ObjectMapper mapper = new ObjectMapper();
        HttpSession session = request.getSession();

        // 0.验证码核对
        String checkCode_browser = request.getParameter("check");               // 获取客户端浏览器提交的验证码
        String checkCode_server = (String) session.getAttribute("CheckCode_Server");     // 获取服务器生成的验证码
        if (!checkCode_server.equalsIgnoreCase(checkCode_browser)) {    // 验证失败
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("验证码错误！");
            // 序列化json
//            String json = mapper.writeValueAsString(resultInfo);
//            System.out.println(json);
            // 回写客户端浏览器
            response.setContentType("application/json:charset:utf-8");
//            response.getWriter().write(json);     // why not...
            mapper.writeValue(response.getOutputStream(), resultInfo);
            return;
        }   // else 验证通过

        // 1.获取用户信息
        /*String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = new User(username, password);*/
        Map<String, String[]> parameterMap = request.getParameterMap();

        // 2.封装 User Bean 对象
        User user = new User();
        try {
            BeanUtils.populate(user, parameterMap);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        // 3.调用service查询user
        // UserService service = new UserServiceImpl();
        User loginUser = service.login(user);

        // 4.判断用户是否存在
        if (loginUser == null) {    // 用户不存在
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("用户名或密码错误！");
        }   // else 用户存在

        // 5.判断用户是否激活
        if (loginUser != null && !"Y".equals(loginUser.getStatus())) {   // 用户未激活
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("您尚未未激活，请激活。");
        }   // else 用户已激活

        // 6.用户名和密码正确且已激活，跳转网站首页
        if (loginUser != null && "Y".equals(loginUser.getStatus())) {
            resultInfo.setFlag(true);
            session.setAttribute("user", loginUser);
        }
//        String json = mapper.writeValueAsString(resultInfo);
//        System.out.println(json);
        response.setContentType("application/json:charset:utf-8");
        mapper.writeValue(response.getOutputStream(), resultInfo);
//        response.getWriter().write(json);     // why not...
    }

    /**
     * 用户激活方法
     */
    public void active(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 1.获取激活码
        String code = request.getParameter("code");
        if (code != null) {     // 获取激活码
            // 2.调用service完成激活操作
            // UserService service = new UserServiceImpl();
            boolean flag = service.active(code);
            // 3.判断标记
            String msg = null;
            if (flag) {     // 激活成功
                msg = "激活成功，请<a href='../login.html'>登录</a>";
            } else {        // 激活失败
                msg = "激活失败，请联系管理员";
            }
            // 4.回写响应内容
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
        }
    }

    /**
     * 查询登录用户信息方法，查询结果在index.html中的header上显示
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 从session中获取登录用户
        User user = (User) request.getSession().getAttribute("user");
        // 将user写回客户端
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(), user);
    }

    /**
     * 退出登录方法
     */
    public void exit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 1.销毁session
        request.getSession().invalidate();
        // 2.跳转至登陆页面
        response.sendRedirect(request.getContextPath() + "/login.html");
    }
}
