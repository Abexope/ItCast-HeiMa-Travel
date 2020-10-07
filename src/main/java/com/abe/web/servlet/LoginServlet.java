package com.abe.web.servlet;

import com.abe.domain.ResultInfo;
import com.abe.domain.User;
import com.abe.service.UserService;
import com.abe.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @Author: Abe
 * @Date: 2020/10/7
 *
 * 用户登录
 */
@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
        UserService service = new UserServiceImpl();
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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
