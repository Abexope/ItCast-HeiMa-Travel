package com.abe.dao;

import com.abe.domain.Category;

import java.util.List;

/**
 * @Author: Abe
 * @Date: 2020/10/8
 */
public interface CategoryDao {

    /**
     * 查询所有Category
     * @return 泛型为 Category Bean 类构成的List集合
     */
    List<Category> findAll();
}
