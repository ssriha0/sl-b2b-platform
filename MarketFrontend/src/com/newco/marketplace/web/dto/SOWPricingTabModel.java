package com.newco.marketplace.web.dto;

import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.web.action.wizard.ISOWTabModel;

public class SOWPricingTabModel extends SOWBaseTabDTO implements ISOWTabModel
{
	private static final long serialVersionUID = 1L;
	
	private List<SOWPricingTabDTO> dtoList = new ArrayList<SOWPricingTabDTO>();
	
	

	@Override
	public String getTabIdentifier()
	{
		return OrderConstants.SOW_PRICING_TAB;
	}

	@Override
	public void validate()
	{
		getErrors().clear();
		
		// Loop thru each of the dto/tabs, copy child errors to this model
		for(SOWPricingTabDTO  dto : dtoList)
		{
			dto.validate();
			getErrors().addAll(dto.getErrors());
		}
	}

	public List<SOWPricingTabDTO> getDtoList()
	{
		return dtoList;
	}

	public void setDtoList(List<SOWPricingTabDTO> dtoList)
	{
		this.dtoList = dtoList;
	}

	public SOWBaseTabDTO getTabById(String soId)
	{
		for(SOWPricingTabDTO dto : dtoList)
		{
			if(dto.getSoId().endsWith(soId))
			{
				return dto;
			}
		}
		
		return null;
	}

	public void setTab(SOWBaseTabDTO tab)
	{
		if(tab == null)
			return;

		if(dtoList == null)
		{
			dtoList = new ArrayList<SOWPricingTabDTO>();
		}
		

		boolean foundIt = false;
		int i=0;
		for(SOWPricingTabDTO dto : dtoList)
		{
			if(dto.getSoId() != null)
			{
				if(dto.getSoId().equals(tab.getSoId()))
				{
					foundIt = true;
					break;
				}
			}
			i++;
		}
		
		if(foundIt)
			dtoList.set(i, (SOWPricingTabDTO)tab);
		else
			dtoList.add((SOWPricingTabDTO)tab);
	}

}
	
