package com.abe.web.servlet;

import com.abe.domain.Category;
import com.abe.service.CategoryService;
import com.abe.service.impl.CategoryServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Author: Abe
 * @Date: 2020/10/8
 *
 * Category通用Servlet
 */
@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {

    private CategoryService service = new CategoryServiceImpl();

    public void findAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 1.调用service查询所有categories
        List<Category> categories = service.findAll();
        // 2.序列化json，并回写客户端浏览器
        this.writeValue(categories, response);
    }
}
