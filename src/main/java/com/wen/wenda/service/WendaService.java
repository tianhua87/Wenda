package com.wen.wenda.service;

import org.springframework.stereotype.Service;

/**
 * Created by wen on 2017/4/10.
 */
@Service
public class WendaService {

    public String getMessage(String msg){
        return "WendaService msg: "+msg;
    }
}
