package com.qxy.movierank.contracts;

import android.content.Context;

import com.qxy.movierank.bean.RankBean;
import com.qxy.movierank.interfaces.InfoCallBack;

import java.util.List;

public interface VarietyShowContract {


    interface Model{
        /**
         *获取综艺榜数据
         * @param infoCallBack
         */
        void getVarietyRankData(String type,String version,InfoCallBack infoCallBack);

        /**
         * 获取综艺榜版本数据
         * @param cursor
         * @param count
         * @param type
         * @param infoCallBack
         */
        void getVarietyRankVersionData(String cursor,String count,String type,InfoCallBack infoCallBack);

        /**
         * 获取Client_Token   不需要用户授权的
         * @param infoCallBack
         */
        void getClient_Token(InfoCallBack infoCallBack);
    }


    interface View{
        /**
         * 展示综艺榜数据
         * @param varietyShowBeanList
         */
        void showVarietyRank(String active_time,List<RankBean.DataDTO.ListDTO> varietyShowBeanList);
    }

    interface ViewModel{
        /**
         * 加载综艺榜数据
         */
        void loadVarietyRank(Context context,String type, String version);

        /**
         * 加载综艺榜版本数据
         * @param cursor
         * @param count
         * @param type
         */
        void loadVarietyRankVersion(String cursor,String count,String type);

    }
}
