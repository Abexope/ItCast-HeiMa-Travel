package com.abe.web.servlet;

import com.abe.domain.PageBean;
import com.abe.domain.Route;
import com.abe.service.RouteService;
import com.abe.service.impl.RouteServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

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
        String rname = request.getParameter("rname");                       // 搜索文本：线路名称

        // 2.处理参数
        int cid = this.parseInt(cidStr, 0);
        int currentPage = this.parseInt(currentPageStr, 1);
        int pageSize = this.parseInt(pageSizeStr, 5);
        rname = parseStr(rname);

        // 3.调用service查询PageBean对象
        PageBean<Route> routePageBean = service.pageQuery(cid, currentPage, pageSize, rname);

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
        return (numStr != null && numStr.length() > 0 && !"null".equalsIgnoreCase(numStr))
                ? Integer.parseInt(numStr) : defaultValue;      // 注意：浏览器提交的空值到后台会被识别为<字符串"null">
    }

    /**
     * 非数字字符串参数处理方法
     * @param str 字符串对象
     * @return 处理后的字符串对象
     */
    private String parseStr(String str) {
        /*tomcat7存在中文乱码问题*/
        if (str != null && str.length() > 0 && !"null".equalsIgnoreCase(str)) {
            str = new String(str.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            return str;
        }
        return null;
    }

}
