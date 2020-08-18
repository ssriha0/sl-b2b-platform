package com.newco.marketplace.web.action.simple;

import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.delegates.ICreateServiceOrderDelegate;
import com.newco.marketplace.web.dto.simple.CreateServiceOrderEmailSentDTO;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

public class CreateServiceOrderEmailSentAction extends SLBaseAction implements Preparable, ModelDriven<CreateServiceOrderEmailSentDTO>
{
	private ICreateServiceOrderDelegate delegate;
	
	private CreateServiceOrderEmailSentDTO model = new CreateServiceOrderEmailSentDTO();
	
	
	private static final long serialVersionUID = 0L;

	public CreateServiceOrderEmailSentAction(ICreateServiceOrderDelegate delegate)
	{
		this.delegate = delegate;
	}
	
	public void prepare() throws Exception
	{
		model = (CreateServiceOrderEmailSentDTO)getSession().getAttribute("testKey");
	}

	public String displayPage() throws Exception
	{
		return SUCCESS;
	}
	
	public CreateServiceOrderEmailSentDTO getModel()
	{
		return model;
	}

	public void setModel(CreateServiceOrderEmailSentDTO model)
	{
		this.model = model;
	}

	public ICreateServiceOrderDelegate getCreateServiceOrderDelegate() {
		return delegate;
	}

	public void setCreateServiceOrderDelegate(
			ICreateServiceOrderDelegate createServiceOrderDelegate) {
		this.delegate = createServiceOrderDelegate;
	}

}
