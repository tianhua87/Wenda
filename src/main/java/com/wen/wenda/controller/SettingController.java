package com.wen.wenda.controller;

import com.wen.wenda.service.WendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by wen on 2017/4/10.
 */
@Controller
public class SettingController {

    @Autowired
    WendaService wendaService;

    @RequestMapping(path = {"/setting"},method = {RequestMethod.GET})
    @ResponseBody
    public String setting(){
        return "设置"+"<br>"+wendaService.getMessage("设置");
    }
}
