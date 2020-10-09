package com.abe.dao;

import com.abe.domain.RouteImg;

import java.util.List;

/**
 * @Author: Abe
 * @Date: 2020/10/9
 *
 * 旅游线路展示图片数据库操作接口
 */
public interface RouteImgDao {

    /**
     * 根据 Route Bean 对象中的 rid 属性查询展示图片集合
     * @param rid 线路id
     * @return 泛型为 RouteImg Bean 对象的List集合
     */
    List<RouteImg> findByRid(int rid);

}
