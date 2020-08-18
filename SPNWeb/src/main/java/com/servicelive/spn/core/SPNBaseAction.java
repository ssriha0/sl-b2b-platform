/**
 *
 */
package com.servicelive.spn.core;

import static com.servicelive.spn.constants.SPNActionConstants.USER_OBJECT_IN_SESSION;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.servicelive.domain.common.BuyerFeatureSetEnum;
import com.servicelive.domain.common.Document;
import com.servicelive.domain.lookup.LookupSPNMeetAndGreetState;
import com.servicelive.domain.provider.ProviderFirm;
import com.servicelive.domain.provider.ServiceProvider;
import com.servicelive.domain.spn.detached.LabelValueBean;
import com.servicelive.domain.spn.network.SPNHeader;
import com.servicelive.domain.spn.network.SPNMeetAndGreetState;
import com.servicelive.domain.spn.network.SPNMeetAndGreetStatePk;
import com.servicelive.domain.spn.network.SPNProviderFirmState;
import com.servicelive.domain.userprofile.SPNUser;
import com.servicelive.domain.userprofile.User;
import com.servicelive.spn.buyer.auditor.SPNApplicantCounts;
import com.servicelive.spn.buyer.auditor.newapplicants.SPNNewApplicantsTabModel;
import com.servicelive.spn.common.SPNBackendConstants;
import com.servicelive.spn.common.detached.SPNAuditorQueueCountVO;
import com.servicelive.spn.common.util.CalendarUtil;
import com.servicelive.spn.common.util.UiUtil;
import com.servicelive.spn.services.DocumentServices;
import com.servicelive.spn.services.SPNTierService;
import com.servicelive.spn.services.auditor.MeetAndGreetStateService;
import com.servicelive.spn.services.buyer.BuyerServices;
import com.servicelive.spn.services.campaign.CampaignInvitationServices;
import com.servicelive.spn.services.interfaces.IAuditorQueueCountService;
import com.servicelive.spn.services.interfaces.IProviderFirmStateService;
import com.newco.marketplace.persistence.service.document.DocumentService;

/**
 * @author ccarle5
 *
 */
