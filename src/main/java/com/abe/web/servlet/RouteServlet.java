package com.abe.web.servlet;

import com.abe.domain.PageBean;
import com.abe.domain.Route;
import com.abe.domain.User;
import com.abe.service.FavoriteService;
import com.abe.service.RouteService;
import com.abe.service.impl.FavoriteServiceImpl;
import com.abe.service.impl.RouteServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Author: Abe
 * @Date: 2020/10/8
 *
 * 旅游线路通用Servlet
 */
@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {

    private RouteService routeService = new RouteServiceImpl();
    private FavoriteService favoriteService = new FavoriteServiceImpl();

    /**
     * 分页查询方法
     */
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 1.接收客户端浏览器提交的请求参数
        String cidStr = request.getParameter("cid");                        // 类别id
        String currentPageStr = request.getParameter("currentPage");        // 当前页数
        String pageSizeStr = request.getParameter("pageSize");              // 每页显示条数
        String rname = request.getParameter("rname");                       // 搜索文本：线路名称

        // 2.处理参数
        int cid = this.parseInt(cidStr, 0);
        int currentPage = this.parseInt(currentPageStr, 1);
        int pageSize = this.parseInt(pageSizeStr, 5);
        rname = parseStr(rname);

        // 3.调用service查询PageBean对象
        PageBean<Route> routePageBean = routeService.pageQuery(cid, currentPage, pageSize, rname);

        // 4.将PageBean对象序列化json并回写客户端浏览器
        this.writeValue(routePageBean, response);

    }

    /**
     * 查询单个旅游路线的详细信息
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();

        // 1.接收线路id
        String rid = request.getParameter("rid");
        rid = this.parseStr(rid);   // 主要防止获取的"null"字符串
        // 2.调用service查询
        Route route = routeService.findOne(rid);

        // 3.回写给客户端浏览器
        this.writeValue(route, response);
    }

    /**
     * 判断当前用户是否收藏了指定rid对应的旅游线路
     */
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 1.接收线路id
        String rid = request.getParameter("rid");
        rid = this.parseStr(rid);   // 主要防止获取的"null"字符串

        // 2.获取当前登陆用户
        User user = (User) request.getSession().getAttribute("user");
        int uid = user == null ? 0 : user.getUid();     // 获取用户的uid

        // 3.调用service查询
        boolean flag = favoriteService.isFavorite(rid, uid);

        // 4.回写给客户端浏览器
        writeValue(flag, response);
    }

    /**
     * 添加收藏
     */
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) {
        // 1.获取线路id
        String rid = request.getParameter("rid");
        // 2.获取用户id
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) return;   // 未登录无法收藏，直接跳出方法
        int uid = user.getUid();
        // 3.调用service执行添加收藏
        favoriteService.add(rid, uid);
    }

    /**
     * 取消收藏
     */
    public void removeFavorite(HttpServletRequest request, HttpServletResponse response) {
        // 1.获取线路id
        String rid = request.getParameter("rid");
        // 2.获取用户id
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) return;   // 未登录无法收藏，直接跳出方法
        int uid = user.getUid();
        // 3.调用service执行添加收藏
        favoriteService.remove(rid, uid);
    }

}
