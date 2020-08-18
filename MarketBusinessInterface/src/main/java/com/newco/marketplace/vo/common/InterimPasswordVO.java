package com.newco.marketplace.vo.common;

import java.sql.Date;
import java.sql.Timestamp;
import com.newco.marketplace.utils.AdminUtil;
import com.newco.marketplace.utils.CryptoUtil;

/**
 * 
 * @author Shekhar Nirkhe
 *
 */
public class InterimPasswordVO {
	private String password;
	private String userName;
	private Timestamp startTime;
	private int valid = 1;
	private boolean expired;
	private String plainTextPassword;
	
	public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	public InterimPasswordVO() {
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Timestamp getStartTime() {
		return startTime;
	}
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	public int getValid() {
		return valid;
	}
	public void setValid(int valid) {
		this.valid = valid;
	}
	
	public static InterimPasswordVO createNewInstance(String userName, String password) {
		InterimPasswordVO interimPassword = new InterimPasswordVO();
		if (userName == null) {//for select
			interimPassword.setPassword(password);
			return interimPassword;
		} 
		
		//Timestamp startTime = new Timestamp(System.currentTimeMillis());
		if (password == null ){
			password= AdminUtil.generatePassword();
			interimPassword.setPlainTextPassword(password);
			password = CryptoUtil.encrypt(password);
			
			//fix done by shekhar - replace all '+' with '#'	
	        
	        password  = password.replaceAll("\\+", "0");
	        
		}
		
		interimPassword.setPassword(password);
		interimPassword.setUserName(userName);		
		//interimPassword.setStartTime(startTime);
		return interimPassword;
	}

	public String getPlainTextPassword() {
		return plainTextPassword;
	}

	public void setPlainTextPassword(String plainTextPassword) {
		this.plainTextPassword = plainTextPassword;
	}
	
}
