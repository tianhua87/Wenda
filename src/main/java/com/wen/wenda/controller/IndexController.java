package com.wen.wenda.controller;

import com.wen.wenda.model.User;
import com.wen.wenda.service.WendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by wen on 2017/4/9.
 *
 * 网站首页
 */
@Controller
public class IndexController {

    @Autowired
    WendaService wendaService;

    @RequestMapping(path = {"/","/index"})
    @ResponseBody
    public String index(HttpSession session){


            return "问答网站首页"+session.getAttribute("baba")+"<br>"+
                    wendaService.getMessage("首页");
    }

    @RequestMapping(path = {"profile/{userId}"})
    @ResponseBody
    public String profile(@PathVariable("userId") int userId,
                          @RequestParam(value="type",defaultValue = "wen") String type,
                          @RequestParam(value = "key",defaultValue = "jibin") String key){
        String response="";
        response=response+"用户ID："+userId+"\r\n";
        response+="type:"+type;
        response+=" key:"+key;

        return response;
    }

    @RequestMapping(path = {"/vm"},method = {RequestMethod.GET})
    public String template(Model model){
        model.addAttribute("name","wenjibin");
        List<String> colors= Arrays.asList(new String[]{"RED","BLUE","GREEN"});
        model.addAttribute("colors",colors);

        model.addAttribute("user",new User("温吉斌"));

        Map <String,String> map=new HashMap<>();
        for(int i=0;i<4;i++)
            map.put(i+"",i*i+"");
        model.addAttribute("number",map);

        return "home";
    }

    //请求
    @RequestMapping(path = {"/request"},method = {RequestMethod.GET})
    @ResponseBody
    public String request(Model model , HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        StringBuilder sb=new StringBuilder();
        Enumeration<String> headerNames=httpServletRequest.getHeaderNames();
        while(headerNames.hasMoreElements()){
            String name=headerNames.nextElement();
            sb.append(name+": "+httpServletRequest.getHeader(name)+"<br>");
        }

        sb.append(httpServletRequest.getMethod()+"<br>");
        sb.append(httpServletRequest.getAuthType()+"<br>");
        sb.append(httpServletRequest.getRemoteHost()+"<br>");

        httpServletResponse.addHeader("name:","温吉斌");

        return sb.toString();
    }

    @RequestMapping(path = {"/redirect/{code}"})
    public RedirectView redirect(@PathVariable("code") int code, HttpSession session){
        RedirectView red=new RedirectView("/",true);
        session.setAttribute("baba","no problem is dady");
        if(code==301){
            red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        }
        return red;
    }

    @RequestMapping(path = {"/admin"},method = {RequestMethod.GET})
    @ResponseBody
    public String admin(@RequestParam("key") String key){

        if("admin".equals(key))
            return "后台管理";
        throw new IllegalArgumentException("参数不对");

    }

    //处理异常
    @ExceptionHandler
    @ResponseBody
    public String error(Exception e){
        return e.getMessage();
    }
}
