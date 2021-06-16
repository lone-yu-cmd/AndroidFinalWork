package com.example.androidfinalwork;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//适配器是一个模块的具体内容 msg的类型就只有内容content和类型type  type是接收类型还是发送类型
//主要有几个功能
//-------------1.定义ViewHolder 将在一个模块里的组件位置全部获取到，但是此时不知道具体是在哪个视图(view)
//-------------2.找到item的view，将view绑定到ViewHolder上，就算正式成功了做好一个模块了
//-------------3.这个构造函数就是放入msglist，会在onBindViewHolder拿到msglist中相应位置的msg的信息，进行操作
public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder> {
    private List<Msg> mMsgList;

    //把一个模块的所有内容都放到ViewHolder类中 但是此时只是对类的定义，没有使用类 已经找到所有组件的位置，但是不知道是用那个视图
    // 因为是静态的 所以可以加快载入速度
    static class ViewHolder extends RecyclerView.ViewHolder
    {
        LinearLayout leftLayout;
        LinearLayout rightLayout;
        TextView leftMsg;
        TextView rightMsg;
        //这个view当绑定msg_item的时候 就知道是再msg_item去寻找left_layout等了
        public ViewHolder(View view)
        {
            super(view);
            leftLayout=(LinearLayout) view.findViewById(R.id.left_layout);//因为layout是在文件中，所以是R.id
            rightLayout=(LinearLayout) view.findViewById(R.id.right_layout);
            leftMsg=(TextView) view.findViewById(R.id.left_msg);
            rightMsg=(TextView) view.findViewById(R.id.right_msg);
        }
    }
    //先把适配器里面的所有类放进来
    public MsgAdapter(List<Msg> mMsgList){
        this.mMsgList=mMsgList;
    }

    //通过此方法 就讲单个试图给绑定和创造出来了
    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int i) {
//这里的i是viewtype，在这个项目中没有用到。主要解决一个RecyclerView加载不同子项item问题。
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_item,parent,false);
        return new ViewHolder(view);
    }

    //获取到list中的msg的
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Msg msg=mMsgList.get(position);
        if(msg.getType()== Msg.TYPE_RECEIVED)
        {
            holder.leftLayout.setVisibility(View.VISIBLE);
            holder.rightLayout.setVisibility(View.GONE);
            holder.leftMsg.setText(msg.getContent());
        }
        else if (msg.getType()==Msg.TYPE_SEND)
        {
            holder.rightLayout.setVisibility(View.VISIBLE);
            holder.leftLayout.setVisibility(View.GONE);
            holder.rightMsg.setText(msg.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return mMsgList.size();
    }
}

