package com.example.mypra.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.mypra.R;
import com.example.mypra.model.searchNewsBean;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends BaseAdapter implements Filterable {
    Context mContent;
    private List<searchNewsBean> beans;
    private List<searchNewsBean> beanList;
    MyFilter filter;
    public MyAdapter() {
    }

    public MyAdapter(Context mContent, List<searchNewsBean> beans) {
        this.mContent = mContent;
        this.beans = beans;
        this.beanList = beans;
    }

    @Override
    public int getCount() {
        return beans.size();
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
            convertView = LayoutInflater.from(mContent).inflate(R.layout.layout_item, null);
            holder = new ViewHolder();
            holder.total = (TextView) convertView.findViewById(R.id.tv_total);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.total.setText(beans.get(position).getTitle());
        return convertView;
    }

    @Override
    public Filter getFilter() {
        if(filter == null){
            filter = new MyFilter();
        }
        return filter;
    }
    class MyFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            List<searchNewsBean> list;
            if(TextUtils.isEmpty(constraint)){
                list = beanList;
            }else{
                list = new ArrayList<>();
                for(searchNewsBean serNewsBean : beanList){
                    if(serNewsBean.getTitle().contains(constraint)){
                        list.add(serNewsBean);
                    }
                }
            }
            filterResults.count = list.size();
            filterResults.values = list;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            beans = (List<searchNewsBean>) results.values;
            if(results.count > 0){
                notifyDataSetChanged();
            }else{
                notifyDataSetInvalidated();
            }
        }
    }
    class ViewHolder {
        TextView total;
    }
}
