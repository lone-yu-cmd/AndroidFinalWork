package com.example.androidfinalwork.xiaoice;

import com.example.androidfinalwork.asr.com.baidu.speech.restapi.common.ConnUtil;
import com.example.androidfinalwork.asr.com.baidu.speech.restapi.common.DemoException;
import com.example.androidfinalwork.tts.TtsMain;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class XiaoIce {
    public static void main(String[] args) throws IOException, DemoException {
        (new XiaoIce()).run();
    }

//    public final String url = "http://tsn.baidu.com/text2audio"; // 可以使用https
    public final String url = "http://112.74.185.224:6789/chat"; // 可以使用https

    private void run() throws IOException, DemoException {
        // 此处2次urlencode， 确保特殊字符被正确编码

        String params = "text=" + ConnUtil.urlEncode(ConnUtil.urlEncode("你叫什么名字"));
        params += "&type=" + "text";
        String URL = url + "?" + params;
        System.out.println(url + "?" + params); // 反馈请带上此url，浏览器上可以测试
        HttpURLConnection conn = (HttpURLConnection) new URL(URL).openConnection();
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setConnectTimeout(5000);
        System.out.println(ConnUtil.getResponseString(conn));

    }

}
