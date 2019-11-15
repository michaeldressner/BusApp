package com.example.busapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.princetonbus.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    LinearLayout llStopList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        allowNetworkUsage();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataScrape d = new DataScrape(84);
    }

    private void allowNetworkUsage() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
}
