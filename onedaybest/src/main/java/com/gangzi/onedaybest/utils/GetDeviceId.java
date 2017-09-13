package com.gangzi.onedaybest.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.lang.reflect.Method;
import java.util.UUID;

/**
 * Created by dan on 2017/8/28.
 */

public class GetDeviceId {
    private Context context;

    public GetDeviceId(Context context) {
        this.context = context;
    }

    private String getDeviceId(){
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId =null,  androidId = null, mac= null, serial= null ;

        WifiManager wifiMan = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        try {
            if (PackageManager.PERMISSION_GRANTED == context.getPackageManager()
                    .checkPermission(Manifest.permission.READ_PHONE_STATE,
                            context.getPackageName())) {

                deviceId = tm.getDeviceId();

            }
            androidId =  android.provider.Settings.Secure.getString(context.getContentResolver(),
                    android.provider.Settings.Secure.ANDROID_ID);

            mac=wifiMan.getConnectionInfo().getMacAddress();

            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class, String.class );
            serial = (String) get.invoke(c, "ro.serialno", "unknown" );
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(TextUtils.isEmpty(deviceId )&& TextUtils.isEmpty(androidId)
                &&TextUtils.isEmpty(mac)&&TextUtils.isEmpty(serial)){
            return UUID.randomUUID().toString();
        }

        UUID deviceUuid = new UUID(androidId.hashCode(), deviceId .hashCode()| mac.hashCode()|serial.hashCode());
        String uniqueId = deviceUuid.toString();

        return uniqueId;

    }
}
