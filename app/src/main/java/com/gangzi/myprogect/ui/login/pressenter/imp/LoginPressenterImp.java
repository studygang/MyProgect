package com.gangzi.myprogect.ui.login.pressenter.imp;

import com.gangzi.myprogect.ui.login.model.LoginModel;
import com.gangzi.myprogect.ui.login.model.OnLoginFinishedListener;
import com.gangzi.myprogect.ui.login.model.imp.LoginModelImp;
import com.gangzi.myprogect.ui.login.pressenter.LoginPressenter;
import com.gangzi.myprogect.ui.login.view.LoginView;

/**
 * Created by gangzi on 2017/6/20.
 */

public class LoginPressenterImp implements LoginPressenter,OnLoginFinishedListener{

    private LoginView mLoginView;
    private LoginModel mLoginModel;
    public LoginPressenterImp(LoginView mLoginView) {
        this.mLoginView=mLoginView;
        mLoginModel=new LoginModelImp();
    }

    @Override
    public void validateCredentials(String username, String password) {
        if (mLoginView!=null){
            mLoginView.showProgress();
        }
        mLoginModel.login(username,password,this);
    }

    @Override
    public void onDestroy() {
        mLoginView=null;
    }

    @Override
    public void onUsernameError() {
        if (mLoginView!=null){
            mLoginView.setUsernameError();
            mLoginView.hideProgress();
        }
    }

    @Override
    public void onPasswordError() {
        if (mLoginView!=null){
            mLoginView.setPasswordError();
            mLoginView.hideProgress();
        }
    }

    @Override
    public void onSuccess() {
        if (mLoginView != null) {
            mLoginView.navigateToHome();
        }
    }
}
