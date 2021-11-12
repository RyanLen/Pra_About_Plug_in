package com.example.mypra.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mypra.R;
import com.example.mypra.adapter.MyNewListsAdapter;
import com.example.mypra.adapter.MyNewTitleAdapter;
import com.example.mypra.model.aboutNewsBean;
import com.example.mypra.model.newsListBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NewsFragment extends Fragment {
    RecyclerView recyclerView;
    ListView listView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        recyclerView = view.findViewById(R.id.news_recycle_view);
        listView = view.findViewById(R.id.news_list_view);
        getTitle();
        getAboutNews(9);
        return view;
    }

    private void getTitle() {
        new Thread() {
            @Override
            public void run() {
                try {
                    OkHttpClient okHttpClient = new OkHttpClient();
                    Request request = new Request.Builder().url("http://10.0.2.2:10001/prod-api/press/category/list").build();
                    Response response = okHttpClient.newCall(request).execute();
                    String res = response.body().string();
                    JSONObject jsonObject = new JSONObject(res);
                    String jsonData = jsonObject.getString("data");
                    Gson gson = new Gson();
                    List<newsListBean> listBeans = gson.fromJson(jsonData, new TypeToken<ArrayList<newsListBean>>() {
                    }.getType());
                    MyNewTitleAdapter adapter = new MyNewTitleAdapter(getActivity(), listBeans);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                            linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
                            recyclerView.setLayoutManager(linearLayoutManager);
                            recyclerView.setAdapter(adapter);
                            adapter.setOnItemClickListener(new MyNewTitleAdapter.OnItemClickListener() {
                                @Override
                                public void OnItemClickListener(View view, int position) {
                                    getAboutNews(listBeans.get(position).getId());
                                }
                            });
                        }
                    });
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void getAboutNews(int id) {
        new Thread() {
            @Override
            public void run() {
                try {
                    OkHttpClient okHttpClient = new OkHttpClient();
                    Request request = new Request.Builder().url("http://10.0.2.2:10001/prod-api/press/press/list?type=" + id).build();
                    Response response = okHttpClient.newCall(request).execute();
                    String res = response.body().string();
                    JSONObject jsonObject = new JSONObject(res);
                    String jsonData = jsonObject.getString("rows");
                    Gson gson = new Gson();
                    List<aboutNewsBean> newsBeanList = gson.fromJson(jsonData, new TypeToken<ArrayList<aboutNewsBean>>() {
                    }.getType());
                    MyNewListsAdapter adapter = new MyNewListsAdapter(getActivity(), newsBeanList);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            listView.setAdapter(adapter);
                        }
                    });
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
