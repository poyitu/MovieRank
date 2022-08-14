package com.qxy.movierank.model;

import android.util.Log;

import com.qxy.movierank.bean.RankBean;
import com.qxy.movierank.contracts.VarietyShowContract;
import com.qxy.movierank.interfaces.InfoCallBack;
import com.qxy.movierank.utils.RetrofitUtil;

public class VarietyShowModel implements VarietyShowContract.Model {
    /**
     * 请求榜单数据
     * @param type
     * @param version
     * @param infoCallBack
     */
    @Override
    public void getVarietyRankData(String type,String version,InfoCallBack infoCallBack) {
        RetrofitUtil.getInstance().getRank(type, version, new RetrofitUtil.CallBack() {
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

    /**
     * 请求榜单版本数据
     * @param cursor
     * @param count
     * @param type
     * @param infoCallBack
     */
    @Override
    public void getVarietyRankVersionData(String cursor, String count, String type, InfoCallBack infoCallBack) {
        RetrofitUtil.getInstance().getRankVersion(cursor, count, type, new RetrofitUtil.CallBack() {
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

    /**
     * 请求client_access_token
     * @param infoCallBack
     */
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
