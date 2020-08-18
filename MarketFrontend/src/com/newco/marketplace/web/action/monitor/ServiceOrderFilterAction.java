package com.newco.marketplace.web.action.monitor;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.dto.ServiceOrderDTO;
import com.newco.marketplace.web.utils.ServiceOrderFilterer;

/**
 * $Revision: 1.5 $ $Author: glacy $ $Date: 2008/04/26 01:13:45 $
 */
/* Maintenance History: See bottom of file
 */
public class ServiceOrderFilterAction extends SLBaseAction{
	
	private static final long serialVersionUID = -7168599190287454169L;
	private static final Logger logger = Logger.getLogger(ServiceOrderFilterAction.class.getName());
	private String filterByStatus = "";
	private String filterBySubStatus = "";
	
	
	public void filterStatus(){
		
		ArrayList<ServiceOrderDTO> dtoList = (ArrayList<ServiceOrderDTO>)getSession().getAttribute("dtoList");		
		Boolean isFiltered = (Boolean)getSession().getAttribute("isFiltered");
		ArrayList<ServiceOrderDTO> filteredList = new ArrayList<ServiceOrderDTO>();
		try{
			//Do less work if we're filtering an already filtered list of SOs
			if(isFiltered !=null && !isFiltered.equals(null)){
	
				filteredList = (ArrayList<ServiceOrderDTO>)getSession().getAttribute("filteredList");
			} 
			//Filter the whole list of SOs
			if(filterBySubStatus != null && !filterBySubStatus.equalsIgnoreCase("")){
					filteredList = ServiceOrderFilterer.getFilteredDataSub(filteredList, dtoList, filterBySubStatus);
					isFiltered = true;
			} else if (filterByStatus != null && !filterByStatus.equalsIgnoreCase("")){
				
				filteredList = ServiceOrderFilterer.getFilteredData(filteredList, dtoList, filterByStatus);
				isFiltered = true;
			}
			getSession().setAttribute("filteredList", filteredList);
			getSession().setAttribute("isFiltered", isFiltered);			
			
		} catch(Exception e){
			logger.error(e.getMessage(),e);
		}
	}
	
	
	public String getFilterByStatus() {
		return filterByStatus;
	}
	public void setFilterByStatus(String filterByStatus) {
		this.filterByStatus = filterByStatus;
	}
	public String getFilterBySubStatus() {
		return filterBySubStatus;
	}
	public void setFilterBySubStatus(String filterBySubStatus) {
		this.filterBySubStatus = filterBySubStatus;
	}

}
/* Maintenance History
 * $Log: ServiceOrderFilterAction.java,v $
 * Revision 1.5  2008/04/26 01:13:45  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.3.6.1  2008/04/23 11:41:32  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.4  2008/04/23 05:19:33  hravi
 * Shyam: Reverting to build 247.
 *
 * Revision 1.3  2008/02/26 18:17:56  mhaye05
 * Merged Iteration 17 Branch into Head
 *
 * Revision 1.2.2.1  2008/02/25 16:20:59  mhaye05
 * replaced system out println with logger.error statement
 *
 */