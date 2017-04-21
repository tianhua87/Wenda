package com.wen.wenda.async;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wen on 2017/4/21.
 */
public class EventModel {

    private String eventType;
    private int actorId;
    private int entityId;
    private  String entityType;
    private int entityOwnerId;

    private Map<String,String> exts=new HashMap<String ,String >();

    public EventModel setExt(String key,String value){
        exts.put(key,value);
        return this;
    }
    public String getExt(String key){
        return exts.get(key);
    }

    public Map<String, String> getExts() {
        return exts;
    }

    public EventModel setExts(Map<String, String> exts) {
        this.exts = exts;
        return this;
    }

    public String getEventType() {
        return eventType;
    }

    public EventModel setEventType(String eventType) {
        this.eventType = eventType;
        return this;
    }

    public int getActorId() {
        return actorId;
    }

    public EventModel setActorId(int actorId) {
        this.actorId = actorId;
        return this;
    }

    public int getEntityId() {
        return entityId;
    }

    public EventModel setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }

    public String getEntityType() {
        return entityType;
    }

    public EventModel setEntityType(String entityType) {
        this.entityType = entityType;
        return this;
    }

    public int getEntityOwnerId() {
        return entityOwnerId;
    }

    public EventModel setEntityOwnerId(int entityOwnerId) {
        this.entityOwnerId = entityOwnerId;
        return this;
    }
}
