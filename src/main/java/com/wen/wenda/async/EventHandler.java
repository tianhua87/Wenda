package com.wen.wenda.async;

import java.util.List;

/**
 * Created by wen on 2017/4/21.
 */

public interface EventHandler {

    public void doHandler(EventModel eventModel);
    public List<String>  getSupportEventType();
}
