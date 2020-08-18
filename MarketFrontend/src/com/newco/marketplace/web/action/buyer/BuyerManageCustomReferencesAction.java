package com.newco.marketplace.web.action.buyer;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.buyer.IBuyerBO;
import com.newco.marketplace.dto.vo.buyer.BuyerReferenceVO;
import com.newco.marketplace.utils.UIUtils;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.constants.SOConstants;
import com.newco.marketplace.web.dto.buyer.BuyerManageCustomReferencesDTO;
import com.newco.marketplace.web.dto.buyer.BuyerReferenceDTO;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

/**
 * @author nsanzer
 *
 */
public class BuyerManageCustomReferencesAction extends SLBaseAction implements Preparable,ModelDriven<BuyerManageCustomReferencesDTO> {

	/**	serialVersionUID */
	private static final long serialVersionUID = -7170710137469364079L;

	/** BuyerManageCustomReferencesDTO */
	private BuyerManageCustomReferencesDTO manageCustomRefsDTO = new BuyerManageCustomReferencesDTO();

	/**	IBuyerBO - skipping use of a delegate */
	private IBuyerBO buyerBo;     

	/**	Logger */
	private static final Logger logger = Logger.getLogger(BuyerRegistrationAction.class.getName());
	
	/** Integer buyerId	 */
	Integer buyerId = new Integer(0);
	
	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.Preparable#prepare()
	 */
	public void prepare() throws Exception {
		createCommonServiceOrderCriteria();
		buyerId = this.get_commonCriteria().getCompanyId();
		getModel().setBuyerRefs(getDTOsFromVOs(buyerBo.getBuyerReferences(buyerId)));
		logger.info("Retrieved Reference type objects.");
	}

	/**
	 * Description: Entry point - checks to see if user is logged in.
	 * @return <code>String</code>
	 * @throws Exception
	 */
	public String displayPage() throws Exception {
		Boolean isLoggedIn = (Boolean)getSession().getAttribute(SOConstants.IS_LOGGED_IN);
		if (!isLoggedIn.booleanValue()) {
			return "dashboard";
		}
		return SUCCESS;
	}
	
	/**
	 * Description:
	 * @return
	 * @throws Exception
	 * <code>String</code>
	 */
	public String save() throws Exception {
		manageCustomRefsDTO = getModel();
		manageCustomRefsDTO.clearAllErrors();
		manageCustomRefsDTO.validate();
		
		if(getModel().getErrors().size()== 0) {
			createCommonServiceOrderCriteria(); 
			buyerId = this.get_commonCriteria().getCompanyId();
			BuyerReferenceDTO buyerRefDTO = manageCustomRefsDTO.getBuyerRef();
			BuyerReferenceVO buyerRefVO = getBuyerRefVO(buyerRefDTO);
			buyerRefVO.setBuyerId(buyerId);
			buyerRefVO.setModifiedDate(new Date(System.currentTimeMillis()));
			buyerRefVO.setModifiedBy(get_commonCriteria().getFName() + " " + get_commonCriteria().getLName());			

			//save if new
			if ("true".equalsIgnoreCase(manageCustomRefsDTO.getBuyerRef().getRefEdit())){
				buyerRefVO.setCreatedDate(new Date(System.currentTimeMillis()));
				buyerBo.updateBuyerCustomReference(buyerRefVO);
			}else{ //update if edit
				buyerBo.insertBuyerCustomReference(buyerRefVO);
			}
			
			//retrieve list with newly added ref for display
			getModel().setBuyerRefs(getDTOsFromVOs(buyerBo.getBuyerReferences(buyerId)));
			
			//clear out add/edit fields 
			manageCustomRefsDTO.setBuyerRef(new BuyerReferenceDTO());
			manageCustomRefsDTO.setMessage("Your Custom Reference information has been saved!");

			//Post seuccess message
			logger.info("Saved new Reference type objects.");
			return SUCCESS;
		} else {
			return SUCCESS;
		}
	}

