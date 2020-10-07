package com.abe.dao.impl;

import com.abe.dao.UserDao;
import com.abe.domain.User;
import com.abe.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @Author: Abe
 * @Date: 2020/10/7
 * @Description: com.abe.dao.impl
 * @version: 1.0
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

        } catch (Exception ignore) { }

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
                "username, password, name, birthday, sex, telephone, email) " +
                "VALUES(?,?,?,?,?,?,?)";

        // 2.执行sql
        template.update(sql,
                user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getBirthday(),
                user.getSex(),
                user.getTelephone(),
                user.getEmail()
        );

    }
}
