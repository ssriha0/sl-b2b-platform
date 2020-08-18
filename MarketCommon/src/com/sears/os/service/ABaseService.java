package com.sears.os.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sears.os.utils.ProcessUtils;
import com.sears.os.vo.request.ABaseServiceRequestVO;
import com.sears.os.vo.response.ABaseServiceResponseVO;

public abstract class ABaseService implements ServiceConstants {

	protected final Log logger = LogFactory.getLog(getClass());

	private ServiceAttributes serviceAttributes;

	protected void logEntry(String method, ABaseServiceRequestVO requestVo) {
		if (logger.isDebugEnabled()) {
			StringBuilder sb = new StringBuilder();
			sb.append("PROCESS ID[");
			sb.append(ProcessUtils.getProcessId());
			sb.append("] METHOD[");
			sb.append(method);
			sb.append("] started REQUEST[");
			sb.append(requestVo.toString());
			sb.append("]");
			logger.debug(sb.toString());
		}
	}

	protected void logExit(String method, ABaseServiceResponseVO responseVo) {
		if (logger.isDebugEnabled()) {
			StringBuilder sb = new StringBuilder();
			sb.append("PROCESS ID[");
			sb.append(ProcessUtils.getProcessId());
			sb.append("] METHOD[");
			sb.append(method);
			sb.append("] ended RESPONSE[");
			sb.append(responseVo.toString());
			sb.append("]");
			logger.debug(sb.toString());
		}
	}

	public ServiceAttributes getServiceAttributes() {
		return serviceAttributes;
	}

	/**
	 * Sets the serviceAttributes.
	 * @param serviceAttributes The serviceAttributes to set
	 */
	public void setServiceAttributes(ServiceAttributes serviceAttributes) {
		this.serviceAttributes = serviceAttributes;
	}
}
