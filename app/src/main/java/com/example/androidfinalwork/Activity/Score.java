package com.example.androidfinalwork.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.example.androidfinalwork.Msg;
import com.example.androidfinalwork.R;
import com.example.androidfinalwork.asr.com.baidu.speech.restapi.asrdemo.AsrMain;
import com.example.androidfinalwork.asr.com.baidu.speech.restapi.common.ConnUtil;
import com.example.androidfinalwork.asr.com.baidu.speech.restapi.common.DemoException;
import com.example.androidfinalwork.xiaoice.BeautyScore;
import com.example.androidfinalwork.xiaoice.XiaoIce;
import com.xuexiang.xui.XUI;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.dialog.LoadingDialog;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class Score extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    int takePhoto = 1;
    int fromAlbum = 2;
    DrawerLayout drawerLayout;
    Uri imageUri;
    File outputImage;
//    LoadingDialog mLoadingDialog ;
    String remark = "";
    String grade = "";
    ImageView imageViewOnCreate;
    TextView gradeTextView ;
    TextView remarkTextView ;
    Bitmap bitmap;//图片
    Uri  getImageUri;//图片

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }else {
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
//        XUI.initTheme(this);
        Button takePhotoBtn = findViewById(R.id.takePhoto);
//        takePhotoBtn.setVisibility(View.GONE);
        Button fromAlbumBtn = findViewById(R.id.fromAlbum);
        gradeTextView = findViewById(R.id.score);
        remarkTextView = findViewById(R.id.remark);
//        mLoadingDialog= WidgetUtils.getLoadingDialog(this)
//                .setIconScale(0.4F)
//                .setLoadingSpeed(8);
        imageViewOnCreate = findViewById(R.id.imageView2);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        };


        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            //如果应用之前请求过此权限但用户拒绝了请求，此方法将返回 true。
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {//这里可以写个对话框之类的项向用户解释为什么要申请权限，并在对话框的确认键后续再次申请权限.它在用户选择"不再询问"的情况下返回false
            } else {
                //申请权限，字符串数组内是一个或多个要申请的权限，1是申请权限结果的返回参数，在onRequestPermissionsResult可以得知申请结果
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA,}, 1);
            }
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //如果应用之前请求过此权限但用户拒绝了请求，此方法将返回 true。
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {//这里可以写个对话框之类的项向用户解释为什么要申请权限，并在对话框的确认键后续再次申请权限.它在用户选择"不再询问"的情况下返回false
            } else {
                //申请权限，字符串数组内是一个或多个要申请的权限，1是申请权限结果的返回参数，在onRequestPermissionsResult可以得知申请结果
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,}, 1);
            }
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
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


        takePhotoBtn.setOnClickListener( v-> {
            outputImage = new File (getExternalCacheDir(),"image.jpg");
            if(outputImage.exists()){
                outputImage.delete();
            }
            try {
                outputImage.createNewFile();
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                    imageUri=FileProvider.getUriForFile(this,"com.example.androidfinalwork.Activity",outputImage);
                }else {
                    imageUri= Uri.fromFile(outputImage);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
//            启动相机
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            System.out.println(imageUri.toString());
            System.out.println(imageUri.getPath());
            intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
            startActivityForResult(intent,takePhoto);

//            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//            }
        });
        fromAlbumBtn.setOnClickListener( v ->{
            Intent pickIntent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
            startActivityForResult(pickIntent,fromAlbum);
//            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//            intent.setType("image/*");
//            intent.addCategory(Intent.CATEGORY_OPENABLE);
//
//            startActivityForResult(intent,fromAlbum);

        });
        boolean hasFocusFlag=false;


        gradeTextView.setVisibility(View.INVISIBLE);
        remarkTextView.setVisibility(View.INVISIBLE);
        gradeTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                gradeTextView.setVisibility(View.INVISIBLE);
                remarkTextView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {
//                    if(!mLoadingDialog.isShowing()){
//                        gradeTextView.setVisibility(View.VISIBLE);
//                        remarkTextView.setVisibility(View.VISIBLE);
//                        }

            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        ImageView imageView = findViewById(R.id.imageView2);
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_IMAGE_CAPTURE){
            if(resultCode == Activity.RESULT_OK){
                    try {
//                        mLoadingDialog.updateMessage("颜值评分中");
//                        mLoadingDialog.setLoadingIcon(R.drawable.ic_baseline_person_pin_circle_24);
//                        mLoadingDialog.show();
                        Toast.makeText(this,"Please wait a few seconds....",Toast.LENGTH_LONG);
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        imageView = findViewById(R.id.imageView2);
                        ConnUtil util = new ConnUtil(); //转码
                        BeautyScore beautyScore = new BeautyScore();
                        JSONObject result = beautyScore.run(util.bitmapToBase64(bitmap));
                        grade = result.getString("score");
                        remark = result.getString("text");
                        System.out.println(grade);
                        System.out.println(remark);
                        gradeTextView.setText(grade);
                        remarkTextView.setText(remark);
//                        System.out.println(util.bitmapToBase64(bitmap));
                        imageView.setImageBitmap(rotateIfRequired(bitmap));
//                        final Timer t = new Timer();
//                        t.schedule(new TimerTask() {
//                            public void run() {
//                                mLoadingDialog.dismiss();
//                                //结束本界面并跳转到收派员列表的界面
////                            finish();
//                                t.cancel();
//                            }
//                        }, 3000);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (DemoException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }

//                catch (DemoException e) {
//                    e.printStackTrace();
//                }
//                catch (IOException e) {
//                    e.printStackTrace();
//                }
            ;
        }else if(requestCode == fromAlbum){
//            System.out.println("here");
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            imageView.setImageBitmap(imageBitmap);
            if(data != null){
                try {
                    Handler mHandler = new Handler(){
                        @Override
                        public void handleMessage(Message msg) {
                            switch (msg.what){
                                case 1:
//                                    mLoadingDialog.dismiss();
                                    grade = ((JSONObject)msg.obj).getString("score");
                                    remark = ((JSONObject)msg.obj).getString("text");
                                    System.out.println(grade);
                                    System.out.println(remark);
                                    gradeTextView.setText(grade);
                                    remarkTextView.setText(remark);
                                    gradeTextView.setVisibility(View.VISIBLE);
                                    remarkTextView.setVisibility(View.VISIBLE);
                                    break;
                                default:
                                    break;
                            }
                        }
                    };

//                    mLoadingDialog.updateMessage("颜值评分中");
//                    mLoadingDialog.setLoadingIcon(R.drawable.ic_baseline_person_pin_circle_24);
                    Toast.makeText(this,"Please wait a few seconds....",Toast.LENGTH_LONG);
//                    mLoadingDialog.show();

                    getImageUri = data.getData();
                    System.out.println(getImageUri.getPath());
                     bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(data.getData()));
                    imageView.setImageBitmap(bitmap);
//                    mLoadingDialog.show();

                    BeautyScore beautyScore = new BeautyScore();
                    try {
                        String base = beautyScore.base64Encode(ConnUtil.getInputStreamContent(getContentResolver().openInputStream(getImageUri)));
                        new Thread(new Runnable() {
                            public void run() {

                                try {
                                    JSONObject result = beautyScore.run(base);
                                    Message message = mHandler.obtainMessage();
                                    message.what = 1;
                                    //传递对象
                                    message.obj = result;
                                    mHandler.sendMessage(message);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (DemoException e) {
                                    e.printStackTrace();
                                }


                            }}).start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }








//                    ConnUtil util = new ConnUtil(); //转码
//                    BeautyScore beautyScore = new BeautyScore();
//                    String base = beautyScore.base64Encode(ConnUtil.getInputStreamContent(getContentResolver().openInputStream(data.getData())));
//                    System.out.println(base);
////                    System.out.println(base);
//                    System.out.println("here");
////                    base = "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";
////                    JSONObject result = beautyScore.run(util.bitmapToBase64(bitmap));
//                    JSONObject result = beautyScore.run(base);
//                    grade = result.getString("score");
//                    remark = result.getString("text");
//                    System.out.println(grade);
//                    System.out.println(remark);
//                    gradeTextView.setText(grade);
//                    remarkTextView.setText(remark);


                } catch (FileNotFoundException  e) {
                    e.printStackTrace();
                }
//                catch (DemoException e) {
//                    e.printStackTrace();
//                }

            }
        }

//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            imageView.setImageBitmap(imageBitmap);
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        final Timer t = new Timer();
//        t.schedule(new TimerTask() {
//            public void run() {
//                mLoadingDialog.dismiss();
//                //结束本界面并跳转到收派员列表的界面
////                            finish();
//                t.cancel();
//            }
//        }, 3000);
//

    }

    private Bitmap  rotateIfRequired(Bitmap bitmap){
        try {
            ExifInterface exifInterface = new ExifInterface(outputImage.getAbsolutePath());
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            if(orientation == ExifInterface.ORIENTATION_ROTATE_90){
                return rotateBitmap(bitmap,90);
            }else if(orientation == ExifInterface.ORIENTATION_ROTATE_180){
                return rotateBitmap(bitmap,180);
            }else if(orientation == ExifInterface.ORIENTATION_ROTATE_270){
                return rotateBitmap(bitmap,270);
            }else {
                return bitmap;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private Bitmap rotateBitmap(Bitmap bitmap,int degree){
        Matrix matrix = new Matrix();
        matrix.postRotate((float)degree);
        Bitmap rotateBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        bitmap.recycle();
        return rotateBitmap;
    }
}