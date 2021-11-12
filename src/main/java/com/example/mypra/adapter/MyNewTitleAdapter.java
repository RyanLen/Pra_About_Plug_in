package com.example.mypra.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mypra.R;
import com.example.mypra.fragment.NewsFragment;
import com.example.mypra.model.newsListBean;

import java.util.ArrayList;
import java.util.List;

public class MyNewTitleAdapter extends RecyclerView.Adapter<MyNewTitleAdapter.MyViewHolder3> {
    Context mContext;
    List<newsListBean> newsFragments = new ArrayList<newsListBean>();
    OnItemClickListener onItemClickListener;
    public MyNewTitleAdapter(Context mContext, List<newsListBean> newsFragments) {
        this.mContext = mContext;
        this.newsFragments = newsFragments;
    }

    @NonNull
    @Override
    public MyViewHolder3 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.new_title_item_layout, parent, false);
        return new MyViewHolder3(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder3 holder, int position) {
        Log.d("TAG", "onBindViewHolder: "+newsFragments.get(position).getName());
        holder.textView.setText(newsFragments.get(position).getName());
        if(onItemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.OnItemClickListener(holder.itemView, holder.getLayoutPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return newsFragments.size();
    }

    public class MyViewHolder3 extends RecyclerView.ViewHolder{
        TextView textView;
        public MyViewHolder3(@NonNull View itemView) {
            super(itemView);
            this.textView = (TextView) itemView.findViewById(R.id.tv_showTitle);
        }
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public interface OnItemClickListener {
        void OnItemClickListener(View view, int position);
    }
}
