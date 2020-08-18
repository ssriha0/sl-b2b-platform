package com.newco.marketplace.web.action.BuyerLeadManagement;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.ErrorResult;
import com.newco.marketplace.api.beans.leaddetailmanagement.addNotes.EmailAlertType;
import com.newco.marketplace.api.beans.leaddetailmanagement.addNotes.LeadAddNoteRequest;
import com.newco.marketplace.api.beans.leaddetailmanagement.addNotes.LeadAddNoteResponse;
import com.newco.marketplace.api.beans.leaddetailmanagement.addNotes.LeadNoteType;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.businessImpl.so.pdf.SOPDFUtils;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.CancelLeadRequest;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.CancelLeadResponse;
import com.newco.marketplace.dto.vo.leadsmanagement.BuyerLeadAttachmentVO;
import com.newco.marketplace.dto.vo.leadsmanagement.BuyerLeadLookupVO;
import com.newco.marketplace.dto.vo.leadsmanagement.BuyerLeadManagementCriteriaVO;
import com.newco.marketplace.dto.vo.leadsmanagement.LeadHistoryVO;
import com.newco.marketplace.dto.vo.leadsmanagement.LeadInfoVO;
import com.newco.marketplace.dto.vo.leadsmanagement.ProviderInfoVO;
import com.newco.marketplace.dto.vo.leadsmanagement.SLLeadHistoryVO;
import com.newco.marketplace.dto.vo.leadsmanagement.SLLeadNotesVO;
import com.newco.marketplace.interfaces.NewServiceConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.vo.login.LoginCredentialVO;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.dto.BuyerLeadManagementTabDTO;
import com.newco.marketplace.web.dto.SODocumentDTO;
import com.newco.marketplace.web.dto.SOWError;
import com.newco.marketplace.web.dto.ServiceOrdersCriteria;
import com.newco.marketplace.web.ordermanagement.api.services.LeadManagementRestClient;
import com.newco.marketplace.web.utils.SecurityChecker;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.servicelive.buyerleadmanagement.services.BuyerLeadManagementService;
import com.servicelive.common.exception.DataNotFoundException;
import com.servicelive.common.properties.IApplicationProperties;


public class BuyerLeadManagementControllerAction extends SLBaseAction implements Preparable, ModelDriven<BuyerLeadManagementTabDTO>{
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger
			.getLogger(BuyerLeadManagementControllerAction.class);
	private BuyerLeadManagementTabDTO buyerLeadManagementTabDTO = new BuyerLeadManagementTabDTO();
	private BuyerLeadManagementService buyerLeadManagementService;
	private IApplicationProperties applicationProperties;
	private Integer sEcho=1;
	private String iTotalRecords="2";
	private String iTotalDisplayRecords="2";
	private String aaData[][];
	private LeadManagementRestClient buyerLeadClient;
	private List<SOWError> omErrors = new ArrayList<SOWError>();
	
	public void prepare() throws Exception {
		 createCommonServiceOrderCriteria();
	 }
	public String execute() throws Exception {
		Integer numberOfRecords=getBuyerLeadCount();
		getRequest().setAttribute("numberOfRecords",numberOfRecords);
		Integer buyerId = this._commonCriteria.getCompanyId();
		loadBuyerLeadManagementSearchCriteriaData(buyerId);
		return "success";
	}
	
	
	
	
	/**
	 * Convert the selected list of resource Ids which is a Comma seperated
	 * string, to List of Integers.
	 * 
	 * @return List<Integer> resource Ids
	 * **/
	private List<Integer> getCheckedResourceID() {
		List<Integer> resourceIDs = new ArrayList<Integer>();
		String resourceId = null;
		resourceId = getParameter("resources");
		String[] items = resourceId.split(COMMA_DELIMITER);
		for (String resourceids : items) {
			if ((StringUtils.isNotBlank(resourceids))
					&& (StringUtils.isNumeric(resourceids))) {
				int resid = Integer.valueOf(resourceids);
				resourceIDs.add(resid);
			}
		}
		return resourceIDs;
	}
	
	public String cancelLead()
	{
		
		Map<String,Integer> unsortedLeadReasons=buyerLeadManagementService.getBuyerLeadReasons(OrderConstants.LEAD_REASON_CODE_TYPE_CANCELLATION,
				OrderConstants.LEAD_ROLE_TYPE_BUYER);
		Map<String,Integer> leadReasons=new TreeMap<String,Integer>(unsortedLeadReasons);
		Map<String,Integer> sortedLeadReasons= sortedCancelLeadReasons(leadReasons);
		getSession().setAttribute("leadReasons",sortedLeadReasons);
		return "success";
	}
	
	 public  Map<String,Integer> sortedCancelLeadReasons(Map<String, Integer> map)
	    {
	    	Integer otherValue=-1;
	    	String otherKey="Other";
	        for (Entry<String, Integer> entry : map.entrySet())
	        {
	            if(entry.getKey().equalsIgnoreCase(otherKey)){
	            	otherValue=entry.getValue();
	            }
	        }
	        if(otherValue!=-1)
	        {
	        map.remove(otherKey);
	        }
	        Map<String, Integer> sortedLinkedMap = new LinkedHashMap<String, Integer>();
	        sortedLinkedMap.putAll(map);
	        sortedLinkedMap.put(otherKey, otherValue);
	        return sortedLinkedMap;
	    }
	 
