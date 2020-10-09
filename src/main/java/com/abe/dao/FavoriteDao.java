package com.abe.dao;

import com.abe.domain.Favorite;

/**
 * @Author: Abe
 * @Date: 2020/10/9
 *
 * 旅游线路收藏相关数据库操作接口
 */
public interface FavoriteDao {

    /**
     * 判断判断当前用户是否收藏旅游线路
     * @param rid 旅游线路id
     * @param uid 用户id
     * @return
     *      收藏：Favorite Bean 对象
     *      未收藏：null
     */
    Favorite findByRidAndUid(int rid, int uid);

    /**
     * 根据旅游线路id查询对应的收藏次数
     * @param rid 旅游线路id
     * @return 收藏次数
     */
    int findCountByRid(int rid);

    /**
     * 添加收藏路线
     * @param rid 旅游路线id
     * @param uid 用户id
     */
    void add(int rid, int uid);
}
