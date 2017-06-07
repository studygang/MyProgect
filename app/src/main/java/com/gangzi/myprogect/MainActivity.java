package com.gangzi.myprogect;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.allenliu.versionchecklib.AVersionService;
import com.allenliu.versionchecklib.HttpRequestMethod;
import com.allenliu.versionchecklib.VersionParams;
import com.gangzi.myprogect.base.BaseFragment;
import com.gangzi.myprogect.service.UpdateAppService;
import com.gangzi.myprogect.ui.cart.CartFragment;
import com.gangzi.myprogect.ui.home.HomeFragment;
import com.gangzi.myprogect.ui.login.view.imp.LoginActivity;
import com.gangzi.myprogect.ui.type.view.imp.TypeFragment;
import com.gangzi.myprogect.ui.user.UserFragment;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    @BindView(R.id.main_layout)
    FrameLayout main_layout;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.rg_main)
    RadioGroup rg_main;
    @BindView(R.id.navigation)
    NavigationView mNavigationView;
    private ImageView icon_image;

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
        initNavigationView();
        checkUpdateApp();
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

    /**
     * 得到渠道
     * @return
     */
    private String getChannel(){
        PackageManager packageManager=getPackageManager();
        try {
            ApplicationInfo applicationInfo=packageManager.getApplicationInfo(getPackageName(),PackageManager.GET_META_DATA);
            return applicationInfo.metaData.getString("UMENG_CHANNEL");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    private boolean mBackKeyPressed = false;// 记录是否有首次按键

    @Override
    public void onBackPressed() {
        if (!mBackKeyPressed){
            Toast.makeText(this,"再按一次退出程序",Toast.LENGTH_SHORT).show();
            mBackKeyPressed=true;
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    mBackKeyPressed=false;
                }
            },2000);
        }else{
            this.finish();
            System.exit(0);
        }
    }
    //NavigationView初始化
    private void initNavigationView() {
        mNavigationView.setItemIconTintList(null);
        View headView= mNavigationView.getHeaderView(0);
        icon_image = (ImageView) headView.findViewById(R.id.icon_image);
        mNavigationView.setNavigationItemSelectedListener(this);
        setHomeItemState();
        login();
    }

    private void login() {
        icon_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                mDrawerLayout.closeDrawer(GravityCompat.START);
            }
        });
    }

    private void setHomeItemState() {
       Menu menu= mNavigationView.getMenu();
        MenuItem item=menu.getItem(0);
        //更多中  特殊情况  取消选中状态
       // menu.getItem(5).getSubMenu().getItem(0).setChecked(false);
       // menu.getItem(5).getSubMenu().getItem(1).setChecked(false);
        item.setChecked(true);
    }

    private void checkUpdateApp() {
        VersionParams versionParams=new VersionParams();
        versionParams.setRequestUrl("http://www.baidu.com");
        versionParams.setRequestMethod(HttpRequestMethod.GET);
        Intent intent=new Intent(this, UpdateAppService.class);
        intent.putExtra(AVersionService.VERSION_PARAMS_KEY,versionParams);
        intent.putExtra("versionCode",getVerCode(this));
        startService(intent);
    }

    /**
     * 获取软件版本号
     * @param context
     * @return
     */
    public static int getVerCode(Context context) {
        int verCode = -1;
        try {
            //注意："com.example.try_downloadfile_progress"对应AndroidManifest.xml里的package="……"部分
            verCode = context.getPackageManager().getPackageInfo(
                    "com.gangzi.myprogect", 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("msg",e.getMessage());
        }
        return verCode;
    }
    /**
     * 获取版本名称
     * @param context
     * @return
     */
    public static String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().getPackageInfo(
                    "com.gangzi.myprogect", 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("msg",e.getMessage());
        }
        return verName;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId=item.getItemId();
        switch (itemId){
            case R.id.bt_home:
                break;
            case R.id.bt_type:
                startActivity(new Intent(this,TypeFragment.class));
                break;
            case R.id.bt_cart:
                startActivity(new Intent(this,CartFragment.class));
                break;
            case R.id.bt_me:
                break;
        }
        item.setChecked(true);
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
