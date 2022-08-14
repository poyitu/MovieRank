package com.qxy.movierank.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qxy.movierank.R;
import com.qxy.movierank.bean.RankVersionBean;

import java.util.ArrayList;
import java.util.List;

public class RankVersionListViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<RankVersionBean.DataDTO.ListDTO> mRankVersion_List = new ArrayList<>();

    public RankVersionListViewAdapter(Context context) {
        super();
        mContext = context;
    }

    public void setData(List<RankVersionBean.DataDTO.ListDTO> rankVersion_List) {
        if (mRankVersion_List != null) {
            mRankVersion_List.clear();
        }
        mRankVersion_List.addAll(rankVersion_List);
        notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        if (mRankVersion_List != null) {
            return mRankVersion_List.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return mRankVersion_List.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view_item = LayoutInflater.from(mContext).inflate(R.layout.rank_version_item, null);

        TextView versionInfoRankVersionItem = (TextView) view_item.findViewById(R.id.versionInfo_RankVersionItem);
        int version = mRankVersion_List.get(i).getVersion();
        String start_time = mRankVersion_List.get(i).getStart_time();
        String end_time = mRankVersion_List.get(i).getEnd_time();
        versionInfoRankVersionItem.setText("第"+version+"期 "+start_time+"~"+end_time);

        return view_item;
    }

}
