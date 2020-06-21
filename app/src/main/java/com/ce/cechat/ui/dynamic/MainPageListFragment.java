package com.ce.cechat.ui.dynamic;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import com.ce.cechat.app.BaseFragment;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v4.widget.SwipeRefreshLayout;

import com.ce.cechat.ui.dynamic.InfoItem;
import com.ce.cechat.ui.dynamic.InfoItemList;
import com.ce.cechat.R;
import com.ce.cechat.ui.dynamic.MainPageListAdapter;
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.support.design.widget.FloatingActionButton;

import java.util.LinkedList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainPageListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainPageListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View view;
    private final LinkedList<InfoItem> infoList = new LinkedList<>();  // 列表信息
    private final InfoItemList infoItemList = new InfoItemList(); // 从服务器获取列表的类的实例

    private FloatingActionButton goTop;

    private RecyclerView mainPageListRV;                // RecyclerView部件
    private MainPageListAdapter mainPageListAdapter;
    private SwipeRefreshLayout mainPageListSwipeRefreshLayout;      // 用于下拉刷新和上滑加载

    public MainPageListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainPageListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainPageListFragment newInstance(String param1, String param2) {
        MainPageListFragment fragment = new MainPageListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_main_page_list, container, false);

        goTop = (FloatingActionButton)view.findViewById(R.id.fab_go_top);
        goTop.setVisibility(View.GONE);  //隐藏

        initRecyclerView();

        // 返回列表首端
        goTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainPageListRV.smoothScrollToPosition(0);
                goTop.setVisibility(View.GONE);
            }
        });

        return view;
    }

    private void initRecyclerView()
    {
        mainPageListSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.main_page_list_swipeRefreshLayout);
        mainPageListRV = (RecyclerView)view.findViewById(R.id.main_page_list_rv);
        mainPageListAdapter = new MainPageListAdapter(getActivity(),infoList);
        mainPageListRV.setAdapter(mainPageListAdapter);

        mainPageListRV.setLayoutManager(
                new LinearLayoutManager(getActivity(),
                        LinearLayoutManager.VERTICAL,false));


        //infoList.addAll(infoItemList.getMoreItems());

        // 下拉刷新
        this.mainPageListSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 此处考虑多线程？
                infoList.clear();
                //infoList.addAll(infoItemList.refreshItemList());
                mainPageListSwipeRefreshLayout.setRefreshing(false);
                mainPageListAdapter.notifyDataSetChanged();
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
                    if(lastVisiblePosition == mainPageListAdapter.getItemCount()-1)
                    {
                        //infoList.addAll(infoItemList.getMoreItems());
                        mainPageListAdapter.notifyDataSetChanged();
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

        mainPageListAdapter.setOnItemClickListener(new MainPageListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                switch (v.getId())
                {
                    case R.id.attention:
                        attentionBtnClick(v,position);
                        System.out.println(position+" attention");
                        Log.d("attention","success "+position);
                        break;
                    case R.id.head_image:  // 头像点击事件处理
                        System.out.println(position+" head");
                        Log.d("head","success "+position);
                        break;
                    case R.id.announcement_content:// 内容点击事件处理
                        System.out.println(position+" announcement_content");
                        Log.d("announcement_content","success "+position);
                        break;
                    default:
                        System.out.println(position+" item");
                        Log.d("item","success "+position);
                        break;
                }
            }
        });
    }

    // “关注” 按钮点击事件
    private void attentionBtnClick(View v, int position)
    {
        ImageButton imageButton = (ImageButton)v;
        if(infoList.get(position).attention==false)
        {
            infoList.get(position).attention=true;
            imageButton.setBackgroundResource(R.drawable.ic_quxiaoguanzhu);
        }
        else
        {
            infoList.get(position).attention=false;
            imageButton.setBackgroundResource(R.drawable.ic_guanzhu);
        }
    }
}
