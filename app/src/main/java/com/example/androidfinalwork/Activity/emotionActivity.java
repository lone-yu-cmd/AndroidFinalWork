package com.example.androidfinalwork.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.androidfinalwork.R;
import com.example.androidfinalwork.XUIApplication;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class emotionActivity extends AppCompatActivity {

    private HorizontalBarChart barHor;
    private HorizontalBarChart mChart;
    private HorizontalBarChart xiaoIcebarHor;

    DrawerLayout drawerLayout;
    List<BarEntry> list = new ArrayList<>();

    ArrayList<String> xLabel = new ArrayList<>();
    private int sCount = 5;
    private ArrayList<String> data;  //保存X轴坐标数据

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
        setContentView(R.layout.activity_emotion);
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
//        List<String> user = ((XUIApplication) getApplication()).user;
//        System.out.println(user);
//        ((XUIApplication) getApplication()).user.remove(1);
//        System.out.println(((XUIApplication) getApplication()).user);

        if( ((XUIApplication)getApplication()).chatCount<10){
            Toast.makeText(this,"Please chat a few more words and check again!",Toast.LENGTH_LONG).show();
        }else{
            mChart = findViewById(R.id.bar_hor);
            xLabel.add("like");
            xLabel.add("happy");
            xLabel.add("angry");
            xLabel.add("sad");
            xLabel.add("fearful");

            mChart.setDrawBarShadow(false);
            mChart.setDrawValueAboveBar(true);
            mChart.setContentDescription("Your");
            mChart.getDescription().setEnabled(false);
//  mChart.setNoDataText(getString(R.string.s_no_data));
            mChart.setPinchZoom(false);   //scaling can now only be done on x- and y-axis separately
            mChart.setDrawGridBackground(false);
            mChart.getAxisRight().setEnabled(false);   //不绘制右侧轴线

            XAxis xl = mChart.getXAxis();
            xl.setTextSize(20);
            xl.setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    if(((int)value >=0 && (int)value < xLabel.size()))
                        return xLabel.get((int) value);
                    else
                        return "";
                }
            });
            xl.setPosition(XAxis.XAxisPosition.BOTTOM);
            //xl.setLabelRotationAngle(45);   //标签倾斜
            xl.setDrawAxisLine(true);
            xl.setDrawGridLines(false);
            xl.setCenterAxisLabels(false);   //可不加这句，默认为false
            xl.setGranularity(sCount);   //x轴坐标占的宽度
            xl.setGranularity(1f);  //x轴坐标占的宽度
            //xl.setCenterAxisLabels(true);
            xl.setAxisMinimum(-0.5f);   // 此轴显示的最小值
            //xl.setAxisMaximum(sCount*sCount);   // 此轴显示的最大值
            xl.setLabelCount(sCount);       //显示的坐标数量
            YAxis yl = mChart.getAxisLeft();
            yl.setDrawAxisLine(true);
            yl.setDrawGridLines(true);
            yl.setAxisMinimum(0f);   //this replaces setStartAtZero(true)
            float[] val = new float[5]; //数据集
            Map<String, Integer> user = ((XUIApplication) getApplication()).user;
            val[0]=user.get("like");
            val[1]=user.get("happy");
            val[2]=user.get("angry");
            val[3]=user.get("sad");
            val[4]=user.get("fearful");

            mChartsetData(sCount, val);
            mChart.setFitBars(true);

            mChart.animateXY(2000, 2000);
            Legend legend = mChart.getLegend();
            legend.setEnabled(false);   //不显示图例


            xiaoIcebarHor = (HorizontalBarChart) findViewById(R.id.xiaoIce_bar_hor);
            xiaoIcebarHor.setDrawBarShadow(false);
            xiaoIcebarHor.setDrawValueAboveBar(true);
            xiaoIcebarHor.setContentDescription("Your");
            xiaoIcebarHor.getDescription().setEnabled(false);