	public String submitUpdateRewardPoints()
	{    
		logger.info("submitUpdateRewardPoints method");
	     buyerLeadManagementTabDTO=getModel();
		 SecurityContext leadContxt = (SecurityContext) getSession()
			.getAttribute(SECURITY_CONTEXT);
		 
		 String custMemberShipId = "";
		 String slLeadId = "";
		 int addOrRevoke = 0;
		 int pointsToAddOrRevoke = 0;
		 String desc = "";
		 String comments = "";
		 Integer points = 0;
		 String reasonDesc = "";
		
		 SOWError error = null;
		 
		 int actionId = SHOP_YOUR_WAY_REWARD_POINDS_ID;
		 String modifiedBy = leadContxt.getUsername();
		 LeadHistoryVO leadHistoryVO= new LeadHistoryVO();
		  //Get the values from request 
	    
		slLeadId = buyerLeadManagementTabDTO.getLeadId();
		logger.info("Lead Id from request  : " + slLeadId);
		
		custMemberShipId = buyerLeadManagementTabDTO.getShopYourWayId();
		logger.info("CustMemberShipId from request  : " + custMemberShipId);
		
		comments = buyerLeadManagementTabDTO.getComments();
		logger.info("Comments from request  : " + comments);
		
		
		addOrRevoke = buyerLeadManagementTabDTO.getAddOrRevoke();
		logger.info("AddOrRevoke from request  : " + addOrRevoke);
		
		reasonDesc = buyerLeadManagementTabDTO.getReasonCodeDesc();
		logger.info("ReasonDesc from request  : " + reasonDesc);
		
		pointsToAddOrRevoke = buyerLeadManagementTabDTO.getPointsToAddOrRevoke();
		logger.info("PointsToAddOrRevoke from request  : " + pointsToAddOrRevoke);
		 
		if(null == buyerLeadManagementTabDTO.getPointsToAddOrRevoke() || 0 == buyerLeadManagementTabDTO.getPointsToAddOrRevoke()){
			if(null != buyerLeadManagementTabDTO.getCustomPointsToAddOrRevoke() && 0 != buyerLeadManagementTabDTO.getCustomPointsToAddOrRevoke()){
				int customPoints = buyerLeadManagementTabDTO.getCustomPointsToAddOrRevoke();
				logger.info("Custome PointsToAddOrRevoke from request  : " + pointsToAddOrRevoke);
				pointsToAddOrRevoke = customPoints;
			}
		}
		
		
	    //Get the Current points from DB 
	    points = buyerLeadManagementService.selectShopYourWayPointsForLeadMember(slLeadId,custMemberShipId);
	    if(null == points){
    		points = 0 ;
    	}
	    logger.info("ShopYourWayPointsForLeadMember  : " + points);
		String createdBy="";
		if(leadContxt.isAdopted())
		{
			createdBy=leadContxt.getSlAdminFName()+" "+leadContxt.getSlAdminLName();
		}
		else
		{
			createdBy=this._commonCriteria.getFName()+" "+this._commonCriteria.getLName();
		}
	    leadHistoryVO.setActionId(actionId);
	    leadHistoryVO.setSlLeadId(slLeadId);
	    leadHistoryVO.setRoleId(BUYER_ROLEID);
	    leadHistoryVO.setCreatedBy(createdBy);
	    leadHistoryVO.setModifiedBy(modifiedBy);
	    leadHistoryVO.setEnitityId(leadContxt.getVendBuyerResId());
	    
	    if(addOrRevoke == SHOP_YOUR_WAY_REWARD_POINDS_ADD){
	    	logger.info("Add points : " + pointsToAddOrRevoke);
	    	desc = APPEND_STR_PLUS + pointsToAddOrRevoke + SPACE + REASON + reasonDesc + SPACE + REWARD_POINT_COMMENTS + comments;
	    	logger.info("Description : " + desc);
	    	leadHistoryVO.setDescription(desc);
	    	buyerLeadManagementService.insertShowYourWayRewardsHistoryForLeadMember(leadHistoryVO);
	    	points = points + pointsToAddOrRevoke;
	    	logger.info("Points after adding : " + points);
	    	buyerLeadManagementService.addOrRevokeShopYourWayPoints(points, slLeadId, custMemberShipId, modifiedBy);
	    }else if(addOrRevoke == SHOP_YOUR_WAY_REWARD_POINDS_REVOKE){
	    	if( (points - pointsToAddOrRevoke ) < 0){
	    		//Set the error "Points cannot be Revoked"
	    		logger.info("Points cannot be reduced");
	    		error = new SOWError(null, "Do not have sufficient points to revoke", "ID");
	    	}else{
	    		logger.info("Points to be subtracted : " + pointsToAddOrRevoke);
	    		desc = APPEND_STR_MINUS  + pointsToAddOrRevoke + SPACE + REASON + reasonDesc + SPACE + REWARD_POINT_COMMENTS + comments;
	    		logger.info("Description : " + desc);
	    		leadHistoryVO.setDescription(desc);
	    		//Log into histort table
	    		buyerLeadManagementService.insertShowYourWayRewardsHistoryForLeadMember(leadHistoryVO);
	    		//Update the points
	    		points = points - pointsToAddOrRevoke;
	    		logger.info("Points after subtracting : " + points);
	    		buyerLeadManagementService.addOrRevokeShopYourWayPoints(points, slLeadId, custMemberShipId, modifiedBy);
	    	}
	    }
		return "json";
	}
	
	public String updateRewardPoints()
	{
		logger.info("updateRewardPoints method");
		String reason   = SHOP_YOUR_WAY_REWARD_POINDS_ACTION_REASON; 
		String actionId = SHOP_YOUR_WAY_REWARD_POINDS_ID_STR;
		
		//Need to get the leadId from the request
		String leadId = (String)getRequest().getParameter("leadId");
		logger.info("Lead Id from request  : " + leadId);
		
		String custMemberShipId = "";
		
		if(null != getRequest().getParameter("membershipId")){
			custMemberShipId = (String)getRequest().getParameter("membershipId");
			logger.info("custMemberShipId : " + custMemberShipId);
		}
		
		getSession().removeAttribute("updateRewardPointsReasons");
		getSession().removeAttribute("updateRewardPointslst");
		getSession().removeAttribute("historylst");
		getSession().removeAttribute("currentSYWPoints");
		
		List<String> updateRewardPointsReasons=buyerLeadManagementService.getLeadReasons(reason);
		logger.info("updateRewardPointsReasons Size : " + updateRewardPointsReasons.size());
		getSession().setAttribute("updateRewardPointsReasons",updateRewardPointsReasons);
		
		Map<String,String> updateRewardPointslst=buyerLeadManagementService.getRewardPoints();
		
		TreeMap updateRewardPointslsttm = new TreeMap(); 
		updateRewardPointslsttm.putAll(updateRewardPointslst);
		
		logger.info("updateRewardPointslst Size : " + updateRewardPointslst.size());
		getSession().setAttribute("updateRewardPointslst",updateRewardPointslsttm);
		
		//Lead history while shop your points are updated
		
		List<SLLeadHistoryVO> historyVOlst = buyerLeadManagementService.getBuyerLeadHistory(leadId, actionId);
		logger.info("historyVOlst Size : " + historyVOlst.size());
		getSession().setAttribute("historylst",historyVOlst);
		
		//Get the Current points from DB 
	    
		 if(!StringUtils.isBlank(custMemberShipId)){
				//Get the Current points from DB 
		    	Integer points = buyerLeadManagementService.selectShopYourWayPointsForLeadMember(leadId,custMemberShipId);
		    	if(null == points){
		    		points = 0 ;
		    	}
			    logger.info("currentSYWPoints  : " + points);
			    getSession().setAttribute("currentSYWPoints",points);
			} 
		    
	    
		return "success";
	}
	
