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

public class ServiceListAdapter extends RecyclerView.Adapter<ServiceListAdapter.MyViewHolder2> {
    Context mContent;
    List<allServiceBean> beanList = new ArrayList<allServiceBean>();

    public ServiceListAdapter(Context mContent, List<allServiceBean> beanList) {
        this.mContent = mContent;
        this.beanList = beanList;

    }

    @NonNull
    @Override
    public MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContent).inflate(R.layout.all_service_item_layout, parent, false);
        return new MyViewHolder2(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder2 holder, int position) {
        Glide.with(mContent).load("http://10.0.2.2:10001" + beanList.get(position).getImgUrl()).into(holder.imageView);
        holder.textView.setText(beanList.get(position).getServiceName());
    }

    @Override
    public int getItemCount() {
        return beanList.size();
    }

    public class MyViewHolder2 extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public MyViewHolder2(@NonNull View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.iv_show_service);
            this.textView = itemView.findViewById(R.id.tv_show_service);
        }
    }
}
