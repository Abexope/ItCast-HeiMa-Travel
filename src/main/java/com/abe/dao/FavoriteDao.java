package com.abe.dao;

import com.abe.domain.Favorite;
import com.abe.domain.MyFavorite;

import java.util.List;

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
     * 查询用户uid的收藏总数
     * @param uid 用户uid
     * @return 收藏路线总数
     */
    int findCountByUid(int uid);

    /**
     * 添加收藏路线
     * @param rid 旅游路线id
     * @param uid 用户id
     */
    void add(int rid, int uid);

    /**
     * 删除收藏路线
     * @param rid 旅游路线id
     * @param uid 用户id
     */
    void remove(int rid, int uid);

    /**
     * 分页查询用户uid的收藏记录
     * @param uid 用户uid
     * @param start 起始条目
     * @param pageSize 每页显示条数
     * @return MyFavorite Bean 对象构成的 List 集合
     */
    List<MyFavorite> findByUidAndPage(int uid, int start, int pageSize);
}
