package com.newco.marketplace.web.action.wizard.scopeofwork; 

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.newco.marketplace.business.iBusiness.buyer.IBuyerFeatureSetBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.BuyerSOTemplateForSkuDTO;
import com.newco.marketplace.dto.vo.BuyerSOTemplateForSkuVO;
import com.newco.marketplace.dto.vo.BuyerSkuCategoryVO;
import com.newco.marketplace.dto.vo.BuyerSkuTaskForSoVO;
import com.newco.marketplace.dto.vo.BuyerSkuVO;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.LuServiceTypeTemplateVO;
import com.newco.marketplace.dto.vo.SkillTreeForSkuVO;
import com.newco.marketplace.dto.vo.TaskForSkuVO;
import com.newco.marketplace.dto.vo.skillTree.ServiceTypesVO;
import com.newco.marketplace.dto.vo.skillTree.SkillNodeVO;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.util.LocationUtils;
import com.newco.marketplace.web.action.base.SLWizardBaseAction;
import com.newco.marketplace.web.action.wizard.ISOWAction;
import com.newco.marketplace.web.delegates.ISOWizardFetchDelegate;
import com.newco.marketplace.web.dto.IError;
import com.newco.marketplace.web.dto.SODocumentDTO;
import com.newco.marketplace.web.dto.SOTaskDTO;
import com.newco.marketplace.web.dto.SOWContactLocationDTO;
import com.newco.marketplace.web.dto.SOWError;
import com.newco.marketplace.web.dto.SOWPhoneDTO;
import com.newco.marketplace.web.dto.SOWPricingTabDTO;
import com.newco.marketplace.web.dto.SOWScopeOfWorkTabDTO;
import com.newco.marketplace.web.dto.SOWTabStateVO;
import com.newco.marketplace.web.dto.ServiceOrderWizardBean;
import com.newco.marketplace.web.dto.TabNavigationDTO;
import com.newco.marketplace.web.dto.ajax.AjaxResultsDTO;
import com.newco.marketplace.web.utils.ObjectMapper;
import com.newco.marketplace.web.utils.SOClaimedFacility;
import com.newco.marketplace.web.validator.sow.SOWSessionFacility;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * $Revision: 1.110 $ $Author: glacy $ $Date: 2008/05/02 21:23:26 $
 */

/*
 * Maintenance History: See bottom of file
 */
