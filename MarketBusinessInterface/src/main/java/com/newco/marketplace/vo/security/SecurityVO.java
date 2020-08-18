/**
 * 
 */
package com.newco.marketplace.vo.security;

import com.newco.marketplace.vo.provider.BaseVO;


/**
 * @author KSudhanshu
 *
 */
public class SecurityVO extends BaseVO {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8395466455994129929L;
	private String appId;
	private String username;
	private String appKey;
	private String appName;
	private String ipAddress;	
    private int entityId;
    private int entityType;
  
   
	public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

	

	public int getEntityId() {
		return entityId;
	}

	public void setEntityId(int entityId) {
		this.entityId = entityId;
	}

	public int getEntityType() {
		return entityType;
	}

	public void setEntityType(int entityType) {
		this.entityType = entityType;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}
   
    
}