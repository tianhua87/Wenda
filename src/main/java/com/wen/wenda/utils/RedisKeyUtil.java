package com.wen.wenda.utils;

import org.omg.CORBA.PUBLIC_MEMBER;

/**
 * Created by wen on 2017/4/18.
 */
public class RedisKeyUtil {

    private static String SPLIT=":";
    private static String BIZ_LIKE="LIKE";
    private static String BIZ_DISLIKE="DISLIKE";


    public static String getLikeKey(String entityType,int entityId){
        return BIZ_LIKE+SPLIT+entityType+SPLIT+entityId;
    }

    public static String getDislikeKey(String entityType,int entityId){
        return BIZ_DISLIKE+SPLIT+entityType+SPLIT+entityId;
    }

}
