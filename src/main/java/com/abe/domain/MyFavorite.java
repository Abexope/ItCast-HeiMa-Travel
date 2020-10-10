package com.abe.domain;

/**
 * @Author: Abe
 * @Date: 2020/10/10
 *
 * 用户收藏列表Bean封装类
 */
public class MyFavorite {

    private int uid;
    private String date;
    private int rid;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }
}
