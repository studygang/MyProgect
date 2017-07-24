package com.gangzi.onedaybest;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.gangzi.onedaybest.adapter.MainAdapter;
import com.gangzi.onedaybest.bean.WeChatData;
import com.gangzi.onedaybest.message.Message;
import com.gangzi.onedaybest.pressenter.WeChatPressenter;
import com.gangzi.onedaybest.pressenter.imp.WeChatPresenterImp;
import com.gangzi.onedaybest.ui.WeChatView;
import com.gangzi.onedaybest.widget.MyProgressDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

//这是必须使用的注解，用于标注在你想要申请权限的Activity或者Fragment上，如demo里面的PermissionsDispatcherActivity：
@RuntimePermissions
public class MainActivity extends AppCompatActivity implements WeChatView{

    @BindView(R.id.toobal)
    Toolbar mToolbar;
    @BindView(R.id.swip_refresh)
    MaterialRefreshLayout mRefreshLayout;
    //SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.recycle)
    RecyclerView mRecyclerView;

    private MyProgressDialog mProgressDialog;
    private WeChatPressenter mWeChatPressenter;
    private MainAdapter adapter;
    //private CenterListAdapter2 adapter;
  //  private CenterListAdapter4 adapter;
    //private TestAdapter adapter;
    //private CenterListAdapter4 mCenterListAdapter4;
    private int lastVisibleItem;
    private  boolean isLoading = false;
    private  List<WeChatData.ResultBean.ListBean>tempdataList;

    private int ps=20;
    private String key="f6db366d5a4acbc5e75864c8435eff2f";
    private String dtype="json";

    private static final int NORMAL=0;
    private static final int REFRESH=1;
    private static final int LOADING=2;

