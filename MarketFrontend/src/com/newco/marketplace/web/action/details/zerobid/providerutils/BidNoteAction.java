package com.newco.marketplace.web.action.details.zerobid.providerutils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.serviceorder.RoutedProvider;
import com.newco.marketplace.interfaces.AlertConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.web.action.base.SLDetailsBaseAction;
import com.newco.marketplace.web.action.details.zerobid.providerutils.viewobjects.BidNoteViewObject;
import com.newco.marketplace.web.action.details.zerobid.providerutils.viewobjects.PostViewObject;
import com.newco.marketplace.web.delegates.ISODetailsDelegate;
import com.newco.marketplace.web.dto.ServiceOrderDTO;
import com.opensymphony.xwork2.Preparable;
import com.servicelive.activitylog.client.IActivityLogHelper;
import com.servicelive.activitylog.domain.ActivityAssociationType;
import com.servicelive.activitylog.domain.ActivityLog;
import com.servicelive.activitylog.domain.ActivityStatus;
import com.servicelive.activitylog.domain.ActivityStatusType;
import com.servicelive.activitylog.domain.ActivityViewStatusName;
import com.servicelive.activitylog.domain.PostActivity;


public class BidNoteAction extends SLDetailsBaseAction implements Preparable{
	
	private static final long serialVersionUID = -211031532407737026L;
	private PostViewObject postViewObject = new PostViewObject();
	private static final Logger logger = Logger.getLogger(BidNoteAction.class.getName());
	private static final String REPORTED_POST_TEXT = "This post has been reported. It will remain hidden until further review.";
	private static final String CREATED_BY_ME_LABEL = "You ";
	private static final String CREATED_BY_SL_SUPPORT = "ServiceLive Support";
	
	private IActivityLogHelper activityLogHelper;
	protected ISODetailsDelegate soDetailsDelegate;
	
	private String globalOrderId;
	
	
	private List<BidNoteViewObject> bidNotes = new ArrayList<BidNoteViewObject>();
	private Map<Long, BidNoteViewObject> bidNoteMap = new TreeMap<Long, BidNoteViewObject>();
	private Map<Long, String> providerNames = new HashMap<Long, String>();
	
	public PostViewObject getModel() {
		return postViewObject;
	}

	public void prepare() throws Exception
	{
		createCommonServiceOrderCriteria();
	}
	
	
	public String viewNotes() {
		globalOrderId = getServiceOrderId();
		setupBidNotes(false);
		
		return SUCCESS;
	}

	
	private String getServiceOrderId()
	{
		//SL-19820
		String soId = getParameter("soId");
		//ServiceOrderDTO so = (ServiceOrderDTO) getRequest().getSession().getAttribute("THE_SERVICE_ORDER");
		ServiceOrderDTO so = (ServiceOrderDTO) getRequest().getAttribute("THE_SERVICE_ORDER");
		if(null == so){
			try{
				so = soDetailsDelegate.getServiceOrder(soId, get_commonCriteria().getRoleId(), null);
			}catch(Exception e){
				logger.error("Exception while trying to fetch SO Details");
			}
		}
		setAttribute(THE_SERVICE_ORDER, so);
		setAttribute(SO_ID, soId);
		String msg = (String)getSession().getAttribute(Constants.SESSION.SOD_MSG+"_"+soId);
		getSession().removeAttribute(Constants.SESSION.SOD_MSG+"_"+soId);
		setAttribute(Constants.SESSION.SOD_MSG, msg);
		
		if(so != null) {
			if (so.getStatus() == OrderConstants.ROUTED_STATUS) {
				getRequest().setAttribute("showAddNote", Boolean.TRUE);
			} else {
				getRequest().setAttribute("showAddNote", Boolean.FALSE);
			}
			return so.getId();
		}
		return null;
	}

	private Long getProviderResourceId()
	{		
		return get_commonCriteria().getVendBuyerResId().longValue();
	}
	
