package com.wen.wenda.service;

import com.wen.wenda.dao.MessageDao;
import com.wen.wenda.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * Created by wen on 2017/4/17.
 */
@Service
public class MessageService {

    @Autowired
    MessageDao messageDao;

    @Autowired
    SensitiveService sensitiveService;

    public int addMessage(Message message){
        message.setContent(HtmlUtils.htmlEscape(message.getContent()));
        message.setContent(sensitiveService.sensitiveFilter(message.getContent()));
        int result=messageDao.addMessage(message);
        return result>0?result:0;
    }

    public List<Message> getConversationDetail(String conversationId,int offset,int limit){
        List<Message> conversations=messageDao.getConversationDetail(conversationId,offset,limit);
        return conversations;
    }

    public List<Message> getConversationList(int userId,int offset,int limit){
        List<Message> conversations=messageDao.getConversationList(userId,offset,limit);
        return conversations;
    }

    //获取未读消息数量
    public int getUnreadMessageCount(int userId,String conversationId){
        return messageDao.getUnreadMessageCount(userId,conversationId);
    }

    //把未读消息变为已读
    public int updateUnreadMessageStatus(String conversationId,int userId){
       return messageDao.updateUnreadMessageStatus(conversationId,userId);
    }

}
