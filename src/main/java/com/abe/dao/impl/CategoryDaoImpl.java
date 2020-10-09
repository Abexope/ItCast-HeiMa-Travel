package com.abe.dao.impl;

import com.abe.dao.CategoryDao;
import com.abe.domain.Category;
import com.abe.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @Author: Abe
 * @Date: 2020/10/8
 *
 * 分类相关数据库操作实现类
 */
public class CategoryDaoImpl implements CategoryDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 查询所有Category
     * @return 泛型为 Category Bean 类构成的List集合
     */
    @Override
    public List<Category> findAll() {
        String sql = "SELECT * FROM tab_category";
        return template.query(sql, new BeanPropertyRowMapper<>(Category.class));
    }
}
