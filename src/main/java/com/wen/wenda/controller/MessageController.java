package com.wen.wenda.controller;

import com.wen.wenda.model.HostHolder;
import com.wen.wenda.model.Message;
import com.wen.wenda.model.User;
import com.wen.wenda.model.ViewObject;
import com.wen.wenda.service.MessageService;
import com.wen.wenda.service.UserService;
import com.wen.wenda.utils.WendaUtil;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wen on 2017/4/17.
 */
@Controller
public class MessageController {

    private final Logger logger= LoggerFactory.getLogger(MessageController.class);

    @Autowired
    MessageService messageService;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    UserService userService;

    @RequestMapping(path={"/msg/addMessage"},method = {RequestMethod.POST})
    @ResponseBody
    public String addMessage(@RequestParam("toName") String toName,
                             @RequestParam("content") String content){
        try{
            User fromUser=hostHolder.getUser();
            if(fromUser==null){
                return WendaUtil.getJSONString(999,"未登录");
            }else {
                User toUser=userService.getUserByName(toName);
                if(toUser==null){
                    return WendaUtil.getJSONString(1,"用户不存在");

                }
                else{
                    Message message=new Message();
                    message.setContent(content);
                    message.setCreateDate(new Date());
                    message.setHasRead(0);
                    message.setFromId(fromUser.getId());
                    message.setToId(toUser.getId());
                    message.setConversationId();

                    messageService.addMessage(message);
                    return WendaUtil.getJSONString(0);
                }
            }

        }catch (Exception e){

            logger.error("发送消息失败 "+e.getMessage());
            return WendaUtil.getJSONString(1,"发送消息失败");

        }

    }

    @RequestMapping(path = {"/msg/list"},method = {RequestMethod.GET})
    public String getConversationList(Model model){
        User user=hostHolder.getUser();
        if(user!=null){
            List<Message> messageList = messageService.getConversationList(user.getId(), 0, 10);
            List<ViewObject> conversations = new ArrayList<>();
            int localId=user.getId();
            if (messageList != null) {
                for(Message message:messageList){
                    ViewObject vo=new ViewObject();
                    vo.set("message",message);
                    int targetId=message.getFromId()==localId?message.getToId():message.getFromId();
                    vo.set("user",userService.getUser(targetId));
                    vo.set("unread",messageService.getUnreadMessageCount(localId,message.getConversationId()));
                    conversations.add(vo);
                }
                model.addAttribute("conversations",conversations);
            }
        }
        return "letter";
    }

    @RequestMapping(path = {"/msg/detail"},method = {RequestMethod.GET})
    public String getConversationDetail(@RequestParam("conversationId") String conversationId,
                                        Model model){
        try {
            List<Message> messageList = messageService.getConversationDetail(conversationId, 0, 10);
            List<ViewObject> messages = new ArrayList<>();
            User me=hostHolder.getUser();
            if(me!=null) {
                if (messageList != null) {
                    for (Message message : messageList) {
                        ViewObject vo = new ViewObject();
                        vo.set("message", message);
                        vo.set("user", userService.getUser(message.getFromId()));
                        messages.add(vo);
                        messageService.updateUnreadMessageStatus(message.getConversationId(),me.getId());
                    }
                    model.addAttribute("messages", messages);
                }
            }
        }catch (Exception e){
            logger.error("获取详情失败"+" "+e.getMessage());
        }

        return "letterDetail";
    }
}
