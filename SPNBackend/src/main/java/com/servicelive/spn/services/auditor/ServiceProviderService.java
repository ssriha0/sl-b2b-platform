package com.servicelive.spn.services.auditor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.SPNConstants;
import com.newco.marketplace.utils.DateUtils;
import com.servicelive.domain.lookup.LookupSPNWorkflowState;
import com.servicelive.domain.provider.ServiceProvider;
import com.servicelive.spn.auditor.vo.DocumentExpirationDetailsVO;
import com.servicelive.spn.dao.provider.ServiceProviderDao;
import com.servicelive.spn.services.BaseServices;
import com.servicelive.spn.services.interfaces.IServiceProviderService;

/**
 * 
 * @author svanloon
 *
 */
public class ServiceProviderService extends BaseServices implements IServiceProviderService {

	private ServiceProviderDao serviceProviderDao;
	
	/**
	 * 
	 * @param providerFirmId
	 * @return List<ServiceProvider>
	 */
	public List<ServiceProvider> findAdmin(Integer providerFirmId) {
		return serviceProviderDao.findAdmin(providerFirmId);
	}

	/**
	 * 
	 * @param spnId
	 * @param wfStates
	 * @return List
	 * @throws Exception
	 */
	public List<ServiceProvider> findBySPNProviderFirmStates(Integer spnId, List<LookupSPNWorkflowState> wfStates) throws Exception {
		return serviceProviderDao.findAdminsBySpnIdAndWfStates(spnId, wfStates);
	}

	@Override
	@SuppressWarnings("unused")
	protected void handleDates(Object entity) {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see com.servicelive.spn.services.auditor.IServiceProviderService#setServiceProviderDao(com.servicelive.spn.dao.provider.ServiceProviderDao)
	 */
	public void setServiceProviderDao(ServiceProviderDao serviceProviderDao) {
		this.serviceProviderDao = serviceProviderDao;
	}

	//fetches the list of spns of the provider for which notifications are send
	public Map<String,Integer> getSPNForProvider(Integer providerFirmId, Integer buyerId, Integer requirementType) throws BusinessServiceException{
		try{
			List<Object[]> providerSPN = serviceProviderDao.getSPNForProvider(providerFirmId, buyerId, requirementType);
			Map<String,Integer> providerSPNs = new TreeMap<String,Integer>();
			for(Object[] spn : providerSPN){
				String spnName = (String)spn[0];
				Integer spnId = (Integer)spn[1];
				providerSPNs.put(spnName, spnId);
			}
			return providerSPNs;
			
		}catch(DataServiceException e){
			logger.debug("Exception in ServiceProviderService.getSPNForProvider due to "+e.getMessage());
			throw new BusinessServiceException(e.getMessage(),e);
		}
	}

	//fetches the list of credentials for which notifications are send
	public Map<String,Integer> getRequirementType(Integer providerFirmId, Integer buyerId, Integer spnId) throws BusinessServiceException{
		try{
			List<Object[]> requirements = serviceProviderDao.getRequirementType(providerFirmId, buyerId, spnId);
			Map<String,Integer> requirementTypes = new TreeMap<String,Integer>();
			for(Object[] types : requirements){
				String credentialName = (String)types[0];
				Integer credentialInd = (Integer)types[1];
				requirementTypes.put(credentialName, credentialInd);
			}
			return requirementTypes;
			
		}catch(DataServiceException e){
			logger.debug("Exception in ServiceProviderService.getRequirementType due to "+e.getMessage());
			throw new BusinessServiceException(e.getMessage(),e);
		}
		
	}

	//fetches the details of expiration notifications
	public List<DocumentExpirationDetailsVO> getExpirationDetailsForProvider(Integer providerFirmId, Integer buyerId, Integer spnId,Integer requirementType) throws BusinessServiceException{
		try{
			List<Object[]> expiryDetails = serviceProviderDao.getExpirationDetailsForProvider(providerFirmId, buyerId, spnId, requirementType);
			List<DocumentExpirationDetailsVO> expirationDetails = new ArrayList<DocumentExpirationDetailsVO>();
			SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
			String today = format.format(new Date());
			
			for(Object[] details : expiryDetails){
				String credentialName = (String)details[0];
				String expirationDate = format.format((Date)details[1]);
				Integer notificationType = (Integer)details[2];
				Date notificationDate = (Date)details[3];								
				
				//getting the no. of days for expiration
				Long expiresIn = DateUtils.getExactDaysBetweenDates(format.parse(today), format.parse(expirationDate));
				
				DocumentExpirationDetailsVO expirationDetailsVO = new DocumentExpirationDetailsVO();							
				expirationDetailsVO.setRequirementType(credentialName);
				expirationDetailsVO.setExpirationDate(format.parse(expirationDate));
				expirationDetailsVO.setExpiresIn(expiresIn.intValue());
				expirationDetailsVO.setAction(notificationType.toString() + SPNConstants.DAYS_NOTICE_SENT);
				expirationDetailsVO.setNoticeSentOn(notificationDate);
				expirationDetails.add(expirationDetailsVO);
			}
			return expirationDetails;
			
		}
		catch(ParseException e){
			logger.debug("Exception in ServiceProviderService.getExpirationDetailsForProvider due to "+e.getMessage());
			throw new BusinessServiceException(e.getMessage(),e);
		}
		catch(DataServiceException e){
			logger.debug("Exception in ServiceProviderService.getExpirationDetailsForProvider due to "+e.getMessage());
			throw new BusinessServiceException(e.getMessage(),e);
		}
		
	}
		
}
