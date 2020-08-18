package com.newco.marketplace.web.action.details;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.web.action.base.SLDetailsBaseAction;
import com.newco.marketplace.web.delegates.ISODetailsDelegate;
import com.newco.marketplace.web.dto.FirmDTO;
import com.newco.marketplace.web.dto.ResponseStatusDTO;
import com.newco.marketplace.web.dto.ResponseStatusTabDTO;
import com.newco.marketplace.web.dto.ServiceOrderDTO;
import com.opensymphony.xwork2.Preparable;

/**
 * $Revision: 1.12 $ $Author: glacy $ $Date: 2008/04/26 01:13:47 $
 */

/*
 * Maintenance History
 * $Log: SODetailsResponseHistoryAction.java,v $
 * Revision 1.12  2008/04/26 01:13:47  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.10.12.1  2008/04/23 11:41:34  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.11  2008/04/23 05:19:31  hravi
 * Shyam: Reverting to build 247.
 *
 * Revision 1.10  2008/02/14 23:44:55  mhaye05
 * Merged Feb4_release branch into head
 *
 * Revision 1.9  2008/02/05 21:34:39  usawant
 * Removed Modeldriven as it was not used, as part of Struts2 optimization
 *
 * Revision 1.8  2007/11/27 22:37:41  pbhinga
 * Files changed for the functionality of showing star images based on the various ratings on screens like dashboard, providers, response history, view ratings etc.
 *
 * Revision 1.7  2007/11/14 21:58:51  mhaye05
 * changed reference to SOW_SO_ID or THE_CURRENT_SERVICE_ORDER_ID to be OrderConstants.SO_ID
 *
 */
public class SODetailsResponseHistoryAction extends SLDetailsBaseAction implements Preparable {
	
	private static final long serialVersionUID = 10002;// arbitrary number to get rid
												// of warning
	private static final Logger logger = Logger.getLogger(SODetailsResponseHistoryAction.class.getName());
	private ResponseStatusDTO responseStatusDto = new ResponseStatusDTO();
	public SODetailsResponseHistoryAction(ISODetailsDelegate delegate) {
		this.detailsDelegate = delegate;
	}

	/**
	 * Description of the Method
	 * 
	 * @exception Exception
	 *                Description of the Exception
	 */
	public void prepare() throws Exception {
		
		createCommonServiceOrderCriteria();
	}

	public String execute() throws Exception {
		
		populateDTO();
		return SUCCESS;
	}

