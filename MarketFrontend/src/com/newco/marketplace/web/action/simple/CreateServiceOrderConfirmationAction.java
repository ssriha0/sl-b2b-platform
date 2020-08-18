package com.newco.marketplace.web.action.simple;

import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.dto.simple.CreateServiceOrderConfirmMessagingDTO;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

public class CreateServiceOrderConfirmationAction extends SLBaseAction implements Preparable, ModelDriven<CreateServiceOrderConfirmMessagingDTO>
{
	
	private static final long serialVersionUID = -8433232464540678838L;
	private CreateServiceOrderConfirmMessagingDTO confirmMessagingDTO = new CreateServiceOrderConfirmMessagingDTO();;

	public void prepare() throws Exception
	{
		// TODO Modify prepare 
	}
	
	public String displayPage() throws Exception
	{
		// TODO Modify this lines when accountDTO is ready
		//CreateServiceOrderCreateAccountDTO accountDTO = (CreateServiceOrderCreateAccountDTO) getRequest().getAttribute("accountDTO");
		//confirmMessagingDTO.setEmail(accountDTO.getEmail());
		confirmMessagingDTO.setEmail("test@testmail.com");
		confirmMessagingDTO.setMessageType("Signup Confirmation");
		return SUCCESS;
	}

	/**
	 * @return the confirmMessagingDTO
	 */
	public CreateServiceOrderConfirmMessagingDTO getModel() {
		// TODO Auto-generated method stub
		return confirmMessagingDTO;
	}

	/**
	 * @param confirmMessagingDTO the confirmMessagingDTO to set
	 */
	public void setModel(
			CreateServiceOrderConfirmMessagingDTO confirmMessagingDTO) {
		this.confirmMessagingDTO = confirmMessagingDTO;
	}


}
