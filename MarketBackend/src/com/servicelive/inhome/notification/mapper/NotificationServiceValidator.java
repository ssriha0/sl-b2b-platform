package com.servicelive.inhome.notification.mapper;

import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.business.iBusiness.inhomeoutbound.constants.InHomeNPSConstants;
import com.newco.marketplace.inhomeoutboundnotification.beans.JobCodeData;
import com.newco.marketplace.inhomeoutboundnotification.beans.OrderUpdateRequest;
import com.newco.marketplace.inhomeoutboundnotification.beans.RequestInHomeDetails;


/**
 * 
 * @author Infosys
 * Used for creating In Home out-bound notifications
 * for sub status changes  
 */
public class NotificationServiceValidator {
   
	/**@Description:Validate mandatory request parameters for web service for send message
	 * @param requestDetails
	 * @return
	 */
	public String validateDetails(RequestInHomeDetails requestDetails) {
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
        	if(InHomeNPSConstants.MESSAGE_LENGTH < requestDetails.getMessage().trim().length() ){
        		invalidData.append(InHomeNPSConstants.INVALID_MESSAGE_LENGTH);
        	}
        }
        if(null!= requestDetails && (StringUtils.isBlank(requestDetails.getEmpId())|| (!StringUtils.isAlphanumeric(requestDetails.getEmpId())))){
        	error.append(InHomeNPSConstants.EMP_ID_ERROR);
        }else if(InHomeNPSConstants.EMP_ID_LENGTH < requestDetails.getEmpId().length()){
        	invalidData.append(InHomeNPSConstants.INVALID_EMP_ID_LENGTH);
        }
		String result = error.toString();
		String validDataResult=invalidData.toString();
		if(InHomeNPSConstants.VALIDATION_MESSAGE.equalsIgnoreCase(result)
				&& InHomeNPSConstants.INVALID_DATA.equalsIgnoreCase(validDataResult)){
			return InHomeNPSConstants.VALIDATION_SUCCESS;
		}else if(InHomeNPSConstants.VALIDATION_MESSAGE.equalsIgnoreCase(result)
				&&(!(InHomeNPSConstants.INVALID_DATA.equalsIgnoreCase(validDataResult)))){
			       validDataResult=validDataResult.substring(0,validDataResult.toString().length()-2);
			       result="";
		}else if(InHomeNPSConstants.INVALID_DATA.equalsIgnoreCase(validDataResult)
				&&(!(InHomeNPSConstants.VALIDATION_MESSAGE.equalsIgnoreCase(result)))){
			       result = result.substring(0, error.toString().length()-2);
			       validDataResult="";
		}else{
				   result = result.substring(0, error.toString().length()-2);
		           validDataResult=validDataResult.substring(0,validDataResult.toString().length()-2);
		          }
			result=result + InHomeNPSConstants.SPACE + validDataResult;
			return result;
		
	}
	
	
	/**R12_0 :validate mandatory fields of Service Operations API request, for SO Revisit Needed
	 * @param inHomeDetails
	 * @return
	 */
	public String validateInHomeDetails(OrderUpdateRequest inHomeDetails) {
		
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
		//Validating whether Call Code is either 13,15 or16
		else if(!InHomeNPSConstants.callCodeRevisitList().contains(inHomeDetails.getCallCd())){
			invalid.append(InHomeNPSConstants.CALLCODE_RESCHD_INVALID);
		}
		else {
		//Validating Job Code Data if Call Code is 15 or 16.
		if(InHomeNPSConstants.RESCHD_PARTS_CALLCODE.equals(inHomeDetails.getCallCd()) || InHomeNPSConstants.RESCHD_CALLCODE.equals(inHomeDetails.getCallCd())){
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
			
			if(StringUtils.isBlank(inHomeDetails.getReschdDate())){
				error.append(InHomeNPSConstants.RESCHDATE_ERROR);
			}
			else if(StringUtils.isNumeric(inHomeDetails.getReschdDate())){
				if(inHomeDetails.getRouteDate().length() > InHomeNPSConstants.EIGHT){
					invalid.append(InHomeNPSConstants.RESCHDATE_INVALID);
				}
			}
			else {
				invalid.append(InHomeNPSConstants.RESCHDATE_INVALID);
			}
			
			if(StringUtils.isBlank(inHomeDetails.getReschdFromTime())){
				error.append(InHomeNPSConstants.FROM_RESCHD_TIME_ERROR);
			}
		
			if(StringUtils.isBlank(inHomeDetails.getReschdToTime())){
				error.append(InHomeNPSConstants.TO_RESCHD_TIME_ERROR);
			}
		}
		}
		
		
		if(StringUtils.isBlank(inHomeDetails.getServiceFromTime())){
			error.append(InHomeNPSConstants.FROM_TIME_ERROR);
		}
	
		if(StringUtils.isBlank(inHomeDetails.getServiceToTime())){
			error.append(InHomeNPSConstants.TO_TIME_ERROR);
		}
		
	
		String errorInfo = error.toString();
		String invalidInfo = invalid.toString();
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
}