package com.dreame.apk_update;

import android.content.Context;

import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;

public class ApkUpdate {

    public static void initBugly(Context context){
        Bugly.init(context, "39f296dd6b", false);
        Beta.initDelay = 1 * 1000;
        Beta.enableNotification = true;//设置是否显示消息通知
    }
}
