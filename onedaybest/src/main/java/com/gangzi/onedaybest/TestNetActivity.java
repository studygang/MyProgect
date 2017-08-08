package com.gangzi.onedaybest;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gangzi.onedaybest.utils.OkhttpManager;
import com.gangzi.onedaybest.utils.OkhttpManager.NetworkRequestListener;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class TestNetActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.bt_get)
    Button bt_get;
    @BindView(R.id.bt_post)
    Button bt_post;
    @BindView(R.id.tv_result)
    TextView tv_result;
    @BindView(R.id.bt_download)
    Button bt_download;

    private int ps=20;
    private String key="f6db366d5a4acbc5e75864c8435eff2f";
    private String dtype="json";
    private int pno=1;

    private static final String BASE_URL="http://v.juhe.cn/weixin/query";


    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    String result=msg.obj.toString();
                    tv_result.setText(result);
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_net);
        ButterKnife.bind(this);
        bt_get.setOnClickListener(this);
        bt_post.setOnClickListener(this);
        bt_download.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final Map<String,Object>map=new HashMap<>();
        map.put("pno",pno);
        map.put("ps",ps);
        map.put("key",key);
        map.put("dtype",dtype);
        switch (v.getId()){
            case R.id.bt_get:
                OkhttpManager.getInstance().doGet(BASE_URL, map, new NetworkRequestListener() {
                    @Override
                    public void onRequestSuccess(String result) {
                        System.out.println("result-----------------"+result);
                        tv_result.setText(result);
                      /*  Message message=Message.obtain();
                        message.what=0;
                        message.obj=result;
                        mHandler.sendMessage(message);*/
                    }

                    @Override
                    public void onRequestFail(String msg) {

                    }
                });
                break;
            case R.id.bt_post:
               OkhttpManager.getInstance().doPost(BASE_URL, map, new NetworkRequestListener() {
                    @Override
                    public void onRequestSuccess(String result) {
                        System.out.println("result-----------------"+result);
                         tv_result.setText(result);
                    }

                    @Override
                    public void onRequestFail(String msg) {

                    }
                });
             /* AsyncManager.execute(new Runnable() {
                    @Override
                    public void run() {
                        String result=OkhttpManager.getInstance().sendPost(BASE_URL,map);
                      //  System.out.println("result-----------------"+result);
                    }
                });*/
             /* new Thread(new Runnable() {
                  @Override
                  public void run() {
                      String result=OkhttpManager.getInstance().sendPost(BASE_URL,map);
                      System.out.println("result-----------------"+result);
                  }
              }).start();*/
                break;
            case R.id.bt_download:
                getSdPerssion();
                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void requestPermission() {
        TestNetActivityPermissionsDispatcher.getSdPerssionWithCheck(this);
    }
    //这也是必须使用的注解，用于标注在你要获取权限的方法，注解括号里面有参数，传入想要申请的权限。
    // 也就是说你获取了相应的权限之后就会执行这个方法：
    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void getSdPerssion(){
        String url="http://zxpic.gtimg.com/infonew/0/wechat_pics_-214358.jpg/168";
        String SDPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        String fileName="lulu.jpg";
        OkhttpManager.getInstance().downFile(url,fileName,SDPath, new OkhttpManager.OnDownloadListener() {
            @Override
            public void onDownloadSuccess() {
                //Toast.makeText(TestNetActivity.this,"下载完成",Toast.LENGTH_SHORT).show();
                System.out.println("------下载完成----");
            }

            @Override
            public void onDownloading(int progress, long total, int id) {
                System.out.println("progress--------"+progress+"total----------"+total);
            }

            @Override
            public void onDownloadFailed() {

            }
        });
    }
    //这个不是必须的注解，用于标注申请权限前需要执行的方法，注解括号里面有参数，传入想要申请的权限，
    // 而且这个方法还要传入一个PermissionRequest对象，这个对象有两种方法：proceed()让权限请求继续，
    // cancel()让请求中断。也就是说，这个方法会拦截你发出的请求，
    // 这个方法用于告诉用户你接下来申请的权限是干嘛的，说服用户给你权限。
    @OnShowRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void showRationale(final PermissionRequest request){
        new AlertDialog.Builder(this)
                .setMessage("申请SD卡权限")
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
    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void permissionDenied(){
        Toast.makeText(this, "已拒绝SD权限", Toast.LENGTH_SHORT).show();
        requestPermission();
    }
    //也不是必须的注解，用于标注如果权限请求失败,而且用户勾选不再询问的时候执行的方法，
    // 注解括号里面有参数，传入想要申请的权限。也就是说，
    // 我们可以在这个方法做申请权限失败并选择不再询问之后的处理。
    // 例如，可以告诉作者想开启权限的就从手机设置里面开启。
    @OnNeverAskAgain(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void neverAskAgain(){
        Toast.makeText(this, "已拒绝SD权限，并不再询问", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        TestNetActivityPermissionsDispatcher.onRequestPermissionsResult(this,requestCode,grantResults);
    }

}
