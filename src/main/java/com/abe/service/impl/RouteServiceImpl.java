package com.abe.service.impl;

import com.abe.dao.RouteDao;
import com.abe.dao.impl.RouteDaoImpl;
import com.abe.domain.PageBean;
import com.abe.domain.Route;
import com.abe.service.RouteService;

/**
 * @Author: Abe
 * @Date: 2020/10/8
 *
 * 旅游线路相关业务实现类
 */
public class RouteServiceImpl implements RouteService {

    RouteDao dao = new RouteDaoImpl();

    /**
     * 根据旅游线路类别进行分页查询
     * @param cid 类别id
     * @param currentPage 当前页数
     * @param pageSize 每页显示条数
     * @param rname 搜索文本：线路名称
     * @return PageBean 封装对象
     */
    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname) {

        // 初始化 PageBean 封装对象
        PageBean<Route> routePageBean = new PageBean<>();

        // 计算起始记录数start，查询总记录数totalCount，计算总页数totalPage
        int start = (currentPage - 1) * pageSize;
        int totalCount = dao.findTotalCount(cid, rname);
        int totalPage = (totalCount % pageSize == 0) ? (totalCount / pageSize) : (totalCount / pageSize + 1);

        routePageBean.setCurrentPage(currentPage);                              // 设置当前页码
        routePageBean.setPageSize(pageSize);                                    // 设置每页显示条数
        routePageBean.setTotalPage(totalPage);                                  // 设置总页数
        routePageBean.setTotalCount(totalCount);                                // 查询并设置总记录数
        routePageBean.setList(dao.findByPage(cid, start, pageSize, rname));     // 查询并设置当前页显示记录

        return routePageBean;
    }
}
