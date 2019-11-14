package com.example.busapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;

import com.example.princetonbus.R;

public class MainActivity extends AppCompatActivity {

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
