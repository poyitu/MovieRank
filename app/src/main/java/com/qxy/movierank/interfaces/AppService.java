package com.qxy.movierank.interfaces;

import com.qxy.movierank.bean.ClientTokenBean;
import com.qxy.movierank.bean.RankBean;
import com.qxy.movierank.bean.RankVersionBean;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface AppService {

    /**
     *生成client_token
     * @param map
     * client_key   应用唯一标识
     * client_secret    应用唯一标识对应的密钥
     * grant_type   传client_credential
     * @return
     */
    @FormUrlEncoded
    @POST("oauth/client_token/")
    Call<ClientTokenBean> getClientToken(@FieldMap Map<String,String> map);

    /**
     *获取抖音电影榜、抖音电视剧榜、抖音综艺榜
     * @param client_access_token  不需要用户授权
     * @param type  榜单类型： * 1 - 电影 * 2 - 电视剧 * 3 - 综艺
     * @param version   榜单版本：空值默认为本周榜单
     * @return
     */
    @GET("discovery/ent/rank/item/")
    Call<RankBean> getRankData(@Header("access-token")String client_access_token, @Query("type") String type, @Query("version") String version);

    @GET("Rank2.json")
    Call<RankBean> getRankData_Tomcat();

    @GET("RankVersion2.json")
    Call<RankVersionBean> getRankVersionData_Tomcat();

    /**
     *获取抖音影视综榜单版本
     * @param client_access_token   不需要用户授权
     * @param cursor    分页游标, 第一页请求cursor是0, response中会返回下一页请求用到的cursor, 同时response还会返回has_more来表明是否有更多的数据。
     * @param count 每页数量
     * @param type  榜单类型： * 1 - 电影 * 2 - 电视剧 * 3 - 综艺
     * @return
     */
    @GET("discovery/ent/rank/version/")
    Call<RankVersionBean> getRankVersionData(@Header("access-token")String client_access_token, @Query("cursor") String cursor, @Query("count") String count, @Query("type") String type);





}
