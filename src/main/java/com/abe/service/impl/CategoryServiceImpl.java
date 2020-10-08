package com.abe.service.impl;

import com.abe.dao.CategoryDao;
import com.abe.dao.impl.CategoryDaoImpl;
import com.abe.domain.Category;
import com.abe.service.CategoryService;

import java.util.List;

/**
 * @Author: Abe
 * @Date: 2020/10/8
 */
public class CategoryServiceImpl implements CategoryService {

    private CategoryDao categoryDao = new CategoryDaoImpl();

    /**
     * 查询所有Category
     * @return 泛型为 Category Bean 类构成的List集合
     */
    @Override
    public List<Category> findAll() {
        return categoryDao.findAll();
    }
}
