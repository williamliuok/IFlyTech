package com.siweikeji.demo.iflyteck;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import java.util.Locale;

/**
 * @Author William Liu
 * @Time 11:42
 * @Package com.siweikeji.demo.fourdagerobot
 * @Description
 */
public class SpeechUtil implements TextToSpeech.OnInitListener{
    private static TextToSpeech textToSpeech; // TTS对象
    private static Context mContext;

    private static SpeechUtil speechUtil = new SpeechUtil();

    public static void init(Context context){
        mContext = context;
        textToSpeech = new TextToSpeech(mContext,speechUtil);
    }

    public static void speak(String msg){
        if (textToSpeech != null && !textToSpeech.isSpeaking()) {
            // 设置音调，值越大声音越尖（女生），值越小则变成男声,1.0是常规
            textToSpeech.setPitch(0.5f);
            //设定语速 ，默认1.0正常语速
            textToSpeech.setSpeechRate(1.0f);
            //朗读，注意这里三个参数的added in API level 4   四个参数的added in API level 21
            textToSpeech.speak(msg, TextToSpeech.QUEUE_FLUSH, null,"");
        }
    }

    public static void stop(){
        textToSpeech.stop(); // 不管是否正在朗读TTS都被打断
//        textToSpeech.shutdown(); // 关闭，释放资源
    }

    public static boolean isSpeaking(){
        if(textToSpeech == null){
            return false;
        }

        return textToSpeech.isSpeaking();
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = textToSpeech.setLanguage(Locale.CHINA);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(mContext, "数据丢失或不支持", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(mContext, "语音输出已准备", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
