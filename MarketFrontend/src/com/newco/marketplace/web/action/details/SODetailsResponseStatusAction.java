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
import org.apache.struts.util.LabelValueBean;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.vo.login.LoginCredentialVO;
import com.newco.marketplace.web.action.base.SLDetailsBaseAction;
import com.newco.marketplace.web.constants.SOConstants;
import com.newco.marketplace.web.delegates.ISODetailsDelegate;
import com.newco.marketplace.web.dto.FirmDTO;
import com.newco.marketplace.web.dto.ResponseStatusDTO;
import com.newco.marketplace.web.dto.ResponseStatusTabDTO;
import com.newco.marketplace.web.dto.SOWError;
import com.newco.marketplace.web.dto.ServiceOrderDTO;
import com.newco.marketplace.web.dto.ServiceOrderNoteDTO;
import com.newco.marketplace.web.utils.SLStringUtils;
import com.newco.marketplace.web.utils.SODetailsUtils;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
/**
 * $Revision: 1.24 $ $Author: ypoovil $ $Date: 2008/04/28 17:34:08 $
 */

/*
 * Maintenance History: See bottom of file
 */
public class SODetailsResponseStatusAction extends SLDetailsBaseAction implements Preparable, ModelDriven<ResponseStatusDTO>{

	private static final long serialVersionUID = -1477389743543819064L;
	private static final Logger logger = Logger.getLogger(SODetailsResponseStatusAction.class.getName());

	//private String resourceId;
	//SL-19820
	String soID;
	String groupID;
	String fromWFM;
	String tabForWFM;

	private ResponseStatusDTO responseStatusDto = new ResponseStatusDTO();
	public SODetailsResponseStatusAction(ISODetailsDelegate delegate) {
		this.detailsDelegate = delegate;
	}

	/**
	 * Description of the Method
	 *
	 * @exception Exception
	 */
	public void prepare() throws Exception {

		createCommonServiceOrderCriteria();
		getRequest().getAttribute(Constants.SESSION.SOD_MSG);

	}

	public String execute() throws Exception {

		// This line should be next to last on all the detail tabs
		populateDTO();

		//SL-19820
		//ServiceOrderDTO dto= getCurrentServiceOrderFromSession();
		ServiceOrderDTO dto= getCurrentServiceOrderFromRequest();

		if (dto != null) {
			setAttribute("buyerId", dto.getBuyerID());
		}else{
			setAttribute("buyerId", get_commonCriteria().getCompanyId());
		}
		
		if (dto != null) {
			setAttribute("priceModelBid", Constants.PriceModel.ZERO_PRICE_BID.equals(dto.getPriceModel()));
			setAttribute("commentCnt", getDetailsDelegate().getBidNoteCount(dto.getId()));
			setAttribute("newCommentCnt", getDetailsDelegate().getNewBidNoteCount(dto.getId()));
			setAttribute("newConditionalAcceptedCnt", getDetailsDelegate().getNewBidCount(dto.getId()));

			setCommunicatePanel();

		} else {
			setAttribute("priceModelBid", false);
			setAttribute("commentCnt", 0);
			setAttribute("newCommentCnt", 0);
			setAttribute("newConditionalAcceptedCnt", 0);
			setAttribute("showCommunication", false);
		}
		
		//SL-19820
		/*getSession().setAttribute("showCommunication", getAttribute("showCommunication"));
		getSession().setAttribute("newCommentCnt", getAttribute("newCommentCnt"));*/

		return SUCCESS;
	}

	private void setCommunicatePanel() {
		boolean showCommunication = false;
		//SL-19820
		//ServiceOrderDTO dto= getCurrentServiceOrderFromSession();
		ServiceOrderDTO dto = getCurrentServiceOrderFromRequest();

		// Communication is allowed if this is a zero price bid, or if the buyer is a simple buyer.
		if (Constants.PriceModel.ZERO_PRICE_BID.equals(dto.getPriceModel())) {
			showCommunication = true;

		} else {
			// Otherwise, see if this order has the ALLOW_COMMUNICATION feature set.
			showCommunication = detailsDelegate.validateServiceOrderFeature(dto.getId(),
					OrderConstants.ALLOW_COMMUNICATION);
		}


		setAttribute("showCommunication", showCommunication);
	}

