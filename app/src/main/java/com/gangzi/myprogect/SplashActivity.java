package com.gangzi.myprogect;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.gangzi.myprogect.adapter.WelcomeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.viewpagers)
    ViewPager mViewPager;
    @BindView(R.id.bt_start_enter)
    Button bt_start_enter;

    private int[] welcomePic={R.drawable.pic1,R.drawable.pic2,R.drawable.pic3};
    private WelcomeAdapter mWelcomeAdapter;
    private List<ImageView>mImageViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        mImageViews=new ArrayList<>();
        bt_start_enter.setOnClickListener(this);
        for (int i=0;i<welcomePic.length;i++){
            ImageView imageView=new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(welcomePic[i]);
            mImageViews.add(imageView);
        }
        mWelcomeAdapter=new WelcomeAdapter(this,mImageViews);
        mViewPager.setAdapter(mWelcomeAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position==welcomePic.length-1){
                    bt_start_enter.setVisibility(View.VISIBLE);

                }else{
                    bt_start_enter.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_start_enter:
                startActivity(new Intent(this,MainActivity.class));
                this.finish();
                break;
        }
    }
}