	private Long getProviderId()
	{		
		return get_commonCriteria().getCompanyId().longValue();
	}
	
	private Long getBuyerId()
	{	
		//SL-19820
		//ServiceOrderDTO so = (ServiceOrderDTO) getRequest().getSession().getAttribute("THE_SERVICE_ORDER");
		ServiceOrderDTO so = (ServiceOrderDTO) getAttribute("THE_SERVICE_ORDER");
		if(so != null)
		{
			if(StringUtils.isNumeric(so.getBuyerID()))
				return Long.parseLong(so.getBuyerID());
		}
		return null;
	}

	private String getProviderName()
	{		
		String name = get_commonCriteria().getFName() + " " + get_commonCriteria().getLName();
		return name;
	}
	
	private Long getAdminResourceId()
	{		
		return new Long(get_commonCriteria().getSecurityContext().getAdminResId());
	}

	private String getAdminUsername()
	{		
		return get_commonCriteria().getSecurityContext().getSlAdminUName();
	}
	
	private String getPostText()
	{
		if(getModel().getPostText() != null)
			return getModel().getPostText();
		else
			return getParameter("postText");
	}

	private String getReplyText()
	{
		return getParameter("replyText");
	}
	
	private Long getParentActivityId()
	{
		String str = getParameter("parentActivityId");
		if(StringUtils.isNumeric(str))
			return Long.parseLong(str);
		
		return null;
	}
	
	private boolean isFilteredByFollowup()
	{
		String str = getParameter("filterFollowup");
		return str != null && str.equals("true");
	}
	
	private String getAssociationType()
	{
		return getParameter("associationType");
	}
	
	
	private Long getBuyerResourceId()
	{
		//SL-19820
		//ServiceOrderDTO so = (ServiceOrderDTO) getRequest().getSession().getAttribute("THE_SERVICE_ORDER");
		ServiceOrderDTO so = (ServiceOrderDTO) getAttribute("THE_SERVICE_ORDER");
		if(so != null)
		{
			if(StringUtils.isNumeric(so.getBuyerID()))
				return Long.parseLong(so.getBuyerID());
		}
		return null;
	}
	
	private String getUserName() {
		return get_commonCriteria().getTheUserName();
	}
	
	private boolean isAdmin()
	{
		return get_commonCriteria().getSecurityContext().getSLAdminInd();
	}
	
