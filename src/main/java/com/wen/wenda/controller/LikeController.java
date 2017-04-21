package com.wen.wenda.controller;

import com.alibaba.fastjson.JSONObject;
import com.wen.wenda.async.EventModel;
import com.wen.wenda.async.EventType;
import com.wen.wenda.model.EntityType;
import com.wen.wenda.model.HostHolder;
import com.wen.wenda.model.User;
import com.wen.wenda.service.CommentService;
import com.wen.wenda.service.LikeService;
import com.wen.wenda.utils.JedisAdapter;
import com.wen.wenda.utils.RedisKeyUtil;
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
    @Autowired
    JedisAdapter jedisAdapter;
    @Autowired
    CommentService commentService;

    @RequestMapping(path = {"/like"},method = {RequestMethod.POST})
    @ResponseBody
    public String like(@RequestParam("commentId") int commentId){

        User user=hostHolder.getUser();
       // System.out.println(commentId);
        if(user==null){
            return WendaUtil.getJSONString(999,"用户未登录");
        }

        //点赞事件
        EventModel eventModel=new EventModel();
        eventModel.setEventType(EventType.LIKE).setActorId(user.getId()).setEntityId(commentId)
        .setEntityType(EntityType.COMMENT).setEntityOwnerId(commentService.getUserIdByCommentId(commentId));
        String key= RedisKeyUtil.getEventQueueKey();
        String jsonStr= JSONObject.toJSONString(eventModel);
        jedisAdapter.lpush(key,jsonStr);
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
