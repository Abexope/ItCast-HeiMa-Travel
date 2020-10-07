package com.abe.dao;

import com.abe.domain.User;

/**
 * @Author: Abe
 * @Date: 2020/10/7
 * @Description: com.abe.dao
 * @version: 1.0
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

}
