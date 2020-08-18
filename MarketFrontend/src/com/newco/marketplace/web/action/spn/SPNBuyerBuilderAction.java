package com.newco.marketplace.web.action.spn;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.web.delegates.ISPNBuyerDelegate;
import com.newco.marketplace.web.dto.IError;
import com.newco.marketplace.web.dto.SOWError;
import com.newco.marketplace.web.dto.SPNBuilderDocRowDTO;
import com.newco.marketplace.web.dto.SPNBuilderFormDTO;
import com.newco.marketplace.web.dto.ServiceOrdersCriteria;
import com.newco.marketplace.web.utils.SLStringUtils;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

public class SPNBuyerBuilderAction extends SPNBaseAction implements Preparable, ModelDriven<SPNBuilderFormDTO>
{

	private static final long serialVersionUID = -5816310320832553273L;
	private SPNBuilderFormDTO spnBuilderFormDTO = new SPNBuilderFormDTO();
	private ISPNBuyerDelegate buyerSpnDelegate;
	private List<SPNBuilderDocRowDTO> allDocuments;
	private static final Logger logger = Logger.getLogger("SPNBuyerBuilderAction");
		
	
	public SPNBuyerBuilderAction(ISPNBuyerDelegate buyerSpnDelegate){
		this.buyerSpnDelegate = buyerSpnDelegate;
	}
	
	/* entry point for all methods, first comes here */
	public void prepare() throws Exception
	{
		createCommonServiceOrderCriteria();

		// Document dropdown
		ServiceOrdersCriteria criteria = this.get_commonCriteria();		
		allDocuments = buyerSpnDelegate.getAllDocumentsList(criteria.getCompanyId(), Constants.DocumentTypes.RESOURCE, criteria.getRoleId(), criteria.getVendBuyerResId());
		
		String spnID = (String)getParameter("spnID");
		/* loads name & id for languages*/
		populateLookup();
		
		if(!SLStringUtils.isNullOrEmpty(spnID))
		{
			//Edit	
			Integer spnIDInteger = Integer.parseInt(spnID);
			spnBuilderFormDTO.setSpnId(spnIDInteger);
			spnBuilderFormDTO = buyerSpnDelegate.getSPNDetails(spnIDInteger, allDocuments);
					
			/* set name for category , skills & language as only respective ids are persisted in DB */
			spnBuilderFormDTO = setMainCategoryName(spnBuilderFormDTO);
			spnBuilderFormDTO = loadCriteriaSkillInfo(spnBuilderFormDTO.getTheCriteria().getMainServiceCategoryId(), spnBuilderFormDTO);
			spnBuilderFormDTO = setLanguageDesc(spnBuilderFormDTO);
			
			setModel(spnBuilderFormDTO);			
		}
		else
		{
			SPNBuilderFormDTO dto = (SPNBuilderFormDTO)getSession().getAttribute("spnBuilderFormInfo");
			if(dto != null)
				setModel(dto);			
			
		}
	}	
	
	public String displayPage() throws Exception
	{
		remapDocumentsList(getModel());
		return SUCCESS;
	}
	
	/*
	 * Add Criteria ,loads SPN info & forwards to SPN Criteria Builder page
	 */
	public String buttonAddCriteria() throws Exception
	{
		SPNBuilderFormDTO dto = getModel();
		getSession().setAttribute("spnBuilderFormInfo", dto);
		return "add_criteria_success";
	}
	
	/*
	 * Save the SPN info
	 */
	public String buttonSave() throws Exception
	{
		if(isFormValid()){
			SPNBuilderFormDTO dto = getModel();
			remapDocumentsList(dto);
			
			String spnID = (String)getParameter("spnID");
			
			if(!SLStringUtils.isNullOrEmpty(spnID))
			{
				//Edit		

				Integer spnIDInteger = Integer.parseInt(spnID);
				dto.setSpnId(spnIDInteger);
			}
			
			
			ServiceOrdersCriteria criteria = this.get_commonCriteria();	
			dto.setBuyerId(criteria.getCompanyId());
			if(dto.getSpnId()!= null && dto.getSpnId().intValue() >0){
				buyerSpnDelegate.updateSPN(dto);
			}else{
				buyerSpnDelegate.createSPN(dto);
			}
		
			return "save_success";
		}	
		else
			return "save_failure";
		
	}
	
	// Get All the documents, set the 'checked' flag based on the checkedDocuments
	// list retrieved in initial get from DB.  Should this be populated in the
	// mapper or delegate?
	private void remapDocumentsList(SPNBuilderFormDTO formDTO)
	{
		if(formDTO == null)
			return;
		
		List<SPNBuilderDocRowDTO> checkedDocuments = formDTO.getDocumentsDetailsList();
		
		formDTO.setDocumentsDetailsList(allDocuments);
		
		if (null != checkedDocuments && checkedDocuments.size() > 0) {
			int i=0;
			for(SPNBuilderDocRowDTO row : formDTO.getDocumentsDetailsList())
			{
				row.setChecked(checkedDocuments.get(i).getChecked());
				i++;
			}
		}
	}
	
	/**
	 * validate form Info , check if all required data is filled in
	 * @return
	 */
	private boolean isFormValid()
	{
		SPNBuilderFormDTO dto = getModel();
		
		//Test of validation handling
		SOWError error;
		List<IError> errors = new ArrayList<IError>();		
		
		
		// Contact Name
		if(com.newco.marketplace.web.utils.SLStringUtils.isNullOrEmpty(dto.getContactName()))
		{
			error = new SOWError("contactName", "Contact Name is required", OrderConstants.FM_ERROR);
			errors.add(error);
		}
		// Contact Email
		if(SLStringUtils.isNullOrEmpty(dto.getContactEmail())){
			error = new SOWError("contactEmail", "Contact Email is required", OrderConstants.FM_ERROR);
			errors.add(error);
		}
		//Network Name
		if(SLStringUtils.isNullOrEmpty(dto.getNetworkName())){
			error = new SOWError("networkName", "Network name is required", OrderConstants.FM_ERROR);
			errors.add(error);
		}
		//Network Description
		if(SLStringUtils.isNullOrEmpty(dto.getNetworkDescription())){
			error = new SOWError("networkDescription", "Network Description is required", OrderConstants.FM_ERROR );
			errors.add(error);
		}
		//Approval Instructions
		if(SLStringUtils.isNullOrEmpty(dto.getApprovalInstructions())){
			error = new SOWError("approvalInstructions", "Approval Instructions are required", OrderConstants.FM_ERROR);
			errors.add(error);
		}
		//Criteria
		if(dto.getTheCriteria()== null || (dto.getTheCriteria().getMainServiceCategoryId() < 1)){
			error = new SOWError("networkCriteria", "Criteria is required", OrderConstants.FM_ERROR);
			errors.add(error);
		}
		
		// If we have errors, put them in request.
		if(errors.size() > 0)
		{
			setAttribute("errors", errors);
			return false;
		}
		
		
		return true;
	}
	
	/*
	 * delete SPN
	 */
	public String buttonDelete() throws Exception
	{
		SPNBuilderFormDTO dto = getModel();
		try{
			buyerSpnDelegate.deleteSPN(dto.getSpnId());
		}catch(Exception e){
			//FieldError
			
		}
		
		return "delete";
	}
	
		
	
	public SPNBuilderFormDTO getModel()
	{
			return spnBuilderFormDTO;
	}
	
	public void setModel(SPNBuilderFormDTO theModel) {
		this.spnBuilderFormDTO = theModel;
	}

	
	
}
