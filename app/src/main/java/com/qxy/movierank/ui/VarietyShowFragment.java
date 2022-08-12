package com.qxy.movierank.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qxy.movierank.R;
import com.qxy.movierank.adapter.VarietyAdapter;
import com.qxy.movierank.bean.RankBean;
import com.qxy.movierank.utils.RetrofitUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VarietyShowFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VarietyShowFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView varietyShowRankRecyclerViewVariert;
    private VarietyAdapter varietyAdapter;

    public VarietyShowFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VarietyShowFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VarietyShowFragment newInstance(String param1, String param2) {
        VarietyShowFragment fragment = new VarietyShowFragment();
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

        View root = inflater.inflate(R.layout.fragment_variety_show, container, false);
        initView(root);
        initRecyclerView();
        return root;
    }

    private void initView(View root) {
        varietyShowRankRecyclerViewVariert = (RecyclerView) root.findViewById(R.id.varietyShowRank_RecyclerView_variert);
    }


    private void initRecyclerView(){
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        varietyShowRankRecyclerViewVariert.setLayoutManager(linearLayoutManager);
        //设置适配器
        varietyAdapter = new VarietyAdapter();
        varietyShowRankRecyclerViewVariert.setAdapter(varietyAdapter);

    }

    @Override
    public void onStart() {
        super.onStart();
        RetrofitUtil.getInstance().getRank("3", "", new RetrofitUtil.CallBack() {
            @Override
            public void onSuccess(Object o) {
                if(((RankBean)o).getData().getError_code() == 0){
                    varietyAdapter.setData(((RankBean)o).getData().getList());
                }else {
                    Log.d("测试", "onSuccess: "+((RankBean)o).getData().getDescription());
                }
            }

            @Override
            public void onFailed(Throwable t) {

            }
        });
    }
}