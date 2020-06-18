package com.ce.cechat.ui.dynamic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;

import com.ce.cechat.R;

import java.util.LinkedList;

public class MainPageListAdapter
        extends RecyclerView.Adapter<MainPageListAdapter.ListItemViewHolder>
        implements View.OnClickListener{

    private Context context;
    private LinkedList<InfoItem> infoList;

    public MainPageListAdapter(Context context, LinkedList<InfoItem> infoList)
    {
        this.context = context;
        this.infoList = infoList;
    }

    @Override
    public ListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item,parent,false);
        return new ListItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ListItemViewHolder holder,int position)
    {
        InfoItem itemData = infoList.get(position);
        holder.announcement_content.setText(itemData.announcement_content);
        //holder.announcement_title.setText(itemData.announcement_title);
        holder.publish_time.setText(itemData.publish_time);

        holder.attention.setTag(position);  // 标记按钮处于哪
        holder.head_image.setTag(position);
        holder.announcement_content.setTag(position);

        // 初始关注按钮的状态设定
        if(itemData.attention==true)
            holder.attention.setBackgroundResource(R.drawable.ic_quxiaoguanzhu);
        else
            holder.attention.setBackgroundResource(R.drawable.ic_guanzhu);
    }


    @Override
    public int getItemCount() {
        return infoList.size();
    }


    class ListItemViewHolder extends RecyclerView.ViewHolder{

        private CardView cardView;  // 目前没发现拿进来有什么用处
        private TextView announcement_content;
        //private TextView announcement_title;
        private TextView publish_time;
        private ImageButton attention;
        private ImageView head_image;

        public ListItemViewHolder(View itemView)
        {
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.item_cardview);
            announcement_content = (TextView)itemView.findViewById(R.id.announcement_content);
            //announcement_title=(TextView)itemView.findViewById(R.id.announcement_title);
            publish_time=(TextView)itemView.findViewById(R.id.publish_time);
            attention=(ImageButton)itemView.findViewById(R.id.attention);
            head_image = (ImageView)itemView.findViewById(R.id.head_image);

            //添加点击事件
            attention.setOnClickListener(MainPageListAdapter.this);
            head_image.setOnClickListener(MainPageListAdapter.this);
            announcement_content.setOnClickListener(MainPageListAdapter.this);

        }
    }

    public interface OnItemClickListener
    {
        void onItemClick(View v, int position);
    }

    private OnItemClickListener myItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.myItemClickListener=listener;
    }

    @Override
    public void onClick(View v)
    {
        int position = (int)v.getTag();
        if(myItemClickListener != null)
        {
            myItemClickListener.onItemClick(v, position);
        }
    }

}
