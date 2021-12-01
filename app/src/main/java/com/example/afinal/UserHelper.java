package com.example.afinal;

import com.firebase.ui.auth.data.model.User;

public class UserHelper {
    String username, password;

    public UserHelper(){

    }

    public UserHelper(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
