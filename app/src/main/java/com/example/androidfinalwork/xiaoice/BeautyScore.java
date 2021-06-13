package com.example.androidfinalwork.xiaoice;

import com.example.androidfinalwork.asr.com.baidu.speech.restapi.asrdemo.Base64Util;
import com.example.androidfinalwork.asr.com.baidu.speech.restapi.common.ConnUtil;
import com.example.androidfinalwork.asr.com.baidu.speech.restapi.common.DemoException;
//import com.example.androidfinalwork.asr.orgg.json.JSONObject;
import com.alibaba.fastjson.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class BeautyScore {

    public static void main(String[] args) throws IOException, DemoException {
        byte[] content = getFileContent("2 .jpg");

        String base = base64Encode(content);
        System.out.println(base);
        BeautyScore beautyScore = new BeautyScore();
        beautyScore.run(base);

    }
    public final String url = "http://112.74.185.224:6789/detect"; // 可以使用https

    public JSONObject run(String base) throws IOException, DemoException {
//        System.out.println("base");
//        System.out.println(base);

        // 此处2次urlencode， 确保特殊字符被正确编码
        JSONObject params = new JSONObject();
//        System.out.println(base);
        params.put("base", base);
//        base="b'"+base+"'";
        String URL = url + "?base=" + base;
        System.out.println(URL);
//        System.out.println(url + "?" + params); // 反馈请带上此url，浏览器上可以测试
//        File file = new File("result2.txt");
//        FileWriter fo = new FileWriter(file);
//        fo.write(URL);
//        fo.close();

        HttpURLConnection conn = (HttpURLConnection) new URL(URL).openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        conn.setDoOutput(true);
//        conn.getOutputStream().write(params.toString().getBytes());
        conn.getOutputStream().close();
        System.out.println("conntostring");
        System.out.println(conn.getResponseCode());
//        System.out.println(conn.getErrorStream());
//        System.out.println(conn.getResponseCode());
        String result = ConnUtil.getResponseString(conn);
//        System.out.println(result);
//        String result="{\"text\":\"a\",\"score\":\"b\"}";
        JSONObject resultJson= JSONObject.parseObject(result);
        System.out.println(resultJson.getString("text"));
        System.out.println(resultJson.getString("score"));
        return resultJson;


    }

    private static byte[] getFileContent(String filename) throws DemoException, IOException {
        File file = new File(filename);
        if (!file.canRead()) {
            System.err.println("文件不存在或者不可读: " + file.getAbsolutePath());
            throw new DemoException("file cannot read: " + file.getAbsolutePath());
        }
        FileInputStream is = null;
        try {
            is = new FileInputStream(file);
            return ConnUtil.getInputStreamContent(is);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    public static String base64Encode(byte[] content) {
        /**
         Base64.Encoder encoder = Base64.getEncoder(); // JDK 1.8  推荐方法
         String str = encoder.encodeToString(content);
         **/

        char[] chars = Base64Util.encode(content); // 1.7 及以下，不推荐，请自行跟换相关库
        String str = new String(chars);

        return str;
    }
}
