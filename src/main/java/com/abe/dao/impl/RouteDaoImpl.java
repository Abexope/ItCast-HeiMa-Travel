package com.abe.dao.impl;

import com.abe.dao.RouteDao;
import com.abe.domain.Route;
import com.abe.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @Author: Abe
 * @Date: 2020/10/8
 *
 * 旅游线路相关数据库查询实现类
 */
public class RouteDaoImpl implements RouteDao {

    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 根据cid查询总记录数
     */
    @Override
    public int findTotalCount(int cid) {
        String sql = "SELECT count(*) FROM tab_route WHERE cid = ?";
        return template.queryForObject(sql, Integer.class, cid);
    }

    /**
     * 查询当前页的数据集合
     * @param cid 旅游线路类别id
     * @param start 起始条目
     * @param pageSize 每页显示条数
     * @return Route Bean 对象构成的List集合
     */
    @Override
    public List<Route> findByPage(int cid, int start, int pageSize) {
        String sql = "SELECT * FROM tab_route WHERE cid = ? LIMIT ? , ?";
        return template.query(sql, new BeanPropertyRowMapper<>(Route.class), cid, start, pageSize);
    }
}