	public String addNoteLead()
	{
		List<String> leadNoteCategory=buyerLeadManagementService.getBuyerLeadNoteCategory();
		getSession().setAttribute("leadCategory",leadNoteCategory);
		return "success";
	}

	public String loadBuyerLeadManagementTabInformation() {
		 String roleType = this._commonCriteria.getRoleType();
        if (NEWCO_ADMIN.equals(roleType)) {
				Integer roleId = this._commonCriteria.getSecurityContext()
						.getRoleId();
				if (roleId != null && roleId.equals(BUYER_ROLEID)) {
					roleType = BUYER;
				}
			}
        getSession().setAttribute("roleType",this._commonCriteria.getSecurityContext().getRoleId());
        String buyerLeadManagementPermissionInd=(String) getSession().getAttribute("buyerLeadManagementPermission");
			if(roleType.equalsIgnoreCase(BUYER) && buyerLeadManagementPermissionInd.equalsIgnoreCase("true"))
			{
					List<LeadInfoVO> leadInfoDetails=new  ArrayList<LeadInfoVO>();
				   Integer buyerId = this._commonCriteria.getCompanyId();
				   BuyerLeadManagementCriteriaVO buyerLeadManagementCriteriaVO= new BuyerLeadManagementCriteriaVO();
				   buyerLeadManagementCriteriaVO=buyerLeadManagementCriteria(buyerId);
				   leadInfoDetails=fetchBuyerLeadDetailsWithDataTable(buyerLeadManagementCriteriaVO);
				   getSession().setAttribute("buyerLeadDetails", leadInfoDetails);
//						   if(null!=leadInfoDetails && leadInfoDetails.size()>0)
//						   {
//							loadBuyerLeadManagementSearchCriteriaData(buyerId);
//						   }
						  
		          
			}
			return "json";
          
	}
	public List<LeadInfoVO> fetchBuyerLeadDetailsWithDataTable(BuyerLeadManagementCriteriaVO buyerLeadManagementCriteriaVO)
	{
		List<LeadInfoVO> leadInfoDetails=new  ArrayList<LeadInfoVO>();
		iTotalRecords=buyerLeadManagementService.getBuyerLeadManagementCount(buyerLeadManagementCriteriaVO).toString();
		leadInfoDetails=buyerLeadManagementService.getBuyerLeadManagementDetails(buyerLeadManagementCriteriaVO);
		
		
		iTotalDisplayRecords=iTotalRecords;
		if(null!=leadInfoDetails){
		aaData=new String[leadInfoDetails.size()][7];
		int count=0;
		for(LeadInfoVO  leadInfoVO:leadInfoDetails){
			String data[]=new String[8];
			data[0]="";
			data[1]="";
			data[2]="";
			data[3]="";
			data[4]="";
			data[5]="";
			data[6]="";
			data[0]=leadInfoVO.getFirmStatus();
			data[1]=leadInfoVO.getSlLeadId();
			//SL-20893 Fetching provider firm in leads search results
			if(null!=leadInfoVO.getFirmName())
			{
			//data[2]=leadInfoVO.getFirmName();
			data[2]="<div style='width:150px; word-wrap:break-word'>" +leadInfoVO.getFirmName()+ "</div>";
			}
			else
			{
			data[2]="   -   ";	
			}
			data[3]="<a  href='javascript:void(0);' onclick='javascript:leadProfileDetails(\""+leadInfoVO.getSlLeadId()+"\");'>"+leadInfoVO.getDescription()+"</a>";
			data[4]=leadInfoVO.getCustomerName();
			String usFormattedPhoneNo="";
			if(null!=leadInfoVO.getPhoneNumber())
			{
				usFormattedPhoneNo=SOPDFUtils.formatPhoneNumber(leadInfoVO.getPhoneNumber());
			}
			data[5]=usFormattedPhoneNo;
			data[6]=leadInfoVO.getLocation();
			if(null!=leadInfoVO.getCreatedDate())
			{
				SimpleDateFormat SDF = new SimpleDateFormat("MM/dd/yyyy");
			    String dateString = SDF.format(leadInfoVO.getCreatedDate());
			    //String aligner = "<span style='margin-left:-5px;'>" +dateString+ "</span>";
			    //String viewDetails="<input class='buyerLeadDetailsButton' type='submit' value='View Details' onclick='javascript:leadProfileDetails(\""+leadInfoVO.getSlLeadId()+"\");'></input>";
			    //data[7]=aligner+viewDetails;
			    data[7]=dateString;
			}
			
			aaData[count]=data;
			count=count+1;
		}
		buyerLeadManagementTabDTO.setAaData(aaData);
		buyerLeadManagementTabDTO.setiTotalDisplayRecords(iTotalDisplayRecords);
		buyerLeadManagementTabDTO.setiTotalRecords(iTotalRecords);
		buyerLeadManagementTabDTO.setsEcho(sEcho);
		getRequest().setAttribute("count",count);	
	}
	return leadInfoDetails;
	}
	public BuyerLeadManagementCriteriaVO buyerLeadManagementCriteria(Integer buyerId)
	{
		BuyerLeadManagementCriteriaVO buyerLeadManagementCriteriaVO= new BuyerLeadManagementCriteriaVO();
		buyerLeadManagementCriteriaVO.setBuyerId(buyerId);
		String searching="";
		String searchCriteriaId="";
		if(getRequest().getParameter("searching")!=null && StringUtils.isNotEmpty((String)getRequest().getParameter("searching"))){
			searching = (String)getRequest().getParameter("searching");
		}
		getRequest().setAttribute("searching",searching);	
		// to handle server side pagination.
		int startIndex=0;
		Integer numberOfRecords=0;
		 //String sortColumnName="";
		 String searchColumnName="";
		 //String sortOrder="";
		 String sSearch="";
		if(getRequest().getParameter("iDisplayStart")!=null && StringUtils.isNotEmpty((String)getRequest().getParameter("iDisplayStart"))){
			startIndex = Integer.parseInt((String)getRequest().getParameter("iDisplayStart"));
		}
		
		if(getRequest().getParameter("iDisplayLength")!=null && StringUtils.isNotEmpty((String)getRequest().getParameter("iDisplayLength"))){
			numberOfRecords = Integer.parseInt((String)getRequest().getParameter("iDisplayLength"));
		}
		HttpServletRequest request=getRequest();
		if(getRequest().getParameter("sSearch_1")!=null && StringUtils.isNotEmpty((String)getRequest().getParameter("sSearch_1"))){
		 searchCriteriaId=(String)getRequest().getParameter("sSearch_1");
		 		if(searchCriteriaId.equals("1"))
		 		{
		 			searchColumnName="phoneNumber";
		 		}else if(searchCriteriaId.equals("2"))
		 		{
		 			searchColumnName="lastName";
		 		}else if(searchCriteriaId.equals("3"))
		 		{
		 			searchColumnName="firstName";
		 		}else if(searchCriteriaId.equals("4"))
		 		{
		 			searchColumnName="leadId";
		 		}else if(searchCriteriaId.equals("5"))
		 		{
		 			searchColumnName="providerFirmId";
		 		}else if(searchCriteriaId.equals("6"))
		 		{
		 			searchColumnName="zip";
		 		}else if(searchCriteriaId.equals("7"))
		 		{
		 			searchColumnName="state";
		 		}else if(searchCriteriaId.equals("9"))
		 		{
		 			searchColumnName="projectType";
		 		}else if(searchCriteriaId.equals("10"))
		 		{
		 			searchColumnName="firmStatus";
		 		}else if(searchCriteriaId.equals("11"))
		 		{
		 			searchColumnName="lmsLeadId";
		 		}
		 		//sortColumnName="createdDate";
		 		//sortOrder="desc";		 		
		}
		
		
				
		/*if(getRequest().getParameter("iSortCol_0")!=null && StringUtils.isNotEmpty((String)getRequest().getParameter("iSortCol_0"))){
			String sortColumnId =(String)getRequest().getParameter("iSortCol_0");
			
				if(sortColumnId.equals("0")){
						sortColumnName="firmStatus";
				}else if(sortColumnId.equals("1")){
					sortColumnName="leadId";
				}else if(sortColumnId.equals("2")){
					sortColumnName="lmsLeadId";
				}else if(sortColumnId.equals("3")){
					sortColumnName="title";
				}else if(sortColumnId.equals("4")){
					sortColumnName="customerName";
				}else if(sortColumnId.equals("5")){
					sortColumnName="customerPhone";
				}else if(sortColumnId.equals("6")){
					sortColumnName="location";
				}else if(sortColumnId.equals("7")){
					sortColumnName="createdDate";
				}	
		}
		if(getRequest().getParameter("sSortDir_0")!=null && StringUtils.isNotEmpty((String)getRequest().getParameter("sSortDir_0"))){
			sortOrder = (String)getRequest().getParameter("sSortDir_0");
		}*/
	
		if(getRequest().getParameter("sSearch")!=null && StringUtils.isNotEmpty((String)getRequest().getParameter("sSearch"))){
			sSearch = (String)getRequest().getParameter("sSearch");
		}
		
		//SL_20893: To add second sort order
		StringBuffer orderByString = sortingCriteria(searchCriteriaId, sSearch);
		if (orderByString.toString().contains(NewServiceConstants.VENDOR_ID)){
			buyerLeadManagementCriteriaVO.setSortFirm(NewServiceConstants.SORT_FIRM);
		}
		
		if(searchCriteriaId.equals("8"))
				{
//				 Timestamp startDate=null;
//				 Timestamp completeDate=null;
			     String[] dateString=sSearch.split(",");
			     if(dateString.length>1)
			     {
			     searchColumnName="dateRange";
			     String start=dateString[0];
			     String fromFormatedDate = getTimeStampFromStr(start,"MM/dd/yyyy", "yyyy-MM-dd");
			     fromFormatedDate=fromFormatedDate+" 00:00:00";
//			     startDate.valueOf(start);
			     buyerLeadManagementCriteriaVO.setStartDate(fromFormatedDate);
			     String complete=dateString[1];
			     String toFormatedDate = getTimeStampFromStr(complete,"MM/dd/yyyy", "yyyy-MM-dd");
			     toFormatedDate=toFormatedDate+" 23:59:00";
//			     completeDate.valueOf(complete);
			     buyerLeadManagementCriteriaVO.setCompetedDate(toFormatedDate);
			     }
			     else
			     {
			    	 searchColumnName="dateRange";
			    	 String start=dateString[0];
			    	 String fromFormatedDate = getTimeStampFromStr(start,"MM/dd/yyyy", "yyyy-MM-dd");
			    	 String fromDate=fromFormatedDate+" 00:00:00";
			    	 String toDate=fromFormatedDate+" 23:59:00";
//				     startDate.valueOf(start);
				     buyerLeadManagementCriteriaVO.setStartDate(fromDate);
				     buyerLeadManagementCriteriaVO.setCompetedDate(toDate);
			     }
//			     sortColumnName="createdDate";
//			 	 sortOrder="desc";
				}
		if(searchCriteriaId.equals("filter"))
		{
			searchColumnName="filter";
			buyerLeadManagementCriteriaVO.setFilterSearchId(sSearch);
			//sortColumnName="createdDate";
	 		//sortOrder="desc";
		}
		if(getRequest().getParameter("sEcho")!=null && StringUtils.isNotEmpty((String)getRequest().getParameter("sEcho"))){
			sEcho = Integer.parseInt(getRequest().getParameter("sEcho")) ;
		}
				
		buyerLeadManagementCriteriaVO.setOrderByString(orderByString.toString());
		//buyerLeadManagementCriteriaVO.setSortColumnName(sortColumnName);
		//buyerLeadManagementCriteriaVO.setSortOrder(sortOrder);
		buyerLeadManagementCriteriaVO.setsSearch(sSearch);
		//buyerLeadManagementCriteriaVO.setSortColumnName(sortColumnName);
		buyerLeadManagementCriteriaVO.setStartIndex(startIndex);
		buyerLeadManagementCriteriaVO.setNumberOfRecords(numberOfRecords);
		buyerLeadManagementCriteriaVO.setSearchColumnName(searchColumnName);
	    return buyerLeadManagementCriteriaVO;
		}
	
