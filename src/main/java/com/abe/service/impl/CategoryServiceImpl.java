package com.abe.service.impl;

import com.abe.dao.CategoryDao;
import com.abe.dao.impl.CategoryDaoImpl;
import com.abe.domain.Category;
import com.abe.service.CategoryService;
import com.abe.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 * @Author: Abe
 * @Date: 2020/10/8
 *
 * 分类相关业务实现类
 */
public class CategoryServiceImpl implements CategoryService {

    private CategoryDao dao = new CategoryDaoImpl();
    private Jedis jedis = JedisUtil.getJedis();

    /**
     * 查询所有Category
     * @return 泛型为 Category Bean 类构成的List集合
     */
    @Override
    public List<Category> findAll() {
        // 1.从redis中查询，使用 sorted set 实现排序查询
        Set<Tuple> categories_cache = jedis.zrangeWithScores("category", 0, -1);
        // 2.判断查询结果是否为空
        List<Category> categories;
        if (categories_cache == null || categories_cache.size() == 0) {     // 空
            System.out.println("从数据库中查询...");
            // 从数据库中查询
            categories = dao.findAll();
            // 根据cid排序，保证从数据库结果和redis结果一致
            categories.sort(Comparator.comparingInt(Category::getCid));
            // 将数据存储到redis中
            for (Category category : categories) {
                jedis.zadd("category", category.getCid(), category.getCname());
            }
        } else {    // 非空
            System.out.println("从redis中查询...");
            // 统一输出数据类型，将从redis中拿到的字符串转为List<Category>形式
            categories = new ArrayList<>();
            for (Tuple tuple : categories_cache) {
                Category category = new Category();
                category.setCid((int) tuple.getScore());
                category.setCname(tuple.getElement());
                categories.add(category);
            }
        }
        return categories;
    }
}
