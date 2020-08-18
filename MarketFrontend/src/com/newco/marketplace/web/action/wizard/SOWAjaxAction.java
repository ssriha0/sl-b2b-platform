package com.newco.marketplace.web.action.wizard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.fmt.LocalizationContext;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.newco.marketplace.business.iBusiness.buyer.IBuyerRegistrationBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.LocationVO;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.skillTree.ServiceTypesVO;
import com.newco.marketplace.dto.vo.skillTree.SkillNodeVO;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.delegates.ILookupDelegate;
import com.newco.marketplace.web.dto.SOWAdditionalInfoTabDTO;
import com.newco.marketplace.web.dto.SOWContactLocationDTO;
import com.newco.marketplace.web.dto.SOWCustomRefDTO;
import com.newco.marketplace.web.dto.SOWScopeOfWorkTabDTO;
import com.newco.marketplace.web.dto.ajax.AjaxResultsDTO;
import com.newco.marketplace.web.validator.sow.SOWSessionFacility;

public class SOWAjaxAction extends SLBaseAction {

	private static final long serialVersionUID = 7674757905460024732L;
	
	private static final Logger logger = Logger.getLogger(SOWAjaxAction.class.getName());

	private ILookupDelegate lookupDelegate = null;
	private IBuyerRegistrationBO buyerRegistrationBO = null;
	private ArrayList<SkillNodeVO> skillTreeMainCat;
	private HashMap<Integer, ArrayList<ServiceTypesVO>> skillSelectionMap;
	
	public String validateZipCode() throws Exception
	{
		// Get resource Bundle for validation error messages
		ServletContext servletContext = ServletActionContext.getServletContext();
		LocalizationContext localizationContext = (LocalizationContext)servletContext.getAttribute("serviceliveCopyBundle");
		String invalidZipCodeMsg = localizationContext.getResourceBundle().getString("wizard.invalidZipCode");
		

		//SL-19820
		String soId = getParameter(OrderConstants.SO_ID);
		setAttribute(OrderConstants.SO_ID, soId);
		logger.info("SO_ID = "+soId);
		
		//startCopy will be true, if the user selects the first option in the zip-modal (for copy SO flow).
		//In such case we update the session value and return the call
		String startCopy = getParameter("startCopy");
		if(startCopy != null && startCopy.equalsIgnoreCase("true")){
			/*SL-19820
			 * getSession().setAttribute("startCopy", "true");*/
			 getSession().setAttribute("startCopy_"+soId, "true");
			return NONE;
		}
		
		//Remove previously selected main service category from session SL 18080
		//Sl-19820
		//getSession().removeAttribute("mainServiceCategoryId");
		getSession().removeAttribute("mainServiceCategoryId_"+soId);
		
		String zipcode = getParameter("zipcode");
		logger.info("Zip Code = "+zipcode);
		

		// Initialize ajax response object DTO
		AjaxResultsDTO actionResults = new AjaxResultsDTO();
    	actionResults.setActionState(0);
    	actionResults.setResultMessage(invalidZipCodeMsg);

		// Validate zipcode number format
    	boolean validFormat = false;
    	String stateCode=null;
    	
    	try {
			Integer.parseInt(zipcode);
			validFormat = true;
		} catch (NumberFormatException nfEx) {
			logger.info("Invalid zipcode format - NumberFormatException");
			validFormat = false;
		}
		
		if (validFormat) {
			// Validate zipcode from database
			LocationVO locationVO = lookupDelegate.checkIfZipISValid(zipcode);
			
			// Prepare response output
			if (locationVO == null) {
				logger.info("Invalid zipcode - no state found in database");
			} else {
				stateCode = locationVO.getState();
				logger.info("State Code = " + stateCode);
				
				List<String> blackoutStateCodes = buyerRegistrationBO.getBlackoutStates();
				if (blackoutStateCodes != null && blackoutStateCodes.indexOf(stateCode) >= 0) {
					StringBuilder sb = new StringBuilder();
					int blackoutStateCount = blackoutStateCodes.size();
					int index = 0;
					while(index < blackoutStateCount) {
						sb.append(blackoutStateCodes.get(index));
						++index;
						if (index < blackoutStateCount) {
							sb.append(", ");
						}
					}
					String errMsg = "We are unable to fulfill buyer requests in the following states/U.S. Territories: " + sb.toString();
					actionResults.setResultMessage(errMsg);
					logger.info(errMsg);
				} else {
					List<LookupVO> states = (List<LookupVO>)servletContext.getAttribute(Constants.SERVLETCONTEXT.STATES_LIST);
					String stateDesc = null;
					for (LookupVO lookupVO : states) {
						if (lookupVO.getType().equalsIgnoreCase(stateCode)) {
							stateDesc = lookupVO.getDescr();
							break;
						}
					}
					
					if (stateDesc == null) {
						logger.info("Invalid zipcode - State description not found in servlet context");
					} else {
						// SUCCESS
						logger.info("Success validation of zip code.Going to populate DTOs");
						createCommonServiceOrderCriteria();
						String buyerId = get_commonCriteria().getSecurityContext().getCompanyId()+ "";
						String mainCategoryString= repopulateCategoryAndSkills(buyerId,stateCode,soId);
						logger.info("Main Category skills"+mainCategoryString);
						SOWScopeOfWorkTabDTO scopeOfWorkDTO2 = 	(SOWScopeOfWorkTabDTO)SOWSessionFacility.getInstance().getTabDTO(OrderConstants.SOW_SOW_TAB);
						SOWAdditionalInfoTabDTO additionalInfoTabDTO = 	(SOWAdditionalInfoTabDTO)SOWSessionFacility.getInstance().getTabDTO(OrderConstants.SOW_ADDITIONAL_INFO_TAB);
						List<SOWCustomRefDTO> customRefs = new ArrayList<SOWCustomRefDTO>();
						
						additionalInfoTabDTO.setCustomRefs(customRefs);
						additionalInfoTabDTO.setAlternateLocationContact(new SOWContactLocationDTO());
						additionalInfoTabDTO.setAltServiceLocationContactFlg(false);
						scopeOfWorkDTO2.getServiceLocationContact().setState(stateCode);
						scopeOfWorkDTO2.getServiceLocationContact().setZip(zipcode);
						
						//The following will be used as a flag in the front-end to control the display of zip-modal.
						//The flag will be set to false once the user initiates the SO copy process by clicking on the copy button in SOD\SOM (ref: SOWControllerAction)
					    //SL-19820 
						//getSession().setAttribute("startCopy", "true");
						getSession().setAttribute("startCopy_"+soId, "true");
						setAttribute("startCopy", "true");
						
						actionResults.setActionState(1);
			        	actionResults.setResultMessage(SUCCESS);
						actionResults.setAddtionalInfo1(stateCode);
						actionResults.setAddtionalInfo2(stateDesc);
						actionResults.setAddtionalInfo3(mainCategoryString);
						
					}
				}
			}
		}
		
		
		// Response output
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		String responseStr = actionResults.toXml();
		logger.info(responseStr);
		response.getWriter().write(responseStr);
		
		return NONE;
	}