	private void populateDTO()
	{
		SecurityContext context = (SecurityContext)getSession().getAttribute("SecurityContext");

		ResponseStatusTabDTO responseStatusTabDto=null;
		
		String groupId = getParameter(GROUP_ID);
		
		//Sl-19820
		String soId = getParameter("soId");
		String id = null;
		if(StringUtils.isBlank(groupId)){
			groupId = null;
		}else{
			id = groupId;
		}
		if(StringUtils.isBlank(soId)){
			soId = null;
		}else{
			id = soId;
		}
		setAttribute(OrderConstants.SO_ID, soId);
		setAttribute("groupOrderId", groupId);
		String msg = (String)getSession().getAttribute(Constants.SESSION.SOD_MSG+"_"+id);
		getSession().removeAttribute(Constants.SESSION.SOD_MSG+"_"+id);
		setAttribute(Constants.SESSION.SOD_MSG, msg);
		
		Integer companyId = context.getCompanyId();
		//Sl-19820
		//Integer status = (Integer)(getSession().getAttribute(THE_SERVICE_ORDER_STATUS_CODE));
		Integer status = null;
		if(StringUtils.isNotBlank(getParameter("status"))){
			status = Integer.parseInt(getParameter("status"));
			setAttribute(THE_SERVICE_ORDER_STATUS_CODE, status);
		}
		
		// See if we are dealing with an Order Group
		if(groupId != null)
		{
			responseStatusTabDto = detailsDelegate.getResponseStatusDtoForGroup(groupId, status, companyId);
			if(null != responseStatusTabDto){
				if(responseStatusTabDto.isTaskLevelIsOn()){
					setAttribute("taskLevelIsOn", "true");
				}
				else{
					setAttribute("taskLevelIsOn", "false");
				}
			}
		}
		// Single Order
		else
		{
			responseStatusTabDto = detailsDelegate.getResponseStatusDto(
					//SL-19820
					//(String)getSession().getAttribute(OrderConstants.SO_ID), 
					soId, status, companyId);
		}
		
		if(responseStatusTabDto != null)
		{
			//SL-19820
			//ServiceOrderDTO soDto = (ServiceOrderDTO) getSession().getAttribute(THE_SERVICE_ORDER);
			ServiceOrderDTO soDto = null;
			try{
				if(org.apache.commons.lang.StringUtils.isNotBlank(getParameter("resId"))) {
					Integer resId = Integer.parseInt(getParameter("resId"));
					setAttribute("routedResourceId", resId);
					soDto = getDetailsDelegate().getServiceOrder(soId, get_commonCriteria().getRoleId(), resId);
				}
				else {
					soDto = getDetailsDelegate().getServiceOrder(soId, get_commonCriteria().getRoleId(), null);
				}
			}catch(Exception e){
				logger.error("Exception while trying to fetch SO Details");
			}
			setAttribute(THE_SERVICE_ORDER, soDto);
	if (soDto != null) {
		if(soDto.isTaskLevelPriceInd())
		{
		    setAttribute("taskLevelIsOn","true");

		}
		else
		{
		    setAttribute("taskLevelIsOn","false");

		}
	}
	if(StringUtils.isNotBlank(responseStatusTabDto.getRoutingCriteria())){
		List<ResponseStatusDTO> responseList= responseStatusTabDto.getResponseStatusDtoList();
		
		//separating the provider list based on routed date
		List<Timestamp> routedDates = new ArrayList<Timestamp>();
		Map<Timestamp, Object> providerOfferMap = new TreeMap<Timestamp, Object>();
		 
		//getting the list of routed dates
		for(ResponseStatusDTO responseDto : responseList){
			if(null != responseDto && !routedDates.contains(responseDto.getRoutedDate())){
				routedDates.add(responseDto.getRoutedDate());
			}
		}
		//group the providers based on routed date
		for(Timestamp routedDate : routedDates){
			List<ResponseStatusDTO> dtoList = new ArrayList<ResponseStatusDTO>();
			for(ResponseStatusDTO responseDto : responseList){
				if(0 == routedDate.compareTo(responseDto.getRoutedDate())){
					dtoList.add(responseDto);
				}
			}
			providerOfferMap.put(routedDate, dtoList);
		}
		 
		if("provider".equals(responseStatusTabDto.getRoutingCriteria())){
			//sorting providers based on performance
			Map<Timestamp, Object> providerMap = new TreeMap<Timestamp, Object>();
			for(Entry providers : providerOfferMap.entrySet()){
				List<ResponseStatusDTO> dtoList = (List<ResponseStatusDTO>)providers.getValue();
				sortProviders(dtoList);
				providerMap.put((Timestamp)providers.getKey(), dtoList);
			}
			providerOfferMap.putAll(providerMap);
			//calculating the rank
			int rank = 0;
			int size = responseStatusTabDto.getResponseStatusDtoList().size();
			Iterator itr = providerOfferMap.keySet().iterator();
			while(itr.hasNext()){
				Timestamp date = (Timestamp)itr.next();
				List<ResponseStatusDTO> providerList = (List<ResponseStatusDTO>)providerOfferMap.get(date);
				for(ResponseStatusDTO responseDto : providerList){
					rank = rank + 1;
					responseDto.setRank(rank + "/" + size);
				}
			}
		}
		else if("firm".equals(responseStatusTabDto.getRoutingCriteria())){				
			Map<Timestamp, Object> providerMap = new TreeMap<Timestamp, Object>();
			int rank = 0;
			int size = 0;
			//get the total no. of firms
			List<Integer> firmList = new ArrayList<Integer>();
			List<Integer> tempfirm = new ArrayList<Integer>();
			for(Entry provEntry : providerOfferMap.entrySet()){
				List<ResponseStatusDTO> providerList = (List<ResponseStatusDTO>)provEntry.getValue();
				for(ResponseStatusDTO dto : providerList){
					if(!tempfirm.contains(dto.getVendorId())){
						tempfirm.add(dto.getVendorId());
					}
				}
				firmList.addAll(tempfirm);
				tempfirm.clear();
			}
			size = firmList.size();
			
			//grouping providers based on firms
			Iterator itr = providerOfferMap.keySet().iterator();
			while(itr.hasNext()){
				Timestamp rtdate = (Timestamp)itr.next();
				List<ResponseStatusDTO> providerList = (List<ResponseStatusDTO>)providerOfferMap.get(rtdate);
				//adding the firms to a list
				List<FirmDTO> firmlist = new ArrayList<FirmDTO>();
				for(ResponseStatusDTO responseDto : providerList){
					if(null != responseDto){
						FirmDTO firm = new FirmDTO();
						firm.setFirmId(responseDto.getVendorId());
						firm.setFirmscore(responseDto.getFirmScore());
						firm.setRoutedDate(responseDto.getRoutedDate());
						firmlist.add(firm);
					}
				}
				Set<FirmDTO> firmSet = new TreeSet<FirmDTO>(firmlist);
				List<FirmDTO> firms = new ArrayList<FirmDTO>(firmSet);
				
				//sort firms based on performance
				sortFirms(firms);
				
				//calculating the rank
				for(FirmDTO firm : firms){
					rank = rank + 1;
					firm.setFirmRank(rank + "/" + size);
				}
				
				//for each firm get the providers
				Map<FirmDTO, List<ResponseStatusDTO>> firmMap = new LinkedHashMap<FirmDTO, List<ResponseStatusDTO>>();
				for(FirmDTO firm : firms){
					if(null != firms){
						List<ResponseStatusDTO> dtoList = new ArrayList<ResponseStatusDTO>();
						for(ResponseStatusDTO provider : providerList){
							if(null != provider && firm.getFirmId().intValue() == provider.getVendorId().intValue()){
								dtoList.add(provider);
							}
						}
						firmMap.put(firm, dtoList);
					}
				}
				providerMap.put(rtdate, firmMap);
			}
			providerOfferMap.putAll(providerMap);
		}
		
		setAttribute("providerOfferMap", providerOfferMap);
		setAttribute("criteriaLevel", responseStatusTabDto.getRoutingCriteria());
	    
	}
			
		    setAttribute("providerOfferList", responseStatusTabDto.getResponseStatusDtoList());
		    setAttribute("postedCnt", responseStatusTabDto.getPostedCnt());
		    setAttribute("conditionalAcceptedCnt", responseStatusTabDto.getConditionallyAcceptedCnt());
		    setAttribute("rejectedCnt",responseStatusTabDto.getRejectedCnt());
		    setAttribute("wfStateId", responseStatusTabDto.getWfStateId());
		}
	}
	
