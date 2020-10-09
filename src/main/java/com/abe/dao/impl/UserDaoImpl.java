package com.abe.dao.impl;

import com.abe.dao.UserDao;
import com.abe.domain.User;
import com.abe.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @Author: Abe
 * @Date: 2020/10/7
 *
 * 用户信息数据库操作实现类
 */
public class UserDaoImpl implements UserDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 根据用户名查询用户信息
     * @param username String 用户名
     * @return
     *      查询成功：User Bean 对象
     *      查询失败：null
     */
    @Override
    public User findByUsername(String username) {
        User user = null;
        try {
            // 1.定义sql
            String sql = "SELECT * FROM tab_user WHERE username = ?";
            // 2.执行sql
            user = template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * 用户信息保存
     * @param user User Bean 对象
     */
    @Override
    public void save(User user) {
        // 1.定义sql
        String sql = "INSERT INTO tab_user(" +
                "username, password, name, birthday, sex, telephone, email, status, code) " +
                "VALUES(?,?,?,?,?,?,?,?,?)";
        // 2.执行sql
        template.update(sql,
                user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getBirthday(),
                user.getSex(),
                user.getTelephone(),
                user.getEmail(),
                user.getStatus(),
                user.getCode()
        );
    }

    /**
     * 根据激活码查询用户信息
     * @param code String 激活码
     * @return
     *      查询成功：User Bean 对象
     *      查询失败：null
     */
    @Override
    public User findByCode(String code) {
        User user = null;
        try {
            String sql = "SELECT * FROM tab_user WHERE code = ?";
            user = template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), code);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * 更新激活状态
     * @param user User Bean 对象
     */
    @Override
    public void updateStatus(User user) {
        String sql = "UPDATE tab_user SET status = 'Y' WHERE uid = ?";
        template.update(sql, user.getUid());
    }

    /**
     * 根据用户名和密码查询用户信息
     *      登陆验证
     * @param username String 用户名
     * @param password String 密码
     * @return
     *      查询成功：User Bean 对象
     *      查询失败：null
     */
    @Override
    public User findByUsernameAndPassword(String username, String password) {
        User user = null;
        try {
            String sql = "SELECT * FROM tab_user WHERE username = ? AND password = ?";
            user = template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), username, password);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return user;
    }
}