	private boolean isBuyer() {
		return get_commonCriteria().getRoleId().equals(OrderConstants.BUYER_ROLEID)
				|| get_commonCriteria().getRoleId().equals(OrderConstants.SIMPLE_BUYER_ROLEID);
	}
	
	
	public String addNote() {
		globalOrderId = getServiceOrderId();
		
		Long providerResourceId = getProviderResourceId();
		Long logId=null;	
		
		
		try {
			if(isAdmin())
			{
				
				logId = activityLogHelper.logAdministratorPostOnBehalfOfProvider(
						globalOrderId,//String serviceOrderId,
					    getAdminResourceId(),//Long administratorId,
					    getProviderId(), //Long providerId,
					    getProviderResourceId(),//Long providerResourceId,
					    getBuyerId(),//Long buyerId,
					    getBuyerResourceId(),//Long buyerResourceId,
					    getPostText(),//String comment,
					    getAdminUsername()//String administratorUserId
					    );			
			}
			else if (isBuyer()) {
				logId = activityLogHelper.logBuyerPost(
						globalOrderId, //String serviceOrderId, 
						getBuyerId(), //Long buyerId,
						getProviderResourceId(), //Long buyerResourceId, 
					    getPostText(), //String comment, 
					    getUserName() //String buyerUserId
					    );
			}
			else
			{
				logId = activityLogHelper.logProviderPost(
						globalOrderId,
						getProviderId(),
						providerResourceId,
						getBuyerId(),
						getBuyerResourceId(),
						getPostText(),
						getProviderName()
						);
				
			}
		} catch (Exception x) {
			logger.error("Bid Note Save Failed: " +
					"logID=" +  logId + "\n" +					
					"soID=" +  globalOrderId + "\n" +
					"providerID=" +  getProviderId() + "\n" +
					"providerResourceID=" +  getProviderResourceId() + "\n" +
					"buyerID=" +  getBuyerId() + "\n" +
					"buyerResourceID=" +  getBuyerResourceId() + "\n" +
					"postText=" +  getPostText() + "\n" +
					"providerName=" +  getProviderName() + "\n"
					);
			
			setAttribute("logErrorMsg", "The Activity Logger has failed to save the Note.");
		}
		
		try{
			if(logId != null)
			{
				sendEmailToBuyerAndRoutedProviders();	
				
				logger.info("Bid Note Saved: " +
						"logID=" +  logId + "\n" +					
						"soID=" +  globalOrderId + "\n" +
						"providerID=" +  getProviderId() + "\n" +
						"providerResourceID=" +  getProviderResourceId() + "\n" +
						"buyerID=" +  getBuyerId() + "\n" +
						"buyerResourceID=" +  getBuyerResourceId() + "\n" +
						"postText=" +  getPostText() + "\n" +
						"providerName=" +  getProviderName() + "\n"
						);					
			}
			else
			{
				setAttribute("logErrorMsg", "The Activity Logger did not successfully save the Note.");			
			}
					
			postViewObject.setPostText("");
			
			boolean filterFollowup = isFilteredByFollowup();
			setupBidNotes(filterFollowup);
			
		}catch(Exception e){
			logger.error("Error while adding Bid Notes: " +
					"soID=" +  globalOrderId + "\n" +
					"providerID=" +  getProviderId() + "\n" +
					"providerResourceID=" +  getProviderResourceId() + "\n"
					);
		}
		
		return SUCCESS;
	}
	
	private void sendEmailToBuyerAndRoutedProviders()
	{
		//SL-19820
		//ServiceOrderDTO so = (ServiceOrderDTO) getRequest().getSession().getAttribute("THE_SERVICE_ORDER");
		ServiceOrderDTO so = (ServiceOrderDTO) getAttribute("THE_SERVICE_ORDER");
		
		// Send to buyer (unless this note was posted by the buyer)
		if (!isBuyer()) {
			String buyerEmail = null;
			if(so.getBuyerContact() != null && so.getBuyerContact().getEmail() != null)
				buyerEmail = so.getBuyerContact().getEmail();
			else if(so.getBuyerSupportContact() != null && so.getBuyerSupportContact().getEmail() != null)
				buyerEmail = so.getBuyerSupportContact().getEmail();
			
			String buyerRole = AlertConstants.ROLE_CONSUMER_BUYER;
			if(null != so.getBuyerRoleId() && so.getBuyerRoleId() == 3){
				buyerRole = AlertConstants.ROLE_PROFESSIONAL_BUYER;
			}
			if(buyerEmail != null)
			{
				getSoDetailsDelegate().sendEmailForNoteOrQuestion(buyerEmail, so.getId(), so.getTitle(), getPostText(), buyerRole);
			}
			else
			{
				logger.error("sendEmailToBuyerAndRoutedProviders() Could not find buyerEmail for SO# " + so.getId());
			}
		}
			
		// Send to all providers who have not rejected order
		for(RoutedProvider provider : so.getRoutedResources())
		{
			if("Rejected".equalsIgnoreCase(provider.getProviderRespDescription()))
				continue;
					
			if(provider.getProviderContact() != null && provider.getProviderContact().getEmail() != null)
			{
				if(getSoDetailsDelegate() != null) // Can't test fully. ActivityLog Web Service is not working
					getSoDetailsDelegate().sendEmailForNoteOrQuestion(provider.getProviderContact().getEmail(), so.getId(), so.getTitle(), getPostText(), AlertConstants.ROLE_PROVIDER);
				else if(getDetailsDelegate() != null) // Can't test fully. ActivityLog Web Service is not working
					getDetailsDelegate().sendEmailForNoteOrQuestion(provider.getProviderContact().getEmail(), so.getId(), so.getTitle(), getPostText(), AlertConstants.ROLE_PROVIDER);
			}
		}
	}
	
