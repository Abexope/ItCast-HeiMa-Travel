package com.abe.dao;

import com.abe.domain.User;

/**
 * @Author: Abe
 * @Date: 2020/10/7
 */
public interface UserDao {

    /**
     * 根据用户名查询用户信息
     * @param username String 用户名
     * @return
     *      查询成功：User Bean 对象
     *      查询失败：null
     */
    User findByUsername(String username);

    /**
     * 用户信息保存
     * @param user User Bean 对象
     */
    void save(User user);

    /**
     * 根据激活码查询用户信息
     * @param code String 激活码
     * @return
     *      查询成功：User Bean 对象
     *      查询失败：null
     */
    User findByCode(String code);

    /**
     * 更新激活状态
     * @param user User Bean 对象
     */
    void updateStatus(User user);
}