//  xiaoIcebarHor.setNoDataText(getString(R.string.s_no_data));
            xiaoIcebarHor.setPinchZoom(false);   //scaling can now only be done on x- and y-axis separately
            xiaoIcebarHor.setDrawGridBackground(false);
            xiaoIcebarHor.getAxisRight().setEnabled(false);   //不绘制右侧轴线

            xl = xiaoIcebarHor.getXAxis();
            xl.setTextSize(20);
            xl.setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    if(((int)value >=0 && (int)value < xLabel.size()))
                        return xLabel.get((int) value);
                    else
                        return "";
                }
            });
            xl.setPosition(XAxis.XAxisPosition.BOTTOM);
            //xl.setLabelRotationAngle(45);   //标签倾斜
            xl.setDrawAxisLine(true);
            xl.setDrawGridLines(false);
            xl.setCenterAxisLabels(false);   //可不加这句，默认为false
            xl.setGranularity(sCount);   //x轴坐标占的宽度
            xl.setGranularity(1f);  //x轴坐标占的宽度
            //xl.setCenterAxisLabels(true);
            xl.setAxisMinimum(-0.5f);   // 此轴显示的最小值
            //xl.setAxisMaximum(sCount*sCount);   // 此轴显示的最大值
            xl.setLabelCount(sCount);       //显示的坐标数量
            yl = xiaoIcebarHor.getAxisLeft();
            yl.setDrawAxisLine(true);
            yl.setDrawGridLines(true);
            yl.setAxisMinimum(0f);   //this replaces setStartAtZero(true)
            Map<String, Integer> xiaoIce = ((XUIApplication) getApplication()).xiaoIce;
            val = new float[5];
            val[0]=xiaoIce.get("like");
            val[1]=xiaoIce.get("happy");
            val[2]=xiaoIce.get("angry");
            val[3]=xiaoIce.get("sad");
            val[4]=xiaoIce.get("fearful");
            setData(sCount, val);
            xiaoIcebarHor.setFitBars(true);

            xiaoIcebarHor.animateXY(2000, 2000);
            legend = xiaoIcebarHor.getLegend();
            legend.setEnabled(false);   //不显示图例
        }

    }
    private void setData(int count, float[] val)
       {
               //float barWidth = count-1;       //每个彩色数据条的宽度
               //float spaceForBar = count;     //每个数据条实际占的宽度
               float barWidth = 0.8f;               //每个彩色数据条的宽度
               float spaceForBar = 1f;             //每个数据条实际占的宽度
               ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

               for (int i = 0; i < count; i++)
               {
                       //float val = (float) (Math.random() * range);
                       yVals1.add(new BarEntry(i * spaceForBar, val[i]));
               }

               BarDataSet set1;

               if (xiaoIcebarHor.getData() != null && xiaoIcebarHor.getData().getDataSetCount() > 0)
               {
                       set1 = (BarDataSet)xiaoIcebarHor.getData().getDataSetByIndex(0);
                       set1.setValues(yVals1);
                       xiaoIcebarHor.getData().notifyDataChanged();
                       xiaoIcebarHor.notifyDataSetChanged();
               }
               else
               {
                       set1 = new BarDataSet(yVals1, "XXXX");

                       set1.setDrawIcons(false);

                       ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
                       dataSets.add(set1);

                       BarData data = new BarData(dataSets);
                       data.setValueTextSize(20);
                       data.setBarWidth(barWidth);
                       xiaoIcebarHor.setData(data);
               }
       }
    private void mChartsetData(int count, float[] val)
    {
        //float barWidth = count-1;       //每个彩色数据条的宽度
        //float spaceForBar = count;     //每个数据条实际占的宽度
        float barWidth = 0.8f;               //每个彩色数据条的宽度
        float spaceForBar = 1f;             //每个数据条实际占的宽度
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        for (int i = 0; i < count; i++)
        {
            //float val = (float) (Math.random() * range);
            yVals1.add(new BarEntry(i * spaceForBar, val[i]));
        }

        BarDataSet set1;

        if (mChart.getData() != null && mChart.getData().getDataSetCount() > 0)
        {
            set1 = (BarDataSet)mChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        }
        else
        {
            set1 = new BarDataSet(yVals1, "XXXX");

            set1.setDrawIcons(false);

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(20);
            data.setBarWidth(barWidth);
            mChart.setData(data);
        }
    }
}