	public String addReply() {
		
		Long responseId = null;
		if(isAdmin())
		{
		    responseId =  activityLogHelper.logAdministratorResponseOnBehalfOfProvider(
		    			getServiceOrderId(),//String serviceOrderId,
		    	    	getAdminResourceId(), //Long administratorId,
		    	    	getProviderId(),//Long providerId,
		    	    	getProviderResourceId(), //Long providerResourceId,
		    	    	getBuyerId(),//Long buyerId,
		    	    	getBuyerResourceId(),//Long buyerResourceId,
		    	    	getParentActivityId(), //Long parentActivityId,
		    	    	getReplyText(),//String comment,
		    	    	getAdminUsername()//String administratorUserId
		    	    	);
			
		}
		else if (isBuyer()) {
			responseId = activityLogHelper.logBuyerResponse(
					getServiceOrderId(), //String serviceOrderId 
					getBuyerId(), //Long buyerId,
					getProviderResourceId(), //Long buyerResourceId,
				    getParentActivityId(), //Long parentActivityId 
				    getReplyText(), //String comment, 
				    getUserName() //String buyerUserId
					);
		}
		else
		{
			responseId = activityLogHelper.logProviderResponse(
					getServiceOrderId(),
					getProviderId(),
					getProviderResourceId(),
					getBuyerId(),
					getBuyerResourceId(),
					getParentActivityId(),
					getReplyText(),
					getProviderName());
		}
		
		if(responseId == null || responseId < 0)
		{
			logger.error("Bid Note Reply Failed: " +
					"responseID=" +  responseId + "\n" +					
					"soID=" +  globalOrderId + "\n" +
					"providerID=" +  getProviderId() + "\n" +
					"providerResourceID=" +  getProviderResourceId() + "\n" +
					"buyerID=" +  getBuyerId() + "\n" +
					"buyerResourceID=" +  getBuyerResourceId() + "\n" +
					"replyText=" +  getReplyText() + "\n" +
					"providerName=" +  getProviderName() + "\n"
					);
			
			setAttribute("logErrorMsg", "The Activity Logger did not successfully save the Reply.");			
		}
		else
		{
			logger.info("Bid Reply Saved: " +
					"responseId=" +  responseId + "\n" +					
					"soID=" +  globalOrderId + "\n" +
					"providerID=" +  getProviderId() + "\n" +
					"providerResourceID=" +  getProviderResourceId() + "\n" +
					"buyerID=" +  getBuyerId() + "\n" +
					"buyerResourceID=" +  getBuyerResourceId() + "\n" +
					"replyText=" +  getReplyText() + "\n" +
					"providerName=" +  getProviderName() + "\n"
					);
			postViewObject.setPostText(getReplyText());
			sendEmailToBuyerAndRoutedProviders();
		}
		
		
		postViewObject.setPostText("");
		
		boolean filterFollowup = isFilteredByFollowup();
		setupBidNotes(filterFollowup);
		
		return SUCCESS;
	}
	
	
	public String reportPost() {
		globalOrderId = getServiceOrderId();
		
		if(getParentActivityId() != null)
		{
			String name = "";
			
			if(isAdmin())
			{
				name = getAdminUsername();
			}
			else
			{
				name = getProviderName();
			}
			
			String comment = name + " has reported post #" + getParentActivityId();
			
			activityLogHelper.logPostReported(getParentActivityId(), comment, name);
		}
		
		boolean filterFollowup = isFilteredByFollowup();
		setupBidNotes(filterFollowup);
		
		return SUCCESS;
	}
	

