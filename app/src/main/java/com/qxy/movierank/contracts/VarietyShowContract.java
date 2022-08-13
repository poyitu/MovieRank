package com.qxy.movierank.contracts;

import com.qxy.movierank.bean.RankBean;
import com.qxy.movierank.interfaces.InfoCallBack;

import java.util.List;

public interface VarietyShowContract {


    interface Model{
        /**
         *获取综艺榜数据
         * @param infoCallBack
         */
        void getVarietyRankData(InfoCallBack infoCallBack);
    }


    interface View{
        /**
         * 展示综艺榜数据
         * @param varietyShowBeanList
         */
        void showVarietyRank(List<RankBean.DataDTO.ListDTO> varietyShowBeanList);
    }

    interface ViewModel{
        /**
         * 加载综艺榜数据
         */
        void loadVarietyRank();
    }
}