	/**
	 * Description:
	 * @return
	 * @throws Exception
	 * <code>String</code>
	 */
	public String delete() throws Exception {
		manageCustomRefsDTO = getModel();
		manageCustomRefsDTO.clearAllErrors();
		
		if(getModel().getErrors().size()== 0) {
			createCommonServiceOrderCriteria(); 
			buyerId = this.get_commonCriteria().getCompanyId();
			BuyerReferenceDTO buyerRefDTO = manageCustomRefsDTO.getBuyerRef();
			BuyerReferenceVO buyerRefVO = getBuyerRefVO(buyerRefDTO);
			buyerRefVO.setBuyerId(buyerId);
			buyerRefVO.setModifiedDate(new Date(System.currentTimeMillis()));
			buyerRefVO.setModifiedBy(get_commonCriteria().getFName() + " " + get_commonCriteria().getLName());
			buyerRefVO.setActiveInd(0);			
			buyerBo.deleteBuyerCustomReference(buyerRefVO);
			//retrieve list with newly added ref for display
			getModel().setBuyerRefs(getDTOsFromVOs(buyerBo.getBuyerReferences(buyerId)));
			
			//clear out add/edit fields 
			manageCustomRefsDTO.setBuyerRef(new BuyerReferenceDTO());
			manageCustomRefsDTO.setMessage("Your Custom Reference has been deleted!");

			//Post seuccess message
			logger.info("Deleted Reference object.");
			return SUCCESS;
		} else {
			return SUCCESS;
		}
	}

	
	/**
	 * Description: Convert DTO used by Model to VO used by Database
	 * @param buyerRefDTO
	 * @return <code>BuyerReferenceVO</code>
	 */
	private BuyerReferenceVO getBuyerRefVO(BuyerReferenceDTO buyerRefDTO) {
		BuyerReferenceVO buyerRefVO = new BuyerReferenceVO();
		String buyerInput = buyerRefDTO.getBuyerInput();
		if(null != buyerInput && buyerInput.equals(SOConstants.PUBLIC_CUSTOM_REF)){
			buyerRefDTO.setPrivateInd(false);				
		}
		buyerRefVO.setBuyerId(buyerRefDTO.getBuyerId());
		buyerRefVO.setBuyerRefTypeId(buyerRefDTO.getBuyerRefTypeId());
		buyerRefVO.setReferenceType(buyerRefDTO.getReferenceType());
		buyerRefVO.setReferenceDescription(buyerRefDTO.getReferenceDescription());
		buyerRefVO.setSoIdentifier(1);
		buyerRefVO.setActiveInd(1);
		buyerRefVO.setBuyerInput(UIUtils.getBooleanIntFromString(buyerRefDTO.getBuyerInput()));
		buyerRefVO.setProviderInput(UIUtils.getBooleanIntFromString(buyerRefDTO.getProviderInput()));
		buyerRefVO.setRequired(UIUtils.getBooleanIntFromString(buyerRefDTO.getRequired()));
		buyerRefVO.setSearchable(UIUtils.getBooleanIntFromString(buyerRefDTO.getSearchable()));
		buyerRefVO.setPrivateInd(buyerRefDTO.isPrivateInd());
		buyerRefVO.setEditable(UIUtils.getBooleanIntFromString(buyerRefDTO.getEditable()));
		buyerRefVO.setPdfRefInd(UIUtils.getBooleanIntFromString(buyerRefDTO.getPdfRefInd()));
		
		//SL-18825
		//Code added to set new attribute 'Display field if no value' in Manage Custom Reference
		buyerRefVO.setDisplayNoValue(UIUtils.getBooleanIntFromString(buyerRefDTO.getDisplayNoValue()));
		
		return buyerRefVO;
	}

	/**
	 * Description: Convert VO Integer boolean indicators to string representations. 
	 * @param buyerReferences
	 * @return <code>List<BuyerReferenceDTO></code>
	 */
	private List<BuyerReferenceDTO> getDTOsFromVOs(
			List<BuyerReferenceVO> buyerReferences) {
		List<BuyerReferenceDTO> buyerRefsDTOs = new ArrayList<BuyerReferenceDTO>();
		for (BuyerReferenceVO buyerReferenceVO : buyerReferences) {
			buyerRefsDTOs.add(new BuyerReferenceDTO(buyerReferenceVO));
		}  
		return buyerRefsDTOs;
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public BuyerManageCustomReferencesDTO getModel()
	{
		return manageCustomRefsDTO;
	}

	/**
	 * Description: Gets BO interface used by this action
	 * @return  <code>IBuyerBO</code>
	 */
	public IBuyerBO getBuyerBo() {
		return buyerBo;
	}

	/**
	 * Description: Used by spring to set the BO interface's implementation.
	 * @param buyerBo
	 */
	public void setBuyerBo(IBuyerBO buyerBo) {
		this.buyerBo = buyerBo;
	}
}
