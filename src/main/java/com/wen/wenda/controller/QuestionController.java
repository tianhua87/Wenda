package com.wen.wenda.controller;

import com.wen.wenda.model.*;
import com.wen.wenda.service.CommentService;
import com.wen.wenda.service.LikeService;
import com.wen.wenda.service.QuestionService;
import com.wen.wenda.service.UserService;
import com.wen.wenda.utils.WendaUtil;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wen on 2017/4/12.
 */
@Controller
public class QuestionController {

    private final Logger logger= org.slf4j.LoggerFactory.getLogger(LoginController.class);


    @Autowired
    QuestionService questionService;
    @Autowired
    CommentService commentService;

    @Autowired
    UserService userService;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    LikeService likeService;

    //问题详情页
    @RequestMapping(value = {"/question/{qid}"},method = {RequestMethod.GET})
    public String questionDetail(@PathVariable("qid") int qid,
                                 Model model
    ){
        //System.out.println(qid);
        Question question=questionService.getQuestionById(qid);
        if(question!=null)
        {
            model.addAttribute("question",question);
            model.addAttribute("user",userService.getUser(question.getUserId()));
            List<Comment> comments=commentService.getCommentByEntity(qid, EntityType.QUESTION);
            List<ViewObject> vos=new ArrayList<ViewObject>() ;

            for(Comment comment:comments) {
                ViewObject vo = new ViewObject();
                vo.set("comment", comment);
                vo.set("user",userService.getUser(comment.getUserId()));
                long likeCount=likeService.getLikeCount(EntityType.COMMENT,comment.getId());
                int likeStatus=0;
                if(hostHolder.getUser()!=null) {
                    likeStatus = likeService.getLikeStatus(hostHolder.getUser().getId(),EntityType.COMMENT,comment.getId());
                }
                else{
                    likeStatus=0;
                }
                vo.set("liked",likeStatus);
                vo.set("likeCount",likeCount);

                vos.add(vo);
            }
            model.addAttribute("comments",vos);
        }else
            return "error404";
        return "detail1";
    }


    //发布问题
    @RequestMapping(value = {"/question/add"},method = {RequestMethod.POST})
    @ResponseBody
    public String addQuestion(@RequestParam("title") String title,
                              @RequestParam("content") String content){
        Question question=new Question();
        question.setTitle(title);
        question.setContent(content);
        question.setCreateDate(new Date());
        question.setCommentCount(0);
        System.out.println(title+" "+content);

        //如果用户未登陆，直接跳转到登录界面，前台收到code：999会跳转到登录界面
        if(hostHolder.getUser()==null)
        {
            System.out.println(WendaUtil.getJSONString(999));
            return WendaUtil.getJSONString(999);
        }else{
            System.out.println(WendaUtil.getJSONString(0));
            question.setUserId(hostHolder.getUser().getId());

            int result=questionService.addQuestion(question);
            if(result>0)
                return WendaUtil.getJSONString(0);
            else{
                logger.error("添加问题失败");
            }
            return WendaUtil.getJSONString(-1,"添加问题错误");
        }
    }
}
