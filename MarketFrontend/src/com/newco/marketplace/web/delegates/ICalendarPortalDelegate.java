package com.newco.marketplace.web.delegates;

import java.io.Serializable;
import java.util.Date;

import com.newco.marketplace.dto.vo.serviceorder.ServiceDatetimeSlot;
import com.newco.marketplace.vo.calendarPortal.ExternalCalendarDTO;
import com.newco.marketplace.web.dto.d2cproviderportal.CalendarPortalResponseDto;

/**
 * 
 * @author rranja1
 * 
 */
public interface ICalendarPortalDelegate extends Serializable {

	CalendarPortalResponseDto getVendorEvents(String vendorIdStr, Date startDate, Date endDate);
	ExternalCalendarDTO saveOrUpdateExternalCalendarDetail(int firmID, int personId, String oAuthCode,String contextPath);
	ExternalCalendarDTO getExternalCalendarDetial(int personId);
	CalendarPortalResponseDto acceptSO(String soId, Integer routedProvider, Integer companyID, ServiceDatetimeSlot selectedSlot);
	CalendarPortalResponseDto rejectSO(String soId, Integer routedResourceId, Integer rejectReasonId, String rejectReasonComment, String username);

}
