package com.wen.wenda.interceptor;

import com.wen.wenda.dao.LoginTicketDao;
import com.wen.wenda.dao.UserDao;
import com.wen.wenda.model.HostHolder;
import com.wen.wenda.model.LoginTicket;
import com.wen.wenda.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by wen on 2017/4/11.
 */

@Component
public class PassportInterceptor implements HandlerInterceptor {

    @Autowired
    LoginTicketDao loginTicketDao;
    @Autowired
    UserDao userDao;
    @Autowired
    HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        Cookie cookies[]=httpServletRequest.getCookies();
        String ticket=null;
        if(cookies!=null)
            for(int i=0;i<cookies.length;i++){

                if(cookies[i].getName().equals("ticket")){
                    ticket=cookies[i].getValue();
                    break;
                }

            }

        if (ticket!=null){
            LoginTicket loginTicket=loginTicketDao.selectByTicket(ticket);
            //System.out.println(ticket);
            if(loginTicket==null || loginTicket.getExpired().before(new Date())|| loginTicket.getStatus()==1)
                return true;

            //从数据库中找到ticket对应的user，并加入到hostHoder中
            User user =userDao.selectById(loginTicket.getUserId());
            hostHolder.setUser(user);
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        if(modelAndView!=null)
        {
            modelAndView.addObject("user",hostHolder.getUser());
          //  System.out.println(hostHolder.getUser().getName());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
            hostHolder.clearUser();

    }
}
