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

}
