package com.example.androidfinalwork.Activity;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import com.alibaba.fastjson.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidfinalwork.Msg;
import com.example.androidfinalwork.MsgAdapter;
import com.example.androidfinalwork.R;
import com.example.androidfinalwork.XUIApplication;
import com.example.androidfinalwork.asr.com.baidu.speech.restapi.common.DemoException;
import com.example.androidfinalwork.emotion.Emotion;
import com.example.androidfinalwork.tts.TtsMain;
import com.example.androidfinalwork.xiaoice.XiaoIce;
import com.google.android.material.navigation.NavigationView;

import org.w3c.dom.ls.LSOutput;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private List<Msg> msgList = new ArrayList<>();
    private EditText inputText;
    private Button send;
    private RecyclerView recyclerView;
    private MsgAdapter adapter;
    private boolean Send = true;
    MediaPlayer receivedMp3 = new MediaPlayer();
//        @Override
//        public void onWindowFocusChanged(boolean hasFocus) {
//          super.onWindowFocusChanged(hasFocus);
//          if(hasFocus){
//              System.out.println(true);
//          }else {
//              System.out.println(false);
//          }
//                XiaoIce xiaoIce = new XiaoIce();
//                if(inputText.getText().toString().length()==0&&msgList.size()!=0&&msgList.get(msgList.size()-1).getType()==Msg.TYPE_SEND){
//
//                    try {
//                        String receivedStr = xiaoIce.run(msgList.get(msgList.size()-1).getContent());
//                        System.out.println(receivedStr+"is exist!");
//                        JSONObject receivedJson= JSONObject.parseObject(receivedStr);
////                        if(receivedJson.isEmpty()){
////                            System.out.println("is empty!");
////                        }
//                        receivedJson.getString("text");
//                        Msg received = new Msg( receivedJson.getString("text"),Msg.TYPE_RECEIVED);
//                        msgList.add(received);
//                        adapter.notifyItemInserted(msgList.size() - 1);
//                        recyclerView.scrollToPosition(msgList.size() - 1);
//
//                        receivedMp3.start();
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    } catch (DemoException e) {
//                        e.printStackTrace();
//                    }
//                }
//        }
DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            initReceivedMp3();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_drag_handle_24);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation);

        navigationView.setNavigationItemSelectedListener(item -> {
            if(item.getItemId() == R.id.beautyScore){
                Intent intentMusic = new Intent(MainActivity.this,Score.class);
                startActivity(intentMusic);
                return true;
            }else if(item.getItemId() == R.id.analysis){
                Intent intentVideo = new Intent(MainActivity.this,emotionActivity.class);
                startActivity(intentVideo);
                return true;
            }else {
                return false;
            }
        });

        initMsgs();
        inputText = (EditText) findViewById(R.id.input_text);
        send = (Button) findViewById(R.id.send);
        recyclerView = (RecyclerView) findViewById(R.id.msg_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new MsgAdapter(msgList);
        recyclerView.setAdapter(adapter);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //如果应用之前请求过此权限但用户拒绝了请求，此方法将返回 true。
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {//这里可以写个对话框之类的项向用户解释为什么要申请权限，并在对话框的确认键后续再次申请权限.它在用户选择"不再询问"的情况下返回false
            } else {
                //申请权限，字符串数组内是一个或多个要申请的权限，1是申请权限结果的返回参数，在onRequestPermissionsResult可以得知申请结果
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,}, 1);
            }
        }


        send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String content = inputText.getText().toString();
                if (!"".equals(content)) {
                    Msg msg= new Msg(content, Msg.TYPE_SEND);;
                    msgList.add(msg);
                    adapter.notifyItemInserted(msgList.size() - 1);
                    recyclerView.scrollToPosition(msgList.size() - 1);
                    inputText.setText("");

                    Handler mHandler = new Handler(){
                        @Override
                        public void handleMessage(Message msg) {
                            switch (msg.what){
                                case 1:
                                String receivedStr = String.valueOf(msg.obj);
                                JSONObject receivedJson= JSONObject.parseObject(receivedStr);
//                                String result = receivedJson.getString("text");
//                                String[] split = result.split("[\u4e00-\u9fa5]*");
//                                result="";
//                                    for (int i = 0; i < split.length; i++) {
//                                        result+=split[i];
//                                    }
                                    Msg received = new Msg( receivedJson.getString("text"),Msg.TYPE_RECEIVED);
                                msgList.add(received);
                                adapter.notifyItemInserted(msgList.size() - 1);
                                recyclerView.scrollToPosition(msgList.size() - 1);
                                    ((XUIApplication) getApplication()).chatCount++;
                                    System.out.println(((XUIApplication) getApplication()).chatCount);
                                TtsMain ttsMain = new TtsMain();
                                    try {
                                        ttsMain.run(received.getContent());
                                        initReceivedMp3();
                                        receivedMp3.start();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    } catch (DemoException e) {
                                        e.printStackTrace();
                                    }
                                    Emotion emotion = new Emotion();
                                    Map<String, Integer> xiaoIce = ((XUIApplication) getApplication()).xiaoIce;
                                    Map<String, Integer> once = null;
                                    try {
                                        once = emotion.run(received.getContent());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    } catch (DemoException e) {
                                        e.printStackTrace();
                                    }
                                    int count=0;
                                    if(once.get("like")>0){
                                        count =  xiaoIce.get("like") + once.get("like");
                                        ((XUIApplication) getApplication()).xiaoIce.put("like",count);
                                        count=0;
                                    }
                                    if(once.get("happy")>0){
                                        count =  xiaoIce.get("happy") + once.get("happy");
                                        ((XUIApplication) getApplication()).xiaoIce.put("happy",count);
                                        count=0;
                                    }

                                    if(once.get("fearful")>0){
                                        count =  xiaoIce.get("fearful") + once.get("fearful");
                                        ((XUIApplication) getApplication()).xiaoIce.put("fearful",count);
                                        count=0;
                                    }
                                    if(once.get("angry")>0){
                                        count =  xiaoIce.get("angry") + once.get("angry");
                                        ((XUIApplication) getApplication()).xiaoIce.put("angry",count);
                                        count=0;
                                    }
                                    if(once.get("sad")>0){
                                        count =  xiaoIce.get("sad") + once.get("sad");
                                        ((XUIApplication) getApplication()).xiaoIce.put("sad",count);
                                        count=0;
                                    }
                                    System.out.println(((XUIApplication) getApplication()).xiaoIce);
                                    break;
                                default:
                                    break;
                            }
                        }
                    };

                    new Thread(new Runnable() {
                        public void run() {
                        XiaoIce xiaoIce = new XiaoIce();
                        if(inputText.getText().toString().length()==0&&msgList.size()!=0&&msgList.get(msgList.size()-1).getType()==Msg.TYPE_SEND){
                            try {
                                ((XUIApplication) getApplication()).chatCount++;
                                System.out.println(((XUIApplication) getApplication()).chatCount);
                                String receivedStr = xiaoIce.run(msgList.get(msgList.size()-1).getContent());
                                Emotion emotion = new Emotion();
                                Map<String, Integer> user = ((XUIApplication) getApplication()).user;
                                Map<String, Integer> once = emotion.run(msgList.get(msgList.size() - 1).getContent());
                                int count=0;
                                if(once.get("like")>0){
                                    count =  user.get("like") + once.get("like");
                                    ((XUIApplication) getApplication()).user.put("like",count);
                                    count=0;
                                }
                                if(once.get("happy")>0){
                                    count =  user.get("happy") + once.get("happy");
                                    ((XUIApplication) getApplication()).user.put("happy",count);
                                    count=0;
                                }
                                if(once.get("fearful")>0){
                                    count =  user.get("fearful") + once.get("fearful");
                                    ((XUIApplication) getApplication()).user.put("fearful",count);
                                    count=0;
                                }
                                if(once.get("angry")>0){
                                    count =  user.get("angry") + once.get("angry");
                                    ((XUIApplication) getApplication()).user.put("angry",count);
                                    count=0;
                                }
                                if(once.get("sad")>0){
                                    count =  user.get("sad") + once.get("sad");
                                    ((XUIApplication) getApplication()).user.put("sad",count);
                                    count=0;
                                }

                                System.out.println(((XUIApplication) getApplication()).user);
                                Message message = mHandler.obtainMessage();
                                message.what = 1;
                                //传递对象
                                message.obj = receivedStr;
                                mHandler.sendMessage(message);
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (DemoException e) {
                                e.printStackTrace();
                            }
                        }
                    }}).start();




                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }else {
            return false;
        }
    }

    private void initReceivedMp3() throws IOException {
        receivedMp3 = new MediaPlayer();
//        System.out.println("11111");
//        if (receivedMp3 != null) {
//            receivedMp3.stop();
//            System.out.println("1111221");
//        }
        AssetManager assets = getAssets();
//        AssetFileDescriptor fd = assets.openFd("result.mp3");
        File file = new File(Environment.getExternalStorageDirectory(),"result.mp3");

        receivedMp3.setDataSource(file.getPath());System.out.println("1111221333");
//        receivedMp3.setDataSource(fd.getFileDescriptor(),fd.getStartOffset(),fd.getLength());
        receivedMp3.prepare();

    }
    private void initMsgs() {
//        Msg msg1 = new Msg("hello guy", Msg.TYPE_RECEIVED);
//        msgList.add(msg1);
//        Msg msg2 = new Msg("hello guy2", Msg.TYPE_SEND);
//        msgList.add(msg2);
//        Msg msg3 = new Msg("hello guy3", Msg.TYPE_RECEIVED);
//        msgList.add(msg3);
    }
}