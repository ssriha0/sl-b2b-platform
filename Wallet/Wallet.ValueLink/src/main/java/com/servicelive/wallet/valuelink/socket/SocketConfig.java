package com.servicelive.wallet.valuelink.socket;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

public class SocketConfig implements InitializingBean{
	private static final Logger logger = Logger.getLogger(SocketConfig.class);
	
	private Integer connectTimeout;

	private Integer receiveTimeout;

	private String primaryHostName;

	private String secondaryHostName;
	private List<String> hostNames;

	private String primaryPortString;
	private List<Integer> primaryPorts;

	private String secondaryPortString;
	private List<Integer> secondaryPorts;
	private List<List<Integer>> ports;
	private HostPortConfig currentHostPortConfig;
	
	private Integer hearbeatInterval;
	
	private Integer reconnectTimeout;
	
	private Integer maxConnections;
	
	public Integer getConnectTimeout() {
		return connectTimeout;
	}
	public void setConnectTimeout(Integer connectTimeout) {
		this.connectTimeout = connectTimeout;
	}
	public Integer getReceiveTimeout() {
		return receiveTimeout;
	}
	public void setReceiveTimeout(Integer receiveTimeout) {
		this.receiveTimeout = receiveTimeout;
	}
	public String getPrimaryHostName() {
		return primaryHostName;
	}
	public void setPrimaryHostName(String primaryHostName) {
		this.primaryHostName = primaryHostName;
	}
	public String getSecondaryHostName() {
		return secondaryHostName;
	}
	public void setSecondaryHostName(String secondaryHostName) {
		this.secondaryHostName = secondaryHostName;
	}
	public List<String> getHostNames() {
		return hostNames;
	}
	public void setHostNames(List<String> hostNames) {
		this.hostNames = hostNames;
	}
	public String getPrimaryPortString() {
		return primaryPortString;
	}
	public void setPrimaryPortString(String primaryPortString) {
		this.primaryPortString = primaryPortString;
	}
	public List<Integer> getPrimaryPorts() {
		return primaryPorts;
	}
	public void setPrimaryPorts(List<Integer> primaryPorts) {
		this.primaryPorts = primaryPorts;
	}
	public String getSecondaryPortString() {
		return secondaryPortString;
	}
	public void setSecondaryPortString(String secondaryPortString) {
		this.secondaryPortString = secondaryPortString;
	}
	public List<Integer> getSecondaryPorts() {
		return secondaryPorts;
	}
	public void setSecondaryPorts(List<Integer> secondaryPorts) {
		this.secondaryPorts = secondaryPorts;
	}
	public List<List<Integer>> getPorts() {
		return ports;
	}
	public void setPorts(List<List<Integer>> ports) {
		this.ports = ports;
	}
	
	public HostPortConfig getCurrentHostPortConfig() {
		return currentHostPortConfig;
	}
	public void setCurrentHostPortConfig(HostPortConfig currentHostPortConfig) {
		this.currentHostPortConfig = currentHostPortConfig;
	}
	
	
	public void afterPropertiesSet() throws Exception {
		logger.info("SocketContainerPooledObjectFactory afterproperties set");
		logger.info("Primary hostname: "+primaryHostName);
		logger.info("Secondary hostname: "+secondaryHostName);
		logger.info("Primary ports: "+primaryPortString);
		logger.info("Secondary ports: "+secondaryPortString);
				
		Assert.isTrue(StringUtils.isNotBlank(primaryHostName), "Primary hostname is required");
		Assert.isTrue(StringUtils.isNotBlank(secondaryHostName), "Secondary hostname is required");
		Assert.isTrue(StringUtils.isNotBlank(primaryPortString), "List of primary ports is required");
		Assert.isTrue(StringUtils.isNotBlank(primaryPortString), "List of secondary ports is required");
		
		
		List<HostPortConfig> primaryHostPortConfig = new ArrayList<HostPortConfig>();
		List<HostPortConfig> secondaryHostPortConfig = new ArrayList<HostPortConfig>();
		
		primaryPorts = new ArrayList<Integer>();
		for(String port:primaryPortString.split(",")){
			//primaryPorts.add(Integer.valueOf(port));
			primaryHostPortConfig.add(new HostPortConfig(primaryHostName, Integer.valueOf(port)));
		}
		secondaryPorts = new ArrayList<Integer>();
		for(String port:secondaryPortString.split(",")){
			secondaryHostPortConfig.add(new HostPortConfig(secondaryHostName, Integer.valueOf(port)));
		}
		
		Iterator<HostPortConfig> primaryIterator = primaryHostPortConfig.iterator();
		Iterator<HostPortConfig> secondaryIterator = secondaryHostPortConfig.iterator();
		
		int noOfHostPorts = 0;
		HostPortConfig firstHostPortConfig = null;
		while (true) {
			HostPortConfig hostPortConfig = null;
			if (primaryIterator.hasNext()) {
				hostPortConfig = primaryIterator.next();
				primaryIterator.remove();
			}
			if (hostPortConfig != null) {
				setHostPortConfig(hostPortConfig);
				if (firstHostPortConfig == null) {
					firstHostPortConfig = hostPortConfig;
				}
			}
			
			hostPortConfig = null;
			if (secondaryIterator.hasNext()) {
				hostPortConfig = secondaryIterator.next();
				secondaryIterator.remove();
			}
			if (hostPortConfig != null) {
				setHostPortConfig(hostPortConfig);
				if (firstHostPortConfig == null) {
					firstHostPortConfig = hostPortConfig;
				}
			}
			
			// Is both Primary and Secondary list exhausted
			if (!primaryIterator.hasNext() && !secondaryIterator.hasNext()) {
				break;
			}
			
			noOfHostPorts++;
		}
		// currentHostPortConfig.setNext(firstHostPortConfig);
		currentHostPortConfig = firstHostPortConfig;
		
		logger.info("No of HostPort objects="+noOfHostPorts);
	}


	private void setHostPortConfig(HostPortConfig hostPortConfig) {
		if (currentHostPortConfig == null) {
			currentHostPortConfig = hostPortConfig;
		} else {
			currentHostPortConfig.setNext(hostPortConfig);
			currentHostPortConfig = hostPortConfig;
		}
	}
	
	public Integer getHearbeatInterval() {
		return hearbeatInterval;
	}
	
	public void setHearbeatInterval(Integer hearbeatInterval) {
		this.hearbeatInterval = hearbeatInterval;
	}
	
	public Integer getReconnectTimeout() {
		return reconnectTimeout;
	}
	
	public void setReconnectTimeout(Integer reconnectTimeout) {
		this.reconnectTimeout = reconnectTimeout;
	}
	public Integer getMaxConnections() {
		return maxConnections;
	}
	public void setMaxConnections(Integer maxConnections) {
		this.maxConnections = maxConnections;
	}
}

