package com.ce.cechat.ui.dynamic;

import android.graphics.BitmapFactory;

import com.ce.cechat.ui.Values;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/*
* 从服务器端获取主页列表信息
*/
public class InfoItemList {

    private LinkedList<InfoItem> itemList;
    private int page=1;
    private String oldSearchStr = "";

    // /user/getAllAnnouncement
    // 发送请求并获得回复
    private void getResponse()
    {
        try {
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = new FormBody.Builder()
                    .add("user_id", "newuser")
                    .add("search",oldSearchStr)
                    .add("page",String.valueOf(page))
                    .build();

            Request request = new Request.Builder().url(Values.rootIP+"/user/getAnnouncement").post(requestBody).build();

            Response response = client.newCall(request).execute();
            String result = response.body().string();
//            System.out.println(result);
            jsonToItemList(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private  void jsonToItemList(String result)
    {
        this.itemList = new LinkedList<>();

        if(result != null)
        {
            try{
                JSONObject jsonObject = new JSONObject(result);

                System.out.println(jsonObject.getString("msg"));
                if(jsonObject.getInt("code") == 200)
                {
                    JSONArray list = jsonObject.getJSONArray("list");
                    for(int i=0;i<list.length();i++)
                    {
                        JSONObject j = list.getJSONObject(i);
                        InfoItem newItem = new InfoItem();

                        newItem.user_id = j.getString("user_id");


                        // 获取图片
                        URL url=new URL(Values.rootIP + j.getString("photo_path").substring(1)+"/1.jpg");
                        HttpURLConnection conn;
                        conn = (HttpURLConnection) url.openConnection();  //创建链接
                        conn.setConnectTimeout(6000);   //超时设定
                        conn.setDoInput(true);
                        conn.setUseCaches(false);  //不设置本地缓存
                        InputStream is = conn.getInputStream();
                        newItem.head_image = BitmapFactory.decodeStream(is);    //将流转为Bitmap
                        is.close();


                        newItem.nick_name = j.getString("user_nickname");

                        Long time = j.getLong("publish_time");
                        newItem.publish_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time));

                        newItem.attention=j.getBoolean("issubscribe");

                        newItem.announcement_id = j.getInt("announcement_id");
                        newItem.announcement_title = j.getString("announcement_title");
                        newItem.announcement_content = j.getString("announcement_content");
                        itemList.add(newItem);
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public LinkedList<InfoItem> getMoreItems(String searchStr,boolean refresh)
    {
        //TODO
        if(oldSearchStr.equals(searchStr))
        {

        }
        else
        {
            page=1;
            oldSearchStr=searchStr;
        }

        if(refresh)
        {
            page=1;
        }
        getResponse();

        page++;
        return this.itemList;
    }
}
