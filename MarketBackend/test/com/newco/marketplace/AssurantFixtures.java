package com.newco.marketplace;

import java.sql.Timestamp;
import java.util.Date;

import com.newco.marketplace.business.businessImpl.alert.AlertTask;
import com.newco.marketplace.dto.vo.incident.IncidentResponseVO;
import com.newco.marketplace.dto.vo.incident.IncidentTrackingVO;
import com.newco.marketplace.interfaces.AlertConstants;
import com.newco.marketplace.interfaces.OrderConstants;

public class AssurantFixtures {
	
	public static AlertTask createAssurantFTPAlert() {
		
		Date currentDate = new Date();

		AlertTask alertTask = new AlertTask();
        alertTask.setAlertTaskId(111222345);
        alertTask.setAlertedTimestamp(currentDate);
        alertTask.setCompletionIndicator(AlertConstants.INCOMPLETE_INDICATOR);
        alertTask.setAlertTypeId(AlertConstants.TEMPLATE_TYPE_FTP);
        alertTask.setTemplateId(227);
        alertTask.setPriority("1");
        alertTask.setAlertTo("AssurantFTPDispatcher");
        alertTask.setCreatedDate(currentDate);
        alertTask.setModifiedDate(currentDate);
        alertTask.setModifiedBy("AssurantFTPTest");
		alertTask.setTemplateInputValue("RETAILER=STAP|CONTRACT NUMBER=1261501|CORE_RETURN_NUMBER=|RESOURCE_ID=602|TEMPLATE_ID=227|CLASS CODE=CPU|SHIPPING_CHARGE=0|SKIP_LOGGING=N|PROVIDER_LIST_COUNT=0|STATUS_DATE=2009-05-07 16:12:27|SHIP_AIR_BILL=|COMMENTS=LAST FINAL FTP test dispatch for multiple parts ! Manufacturer:COMPAQ ! Model#:12345 ! Serial#:12345 ! Central Processing Unit Combo Card Cables|CORE_RETURN_AIR_BILL=|ETA_DATE=2009-05-07 00:00:00|SO_ID=611-4477-7672-10|BUYER_ID=1085|BUYER_SUBSTATUS_DESC=Parts Pending|BUYER_RESOURCE_ID=602|AOP_AOP_ACTION_ID=217|REPLACEMENT_MODEL=12345|MODIFIED_BY=cparker|SUBSTATUS_DESC=Part on Order|SALES_TAX=0.00|SUBSTATUS_ID=12|PROVIDER_LIST=[]|AOP_LOGGING_NEWVAL=12|REPLACEMENT_OEM=COMPAQ|CREATED_BY_NAME=Image MICrosystems|PARTS=CPU ^CC ^CABL ~~ClassCode:CPU ! ClassComments:Central Processing Unit ! ShippingMethod:A) Standard Delivery ! Manufacturer:COMPAQ ! Model:12345 ! Serial#:12345 !^ClassCode:CC ! ClassComments:Combo Card ! ShippingMethod:A) Standard Delivery ! Manufacturer:COMPAQ ! Model:12345 ! Serial#:12345 !^ClassCode:CABL ! ClassComments:Cables ! ShippingMethod:A) Standard Delivery ! Manufacturer:COMPAQ ! Model:12345 ! Serial#:12345 !~1^1^1~0^0^0~|STATUS_ID=100|SKIP_ALERT=N|REPLACEMENT_SN=|CONTRACT DATE=2004-01-01|METHOD_NAME=updateSOSubStatus|INCIDENTID=13321688|SERVICELIVE INCIDENT ID=2765|RANDOM=111122223|ASSOCIATED INCIDENT=0|FILE_DATE=070509_161227|PARTS LABOR FLAG=PARTS_AND_LABOR|");
		
        return alertTask;
        
	}

	public static IncidentTrackingVO createIncidentTrackingVO() {
		
		IncidentTrackingVO incidentTrackingVO = new IncidentTrackingVO();
		incidentTrackingVO.setSoId("184-2716-7427-3");
		incidentTrackingVO.setIncidentId(99);
		incidentTrackingVO.setBuyerSubstatusAssocId(3);
		incidentTrackingVO.setBuyerSubstatusDesc("Parts Ordered");
		incidentTrackingVO.setOutputFile("/home/jboss/test.csv");
		incidentTrackingVO.setResponseSentDate(new Timestamp(System.currentTimeMillis()));
		
		return incidentTrackingVO;
		
	}

	public static IncidentResponseVO createIncidentResponseVO() {
		
		String userName = "imageadmin";
		IncidentResponseVO incidentResponseVO = new IncidentResponseVO();
		incidentResponseVO.setRoleId(OrderConstants.BUYER_ROLEID);
		incidentResponseVO.setSoId("184-2716-7427-3");
		incidentResponseVO.setIncidentId(99);
		incidentResponseVO.setClientIncidentId("200054");
		incidentResponseVO.setEntityId(532);
		incidentResponseVO.setResourceId(602);
		incidentResponseVO.setNoteDescription("Note Long Text Description From JUnit");
		incidentResponseVO.setSubject("Note Subject");
		incidentResponseVO.setNoteTypeId(OrderConstants.SO_NOTE_GENERAL_TYPE);
		incidentResponseVO.setPrivateInd("0"); // Public Note
		incidentResponseVO.setCreatedByName(userName);
		incidentResponseVO.setModifiedBy(userName);
		
		return incidentResponseVO;
	}

}
