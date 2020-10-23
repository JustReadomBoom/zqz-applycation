package com.zqz.service.model;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 10:27 2020/10/23
 */
public class UserBean {

    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }
}