	public void loadBuyerLeadManagementSearchCriteriaData(Integer buyerId)
	{
		List<BuyerLeadLookupVO> buyerLeadStates=new ArrayList<BuyerLeadLookupVO>();
		buyerLeadStates=buyerLeadManagementService.loadStates();
		getSession().setAttribute("buyerLeadStates", buyerLeadStates);
	}
	protected void createCommonServiceOrderCriteria() {
		SecurityContext securityContext = (SecurityContext) getSession().getAttribute(SECURITY_KEY);
		boolean isLoggedIn=false;
		if (securityContext == null) {
			getSession().setAttribute(IS_LOGGED_IN, Boolean.FALSE);
			isLoggedIn = false;
			return;
		} else {
			getSession().setAttribute(IS_LOGGED_IN, Boolean.TRUE);
			isLoggedIn = true;
		}
		LoginCredentialVO lvo = securityContext.getRoles();
		lvo.setVendBuyerResId(securityContext.getVendBuyerResId());
		if (lvo == null) {
			// TODO somebody handle this better
			lvo = new LoginCredentialVO();
		}

		_commonCriteria = new ServiceOrdersCriteria();
		_commonCriteria.setCompanyId(securityContext.getCompanyId());
		if (lvo.getVendBuyerResId() != null && lvo.getVendBuyerResId().intValue() == -1) {
			// TODO:: Populate vendor resource id upon login when the schema is
			// updated.
			_commonCriteria.setVendBuyerResId(securityContext.getVendBuyerResId());
		//	_commonCriteria.setVendBuyerResId(9999); //temporarily for testing
		} else {
			_commonCriteria.setVendBuyerResId(lvo.getVendBuyerResId());
		}

		if (securityContext.isSlAdminInd()){
			_commonCriteria.setRoleId(securityContext.getRoleId());
			_commonCriteria.setTheUserName(securityContext.getUsername());
		}else{
			_commonCriteria.setRoleId(lvo.getRoleId());
			_commonCriteria.setTheUserName(lvo.getUsername());
		}

		_commonCriteria.setFName(lvo.getFirstName());
		_commonCriteria.setLName(lvo.getLastName());
		_commonCriteria.setRoleType(securityContext.getRole());
		_commonCriteria.setToday(Boolean.TRUE);
		securityContext.setBuyerLoggedInd(securityContext.isBuyer());
		_commonCriteria.setSecurityContext(securityContext);

		// Set simple buyer flag in session
		if (isLoggedIn && _commonCriteria.getSecurityContext().getRoleId().intValue() == 5) {
			getSession().setAttribute(IS_SIMPLE_BUYER, Boolean.TRUE);
		} else {
			getSession().setAttribute(IS_SIMPLE_BUYER, Boolean.FALSE);
		}
		if(_commonCriteria.getSecurityContext().getRoles()!=null){
			if (isLoggedIn && _commonCriteria.getSecurityContext().getRoles().getRoleName().equals(OrderConstants.NEWCO_ADMIN)) {
				getSession().setAttribute(IS_ADMIN, Boolean.TRUE);
			} else {
				getSession().setAttribute(IS_ADMIN, Boolean.FALSE);
			}
		}
		getSession().setAttribute(OrderConstants.SERVICE_ORDER_CRITERIA_KEY, _commonCriteria);

	}
	
	
	
	
	/**
	 * Controller method that will be invoked when buyer clicks Submit button in
	 * Buyer Lead management- Cancel Lead pop up.
	 * <p>
	 * It calls API to cancel Lead. Error/warning messages are populated.
	 * */
	public String buyerLeadCancellation() {
		SecurityContext leadContxt = (SecurityContext) getSession()
				.getAttribute(SECURITY_CONTEXT);
		SOWError error = null;
		buyerLeadManagementTabDTO = getModel();
		String buyer_name = "";
		if(leadContxt.isAdopted()){
			
			buyer_name = leadContxt.getSlAdminFName() + " "+ leadContxt.getSlAdminLName();
		}
		else{
			buyer_name = leadContxt.getUsername();
		}
		int roleId = leadContxt.getRoleId();

		/* Call validation method */
		
		if(buyerLeadManagementTabDTO.getProviderList().size()==0)
		{
			buyerLeadManagementTabDTO.setFailureIndicator(1);
			buyerLeadManagementTabDTO
			.setReturnResult("No Providers found for the Lead");
			
			return "json";
		}
		error = validateCancelLeadFRequest();
		if (null != error) {
			if (!omErrors.contains(error)) {
				omErrors.add(error);
				buyerLeadManagementTabDTO.setFailureIndicator(1);
				buyerLeadManagementTabDTO
						.setReturnResult("Validation Failed..");

			}
			return "json";
		}
		CancelLeadRequest cancelLeadRequest = new CancelLeadRequest();

		/* Format the resource Ids */
		List<Integer> resourceIds = buyerLeadManagementTabDTO.getProviderList();

		String providers = formatSelectedProviderIds(resourceIds);

		// Reason code are set in session while loading the Reject Order Pop up.

		cancelLeadRequest.setComments(buyerLeadManagementTabDTO.getComments());
		cancelLeadRequest.setLeadId(buyerLeadManagementTabDTO.getLeadId());
		cancelLeadRequest.setProviders(providers);
		cancelLeadRequest.setReasonCode(buyerLeadManagementTabDTO
				.getReasonCode());
		cancelLeadRequest.setRevokePointsIndicator(buyerLeadManagementTabDTO
				.isRevokePointsInd());

		List<ProviderInfoVO> providerMatchedLead = buyerLeadManagementService
				.getBuyerLeadManagementProviderInfo(cancelLeadRequest
						.getLeadId());
		List<ProviderInfoVO> providerCancelledLead = buyerLeadManagementService
				.getBuyerLeadManagementCancelledProviderInfo(cancelLeadRequest.getLeadId());
		Integer cancelListSize=resourceIds.size()+providerCancelledLead.size();
		
		if (providerMatchedLead.size() == cancelListSize) {
			cancelLeadRequest.setChkAllProviderInd(true);
		} else {
			cancelLeadRequest.setChkAllProviderInd(buyerLeadManagementTabDTO
					.isChkAllProviderInd());
		}
		cancelLeadRequest.setVendorBuyerName(buyer_name);
		cancelLeadRequest.setRoleId(roleId);
		cancelLeadRequest.setCancelInitiatedBy(BUYER_INITIATED);
		// lead_history parameter setting 
		String createdBy="";
		if(leadContxt.isAdopted())
		{
			createdBy=leadContxt.getSlAdminFName()+" "+leadContxt.getSlAdminLName();
		}
		else
		{
			createdBy=this._commonCriteria.getFName()+" "+this._commonCriteria.getLName();
		}
		cancelLeadRequest.setCreatedBy(createdBy);
		String modifiedBy = leadContxt.getUsername();
		cancelLeadRequest.setModifiedBy(modifiedBy);
		cancelLeadRequest.setEntityId(leadContxt.getVendBuyerResId());
		/* Calls Cancel API */
		CancelLeadResponse cancelLeadResponse = buyerLeadClient
				.cancelLead(cancelLeadRequest);
		if (null == cancelLeadResponse) {
			error = new SOWError(null, "API call failed", "ID");
			buyerLeadManagementTabDTO.setFailureIndicator(1);
			buyerLeadManagementTabDTO
			.setReturnResult("Lead could not be cancelled..");
			return "json";

		} 
		
		if(cancelLeadResponse.getResults().getResult().get(0).getCode().equals("0002"))
		{
			buyerLeadManagementTabDTO.setFailureIndicator(1);
			buyerLeadManagementTabDTO
			.setReturnResult("Lead could not be cancelled..");
			return "json";
		}
		
		
		if (buyerLeadManagementTabDTO.isChkAllProviderInd()) {
				buyerLeadManagementTabDTO.setFailureIndicator(0);
				buyerLeadManagementTabDTO
						.setReturnResult("Lead cancelled succesfully..");
			} else {
				buyerLeadManagementTabDTO.setFailureIndicator(0);
				buyerLeadManagementTabDTO
						.setReturnResult("Lead cancelled for selected Providers..");

			}
			buyerLeadManagementTabDTO.setLeadId(cancelLeadRequest.getLeadId());

			return "json";
		
		

		
		/*		else if (null != cancelLeadResponse.getResults().getError()) {
			List<ErrorResult> errorResults = cancelLeadResponse.getResults()
					.getError();
			for (ErrorResult result : errorResults) {
				logger.info(result.getMessage());
				omErrors.add(error);
				error = new SOWError(null, result.getMessage(), EMPTY_STR);
				buyerLeadManagementTabDTO
				.setReturnResult(result.getMessage());

			}
			return "json";
		}*/
		
	}

	
	public String formatSelectedProviderIds(List<Integer> rejResources){
		String checkedResources = "";
		for(Integer provider:rejResources)
		{
			if(provider!=null)
			{
				checkedResources+=provider+",";
			}
		}
		return checkedResources.substring(0,checkedResources.length()-1);
	}
	
