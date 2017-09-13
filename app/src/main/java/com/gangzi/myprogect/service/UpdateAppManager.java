package com.gangzi.myprogect.service;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gangzi.myprogect.BuildConfig;
import com.gangzi.myprogect.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.os.Build.VERSION_CODES.N;

/**
 * 应用自动更新
 * Created by gangzi on 2017/6/14.
 */

public class UpdateAppManager {

    private static final int DOWNLOADING = 1;
    private static final int DOWNLOAD_FINISH = 2;

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

    public UpdateAppManager(Context context) {
        mContext = context;
        initOkHttpClient();
       // mNotificationManager = (NotificationManager) context.getSystemService(android.content.Context.NOTIFICATION_SERVICE);
        //Intent intent = new Intent(mContext,UpdateAppManager.class);
        //pd = PendingIntent.getActivity(mContext, 0, intent, 0);
        //createNotification();
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
            Map<String,String>map= (Map<String, String>) msg.obj;
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

    private Handler mUpdateProgressHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case DOWNLOADING:
                    // 设置进度条
                    //mProgressBar.setProgress(mProgerss);
                    /*new Thread(new Runnable() {
                        @Override
                        public void run() {
                            RemoteViews contentview = notification.contentView;
                            // contentview.setTextViewText(R.id.tv_progress_download,mProgerss + "%");
                            contentview.setProgressBar(R.id.download_progress, 100, mProgerss, false);
                            mNotificationManager.notify(NOTIFY_ID, notification);
                        }
                    }).start();*/
                    builder.setProgress(100, mProgerss, false);
                    builder.setContentText("下载进度:" + mProgerss + "%");
                    notification = builder.build();
                    mNotificationManager.notify(NOTIFY_ID, notification);
                    break;
                case DOWNLOAD_FINISH:
                    // 隐藏当前下载对话框
                    //mDownLoadDialog.dismiss();
                    builder.setContentTitle("");
                     builder.setContentText("下载完成");//下载完成
                     builder.setProgress(0,0,false);    //移除进度条
                     notification = builder.build();
                     mNotificationManager.notify(NOTIFY_ID, notification);
                    mNotificationManager.cancel(NOTIFY_ID);
                    // 安装 APK 文件
                    installAPK();
                    break;
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
                createNotification();
                downloadAPK();
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

    private void showDownloadDialog() {
        AlertDialog.Builder downBuilder=new AlertDialog.Builder(mContext);
        View downView=LayoutInflater.from(mContext).inflate(R.layout.dialog_progress_down,null);
        LinearLayout linearLayout= (LinearLayout) downView.findViewById(R.id.linears);
        RelativeLayout relativeLayout= (RelativeLayout) downView.findViewById(R.id.relativeLayout1);
        mProgressBar= (ProgressBar) downView.findViewById(R.id.id_progress);
        TextView tv_title= (TextView) downView.findViewById(R.id.tv_title);
        tv_title.setText("正在下载中...");
        TextView tv_cancel= (TextView) downView.findViewById(R.id.cancle);
        downBuilder.setView(downView);
        mDownLoadDialog=downBuilder.create();
        mDownLoadDialog.setCanceledOnTouchOutside(false);
        mDownLoadDialog.show();
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relativeLayout.setVisibility(View.GONE);
                downView.setVisibility(View.GONE);
                linearLayout.setVisibility(View.GONE);
                mDownLoadDialog.dismiss();
                // 设置下载状态为取消
                isCancel=true;
            }
        });
        // 下载文件
        downloadAPK();
    }

    private void downloadAPK() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                  if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        String sdPath = Environment.getExternalStorageDirectory() + "/";
                        savaPath = sdPath + "myproject";
                        File file = new File(savaPath);
                        if (!file.exists()) {
                            file.mkdir();
                        }
                        //  downloadFiles();
                        downloadFile();
                  }
            }
        }).start();
    }

    private void downloadFiles() throws IOException {
        //下载文件
        HttpURLConnection connection = (HttpURLConnection) new URL(mVersion_path).openConnection();
        connection.connect();
        InputStream is=connection.getInputStream();
        int length=connection.getContentLength();
        File apkFile=new File(savaPath,mVersion_name);
        FileOutputStream fos=new FileOutputStream(apkFile);
        int count=0;
        byte[] buffer=new byte[1024];
        while (!isCancel){
            int numRead=is.read(buffer);
            count+=numRead;
            mProgerss=(int) (((float)count/length)*100);
            // 更新进度条
            mUpdateProgressHandler.sendEmptyMessage(DOWNLOADING);
            // 下载完成
            if (numRead < 0){
                mUpdateProgressHandler.sendEmptyMessage(DOWNLOAD_FINISH);
                break;
            }
            fos.write(buffer, 0, numRead);
        }
        fos.close();
        is.close();
    }

    private void downloadFile() {
        Request request = new Request.Builder().url(mVersion_path).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) {
                InputStream inputStream = response.body().byteStream();
                int length= (int) response.body().contentLength();
                FileOutputStream fileOutputStream = null;
                try {
                    File apkFile=new File(savaPath,mVersion_name);
                    fileOutputStream = new FileOutputStream(apkFile);
                    byte[] buffer = new byte[1024];
                    long beforeTime = System.currentTimeMillis();
                    int count = 0;
                    while (!isCancel){
                        int numRead=inputStream.read(buffer);
                        count+=numRead;
                        //mProgerss=(int) (((float)count/length)*100);
                         mProgerss=(int) (((double) count / (double) length) * 100);
                        //1秒 更新2次进度 非常重要 否则 系统会慢慢卡死
                        if (System.currentTimeMillis() - beforeTime > 500) {
                            mUpdateProgressHandler.sendEmptyMessage(DOWNLOADING);
                            beforeTime = System.currentTimeMillis();
                        }
                        //mProgerss=((count/length)*100);
                        // 更新进度条
                       // mUpdateProgressHandler.sendEmptyMessage(DOWNLOADING);
                       // builder.setProgress(length, mProgerss, false);
                       // mNotificationManager.notify(NOTIFY_ID, builder.build());
                        // 下载完成
                        if (numRead < 0){
                            //mNotificationManager.cancel(NOTIFY_ID);
                            mUpdateProgressHandler.sendEmptyMessage(DOWNLOAD_FINISH);
                           // builder.setContentText("Download complete")//下载完成
                                    //.setProgress(0,0,false);    //移除进度条
                           // mNotificationManager.notify(NOTIFY_ID, builder.build());
                          //  installAPK();
                            break;
                        }
                        fileOutputStream.write(buffer, 0, numRead);
                       // RemoteViews contentview = notification.contentView;
                        // contentview.setTextViewText(R.id.tv_progress_download,mProgerss + "%");
                        //contentview.setProgressBar(R.id.download_progress, length, mProgerss, false);
                       // mNotificationManager.notify(NOTIFY_ID, notification);
                    }
                   /* while ((len = inputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, len);
                    }*/
                    fileOutputStream.flush();
                    inputStream.close();
                    fileOutputStream.close();
                } catch (IOException e) {
                    Log.i("wangshu", "IOException");
                    e.printStackTrace();
                }

                Log.d("wangshu", "文件下载成功");
            }
        });
    }

    /**
     * 安装Apk
     */
    private void installAPK() {
        File apkFile = new File(savaPath, mVersion_name);
        if (!apkFile.exists())
            return;

        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(mContext, BuildConfig.APPLICATION_ID + ".fileprovider", apkFile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            //Uri uri = Uri.parse("file://" + apkFile.toString());
            //intent.setDataAndType(uri, "application/vnd.android.package-archive");
        }

        mContext.startActivity(intent);
    }
    private void createNotification(){
        mNotificationManager = (NotificationManager)mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        builder=new NotificationCompat.Builder(mContext);
        builder.setContentTitle("正在下载中...")
                .setContentText("Download in progress")
                .setSmallIcon(R.drawable.news);
    }
}
