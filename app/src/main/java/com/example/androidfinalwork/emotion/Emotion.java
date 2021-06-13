package com.example.androidfinalwork.emotion;

import com.example.androidfinalwork.asr.com.baidu.speech.restapi.common.ConnUtil;
import com.example.androidfinalwork.asr.com.baidu.speech.restapi.common.DemoException;
import com.example.androidfinalwork.asr.orgg.json.JSONObject;
import com.example.androidfinalwork.tts.TtsMain;
import com.example.androidfinalwork.xiaoice.XiaoIce;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Emotion {
    public static void main(String[] args) throws IOException, DemoException {
        (new Emotion()).run();
    }

    //  填写网页上申请的appkey 如 $apiKey="g8eBUMSokVB1BHGmgxxxxxx"
    private final String appKey = "DyvsKKqGUGUvMhhV37bQZzE0";

    // 填写网页上申请的APP SECRET 如 $secretKey="94dc99566550d87f8fa8ece112xxxxx"
    private final String secretKey = "00WpT2w7ZyVyDznej90x7cXqzxuZhkaQ";

    public  String url = "https://aip.baidubce.com/rpc/2.0/nlp/v1/emotion"; // 可以使用https

    private String cuid = "1234567JAVA";

    private void run() throws IOException, DemoException {
//        TokenHolder holder = new TokenHolder(appKey, secretKey, TokenHolder.ASR_SCOPE);
//        holder.refresh();
//        String token = holder.getToken();
        String token = "24.2b870efd421dba511b2f8be6645d3238.2592000.1626167392.282335-24365641";
        url = url + "?charset=UTF-8";
        url = url + "&access_token=" +token;
        JSONObject params = new JSONObject();
        params.put("text", "本来今天高高兴兴");
        //params.put("lm_id",LM_ID);//测试自训练平台需要打开注释
        params.put("scene","talk");
//        params.put("access_token", token);
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        conn.setDoOutput(true);
        conn.getOutputStream().write(params.toString().getBytes());
        conn.getOutputStream().close();
        String result = ConnUtil.getResponseString(conn);
        System.out.println(result);
    }

    // 下载的文件格式, 3：mp3(default) 4： pcm-16k 5： pcm-8k 6. wav
    private String getFormat(int aue) {
        String[] formats = {"mp3", "pcm", "pcm", "wav"};
        return formats[aue - 3];
    }
}
