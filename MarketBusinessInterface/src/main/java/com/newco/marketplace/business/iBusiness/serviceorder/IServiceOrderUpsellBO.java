package com.newco.marketplace.business.iBusiness.serviceorder;

import java.util.List;

import com.newco.marketplace.dto.vo.serviceorder.AdditionalPaymentVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderAddonVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.webservices.base.response.ProcessResponse;

public interface IServiceOrderUpsellBO {

	public ProcessResponse processCreateAddons(	List<ServiceOrderAddonVO> soAddons) throws BusinessServiceException;
	
	public ProcessResponse processUpdateAddons(List<ServiceOrderAddonVO> updateAddons) throws BusinessServiceException;
	
	public ProcessResponse processUpdateAddonsInBatch(String soId, List<ServiceOrderAddonVO> updateAddons) throws BusinessServiceException;

	public List<ServiceOrderAddonVO> getAddonsbySoId(String soId);
	
	public List<ServiceOrderAddonVO> getAddonswithQtybySoId (String soId);

	public void updateAddonsQty(List<ServiceOrderAddonVO> addons);

	public void deleteUnpurchasedAddonsbySoId(String soId);

	public void insertPaymentInfobySoId(AdditionalPaymentVO paymentInfo);
	
	public AdditionalPaymentVO getAdditionalPaymentInfo(String soId,Boolean isSecuredInfoViewable) throws BusinessServiceException;
	
	public AdditionalPaymentVO getAdditionalPaymentInfo(String soId) throws BusinessServiceException;
	
	public void deleteAdditionalPaymentInfo(String soID);

}
