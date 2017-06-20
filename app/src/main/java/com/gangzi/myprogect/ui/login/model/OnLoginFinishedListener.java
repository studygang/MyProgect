package com.gangzi.myprogect.ui.login.model;

/**
 * Created by gangzi on 2017/6/20.
 */

public interface OnLoginFinishedListener {
    void onUsernameError();

    void onPasswordError();

    void onSuccess();
}
