package com.qxy.movierank.utils;

import android.util.Log;

import com.qxy.movierank.bean.ClientTokenBean;
import com.qxy.movierank.bean.RankBean;
import com.qxy.movierank.bean.RankVersionBean;
import com.qxy.movierank.interfaces.AppService;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {

    private static RetrofitUtil retrofitUtil = null;
    private String baseUrl = "https://open.douyin.com/";
    private String client_key = "aw1o1jsnnkemujkv";
    private String client_secret = "d7f2c83a35dae47bd834bb8a2b5248ca";
    private String grant_type = "client_credential";

    public static String client_access_token = "clt.aad8291f8a66c49199c25a7b7dc90eb71WQ5AvRnd5ZyqHZ53BUnde88p72z";


    public static RetrofitUtil getInstance(){
        if(retrofitUtil == null){
            retrofitUtil = new RetrofitUtil();
        }
        return  retrofitUtil;
    }


    /**
     * 生成client_token
     * client_key   应用唯一标识
     * client_secret    应用唯一标识对应的密钥
     * grant_type   传client_credential
     * @param callBack
     */

    public void getClientToken(CallBack callBack){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        AppService appService = retrofit.create(AppService.class);
        Map<String,String> map= new HashMap<>();
        map.put("client_key",client_key);
        map.put("client_secret",client_secret);
        map.put("grant_type",grant_type);
        appService.getClientToken(map).enqueue(new Callback<ClientTokenBean>() {
            @Override
            public void onResponse(Call<ClientTokenBean> call, Response<ClientTokenBean> response) {
                Log.d("Retrofit测试", "收到响应");
                callBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ClientTokenBean> call, Throwable t) {
                Log.d("Retrofit测试", "发生错误");
                callBack.onFailed(t);
            }
        });

    }


    /**
     * 获取抖音电影榜、抖音电视剧榜、抖音综艺榜
     * @param type  榜单类型： * 1 - 电影 * 2 - 电视剧 * 3 - 综艺
     * @param version   榜单版本：空值默认为本周榜单
     * @param callBack
     */

    public void getRank(String type,String version,CallBack callBack){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        AppService appService = retrofit.create(AppService.class);
        appService.getRankData(client_access_token,type,version).enqueue(new Callback<RankBean>() {
            @Override
            public void onResponse(Call<RankBean> call, Response<RankBean> response) {
                Log.d("Retrofit测试", "收到响应");
                callBack.onSuccess(response.body());

            }

            @Override
            public void onFailure(Call<RankBean> call, Throwable t) {
                Log.d("Retrofit测试", "发生错误111");
                callBack.onFailed(t);
            }

        });
    }

    /**
     *获取抖音影视综榜单版本
     * @param cursor    分页游标, 第一页请求cursor是0, response中会返回下一页请求用到的cursor, 同时response还会返回has_more来表明是否有更多的数据。
     * @param count 每页数量
     * @param type  榜单类型： * 1 - 电影 * 2 - 电视剧 * 3 - 综艺
     * @param callBack
     */
    public void getRankVersion(String cursor,String count,String type,CallBack callBack){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        AppService appService = retrofit.create(AppService.class);
        appService.getRankVersionData(client_access_token,cursor,count,type).enqueue(new Callback<RankVersionBean>() {
            @Override
            public void onResponse(Call<RankVersionBean> call, Response<RankVersionBean> response) {
                Log.d("Retrofit测试", "收到响应");
                callBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<RankVersionBean> call, Throwable t) {
                Log.d("Retrofit测试", "发生错误");
                callBack.onFailed(t);
            }
        });

    }



    public interface CallBack {
        void onSuccess(Object o);
        void onFailed(Throwable t);
    }
}
