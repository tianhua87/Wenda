package com.wen.wenda.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wen on 2017/4/10.
 */
public class ViewObject {

    Map<String,Object> map=new HashMap<String,Object>();

    public void set(String key,Object obj){
        map.put(key,obj);
    }

    public Object get(String key){
        return map.get(key);
    }
}
