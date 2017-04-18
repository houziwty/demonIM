package com.demon.model;

/**
 * Created by Demon on 2017/4/18.
 */
public class UserModel {
    private String userId;
    private String token;
    private String serviceName;
    private String deviceId;
    private String deviceType;
    private boolean online;
    private long updateTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
