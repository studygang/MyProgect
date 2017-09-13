package com.gangzi.myprogect.service;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gangzi.myprogect.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;

/**
 * Created by dan on 2017/9/6.
 */

public class CheckAppIsUpdate {
    private CheckAppIsUpdate instance;

    private static final int NOTIFY_ID = 0;
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder builder;
    private Notification notification;

    //通知显示内容
    private PendingIntent pd;

    private OkHttpClient mOkHttpClient;

    private ProgressBar mProgressBar;
    private AlertDialog mDownLoadDialog;
    private Context mContext;
    private String savaPath;
    private int mProgerss;

    //private long mProgerss;

    private boolean isCancel;
    private static final String url="http://www.baidu.com/";

    private String mVersion_code;
    private String mVersion_name;
    private String mVersion_desc;
    private String mVersion_path;

    public CheckAppIsUpdate(Context context) {
        mContext=context;
    }
    private void initOkHttpClient() {
        //File sdcache = getExternalCacheDir();
        //int cacheSize = 10 * 1024 * 1024;
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS);
        //.cache(new Cache(sdcache.getAbsoluteFile(), cacheSize));
        mOkHttpClient = builder.build();
    }

    private Handler mGetVersionHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Map<String,String> map= (Map<String, String>) msg.obj;
            mVersion_code=map.get("versionCode");
            mVersion_name=map.get("versionName");
            mVersion_desc=map.get("versionDes");
            mVersion_path=map.get("versionPath");
            if (isUpdate()){
                Toast.makeText(mContext, "需要更新", Toast.LENGTH_SHORT).show();
                // 显示提示更新对话框
                showNoticeDialog();
            }else{
                Toast.makeText(mContext, "已是最新版本", Toast.LENGTH_SHORT).show();
            }
        }
    };

    /**
     * 检查软件更新
     */
    public void checkUpdate(){
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                //模拟服务器数据
                String versionCode="2";
                String versionName="测试更新";
                String versionDes="修复了一些已知的Bug";
                String versionPath="http://imtt.dd.qq.com/16891/E1DC6ABF1B4FB804A99F90CE371D1E6D.apk?fsname=cn.zwonline.shangji_3.3.1_2017191622.apk&csr=1bbd&crazycache=1";
                Map<String,String>map=new HashMap<String, String>();
                map.put("versionCode",versionCode);
                map.put("versionName",versionName);
                map.put("versionDes",versionDes);
                map.put("versionPath",versionPath);
                Message message=Message.obtain();
                message.obj=map;
                mGetVersionHandler.sendMessage(message);
            }
        });
    }

    /**
     * 判断是否需要更新
     * @return
     */
    protected boolean isUpdate(){
        int serverVersion=Integer.parseInt(mVersion_code);
        int localVersion=1;
        try {
            localVersion=mContext.getPackageManager().getPackageInfo("com.gangzi.myprogect",0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (serverVersion>localVersion){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 有更新时弹出提示框
     */
    private void showNoticeDialog() {
        AlertDialog.Builder updateDialog=new AlertDialog.Builder(mContext);
        View dialogView= LayoutInflater.from(mContext).inflate(R.layout.dialog_layout,null);
        TextView tv_title= (TextView) dialogView.findViewById(R.id.tv_title);
        tv_title.setText("检测到新版本");
        TextView tv_content= (TextView) dialogView.findViewById(R.id.tv_context);
        tv_content.setText("1.修复BUG\n2.优化界面\n3.欢迎大家下载");
        TextView tv_update= (TextView) dialogView.findViewById(R.id.update);
        TextView tv_cancel= (TextView) dialogView.findViewById(R.id.cancle);
        updateDialog.setView(dialogView);
        AlertDialog alertDialog=updateDialog.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        tv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogView.setVisibility(View.GONE);
                alertDialog.dismiss();
                //显示下载对话框
                // showDownloadDialog();
               // createNotification();
                //downloadAPK();
                Intent intent=new Intent(mContext, UpdateAppManagerService.class);
                intent.putExtra("mVersion_name",mVersion_name);
                intent.putExtra("mVersion_path",mVersion_path);
                mContext.startService(intent);
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }


}
