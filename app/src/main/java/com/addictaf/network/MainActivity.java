package com.addictaf.network;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MAINTAG";
    private Button getRequest, postRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button button = findViewById(R.id.home_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        doGetrequest();
                    }
                }).start();
            }
        });
    }

    private void doGetrequest() {
        Log.d(TAG, "Started");
        String url = "https://api.addictaf.com/posts/post/?limit=1";
        OkHttpClient client = new OkHttpClient();
        Log.d(TAG, "Client created");
        Request request = new Request.Builder()
                .url(url)
                .build();
        Log.d(TAG, "Request built");
        try {
            Response response = client.newCall(request).execute();
            Log.d(TAG, "Response established");
            Log.d(TAG, response.body().string());
        } catch (IOException e) {
            Log.d(TAG, "Error found");
            e.printStackTrace();
        }
    }
}
