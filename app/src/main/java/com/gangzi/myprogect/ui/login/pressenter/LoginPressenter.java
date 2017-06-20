package com.gangzi.myprogect.ui.login.pressenter;

/**
 * Created by gangzi on 2017/6/20.
 */

public interface LoginPressenter {
    void validateCredentials(String username, String password);
    void onDestroy();
}
