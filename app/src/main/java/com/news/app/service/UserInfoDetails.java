package com.news.app.service;

import com.news.app.entity.UserInfo;


public class UserInfoDetails  {

    private String username; // Changed from 'name' to 'username' for clarity
    private String password;

    public UserInfoDetails(UserInfo userInfo) {
        this.username = userInfo.getName(); // Assuming 'name' is used as 'username'
        this.password = userInfo.getPassword();
        
    }

}

