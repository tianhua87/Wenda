package com.wen.wenda.controller;

import com.wen.wenda.model.HostHolder;
import com.wen.wenda.model.Question;
import com.wen.wenda.service.QuestionService;
import com.wen.wenda.service.UserService;
import com.wen.wenda.utils.WendaUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.log4j.spi.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.jws.WebParam;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;


/**
 * Created by wen on 2017/4/11.
 */
@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    QuestionService questionService;

    private final Logger logger= org.slf4j.LoggerFactory.getLogger(LoginController.class);

    //注册
    @RequestMapping(path = {"/reg/"},method = {RequestMethod.POST})
    public String register(Model model,
                           @Param("username") String username,
                           @Param("password") String password,
                           HttpServletResponse response){

        Map<String, String> map=userService.register(username,password);
        if(map.containsKey("ticket")) {
            Cookie cookie=new Cookie("ticket",map.get("ticket"));
            cookie.setPath("/");
            response.addCookie(cookie);
            return "redirect:/";
        }
        else {
            model.addAttribute("msg",map.get("msg"));
        }
        return "login";
    }

    //登录界面
    @RequestMapping(path = {"/login"},method = {RequestMethod.GET})
    public String login(@RequestParam(value = "next", required=false) String next,
                        Model model){
        model.addAttribute("next",next);
        return "login";
    }

    //登录验证
    @RequestMapping(path = {"/loginCheck"},method = {RequestMethod.POST})
    public String loginCheck(Model model,
                             @Param("username") String username,
                             @Param("password") String password,
                             @RequestParam(value = "next", required=false) String next,
                             HttpServletResponse response
    ){
        Map<String,String> map=userService.login(username,password);
        if(map.containsKey("ticket")){
            Cookie cookie=new Cookie("ticket",map.get("ticket"));
            cookie.setPath("/");
            response.addCookie(cookie);
            System.out.println(next);
            if(StringUtils.isNotBlank(next))
                return "redirect:"+next;
            else
                return "redirect:/";

        }else
        {
            model.addAttribute("msg",map.get("msg"));
            return "login";
        }
    }

    //退出登录
    @RequestMapping(path = {"/logout"},method = {RequestMethod.GET})
    public String logout(@CookieValue("ticket") String ticket){
        userService.logout(ticket);

        return "redirect:/login";
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
                return WendaUtil.getJSONString(999);
            }

        }


    }

}
