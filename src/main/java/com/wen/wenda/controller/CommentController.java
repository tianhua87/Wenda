package com.wen.wenda.controller;

import com.wen.wenda.model.Comment;
import com.wen.wenda.model.EntityType;
import com.wen.wenda.model.HostHolder;
import com.wen.wenda.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 * Created by wen on 2017/4/12.
 */
@Controller
public class CommentController {

    @Autowired
    CommentService commentService;
    @Autowired
    HostHolder hostHolder;

    @RequestMapping(path = {"/addComment"},method = RequestMethod.POST)
    public String addComment(@RequestParam("questionId") int questionId,
                             @RequestParam("content") String content){
        Comment comment=new Comment();
        comment.setContent(content);
        comment.setCreateDate(new Date());
        comment.setEntityId(questionId);
        comment.setEntityType(EntityType.QUESTION);
        if(hostHolder.getUser()!=null){
            comment.setUserId(hostHolder.getUser().getId());
            commentService.addComment(comment);
            return "redirect:/question/"+questionId;
        }else
        {
            return "redirect:/login";
        }
    }

}
