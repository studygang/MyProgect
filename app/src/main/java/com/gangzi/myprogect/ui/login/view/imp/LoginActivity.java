package com.gangzi.myprogect.ui.login.view.imp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gangzi.myprogect.R;
import com.gangzi.myprogect.ui.login.model.LoginModel;
import com.gangzi.myprogect.ui.login.model.imp.LoginModelImp;
import com.gangzi.myprogect.ui.login.pressenter.LoginPressenter;
import com.gangzi.myprogect.ui.login.pressenter.imp.LoginPressenterImp;
import com.gangzi.myprogect.ui.login.view.LoginView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gangzi on 2017/6/20.
 */

public class LoginActivity extends AppCompatActivity implements LoginView, View.OnClickListener{

    @BindView(R.id.et_username)
    EditText et_userName;
    @BindView(R.id.et_passeord)
    EditText et_passWord;
    @BindView(R.id.bt_login)
    Button bt_login;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private String userName,password;

    private LoginPressenter mLoginPressenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        bt_login.setOnClickListener(this);
        mLoginPressenter=new LoginPressenterImp(this);
    }

    @Override
    protected void onDestroy() {
        mLoginPressenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setUsernameError() {
        et_userName.setError("用户名错误!");
    }

    @Override
    public void setPasswordError() {
        et_passWord.setError("密码错误!");
    }

    @Override
    public void navigateToHome() {
        Toast.makeText(this,"登陆成功!",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_login:
                userName=et_userName.getText().toString();
                password=et_passWord.getText().toString();
                mLoginPressenter.validateCredentials(userName,password);
                break;
        }
    }
}
