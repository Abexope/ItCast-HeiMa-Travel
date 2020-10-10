package com.abe.service.impl;

import com.abe.dao.FavoriteDao;
import com.abe.dao.RouteDao;
import com.abe.dao.RouteImgDao;
import com.abe.dao.UserDao;
import com.abe.dao.impl.FavoriteDaoImpl;
import com.abe.dao.impl.RouteDaoImpl;
import com.abe.dao.impl.RouteImgDaoImpl;
import com.abe.dao.impl.UserDaoImpl;
import com.abe.domain.*;
import com.abe.service.UserService;
import com.abe.util.MailUtils;
import com.abe.util.UuidUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Abe
 * @Date: 2020/10/7
 *
 * 用户业务逻辑实现类
 */
public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();
    private RouteDao routeDao = new RouteDaoImpl();
    private RouteImgDao routeImgDao = new RouteImgDaoImpl();
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();

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
        String content = "<a href='http://localhost:80/travel/user/active?code=" +
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

    /**
     * 查询`我的收藏`
     * @param uid 用户uid
     * @return 泛型为Route类的PageBean对象，
     *      收藏结果的分页展示
     */
    @Override
    public PageBean<Route> favorPageQuery(int uid, int currentPage, int pageSize) {

        // 创建PageBean<Route>对象
        PageBean<Route> routePageBean = new PageBean<>();

        // 查询总记录数totalCount
        int totalCount = favoriteDao.findCountByUid(uid);
        if (totalCount == 0) {// 没有查到收藏记录
            return routePageBean;
        }

        // 计算起始记录数start，计算总页数totalPage
        int start = (currentPage - 1) * pageSize;
        int totalPage = (totalCount % pageSize == 0) ? (totalCount / pageSize) : (totalCount / pageSize + 1);

        // 分页查询rid列表（封装在MyFavorite类中）
        List<MyFavorite> pageFavoriteList = favoriteDao.findByUidAndPage(uid, start, pageSize);

        // 创新一个空的List<Route>集合
        List<Route> routeList = new ArrayList<>();

        // 遍历pageFavoriteList组装routeList
        for (MyFavorite myFavorite : pageFavoriteList) {
            // 根据其rid属性利用routeDao查route对象
            Route route = routeDao.findOne(myFavorite.getRid());
            // 向routeList中追加route属性
            routeList.add(route);
        }

        // 并组装PageBean<Route>对象
        routePageBean.setCurrentPage(currentPage);     // 设置当前页码
        routePageBean.setPageSize(pageSize);           // 设置每页显示条数
        routePageBean.setTotalPage(totalPage);         // 设置总页数
        routePageBean.setTotalCount(totalCount);       // 查询并设置总记录数
        routePageBean.setList(routeList);

        return routePageBean;
    }

}
