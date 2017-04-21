package com.wen.wenda.async;

import com.wen.wenda.utils.JedisAdapter;
import com.wen.wenda.utils.RedisKeyUtil;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wen on 2017/4/21.
 */
@Service
public class EventProducer {

    private static final Logger logger= LoggerFactory.getLogger(EventProducer.class);

    @Autowired
    JedisAdapter jedisAdapter;

    //发送事件
    public boolean fireEvent(EventModel eventModel){

        try{
            String jsonStr= com.alibaba.fastjson.JSONObject.toJSONString(eventModel);
            String key= RedisKeyUtil.getEventQueueKey();
            jedisAdapter.lpush(key,jsonStr);
            return true;

        }catch (Exception e){
            logger.error("事件发送失败 "+e.getMessage());
            return false;
        }

    }
}
