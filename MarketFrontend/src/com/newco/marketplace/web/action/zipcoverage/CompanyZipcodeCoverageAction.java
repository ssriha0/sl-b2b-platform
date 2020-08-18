package com.newco.marketplace.web.action.zipcoverage;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.google.gson.Gson;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.buyersurvey.BuyerSurveyDTO;
import com.newco.marketplace.dto.vo.zipcoverage.BuyerSpnListDTO;
import com.newco.marketplace.dto.vo.zipcoverage.ProviderListDTO;
import com.newco.marketplace.dto.vo.zipcoverage.QuestionsAnswersDTO;
import com.newco.marketplace.dto.vo.zipcoverage.StateNameDTO;
import com.newco.marketplace.dto.vo.zipcoverage.ZipCoverageFiltersDTO;
import com.newco.marketplace.dto.vo.zipcoverage.ZipcodeDTO;
import com.newco.marketplace.interfaces.ZipCoverageConstants;
import com.newco.marketplace.util.PropertiesUtils;
import com.newco.marketplace.web.action.base.SLSimpleBaseAction;
import com.newco.marketplace.web.delegates.zipcoverage.IZipCoverageDelegate;
import com.opensymphony.xwork2.ModelDriven;

public class CompanyZipcodeCoverageAction  extends SLSimpleBaseAction implements ZipCoverageConstants,ServletRequestAware,ModelDriven<ZipCoverageFiltersDTO>{
	private static final Logger logger = Logger.getLogger("CompanyZipcodeCoverageAction");
	private HttpServletRequest request;
	private IZipCoverageDelegate zipCoverageDelegate;
	private String jsonString;
	private ZipCoverageFiltersDTO zipCoverageFiltersDTO=new ZipCoverageFiltersDTO();
	
	public String displayPage() throws Exception
	{	
		logger.info("Inside BuyerSurveyManagerAction::displayPage() method");
		SecurityContext soContxt=(SecurityContext) getSession().getAttribute("SecurityContext");
		int vendorId = soContxt.getCompanyId();

		List<ZipcodeDTO> zipList=null;
		List<ProviderListDTO> providersList=null;
		List<BuyerSpnListDTO> spnList=null;
		List<QuestionsAnswersDTO> faqList=null;
		String json=null;
		List<StateNameDTO> stateNameList=zipCoverageDelegate.getStateNames(String.valueOf(vendorId));
		spnList=zipCoverageDelegate.getBuyerSpnDetails(String.valueOf(vendorId));
		faqList=zipCoverageDelegate.getFaqAndAnswers(ZIPCOVERAGEFAQ);
		/*if(stateNameList!=null && stateNameList.size()>0){
			zipList=zipCoverageDelegate.getZipCodes(String.valueOf(vendorId), stateNameList.get(0).getStateCode());
			providersList=zipCoverageDelegate.getServiceProviders(String.valueOf(vendorId), stateNameList.get(0).getStateCode(), null);
			spnList=zipCoverageDelegate.getBuyerSpnDetails(String.valueOf(vendorId));
			faqList=zipCoverageDelegate.getFaqAndAnswers();
			String[] zipcodeArray=zipCoverageDelegate.getSelectedZipCodesByFirmIdAndFilter(vendorId,  stateNameList.get(0).getStateCode(), null, null, null, null);
			Gson gson = new Gson();
			json=gson.toJson(zipcodeArray);
			json="{zipcodes :"+json+"}";
		}*/
		
		//getRequest().setAttribute(SELECTEDZIPJSON, json);
		zipCoverageFiltersDTO.setStateNameList(stateNameList);
		//zipCoverageFiltersDTO.setZipList(zipList);
		//zipCoverageFiltersDTO.setProvidersList(providersList);
		zipCoverageFiltersDTO.setSpnList(spnList);
		zipCoverageFiltersDTO.setFaqList(faqList);
		zipCoverageFiltersDTO.setMapboxUrl(PropertiesUtils.getPropertyValue(Constants.AppPropConstants.MAPBOX_URL));
		
		return SUCCESS;
		
	}
	
