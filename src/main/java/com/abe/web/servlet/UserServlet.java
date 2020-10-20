package com.abe.web.servlet;

import com.abe.domain.PageBean;
import com.abe.domain.ResultInfo;
import com.abe.domain.Route;
import com.abe.domain.User;
import com.abe.service.UserService;
import com.abe.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
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
     * 用户登陆方法
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ResultInfo resultInfo = new ResultInfo();
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
//            response.getWriter().write(json);     // why not...
            /*response.setContentType("application/json:charset:utf-8");
            mapper.writeValue(response.getOutputStream(), resultInfo);*/
            this.writeValue(resultInfo, response);
            return;
        }   // else 验证通过

        // 1.获取用户信息
        Map<String, String[]> parameterMap = request.getParameterMap();

        // 1*.判断用户是否勾选`自动登陆`
        if (parameterMap.containsKey("auto_login")) {   // 用户勾选了`自动登录`
            // 创建一个Cookie对象，设置`JSESSIONID`属性为当前session对象的id值
            Cookie jSessionIdCookie = new Cookie("JSESSIONID", session.getId());
            // 将该Cookie对象回写给浏览器，并设置maxAge令浏览器持久化保存用户的登陆状态
            this.writeCookie(jSessionIdCookie, 60*60*24*7, "/", response);
        }

        // 2.封装 User Bean 对象
        User user = new User();
        try {
            BeanUtils.populate(user, parameterMap);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        // 3.调用service查询user
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
        /*response.setContentType("application/json:charset:utf-8");
        mapper.writeValue(response.getOutputStream(), resultInfo);*/
        this.writeValue(resultInfo, response);
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
            String msg;
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
        this.writeValue(user, response);
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

    /**
     * 获取`我的收藏`方法
     */
    public void findMyFavorite(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // 获取session，取得用户对象
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {     // 用户未登录
            writeValue(null, response);
            return;
        }

        // 获取并处理浏览器请求属性
        String currentPageStr = request.getParameter("currentPage");        // 当前页数
        String pageSizeStr = request.getParameter("pageSize");              // 每页显示条数
        int currentPage = this.parseInt(currentPageStr, 1);
        int pageSize = this.parseInt(pageSizeStr, 8);

        // 2.获取service，根据uid查rid，再根据rid查详情数据
        PageBean<Route> routePageBean = service.favorPageQuery(user.getUid(), currentPage, pageSize);

        // 3.将数据回写至浏览器
        writeValue(routePageBean, response);

    }
}
