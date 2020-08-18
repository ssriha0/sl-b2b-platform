package com.servicelive.esb.actions;

import java.io.File;

import org.apache.log4j.Logger;
import org.jboss.soa.esb.helpers.ConfigTree;


public abstract class AbstractMapforceIntegrationAction extends
		AbstractIntegrationSpringAction {
	
	private static final Logger logger = Logger.getLogger(AbstractMapforceIntegrationAction.class);

	String errorFileSuffix;

	public AbstractMapforceIntegrationAction() {
		super();
	}

	public AbstractMapforceIntegrationAction(ConfigTree configTree) {
		super(configTree);
		if (logger.isDebugEnabled()) {
			logger.debug(configTree.toXml());
		}
	}

	public void setErrorFileSuffix(String errorFileSuffix) {
		this.errorFileSuffix = errorFileSuffix;
	}
	
	protected boolean deleteFile(String fileName) {
		File file = new File(fileName);
		if (file != null && file.exists()) {
			return file.delete();
		}
		return false;
	}
	
	protected boolean fileExists(String fileName) {
		File file = new File(fileName);
		return (file != null && file.exists());
	}

}