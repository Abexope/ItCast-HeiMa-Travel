package com.abe.service;

import com.abe.domain.User;

/**
 * @Author: Abe
 * @Date: 2020/10/7
 * @Description: com.abe.service
 * @version: 1.0
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
}
