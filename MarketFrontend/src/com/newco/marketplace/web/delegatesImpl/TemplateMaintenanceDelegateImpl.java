package com.newco.marketplace.web.delegatesImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.document.IDocumentBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IBuyerSOTemplateBO;
import com.newco.marketplace.dto.BuyerSOTemplateDTO;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.web.delegates.ITemplateMaintenanceDelegate;
import com.newco.marketplace.web.dto.TemplateMaintenanceDTO;

public class TemplateMaintenanceDelegateImpl implements ITemplateMaintenanceDelegate{

	private static final Logger logger = Logger.getLogger(TemplateMaintenanceDelegateImpl.class.getName());
	
	private IBuyerSOTemplateBO buyerSOTemplateBO;
	private IDocumentBO documentBO;
	
	public List<DocumentVO> getBuyerDocuments(Integer buyerId) {
		logger.debug("Entering TemplateMaintenanceDelegateImpl --> getBuyerDocuments() ");
		List<DocumentVO> documentList = null;
		try {
			documentList = getDocumentBO().retrieveDocumentsMetaDataByBuyerId(buyerId);
		} catch (BusinessServiceException e) {
			logger.error("Exception in getting buyer documents in TemplateMaintenanceDelegateImpl"
					+" due to "+e.getMessage());
		}
		logger.debug("Leaving TemplateMaintenanceDelegateImpl --> getBuyerDocuments() ");
		return documentList;
	}
	
	public boolean saveTemplate(Integer buyerId, TemplateMaintenanceDTO templateMaintenanceDTO) {
		logger.debug("Entering TemplateMaintenanceDelegateImpl --> saveTemplate() ");
		boolean result = false;
		if(templateMaintenanceDTO != null
				&& buyerId != null){
			String name = templateMaintenanceDTO.getTemplateName();
			BuyerSOTemplateDTO buyerSOTemplateDTO = mapTemplateMaintenanceToBuyerTemplateDto(templateMaintenanceDTO);
			
			result = getBuyerSOTemplateBO().saveBuyerSOTemplate(buyerId, name, buyerSOTemplateDTO);
		}
		logger.debug("Leaving TemplateMaintenanceDelegateImpl --> saveTemplate() ");
		return result;
	}

	private BuyerSOTemplateDTO mapTemplateMaintenanceToBuyerTemplateDto(TemplateMaintenanceDTO templateMaintenanceDTO){
		BuyerSOTemplateDTO buyerSOTemplateDTO = null;
				
		if(templateMaintenanceDTO != null){
			buyerSOTemplateDTO = new BuyerSOTemplateDTO();
			
			Integer selectedAltContact = templateMaintenanceDTO.getSelectedAltContact();
			if(selectedAltContact != null){
				buyerSOTemplateDTO.setAltBuyerContactId(selectedAltContact);				
			}			
			
			List<String> documentTitles = new ArrayList<String>();
			List<Integer> docList = templateMaintenanceDTO.getSelectedBuyerDocument();
			Map<Integer, String> buyerDocMap = templateMaintenanceDTO.getBuyerDocumentList();
			if(docList != null 
					&& docList.size() > 0
					&& buyerDocMap != null){
				for(int i=0;i<docList.size();i++){
					documentTitles.add(buyerDocMap.get(docList.get(i)));
				}
			}
			
			Integer buyerLogo = templateMaintenanceDTO.getSelectedBuyerLogo();
			Map<Integer, String> buyerLogoMap = templateMaintenanceDTO.getBuyerLogo();
			String buyerLogoName = null;
			//Changes for SL-17955-START
			if(buyerLogo != null 
					&& buyerLogoMap != null && MPConstants.NO_BUYER_LOGO_ID != buyerLogo){
				buyerLogoName = buyerLogoMap.get(buyerLogo);
			}
			//Changes for SL-17955-END
			buyerSOTemplateDTO.setDocumentTitles(documentTitles);
			buyerSOTemplateDTO.setMainServiceCategory(templateMaintenanceDTO.getMainServiceCategoryId().toString());
			buyerSOTemplateDTO.setOverview(templateMaintenanceDTO.getOverview());
			buyerSOTemplateDTO.setPartsSuppliedBy(templateMaintenanceDTO.getPartsSuppliedBy());
			buyerSOTemplateDTO.setSpecialInstructions(templateMaintenanceDTO.getSpecialInstructions());
			
			/** If both Spn are selected old/new we would consider old spn as default and set isNewSpn to false 
			 * As per Suganya , if Sears SPN is selected, it is oldSpn, hence isNewSpn is set FALSE
			 * if SPN is selected (new spn ) isNewSpn is set TRUE
			 * */
			if ((templateMaintenanceDTO.getSelectedSpn() != null && templateMaintenanceDTO.getSelectedSpn() == -1) 
					&& (templateMaintenanceDTO.getSearsSelectedSpn() != null && templateMaintenanceDTO.getSearsSelectedSpn() == -1) ){
				buyerSOTemplateDTO.setSpnId(null);
			}
			else{
				 if(templateMaintenanceDTO.getSearsSelectedSpn() != null && templateMaintenanceDTO.getSearsSelectedSpn() != -1){
					buyerSOTemplateDTO.setSpnId(templateMaintenanceDTO.getSearsSelectedSpn());
					buyerSOTemplateDTO.setIsNewSpn(Boolean.FALSE);
				 }else if(templateMaintenanceDTO.getSelectedSpn() != null && templateMaintenanceDTO.getSelectedSpn() != -1){
					buyerSOTemplateDTO.setSpnId(templateMaintenanceDTO.getSelectedSpn());
					buyerSOTemplateDTO.setIsNewSpn(Boolean.TRUE);
				}
				
			}
				
			buyerSOTemplateDTO.setSpnPercentageMatch(templateMaintenanceDTO.getSpnPercentageMatch());
			buyerSOTemplateDTO.setTerms(templateMaintenanceDTO.getBuyerTandC());
			buyerSOTemplateDTO.setTitle(templateMaintenanceDTO.getTemplateName());	
			buyerSOTemplateDTO.setDocumentLogo(buyerLogoName);
			buyerSOTemplateDTO.setConfirmServiceTime(templateMaintenanceDTO.getConfirmServiceTime());
			buyerSOTemplateDTO.setAutoAccept(templateMaintenanceDTO.getAutoAccept());
			buyerSOTemplateDTO.setAutoAcceptDays(templateMaintenanceDTO.getAutoAcceptDays());
			buyerSOTemplateDTO.setAutoAcceptTimes(templateMaintenanceDTO.getAutoAcceptTimes());
			
		}
		return buyerSOTemplateDTO;
	}
	
