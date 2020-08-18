package com.newco.marketplace.web.dto;

import java.util.ArrayList;
import java.util.List;
import com.newco.marketplace.interfaces.OrderConstants;

public class SOWScopeOfWorkTabModel extends SOWBaseTabDTO
{
	private static final long serialVersionUID = 4151637363055595747L;
	
	
	List<SOWScopeOfWorkTabDTO> dtoList = new ArrayList<SOWScopeOfWorkTabDTO>();

	public SOWScopeOfWorkTabDTO getTab(int index)
	{
		return dtoList.get(index);
	}
	
	

	public SOWScopeOfWorkTabDTO getTabById(String soId)
	{
		for(SOWScopeOfWorkTabDTO dto : dtoList)
		{
			if(dto.getSoId().endsWith(soId))
			{
				return dto;
			}
		}
		
		return null;
	}


	public void validate()
	{
		getErrors().clear();
		
		// Loop thru each of the dto/tabs, copy child errors to this model
		for(SOWScopeOfWorkTabDTO  dto : dtoList)
		{
			dto.validate();
			getErrors().addAll(dto.getErrors());
		}
	}
	
	
	public List<SOWScopeOfWorkTabDTO> getDtoList()
	{
		return dtoList;
	}





	public void setDtoList(List<SOWScopeOfWorkTabDTO> dtoList)
	{
		this.dtoList = dtoList;
	}

	public void setTab(SOWBaseTabDTO tab)
	{
		if(tab == null)
			return;

		if(dtoList == null)
		{
			dtoList = new ArrayList<SOWScopeOfWorkTabDTO>();
		}
		

		boolean foundIt = false;
		int i=0;
		for(SOWScopeOfWorkTabDTO dto : dtoList)
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
			dtoList.set(i, (SOWScopeOfWorkTabDTO)tab);
		else
			dtoList.add((SOWScopeOfWorkTabDTO)tab);
	}




	@Override
	public String getTabIdentifier()
	{		
		return OrderConstants.SOW_SOW_TAB;
	}


}
