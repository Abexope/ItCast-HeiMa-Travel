package com.abe.dao;

import com.abe.domain.Route;

import java.util.List;

/**
 * @Author: Abe
 * @Date: 2020/10/8
 *
 * 旅游线路相关数据库查询接口
 */
public interface RouteDao {

    /**
     * 根据cid查询总记录数
     */
    int findTotalCount(int cid);

    /**
     * 查询当前页的数据集合
     * @param cid 旅游线路类别id
     * @param start 起始条目
     * @param pageSize 每页显示条数
     * @return Route Bean 对象构成的List集合
     */
    List<Route> findByPage(int cid, int start, int pageSize);
}
