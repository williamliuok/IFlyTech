package com.siweikeji.demo.iflyteck;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.VoiceWakeuper;
import com.iflytek.cloud.WakeuperListener;
import com.iflytek.cloud.WakeuperResult;
import com.iflytek.cloud.util.ResourceUtil;
import com.iflytek.cloud.util.ResourceUtil.RESOURCE_TYPE;
import com.siweikeji.demo.fourdagerobot.R;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import java.util.Random;

/**
 * @Author William Liu
 * @Time 12:38
 * @Package com.siweikeji.demo.fourdagerobot
 * @Description
 */
public class IflytekWakeUp {

    private Context mContext;
    //唤醒的阈值，就相当于门限值，当用户输入的语音的置信度大于这一个值的时候，才被认定为成功唤醒。
    private int curThresh = 1600;
    //是否持续唤醒
    private String keep_alive = "1";
    private String[] helloword = new String[]{"嗨,我在", "小四在此，谁在放肆", "有人想小四了么"};
    private Random random = new Random();

    /**
     * 闭环优化网络模式有三种：
     * 模式0：关闭闭环优化功能
     * <p>
     * 模式1：开启闭环优化功能，允许上传优化数据。需开发者自行管理优化资源。
     * sdk提供相应的查询和下载接口，请开发者参考API文档，具体使用请参考本示例
     * queryResource及downloadResource方法；
     * <p>
     * 模式2：开启闭环优化功能，允许上传优化数据及启动唤醒时进行资源查询下载；
     * 本示例为方便开发者使用仅展示模式0和模式2；
     */
    private String ivwNetMode = "0";
    // 语音唤醒对象
    private VoiceWakeuper mIvw;
    //存储唤醒词的ID
    private String wordID = "";

    public IflytekWakeUp(Context context) {
        this.mContext = context;
        // 初始化唤醒对象
        mIvw = VoiceWakeuper.createWakeuper(mContext, null);
    }

    /**
     * 开启唤醒功能
     */
    public void startWakeuper() {
        //非空判断，防止因空指针使程序崩溃
        mIvw = VoiceWakeuper.getWakeuper();
        if (mIvw != null) {
            // 清空参数
            mIvw.setParameter(SpeechConstant.PARAMS, null);
            // 唤醒门限值，根据资源携带的唤醒词个数按照“id:门限;id:门限”的格式传入
            mIvw.setParameter(SpeechConstant.IVW_THRESHOLD, "0:" + curThresh);
            // 设置唤醒模式
            mIvw.setParameter(SpeechConstant.IVW_SST, "wakeup");
            // 设置持续进行唤醒
            mIvw.setParameter(SpeechConstant.KEEP_ALIVE, keep_alive);
            // 设置闭环优化网络模式
            mIvw.setParameter(SpeechConstant.IVW_NET_MODE, ivwNetMode);
            // 设置唤醒资源路径
            mIvw.setParameter(SpeechConstant.IVW_RES_PATH, getResource());
            // 设置唤醒录音保存路径，保存最近一分钟的音频
            mIvw.setParameter(SpeechConstant.IVW_AUDIO_PATH, Environment.getExternalStorageDirectory().getPath() + "/msc/ivw.wav");
            mIvw.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
            // 如有需要，设置 NOTIFY_RECORD_DATA 以实时通过 onEvent 返回录音音频流字节
            //mIvw.setParameter( SpeechConstant.NOTIFY_RECORD_DATA, "1" );

            // 启动唤醒
            mIvw.startListening(new MyWakeuperListener());
        }
    }

    /**
     * 获取唤醒词功能
     *
     * @return 返回文件位置
     */
    private String getResource() {
        final String resPath = ResourceUtil.generateResourcePath(mContext, RESOURCE_TYPE.assets, "ivw/" + mContext.getString(R.string.IflytekAPP_id) + ".jet");
        return resPath;
    }

    /**
     * 销毁唤醒功能
     */
    public void destroyWakeuper() {
        // 销毁合成对象
        mIvw = VoiceWakeuper.getWakeuper();
        if (mIvw != null) {
            mIvw.destroy();
        }
    }

    /**
     * 停止唤醒
     */
    public void stopWakeuper() {
        mIvw.stopListening();
    }

    /**
     * 唤醒词监听类
     *
     * @author Administrator
     */
    public class MyWakeuperListener implements WakeuperListener {

        @Override
        public void onBeginOfSpeech() {

        }

        @Override
        public void onResult(WakeuperResult result) {
//            if (!"1".equalsIgnoreCase(keep_alive)) {
//                setRadioEnable(true);
//            }
//            try {
//                String text = result.getResultString();
//                JSONObject object;
//                object = new JSONObject(text);
//                StringBuffer buffer = new StringBuffer();
//                buffer.append("【RAW】 "+text);
//                buffer.append("\n");
//                buffer.append("【操作类型】"+ object.optString("sst"));
//                buffer.append("\n");
//                buffer.append("【唤醒词id】"+ object.optString("id"));
//                buffer.append("\n");
//                buffer.append("【得分】" + object.optString("score"));
//                buffer.append("\n");
//                buffer.append("【前端点】" + object.optString("bos"));
//                buffer.append("\n");
//                buffer.append("【尾端点】" + object.optString("eos"));
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
            SpeechUtil.speak(helloword[random.nextInt(3)]);
            while(SpeechUtil.isSpeaking()){

            }
            if(mContext instanceof MainActivity){
                MainActivity activity = (MainActivity)mContext;
                if(activity.checkAIUIAgent()){
                    activity.startVoiceNlp();
                }
            }
            Toast.makeText(mContext, result.getResultString(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SpeechError speechError) {

        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }

        @Override
        public void onVolumeChanged(int i) {

        }
    }
}
