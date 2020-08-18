package com.newco.marketplace.business.iBusiness.serviceorder;

import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.dto.BuyerSOTemplateDTO;

public interface IBuyerSOTemplateBO {

	public String getBuyerSOTemplateDTOAsXML(BuyerSOTemplateDTO dto);
	
	public BuyerSOTemplateDTO getBuyerSOTemplateXMLAsDTO(String xml);
	
	public Boolean saveBuyerSOTemplate(Integer buyerID, String name, BuyerSOTemplateDTO dto);
	
	public Boolean updateBuyerSOTemplate(Integer buyerID, String name, BuyerSOTemplateDTO dto);
	
	public Boolean deleteBuyerSOTemplate(Integer buyerID, BuyerSOTemplateDTO dto);
	
	public BuyerSOTemplateDTO loadBuyerSOTemplate(Integer buyerID, String templateName);

	public BuyerSOTemplateDTO loadBuyerSOTemplate(Integer templateId);
	
	/**
	 * This method loads teamplate data for the given template id & buyer id
	 * @param templateId
	 * @param buyerId
	 * @return BuyerSOTemplateVO
	 * @throws BusinessServiceException
	 */
	public BuyerSOTemplateDTO loadBuyerSOTemplate(Integer templateId,Integer buyerId)throws BusinessServiceException;
	/**
	 * This method updates docList in so template, when document is deleted from Document Manager
	 * @param buyerId
	 * @param docTitle
	 * @return 
	 * @throws BusinessServiceException
	 */
	public void updateDocListInBuyerSoTemplate(Integer buyerId,String docTitle) throws BusinessServiceException;
}