public class SOWScopeOfWorkAction extends SLWizardBaseAction implements Preparable, ISOWAction, ModelDriven<SOWScopeOfWorkTabDTO>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2539939686810116407L;
	private static final Logger logger = Logger.getLogger(SOWScopeOfWorkAction.class);
	private SOWScopeOfWorkTabDTO sowModel = new SOWScopeOfWorkTabDTO();
	private ArrayList<SkillNodeVO> skillTreeMainCat;
	private ArrayList<LookupVO> phoneTypes;
	private ArrayList<ServiceTypesVO> skillSelection = new ArrayList<ServiceTypesVO>();
	private ArrayList<SkillNodeVO> categorySelection = new ArrayList<SkillNodeVO>();
	private ArrayList<SkillNodeVO> subCategorySelection = new ArrayList<SkillNodeVO>();
	private HashMap<Integer, ArrayList<ServiceTypesVO>> skillSelectionMap;
	private HashMap<Integer, ArrayList<ServiceTypesVO>> categSkillSelectionMap;
	private HashMap<Integer, ArrayList<ServiceTypesVO>> subCategSkillSelectionMap;
	private IBuyerFeatureSetBO buyerFeatureSetBO;
	
	private Integer taskIndex = -1;
	private Integer delIndex = -1;
	//private Integer mainServiceCategoryId = -1;
	private String previous;
	private String next;
	String soID;

	public String getSoID() {
		return soID;
	}

	public void setSoID(String soID) {
		this.soID = soID;
	}

	/***************************************************************************
	 * Private Methods
	 **************************************************************************/

	private void createFirstTask()
	{
		sowModel = getModel();
		sowModel.addTask(new SOTaskDTO());

	}

	/*SL-19820
	 * private HashMap<Integer, ArrayList<ServiceTypesVO>> getSkillSelectionMap()
	{
		return (HashMap<Integer, ArrayList<ServiceTypesVO>>) getSession().getAttribute("skillSelectionMap");
	}

	private void setSkillSelectionMap(HashMap<Integer, ArrayList<ServiceTypesVO>> skillSelectionMap)
	{
		this.skillSelectionMap = skillSelectionMap;
	}*/
	
	private HashMap<Integer, ArrayList<ServiceTypesVO>> getSkillSelectionMap(String soId)
	{
		return (HashMap<Integer, ArrayList<ServiceTypesVO>>) getSession().getAttribute("skillSelectionMap_"+soId);
	}

	private void setSkillSelectionMap(HashMap<Integer, ArrayList<ServiceTypesVO>> skillSelectionMap)
	{
		this.skillSelectionMap = skillSelectionMap;
	}

	/*SL-19820
	 * private HashMap<Integer, ArrayList<ServiceTypesVO>> getCategSkillSelectionMap()
	{
		return (HashMap<Integer, ArrayList<ServiceTypesVO>>) getSession().getAttribute("categSkillSelectionMap");
	}

	private void setCategSkillSelectionMap(HashMap<Integer, ArrayList<ServiceTypesVO>> categSkillSelectionMap)
	{
		this.categSkillSelectionMap = categSkillSelectionMap;
	}
*/
	//SL-19820
	 private HashMap<Integer, ArrayList<ServiceTypesVO>> getCategSkillSelectionMap(String soId)
		{
			return (HashMap<Integer, ArrayList<ServiceTypesVO>>) getSession().getAttribute("categSkillSelectionMap_"+soId);
		}

	private void setCategSkillSelectionMap(HashMap<Integer, ArrayList<ServiceTypesVO>> categSkillSelectionMap)
		{
			this.categSkillSelectionMap = categSkillSelectionMap;
		}
	
	/*SL-19820
	 * private HashMap<Integer, ArrayList<ServiceTypesVO>> getSubCategSkillSelectionMap()
	{
		return (HashMap<Integer, ArrayList<ServiceTypesVO>>) getSession().getAttribute("subCategSkillSelectionMap");
	}

	private void setSubCategSkillSelectionMap(HashMap<Integer, ArrayList<ServiceTypesVO>> subCategSkillSelectionMap)
	{
		this.subCategSkillSelectionMap = subCategSkillSelectionMap;
	}*/
	
	private HashMap<Integer, ArrayList<ServiceTypesVO>> getSubCategSkillSelectionMap(String soId)
		{
			return (HashMap<Integer, ArrayList<ServiceTypesVO>>) getSession().getAttribute("subCategSkillSelectionMap_"+soId);
		}

	private void setSubCategSkillSelectionMap(HashMap<Integer, ArrayList<ServiceTypesVO>> subCategSkillSelectionMap)
		{
			this.subCategSkillSelectionMap = subCategSkillSelectionMap;
		}

	/***************************************************************************
	 * Public Methods
	 **************************************************************************/

	public void prepare() throws Exception
	{

		// Check the session to see if the application is currenlty in the
		// service order wizard workflow
		/*SL-19820
		 * if (getSession().getAttribute(OrderConstants.SERVICE_ORDER_WIZARD_INDICTATOR) == null)
		{*/
		if (getSession().getAttribute(OrderConstants.SERVICE_ORDER_WIZARD_INDICTATOR+"_"+getParameter(OrderConstants.SO_ID)) == null)
		{
			throw new Exception("Invalid entry point into Service Order Wizard.");

		}// end if

		createCommonServiceOrderCriteria();
		maps();
		//SL-19820
        String soId=getParameter(OrderConstants.SO_ID);
        //SL-19820 setting into request for accessing from jsp .
    	String entryTab = (String)getSession().getAttribute("entryTab_"+soId);
		setAttribute("entryTab", entryTab);
		
        Integer status=(Integer) getSession().getAttribute(OrderConstants.SOW_SERVICE_ORDER_STATUS_SOID+"_"+soId);
        setCurrentSOStatusCodeInRequest(status);
        setAttribute(OrderConstants.SO_ID,soId);
        this.soID=soId;
        
        String groupId = (String) getSession().getAttribute(OrderConstants.GROUP_ID+"_"+soId);
        setAttribute(OrderConstants.GROUP_ID,groupId);
        
		try
		{
			SOWScopeOfWorkTabDTO modelScopeDTO = (SOWScopeOfWorkTabDTO) SOWSessionFacility.getInstance().getTabDTO(
					OrderConstants.SOW_SOW_TAB);
			//SL-19820
			//String action = (String) getSession().getAttribute("actionType");
			String action = (String) getSession().getAttribute("actionType_"+soId);
			setAttribute("actionType",action);
			
			if (modelScopeDTO == null || (action != null && (action.equals("edit") || action.equals("copy"))))
			{
				createEntryPoint();
			}
			
			
		}
		catch (Exception e)
		{
			logger.info("Caught Exception and ignoring", e);
		}
        setAttribute("todaysDate", getDateString());
        if(null!= getModel()){
            setSelectedSkuCategory(getModel());
         }
	}
	

	/*Method to fetch sku category on the basis of buyer id for service order creation by sku*/
	public void loadSKUCategoiresAndSKU() throws DelegateException
	{
		Integer buyerId = get_commonCriteria().getSecurityContext().getCompanyId();
		List<BuyerSkuCategoryVO> buyerSkuCategoryList = fetchDelegate.fetchBuyerSkuCategories(buyerId);
		getSession().setAttribute("buyerSkuCategoryList",buyerSkuCategoryList);
		
		// Loading the screen for the first time or when the radio button is selected for 
		// creating order using SKU - remove the SKUs from the session.
		getSession().removeAttribute("buyerSkuNameForSkuList");
	
	}
	/*Ajax Method to fetch sku name on the basis of category id for service order creation by sku*/
	public String loadSKUNameByCategoryId() throws DelegateException
	{	
		Integer categoryId =Integer.parseInt(getParameter("categoryId"));
		List<BuyerSkuVO> buyerSkuNameList = fetchDelegate.fetchBuyerSkuNameByCategory(categoryId);
		//SL-19820
		setAttribute("selectedSkuCategoryId",categoryId);
		setAttribute("buyerSkuNameForSkuList",buyerSkuNameList);
		return SUCCESS;
		
	}
	/*Ajax Method to fetch detail on the basis of sku id for service order creation by sku*/
	public String loadSKUDetailsBySkuId() throws DelegateException{
		Integer nodeId;
		Integer serviceTypeTemplateId;
		BuyerSkuTaskForSoVO buyerSkuDetailBySkuId=null;
		Double skuBidPrice=0.0;
		SkillTreeForSkuVO buyerSkillTreeDetail=null;
		BuyerSOTemplateForSkuVO templateDetail=null;
		BuyerSOTemplateForSkuDTO templateDto = null;
		LuServiceTypeTemplateVO luServiceTemplate=null;
		TaskForSkuVO taskForSku=new TaskForSkuVO();
//		SOWScopeOfWorkTabDTO sowDto=new SOWScopeOfWorkTabDTO();
//		SOTaskDTO tasks=new SOTaskDTO();
//		List<SOTaskDTO> taskList = new ArrayList<SOTaskDTO>();
		Integer skuId =Integer.parseInt(getParameter("skuNameId"));
		String templateForSkuId=getParameter("templateSkuId");
		Integer docReqForSkuInd=Integer.parseInt(getParameter("oldTableLength"));
		//Getting the so model for sku and category
		sowModel = getModel();
		SOTaskDTO sku;
		ArrayList<SOTaskDTO> skuList = sowModel.getSkus();
		if (skuList != null && skuList.size() > 0){
			Iterator<SOTaskDTO> i = skuList.iterator();
			while (i.hasNext()){
				sku = i.next();
				sku.setIsFreshTask(false);
			}
		}
		//sowModel.addSku(new SOTaskDTO());
		// scopeOfWorkDTO.setDoFullValidation(true);
		if (sowModel.getErrors().size() == 0 && sowModel.getWarnings().size() == 0){
			//SL-19820
			//ServiceOrderWizardBean bean = SOWSessionFacility.getInstance().getSOWBean();
			ServiceOrderWizardBean bean = SOWSessionFacility.getInstance().getSOWBean(this.soID);
			SOWTabStateVO vo;
			vo = (SOWTabStateVO) bean.getTabStateDTOs().get(OrderConstants.SOW_SOW_TAB);
			vo.setTabErrorState("");
			vo.setTabWarningState("");

		}
		/*Fetching general information and document detail on the basis of template id*/
		if(templateForSkuId!=null){
		Integer templateSkuId =Integer.parseInt(templateForSkuId);
		templateDetail=fetchDelegate.fetchBuyerTemplateDetailBySkuId(templateSkuId);
			if (templateDetail != null) {
				templateDto = getBuyerSOTemplateXMLAsDTO(templateDetail.getTemplateData(),docReqForSkuInd);
				setAttribute("buyerTemplateDetailBySkuId",templateDto);
				//SL-19820
				//getSession().setAttribute("templateAltBuyerContactId",templateDto.getAltBuyerContactId());
				getSession().setAttribute("templateAltBuyerContactId_"+this.soID,templateDto.getAltBuyerContactId());
			} 
		}
		//Fetching details of selected sku id
		buyerSkuDetailBySkuId = fetchDelegate.fetchBuyerSkuDetailBySkuId(skuId);
		if(null != buyerSkuDetailBySkuId){
			logger.info("to be removed: taskComments:"+ buyerSkuDetailBySkuId.getTaskComments());
		}
		setAttribute("buyerSkuDetailBySkuId",buyerSkuDetailBySkuId);
		//Fetching the bid price of selected sku id for the labor price
		skuBidPrice=fetchDelegate.fetchBidPriceBySkuId(skuId);
		setAttribute("skuBidPrice",skuBidPrice);
		try{
			if(buyerSkuDetailBySkuId!=null){
				nodeId=buyerSkuDetailBySkuId.getCategoryNodeId();
				serviceTypeTemplateId=buyerSkuDetailBySkuId.getServiceTypeTemplateId();
				luServiceTemplate=fetchDelegate.fetchServiceTypeTemplate(serviceTypeTemplateId);
				setAttribute("luServiceTemplate",luServiceTemplate);
				buyerSkillTreeDetail=fetchDelegate.fetchSkillTreeDetailBySkuId(nodeId);
				//Setting skill id for sku 
				taskForSku.setSkillId(serviceTypeTemplateId);
		
		if(buyerSkillTreeDetail!=null){
			int skillLevelCount = buyerSkillTreeDetail.getLevel().intValue();
			//Getting main category,sub category and category if level 3
			if(skillLevelCount == OrderConstants.MAIN_SERVICE_CAT_SKILL_LEVEL){
				taskForSku.setMainServiceCatName(buyerSkillTreeDetail.getNodeName());
				taskForSku.setMainCategoryId(buyerSkillTreeDetail.getNodeId());
		}
		while(skillLevelCount > 1){
			Integer parentNode = buyerSkillTreeDetail.getParentNode();
			if(null != parentNode){
				SkillTreeForSkuVO parentSkill = fetchDelegate.fetchSkillTreeDetailBySkuId(parentNode);			
				if(OrderConstants.SUB_CAT_SKILL_LEVEL == skillLevelCount){
					//Set sub category and category names if level=3
					taskForSku.setTaskSubCatName(buyerSkillTreeDetail.getNodeName());
					taskForSku.setTaskCatName(parentSkill.getNodeName());
					taskForSku.setSubCategoryId(buyerSkillTreeDetail.getNodeId());
					taskForSku.setCategoryId(buyerSkillTreeDetail.getParentNode());
				}else if(OrderConstants.CATEGORY_SKILL_LEVEL == skillLevelCount){
					//Set category and main service category names if level=2
					taskForSku.setTaskCatName(buyerSkillTreeDetail.getNodeName());
					taskForSku.setMainServiceCatName(parentSkill.getNodeName());
					taskForSku.setCategoryId(buyerSkillTreeDetail.getNodeId());
					taskForSku.setMainCategoryId(buyerSkillTreeDetail.getRootNodeId());
				}
				
				buyerSkillTreeDetail.setNodeName(parentSkill.getNodeName());
				buyerSkillTreeDetail.setParentNode(parentSkill.getParentNode()) ;
				skillLevelCount--;
			}
			
		}
		//Setting attribute to use in jsp
		setAttribute("mainCatForSku", taskForSku.getMainServiceCatName());
		setAttribute("taskCatForSku",taskForSku.getTaskCatName());
		setAttribute("taskSubForSku",taskForSku.getTaskSubCatName());
		setAttribute("mainCatIdForSku", taskForSku.getMainCategoryId());
		setAttribute("CatIdForSku", taskForSku.getCategoryId());
		setAttribute("SubCatIdForSku", taskForSku.getSubCategoryId());
		setAttribute("SkillIdForSku", taskForSku.getSkillId());
		
		
		
		// Assigning fetched detail for the sku id to the SOWScopeOfWorkDTO to associate with service order.
		// Associating main category
//		sowDto.setMainServiceCategoryName(taskForSku.getMainServiceCatName());
//		// Associating details in the div section of wire frame
//		tasks.setCategory(taskForSku.getTaskCatName());
//		tasks.setSubCategory(taskForSku.getTaskSubCatName());
//		tasks.setTaskName(buyerSkuDetailBySkuId.getTaskName());
//		tasks.setComments(buyerSkuDetailBySkuId.getTaskComments());
//		tasks.setSkill(luServiceTemplate.getDescr());
	
			}
			}
		
}
		
		catch (Exception e) 
		{
			logger.error("Exception fetching details for the selected skuid");
		}
		return SUCCESS;
		}
		
	/**
	 * Convert a string of XML representing a BuyerSOTemplateForSkuDTO from XML to the object using xstream
	 */
	
	@SuppressWarnings("unchecked")
	public BuyerSOTemplateForSkuDTO getBuyerSOTemplateXMLAsDTO(String xml,Integer docReqForSkuInd) {
		Integer buyerLogo  =null;
		XStream xstream = new XStream(new DomDriver());
		BuyerSOTemplateForSkuDTO templateDto = null;
		BuyerSOTemplateForSkuDTO templateDetailDto = new BuyerSOTemplateForSkuDTO();
		SOWScopeOfWorkTabDTO sowDto=new SOWScopeOfWorkTabDTO();
		List<DocumentVO> attachments = new ArrayList<DocumentVO>();
		List<String> errorList = new ArrayList<String>();
		List<SODocumentDTO> documents = new ArrayList<SODocumentDTO>();
		Integer buyerId = get_commonCriteria().getSecurityContext().getCompanyId();
		sowModel = getModel();
		try {
			xstream.alias("buyerTemplate", BuyerSOTemplateForSkuDTO.class);
			//Mapping the information in XML to the attributes of class
			templateDto = (BuyerSOTemplateForSkuDTO)xstream.fromXML(xml);
			String title = templateDto.getTitle();
			String overview = templateDto.getOverview();
			String terms = templateDto.getTerms();
			String specialInstructions = templateDto.getSpecialInstructions();
			if(StringUtils.isNotBlank(templateDto.getDocumentLogo())){
				buyerLogo = fetchDelegate.retrieveBuyerLogDocumentId(templateDto.getDocumentLogo(), buyerId);
			}
			logger.info("to be removed: overview:"+ overview);
			logger.info("to be removed: terms:"+terms);
			logger.info("to be removed: specialInstructions:"+specialInstructions);
			//Setting this varible to set parts supplied by in parts section
			Integer partsSuppliedBy = templateDto.getPartsSuppliedBy();
			//Setting the information required for General Information section  into templateDetailDto object
			templateDetailDto.setTitle(title);
			templateDetailDto.setOverview(overview);
			templateDetailDto.setTerms(terms);
			templateDetailDto.setSpecialInstructions(specialInstructions);
			templateDetailDto.setAltBuyerContactId(templateDto.getAltBuyerContactId());  // Added as part of the buyer contact id changed.
			templateDetailDto.setPartsSuppliedBy(partsSuppliedBy);
			//SL-21355- Set Selected Document Id of buyer Logo from Template of primary SKU
			templateDetailDto.setBuyerDocumentLogo(buyerLogo);
			// Assigning fetched detail for the sku id to the SOWScopeOfWorkDTO to associate with service order.
			// Associating the information of general information section  with the service order
			sowDto.setTitle(title);
			sowDto.setOverview(overview);
			sowDto.setBuyerTandC(terms);
			sowDto.setSpecialInstructions(specialInstructions);
			sowDto.setBuyerContactId(templateDetailDto.getAltBuyerContactId());   // change done for Buyer contact id changes
			sowModel.setTitle(title);
			sowModel.setOverview(overview);
			sowModel.setBuyerTandC(terms);
			sowModel.setSpecialInstructions(specialInstructions);
			sowModel.setBuyerContactId(templateDetailDto.getAltBuyerContactId());   // change done for Buyer_contact_id
			//Fetching documents on the basis of title if it is available 
			if(docReqForSkuInd==0)
			{
				//Sl-19820
				//List<DocumentVO> lastAttachedDocuments = (List<DocumentVO>) getSession().getAttribute("attachmentsOfDocument");
				List<DocumentVO> lastAttachedDocuments = (List<DocumentVO>) getSession().getAttribute("attachmentsOfDocument_"+this.soID);
				//Sl-19820
				//getSession().setAttribute("lastAttachedDocuments",lastAttachedDocuments);
				getSession().setAttribute("lastAttachedDocuments_"+this.soID,lastAttachedDocuments);
			if (null != templateDto.getDocumentTitles()&& templateDto.getDocumentTitles().size() > 0)
			{
				List<DocumentVO> documentVos = new ArrayList<DocumentVO>();
				for(String docTitle : templateDto.getDocumentTitles())
				{		
					//Getting all documents into the DocumentForSkuVO class
					documentVos =  fetchDelegate.retrieveDocumentByTitleAndEntityID(docTitle, buyerId);
					if (null != documentVos && documentVos.size()>0) 
					{
					//Attaching all the document detail to list of DocumentForSkuVO
						attachments.addAll(documentVos);
						
					}
					else{
						errorList.add("No Document Id associated with the document title \'"+docTitle +"\' specified in template.<br/>");
					}
					if(null!=documentVos)
					{
					// Assigning fetched detail for the sku id to the SOWScopeOfWorkDTO to associate with service order.
					// Associating the information of Document and Photos  section  with the service order
					
					for (DocumentVO docVO : documentVos){
						documents.add(ObjectMapper.convertDocumentVOToDTO(docVO));
				}
						documents.get(documents.size()-1).setDocInCurrentVisit(true);
					
				}
			}
				@SuppressWarnings("unused")
				//Checking if buyer has uploaded any document from front end. If yes append other documents to it.
				List<SODocumentDTO> checkAlreadyDocuments = new ArrayList<SODocumentDTO>();
				checkAlreadyDocuments=sowModel.getDocuments();
				if(checkAlreadyDocuments!=null)
				{
					checkAlreadyDocuments.addAll(documents);
					sowModel.setDocuments((ArrayList<SODocumentDTO>) checkAlreadyDocuments);
				}
				else
				{
				sowModel.setDocuments((ArrayList<SODocumentDTO>) documents);
				sowModel.setSkuIndicatorForDocument(true);
				
				}
				//getSession().setAttribute("skuIndicatorForDocument",sowModel.getServiceOrderTypeIndicator());
				getSession().setAttribute("attachmentsList_"+this.soID,documents);
				getSession().setAttribute("documentVOList_"+this.soID,documentVos);
				//Sl-19820
				//getSession().setAttribute("attachmentsOfDocument",attachments);
				getSession().setAttribute("attachmentsOfDocument_"+this.soID,attachments);
				
				getSession().setAttribute("skuIndicatorForDocAttach_"+this.soID, sowModel.getSkuIndicatorForDocument());
				setAttribute("errorList",errorList);
				
			}
			}
		}
		 catch (Exception e) {
			logger.error("Exception loading template Details For XML to java class");
		}
		return templateDetailDto;
	}	

	public SOWScopeOfWorkAction(ISOWizardFetchDelegate fetchDelegate)
	{
		this.fetchDelegate = fetchDelegate;
	}

	public String setDtoForTab() throws IOException
	{
		String TabStatus = "tabIcon ";
		SOWScopeOfWorkTabDTO sowScopeOfWork = getModel();
		sowScopeOfWork.validate();
		if (sowScopeOfWork.getErrors().size() > 0)
		{
			TabStatus = "tabIcon error";
		}
		else if (sowScopeOfWork.getWarnings().size() > 0)
		{
			TabStatus = "tabIcon incomplete";
		}
		else
		{
			TabStatus = "tabIcon complete";
		}
		AjaxResultsDTO actionResults = new AjaxResultsDTO();
		actionResults.setActionState(1);
		actionResults.setResultMessage(SUCCESS);
		actionResults.setAddtionalInfo1(TabStatus);

		// Response output
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		String responseStr = actionResults.toXml();
		logger.info(responseStr);
		response.getWriter().write(responseStr);

		return NONE;

	}

	// AN ENTRY POINT, no execute()

	public String createEntryPoint() throws Exception
	{
		
		String soId=(String)getAttribute(OrderConstants.SO_ID);
		soId=(soId!=null)?soId:(String)getRequest().getParameter(OrderConstants.SO_ID);
		//String soIdParam=(String)getRequest().getParameter(OrderConstants.SO_ID);
		
		
		setAttribute(OrderConstants.SO_ID,soId);
		skillTreeMainCat = fetchDelegate.getSkillTreeMainCategories();
		Iterator<SkillNodeVO> i = skillTreeMainCat.iterator();
		SkillNodeVO singleSkillNode;
		Map<Integer, String> mainServiceCategoryNamesMap = new HashMap<Integer, String>();
		skillSelectionMap = new HashMap();
		while (i.hasNext())
		{
			singleSkillNode = i.next();
			mainServiceCategoryNamesMap.put(singleSkillNode.getNodeId(), singleSkillNode.getNodeName());
			if (singleSkillNode.getServiceTypes() != null)
			{
				skillSelectionMap.put(singleSkillNode.getNodeId(), new ArrayList(singleSkillNode.getServiceTypes()));
			}
		}

		phoneTypes = fetchDelegate.getPhoneTypes();

		getSession().setAttribute("phoneTypes", phoneTypes);
		//SL-19820
		//getSession().setAttribute("mainServiceCategoryNamesMap", mainServiceCategoryNamesMap);
		getSession().setAttribute("mainServiceCategoryNamesMap_"+this.soID, mainServiceCategoryNamesMap);
		getSession().setAttribute("mainServiceCategory", skillTreeMainCat);
		//Sl-19820
		//getSession().setAttribute("skillSelectionMap", skillSelectionMap);
		getSession().setAttribute("skillSelectionMap_"+this.soID, skillSelectionMap);
		//SL-19820 changing session variable
		//String actionType = (String) getSession().getAttribute("actionType");
		String actionType = (String) getSession().getAttribute("actionType_"+soId);
		setAttribute("actionType",actionType);
		if (actionType != null && actionType.equals("create"))
		{
			TabNavigationDTO tabNavigationDTO = _createNavPoint(null, OrderConstants.SOW_SOW_TAB, OrderConstants.SOW_EDIT_MODE, // Same
					// as
					// SOW_EDIT_MODE?
					OrderConstants.SOW_STARTPOINT_DASHBOARD);
			
			SOWSessionFacility.getInstance().evaluateSOWBean(null, tabNavigationDTO, fetchDelegate, get_commonCriteria().getSecurityContext(),soId);
		}

		SOWScopeOfWorkTabDTO scopeOfWorkDTO2 = (SOWScopeOfWorkTabDTO) SOWSessionFacility.getInstance()
				.getTabDTO(OrderConstants.SOW_SOW_TAB);
		//SL-19820 changing session variable
		//String action = (String) getSession().getAttribute("actionType");
		String action = (String) getSession().getAttribute("actionType_"+soId);
		setAttribute("actionType",action);
		if (action != null && action.equalsIgnoreCase("create"))
		{
			scopeOfWorkDTO2.clearErrorsAndWarnings();
			createFirstTask();
			// Null the Created date and Modified date because we dont want to
			// display on create mode
			scopeOfWorkDTO2.setCreatedDateString(null);
			scopeOfWorkDTO2.setModifiedDate(null);

		}
		else
		{
			if (action != null && action.equalsIgnoreCase("copy"))
			{
				// scopeOfWorkDTO2.clearErrorsAndWarnings();
			}
			else
				scopeOfWorkDTO2.setIsEditMode(Boolean.TRUE);

			if (scopeOfWorkDTO2.getMainServiceCategoryId() != null && scopeOfWorkDTO2.getMainServiceCategoryId() != -1)
			{
				for (int j = 0; j < skillTreeMainCat.size(); j++)
				{
					SkillNodeVO skillVo = skillTreeMainCat.get(j);
					if (skillVo.getNodeId().intValue() == scopeOfWorkDTO2.getMainServiceCategoryId().intValue())
					{
						scopeOfWorkDTO2.setMainServiceCategoryName(skillVo.getNodeName());
						break;
					}
				}

				String buyerId = get_commonCriteria().getSecurityContext().getCompanyId() + "";
				String stateCd = null;
				if (scopeOfWorkDTO2.getServiceLocationContact().getState() == null)
				{
					categorySelection = fetchDelegate.getSkillTreeCategoriesOrSubCategories(scopeOfWorkDTO2.getMainServiceCategoryId());
				}
				else
				{
					stateCd = scopeOfWorkDTO2.getServiceLocationContact().getState();
					categorySelection = fetchDelegate.getNotBlackedOutSkillTreeCategoriesOrSubCategories(scopeOfWorkDTO2
							.getMainServiceCategoryId(), buyerId, stateCd);
					Iterator<SkillNodeVO> j = categorySelection.iterator();
					SkillNodeVO singSkillNode;
					categSkillSelectionMap = new HashMap();
					while (j.hasNext())
					{
						singSkillNode = j.next();
						if (singSkillNode.getServiceTypes() != null)
						{
							categSkillSelectionMap.put(singSkillNode.getNodeId(), new ArrayList(singSkillNode.getServiceTypes()));
						}
					}
					//Sl-19820 setting into request and appending session v'ble with soid
					//getSession().setAttribute("categSkillSelectionMap", categSkillSelectionMap);
					getSession().setAttribute("categSkillSelectionMap_"+soId, categSkillSelectionMap);
					setAttribute("categSkillSelectionMap", categSkillSelectionMap);
				}
				//Sl-19820 setting into request and appending session v'ble with soid
				//getSession().setAttribute("categorySelection", categorySelection);
				getSession().setAttribute("categorySelection_"+soId, categorySelection);
				setAttribute("categorySelection", categorySelection);
				
				//Sl-19820 setting into request and appending session v'ble with soid
				//skillSelection = getSkillSelectionMap().get(scopeOfWorkDTO2.getMainServiceCategoryId());
				skillSelection = getSkillSelectionMap(this.soID).get(scopeOfWorkDTO2.getMainServiceCategoryId());
				//getSession().setAttribute("skillSelection", skillSelection);
				getSession().setAttribute("skillSelection_"+soId, skillSelection);
				setAttribute("skillSelection", skillSelection);
				
				//SL-16667 bug
				if (null != getScopeOfWorkDTO().getTasks()&& getScopeOfWorkDTO().getTasks().size()==0){
					getRequest().setAttribute("createdWithoutTasksForInvalidJobCodes", "true");
				}
				Iterator<SOTaskDTO> initTasks = getScopeOfWorkDTO().getTasks().iterator();
				SOTaskDTO singleTask;
				while (initTasks.hasNext()){
					singleTask = initTasks.next();
					   if (scopeOfWorkDTO2.getServiceLocationContact().getState() == null){
							singleTask.setSubCategoryList(fetchDelegate.getSkillTreeCategoriesOrSubCategories(singleTask.getCategoryId()));
						}else{
							singleTask.setSubCategoryList(fetchDelegate.getNotBlackedOutSkillTreeCategoriesOrSubCategories(singleTask.getCategoryId(), buyerId, stateCd));
					}
					 setNoSubCategoryListinDTO(singleTask);
					
				}
			}
		}
		createPhoneNumbers();
		loadSKUCategoiresAndSKU();
		return SUCCESS;
	}

	private void createPhoneNumbers()
	{

		SOWScopeOfWorkTabDTO scopeOfWorkDTO2 = (SOWScopeOfWorkTabDTO) SOWSessionFacility.getInstance()
				.getTabDTO(OrderConstants.SOW_SOW_TAB);

		// The scopeOfWorkDTO2 will be in the session facility at this point
		// since the calling program
		// sets the dto in the session facility prior to calling this method
		if (scopeOfWorkDTO2.getServiceLocationContact() != null)
		{
			if (scopeOfWorkDTO2.getServiceLocationContact().getPhones().size() == 0)
			{

				ArrayList<SOWPhoneDTO> phones = new ArrayList<SOWPhoneDTO>();
				for (int i = 1; i < 4; i++)
				{
					SOWPhoneDTO addPhoneType = new SOWPhoneDTO();
					// This will add PhoneTypes of 1 and 2 to the DTO
					// 1--> Phone
					// 2--> Alternate
					// 3-->fax
					// LMA...This needs to be set so that the Phone,Alternate
					// Phone and fax fields
					// will show up on the jsp for creating a service order
					// If the user did not enter any information but saves a
					// draft,
					// then I need to put in a check in the entry point for
					// modification of a draft
					// to check what is populated...Figure out which phoneTypes
					// are filled out
					// (only check for phone types 1 or 2)...whichever one is
					// missing then,
					// create a phoneType in the dto for that so that the Phone
					// and Alterate fields
					// display.

					addPhoneType.setPhoneType(i);
					phones.add(addPhoneType);
				}
				scopeOfWorkDTO2.getServiceLocationContact().setPhones(phones);

			}
		}
	}

	public String loadSkillsAndCategories() throws Exception
	{
		String buyerId = get_commonCriteria().getSecurityContext().getCompanyId() + "";
		SOWScopeOfWorkTabDTO scopeOfWorkDTO2 = (SOWScopeOfWorkTabDTO) SOWSessionFacility.getInstance()
				.getTabDTO(OrderConstants.SOW_SOW_TAB);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		StringBuffer xmlString = new StringBuffer("<message_output>");// ------------Start
		// XML
		// String

		Integer req = (Integer) getRequest().getAttribute("mainServiceCategoryId");
		if (req != null && req > -1)
		{
			xmlString.append("<mainCategorySelected>" + req + "</mainCategorySelected>");
			//SL-19820 
			//getSession().setAttribute("mainServiceCategoryId", req);
			//No where using in jsp thats why not settin gto request
			getSession().setAttribute("mainServiceCategoryId_"+this.soID, req);
		}
		try
		{
			// scopeOfWorkDTO = getModel();
			scopeOfWorkDTO2.setMainServiceCategoryId(req);
			String stateCd = scopeOfWorkDTO2.getServiceLocationContact().getState();
			/*
			 * SL-19820
			 * Map<Integer, String> mainServiceCategoryNamesMap = (Map<Integer, String>) getSession().getAttribute(
					"mainServiceCategoryNamesMap");*/
			Map<Integer, String> mainServiceCategoryNamesMap = (Map<Integer, String>) getSession().getAttribute(
					"mainServiceCategoryNamesMap_"+this.soID);
			scopeOfWorkDTO2.setMainServiceCategoryName(mainServiceCategoryNamesMap.get(req));
			//Sl-19820
			//skillSelection = getSkillSelectionMap().get(req);
			skillSelection = getSkillSelectionMap(this.soID).get(req);
			//added for sorting skills
			Collections.sort(skillSelection, new Comparator<ServiceTypesVO>() {
            public int compare(ServiceTypesVO o1, ServiceTypesVO o2) {
					return o1.getDescription().compareToIgnoreCase(o2.getDescription());
				}
			});
			ServiceTypesVO singleSkill = new ServiceTypesVO();

			for (int i = 0; i < skillSelection.size(); i++)
			{
				singleSkill = skillSelection.get(i);
				xmlString.append("<skill>");
				xmlString.append("<skillId>" + singleSkill.getServiceTypeId() + "</skillId>");
				xmlString.append("<skillName>" + singleSkill.getDescription().replaceAll("&", "&amp;") + "</skillName>");
				xmlString.append("</skill>");
			}
			
			getSession().setAttribute("skillSelection", skillSelection);
			categorySelection = fetchDelegate.getNotBlackedOutSkillTreeCategoriesOrSubCategories(
					scopeOfWorkDTO2.getMainServiceCategoryId(), buyerId, stateCd);

			Iterator<SkillNodeVO> j = categorySelection.iterator();
			SkillNodeVO singleSkillNode;
			categSkillSelectionMap = new HashMap();
			while (j.hasNext())
			{
				singleSkillNode = j.next();
				if (singleSkillNode.getServiceTypes() != null)
				{
					categSkillSelectionMap.put(singleSkillNode.getNodeId(), new ArrayList(singleSkillNode.getServiceTypes()));
				}
			}
			
		/*	SL-19820
		 * it is no where using in jsp
		 * getSession().setAttribute("categSkillSelectionMap", categSkillSelectionMap);*/
			getSession().setAttribute("categSkillSelectionMap_"+this.soID, categSkillSelectionMap);

			if (categorySelection != null && !categorySelection.isEmpty())
			{
				SkillNodeVO singleCategory = new SkillNodeVO();
				getSession().setAttribute("categorySelection", categorySelection);
				for (int i = 0; i < categorySelection.size(); i++)
				{
					singleCategory = categorySelection.get(i);
					xmlString.append("<cat>");
					xmlString.append("<categoryId>" + singleCategory.getNodeId() + "</categoryId>");
					xmlString.append("<categoryName>" + singleCategory.getNodeName().replaceAll("&", "&amp;") + "</categoryName>");
					xmlString.append("</cat>");
				}
			}
			xmlString.append("</message_output>");// ------------End XML
			// String
			response.getWriter().write(xmlString.toString());
			scopeOfWorkDTO2.setTheResourceBundle(getTexts("servicelive"));
		}
		catch (Throwable t)
		{
			logger.error("Could not set skills and categories properly", t);
		}
		return null;
	}

	public String loadSubcategories() throws BusinessServiceException
	{
		
		String buyerId = get_commonCriteria().getSecurityContext().getCompanyId() + "";
		ArrayList<SOTaskDTO> taskListToUpdate = null;
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		StringBuffer xmlString = new StringBuffer("<message_output>");// ------------Start
		// XML
		// String
		sowModel = getModel();
		String stateCd = sowModel.getServiceLocationContact().getState();
		if (sowModel.getTasks() != null)
		{
			//SL-19820
			//getSession().setAttribute("taskList", sowModel.getTasks());
			getSession().setAttribute("taskList_"+this.soID, sowModel.getTasks());
			taskListToUpdate = (ArrayList<SOTaskDTO>) sowModel.getTasks();
		}
		/*SL-19820
		 * else if (getSession().getAttribute("taskList") != null)
		{
			taskListToUpdate = (ArrayList<SOTaskDTO>) getSession().getAttribute("taskList");
		}*/
		
		 else if (getSession().getAttribute("taskList_"+this.soID) != null)
			{
				taskListToUpdate = (ArrayList<SOTaskDTO>) getSession().getAttribute("taskList_"+this.soID);
			}
		Integer selectedCategoryId = sowModel.getSelectedCategoryId();
		SkillNodeVO singleSubCategory = new SkillNodeVO();

		if (selectedCategoryId != null && taskIndex != null && taskIndex != -1)
		{

			SOTaskDTO updateSOTask = new SOTaskDTO();
			updateSOTask = taskListToUpdate.get(taskIndex);
			updateSOTask.setCategoryId(selectedCategoryId);
			taskListToUpdate.set(taskIndex, updateSOTask);
			//Sl-19820
			//getSession().setAttribute("taskList", taskListToUpdate);
			getSession().setAttribute("taskList_"+this.soID, taskListToUpdate);
			xmlString.append("<categorySelectedId>" + selectedCategoryId + "</categorySelectedId>");
			xmlString.append("<taskIndex>" + taskIndex + "</taskIndex>");
		}
        //Sl-19820
		//skillSelection = getCategSkillSelectionMap().get(selectedCategoryId);
		skillSelection = getCategSkillSelectionMap(this.soID).get(selectedCategoryId);
		ServiceTypesVO singleSkill = new ServiceTypesVO();
		if (skillSelection != null)
		{
			Collections.sort(skillSelection, new Comparator<ServiceTypesVO>() {
            public int compare(ServiceTypesVO o1, ServiceTypesVO o2) {
					return o1.getDescription().compareToIgnoreCase(o2.getDescription());
				}
			});
			for (int i = 0; i < skillSelection.size(); i++)
			{
				singleSkill = skillSelection.get(i);
				xmlString.append("<skill>");
				xmlString.append("<skillId>" + singleSkill.getServiceTypeId() + "</skillId>");
				xmlString.append("<skillName>" + singleSkill.getDescription().replaceAll("&", "&amp;") + "</skillName>");
				xmlString.append("</skill>");
			}
		}
		else
		{
			categorySelection = fetchDelegate.getNotBlackedOutSkillTreeCategoriesOrSubCategories(sowModel.getMainServiceCategoryId(),
					buyerId, stateCd);

			Iterator<SkillNodeVO> j = categorySelection.iterator();
			SkillNodeVO singleSkillNode;
			categSkillSelectionMap = new HashMap();
			while (j.hasNext())
			{
				singleSkillNode = j.next();
				if (singleSkillNode.getServiceTypes() != null)
				{
					categSkillSelectionMap.put(singleSkillNode.getNodeId(), new ArrayList(singleSkillNode.getServiceTypes()));
				}
			}
			/*Sl-19820
			 * getSession().setAttribute("categSkillSelectionMap", categSkillSelectionMap);*/
			getSession().setAttribute("categSkillSelectionMap_"+this.soID, categSkillSelectionMap);
			//Sl-19820
			//skillSelection = getCategSkillSelectionMap().get(selectedCategoryId);
			skillSelection = getCategSkillSelectionMap(this.soID).get(selectedCategoryId);
			Collections.sort(skillSelection, new Comparator<ServiceTypesVO>() {
	            public int compare(ServiceTypesVO o1, ServiceTypesVO o2) {
						return o1.getDescription().compareToIgnoreCase(o2.getDescription());
					}
				});
			for (int i = 0; i < skillSelection.size(); i++)
			{
				singleSkill = skillSelection.get(i);
				xmlString.append("<skill>");
				xmlString.append("<skillId>" + singleSkill.getServiceTypeId() + "</skillId>");
				xmlString.append("<skillName>" + singleSkill.getDescription().replaceAll("&", "&amp;") + "</skillName>");
				xmlString.append("</skill>");
			}
		}
		
		getSession().setAttribute("skillSelection", skillSelection);

		try
		{

			/*
			 * Let's do some session magic. When a user selects a category,
			 * there may or may not be a list of sub-categories associated with
			 * that selection. Let's save this possible sub-category list in a
			 * session map. the SOTaskDTO's getCategories() and
			 * getSubCategories() will be involved
			 */
			TreeMap<Integer, ArrayList<SkillNodeVO>> subCategorySelectionSessionMap = (TreeMap) getSession().getAttribute(
					"subCategorySelectionSessionMap");
			if (subCategorySelectionSessionMap == null)
			{
				subCategorySelectionSessionMap = new TreeMap();

			}

			if (selectedCategoryId != null && selectedCategoryId != -1)
			{
				subCategorySelection = fetchDelegate.getNotBlackedOutSkillTreeCategoriesOrSubCategories(selectedCategoryId, buyerId,
						stateCd);
			}

			if (!subCategorySelection.isEmpty())
			{

				Iterator<SkillNodeVO> j = subCategorySelection.iterator();
				SkillNodeVO singleSkillNode;
				subCategSkillSelectionMap = new HashMap();
				while (j.hasNext())
				{
					singleSkillNode = j.next();
					if (singleSkillNode.getServiceTypes() != null)
					{
						subCategSkillSelectionMap.put(singleSkillNode.getNodeId(), new ArrayList(singleSkillNode.getServiceTypes()));
					}
				}
				//SL-19820
				//getSession().setAttribute("subCategSkillSelectionMap", subCategSkillSelectionMap);
				getSession().setAttribute("subCategSkillSelectionMap_"+this.soID, subCategSkillSelectionMap);
				boolean noSubCatFound = false;
				for (int i = 0; i < subCategorySelection.size(); i++){
					singleSubCategory = subCategorySelection.get(i);
					xmlString.append("<subcat>");
					xmlString.append("<subCategoryId>" + singleSubCategory.getNodeId() + "</subCategoryId>");
					xmlString.append("<subCategoryName>" + singleSubCategory.getNodeName().replaceAll("&", "&amp;") + "</subCategoryName>");
					xmlString.append("</subcat>");
					if((null!= singleSubCategory.getNodeId() && singleSubCategory.getNodeId()==-1)
							||(StringUtils.isNotBlank(singleSubCategory.getNodeName()) 
									&& (StringUtils.equalsIgnoreCase(singleSubCategory.getNodeName(), "No Sub-Categories Available")))){
						noSubCatFound =true;
					 }
				}
				if(!noSubCatFound){
					SkillNodeVO noSubCategoryVO = new SkillNodeVO();
					noSubCategoryVO.setActive(true);
					noSubCategoryVO.setNodeName("No Sub-Categories Available");
					noSubCategoryVO.setNodeId(-1);
					xmlString.append("<subcat>");
					xmlString.append("<subCategoryId>" + noSubCategoryVO.getNodeId() + "</subCategoryId>");
					xmlString.append("<subCategoryName>" + noSubCategoryVO.getNodeName().replaceAll("&", "&amp;") + "</subCategoryName>");
					xmlString.append("</subcat>");
				}
			}
			else
			{
				subCategorySelection = new ArrayList<SkillNodeVO>();
			}
			ArrayList<SOTaskDTO> tempTasks = sowModel.getTasks();
			SOTaskDTO tempTaskDTO = tempTasks.get(taskIndex);
			tempTaskDTO.setSubCategoryList(subCategorySelection);
			tempTasks.set(taskIndex, tempTaskDTO);
			setNoSubCategoryList(tempTasks);
			sowModel.setTasks(tempTasks);
			subCategorySelectionSessionMap.put(selectedCategoryId, subCategorySelection);
			getSession().setAttribute("subCategorySelectionSessionMap", subCategorySelectionSessionMap);

			xmlString.append("</message_output>");// ------------End XML
			// String
			response.getWriter().write(xmlString.toString());
		}
		catch (Throwable t)
		{
			logger.error("Could not set subcategories properly", t);
		}

		return null;
	}

	public String loadSubCategoriesSkills()
	{
		String buyerId = get_commonCriteria().getSecurityContext().getCompanyId().toString();
		ArrayList<SOTaskDTO> taskListToUpdate = null;
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		StringBuffer xmlString = new StringBuffer("<message_output>");// ------------Start
		// XML
		// String

		sowModel = getModel();
		if (sowModel.getTasks() != null)
		{
			//Sl-19820
			//getSession().setAttribute("taskList", sowModel.getTasks());
			getSession().setAttribute("taskList_"+this.soID, sowModel.getTasks());
			taskListToUpdate = (ArrayList<SOTaskDTO>) sowModel.getTasks();
		}
		/*
		 * Sl-19820
		 * else if (getSession().getAttribute("taskList") != null)
		{
			taskListToUpdate = (ArrayList<SOTaskDTO>) getSession().getAttribute("taskList");
		}*/
		
		else if (getSession().getAttribute("taskList_"+this.soID) != null)
		{
			taskListToUpdate = (ArrayList<SOTaskDTO>) getSession().getAttribute("taskList_"+this.soID);
		}
		Integer selectedCategoryId = sowModel.getSelectedCategoryId();

		if (selectedCategoryId != null && taskIndex != null && taskIndex != -1)
		{
			SOTaskDTO updateSOTask = new SOTaskDTO();
			updateSOTask = taskListToUpdate.get(taskIndex);
			updateSOTask.setCategoryId(selectedCategoryId);
			taskListToUpdate.set(taskIndex, updateSOTask);
			/*SL-19820
			 * getSession().setAttribute("taskList", taskListToUpdate);*/
			getSession().setAttribute("taskList_"+this.soID, taskListToUpdate);
			
			xmlString.append("<categorySelectedId>" + selectedCategoryId + "</categorySelectedId>");
			xmlString.append("<taskIndex>" + taskIndex + "</taskIndex>");
		}
		try
		{
			//Following lines of codes are for populating the session map 'subCategSkillSelectionMap' 
			// when it is null or it doesn't have entry for the selected sub category id.
			/*if(null== getSession().getAttribute("subCategSkillSelectionMap")|| null ==getSubCategSkillSelectionMap().get(selectedCategoryId)){
			*/if(null== getSession().getAttribute("subCategSkillSelectionMap_"+this.soID)|| null ==getSubCategSkillSelectionMap(this.soID).get(selectedCategoryId)){
					Integer parentCategoryId = fetchDelegate.getParentNodeId(selectedCategoryId);
				List<SkillNodeVO> tempSubCategorySelection = new ArrayList<SkillNodeVO>();
				String stateCd = sowModel.getServiceLocationContact().getState();
				tempSubCategorySelection = fetchDelegate.getNotBlackedOutSkillTreeCategoriesOrSubCategories(parentCategoryId, buyerId,
						stateCd);

				Map<Integer, ArrayList<ServiceTypesVO>> tempSubCategSkillSelectionMap = new HashMap<Integer, ArrayList<ServiceTypesVO>>();
				List<ServiceTypesVO> serviceTypes = new ArrayList<ServiceTypesVO>();
					
				for(SkillNodeVO subCategorySkillNode: tempSubCategorySelection){
					serviceTypes = subCategorySkillNode.getServiceTypes();
					if (null!= serviceTypes)
					{
						tempSubCategSkillSelectionMap.put(subCategorySkillNode.getNodeId(), new ArrayList<ServiceTypesVO>(serviceTypes));
					}
				}
				//SL-19820
				//getSession().setAttribute("subCategSkillSelectionMap", tempSubCategSkillSelectionMap);
				getSession().setAttribute("subCategSkillSelectionMap_"+this.soID, tempSubCategSkillSelectionMap);
			}		
		  //Sl-19820
			//skillSelection = getSubCategSkillSelectionMap().get(selectedCategoryId);
			skillSelection = getSubCategSkillSelectionMap(this.soID).get(selectedCategoryId);
			Collections.sort(skillSelection, new Comparator<ServiceTypesVO>() {
	            public int compare(ServiceTypesVO o1, ServiceTypesVO o2) {
						return o1.getDescription().compareToIgnoreCase(o2.getDescription());
					}
				});
			ServiceTypesVO singleSkill = new ServiceTypesVO();
			if(null != skillSelection){
				for (int i = 0; i < skillSelection.size(); i++)
				{
					singleSkill = skillSelection.get(i);
					xmlString.append("<skill>");
					xmlString.append("<skillId>" + singleSkill.getServiceTypeId() + "</skillId>");
					xmlString.append("<skillName>" + singleSkill.getDescription().replaceAll("&", "&amp;") + "</skillName>");
					xmlString.append("</skill>");
				}
			}			

			xmlString.append("</message_output>");// ------------End XML
			// String
			response.getWriter().write(xmlString.toString());
		}
		catch (Throwable t)
		{
			logger.error("Could not set subcategories properly", t);
		}

		return null;
	}
	
	
	//SL-21373
	public String loadCatSubcatSkills()
	{ 
				SOWScopeOfWorkTabDTO sowDTO = (SOWScopeOfWorkTabDTO) SOWSessionFacility.getInstance().getTabDTO(OrderConstants.SOW_SOW_TAB);
				
				String catId=getParameter("catId");
				String subCatId=getParameter("subCatId");
				String skillsId=getParameter("skillsId");
				String taskIndex=getParameter("taskIndex");
				
				if(sowDTO != null && sowDTO.getMainServiceCategoryId() != null){
						ArrayList<SOTaskDTO> soTasks = sowDTO.getTasks();
						if(null!=soTasks && StringUtils.isNotBlank(taskIndex))
						{
							SOTaskDTO taskDto = soTasks.get(Integer.parseInt(taskIndex));
							if(null!=taskDto && StringUtils.isNotBlank(catId) && StringUtils.isNotBlank(subCatId) &&  StringUtils.isNotBlank(skillsId))
							{
								taskDto.setCategoryId(Integer.parseInt(catId));
								taskDto.setSubCategoryId(Integer.parseInt(subCatId));
								taskDto.setSkillId(Integer.parseInt(skillsId));
							}
							
						}
				}
				
				String soId=getParameter(OrderConstants.SO_ID);
				ServiceOrderWizardBean serviceOrderWizardBean = (ServiceOrderWizardBean) getSession().getAttribute(OrderConstants.SERVICE_ORDER_WIZARD_KEY+"_"+soId);
				serviceOrderWizardBean.getTabDTOs().put(OrderConstants.SOW_SOW_TAB, sowDTO);
				getSession().setAttribute(OrderConstants.SERVICE_ORDER_WIZARD_KEY+"_"+soId, serviceOrderWizardBean);
				
				return null;
	}
	
	//Method for mapping each added sku to  dto
	public String addSku() throws Exception{
		SOWPricingTabDTO pricingTabDTO = (SOWPricingTabDTO) SOWSessionFacility.getInstance().getTabDTO(OrderConstants.SOW_PRICING_TAB);
		sowModel = getModel();
		Double totalLabourPriceForSku=0.0;
		  for(SOTaskDTO skuForBidPrice: sowModel.getSkus()){
			 if(null!= skuForBidPrice.getPrice()){
				totalLabourPriceForSku=totalLabourPriceForSku+skuForBidPrice.getPrice();
			  }
			  /**This code block is added to covert html characters entered via sku 
			   * Maintains page with &nbsp to java recognizable format so that the 
			   * Rich Text enabled format to be displayed for task comments in so 
			   * creation via sku  template mapping*/
			  if(StringUtils.isNotBlank(skuForBidPrice.getComments())){ 
				String taskComments = URLDecoder.decode(skuForBidPrice.getComments() ,"UTF-8"); 
				skuForBidPrice.setComments(taskComments);
			   }
			 }
			pricingTabDTO.setTasks(sowModel.getSkus());
			pricingTabDTO.setLaborSpendLimit(Double.toString(totalLabourPriceForSku));
		    //SL-20729: Set already added categorys sku for futher addition of skus
			String skuCategoryAdded = sowModel.getSkuCategoryHidden();
			setAttribute("skuCategoryHidden", skuCategoryAdded);
		return GOTO_COMMON_WIZARD_CONTROLLER;
	}
	
	public String addTask() throws Exception
	{

		sowModel = getModel();
		SOTaskDTO task;
		ArrayList<SOTaskDTO> taskList = sowModel.getTasks();
		if (taskList != null && taskList.size() > 0)
		{
			Iterator<SOTaskDTO> i = taskList.iterator();
			while (i.hasNext())
			{
				task = i.next();
				task.setIsFreshTask(false);
			}
		}
		sowModel.addTask(new SOTaskDTO());
		// scopeOfWorkDTO.setDoFullValidation(true);
		if (sowModel.getErrors().size() == 0 && sowModel.getWarnings().size() == 0)
		{
			ServiceOrderWizardBean bean = SOWSessionFacility.getInstance().getSOWBean(this.soID);
			SOWTabStateVO vo;
			vo = (SOWTabStateVO) bean.getTabStateDTOs().get(OrderConstants.SOW_SOW_TAB);
			vo.setTabErrorState("");
			vo.setTabWarningState("");

		}

		return GOTO_COMMON_WIZARD_CONTROLLER;
	}

	

	public String deleteTask() throws BusinessServiceException
	{

		SOWScopeOfWorkTabDTO scopeOfWorkDTO2 = getModel();
		ArrayList<SOTaskDTO> taskList = scopeOfWorkDTO2.getTasks();
		SOTaskDTO deletedTask = taskList.get(getDelIndex().intValue());
	    taskList.remove(getDelIndex().intValue());
	    //SL-20527 : Setting Group spend limit labor and total permit price in pricing DTO.
		setGroupSpendLimitLabor(deletedTask);
		//SL-20527 : Setting spend limit labor and permit price in pricing DTO
	    sowModel.setTasks(taskList);
		setSpendLimitLabor(scopeOfWorkDTO2);
  
		if (sowModel.getErrors().size() == 0 && sowModel.getWarnings().size() == 0)
		{
			ServiceOrderWizardBean bean = SOWSessionFacility.getInstance().getSOWBean(this.soID);
			SOWTabStateVO vo;
			vo = (SOWTabStateVO) bean.getTabStateDTOs().get(OrderConstants.SOW_SOW_TAB);
			vo.setTabErrorState("");
			vo.setTabWarningState("");

		}
		sowModel.setServiceOrderTypeIndicator("SoUsingTask");
		//getSession().setAttribute("serviceOrderTypeIndicator","SoUsingTask");
		return GOTO_COMMON_WIZARD_CONTROLLER;
	}
	
	
	

	public String switchtoSku() throws Exception
	{
		//Sl-19820
		//String soId = (String) getSession().getAttribute(OrderConstants.SO_ID);
		String soId = (String)getAttribute(OrderConstants.SO_ID);
		ArrayList<SOTaskDTO> taskList = new ArrayList<SOTaskDTO>();
		taskList.add(new SOTaskDTO());
		sowModel.setTasks(taskList);
		sowModel.setTitle(null);
		sowModel.setOverview(null);
		sowModel.setBuyerTandC(null);
		sowModel.setSpecialInstructions(null);
		sowModel.setMainServiceCategoryId(-1);
		//SL-19820
		//getSession().removeAttribute("mainServiceCategoryId");
		getSession().removeAttribute("mainServiceCategoryId_"+this.soId);
		sowModel.setServiceOrderTypeIndicator("SoUsingSku");
		if (sowModel.getErrors().size() == 0 && sowModel.getWarnings().size() == 0)
		{
			//SL-19820
			ServiceOrderWizardBean bean = SOWSessionFacility.getInstance().getSOWBean(soId);
			SOWTabStateVO vo;
			vo = (SOWTabStateVO) bean.getTabStateDTOs().get(OrderConstants.SOW_SOW_TAB);
			vo.setTabErrorState("");
			vo.setTabWarningState("");

		}
		
		SOWPricingTabDTO pricingTabDTO = (SOWPricingTabDTO) SOWSessionFacility.getInstance().getTabDTO(OrderConstants.SOW_PRICING_TAB);
		Double totalLabourPriceForSku=0.0;
		pricingTabDTO.setLaborSpendLimit(Double.toString(totalLabourPriceForSku));
		pricingTabDTO.setTasks(null);
			
		ProcessResponse pr = new ProcessResponse();
		try
		{
		pr = isoWizardPersistDelegate.deleteSODocumentforTask(soId);
		
		if ((pr!=null) && (!pr.getSubCode().equals("SUCCESS"))){
			logger.info("deleting all so documents");
		}
		}catch(Exception e)
		{
			logger.info("deleting all so document");	
		}
		return GOTO_COMMON_WIZARD_CONTROLLER;
	}
	
	
	public String switchtoTask() throws Exception{
		//SL-19820
		//String soId = (String) getSession().getAttribute(OrderConstants.SO_ID);
		String soId = (String)getAttribute(OrderConstants.SO_ID);
		sowModel.setSkus(null);
		sowModel.setMainServiceCategoryId(-1);
		sowModel.setTitle(null);
		sowModel.setOverview(null);
		sowModel.setBuyerTandC(null);
		sowModel.setSpecialInstructions(null);
		sowModel.setSkuCategoryHidden("-1");
		sowModel.setServiceOrderTypeIndicator("SoUsingTask");
		ProcessResponse pr = new ProcessResponse();
		
		if (sowModel.getErrors().size() == 0 && sowModel.getWarnings().size() == 0)
		{
			//SL-19820
			ServiceOrderWizardBean bean = SOWSessionFacility.getInstance().getSOWBean(soId);
			SOWTabStateVO vo;
			vo = (SOWTabStateVO) bean.getTabStateDTOs().get(OrderConstants.SOW_SOW_TAB);
			vo.setTabErrorState("");
			vo.setTabWarningState("");

		}
		try
		{
		pr = isoWizardPersistDelegate.deleteSODocumentforTask(soId);
		
		if ((pr!=null) && (!pr.getSubCode().equals("SUCCESS"))){
			logger.info("deleting all so document");
		}
		}catch(Exception e)
		{
			logger.info("deleting all so document");	
	
		}

		return GOTO_COMMON_WIZARD_CONTROLLER;
	}
	
	
	
	
