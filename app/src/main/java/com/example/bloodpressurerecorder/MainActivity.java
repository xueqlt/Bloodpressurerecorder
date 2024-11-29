package com.example.bloodpressurerecorder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.bloodpressurerecorder.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.button1) {
            startActivity(new Intent(MainActivity.this, handin.class));
        } else if (id == R.id.button2) {
            startActivity(new Intent(MainActivity.this, ChartLineViewActivity.class));
        } else if (id == R.id.button3) {
            startActivity(new Intent(MainActivity.this, readin.class));
        }
    }



}
