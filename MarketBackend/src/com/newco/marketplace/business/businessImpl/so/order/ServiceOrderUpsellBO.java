package com.newco.marketplace.business.businessImpl.so.order;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderUpsellBO;
import com.newco.marketplace.dto.vo.CryptographyVO;
import com.newco.marketplace.dto.vo.serviceorder.AdditionalPaymentVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderAddonVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.MPConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.iDao.so.order.IServiceOrderAdditionalPaymentDAO;
import com.newco.marketplace.persistence.iDao.so.order.IServiceOrderAddonDAO;
import com.newco.marketplace.util.Cryptography;
import com.newco.marketplace.util.Cryptography128;
import com.newco.marketplace.utils.ServiceLiveStringUtils;
import com.newco.marketplace.webservices.base.response.ProcessResponse;

public class ServiceOrderUpsellBO extends BaseOrderBO implements IServiceOrderUpsellBO {
	private static final Logger logger = Logger
			.getLogger(ServiceOrderUpsellBO.class.getName());

	private IServiceOrderAddonDAO serviceOrderAddonDAO;
	private IServiceOrderAdditionalPaymentDAO serviceOrderAdditionalPaymentDAO;
	private Cryptography cryptography;
	private Cryptography128 cryptography128;

	public ProcessResponse processCreateAddons(
			List<ServiceOrderAddonVO> soAddons) throws BusinessServiceException {
		ProcessResponse processResp = new ProcessResponse();
		List<String> errorMessages = new ArrayList<String>();
		try {
			getServiceOrderAddonDAO().createServiceOrderAddons(soAddons);
			processResp.setCode(VALID_RC);
			processResp.setMessage(VALID_MSG);
			logger.info("Service Order Addons - Created successful.");
		} catch (DataServiceException dataEx) {
			logger.error("Unexpected error occured while creating soAddons ",
					dataEx);
			processResp.setCode(SYSTEM_ERROR_RC);
			errorMessages.add(new String("Unexpected error occured while updating creating soAddons"));
			processResp.setMessages(errorMessages);
			processResp.setObj(OrderConstants.FAILED_SERVICE_ORDER_NO);
		}
		return processResp;
	}
	
	
	public ProcessResponse processUpdateAddonsInBatch(String soId, List<ServiceOrderAddonVO> updateAddons) 
	throws BusinessServiceException {
		ProcessResponse processResp = new ProcessResponse();
		List<String> errorMessages = new ArrayList<String>();
		List<ServiceOrderAddonVO> updateAddonsNew = new ArrayList<ServiceOrderAddonVO>();
		ServiceOrderAddonVO obj = new ServiceOrderAddonVO();
		obj.setSoId(soId);
		List<String> list = new ArrayList<String>();					
		for (ServiceOrderAddonVO vo : updateAddons) {
			list.add(vo.getSku());
		}		
		obj.setSkuList(list);

		try {
			Map<String, Boolean> resultMap = getServiceOrderAddonDAO().isAddonsSaved(obj);
			for(ServiceOrderAddonVO updateAddon : updateAddons){
				Boolean addonFound = resultMap.get(updateAddon.getSku());
				if(!addonFound){
					updateAddonsNew.add(updateAddon);
					
					if (logger.isDebugEnabled())
					  logger.debug("processUpdateAddonsInBatch: added for " + updateAddon.getSku());
				}
			}
			getServiceOrderAddonDAO().createServiceOrderAddons(updateAddonsNew);	
			processResp.setCode(VALID_RC);
			processResp.setMessage(VALID_MSG);
			logger.info("Service Order Addons - Batch update successful.");
		} catch (DataServiceException dataEx) {
			logger.error("Unexpected error occured while creating soAddons ",
					dataEx);
			processResp.setCode(SYSTEM_ERROR_RC);
			errorMessages.add(new String("Unexpected error occured while updating creating soAddons"));
			processResp.setMessages(errorMessages);
			processResp.setObj(OrderConstants.FAILED_SERVICE_ORDER_NO);
		}
		return processResp;
	}
	
