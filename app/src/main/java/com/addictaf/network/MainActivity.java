package com.addictaf.network;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MAINTAG3";
    private Button formRequest, postRequest, multipartRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button button = findViewById(R.id.get_button);
        postRequest = findViewById(R.id.post_button);
        formRequest = findViewById(R.id.form_request_button);
        multipartRequest = findViewById(R.id.multipart_request_button);
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
                        doGetRequest();
                    }
                }).start();
            }
        });
        formRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        doFormDataRequest();
                    }
                }).start();
            }
        });
        multipartRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        doMultipartRequest();
                    }
                }).start();
            }
        });
    }

    private void doMultipartRequest() {
        String path = Environment.getExternalStorageDirectory().toString() +"/";
        Log.d(TAG, "Path " + path);
        File f = new File(path);
        File files[] = f.listFiles();
        if (files == null){
            Log.d(TAG, "The dirrectory is null");
            return;
        }
        Log.d(TAG, String.format("Number of files %s", Integer.toString(files.length)));
        for (int i=1; i<files.length; i++)
        {
            if (files[i].isFile())
            {
                Log.d(TAG, "Filename " + files[i].getName());
                doActualFilePost(files[i]);
                break;
            } else
            {
                Log.d(TAG, "Not a file");
            }
        }
    }

    private void doActualFilePost(File file) {
        OkHttpClient client = new OkHttpClient();
        Log.d(TAG, "Client created");
        String url = "https://reqres.in/api/users";
        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("name", "isaac")
                .addFormDataPart("image", file.getName(),
                        RequestBody.create(MediaType.parse("image/jpg"), file))
                .build();
        Log.d(TAG, "Body built");
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Log.d(TAG, "Request created");
        Log.d(TAG, "");
        try {
            Response response = client.newCall(request).execute();
            Log.d(TAG, "Response gotten");
            Log.d(TAG, response.body().string());
        } catch (IOException e) {
            Log.d(TAG, "exception gotten");
            Log.d(TAG, e.toString());
            e.printStackTrace();

        }
    }

    private void doFormDataRequest() {
        OkHttpClient client = new OkHttpClient();
        String url = "https://reqres.in/api/users";
        Log.d(TAG, "Started");
        RequestBody body = new FormBody.Builder()
                .add("name", "De Isaac")
                .add("job", "Engineer")
                .add("age", String.valueOf(23))
                .build();
        Log.d(TAG, "Body created");
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
            Log.d(TAG, "Io exception raised: " + e.getMessage());
            Log.d(TAG, e.toString());
            e.printStackTrace();
        }
    }

    private void doGetRequest() {
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
