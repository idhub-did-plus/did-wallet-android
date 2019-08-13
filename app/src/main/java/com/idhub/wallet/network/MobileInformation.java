package com.idhub.wallet.network;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.idhub.wallet.App;
import com.idhub.wallet.utils.PhoneInfoUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import okhttp3.Headers;

public class MobileInformation {

    public int app_version_code;
    public String app_version_name = "";
    public String device_model = Build.MODEL;
    public String device_manufacturer = Build.MANUFACTURER;
    public String device_mac = "";
    public String device_imei = "";
    public String device_imsi = "";
    public String device_serialno = "";
    public String os_type = "android";
    public String os_version = Build.VERSION.RELEASE;
    public String os_screen_resolution = "";
    public String os_is_root = "";
    public String os_api_level = String.valueOf(Build.VERSION.SDK_INT);
    public String os_id = "";
    public float os_density;
    public String env_user_agent = "";
    public String env_net = "4G";


    private static MobileInformation sMe = null;

    public static MobileInformation getInstance(){
        if(sMe == null){
            sMe = new MobileInformation();
        }
        return sMe;
    }

    private MobileInformation(){
        getAppVersionNameAndCode();
        getDeviceImsiAndImeiAndUniquelyCode();
        getDeviceScreenResolution();
        getAndroidId();
        device_serialno = PhoneInfoUtil.getDeviceSerialNumber();
        os_density = Resources.getSystem().getDisplayMetrics().density;
        env_user_agent = PhoneInfoUtil.getUserAgent();
    }

    private void getDeviceScreenResolution() {
        int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        int screenHeight= Resources.getSystem().getDisplayMetrics().heightPixels;
        os_screen_resolution = screenWidth + "x" +screenHeight;
    }

    @SuppressLint("MissingPermission")
    private void getDeviceImsiAndImeiAndUniquelyCode() {
        TelephonyManager tm = (TelephonyManager) App.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
        device_mac = PhoneInfoUtil.getDeviceMac();
        try {
            device_imsi = tm.getSubscriberId();
            if (TextUtils.isEmpty(device_imsi)) {
                device_imsi = "none1103";
            }
        } catch (SecurityException e) {
            device_imsi = "none1101";
        } catch (Exception e) {
            device_imsi = "none1102";
        }

//        device_imsi = tm.getDeviceId();

        if (TextUtils.isEmpty(device_imei)) {
            // 地址
            if (!TextUtils.isEmpty(device_mac)) { // mac 优先
                device_imei = "rmac" + device_mac + device_model + os_version;
            } else {
                device_imei = "imei1";
                if (device_imei.length() > 2) {
                } else {
                    int aa = (int) Math.pow(10, 14);
                    Random random = new Random();
                    device_imei = "rrand" + random.nextInt(aa) + aa + "";// 随机产生imei,避免多次重复,r代表没imei的手机用户
                    //T_MoXiuConfigHelper.setMobileRandImei(imei);
                }
            }
        }
    }

    private void getAppVersionNameAndCode() {
        PackageManager manager = App.getInstance().getPackageManager();
        PackageInfo info;
        try {
            info = manager.getPackageInfo(App.getInstance().getPackageName(),0);
            app_version_name = info.versionName;
            app_version_code = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取移动设备序列号
     */
    private void getAndroidId(){
        os_id = Settings.Secure.getString(App.getInstance().getContentResolver(), Settings.Secure.ANDROID_ID);
    }
//    public int app_version_code;
//    public String app_version_name = "";
//    public String device_model = Build.MODEL;
//    public String device_manufacturer = Build.MANUFACTURER;
//    public String device_mac = "";
//    public String device_imei = "";
//    public String device_imsi = "";
//    public String device_serialno = "";
//    public String os_type = "android";
//    public String os_version = Build.VERSION.RELEASE;
//    public String os_screen_resolution = "";
//    public String os_is_root = "";
//    public String os_api_level = String.valueOf(Build.VERSION.SDK_INT);
//    public String os_id = "";
//    public float os_density;
//    public String env_user_agent = "";
//    public String env_net = "";
    public Headers getMobilePhoneHeaders(){
        Map<String,String> map = new HashMap<>();
        map.put("app_version_code",app_version_code+"");
        map.put("app_version_name",app_version_name);
        map.put("device_model",device_model);
        map.put("device_manufacturer",device_manufacturer);
        map.put("device_mac",device_mac);
        map.put("device_imei",device_imei);
        map.put("device_imsi",device_imsi);
        map.put("device_serialno",device_serialno);
        map.put("os_type",os_type);
        map.put("os_version",os_version);
        map.put("os_screen_resolution",os_screen_resolution);
        map.put("os_api_level",os_api_level);
        map.put("os_id",os_id);
        map.put("os_density",os_density+"");
        map.put("env_user_agent",env_user_agent);
        map.put("env_net",env_net);
        Headers headers = Headers.of(map);
        return headers;
    }
}
