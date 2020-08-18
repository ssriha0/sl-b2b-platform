package test.newco.test.marketplace.web.action;

import java.util.HashMap;

import junit.framework.TestCase;

import org.apache.struts.mock.MockHttpServletRequest;
import org.apache.struts.mock.MockHttpServletResponse;
import org.apache.struts.mock.MockServletContext;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.Dispatcher;
import org.apache.struts2.spring.StrutsSpringObjectFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.ActionProxyFactory;

public class BaseStrutsTestCase<T> extends TestCase {
    //private static final Logger logger = Logger.getLogger(BaseStrutsTestCase.class.getName());
	private Dispatcher dispatcher;
	protected ActionProxy proxy;
	protected MockServletContext servletContext;
	protected MockHttpServletRequest request;
	protected MockHttpServletResponse response;

	/**
	 * Created action class based on namespace and name
	 */
	protected T createAction(Class clazz, String namespace, String name)
			throws Exception {

		// create a proxy class which is just a wrapper around the action call.
		// The proxy is created by checking the namespace and name against the
		// struts.xml configuration
		proxy = dispatcher.getContainer().getInstance(ActionProxyFactory.class)
				.createActionProxy(namespace, name, null, true, false);
		//logger.info("*****************881");
		// set to true if you want to process Freemarker or JSP results
		proxy.setExecuteResult(false);
		// by default, don't pass in any request parameters
		proxy.getInvocation().getInvocationContext().setParameters(
				new HashMap());
		//logger.info("*****************882");
		// set the actions context to the one which the proxy is using
		ServletActionContext.setContext(proxy.getInvocation()
				.getInvocationContext());
		//logger.info("*****************883");
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		//logger.info("*****************883");
		ServletActionContext.setRequest(request);
		ServletActionContext.setResponse(response);
		//logger.info("*****************884");
		ActionContext context = ServletActionContext.getContext();
		context.setSession(new HashMap());
		//logger.info("*****************885");
		ServletActionContext.setServletContext(servletContext);
		return (T) proxy.getAction();
	}

	protected void setUp() throws Exception {
		String[] config = new String[] { "applicationContext.xml" };

		// Link the servlet context and the Spring context
		servletContext = new MockServletContext();
		XmlWebApplicationContext appContext = new XmlWebApplicationContext();
		appContext.setServletContext(servletContext);
		appContext.setConfigLocations(config);
		appContext.refresh();
		servletContext.setAttribute(
				WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE,
				appContext);


		// Dispatcher is the guy that actually handles all requests. Pass in
		// an empty Map as the parameters but if you want to change stuff like
		// what config files to read, you need to specify them here
		// (see Dispatcher's source code)
		dispatcher = new Dispatcher(servletContext, new HashMap());
		dispatcher.init();
		Dispatcher.setInstance(dispatcher);
		
		// Use spring as the object factory for Struts
		//Creating new instance of StrutsSpringObjectFactory after creation of Dispatcher
		StrutsSpringObjectFactory ssf = new StrutsSpringObjectFactory(null,
						null, null, servletContext, null, dispatcher.getContainer());
		ssf.setApplicationContext(appContext);
		// ssf.setServletContext(servletContext);
		//StrutsSpringObjectFactory.setObjectFactory(ssf);
	}
}
