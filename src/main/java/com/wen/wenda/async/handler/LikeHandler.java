package com.wen.wenda.async.handler;

import com.wen.wenda.async.EventHandler;
import com.wen.wenda.async.EventModel;
import com.wen.wenda.async.EventType;
import com.wen.wenda.model.Comment;
import com.wen.wenda.model.Message;
import com.wen.wenda.service.CommentService;
import com.wen.wenda.service.MessageService;
import com.wen.wenda.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by wen on 2017/4/21.
 */
@Component
public class LikeHandler implements EventHandler{
    @Autowired
    MessageService messageService;
    @Autowired
    UserService userService;
    @Autowired
    CommentService commentService;


    @Override
    public void doHandler(EventModel eventModel) {
        Message message=new Message();
        message.setToId(eventModel.getEntityOwnerId());
        message.setFromId(eventModel.getActorId());
        message.setConversationId();
        message.setHasRead(0);
        message.setCreateDate(new Date());
        message.setContent(userService.getUser(eventModel.getActorId()).getName()+" 赞了你的评论 "+
                "http://localhost:8080/question/"+commentService.getQuestionIdByCommentId(eventModel.getEntityId()));
        messageService.addMessage(message);
    }

    @Override
    public List<String> getSupportEventType() {
        return Arrays.asList(EventType.LIKE);
    }
}
