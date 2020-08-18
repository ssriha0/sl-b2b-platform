package com.servicelive.bus.event.order;

import java.util.Calendar;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.servicelive.bus.event.EventHeader;
import com.servicelive.bus.event.ServiceLiveEvent;

/**
 * User: Mustafa Motiwala
 * Date: Mar 26, 2010
 * Time: 1:03:43 PM
 */
@XmlRootElement(name="ServiceOrderEvent")
@XmlAccessorType(XmlAccessType.FIELD)
public final class ServiceOrderEvent extends ServiceLiveEvent{

    /** generated serialVersionUID */
	private static final long serialVersionUID = 701861880269327412L;
	
	@XmlTransient
    public static final String ORDER_EVENT="ORDER_EVENT";
	
	@XmlTransient
    public static final String ORDER_CREATION_TYPE_NEW = "NEW";

	@XmlTransient
    public static final String ORDER_CREATION_TYPE_REOPEN = "REOPEN";

    /**
     * Default constructor for JAXB.
     */
    protected ServiceOrderEvent() { }

    /**
     * Creates a new ServiceOrderEvent. The type hierarchy allows you to create
     * more specialized Events which represent a more specific event in the
     * ServiceOrder life cycle.
     * @param soId - the id for the service order which triggered this event.
     * @param type - The specific type of the order event.
     * @param source - As a loose contract between publisher and
     * subscriber it is recommended that the source always be of type ServiceOrder.
     * However this is not enforced by the bus.
     */
    public ServiceOrderEvent(String soId, Object source, OrderEventType type) {
        super(source, Calendar.getInstance());
        addHeader(EventHeader.SERVICE_ORDER_ID, soId);
        addHeader(EventHeader.EVENT_TYPE, this.getClass().getName());
        eventHeader.put(ORDER_EVENT,type.name());
    }

    public ServiceOrderEvent(String soId, Object source) {
        super(source, Calendar.getInstance());
        addHeader(EventHeader.SERVICE_ORDER_ID, soId);
        addHeader(EventHeader.EVENT_TYPE, this.getClass().getName());
    }

    public void setEventType(OrderEventType type){
        eventHeader.put(ORDER_EVENT,type.name());
    }
    
    @Override
    public String toString() {
    	return ToStringBuilder.reflectionToString(this);
    }
}
