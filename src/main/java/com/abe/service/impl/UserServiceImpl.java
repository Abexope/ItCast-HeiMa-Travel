package com.abe.service.impl;

import com.abe.dao.UserDao;
import com.abe.dao.impl.UserDaoImpl;
import com.abe.domain.User;
import com.abe.service.UserService;
import com.abe.util.MailUtils;
import com.abe.util.UuidUtil;

/**
 * @Author: Abe
 * @Date: 2020/10/7
 *
 * 用户业务逻辑实现类
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

        // 判断loginUser是否为null
        if (loginUser != null) {    // 用户名存在，注册失败
            return false;
        }   // 用户名不存在，注册成功

        // 2.保存用户信息
        // 2.1 设置唯一的激活码
        user.setCode(UuidUtil.getUuid());
        // 2.2 设置激活状态
        user.setStatus("N");    // 初始状态：未激活
        // 2.3 向数据库中写入用户信息
        userDao.save(user);

        // 3.发送激活邮件
        // 3.1 定义邮件内容
        String content = "<a href='http://localhost:80/travel/activeUserServlet?code=" +
                user.getCode() + "'>点击激活【黑马旅游网】</a>";
        // 3.2 发送邮件
        MailUtils.sendMail(user.getEmail(), content, "激活邮件");
        return true;
    }

    /**
     * 激活用户
     * @param code String 激活码
     * @return
     *      true：激活成功
     *      false：激活失败
     */
    @Override
    public boolean active(String code) {
        // 1.根据激活码查询用户对象
        User user = userDao.findByCode(code);
        if (user != null) {
            // 2.调用userDao中的修改激活状态方法
            userDao.updateStatus(user);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 登录用户
     * @param user 用户名
     * @return
     *      true：激活成功
     *      false：激活失败
     */
    @Override
    public User login(User user) {
        return userDao.findByUsernameAndPassword(user.getUsername(), user.getPassword());
    }

}