//Method to delete sku from front end and list of sku	
	 
	@SuppressWarnings("unchecked")
	public String deleteSku() throws BusinessServiceException{
		SOTaskDTO primarySKU = null;
		Integer logoDocument = null;
		Integer existingLogo = null;
		//SL-19820
		//String SO_ID = (String) getSession().getAttribute(OrderConstants.SO_ID);
		String SO_ID =(String)getAttribute(OrderConstants.SO_ID);
		logger.info("Checking so id for deleting document-"+SO_ID);
		getSession().setAttribute("skuAttached_"+SO_ID,"0");
		SOWScopeOfWorkTabDTO scopeOfWorkDTO2 = getModel();
		ArrayList<SOTaskDTO> skuList = scopeOfWorkDTO2.getSkus();
		String primarySkuInd = getParameter("primarySkuInd");
		String onlySkuDeleteInd = getParameter("onlySkuDeleteInd");
		if(scopeOfWorkDTO2.getOnlySkuDeleteInd().equals("")||scopeOfWorkDTO2.getOnlySkuDeleteInd() == null ){
			scopeOfWorkDTO2.setOnlySkuDeleteInd(onlySkuDeleteInd);
		}
		int deleteIndex = getDelIndex().intValue();
		if(deleteIndex == 0){
			primarySKU = skuList.get(deleteIndex);
			logoDocument = primarySKU.getBuyerDocumentLogo();
			//SL-21355 : Code added to remove buyer logo association from service order for the primary SKU
			existingLogo = fetchDelegate.getLogoDocumentId(this.soID);
			if(null!= logoDocument && null!= existingLogo && logoDocument.intValue()== existingLogo.intValue()){
			    fetchDelegate.applyBrandingLogo(this.soID, null);
			}
		}
		skuList.remove(deleteIndex);
		sowModel.setSkus(skuList);
		if(skuList.size() == 0 || primarySkuInd.equals("0")){
			logger.info("Entering inside deletion of docuement when size of sku is"+skuList.size());
			logger.info("Checking list size after deletion of sku-"+skuList.size());
			try{
			List<DocumentVO>  documentVO = new ArrayList<DocumentVO>();
			if(primarySkuInd.equals("0")){	
			//Sl-19820
			//documentVO = (List<DocumentVO>) getSession().getAttribute("lastAttachedDocuments");
			documentVO = (List<DocumentVO>) getSession().getAttribute("lastAttachedDocuments_"+this.soID);
			}else if(documentVO == null || (skuList.size() == 0 && primarySkuInd.equals("1"))){
				//Sl-19820
				//documentVO = (List<DocumentVO>) getSession().getAttribute("attachmentsOfDocument");
				documentVO = (List<DocumentVO>) getSession().getAttribute("attachmentsOfDocument_"+this.soID);
				
				
			}
			
			for(DocumentVO documentVOs : documentVO)
			{
				if(documentVOs != null)
				{
					logger.info("logging document id of each document-"+documentVOs.getDocumentId());
					ProcessResponse pr = new ProcessResponse();
					documentVOs.setSoId(SO_ID);
					pr = isoWizardPersistDelegate.deleteSODocument(documentVOs);
					//getSession().removeAttribute("attachmentsOfDocument");
					//getSession().removeAttribute("lastAttachedDocuments");
					if ((pr!=null) && (!pr.getSubCode().equals("SUCCESS"))){
						logger.info("logging Error while deleting document when primary sku is deleted");
					}
					
				}	
					
			}	
			}
			catch (Exception e) {
				logger.debug("Error while checking deletion of sku");
			}
			
		}
		if (sowModel.getErrors().size() == 0 && sowModel.getWarnings().size() == 0)
		{
			//SL-19820
			//ServiceOrderWizardBean bean = SOWSessionFacility.getInstance().getSOWBean();
			ServiceOrderWizardBean bean = SOWSessionFacility.getInstance().getSOWBean(SO_ID);
			SOWTabStateVO vo;
			vo = (SOWTabStateVO) bean.getTabStateDTOs().get(OrderConstants.SOW_SOW_TAB);
			vo.setTabErrorState("");
			vo.setTabWarningState("");

		}
		SOWPricingTabDTO pricingTabDTO = (SOWPricingTabDTO) SOWSessionFacility.getInstance().getTabDTO(OrderConstants.SOW_PRICING_TAB);
		sowModel = getModel();
		Double totalLabourPriceForSku=0.0;
		
			for(SOTaskDTO skuForBidPrice: sowModel.getSkus())
			{
				if(skuForBidPrice.getPrice()!=null)
				{
					totalLabourPriceForSku=totalLabourPriceForSku+skuForBidPrice.getPrice();
				}
				
			}
			pricingTabDTO.setLaborSpendLimit(Double.toString(totalLabourPriceForSku));
			sowModel.setServiceOrderTypeIndicator("SoUsingSku");
		//	getSession().setAttribute("serviceOrderTypeIndicator","SoUsingSku");

		return GOTO_COMMON_WIZARD_CONTROLLER;
	}
	
	
	public String createAndRoute() throws Exception
	{
		return null;
	}

	public String editEntryPoint() throws Exception
	{
		String soID = null;
		//SL -19820 get soid from param and attribute
		///TO DO change accordingly
		String soIdAttr=(String)getAttribute(OrderConstants.SO_ID);
		String soIdParam=(String)getRequest().getParameter(OrderConstants.SO_ID);
		String returnValue = null;

		logger.info("*****  Entering SOWScopeOfWorkAction.editEntryPoint()  *****");

		soID = (String) getSession().getAttribute(OrderConstants.SO_ID);
		if (soID == null || soID.length() < 1)
		{
			logger.debug("Service Order number not available in HTTPRequest");
			return ERROR;

		}// end if
		else
		{
			// Build Tab Navigation DTO
			TabNavigationDTO tabNavDTO = _createNavPoint(null, null, OrderConstants.SOW_EDIT_MODE, "SOM");
			// 
			try
			{
				SOWSessionFacility.getInstance().evaluateSOWBean(null, tabNavDTO, this.getFetchDelegate(), get_commonCriteria().getSecurityContext(),soID);
				returnValue = SUCCESS;
			}// end try
			catch (Throwable t)
			{
				logger.debug("Throwable event occurred during editEntryPoint.", t);
				returnValue = ERROR;
			}// end catch
		}// end else
		logger.info("*****  Exiting SOWScopeOfWorkAction.editEntryPoint()  *****");
		return returnValue;

	}// end method editEntryPoint()

	public String next() throws Exception
	{

		getRequest().setAttribute("previous", "tab1");
		getRequest().setAttribute("next", "tab2");
		TabNavigationDTO tabNav = _createNavPoint(OrderConstants.SOW_NEXT_ACTION, OrderConstants.SOW_SOW_TAB, OrderConstants.SOW_EDIT_MODE,
				"SOM");
		SOWScopeOfWorkTabDTO sowScopeOfWork = getModel();
		// handlePartSuppliedBy(sowScopeOfWork);
		if (sowScopeOfWork != null && sowScopeOfWork.getServiceDateType() != null
				&& sowScopeOfWork.getServiceDateType().equals(new Integer(1)))
		{
			sowScopeOfWork = fixedDateType(sowScopeOfWork);
		}
		sowScopeOfWork.setDoFullValidation(false);
		// nullOutParts();
		
		//summing  of bid price of each sku  to show as labor price
//		String skuIndicator=sowScopeOfWork.getServiceOrderTypeIndicator();
//		if(skuIndicator!=null)
//		{
//			Double totalLabourPriceForSku=0.0;
//			if(skuIndicator.equalsIgnoreCase("SoUsingSku"))
//		{
//				for(SOTaskDTO skuForBidPrice: sowScopeOfWork.getSkus())
//				{
//					if(skuForBidPrice.getPrice()!=null)
//					{
//						totalLabourPriceForSku=totalLabourPriceForSku+skuForBidPrice.getPrice();
//					}
//					
//				}
//			getSession().setAttribute("labourPriceForSku", totalLabourPriceForSku);
//			getSession().setAttribute("labourForSkuCheck",skuIndicator);
//	}
	//	}
		
		// Validate if the state and zip code match
		validateZipCode(sowScopeOfWork);
        setSpendLimitLabor(sowScopeOfWork); 
		// TODO need to remove after implementing radio button
		SOWSessionFacility.getInstance().evaluateSOWBean(sowScopeOfWork, tabNav);
		String temp = SOWSessionFacility.getInstance().getGoingToTab();
		if (temp == "Scope of Work")
		{
			this.setNext("tab1");
		}
		setDefaultTab(SOWSessionFacility.getInstance().getGoingToTab());
		
		
		return GOTO_COMMON_WIZARD_CONTROLLER;
	}

	/*
	 * private void handlePartSuppliedBy(SOWScopeOfWorkTabDTO sowScopeOfWork){
	 * if (sowScopeOfWork!=null) { String partSuppliedBy =
	 * sowScopeOfWork.getPartsSuppliedBy(); if (partSuppliedBy!=null && (
	 * partSuppliedBy.equals(OrderConstants.SOW_SOW_PARTS_NOT_REQUIRED) ||
	 * partSuppliedBy.equals(OrderConstants.SOW_SOW_PROVIDER_PROVIDES_PART )) ) {
	 * SOWPartsTabDTO sowPartsTabDTO =
	 * (SOWPartsTabDTO)SOWSessionFacility.getInstance().getTabDTO(OrderConstants.SOW_PARTS_TAB);
	 * if (sowPartsTabDTO!=null) { sowPartsTabDTO.setParts(new ArrayList()); } } } }
	 */
	public String previous() throws Exception
	{
		return SOWSessionFacility.getInstance().getStartPoint();
	}

	public String saveAsDraft() throws Exception
	{

		// 1. NAME OF THE ACTION (NEXT OR PREVIOUS)
		// 2. WHAT TAB OR YOU ON sow ETC..
		// 3. WHAT MODE ARE YOU IN CREATE OR EDIT
		// 4. WHERE DID YOU START FROM IN THE APPLICATION
		String returnValue = null;
		TabNavigationDTO tabNav = _createNavPoint(OrderConstants.SOW_SAVE_AS_DRAFT_ACTION, OrderConstants.SOW_SOW_TAB,
				OrderConstants.SOW_EDIT_MODE, "SOM");
		SOWScopeOfWorkTabDTO sowScopeOfWork = getModel();
		// handlePartSuppliedBy(sowScopeOfWork);
		if (sowScopeOfWork != null && sowScopeOfWork.getServiceDateType() != null
				&& sowScopeOfWork.getServiceDateType().equals(new Integer(1)))
		{
			sowScopeOfWork = fixedDateType(sowScopeOfWork);
		}
		if (sowScopeOfWork.getServiceLocationContact().getState().equals("-1"))
			sowScopeOfWork.getServiceLocationContact().setState("");
		//Calling delete sku to delete null entry from the list of skus
//		String skuIndicator=sowScopeOfWork.getServiceOrderTypeIndicator();
//		@SuppressWarnings("unused")
//		Boolean skuDetector=false;
//		if(skuIndicator!=null)
//		{if(skuIndicator.equalsIgnoreCase("SoUsingSku"))
//		{	skuDetector=true;
//			deleteSku();
//			
//		}
//		}
		// Validate if the state and zip code match
		validateZipCode(sowScopeOfWork);
        soPricePopulation();
		//SL-20527 Setting Spend Limit labor with out considering deleted task
		setSpendLimitLabor(sowScopeOfWork);
		SOWSessionFacility.getInstance().evaluateAndSaveSOWBean(sowScopeOfWork, tabNav, isoWizardPersistDelegate, get_commonCriteria(), orderGroupDelegate);
		String str = SOWSessionFacility.getInstance().getGoingToTab();
		if (str != null && str.equalsIgnoreCase(OrderConstants.SOW_EXIT_SAVE_AS_DRAFT))
		{
			//Sl-19820
			//String currentSO = (String) getSession().getAttribute(OrderConstants.SO_ID);
			String currentSO =(String)getAttribute(OrderConstants.SO_ID);
			//SL-21355 - Code change to save the buyer logo associated with the primary SKU 
			setBuyerLogo(sowScopeOfWork);
			invalidateAndReturn(fetchDelegate);
			Map sessionMap = ActionContext.getContext().getSession();
			if (new SOClaimedFacility().isWorkflowTheStartingPoint(sessionMap,currentSO))
			{
				returnValue = OrderConstants.WORKFLOW_STARTINGPOINT;
			}
			else
			{
				returnValue = OrderConstants.SOW_STARTPOINT_SOM;
			}
		}
		else
		{
			this.setDefaultTab(str);
			returnValue = GOTO_COMMON_WIZARD_CONTROLLER;
		}
		return returnValue;
	}

	
	
	

	public SOWScopeOfWorkTabDTO fixedDateType(SOWScopeOfWorkTabDTO sowScopeOfWork)
	{
		sowScopeOfWork.setServiceDate2(null);
		sowScopeOfWork.setEndTime(null);
		return sowScopeOfWork;
	}

	public SOWScopeOfWorkTabDTO getScopeOfWorkDTO()
	{
		return getModel();
	}

	public void setScopeOfWorkDTO(SOWScopeOfWorkTabDTO scopeOfWorkDTO)
	{
		this.sowModel = scopeOfWorkDTO;
	}

	public void setModel(SOWScopeOfWorkTabDTO scopeOfWorkDTO)
	{
		this.sowModel = scopeOfWorkDTO;
	}

	public ArrayList<SkillNodeVO> getSkillTreeMainCat()
	{
		return (ArrayList<SkillNodeVO>) getSession().getAttribute("mainServiceCategory");
	}

	public void setSkillTreeMainCat(ArrayList<SkillNodeVO> skillTreeMainCat)
	{
		this.skillTreeMainCat = skillTreeMainCat;
	}

	public SOWScopeOfWorkTabDTO getModel()
	{
		sowModel = (SOWScopeOfWorkTabDTO) SOWSessionFacility.getInstance().getTabDTO(OrderConstants.SOW_SOW_TAB);

		return sowModel;
	}

	public ArrayList<ServiceTypesVO> getSkillSelection()
	{
		ArrayList<ServiceTypesVO> skillSelectionFromSession = (ArrayList<ServiceTypesVO>) getSession().getAttribute("skillSelection");
		if (skillSelectionFromSession != null && skillSelectionFromSession.get(0) != null)
		{
			return skillSelectionFromSession;
		}
		return skillSelection;
	}

	public void setSkillSelection(ArrayList<ServiceTypesVO> skillSelection)
	{
		this.skillSelection = skillSelection;
	}

	public ArrayList<SkillNodeVO> getCategorySelection()
	{

		ArrayList<SkillNodeVO> categorySelectionFromSession = (ArrayList<SkillNodeVO>) getSession().getAttribute("categorySelection");
		if (categorySelectionFromSession != null && categorySelectionFromSession.get(0) != null)
		{
			return categorySelectionFromSession;
		}

		return categorySelection;
	}

	private void validateZipCode(SOWScopeOfWorkTabDTO sowScopeOfWork)
	{
		sowScopeOfWork.setErrors(new ArrayList<IError>());
		SOWContactLocationDTO contLocDto = sowScopeOfWork.getServiceLocationContact();
		if (sowScopeOfWork != null && contLocDto != null)
		{
			int zipCheck = LocationUtils.checkIfZipAndStateValid(contLocDto.getZip(), contLocDto.getState());
			switch (zipCheck)
			{
			case Constants.LocationConstants.ZIP_NOT_VALID:
				sowScopeOfWork.getErrors().add(
						new SOWError(sowScopeOfWork.getTheResourceBundle().getString("Zip"), sowScopeOfWork.getTheResourceBundle()
								.getString("Zip_Not_Valid"), OrderConstants.SOW_TAB_ERROR));
				break;
			case Constants.LocationConstants.ZIP_STATE_NO_MATCH:
				sowScopeOfWork.getErrors().add(
						new SOWError(sowScopeOfWork.getTheResourceBundle().getString("Zip"), sowScopeOfWork.getTheResourceBundle()
								.getString("Zip_State_No_Match"), OrderConstants.SOW_TAB_ERROR));
				break;
			}
		}
		String timeZone = getTimeZone(contLocDto.getZip());
		if (StringUtils.isNotEmpty(timeZone))
		{
			sowScopeOfWork.setTimeZone(timeZone);
		}
	}


	

	public void setCategorySelection(ArrayList<SkillNodeVO> categorySelection)
	{
		this.categorySelection = categorySelection;
	}

	public ArrayList<SkillNodeVO> getSubCategorySelection()
	{
		ArrayList<SkillNodeVO> subCategorySelectionFromSession = (ArrayList<SkillNodeVO>) getSession().getAttribute("subCategorySelection");
		if (subCategorySelectionFromSession != null && subCategorySelectionFromSession.get(0) != null)
		{
			return subCategorySelectionFromSession;
		}

		return subCategorySelection;
	}
    
	/**@Description: Setting spend limit labor and permit price in pricing DTO 
	 * @param sowScopeOfWork
	 */
	public void setSpendLimitLabor(SOWScopeOfWorkTabDTO sowScopeOfWork){
		SOWPricingTabDTO dto =(SOWPricingTabDTO)SOWSessionFacility.getInstance().getTabDTO(OrderConstants.SOW_PRICING_TAB);
		List<SOTaskDTO> tasks =sowScopeOfWork.getTasks();
		logger.info("Spend Limit Labor : "+ dto.getLaborSpendLimit());
		logger.info("Pre Paid Permit Price :"+ dto.getPermitPrepaidPrice());
		logger.info("Task Level Pricing Indicator"+ sowScopeOfWork.isTaskLevelPricingInd());
        Double totalLabourPrice = 0.0;
        Double prePaidpermitPrice = 0.0;
        try{
        //SL-20527 : We need to recalculate spend limit incase of taskLevel Pricing only.	
		if(null != tasks && !(tasks.isEmpty()) &&(sowScopeOfWork.isTaskLevelPricingInd())){
			for(SOTaskDTO task : tasks){
				if(null != task && null!= task.getFinalPrice() && (task.getTaskType()!= 2)){
					if(StringUtils.isNotBlank(task.getTaskStatus()) &&(OrderConstants.ACTIVE_TASK.equalsIgnoreCase(task.getTaskStatus()))){
					  totalLabourPrice = totalLabourPrice + task.getFinalPrice();
					}
				}
				if(null != task && null!= task.getFinalPrice() && (task.getTaskType()== 1)){
					if(StringUtils.isNotBlank(task.getTaskStatus()) &&(OrderConstants.ACTIVE_TASK.equalsIgnoreCase(task.getTaskStatus()))){
						prePaidpermitPrice = prePaidpermitPrice + task.getFinalPrice();
					}
				}
			}
			logger.info("Spend Limit Labor excluding deleted tasks :"+ totalLabourPrice);
			logger.info("Pre paid permit price  excluding deleted tasks :"+ prePaidpermitPrice);
			//setting spendLimit labor in DTO
			dto.setLaborSpendLimit(totalLabourPrice.toString());
			dto.setPermitPrepaidPrice(prePaidpermitPrice);
		  }
		
        }catch (Exception e) {
			logger.error("Exception in setting spend limit labor and prepaid price  excluding deleted tasks:"+ e.getMessage());
		}
	}
	/**@Description: Setting group spend Limit labor and total permit price
	 * @param sowScopeOfWork
	 */
	private void setGroupSpendLimitLabor(SOTaskDTO deletedTask) {
		SOWPricingTabDTO dto =(SOWPricingTabDTO)SOWSessionFacility.getInstance().getTabDTO(OrderConstants.SOW_PRICING_TAB);
		logger.info("Group Spend Limit Labor : "+ dto.getOgLaborSpendLimit());
		logger.info("Group Pre Paid Permit Price :"+ dto.getGroupTotalPermits());
		logger.info("Task Level Pricing Indicator"+ dto.isTaskLevelPricingInd());
        Double groupTotalLabourPrice = 0.0;
        Double groupPrePaidpermitPrice = 0.0;
        //Assigning values to dto
        groupTotalLabourPrice = dto.getOgLaborSpendLimit();
        groupPrePaidpermitPrice = dto.getGroupTotalPermits();
        Double deletedLaborPrice = 0.0;
        Double deletedPermitPrice = 0.0;
        try{
          //Checking task Level Pricing Indicator	
         if(dto.isTaskLevelPricingInd()){
				  if(deletedTask.getIsSaved() && (deletedTask.getTaskType()!= 2) &&(deletedTask.getTaskType()!= 1)){
					  deletedLaborPrice = deletedTask.getFinalPrice();
				  }
				  if(deletedTask.getIsSaved() && deletedTask.getTaskType()== 1){
					  deletedPermitPrice = deletedTask.getFinalPrice();
				  }
				  if(null!= groupTotalLabourPrice && groupTotalLabourPrice.intValue() != 0.0){
					  groupTotalLabourPrice = groupTotalLabourPrice - deletedLaborPrice;
				  }
				  if(null!= groupPrePaidpermitPrice && groupPrePaidpermitPrice.intValue() != 0.0){
					  groupPrePaidpermitPrice = groupPrePaidpermitPrice - deletedPermitPrice;
				  }
				  logger.info("Group Spend Limit Labor excluding deleted tasks :"+ groupTotalLabourPrice);
				  logger.info("Group Pre paid permit price  excluding deleted tasks :"+ groupPrePaidpermitPrice);
				  //setting spendLimit labor in DTO
				  dto.setOgLaborSpendLimit(groupTotalLabourPrice);
				  dto.setGroupTotalPermits(groupPrePaidpermitPrice);
        	  }		  
		
        }catch (Exception e) {
			logger.error("Exception in setting spend limit labor and prepaid price  excluding deleted tasks:"+ e.getMessage());
		}
		
	}
	private void setSelectedSkuCategory(SOWScopeOfWorkTabDTO modelScopeDTO) {
		Integer selectedSkuCategory = null;
		List<BuyerSkuVO> initialbuyerSkuNameList = (List<BuyerSkuVO>) getAttribute("buyerSkuNameForSkuList");
		List<BuyerSkuVO> buyerSkuNameList = (List<BuyerSkuVO>) getAttribute("selectedSkuCategory");
		List<SOTaskDTO> skuList = modelScopeDTO.getSkus();
		if(null ==buyerSkuNameList && null== initialbuyerSkuNameList && null!= skuList && !skuList.isEmpty() && null!=skuList.get(0)){
			selectedSkuCategory = skuList.get(0).getSelectedSkuCategoryNew();
			if(null== selectedSkuCategory && StringUtils.isNotBlank(modelScopeDTO.getSkuCategoryHidden())
					&& StringUtils.isNumeric(modelScopeDTO.getSkuCategoryHidden())){
				selectedSkuCategory = Integer.parseInt(modelScopeDTO.getSkuCategoryHidden());
			}
			try {
				buyerSkuNameList= fetchDelegate.fetchBuyerSkuNameByCategory(selectedSkuCategory);
			} catch (DelegateException e) {
				logger.error("Exception in setting selecte sku category in request");
				e.printStackTrace();
			}
		}
		setAttribute("selectedSkuCategory",buyerSkuNameList);
		
	}
	
	/**
	 * @param taskList
	 */
	private void setNoSubCategoryList(ArrayList<SOTaskDTO> taskList) {
		SkillNodeVO noSubCategoryVO = new SkillNodeVO();
		noSubCategoryVO.setActive(true);
		noSubCategoryVO.setNodeName("No Sub-Categories Available");
		noSubCategoryVO.setNodeId(-1);
		if(null!= taskList && !taskList.isEmpty()){
		for(SOTaskDTO taskDTO : taskList){
				if(null!= taskDTO.getSubCategoryId()){
					ArrayList<SkillNodeVO> subCategoryList = taskDTO.getSubCategoryList();
					if(null!= subCategoryList && !subCategoryList.isEmpty()){
						boolean noSubCatFound = false;
						for(SkillNodeVO skillNode : subCategoryList){
							if(null!=skillNode && skillNode.getNodeName().equalsIgnoreCase("No Sub-Categories Available")){
								noSubCatFound =true;
								break;
							}
						}
						if(!noSubCatFound){
							subCategoryList.add(noSubCategoryVO);
						}
					}
					
					taskDTO.setSubCategoryList(subCategoryList);
				}
			}
	  }
	}
	
	/**
	 * @param taskDTO
	 */
	private void setNoSubCategoryListinDTO(SOTaskDTO  taskDTO) {
		SkillNodeVO noSubCategoryVO = new SkillNodeVO();
		noSubCategoryVO.setActive(true);
		noSubCategoryVO.setNodeName("No Sub-Categories Available");
		noSubCategoryVO.setNodeId(-1);
		if(null!= taskDTO && null!= taskDTO.getSubCategoryId()){
		    ArrayList<SkillNodeVO> subCategoryList = taskDTO.getSubCategoryList();
				if(null!= subCategoryList && !subCategoryList.isEmpty()){
				  boolean noSubCatFound = false;
					for(SkillNodeVO skillNode : subCategoryList){
					   if(null!=skillNode && skillNode.getNodeName().equalsIgnoreCase("No Sub-Categories Available")){
							noSubCatFound =true;
							break;
						 }
						}
						if(!noSubCatFound){
							subCategoryList.add(noSubCategoryVO);
						}
					}
				 taskDTO.setSubCategoryList(subCategoryList);
			
		}
	}
	
	/**@Description : Code added to set the buyer logo from template of primary sku to the service order.
	 * @param sowScopeOfWork
	 * @throws BusinessServiceException 
	 */
	private void setBuyerLogo(SOWScopeOfWorkTabDTO sowScopeOfWork) throws BusinessServiceException {
		Integer buyerDocumentLogo =null;
		Integer existingLogo =null;
		String soId = (String) getAttribute(OrderConstants.SO_ID);
		if(null!= sowScopeOfWork && null!=sowScopeOfWork.getSkus() 
				&& !sowScopeOfWork.getSkus().isEmpty() && null!= sowScopeOfWork.getSkus().get(0)){
			    buyerDocumentLogo = sowScopeOfWork.getSkus().get(0).getBuyerDocumentLogo();
		}
		existingLogo = fetchDelegate.getLogoDocumentId(soId);
		if(null!= buyerDocumentLogo && null== existingLogo ){
			fetchDelegate.applyBrandingLogo(soId, buyerDocumentLogo);
		}
	}
	
	public void setSubCategorySelection(ArrayList<SkillNodeVO> subCategorySelection)
	{
		this.subCategorySelection = subCategorySelection;
	}

	public ArrayList<LookupVO> getPhoneTypes()
	{
		return phoneTypes;
	}

	public void setPhoneTypes(ArrayList<LookupVO> phoneTypes)
	{
		this.phoneTypes = phoneTypes;
	}

	public Integer getTaskIndex()
	{
		return taskIndex;
	}

	public void setTaskIndex(Integer taskIndex)
	{
		this.taskIndex = taskIndex;
	}

//	public Integer getMainServiceCategoryId()
//	{
//		return mainServiceCategoryId;
//	}
//
//	public void setMainServiceCategoryId(Integer mainServiceCategoryIdSession)
//	{
//		this.mainServiceCategoryId = mainServiceCategoryIdSession;
//	}

	public Integer getDelIndex()
	{
		return delIndex;
	}

	public void setDelIndex(Integer delIndex)
	{
		this.delIndex = delIndex;
	}

	public String getPrevious()
	{
		return previous;
	}

	public void setPrevious(String previous)
	{
		this.previous = previous;
	}

	public String getNext()
	{
		return next;
	}

	public void setNext(String next)
	{
		this.next = next;
	}

	public IBuyerFeatureSetBO getBuyerFeatureSetBO() {
		return buyerFeatureSetBO;
	}

	public void setBuyerFeatureSetBO(IBuyerFeatureSetBO buyerFeatureSetBO) {
		this.buyerFeatureSetBO = buyerFeatureSetBO;
	}

}
