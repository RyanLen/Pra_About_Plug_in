package com.example.mypra.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mypra.R;
import com.example.mypra.model.aboutNewsBean;

import java.util.ArrayList;
import java.util.List;

public class MyNewListsAdapter extends BaseAdapter {
    Context mContext;
    List<aboutNewsBean> newsBeanList = new ArrayList<aboutNewsBean>();

    public void MyNewTitleAdapter() {

    }

    public MyNewListsAdapter(Context mContext, List<aboutNewsBean> newsBeanList) {
        this.mContext = mContext;
        this.newsBeanList = newsBeanList;
    }

    @Override
    public int getCount() {
        return newsBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_view_about_news_layout, null);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.news_about_img);
            holder.title = (TextView) convertView.findViewById(R.id.tv_show_title);
            holder.time = (TextView) convertView.findViewById(R.id.tv_show_time);
            holder.content = (TextView) convertView.findViewById(R.id.tv_show_content);
            holder.commentNum = (TextView) convertView.findViewById(R.id.tv_show_commentNum);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Glide.with(mContext).load("http://10.0.2.2:10001" + newsBeanList.get(position).getCover()).into(holder.imageView);
        holder.title.setText(newsBeanList.get(position).getTitle());
        holder.content.setText(newsBeanList.get(position).getContent().replaceAll("(<).*?(>)",""));
        holder.time.setText("发布日期:"+newsBeanList.get(position).getCreateTime());
        holder.commentNum.setText("评论:"+newsBeanList.get(position).getCommentNum());
        return convertView;
    }

    public class ViewHolder {
        ImageView imageView;
        TextView title, content, time, commentNum;
    }
}
