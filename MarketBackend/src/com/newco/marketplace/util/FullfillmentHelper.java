package com.newco.marketplace.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.MessageEOFException;

import org.apache.log4j.Logger;
import org.springframework.jms.support.converter.SimpleMessageConverter;

import com.newco.marketplace.interfaces.FullfillmentConstants;
import com.newco.marketplace.persistence.iDao.ledger.IFullfillmentDao;



public class FullfillmentHelper extends SimpleMessageConverter {
	private static final Logger logger = Logger.getLogger(FullfillmentHelper.class.getName());
	private IFullfillmentDao fullfillmentDao;
	
	public  byte[] getBytesFromBytesMessage(BytesMessage bMsg) throws JMSException{
		//byte[] bytes = new byte[(int)bMsg.getBodyLength()];
		Vector<Byte> bytesVector = new Vector<Byte>();
		byte[] bytes;
		try{
			int i = 0;
			while(true){
				try{
					bytesVector.add(bMsg.readByte());
					i=i+1;
				}catch(MessageEOFException mee){
					break;
				}
			}
			
			bytes = new byte[i];
			int j = 0;
			Iterator<Byte> iter = bytesVector.iterator();
			while(iter.hasNext()){
				bytes[j] = (Byte)iter.next();
				j=j+1;
			}
			//bytes = extractByteArrayFromMessage(bMsg);
			
		}catch(JMSException jmse){
			logger.error("Error occured in FullfillmentUtil --------->getBytesFromBytesMessage");
			throw jmse;
		}
		
		return bytes;
	}
	
	public boolean checkExternalQueuesValidity(String queueName) throws Exception{
		boolean flag = false;
		Map<String, String> resultMap = null;
		try{
			resultMap = fullfillmentDao.getExternalQueueStatus();
			if(queueName == FullfillmentConstants.SHARP_QUEUE){
				if(resultMap.get("sharpInd") == "Y"){
					flag = true;
				}
			}else if(queueName == FullfillmentConstants.VALUELINK_QUEUE){
				if(resultMap.get("valueLinkInd") == "Y"){
					flag = true;
				}
			}
		}catch(Exception e){
			logger.error("Error occured in FullfillmentUtil ------->checkExternalQueuesValidity");
			throw e;
		}
		return flag;
	}

	public IFullfillmentDao getFullfillmentDao() {
		return fullfillmentDao;
	}

	public void setFullfillmentDao(IFullfillmentDao fullfillmentDao) {
		this.fullfillmentDao = fullfillmentDao;
	}

}
