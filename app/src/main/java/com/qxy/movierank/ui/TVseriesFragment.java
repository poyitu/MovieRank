package com.qxy.movierank.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qxy.movierank.R;
import com.qxy.movierank.adapter.MovieAdapter;
import com.qxy.movierank.adapter.TvAdapter;
import com.qxy.movierank.bean.RankBean;
import com.qxy.movierank.utils.RetrofitUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TVseriesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TVseriesFragment extends Fragment {
    private static final String TAG = "TVseriesFragment";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View root;
    private RecyclerView mRecyclerView;
    private TvAdapter mAdapter;
    private ArrayList<RankBean.DataDTO.ListDTO> beanArrayList = new ArrayList<>();

    public TVseriesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TVseriesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TVseriesFragment newInstance(String param1, String param2) {
        TVseriesFragment fragment = new TVseriesFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (root == null) {
            //获得布局文件
            root = inflater.inflate((R.layout.fragment_movie), container, false);
        }
        //初始化RecylerView组件的方法
        initRecyclerview();
        //初始化数据
        initData();
        return root;
    }

    private void initData() {
        RetrofitUtil retrofitUtil = RetrofitUtil.getInstance();
        retrofitUtil.getRank_Tomcat("2", null, new RetrofitUtil.CallBack() {
            @Override
            public void onSuccess(Object o) {
                String json = o.toString();
                Log.d(TAG, "json: " + json);
                List<RankBean.DataDTO.ListDTO> rank_list = ((RankBean) o).getData().getList();
//                for (RankBean.DataDTO.ListDTO listDTO : rank_list) {
//                    Log.d(TAG, "onSuccess: " + listDTO.getName());
//                }

                if (((RankBean) o).getData().getError_code() == 0) {
                    mAdapter.setData(rank_list);
                } else {
                    Log.d("测试", "onSuccess: " + ((RankBean) o).getData().getDescription());
                }

                // mAdapter = new MovieAdapter(getActivity(), rank_list);

            }

            @Override
            public void onFailed(Throwable t) {

            }
        });
    }

    private void initRecyclerview() {
        mRecyclerView = (RecyclerView) root.findViewById(R.id.tv1);
        //创建adapter类的对象

        //将对象作为参数通过setAdapter方法设置给recylerview；
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new TvAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);
        //这步骤必须有，这是选择RecylerView的显示方式
    }
}