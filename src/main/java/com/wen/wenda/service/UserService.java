package com.wen.wenda.service;

import com.wen.wenda.dao.LoginTicketDao;
import com.wen.wenda.dao.UserDao;
import com.wen.wenda.model.LoginTicket;
import com.wen.wenda.model.User;
import com.wen.wenda.utils.WendaUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.security.provider.MD5;

import java.util.*;

/**
 * Created by wen on 2017/4/10.
 */
@Service
public class UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    LoginTicketDao loginTicketDao;

    public User getUser(int id){
        return userDao.selectById(id);
    }

    public User getUserByName(String name){return userDao.selectByName(name);}

    //注册新用户
    public Map<String,String> register(String name, String password){
        User user=userDao.selectByName(name);
        Map<String,String> map=new HashMap<>();
       // System.out.println("用户名："+name+"  密码: "+password);
        if(StringUtils.isBlank(name)){
            map.put("msg","用户名不能为空");
            return map;
        }
        if(StringUtils.isBlank(password)){
            map.put("msg","密码不能为空");
            return map;
        }
        if(user!=null){
            map.put("msg","用户名已存在");
            return map;
        }

        user=new User();
        user.setName(name);
        user.setSalt(UUID.randomUUID().toString().substring(0,5));
        user.setPassword(WendaUtil.MD5(password+user.getSalt()));
        user.setHeaderUrl("http://images.nowcoder.com/head/"+new Random().nextInt(1000)+"t.png");
        userDao.addUser(user);

        //注册成功时为相应用户添加ticket
        String ticket=addLoginTicket(user.getId());
        map.put("ticket",ticket);
        return map;
    }


    public Map<String,String> login(String name, String password){
        User user=userDao.selectByName(name);
        Map<String,String> map=new HashMap<>();
       // System.out.println("用户名："+name+"  密码: "+password);
        if(StringUtils.isBlank(name)){
            map.put("msg","用户名不能为空");
            return map;
        }
        if(StringUtils.isBlank(password)){
            map.put("msg","密码不能为空");
            return map;
        }
        if(user==null){
            map.put("msg","用户名不存在");
            return map;
        }

        if(!(WendaUtil.MD5(password+user.getSalt()).equals(user.getPassword())))
        {
            map.put("msg","密码错误");
            return map;
        }

        //添加用户对应的ticket
        String ticket=addLoginTicket(user.getId());
        map.put("ticket",ticket);

        return map;
    }

    //添加用户对应的ticket
    public String addLoginTicket(int userId){
        LoginTicket loginTicket=new LoginTicket();
        loginTicket.setUserId(userId);
        loginTicket.setStatus(0);
        Date now=new Date();
        now.setTime(now.getTime()+3600*24*10*1000);
        loginTicket.setExpired(now);
        loginTicket.setTicket(UUID.randomUUID().toString().replace("-",""));
         loginTicketDao.addLoginTicket(loginTicket);
         return loginTicket.getTicket();
    }

    //退出登录
    public void logout(String ticket){
        loginTicketDao.updateStatus(ticket,1);
    }

}
