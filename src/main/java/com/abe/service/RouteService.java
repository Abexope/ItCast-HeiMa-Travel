package com.abe.service;

import com.abe.domain.PageBean;
import com.abe.domain.Route;

/**
 * @Author: Abe
 * @Date: 2020/10/8
 *
 * 旅游线路相关业务接口
 */
public interface RouteService {

    /**
     * 根据旅游线路类别进行分页查询
     * @param cid 类别id
     * @param currentPage 当前页数
     * @param pageSize 每页显示条数
     * @return PageBean 封装对象
     */
    PageBean<Route> pageQuery(int cid, int currentPage, int pageSize);

}
