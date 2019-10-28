package com.idhub.wallet.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.idhub.base.App;


/**
 * 网络状态工具类
 * Created by fzp on 16/8/26.
 */

public class NetStatusUtils {

    private static ConnectivityManager sConnectivityManager;
    private static TelephonyManager sTelephonyManager;

    /**
     * 状态码 0 为无网络 1为 wifi 2 为3G网络 3 为2G网络 4 为4G网络
     */
    public enum NetStatus {
        noNetStatus(0), wifiNetStatus(1), threeGNetStatus(2), twoGNetStatus(3), fourNetStatus(
                4);
        private int value;

        NetStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public NetStatusUtils() {
        super();
    }

    /**
     * 获取网络连接管理器
     *
     * @param context  上下文
     */
    private static ConnectivityManager getConnectivityManager(Context context) {
        if (sConnectivityManager == null) {
            sConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        }

        return sConnectivityManager;
    }

    /**
     * 获取通讯管理器
     *
     * @param
     * @return 返回通讯管理对象
     */
    private static TelephonyManager getTelephonyManager() {
        if (sTelephonyManager == null) {
            sTelephonyManager = (TelephonyManager) App.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
        }

        return sTelephonyManager;
    }


    /**
     * 获取网络状态 enum
     *
     * @param
     * @return 返回网络状态enum 见NetStatus
     */
    public static NetStatus getNetWorkStatus() {
        ConnectivityManager connectivityManager = getConnectivityManager(App.getInstance());
        try {
            if (connectivityManager != null) {
                NetworkInfo networkInfowifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                if (networkInfowifi != null) {
                    NetworkInfo.State wifi = networkInfowifi.getState();
                    if (wifi == NetworkInfo.State.CONNECTED) {
                        return NetStatus.wifiNetStatus;
                    }
                }

                NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                if (networkInfo != null) {
                    NetworkInfo.State mobile = networkInfo.getState();
                    if (mobile == NetworkInfo.State.CONNECTED) {
                        TelephonyManager telephonyManager = (TelephonyManager) App.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
                        if(telephonyManager.getNetworkType() == TelephonyManager.NETWORK_TYPE_LTE){
                            return NetStatus.fourNetStatus;
                        } else if (isFastMobileNetwork()) {
                            return NetStatus.threeGNetStatus;
                        } else {
                            return NetStatus.twoGNetStatus;
                        }
                    }
                }
            }
        } catch (Exception e) {
        }

        return NetStatus.noNetStatus;
    }

    /**
     * 是否处于无网状态
     *
     *
     * @return 返回true表示无网。
     */
    public static boolean inNoNetworkStatus() {
        NetStatus netstate = getNetWorkStatus();
        return netstate == NetStatus.noNetStatus;
    }

    /**
     * 是否处于弱网状态
     *
     * @param context   上下文
     * @return  返回true表示弱网。
     */
    public static boolean inWeakNetworkStatus(Context context) {
        NetStatus netstate = getNetWorkStatus();

        return netstate == NetStatus.twoGNetStatus;
    }

    /**
     * 是否处于强网状态
     * @param context     上下文
     * @return 返回true表示处于WIFI或4G网
     */
    public static boolean inStrongNetworkStatus(Context context) {
        NetStatus netstate = getNetWorkStatus();

        return netstate == NetStatus.wifiNetStatus || netstate == NetStatus.fourNetStatus;
    }

    /**
     * 移动判断是否是3G/4G网络
     * @return  返回true表示处于 3G或4G
     */
    public static boolean isFastMobileNetwork() {
        TelephonyManager telephonyManager = getTelephonyManager();
        switch (telephonyManager.getNetworkType()) {
            case TelephonyManager.NETWORK_TYPE_1xRTT:
                return false; // ~ 50-100 kbps
            case TelephonyManager.NETWORK_TYPE_CDMA:
                return false; // ~ 14-64 kbps
            case TelephonyManager.NETWORK_TYPE_EDGE:
                return false; // ~ 50-100 kbps
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                return true; // ~ 400-1000 kbps
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                return true; // ~ 600-1400 kbps
            case TelephonyManager.NETWORK_TYPE_GPRS:
                return false; // ~ 100 kbps
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                return true; // ~ 2-14 Mbps
            case TelephonyManager.NETWORK_TYPE_HSPA:
                return true; // ~ 700-1700 kbps
            case TelephonyManager.NETWORK_TYPE_HSUPA:
                return true; // ~ 1-23 Mbps
            case TelephonyManager.NETWORK_TYPE_UMTS:
                return true; // ~ 400-7000 kbps
            case TelephonyManager.NETWORK_TYPE_EHRPD:
                return true; // ~ 1-2 Mbps
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
                return true; // ~ 5 Mbps
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return true; // ~ 10-20 Mbps
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return false; // ~25 kbps
            case TelephonyManager.NETWORK_TYPE_LTE:
                return true; // ~ 10+ Mbps
            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                return false;
            default:
                return false;
        }
    }

    /**
     * 是否有网
     *
     * @param context  上下文
     * @return  返回true表示有网
     *
     * @param context  上下文
     * @return  返回true表示有网
     */
    public static boolean isConnectedNetwork(Context context) {
        return isOnlyFastMobile(context, false);
    }

    /**
     * 是否有 WIFI 或 4G 或 3G (网速快)
     *
     * @param context  上下文
     * @return  返回true 表示处于强度高的网络
     */
    public static boolean isConnectedFastNetwork(Context context) {
        return isOnlyFastMobile(context, true);
    }

    /**
     * 是否只判断快速网络
     *
     * @param context  上下文
     * @param fast 是否判断强度
     * @return  返回true表示只判断强度高的网络
     */
    private static boolean isOnlyFastMobile(Context context, boolean fast) {
        ConnectivityManager manager = getConnectivityManager(context);
        if (manager == null) {
            return false;
        }

        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null) {
            if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                return true;
            } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                TelephonyManager tm = getTelephonyManager();
                if (tm != null && (tm.getDataState() == TelephonyManager.DATA_CONNECTED ||
                        tm.getDataState() == TelephonyManager.DATA_ACTIVITY_NONE)) {
                    if (fast) {
                        return isFastMobileNetwork();
                    } else {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * 是否有连接网络
     *
     * @param context  上下文
     * @return  返回true表示有连接网络。
     */
    public static boolean isConnectedActiveNetwork(Context context) {
        try {
            ConnectivityManager NetWork = getConnectivityManager(context);

            NetworkInfo networkInfo = NetWork.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            // NullPointerException
            // coolpad 8185, 4.1.2
        }

        return false;
    }

    /**
     * 获取网络状态是否是wifi
     *
     * @param context 上下文
     * @return  是否处理WIFI下
     * */
    public static boolean isConnectWifiNetState(Context context) {
        ConnectivityManager connManager = getConnectivityManager(context);
        if (connManager == null) return false;
        NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (networkInfo == null) return false;
        NetworkInfo.State state =networkInfo.getState();
        if (state == NetworkInfo.State.CONNECTED) {
            return true;
        }
        return false;
    }

    /**
     * 获取网络状态是否是数据网络
     *
     * @param context 上下文
     * @return  是否处理数据网络下
     * */
    public static boolean isConnectMobileNetState(Context context) {
        ConnectivityManager connManager = getConnectivityManager(context);
        try {
            //平板可能没有移动数据模块
            NetworkInfo.State state = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
            if (state == NetworkInfo.State.CONNECTED) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return false;
    }
}
