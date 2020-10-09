package com.abe.service.impl;

import com.abe.dao.FavoriteDao;
import com.abe.dao.RouteDao;
import com.abe.dao.RouteImgDao;
import com.abe.dao.SellerDao;
import com.abe.dao.impl.FavoriteDaoImpl;
import com.abe.dao.impl.RouteDaoImpl;
import com.abe.dao.impl.RouteImgDaoImpl;
import com.abe.dao.impl.SellerDaoImpl;
import com.abe.domain.PageBean;
import com.abe.domain.Route;
import com.abe.domain.RouteImg;
import com.abe.domain.Seller;
import com.abe.service.RouteService;

import java.util.List;

/**
 * @Author: Abe
 * @Date: 2020/10/8
 *
 * 旅游线路相关业务实现类
 */
public class RouteServiceImpl implements RouteService {

    private RouteDao routeDao = new RouteDaoImpl();
    private RouteImgDao routeImgDao = new RouteImgDaoImpl();
    private SellerDao sellerDao = new SellerDaoImpl();
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();

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
        int totalCount = routeDao.findTotalCount(cid, rname);
        int totalPage = (totalCount % pageSize == 0) ? (totalCount / pageSize) : (totalCount / pageSize + 1);

        routePageBean.setCurrentPage(currentPage);                              // 设置当前页码
        routePageBean.setPageSize(pageSize);                                    // 设置每页显示条数
        routePageBean.setTotalPage(totalPage);                                  // 设置总页数
        routePageBean.setTotalCount(totalCount);                                // 查询并设置总记录数
        routePageBean.setList(routeDao.findByPage(cid, start, pageSize, rname));     // 查询并设置当前页显示记录

        return routePageBean;
    }

    /**
     * 根据线路id查询详细信息
     * @param rid 线路id
     * @return Route Bean 对象
     */
    @Override
    public Route findOne(String rid) {

        // 1.根据rid从tab_route表中查询Route Bean对象
        Route route = routeDao.findOne(Integer.parseInt(rid));

        // 2.根据 Route Bean 对象中的 rid 查询图片集合信息
        List<RouteImg> routeImgList = routeImgDao.findByRid(route.getRid());
        route.setRouteImgList(routeImgList);    // 将集合设置为route对象的属性

        // 3.根据 Route Bean 对象中的 id 查询商家信息（Seller Bean 对象）
        Seller seller = sellerDao.findById(route.getSid());
        route.setSeller(seller);        // 将seller设置为route对象的属性

        // 查询收藏次数
        int count = favoriteDao.findCountByRid(route.getRid());
        route.setCount(count);

        return route;
    }
}
