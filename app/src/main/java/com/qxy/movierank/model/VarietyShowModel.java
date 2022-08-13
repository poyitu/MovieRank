package com.qxy.movierank.model;

import android.util.Log;

import com.qxy.movierank.bean.RankBean;
import com.qxy.movierank.contracts.VarietyShowContract;
import com.qxy.movierank.interfaces.InfoCallBack;
import com.qxy.movierank.utils.RetrofitUtil;

public class VarietyShowModel implements VarietyShowContract.Model {
    @Override
    public void getVarietyRankData(InfoCallBack infoCallBack) {
        RetrofitUtil.getInstance().getRank_Tomcat("3", "", new RetrofitUtil.CallBack() {
            @Override
            public void onSuccess(Object obj) {
                infoCallBack.resultSuccess(obj);
            }

            @Override
            public void onFailed(Throwable t) {
                infoCallBack.resultFail(t);
            }
        });

    }
}
