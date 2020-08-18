package com.servicelive.esb.mapper;

import com.servicelive.esb.constant.HSRFieldNameConstants;
import com.servicelive.esb.dto.HSRServiceOrder;
import com.servicelive.esb.dto.HSRSoDatesAndTimes;

public class HSRUpdateFileDataMapper extends HSRDataMapper implements Mapper{

	public Object mapData(Object dataObj){
		HSRServiceOrder hsrServiceOrder = null;
		if(dataObj!= null){
			String fileData  = (String) dataObj;
			String[] fields = fileData.split("\\|");
			/* the Update file is supposed to have 44 fields, if it doesn't have do not process the file*/
			if(fields!= null && fields.length < 44){
				return hsrServiceOrder;
			}
			hsrServiceOrder = new HSRServiceOrder();
			
			hsrServiceOrder.setInputFragment(fileData);
			
			hsrServiceOrder.setServiceOrderNumber(fields[HSRFieldNameConstants.SERVICE_ORDER_NUMBER_UPDATE]);
			hsrServiceOrder.setServiceUnitNumber(fields[HSRFieldNameConstants.SERVICE_UNIT_NUMBER_UPDATE]);
			hsrServiceOrder.setServiceOrderStatusCode(fields[HSRFieldNameConstants.SERVICE_ORDER_STATUS_CODE_UPDATE]);
			
			HSRSoDatesAndTimes soDatesAndTimes = new HSRSoDatesAndTimes();
			soDatesAndTimes.setModifiedDate(fields[HSRFieldNameConstants.MODIFIED_DATE_UPDATE]);
			soDatesAndTimes.setModifiedTime(fields[HSRFieldNameConstants.MODIFIED_TIME_UPDATE]);
			
			hsrServiceOrder.setDatesAndTimes(soDatesAndTimes);
			
			hsrServiceOrder.setModifyingUnitId(fields[HSRFieldNameConstants.MODIFYING_UNIT_ID_UPDATE]);
			hsrServiceOrder.setFiller(fields[HSRFieldNameConstants.FILLER_UPDATE]);
			hsrServiceOrder.setEmployeeIDNumber(fields[HSRFieldNameConstants.EMPLOYEE_ID_NUMBER_UPDATE]);
			
			hsrServiceOrder.setIsCameInUpdateFile(Boolean.TRUE);
			
			/*Map parts later on  */
		}
		return hsrServiceOrder;
	}
	
	
}
