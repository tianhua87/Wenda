package com.wen.wenda.async;

import com.alibaba.fastjson.JSONObject;
import com.wen.wenda.utils.JedisAdapter;
import com.wen.wenda.utils.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wen on 2017/4/21.
 */
@Service
public class EventConsumer implements InitializingBean,ApplicationContextAware{

    private Map<String, List<EventHandler>> config=new HashMap<>();
    private ApplicationContext applicationContext;
    private static final Logger logger= LoggerFactory.getLogger(EventConsumer.class);

    @Autowired
    JedisAdapter jedisAdapter;

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String ,EventHandler> beans=applicationContext.getBeansOfType(EventHandler.class);
        if(beans!=null){
            for(Map.Entry<String,EventHandler> entry:beans.entrySet()){
                List<String> eventTypes=entry.getValue().getSupportEventType();
                for(String eventType:eventTypes){
                    if(!config.containsKey(eventType))
                    {
                        config.put(eventType,new ArrayList<EventHandler>());
                    }
                    config.get(eventType).add(entry.getValue());
                }
            }
        }
        //开启线程不断的从事件队列中读取事件，并让相应的EventHandler去处理事件
        Thread thread=new Thread(){
            @Override
            public void run(){
                while(true){
                    String key= RedisKeyUtil.getEventQueueKey();
                    List<String> events=jedisAdapter.brpop(0,key);
                    for(String eve:events){
                        if(eve.equals(key)){
                            continue;
                        }
                        EventModel eventModel= JSONObject.parseObject(eve,EventModel.class);

                        if(!config.containsKey(eventModel.getEventType())){
                            logger.error("不能识别的事件");
                        }else{
                            for(EventHandler eventHandler:config.get(eventModel.getEventType())){
                                eventHandler.doHandler(eventModel);
                            }
                        }

                    }
                }
            }
        };
        thread.start();

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }
}
