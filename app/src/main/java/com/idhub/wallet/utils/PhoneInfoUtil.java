package com.idhub.wallet.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.text.TextUtils;

import com.idhub.wallet.App;

import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PhoneInfoUtil {


    public static String getDeviceMac(){
        String mac = "";
        PackageManager pm = App.getInstance().getPackageManager();
        if(pm.checkPermission(Manifest.permission.ACCESS_WIFI_STATE,
                App.getInstance().getPackageName()) == PackageManager.PERMISSION_GRANTED){
            WifiManager wifi = (WifiManager) App.getInstance().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            mac = wifi.getConnectionInfo().getMacAddress();
        }
        //获取不到 或 获取到默认值 则通过另一种方法再次获取
        if(TextUtils.isEmpty(mac) || "02:00:00:00:00:00".equals(mac)){
            mac = getMacAddr();
        }
        return mac;
    }

    /**
     * 获取Mac地址
     */


    public static String getMacAddr() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:",b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
        }
        return generateRandomMac();
    }

    /**
     * 获取不到则生成一个随机的mac地址
     * @return
     */
    private static String generateRandomMac(){
        StringBuilder randomMac=new StringBuilder();
        for(int i=0; i<6; i++){
            Random r=new Random();
            int hex=r.nextInt(256);
            String hexStr=Integer.toHexString(hex);
            randomMac.append(hexStr);
            if(i!=5) randomMac.append(":");
        }
        return randomMac.toString().toUpperCase();
    }

    public static String getDeviceSerialNumber(){
        String serialno = "";
        try {
            Class<?> c =Class.forName("android.os.SystemProperties");
            Method get =c.getMethod("get", String.class);
            serialno = (String)get.invoke(c, "ro.serialno");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serialno;
    }
    public static String getUserAgent(){
        String userAgent =  System.getProperty("http.agent");
        StringBuffer sb = new StringBuffer();
        for (int i = 0, length = userAgent.length(); i < length; i++) {
            char c = userAgent.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", (int) c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static String getAppVersionName() {
        PackageManager manager = App.getInstance().getPackageManager();
        String versionName = "";
        try {
            PackageInfo info = manager.getPackageInfo(App.getInstance().getPackageName(),0);
            versionName = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }
}
