package com.wen.wenda.utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Created by wen on 2017/4/11.
 */
public class WendaUtil {

    /**
     * md5加密
     * @param str
     * @return
     */
    public static String MD5(String str) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    //获取JSON格式String
    public static String getJSONString(int code,String msg){
        JSONObject obj=new JSONObject();
        try {
            obj.put("code",code);
            obj.put("msg",msg);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj.toString();
    }

    public static String getJSONString(int code){
        JSONObject obj=new JSONObject();
        try {
            obj.put("code",code);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj.toString();
    }

}
