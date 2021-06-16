//package com.example.androidfinalwork.Activity;
//
//
//import android.content.res.AssetFileDescriptor;
//import android.content.res.AssetManager;
//import android.media.MediaPlayer;
//import android.os.Bundle;
//import android.os.Environment;
//import android.os.Handler;
//import android.os.Message;
//import android.os.StrictMode;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.view.View;
//import android.view.ViewTreeObserver;
//import android.widget.Button;
//import android.widget.EditText;
//import com.alibaba.fastjson.*;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.androidfinalwork.Msg;
//import com.example.androidfinalwork.MsgAdapter;
//import com.example.androidfinalwork.R;
//import com.example.androidfinalwork.asr.com.baidu.speech.restapi.common.DemoException;
//import com.example.androidfinalwork.tts.TtsMain;
//import com.example.androidfinalwork.xiaoice.XiaoIce;
//
//import org.w3c.dom.ls.LSOutput;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Timer;
//import java.util.TimerTask;
//
//public class MainActivity extends AppCompatActivity {
//    private List<Msg> msgList = new ArrayList<>();
//    private EditText inputText;
//    private Button send;
//    private RecyclerView recyclerView;
//    private MsgAdapter adapter;
//    private boolean Send = true;
//    MediaPlayer receivedMp3 = new MediaPlayer();
////        @Override
////        public void onWindowFocusChanged(boolean hasFocus) {
////          super.onWindowFocusChanged(hasFocus);
////          if(hasFocus){
////              System.out.println(true);
////          }else {
////              System.out.println(false);
////          }
////                XiaoIce xiaoIce = new XiaoIce();
////                if(inputText.getText().toString().length()==0&&msgList.size()!=0&&msgList.get(msgList.size()-1).getType()==Msg.TYPE_SEND){
////
////                    try {
////                        String receivedStr = xiaoIce.run(msgList.get(msgList.size()-1).getContent());
////                        System.out.println(receivedStr+"is exist!");
////                        JSONObject receivedJson= JSONObject.parseObject(receivedStr);
//////                        if(receivedJson.isEmpty()){
//////                            System.out.println("is empty!");
//////                        }
////                        receivedJson.getString("text");
////                        Msg received = new Msg( receivedJson.getString("text"),Msg.TYPE_RECEIVED);
////                        msgList.add(received);
////                        adapter.notifyItemInserted(msgList.size() - 1);
////                        recyclerView.scrollToPosition(msgList.size() - 1);
////
////                        receivedMp3.start();
////
////                    } catch (IOException e) {
////                        e.printStackTrace();
////                    } catch (DemoException e) {
////                        e.printStackTrace();
////                    }
////                }
////        }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        try {
//            initReceivedMp3();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        initMsgs();
//        inputText = (EditText) findViewById(R.id.input_text);
//        send = (Button) findViewById(R.id.send_button);
//        recyclerView = (RecyclerView) findViewById(R.id.msg_recycler_view);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(linearLayoutManager);
//
//        adapter = new MsgAdapter(msgList);
//        recyclerView.setAdapter(adapter);
//        if (android.os.Build.VERSION.SDK_INT > 9) {
//            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//            StrictMode.setThreadPolicy(policy);
//        }
//        recyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
//            @Override
//            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
//
//            }
//        });
//        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
////                XiaoIce xiaoIce = new XiaoIce();
////
////                if(inputText.getText().toString().length()==0&&msgList.size()!=0&&msgList.get(msgList.size()-1).getType()==Msg.TYPE_SEND){
////
////                    try {
////                        String receivedStr = xiaoIce.run(msgList.get(msgList.size()-1).getContent());
////                        System.out.println(receivedStr+"is exist!");
////                        JSONObject receivedJson= JSONObject.parseObject(receivedStr);
//////                        if(receivedJson.isEmpty()){
//////                            System.out.println("is empty!");
//////                        }
////                        receivedJson.getString("text");
////                        Msg received = new Msg( receivedJson.getString("text"),Msg.TYPE_RECEIVED);
////                        msgList.add(received);
////                        adapter.notifyItemInserted(msgList.size() - 1);
////                        recyclerView.scrollToPosition(msgList.size() - 1);
////
////                        receivedMp3.start();
////
////                    } catch (IOException e) {
////                        e.printStackTrace();
////                    } catch (DemoException e) {
////                        e.printStackTrace();
////                    }
////                }
//            }
//        });
//        inputText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//
//        send.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                String content = inputText.getText().toString();
//                if (!"".equals(content)) {
//                    Msg msg= new Msg(content, Msg.TYPE_SEND);;
////                    if (Send) {
////                        System.out.println("asdasd");
////                    } else {
////                        msg = new Msg(content, Msg.TYPE_RECEIVED);
////                    }
////                    Send = !Send;
////                    Msg msg2= new Msg(content, Msg.TYPE_RECEIVED);
////                    msgList.add(msg2);
//
//                    msgList.add(msg);
//                    adapter.notifyItemInserted(msgList.size() - 1);
//                    recyclerView.scrollToPosition(msgList.size() - 1);
//                    inputText.setText("");
//
//                    Handler mHandler = new Handler(){
//                        @Override
//                        public void handleMessage(Message msg) {
//                            switch (msg.what){
//                                case 1:
//
//                                String receivedStr = String.valueOf(msg.obj);
//                                JSONObject receivedJson= JSONObject.parseObject(receivedStr);
//
//                                Msg received = new Msg( receivedJson.getString("text"),Msg.TYPE_RECEIVED);
//                                msgList.add(received);
//                                adapter.notifyItemInserted(msgList.size() - 1);
//                                recyclerView.scrollToPosition(msgList.size() - 1);
//                                TtsMain ttsMain = new TtsMain();
//                                    try {
//                                        ttsMain.run(received.getContent());
//                                        initReceivedMp3();
//                                        receivedMp3.start();
//                                    } catch (IOException e) {
//                                        e.printStackTrace();
//                                    } catch (DemoException e) {
//                                        e.printStackTrace();
//                                    }
//
//                                    break;
//                                default:
//                                    break;
//                            }
//                        }
//                    };
//
//                    new Thread(new Runnable() {
//                        public void run() {
//                        XiaoIce xiaoIce = new XiaoIce();
//                        if(inputText.getText().toString().length()==0&&msgList.size()!=0&&msgList.get(msgList.size()-1).getType()==Msg.TYPE_SEND){
//                            try {
//                                String receivedStr = xiaoIce.run(msgList.get(msgList.size()-1).getContent());
//                                Message message = mHandler.obtainMessage();
//                                message.what = 1;
//                                //传递对象
//                                message.obj = receivedStr;
//                                mHandler.sendMessage(message);
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            } catch (DemoException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }}).start();
//
//
//
//
//                }
//            }
//        });
//    }
//
//    private void initReceivedMp3() throws IOException {
//        receivedMp3 = new MediaPlayer();
////        System.out.println("11111");
////        if (receivedMp3 != null) {
////            receivedMp3.stop();
////            System.out.println("1111221");
////        }
//        AssetManager assets = getAssets();
//        AssetFileDescriptor fd = assets.openFd("result.mp3");
//        File file = new File(Environment.getExternalStorageDirectory(),"result.mp3");
//
//        receivedMp3.setDataSource(file.getPath());System.out.println("1111221333");
////        receivedMp3.setDataSource(fd.getFileDescriptor(),fd.getStartOffset(),fd.getLength());
//        receivedMp3.prepare();
//
//    }
//    private void initMsgs() {
////        Msg msg1 = new Msg("hello guy", Msg.TYPE_RECEIVED);
////        msgList.add(msg1);
////        Msg msg2 = new Msg("hello guy2", Msg.TYPE_SEND);
////        msgList.add(msg2);
////        Msg msg3 = new Msg("hello guy3", Msg.TYPE_RECEIVED);
////        msgList.add(msg3);
//    }
//}