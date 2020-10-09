package com.abe.dao;

import com.abe.domain.Seller;

/**
 * @Author: Abe
 * @Date: 2020/10/9
 *
 * 商家信息数据库操作接口
 */
public interface SellerDao {

    /**
     * 根据商家id查询商家信息
     * @param sid 商家id
     * @return Seller Bean 对象
     */
    Seller findById(int sid);
}