	public ProcessResponse processUpdateAddons(List<ServiceOrderAddonVO> updateAddons) throws BusinessServiceException {
		ProcessResponse processResp = new ProcessResponse();
		List<String> errorMessages = new ArrayList<String>();
		List<ServiceOrderAddonVO> updateAddonsNew = new ArrayList<ServiceOrderAddonVO>();
		try {
				for(ServiceOrderAddonVO updateAddon : updateAddons){
					boolean addonFound = getServiceOrderAddonDAO().isAddonSaved(updateAddon);
					if(!addonFound){
						updateAddonsNew.add(updateAddon);
						if (logger.isDebugEnabled())
							logger.debug("processUpdateAddons: added for " + updateAddon.getSku());
					}
				}
				getServiceOrderAddonDAO().createServiceOrderAddons(updateAddonsNew);	
			processResp.setCode(VALID_RC);
			processResp.setMessage(VALID_MSG);
			logger.info("Service Order Addons - Created successful.");
		} catch (DataServiceException dataEx) {
			logger.error("Unexpected error occured while creating soAddons ",
					dataEx);
			processResp.setCode(SYSTEM_ERROR_RC);
			errorMessages.add(new String("Unexpected error occured while updating creating soAddons"));
			processResp.setMessages(errorMessages);
			processResp.setObj(OrderConstants.FAILED_SERVICE_ORDER_NO);
		}
		return processResp;
	}

	public List<ServiceOrderAddonVO> getAddonsbySoId(String soId) {

		List soUpsellSkus = null;
		try {
			soUpsellSkus = getServiceOrderAddonDAO().getUpsellALLSkusForSO(soId);
		} catch (DataServiceException dataEx) {
			logger.error(
					"Unexpected error occured while getting soAddons for SO: "
							+ soId, dataEx);
		}

		return soUpsellSkus;
	}
	
	/* (non-Javadoc)
	 * Fetches only required data from so_addon only if quantity > 0. This is used for add on price calculation.   
	 */
	public List<ServiceOrderAddonVO> getAddonswithQtybySoId(String soId) {

		List soUpsellSkus = null;
		try {
			soUpsellSkus = getServiceOrderAddonDAO().getUpsellSkuswithQtyForSO(soId);
		} catch (DataServiceException dataEx) {
			logger.error(
					"Unexpected error occured while getting soAddons for SO: "
							+ soId, dataEx);
		}

		return soUpsellSkus;
	}

	
		public void updateAddonsQty(List<ServiceOrderAddonVO> addons) {
		try {
			getServiceOrderAddonDAO().updateAddonQtys(addons);
		} catch (DataServiceException dataEx) {
			logger.error("Unexpected error occured while updating soAddons",
					dataEx);
		}
	}

	public void deleteUnpurchasedAddonsbySoId(String soId) {
		try {
			getServiceOrderAddonDAO().deleteUnpurchasedAddonsbySO(soId);
		} catch (DataServiceException dataEx) {
			logger.error(
					"Unexpected error occured while deleting unpurchased soAddons for SO:"
							+ soId, dataEx);
		}
	}

	public void insertPaymentInfobySoId(AdditionalPaymentVO paymentInfo) {
		
		if(paymentInfo != null){
			if(paymentInfo.getPaymentType().equals(OrderConstants.UPSELL_PAYMENT_TYPE_CHECK))
			{			
				getServiceOrderAdditionalPaymentDAO().insertCheckPaymentInfobySo(paymentInfo);
			}
			else
			{
				encryptCreditCard(paymentInfo);
				getServiceOrderAdditionalPaymentDAO().insertCreditCardPaymentInfobySo(paymentInfo);
			}
		}else{
			logger.error("insertPaymentInfobySoId() error for SO:"	+ paymentInfo.getSoId() + " paymentInfo parameter is null");			
		}
	}
	
	public void deleteAdditionalPaymentInfo(String soID){
		getServiceOrderAdditionalPaymentDAO().deleteAdditionalPaymentInfo(soID);
	}
	
	

