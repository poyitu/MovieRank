package com.qxy.movierank.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qxy.movierank.bean.RankBean;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonParse {
    private static JsonParse instance;

    private JsonParse() {
    }

    public static JsonParse getInstance() {
        if (instance == null) {
            instance = new JsonParse();
        }
        return instance;
    }

    /**
     * Use the gson library to parse JSON data
     */
    public ArrayList<RankBean> getShopList(String json) {
        Gson gson = new Gson();
        //Create an anonymous subclass object of TypeToken and call the getType() method of the object
        Type listType = new TypeToken<List<RankBean>>() {
        }.getType();

        // Save the collected information in the shopList
        ArrayList<RankBean> rankBeans = gson.fromJson(json, listType);
        return rankBeans;
    }

    //根据泛型返回解析制定的类型
    public  <T> T fromToJson(String json,Type listType){
        Gson gson = new Gson();
        T t = null;
        t = gson.fromJson(json,listType);
        return t;
    }
}
