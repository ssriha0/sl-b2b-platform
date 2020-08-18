package com.newco.marketplace.web.dto.buyer;

import java.util.ArrayList;
import java.util.List;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.utils.UIUtils;
import com.newco.marketplace.web.dto.IError;
import com.newco.marketplace.web.dto.SOWBaseTabDTO;

/**
 * @author nsanzer
 *
 */
public class BuyerManageCustomReferencesDTO extends SOWBaseTabDTO{
	private static final long serialVersionUID = -7927711052687801639L;
	private List<BuyerReferenceDTO> buyerRefs = new ArrayList<BuyerReferenceDTO>();
	private BuyerReferenceDTO buyerRef;
	private String message;
	
	public String getTabIdentifier() {
		// TODO Auto-generated method stub
		return "BuyerManageCustomReferencesDTO";
	}
	
	public void validate(){
//		logger.info("In validate.");
//		manageCustomRefsDTO = getModel();
		List<String> refNames = new ArrayList<String>();
		List<BuyerReferenceDTO> buyerRefDTOs = getBuyerRefs();
		//Get List of ref names
		for (BuyerReferenceDTO buyerReferenceDTO : buyerRefDTOs) {
			refNames.add(buyerReferenceDTO.getReferenceType());
		}
		
		if (getBuyerRef() != null) {
			//If not in edit mode, check that they are not trying to enter a duplicate ref name
			if ("false".equalsIgnoreCase(getBuyerRef().getRefEdit())){
				for (BuyerReferenceDTO buyerRefDTO : buyerRefDTOs) {
					if (getBuyerRef().getReferenceType().equalsIgnoreCase(buyerRefDTO.getReferenceType())){
						addError("refType", "'"+ getBuyerRef().getReferenceType()
								+ "' has already been used.", OrderConstants.SOW_TAB_ERROR);
					}
				}
			}else{ //check for duplicates within edit values. 
				for (BuyerReferenceDTO buyerRefDTO : buyerRefDTOs) {
					if (getBuyerRef().getReferenceType().equalsIgnoreCase(buyerRefDTO.getReferenceType())
							&& !getBuyerRef().getBuyerRefTypeId().equals(buyerRefDTO.getBuyerRefTypeId()) )
						addError("refType", "'"+ getBuyerRef().getReferenceType()
								+ "' has already been used.", OrderConstants.SOW_TAB_ERROR);
				}
			}
			//Ref name must be selected. 
			if (getBuyerRef().getReferenceType() == null ||getBuyerRef().getReferenceType().trim().equals("")) {
				addError("refType", "Reference Type name cannot be empty.", OrderConstants.SOW_TAB_ERROR);
			}
			//Either Buyer or Provider must be selected. 
			if ("false".equalsIgnoreCase(getBuyerRef().getBuyerInput()) && "false".equalsIgnoreCase(getBuyerRef().getProviderInput())){
				addError("BuyerProvider", "Either buyer or provider input must be selected", OrderConstants.SOW_TAB_ERROR );
			}
			//Both Buyer AND Provider cannot be selected. 
			if ("true".equalsIgnoreCase(getBuyerRef().getBuyerInput()) && "true".equalsIgnoreCase(getBuyerRef().getProviderInput())){
				addError("BuyerProvider", "Both Buyer and Provider cannot be selected.", OrderConstants.SOW_TAB_ERROR );
			}			
			getBuyerRef().setReferenceDescription(UIUtils.encodeSpecialChars(getBuyerRef().getReferenceDescription()));
		}
		
		List<IError> errorList = getErrorsOnly();
		setErrors(errorList);
	
	}
	
	
	
	public List<BuyerReferenceDTO> getBuyerRefs() {
		return buyerRefs;
	}
	public void setBuyerRefs(List<BuyerReferenceDTO> buyerRefs) {
		this.buyerRefs = buyerRefs;
	}
	public BuyerReferenceDTO getBuyerRef() {
		return buyerRef;
	}
	public void setBuyerRef(BuyerReferenceDTO buyerRef) {
		this.buyerRef = buyerRef;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	

}
