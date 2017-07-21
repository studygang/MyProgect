package com.gangzi.myprogect.service;

import android.content.Intent;

import com.allenliu.versionchecklib.AVersionService;

/**
 * Created by Administrator on 2017/6/6.
 */

public class UpdateAppService extends AVersionService {

    private int serverVersion=2;
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
            service.showVersionDialog("http://imtt.dd.qq.com/16891/E1DC6ABF1B4FB804A99F90CE371D1E6D.apk?fsname=cn.zwonline.shangji_3.3.1_2017191622.apk&csr=1bbd&crazycache=1","检测到新版本","1.修复BUG\n2.优化界面\n3.欢迎大家下载");
        }
    }
}