	public BuyerSOTemplateDTO getTemplateDetails(Integer templateId) {
		BuyerSOTemplateDTO buyerSOTemplateDTO = null;
		if(templateId != null){
			buyerSOTemplateDTO = getBuyerSOTemplateBO().loadBuyerSOTemplate(templateId);
		}		
		return buyerSOTemplateDTO;
	}
	
	public boolean updateTemplate(Integer buyerId, TemplateMaintenanceDTO templateMaintenanceDTO) {
		logger.debug("Entering TemplateMaintenanceDelegateImpl --> updateTemplate() ");
		boolean result = false;
		if(templateMaintenanceDTO != null
				&& buyerId != null){
			String name = templateMaintenanceDTO.getTemplateName();
			BuyerSOTemplateDTO buyerSOTemplateDTO = mapTemplateMaintenanceToBuyerTemplateDto(templateMaintenanceDTO);
			
			result = getBuyerSOTemplateBO().updateBuyerSOTemplate(buyerId, name, buyerSOTemplateDTO);
		}
		logger.debug("Leaving TemplateMaintenanceDelegateImpl --> updateTemplate() ");
		return result;
	}
	
	public Map<Integer, String> getBuyerLogoDocuments(Integer buyerId, Integer categoryId, Integer roleId, Integer userId){
		logger.debug("Entering TemplateMaintenanceDelegateImpl --> getBuyerLogoDocuments() ");
		Map<Integer, String> buyerLogo = new TreeMap<Integer, String>();
		try {
			ArrayList<DocumentVO> brandingInfoListVO = (ArrayList)getDocumentBO().retrieveBuyerDocumentsByBuyerIdAndCategory(buyerId, categoryId, roleId, userId);
						
			for(DocumentVO document: brandingInfoListVO){
				buyerLogo.put(document.getDocumentId(), document.getTitle());
			}
			brandingInfoListVO = null;
		} catch (BusinessServiceException e) {
			logger.error("Error occured when getting buyer logo ");
		}
		
		logger.debug("Entering TemplateMaintenanceDelegateImpl --> getBuyerLogoDocuments() ");
		return buyerLogo;
	}
	

	
	/**
	 * @return the buyerSOTemplateBO
	 */
	public IBuyerSOTemplateBO getBuyerSOTemplateBO() {
		return buyerSOTemplateBO;
	}

	/**
	 * @param buyerSOTemplateBO the buyerSOTemplateBO to set
	 */
	public void setBuyerSOTemplateBO(IBuyerSOTemplateBO buyerSOTemplateBO) {
		this.buyerSOTemplateBO = buyerSOTemplateBO;
	}

	/**
	 * @return the documentBO
	 */
	public IDocumentBO getDocumentBO() {
		return documentBO;
	}

	/**
	 * @param documentBO the documentBO to set
	 */
	public void setDocumentBO(IDocumentBO documentBO) {
		this.documentBO = documentBO;
	}



	
	
}
