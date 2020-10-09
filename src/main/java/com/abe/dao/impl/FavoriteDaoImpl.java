package com.abe.dao.impl;

import com.abe.dao.FavoriteDao;
import com.abe.domain.Favorite;
import com.abe.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;

/**
 * @Author: Abe
 * @Date: 2020/10/9
 *
 * 旅游线路收藏相关数据库操作实现类
 */
public class FavoriteDaoImpl implements FavoriteDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 判断判断当前用户是否收藏旅游线路
     * @param rid 旅游线路id
     * @param uid 用户id
     * @return
     *      收藏：Favorite Bean 对象
     *      未收藏：null
     */
    @Override
    public Favorite findByRidAndUid(int rid, int uid) {
        Favorite favorite = null;
        try {
            String sql = "SELECT * FROM tab_favorite WHERE rid = ? AND uid = ?";
            favorite = template.queryForObject(sql, new BeanPropertyRowMapper<>(Favorite.class), rid, uid);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return favorite;
    }

    /**
     * 根据旅游线路id查询对应的收藏次数
     * @param rid 旅游线路id
     * @return 收藏次数
     */
    @Override
    public int findCountByRid(int rid) {
        String sql = "SELECT COUNT(*) FROM tab_favorite WHERE rid = ?";
        return template.queryForObject(sql, Integer.class, rid);
    }

    /**
     * 添加收藏路线
     * @param rid 旅游路线id
     * @param uid 用户id
     */
    @Override
    public void add(int rid, int uid) {
        String sql = "INSERT INTO tab_favorite(rid, date, uid)  VALUES( ? , ? , ? )";
        template.update(sql, rid, new Date(), uid);
    }
}