	public String getSelectedZipCodeValues() throws Exception{
		logger.info("Inside BuyerSurveyManagerAction:getSelectedZipCodeValues() method");
		SecurityContext soContxt=(SecurityContext) getSession().getAttribute("SecurityContext");
		int vendorId = soContxt.getCompanyId();
		String filter1=request.getParameter(FILTER1);
		String filter2=request.getParameter(FILTER2);
		String filter3=request.getParameter(FILTER3);
		String filter4=request.getParameter(FILTER4);
		String stateCode=null;
		String zipCode=null;
		Integer providerId=null;
		Integer spnId=null;
		Integer spnProviderId=null;
		String spnStateCode=null;
		String spnZipCode=null;
		
		if(filter1!=null && filter1.equals(STATECODE) && request.getParameter(FILTERVALUE1)!=null && !request.getParameter(FILTERVALUE1).equals(NULL))
			stateCode=request.getParameter(FILTERVALUE1);
		
		if(filter2!=null && filter2.equals(ZIPCODE)  && request.getParameter(FILTERVALUE2)!=null && !request.getParameter(FILTERVALUE2).equals(NULL))
			zipCode=request.getParameter(FILTERVALUE2);
		
		if(filter3!=null && filter3.equals(RESOURCEID)  && request.getParameter(FILTERVALUE3)!=null && !request.getParameter(FILTERVALUE3).equals(NULL))
			providerId=Integer.parseInt(request.getParameter(FILTERVALUE3));
		
		if(filter1!=null && filter1.equals(BUYERSPNID))
			spnId=Integer.parseInt(request.getParameter(FILTERVALUE1));
		
		if(filter2!=null && filter2.equals(BUYERSPNPROVID) && request.getParameter(FILTERVALUE2)!=null && !request.getParameter(FILTERVALUE2).equals(NULL))
			spnProviderId=Integer.parseInt(request.getParameter(FILTERVALUE2));
		
		if(filter3!=null && filter3.equals(SPNSTATE)  && request.getParameter(FILTERVALUE3)!=null && !request.getParameter(FILTERVALUE3).equals(NULL))
			spnStateCode=request.getParameter(FILTERVALUE3);
		
		if(filter4!=null && filter4.equals(SPNZIP)  && request.getParameter(FILTERVALUE4)!=null && !request.getParameter(FILTERVALUE4).equals(NULL))
			spnZipCode=request.getParameter(FILTERVALUE4);
		
		String[] zipcodeArray=zipCoverageDelegate.getSelectedZipCodesByFirmIdAndFilter(vendorId, stateCode, zipCode, providerId, 
				spnId, spnProviderId, spnStateCode, spnZipCode);
		Gson gson = new Gson();
		String json=gson.toJson(zipcodeArray);
		//json="{\"zipcodes\": "+json+"}";
		setJsonString(json);
		return JSON;
	}
	
	public String loadFiltersWithMetaData() throws Exception{
		logger.info("Inside BuyerSurveyManagerAction:loadFiltersWithMetaData() method");
		SecurityContext soContxt=(SecurityContext) getSession().getAttribute("SecurityContext");
		int vendorId = soContxt.getCompanyId();
		String changedFilter=request.getParameter(CHANGEDFILTER);
		String filterValue1=null;
		String filterValue2=null;
		if(!request.getParameter(FILTERVALUE1).equals(NULL))
			filterValue1=request.getParameter(FILTERVALUE1);
		if(request.getParameter(FILTERVALUE2)!=null && !request.getParameter(FILTERVALUE2).equals(NULL))
			filterValue2=request.getParameter(FILTERVALUE2);
		List<ProviderListDTO> providersList=null;
		List<ProviderListDTO> spnProvidersList=null;
		if(changedFilter.equals(STATE) && filterValue1!=null){
			List<ZipcodeDTO> zipList=zipCoverageDelegate.getZipCodes(String.valueOf(vendorId), filterValue1);
			providersList=zipCoverageDelegate.getServiceProviders(String.valueOf(vendorId), filterValue1, null);
			zipCoverageFiltersDTO.setZipList(zipList);
			zipCoverageFiltersDTO.setProvidersList(providersList);
		}
		else if(changedFilter.equals(ZIP)){
			providersList=zipCoverageDelegate.getServiceProviders(String.valueOf(vendorId), filterValue1, filterValue2);
			zipCoverageFiltersDTO.setProvidersList(providersList);
	
		}
		/*else{
			spnProvidersList=zipCoverageDelegate.getBuyerSpnServiceProviders(String.valueOf(vendorId), filterValue1);
			zipCoverageFiltersDTO.setSpnProvidersList(spnProvidersList);
		}*/
		return FILTER;
	}
	
