package com.newco.marketplace.web.action.simple;

import org.apache.log4j.Logger;

import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.web.action.base.SLSimpleBaseAction;
import com.newco.marketplace.web.constants.SOConstants;
import com.newco.marketplace.web.delegates.ICreateServiceOrderDelegate;
import com.newco.marketplace.web.dto.simple.CreateServiceOrderEditAccountDTO;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;


/**
* @author Nick Sanzeri
*
* $Revision: 1.2 $ $Author: akashya $ $Date: 2008/05/21 23:32:56 $
*/
public class CreateServiceOrderEditAccountAction extends SLSimpleBaseAction implements
		Preparable, ModelDriven<CreateServiceOrderEditAccountDTO> {
	private ICreateServiceOrderDelegate createServiceOrderDelegate;
	private CreateServiceOrderEditAccountDTO model = new CreateServiceOrderEditAccountDTO();
	private static final Logger logger = Logger.getLogger("CreateServiceOrderEditAccountAction");
	
	public void prepare() throws Exception {
		createCommonServiceOrderCriteria();
		Integer buyerResourceID = get_commonCriteria().getVendBuyerResId();
		setModel(getBuyerResourceInfo(buyerResourceID));
	}

	public String displayPage() throws Exception {
		Boolean isLoggedIn = (Boolean)getSession().getAttribute(SOConstants.IS_LOGGED_IN);
		if (!isLoggedIn.booleanValue()) {
			return "dashboard";
		}
		return SUCCESS;
	}

	public CreateServiceOrderEditAccountDTO getModel() {
		return model;
	}

	public void setModel(CreateServiceOrderEditAccountDTO model) {
		this.model = model;
	}

	/**
	 * Description:  Retrieves buyer resource info for the Edit Account screen
	 * @param buyerResId
	 * @return <code>CreateServiceOrderEditAccountDTO</code>
	 * @throws Exception
	 */
	private CreateServiceOrderEditAccountDTO getBuyerResourceInfo(Integer buyerResId) throws Exception
	{
		CreateServiceOrderEditAccountDTO csoOrderEditDto = new CreateServiceOrderEditAccountDTO();
		try{
			csoOrderEditDto = createServiceOrderDelegate.getBuyerResourceInfo(buyerResId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return csoOrderEditDto;
	}
	
	/**
	 * Description:  Retrieves buyer resource info for the Edit Account screen
	 * @param buyerResId
	 * @return <code>CreateServiceOrderEditAccountDTO</code>
	 * @throws BusinessServiceException 
	 * @throws Exception
	 */
	public String updateContactInfo() throws BusinessServiceException
	{	
		model = getModel();
		model.clearAllErrors();
		model.validate();
		model.parsePhoneNumber(model.getPrimaryPhone(),  "PRIMARY");
		model.parsePhoneNumber(model.getSecondaryPhone(), "SECONDARY");
		
		if(getModel().getErrors().size()== 0) {
			createServiceOrderDelegate.updateBuyerResourceInfo(model);
			model.setMessage("Your contact information has been saved!");
			return SUCCESS;
		} else {
			return SUCCESS;
		}
	}
	
	
	public ICreateServiceOrderDelegate getCreateServiceOrderDelegate() {
		return createServiceOrderDelegate;
	}

	public void setCreateServiceOrderDelegate(
			ICreateServiceOrderDelegate createServiceOrderDelegate) {
		this.createServiceOrderDelegate = createServiceOrderDelegate;
	}
	
}
