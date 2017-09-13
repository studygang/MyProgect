package com.gangzi.myprogect.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.gangzi.myprogect.BuildConfig;
import com.gangzi.myprogect.R;
import com.gangzi.myprogect.utils.SPUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.os.Build.VERSION_CODES.N;

/**
 * Created by dan on 2017/9/6.
 */

public class UpdateAppManagerService extends Service{

    private static final int DOWNLOADING = 1;
    private static final int DOWNLOAD_FINISH = 2;

    private static final int NOTIFY_ID = 0;
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder builder;
    private Notification notification;

    //通知显示内容
    private PendingIntent pd;

    private OkHttpClient mOkHttpClient;

    private String savaPath;
    private int mProgerss;

    //private long mProgerss;

    private boolean isCancel;
    private static final String url="http://www.baidu.com/";

    private String mVersion_name;
    private String mVersion_desc;
    private String mVersion_path;

    private boolean isFirstStart=true;


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

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initOkHttpClient();
        createNotification();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent!=null){
           String mVersion_names=intent.getStringExtra("mVersion_name");
           String mVersion_paths=intent.getStringExtra("mVersion_path");
           SPUtils.setSharedStringData(this,"mVersion_names",mVersion_names);
           SPUtils.setSharedStringData(this,"mVersion_paths",mVersion_paths);
            //isFirstStart=false;
            //mVersion_name=intent.getStringExtra("mVersion_name");
            //mVersion_path=intent.getStringExtra("mVersion_path");
        }
        mVersion_name=SPUtils.getSharedStringData(this,"mVersion_names");
        mVersion_path=SPUtils.getSharedStringData(this,"mVersion_paths");
        downloadAPK();
        return super.onStartCommand(intent, flags, startId);
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
            Uri contentUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileprovider", apkFile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            //Uri uri = Uri.parse("file://" + apkFile.toString());
            //intent.setDataAndType(uri, "application/vnd.android.package-archive");
        }

        startActivity(intent);
    }

    private void createNotification(){
        mNotificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        builder=new NotificationCompat.Builder(this);
        builder.setContentTitle("正在下载中...")
                .setContentText("Download in progress")
                .setSmallIcon(R.drawable.news);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
