package com.newco.marketplace.web.dto;

import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.interfaces.OrderConstants;

public class SOWPartsTabModel extends SOWBaseTabDTO
{

	private static final long serialVersionUID = 8290331006316446638L;
	
	private List<SOWPartsTabDTO> dtoList = new ArrayList<SOWPartsTabDTO>();
	

	public List<SOWPartsTabDTO> getDtoList()
	{
		return dtoList;
	}

	public void setDtoList(List<SOWPartsTabDTO> dtoList)
	{
		this.dtoList = dtoList;
	}

	@Override
	public String getTabIdentifier()
	{
		 return OrderConstants.SOW_PARTS_TAB;
	}
	
	
	@Override
	public void validate()
	{
		getErrors().clear();
		
		// Loop thru each of the dto/tabs, copy child errors to this model
		for(SOWPartsTabDTO  dto : dtoList)
		{
			dto.validate();
			getErrors().addAll(dto.getErrors());
		}
		
	}
	
	public SOWBaseTabDTO getTabById(String soId)
	{
		for(SOWPartsTabDTO dto : dtoList)
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
			dtoList = new ArrayList<SOWPartsTabDTO>();
		}
		

		boolean foundIt = false;
		int i=0;
		for(SOWPartsTabDTO dto : dtoList)
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
			dtoList.set(i, (SOWPartsTabDTO)tab);
		else
			dtoList.add((SOWPartsTabDTO)tab);
	}
	
	

}
