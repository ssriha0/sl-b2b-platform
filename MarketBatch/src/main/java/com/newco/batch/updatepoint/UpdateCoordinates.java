package com.newco.batch.updatepoint;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.business.iBusiness.location.ILocationBO;

public class UpdateCoordinates {

	private static final Logger logger = Logger.getLogger(UpdateCoordinates.class.getName());
	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) {
		
		Object x = getBusinessBean("locationBO");
		
		ILocationBO obj = (ILocationBO)x;
	
		try {
			obj.updateLocationPoint();
			logger.debug("Point :"+obj.updateLocationPoint());
		} catch (Exception e) {
			logger.error("Error Occured:" + e.getMessage(), e);
			e.printStackTrace();
		}
			
	}
	
	public static  Object getBusinessBean(String str)
	{
		ApplicationContext appContext = MPSpringLoaderPlugIn.getCtx();
		Object pointbusiness = appContext.getBean(str);
		return pointbusiness;
	}

}
