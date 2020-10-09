package com.abe.dao.impl;

import com.abe.dao.RouteImgDao;
import com.abe.domain.RouteImg;
import com.abe.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @Author: Abe
 * @Date: 2020/10/9
 *
 * 旅游线路展示图片数据库操作实现类
 */
public class RouteImgDaoImpl implements RouteImgDao {

    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 根据 Route Bean 对象中的 rid 属性查询展示图片集合
     * @param rid 线路id
     * @return 泛型为 RouteImg Bean 对象的List集合
     */
    @Override
    public List<RouteImg> findByRid(int rid) {
        String sql = "SELECT * FROM tab_route_img WHERE rid = ?";
        return template.query(sql, new BeanPropertyRowMapper<>(RouteImg.class), rid);
    }
}
