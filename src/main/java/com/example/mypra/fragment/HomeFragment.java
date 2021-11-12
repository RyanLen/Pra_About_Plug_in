package com.example.mypra.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ListView;
import android.widget.ScrollView;

import com.example.mypra.R;
import com.example.mypra.adapter.ImageAdapter;
import com.example.mypra.adapter.MyAdapter;
import com.example.mypra.adapter.MoreServiceListAdapter;
import com.example.mypra.adapter.MyNewListsAdapter;
import com.example.mypra.adapter.MyNewTitleAdapter;
import com.example.mypra.model.aboutNewsBean;
import com.example.mypra.model.adRotationBean;
import com.example.mypra.model.allServiceBean;
import com.example.mypra.model.newsListBean;
import com.example.mypra.model.searchNewsBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class HomeFragment extends Fragment {
    ListView listView,listView2;
    Banner banner;
    RecyclerView recyclerView,recyclerView2;
    ScrollView scrollView;
    MyAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        listView = view.findViewById(R.id.list_view);
        banner = view.findViewById(R.id.banner);
        recyclerView = view.findViewById(R.id.recycle_view);
        recyclerView2 = view.findViewById(R.id.news_recycle_view2);
        listView2 = view.findViewById(R.id.news_list_view2);
        scrollView = view.findViewById(R.id.scroll_view);
        listView2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                scrollView.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        setHasOptionsMenu(true);
        SearchNews();
        Rotation();
        Services();
        getTitle();
        getAboutNews(9);
        return view;
    }

    private void SearchNews(){
        new Thread(){
            @Override
            public void run() {
                try {
                    OkHttpClient okHttpClient = new OkHttpClient();
                    Request request = new Request.Builder().url("http://10.0.2.2:10001/prod-api/press/press/list").build();
                    Response response = okHttpClient.newCall(request).execute();
                    String res = response.body().string();
                    JSONObject jsonObject = new JSONObject(res);
                    String strData = jsonObject.getString("rows");
                    Gson gson = new Gson();
                    List<searchNewsBean> serNewsBeans = gson.fromJson(strData, new TypeToken<ArrayList<searchNewsBean>>(){}.getType());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter = new MyAdapter(getActivity(), serNewsBeans);
                            listView.setAdapter(adapter);
                            listView.setTextFilterEnabled(true);
                        }
                    });
                }catch (IOException | JSONException e){
                    e.printStackTrace();
                }
            }
        }.start();
    }
    private void Rotation(){
        new Thread(){
            @Override
            public void run() {
                try{
                    OkHttpClient okHttpClient = new OkHttpClient();
                    Request request = new Request.Builder().url("http://10.0.2.2:10001/prod-api/api/rotation/list?type=2").build();
                    Response response = okHttpClient.newCall(request).execute();
                    String res = response.body().string();
                    JSONObject jsonObject = new JSONObject(res);
                    String jsonData = jsonObject.getString("rows");
                    Gson gson = new Gson();
                    List<adRotationBean> adRotationBeans = gson.fromJson(jsonData, new TypeToken<ArrayList<adRotationBean>>(){}.getType());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ImageAdapter imageAdapter = new ImageAdapter(adRotationBeans);
                            banner.addBannerLifecycleObserver(getActivity())
                                    .setAdapter(imageAdapter)
                                    .setIndicator(new CircleIndicator(getActivity()));
                        }
                    });
                }catch (IOException | JSONException e){
                    e.printStackTrace();
                }
            }
        }.start();
    }
    private void Services(){
        new Thread(){
            @Override
            public void run() {
                try {
                    OkHttpClient okHttpClient = new OkHttpClient();
                    Request request = new Request.Builder().url("http://10.0.2.2:10001/prod-api/api/service/list").build();
                    Response response = okHttpClient.newCall(request).execute();
                    String res = response.body().string();
                    JSONObject jsonObject = new JSONObject(res);
                    String jsonData = jsonObject.getString("rows");
                    Gson gson = new Gson();
                    List<allServiceBean> servicesBeanList = gson.fromJson(jsonData, new TypeToken<ArrayList<allServiceBean>>(){}.getType());
                    servicesBeanList = servicesBeanList.subList(0, 10);
                    MoreServiceListAdapter moreServiceListAdapter = new MoreServiceListAdapter(getActivity(), servicesBeanList);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 5);
                            recyclerView.setLayoutManager(gridLayoutManager);
                            recyclerView.setAdapter(moreServiceListAdapter);
                        }
                    });
                }catch (IOException | JSONException e){
                    e.printStackTrace();
                }
            }
        }.start();
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
                            recyclerView2.setLayoutManager(linearLayoutManager);
                            recyclerView2.setAdapter(adapter);
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
                            listView2.setAdapter(adapter);
                        }
                    });
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.action_search_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setIconifiedByDefault(true);
        searchView.setSubmitButtonEnabled(true);
        searchView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        searchView.setIconified(false);
        searchView.setFocusable(true);
        searchView.setFocusableInTouchMode(false);
        searchView.requestFocusFromTouch();
        searchView.clearFocus();
        searchView.setQueryHint("请输入新闻相关信息");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (TextUtils.isEmpty(query)) {
                    listView.clearTextFilter();
                    adapter.getFilter().filter("");
                } else {
                    adapter.getFilter().filter(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    banner.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.GONE);
                } else {
                    banner.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, getActivity().getMenuInflater());
    }
}