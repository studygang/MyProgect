package com.gangzi.myprogect;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.gangzi.myprogect.base.BaseFragment;
import com.gangzi.myprogect.ui.cart.CartFragment;
import com.gangzi.myprogect.ui.home.HomeFragment;
import com.gangzi.myprogect.ui.type.TypeFragment;
import com.gangzi.myprogect.ui.user.UserFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_layout)
    FrameLayout main_layout;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.rg_main)
    RadioGroup rg_main;

    private ArrayList<BaseFragment>fragmentList;
    private BaseFragment mFragment;
    private HomeFragment mHomeFragment;
    private CartFragment mCartFragment;
    private TypeFragment mTypeFragment;
    private UserFragment mUserFragment;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initFragment();
        initListener();
    }

    private void initFragment() {
        fragmentList=new ArrayList<>();
        mHomeFragment=new HomeFragment();
        mTypeFragment=new TypeFragment();
        mUserFragment=new UserFragment();
        mCartFragment=new CartFragment();
        fragmentList.add(mHomeFragment);
        fragmentList.add(mTypeFragment);
        fragmentList.add(mCartFragment);
        fragmentList.add(mUserFragment);
    }

    private void initListener() {
        rg_main.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int i) {
                switch (i){
                    case R.id.rb_home:
                        position=0;
                        break;
                    case R.id.rb_type:
                        position=1;
                        break;
                    case R.id.rb_cart:
                        position=2;
                        break;
                    case R.id.rb_me:
                        position=3;
                        break;
                }
               BaseFragment fragment=getFragment(position);
                switchFragment(mFragment,fragment);
            }
        });
        rg_main.check(R.id.rb_home);
    }

    //根据位置得到对应的Fragment
    private BaseFragment getFragment(int position){
        if (fragmentList!=null||fragmentList.size()>0){
            BaseFragment fragment=fragmentList.get(position);
            return fragment;
        }
        return null;
    }

    private void switchFragment(Fragment fromFragment, BaseFragment nextFragment) {
        if (mFragment!=nextFragment){
            mFragment=nextFragment;
           if (nextFragment!=null){
               FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
               if (!nextFragment.isAdded()){
                   if (fromFragment!=null){
                       transaction.hide(fromFragment);
                   }
                   transaction.add(R.id.main_layout,nextFragment).commit();
               }else{
                   if (fromFragment!=null){
                       transaction.hide(fromFragment);
                   }
                   transaction.show(nextFragment).commit();
               }
           }
        }
    }


    public static DrawerLayout getDrawerLayout(){
        return null;
    }
}
