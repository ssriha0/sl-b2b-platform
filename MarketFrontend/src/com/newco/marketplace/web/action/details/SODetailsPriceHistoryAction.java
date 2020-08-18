package com.newco.marketplace.web.action.details;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.so.price.SOPriceHistoryResponse;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.priceHistory.SoPriceHistoryDTO;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.web.action.base.SLDetailsBaseAction;
import com.newco.marketplace.web.dto.ServiceOrderDTO;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.Preparable;
import com.servicelive.sodetailspricehistory.services.SoDetailsPricehistoryService;

public class SODetailsPriceHistoryAction extends SLDetailsBaseAction implements Preparable{
	
	private static final long serialVersionUID = 2244714831562190481L;
	private static final Logger logger = Logger.getLogger(SODetailsPriceHistoryAction.class.getName());
	private SoDetailsPricehistoryService soDetailsPricehistoryService;
	ServiceOrderDTO soDTO=null;
	SoPriceHistoryDTO priceHistorytabDto=null;
	SOPriceHistoryResponse responseAsObject=null;
	String soId=null;
	String infoLevel=null;
	String buyerId=null;
	
	/**
	 * prepare
	 */
	@SuppressWarnings("unchecked")
	public void prepare() throws Exception {
		createCommonServiceOrderCriteria();
	 // This is only used to get the order level price history
	    infoLevel = SO_LEVEL_PRICE_HISTORY.toString();
		 //setting url parameters for api
		 SecurityContext context=get_commonCriteria().getSecurityContext();
		 //SL-19820
		 /*if(StringUtils.isNotBlank((String)getSession().getAttribute(OrderConstants.SO_ID))){
			 soId = (String)getSession().getAttribute(OrderConstants.SO_ID);
		 }*/
		soId = getParameter("soId");
		setAttribute(SO_ID, soId);
		String msg = (String)getSession().getAttribute(Constants.SESSION.SOD_MSG+"_"+soId);
		getSession().removeAttribute(Constants.SESSION.SOD_MSG+"_"+soId);
		setAttribute(Constants.SESSION.SOD_MSG, msg);
		 if(null!=context){
			 buyerId=context.getCompanyId().toString();
		 }
	}
	
	/**
	 * Exceute
	 */
	public String execute() throws Exception {
		logger.debug("Inside SODetailsPriceHistoryAction.execute()");	
		getRequest().removeAttribute("priceHistTabDto");
		populateDTO();
		return SUCCESS;		
	}
	
	
	/**
	 * This Method will call the rest client for fetching price history API and 
	 * set into one session attribute
	 */
	private void populateDTO() {
		logger.debug("Inside populateDto");
		if(StringUtils.isNotBlank(soId)&& StringUtils.isNotBlank(buyerId)&& StringUtils.isNotBlank(infoLevel)){
			responseAsObject=soDetailsPricehistoryService.getpriceHistoryList(buyerId, soId, infoLevel);
		 
			// This screen is only for order level pricing 
			priceHistorytabDto=createPriceHistoryTabDto(priceHistorytabDto);
			soDetailsPricehistoryService.mapResponseObjectToDto(responseAsObject,priceHistorytabDto);
			if(null != priceHistorytabDto.getSoLevelHistoryVo() && !priceHistorytabDto.getSoLevelHistoryVo().isEmpty()){
				logger.debug("setting tabDto in session");
				//remove attribute from request
				 getRequest().removeAttribute("priceHistTabDto");
				 getRequest().setAttribute("priceHistTabDto",priceHistorytabDto);} 
			else{
				logger.info("1- removing price history tab details from dto");
				getRequest().removeAttribute("priceHistTabDto");
			}
			    
			}
		}
	
	
	private SoPriceHistoryDTO createPriceHistoryTabDto(SoPriceHistoryDTO priceHistorytab) {
		if(null==priceHistorytab){
			priceHistorytabDto = new SoPriceHistoryDTO();
			priceHistorytabDto.setPricingType(OrderConstants.SO_LEVEL_PRICING);
			 }
		return priceHistorytabDto;
	}

	/*private SoPriceHistoryDTO setTimezoneInDto() {
		priceHistorytabDto = new SoPriceHistoryDTO();
		if (StringUtils.isNotBlank(soDTO.getServiceLocationTimeZone())) {
			priceHistorytabDto.setTimeZone(soDTO.getServiceLocationTimeZone());
		} else {
			priceHistorytabDto.setTimeZone(DEFAULT_SERVICE_LOCATION_TIMEZONE);
		}
		
		return priceHistorytabDto;
	}
    */
	public SoDetailsPricehistoryService getSoDetailsPricehistoryService() {
		return soDetailsPricehistoryService;
	}
	public void setSoDetailsPricehistoryService(
			SoDetailsPricehistoryService soDetailsPricehistoryService) {
		this.soDetailsPricehistoryService = soDetailsPricehistoryService;
	}


		
		
		

}
