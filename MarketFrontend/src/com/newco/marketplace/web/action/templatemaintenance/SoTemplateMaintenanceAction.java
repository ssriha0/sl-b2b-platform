package com.newco.marketplace.web.action.templatemaintenance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.buyer.IBuyerFeatureSetBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.BuyerSOTemplateDTO;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.serviceorder.ContactLocationVO;
import com.newco.marketplace.dto.vo.skillTree.SkillNodeVO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.interfaces.BuyerConstants;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.delegates.IJobCodeDelegate;
import com.newco.marketplace.web.delegates.ISOWizardFetchDelegate;
import com.newco.marketplace.web.delegates.ISPNBuyerDelegate;
import com.newco.marketplace.web.delegates.ITemplateMaintenanceDelegate;
import com.newco.marketplace.web.dto.SOWAltBuyerContactDTO;
import com.newco.marketplace.web.dto.SPNLandingTableRowDTO;
import com.newco.marketplace.web.dto.TemplateMaintenanceDTO;
import com.newco.marketplace.web.utils.ObjectMapperWizard;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

public class SoTemplateMaintenanceAction extends SLBaseAction implements Preparable, ModelDriven<TemplateMaintenanceDTO>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1958664714991407630L;
	private static final Logger logger = Logger.getLogger(SoTemplateMaintenanceAction.class.getName());
	
	private ITemplateMaintenanceDelegate templateMaintenanceDelegate;
	private TemplateMaintenanceDTO templateMaintenanceDTO = new TemplateMaintenanceDTO();
	private IJobCodeDelegate jobCodeDelegate;
	private ISPNBuyerDelegate spnBuyerDelegate;
	private ISOWizardFetchDelegate soWizardFetchDelegate;
	private ArrayList<SkillNodeVO> skillTreeMainCat;
	private Map<Integer, String> partsSupplied;
	private IBuyerFeatureSetBO buyerFeatureSetBO;
	
	public void prepare() throws Exception {
		createCommonServiceOrderCriteria();
		loadMaps();
		loadBuyerTemplates();
		loadBuyerDocuments();
		loadBuyerLogo();
		
		boolean isSearsBuyer = isSearsBuyer(get_commonCriteria().getCompanyId());
		/* Load old/new spn list for Sears buyer
		  Load old spn list for Non Sears buyer */
		if(isSearsBuyer){
			loadSearsOldSpnList();
		}
		loadSpnList();
		loadAltContact();		
		templateMaintenanceDTO.setDisableAddTemplate(Boolean.TRUE);
		templateMaintenanceDTO.setIsSearsBuyer(isSearsBuyer);
	}

	private boolean isSearsBuyer(Integer buyerId) {
		/* Sears buyer means Sears RI */
		if (buyerId!= null && buyerId.intValue() == BuyerConstants.OMS_BUYER_ID.intValue()){
			return true;
		}else
			return false;
	}	

	public TemplateMaintenanceDTO getModel() {
		return templateMaintenanceDTO;
	}
	
	public String execute() throws Exception{
		logger.debug("Entering SoTemplateMaintenanceAction --> execute() ");
		getSession().removeAttribute("mainServiceCategory");
		getSession().removeAttribute("altBuyerContact");
		getSession().removeAttribute("buyerSpnList");
		getSession().removeAttribute("buyerLogoList");		
		loadMainServiceCategory();
		clearSelectedValueFromDTO();
		logger.debug("Leaving SoTemplateMaintenanceAction --> execute() ");
		return SUCCESS;
	}

	/**
	 * This method clears the selected value from DTO. This method is useful when someone selects
	 * add template option after selecting an exisitng template in edit template option.
	 */
	private void clearSelectedValueFromDTO() {
		templateMaintenanceDTO.setTemplateName(null);
		templateMaintenanceDTO.setMainServiceCategoryId(null);
		templateMaintenanceDTO.setPartsSuppliedBy(null);
		templateMaintenanceDTO.setSpecialInstructions(null);
		templateMaintenanceDTO.setSelectedSpn(null);
		templateMaintenanceDTO.setSpnPercentageMatch(null);
		templateMaintenanceDTO.setSelectedBuyerLogo(null);
		templateMaintenanceDTO.setConfirmServiceTime(null);
		templateMaintenanceDTO.setOverview(null);
		templateMaintenanceDTO.setBuyerTandC(null);
		templateMaintenanceDTO.setSpecialInstructions(null);
		templateMaintenanceDTO.setAutoAccept(Constants.AUTO_ACCEPT);
		templateMaintenanceDTO.setAutoAcceptDays(Constants.AUTO_ACCEPT_DAYS);
		templateMaintenanceDTO.setAutoAcceptTimes(Constants.AUTO_ACCEPT_TIMES);
		templateMaintenanceDTO.setSelectedDocuments(new HashMap<Integer, String>());
		templateMaintenanceDTO.setDisableAddTemplate(Boolean.FALSE);
		templateMaintenanceDTO.setSelectedAltContact(get_commonCriteria().getSecurityContext().getBuyerAdminContactId());
	}

	public String saveTemplate() throws Exception{
		logger.debug("Entering SoTemplateMaintenanceAction --> saveTemplate() ");
		templateMaintenanceDTO = getModel();
		Integer buyerId = get_commonCriteria().getCompanyId();
		ArrayList<SkillNodeVO> skillList = (ArrayList<SkillNodeVO>)getSession().getAttribute("mainServiceCategory");
		templateMaintenanceDTO.setMainServiceCategory(skillList);		
		templateMaintenanceDTO.setSelectedDocuments(new HashMap<Integer, String>());
		templateMaintenanceDTO.setDisableAddTemplate(Boolean.FALSE);
		loadBuyerLogo();
		boolean result = getTemplateMaintenanceDelegate().saveTemplate(buyerId, templateMaintenanceDTO);
		loadBuyerSelectedDocuments();
		if(result){
			getRequest().setAttribute("isTemplateError", "false");
			getRequest().setAttribute("templateMsg", "Template successfully saved");
		}else{
			getRequest().setAttribute("isTemplateError", "true");
			getRequest().setAttribute("templateMsg", "Error occured. Try again");
		}
		loadBuyerTemplates();
		clearSelectedValueFromDTO();
		logger.debug("Leaving SoTemplateMaintenanceAction --> saveTemplate() ");
		return SUCCESS;
	}
	
	public String updateTemplate() throws Exception{
		logger.debug("Entering SoTemplateMaintenanceAction --> updateTemplate() ");
		templateMaintenanceDTO = getModel();
		
		Integer buyerId = get_commonCriteria().getCompanyId();
		ArrayList<SkillNodeVO> skillList = (ArrayList<SkillNodeVO>)getSession().getAttribute("mainServiceCategory");
		templateMaintenanceDTO.setMainServiceCategory(skillList);
		templateMaintenanceDTO.setEditTemplate(Boolean.TRUE);
		loadBuyerLogo();
		boolean result = getTemplateMaintenanceDelegate().updateTemplate(buyerId, templateMaintenanceDTO);
		loadBuyerSelectedDocuments();
		if(result){
			getRequest().setAttribute("isTemplateError", "false");
			getRequest().setAttribute("templateMsg", "Template successfully updated");
		}else{
			getRequest().setAttribute("isTemplateError", "true");
			getRequest().setAttribute("templateMsg", "Error occured. Try again");
		}
		logger.debug("Leaving SoTemplateMaintenanceAction --> updateTemplate() ");
		return SUCCESS;
	}
	
	public String getTemplateDetails() throws Exception{
		logger.debug("Entering SoTemplateMaintenanceAction --> getTemplateDetails() ");
		
		Integer templateId = null;
		String value = getRequest().getParameter("templateId");
		if(StringUtils.isNotBlank(value)){
			templateId = Integer.parseInt(value); 
		}
		
		BuyerSOTemplateDTO buyerSOTemplateDTO = getTemplateMaintenanceDelegate().getTemplateDetails(templateId);				
		
		mapBuyerTemplateToTemplateMaintenanceDto(buyerSOTemplateDTO, templateId);
		ArrayList<SkillNodeVO> skillList = (ArrayList<SkillNodeVO>)getSession().getAttribute("mainServiceCategory");
		templateMaintenanceDTO.setMainServiceCategory(skillList);
		
		logger.debug("Leaving SoTemplateMaintenanceAction --> getTemplateDetails() ");
		return SUCCESS;
	}
	
	private TemplateMaintenanceDTO mapBuyerTemplateToTemplateMaintenanceDto(BuyerSOTemplateDTO 
			buyerSOTemplateDTO, Integer templateId){
		
		if(buyerSOTemplateDTO != null){		
			templateMaintenanceDTO.setBuyerTandC(buyerSOTemplateDTO.getTerms());
			templateMaintenanceDTO.setEditTemplate(Boolean.TRUE);
			String mainCatId = buyerSOTemplateDTO.getMainServiceCategory();
			if(StringUtils.isNotBlank(mainCatId)){
				templateMaintenanceDTO.setMainServiceCategoryId(Integer.parseInt(mainCatId));
			}
			templateMaintenanceDTO.setOverview(buyerSOTemplateDTO.getOverview());
			templateMaintenanceDTO.setPartsSuppliedBy(buyerSOTemplateDTO.getPartsSuppliedBy());
			templateMaintenanceDTO.setSelectedAltContact(buyerSOTemplateDTO.getAltBuyerContactId());
			templateMaintenanceDTO.setSelectedBuyerSOTemplate(templateId);
			
			if(buyerSOTemplateDTO.getIsNewSpn()== null ||
					(buyerSOTemplateDTO.getIsNewSpn()!= null && !buyerSOTemplateDTO.getIsNewSpn() )){
				templateMaintenanceDTO.setSearsSelectedSpn(buyerSOTemplateDTO.getSpnId());
			}else if(buyerSOTemplateDTO.getIsNewSpn()!= null && buyerSOTemplateDTO.getIsNewSpn()){
				templateMaintenanceDTO.setSelectedSpn(buyerSOTemplateDTO.getSpnId());
			}
			
			if(!templateMaintenanceDTO.getIsSearsBuyer()){
				templateMaintenanceDTO.setSelectedSpn(buyerSOTemplateDTO.getSpnId());
			}
			
			templateMaintenanceDTO.setSpecialInstructions(buyerSOTemplateDTO.getSpecialInstructions());
			templateMaintenanceDTO.setSpnPercentageMatch(buyerSOTemplateDTO.getSpnPercentageMatch());
			templateMaintenanceDTO.setTemplateName(buyerSOTemplateDTO.getTitle());
			templateMaintenanceDTO.setConfirmServiceTime(buyerSOTemplateDTO.getConfirmServiceTime());
			templateMaintenanceDTO.setAutoAccept(buyerSOTemplateDTO.getAutoAccept());
			templateMaintenanceDTO.setAutoAcceptDays(buyerSOTemplateDTO.getAutoAcceptDays());
			templateMaintenanceDTO.setAutoAcceptTimes(buyerSOTemplateDTO.getAutoAcceptTimes());
								
			String documentLogo = buyerSOTemplateDTO.getDocumentLogo();
			Integer selectedLogoId = null;
			
			Map<Integer, String> buyerDocsMap = templateMaintenanceDTO.getBuyerLogo();
			if(buyerDocsMap != null
					&& StringUtils.isNotBlank(documentLogo)){
				if(buyerDocsMap.containsValue(documentLogo)){
					Iterator setIterator = buyerDocsMap.keySet().iterator();
					while(setIterator.hasNext()){
						Integer key = (Integer)setIterator.next();
						if(buyerDocsMap.get(key).equals(documentLogo)){
							selectedLogoId = key;							
							break;
						}
					}
				}
			}
			//Changes for SL-17955-START
			if(selectedLogoId == null){
				selectedLogoId = MPConstants.NO_BUYER_LOGO_ID;
			}
			//Changes for SL-17955-END
			templateMaintenanceDTO.setSelectedBuyerLogo(selectedLogoId);
			Map<Integer, String> buyerDocs = templateMaintenanceDTO.getBuyerDocumentList();
			List<String> buyerList = buyerSOTemplateDTO.getDocumentTitles();
			Map<Integer, String> selectedBuyerDocs = new HashMap<Integer, String>(); 
			if(buyerList != null
					&& buyerList.size() > 0
					&& buyerDocs != null){
				for(int i=0;i<buyerList.size();i++){
					if(buyerDocs.containsValue(buyerList.get(i))){
						Iterator setIterator = buyerDocs.keySet().iterator();
						while(setIterator.hasNext()){
							Integer key = (Integer)setIterator.next();
							if(buyerDocs.get(key).equals(buyerList.get(i))){
								selectedBuyerDocs.put(key, buyerDocs.get(key));
								
								buyerDocs.remove(key);
								break;
							}
						}						
					}				
				}
			}
			templateMaintenanceDTO.setSelectedDocuments(selectedBuyerDocs);
			templateMaintenanceDTO.setBuyerDocumentList(buyerDocs);
		}
		return templateMaintenanceDTO;
	}
	
	private void loadBuyerSelectedDocuments(){
		Map<Integer, String> buyerDocs = templateMaintenanceDTO.getBuyerDocumentList();
		List<Integer> buyerList = templateMaintenanceDTO.getSelectedBuyerDocument();
		Map<Integer, String> selectedBuyerDocs = new HashMap<Integer, String>(); 
		if(buyerList != null
				&& buyerList.size() > 0
				&& buyerDocs != null){
			for(int i=0;i<buyerList.size();i++){
				if(buyerDocs.containsKey(buyerList.get(i))){
					selectedBuyerDocs.put(buyerList.get(i), buyerDocs.get(buyerList.get(i)));							
					buyerDocs.remove(buyerList.get(i));									
				}				
			}
		}
		templateMaintenanceDTO.setSelectedDocuments(selectedBuyerDocs);
		templateMaintenanceDTO.setBuyerDocumentList(buyerDocs);
	}
	
	private void loadMainServiceCategory(){
		try {
			skillTreeMainCat = getLookupManager().getSkillTreeMainCategories();		
			templateMaintenanceDTO.setMainServiceCategory(skillTreeMainCat);
			
			getSession().setAttribute("mainServiceCategory", skillTreeMainCat);			
		} catch (com.newco.marketplace.exception.core.BusinessServiceException e) {
			logger.error("Exception loading main skill category");
		}
	}
	
	private void loadBuyerTemplates(){
		Integer buyerId = get_commonCriteria().getCompanyId();
		Map<Integer, String> soTemplates = new HashMap<Integer, String>();
		try {
			soTemplates = getJobCodeDelegate().getSoTemplates(buyerId);
		} catch (BusinessServiceException e) {
			logger.error("Exception in loading buyer so templates in SoTemplateMaintenanceAction");
		}
		templateMaintenanceDTO.setBuyerSOTemplateList(soTemplates);
	}
	
	private void loadAltContact(){
		List<SOWAltBuyerContactDTO> altBuyerContacts = null;
		Integer buyerAdminContactId = null;
		
		if(getSession().getAttribute("altBuyerContact") != null){
			altBuyerContacts = (List<SOWAltBuyerContactDTO>)getSession().getAttribute("altBuyerContact");
		}else{
			ContactLocationVO contactVO = soWizardFetchDelegate.getBuyerResContLocDetails(get_commonCriteria().getCompanyId(), 
					get_commonCriteria().getVendBuyerResId());
			altBuyerContacts = ObjectMapperWizard.convertVOtoAltBuyerContacts(contactVO);
			
			if(altBuyerContacts == null){
				altBuyerContacts = new ArrayList<SOWAltBuyerContactDTO>();
			}
			getSession().setAttribute("altBuyerContact", altBuyerContacts); 
		}
		templateMaintenanceDTO.setAltBuyerContactList(altBuyerContacts);
	}
	
	private void loadBuyerDocuments(){
		Integer buyerId = get_commonCriteria().getCompanyId();
		Map<Integer, String> buyerDocuments = null;
		
		List<DocumentVO> documentVoList = null;
		buyerDocuments = new HashMap<Integer, String>();
		if(buyerId != null){
			documentVoList = getTemplateMaintenanceDelegate().getBuyerDocuments(buyerId);
		}
		
		if(documentVoList != null && documentVoList.size() > 0){
			for(int i=0;i<documentVoList.size();i++){
				DocumentVO documentVO = documentVoList.get(i);				
				buyerDocuments.put(documentVO.getDocumentId(), documentVO.getTitle());
			}
		}
		templateMaintenanceDTO.setBuyerDocumentList(buyerDocuments);
	}
	
	private void loadBuyerLogo(){
		Map<Integer, String> buyerLogos = null;
		if(getSession().getAttribute("buyerLogoList") != null){
			buyerLogos = (Map<Integer, String>)getSession().getAttribute("buyerLogoList");
		}else{
			buyerLogos = getTemplateMaintenanceDelegate().getBuyerLogoDocuments(
				get_commonCriteria().getCompanyId(),
				new Integer(Constants.DocumentTypes.CATEGORY.LOGO),
				get_commonCriteria().getRoleId(),
				get_commonCriteria().getCompanyId());
			
			if(buyerLogos == null){
				buyerLogos = new TreeMap<Integer, String>();
			}
			//Changes for SL-17955-START
			buyerLogos.put(MPConstants.NO_BUYER_LOGO_ID, MPConstants.NO_BUYER_LOGO);
			//Changes for SL-17955-END
			getSession().setAttribute("buyerLogoList", buyerLogos);
		}
		templateMaintenanceDTO.setBuyerLogo(buyerLogos);
	}
	
	private void loadMaps(){
		partsSupplied = new HashMap<Integer, String>();
		
		partsSupplied.put(3, "Not Applicable");
		partsSupplied.put(2, "Provider");
		partsSupplied.put(1, "Buyer");
		
		templateMaintenanceDTO.setPartsSuppliedByList(partsSupplied);
		
		
	}
	
	private void loadSpnList(){
		Integer buyerId = get_commonCriteria().getCompanyId();
		Map<Integer, String> buyerSpnList = null;
		if(getSession().getAttribute("buyerSpnList") != null){
			buyerSpnList = (Map<Integer, String>)getSession().getAttribute("buyerSpnList");
		}else{
			List<SPNLandingTableRowDTO> spnList = getSpnBuyerDelegate().getAllNetworkList(buyerId);
			buyerSpnList = new HashMap<Integer, String>();
			
			if(spnList != null){
				for(int i=0;i<spnList.size();i++){
					SPNLandingTableRowDTO landingTableRowDTO = spnList.get(i);
					String spnId = landingTableRowDTO.getId();
					if(StringUtils.isNotBlank(spnId)){
						buyerSpnList.put(Integer.parseInt(spnId), landingTableRowDTO.getName());
					}
				}
			}
			getSession().setAttribute("buyerSpnList", buyerSpnList);
		}
		templateMaintenanceDTO.setSpnList(buyerSpnList);
	}
	
	private void loadSearsOldSpnList(){
		Integer buyerId = get_commonCriteria().getCompanyId();
		Map<Integer, String> buyerSpnList = null;
		if(getSession().getAttribute("buyerSearsSpnList") != null){
			buyerSpnList = (Map<Integer, String>)getSession().getAttribute("buyerSearsSpnList");
		}else{
			List<SPNLandingTableRowDTO> spnList = getSpnBuyerDelegate().getExistingNetworkListByBuyer(buyerId);
			buyerSpnList = new HashMap<Integer, String>();
			
			if(spnList != null){
				for(int i=0;i<spnList.size();i++){
					SPNLandingTableRowDTO landingTableRowDTO = spnList.get(i);
					String spnId = landingTableRowDTO.getId();
					if(StringUtils.isNotBlank(spnId)){
						buyerSpnList.put(Integer.parseInt(spnId), landingTableRowDTO.getName());
					}
				}
			}
			getSession().setAttribute("buyerSearsSpnList", buyerSpnList);
		}
		templateMaintenanceDTO.setSearsSpnList(buyerSpnList);
	}
	
	/**
	 * @return the templateMaintenanceDelegate
	 */
	public ITemplateMaintenanceDelegate getTemplateMaintenanceDelegate() {
		return templateMaintenanceDelegate;
	}

	/**
	 * @param templateMaintenanceDelegate the templateMaintenanceDelegate to set
	 */
	public void setTemplateMaintenanceDelegate(
			ITemplateMaintenanceDelegate templateMaintenanceDelegate) {
		this.templateMaintenanceDelegate = templateMaintenanceDelegate;
	}

	/**
	 * @return the partsSupplied
	 */
	public Map<Integer, String> getPartsSupplied() {
		return partsSupplied;
	}

	/**
	 * @param partsSupplied the partsSupplied to set
	 */
	public void setPartsSupplied(Map<Integer, String> partsSupplied) {
		this.partsSupplied = partsSupplied;
	}

	/**
	 * @return the skillTreeMainCat
	 */
	public ArrayList<SkillNodeVO> getSkillTreeMainCat() {
		return skillTreeMainCat;
	}

	/**
	 * @param skillTreeMainCat the skillTreeMainCat to set
	 */
	public void setSkillTreeMainCat(ArrayList<SkillNodeVO> skillTreeMainCat) {
		this.skillTreeMainCat = skillTreeMainCat;
	}

	/**
	 * @return the jobCodeDelegate
	 */
	public IJobCodeDelegate getJobCodeDelegate() {
		return jobCodeDelegate;
	}

	/**
	 * @param jobCodeDelegate the jobCodeDelegate to set
	 */
	public void setJobCodeDelegate(IJobCodeDelegate jobCodeDelegate) {
		this.jobCodeDelegate = jobCodeDelegate;
	}

	/**
	 * @return the spnBuyerDelegate
	 */
	public ISPNBuyerDelegate getSpnBuyerDelegate() {
		return spnBuyerDelegate;
	}

	/**
	 * @param spnBuyerDelegate the spnBuyerDelegate to set
	 */
	public void setSpnBuyerDelegate(ISPNBuyerDelegate spnBuyerDelegate) {
		this.spnBuyerDelegate = spnBuyerDelegate;
	}

	/**
	 * @return the soWizardFetchDelegate
	 */
	public ISOWizardFetchDelegate getSoWizardFetchDelegate() {
		return soWizardFetchDelegate;
	}

	/**
	 * @param soWizardFetchDelegate the soWizardFetchDelegate to set
	 */
	public void setSoWizardFetchDelegate(
			ISOWizardFetchDelegate soWizardFetchDelegate) {
		this.soWizardFetchDelegate = soWizardFetchDelegate;
	}

	/**
	 * @return the buyerFeatureSetBO
	 */
	public IBuyerFeatureSetBO getBuyerFeatureSetBO() {
		return buyerFeatureSetBO;
	}

	/**
	 * @param buyerFeatureSetBO the buyerFeatureSetBO to set
	 */
	public void setBuyerFeatureSetBO(IBuyerFeatureSetBO buyerFeatureSetBO) {
		this.buyerFeatureSetBO = buyerFeatureSetBO;
	}

}
