package com.newco.marketplace.web.validator.sow;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.web.dto.IError;
import com.newco.marketplace.web.dto.IWarning;
import com.newco.marketplace.web.dto.SOWBaseTabDTO;
import com.newco.marketplace.web.dto.SOWContactLocationDTO;
import com.newco.marketplace.web.dto.SOWScopeOfWorkTabDTO;
import com.newco.marketplace.web.dto.SOWTabStateVO;
import com.newco.marketplace.web.dto.ServiceOrderWizardBean;
import com.newco.marketplace.web.dto.TabNavigationDTO;

public class SOWTabstateManagerFacility {

	private static SOWTabstateManagerFacility _facility = new SOWTabstateManagerFacility();
	
	private SOWTabstateManagerFacility(){}
	
	public static SOWTabstateManagerFacility getInstance(){
		if(_facility == null)
		{
			_facility = new SOWTabstateManagerFacility();
		}
		return _facility;
	}
	
	public void populateTabStates(ServiceOrderWizardBean serviceOrderWizardBean,SOWBaseTabDTO sowTabDTO, TabNavigationDTO tabNavigationDTO){

		String oldNextURL = serviceOrderWizardBean.getNextURL();
		String oldPreviousURL = serviceOrderWizardBean.getPreviousURL();
		//HashMap<String, Object> oldTabDTOHashMap = serviceOrderWizardBean.getTabDTOs();
		//HashMap<String, Object> oldTabStateHashMap = serviceOrderWizardBean.getTabStateDTOs();
		
		//Set the tabStates		
		setTabStates(serviceOrderWizardBean, sowTabDTO, tabNavigationDTO.getComingFromTab());
		
		//1. Get the tabDTO to check if there are errors. 
		HashMap<String, Object> dtoList = serviceOrderWizardBean.getTabDTOs();
		//SOWBaseTabDTO sowBaseTabDTO = (SOWBaseTabDTO)dtoList.get(tabNavigationDTO.getComingFromTab());
		List<IError> errorList = sowTabDTO.getErrorsOnly();
	    List<IWarning> warningList = sowTabDTO.getWarningsOnly();
		
		int errorListSize = getListSize(errorList);
		int warningListSize = getListSize(warningList);
		HashMap<String, Object> tabStateDTOs = serviceOrderWizardBean.getTabStateDTOs();
		SOWTabStateVO sowTabStateVO = (SOWTabStateVO)tabStateDTOs.get(tabNavigationDTO.getComingFromTab());

		if (errorListSize>0 )
		{
			// Don't proceed to the next tab
			serviceOrderWizardBean.setGoingToTab(LookupHash.getMyActionName(tabNavigationDTO.getComingFromTab()));
			serviceOrderWizardBean.setComingFromTab(LookupHash.getMyActionName(tabNavigationDTO.getComingFromTab()));
			sowTabStateVO.setTabErrorState(OrderConstants.SOW_TAB_ERROR);
			sowTabStateVO.setTabCompleteState(null);

		}
		else{
			String currentTabIdentifier = tabNavigationDTO.getComingFromTab();
			if (tabNavigationDTO.getActionPerformed() != null && 
				tabNavigationDTO.getActionPerformed().length() > 0 && 
				tabNavigationDTO.getActionPerformed().equals(OrderConstants.SOW_NEXT_ACTION)){
				String nextTab = getNextTab(serviceOrderWizardBean, currentTabIdentifier); 
				serviceOrderWizardBean.setGoingToTab(LookupHash.getMyActionName(nextTab));
				String nextUrl = getNextTab(serviceOrderWizardBean, nextTab); 
				serviceOrderWizardBean.setNextURL(LookupHash.getMyActionName(nextUrl));
				String previousUrl = getPreviousUrl(serviceOrderWizardBean, nextTab); 
				serviceOrderWizardBean.setPreviousURL(LookupHash.getMyActionName(previousUrl));
			}
			else if (tabNavigationDTO.getActionPerformed() != null && 
					tabNavigationDTO.getActionPerformed().length() > 0 && 
					tabNavigationDTO.getActionPerformed().equals(OrderConstants.SOW_PREVIOUS_ACTION))
			{
				serviceOrderWizardBean.setNextURL(LookupHash.getMyActionName(currentTabIdentifier));
				String s1 = getPreviousUrl(serviceOrderWizardBean, currentTabIdentifier);
				String s2 = getPreviousUrl(serviceOrderWizardBean, s1);
				serviceOrderWizardBean.setGoingToTab(LookupHash.getMyActionName(s1));
				serviceOrderWizardBean.setPreviousURL(LookupHash.getMyActionName(s2));
				
			}
			else if (tabNavigationDTO.getActionPerformed() != null && 
					tabNavigationDTO.getActionPerformed().length() > 0 && 
					tabNavigationDTO.getActionPerformed().equals(OrderConstants.SOW_SAVE_AS_DRAFT_ACTION))
			{
				serviceOrderWizardBean.setGoingToTab(OrderConstants.SOW_EXIT_SAVE_AS_DRAFT);
				
			}
			else if (tabNavigationDTO.getActionPerformed() != null && 
					tabNavigationDTO.getActionPerformed().length() > 0 && 
					tabNavigationDTO.getActionPerformed().equals(OrderConstants.SOW_GO_TO_REVIEW_ACTION))
			{

				serviceOrderWizardBean.setGoingToTab(LookupHash.getMyActionName(OrderConstants.SOW_REVIEW_TAB));
				serviceOrderWizardBean.setPreviousURL(LookupHash.getMyActionName(OrderConstants.SOW_PRICING_TAB));
				
			}
			
			sowTabStateVO.setTabErrorState(null);
			serviceOrderWizardBean.setComingFromTab(LookupHash.getMyActionName(tabNavigationDTO.getComingFromTab()));
		}
		if ( tabNavigationDTO.getActionPerformed() != null && 
				tabNavigationDTO.getActionPerformed().length() > 0 && 
				(tabNavigationDTO.getActionPerformed().equals(OrderConstants.SOW_POST_SO_ACTION)))
		{
			serviceOrderWizardBean.setComingFromTab(LookupHash.getMyActionName(tabNavigationDTO.getComingFromTab()));
			serviceOrderWizardBean.setGoingToTab(LookupHash.getMyActionName(tabNavigationDTO.getComingFromTab()));
			serviceOrderWizardBean.setNextURL(oldNextURL);
			serviceOrderWizardBean.setPreviousURL(oldPreviousURL);
			//serviceOrderWizardBean.setTabDTOs(oldTabDTOHashMap);
			//serviceOrderWizardBean.setTabStateDTOs(oldTabStateHashMap);
		}
		if (warningListSize>0){
			sowTabStateVO.setTabWarningState(OrderConstants.SOW_TAB_WARNING);
			sowTabStateVO.setTabCompleteState(null);
		}
		else{
			sowTabStateVO.setTabWarningState(null);
		}
		if (warningListSize==0 && errorListSize == 0){
			sowTabStateVO.setTabCompleteState(OrderConstants.SOW_TAB_COMPLETE);
			sowTabStateVO.setTabErrorState(null);
			sowTabStateVO.setTabWarningState(null);
			
		}
		
	}
	public void populateTabStates(ServiceOrderWizardBean serviceOrderWizardBean, HashMap<String, Object> tabDTOs, String incomingMode){
		if (incomingMode.equals(OrderConstants.SOW_EDIT_MODE)){
			HashMap<String, Object> tabStateDTOs = serviceOrderWizardBean.getTabStateDTOs();
			Set set = tabStateDTOs.keySet();
			Iterator iterator =(Iterator) set.iterator();
			while (iterator.hasNext()){
				String key = (String)iterator.next();
				if(key != null)
				{
					//TODO it was crashing with class cast exception with string
					if (!key.equalsIgnoreCase(OrderConstants.SO_ID))  
					{
						SOWBaseTabDTO sowBaseTabDTO = (SOWBaseTabDTO)tabDTOs.get(key);
						SOWTabStateVO sowTabStateVO = (SOWTabStateVO)tabStateDTOs.get(key);
						List<IError> errorList = null;
						List<IWarning> warningList = null;
						if(sowBaseTabDTO != null){
							errorList = sowBaseTabDTO.getErrorsOnly();
							warningList = sowBaseTabDTO.getWarningsOnly();
						}
						
						int errorListSize = getListSize(errorList);
						int warningListSize = getListSize(warningList);
						if (warningListSize>0){
							sowTabStateVO.setTabWarningState(OrderConstants.SOW_TAB_WARNING);
							sowTabStateVO.setTabCompleteState(null);
						}
						else{
							sowTabStateVO.setTabWarningState(null);
						}
						if (errorListSize>0 )
						{
							// Don't proceed to the next tab
							sowTabStateVO.setTabErrorState(OrderConstants.SOW_TAB_ERROR);
							sowTabStateVO.setTabCompleteState(null);

						}						
						if (warningListSize==0 && errorListSize == 0){
							sowTabStateVO.setTabCompleteState(OrderConstants.SOW_TAB_COMPLETE);
							sowTabStateVO.setTabErrorState(null);
							sowTabStateVO.setTabWarningState(null);
							
						}
						
					}
				}
			}			
		}
	}
private String getPreviousUrl(ServiceOrderWizardBean serviceOrderWizardBean,String tabId ){
	return getMyTab(serviceOrderWizardBean,"P", tabId);
	
}
private String getNextTab(ServiceOrderWizardBean serviceOrderWizardBean, String tabId){
	return getMyTab(serviceOrderWizardBean,"N", tabId);
}
private String getNextUrl(ServiceOrderWizardBean serviceOrderWizardBean, String tabId){
	return getMyTab(serviceOrderWizardBean,"N",tabId);
}

private String getMyTab(ServiceOrderWizardBean serviceOrderWizardBean, String prevNextIndicator, String tabInContext){
	HashMap<String, String> allTabSequenceInfo = LookupHash.getAllTabSequenceInfo();
	String currentTabIdentifier = tabInContext;
	String temp =(String)allTabSequenceInfo.get(currentTabIdentifier);
	int currentTabId = new Integer(temp).intValue();
	HashMap<String, Object> tabStateInfo = serviceOrderWizardBean.getTabStateDTOs();
	boolean myFlag = true;
	while (myFlag==true){
		 if (prevNextIndicator.equals("N"))
		 {
			 currentTabId ++;
		 }
		 else
		 {
			 currentTabId --;
		 }
		String s = LookupHash.getMyTab(allTabSequenceInfo, new Integer(currentTabId).toString());
		if (s==null) {break;}
		SOWTabStateVO sowTabStateVO = (SOWTabStateVO)tabStateInfo.get(s);
		if(sowTabStateVO!=null )
		{
			String tabState = sowTabStateVO.getTabState();
			if (tabState.equals(OrderConstants.SOW_TAB_INACTIVE))
			{
			}
			else
			{
				myFlag = false;
			}
		}
	}	
	if (currentTabId <0)
	{
		currentTabId = 1;
	}
	else if (currentTabId>allTabSequenceInfo.size())
	{
		//hard coded for want of logic
		currentTabId = 6;
	}

	String nextTabStr = new String (new Integer(currentTabId).toString());
	String myNextTabStr = LookupHash.getMyTab(allTabSequenceInfo, nextTabStr);
	
	return myNextTabStr;
}	
	
private void setTabStates(ServiceOrderWizardBean serviceOrderWizardBean,SOWBaseTabDTO sowTabDTO, String tabName){
	HashMap<String, Object> tabStateInfo = serviceOrderWizardBean.getTabStateDTOs();
	
	//Check if Parts tab need to be enabled
	if (tabName.equals(OrderConstants.SOW_SOW_TAB)) 
	{
		//HashMap<String, Object> tabDTOs = serviceOrderWizardBean.getTabDTOs();
		SOWScopeOfWorkTabDTO sowScopeOfWorkTab = (SOWScopeOfWorkTabDTO)sowTabDTO;
		if (sowScopeOfWorkTab!=null){
			Integer mainServiceCategoryId = sowScopeOfWorkTab.getMainServiceCategoryId();
			SOWContactLocationDTO serviceLocationContact = sowScopeOfWorkTab.getServiceLocationContact();
			SOWTabStateVO parts_SowTabStateVO = (SOWTabStateVO)tabStateInfo.get(OrderConstants.SOW_PARTS_TAB);
			SOWTabStateVO providers_SowTabStateVO = (SOWTabStateVO)tabStateInfo.get(OrderConstants.SOW_PROVIDERS_TAB);
			parts_SowTabStateVO.setTabState(OrderConstants.SOW_TAB_ENABLED);
						
			if (mainServiceCategoryId!=null && serviceLocationContact!= null ) {
				providers_SowTabStateVO.setTabState(OrderConstants.SOW_TAB_ENABLED);
			}
			else{
				providers_SowTabStateVO.setTabState(OrderConstants.SOW_TAB_INACTIVE);
			}
			
		}
	}
	/*Provider tab is enabled under the following
	conditions: In the Scope of Work tab, user has
	selected a Main Category and added at least 1 Task
	and a valid service location address.
	*/
	
	/*Parts tab is disabled unless the Buyer option in the
	Parts will be supplied by section of the Scope of
	Work section is selected*/
	
}

public void setTabStateForReviewTab(ServiceOrderWizardBean serviceOrderWizardBean, String status, TabNavigationDTO tabNavigationDTO){
	if (status.equals(OrderConstants.SOW_REVIEW_TAB_SUCCESS))
	{
		serviceOrderWizardBean.setGoingToTab(serviceOrderWizardBean.getStartPoint());
	}else if (status.equals(OrderConstants.SOW_REVIEW_TAB_FAILED))
	{
		serviceOrderWizardBean.setGoingToTab(tabNavigationDTO.getComingFromTab());
	}
	serviceOrderWizardBean.setComingFromTab(tabNavigationDTO.getComingFromTab());

	
	
}

private int getListSize(List list){
	int i =0;
	if (list!=null)
	{ if (list.size()>0) 
		{
			 i = list.size();
		}
	}
	return i;
}
	
	
}
