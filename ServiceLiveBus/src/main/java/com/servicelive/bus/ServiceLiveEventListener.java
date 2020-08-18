package com.servicelive.bus;

import java.util.EventListener;

import com.servicelive.bus.event.ServiceLiveEvent;

/**
 * User: Mustafa Motiwala
 * Date: Mar 27, 2010
 * Time: 5:10:34 PM
 */
public interface ServiceLiveEventListener extends EventListener{
    public void handleEvent(ServiceLiveEvent event);
}