	private void populateDTO()
	{
		SecurityContext context = (SecurityContext)getSession().getAttribute("SecurityContext");


		ResponseStatusTabDTO responseStatusTabDto = null;

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

		if(StringUtils.isBlank(groupId) == true)
		{
			responseStatusTabDto = detailsDelegate.getResponseStatusDto(soId, status,companyId);
		}
		else
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

		boolean slAdmin = false;
		ArrayList<LabelValueBean> callStatusList = null;
		if ((context.getAdminResId() != null && context.getAdminResId() != -1) || context.getSLAdminInd())
		{
			slAdmin = true;
			ArrayList<LookupVO> callStatus= (ArrayList<LookupVO>)detailsDelegate.getCallStatusList();

			Iterator<LookupVO> it = callStatus.iterator();
			LookupVO entry = null;
			callStatusList = new ArrayList<LabelValueBean>();
			while (it.hasNext())
			{
				entry =	(LookupVO)it.next();

				callStatusList.add(new LabelValueBean(String.valueOf(entry.getType()), String.valueOf(entry.getDescr())));

			}
		}

		//SL-19820
		//getSession().setAttribute("callStatusList", callStatusList);
		setAttribute("callStatusList", callStatusList);
     
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
			if (soDto.isTaskLevelPriceInd()) {
				setAttribute("taskLevelIsOn", "true");

			} else {
				setAttribute("taskLevelIsOn", "false");

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
	    setAttribute("slAdmin", slAdmin);
	}

	public String updateMarketMakerResponse() throws BusinessServiceException
	{
		//SL-19820
		String soId = getParameter("soId");
		if(StringUtils.isBlank(soId)){
			soId = null;
		}
		setAttribute(OrderConstants.SO_ID, soId);
		this.soID = soId;
		this.fromWFM = getParameter(Constants.SESSION.CAME_FROM_WORKFLOW_MONITOR);
		responseStatusDto = ((ResponseStatusDTO)getModel());
		logger.debug("ResourceID"+responseStatusDto.getResourceId());

		ArrayList<LabelValueBean> callStatusList = null;
		//setting values selected for update.
		responseStatusDto.setMktMakerComments(responseStatusDto.getMktMakerComment());
		responseStatusDto.setCallStatusId(responseStatusDto.getCallStatusIdSelected());

		responseStatusDto.validate();
		int errCnt = 0;

		if (responseStatusDto.getErrors() != null)
			errCnt = responseStatusDto.getErrors().size();
		if ( errCnt > 0){

			SOWError error =   (SOWError) responseStatusDto.getErrors().get(0);
			//Sl-19820
			//getSession().setAttribute(Constants.SESSION.SOD_MSG, error.getMsg());
			getSession().setAttribute(Constants.SESSION.SOD_MSG+"_"+soId, error.getMsg());
		}
		else
		{


		/* code for adding notes on notes tab */

		ServiceOrderNoteDTO soNoteDTO = new ServiceOrderNoteDTO();
		//soNoteDTO.setNoteTypeId(SOConstants.PRIVATE_NOTE);
		soNoteDTO.setNoteTypeId(SOConstants.GENERAL_NOTE);
		soNoteDTO.setRadioSelection(SOConstants.PRIVATE_NOTE.toString());
		soNoteDTO.setSubject(Constants.SESSION.SOD_MKT_MAKER_RESPONSE_NOTES_SUBJECT);
		soNoteDTO.setModifiedBy(responseStatusDto.getModifyingAdmin());
		soNoteDTO.setCreatedDateString(responseStatusDto.getModifiedDateTime());
		String noteMessage = "Call Status: " ;
		if(!SLStringUtils.isNullOrEmpty(responseStatusDto.getCallStatusDescription())){
			noteMessage += responseStatusDto.getCallStatusDescription();
		}
		String newline = ".<br> ";
		noteMessage += newline + "Comment:" + responseStatusDto.getMktMakerComments();
		//Adding Provider name and Id to the message
		noteMessage += newline + "Provider:" + responseStatusDto.getFirstName()+OrderConstants.SPACE +responseStatusDto.getLastName() ;
		noteMessage+=OrderConstants.OPENING_BRACE + responseStatusDto.getResourceId()+OrderConstants.CLOSING_BRACE;
		soNoteDTO.setMessage(noteMessage);


		LoginCredentialVO lvRoles = get_commonCriteria().getSecurityContext().getRoles();
		Integer resourceId = get_commonCriteria().getVendBuyerResId();
		/*
		 * Added code to insert
		 * Created By Name - User Name (First and Last Name),
		 * Modified By - User Id,
		 * Entity Id - Resource Id
		 * 	- Covansys (Offshore)
		 */

		//Resource Id is set as Entity Id
		soNoteDTO.setEntityId(resourceId);

		/*
		 * Added code to provide SLAdmin Role Id
		 */
		SecurityContext context = (SecurityContext)getSession().getAttribute("SecurityContext");
		if ((context.getSLAdminInd()&& context.getAdminRoleId()!=-1))
		{
			lvRoles.setRoleId(get_commonCriteria().getSecurityContext().getAdminRoleId());
		}else{
			//Incase proper data isnt set- setting role as Admin
			//-since only admin adds notes
			lvRoles.setRoleId(OrderConstants.NEWCO_ADMIN_ROLEID);
		}
		//String soId = (String)getSession().getAttribute(OrderConstants.SO_ID);
		//For Grouped Order
		List<String> soIds = new ArrayList<String>();
		String groupId =null;
		if(soId==null){
			//SL-19820
			//groupId =(String) getSession().getAttribute(OrderConstants.GROUP_ID);
			groupId = getParameter("groupId");
			this.groupID = groupId;
			setAttribute(GROUP_ID, groupId);
			soIds= detailsDelegate.getServiceOrderIDsForGroup(groupId);
		}else{
			soIds.add(soId);
		}
		//For a Group propagate the mrk maker comments and Notes across soIds
		for( String serviceOrderId : soIds){
			responseStatusDto.setSoId(serviceOrderId);
			soNoteDTO.setSoId(serviceOrderId);
			detailsDelegate.updateMktMakerComments(responseStatusDto);
			detailsDelegate.serviceOrderAddNote(lvRoles, soNoteDTO, resourceId);
		}
		//Add a group Note also
		if(groupId!=null){
			soNoteDTO.setSoId(groupId);
			detailsDelegate.serviceOrderAddNote(lvRoles, soNoteDTO, resourceId);
		}
		/*end code for adding note */

		//this.setSuccessMessage(OrderConstants.SO_MARKET_MAKER_COMMENTS_UPDATED);
		//SL-19820
		//getSession().setAttribute(Constants.SESSION.SOD_MSG, OrderConstants.SO_MARKET_MAKER_COMMENTS_UPDATED);
		getSession().setAttribute(Constants.SESSION.SOD_MSG+"_"+soId, OrderConstants.SO_MARKET_MAKER_COMMENTS_UPDATED);
		}
		this.setDefaultTab( SODetailsUtils.ID_RESPONSE_STATUS);
		this.setTabForWFM( SODetailsUtils.ID_RESPONSE_STATUS);
		//SL-20001
		Integer status = null;
		if(StringUtils.isNotBlank(getParameter("status"))){
			status = Integer.parseInt(getParameter("status"));
		}
		if(null != status && OrderConstants.ROUTED_STATUS != status.intValue()){
			this.setDefaultTab( SODetailsUtils.ID_RESPONSE_HISTORY);
			this.setTabForWFM( SODetailsUtils.ID_RESPONSE_HISTORY);
		}
		//SL-19820
		//return GOTO_COMMON_DETAILS_CONTROLLER;
		return SUCCESS;
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

	public ResponseStatusDTO getModel() {
		return responseStatusDto;
	}

	public void setModel(ResponseStatusDTO responseStatusDto) {

        this.responseStatusDto = responseStatusDto;

	}

	public ResponseStatusDTO getResponseStatusDto() {
		return responseStatusDto;
	}

	public void setResponseStatusDto(ResponseStatusDTO responseStatusDto) {
		this.responseStatusDto = responseStatusDto;
	}

	public String getSoID() {
		return soID;
	}

	public void setSoID(String soID) {
		this.soID = soID;
	}

	public String getGroupID() {
		return groupID;
	}

	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}

	public String getFromWFM() {
		return fromWFM;
	}

	public void setFromWFM(String fromWFM) {
		this.fromWFM = fromWFM;
	}

	public String getTabForWFM() {
		return tabForWFM;
	}

	public void setTabForWFM(String tabForWFM) {
		this.tabForWFM = tabForWFM;
	}

}
/*
 * Maintenance History
 * $Log: SODetailsResponseStatusAction.java,v $
 * Revision 1.24  2008/04/28 17:34:08  ypoovil
 * Fixed part of Sears00048311 - Corrected typos in conditional offer messages
 *
 * Revision 1.23  2008/04/26 01:13:47  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.21.6.1  2008/04/23 11:41:34  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.22  2008/04/23 05:19:31  hravi
 * Shyam: Reverting to build 247.
 *
 * Revision 1.21  2008/02/26 18:18:02  mhaye05
 * Merged Iteration 17 Branch into Head
 *
 * Revision 1.20.18.1  2008/02/25 16:05:15  mhaye05
 * removed System out println's
 *
 * Revision 1.20  2007/12/13 23:53:23  mhaye05
 * replaced hard coded strings with constants
 *
 * Revision 1.19  2007/11/27 22:37:41  pbhinga
 * Files changed for the functionality of showing star images based on the various ratings on screens like dashboard, providers, response history, view ratings etc.
 *
 * Revision 1.18  2007/11/14 21:58:50  mhaye05
 * changed reference to SOW_SO_ID or THE_CURRENT_SERVICE_ORDER_ID to be OrderConstants.SO_ID
 *
 */