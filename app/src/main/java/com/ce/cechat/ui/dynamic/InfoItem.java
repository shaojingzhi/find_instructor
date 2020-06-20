package com.ce.cechat.ui.dynamic;


import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

/*
* 主页消息列表展示所需的具体信息
*
* */
public class InfoItem {

    public String user_id;
    public Bitmap head_image;
    public String nick_name;

    public String publish_time;
    public boolean attention;

    public int announcement_id;
    public String announcement_title;
    public String announcement_content;
}
