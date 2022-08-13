package com.qxy.movierank.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.qxy.movierank.bean.RankBean;
import com.qxy.movierank.contracts.VarietyShowContract;
import com.qxy.movierank.interfaces.InfoCallBack;
import com.qxy.movierank.model.VarietyShowModel;

import java.util.ArrayList;
import java.util.List;

public class VarietyShowViewModel extends ViewModel implements VarietyShowContract.ViewModel {

    private VarietyShowModel model;
    private MutableLiveData<List<RankBean.DataDTO.ListDTO>> mVarietyBeanList;

    public VarietyShowViewModel() {
        model = new VarietyShowModel();
        mVarietyBeanList = new MutableLiveData<>();
    }

    public LiveData<List<RankBean.DataDTO.ListDTO>> getmVarietyBeanList() {
        return mVarietyBeanList;
    }

    @Override
    public void loadVarietyRank() {
        model.getVarietyRankData(new InfoCallBack() {
            @Override
            public void resultSuccess(Object obj) {
                if(((RankBean)obj).getData().getError_code() == 0){
                    mVarietyBeanList.postValue(((RankBean) obj).getData().getList());
                }else {
                    Log.d("测试", "onSuccess: "+((RankBean)obj).getData().getDescription());
                }
            }

            @Override
            public void resultFail(Throwable t) {

            }
        });

    }
}