	public String buyerLeadAddNote(){
		buyerLeadManagementTabDTO=getModel();
		String roleType = this._commonCriteria.getRoleType();
        if (NEWCO_ADMIN.equals(roleType)) {
			Integer roleId = this._commonCriteria.getSecurityContext().getRoleId();
			if (roleId != null && roleId.equals(BUYER_ROLEID)) {
				roleType = BUYER;
			}
		}
        String emailAlertToType="";
        String noteType="";
        String leadEmailTitle=(String)getRequest().getParameter("leadEmailTitle");
        Boolean emailSupport=buyerLeadManagementTabDTO.getCheckSupport();
        Boolean emailAllProviders=buyerLeadManagementTabDTO.getCheckAllProviders();
        Boolean emailAlertIndicator=buyerLeadManagementTabDTO.getEmailAlertInd();
        if(buyerLeadManagementTabDTO.getCheckPrivate()){
        	noteType="PRIVATE";
        }
        else{
        	noteType="PUBLIC";
        }
        if(emailSupport && emailAlertIndicator){
        	emailAlertToType="SLSUPPORT";
        	if(emailAllProviders && emailAlertIndicator && (noteType.equalsIgnoreCase("PUBLIC"))){
        		emailAlertToType="BOTH";
        	}
        }
        else if(emailAllProviders && emailAlertIndicator){
        	emailAlertToType="PROVIDERS";
        }
        if(!emailAlertIndicator){
        	emailAlertToType="NONE";
        }
        LeadNoteType leadNoteType=new LeadNoteType();
        EmailAlertType emailAlertType=new EmailAlertType();
        
		String leadId = buyerLeadManagementTabDTO.getLeadId();
		leadNoteType.setNoteBody(buyerLeadManagementTabDTO.getAddNoteMessage());
		leadNoteType.setNoteCategory(buyerLeadManagementTabDTO.getAddNoteSubject());
		leadNoteType.setNoteType(noteType);
		emailAlertType.setEmailAlertInd(emailAlertIndicator);
		emailAlertType.setEmailAlertTos(emailAlertToType);
		
		SecurityContext leadContxt = (SecurityContext) getSession().getAttribute(SECURITY_CONTEXT);
		String createdBy="";
		if(leadContxt.isAdopted())
		{
			createdBy=leadContxt.getSlAdminFName()+" "+leadContxt.getSlAdminLName();
		}
		else
		{
			createdBy=this._commonCriteria.getFName()+" "+this._commonCriteria.getLName();
		}
	    SOWError error = null;
	    if(null!=roleType && roleType.equalsIgnoreCase("buyer"))
	    {
	    	roleType="Buyer";	
	    }
	    else if(null!=roleType && roleType.equalsIgnoreCase("provider"))
	    {
	    	roleType="Provider";
	    }
	    LeadAddNoteRequest leadAddNoteRequest = new LeadAddNoteRequest();
	    leadAddNoteRequest.setLeadId(leadId);
	    leadAddNoteRequest.setRole(roleType);
	    leadAddNoteRequest.setVendorBuyerId(leadContxt.getCompanyId());
	    leadAddNoteRequest.setVendorBuyerResourceId(leadContxt.getVendBuyerResId());
	    leadAddNoteRequest.setLeadNote(leadNoteType);
	    leadAddNoteRequest.setEmailAlert(emailAlertType);
	    leadAddNoteRequest.setLeadEmailTitle(leadEmailTitle);
	    leadAddNoteRequest.setLeadEmailAuthor(this._commonCriteria.getFName()+" "+ this._commonCriteria.getLName());
	    //lead name and lead phone number will be customer name and phone in the add note email template
	    leadAddNoteRequest.setLeadName(buyerLeadManagementTabDTO.getFirstName()+" "+buyerLeadManagementTabDTO.getLastName());
	    leadAddNoteRequest.setLeadPhone(buyerLeadManagementTabDTO.getPhoneNo());

	        /* Calls Reject SO API */
	    LeadAddNoteResponse leadAddNoteResponse = buyerLeadClient.getResponseForLeadAddNote(leadAddNoteRequest);
	        if (null == leadAddNoteResponse) {
	            error = new SOWError(null, "API call failed", "ID");
	        } else if (null != leadAddNoteResponse.getResults().getError()) {
	            List<ErrorResult> errorResults = leadAddNoteResponse.getResults()
	                    .getError();
	            for (ErrorResult result : errorResults) {
	                logger.info(result.getMessage());
	                omErrors.add(error);
	                error = new SOWError(null, result.getMessage(), EMPTY_STR);

	            }
	            return "json";
	        }
	       
            String desc = "";
            String modifiedBy = leadContxt.getUsername();
            desc = BUYER_NOTE_TEXT;
            LeadHistoryVO leadHistoryVO= new LeadHistoryVO();  
            //TODO: Need to add a record in lu_lead_action table
            int actionId = BUYER_LEAD_ADD_NOTE;    
            leadHistoryVO.setActionId(actionId);     
            leadHistoryVO.setSlLeadId(leadId);     
            leadHistoryVO.setRoleId(BUYER_ROLEID);   
            leadHistoryVO.setCreatedBy(createdBy);  
            leadHistoryVO.setModifiedBy(modifiedBy);
            leadHistoryVO.setDescription(desc);
            leadHistoryVO.setEnitityId(leadContxt.getVendBuyerResId());
            buyerLeadManagementService.insertShowYourWayRewardsHistoryForLeadMember(leadHistoryVO);
            
	        return "json";
	    }

