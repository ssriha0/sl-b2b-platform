package com.newco.marketplace.business.iBusiness.webservices;

import java.lang.reflect.InvocationTargetException;

import com.newco.marketplace.dto.vo.webservices.WSPayloadVO;



public interface IWSPayloadDispatcher {

	void invokeMethod(WSPayloadVO vo) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException;
	
	boolean sendPayloads();
}
