package com.abe.service.impl;

import com.abe.dao.FavoriteDao;
import com.abe.dao.impl.FavoriteDaoImpl;
import com.abe.domain.Favorite;
import com.abe.service.FavoriteService;

/**
 * @Author: Abe
 * @Date: 2020/10/9
 *
 * 线路收藏业务实现类
 */
public class FavoriteServiceImpl implements FavoriteService {

    private FavoriteDao favoriteDao = new FavoriteDaoImpl();

    /**
     * 判断判断当前用户是否收藏旅游线路
     * @param rid 旅游线路id
     * @param uid 用户id
     * @return
     *      收藏：true
     *      未收藏：false
     */
    @Override
    public boolean isFavorite(String rid, int uid) {
        Favorite favorite = favoriteDao.findByRidAndUid(Integer.parseInt(rid), uid);
        return favorite != null;    // 对象非空返回true，为空返回false
    }

    /**
     * 添加收藏路线
     * @param rid 旅游路线id
     * @param uid 用户id
     */
    @Override
    public void add(String rid, int uid) {
        favoriteDao.add(Integer.parseInt(rid), uid);
    }

    /**
     * 取消收藏路线
     * @param rid 旅游路线id
     * @param uid 用户id
     */
    @Override
    public void remove(String rid, int uid) {
        favoriteDao.remove(Integer.parseInt(rid), uid);
    }
}