	/**
	 * Method which validates Reject Service Order Request parameters.
	 * 
	 * @return SOWError : Populated with error messages.
	 *         <p>
	 *         null returned when no error/warning
	 * */
	private SOWError validateCancelLeadFRequest() {
		SOWError error = null;
		buyerLeadManagementTabDTO=getModel();
		String resourceIds = buyerLeadManagementTabDTO.getProviderList().toString();
		String reasonCodeId = buyerLeadManagementTabDTO.getReasonCode().toString();
		// Resources should be selected
		if (StringUtils.isBlank(resourceIds)||buyerLeadManagementTabDTO.getProviderList().size()==0) {
			error = new SOWError(null,
					"Please select 1 or more providers first", "pro_id");
			
		} else if (StringUtils.isBlank(reasonCodeId)
				|| "-1".equals(reasonCodeId)) {
			// Reason for rejection should be provided.
			error = new SOWError(null, "Please select reason to reject",
					"pro_id");
		}
		return error;
	}
	
	public Integer getBuyerLeadCount()
	{
		try {
			String defaultCount=applicationProperties.getPropertyValue(OrderConstants.BUYER_LEAD_MANAGEMENT_DEFAULT_LEAD_COUNT);
			Integer defaultLeadCount=Integer.parseInt(defaultCount);
			return defaultLeadCount;
		} catch (DataNotFoundException e) {
			logger.info("Exception thrown while getting default count for buyer lead management");
			e.printStackTrace();
			return null;
		}
		
	}

