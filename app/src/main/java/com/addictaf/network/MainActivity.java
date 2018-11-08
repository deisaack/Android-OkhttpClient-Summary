package com.addictaf.network;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MAINTAG";
    private Button getRequest, postRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button button = findViewById(R.id.get_button);
        postRequest = findViewById(R.id.post_button);
        postRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        doPostRequest();
                    }
                }).start();
            }
        });
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

    private void doPostRequest() {
        String url = "https://api.addictaf.com/users/login/";
        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.get("application/json;charset=utf-8");
        JSONObject data = new JSONObject();
        try {
            data.put("username", "de");
            data.put("password", "1");
        } catch (JSONException e) {
            Log.d(TAG, "Json error " + e.getMessage());
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, data.toString());
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Log.d(TAG, "Request built");
        try {
            Response response = client.newCall(request).execute();
            Log.d(TAG, "Response received");
            Log.d(TAG, response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "Error found");
        }

    }
}
