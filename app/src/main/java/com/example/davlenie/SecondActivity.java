package com.example.davlenie;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Intent intent = getIntent();
        TextView tvView = (TextView)findViewById(R.id.mTXt);
        String ans = intent.getStringExtra("lname");;
        String renam = ans.replaceAll("\\<.*?\\>", "");
        tvView.setText(renam.trim());
    }
}