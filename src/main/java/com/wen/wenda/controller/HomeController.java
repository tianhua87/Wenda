package com.wen.wenda.controller;

import com.wen.wenda.dao.QuestionDao;
import com.wen.wenda.model.Question;
import com.wen.wenda.model.ViewObject;
import com.wen.wenda.service.QuestionService;
import com.wen.wenda.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wen on 2017/4/10.
 */
@Controller
public class HomeController {

    @Autowired
    QuestionService questionService;
    @Autowired
    UserService userService;

    @RequestMapping(path = {"/","/index"},method = {RequestMethod.GET})
    public String index(Model model){

        List<Question> questions=questionService.getLatesQuestions(0,0,10);
        List<ViewObject> vos=new ArrayList<ViewObject>() ;
        for(Question question:questions) {
            ViewObject vo = new ViewObject();
            vo.set("question", question);
            vo.set("user",userService.getUser(question.getUserId()));
            vos.add(vo);
        }
        model.addAttribute("vos",vos);

        return "index";
    }

    @RequestMapping(path={"/user/{userId}"})
    public String userQuestion(@PathVariable("userId") int userId, Model model){

        List<Question> questions=questionService.getLatesQuestions(userId,0,10);
        List<ViewObject> vos=new ArrayList<ViewObject>() ;
        for(Question question:questions) {
            ViewObject vo = new ViewObject();
            vo.set("question", question);
            vo.set("user",userService.getUser(question.getUserId()));
            vos.add(vo);
        }
        model.addAttribute("vos",vos);

        return "index";
    }
}
