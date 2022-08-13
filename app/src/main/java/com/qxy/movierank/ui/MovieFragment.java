package com.qxy.movierank.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.reflect.TypeToken;
import com.qxy.movierank.R;
import com.qxy.movierank.adapter.MovieAdapter;
import com.qxy.movierank.bean.MovieBean;
import com.qxy.movierank.bean.RankBean;
import com.qxy.movierank.utils.JsonParse;
import com.qxy.movierank.utils.RetrofitUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "MovieFragment";
    private View root;

    private String mParam1;
    private String mParam2;
    private RecyclerView mRecyclerView;
    private MovieAdapter mAdapter;
    private ArrayList<RankBean.DataDTO.ListDTO> beanArrayList = new ArrayList<>();

    public MovieFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MovieFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MovieFragment newInstance(String param1, String param2) {
        MovieFragment fragment = new MovieFragment();
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
        retrofitUtil.getRank_Tomcat("1", null, new RetrofitUtil.CallBack() {
            @Override
            public void onSuccess(Object o) {
                String json = o.toString();
                Log.d(TAG, "json: " + json);
                List<RankBean.DataDTO.ListDTO> rank_list = new ArrayList<>();

                if (((RankBean) o).getData().getError_code() == 0) {
                    rank_list = ((RankBean)o).getData().getList();
                    mAdapter.setData(rank_list);
                } else {
                    Log.d("测试", "onSuccess: " + ((RankBean) o).getData().getDescription());
                }


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
        mAdapter = new MovieAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);
        //这步骤必须有，这是选择RecylerView的显示方式

    }

    //@Override
    //public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    //    super.onViewCreated(view, savedInstanceState);
    //    mRecyclerView = view.findViewById(R.id.tv1);
    //    mAdapter = new MovieAdapter(getContext(), );
    //    mRecyclerView.setAdapter(mAdapter);
    //}
}