	public void setLookupDelegate(ILookupDelegate lookupDelegate) {
		this.lookupDelegate = lookupDelegate;
	}
	
	/*public String repopulateCategoryAndSkills(String buyerId,String stateCd,String soId) throws Exception {	*/
	//Sl-19820
	  public String repopulateCategoryAndSkills(String buyerId,String stateCd,String soId) throws Exception {	
				
		skillTreeMainCat = lookupDelegate.getNotBlackedOutSkillTreeMainCategories(buyerId,stateCd);
		Iterator<SkillNodeVO> i = skillTreeMainCat.iterator();
		SkillNodeVO singleSkillNode;
		Map<Integer, String> mainServiceCategoryNamesMap = new HashMap<Integer,String>();
		skillSelectionMap = new HashMap();
		String xmlString = new String();
		while(i.hasNext()){
			singleSkillNode = i.next();
			mainServiceCategoryNamesMap.put(singleSkillNode.getNodeId(), singleSkillNode.getNodeName());
			logger.info("to be removed: singleSkillNode.getNodeName():"+singleSkillNode.getNodeName());
			if (!xmlString.equalsIgnoreCase(null)){
				xmlString=xmlString+singleSkillNode.getNodeId() +"|"+singleSkillNode.getNodeName().replaceAll("&", "&amp;")+";";
			}else{
				xmlString=singleSkillNode.getNodeId() +"|"+singleSkillNode.getNodeName().replaceAll("&", "&amp;")+";";
			} 
				if(singleSkillNode.getServiceTypes() != null){
				skillSelectionMap.put(singleSkillNode.getNodeId(), new ArrayList(singleSkillNode.getServiceTypes()));
			}
		}
		logger.info("to be removed: xmlString:"+xmlString);
		//SL-19820
		getSession().setAttribute("mainServiceCategoryNamesMap_"+soId, mainServiceCategoryNamesMap);
		//getSession().setAttribute("mainServiceCategoryNamesMap", mainServiceCategoryNamesMap);
		getSession().setAttribute("mainServiceCategory", skillTreeMainCat);
		//Sl-19820
		//getSession().setAttribute("skillSelectionMap", skillSelectionMap);
		getSession().setAttribute("skillSelectionMap_"+soId, skillSelectionMap);
		return xmlString;	
	}
		
	public IBuyerRegistrationBO getBuyerRegistrationBO() {
		return buyerRegistrationBO;
	}

	public void setBuyerRegistrationBO(IBuyerRegistrationBO buyerRegistrationBO) {
		this.buyerRegistrationBO = buyerRegistrationBO;
	}
	
}
