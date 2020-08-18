package com.servicelive.spn.buyer.network;


import static com.servicelive.spn.constants.SPNActionConstants.NOT_LOGGED_IN;

import static com.servicelive.spn.constants.SPNActionConstants.NOT_LOGGED_IN_AS_BUYER;
import static com.servicelive.spn.constants.SPNActionConstants.ROLE_ID_PROVIDER;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.newco.marketplace.dto.vo.CryptographyVO;
import com.newco.marketplace.interfaces.MPConstants;
import com.newco.marketplace.utils.MoneyUtil;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.servicelive.domain.spn.detached.SPNCoverageDTO;
import com.servicelive.domain.spn.detached.SPNRoutingDTO;
import com.servicelive.domain.spn.detached.SPNSummaryFilterVO;
import com.servicelive.domain.spn.detached.SPNSummaryVO;
import com.servicelive.domain.spn.network.ExceptionsAndGracePeriodVO;
import com.servicelive.domain.spn.network.ExclusionsVO;
import com.servicelive.domain.spn.network.RequirementVO;
import com.servicelive.domain.spn.network.SPNComplianceVO;
import com.servicelive.domain.spn.network.SPNExclusionsVO;
import com.servicelive.domain.userprofile.User;
import com.servicelive.spn.buyer.common.SPNCreateAction;
import com.servicelive.spn.common.SPNBackendConstants;
import com.servicelive.spn.common.detached.ComplianceCriteriaVO;
import com.servicelive.spn.common.detached.NetworkHistoryVO;
import com.servicelive.spn.services.network.NetworkSummaryServices;
import com.servicelive.spn.common.util.CalendarUtil;
import com.servicelive.spn.common.util.Cryptography;
//import com.servicelive.spn.services.network.NightlyMemberMaintenanceService;
/**
 *
 * 
 *
 */

public class SPNMonitorNetworkAction extends SPNCreateAction implements Preparable, ModelDriven<SPNMonitorNetworkModel>
{
	private static final long serialVersionUID = 236289347500569580L;
	private SPNMonitorNetworkModel model = new SPNMonitorNetworkModel();
	private NetworkSummaryServices networkSummaryServices;
	/*private NightlyMemberMaintenanceService nightlyMemberMaintenanceService;

	public NightlyMemberMaintenanceService getNightlyMemberMaintenanceService() {
		return nightlyMemberMaintenanceService;
	}



	public void setNightlyMemberMaintenanceService(
			NightlyMemberMaintenanceService nightlyMemberMaintenanceService) {
		this.nightlyMemberMaintenanceService = nightlyMemberMaintenanceService;
	}
	 */
	private Integer SPN_MAX_ROWS_DEFAULT= Integer.valueOf(30);
	private  final String NETWORK_ID = "networkid";
	private static String SAVED_DTO = "savedRoutingDTO";
	private static String FILTERED_COVERAGE_DTO = "filteredCoverageDTO";
	private Integer networkCountLimit = null;
	private Integer networkCountAll = SPN_MAX_ROWS_DEFAULT;
	private Integer completeSPNCount;
	private Integer incompleteSPNCount;
	private Cryptography cryptography;
	private String expIncludedInd = "false";
	private String spnId;
	private Integer sEcho=1;
	private String iTotalRecords="2";
	private String iTotalDisplayRecords="2";
	private String aaData[][];
	
	
	
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



	





	public String getExpIncludedInd() {
		return expIncludedInd;
	}



	public void setExpIncludedInd(String expIncludedInd) {
		this.expIncludedInd = expIncludedInd;
	}



	public String getSpnId() {
		return spnId;
	}



	public void setSpnId(String spnId) {
		this.spnId = spnId;
	}



	/**
	 *
	 * @return String
	 */
	@Override
	public String display()
	{
		User loggedInUser = getLoggedInUser();
		model = getModel();


		if (loggedInUser == null)
		{
			return NOT_LOGGED_IN;
		}
		else if (loggedInUser.getRole() == null || loggedInUser.getRole().getId() == null || loggedInUser.getRole().getId().intValue() == ROLE_ID_PROVIDER.intValue())
		{
			return NOT_LOGGED_IN_AS_BUYER;
		}

		Integer buyerId2 = loggedInUser.getUserId();
		if (buyerId2 != null)
		{
			List<SPNSummaryVO> spnlist = new LinkedList<SPNSummaryVO>();
			try
			{	SPNSummaryFilterVO summaryFilterVO = new SPNSummaryFilterVO();
			summaryFilterVO.setBuyerId(buyerId2);
			setNetworkCountAll(networkSummaryServices.getCountOfSPNforBuyer(buyerId2));

			setCompleteSPNCount(networkSummaryServices.getCountOfCompleteSPNs(buyerId2));
			setIncompleteSPNCount(networkSummaryServices.getCountOfIncompleteSPNs(buyerId2));


			spnlist = networkSummaryServices.getListOfSPNSummaryForBuyer(summaryFilterVO, getNetworkCountLimit());

			if(spnlist!=null && (!spnlist.isEmpty()) &&("true").equals(this.expIncludedInd)){
				Integer spnId = 0;
				if(this.spnId!=null){
					spnId =Integer.parseInt(this.spnId);
				}
				for(SPNSummaryVO spnSummaryVO :  spnlist){if(null!=spnSummaryVO && spnSummaryVO.getSpnId()!=null && spnId!=null){
					if(spnId.intValue() == spnSummaryVO.getSpnId().intValue()){
						spnSummaryVO.setExceptionInd(1);
						break;
					}
				}
				}
			}
			model.setSpnSummary(spnlist);

			if(spnlist.size() > SPN_MAX_ROWS_DEFAULT.intValue())
				setNetworkCountLimit(SPN_MAX_ROWS_DEFAULT);
			else
				setNetworkCountLimit(null);

			if(getNetworkCountLimit() != null)
			{
				model.setSpnSummary(spnlist.subList(0, getNetworkCountLimit().intValue() ));
			}
			}
			catch (Exception e)
			{
				logger.info("Exception in display()"+e.getMessage());
				e.printStackTrace();
			}

		}
		else
		{
			model.setSpnSummary(new ArrayList<SPNSummaryVO>());
		}
		model.setMarketList(lookupService.getAllMarkets());
		model.setStateList(lookupService.findStatesNotBlackedOut());

		initTabDisplay();
		if(getRequest().getSession().getAttribute("rememberUserId") != null &&
				getRequest().getSession().getAttribute("rememberUserId").toString().equalsIgnoreCase("true")){
			CryptographyVO cryptographyVO = new CryptographyVO();
			cryptographyVO.setInput(loggedInUser.getUsername());
			cryptographyVO.setKAlias(MPConstants.ENCRYPTION_KEY);		
			cryptographyVO = cryptography.encryptKey(cryptographyVO);
			if(cryptographyVO != null && 
					null != cryptographyVO.getResponse() ){
				Cookie cookie =  new Cookie("spn_username",cryptographyVO.getResponse());
				ServletActionContext.getResponse().addCookie(cookie);		

			}						
		}
		getRequest().setAttribute("expIncludedInd", this.expIncludedInd);
		getRequest().setAttribute("spnId", this.spnId);
		return SUCCESS;
	}



	@Override
	public void prepare() throws Exception
	{
		setModel(getModel());
	}

