package com.servicelive.bus;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.ConnectionFactory;
import javax.jms.Topic;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.transaction.PlatformTransactionManager;

import com.servicelive.bus.event.log.IServiceLiveBusEventLogBO;

/**
 * User: Mustafa Motiwala
 * Date: Mar 25, 2010
 * Time: 11:43:13 AM
 */
public class EventAdapterFactory implements FactoryBean, InitializingBean {

    private static final boolean DEFAULT_SUBSCRIBERS_ENABLED = true;

    private static final boolean DEFAULT_SUBSCRIBER_SESSION_TRANSACTED = true;

	private static final boolean DEFAULT_PUBLISHER_SESSION_TRANSACTED = true;

	private static final boolean DEFAULT_DURABILITY = false;

    private static Log log = LogFactory.getLog(EventAdapterFactory.class);
    
	// Cache of JMS subscribers that are created by this class.
	// It is used to avoid duplicate subscriptions, in case the spring
	// configuration is reloaded
    private static Map<String, Boolean> createdSubscribersMap = new HashMap<String, Boolean>();
    
    private MessageSelector messageSelector;
    private String clientId;
    private ConnectionFactory jmsConnectionFactory;
    private Topic jmsTopic;
    private EventAdapter delegate;
    private PlatformTransactionManager txManager;
    private List<Class<?>> classes = Collections.emptyList();
    private List<ServiceLiveEventListener> eventListenerList = Collections.emptyList();
    private List<String> customEventHeaders = Collections.emptyList();
    private IServiceLiveBusEventLogBO serviceLiveBusEventLogBO;

    private boolean durable = DEFAULT_DURABILITY;
    private boolean subscribersEnabled = DEFAULT_SUBSCRIBERS_ENABLED;

	private boolean publisherSessionTransacted = DEFAULT_PUBLISHER_SESSION_TRANSACTED;
	private boolean subscriberSessionTransacted = DEFAULT_SUBSCRIBER_SESSION_TRANSACTED;

    public void afterPropertiesSet() throws Exception {
        log.debug("Begin Method>");
        JmsTemplate jmsTemplate = createPublisher();
        logPropertiesDetails();
		delegate = new EventAdapter(this.clientId, jmsTemplate,
				this.eventListenerList, this.classes, this.customEventHeaders);
		delegate.setServiceLiveBusEventLogBO(this.serviceLiveBusEventLogBO);
		if (this.eventListenerList != null && !this.eventListenerList.isEmpty()) {
			createSubscriber();
		}
        log.debug("<End Method");
    }

    private JmsTemplate createPublisher() {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(jmsConnectionFactory);
        jmsTemplate.setDefaultDestination(jmsTopic);
        jmsTemplate.setSessionTransacted(publisherSessionTransacted);
        jmsTemplate.afterPropertiesSet();
        return jmsTemplate;
			}

	private void createSubscriber() {
    	if (!subscribersEnabled) return;
    	
    	if (!createdSubscribersMap.containsKey(clientId) || !createdSubscribersMap.get(clientId)) {
	        DefaultMessageListenerContainer jmsContainer = new DefaultMessageListenerContainer();
	        jmsContainer.setConnectionFactory(jmsConnectionFactory);
	        jmsContainer.setDestination(jmsTopic);
			// TODO setting the clientId is resulting in a Websphere MQ error:
			// Can't set ClientID after connection has been used.
			// This is due to JmsTemplate's reliance on pooled connections
			// jmsContainer.setClientId(clientId);
	        jmsContainer.setSubscriptionDurable(isDurable());
			if (null != messageSelector) {
				jmsContainer.setMessageSelector(messageSelector.buildSelector());
			}
			jmsContainer.setSessionTransacted(subscriberSessionTransacted);
	        jmsContainer.setMessageListener(delegate);
	        jmsContainer.setTransactionManager(txManager);
	        jmsContainer.afterPropertiesSet();
	        createdSubscribersMap.put(clientId, true);
    	}
    }