	public String makePostViewable() {
		globalOrderId = getServiceOrderId();
		
		if(getParentActivityId() != null)
		{
			if(isAdmin())
			{
				String name = getAdminUsername();			
				activityLogHelper.updateActivityStatus(getParentActivityId(), ActivityStatusType.ENABLED, "Admin change request", name);
			}
		}
		
		boolean filterFollowup = isFilteredByFollowup();  // this is not being set when action is called
		setupBidNotes(filterFollowup);
		
		return SUCCESS;
	}

	public String hidePost() {
		globalOrderId = getServiceOrderId();
		
		if(getParentActivityId() != null)
		{
			if(isAdmin())
			{
				String name = getAdminUsername();
				activityLogHelper.updateActivityStatus(getParentActivityId(), ActivityStatusType.DISABLED, "Admin change request", name);
			}			
		}				
		
		boolean filterFollowup = isFilteredByFollowup();  // this is not being set when action is called
		setupBidNotes(filterFollowup);
		
		return SUCCESS;
	}
	
	public String markPostRead() {
		String username = get_commonCriteria().getTheUserName();
		getSoDetailsDelegate().markPostAsReadByBuyer(getParentActivityId(), getAssociationType(), "", username);
		boolean filterFollowup = isFilteredByFollowup();
		
		setupBidNotes(filterFollowup);
		return SUCCESS;
	}
	
	public String flagForFollowup() {
		String username = get_commonCriteria().getTheUserName();
		getSoDetailsDelegate().markPostAsRequiringFollowUpByBuyer(getParentActivityId(), getAssociationType(), "", username);
		
		setupBidNotes(false);
		return SUCCESS;
	} 
	
	public String viewFollowupPosts() {
		setupBidNotes(true);
		
		return SUCCESS;
	}
	
	
	
