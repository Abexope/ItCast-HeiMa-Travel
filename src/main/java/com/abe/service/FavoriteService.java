package com.abe.service;

/**
 * @Author: Abe
 * @Date: 2020/10/9
 *
 * 线路收藏业务接口
 */
public interface FavoriteService {

    /**
     * 判断判断当前用户是否收藏旅游线路
     * @param rid 旅游线路id
     * @param uid 用户id
     * @return
     *      收藏：true
     *      未收藏：false
     */
    boolean isFavorite(String rid, int uid);

    /**
     * 添加收藏路线
     * @param rid 旅游路线id
     * @param uid 用户id
     */
    void add(String rid, int uid);

    /**
     * 取消收藏路线
     * @param rid 旅游路线id
     * @param uid 用户id
     */
    public void remove(String rid, int uid);

}
