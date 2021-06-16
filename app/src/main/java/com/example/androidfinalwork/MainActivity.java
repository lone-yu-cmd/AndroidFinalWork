package com.example.androidfinalwork;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Msg> msgList = new ArrayList<>();
    private EditText inputText;
    private Button send,voice;
    private RecyclerView recyclerView;
    private MsgAdapter adapter;
    private boolean Send = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initMsgs();
        voice = (Button) findViewById(R.id.voice_button);
        inputText = (EditText) findViewById(R.id.input_text);
        send = (Button) findViewById(R.id.send_button);
        recyclerView = (RecyclerView) findViewById(R.id.msg_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        //------链表中都是Msg类（是每个重复的模块）
        //------将链表装入Adapter中（Adapter是一个模块中的具体内容）
        adapter = new MsgAdapter(msgList);
        recyclerView.setAdapter(adapter);

        voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 加入录音功能
                final RecordAudioDialogFragment fragment = RecordAudioDialogFragment.newInstance();
                fragment.show(getSupportFragmentManager(), RecordAudioDialogFragment.class.getSimpleName());
                fragment.setOnCancelListener(new RecordAudioDialogFragment.OnAudioCancelListener() {
                    @Override
                    public void onCancel() {
                        fragment.dismiss();
                    }
                });

            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = inputText.getText().toString();
                if (!"".equals(content)) {
                    Msg msg= new Msg(content, Msg.TYPE_SEND);;
//                    if (Send) {
//                        System.out.println("asdasd");
//                    } else {
//                        msg = new Msg(content, Msg.TYPE_RECEIVED);
//                    }
//                    Send = !Send;

                    msgList.add(msg);
                    adapter.notifyItemInserted(msgList.size() - 1);
                    recyclerView.scrollToPosition(msgList.size() - 1);
                    inputText.setText("");
                }
            }
        });
    }

    private void initMsgs() {
        Msg msg1 = new Msg("hello guy", Msg.TYPE_RECEIVED);
        msgList.add(msg1);
        Msg msg2 = new Msg("hello guy2", Msg.TYPE_SEND);
        msgList.add(msg2);
        Msg msg3 = new Msg("hello guy3", Msg.TYPE_RECEIVED);
        msgList.add(msg3);
    }
}