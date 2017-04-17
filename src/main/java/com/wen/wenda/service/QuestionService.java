package com.wen.wenda.service;

import com.sun.deploy.net.HttpUtils;
import com.wen.wenda.dao.QuestionDao;
import com.wen.wenda.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * Created by wen on 2017/4/10.
 */
@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao;

    @Autowired
    SensitiveService sensitiveService;

    public List<Question> getLatesQuestions(int userId,int offset,int limit){
        return questionDao.selectLatesQuestions(userId,offset,limit);
    }

    public Question getQuestionById(int questionId){
        return questionDao.selectQuestionById(questionId);
    }


    public int addQuestion(Question question){

        //html脚本敏感词过滤
        question.setContent(HtmlUtils.htmlEscape(question.getContent()));
        question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));

        //敏感词过滤，比如”强奸“
        question.setTitle(sensitiveService.sensitiveFilter(question.getTitle()));
        question.setContent(sensitiveService.sensitiveFilter(question.getContent()));

        return questionDao.addQuestion(question)>0 ? question.getUserId():0;
    }

    public int updateQuestionCommentCount(int questionId,int commentCount){
        return questionDao.updateQuetionComment(questionId,commentCount);
    }
}
