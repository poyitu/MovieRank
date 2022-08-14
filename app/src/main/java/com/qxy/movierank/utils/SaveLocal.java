package com.qxy.movierank.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qxy.movierank.bean.RankBean;
import com.qxy.movierank.ui.MovieFragment;

import java.util.List;

/**
 * @author Poyitu
 */
public class SaveLocal {

    private Context mContext;
    private SharedPreferences sharedPreferences;
    private static SaveLocal instance;

    public SaveLocal(Context context) {
        mContext = context;
    }

    public static SaveLocal getInstance() {
        if (instance == null) {
            instance = new SaveLocal(MovieFragment.newInstance().getActivity());
        }
        return instance;

    }

    public void saveBean(List<RankBean.DataDTO.ListDTO> rankItem, String ItemName) {
        //保存实体类
        sharedPreferences = mContext.getSharedPreferences(ItemName, mContext.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(ItemName, new Gson().toJson(rankItem));
        edit.commit();
    }

    public List<RankBean.DataDTO.ListDTO> getBean(String ItemName) {
        SharedPreferences rankItem = mContext.getSharedPreferences(ItemName, 0);
        String rankJosn = rankItem.getString(ItemName, "");
        Gson gson = new Gson();
        List<RankBean.DataDTO.ListDTO> rankList = gson.fromJson(rankJosn, new TypeToken<List<RankBean.DataDTO.ListDTO>>() {
        }.getType());
        return rankList;
    }
}
