package com.newco.batch.isleads;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.newco.marketplace.api.leads.GetISDataParameters;
import com.newco.marketplace.api.leads.GetInsideSalesRequest;
import com.newco.marketplace.api.leads.LoginInsideSalesRequest;
import com.newco.marketplace.business.iBusiness.leadsmanagement.ILeadProcessingBO;
import com.newco.marketplace.interfaces.ProviderConstants;
import com.newco.marketplace.vo.provider.Campaign;
import com.newco.marketplace.vo.provider.CampaignVO;
import com.newco.marketplace.vo.provider.Cdr;
import com.newco.marketplace.vo.provider.CdrVO;
import com.newco.marketplace.vo.provider.Employee;
import com.newco.marketplace.vo.provider.EmployeeVO;
import com.newco.marketplace.vo.provider.Impressions;
import com.newco.marketplace.vo.provider.ImpressionsVO;
import com.servicelive.InsideSalesRestClient;

/**
 * @author akurian
 */
public class InsideSalesDataLoadProcessor {

	private static final Logger logger = Logger
			.getLogger(InsideSalesDataLoadProcessor.class);

	private ILeadProcessingBO leadProcessingBO;
	private String isApiBaseUrl;
	private String isApiUserName;
	private String isApiPassword;
	private String isApiToken;

	
	public int process() {
		try {
			logger.info("Starting inside sales data load");
			InsideSalesRestClient insideSalesRestClient = new InsideSalesRestClient();

			/*
			 * Employees and Campaign - Truncate and reload every day
			 * Impressions and CDR - Truncate and load everything if complete
			 * load parameter is true Load the data for previous date if
			 * complete load parameter is false
			 */

			// Step 1: Login to inside sales and get the cookies
			String cookies = insideSalesRestClient
					.logInInsideSalesAndGetCookies(
							getInsideSalesLoginRequestJSON(), isApiBaseUrl);
			// logger.info("cookies::"+cookies);
			// logger.info("isApiBaseUrl::"+isApiBaseUrl);
			
			if(null!=cookies && StringUtils.isNotBlank(cookies)){
				
				// Get limit for each call
				String limitS = "500"; // Default limit
				limitS = leadProcessingBO.getPropertyValue(ProviderConstants.IS_DATALOAD_LIMIT);
				
				// Do I need to load complete data?
				String isFullLoad = ProviderConstants.IS_FALSE; // Default full load
				isFullLoad = leadProcessingBO.getPropertyValue(ProviderConstants.IS_FULLLOAD);				
				
				// Load Employees
				readAndSaveEmployees(Integer.valueOf(limitS),cookies,insideSalesRestClient);
				
				// Load campaigns
				readAndSaveCampaigns(Integer.valueOf(limitS),cookies,insideSalesRestClient);
				
				// load CDR	- Not required as of now.			
				// readAndSaveCDRs(Integer.valueOf(limitS),cookies,insideSalesRestClient,isFullLoad);	
				
				// load Impressions
				readAndSaveImpressions(Integer.valueOf(limitS),cookies,insideSalesRestClient,isFullLoad);	
				
			}else{
				logger.info("Exiting inside sales data load because I am able to login to inside sales." );
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}
	
	/**
	 * Fetch the impressions list and save in is_impressions 
	 * @param limit
	 * @param cookies
	 * @param insideSalesRestClient
	 * @param isFullLoad
	 */
	
	private void readAndSaveImpressions(Integer limit, String cookies,
			InsideSalesRestClient insideSalesRestClient, String isFullLoad) {
		// Truncate the table
		try {
			int totalSize = 0;
			boolean exit = true;
			String startDate = null;
			String currentDate = null;  // today - 1 ;
					
			DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			currentDate = dateformat.format(date);
			
			
			if(StringUtils.equalsIgnoreCase(isFullLoad, ProviderConstants.IS_TRUE)){
				// Truncate the table is_impressions
				leadProcessingBO.truncateData(4);
				startDate = leadProcessingBO.getPropertyValue(ProviderConstants.IS_DATALOAD_START_DATE);
			}else{
				// yesterday as start date
				startDate = dateformat.format(yesterday());
			}
			
			// logger.info("Current Date ::"+currentDate);			
			// logger.info("Start Date ::"+startDate);
			
			while (exit) {
				int size = 0;
				String response = null;
				response = insideSalesRestClient.postData(
						getInsideSalesgetRequestJSON(startDate,
								ProviderConstants.INSIDE_SALES_CALLDATE_OPERATION,
								Integer.toString(totalSize),
								Integer.toString(limit),
								ProviderConstants.INSIDE_SALES_IMP_OPERATION,
								ProviderConstants.INSIDE_SALES_BETWEEN_OPERATION,
								currentDate), 
								isApiBaseUrl, cookies);
				
				if (null!=response && StringUtils.isNotBlank(response)){
					List<ImpressionsVO> imps = new ArrayList<ImpressionsVO>();
					imps = formImpObject(response);
					size = imps.size();

					if (size > 0) {
						totalSize = totalSize + size;
						// Create the list of objects
						List<Impressions> impsList = mapImps(imps);

						// Insert to DB
						leadProcessingBO.saveImpressionsList(impsList);
					}
				}
				if (0 == size) {
					exit = false;
				}
			}

			logger.info("Created total " + totalSize + "impressions");

		} catch (com.newco.marketplace.exception.core.BusinessServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private Date yesterday() {
	    final Calendar cal = Calendar.getInstance();
	    cal.add(Calendar.DATE, -1);
	    return cal.getTime();
	}
	
	/**
	 * Fetch the cdr list and save in is_cdr 
	 * @param limit
	 * @param cookies
	 * @param insideSalesRestClient
	 * @param isFullLoad
	 */
	
	private void readAndSaveCDRs(Integer limit, String cookies,
			InsideSalesRestClient insideSalesRestClient, String isFullLoad) {
		// Truncate the table
		try {
			int totalSize = 0;
			boolean exit = true;
			String startId = "0";
			
			// One time data load will happen outside the system (using files).
			isFullLoad = ProviderConstants.IS_FALSE;
			
			if(StringUtils.equalsIgnoreCase(isFullLoad, ProviderConstants.IS_TRUE)){
				// Truncate the table is_cdr
				leadProcessingBO.truncateData(3);
			}else{
				// Get the latest id from the table and get the data after that.
			    // startId = Integer.toString(leadProcessingBO.getLatestId(3));
				
				// input parameter id will give only current dates data.
				// batch will be executed only once a day
				startId = "0";
			}
			
			while (exit) {
				int size = 0;
				String response = null;
				response = insideSalesRestClient.postData(
						getInsideSalesgetRequestJSON(startId,
								ProviderConstants.INSIDE_SALES_ID_OPERATION,
								Integer.toString(totalSize),
								Integer.toString(limit),
								ProviderConstants.INSIDE_SALES_CDR_OPERATION,
								ProviderConstants.INSIDE_SALES_GTRTHN_OPERATION,null), 
								isApiBaseUrl, cookies);
				
				if (null!=response && StringUtils.isNotBlank(response)){
					List<CdrVO> cdrs = new ArrayList<CdrVO>();
					cdrs = formCDRObject(response);
					size = cdrs.size();

					if (size > 0) {
						totalSize = totalSize + size;
						// Create the list of objects
						List<Cdr> cdrList = mapCdrs(cdrs);

						// Insert to DB
						leadProcessingBO.saveCdrList(cdrList);
					}
				}
				if (0 == size) {
					exit = false;
				}
			}

			logger.info("Created total " + totalSize + "crds");

		} catch (com.newco.marketplace.exception.core.BusinessServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Fetch the campaign list and save in is_campaigns 
	 * @param limit
	 * @param cookies
	 * @param insideSalesRestClient
	 * @param startDate
	 */
	
	private void readAndSaveCampaigns(Integer limit, String cookies,
			InsideSalesRestClient insideSalesRestClient) {
		// Truncate the table
		try {
			int totalSize = 0;
			boolean exit = true;
			String startId = "0";
			
			// Truncate the table is_campaigns
			leadProcessingBO.truncateData(2);
			
			while (exit) {
				int size = 0;
				String response = null;
				response = insideSalesRestClient.postData(
						getInsideSalesgetRequestJSON(startId,
								ProviderConstants.INSIDE_SALES_CAMPID_OPERATION,
								Integer.toString(totalSize),
								Integer.toString(limit),
								ProviderConstants.INSIDE_SALES_CAMP_OPERATION,
								ProviderConstants.INSIDE_SALES_GTRTHN_OPERATION,null), 
								isApiBaseUrl, cookies);
				
				if (null!=response && StringUtils.isNotBlank(response)){
					List<CampaignVO> campaigns = new ArrayList<CampaignVO>();
					campaigns = formCampObject(response);
					size = campaigns.size();

					if (size > 0) {
						totalSize = totalSize + size;
						// Create the list of objects
						List<Campaign> campList = mapCamp(campaigns);

						// Insert to DB
						leadProcessingBO.saveCampaignList(campList);
					}
				}
				if (0 == size) {
					exit = false;
				}
			}

			logger.info("Created total " + totalSize + "campaigns");

		} catch (com.newco.marketplace.exception.core.BusinessServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Fetch the employee list and save in is_employees 
	 * @param limit
	 * @param cookies
	 * @param insideSalesRestClient
	 * @param startDate
	 */
	private void readAndSaveEmployees(Integer limit, String cookies,
			InsideSalesRestClient insideSalesRestClient) {
		// Truncate the table
		try {
			int totalSize = 0;
			boolean exit = true;
			String startId = "0";
			
			// Truncate the table is_employees
			leadProcessingBO.truncateData(1);
			
			while (exit) {
				int size = 0;
				String response = null;
				response = insideSalesRestClient.postData(
						getInsideSalesgetRequestJSON(startId,
								ProviderConstants.INSIDE_SALES_ID_OPERATION,
								Integer.toString(totalSize),
								Integer.toString(limit),
								ProviderConstants.INSIDE_SALES_EMP_OPERATION,
								ProviderConstants.INSIDE_SALES_GTRTHN_OPERATION,null)
								, isApiBaseUrl, cookies);
				
				if (null!=response && StringUtils.isNotBlank(response)){
					List<EmployeeVO> employees = new ArrayList<EmployeeVO>();
					employees = formEmpObject(response);
					size = employees.size();
	
					if (size > 0) {
						totalSize = totalSize + size;
						// Create the list of objects
						List<Employee> empList = mapEmp(employees);
	
						// Insert to DB
						leadProcessingBO.saveEmployeesList(empList);
					}
				}
				if (0 == size) {
					exit = false;
				}
			}

			logger.info("Created total " + totalSize + "employees");

		} catch (com.newco.marketplace.exception.core.BusinessServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 
	 * @param campaigns
	 * @return
	 */
	private List<Cdr> mapCdrs(List<CdrVO> cdrs){
		
		List<Cdr> cdrList = new ArrayList<Cdr>();
		
		for(CdrVO cdrvo : cdrs){
			Cdr crd = new Cdr(cdrvo.getId(), null, Integer.toString(cdrvo.getLead_id()),
					null, null, null, cdrvo.getDatetime(),
					cdrvo.getDuration().doubleValue(),0.0d, cdrvo.getEmployee_user_id(),
					null,null, null,null);			
			cdrList.add(crd);
		}
		return cdrList;
	}
	
	/**
	 * 
	 * @param campaigns
	 * @return
	 */
	private List<Impressions> mapImps(List<ImpressionsVO> imps){
		
		List<Impressions> impsList = new ArrayList<Impressions>();
		
		for(ImpressionsVO impvo : imps){
			
			Impressions imp = new Impressions(impvo.getId(), null,
					Integer.toString(impvo.getContact_info_id()),
					null, impvo.getEmployee_user_id(),
					null, impvo.getCall_type(), null,
					null!=impvo.getTask_type_id() ? impvo.getTask_type_id() :0 ,
					null!=impvo.getCall_duration() ? impvo.getCall_duration() :0 ,
					null!=impvo.getTotal_talk_time() ? impvo.getTotal_talk_time() :0,
					null!=impvo.getTotal_ring_time() ? impvo.getTotal_ring_time() :0,
					null!=impvo.getDial_attempt() ? impvo.getDial_attempt() : 0, 
					null!=impvo.getPhone() ? impvo.getPhone():"",
					null!=impvo.getDialer_initiative_id() ? impvo.getDialer_initiative_id() :0,
					null!=impvo.getDialer_initiative_name() ? impvo.getDialer_initiative_name() : "",
					null!=impvo.getCall_date_time() ? impvo.getCall_date_time() :"",
					null!=impvo.getCall_subject() ? impvo.getCall_subject() :"",
					null!=impvo.getCall_description() ? impvo.getCall_description() :"",
					null!=impvo.getCall_result() ? impvo.getCall_result() :"",
					null!=impvo.getCall_type() ? impvo.getCall_type() :"",
					null!=impvo.getRecording_url() ? impvo.getRecording_url() :"",
					null!=impvo.getCdr_id() ? impvo.getCdr_id() :0,
					null!=impvo.getImpression_type_id() ? impvo.getImpression_type_id() :0,
					null!=impvo.getTask_id() ? impvo.getTask_id() :0);
				
			impsList.add(imp);
		}
		return impsList;
	}
	
	
	/**
	 * 
	 * @param campaigns
	 * @return
	 */
	private List<Campaign> mapCamp(List<CampaignVO> campaigns){
		
		List<Campaign> campList = new ArrayList<Campaign>();
		
		for(CampaignVO camp : campaigns){
			String attendees = null;
			if(null!=camp.getAttendee_user_ids()){
				attendees = StringUtils.join(camp.getAttendee_user_ids().toArray(),",");
			}
			
			String deleted = "No";
			
			if(camp.isDeleted()){
				deleted = "Yes";
			}
			
			Campaign campaign = new Campaign(camp.getCampaign_id(),deleted , 
					camp.getCampaign_name(), camp.getStart_date(), camp.getPort_count(), camp.getCaller_id(), 
					0, camp.getCampaign_category_id(), Integer.toString(camp.getEmail_notice_id()), Integer.toString(camp.getFax_notice_id()), 
					null, camp.getMinutes_between_calls().doubleValue(), camp.getDescription(), 
					null, attendees);
			
			campList.add(campaign);
		}
		return campList;
	}
	
	/**
	 * 
	 * @param employees
	 * @return
	 */
	
	private List<Employee> mapEmp(List<EmployeeVO> employees){
		List<Employee> empList = new ArrayList<Employee>();
		for(EmployeeVO employeeVO : employees){
			String compSettings = null;
			String divisions = null;
			if(null!=employeeVO.getCompany_settings()){
				compSettings = StringUtils.join(employeeVO.getCompany_settings().toArray(),",");
			}
			
			if(null!=employeeVO.getDivision_ids()){
				divisions = StringUtils.join(employeeVO.getDivision_ids().toArray(),",");				
				divisions = divisions + String.valueOf(employeeVO.getDivision_id());
			}
			Integer zip = 0;
			if(null!=employeeVO.getZip()){
				zip = Integer.valueOf(employeeVO.getZip());
			}
			
			Employee emp = new Employee(employeeVO.getId(), employeeVO.isDeleted(), 
					employeeVO.getName_prefix(),
					employeeVO.getFirst_name()+" "+employeeVO.getLast_name(),
					employeeVO.getTitle(),employeeVO.getUsername(),
					employeeVO.getBirthdate(),
					employeeVO.getReports_to_employee_user_id(),
					null,employeeVO.getAssistant_employee_user_id(), null,
					employeeVO.getEmployee_identifier(),
					null,
					employeeVO.getColor(),
					compSettings,
					String.valueOf(employeeVO.getTeam_id()),
					divisions,
					employeeVO.isWarn_on_exit(),
					employeeVO.getPhone(),
					employeeVO.getOther_phone(),
					employeeVO.getHome_phone(),
					employeeVO.getMobile_phone(),
					employeeVO.getFax(),
					employeeVO.getEmail(),
					employeeVO.getWebsite(),
					String.valueOf(employeeVO.getTime_zone_id()),
					String.valueOf(employeeVO.getZone_id()),
					String.valueOf(employeeVO.getPerm_id()),
					String.valueOf(employeeVO.getStart_page_saved_view_id()),
					String.valueOf(employeeVO.getDefault_results_per_page()),
					employeeVO.getSmtp_email_username(),
					employeeVO.getCreated_by_user_id(),
					null,
					employeeVO.getDate_created(),
					employeeVO.getModified_by_user_id(),
					null,
					employeeVO.getDate_modified(),
					employeeVO.getAddr1(),
					employeeVO.getAddr2(),
					employeeVO.getCity(),
					employeeVO.getState(),
					zip,
					employeeVO.getCountry()
					);
			empList.add(emp);
		}
		return empList;
	}

	/**
	 * @param request
	 * @param response
	 * @return
	 */
	private String getInsideSalesLoginRequestJSON() {
		LoginInsideSalesRequest insideSalesLoginRequest = new LoginInsideSalesRequest();
		insideSalesLoginRequest
				.setOperation(ProviderConstants.INSIDE_SALES_LOGIN_OPERATION);
		List<String> parameters = new ArrayList<String>();
		parameters.add(isApiUserName);
		parameters.add(isApiPassword);
		parameters.add(isApiToken);
		insideSalesLoginRequest.setParameters(parameters);
		String insideSaleLoginReq = formJson(insideSalesLoginRequest);
		logger.info("insideSaleLoginReq::"+insideSaleLoginReq);
		return insideSaleLoginReq;
	}
	
	
	/**
	 * 
	 * @param response
	 * @return
	 */
	private static List<EmployeeVO> formEmpObject(String response) {
		List<EmployeeVO> emps = new ArrayList<EmployeeVO>();
		JSONArray j = (JSONArray) JSONSerializer.toJSON(response);
		for(int index = 0; index <j.length();index++){
			// System.out.println(j.get(index));
			JSONObject ob= (JSONObject) j.get(index);			
			EmployeeVO lo = (EmployeeVO) JSONObject.toBean(ob, EmployeeVO.class);
			emps.add(lo);
		}	
		return emps;
	}
	

	/**
	 * 
	 * @param response
	 * @return
	 */
	private static List<CampaignVO> formCampObject(String response) {
		List<CampaignVO> camps = new ArrayList<CampaignVO>();
		JSONArray j = (JSONArray) JSONSerializer.toJSON(response);
		for(int index = 0; index <j.length();index++){
			// System.out.println(j.get(index));
			JSONObject ob= (JSONObject) j.get(index);
			
			CampaignVO co = (CampaignVO) JSONObject.toBean(ob, CampaignVO.class);
			camps.add(co);
		}	
		return camps;
	}
	
	
	/**
	 * 
	 * @param response
	 * @return
	 */
	private static List<CdrVO> formCDRObject(String response) {
		List<CdrVO> cdrs = new ArrayList<CdrVO>();
		JSONArray j = (JSONArray) JSONSerializer.toJSON(response);
		for(int index = 0; index <j.length();index++){
			// System.out.println(j.get(index));
			JSONObject ob= (JSONObject) j.get(index);
			
			CdrVO co = (CdrVO) JSONObject.toBean(ob, CdrVO.class);
			cdrs.add(co);
		}	
		return cdrs;
	}
	
	/**
	 * 
	 * @param response
	 * @return
	 */
	private static List<ImpressionsVO> formImpObject(String response) {
		List<ImpressionsVO> imps = new ArrayList<ImpressionsVO>();
		JSONArray j = (JSONArray) JSONSerializer.toJSON(response);
		for(int index = 0; index <j.length();index++){
			// System.out.println(j.get(index));
			JSONObject ob= (JSONObject) j.get(index);
			
			ImpressionsVO co = (ImpressionsVO) JSONObject.toBean(ob, ImpressionsVO.class);
			imps.add(co);
		}	
		return imps;
	}

	/**
	 * 
	 * @param insideSalesLoginRequest
	 * @return
	 */
	private String formJson(LoginInsideSalesRequest insideSalesLoginRequest) {
		// TODO Auto-generated method stub
		String insideSaleLoginReq = null;
		JSON j = JSONSerializer.toJSON(insideSalesLoginRequest);
		insideSaleLoginReq = j.toString();
		return insideSaleLoginReq;
	}
	
	/**
	 * 
	 * @param startdate
	 * @param field
	 * @param offset
	 * @param limit
	 * @param method
	 * @param operator
	 * @return
	 */
	private static String getInsideSalesgetRequestJSON(String startdate,
			String field, String offset, 
			String limit, String method, 
			String operator,String enddate) {
		GetInsideSalesRequest insideSalesGetRequest = new GetInsideSalesRequest();

		// Create parameter list
		GetISDataParameters startParam = new GetISDataParameters();
		startParam.setField(field);
		startParam.setOperator(operator);

		List<String> startValues = new ArrayList<String>();
		startValues.add(startdate);
		if(null!=enddate){
			startValues.add(enddate);
		}
		startParam.setValues(startValues);

		List<GetISDataParameters> parameters = new ArrayList<GetISDataParameters>();
		parameters.add(startParam);
		insideSalesGetRequest.setParameters(parameters);
		String insideSaleLoginReq = formJson(insideSalesGetRequest);
		logger.info("request::" + insideSaleLoginReq);
		String parameterv = insideSaleLoginReq.split(":", 2)[0];
		String filter = insideSaleLoginReq.split(":", 2)[1];
		parameterv = parameterv.substring(1, parameterv.length());
		filter = filter.substring(0, filter.length() - 1);
		filter = "[" + filter + "," + offset + "," + limit + "]" + "}";
		String firstSet = "{\"operation\":\"" + method + "\",";
		insideSaleLoginReq = firstSet + parameterv + ":" + filter;
		logger.info("insideSaleLoginReq::" + insideSaleLoginReq);
		return insideSaleLoginReq;
	}
	
	
	/**
	 * 
	 * @param insideSalesGetRequest
	 * @return
	 */
	private static String formJson(GetInsideSalesRequest insideSalesGetRequest) {
		// TODO Auto-generated method stub
		String insideSaleLoginReq = null;
		JSON j = JSONSerializer.toJSON(insideSalesGetRequest);
		insideSaleLoginReq  = j.toString();  
		//System.out.println(insideSaleLoginReq);
		return insideSaleLoginReq;
	}
	

	public String getIsApiUserName() {
		return isApiUserName;
	}

	public void setIsApiUserName(String isApiUserName) {
		this.isApiUserName = isApiUserName;
	}

	public String getIsApiPassword() {
		return isApiPassword;
	}

	public void setIsApiPassword(String isApiPassword) {
		this.isApiPassword = isApiPassword;
	}

	public String getIsApiToken() {
		return isApiToken;
	}

	public void setIsApiToken(String isApiToken) {
		this.isApiToken = isApiToken;
	}

	public ILeadProcessingBO getLeadProcessingBO() {
		return leadProcessingBO;
	}
	public void setLeadProcessingBO(ILeadProcessingBO leadProcessingBO) {
		this.leadProcessingBO = leadProcessingBO;
	}
	public String getIsApiBaseUrl() {
		return isApiBaseUrl;
	}

	public void setIsApiBaseUrl(String isApiBaseUrl) {
		this.isApiBaseUrl = isApiBaseUrl;
	}
}
