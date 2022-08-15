package com.qxy.movierank.ui;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qxy.movierank.R;
import com.qxy.movierank.adapter.MovieAdapter;
import com.qxy.movierank.adapter.RankVersionListViewAdapter;
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
 * Use the {@link MovieFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieFragment extends Fragment implements VarietyShowContract.View {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "MovieFragment";
    private View root;
    private int netWorkStart;
    private final String ITEMNAME = "movie";
    private String mParam1;
    private String mParam2;
    private RecyclerView mRecyclerView;
    private MovieAdapter mAdapter;
    private ArrayList<RankBean.DataDTO.ListDTO> beanArrayList = new ArrayList<>();
    private SaveLocal mSaveLocal;
    private ImageView imageView5;
    private TextView currentRankVersionMovieRank;
    private TextView textView2;
    private RecyclerView rvMovieRank;


    private AlertDialog rankVersionDialog;
    private RankVersionListViewAdapter rankVersionListViewAdapter;

    private String rankType = "1";
    private String rankVersion = "";
    private String active_time_rank = "";
    private String start_time_rank = "";
    private String end_time_rank = "";

    private VarietyShowViewModel varietyShowViewModel;

    private static List<RankVersionBean.DataDTO.ListDTO> rankVersion_List = new ArrayList<>();
    private Boolean isFirstGetRankVersion = true;

    public MovieFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static MovieFragment newInstance() {
        MovieFragment fragment = new MovieFragment();

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
        //获取存储到本地数据的工具类
        mSaveLocal = new SaveLocal(getActivity());
        varietyShowViewModel = new ViewModelProvider(getActivity()).get(VarietyShowViewModel.class);

        initView(root);
        initShowRankVersionDialog();
        //初始化RecylerView组件的方法
        initRecyclerview();
        initObserveData();

        SharedPreferences rankItem = getActivity().getSharedPreferences(ITEMNAME, 0);
        if (rankItem != null && netWorkStart == 1) {
            // 断网时初始化本地数据
            initLoclDate();
        } else {
            //初始化数据
//            initData();
        }


        return root;
    }


    @Override
    public void onStart() {
        super.onStart();
        //加载电影榜数据
        varietyShowViewModel.loadVarietyRank(getContext(), rankType, rankVersion);
    }

    private void initView(View root) {
        imageView5 = (ImageView) root.findViewById(R.id.imageView5);
        currentRankVersionMovieRank = (TextView) root.findViewById(R.id.currentRankVersion_movieRank);
        textView2 = (TextView) root.findViewById(R.id.textView2);
        rvMovieRank = (RecyclerView) root.findViewById(R.id.rv_movieRank);

        currentRankVersionMovieRank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFirstGetRankVersion) {
                    //加载电影榜版本数据
                    varietyShowViewModel.loadVarietyRankVersion(active_time_rank, "0", "10", rankType);
                    isFirstGetRankVersion = false;
                }

                rankVersionDialog.show();
            }
        });
    }

    private void initLoclDate() {
        List<RankBean.DataDTO.ListDTO> bean = mSaveLocal.getBean(ITEMNAME);
        mAdapter.setData(bean);
    }


    private void initRecyclerview() {
        mRecyclerView = (RecyclerView) root.findViewById(R.id.rv_movieRank);
        //创建adapter类的对象
        netWorkStart = NetUtil.getNetWorkStart(getActivity());
        //将对象作为参数通过setAdapter方法设置给recylerview；
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new MovieAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);
        //这步骤必须有，这是选择RecylerView的显示方式

    }

    @Override
    public void showVarietyRank(String active_time, List<RankBean.DataDTO.ListDTO> varietyShowBeanList) {
        //本周榜
        if (rankVersion.equals("0") || rankVersion.isEmpty()) {
            currentRankVersionMovieRank.setText("本周榜 | 更新于" + active_time + " 12:00");
        } else {
            //非本周榜
            currentRankVersionMovieRank.setText("第" + rankVersion + "期 " + start_time_rank + "~" + end_time_rank);
        }

        mAdapter.setData(varietyShowBeanList);
    }

    private void initObserveData() {

        varietyShowViewModel.getmVarietyRankData().observe(getViewLifecycleOwner(), new Observer<RankBean.DataDTO>() {
            @Override
            public void onChanged(RankBean.DataDTO dataDTO) {
                active_time_rank = dataDTO.getActive_time();
                showVarietyRank(dataDTO.getActive_time(), dataDTO.getList());
            }
        });

        varietyShowViewModel.getmVarietyRankVersionList().observe(getViewLifecycleOwner(), new Observer<List<RankVersionBean.DataDTO.ListDTO>>() {
            @Override
            public void onChanged(List<RankVersionBean.DataDTO.ListDTO> listDTOS) {
                rankVersion_List = listDTOS;
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
                        rankVersion = rankVersion_List.get(i).getVersion() + "";
                        start_time_rank = rankVersion_List.get(i).getStart_time();
                        end_time_rank = rankVersion_List.get(i).getEnd_time();

                        varietyShowViewModel.loadVarietyRank(getContext(), rankType, rankVersion);

                        Toast.makeText(getContext(), "点击了" + rankVersion_List.get(i).getVersion(), Toast.LENGTH_SHORT).show();

                    }
                })
                .create();


    }

    //@Override
    //public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    //    super.onViewCreated(view, savedInstanceState);
    //    mRecyclerView = view.findViewById(R.id.tv1);
    //    mAdapter = new MovieAdapter(getContext(), );
    //    mRecyclerView.setAdapter(mAdapter);
    //}
}