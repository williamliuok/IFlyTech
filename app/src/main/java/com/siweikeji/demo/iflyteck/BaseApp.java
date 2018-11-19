package com.siweikeji.demo.iflyteck;

import android.app.Application;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;


/**
 * @Author William Liu
 * @Time 11:31
 * @Package com.siweikeji.demo.fourdagerobot
 * @Description
 */
public class BaseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        StringBuffer param = new StringBuffer();
        param.append("appid=5bdff3ff");
        param.append(",");
        param.append(SpeechConstant.ENGINE_MODE + "=" + SpeechConstant.MODE_MSC);
        // param.append(",");
        // param.append(SpeechConstant.FORCE_LOGIN + "=true");
        SpeechUtility.createUtility(this, param.toString());
    }
}