public class SPNBaseAction extends ActionSupport implements SessionAware,
		ServletRequestAware {

	private IAuditorQueueCountService auditorQueueCountService;
	private MeetAndGreetStateService meetAndGreetStateService;
	private CampaignInvitationServices campaignInvitationServices;
	private DocumentServices documentServices;
	private IProviderFirmStateService providerFirmStateService;
	private SPNTierService spnTierService;
    private DocumentService documentService;



	/**
	 *
	 */
	private static final long serialVersionUID = 5123722257715804324L;
	protected final Log logger = LogFactory.getLog(this.getClass());




	private Map<?,?> sSessionMap;
	private HttpServletRequest request;
	private BuyerServices buyerServices;
	//private PrincipalProxy principalProxy;
	//private Integer loggedInUserId = null;


	/**
	 * @return the loggedInUserId
	 */
	public Integer getCurrentBuyerId() {

		User loggedInUser = getLoggedInUser();
		if (loggedInUser == null)
		{
			return null;
		}
		return loggedInUser.getUserId();
	}

	/**
	 * This method suppose to return  User object wrapped in Provider , Buyer object
	 * @return the loggedInUser
	 */
	public User getLoggedInUser() {
		/*System.err.println("Principal in SPN App: " + principalProxy.getRemoteUser());
		String userName = principalProxy.getRemoteUser();*/

		SPNUser spnuser = getLoggedInSPNUser();
		return spnuser.getUserDetails();
	}
	
	public SPNUser getLoggedInSPNUser() { 
		SPNUser user = (SPNUser)this.getRequest().getSession().getAttribute(USER_OBJECT_IN_SESSION);
		return user;
	}
	/**
	 *
	 * @return String
	 */
	public String getCurrentBuyerResourceUserName() {
		User loggedInUser = getLoggedInUser();
		if (loggedInUser != null)
		{
			return loggedInUser.getUsername();
		}
		return null;
	}



	/*
	 * (non-Javadoc)
	 *
	 * @see org.apache.struts2.interceptor.SessionAware#setSession(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public void setSession(Map sessionMap) {
		sSessionMap = sessionMap;

	}
	/**
	 *
	 * @param buyerId
	 * @return SPNApplicantCounts
	 */
	public SPNApplicantCounts getApplicantCounts(Integer buyerId)
	{
		SPNApplicantCounts applicantCounts = new SPNApplicantCounts();
		List<SPNAuditorQueueCountVO> counts = null;
		try
		{
			counts = auditorQueueCountService.getCounts(buyerId);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return applicantCounts;
		}



		for(SPNAuditorQueueCountVO vo : counts)
		{
			if(vo.getState().getId().equals(SPNBackendConstants.WF_STATUS_PF_SPN_APPLICANT))
			{
				applicantCounts.setNewApplicantsCount(vo.getCount().intValue());
			}
			else if(vo.getState().getId().equals(SPNBackendConstants.WF_STATUS_PF_SPN_REAPPLICANT))
			{
				applicantCounts.setReApplicantsCount(vo.getCount().intValue());
			}
			// Code Added for Jira SL-19384
			// To get count of 'Membership Under Review' status 
			else if(vo.getState().getId().equals(SPNBackendConstants.WF_STATUS_PF_SPN_MEMBERSHIP_UNDER_REVIEW))
			{
				applicantCounts.setMembershipUnderReviewCount(vo.getCount().intValue());
			}
		}

		return applicantCounts;
	}

	/** This piece of functionality is used on all 3 tabs of the Auditor
	 *
	 * @param firmState
	 * @param model
	 * @param serviceProvider
	 * @return SPNNewApplicantsTabModel
	 * @throws SQLException
	 */
	public  SPNNewApplicantsTabModel initApplicantModel(SPNProviderFirmState firmState, SPNNewApplicantsTabModel model, ServiceProvider serviceProvider) throws SQLException
	{
		if(firmState == null)
			return model;

		if(model == null)
			return model;

		if(serviceProvider == null)
			return model;

		Integer providerFirmId = null;
		providerFirmId = firmState.getProviderFirmStatePk().getProviderFirm().getId();

		model.setBusinessName(serviceProvider.getProviderFirm().getBusinessName());
		model.setFirmID(providerFirmId + "");
		model.setNetworkName(firmState.getProviderFirmStatePk().getSpnHeader().getSpnName());
		model.setSpnStatus((String)firmState.getWfState().getId());
		model.setSlStatus(firmState.getProviderFirmStatePk().getProviderFirm().getServiceLiveWorkFlowState().getWfState());

		// Doing Business As - DBA
		//getModel().setDba(firmState.getProviderFirmStatePk().getProviderFirm().get);
		model.setDba(firmState.getProviderFirmStatePk().getProviderFirm().getDba());

		// Set Invited Date and Applied Dates
		String tmpDate;
		tmpDate = CalendarUtil.formatDateAndTimeForDisplay(campaignInvitationServices.findProviderFirmInvitedDate(firmState.getProviderFirmStatePk().getProviderFirm().getId(), firmState.getProviderFirmStatePk().getSpnHeader().getSpnId()));
		model.setInvitedDate(tmpDate);
		tmpDate = CalendarUtil.formatDateAndTimeForDisplay(firmState.getApplicationSubmissionDate());
		model.setAppliedDate(tmpDate);


		if (serviceProvider.getContact() != null)
		{
			// Small hack to format the phone number with hyphens
			serviceProvider.getContact().setPhoneNo(UiUtil.formatPhone(serviceProvider.getContact().getPhoneNo()));
			model.setContact(serviceProvider.getContact());
		}
		
		//SL-19381
		//Setting information to modal to display contact information of a firm
		if (serviceProvider.getProviderLocationDetails() != null)
		{
			
			model.setProviderLocationDetails(serviceProvider.getProviderLocationDetails());
		}
	
		model.setNetworkId(firmState.getProviderFirmStatePk().getSpnHeader().getSpnId());
		model.setProviderFirmId(providerFirmId);

		return model;
	}

	/**
	 *
	 * @param str
	 * @return boolean
	 */
	public boolean isInteger(String str)
	{
		try
		{
			Integer.parseInt(str);
		}
		catch(Exception e)
		{
			return false;
		}

		return true;
	}

	/**
	 * 2 separate backend calls to get MeetNGreet states.  Throw them into the same list, and then process them for display.
	 * @param buyerId
	 * @param networkId
	 * @param providerFirmId
	 * @return List
	 */
	public List<SPNMeetAndGreetState> getMeetGreetStates(Integer buyerId, Integer networkId, Integer providerFirmId)
	{
		if(buyerId == null)
			return null;

		if(networkId == null)
			return null;

		if(providerFirmId == null)
			return null;

		// 2 separate backend calls to get MeetNGreet states.  Throw them into the same list, and then process them for display.
		List<SPNMeetAndGreetState> meetGreetStates = new ArrayList<SPNMeetAndGreetState>();
		List<SPNMeetAndGreetState> tmpStates = meetAndGreetStateService.findByBuyerIdAndProviderFirmIdExcludingSpnId(buyerId, networkId, providerFirmId);


		SPNMeetAndGreetState tmpState = null;

		Boolean isRequired = Boolean.valueOf(this.isMeetAndGreetRequiredForNetwork(networkId));

		if(isRequired.booleanValue())
		{
			tmpState = this.findMeetAndGreet(networkId, providerFirmId);

			// If MnG is required and no row in DB is found, create a placeholder object with minimal data.
			if(tmpState == null)
			{
				tmpState = new SPNMeetAndGreetState();
				tmpState.setMeetAndGreetStatePk(new SPNMeetAndGreetStatePk(networkId, providerFirmId));

				ProviderFirm providerFirm = new ProviderFirm();
				tmpState.getMeetAndGreetStatePk().setProviderFirm(providerFirm);


				try
				{
					SPNProviderFirmState firmState = providerFirmStateService.findProviderFirmState(networkId, providerFirmId);

					SPNHeader spnHeader = new SPNHeader();
					spnHeader.setSpnName(firmState.getProviderFirmStatePk().getSpnHeader().getSpnName());
					tmpState.getMeetAndGreetStatePk().setSpnHeader(spnHeader);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}


				LookupSPNMeetAndGreetState mngState = new LookupSPNMeetAndGreetState();
				mngState.setDescription("Pending Approval");
				tmpState.setState(mngState);
			}
			// We have an existing DB object/row.
			else
			{
				// Some Data Correction code. If the Network name is somehow null or empty string,
				// use the providerFirmsState to get the Network name.
				if(		tmpState.getMeetAndGreetStatePk().getSpnHeader().getSpnName() == null ||
						"".equals(tmpState.getMeetAndGreetStatePk().getSpnHeader().getSpnName())
					)
					{
						SPNProviderFirmState firmState=null;
						try
						{
							firmState = providerFirmStateService.findProviderFirmState(networkId, providerFirmId);
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
						if(firmState != null)
						{
							tmpState.getMeetAndGreetStatePk().getSpnHeader().setSpnName(firmState.getProviderFirmStatePk().getSpnHeader().getSpnName());
						}
					}
			}
		}
		else
		{
			tmpState = new SPNMeetAndGreetState();
		}

		meetGreetStates.add(tmpState);

		if(tmpStates != null && tmpStates.size() > 0)
		{
			meetGreetStates.addAll(tmpStates);
		}

		return meetGreetStates;

	}


	public boolean isMeetAndGreetRequiredForNetwork(Integer networkId)
	{
		return meetAndGreetStateService.isMeetAndGreetRequired(networkId).booleanValue();
	}

	public SPNMeetAndGreetState findMeetAndGreet(Integer networkId, Integer providerFirmId)
	{
		return meetAndGreetStateService.find(networkId, providerFirmId);
	}

	/**
	 *
	 * @return Integer
	 */
	public Integer getNetworkIdFromParameter()
	{
		String networkIdStr = getRequest().getParameter("networkId");

		if(networkIdStr == null || networkIdStr == "")
			return null;

		Integer networkId;
		try
		{
			networkId = Integer.valueOf(networkIdStr);
		}
		catch(Exception e)
		{
			return null;
		}

		return networkId;
	}
	/**
	 *
	 * @return Integer
	 */
	public Integer getProviderFirmIdFromParameter()
	{
		String providerFirmIdStr = getRequest().getParameter("providerFirmId");

		if(providerFirmIdStr == null || providerFirmIdStr == "")
			return null;

		Integer providerFirmId;
		try
		{
			providerFirmId = Integer.valueOf(providerFirmIdStr);
		}
		catch(Exception e)
		{
			return null;
		}

		return providerFirmId;
	}

	protected void loadDocument(Integer documentID)
	{
	  try{
			
		Document document = documentServices.findDocumentById_spn(documentID);
		
		if (document == null)
		{
			return;
		}
		String filename = document.getDocumentFileName();
		// Code has been changed under JIRA SL-18757
		// ServletActionContext.getResponse().setContentType("application/octet-stream");
		ServletActionContext.getResponse().setContentType(document.getDocumentContentType());
    	ServletActionContext.getResponse().setHeader("Content-disposition","attachment; filename="+filename+""); //+document.getDocumentFileName()+
    	//ServletActionContext.getResponse().setHeader("Cache-Control", "no-cache");
    	//ServletActionContext.getResponse().setHeader("Pragma", "No-cache");
				
		File file = new File(document.getDocPath());  
		FileInputStream fileInputStream  = new FileInputStream(file);
		ServletOutputStream out = ServletActionContext.getResponse().getOutputStream();
		byte[] buf=new byte[2000];
		int bytesread = 0, bytesBuffered = 0;
		while( (bytesread = fileInputStream.read( buf )) > -1 ) {
		    out.write( buf, 0, bytesread );
		    bytesBuffered += bytesread;
		    if (bytesBuffered > 1024 * 1024) { //flush after 1MB
		        bytesBuffered = 0;
		        out.flush();
		    }
		}
		out.flush();
		out.close();	

		}
		catch (Exception e)
		{
			logger.error("Error returned trying to get Document Content for the document ID:" + documentID, e);
		}
	}

		public String securityCheck(String nextView) {                 
			 ResourceBundle resBundle = ResourceBundle.getBundle("/resources/properties/servicelive");
			    String charac=resBundle.getString("invalid_Characters");  
	         try{                           
	                 Pattern pattern = Pattern.compile(charac,Pattern.CASE_INSENSITIVE);            
	                 Matcher matcher=pattern.matcher(nextView);                     
	                 nextView=matcher.replaceAll("");                                       
	         }
	         catch (Exception e) {
	                 logger.error("SecurityChecker.java::securityCheck::Exception occured",e);
	         }              
	         return nextView;                       
	         }
		
	public boolean isMeetAndGreetDeclined(SPNMeetAndGreetState meetGreetState)
	{
		if(	(meetGreetState != null) &&
			(meetGreetState.getState() != null) &&
			(meetGreetState.getState().getId() != null))
		{
			// TODO - Make 'MEET DECLINED' a constant
			if(meetGreetState.getState().getId().equals("MEET DECLINED"))
				return true;
		}

		return false;
	}

	public boolean isMeetAndGreetRequiredAndDeclined(Integer networkId, Integer providerFirmId)
	{
		boolean meetDeclined = false;
		boolean meetAndGreetRequired = this.isMeetAndGreetRequiredForNetwork(networkId);
		if(meetAndGreetRequired)
		{
			SPNMeetAndGreetState meetGreetState = this.findMeetAndGreet(networkId, providerFirmId);
			if(meetGreetState != null)
			{
				if(isMeetAndGreetDeclined(meetGreetState))
					meetDeclined = true;
			}
			else
			{
				logger.error("SPN Auditor: Meet&Greet has bad data.  MnG is required but state is missing for spnId=" + networkId);
			}
		}

		return meetDeclined;
	}

	private static List<LabelValueBean> _minuteOptions = null;
	private static List<LabelValueBean> _hourOptions = null;
	private static List<LabelValueBean> _dayOptions = null;
	private List<LabelValueBean> _yesNoOptions = null;

	//TODO make all the constatns values like 60 etc.. be defined in either properties file or in CONSTANST  java
	public List<LabelValueBean> getMinuteOptions() {
		if(_minuteOptions == null) {
			LabelValueBean option;
			List<LabelValueBean> minuteOptions = new ArrayList<LabelValueBean>();
			for (int i = 0; i < 60; i++) {
				option = new LabelValueBean(String.valueOf(i), String.valueOf(i));
				minuteOptions.add(option);
			}
			_minuteOptions = minuteOptions;
		}
		return _minuteOptions;

	}


	public List<LabelValueBean> getHourOptions() {
		if(_hourOptions == null) {
			List<LabelValueBean> hourOptions = new ArrayList<LabelValueBean>();
			LabelValueBean option;
			for (int i = 0; i < 24; i++) {
				option = new LabelValueBean(String.valueOf(i), String.valueOf(i));
				hourOptions.add(option);
			}
			_hourOptions = hourOptions;
		}
		return _hourOptions;
	}

	public List<LabelValueBean> getDayOptions() {
		if(_dayOptions == null) {
			List<LabelValueBean> dayOptions = new ArrayList<LabelValueBean>();
			LabelValueBean option;
			for (int i = 0; i <= 10; i++) {
				option = new LabelValueBean(String.valueOf(i), String.valueOf(i));
				dayOptions.add(option);
			}
			_dayOptions = dayOptions;
		}
		return _dayOptions;
	}



	public List<LabelValueBean> getYesNoOptions() {
		if(_yesNoOptions == null) {
			List<LabelValueBean> yesNoOptions = new ArrayList<LabelValueBean>();
			yesNoOptions.add(new LabelValueBean("Yes", "1"));
			yesNoOptions.add(new LabelValueBean("No", "0"));
			_yesNoOptions = yesNoOptions;
		}
		return _yesNoOptions;
	}
	
	public Boolean isRoutingTierAccessibleByCurrentUser() throws Exception {
		Boolean result = Boolean.FALSE;
		SPNUser user =  (SPNUser)getRequest().getSession().getAttribute(com.servicelive.spn.constants.SPNActionConstants.USER_OBJECT_IN_SESSION);
    	Set<String> authorities = user.getUserDetails().getAuthorities();
    	if(authorities.contains(BuyerFeatureSetEnum.TIER_ROUTE.name())) {
    		if(authorities.contains(com.servicelive.spn.constants.SPNActionConstants.SPN_AUTHORITY_ROUTING_TIERS)){
    			result = Boolean.TRUE;
    		}
    	}
		return result;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.apache.struts2.interceptor.ServletRequestAware#setServletRequest(javax.servlet.http.HttpServletRequest)
	 */
	public void setServletRequest(HttpServletRequest req) {
		this.request = req;
	}


	/**
	 * @return the sSessionMap
	 */
	public Map<?,?> getSSessionMap() {
		return sSessionMap;
	}


	/**
	 * @return the request
	 */
	public HttpServletRequest getRequest() {
		return request;
	}

	/**
	 * @return the response
	 */
	
	protected HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}
	/**
	 * @return the buyerServices
	 */
	public BuyerServices getBuyerServices() {
		return buyerServices;
	}

	/**
	 * @param buyerServices the buyerServices to set
	 */
	public void setBuyerServices(BuyerServices buyerServices) {
		this.buyerServices = buyerServices;
	}


	public IAuditorQueueCountService getAuditorQueueCountService()
	{
		return auditorQueueCountService;
	}

	public void setAuditorQueueCountService(IAuditorQueueCountService auditorQueueCountService)
	{
		this.auditorQueueCountService = auditorQueueCountService;
	}

	/**
	 *
	 * @return MeetAndGreetStateService
	 */
	public MeetAndGreetStateService getMeetAndGreetStateService()
	{
		return meetAndGreetStateService;
	}

	/**
	 *
	 * @param meetAndGreetStateService
	 */
	public void setMeetAndGreetStateService(MeetAndGreetStateService meetAndGreetStateService)
	{
		this.meetAndGreetStateService = meetAndGreetStateService;
	}


	/**
	 * @param campaignInvitationServices the campaignInvitationServices to set
	 */
	public void setCampaignInvitationServices(CampaignInvitationServices campaignInvitationServices)
	{
		this.campaignInvitationServices = campaignInvitationServices;
	}

	public DocumentServices getDocumentServices()
	{
		return documentServices;
	}

	public void setDocumentServices(DocumentServices documentServices)
	{
		this.documentServices = documentServices;
	}

	public IProviderFirmStateService getProviderFirmStateService()
	{
		return providerFirmStateService;
	}

	public void setProviderFirmStateService(IProviderFirmStateService providerFirmStateService)
	{
		this.providerFirmStateService = providerFirmStateService;
	}

	public SPNTierService getSpnTierService()
	{
		return spnTierService;
	}

	public void setSpnTierService(SPNTierService spnTierService)
	{
		this.spnTierService = spnTierService;
	}

}
