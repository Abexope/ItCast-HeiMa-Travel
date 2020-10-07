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
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @Author: Abe
 * @Date: 2020/10/7
 * @Description: ${PACKAGE_NAME}
 * @version: 1.0
 *
 * 用户注册
 */
@WebServlet("/registerUserServlet")
public class RegisterUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
        UserService service = new UserServiceImpl();
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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
