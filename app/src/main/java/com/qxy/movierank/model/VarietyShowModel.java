package com.qxy.movierank.model;

import android.util.Log;

import com.qxy.movierank.bean.RankBean;
import com.qxy.movierank.contracts.VarietyShowContract;
import com.qxy.movierank.interfaces.InfoCallBack;
import com.qxy.movierank.utils.RetrofitUtil;

public class VarietyShowModel implements VarietyShowContract.Model {
    @Override
    public void getVarietyRankData(String type,String version,InfoCallBack infoCallBack) {
        RetrofitUtil.getInstance().getRank_Tomcat(type, version, new RetrofitUtil.CallBack() {
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

    @Override
    public void getVarietyRankVersionData(String cursor, String count, String type, InfoCallBack infoCallBack) {
        RetrofitUtil.getInstance().getRankVersion_Tomcat(cursor, count, type, new RetrofitUtil.CallBack() {
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

    @Override
    public void getClient_Token(InfoCallBack infoCallBack) {
        RetrofitUtil.getInstance().getClientToken(new RetrofitUtil.CallBack() {
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
