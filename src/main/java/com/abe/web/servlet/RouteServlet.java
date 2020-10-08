package com.abe.web.servlet;

import com.abe.domain.PageBean;
import com.abe.domain.Route;
import com.abe.service.RouteService;
import com.abe.service.impl.RouteServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: Abe
 * @Date: 2020/10/8
 *
 * 旅游线路通用Servlet
 */
@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {

    private RouteService service = new RouteServiceImpl();

    /**
     * 分页查询方法
     */
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 1.接收客户端浏览器提交的请求参数
        String cidStr = request.getParameter("cid");                        // 类别id
        String currentPageStr = request.getParameter("currentPage");        // 当前页数
        String pageSizeStr = request.getParameter("pageSize");              // 每页显示条数

        // 2.处理参数
        int cid = this.parseInt(cidStr, 0);
        int currentPage = this.parseInt(currentPageStr, 1);
        int pageSize = this.parseInt(pageSizeStr, 5);

        // 3.调用service查询PageBean对象
        PageBean<Route> routePageBean = service.pageQuery(cid, currentPage, pageSize);

        // 4.将PageBean对象序列化json并回写客户端浏览器
        this.writeValue(routePageBean, response);

    }

    /**
     * 字符串数字类型转换方法
     *      将数字字符串转换为数字，例如："5" -> 5
     * @param numStr 数字字符串
     * @return 整型数字
     * @throws ClassCastException
     *      类型转换异常，避免传入的字符串内容不是数字
     */
    private int parseInt(String numStr, int defaultValue) throws ClassCastException {
        return (numStr != null && numStr.length() > 0) ? Integer.parseInt(numStr) : defaultValue;
    }

}
