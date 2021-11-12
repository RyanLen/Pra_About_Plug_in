package com.example.mypra;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.example.mypra.fragment.AllServiceFragment;
import com.example.mypra.fragment.HomeFragment;
import com.example.mypra.fragment.NewsFragment;

public class TopActivity extends AppCompatActivity {
    private LinearLayout linearLayout;
    private RadioGroup radioGroup;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);
        linearLayout = findViewById(R.id.line_layout);
        radioGroup = findViewById(R.id.rg);
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.add(R.id.line_layout, new HomeFragment());
        transaction.commit();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                transaction = manager.beginTransaction();
                switch (checkedId){
                    case R.id.rb_home:
                        transaction.replace(R.id.line_layout, new HomeFragment());
                        break;
                    case R.id.rb_service:
                        transaction.replace(R.id.line_layout, new AllServiceFragment());
                        break;
                    case R.id.rb_news:
                        transaction.replace(R.id.line_layout, new NewsFragment());
                        break;
                    case R.id.rb_activity:
                        break;
                    case R.id.rb_my:
                        break;
                    default:
                        break;
                }
                transaction.commit();
            }
        });
    }
}