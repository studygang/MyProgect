package com.gangzi.myprogect.ui.login.model;

/**
 * Created by gangzi on 2017/6/20.
 */

public interface LoginModel {
    void login(String account,String password,OnLoginFinishedListener onLoginFinishedListener);
}