	private void setupBidNotes(boolean filterByFollowup) {
		
		setAttribute("filterFollowup", filterByFollowup);
		//Sl-19820
		//ServiceOrderDTO so = (ServiceOrderDTO) getRequest().getSession().getAttribute("THE_SERVICE_ORDER");
		List<ActivityLog> providerActivities= new ArrayList<ActivityLog>();
		try
		{
			//providerActivities = activityLogHelper.getProviderActivities(providerId, soID); Original
			providerActivities = activityLogHelper.getAllOrderActivities(getProviderId(), getServiceOrderId());
		}
		catch (RuntimeException e)
		{
			logger.error("Error Retrieving Bid Notes: " +
					"soID=" +  globalOrderId + "\n" +
					"providerID=" +  getProviderId() + "\n" +
					"providerResourceID=" +  getProviderResourceId() + "\n"
					);						
		}
		logger.info("Retrieving Bid Notes: " +
				"count" +  providerActivities.size() + "\n" +						
				"soID=" +  globalOrderId + "\n" +
				"providerID=" +  getProviderId() + "\n" +
				"providerResourceID=" +  getProviderResourceId() + "\n"
				);			
		
		ServiceOrderDTO so = (ServiceOrderDTO) getRequest().getAttribute("THE_SERVICE_ORDER");
		
		setAttribute("REPORTED_POST_TEXT", REPORTED_POST_TEXT);
		
		createBidNotes(providerActivities, filterByFollowup);
		
		
		TreeMap<Date, BidNoteViewObject> bidNotesByDate = new TreeMap<Date, BidNoteViewObject>();
		// add the bid notes to a tree map sorted by last thread activity date
		for (BidNoteViewObject viewObject : bidNoteMap.values()) {
			bidNotesByDate.put(viewObject.getLastActivity(), viewObject);
		}
		// add the bid notes to a list
		for (BidNoteViewObject viewObject : bidNotesByDate.values()) {
			bidNotes.add(viewObject);
		}
		// reverse the list so the newest threads are first
		java.util.Collections.reverse(bidNotes);
		
		// Set the role
		setAttribute("userRole", get_commonCriteria().getRoleType());
		
		// If the order is not in Routed status, make the notes read-only 
		if(null != so){
			setAttribute("readOnly", !so.getStatus().equals(OrderConstants.ROUTED_STATUS));
		}
		//&& get_commonCriteria().getRoleId().equals(OrderConstants.BUYER_ROLEID)
	}
	
	
	private boolean isReportedForReview(PostActivity post)
	{
		if(post == null)
			return false;
		
		if(post.getStatuses() == null || post.getStatuses().size() == 0)
			return false;
		
		String postStatus = getPostStatusName(post);
		if("REPORTED_FOR_REVIEW".equals(postStatus))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	private String getPostStatusName(PostActivity post)
	{
		for (ActivityStatus as : post.getStatuses()) {
			if (as.getIsCurrent()) {
				return as.getStatus().name();
			}
		}		
		return "";
	}
	
	private void createBidNotes(List<ActivityLog> activities, boolean filterByFollowup) {
		int currentProviderNumber = 1;

		boolean isAdmin = get_commonCriteria().getSecurityContext().getSLAdminInd();
		setAttribute("isAdmin", isAdmin);
		int roleId = get_commonCriteria().getRoleId().intValue();
		if(isAdmin)
		{
			setAttribute("currentResourceId", getAdminResourceId());
		}
		else if(roleId == OrderConstants.PROVIDER_ROLEID)
		{
			setAttribute("currentResourceId", getProviderResourceId());			
		}
		else if(roleId == OrderConstants.BUYER_ROLEID || roleId == OrderConstants.SIMPLE_BUYER_ROLEID)
		{
			setAttribute("currentResourceId", get_commonCriteria().getVendBuyerResId());
		}
		
		// loop through in reverse order to ensure original posts are retrieved before their replies
		for (ListIterator<ActivityLog> iter = activities.listIterator(activities.size()); iter.hasPrevious(); ) {
			ActivityLog activity = iter.previous();
			BidNoteViewObject viewObject = new BidNoteViewObject();
			
			// only add posts (not bids, etc)
			if (activity.getType().getId() == 0) {
				// set the basic info for the message
				PostActivity post = (PostActivity) activity;
				
				
				viewObject.setActivityId(post.getActivityId());
				viewObject.setCreatedOn(post.getCreatedOn());
				viewObject.setStatus(getPostStatusName(post));
				if (post.getTargetType() == ActivityAssociationType.Buyer) {
					viewObject.setViewStatusName(post.getCurrentTargetViewStatus().getViewStatusName().name());
					viewObject.setAssociationType("target");
				} else {
					viewObject.setViewStatusName(post.getCurrentSourceViewStatus().getViewStatusName().name());
					viewObject.setAssociationType("source");
				}
				viewObject.setShowFollowupFlag(roleId == OrderConstants.BUYER_ROLEID || roleId == OrderConstants.SIMPLE_BUYER_ROLEID);  // Show followup flag if the user is a buyer
				
				if("DISABLED".equals(getPostStatusName(post)))
					viewObject.setPost("This posting was removed by ServiceLive.");
				else
					viewObject.setPost(post.getPost());
				
				//boolean doNotAdd = (filterByFollowup && !ActivityViewStatusName.REQUIRES_FOLLOW_UP.name().equals(viewObject.getViewStatusName()));
				//if (!doNotAdd) {
					Long vendorBuyerResourceId = new Long(get_commonCriteria().getSecurityContext().getVendBuyerResId());
					ActivityAssociationType type = post.getSourceType();
					
					// set the name and type of the message's author
					if (type == ActivityAssociationType.Provider) {
						Long providerId = post.getProviderId();
						
						// For SL Admin adopted users
						if(isAdmin)
						{
							viewObject.setPosterId(post.getProviderId());						
							viewObject.setPosterName(post.getCreatedBy());
						}
						
						if (vendorBuyerResourceId.longValue() == post.getProviderResourceId().longValue())
						{
							viewObject.setPosterId(post.getProviderResourceId());						
							viewObject.setCreatedByName(CREATED_BY_ME_LABEL);
							viewObject.setIcon("images/icons/bidNotes/comment.png");
						}
						else
						{
							// get provider name from name map or add it
							if (providerNames.containsKey(providerId))
							{
								viewObject.setCreatedByName(providerNames.get(providerId));
							}
							else
							{
								String newName = "Provider " + currentProviderNumber;
								if (isBuyer()) {
									newName = getProviderName(post.getProviderResourceId()); 
								}
								
								viewObject.setCreatedByName(newName);
								providerNames.put(providerId, newName);
								currentProviderNumber++;
							}
							viewObject.setIcon("images/icons/bidNotes/comments.png");
						}
					}
					else if (type == ActivityAssociationType.Buyer)
					{					
						if (isBuyer()) {
							viewObject.setCreatedByName(CREATED_BY_ME_LABEL);
							viewObject.setIcon("images/icons/bidNotes/comment.png");
						} else {
							viewObject.setCreatedByName("Buyer");
							viewObject.setIcon("images/icons/bidNotes/communicationBlue.gif");
						}
						viewObject.setPosterId(post.getBuyerResourceId());
						// For SL Admin adopted users
						if (isAdmin)
						{
							viewObject.setPosterId(post.getBuyerResourceId());
							viewObject.setPosterName(post.getCreatedBy());
						}
	
					}
					else if (type == ActivityAssociationType.Administrator)
					{
						viewObject.setCreatedByName(CREATED_BY_SL_SUPPORT);
						viewObject.setIcon("images/icons/bidNotes/slSupportNote.png");
						viewObject.setPosterId(post.getAdminId());
					}
	
					// add replies to their parent bidNoteViewObject
					if (activity.getParentActivityId() != null)
					{
						if (bidNoteMap.containsKey(activity.getParentActivityId()))
						{
							BidNoteViewObject parentActivity = bidNoteMap.get(activity.getParentActivityId());
							parentActivity.addReply(viewObject);
							if (viewObject.getCreatedOnDate().after(parentActivity.getCreatedOnDate()))
							{
								parentActivity.setLastActivity(viewObject.getCreatedOnDate());
							}
						}
					}
					// add original posts to the bidNotes map
					else
					{
						viewObject.setLastActivity(viewObject.getCreatedOnDate());
						bidNoteMap.put(activity.getActivityId(), viewObject);
					}
				//}
			}
		}
	}
	
	private String getProviderName(Long providerId) {
		//SL-19820
		//ServiceOrderDTO so = (ServiceOrderDTO) getRequest().getSession().getAttribute("THE_SERVICE_ORDER");
		ServiceOrderDTO so = (ServiceOrderDTO) getAttribute("THE_SERVICE_ORDER");
		String providerName = "Provider";
		
		for (int i = 0; i < so.getRoutedResources().size(); i++) {
			RoutedProvider provider = so.getRoutedResources().get(i);
			if (providerId.equals(provider.getResourceId().longValue())) {
				providerName = provider.getProviderContact().getFirstName() + " " + provider.getProviderContact().getLastName().substring(0, 1);
				break;
			}
		}
		/*
		for (RoutedProvider provider : so.getRoutedResources()) {
			if ( provider.getResourceId().equals(providerId)) {
				providerName = provider.getProviderContact().getFirstName() + " " + provider.getProviderContact().getLastName().substring(0, 1);
				break;
			}
		}
		*/
		
		return providerName;
	}
	
	public void setActivityLogHelper(IActivityLogHelper activityLogHelper) {
		this.activityLogHelper = activityLogHelper;
	}

	public IActivityLogHelper getActivityLogHelper() {
		return activityLogHelper;
	}
	


	public List<BidNoteViewObject> getBidNotes() {
		return bidNotes;
	}

	public void setBidNotes(List<BidNoteViewObject> bidNotes) {
		this.bidNotes = bidNotes;
	}

	public ISODetailsDelegate getSoDetailsDelegate()
	{
		return soDetailsDelegate;
	}

	public void setSoDetailsDelegate(ISODetailsDelegate soDetailsDelegate)
	{
		this.soDetailsDelegate = soDetailsDelegate;
	}

	
}
