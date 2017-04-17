package com.wen.wenda.model;

import java.util.Date;

/**
 * Created by wen on 2017/4/17.
 */
public class Message {

    private int id;
    private int fromId;
    private int toId;
    private Date createDate;
    private int hasRead;
    private String conversationId;
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    public int getToId() {
        return toId;
    }

    public void setToId(int toId) {
        this.toId = toId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getHasRead() {
        return hasRead;
    }

    public void setHasRead(int hasRead) {
        this.hasRead = hasRead;
    }

    public String getConversationId() {
        if(fromId<toId){
            return fromId+"_"+toId;
        }else
            return toId+"_"+fromId;

    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }
    public void setConversationId() {
        if(fromId<toId){
            this.conversationId= fromId+"_"+toId;
        }else
            this.conversationId= toId+"_"+fromId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