    private int status=NORMAL;
    private int pno=1;
    private int currentPager;
    private int totalPager=2;
    private List<WeChatData.ResultBean.ListBean>data;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        mProgressDialog=new MyProgressDialog(this,"正在加载中...");
        mWeChatPressenter=new WeChatPresenterImp(this,false);
        initData();
        ButterKnife.bind(this);
        mRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                refreshData();
            }

           /* @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
                if (pno<totalPager){
                    loadData();
                }else{
                    //mRefreshLayout.setLoadMore(false);
                    Toast.makeText(MainActivity.this,"已经到底部了", Toast.LENGTH_SHORT).show();
                    mRefreshLayout.finishRefreshLoadMore();
                }
            }*/
        });
        final LinearLayoutManager manager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState ==RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 ==adapter.getItemCount()) {
                    loadData();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem =manager.findLastVisibleItemPosition();
            }
        });
    }

    private void initData() {
        //status=NORMAL;
       // pno=1;
        mWeChatPressenter.getWeChatData(pno,ps,key,dtype);
    }

    private void refreshData(){
        pno=1;
        status=REFRESH;
        mWeChatPressenter.getWeChatData(pno,ps,key,dtype);
    }
    private void loadData(){
        status=LOADING;
        pno++;
        mWeChatPressenter.getWeChatData(pno,ps,key,dtype);
    }

    @Override
    public void showProgress() {
        //mProgressDialog.show();
    }

    @Override
    public void hideProgress() {
        //mProgressDialog.dismiss();
    }

    @Override
    public void loadWeChat(WeChatData weChatData) {
        data=weChatData.getResult().getList();
        //showData(data);
        showData2(data);
    }

    @Override
    public void refreshData(WeChatData weChatData) {

    }

    @Override
    public void loadMoreData(WeChatData weChatData) {

    }

    private void showData2(List<WeChatData.ResultBean.ListBean> data) {

        switch (status){
            case NORMAL:
                adapter=new MainAdapter(this,data);
                mRecyclerView.setAdapter(adapter);
                break;
            case REFRESH:
                adapter.cleanData();
                adapter.addData(0,data);
                mRefreshLayout.finishRefresh();
                break;
            case LOADING:
                /*if (pno<=totalPager){
                    adapter.setBottomView(true);
                    adapter.setLoading(true);
                    adapter.addData(adapter.getcountData(), data);
                }else{
                    adapter.setBottomView(false);
                    adapter.setLoading(false);
                   // status=NORMAL;
                }*/
                break;
        }

    }

    private void showData(List<WeChatData.ResultBean.ListBean> data) {
        switch (status){
            case NORMAL:
                adapter=new MainAdapter(this,data);
                //adapter=new MainAdapter(this,data);
                mRecyclerView.setAdapter(adapter);
                LinearLayoutManager manager=new LinearLayoutManager(this);
                mRecyclerView.setLayoutManager(manager);
                break;
            case REFRESH:
                //adapter.cleanData();
                //adapter.addData(data);
              //  mRecyclerView.scrollToPosition(0);
                mRefreshLayout.finishRefresh();
               // adapter.setBottomView(false);
               // mRecyclerView.refreshComlete();
                break;
            case LOADING:
                adapter.addData(adapter.getcountData(), data);
              //  mRecyclerView.scrollToPosition(adapter.getcountData());
                //adapter.notifyDataSetChanged();
                mRefreshLayout.finishRefreshLoadMore();
               // mRecyclerView.refreshComlete();
               /*if (pno<=totalPager){
                    adapter.setBottomView(true);
                }else{
                    adapter.setBottomView(false);
                    //status=NORMAL;
                }*/
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void requestCameraPermission(Message message){
        String code=message.code;
        System.out.println("消息code-------------"+code);
        if (code.equals("openCamer")){
            requestPermission();
        }
    }
    @TargetApi(Build.VERSION_CODES.M)
    private void requestPermission() {
        MainActivityPermissionsDispatcher.openCameraWithCheck(this);
    }
    //这也是必须使用的注解，用于标注在你要获取权限的方法，注解括号里面有参数，传入想要申请的权限。
    // 也就是说你获取了相应的权限之后就会执行这个方法：
    @NeedsPermission(Manifest.permission.CAMERA)
    public void openCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 启动系统相机
        startActivity(intent);
    }
    //这个不是必须的注解，用于标注申请权限前需要执行的方法，注解括号里面有参数，传入想要申请的权限，
    // 而且这个方法还要传入一个PermissionRequest对象，这个对象有两种方法：proceed()让权限请求继续，
    // cancel()让请求中断。也就是说，这个方法会拦截你发出的请求，
    // 这个方法用于告诉用户你接下来申请的权限是干嘛的，说服用户给你权限。
    @OnShowRationale(Manifest.permission.CAMERA)
    public void showRationale(final PermissionRequest request){
        new AlertDialog.Builder(this)
                .setMessage("申请相机权限")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();//继续申请权限
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                request.cancel();
            }
        }).show();
    }
    //也不是必须的注解，用于标注如果权限请求失败，但是用户没有勾选不再询问的时候执行的方法，
    // 注解括号里面有参数，传入想要申请的权限。也就是说，我们可以在这个方法做申请权限失败之后的处理，
    // 如像用户解释为什么要申请，或者重新申请操作等。
    @OnPermissionDenied(Manifest.permission.CAMERA)
    public void permissionDenied(){
        Toast.makeText(this, "已拒绝CAMERA权限", Toast.LENGTH_SHORT).show();
        requestPermission();
    }
    //也不是必须的注解，用于标注如果权限请求失败,而且用户勾选不再询问的时候执行的方法，
    // 注解括号里面有参数，传入想要申请的权限。也就是说，
    // 我们可以在这个方法做申请权限失败并选择不再询问之后的处理。
    // 例如，可以告诉作者想开启权限的就从手机设置里面开启。
    @OnNeverAskAgain(Manifest.permission.CAMERA)
    public void neverAskAgain(){
        Toast.makeText(this, "已拒绝CAMERA权限，并不再询问", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this,requestCode,grantResults);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
