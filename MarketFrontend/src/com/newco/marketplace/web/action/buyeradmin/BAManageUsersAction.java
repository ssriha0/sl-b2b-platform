package com.newco.marketplace.web.action.buyeradmin;

import java.util.List;

import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.delegates.IManageUsersDelegate;
import com.newco.marketplace.web.dto.BuyerAdminManageUsersUserDTO;
import com.opensymphony.xwork2.Preparable;

/**
 * $Revision: 1.4 $ $Author: glacy $ $Date: 2008/04/26 01:13:49 $
 */
public class BAManageUsersAction extends SLBaseAction implements Preparable
{

	private static final long serialVersionUID = -1348787234691454002L;
	private IManageUsersDelegate manageUsersDelegate;
	
	public void prepare() throws Exception
	{
		createCommonServiceOrderCriteria();	
	}	

	
	public BAManageUsersAction(IManageUsersDelegate manageUsersDelegate)
	{
		this.manageUsersDelegate = manageUsersDelegate;
	}
	
	public String execute() throws Exception
	{
		retrieveActionMessage();
		List<BuyerAdminManageUsersUserDTO> usersList;
		usersList = manageUsersDelegate.getAllBuyerUsers(get_commonCriteria().getSecurityContext().getCompanyId());
		setAttribute("usersList", usersList);
		
		return SUCCESS;
	}
	
	
}