	private void encryptCreditCard(AdditionalPaymentVO paymentInfo) {
		CryptographyVO cryptographyVO = new CryptographyVO();
		cryptographyVO.setInput(paymentInfo.getCardNo());
		//Commenting the code for SL-18789
		//cryptographyVO.setKAlias(MPConstants.ENCRYPTION_KEY);
		//cryptographyVO = cryptography.encryptKey(cryptographyVO);
		cryptographyVO.setKAlias(MPConstants.CC_ENCRYPTION_KEY);
		cryptographyVO = cryptography128.encryptKey128Bit(cryptographyVO);
		paymentInfo.setCardNo(cryptographyVO.getResponse());
	}

	private void decryptCreditCard(AdditionalPaymentVO paymentInfo,Boolean isSecuredInfoViewable) {
		CryptographyVO cryptographyVO = new CryptographyVO();
		cryptographyVO.setInput(paymentInfo.getCardNo());
		//Commenting the code for SL-18789
		//cryptographyVO.setKAlias(MPConstants.ENCRYPTION_KEY);
		//cryptographyVO = cryptography.decryptKey(cryptographyVO);
		cryptographyVO.setKAlias(MPConstants.CC_ENCRYPTION_KEY);
		cryptographyVO = cryptography128.decryptKey128Bit(cryptographyVO);
		
		paymentInfo.setCardNo(cryptographyVO.getResponse());
		if(!isSecuredInfoViewable){
			paymentInfo.setCardNo(ServiceLiveStringUtils.maskString(paymentInfo.getCardNo(), 4, "*"));
		}
	}

	public AdditionalPaymentVO getAdditionalPaymentInfo(String soId,Boolean isSecuredInfoViewable)
			throws BusinessServiceException {
		AdditionalPaymentVO paymentInfoVO = getServiceOrderAdditionalPaymentDAO()
				.getAdditionalPaymentInfo(soId);
    
		//SL-20853
		if(null != paymentInfoVO && StringUtils.isNotBlank(paymentInfoVO.getMaskedAccountNo())){
			logger.info("Masked Account Number is displaying:"+paymentInfoVO.getMaskedAccountNo());
			if (paymentInfoVO != null && paymentInfoVO.getCardNo() != null) {
				logger.info("Encrypted Credit card No required for edit so completion"+ paymentInfoVO.getCardNo());
				paymentInfoVO.setEncryptedCardNo(paymentInfoVO.getCardNo()); 
			}
			if(!isSecuredInfoViewable){
				paymentInfoVO.setCardNo(ServiceLiveStringUtils.maskString(paymentInfoVO.getMaskedAccountNo(), 4, "*"));
			}else{
				paymentInfoVO.setCardNo(paymentInfoVO.getMaskedAccountNo());
			}
		}
		else if (paymentInfoVO != null && paymentInfoVO.getCardNo() != null) {
			decryptCreditCard(paymentInfoVO, isSecuredInfoViewable);
		}
		return paymentInfoVO;
	}
	
	public AdditionalPaymentVO getAdditionalPaymentInfo(String soId) throws BusinessServiceException {
		return getAdditionalPaymentInfo(soId,Boolean.FALSE);
	}
	
	
	public IServiceOrderAddonDAO getServiceOrderAddonDAO() {
		return serviceOrderAddonDAO;
	}

	public void setServiceOrderAddonDAO(IServiceOrderAddonDAO serviceOrderAddonDAO) {
		this.serviceOrderAddonDAO = serviceOrderAddonDAO;
	}

	public IServiceOrderAdditionalPaymentDAO getServiceOrderAdditionalPaymentDAO() {
		return serviceOrderAdditionalPaymentDAO;
	}

	public void setServiceOrderAdditionalPaymentDAO(
			IServiceOrderAdditionalPaymentDAO serviceOrderAdditionalPaymentDAO) {
		this.serviceOrderAdditionalPaymentDAO = serviceOrderAdditionalPaymentDAO;
	}

	public Cryptography getCryptography() {
		return cryptography;
	}

	public void setCryptography(Cryptography cryptography) {
		this.cryptography = cryptography;
	}


	public Cryptography128 getCryptography128() {
		return cryptography128;
	}


	public void setCryptography128(Cryptography128 cryptography128) {
		this.cryptography128 = cryptography128;
	}

	
}
