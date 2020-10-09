package com.abe.dao.impl;

import com.abe.dao.RouteDao;
import com.abe.domain.Route;
import com.abe.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Abe
 * @Date: 2020/10/8
 *
 * 旅游线路相关数据库操作实现类
 */
public class RouteDaoImpl implements RouteDao {

    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 根据cid查询总记录数
     */
    @Override
    public int findTotalCount(int cid, String rname) {

        // 1.定义sql模板
        String sql = "SELECT count(*) FROM tab_route WHERE 1 = 1 ";

        StringBuilder stringBuilder = new StringBuilder(sql);   // 字符串拼接
        ArrayList<Object> paramList = new ArrayList<>();        // sql参数列表

        // 2.判断查询参数是否有值
        if (cid != 0) {
            stringBuilder.append(" AND cid = ? ");
            paramList.add(cid);
        }
        if (rname != null && rname.length() > 0) {
            stringBuilder.append(" AND rname LIKE ? ");
            paramList.add("%"+rname+"%");   // 模糊查询
        }

        sql = stringBuilder.toString();     // 拼接sql语句

        return template.queryForObject(sql, Integer.class, paramList.toArray());
    }

    /**
     * 查询当前页的数据集合
     * @param cid 旅游线路类别id
     * @param start 起始条目
     * @param pageSize 每页显示条数
     * @param rname 搜索文本：线路名称
     * @return Route Bean 对象构成的List集合
     */
    @Override
    public List<Route> findByPage(int cid, int start, int pageSize, String rname) {

        // 1.定义sql模板
        String sql = "SELECT * FROM tab_route WHERE 1 = 1";

        StringBuilder stringBuilder = new StringBuilder(sql);   // 字符串拼接
        ArrayList<Object> paramList = new ArrayList<>();        // sql参数列表

        // 2.判断查询参数是否有值
        if (cid != 0) {
            stringBuilder.append(" AND cid = ? ");
            paramList.add(cid);
        }
        if (rname != null && rname.length() > 0) {
            stringBuilder.append(" AND rname LIKE ? ");
            paramList.add("%"+rname+"%");   // 模糊查询
        }

        // 3.补充分页条件
        stringBuilder.append(" LIMIT ? , ? ");
        paramList.add(start);
        paramList.add(pageSize);

        sql = stringBuilder.toString();     // 拼接sql语句

        return template.query(sql, new BeanPropertyRowMapper<>(Route.class), paramList.toArray());
    }

    /**
     * 根据线路id查询详细信息
     * @param rid 线路id
     * @return Route Bean 对象
     */
    @Override
    public Route findOne(int rid) {
        String sql = "SELECT * FROM tab_route WHERE rid = ?";
        return template.queryForObject(sql, new BeanPropertyRowMapper<>(Route.class), rid);
    }
}
