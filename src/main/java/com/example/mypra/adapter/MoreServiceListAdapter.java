package com.example.mypra.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mypra.R;
import com.example.mypra.model.allServiceBean;

import java.util.ArrayList;
import java.util.List;

public class MoreServiceListAdapter extends RecyclerView.Adapter<MoreServiceListAdapter.MyViewHolder> {
    Context mContext;
    List<allServiceBean> beanList = new ArrayList<allServiceBean>();

    public MoreServiceListAdapter(Context mContext, List<allServiceBean> beanList) {
        this.mContext = mContext;
        this.beanList = beanList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.service_item_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        if(position <10){
//            if(position == 9){
//                holder.imageView.setImageResource(R.drawable.more);
//                holder.textView.setText("更多服务");
//            }else{
//                Glide.with(mContext).load("http://10.0.2.2:10001"+beanList.get(position).getImgUrl()).into(holder.imageView);
//                holder.textView.setText(beanList.get(position).getServiceName());
//            }
//        }
        if (position < 9) {
            Glide.with(mContext).load("http://10.0.2.2:10001" + beanList.get(position).getImgUrl()).into(holder.imageView);
            holder.textView.setText(beanList.get(position).getServiceName());
        }
        if (position == 9) {
            holder.imageView.setImageResource(R.drawable.more);
            holder.textView.setText("更多服务");
        }
    }

    @Override
    public int getItemCount() {
        return beanList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_show);
            textView = (TextView) itemView.findViewById(R.id.tv_showName);
        }
    }
}
