package com.gangzi.myprogect;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.gangzi.myprogect.utils.CacheUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelcomeActivity extends AppCompatActivity {

    @BindView(R.id.welcome_image)
    ImageView welComeImage;

    private boolean isFirst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        isFirst=CacheUtils.getBoolean(this,CacheUtils.ISFIRST);

        welComeImage.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isFirst){
                    startActivity(new Intent(WelcomeActivity.this,SplashActivity.class));
                    WelcomeActivity.this.finish();
                }else{
                    startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                    WelcomeActivity.this.finish();
                }
            }
        },2000);
    }
}
