package com.example.mypra.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mypra.model.adRotationBean;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

public class ImageAdapter extends BannerAdapter<adRotationBean, ImageAdapter.BannerViewHolder> {
    Context mContext;
    public ImageAdapter(List<adRotationBean> datas) {
        super(datas);
    }

    @Override
    public BannerViewHolder onCreateHolder(ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        ImageView imageView = new ImageView(mContext);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return new BannerViewHolder(imageView);
    }

    @Override
    public void onBindView(BannerViewHolder bannerViewHolder, adRotationBean adRotationBean, int i, int i1) {
        Glide.with(bannerViewHolder.imageView.getContext()).load("http://10.0.2.2:10001"+ adRotationBean.getAdvImg()).into(bannerViewHolder.imageView);
    }

    public class BannerViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        public BannerViewHolder(@NonNull ImageView itemView) {
            super(itemView);
            this.imageView = itemView;
        }
    }
}
