package com.qxy.movierank.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.qxy.movierank.bean.ClientTokenBean;
import com.qxy.movierank.bean.RankBean;
import com.qxy.movierank.contracts.VarietyShowContract;
import com.qxy.movierank.interfaces.InfoCallBack;
import com.qxy.movierank.model.VarietyShowModel;
import com.qxy.movierank.utils.RetrofitUtil;
import com.qxy.movierank.utils.SaveLocal;

import java.util.List;

public class VarietyShowViewModel extends ViewModel implements VarietyShowContract.ViewModel {

    private VarietyShowModel model;
    private MutableLiveData<RankBean.DataDTO> mVarietyRankData;
    //client_access_token是否已刷新
    private MutableLiveData<Boolean> isClientTokenRefreshComplete;
    private SaveLocal mSaveLocal;

    public VarietyShowViewModel() {
        model = new VarietyShowModel();
        mVarietyRankData = new MutableLiveData<>();
        isClientTokenRefreshComplete = new MutableLiveData<>(false);
    }

    public MutableLiveData<RankBean.DataDTO> getmVarietyRankData() {
        return mVarietyRankData;
    }

    public MutableLiveData<Boolean> getIsClientTokenRefreshComplete() {
        return isClientTokenRefreshComplete;
    }

    @Override
    public void loadVarietyRank(String type,String version) {
        model.getVarietyRankData(type,version,new InfoCallBack() {
            private static final String ITEMNAME = "Variety";

            @Override
            public void resultSuccess(Object obj) {
                if(((RankBean)obj).getData().getError_code() == 0){
                    mVarietyRankData.postValue(((RankBean)obj).getData());
                    List<RankBean.DataDTO.ListDTO> list = ((RankBean) obj).getData().getList();
                    mSaveLocal.saveBean(list, ITEMNAME);
                } else if (((RankBean)obj).getData().getError_code() == 2190008){
                    /*
                    "description": "access_token过期,请刷新或重新授权", "error_code": 2190008
                    * */
                    //刷新client_access_token
                    model.getClient_Token(new InfoCallBack() {
                        @Override
                        public void resultSuccess(Object obj) {
                            //获取到Client_Token
                            if(((ClientTokenBean)obj).getData().getError_code() == 0){
                                RetrofitUtil.client_access_token = ((ClientTokenBean)obj).getData().getAccess_token();
                                isClientTokenRefreshComplete.postValue(true);
                            }
                        }

                        @Override
                        public void resultFail(Throwable t) {

                        }
                    });

                } else {
                    Log.d("测试VarietyShowViewModel", "onSuccess: "+((RankBean)obj).getData().getDescription());
                }
            }

            @Override
            public void resultFail(Throwable t) {

            }
        });

    }
}
