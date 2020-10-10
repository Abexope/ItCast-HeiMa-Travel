package com.abe.service;

import com.abe.domain.PageBean;
import com.abe.domain.Route;
import com.abe.domain.User;

/**
 * @Author: Abe
 * @Date: 2020/10/7
 *
 * 用户业务逻辑接口
 */
public interface UserService {

    /**
     * 注册用户
     * @param user User Bean 对象
     * @return boolean
     *      true：注册成功
     *      false：注册失败
     */
    boolean register(User user);

    /**
     * 激活用户
     * @param code String 激活码
     * @return
     *      true：激活成功
     *      false：激活失败
     */
    boolean active(String code);

    /**
     * 登录用户
     * @param user 用户名
     * @return
     *      true：激活成功
     *      false：激活失败
     */
    User login(User user);

    /**
     * 查询`我的收藏`
     * @param uid 用户uid
     * @return 泛型为Route类的PageBean对象，
     *      收藏结果的分页展示
     */
    PageBean<Route> favorPageQuery(int uid, int currentPage, int pageSize);
}
