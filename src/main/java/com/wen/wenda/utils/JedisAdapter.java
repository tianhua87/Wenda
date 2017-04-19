package com.wen.wenda.utils;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by wen on 2017/4/18.
 */
@Service
public class JedisAdapter implements InitializingBean {

    private final Logger logger= LoggerFactory.getLogger(JedisAdapter.class);

    private JedisPool pool;

    @Override
    public void afterPropertiesSet() throws Exception {

//        GenericObjectPoolConfig config=new GenericObjectPoolConfig();
//        config.setMaxIdle(100);
//        config.setMaxTotal(100);

        pool=new JedisPool("redis://localhost:6379/10");

    }

    //向集合中添加元素
    public long sadd(String key,String value){
        Jedis jedis=null;
        try{
            jedis=pool.getResource();
            return jedis.sadd(key,value);

        }catch (Exception e){
            logger.error("redis集合添加失败 "+e.getMessage());
        }finally {
            if(jedis!=null)
                jedis.close();
        }
        return 0;
    }

    //向集合中添加元素
    public long srem(String key,String value){
        Jedis jedis=null;
        try{
            jedis=pool.getResource();
            return jedis.srem(key,value);

        }catch (Exception e){
            logger.error("redis集合remove失败 "+e.getMessage());
        }finally {
            if(jedis!=null)
                jedis.close();
        }
        return 0;
    }


    //获取集合key对应的value数量
    public long scard(String key){
        Jedis jedis=null;
        try{
            jedis=pool.getResource();
            return jedis.scard(key);

        }catch (Exception e){
            logger.error("redis异常"+e.getMessage());
        }finally {
            if(jedis!=null)
                jedis.close();
        }
        return 0;
    }

    //获取集合key对应的value数量
    public boolean sismember(String key,String value){
        Jedis jedis=null;
        try{
            jedis=pool.getResource();
            return jedis.sismember(key,value);

        }catch (Exception e){
            logger.error("redis异常"+e.getMessage());
        }finally {
            if(jedis!=null)
                jedis.close();
        }
        return false;
    }
}
