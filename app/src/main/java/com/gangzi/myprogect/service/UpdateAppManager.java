package com.gangzi.myprogect.service;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

import okhttp3.Call;

/**
 * 应用自动更新
 * Created by gangzi on 2017/6/14.
 */

public class UpdateAppManager {

    private static final int DOWNLOADING = 1;
    private static final int DOWNLOAD_FINISH = 2;

    private ProgressBar mProgressBar;
    private Dialog mDownLoadDialog;
    private Context mContext;
    private String savaPath;
    private int mProgerss;

    private boolean isCancel;
    private static final String url="http://www.baidu.com/";

    private String mVersion_code;
    private String mVersion_name;
    private String mVersion_desc;
    private String mVersion_path;

    public UpdateAppManager(Context context) {
        mContext = context;
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
                    mProgressBar.setProgress(mProgerss);
                    break;
                case DOWNLOAD_FINISH:
                    // 隐藏当前下载对话框
                    mDownLoadDialog.dismiss();
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
                String versionPath="http://123.125.110.15/imtt.dd.qq.com/16891/A2165D036E69E4271664B70FCA6446ED.apk?mkey=593653e882f06f80&f=1d58&c=0&fsname=com.tencent.qqgame_6.8.6_60625.apk&csr=1bbd&p=.apk";
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
        tv_content.setText("检测到新版本\n1.修复BUG\n2.优化界面\n3.欢迎大家下载");
        TextView tv_update= (TextView) dialogView.findViewById(R.id.update);
        TextView tv_cancel= (TextView) dialogView.findViewById(R.id.cancle);
        updateDialog.setView(dialogView);
        tv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogView.setVisibility(View.GONE);
                //显示下载对话框
                showDownloadDialog();
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        updateDialog.create().show();
    }

    private void showDownloadDialog() {
        AlertDialog.Builder downBuilder=new AlertDialog.Builder(mContext);
        View downView=LayoutInflater.from(mContext).inflate(R.layout.dialog_progress_down,null);
        mProgressBar= (ProgressBar) downView.findViewById(R.id.id_progress);
        TextView tv_title= (TextView) downView.findViewById(R.id.tv_title);
        tv_title.setText("正在下载中...");
        TextView tv_cancel= (TextView) downView.findViewById(R.id.cancle);
        downBuilder.setView(downView);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downView.setVisibility(View.GONE);
                // 设置下载状态为取消
                isCancel=true;
            }
        });
        mDownLoadDialog=downBuilder.create();
        mDownLoadDialog.show();
        // 下载文件
        downloadAPK();
    }

    private void downloadAPK() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                    try {
                        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                            String sdPath = Environment.getExternalStorageDirectory() + "/";
                            savaPath = sdPath + "myproject";
                            File file = new File(savaPath);
                            if (!file.exists()) {
                                file.mkdir();
                            }
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
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
        }).start();
    }

    /**
     * 安装Apk
     */
    private void installAPK() {
        File apkFile = new File(savaPath, mVersion_name);
        if (!apkFile.exists())
            return;

        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.parse("file://" + apkFile.toString());
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        mContext.startActivity(intent);
    }
}