	public String loadSpnFiltersWithMetaData() throws Exception{
		logger.info("Inside BuyerSurveyManagerAction:loadSpnFiltersWithMetaData() method");
		SecurityContext soContxt=(SecurityContext) getSession().getAttribute("SecurityContext");
		int vendorId = soContxt.getCompanyId();
		String changedFilter=request.getParameter(CHANGEDFILTER);
		String spnId=null;
		String spnProvId=null;
		String stateCode=null;
		List<ProviderListDTO> spnProvidersList=null;
		List<StateNameDTO> spnStateNameList=null;
		List<ZipcodeDTO> spnZipList=null;
		if(!request.getParameter(FILTERVALUE1).equals(NULL))
			spnId=request.getParameter(FILTERVALUE1);
		if(request.getParameter(FILTERVALUE2)!=null && !request.getParameter(FILTERVALUE2).equals(NULL))
			spnProvId=request.getParameter(FILTERVALUE2);
		if(request.getParameter(FILTERVALUE3)!=null && !request.getParameter(FILTERVALUE3).equals(NULL))
			stateCode=request.getParameter(FILTERVALUE3);
		
		if(changedFilter.equals(SPN)){
			spnProvidersList=zipCoverageDelegate.getBuyerSpnServiceProviders(String.valueOf(vendorId), spnId);
			spnStateNameList=zipCoverageDelegate.getSpnStateNames(String.valueOf(vendorId), spnId, null);
			spnZipList=zipCoverageDelegate.getSpnZipCodes(String.valueOf(vendorId), spnId, null, null);
		}
		else if(changedFilter.equals(SPNPROVIDER)){
			spnStateNameList=zipCoverageDelegate.getSpnStateNames(String.valueOf(vendorId), spnId, spnProvId);
			spnZipList=zipCoverageDelegate.getSpnZipCodes(String.valueOf(vendorId), spnId, spnProvId, null);
		}
		else{
			spnZipList=zipCoverageDelegate.getSpnZipCodes(String.valueOf(vendorId), spnId, spnProvId, stateCode);
		}
		
		zipCoverageFiltersDTO.setSpnProvidersList(spnProvidersList);
		zipCoverageFiltersDTO.setSpnStateNameList(spnStateNameList);
		zipCoverageFiltersDTO.setSpnZipList(spnZipList);
		return FILTER;
	}
	
	public void setServletRequest(HttpServletRequest arg0) {
		request = arg0;
	}

	public IZipCoverageDelegate getZipCoverageDelegate() {
		return zipCoverageDelegate;
	}
	public void setZipCoverageDelegate(IZipCoverageDelegate zipCoverageDelegate) {
		this.zipCoverageDelegate = zipCoverageDelegate;
	}

	public String getJsonString() {
		return jsonString;
	}

	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

	public ZipCoverageFiltersDTO getZipCoverageFiltersDTO() {
		return zipCoverageFiltersDTO;
	}

	public void setZipCoverageFiltersDTO(ZipCoverageFiltersDTO zipCoverageFiltersDTO) {
		this.zipCoverageFiltersDTO = zipCoverageFiltersDTO;
	}

	@Override
	public ZipCoverageFiltersDTO getModel() {
		// TODO Auto-generated method stub
		return zipCoverageFiltersDTO;
	}
	
}
