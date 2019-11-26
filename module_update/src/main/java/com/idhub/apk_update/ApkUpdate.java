package com.idhub.apk_update;

import android.content.Context;

import com.idhub.config.ConfigPropertiesUtils;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;

public class ApkUpdate {

    public static void initBugly(Context context){
        Bugly.init(context, ConfigPropertiesUtils.getBuglyAppId(context), false);
        Beta.initDelay = 1 * 1000;
        Beta.enableNotification = true;//设置是否显示消息通知
    }
}
