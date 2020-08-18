package com.newco.marketplace.api.utils.mappers.provider.capacity.v1_1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.provider.capacity.AvailableTimeSlotList;
import com.newco.marketplace.api.beans.provider.capacity.AvailableTimeSlotsResponse;
import com.newco.marketplace.api.beans.provider.capacity.TimeSlot;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import com.newco.marketplace.utils.DateUtils;
import com.newco.marketplace.vo.provider.TimeSlotDTO;
import com.servicelive.common.CommonUtil;

public class AvailableTimeSlotMapper {

	private static final String defaultDateFormat = "yyyy-MM-dd'T'HH:mm:ss";
	private Logger LOGGER = Logger.getLogger(AvailableTimeSlotMapper.class);

	/**@Description :This method will set error reponse with internal server error
	 * @return
	 */
	public AvailableTimeSlotsResponse createErrorResponse() {
		AvailableTimeSlotsResponse response = new AvailableTimeSlotsResponse();
		Results results = Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(), ResultsCode.INTERNAL_SERVER_ERROR.getCode());
		response.setResults(results);
		return response;
	}
	/**@Description :This method will set error reponse with No sku availeble  error
	 * @return
	 */
	public AvailableTimeSlotsResponse createErrorResponse(String message,String code) {
		AvailableTimeSlotsResponse response = new AvailableTimeSlotsResponse();
		Results results = Results.getError(message,code);
		response.setResults(results);
		return response;
	}
	
	/**@Description :This method will set success Response
	 * @return
	 */
	public AvailableTimeSlotsResponse createSuccessResponse(Map<Date, List<TimeSlotDTO>> availableTimeSlots, Date startTime, Date endTime) {
		AvailableTimeSlotsResponse availableTimeSlotsResponse = new AvailableTimeSlotsResponse();
		List<AvailableTimeSlotList> availableTimeSlotList = new ArrayList<AvailableTimeSlotList>();
		if (null != availableTimeSlots && 0 < availableTimeSlots.size()) {
			Set<Date> dates = availableTimeSlots.keySet();
			Iterator<Date> dateIterator = dates.iterator();
			while (dateIterator.hasNext()) {
				Date passedDate = dateIterator.next();
				List<TimeSlotDTO> timeSlotLists = availableTimeSlots.get(passedDate);
				List<TimeSlot> availableSlots = new ArrayList<TimeSlot>();
				AvailableTimeSlotList availableTimeSlots1 = new AvailableTimeSlotList();
				for (int index = 0; index < timeSlotLists.size(); index++) {
					if (startTime.compareTo(timeSlotLists.get(index).getEndTime()) < 0
							&& endTime.compareTo(timeSlotLists.get(index).getStartTime()) > 0) {
						availableSlots.add(new TimeSlot(DateUtils.getFormatedDate(
								startTime.after(timeSlotLists.get(index).getStartTime()) ? startTime : timeSlotLists.get(index)
										.getStartTime(), defaultDateFormat), DateUtils.getFormatedDate(
								endTime.before(timeSlotLists.get(index).getEndTime()) ? endTime : timeSlotLists.get(index).getEndTime(),
								defaultDateFormat)));
					}
				}
				availableTimeSlots1.setDate(DateUtils.getFormatedDate(passedDate, defaultDateFormat));
				availableTimeSlots1.setTimeSlot(availableSlots);
				availableTimeSlotList.add(availableTimeSlots1);
			}
		}

		Collections.sort(availableTimeSlotList, new Comparator<AvailableTimeSlotList>() {
			public int compare(AvailableTimeSlotList o1, AvailableTimeSlotList o2) {
				return DateUtils.getDateFromString(o1.getDate(), defaultDateFormat).compareTo(
						DateUtils.getDateFromString(o2.getDate(), defaultDateFormat));
			}
		});

		availableTimeSlotsResponse.setAvailableTimeSlotList(availableTimeSlotList);
		Results results = Results.getSuccess();
		availableTimeSlotsResponse.setResults(results);
		return availableTimeSlotsResponse;
	}
}
