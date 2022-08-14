package com.qxy.movierank.ui;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qxy.movierank.R;
import com.qxy.movierank.adapter.RankVersionListViewAdapter;
import com.qxy.movierank.adapter.VarietyAdapter;
import com.qxy.movierank.bean.RankBean;
import com.qxy.movierank.bean.RankVersionBean;
import com.qxy.movierank.contracts.VarietyShowContract;
import com.qxy.movierank.utils.NetUtil;
import com.qxy.movierank.utils.RetrofitUtil;
import com.qxy.movierank.utils.SaveLocal;
import com.qxy.movierank.viewmodel.VarietyShowViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VarietyShowFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VarietyShowFragment extends Fragment implements VarietyShowContract.View {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ITEMNAME = "Variety";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView varietyShowRankRecyclerViewVariert;
    private VarietyAdapter varietyAdapter;

    private VarietyShowViewModel varietyShowViewModel;

    private String rankType = "3";
    private String rankVersion = "141";
    private String start_time_rank = "2022-08-01";
    private String end_time_rank = "2022-08-08";

    private Boolean isFirstGetRankVersion = true;
    private static List<RankVersionBean.DataDTO.ListDTO> rankVersion_List = new ArrayList<>();

    private TextView currentRankVersionVariety;
    private TextView historyRankVersionVariety;
    private int netWorkStart;
    private SaveLocal mSaveLocal;

    private AlertDialog rankVersionDialog;
    private RankVersionListViewAdapter rankVersionListViewAdapter;

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
        varietyShowViewModel = new ViewModelProvider(getActivity()).get(VarietyShowViewModel.class);
        //获取存储到本地数据的工具类
        mSaveLocal = new SaveLocal(getActivity());

        initView(root);
        initRecyclerView();
        initObserveData();
        initShowRankVersionDialog();
        SharedPreferences rankItem = getActivity().getSharedPreferences(ITEMNAME, 0);
        if (rankItem != null && netWorkStart == 1) {
            // 断网时初始化本地数据
            initLoclDate();
        } else {
            //初始化数据
            initObserveData();
        }

        return root;
    }


    @Override
    public void onStart() {
        super.onStart();
        //加载综艺榜数据
        varietyShowViewModel.loadVarietyRank(getContext(), rankType, rankVersion);

    }

    private void initView(View root) {
        varietyShowRankRecyclerViewVariert = (RecyclerView) root.findViewById(R.id.varietyShowRank_RecyclerView_variert);
        currentRankVersionVariety = (TextView) root.findViewById(R.id.currentRankVersion_variety);
        historyRankVersionVariety = (TextView) root.findViewById(R.id.historyRankVersion_variety);

        currentRankVersionVariety.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFirstGetRankVersion) {
                    //加载综艺榜版本数据
                    varietyShowViewModel.loadVarietyRankVersion("0","10",rankType);
                    isFirstGetRankVersion = false;
                }

                rankVersionDialog.show();
            }
        });
    }

    // 加载本地数据
    private void initLoclDate() {
        List<RankBean.DataDTO.ListDTO> bean = mSaveLocal.getBean(ITEMNAME);
        varietyAdapter.setData(bean);
    }

    private void initRecyclerView() {
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        varietyShowRankRecyclerViewVariert.setLayoutManager(linearLayoutManager);
        // 查看当前网络状态
        netWorkStart = NetUtil.getNetWorkStart(getActivity());
        //设置适配器
        varietyAdapter = new VarietyAdapter();
        varietyShowRankRecyclerViewVariert.setAdapter(varietyAdapter);

    }

    private void initObserveData() {
//        varietyShowViewModel.getmVarietyBeanList().observe(getViewLifecycleOwner(), new Observer<List<RankBean.DataDTO.ListDTO>>() {
//            @Override
//            public void onChanged(List<RankBean.DataDTO.ListDTO> listDTOS) {
//                showVarietyRank(listDTOS);
//            }
//        });

        varietyShowViewModel.getmVarietyRankData().observe(getViewLifecycleOwner(), new Observer<RankBean.DataDTO>() {
            @Override
            public void onChanged(RankBean.DataDTO dataDTO) {
                showVarietyRank(dataDTO.getActive_time(), dataDTO.getList());
            }
        });

        varietyShowViewModel.getmVarietyRankVersionData().observe(getViewLifecycleOwner(), new Observer<RankVersionBean.DataDTO>() {
            @Override
            public void onChanged(RankVersionBean.DataDTO dataDTO) {
                rankVersion_List = dataDTO.getList();
                rankVersionListViewAdapter.setData(rankVersion_List);

            }
        });

        //在提示access_token过期后，已重新获取access_token，现重新请求数据
        varietyShowViewModel.getIsClientTokenRefreshComplete().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                varietyShowViewModel.loadVarietyRank(getContext(), rankType, rankVersion);
            }
        });

    }


    private void initShowRankVersionDialog() {
        View view = View.inflate(getContext(), R.layout.dialog_rank_version, null);

        rankVersionListViewAdapter = new RankVersionListViewAdapter(getContext());

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        rankVersionDialog = builder
                .setView(view)
                .setAdapter(rankVersionListViewAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        rankVersion = rankVersion_List.get(i).getVersion()+"";
                        start_time_rank = rankVersion_List.get(i).getStart_time();
                        end_time_rank = rankVersion_List.get(i).getEnd_time();

                        varietyShowViewModel.loadVarietyRank(getContext(),rankType,rankVersion);

                        Toast.makeText(getContext(),"点击了"+rankVersion_List.get(i).getVersion(),Toast.LENGTH_SHORT).show();

                    }
                })
                .create();


    }

    @Override
    public void showVarietyRank(String active_time, List<RankBean.DataDTO.ListDTO> varietyShowBeanList) {
        //本周榜
        if (rankVersion.isEmpty()) {
            currentRankVersionVariety.setText("本周榜 | 更新于" + active_time + " 12:00");
        } else {
            //非本周榜
            currentRankVersionVariety.setText("第" + rankVersion + "期 " + start_time_rank + "~" + end_time_rank);
        }

        varietyAdapter.setData(varietyShowBeanList);
        // 接受获取到的数据，并存储到本地
    }


}