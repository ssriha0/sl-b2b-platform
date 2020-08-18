package com.newco.marketplace.web.action.admin;

import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.dto.SLTabDTO;
import com.opensymphony.xwork2.Preparable;


/*
 * Maintenance History: See bottom of file
 */
public class AdminSearchControllerAction extends SLBaseAction implements Preparable
{
	
	private static final long serialVersionUID = 1L;
	private static final String TAB_SEARCH1 = "Search1";
	private static final String TAB_SEARCH2 = "Search2";
	
	public void prepare() throws Exception
	{
		createCommonServiceOrderCriteria();
		
	}	

	
	// EXECUTE METHODS SHOULD BE CONSIDERED AS AN ENTRY POINT 
	public String execute() throws Exception
	{
		//HttpSession session = getSession();
		
		
		_setTabs(null, TAB_SEARCH1);
		
		return "success";
	}
	
	
	public void _setTabs(String role, String selected)
	{
		List<SLTabDTO> tabList = new ArrayList<SLTabDTO>();
		SLTabDTO tab;
		String icon=null;
		
		icon = null;
		
		tab = new SLTabDTO("tab1", "adminSearch_search1.action", TAB_SEARCH1, icon, selected, selected);		
		tabList.add(tab);

		tab = new SLTabDTO("tab2", "adminSearch_search2.action", TAB_SEARCH2, icon, selected, selected);		
		tabList.add(tab);			
			
		
		setAttribute("tabList", tabList);				
	}

	
	
}
