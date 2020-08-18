/**
 * 
 */
package com.newco.marketplace.web.delegatesImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.buyersearch.IBuyerSearchBO;
import com.newco.marketplace.dto.vo.buyersearch.BuyerResultForAdminVO;
import com.newco.marketplace.dto.vo.buyersearch.AgentVisibilityWidgetVO;
import com.newco.marketplace.dto.vo.serviceorder.CriteriaMap;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.web.delegates.AdminBuyerSearchDelegate;
import com.newco.marketplace.web.dto.AdminSearchFormDTO;
import com.newco.marketplace.web.dto.AdminSearchResultsAllDTO;
import com.newco.marketplace.web.dto.AdminSearchResultsDTO;
import com.newco.marketplace.web.dto.ProviderWidgetResultsDTO;
import com.newco.marketplace.web.utils.SLStringUtils;

/**
 * @author HOZA
 *
 */
public class AdminBuyerSearchDelegateImpl implements AdminBuyerSearchDelegate {

	private static final Logger logger = Logger.getLogger("AdminBuyerSearchDelegateImpl");
	private IBuyerSearchBO buyerSearchBO;

	/**
	 * 
	 */
	public AdminBuyerSearchDelegateImpl() {
		super();
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.AdminBuyerSearchDelegate#getSearchResultsForAdmin(com.newco.marketplace.web.dto.AdminSearchFormDTO, com.newco.marketplace.dto.vo.serviceorder.CriteriaMap)
	 */
	public AdminSearchResultsAllDTO getSearchResultsForAdmin(AdminSearchFormDTO searchCriteria, CriteriaMap criteriaMap){
		logger.debug("Entering getSearchResultsForAdmin in AdminBuyerSearchDelegateImpl.");
		BuyerResultForAdminVO criteria = new BuyerResultForAdminVO();

		if (SLStringUtils.isNullOrEmpty(searchCriteria.getBuyerId()))
		{
			criteria.setBuyer_id(null);
		}else{
			try{
				if(org.apache.commons.lang.StringUtils.isNumeric(searchCriteria.getBuyerId())){
					criteria.setBuyer_id(Integer.parseInt(searchCriteria.getBuyerId()));
				}else{
					criteria.setBuyer_id(Integer.parseInt("-1"));
				}
			}
			catch(Exception bse){
				//Swallo this exception... purely BS.. I want to throw this one.
				logger.error("Recieve bad number from the Search criteria ", bse);
			}

		}
		if (SLStringUtils.isNullOrEmpty(searchCriteria.getBuyer_businessName())){
			criteria.setBusinessName(null);
		}else{
			criteria.setBusinessName(searchCriteria.getBuyer_businessName());
		}

		if (SLStringUtils.isNullOrEmpty(searchCriteria.getBuyer_city()))
		{
			criteria.setCity(null);
		}else{
			criteria.setCity(searchCriteria.getBuyer_city());
		}
		if (SLStringUtils.isNullOrEmpty(searchCriteria.getBuyer_email()))
		{
			criteria.setEmail(null);
		}else{
			criteria.setEmail(searchCriteria.getBuyer_email());
		}
		if (SLStringUtils.isNullOrEmpty(searchCriteria.getBuyer_orderNumber())){
			criteria.setSoId(null);
		}else{
			criteria.setSoId(searchCriteria.getBuyer_orderNumber());
		}
		if (SLStringUtils.isNullOrEmpty(searchCriteria.getBuyer_phone())){
			criteria.setPhone(null);
		}else{
			criteria.setPhone(searchCriteria.getBuyer_phone());
		}
		if (SLStringUtils.isNullOrEmpty(searchCriteria.getBuyer_state()) ||
				searchCriteria.getBuyer_state().trim().equals("-1")
		)
		{
			criteria.setState(null);
		}else
		{
			criteria.setState(searchCriteria.getBuyer_state());
		}
		if (SLStringUtils.isNullOrEmpty(searchCriteria.getBuyer_username()))
		{
			criteria.setUsername(null);
		}else{
			criteria.setUsername(searchCriteria.getBuyer_username());
		}
		if (SLStringUtils.isNullOrEmpty(searchCriteria.getBuyer_zip()))
		{
			criteria.setZip(null);
		}else{
			criteria.setZip(searchCriteria.getBuyer_zip());
		}

		if (SLStringUtils.isNullOrEmpty(searchCriteria.getBuyer_resourceId()))
		{
			criteria.setResourceId(null);
		}else{
			try{
				if(org.apache.commons.lang.StringUtils.isNumeric(searchCriteria.getBuyer_resourceId())){
					criteria.setResourceId(Integer.parseInt(searchCriteria.getBuyer_resourceId()));
				}else{
					criteria.setResourceId(Integer.parseInt("-1"));
				}	
			}
			catch(Exception bse){
				logger.error("Recieve bad number from the Search criteria ", bse);
			}

		}




		List<BuyerResultForAdminVO> buyers = new ArrayList<BuyerResultForAdminVO>();
		AdminSearchResultsAllDTO allResults = new AdminSearchResultsAllDTO();
		try {
			buyers = buyerSearchBO.getBuyerListForAdmin(criteria, criteriaMap);
			logger.debug("Got the list of buyers." + " bUYERS FROM  DB = " + buyers.size());
			// Map the returned VO to the DTO
			List<AdminSearchResultsDTO> results = new ArrayList<AdminSearchResultsDTO>();
			logger.debug("About to map VO to the DTO lists.");

			for (BuyerResultForAdminVO buyer : buyers) {
				AdminSearchResultsDTO result = new AdminSearchResultsDTO();

				result.setId(buyer.getBuyer_id().toString());
				result.setResourceId(buyer.getResourceId().toString());
				result.setUserName(buyer.getUsername());
				result.setRoleId(buyer.getRoleId().toString());
				result.setFundingType(buyer.getFundingType());
				result.setName(buyer.getBusinessName());
				result.setPrimaryIndustry(buyer.getPrimaryIndustry());
				result.setMarket(buyer.getMarketName());
				result.setState(buyer.getState());
				result.setZip(buyer.getZip());
				result.setLastActivityDate(buyer.getLastActivityDate());
				result.setCity(buyer.getCity());
				String cFn = SLStringUtils.isNullOrEmpty(buyer.getContactfirstName())? " " : buyer.getContactfirstName();
				String cLn = SLStringUtils.isNullOrEmpty(buyer.getContactLastName())? " " :buyer.getContactLastName();
				if((cFn.trim().length() > 0   )&& (cLn.trim().length() > 0) ){
					String buyerName = cLn + ", " + cFn;
					result.setBuyerName(buyerName);
				}
				results.add(result);
			}

			allResults.setTooManyRows(false);

			allResults.setListOfProviders(results);
			allResults.setNumberOfRows(results.size());

			logger.debug("Completed mapping VO to DTO lists.  Returning results. Got buyers list size "+results.size());
		}catch(BusinessServiceException bse){
			logger.error("Could not retrieve a list of matching providers - database error. ", bse);
			bse.printStackTrace();	
		}

		return allResults;
	}


	public AdminSearchResultsDTO getSearchResultsForAdminWidget(String soId, Integer buyerId){
		logger.debug("Entering getSearchResultsForAdminWidget in AdminBuyerSearchDelegateImpl.");
		BuyerResultForAdminVO criteria = new BuyerResultForAdminVO();

		try{
			criteria.setBuyer_id(buyerId);
		}
		catch(Exception bse){
			logger.error("Recieve bad number from the Search criteria ", bse);
		}

		if (SLStringUtils.isNullOrEmpty(soId)){
			criteria.setSoId(null);
		}else{
			criteria.setSoId(soId);
		}



		BuyerResultForAdminVO buyer = new BuyerResultForAdminVO();
		AdminSearchResultsDTO result = new AdminSearchResultsDTO();



		try {
			buyer = buyerSearchBO.getBuyerDetails(criteria);
			logger.debug("Got the details of buyers." );
			// Map the returned VO to the DTO
			result = new AdminSearchResultsDTO();

			result.setId(buyer.getBuyer_id().toString());
			result.setResourceId(buyer.getResourceId().toString());
			result.setUserName(buyer.getUsername());
			result.setRoleId(buyer.getRoleId().toString());
			result.setFundingType(buyer.getFundingType());
			result.setName(buyer.getBusinessName());
			result.setPrimaryIndustry(buyer.getPrimaryIndustry());
			result.setMarket(buyer.getMarketName());
			result.setState(buyer.getState());
			result.setZip(buyer.getZip());
			result.setLastActivityDate(buyer.getLastActivityDate());
			result.setCity(buyer.getCity());
			String cFn = SLStringUtils.isNullOrEmpty(buyer.getContactfirstName())? " " : buyer.getContactfirstName();
			String cLn = SLStringUtils.isNullOrEmpty(buyer.getContactLastName())? " " :buyer.getContactLastName();
			if((cFn.trim().length() > 0   )&& (cLn.trim().length() > 0) ){
				String buyerName = cLn + ", " + cFn;
				result.setBuyerName(buyerName);
			}

			logger.debug("Completed mapping VO to DTO lists.");
		}catch(BusinessServiceException bse){
			logger.error("Could not retrieve a list of matching providers - database error. ", bse);
			bse.printStackTrace();	
		}

		return result;
	}

	public ProviderWidgetResultsDTO getInfoForProviderWidget(String strSoID,boolean isBuyer,Integer buyerId) {

		logger
		.debug("Entering getSearchResultsForAgentWidget in AdminBuyerSearchDelegateImpl.");
		ProviderWidgetResultsDTO objProviderWidgetResultsDTO = null;
		AgentVisibilityWidgetVO objAgentVisibilityWidgetVO = null;
		try {
			objAgentVisibilityWidgetVO = buyerSearchBO
			.getSearchResultsForProWidget(strSoID,isBuyer,buyerId);
			if (objAgentVisibilityWidgetVO != null) {
				objProviderWidgetResultsDTO = new ProviderWidgetResultsDTO();

				if (objAgentVisibilityWidgetVO.getStrProviderCount() != null
						&& !"".equalsIgnoreCase(objAgentVisibilityWidgetVO
								.getStrProviderCount()))
				{
					if("0".equalsIgnoreCase(objAgentVisibilityWidgetVO.getStrProviderCount()))
					{
						objProviderWidgetResultsDTO.setProviderCount("N/A");
					}
					else
					{
						objProviderWidgetResultsDTO
						.setProviderCount(objAgentVisibilityWidgetVO
								.getStrProviderCount());
					}
				}
				if (objAgentVisibilityWidgetVO.getStrFirmProviderCount() != null
						&& !"".equalsIgnoreCase(objAgentVisibilityWidgetVO
								.getStrFirmProviderCount()))
					if("0".equalsIgnoreCase(objAgentVisibilityWidgetVO.getStrFirmProviderCount()))
					{
						objProviderWidgetResultsDTO.setFirmProviderCount("N/A");
					}
					else
					{
						objProviderWidgetResultsDTO
						.setFirmProviderCount(objAgentVisibilityWidgetVO
								.getStrFirmProviderCount());
					}
				if (objAgentVisibilityWidgetVO.getServiceStartDate() != null)
				{
					objProviderWidgetResultsDTO
					.setAppointStartDate(objAgentVisibilityWidgetVO
							.getServiceStartDate());

				}
				if (objAgentVisibilityWidgetVO.getServiceEndDate() != null)
					objProviderWidgetResultsDTO
					.setAppointEndDate(objAgentVisibilityWidgetVO
							.getServiceEndDate());
				else
					objProviderWidgetResultsDTO
					.setAppointEndDate(objAgentVisibilityWidgetVO
							.getServiceStartDate());
				if (objAgentVisibilityWidgetVO.getServiceLocationTimezone() != null)
					objProviderWidgetResultsDTO
					.setServiceLocationTimezone(objAgentVisibilityWidgetVO
							.getServiceLocationTimezone());
				if (objAgentVisibilityWidgetVO.getServiceTimeEnd() != null)
					objProviderWidgetResultsDTO
					.setServiceTimeEnd(objAgentVisibilityWidgetVO
							.getServiceTimeEnd());
				else
					objProviderWidgetResultsDTO
					.setServiceTimeEnd("");
				if (objAgentVisibilityWidgetVO.getServiceTimeStart() != null)
				{
					objProviderWidgetResultsDTO
					.setServiceTimeStart(objAgentVisibilityWidgetVO
							.getServiceTimeStart());
					objProviderWidgetResultsDTO.setShowProgressBar("true");
				}
				objProviderWidgetResultsDTO.setSoId(objAgentVisibilityWidgetVO
						.getStrSoId());
				if (objAgentVisibilityWidgetVO.getColor() != null
						&& !"".equalsIgnoreCase(objAgentVisibilityWidgetVO
								.getColor()))
					objProviderWidgetResultsDTO.setColor(objAgentVisibilityWidgetVO
							.getColor());
				if (objAgentVisibilityWidgetVO.getPercentage() != null
						&& !"".equalsIgnoreCase(objAgentVisibilityWidgetVO
								.getPercentage()))
					objProviderWidgetResultsDTO
					.setPercentage(objAgentVisibilityWidgetVO
							.getPercentage());


			}
		} catch (BusinessServiceException bse) {
			logger
			.error(
					"Could not retrieve -ProviderWidget Details-AdminBuyerSearchDelegateImpl database error. ",
					bse);
			bse.printStackTrace();
		}
		return objProviderWidgetResultsDTO;
	}


	public IBuyerSearchBO getBuyerSearchBO() {
		return buyerSearchBO;
	}

	public void setBuyerSearchBO(IBuyerSearchBO buyerSearchBO) {
		this.buyerSearchBO = buyerSearchBO;
	}

}
