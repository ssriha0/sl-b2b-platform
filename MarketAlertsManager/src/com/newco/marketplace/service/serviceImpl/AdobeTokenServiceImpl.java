package com.newco.marketplace.service.serviceImpl;

import java.time.Duration;
import java.time.LocalDateTime;

import org.apache.log4j.Logger;

import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.service.TokenService;
import com.newco.marketplace.utils.utility.JWTUtil;

public class AdobeTokenServiceImpl implements TokenService{
	
	private static final Logger logger = Logger.getLogger(AdobeTokenServiceImpl.class.getName());
	
	private JWTUtil jwtUtil;
	
	public void genrateToken() throws DataServiceException{
		logger.info("AdobeTokenServiceImpl ---> genrateToken ");
		if (!validateToken()){
			jwtUtil.genrateToken();
			logger.info("AdobeTokenServiceImpl ---> new token genrated !!");
		}
	}
	
	private boolean validateToken(){		
		LocalDateTime currentTime = LocalDateTime.now();
		LocalDateTime lastTokentTime = jwtUtil.getGenratedOnTime();
		logger.info("AdobeTokenServiceImpl ---> last token GenratedOnTime: " + lastTokentTime);
		if (lastTokentTime == null || null == jwtUtil.getAccessToken()){
			return false;
		}	
		Duration duration = Duration.between(currentTime, lastTokentTime);		
		long diff = Math.abs(duration.toHours());
		logger.info("AdobeTokenServiceImpl ---> Hour difference: " + diff);
		if(diff > 23){
			return false;
		}else {
			return true;
		}		
	}

	public JWTUtil getJwtUtil() {
		return jwtUtil;
	}

	public void setJwtUtil(JWTUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}
}
