package com.newco.marketplace.web.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * Modified version of standard xwork LoggingInterceptor that logs the session
 * ID.
 */
public class LogActionInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger
			.getLogger(LogActionInterceptor.class);
	private static final String FINISH_MESSAGE = "Finishing execution stack for action ";
	private static final String START_MESSAGE = "Starting execution stack for action ";

	public LogActionInterceptor() {
		super();
		log.info("Initializing ServiceLive LogActionInterceptor");
	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		final ActionContext context = invocation.getInvocationContext();
		final HttpServletRequest request = (HttpServletRequest) context
				.get(StrutsStatics.HTTP_REQUEST);
		final HttpSession session = request.getSession();
		final String sessionId = session.getId();
		logMessage(invocation, START_MESSAGE, sessionId);
		String result = invocation.invoke();
		logMessage(invocation, FINISH_MESSAGE, sessionId);
		return result;
	}

	private void logMessage(ActionInvocation invocation, String baseMessage,
			String sessionId) {
		if (log.isInfoEnabled()) {
			StringBuffer message = new StringBuffer(baseMessage);
			String namespace = invocation.getProxy().getNamespace();
			if ((namespace != null) && (namespace.trim().length() > 0)) {
				message.append(namespace).append("/");
			}
			message.append(invocation.getProxy().getActionName());
			message.append(" in session ");
			message.append(sessionId);
			log.info(message.toString());
		}
	}

}