	public String viewNetworkTableAjax() throws Exception
	{
		String limitRowsStr = getRequest().getParameter("limitRows");
		//Integer marketId = model.getSpnSummaryFilterVO().getMarketId();
		//String stateCd = model.getSpnSummaryFilterVO().getStateCd();
		List<String> stateCds = new ArrayList<String>();
		List<Integer> marketIds = new ArrayList<Integer>();
		if(null != model.getSpnSummaryFilterVO()){
			stateCds = model.getSpnSummaryFilterVO().getStateCds();
			marketIds = model.getSpnSummaryFilterVO().getMarketIds();
		}

		//setting the input states
		List<String> states = new ArrayList<String>();
		if(null != stateCds && !stateCds.isEmpty()){
			for(String stateCd : stateCds){
				if(StringUtils.isNotBlank(stateCd)){
					states.add(stateCd);
				}
			}
		}

		//setting the input markets
		List<Integer> markets = new ArrayList<Integer>();
		if(null != marketIds && !marketIds.isEmpty()){
			for(Integer marketId : marketIds){
				if(null != marketId){
					markets.add(marketId);
				}
			}
		}


		if(limitRowsStr != null)
		{
			setNetworkCountLimit(SPN_MAX_ROWS_DEFAULT);
		}
		else
		{
			setNetworkCountLimit(null);
		}
		User loggedInUser = getLoggedInUser();
		Integer buyerId2 = loggedInUser.getUserId();
		if (buyerId2 != null)
		{
			List<SPNSummaryVO> spnlist = new LinkedList<SPNSummaryVO>();
			try
			{	SPNSummaryFilterVO summaryFilterVO = new SPNSummaryFilterVO();
			summaryFilterVO.setBuyerId(buyerId2);
			//summaryFilterVO.setMarketId(marketId);
			//summaryFilterVO.setStateCd(stateCd);
			summaryFilterVO.setMarketIds(markets);
			summaryFilterVO.setStateCds(states);
			if((summaryFilterVO.getBuyerId()!=null && summaryFilterVO.getBuyerId()!=-1) || 
					(summaryFilterVO.getMarketIds()!=null && !summaryFilterVO.getMarketIds().isEmpty())) 
			{
				summaryFilterVO.setAddJoinInd(new Integer(1));
			}
			setNetworkCountAll(networkSummaryServices.getCountOfSPNforBuyer(buyerId2));				
			spnlist = networkSummaryServices.getListOfSPNSummaryForBuyer(summaryFilterVO, getNetworkCountLimit());

			getModel().setSpnSummary(spnlist);
			if(getNetworkCountLimit() != null)
			{
				model.setSpnSummary(spnlist.subList(0, getNetworkCountLimit().intValue() ));
			}
			}
			catch (Exception e)
			{
				logger.error(e);
				e.printStackTrace();
			}
		}
		else
		{
			getModel().setSpnSummary(new ArrayList<SPNSummaryVO>());
		}
		return SUCCESS;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public String viewExceptionsAjax() throws Exception
	{
		try {
			Integer networkId = 0;
			if (getRequest().getAttribute(NETWORK_ID) != null) {
				networkId = (Integer) getRequest().getAttribute(NETWORK_ID);
			} else if (getRequest().getParameter(NETWORK_ID) != null
					&& StringUtils.isNotEmpty((String) getRequest()
							.getParameter(NETWORK_ID))) {
				networkId = Integer.parseInt((String) getRequest().getParameter(
						NETWORK_ID));
				getRequest().setAttribute("spnId", networkId);
			}
			setSpnExceptionTypes();
			setGracePeriods();
			setExceptionStates();
			setExclusionLists(networkId);

		} catch (Exception e) {
			logger.info("Error Occured in loading exceptions:" + e.getMessage());
		}
		return SUCCESS;
	}


	private void setExclusionLists(Integer networkId) {
		try{
			ExclusionsVO exclusionsVO = new ExclusionsVO();
			exclusionsVO = networkSummaryServices.getSPNExceptions(networkId);
			if (exclusionsVO != null){
				if((exclusionsVO.getCompanyExclusions() != null && exclusionsVO
						.getCompanyExclusions().size() > 0) || (exclusionsVO
								.getResourceExclusions() != null && exclusionsVO
								.getResourceExclusions().size() > 0)) {
					for (SPNExclusionsVO vo : exclusionsVO.getCompanyExclusions()) {
						//to check whether two exceptions are applied

						mapExceptionsCount(vo,exclusionsVO.getCompanyExclusions());

					}
					for (SPNExclusionsVO vo : exclusionsVO.getResourceExclusions()) {
						//to check whether two exceptions are applied
						mapExceptionsCount(vo,exclusionsVO.getResourceExclusions());
					}
					if(exclusionsVO
							.getCompanyExclusions().size() == 0){
						exclusionsVO.setCompanyExclusions(null);
					}
					if(exclusionsVO
							.getResourceExclusions().size() == 0){
						exclusionsVO.setResourceExclusions(null);
					}
					getRequest().setAttribute("exclusionLists", exclusionsVO);
				}
			}
		}
		catch (SQLException e) {
			logger.info("Error Occured in setting Grace Periods:" + e.getMessage());
		}
	}



	/**
	 * method to set exception states
	 */
	private void setExceptionStates() {

		try{
			List<String> states = new ArrayList<String>();
			states = networkSummaryServices.getStates();
			if (states != null) {
				getRequest().setAttribute("exceptionStates", states);
			}
		}
		catch (SQLException e) {
			logger.info("Error Occured in setting Grace Periods:" + e.getMessage());
		}

	}



	/**
	 * method to set grace periods
	 */
	private void setGracePeriods() {
		try{
			List<ExceptionsAndGracePeriodVO> gracePeriods = new ArrayList<ExceptionsAndGracePeriodVO>();
			gracePeriods = networkSummaryServices.getGracePeriod();
			if (gracePeriods != null) {
				getRequest().setAttribute("gracePeriods", gracePeriods);
			}
		}
		catch (SQLException e) {
			logger.info("Error Occured in setting Grace Periods:" + e.getMessage());
		}
	}



	/**
	 * method to set SPN exception types in request
	 */
	private void setSpnExceptionTypes() {
		try {
			List<ExceptionsAndGracePeriodVO> spnExceptionTypes = new ArrayList<ExceptionsAndGracePeriodVO>();

			spnExceptionTypes = networkSummaryServices.getSpnExceptionTypes();

			if (spnExceptionTypes != null) {
				getRequest().setAttribute("exceptionTypes", spnExceptionTypes);
			}
		} catch (SQLException e) {
			logger.info("Error Occured in setting  Spn Exception Types:" + e.getMessage());
		}
	}



	/**
	 * @param vo
	 * @param companyExclusions
	 * 	//to map count of exceptions applied
	 */
	private void mapExceptionsCount(SPNExclusionsVO vo, List<SPNExclusionsVO> exclusions) {
		if (vo != null) {
			//to check whether two exceptions are applied
			for (SPNExclusionsVO spnExclusionsVO :exclusions) {
				if (spnExclusionsVO != null) {
					if ((vo.getCredentialTypeId().intValue()==
							spnExclusionsVO.getCredentialTypeId().intValue())
							&& (!((vo.getExceptionTypeId()!=null && spnExclusionsVO
							.getExceptionTypeId()!=null )&&(vo.getExceptionTypeId().intValue() == 
							spnExclusionsVO
							.getExceptionTypeId().intValue())))) {
						if (vo.getCredentialCategoryId() == null
								&& spnExclusionsVO
								.getCredentialCategoryId() == null) {
							vo.setExpCount(2);
							break;
						} else if ((vo.getCredentialCategoryId() != null && spnExclusionsVO
								.getCredentialCategoryId()!=null )
								&& (vo.getCredentialCategoryId().intValue()
										== spnExclusionsVO
										.getCredentialCategoryId().intValue())) {
							vo.setExpCount(2);
							break;
						} else {
							vo.setExpCount(1);
						}
					} else {
						vo.setExpCount(1);
					}
				}

			}
			if (vo.getCredentialExceptionId() != null
					&& vo.getExceptionTypeId().intValue()==2) {
				setExceptionStateValues(vo);
			}
		}

	}



	/**
	 * @param vo
	 * 
	 * method to generate selected State values from exception value
	 * 
	 */
	private void setExceptionStateValues(SPNExclusionsVO vo) {

		String expValue = vo.getExceptionValue();
		String[] expStates = expValue.split(",");
		List<String> expStateValues = new ArrayList<String>();
		if(expStates!=null && expStates.length>0){
			for (String arrItem :expStates){
				expStateValues.add(arrItem);
			}

		}
		String selectedStates = expValue;
		if(expValue.length()>5){
			selectedStates = expValue.substring(0,5);
			String selectedLeftStates = expValue.substring(6,expValue.length());
			vo.setSelectedStatesLeft(selectedLeftStates);
			vo.setRemainingStatesCount(expStateValues.size()-2);
		}
		vo.setSelectedStates(selectedStates);
		vo.setSelectedStatesValues(expStateValues);
	}



	public String saveExceptions(){
		User loggedInUser = getLoggedInUser();
		Integer buyerId = loggedInUser.getUserId();
		String userName = loggedInUser.getUsername();
		SPNExclusionsVO exclusionsVO = model.getExclusionsVO();
		try{
			if(exclusionsVO!=null){
				exclusionsVO.setBuyerId(buyerId);
				exclusionsVO.setModifiedBy(userName);
				exclusionsVO.setModifiedByUserName(userName);
				exclusionsVO.setModifiedDate(CalendarUtil.getNow());
				exclusionsVO.setCreatedDate(CalendarUtil.getNow());
				networkSummaryServices.saveException(exclusionsVO);
			}
		}
		catch(Exception e){
			logger.info("Error Occured in saving exceptions:"+e.getMessage());
		}
		return "json";
	}
	
	
	public String demoviewComplianceAjax() throws Exception
	{
		try{
			Integer spnId = 0;
			if(getRequest().getParameter("networkid")!=null && StringUtils.isNotEmpty((String)getRequest().getParameter("networkid"))){
				spnId = Integer.parseInt((String)getRequest().getParameter("networkid"));
			}
			ComplianceCriteriaVO complianceCriteriaVO=new ComplianceCriteriaVO();
			complianceCriteriaVO.setSpnId(spnId);
			String complianceType="";
			if(getRequest().getParameter("complianceType")!=null && StringUtils.isNotEmpty((String)getRequest().getParameter("complianceType"))){
				complianceType = (String)getRequest().getParameter("complianceType");
			}
		long startTime=System.currentTimeMillis();
		logger.info("ssstart of"+complianceType+" complaince of spn "+spnId);
			model=getModel();
			getRequest().setAttribute("spnId",spnId);
			getRequest().setAttribute("complianceType",complianceType);

			List<RequirementVO> requirementVoList=new ArrayList<RequirementVO>();
			List<SPNComplianceVO> requirementList=null;
			if(complianceType.equals(SPNBackendConstants.PROVIDER_COMPLIANCE)){
				requirementList=networkSummaryServices.getRequirementsForProviderCompliance(complianceCriteriaVO);	

			}
			else if(complianceType.equals(SPNBackendConstants.FIRM_COMPLIANCE)){
				requirementList=networkSummaryServices.getRequirements(complianceCriteriaVO);	
			}
			String requirementValue="";
			String requirementDescr="";
			if(null!=requirementList){
				for(SPNComplianceVO spn:requirementList){
					RequirementVO req=new RequirementVO();
					if(null!=spn.getCredType()){
						requirementValue=spn.getCredType();
						if(null!=spn.getCredCategory()){
							requirementValue=requirementValue+" - "+spn.getCredCategory();	
						}

						req.setDescr(requirementValue);
						req.setValue(requirementValue);
					}
					else{
						if(null!=spn.getCriteriaName())
						{
							requirementDescr=spn.getCriteriaName();
							requirementValue=spn.getCriteriaName();
							req.setDescr(requirementDescr);
							req.setValue(requirementValue);
						}
					}
					requirementVoList.add(req);
				}
				getRequest().setAttribute("requirementList",requirementVoList);	

			}
			List<String> statesList=networkSummaryServices.getStates();
			getRequest().setAttribute("statesList",statesList);	
			List<String> marketsList=networkSummaryServices.getMarkets();
			getRequest().setAttribute("marketsList",marketsList);	
			List<SPNComplianceVO> complianceList = new ArrayList<SPNComplianceVO>();


			//
			List<String> selectedRequirements= new ArrayList<String>();
			List<String> selectedComplianceStatus= new ArrayList<String>();
			List<String> selectedMarkets= new ArrayList<String>();
			List<String> selectedStates= new ArrayList<String>();
			if(null!=model.getSpnComplianceVO() && null!=model.getSpnComplianceVO().getSelectedRequirements()){
				for (String selectedRequirement : model.getSpnComplianceVO().getSelectedRequirements()) {
					if (StringUtils.isNotBlank(selectedRequirement)) {
						selectedRequirements.add(selectedRequirement);
					}
				}
			}
			if(null!=model.getSpnComplianceVO() && null!=model.getSpnComplianceVO().getSelectedComplianceStatus()){
				for (String status : model.getSpnComplianceVO().getSelectedComplianceStatus()) {
					if (StringUtils.isNotBlank(status)) {
						selectedComplianceStatus.add(status);
					}
				}
			}
			if(null!=model.getSpnComplianceVO() && null!=model.getSpnComplianceVO().getSelectedMarkets()){
				for (String market : model.getSpnComplianceVO().getSelectedMarkets()) {
					if (StringUtils.isNotBlank(market)) {
						selectedMarkets.add(market);
					}
				}
			}
			if(null!=model.getSpnComplianceVO() && null!=model.getSpnComplianceVO().getSelectedStates()){
				for (String state : model.getSpnComplianceVO().getSelectedStates()) {
					if (StringUtils.isNotBlank(state)) {
						selectedStates.add(state);
					}
				}
			}
			complianceCriteriaVO.setSelectedComplianceStatus(selectedComplianceStatus);
			complianceCriteriaVO.setSelectedMarkets(selectedMarkets);
			complianceCriteriaVO.setSelectedRequirements(selectedRequirements);
			complianceCriteriaVO.setSelectedStates(selectedStates);

			getRequest().setAttribute("selectedComplianceStatus",selectedComplianceStatus);	
			getRequest().setAttribute("selectedMarkets",selectedMarkets);	
			getRequest().setAttribute("selectedRequirements",selectedRequirements);	
			getRequest().setAttribute("selectedStates",selectedStates);	
			long endTime=System.currentTimeMillis() -startTime;
			logger.info("timefor filterfetch"+endTime);


			if(complianceType.equals(SPNBackendConstants.PROVIDER_COMPLIANCE)){
				complianceList= networkSummaryServices.getProviderCompliance(complianceCriteriaVO);
			}
			else if(complianceType.equals(SPNBackendConstants.FIRM_COMPLIANCE)){
				complianceList= networkSummaryServices.getFirmCompliance(complianceCriteriaVO);
			}
			//getRequest().setAttribute("complianceList",complianceList);
			
			String datas="";
			for(int i=0;i<30000;i++){
				datas= datas +"<tr><td>";
				datas=datas+"ddddddddddddddddddddddddddddd"+"</td>";
				datas=datas+"<td>"+"<a  style='color: #00A0D2;' href='javascript:void(0);' onclick='javascript:openProviderProfile("+32+","+"qqqq"+")' ><b>"+"fffff+" +"kkk"+"</td>"; 
				datas=datas+"<td>"+"ddddddddddddddddddddddddddddd"+"</td>";
				datas=datas+"<td>"+"ddddddddddddddddddddddddddddd"+"</td>";
				datas=datas+"<td>"+"<img alt='In compliance due to buyer override' title='In compliance due to buyer override' src='/ServiceLiveWebUtil/images/common/status-blue.png' style='cursor: pointer;'></img>"+"</td></tr>"; 

			}
			
			getRequest().setAttribute("complianceList",datas);	

			long endTime1=System.currentTimeMillis() -startTime;
			logger.info("timefor overall fetch"+endTime1);


			/*	

    Integer valueofSpn=0;

		if (spnId.intValue() == 1) {
  nightlyMemberMaintenanceService.processFirmCredentialsData(valueofSpn);
			}
			if (spnId.intValue() == 2) {
				nightlyMemberMaintenanceService.processProviderCredentialsData(valueofSpn);
			}
			if (spnId.intValue() == 3) {
				nightlyMemberMaintenanceService.maintainCompliance();
			}
			if (spnId.intValue() == 4) {
				nightlyMemberMaintenanceService.processMinimumRatingComplaince();
			}
			if (spnId.intValue() == 5) {
				nightlyMemberMaintenanceService.runSpn1();
			}
			if (spnId.intValue() == 7) {
				nightlyMemberMaintenanceService.runSpn();
			}
			 */
		}
		catch(Exception e){
			logger.info("Error Occured in loading Compliance Tab:"+e.getMessage());
			return SUCCESS;
		}
		return SUCCESS;
	}
	
	

	public String viewComplianceTabAjax(){
		
		try{
		// setting all aother values

		Integer spnId = 0;
	if(getRequest().getParameter("networkid")!=null && StringUtils.isNotEmpty((String)getRequest().getParameter("networkid"))){
		spnId = Integer.parseInt((String)getRequest().getParameter("networkid"));
	}
	ComplianceCriteriaVO complianceCriteriaVO=new ComplianceCriteriaVO();
	complianceCriteriaVO.setSpnId(spnId);
	String complianceType="";
	if(getRequest().getParameter("complianceType")!=null && StringUtils.isNotEmpty((String)getRequest().getParameter("complianceType"))){
		complianceType = (String)getRequest().getParameter("complianceType");
	}
	
	String searching="";
	if(getRequest().getParameter("searching")!=null && StringUtils.isNotEmpty((String)getRequest().getParameter("searching"))){
		searching = (String)getRequest().getParameter("searching");
	}
	getRequest().setAttribute("searching",searching);	
	long startTime=System.currentTimeMillis();
	logger.info("ssstart of"+complianceType+" complaince of spn "+spnId);
	model=getModel();
	getRequest().setAttribute("spnId",spnId);
	getRequest().setAttribute("complianceType",complianceType);
	 /*
	List<RequirementVO> requirementVoList=new ArrayList<RequirementVO>();
	List<SPNComplianceVO> requirementList=null;
	if(complianceType.equals(SPNBackendConstants.PROVIDER_COMPLIANCE)){
		requirementList=networkSummaryServices.getRequirementsForProviderCompliance(complianceCriteriaVO);	
		
		}
		else if(complianceType.equals(SPNBackendConstants.FIRM_COMPLIANCE)){
			requirementList=networkSummaryServices.getRequirements(complianceCriteriaVO);	
		}
	String requirementValue="";
	String requirementDescr="";
	if(null!=requirementList){
		for(SPNComplianceVO spn:requirementList){
			RequirementVO req=new RequirementVO();
			if(null!=spn.getCredType()){
				requirementValue=spn.getCredType();
				if(null!=spn.getCredCategory()){
					requirementValue=requirementValue+" - "+spn.getCredCategory();	
				}
				
				req.setDescr(requirementValue);
				req.setValue(requirementValue);
			}
			else{
				if(null!=spn.getCriteriaName())
				{
					requirementDescr=spn.getCriteriaName();
					requirementValue=spn.getCriteriaName();
					req.setDescr(requirementDescr);
					req.setValue(requirementValue);
				}
			}
			requirementVoList.add(req);
		}
		getRequest().setAttribute("requirementList",requirementVoList);	

	}
	List<String> statesList=networkSummaryServices.getStates();
	getRequest().setAttribute("statesList",statesList);	
	List<String> marketsList=networkSummaryServices.getMarkets();
	getRequest().setAttribute("marketsList",marketsList);	*/
	List<SPNComplianceVO> complianceList = new ArrayList<SPNComplianceVO>();
	
		
		//
		List<String> selectedRequirements= new ArrayList<String>();
		List<String> selectedComplianceStatus= new ArrayList<String>();
		List<String> selectedMarkets= new ArrayList<String>();
		List<String> selectedStates= new ArrayList<String>();
		if(null!=model.getSpnComplianceVO() && null!=model.getSpnComplianceVO().getSelectedRequirements()){
		for (String selectedRequirement : model.getSpnComplianceVO().getSelectedRequirements()) {
			if (StringUtils.isNotBlank(selectedRequirement)) {
				selectedRequirements.add(selectedRequirement);
			}
		  }
		}
		if(null!=model.getSpnComplianceVO() && null!=model.getSpnComplianceVO().getSelectedComplianceStatus()){
		for (String status : model.getSpnComplianceVO().getSelectedComplianceStatus()) {
			if (StringUtils.isNotBlank(status)) {
				selectedComplianceStatus.add(status);
			}
		 }
		}
		if(null!=model.getSpnComplianceVO() && null!=model.getSpnComplianceVO().getSelectedMarkets()){
		for (String market : model.getSpnComplianceVO().getSelectedMarkets()) {
			if (StringUtils.isNotBlank(market)) {
				selectedMarkets.add(market);
			}
		 }
		}
		if(null!=model.getSpnComplianceVO() && null!=model.getSpnComplianceVO().getSelectedStates()){
		for (String state : model.getSpnComplianceVO().getSelectedStates()) {
			if (StringUtils.isNotBlank(state)) {
				selectedStates.add(state);
			}
		 }
		}
		complianceCriteriaVO.setSelectedComplianceStatus(selectedComplianceStatus);
		complianceCriteriaVO.setSelectedMarkets(selectedMarkets);
		complianceCriteriaVO.setSelectedRequirements(selectedRequirements);
		complianceCriteriaVO.setSelectedStates(selectedStates);
		
		/*getRequest().setAttribute("selectedComplianceStatus",selectedComplianceStatus);	
		getRequest().setAttribute("selectedMarkets",selectedMarkets);	
		getRequest().setAttribute("selectedRequirements",selectedRequirements);	
		getRequest().setAttribute("selectedStates",selectedStates);	
		long endTime=System.currentTimeMillis() -startTime;
		logger.info("timefor filterfetch"+endTime);*/

		// to handle server side pagination.
		int startIndex=0;
		int numberOfRecords=0;
		 String sortColumnName="";
		 String sortOrder="";
		 String sSearch="";
		if(getRequest().getParameter("iDisplayStart")!=null && StringUtils.isNotEmpty((String)getRequest().getParameter("iDisplayStart"))){
			startIndex = Integer.parseInt((String)getRequest().getParameter("iDisplayStart"));
		}
		
		if(getRequest().getParameter("iDisplayLength")!=null && StringUtils.isNotEmpty((String)getRequest().getParameter("iDisplayLength"))){
			numberOfRecords = Integer.parseInt((String)getRequest().getParameter("iDisplayLength"));
		}
		if(getRequest().getParameter("iSortCol_0")!=null && StringUtils.isNotEmpty((String)getRequest().getParameter("iSortCol_0"))){
			String sortColumnId =(String)getRequest().getParameter("iSortCol_0");
			if(complianceType.equals(SPNBackendConstants.PROVIDER_COMPLIANCE)){
				if(sortColumnId.equals("0")){
						sortColumnName="requirement";
				}else if(sortColumnId.equals("1")){
					sortColumnName="provider";
				}else if(sortColumnId.equals("2")){
					sortColumnName="market";
				}else if(sortColumnId.equals("3")){
					sortColumnName="state";
				}else if(sortColumnId.equals("4")){
					sortColumnName="status";

				}
				
			}
			else if(complianceType.equals(SPNBackendConstants.FIRM_COMPLIANCE)){
				if(sortColumnId.equals("0")){
					sortColumnName="requirement";
			}else if(sortColumnId.equals("1")){
				sortColumnName="firm";
			}else if(sortColumnId.equals("2")){
				sortColumnName="market";
			}else if(sortColumnId.equals("3")){
				sortColumnName="state";
			}else if(sortColumnId.equals("4")){
				sortColumnName="status";

			}
			}
		}
		if(getRequest().getParameter("sSortDir_0")!=null && StringUtils.isNotEmpty((String)getRequest().getParameter("sSortDir_0"))){
			sortOrder = (String)getRequest().getParameter("sSortDir_0");
		}
	
		if(getRequest().getParameter("sSearch")!=null && StringUtils.isNotEmpty((String)getRequest().getParameter("sSearch"))){
			sSearch = (String)getRequest().getParameter("sSearch");
		}
		if(getRequest().getParameter("sEcho")!=null && StringUtils.isNotEmpty((String)getRequest().getParameter("sEcho"))){
			sEcho = Integer.parseInt(getRequest().getParameter("sEcho")) ;
		}
		
		
		complianceCriteriaVO.setSortColumnName(sortColumnName);
		complianceCriteriaVO.setSortOrder(sortOrder);
		complianceCriteriaVO.setsSearch(sSearch);
		complianceCriteriaVO.setStartIndex(startIndex);
		complianceCriteriaVO.setNumberOfRecords(numberOfRecords);
		
		String requirement="";
		String resource="";
		String market="";
		String state="";
		String status="";
		
		
		iTotalRecords="100";
		iTotalDisplayRecords="10";
	/*	String [][] kbbb={{"1", "1", "1","1","1"}, {"1", "1", "1","1","1"}, {"1", "1", "1","1","1"},{"1", "1", "1","1","1"},{"1", "1", "1","1","1"}
		,{"1", "1", "1","1","1"}, {"1", "1", "1","1","1"}, {"1", "1", "1","1","1"},{"1", "1", "1","1","1"},{"1", "1", "1","1","1"}
		};*/
		
		
		List<String> criteriaNameList=new ArrayList<String>();
		criteriaNameList.add("Minimum Rating");
		criteriaNameList.add("Minimum Completed Service Orders");
		criteriaNameList.add("Vehicle Liability");
		criteriaNameList.add("Commercial General Liability");
		criteriaNameList.add("Worker's Compensation");

		
		if(complianceType.equals(SPNBackendConstants.PROVIDER_COMPLIANCE)){
			
			iTotalRecords=networkSummaryServices.getProviderComplianceCount(complianceCriteriaVO).toString();
			complianceList= networkSummaryServices.getProviderCompliance(complianceCriteriaVO);
			iTotalDisplayRecords=iTotalRecords;	
			if(null!=complianceList){
			aaData=new String[complianceList.size()][5];
			int count=0;
			for(SPNComplianceVO sPNComplianceVO:complianceList){
				String data[]=new String[5];
				data[0]="";
				data[1]="";
				data[2]="";
				data[3]="";
				data[4]="";
				if(null!=sPNComplianceVO.getCredType())
				{
				data[0]=sPNComplianceVO.getCredType();
				if(null!=sPNComplianceVO.getCredCategory()){
				data[0]=data[0]+" > "+sPNComplianceVO.getCredCategory();
				}
				}
				else
				{
					if(null!=sPNComplianceVO.getCriteriaName() && criteriaNameList.contains(sPNComplianceVO.getCriteriaName())){
						data[0]=sPNComplianceVO.getCriteriaName();	
					}
					else
					{
						if(null!=sPNComplianceVO.getCriteriaValueName())
						data[0]=sPNComplianceVO.getCriteriaValueName();	

					}
					
				}
				

				data[1]="<a  style='color: #00A0D2;' href='javascript:void(0);' onclick='javascript:openProviderProfile("+sPNComplianceVO.getId()+","+sPNComplianceVO.getVendorId()+")' ><b>"+sPNComplianceVO.getProviderFirstName()+" "+sPNComplianceVO.getProviderLastName() 
				+"</b></a><br/>"+" (ID #"+sPNComplianceVO.getId()+")<br/>";
				if(null==sPNComplianceVO.getProviderState()){
					data[1]=data[1]+" Approved (Market Ready)";
				}
				else if(sPNComplianceVO.getProviderState().equalsIgnoreCase("SP SPN OUT OF COMPLIANCE")){
					data[1]=data[1]+" SPN Out Of Compliance";
				}
				else if(sPNComplianceVO.getProviderState().equalsIgnoreCase("SP SPN APPROVED")){
					data[1]=data[1]+" SPN Approved";
				}
				else{
					data[1]=data[1]+" Approved (Market Ready)";
				}
				data[2]=sPNComplianceVO.getMarketName();
				data[3]=sPNComplianceVO.getStateCode();
				
				if( "SP SPN CRED INCOMPLIANCE".equals(sPNComplianceVO.getWfState()))
				{
				data[4]="<img alt='In compliance' title='In compliance' src='/ServiceLiveWebUtil/images/common/status-green.png' style='cursor: pointer;'></img>";

				}
				else if("SP SPN CRED OUTOFCOMPLIANCE".equals(sPNComplianceVO.getWfState()))
				{
				data[4]="<img alt='Out of compliance' title='Out of compliance' src='/ServiceLiveWebUtil/images/common/status-yellow.png' style='cursor: pointer;'></img>";

				}
				else if("SP SPN CRED OVERRIDE".equals(sPNComplianceVO.getWfState()))
				{
				data[4]="<img alt='In compliance due to buyer override' title='In compliance due to buyer override' src='/ServiceLiveWebUtil/images/common/status-blue.png' style='cursor: pointer;'></img>";
	
				}
				aaData[count]=data;
			
				count=count+1;
			}
		}
		
			
			
		}
		else if(complianceType.equals(SPNBackendConstants.FIRM_COMPLIANCE)){
			
			iTotalRecords=networkSummaryServices.getFirmComplianceCount(complianceCriteriaVO).toString();
			complianceList= networkSummaryServices.getFirmCompliance(complianceCriteriaVO);
			iTotalDisplayRecords=iTotalRecords;
			
			if(null!=complianceList){
			aaData=new String[complianceList.size()][5];
			int count=0;
			for(SPNComplianceVO sPNComplianceVO:complianceList){
				String data[]=new String[5];
				data[0]="";
				data[1]="";
				data[2]="";
				data[3]="";
				data[4]="";
				if(null!=sPNComplianceVO.getCredType())
				{
				data[0]=sPNComplianceVO.getCredType();
				if(null!=sPNComplianceVO.getCredCategory()){
				data[0]=data[0]+" > "+sPNComplianceVO.getCredCategory();
				}
				}
				else
				{
					if(null!=sPNComplianceVO.getCriteriaName() && criteriaNameList.contains(sPNComplianceVO.getCriteriaName())){
						data[0]=sPNComplianceVO.getCriteriaName();	
					}
					else
					{
						if(null!=sPNComplianceVO.getCriteriaValueName())
							data[0]=sPNComplianceVO.getCriteriaValueName();	

					}
					
				}
				if(null!=sPNComplianceVO.getLiabilityAmount()){
					BigDecimal liabAmount= new BigDecimal(String.valueOf(sPNComplianceVO.getLiabilityAmount())).setScale(2, BigDecimal.ROUND_UP);
					//Fixing SL-19872: converting amount to comma separated value.
					String liabilityAmount=MoneyUtil.convertDoubleToCurrency(liabAmount.doubleValue(), Locale.US);
					data[0]=data[0]+" :$"+liabilityAmount;	
				}
				
				data[1]="<a  style='color: #00A0D2;' href='javascript:void(0);' onclick='javascript:openProviderFirmProfile("+sPNComplianceVO.getId()+")' ><b>"+sPNComplianceVO.getFullName()+ 
				"</b></a><br/>"+" (ID #"+sPNComplianceVO.getId()+")";
				data[2]=sPNComplianceVO.getMarketName();
				data[3]=sPNComplianceVO.getStateCode();
				
				if("PF SPN CRED INCOMPLIANCE".equals(sPNComplianceVO.getWfState()))
				{
				data[4]="<img alt='In compliance' title='In compliance' src='/ServiceLiveWebUtil/images/common/status-green.png' style='cursor: pointer;'></img>";

				}
				else if("PF SPN CRED OUTOFCOMPLIANCE".equals(sPNComplianceVO.getWfState()))
				{
				data[4]="<img alt='Out of compliance' title='Out of compliance' src='/ServiceLiveWebUtil/images/common/status-yellow.png' style='cursor: pointer;'></img>";

				}
				else if("PF SPN CRED OVERRIDE".equals(sPNComplianceVO.getWfState()))
				{
				data[4]="<img alt='In compliance due to buyer override' title='In compliance due to buyer override' src='/ServiceLiveWebUtil/images/common/status-blue.png' style='cursor: pointer;'></img>";
	
				}
				aaData[count]=data;
			
				count=count+1;
			}
		}
			
			
		}
		model.setAaData(aaData);
		model.setsEcho(sEcho);
		model.setiTotalDisplayRecords(iTotalDisplayRecords);
		model.setiTotalRecords(iTotalRecords);	
		getRequest().setAttribute("complianceList",complianceList);	
		long endTime1=System.currentTimeMillis() -startTime;
		logger.info("timefor overall fetch"+endTime1);
		
		}	
catch(Exception e)
{
	logger.info("Exception in complaince tab"+e);
	return "json";
}
		

	

	
	return "json";

	}
	
	public String viewComplianceAjax() throws Exception
	{
		try{
			Integer spnId = 0;
			if(getRequest().getParameter("networkid")!=null && StringUtils.isNotEmpty((String)getRequest().getParameter("networkid"))){
				spnId = Integer.parseInt((String)getRequest().getParameter("networkid"));
			}
			ComplianceCriteriaVO complianceCriteriaVO=new ComplianceCriteriaVO();
			complianceCriteriaVO.setSpnId(spnId);
			String complianceType="";
			if(getRequest().getParameter("complianceType")!=null && StringUtils.isNotEmpty((String)getRequest().getParameter("complianceType"))){
				complianceType = (String)getRequest().getParameter("complianceType");
			}
			
			String searching="";
			if(getRequest().getParameter("searching")!=null && StringUtils.isNotEmpty((String)getRequest().getParameter("searching"))){
				searching = (String)getRequest().getParameter("searching");
			}
			getRequest().setAttribute("searching",searching);	
		long startTime=System.currentTimeMillis();
		logger.info("ssstart of"+complianceType+" complaince of spn "+spnId);
			model=getModel();
			getRequest().setAttribute("spnId",spnId);
			getRequest().setAttribute("complianceType",complianceType);

			List<RequirementVO> requirementVoList=new ArrayList<RequirementVO>();
			List<SPNComplianceVO> requirementList=null;
			if(complianceType.equals(SPNBackendConstants.PROVIDER_COMPLIANCE)){
				requirementList=networkSummaryServices.getRequirementsForProviderCompliance(complianceCriteriaVO);	

			}
			else if(complianceType.equals(SPNBackendConstants.FIRM_COMPLIANCE)){
				requirementList=networkSummaryServices.getRequirements(complianceCriteriaVO);	
			}
			String requirementValue="";
			String requirementDescr="";
			if(null!=requirementList){
				for(SPNComplianceVO spn:requirementList){
					RequirementVO req=new RequirementVO();
					if(null!=spn.getCredType()){
						requirementValue=spn.getCredType();
						if(null!=spn.getCredCategory()){
							requirementValue=requirementValue+" - "+spn.getCredCategory();	
						}

						req.setDescr(requirementValue);
						req.setValue(requirementValue);
					}
					else{
						if(null!=spn.getCriteriaName())
						{
							requirementDescr=spn.getCriteriaName();
							requirementValue=spn.getCriteriaName();
							req.setDescr(requirementDescr);
							req.setValue(requirementValue);
						}
					}
					requirementVoList.add(req);
				}
				getRequest().setAttribute("requirementList",requirementVoList);	

			}
			List<String> statesList=networkSummaryServices.getStates();
			getRequest().setAttribute("statesList",statesList);	
			List<String> marketsList=networkSummaryServices.getMarkets();
			getRequest().setAttribute("marketsList",marketsList);	
			//List<SPNComplianceVO> complianceList = new ArrayList<SPNComplianceVO>();


			//
			List<String> selectedRequirements= new ArrayList<String>();
			List<String> selectedComplianceStatus= new ArrayList<String>();
			List<String> selectedMarkets= new ArrayList<String>();
			List<String> selectedStates= new ArrayList<String>();
			if(null!=model.getSpnComplianceVO() && null!=model.getSpnComplianceVO().getSelectedRequirements()){
				for (String selectedRequirement : model.getSpnComplianceVO().getSelectedRequirements()) {
					if (StringUtils.isNotBlank(selectedRequirement)) {
						selectedRequirements.add(selectedRequirement);
					}
				}
			}
			if(null!=model.getSpnComplianceVO() && null!=model.getSpnComplianceVO().getSelectedComplianceStatus()){
				for (String status : model.getSpnComplianceVO().getSelectedComplianceStatus()) {
					if (StringUtils.isNotBlank(status)) {
						selectedComplianceStatus.add(status);
					}
				}
			}
			if(null!=model.getSpnComplianceVO() && null!=model.getSpnComplianceVO().getSelectedMarkets()){
				for (String market : model.getSpnComplianceVO().getSelectedMarkets()) {
					if (StringUtils.isNotBlank(market)) {
						selectedMarkets.add(market);
					}
				}
			}
			if(null!=model.getSpnComplianceVO() && null!=model.getSpnComplianceVO().getSelectedStates()){
				for (String state : model.getSpnComplianceVO().getSelectedStates()) {
					if (StringUtils.isNotBlank(state)) {
						selectedStates.add(state);
					}
				}
			}
			complianceCriteriaVO.setSelectedComplianceStatus(selectedComplianceStatus);
			complianceCriteriaVO.setSelectedMarkets(selectedMarkets);
			complianceCriteriaVO.setSelectedRequirements(selectedRequirements);
			complianceCriteriaVO.setSelectedStates(selectedStates);

			getRequest().setAttribute("selectedComplianceStatus",selectedComplianceStatus);	
			getRequest().setAttribute("selectedMarkets",selectedMarkets);	
			getRequest().setAttribute("selectedRequirements",selectedRequirements);	
			getRequest().setAttribute("selectedStates",selectedStates);	
			long endTime=System.currentTimeMillis() -startTime;
			logger.info("timefor filterfetch"+endTime);

			Integer count=0;
			if(complianceType.equals(SPNBackendConstants.PROVIDER_COMPLIANCE)){
				count= networkSummaryServices.getProviderComplianceCount(complianceCriteriaVO);
				
				Date date=networkSummaryServices.getProviderComplianceDate();
				getRequest().setAttribute("updatedDate",date);	

			}
			else if(complianceType.equals(SPNBackendConstants.FIRM_COMPLIANCE)){
				count= networkSummaryServices.getFirmComplianceCount(complianceCriteriaVO);
				Date date=networkSummaryServices.getFirmComplianceDate();
				getRequest().setAttribute("updatedDate",date);	
			}
			getRequest().setAttribute("count",count);	
			long endTime1=System.currentTimeMillis() -startTime;
			logger.info("timefor overall fetch"+endTime1);


			/*	

    Integer valueofSpn=0;

		if (spnId.intValue() == 1) {
  nightlyMemberMaintenanceService.processFirmCredentialsData(valueofSpn);
			}
			if (spnId.intValue() == 2) {
				nightlyMemberMaintenanceService.processProviderCredentialsData(valueofSpn);
			}
			if (spnId.intValue() == 3) {
				nightlyMemberMaintenanceService.maintainCompliance();
			}
			if (spnId.intValue() == 4) {
				nightlyMemberMaintenanceService.processMinimumRatingComplaince();
			}
			if (spnId.intValue() == 5) {
				nightlyMemberMaintenanceService.runSpn1();
			}
			if (spnId.intValue() == 7) {
				nightlyMemberMaintenanceService.runSpn();
			}
			 */
		}
		catch(Exception e){
			logger.info("Error Occured in loading Compliance Tab:"+e.getMessage());
			return SUCCESS;
		}
		return SUCCESS;
	}




	public String spnSummaryFilterByAJAX(){

		User loggedInUser = getLoggedInUser();
		model = getModel();


		if (loggedInUser == null)
		{
			return NOT_LOGGED_IN;
		}
		else if (loggedInUser.getRole() == null || loggedInUser.getRole().getId() == null || loggedInUser.getRole().getId().intValue() == ROLE_ID_PROVIDER.intValue())
		{
			return NOT_LOGGED_IN_AS_BUYER;
		}

		Integer buyerId2 = loggedInUser.getUserId();
		if (buyerId2 != null)
		{
			List<SPNSummaryVO> spnlist = new LinkedList<SPNSummaryVO>();
			try
			{	SPNSummaryFilterVO summaryFilterVO = model.getSpnSummaryFilterVO();
			summaryFilterVO.setBuyerId(buyerId2);
			if(summaryFilterVO.getMarketId()!= null && summaryFilterVO.getMarketId().intValue() == -1){
				summaryFilterVO.setMarketId(null);
			}
			if(summaryFilterVO.getStateCd()!= null && summaryFilterVO.getStateCd().equals("-1")){
				summaryFilterVO.setStateCd(null);
			}
			if(summaryFilterVO.getStateCd()!=null || summaryFilterVO.getMarketId()!=null)
			{
				summaryFilterVO.setAddJoinInd(new Integer(1));
			}


			spnlist = networkSummaryServices.getListOfSPNSummaryForBuyer(summaryFilterVO, null);
			}
			catch (Exception e)
			{
				logger.error(e);
			}
			model.setSpnSummary(spnlist);
		}
		else
		{
			model.setSpnSummary(new ArrayList<SPNSummaryVO>());
		}

		return SUCCESS;

	}


	public String viewNetworkHistoryAjax() throws Exception
	{

		// Get networkid
		String networkid_str = getRequest().getParameter("networkid");
		List<NetworkHistoryVO> networkHistory = null;
		if(networkid_str != null)
		{
			Integer networkid_int = Integer.valueOf(networkid_str);

			networkHistory = networkSummaryServices.getNetworkHistory(networkid_int);
		}
		else
		{
			networkHistory = new ArrayList<NetworkHistoryVO>();
		}
		getRequest().setAttribute("networkHistory", networkHistory);

		return SUCCESS;
	}


	public SPNMonitorNetworkModel getModel()
	{
		return model;
	}


	public void setModel(SPNMonitorNetworkModel model) {
		this.model = model;
	}

	public String viewCoverageDetailsAjax(){
		try{
		logger.info("Inside view coverage details ajax!!!");
		SPNRoutingDTO coverageDTO;
		SPNRoutingDTO savedDTO = (SPNRoutingDTO)getRequest().getSession().getAttribute(SAVED_DTO);
		SPNRoutingDTO filteredCoverageDTO = (SPNRoutingDTO)getRequest().getSession().getAttribute(FILTERED_COVERAGE_DTO);
		if(filteredCoverageDTO==null){
			coverageDTO = savedDTO;
		}
		else{
			coverageDTO = filteredCoverageDTO;
		}
		getRequest().getSession().removeAttribute(SAVED_DTO);
		List<SPNCoverageDTO> coverageList = new ArrayList<SPNCoverageDTO>();
		
		for(SPNCoverageDTO tempList : coverageDTO.getCoveragelist()){
			coverageList.add(tempList);
		}
		List<SPNCoverageDTO> newCoverageList = new ArrayList<SPNCoverageDTO>();
		String criteriaLevel = coverageDTO.getSpnHdr().getCriteriaLevel();
		
		int startIndex=0;
		int numberOfRecords=0;
		String sortOrder="des";
		String sortColumnId="1";
		String sSearch="";
		int disclaimer=0;
		savedDTO.setDisclaimer(0);
		savedDTO.setProviderCount(0);
		savedDTO.setFirmCount(0);
		
		if(getRequest().getParameter("iDisplayStart")!=null && StringUtils.isNotEmpty((String)getRequest().getParameter("iDisplayStart"))){
			startIndex = Integer.parseInt((String)getRequest().getParameter("iDisplayStart"));
		}
		
		if(getRequest().getParameter("iDisplayLength")!=null && StringUtils.isNotEmpty((String)getRequest().getParameter("iDisplayLength"))){
			numberOfRecords = Integer.parseInt((String)getRequest().getParameter("iDisplayLength"));
		}
		if(getRequest().getParameter("sSortDir_0")!=null && StringUtils.isNotEmpty((String)getRequest().getParameter("sSortDir_0"))){
			sortOrder = (String)getRequest().getParameter("sSortDir_0");
		}
		Comparator<SPNCoverageDTO> compare = null;
		if(getRequest().getParameter("iSortCol_0")!=null && StringUtils.isNotEmpty((String)getRequest().getParameter("iSortCol_0"))){
			sortColumnId =(String)getRequest().getParameter("iSortCol_0");
			if(criteriaLevel.equalsIgnoreCase("PROVIDER")){
				if(sortOrder.equalsIgnoreCase("asc")){
					if(sortColumnId.equals("0")){
						compare = SPNCoverageDTO.getComparator(SPNCoverageDTO.SortParameter.PROVIDER_NAME_ASCENDING);
					}else if(sortColumnId.equals("1")){
						compare = SPNCoverageDTO.getComparator(SPNCoverageDTO.SortParameter.SCORE_ASCENDING);
					}else if(sortColumnId.equals("2")){
						compare = SPNCoverageDTO.getComparator(SPNCoverageDTO.SortParameter.FIRM_NAME_ASCENDING);
					}else if(sortColumnId.equals("3")){
						compare = SPNCoverageDTO.getComparator(SPNCoverageDTO.SortParameter.TITLE_ASCENDING);
					}
				}
				else{
					if(sortColumnId.equals("0")){
						compare = SPNCoverageDTO.getComparator(SPNCoverageDTO.SortParameter.PROVIDER_NAME_DESCENDING);
					}else if(sortColumnId.equals("1")){
						compare = SPNCoverageDTO.getComparator(SPNCoverageDTO.SortParameter.SCORE_DESCENDING);
					}else if(sortColumnId.equals("2")){
						compare = SPNCoverageDTO.getComparator(SPNCoverageDTO.SortParameter.FIRM_NAME_DESCENDING);
					}else if(sortColumnId.equals("3")){
						compare = SPNCoverageDTO.getComparator(SPNCoverageDTO.SortParameter.TITLE_DESCENDING);
					}
				}
			}
			else if(criteriaLevel.equalsIgnoreCase("FIRM")){
				if(sortOrder.equalsIgnoreCase("asc")){
					if(sortColumnId.equals("0")){
						compare = SPNCoverageDTO.getComparator(SPNCoverageDTO.SortParameter.FIRM_NAME_ASCENDING);
					}else if(sortColumnId.equals("1")){
						compare = SPNCoverageDTO.getComparator(SPNCoverageDTO.SortParameter.SCORE_ASCENDING);
					}else if(sortColumnId.equals("2")){
						compare = SPNCoverageDTO.getComparator(SPNCoverageDTO.SortParameter.SL_STATUS_ASCENDING);
					}else if(sortColumnId.equals("3")){
						compare = SPNCoverageDTO.getComparator(SPNCoverageDTO.SortParameter.NO_OF_APPROVED_PROVIDERS_ASCENDING);
					}
				}
				else{
					if(sortColumnId.equals("0")){
						compare = SPNCoverageDTO.getComparator(SPNCoverageDTO.SortParameter.FIRM_NAME_DESCENDING);
					}else if(sortColumnId.equals("1")){
						compare = SPNCoverageDTO.getComparator(SPNCoverageDTO.SortParameter.SCORE_DESCENDING);
					}else if(sortColumnId.equals("2")){
						compare = SPNCoverageDTO.getComparator(SPNCoverageDTO.SortParameter.SL_STATUS_DESCENDING);
					}else if(sortColumnId.equals("3")){
						compare = SPNCoverageDTO.getComparator(SPNCoverageDTO.SortParameter.NO_OF_APPROVED_PROVIDERS_DESCENDING);
					}
				}
			}
			Collections.sort(coverageList, compare);
		}
		if(getRequest().getParameter("sSearch")!=null && StringUtils.isNotEmpty((String)getRequest().getParameter("sSearch"))){
			List<SPNCoverageDTO> searchResult= new ArrayList<SPNCoverageDTO>();
			sSearch = (String)getRequest().getParameter("sSearch");
			if(criteriaLevel.equalsIgnoreCase("PROVIDER")){
				for(SPNCoverageDTO sPNCoverageDTO:coverageList){
					String providerSearch=sPNCoverageDTO.getProvFirstName()+" "+sPNCoverageDTO.getProvLastName()+" ID# "+sPNCoverageDTO.getMemberId()+" "+sPNCoverageDTO.getScore()+" "+sPNCoverageDTO.getFirmName()+" ID# "+sPNCoverageDTO.getFirmId()+" "+sPNCoverageDTO.getJobTitle();
					if(providerSearch.toLowerCase().contains(sSearch.toLowerCase())){
						searchResult.add(sPNCoverageDTO);
					}
				}
			}
			else if(criteriaLevel.equalsIgnoreCase("FIRM")){
				for(SPNCoverageDTO sPNCoverageDTO:coverageList){
					String firmSearch=sPNCoverageDTO.getFirmName()+" ID# "+sPNCoverageDTO.getMemberId()+" "+sPNCoverageDTO.getScore()+" "+sPNCoverageDTO.getSlStatus()+" "+sPNCoverageDTO.getNoOfEligibleProvs();
					if(firmSearch.toLowerCase().contains(sSearch.toLowerCase())){
						searchResult.add(sPNCoverageDTO);
					}
				}
			}
			coverageList.clear();
			coverageList = searchResult;
		}
		if(getRequest().getParameter("sEcho")!=null && StringUtils.isNotEmpty((String)getRequest().getParameter("sEcho"))){
			sEcho = Integer.parseInt((getRequest().getParameter("sEcho")));
		}
		if(numberOfRecords > (coverageList.size()-startIndex)){
			numberOfRecords = coverageList.size()-startIndex;
		}
		iTotalRecords= String.valueOf(numberOfRecords);
		iTotalDisplayRecords=String.valueOf(coverageList.size());
		iTotalRecords=String.valueOf(coverageList.size());
		for(SPNCoverageDTO sPNCoverageDTO:coverageList){
			if(sPNCoverageDTO.getNoScoreInd()==1){
				disclaimer = 1;
				break;
			}
		}
		savedDTO.setDisclaimer(disclaimer);
		if(criteriaLevel.equalsIgnoreCase("FIRM")){
			if(null!=coverageList){
				int count=0;
				int provCount=0;
				savedDTO.setFirmCount(coverageList.size());
				for(SPNCoverageDTO sPNCoverageDTO:coverageList){
					provCount=provCount+sPNCoverageDTO.getNoOfEligibleProvs();
				}
				savedDTO.setProviderCount(provCount);
				for(int i=startIndex;i<numberOfRecords+startIndex;i++){
					newCoverageList.add(coverageList.get(i));
				}
				aaData=new String[newCoverageList.size()][4];
				for(SPNCoverageDTO sPNCoverageDTO:newCoverageList){
					String data[]=new String[4];
					data[0]="";
					data[1]="";
					data[2]="";
					data[3]="";
					
					if(sPNCoverageDTO.getNoScoreInd()==1){
						data[0]="<div  style='color: #0099FF;cursor: pointer;word-wrap:break-word;width:235px;' onclick='javascript:openFirmProfile("+sPNCoverageDTO.getMemberId()+");' >"+sPNCoverageDTO.getFirmName()+"&nbsp;&nbsp;<i class='icon-share' title='Opens in a new window'></i><br/></div>"+"<span class='ids'>ID# "+sPNCoverageDTO.getMemberId()+"*";
					}
					else{
						data[0]="<div  style='color: #0099FF;cursor: pointer;word-wrap:break-word;width:235px;' onclick='javascript:openFirmProfile("+sPNCoverageDTO.getMemberId()+");' >"+sPNCoverageDTO.getFirmName()+"&nbsp;&nbsp;<i class='icon-share' title='Opens in a new window'></i><br/></div>"+"<span class='ids'>ID# "+sPNCoverageDTO.getMemberId();
					}
					if(sPNCoverageDTO.getRank()==null){
						data[1]=sPNCoverageDTO.getScore()+"%";
					}
					else{
						data[1]=sPNCoverageDTO.getScore()+"% "+sPNCoverageDTO.getRank();
					}
					data[2]=sPNCoverageDTO.getSlStatus();
					
					data[3]=sPNCoverageDTO.getNoOfEligibleProvs().toString();
					
					aaData[count]=data;
					
					count=count+1;
				}
			}
		}
		else if(criteriaLevel.equalsIgnoreCase("PROVIDER")){
			if(null!=coverageList){
				int count=0;
				savedDTO.setProviderCount(coverageList.size());
				for(int i=startIndex;i<numberOfRecords+startIndex;i++){
					newCoverageList.add(coverageList.get(i));
				}
				aaData=new String[newCoverageList.size()][4];
				for(SPNCoverageDTO sPNCoverageDTO:newCoverageList){
					String data[]=new String[4];
					data[0]="";
					data[1]="";
					data[2]="";
					data[3]="";
					
					if(sPNCoverageDTO.getNoScoreInd()==1){
						data[0]="<div  style='color: #0099FF;cursor: pointer;word-wrap:break-word;width:235px;' onclick='javascript:openProvProfile("+sPNCoverageDTO.getMemberId()+","+sPNCoverageDTO.getFirmId()+");' >"+sPNCoverageDTO.getProvFirstName()+" "+sPNCoverageDTO.getProvLastName()+"&nbsp;&nbsp;<i class='icon-share' title='Opens in a new window'></i><br/></div><span class='ids'>ID# "+sPNCoverageDTO.getMemberId()+"*";
					}
					else{
						data[0]="<div  style='color: #0099FF;cursor: pointer;word-wrap:break-word;width:235px;' onclick='javascript:openProvProfile("+sPNCoverageDTO.getMemberId()+","+sPNCoverageDTO.getFirmId()+");' >"+sPNCoverageDTO.getProvFirstName()+" "+sPNCoverageDTO.getProvLastName()+"&nbsp;&nbsp;<i class='icon-share' title='Opens in a new window'></i><br/></div><span class='ids'>ID# "+sPNCoverageDTO.getMemberId();
					}
					
					if(sPNCoverageDTO.getRank()==null){
						data[1]=sPNCoverageDTO.getScore()+"%";
					}
					else{
						data[1]=sPNCoverageDTO.getScore()+"% "+sPNCoverageDTO.getRank();
					}
					
					data[2]="<div  style='color: #0099FF;cursor: pointer;word-wrap:break-word;width:235px;' onclick='javascript:openFirmProfile("+sPNCoverageDTO.getFirmId()+");'>"+sPNCoverageDTO.getFirmName()+"&nbsp;&nbsp;<i class='icon-share' title='Opens in a new window'></i><br/></div><span class='ids'>ID# "+sPNCoverageDTO.getFirmId()+"</span>";
					
					data[3]=sPNCoverageDTO.getJobTitle();
					
					aaData[count]=data;
			
					count=count+1; 
				}
			}
		}
		model.setAaData(aaData);
		model.setsEcho(sEcho);
		model.setiTotalDisplayRecords(iTotalDisplayRecords);
		model.setiTotalRecords(iTotalRecords);	
		getRequest().getSession().setAttribute(SAVED_DTO, savedDTO);
		}
		catch(Exception e)
		{
			logger.info("Exception in coverage details table"+e);
			return "json";
		}
		return "json";
	}
	
	/**
	 * @param networkSummaryServices the networkSummaryServices to set
	 */
	public void setNetworkSummaryServices(
			NetworkSummaryServices networkSummaryServices) {
		this.networkSummaryServices = networkSummaryServices;
	}


	public Integer getNetworkCountLimit()
	{
		return networkCountLimit;
	}


	public void setNetworkCountLimit(Integer networkCountLimit)
	{
		this.networkCountLimit = networkCountLimit;
	}


	public Integer getNetworkCountAll()
	{
		return networkCountAll;
	}


	public void setNetworkCountAll(Integer networkCountAll)
	{
		this.networkCountAll = networkCountAll;
	}


	public Integer getCompleteSPNCount()
	{
		return completeSPNCount;
	}


	public void setCompleteSPNCount(Integer completeSPNCount)
	{
		this.completeSPNCount = completeSPNCount;
	}


	public Integer getIncompleteSPNCount()
	{
		return incompleteSPNCount;
	}


	public void setIncompleteSPNCount(Integer incompleteSPNCount)
	{
		this.incompleteSPNCount = incompleteSPNCount;
	}

	public Cryptography getCryptography() {
		return cryptography;
	}

	public void setCryptography(Cryptography cryptography) {
		this.cryptography = cryptography;
	}
}
