package com.abe.dao.impl;

import com.abe.dao.FavoriteDao;
import com.abe.domain.Favorite;
import com.abe.domain.MyFavorite;
import com.abe.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.util.List;

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
        } catch (DataAccessException ignore) { }
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
     * 查询用户uid的收藏总数
     * @param uid 用户uid
     * @return 收藏路线总数
     */
    @Override
    public int findCountByUid(int uid) {
        String sql = "SELECT COUNT(*) FROM tab_favorite WHERE uid = ?";
        return template.queryForObject(sql, Integer.class, uid);
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

    /**
     * 删除收藏路线
     * @param rid 旅游路线id
     * @param uid 用户id
     */
    @Override
    public void remove(int rid, int uid) {
        String sql = "DELETE FROM tab_favorite WHERE rid = ? AND uid = ?";
        template.update(sql, rid, uid);
    }

    /**
     * 分页查询用户uid的收藏记录
     * @param uid 用户uid
     * @param start 起始条目
     * @param pageSize 每页显示条数
     * @return MyFavorite Bean 对象构成的 List 集合
     */
    @Override
    public List<MyFavorite> findByUidAndPage(int uid, int start, int pageSize) {
        String sql = "SELECT * FROM tab_favorite WHERE uid = ? LIMIT ? , ?";
        return template.query(sql, new BeanPropertyRowMapper<>(MyFavorite.class), uid, start, pageSize);
    }
}