	public List<Class<?>> getClasses() {
		return Collections.unmodifiableList(this.classes);
    }

    public Object getObject() throws Exception {
        return delegate;
    }

    public Class<EventAdapter> getObjectType() {
        return EventAdapter.class;
    }

    public boolean isDurable() {
        return durable;
    }

    /**
     * The bean instance returned from the factory is never a singleton.
     * @return Always returns true as EventAdapter instance should be one per context.
     */
    public boolean isSingleton() {
        return true;
    }

    private void logPropertiesDetails() {
    	if (!log.isDebugEnabled()) return;

		if (this.eventListenerList != null) {
			log.debug(String.format("found %d entries in eventListenerList", this.eventListenerList.size()));
			for (ServiceLiveEventListener listener : this.eventListenerList) {
				log.debug(String.format("found eventListenerList with class name '%s' and hashCode %d.",
						listener.getClass().getName(), listener.hashCode()));
    }
		}
		else {
			log.debug("eventListenerList is null");
		}
		
		if (this.classes != null) {
			log.debug(String.format("found %d entries in classes", this.classes.size()));
			for (Class<?> clazz : this.classes) {
				log.debug(String.format("found classes entry '%s'.", clazz.getName()));
			}
		}
		else {
			log.debug("classes is null");
		}

		if (this.customEventHeaders != null) {
			log.debug(String.format("found %d entries in customEventHeaders", this.customEventHeaders.size()));
			for (String entry : this.customEventHeaders) {
				log.debug(String.format("found customEventHeaders entry '%s'.", entry));
    }
		}
		else {
			log.debug("customEventHeaders is null");
		}
	}

    public void setClasses(List<Class<?>> classes) {
		this.classes = classes;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setCustomEventHeaders(List<String> customEventHeaders) {
		this.customEventHeaders = customEventHeaders;
    }

    public void setDurable(boolean durable) {
        this.durable = durable;
    }

    public void setEventHandlerList(List<ServiceLiveEventListener> list) {
        this.eventListenerList = list;
    }

    public void setJmsConnectionFactory(ConnectionFactory jmsConnectionFactory) {
        this.jmsConnectionFactory = jmsConnectionFactory;
	}

	public void setJmsTopic(Topic jmsTopic) {
        this.jmsTopic = jmsTopic;
    }
	
	public void setMessageSelector(MessageSelector messageSelector) {
        this.messageSelector = messageSelector;
	}
	

	/**
	 * Indicates whether the message publisher should participate in a global
	 * transaction (if present) or not. When true, the message sending will
	 * participate in a global transaction when one is present. When false, the
	 * message sending will happen in a local JMS transaction and its success or
	 * failure will not affect any global transaction that might be present.
	 * Default value is true.
	 */
	public void setPublisherSessionTransacted(boolean publisherSessionTransacted) {
		this.publisherSessionTransacted = publisherSessionTransacted;
	}

	public void setServiceLiveBusEventLogBO(IServiceLiveBusEventLogBO serviceLiveBusEventLogBO) {
		this.serviceLiveBusEventLogBO = serviceLiveBusEventLogBO;
	}

	/**
	 * Indicates whether the message subscriber should be invoked as part of a
	 * transaction or not. Effectively, this property indicates whether the broker
	 * should resend the message if the subscriber fails. When true, the broker will
	 * resend if the subscriber fails. When false, the broker will deliver the message
	 * once, and not be concerned with whether the subscriber succeeds or fails. 
	 * Default value is true.
	 * @param subscriberSessionTransacted
	 */
	public void setSubscriberSessionTransacted(boolean subscriberSessionTransacted) {
		this.subscriberSessionTransacted = subscriberSessionTransacted;
	}

	public void setSubscribersEnabled(boolean subscribersEnabled) {
		this.subscribersEnabled = subscribersEnabled;
	}

	public void setTxManager(PlatformTransactionManager txManager) {
        this.txManager = txManager;
    }
}