	public String getAttachmentDetails(){
		String leadId = (String)getRequest().getParameter("leadId");
		List<BuyerLeadAttachmentVO> attachments = buyerLeadManagementService.getAttachmentDetails(leadId);
		getSession().setAttribute("attachments", attachments);
		return SUCCESS;
	}
	
	public String viewDocument(){
		downloadDocument();		
		return SUCCESS;
	}
	
	private String downloadDocument(){
		Integer documentId = null;
		try {
			BuyerLeadAttachmentVO document = new BuyerLeadAttachmentVO();
			
			if(null != getRequest().getParameter("docId")){
				documentId = Integer.parseInt(getRequest().getParameter("docId"));
			}
        
        	//Retrieving the Document
			if (null != documentId){
	        	document = buyerLeadManagementService.retrieveDocumentByDocumentId(documentId);
	
	        	SecurityChecker sc = new SecurityChecker();
	        	String uploadType = sc.securityCheck(document.getFormat());
	        	getResponse().setContentType(uploadType);
	        	
	        	String docName = sc.fileNameCheck(document.getFileName());
	            String header = "attachment;filename=\""
	                          + docName + "\"";
	            getResponse().setHeader("Content-Disposition", header);
	            getResponse().setHeader("Cache-Control", "private"); // HTTP 1.1.
                getResponse().setHeader("Pragma", "token"); // HTTP 1.0.
	            InputStream in = new ByteArrayInputStream(document.getBlobBytes());
	
	            ServletOutputStream outs = getResponse().getOutputStream();
	
	            int bit = 256;
	
	            while ((bit) >= 0) {
	               bit = in.read();
	               outs.write(bit);
	            }
	
	            outs.flush();
	            outs.close();
	            in.close();
			}
			
			getAttachmentDetails();
			
      } catch (Exception e) {
    	  e.printStackTrace();
      }
		return SUCCESS;
	}
	
