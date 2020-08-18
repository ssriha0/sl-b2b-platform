package com.servicelive.wallet.valuelink.web;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.servicelive.wallet.valuelink.ejb.ValueLinkQueueSingleton;

/**
 * Class ValueLinkQueueStartupServlet.
 */
public class ValueLinkQueueStartupServlet extends HttpServlet {
		
	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** logger. */
	private static final Logger logger  = Logger.getLogger(ValueLinkQueueStartupServlet.class);
       
    /**
     * ValueLinkQueueStartupServlet.
     */
    public ValueLinkQueueStartupServlet() {
        super();
    }
    
    /* (non-Javadoc)
     * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
     */
    @Override
    public void init(ServletConfig config) throws ServletException {   
		logger.info("SHARP/ValueLink initialization servlet");
    	super.init(config);
		try {
			BeanFactory beanFactory =  WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
			ValueLinkQueueSingleton valueLinkQueueSingleton = (ValueLinkQueueSingleton) beanFactory.getBean("valueLinkQueueSingleton");
			Thread vlSingletonThr = new Thread(valueLinkQueueSingleton);
			vlSingletonThr.setDaemon(true);
			vlSingletonThr.start();
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal(e);
		}    	
    }

}
