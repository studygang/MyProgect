package com.gangzi.myprogect.ui.login.model.imp;

import android.os.Handler;
import android.text.TextUtils;

import com.gangzi.myprogect.ui.login.model.LoginModel;
import com.gangzi.myprogect.ui.login.model.OnLoginFinishedListener;

import org.w3c.dom.Text;

/**
 * Created by gangzi on 2017/6/20.
 */

public class LoginModelImp implements LoginModel {
    @Override
    public void login(String account, String password, OnLoginFinishedListener onLoginFinishedListener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean error = false;
                if (TextUtils.isEmpty(account)){
                    onLoginFinishedListener.onUsernameError();
                    error=true;
                }
                if (TextUtils.isEmpty(password)){
                    onLoginFinishedListener.onPasswordError();
                    error=true;
                }
                if (!error){
                    onLoginFinishedListener.onSuccess();
                }
            }
        },2000);
    }
}
