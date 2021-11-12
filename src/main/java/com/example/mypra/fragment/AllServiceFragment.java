package com.example.mypra.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mypra.R;
import com.example.mypra.adapter.ServiceListAdapter;
import com.example.mypra.model.allServiceBean;
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

public class AllServiceFragment extends Fragment implements View.OnClickListener {
    Button live, convenience, carer;
    RecyclerView recyclerView;
    ServiceListAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_service, container, false);
        live = view.findViewById(R.id.live_service);
        convenience = view.findViewById(R.id.convenience_service);
        carer = view.findViewById(R.id.carer_service);
        recyclerView = view.findViewById(R.id.service_recycler_view);
        live.setOnClickListener(this);
        convenience.setOnClickListener(this);
        carer.setOnClickListener(this);
        getService("生活服务");
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.live_service:
                getService("生活服务");
                break;
            case R.id.convenience_service:
                getService("便民服务");
                break;
            case R.id.carer_service:
                getService("车主服务");
                break;
        }
    }

    public void getService(String serviceType) {
        new Thread() {
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
                    List<allServiceBean> beanList = gson.fromJson(jsonData, new TypeToken<ArrayList<allServiceBean>>() {
                    }.getType());
                    ArrayList<allServiceBean> list = new ArrayList<>();
                    for (allServiceBean item : beanList) {
                        if (item.getServiceType().equals(serviceType)) {
                            list.add(item);
                        }
                    }
                    adapter = new ServiceListAdapter(getActivity(), list);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
                            recyclerView.setLayoutManager(gridLayoutManager);
                            recyclerView.setAdapter(adapter);
                        }
                    });
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
