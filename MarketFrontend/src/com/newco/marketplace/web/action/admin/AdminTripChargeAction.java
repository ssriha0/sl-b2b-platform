package com.newco.marketplace.web.action.admin;

import java.util.List;
import java.util.ResourceBundle;

import com.newco.marketplace.dto.vo.TripChargeVO;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.delegates.IAdminTripChargeDelegate;
import com.newco.marketplace.web.dto.AdminTripChargeDTO;
import com.newco.marketplace.web.utils.Config;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

public class AdminTripChargeAction extends SLBaseAction implements Preparable, ModelDriven<AdminTripChargeDTO>
{
	private static final long serialVersionUID = -5189002576149161457L;
	private AdminTripChargeDTO adminTripChargeDTO = new AdminTripChargeDTO();
	private IAdminTripChargeDelegate adminTripChargeDelegate;
	

	public AdminTripChargeAction()
	{
	}
	
	
	public void prepare() throws Exception
	{
		createCommonServiceOrderCriteria();

		loadTripCharges();
	}	

	
	public String displayPage() throws Exception
	{		
		return SUCCESS;
	}

	public String cancel() throws Exception
	{		
		loadTripCharges();
		return SUCCESS;
	}
	
	public String save() throws Exception
	{
		Integer buyerID = get_commonCriteria().getCompanyId();
		
		List<TripChargeVO> oldList = adminTripChargeDelegate.getAllTripCharges(buyerID);
		List<TripChargeVO> updatedList = getModel().getTripCharges();
		
		if(updatedList == null)
			return SUCCESS;
		
		Integer numChanges = 0;
		for(int i=0 ; i<oldList.size() ; i++)
		{
			Double oldVal = oldList.get(i).getTripCharge();
			Double newVal = updatedList.get(i).getTripCharge();
			if(oldVal != null && newVal != null)
			{
				if(oldVal.doubleValue() != newVal.doubleValue())
				{
					TripChargeVO updateVO = updatedList.get(i);
					updateVO.setBuyerID(buyerID);
					updateVO.setMainCategoryID(oldList.get(i).getMainCategoryID());
					adminTripChargeDelegate.saveTripCharge(updateVO);
					numChanges++;
				}
			}
		}
		
		if(numChanges > 0)
			setAttribute("msg", numChanges + " trip charges changes have been saved.");
		else
			setAttribute("msg", null);
		
		return SUCCESS;
	}

	private void loadTripCharges()
	{
		// Set the default list of trip charges to null
		adminTripChargeDTO.setTripCharges(null);
		
		if(adminTripChargeDelegate != null)
		{
			if(get_commonCriteria() != null && get_commonCriteria().getCompanyId() != null)
			{
				Integer buyerId = get_commonCriteria().getCompanyId();
				List<TripChargeVO> list = adminTripChargeDelegate.getAllTripCharges(buyerId);
				if(list != null && list.size() > 0)
				{
					adminTripChargeDTO.setTripCharges(list);				
					setModel(adminTripChargeDTO);
				}
			}
		}				
	}
	
	public AdminTripChargeDTO getModel()
	{
		return adminTripChargeDTO;
	}
	
	public void setModel(AdminTripChargeDTO model)
	{
		adminTripChargeDTO = model;
	}





	public ResourceBundle getTheResourceBundle() {
		return Config.getResouceBundle();
	}


	public IAdminTripChargeDelegate getAdminTripChargeDelegate() {
		return adminTripChargeDelegate;
	}


	public void setAdminTripChargeDelegate(
			IAdminTripChargeDelegate adminTripChargeDelegate) {
		this.adminTripChargeDelegate = adminTripChargeDelegate;
	}


	public AdminTripChargeDTO getAdminTripChargeDTO() {
		return adminTripChargeDTO;
	}


	public void setAdminTripChargeDTO(AdminTripChargeDTO adminTripChargeDTO) {
		this.adminTripChargeDTO = adminTripChargeDTO;
	}
	
	
}
