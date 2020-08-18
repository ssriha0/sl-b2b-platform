package com.servicelive.esb.actions;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.actions.AbstractSpringAction;
import org.jboss.soa.esb.actions.ActionLifecycleException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;
import org.springframework.beans.factory.BeanFactory;

import com.newco.marketplace.translator.util.SpringUtil;
import com.servicelive.esb.integration.IIntegrationServiceCoordinator;
import com.servicelive.esb.integration.bo.IIntegrationBO;
 
/**
 * Implementation of the AbstractSpringAction that uses a static BeanFactory
 * instead of a per instance BeanFactory.
 * 
 * @author sahmed
 */
public class AbstractEsbSpringAction extends AbstractSpringAction {

	private static Logger logger = Logger
			.getLogger(AbstractEsbSpringAction.class);
			
	private IIntegrationServiceCoordinator integrationServiceCoordinator;

	public IIntegrationServiceCoordinator getIntegrationServiceCoordinator() {
		return integrationServiceCoordinator;
	}

	public void setIntegrationServiceCoordinator(
			IIntegrationServiceCoordinator integrationServiceCoordinator) {
		this.integrationServiceCoordinator = integrationServiceCoordinator;
	}


	public AbstractEsbSpringAction() {
		super();
	}

	public AbstractEsbSpringAction(ConfigTree configTree) {
		super();
		super.configTree = configTree;
	}

	@Override
	public void initialise() throws ActionLifecycleException {
		// SL-21931
		
		logger.info("In initialise method");
		
		if (this.getIntegrationServiceCoordinator() == null) {
			this.setIntegrationServiceCoordinator((IIntegrationServiceCoordinator) this.getBeanFactory().getBean("integrationServiceCoordinator"));
		}

		if (this.getIntegrationServiceCoordinator().getIntegrationBO() == null) {
			this.getIntegrationServiceCoordinator().setIntegrationBO((IIntegrationBO) this.getBeanFactory().getBean("integrationBO"));
		}
		
	}

	@Override
	public boolean isBeanFactoryNull() {
		return SpringUtil.factory == null;
	}

	@Override
	public void exceptionHandler(Message message, Throwable exception) {
		logger.error("Error occurred during execution of ESB action.", exception);
	}

	@Override
	public void destroy() throws ActionLifecycleException {
		super.destroy();
	}

	@Override
	protected BeanFactory getBeanFactory() throws ActionLifecycleException {
		return SpringUtil.factory;
	}

	@Override
	protected void initializeSpring() throws ActionLifecycleException {
		logger.info("In initializeSpring; not doing anything");
	}

	@Override
	protected void logHeader() {
		super.logHeader();
	}

	@Override
	protected void logFooter() {
		super.logFooter();
	}
	
	protected String convertThrowableToString(Throwable t) {
		StringWriter stringWriter = new StringWriter();
		t.printStackTrace(new PrintWriter(stringWriter));
		return stringWriter.toString();
	}
}
