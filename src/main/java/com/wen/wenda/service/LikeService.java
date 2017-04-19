package com.wen.wenda.service;

import com.wen.wenda.utils.JedisAdapter;
import com.wen.wenda.utils.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wen on 2017/4/18.
 */
@Service
public class LikeService {
    @Autowired
    JedisAdapter jedisAdapter;


    //点赞
    public long like(int userId,String entityType,int entityId){
        String likeKey= RedisKeyUtil.getLikeKey(entityType,entityId);

        jedisAdapter.sadd(likeKey,userId+"");

        String dislikeKey= RedisKeyUtil.getDislikeKey(entityType,entityId);
        jedisAdapter.srem(dislikeKey,userId+"");

        return jedisAdapter.scard(likeKey);
    }


    //踩
    public long dislike(int userId,String entityType,int entityId){

        String dislikeKey= RedisKeyUtil.getDislikeKey(entityType,entityId);
        jedisAdapter.sadd(dislikeKey,userId+"");

        String likeKey= RedisKeyUtil.getLikeKey(entityType,entityId);
        jedisAdapter.srem(likeKey,userId+"");

        return jedisAdapter.scard(likeKey);
    }


    //判断是否喜欢
    public int getLikeStatus(int userId,String entityType,int entityId){
        String likeKey= RedisKeyUtil.getLikeKey(entityType,entityId);
        if(jedisAdapter.sismember(likeKey,userId+"")){
            return 1;
        }

        String dislikeKey= RedisKeyUtil.getDislikeKey(entityType,entityId);
        return jedisAdapter.sismember(dislikeKey,userId+"")?-1:0;
    }

    //获取喜欢的数量
    public long getLikeCount(String entityType,int entityId){
        String likeKey= RedisKeyUtil.getLikeKey(entityType,entityId);
        return jedisAdapter.scard(likeKey);
    }
}
