package com.gangzi.myprogect.ui.login.view;

/**
 * Created by gangzi on 2017/6/20.
 */

public interface LoginView {
    void showProgress();

    void hideProgress();

    void setUsernameError();

    void setPasswordError();

    void navigateToHome();
}
