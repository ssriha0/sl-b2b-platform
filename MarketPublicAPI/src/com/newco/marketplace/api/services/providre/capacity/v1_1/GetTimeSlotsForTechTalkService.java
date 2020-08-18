package com.newco.marketplace.api.services.providre.capacity.v1_1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.provider.capacity.AvailableTimeSlotsResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.provider.capacity.v1_1.AvailableTimeSlotMapper;
import com.newco.marketplace.business.capacity.timeslots.IAvailableTimeSlotCalculatorBO;
import com.newco.marketplace.business.iBusiness.provider.IGeneralInfoBO;
import com.newco.marketplace.business.iBusiness.provider.IProviderInfoPagesBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IBuyerSkuBO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.interfaces.BuyerConstants;
import com.newco.marketplace.utils.DateUtils;
import com.newco.marketplace.vo.provider.TimeSlotDTO;

@APIResponseClass(GetTimeSlotsForTechTalkService.class)
public class GetTimeSlotsForTechTalkService extends BaseService {

	private static final Logger LOGGER = Logger.getLogger(GetTimeSlotsForTechTalkService.class);
	private static final String defaultDateFormat = "yyyy-MM-dd";
	private static final String defaultTimeFormat = "HH:mm:ss";
	private AvailableTimeSlotMapper availableTimeSlotMapper;
	private IGeneralInfoBO generalInfoBO;
	private IAvailableTimeSlotCalculatorBO avaialbleTimeSlotCalculatorBO;
	private IBuyerSkuBO buyerSkuBO;
	private SimpleDateFormat sdf = new SimpleDateFormat(defaultDateFormat);
	private SimpleDateFormat stf = new SimpleDateFormat(defaultTimeFormat);
	private SimpleDateFormat isoSDF = new SimpleDateFormat(defaultDateFormat+"'T'"+defaultTimeFormat + "'Z'");
	
	
	public GetTimeSlotsForTechTalkService() {
		super(
				null,
				PublicAPIConstant.PROVIDER_CAPACITY_AVAILABLE_TIMESLOTS_XSD,
				PublicAPIConstant.PROVIDER_CAPACITY_AVAILABLE_TIMESLOTS_NAMESPACE,
				PublicAPIConstant.PROVODER_CAPACITY_V1_1,
				PublicAPIConstant.PROVIDER_CAPACITY_AVAILABLE_TIMESLOTS_SCHEMALOCATION,
				null, AvailableTimeSlotsResponse.class);
		// strict date format checking
		sdf.setLenient(false);
		stf.setLenient(false);
		isoSDF.setLenient(false);
	}

	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		LOGGER.info("accessing available time slot service..");
		String sku = apiVO.getSku();
		Integer buyerID = apiVO.getBuyerIdInteger();
		AvailableTimeSlotsResponse availableTimeSlotsResponse = null;
		boolean isSkuAvailable = false;
		if(null!= apiVO.getBuyerIdInteger()){			
			try {
				if (buyerSkuBO.isSkuExistForBuyer(sku, buyerID)) {

					isSkuAvailable = buyerSkuBO.checkSkuCatAvailable(apiVO.getBuyerIdInteger());
					if (isSkuAvailable) {
						
						if( null == apiVO.getStartDate() || apiVO.getStartDate().length() <= 0 || null == apiVO.getEndDate() || apiVO.getEndDate().length() <= 0) {
							LOGGER.error("Error in execute of GetTimeSlotsForTechTalkService. Date is not in correct format");
							return availableTimeSlotMapper.createErrorResponse(ResultsCode.GET_AVAILABLE_TIME_SLOTS_DATE_FORMAT_ERROR.getMessage(),
									ResultsCode.GET_AVAILABLE_TIME_SLOTS_DATE_FORMAT_ERROR.getCode());
						}

						apiVO.setStartTime(apiVO.getStartDate().indexOf("T") >=0 ? apiVO.getStartDate().split("T")[1] : "00:00:00Z");
						apiVO.setEndTime(apiVO.getEndDate().indexOf("T") >=0 ? apiVO.getEndDate().split("T")[1] : "23:59:59Z");
						
						Date passedDate1 = sdf.parse(apiVO.getStartDate().split("T")[0]);
						Date passedDate2 = sdf.parse(apiVO.getEndDate().split("T")[0]);
						
						Date passedTime1 = isoSDF.parse(apiVO.getStartDate().split("T")[0] + "T" + apiVO.getStartTime());
						//getStartTime(apiVO.getStartTime(), passedDate1);
						Date passedTime2 = isoSDF.parse(apiVO.getEndDate().split("T")[0] + "T" + apiVO.getEndTime());
						//getEndTime(apiVO.getEndTime(), passedDate2);
						
						if (passedTime1.after(passedTime2)) {
							LOGGER.error(" Start date is AFTER end date ");
							return availableTimeSlotMapper.createErrorResponse(ResultsCode.START_DATE_AFTER_END_DATE.getMessage(),
									ResultsCode.START_DATE_AFTER_END_DATE.getCode());
						}

						Calendar cStart = Calendar.getInstance();
						cStart.setTime(passedDate1);
						Calendar cEnd = Calendar.getInstance();
						cEnd.setTime(passedDate2);
						Map<Date, List<TimeSlotDTO>> timeSlotsMap = new HashMap<Date, List<TimeSlotDTO>>();
						while (!cStart.after(cEnd)) {
							Date passedDate = cStart.getTime();
							List<String> applicableProviders = avaialbleTimeSlotCalculatorBO
									.getValidProvidersForTechTalk(buyerID, sku);
							Map<String, TimeSlotDTO> capacityWindowList = generalInfoBO.getAvailableTimeSlots(applicableProviders,
									passedDate);
							Map<String, List<TimeSlotDTO>> bookedCalendarList = avaialbleTimeSlotCalculatorBO.getBlockedCalendarSlots(
									applicableProviders, passedDate);
							List<TimeSlotDTO> availableTimeSlots = avaialbleTimeSlotCalculatorBO.getAvailableTimeSlots(passedDate,
									capacityWindowList, bookedCalendarList);
							timeSlotsMap.put(passedDate, availableTimeSlots);
							cStart.add(Calendar.DAY_OF_MONTH, 1);
						}
						availableTimeSlotsResponse = availableTimeSlotMapper.createSuccessResponse(timeSlotsMap, passedTime1, passedTime2);
					} else {
						LOGGER.error(" Sku not available ");
						return availableTimeSlotMapper.createErrorResponse(ResultsCode.NO_SKU_AVAILABLE.getMessage(),
								ResultsCode.NO_SKU_AVAILABLE.getCode());
					}
				} else {
					LOGGER.error(" Either sku is not present or it is not available for this buyer ");
					return availableTimeSlotMapper.createErrorResponse(ResultsCode.SKU_NOT_AVAILABLE_FOR_BUYER.getMessage(),
							ResultsCode.SKU_NOT_AVAILABLE_FOR_BUYER.getCode());
				}
			} catch (BusinessServiceException e) {
					LOGGER.error("Error in execute of GetTimeSlotsForTechTalkService: "+e);
					return availableTimeSlotMapper.createErrorResponse();
			} catch (ParseException e) {
				LOGGER.error("Error in execute of GetTimeSlotsForTechTalkService: "+e);
				return availableTimeSlotMapper.createErrorResponse(ResultsCode.GET_AVAILABLE_TIME_SLOTS_DATE_FORMAT_ERROR.getMessage(),
						ResultsCode.GET_AVAILABLE_TIME_SLOTS_DATE_FORMAT_ERROR.getCode());
			} catch (Exception e) {
				LOGGER.error("Error in execute of GetTimeSlotsForTechTalkService: "+e);
				return availableTimeSlotMapper.createErrorResponse();
			}
		}
		else{
			LOGGER.error("Buyer id is null ");
			return availableTimeSlotMapper.createErrorResponse();
		}
		return availableTimeSlotsResponse;
	}

	private Date getEndTime(String endTime, Date passedDate2) throws ParseException {
		Date passedTime2;
		if (null != endTime && endTime.trim().length() > 0) {
			passedTime2 = stf.parse(endTime.trim());
			Calendar endTimeCal = Calendar.getInstance();
			endTimeCal.setTime(passedDate2);
			endTimeCal.set(Calendar.HOUR_OF_DAY, passedTime2.getHours());
			endTimeCal.set(Calendar.MINUTE, passedTime2.getMinutes());
			endTimeCal.set(Calendar.SECOND, passedTime2.getSeconds());
			passedTime2 = endTimeCal.getTime();
		} else {
			Calendar endTimeCal = Calendar.getInstance();
			endTimeCal.setTime(passedDate2);
			endTimeCal.set(Calendar.HOUR_OF_DAY, 23);
			endTimeCal.set(Calendar.MINUTE, 59);
			endTimeCal.set(Calendar.SECOND, 59);
			passedTime2 = endTimeCal.getTime();
		}
		return passedTime2;
	}

	private Date getStartTime(String startTime, Date passedDate1) throws ParseException {
		Date passedTime1;
		if (null != startTime && startTime.trim().length() > 0) {
			passedTime1 = stf.parse(startTime.trim());
			
			Calendar startTimeCal = Calendar.getInstance();
			startTimeCal.setTime(passedDate1);
			startTimeCal.set(Calendar.HOUR_OF_DAY, passedTime1.getHours());
			startTimeCal.set(Calendar.MINUTE, passedTime1.getMinutes());
			startTimeCal.set(Calendar.SECOND, passedTime1.getSeconds());
			passedTime1 = startTimeCal.getTime();
		} else {
			Calendar startTimeCal = Calendar.getInstance();
			startTimeCal.setTime(passedDate1);
			startTimeCal.set(Calendar.HOUR_OF_DAY, 0);
			startTimeCal.set(Calendar.MINUTE, 0);
			startTimeCal.set(Calendar.SECOND, 0);
			passedTime1 = startTimeCal.getTime();
		}
		return passedTime1;
	}

	public AvailableTimeSlotMapper getAvailableTimeSlotMapper() {
		return availableTimeSlotMapper;
	}

	public void setAvailableTimeSlotMapper(
			AvailableTimeSlotMapper availableTimeSlotMapper) {
		this.availableTimeSlotMapper = availableTimeSlotMapper;
	}

	public IGeneralInfoBO getGeneralInfoBO() {
		return generalInfoBO;
	}

	public void setGeneralInfoBO(IGeneralInfoBO generalInfoBO) {
		this.generalInfoBO = generalInfoBO;
	}

	public IAvailableTimeSlotCalculatorBO getAvaialbleTimeSlotCalculatorBO() {
		return avaialbleTimeSlotCalculatorBO;
	}

	public void setAvaialbleTimeSlotCalculatorBO(
			IAvailableTimeSlotCalculatorBO avaialbleTimeSlotCalculatorBO) {
		this.avaialbleTimeSlotCalculatorBO = avaialbleTimeSlotCalculatorBO;
	}

	public IBuyerSkuBO getBuyerSkuBO() {
		return buyerSkuBO;
	}

	public void setBuyerSkuBO(IBuyerSkuBO buyerSkuBO) {
		this.buyerSkuBO = buyerSkuBO;
	}
}
