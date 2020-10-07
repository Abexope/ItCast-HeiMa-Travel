package com.abe.service.impl;

import com.abe.dao.UserDao;
import com.abe.dao.impl.UserDaoImpl;
import com.abe.domain.User;
import com.abe.service.UserService;

/**
 * @Author: Abe
 * @Date: 2020/10/7
 * @Description: com.abe.service.impl
 * @version: 1.0
 */
public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();

    /**
     * 注册用户
     * @param user User Bean 对象
     * @return boolean
     *      true：注册成功
     *      false：注册失败
     */
    @Override
    public boolean register(User user) {

        // 1.根据用户名查询用户对象（存在false，不存在true）
        User loginUser = userDao.findByUsername(user.getUsername());

        // 2.判断loginUser是否为null
        if (loginUser != null) {    // 用户名存在，注册失败
            return false;
        } else {                    // 用户名不存在，注册成功
            // 保存用户信息
            userDao.save(user);
            return true;
        }
    }

}
