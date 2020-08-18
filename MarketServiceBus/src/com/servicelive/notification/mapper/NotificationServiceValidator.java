package com.servicelive.notification.mapper;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import java.util.regex.Pattern;

import com.newco.marketplace.business.iBusiness.inhomeoutbound.constants.InHomeNPSConstants;
import com.servicelive.notification.beans.JobCodeData;
import com.servicelive.notification.beans.OrderUpdateRequest;
import com.servicelive.notification.beans.RequestInHomeMessageDetails;
import com.servicelive.notification.bo.INotificationBO;

/**
 * 
 * @author Infosys
 * Used for creating In Home out-bound notifications
 * for status changes and Closure  
 */
public class NotificationServiceValidator {

	private static final Logger LOGGER = Logger.getLogger(NotificationServiceValidator.class);
	private INotificationBO notificationBO;

	/**validate mandatory fields for closure notification
	 * @param inHomeDetails
	 * @return
	 */
	public String validateInHomeDetails(OrderUpdateRequest inHomeDetails,String serialNumberFlag) {
		
		LOGGER.info("Entering validateInHomeDetails");
		StringBuilder error = new StringBuilder(InHomeNPSConstants.VALIDATION_MESSAGE);
		StringBuilder invalid = new StringBuilder(InHomeNPSConstants.INVALID_MESSAGE);
		if(StringUtils.isBlank(inHomeDetails.getCorrelationId())){
			error.append(InHomeNPSConstants.CORRELATION_ERROR);
		}
		else if(inHomeDetails.getCorrelationId().length() > InHomeNPSConstants.EIGHT){
			invalid.append(InHomeNPSConstants.CORRELATION_INVALID);
		}
		if(StringUtils.isBlank(inHomeDetails.getOrderType())){
			error.append(InHomeNPSConstants.ORDERTYPE_ERROR);
		}
		else if(!InHomeNPSConstants.orderTypeList().contains(inHomeDetails.getOrderType())){
			invalid.append(InHomeNPSConstants.ORDERTYPE_INVALID);
		}
		if(StringUtils.isBlank(inHomeDetails.getUnitNum())){
			error.append(InHomeNPSConstants.UNITNUM_ERROR);
		}
		else if(StringUtils.isNumeric(inHomeDetails.getUnitNum())){
			if(inHomeDetails.getUnitNum().length() > InHomeNPSConstants.SEVEN){
				invalid.append(InHomeNPSConstants.UNITNUM_INVALID);
			}
		}
		else{
			invalid.append(InHomeNPSConstants.UNITNUM_NUMERIC);
		}
		if(StringUtils.isBlank(inHomeDetails.getOrderNum())){
			error.append(InHomeNPSConstants.ORDERNUM_ERROR);
		}
		else if(StringUtils.isNumeric(inHomeDetails.getOrderNum())){
			if(inHomeDetails.getOrderNum().length() > InHomeNPSConstants.EIGHT){
				invalid.append(InHomeNPSConstants.ORDER_NUMBER_INVALID);
			}
		}
		else{
			invalid.append(InHomeNPSConstants.ORDERNUM_NUMERIC);
		}
		if(StringUtils.isBlank(inHomeDetails.getRouteDate())){
			error.append(InHomeNPSConstants.ROUTEDATE_ERROR);
		}
		else if(StringUtils.isNumeric(inHomeDetails.getRouteDate())){
			if(inHomeDetails.getRouteDate().length() > InHomeNPSConstants.EIGHT){
				invalid.append(InHomeNPSConstants.ROUTEDATE_INVALID);
			}
		}
		else {
			invalid.append(InHomeNPSConstants.ROUTEDATE_INVALID);
		}
		if(StringUtils.isBlank(inHomeDetails.getTechId())){
			error.append(InHomeNPSConstants.TECHID_ERROR);
		}
		else if(inHomeDetails.getTechId().length() > InHomeNPSConstants.FIFTY){
			invalid.append(InHomeNPSConstants.TECHID_INVALID);
		}
		if(StringUtils.isBlank(inHomeDetails.getCallCd())){
			error.append(InHomeNPSConstants.CALLCODE_ERROR);
		}
		else if(!InHomeNPSConstants.callCodeList().contains(inHomeDetails.getCallCd())){
			invalid.append(InHomeNPSConstants.CALLCODE_INVALID);
		}
		if(StringUtils.isBlank(inHomeDetails.getServiceFromTime())){
			error.append(InHomeNPSConstants.FROM_TIME_ERROR);
		}
		/*else if(InHomeNPSConstants.SIX != inHomeDetails.getServiceFromTime().length()){
			invalid.append(InHomeNPSConstants.FROM_TIME_INVALID);
		}*/
		if(StringUtils.isBlank(inHomeDetails.getServiceToTime())){
			error.append(InHomeNPSConstants.TO_TIME_ERROR);
		}
		/*else if(InHomeNPSConstants.SIX != inHomeDetails.getServiceToTime().length()){
			invalid.append(InHomeNPSConstants.TO_TIME_INVALID);
		}*/
		if(StringUtils.isBlank(inHomeDetails.getApplBrand())){
			error.append(InHomeNPSConstants.BRAND_ERROR);
		}else if(StringUtils.isAlphanumeric(inHomeDetails.getApplBrand())){
			if(inHomeDetails.getApplBrand().length() > InHomeNPSConstants.TWELVE){
				invalid.append(InHomeNPSConstants.BRAND_INVALID);
			}
		}
		/*else{
			invalid.append(InHomeNPSConstants.BRAND_NUMERIC);
		}*/
		if(StringUtils.isBlank(inHomeDetails.getModelNum())){
			error.append(InHomeNPSConstants.MODEL_ERROR);
		}else if(StringUtils.isNotBlank(inHomeDetails.getModelNum())){
			if(inHomeDetails.getModelNum().length() > InHomeNPSConstants.TWENTY_FOUR){
				invalid.append(InHomeNPSConstants.MODEL_INVALID);
			}
		}
		/*else{
			invalid.append(InHomeNPSConstants.MODEL_NUMERIC);
		}*/
		
		if (StringUtils.isNotBlank(serialNumberFlag) && InHomeNPSConstants.ON.equalsIgnoreCase(serialNumberFlag))
		{
				//SL-21009 :Setting Serial Number for Call Close API
				if(StringUtils.isBlank(inHomeDetails.getSerialNum())){
					error.append(InHomeNPSConstants.SERIAL_ERROR);
				}else if(StringUtils.isNotBlank(inHomeDetails.getSerialNum())){
					if(inHomeDetails.getSerialNum().length() > InHomeNPSConstants.TWENTY){
						invalid.append(InHomeNPSConstants.SERIAL_INVALID);
					}
				}
		}
		if(StringUtils.isBlank(inHomeDetails.getTechComments())){
			error.append(InHomeNPSConstants.TECHCOMMENTS_ERROR);
		}
		//mandatory job code fields
		if(null != inHomeDetails.getJobCodeData()){
			JobCodeData job = inHomeDetails.getJobCodeData();
			if(StringUtils.isBlank(job.getJobCalcPrice())){
				error.append(InHomeNPSConstants.JOB_PRICE_ERROR);
			}
			else if(!Pattern.matches(InHomeNPSConstants.PRICE_REGEX, job.getJobCalcPrice())){
				invalid.append(InHomeNPSConstants.JOB_PRICE_INVALID);
			}
			if(StringUtils.isBlank(job.getJobChargeCd())){
				error.append(InHomeNPSConstants.JOB_CHARGECD_ERROR);
			}
			else if(job.getJobChargeCd().length() > InHomeNPSConstants.ONE){
				invalid.append(InHomeNPSConstants.JOB_CHARGECD_INVALID);
			}
			if(StringUtils.isBlank(job.getJobCode())){
				error.append(InHomeNPSConstants.JOB_CODE_ERROR);
			}
			/*else if(InHomeNPSConstants.FIVE != job.getJobCode().length()){
				invalid.append(InHomeNPSConstants.JOB_CODE_INVALID);
			}*/
			if(StringUtils.isBlank(job.getJobCoverageCd())){
				error.append(InHomeNPSConstants.JOB_COVERAGECD_ERROR);
			}
			else if(!InHomeNPSConstants.coverageCodeList().contains(job.getJobCoverageCd())){
				invalid.append(InHomeNPSConstants.JOB_COVERAGECD_INVALID);
			}
			if(StringUtils.isBlank(job.getJobCodePrimaryFl())){
				error.append(InHomeNPSConstants.JOB_PRIMFL_ERROR);
			}
			else if(!InHomeNPSConstants.validateSet().contains(job.getJobCodePrimaryFl())){
				invalid.append(InHomeNPSConstants.JOB_PRIMFL_INVALID);
			}
			if(StringUtils.isBlank(job.getJobRelatedFl())){
				error.append(InHomeNPSConstants.JOB_RELFL_ERROR);
			}
			else if(!InHomeNPSConstants.validateSet().contains(job.getJobRelatedFl())){
				invalid.append(InHomeNPSConstants.JOB_RELFL_INVALID);
			}
		}
		else{
			error.append(InHomeNPSConstants.JOB_DATA_ERROR);
		}
		
		//mandatory part fields
		if(null != inHomeDetails.getPartsDatas() && !inHomeDetails.getPartsDatas().isEmpty()){
			int count = 0;
			for(com.servicelive.notification.beans.PartsDatas part : inHomeDetails.getPartsDatas()){
				count ++ ;
				if(StringUtils.isBlank(part.getPartDivNo())){
					error.append(InHomeNPSConstants.PART_DIV_NO + count + InHomeNPSConstants.SEQ_COM);
				}
				else if(part.getPartDivNo().length() > InHomeNPSConstants.THREE){
					invalid.append(InHomeNPSConstants.PART_DIV_NO + count+ InHomeNPSConstants.PART_DIV_INVALID);
				}
				if(StringUtils.isBlank(part.getPartPlsNo())){
					error.append(InHomeNPSConstants.PART_PLS_NO + count + InHomeNPSConstants.SEQ_COM);
				}
				else if(part.getPartPlsNo().length() > InHomeNPSConstants.THREE){
					invalid.append(InHomeNPSConstants.PART_PLS_NO + count + InHomeNPSConstants.PART_SOURCE_INVALID);
				}
				if(StringUtils.isBlank(part.getPartPartNo())){
					error.append(InHomeNPSConstants.PART_NO + count + InHomeNPSConstants.SEQ_COM);
				}
				else if(part.getPartPartNo().length() > InHomeNPSConstants.TWENTY_FOUR){
					invalid.append(InHomeNPSConstants.PART_NO + count + InHomeNPSConstants.PART_NO_INVALID);
				}
				/*if(StringUtils.isBlank(part.getPartOrderQty())){
					error.append(InHomeNPSConstants.PART_ORDER_QTY + count + InHomeNPSConstants.SEQ_COM);				
				}
				else if(StringUtils.isNumeric(part.getPartOrderQty())){
					if(part.getPartOrderQty().length() > InHomeNPSConstants.THREE){
						invalid.append(InHomeNPSConstants.PART_ORDER_QTY + count + InHomeNPSConstants.PART_QTY_INVALID);
					}
				}
				else {
					invalid.append(InHomeNPSConstants.PART_ORDER_QTY + count + InHomeNPSConstants.PART_QTY_NUMERIC);
				}*/
				if(StringUtils.isBlank(part.getPartInstallQty())){
					error.append(InHomeNPSConstants.PART_INSTALL_QTY + count + InHomeNPSConstants.SEQ_COM);
				}
				else if(StringUtils.isNumeric(part.getPartInstallQty())){
					if(part.getPartInstallQty().length() > InHomeNPSConstants.THREE){
						invalid.append(InHomeNPSConstants.PART_INSTALL_QTY + count + InHomeNPSConstants.PART_QTY_INVALID);
					}
				}
				else {
					invalid.append(InHomeNPSConstants.PART_INSTALL_QTY + count + InHomeNPSConstants.PART_QTY_NUMERIC);
				}
				if(StringUtils.isBlank(part.getPartLocation())){
					error.append(InHomeNPSConstants.PART_LOCATION + count + InHomeNPSConstants.SEQ_COM);
				}
				else if(!InHomeNPSConstants.partLocationList().contains(part.getPartLocation())){
					invalid.append(InHomeNPSConstants.PART_LOCATION + count + InHomeNPSConstants.PART_LOCATION_INVALID);
				}
				if(StringUtils.isBlank(part.getPartCoverageCode())){
					error.append(InHomeNPSConstants.PART_COVERAGE_CODE + count + InHomeNPSConstants.SEQ_COM);
				}
				else if(part.getPartCoverageCode().length()>InHomeNPSConstants.TWO){
					invalid.append(InHomeNPSConstants.PART_COVERAGE_CODE + count + InHomeNPSConstants.PART_COVERAGE_INVALID);
				}
				if(StringUtils.isBlank(part.getPartPrice())){
					error.append(InHomeNPSConstants.PART_PRICE + count + InHomeNPSConstants.SEQ_COM);
				}
				else if(!Pattern.matches(InHomeNPSConstants.PRICE_REGEX, part.getPartPrice())){
					invalid.append(InHomeNPSConstants.PART_PRICE + count + InHomeNPSConstants.PART_PRICE_INVALID);
				}
			}
		}
	
		String errorInfo = error.toString();
		String invalidInfo = invalid.toString();
		LOGGER.info("Leaving validateInHomeDetails");
		if(InHomeNPSConstants.VALIDATION_MESSAGE.equalsIgnoreCase(errorInfo) &&
				InHomeNPSConstants.INVALID_MESSAGE.equalsIgnoreCase(invalidInfo)){
			return InHomeNPSConstants.VALIDATION_SUCCESS;
		}
		else{
			String result = "";
			if(!InHomeNPSConstants.VALIDATION_MESSAGE.equalsIgnoreCase(errorInfo)){
				errorInfo = errorInfo.substring(0, error.toString().length()-2);
			}else{
				errorInfo = InHomeNPSConstants.NO_DATA;
			}
			if(!InHomeNPSConstants.INVALID_MESSAGE.equalsIgnoreCase(invalidInfo)){
				invalidInfo = invalidInfo.substring(0, invalid.toString().length()-2);
			}else{
				invalidInfo = InHomeNPSConstants.NO_DATA;
			}
			if(StringUtils.isNotBlank(errorInfo) && StringUtils.isNotBlank(invalidInfo)){
				result = errorInfo + InHomeNPSConstants.SEPERATOR + invalidInfo;
			}
			else if(StringUtils.isNotBlank(errorInfo)){
				result = errorInfo;
			}
			else if(StringUtils.isNotBlank(invalidInfo)){
				result = invalidInfo;
			}
			
			return result;
		}
	}

	
	/** Description:Validate mandatory request parameters for web service
	 * @param requestDetails
	 * @return
	 */
	public String validateDetails(RequestInHomeMessageDetails requestDetails) {
		StringBuilder error = new StringBuilder(InHomeNPSConstants.VALIDATION_MESSAGE);
		StringBuilder invalidData = new StringBuilder(InHomeNPSConstants.INVALID_DATA);
	    if(null!= requestDetails && (StringUtils.isBlank(requestDetails.getOrderType())||(!InHomeNPSConstants.orderTypeList().contains(requestDetails.getOrderType())))){
        	error.append(InHomeNPSConstants.ORDER_TYPE_ERROR);
        }
        if(null!= requestDetails && StringUtils.isBlank(requestDetails.getUnitNum())){
        	error.append(InHomeNPSConstants.UNIT_NUMBER_ERROR);
        }else if(StringUtils.isNumeric(requestDetails.getUnitNum())){
        	if(requestDetails.getUnitNum().length() > InHomeNPSConstants.UNIT_NUMBER_LENGTH){
        		invalidData.append(InHomeNPSConstants.UNIT_NUMBER_INVALID_LENGTH);
        	}
        }else{
        	invalidData.append(InHomeNPSConstants.UNIT_NUMBER_INVALID);
        }
        if(null!= requestDetails && StringUtils.isBlank(requestDetails.getOrderNum())){
        	error.append(InHomeNPSConstants.ORDER_NUMBER_ERROR);
        }else if(StringUtils.isNumeric(requestDetails.getOrderNum())){
        	if(requestDetails.getOrderNum().length() > InHomeNPSConstants.ORDER_NUMBER_LENGTH){
        		invalidData.append(InHomeNPSConstants.ORDER_NUMBER_INVALID_LENGTH);
        	}
        }else{
        	invalidData.append(InHomeNPSConstants.ORDER_NUMBER_INVALID);
        }
        if(null!= requestDetails &&((StringUtils.isBlank(requestDetails.getFromFunction()))||(!InHomeNPSConstants.fromAndToFunction().contains(requestDetails.getFromFunction())))){
        	error.append(InHomeNPSConstants.FROM_FUNCTION_ERROR);
        }
        if(null!= requestDetails &&StringUtils.isNotBlank(requestDetails.getToFunction())){
        	error.append(InHomeNPSConstants.TO_FUNCTION_ERROR);
        }
        if(null!= requestDetails && StringUtils.isBlank(requestDetails.getMessage())){
        	error.append(InHomeNPSConstants.MESSAGE_ERROR);
        }else{
        	if(requestDetails.getMessage().trim().length() > 2000){
        		invalidData.append(InHomeNPSConstants.INVALID_MESSAGE_LENGTH);
        	}
        }
        if(null!= requestDetails && (StringUtils.isBlank(requestDetails.getEmpId())|| (!StringUtils.isAlphanumeric(requestDetails.getEmpId())))){
        	error.append(InHomeNPSConstants.EMP_ID_ERROR);
        }else if(InHomeNPSConstants.EMP_ID_LENGTH < requestDetails.getEmpId().length()){
        	invalidData.append(InHomeNPSConstants.INVALID_EMP_ID_LENGTH);
        }
		String errorInfo = error.toString();
		String validDataResult = invalidData.toString();
		if(InHomeNPSConstants.VALIDATION_MESSAGE.equalsIgnoreCase(errorInfo)
				&& InHomeNPSConstants.INVALID_DATA.equalsIgnoreCase(validDataResult)){
			return InHomeNPSConstants.VALIDATION_SUCCESS;
		}
		else{
			String result = "";
			if(!InHomeNPSConstants.VALIDATION_MESSAGE.equalsIgnoreCase(errorInfo)){
				errorInfo = errorInfo.substring(0, error.toString().length()-2);
			}else{
				errorInfo = InHomeNPSConstants.NO_DATA;
			}
			if(!InHomeNPSConstants.INVALID_DATA.equalsIgnoreCase(validDataResult)){
				validDataResult = validDataResult.substring(0, invalidData.toString().length()-2);
			}else{
				validDataResult = InHomeNPSConstants.NO_DATA;
			}
			if(StringUtils.isNotBlank(errorInfo) && StringUtils.isNotBlank(validDataResult)){
				result = errorInfo + InHomeNPSConstants.SEPERATOR + validDataResult;
			}
			else if(StringUtils.isNotBlank(errorInfo)){
				result = errorInfo;
			}
			else if(StringUtils.isNotBlank(validDataResult)){
				result = validDataResult;
			}
			
			return result;
		}
	}

	public INotificationBO getNotificationBO() {
		return notificationBO;
	}

	public void setNotificationBO(INotificationBO notificationBO) {
		this.notificationBO = notificationBO;
	}

}