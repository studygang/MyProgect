package com.gangzi.myprogect.service;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

import com.allenliu.versionchecklib.AVersionService;

/**
 * Created by Administrator on 2017/6/6.
 */

public class UpdateAppService extends AVersionService {

    private int serverVersion;
    private int clientVersion;

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        clientVersion=intent.getIntExtra("versionCode",-1);
        System.out.println("------clientVersion------"+clientVersion);
    }

    @Override
    public void onResponses(AVersionService service, String response) {
        System.out.println("--------updateresponse-----"+response);
        if (serverVersion>clientVersion){
            service.showVersionDialog("http://123.125.110.15/imtt.dd.qq.com/16891/A2165D036E69E4271664B70FCA6446ED.apk?mkey=593653e882f06f80&f=1d58&c=0&fsname=com.tencent.qqgame_6.8.6_60625.apk&csr=1bbd&p=.apk","检测到新版本","1.修复BUG\n2.优化界面\n3.欢迎大家下载");
        }
    }
}
