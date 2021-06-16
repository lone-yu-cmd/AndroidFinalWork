package com.example.androidfinalwork;

import android.Manifest;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.Service;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
//import android.os.SystemClock;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v4.app.DialogFragment;
//import android.support.v7.app.AlertDialog;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

//import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.List;


public class RecordAudioDialogFragment extends DialogFragment {

    private static final String TAG = "RecordAudioDialogFragment";

    private int mRecordPromptCount = 0;
    boolean isConnected = false;
    RecordingService.LocalBinder localBinder;
    private boolean mStartRecording = true;
    private boolean mPauseRecording = true;

    long timeWhenPaused = 0;

    private FloatingActionButton mFabRecord;
    private Chronometer mChronometerTime;
    private ImageView mIvClose;

    private OnAudioCancelListener mListener;

    public static RecordAudioDialogFragment newInstance() {
        RecordAudioDialogFragment dialogFragment = new RecordAudioDialogFragment();
        Bundle bundle = new Bundle();
        dialogFragment.setArguments(bundle);
        return dialogFragment;
    }

    //创造服务链接器 便于使用服务内部的方法
    private ServiceConnection serviceConnection = new ServiceConnection() {
        //        连接时候启动的方法
        @Override
        //这里返回的是 Service中的onBind方法
        public void onServiceConnected(ComponentName name, IBinder service) {
        //        连接服务时候就会生命localBinder
            localBinder = (RecordingService.LocalBinder) service;
            System.out.println("declare service-------------------------------------------------");
            isConnected = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

            System.out.println("declare service defalut-------------------------------------------------");
            isConnected = false;
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_record_audio, null);
        initView(view);

        mFabRecord.setColorNormal(getResources().getColor(R.color.colorPrimary));
        mFabRecord.setColorPressed(getResources().getColor(R.color.colorPrimaryDark));

        mFabRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //没有权限申请权限
                    System.out.println("没有权限申请权限");
                    ActivityCompat.requestPermissions(getActivity()
                            , new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 1);
                } else {
                    //有权限执行录音方法
                    System.out.println("有权限执行录音方法");
                    onRecord(mStartRecording);
                    mStartRecording = !mStartRecording;
                }

            }
        });

        mIvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onCancel();
            }
        });

        builder.setCancelable(false);
        builder.setView(view);
        return builder.create();
    }

    private void initView(View view) {
        mChronometerTime = (Chronometer) view.findViewById(R.id.record_audio_chronometer_time);
        mFabRecord = (FloatingActionButton) view.findViewById(R.id.record_audio_fab_record);
        mIvClose = (ImageView) view.findViewById(R.id.record_audio_iv_close);
    }

    //开始录音
    private void onRecord(boolean start) {
        Intent intent = new Intent(this.getActivity(), RecordingService.class);
        if (start) {
            // start recording
            mFabRecord.setImageResource(R.drawable.ic_media_stop);
            //mPauseButton.setVisibility(View.VISIBLE);
            Toast.makeText(getActivity(), "开始录音...", Toast.LENGTH_SHORT).show();
            File folder = new File(Environment.getExternalStorageDirectory() + "/SoundRecorder");
            if (!folder.exists()) {
                //folder /SoundRecorder doesn't exist, create the folder
                folder.mkdir();
            }

            //start Chronometer
            mChronometerTime.setBase(SystemClock.elapsedRealtime());
            mChronometerTime.start();
            //start RecordingService 启动服务
            getActivity().startService(intent);
            //绑定该类和服务之间的关系
            //TODO 不走这一步！
            getActivity().getApplicationContext().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
            //keep screen on while recording
            Log.d("mainactivity》》","启动服务"+getActivity().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE));
            //启动服务
            localBinder.startRecording();
//            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        } else {
            //stop recording
            mFabRecord.setImageResource(R.drawable.ic_mic_white_36dp);
            //mPauseButton.setVisibility(View.GONE);
            mChronometerTime.stop();
            timeWhenPaused = 0;
            Toast.makeText(getActivity(), "录音结束...", Toast.LENGTH_SHORT).show();

            getActivity().stopService(intent);
            //allow the screen to turn off again once recording is finished
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
        System.out.println("intent start4");

    }


    public void setOnCancelListener(OnAudioCancelListener listener) {
        this.mListener = listener;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    onRecord(mStartRecording);
                    System.out.println("intent start5");
                }
                break;
        }
    }

    public interface OnAudioCancelListener {
        void onCancel();
    }

    //    检查服务是否启动
//    public static boolean isServiceRunning(Context mContext, String className) {
//
//        boolean isRunning = false;
//        ActivityManager activityManager = (ActivityManager) mContext
//                .getSystemService(Context.ACTIVITY_SERVICE);
//        List<ActivityManager.RunningServiceInfo> serviceList = activityManager
//                .getRunningServices(30);
//
//        if (!(serviceList.size() > 0)) {
//            return false;
//        }
//        Log.e("OnlineService：", className);
//        for (int i = 0; i < serviceList.size(); i++) {
//            Log.e("serviceName：", serviceList.get(i).service.getClassName());
//            if (serviceList.get(i).service.getClassName().contains(className) == true) {
//                isRunning = true;
//                break;
//            }
//        }
//        return isRunning;
//    }\
}