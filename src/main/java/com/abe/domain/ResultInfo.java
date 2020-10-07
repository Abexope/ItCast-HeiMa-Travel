package com.abe.domain;

import java.io.Serializable;

/**
 * 用于封装后端返回前端数据对象
 */
public class ResultInfo implements Serializable {

    private boolean flag;       // 后端返回结果正常为true，发生异常返回false
    private Object data;        // 后端返回结果数据对象
    private String errorMsg;    // 发生异常的错误消息

    //无参构造方法
    public ResultInfo() {
    }

    public ResultInfo(boolean flag) {
        this.flag = flag;
    }

    /**
     * 有参构造方法
     * @param flag boolean，结果指示
     * @param errorMsg String，提示信息
     */
    public ResultInfo(boolean flag, String errorMsg) {
        this.flag = flag;
        this.errorMsg = errorMsg;
    }

    /**
     * 有参构造方法
     * @param flag boolean，结果指示
     * @param data Data Bean 对象
     * @param errorMsg String，提示信息
     */
    public ResultInfo(boolean flag, Object data, String errorMsg) {
        this.flag = flag;
        this.data = data;
        this.errorMsg = errorMsg;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
