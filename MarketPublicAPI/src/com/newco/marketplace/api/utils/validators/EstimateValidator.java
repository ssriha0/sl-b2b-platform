package com.newco.marketplace.api.utils.validators;

import java.text.DecimalFormat;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.business.iBusiness.mobile.IMobileGenericBO;
import com.newco.marketplace.dto.vo.EstimateVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.mobile.constants.MPConstants;

/**
 * The class acts as the entry point for the validation done for
 * add estimate web service 
 * 
 */
public class EstimateValidator {

	private Logger LOGGER = Logger.getLogger(EstimateValidator.class);
	private IMobileGenericBO mobileGenericBO;
    
	/** Method to validate estimate price and estimate id in the request
	 * @param estimateVO
	 * @return
	 * @throws BusinessServiceException
	 */
	public EstimateVO validateAddEstimateRequest(EstimateVO estimateVO) throws BusinessServiceException{
		ResultsCode resultCode = ResultsCode.SUCCESS;
		//validating the estimationId exists or not
		if(null!= estimateVO &&null!= estimateVO.getEstimationId()){
			estimateVO = validateEstimationIdStaus(estimateVO);
			if(null!=estimateVO.getIsEstimateIdExist() && estimateVO.getIsEstimateIdExist()){
				//validate price details in the Request
				boolean isValidPrice = validateEstimatePrice(estimateVO);
				if(!isValidPrice){
					resultCode = ResultsCode.INVALID_ESTIMATE_PRICE;
				}
			}else{
				resultCode = ResultsCode.INVALID_ESTIMATE_ID;
			}
		}
		//Validating price for Adding Estimate Flow.
		else{
			boolean isValidPrice = validateEstimatePrice(estimateVO);
			if(!isValidPrice){
				resultCode = ResultsCode.INVALID_ESTIMATE_PRICE;
			 }
			}
		//Setting Results Code based on validation
		estimateVO.setApiResultCode(resultCode);
		return estimateVO;
		
	}
	
	
	/**@Description : This method will fetch the status of estimate and set it in EstimateVo
	 * @param estimateVO
	 * @return
	 * @throws BusinessServiceException
	 */
	private EstimateVO validateEstimationIdStaus(EstimateVO estimateVO) throws BusinessServiceException {
		String status = null;
		try{
			status = mobileGenericBO.validateEstimateStatus(estimateVO.getSoId(),estimateVO.getEstimationId());
			if(StringUtils.isNotBlank(status)){
				estimateVO.setIsEstimateIdExist(true);
				if(StringUtils.equalsIgnoreCase(PublicAPIConstant.DRAFT_CAPS,status)){
					if((StringUtils.isBlank(estimateVO.getIsDraftEstimate()) 
							|| StringUtils.equalsIgnoreCase(PublicAPIConstant.DRAFT_STATUS_NO, estimateVO.getIsDraftEstimate()))){
						estimateVO.setStatus(PublicAPIConstant.NEW);
					  }
				}
			}
		}catch (Exception e) {
			LOGGER.error("Exception in validating Estimate Status for the service order Estimate"+e);
			throw new BusinessServiceException(e);
		}
		return estimateVO;
	}
	/**
	 * @Description Method to validate whether estimation id is present in the db for a service order
	 * @param soId
	 * @param estimationId
	 * @return boolean
	 * @throws BusinessServiceException 
	 */
	public Boolean validateEstimationId(String soId,Integer estimationId) throws BusinessServiceException{
		Boolean isEstimationIdExist =null;
		if(StringUtils.isNotBlank(soId) && null !=estimationId){
			isEstimationIdExist=mobileGenericBO.isEstimationIdExists(soId,estimationId);
		}
		return isEstimationIdExist;
	}
	
	/**@Description Method to validate estimate Price is valid after checking labor price,parts price,other estimate service price,discount,tax
	 * @param estimateVO
	 * @return
	 * @throws BusinessServiceException 
	 */
	private boolean validateEstimatePrice(EstimateVO estimateVO) throws BusinessServiceException {
		boolean isValidPrice = false;
		Double totalprice = 0.0;
		Double totalLaborPrice = 0.0;
		Double totalPartPrice = 0.0;
		Double totalOtherServicePrice=0.0;
		Double discountPrice = 0.0;
		Double taxPrice = 0.0;
		Double totalPricecalc = 0.0;
		DecimalFormat df = new DecimalFormat(MPConstants.ROUND_OFF_TWO_DECIMAL); 
		try{
			//Commenting code,to be implemented later after getting confirmation
			/*if(null!= estimateVO.getEstimateTasks() && !estimateVO.getEstimateTasks().isEmpty()){
				for(EstimateTaskVO taskVO : estimateVO.getEstimateTasks() ){
					if(null!= taskVO.getQuantity() && null!= taskVO.getUnitPrice()){
						totalLaborPrice = totalprice + (taskVO.getQuantity()*taskVO.getUnitPrice());
					}
				}
			}
			if(null!= estimateVO.getEstimateParts() && !estimateVO.getEstimateParts().isEmpty()){
				for(EstimateTaskVO taskVO : estimateVO.getEstimateParts() ){
					if(null!= taskVO.getQuantity() && null!= taskVO.getUnitPrice()){
						totalPartPrice = totalprice + (taskVO.getQuantity()*taskVO.getUnitPrice());
					}
				}
			}*/
			totalprice = estimateVO.getTotalPrice();
			LOGGER.info("Total Price from  Request"+ totalprice);
			totalLaborPrice = estimateVO.getTotalLaborPrice();
			totalPartPrice = estimateVO.getTotalPartsPrice();
			totalOtherServicePrice=estimateVO.getTotalOtherServicePrice();
			discountPrice = estimateVO.getDiscountedAmount();
			taxPrice = estimateVO.getTaxPrice();
			totalPricecalc = totalLaborPrice + totalPartPrice+totalOtherServicePrice-discountPrice+taxPrice;
			LOGGER.info("Total Price before formatting"+ totalPricecalc);
			totalPricecalc = Double.valueOf(df.format(totalPricecalc));
			LOGGER.info("Total Price After formatting"+ totalPricecalc);
			if(null!= totalprice && null!= totalPricecalc && totalPricecalc.doubleValue() == totalprice.doubleValue() ){
				isValidPrice = true;
			}
			
		}catch (Exception e) {
			LOGGER.error("Exception in validating Estimation Price"+ e);
			throw new BusinessServiceException(e);
		}
		return isValidPrice;
	}
    
	public IMobileGenericBO getMobileGenericBO() {
		return mobileGenericBO;
	}

	public void setMobileGenericBO(IMobileGenericBO mobileGenericBO) {
		this.mobileGenericBO = mobileGenericBO;
	}
	
}
 