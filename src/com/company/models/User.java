package com.company.models;

public class User {
    private String username;
    private String userGroup;

    public User(String username, String user_group) {
        this.username = username;
        this.userGroup = user_group;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(String userGroup) {
        this.userGroup = userGroup;
    }
}
