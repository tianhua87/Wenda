package com.wen.wenda.model;

import org.springframework.stereotype.Component;

/**
 * Created by wen on 2017/4/11.
 */
@Component
public class HostHolder {

    private static ThreadLocal<User> users=new ThreadLocal<>();

    public User getUser(){
        return users.get();
    }

    public void setUser(User user){
        users.set(user);
    }

    public void clearUser(){
        users.remove();
    }
}
