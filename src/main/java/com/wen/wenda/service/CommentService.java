package com.wen.wenda.service;

import com.wen.wenda.dao.CommentDao;
import com.wen.wenda.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * Created by wen on 2017/4/12.
 */
@Service
public class CommentService {

    @Autowired
    CommentDao commentDao;
    @Autowired
    SensitiveService sensitiveService;

    @Autowired
    QuestionService questionService;

    public int addComment(Comment comment){
        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
        comment.setContent(sensitiveService.sensitiveFilter(comment.getContent()));

        int result=commentDao.addComment(comment);
        int count=getCommentCount(comment.getEntityId(),comment.getEntityType());
        if(result>0)
        {
            questionService.updateQuestionCommentCount(comment.getEntityId(),count);
        }

        return result>0?result:0;
    }

    public List<Comment> getCommentByEntity(int entityId,String entityType){
        return commentDao.selectByEntity(entityId,entityType);
    }

    public Comment getCommentById(int id){
        return commentDao.selectById(id);
    }

    public int deleteComment(int id){
        return commentDao.updateStatus(0,id);
    }

    public int getCommentCount(int entityId,String entityType){
        return commentDao.getCommentCount(entityId,entityType);
    }

    public int getUserIdByCommentId(int commentId){
        return commentDao.getUserIdByCommentId(commentId);
    }
    public int getQuestionIdByCommentId(int commentId){
        return commentDao.getQuestionIdByCommentId(commentId);
    }

}