	//to sort criteria list based on performance score
	private void sortFirms(List<FirmDTO> firms) {
		Collections.sort(firms, new Comparator<Object>(){
			public int compare(final Object obj1, final Object obj2){
				final FirmDTO firm1 = (FirmDTO)obj1;
				final FirmDTO firm2 = (FirmDTO)obj2;
				final double score1 = firm1.getFirmscore();
				final double score2 = firm2.getFirmscore();
				if(score2 < score1){
					return -1;
				}
				else{
					return (score2 > score1)? 1 : 0;
				}
			}
		});
	}
	
	
	private void sortProviders(List<ResponseStatusDTO> providers) {
		Collections.sort(providers, new Comparator<Object>(){
			public int compare(final Object obj1, final Object obj2){
				final ResponseStatusDTO provider1 = (ResponseStatusDTO)obj1;
				final ResponseStatusDTO provider2 = (ResponseStatusDTO)obj2;
				final double score1 = provider1.getScore();
				final double score2 = provider2.getScore();
				if(score2 < score1){
					return -1;
				}
				else{
					return (score2 > score1)? 1 : 0;
				}
			}
		});
	}
	
	public ResponseStatusDTO getResponseStatusDto() {
		return responseStatusDto;
	}

	public void setResponseStatusDto(ResponseStatusDTO responseStatusDto) {
		this.responseStatusDto = responseStatusDto;
	}
	
	
}
