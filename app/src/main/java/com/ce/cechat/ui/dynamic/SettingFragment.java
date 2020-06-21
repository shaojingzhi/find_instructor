package com.ce.cechat.ui.dynamic;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

import com.ce.cechat.R;
import com.ce.cechat.app.BaseFragment;
import com.ce.cechat.ui.Values;

import java.util.LinkedList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;

/**
 * @author CE Chen
 *
 * 动态
 *
 * Use the {@link SettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingFragment extends BaseFragment {


    private boolean flagForThread=true;  //更新数据的线程管理 如：下拉刷新，上滑加载，搜索等
    private boolean flagForThread2=false;   // 关注按钮事件处理的线程管理
    private View view;
    private LinkedList<InfoItem> infoList = new LinkedList<>();  // 列表信息
    private LinkedList<InfoItem> newInfos;                      // 获取的新的部分信息
    private final InfoItemList infoItemList = new InfoItemList(); // 从服务器获取列表的类的实例

    private FloatingActionButton goTop;                     // 返回顶部按钮
    private FloatingActionButton writeAnnouncement;         // 新动态

    private RecyclerView mainPageListRV;                // RecyclerView部件
    private MainPageListAdapter mainPageListAdapter;                // adapter
    private SwipeRefreshLayout mainPageListSwipeRefreshLayout;      // 用于下拉刷新和上滑加载
    private EditText search;                            //search 搜索框
    private LinearLayoutCompat search_linearLayout;        // search外部的布局，用于解决search搜索框 focus的问题

    private String searchStr; // 搜索框字符

    public SettingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment SettingFragment.
     */
    public static SettingFragment newInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    protected void initView(View view) {
        this.view=view;
        initRecyclerView();
    }

    @Override
    protected void setListener() {
        setEvent();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_setting;
    }


    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.what==100)
            {
                if(newInfos != null) {
                    infoList.addAll(newInfos);
                    newInfos=null;
                    mainPageListAdapter.notifyDataSetChanged();
                }
                flagForThread=false;
            }
            else if(msg.what==101)
            {
                flagForThread2=false;
            }
            else if(msg.what==102)  // 需要清空infolist
            {
                infoList.clear();
                if(newInfos != null) {
                    infoList.addAll(newInfos);
                    newInfos=null;
                    mainPageListAdapter.notifyDataSetChanged();
                }
                flagForThread=false;
            }
        }
    };

    private void initRecyclerView() {

        goTop = view.findViewById(R.id.fab_go_top);
        goTop.setVisibility(View.GONE);  //隐藏

        writeAnnouncement = view.findViewById(R.id.write);

        mainPageListSwipeRefreshLayout = view.findViewById(R.id.main_page_list_swipeRefreshLayout);
        mainPageListRV = view.findViewById(R.id.main_page_list_rv);
        mainPageListAdapter = new MainPageListAdapter(getActivity(), infoList);
        mainPageListRV.setAdapter(mainPageListAdapter);

        search = view.findViewById(R.id.search);  // 搜索输入框
        searchStr = search.getText().toString();
        search_linearLayout = view.findViewById(R.id.search_linearLayout);  //为了调整search输入框焦点

        mainPageListRV.setLayoutManager(
                new LinearLayoutManager(getActivity(),
                        LinearLayoutManager.VERTICAL, false));

        new Thread(new Runnable() {
            @Override
            public void run() {
                flagForThread = true;
                newInfos = infoItemList.getMoreItems(searchStr,true);
                Message msg = new Message();
                msg.what = 100;
                handler.sendMessage(msg);
            }
        }).start();

    }
    private void setEvent()
    {
        goTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainPageListRV.smoothScrollToPosition(0);
                goTop.setVisibility(View.GONE);
            }
        });

        writeAnnouncement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingFragment.this.getActivity(), newAnnouncement.class);
                startActivityForResult(intent,101);
            }
        });

        // 去掉search输入框焦点
        mainPageListRV.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                search_linearLayout.setFocusable(true);
                search_linearLayout.setFocusableInTouchMode(true);
                search_linearLayout.requestFocus();
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(search.getWindowToken(), 0);
                return false;
            }
        });


        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchStr = search.getText().toString();
                //TODO
                if(!flagForThread) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            flagForThread = true;
                            newInfos = infoItemList.getMoreItems(searchStr, true);
                            Message msg = new Message();
                            msg.what = 102;
                            handler.sendMessage(msg);
                        }
                    }).start();
                }
            }
        });

        // 下拉刷新
        this.mainPageListSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 此处考虑多线程？
                if(!flagForThread)
                {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            flagForThread=true;
                            newInfos = infoItemList.getMoreItems(searchStr,true);
                            Message msg = new Message();
                            msg.what=102;
                            handler.sendMessage(msg);
                        }
                    }).start();
                }
                mainPageListSwipeRefreshLayout.setRefreshing(false);

            }
        });

        // 上滑加载更多
        this.mainPageListRV.addOnScrollListener(new RecyclerView.OnScrollListener() {

            private int lastVisiblePosition;
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE)
                {
                    if(!mainPageListRV.canScrollVertically(1))
                    {
                        if(!flagForThread)
                        {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    flagForThread=true;
                                    newInfos = infoItemList.getMoreItems(searchStr,false);
                                    Message msg = new Message();
                                    msg.what=100;
                                    handler.sendMessage(msg);
                                }
                            }).start();
                        }
                    }

                    // 返回顶部按键在一定条件下显示
                    if(lastVisiblePosition >= 10) goTop.setVisibility(View.VISIBLE);
                    else goTop.setVisibility(View.GONE);
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

                if(layoutManager instanceof LinearLayoutManager)
                {
                    LinearLayoutManager lm = (LinearLayoutManager)layoutManager;
                    lastVisiblePosition = lm.findLastVisibleItemPosition();
                }
            }
        });

        //TODO click
        //item内部部件点击事件处理
        mainPageListAdapter.setOnItemClickListener(new MainPageListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                if(infoList!=null && position<infoList.size())
                {
                    switch (v.getId())
                    {
                        case R.id.attention:
                            attentionBtnClick(v,position);
                            break;
                        case R.id.btnDelete:
                            deleteBtnCilck(v,position);
                            break;
                        case R.id.head_image:  // 头像点击事件处理
                            break;
                        case R.id.announcement_content:// 内容点击事件处理
                            break;
                        default:
                            break;
                    }
                }
            }
        });
    }

    private void deleteBtnCilck(View v,final int position)
    {
        ImageButton deleteButton = (ImageButton)v;
        final InfoItem deleteItem = infoList.get(position);
        infoList.remove(position);
        mainPageListAdapter.notifyDataSetChanged();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("user_id", Values.use_id)
                            .add("announcement_id",String.valueOf(deleteItem.announcement_id))
                            .build();

                    Request request = new Request.Builder().url(Values.rootIP+"/user/deleteAnnouncement").post(requestBody).build();
                    client.newCall(request).execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    // “关注” 按钮点击事件
    private void attentionBtnClick(View v, final int position)
    {
        ImageButton imageButton = (ImageButton)v;

        if(!flagForThread2)
        {
            if(infoList.get(position).attention==false)
            {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        flagForThread2=true;
                        try {
                            OkHttpClient client = new OkHttpClient();
                            RequestBody requestBody = new FormBody.Builder()
                                    .add("user_id", Values.use_id)
                                    .add("to_user_id",infoList.get(position).user_id)
                                    .build();

                            Request request = new Request.Builder().url(Values.rootIP+"/user/subscribe").post(requestBody).build();
                            client.newCall(request).execute();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Message msg = new Message();
                        msg.what=101;
                        handler.sendMessage(msg);

                    }
                }).start();

                infoList.get(position).attention=true;
                imageButton.setBackgroundResource(R.drawable.ic_quxiaoguanzhu);
            }
            else
            {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        flagForThread2=true;

                        try {
                            OkHttpClient client = new OkHttpClient();
                            RequestBody requestBody = new FormBody.Builder()
                                    .add("user_id", Values.use_id)
                                    .add("to_user_id",infoList.get(position).user_id)
                                    .build();

                            Request request = new Request.Builder().url(Values.rootIP+"/user/unsubscribe").post(requestBody).build();

                            client.newCall(request).execute();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        Message msg = new Message();
                        msg.what=101;
                        handler.sendMessage(msg);

                    }
                }).start();

                infoList.get(position).attention=false;
                imageButton.setBackgroundResource(R.drawable.ic_guanzhu);
            }

        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101 && resultCode == RESULT_OK) {

            final String new_title = data.getStringExtra("title");
            final String new_content = data.getStringExtra("content");

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        OkHttpClient client = new OkHttpClient();
                        RequestBody requestBody = new FormBody.Builder()
                                .add("user_id", Values.use_id)
                                .add("announcement_title",new_title)
                                .add("announcement_content",new_content)
                                .build();

                        Request request = new Request.Builder().url(Values.rootIP+"/user/postAnnouncement").post(requestBody).build();

                        Response response = client.newCall(request).execute();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
