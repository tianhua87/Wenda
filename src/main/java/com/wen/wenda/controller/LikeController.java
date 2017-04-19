package com.wen.wenda.controller;

import com.wen.wenda.model.EntityType;
import com.wen.wenda.model.HostHolder;
import com.wen.wenda.model.User;
import com.wen.wenda.service.LikeService;
import com.wen.wenda.utils.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by wen on 2017/4/18.
 */
@Controller
public class LikeController {

    @Autowired
    LikeService likeService;
    @Autowired
    HostHolder hostHolder;

    @RequestMapping(path = {"/like"},method = {RequestMethod.POST})
    @ResponseBody
    public String like(@RequestParam("commentId") int commentId){

        User user=hostHolder.getUser();
       // System.out.println(commentId);
        if(user==null){
            return WendaUtil.getJSONString(999,"用户未登录");
        }

        long likeCount=likeService.like(user.getId(), EntityType.COMMENT,commentId);
        return WendaUtil.getJSONString(0,likeCount+"");
    }

    @RequestMapping(path = {"/dislike"},method = {RequestMethod.POST})
    @ResponseBody
    public String dislike(@RequestParam("commentId") int commentId){

        User user=hostHolder.getUser();

        if(user==null){
            return WendaUtil.getJSONString(999,"用户未登录");
        }

        long likeCount=likeService.dislike(user.getId(), EntityType.COMMENT,commentId);
        return WendaUtil.getJSONString(0,likeCount+"");
    }
}
