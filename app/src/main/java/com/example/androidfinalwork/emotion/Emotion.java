package com.example.androidfinalwork.emotion;

import com.example.androidfinalwork.asr.com.baidu.speech.restapi.asrdemo.Base64Util;
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
//        String test = "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";
//        byte[] bytes = Base64Util.decode(test.toCharArray());
//        System.out.println(Base64Util.decode(test));
//            File file = new File("resultImage.jpg");
//            FileOutputStream os = new FileOutputStream(file);
//            os.write(bytes);
//            os.close();
//            System.out.println("audio file write to " + file.getAbsolutePath());

//            System.err.println("ERROR: content-type= " + contentType);
//            System.err.println(res);

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
