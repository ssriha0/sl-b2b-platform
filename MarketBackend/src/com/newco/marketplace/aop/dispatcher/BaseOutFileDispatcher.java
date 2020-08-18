/**
 * 
 */
package com.newco.marketplace.aop.dispatcher;

import java.io.IOException;

import com.jcraft.jsch.JSchException;
import com.newco.marketplace.business.businessImpl.alert.AlertTask;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.util.ssh.SSHUtil;

/**
 * @author hoza
 *
 */
public abstract class BaseOutFileDispatcher implements Dispatcher {

	/* (non-Javadoc)
	 * @see com.newco.marketplace.aop.dispatcher.Dispatcher#sendAlert(com.newco.marketplace.business.businessImpl.alert.AlertTask, java.lang.String)
	 */

	public abstract boolean sendAlert(AlertTask task, String payload); 

	/* (non-Javadoc)
	 * @see com.newco.marketplace.aop.dispatcher.Dispatcher#sendAlert(com.newco.marketplace.business.businessImpl.alert.AlertTask, java.lang.String, java.lang.String)
	 */

	public abstract  boolean sendAlert(AlertTask task, String payload, String fileName); 
	
	protected void sendSCP(String user, String pass, String host, String payload, String fileName, String directory) throws JSchException, IOException {
		//user:password@host:/dir/filename
		StringBuilder commandInfo = new StringBuilder();
		String hostConnectionString = getHostConnectionDirString(user, pass, host, directory);
		commandInfo.append(hostConnectionString);
		commandInfo.append("/");
		commandInfo.append(fileName);
		SSHUtil.scpTo(payload.getBytes(), commandInfo.toString());
	}

	protected void sendMV(String user, String pass, String host, String fileName, String directory) throws JSchException, IOException {
		//user:password@host:/dir
		StringBuilder commandInfo = new StringBuilder();
		String hostConnectionString = getHostConnectionDirString(user, pass, host, directory);
		commandInfo.append(hostConnectionString);
		SSHUtil.mvTo(fileName + OrderConstants.FILE_EXTENSION_INPROG, fileName, commandInfo.toString());
	}
	
	private String getHostConnectionDirString(String userName, String password, String hostName, String directory) {
		StringBuilder hostConnectionDirString = new StringBuilder();
		hostConnectionDirString.append(userName);
		hostConnectionDirString.append(":");
		hostConnectionDirString.append(password);
		hostConnectionDirString.append("@");
		hostConnectionDirString.append(hostName);
		hostConnectionDirString.append(":");
		hostConnectionDirString.append(directory);
		return hostConnectionDirString.toString();
	}

}
