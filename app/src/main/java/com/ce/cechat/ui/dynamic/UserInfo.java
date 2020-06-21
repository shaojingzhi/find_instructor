package com.ce.cechat.ui.dynamic;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ce.cechat.R;
import com.ce.cechat.ui.Values;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UserInfo extends AppCompatActivity {


    private ImageButton btnBack;
    private CircleImageView view_head_image;
    private TextView view_user_nickname;

    private TextView view_user_id;
    private TextView view_user_sex;
    private TextView view_user_role;
    private TextView view_user_department;
    private TextView view_user_introduction;

    private String user_id;
    private String user_department;
    private String user_introduction;
    private String user_nickname;
    private Bitmap head_image;
    private String user_role;
    private String user_sex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        getSupportActionBar().hide();

        btnBack=findViewById(R.id.btnBack);

        view_head_image = findViewById(R.id.view_head_image);
        view_user_nickname = findViewById(R.id.view_user_nickname);
        view_user_id=findViewById(R.id.view_user_id);
        view_user_sex=findViewById(R.id.view_user_sex);
        view_user_role=findViewById(R.id.view_user_role);
        view_user_department=findViewById(R.id.view_user_department);
        view_user_introduction=findViewById(R.id.view_user_introduction);

        user_id=getIntent().getStringExtra("user_id");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("user_id",user_id)
                            .build();

                    Request request = new Request.Builder().url(Values.rootIP+"/user/getUserInfo").post(requestBody).build();
                    String result = client.newCall(request).execute().body().string();

                    JSONObject jsonObject = new JSONObject(result);

                    URL url=new URL(Values.rootIP + jsonObject.getString("user_photo_path"));
                    HttpURLConnection conn;
                    conn = (HttpURLConnection) url.openConnection();  //创建链接
                    conn.setConnectTimeout(6000);   //超时设定
                    conn.setDoInput(true);
                    conn.setUseCaches(false);  //不设置本地缓存
                    InputStream is = conn.getInputStream();
                    head_image = BitmapFactory.decodeStream(is);    //将流转为Bitmap
                    is.close();


                    user_nickname=jsonObject.getString("user_nickname");
                    user_sex=jsonObject.getString("user_sex");
                    user_role=jsonObject.getString("user_role");
                    user_department=jsonObject.getString("user_department");

                    user_introduction=jsonObject.getString("user_introduction");


                    Message msg = new Message();
                    msg.what = 103;
                    handler.sendMessage(msg);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==103)
            {
                view_user_id.setText(user_id);
                view_head_image.setImageBitmap(head_image);
                view_user_nickname.setText(user_nickname);
                view_user_sex.setText(user_sex);
                view_user_role.setText(user_role);
                view_user_department.setText(user_department);
                view_user_introduction.setText(user_introduction);
            }
        }
    };
}
