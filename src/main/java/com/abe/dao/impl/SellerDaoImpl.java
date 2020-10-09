package com.abe.dao.impl;

import com.abe.dao.SellerDao;
import com.abe.domain.Seller;
import com.abe.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @Author: Abe
 * @Date: 2020/10/9
 *
 * 商家信息数据库操作实现类
 */
public class SellerDaoImpl implements SellerDao {

    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 根据商家id查询商家信息
     * @param sid 商家id
     * @return Seller Bean 对象
     */
    @Override
    public Seller findById(int sid) {
        String sql = "SELECT * FROM tab_seller WHERE sid = ?";
        return template.queryForObject(sql, new BeanPropertyRowMapper<>(Seller.class), sid);
    }
}
