package com.ce.cechat.ui.dynamic;

import java.util.LinkedList;


/*
* 从服务器端获取主页列表信息
*/
public class InfoItemList {
    private LinkedList<InfoItem> itemList;
    private int count = 0;

    public LinkedList<InfoItem> getMoreItems()
    {
        //TODO
        itemList = new LinkedList<>();

        for(int i=0;i<10;i++)
        {
            InfoItem newItem = new InfoItem();
            newItem.announcement_title = "招聘"+count;
            newItem.announcement_content = "招收计算机专业学生，有意向请联系我"+count;
            newItem.attention=false;
//        newItem.announcement_id = 1;
//        newItem.user_id = "sss";

            //newItem.publish_time = "2020-05-31";
            Long currentTime = System.currentTimeMillis();
            newItem.publish_time = currentTime.toString();
            itemList.add(newItem);
            count++;
        }
        return itemList;
    }

    public LinkedList<InfoItem> refreshItemList()
    {
        //TODO
        itemList = new LinkedList<>();
        count=0;

        for(int i=0;i<10;i++)
        {
            InfoItem newItem = new InfoItem();
            newItem.announcement_title = "招聘"+count;
            newItem.announcement_content = "招收计算机专业学生，有意向请联系我"+count;
            newItem.attention=false;
//        newItem.announcement_id = 1;
//        newItem.user_id = "sss";

            //newItem.publish_time = "2020-05-31";
            Long currentTime = System.currentTimeMillis();
            newItem.publish_time = currentTime.toString();
            itemList.add(newItem);
            count++;
        }
        return itemList;
    }
}