	private  String getTimeStampFromStr(String date, String format,
			String requiredFormat) {
		
		String strDate = date;
		Date dt1 = stringToDate(strDate, format);
		DateFormat formatter = new SimpleDateFormat(requiredFormat);
		String strFotrmated = " ";
		strFotrmated = formatter.format(dt1);
		return strFotrmated;
	}
	private  Date stringToDate(String strDate, String format) {
		DateFormat formatter;
		Date date = null;
		formatter = new SimpleDateFormat(format);
		try {
			date = (Date) formatter.parse(strDate);
		} catch (ParseException e) {
			//LOGGER.error(e);
		}
		return date;
	}
	public BuyerLeadManagementTabDTO getModel() {
		// TODO Auto-generated method stub
		return buyerLeadManagementTabDTO;
	}
	public BuyerLeadManagementTabDTO getBuyerLeadManagementTabDTO() {
		return buyerLeadManagementTabDTO;
	}
	public void setBuyerLeadManagementTabDTO(
			BuyerLeadManagementTabDTO buyerLeadManagementTabDTO) {
		this.buyerLeadManagementTabDTO = buyerLeadManagementTabDTO;
	}
	public void setModel(BuyerLeadManagementTabDTO buyerLeadManagementTabDTO) {
		this.buyerLeadManagementTabDTO = buyerLeadManagementTabDTO;
	}
	public BuyerLeadManagementService getBuyerLeadManagementService() {
		return buyerLeadManagementService;
	}
	public void setBuyerLeadManagementService(
			BuyerLeadManagementService buyerLeadManagementService) {
		this.buyerLeadManagementService = buyerLeadManagementService;
	}
	public Integer getsEcho() {
		return sEcho;
	}
	public void setsEcho(Integer sEcho) {
		this.sEcho = sEcho;
	}
	public String getiTotalRecords() {
		return iTotalRecords;
	}
	public void setiTotalRecords(String iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}
	public String getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}
	public void setiTotalDisplayRecords(String iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}
	public String[][] getAaData() {
		return aaData;
	}
	public void setAaData(String[][] aaData) {
		this.aaData = aaData;
	}
	public IApplicationProperties getApplicationProperties() {
		return applicationProperties;
	}
	public void setApplicationProperties(
			IApplicationProperties applicationProperties) {
		this.applicationProperties = applicationProperties;
	}

	 public LeadManagementRestClient getBuyerLeadClient() {
		return buyerLeadClient;
	}
	public void setBuyerLeadClient(LeadManagementRestClient buyerLeadClient) {
		this.buyerLeadClient = buyerLeadClient;
	}
	public List<SOWError> getOmErrors() {
			return omErrors;
		}
		public void setOmErrors(List<SOWError> omErrors) {
			this.omErrors = omErrors;
		}
	
		/**SL_20893: To add second sort order
		 * @param sSearch 
		 * @param searchCriteriaId 
		 * @return
		 */
		private StringBuffer sortingCriteria (String searchCriteriaId, String sSearch){
			String sortingCols = "";
			StringBuffer orderByString = new StringBuffer();
			String sortColumnName="";
			String sortDir ="";
		    
			/*if(searchCriteriaId.equals(NewServiceConstants.FILTER) && sSearch.equalsIgnoreCase(NewServiceConstants.TWO))
			{
					orderByString.append(NewServiceConstants.PHONE_NUMBER);
					orderByString.append(" ");
					orderByString.append(NewServiceConstants.DESC);
					orderByString.append(",");
			}*/
			
			if(getRequest().getParameter(NewServiceConstants.SORTING_COLS)!=null && StringUtils.isNotEmpty((String)getRequest().getParameter(NewServiceConstants.SORTING_COLS))){
				sortingCols = (String)getRequest().getParameter(NewServiceConstants.SORTING_COLS);
			}
			for (int index=0; index<Integer.parseInt(sortingCols); index++){
				if(getRequest().getParameter(NewServiceConstants.SORT_COL+index)!=null && StringUtils.isNotEmpty((String)getRequest().getParameter(NewServiceConstants.SORT_COL+index))){
					String sortColumnId =(String)getRequest().getParameter(NewServiceConstants.SORT_COL+index);
					
					if(sortColumnId.equals(NewServiceConstants.ZERO)){
						sortColumnName = NewServiceConstants.FIRM_STATUS;
					}else if(sortColumnId.equals(NewServiceConstants.ONE)){
						sortColumnName = NewServiceConstants.SL_LEAD_ID;
					}else if(sortColumnId.equals(NewServiceConstants.TWO)){
						sortColumnName = NewServiceConstants.VENDOR_ID;
					}else if(sortColumnId.equals(NewServiceConstants.THREE)){
						sortColumnName = NewServiceConstants.DESCRIPTION;
					}else if(sortColumnId.equals(NewServiceConstants.FOUR)){
						sortColumnName = NewServiceConstants.CUSTOMERNAME;
					}else if(sortColumnId.equals(NewServiceConstants.FIVE)){
						sortColumnName = NewServiceConstants.PHONE_NUMBER;
					}else if(sortColumnId.equals(NewServiceConstants.SIX)){
						sortColumnName = NewServiceConstants.LOCATION;
					}else if(sortColumnId.equals(NewServiceConstants.SEVEN)){
						sortColumnName = NewServiceConstants.CREATED_DATE;
					}	
				}
				if(getRequest().getParameter(NewServiceConstants.SORT_DIR+index)!=null && StringUtils.isNotEmpty((String)getRequest().getParameter(NewServiceConstants.SORT_DIR+index))){
					 sortDir = (String)getRequest().getParameter("sSortDir_"+index);
				}
				
				if(getRequest().getParameter(NewServiceConstants.SORT_COL+index)!=null && StringUtils.isNotEmpty((String)getRequest().getParameter(NewServiceConstants.SORT_COL+index))
						&& getRequest().getParameter(NewServiceConstants.SORT_DIR+index)!=null && StringUtils.isNotEmpty((String)getRequest().getParameter(NewServiceConstants.SORT_DIR+index))){
					if (index>0){
						orderByString.append(" ,");
					}
					orderByString.append(sortColumnName);
					orderByString.append(" ");
					orderByString.append(sortDir);
				}
			}
			return orderByString;
		}
}