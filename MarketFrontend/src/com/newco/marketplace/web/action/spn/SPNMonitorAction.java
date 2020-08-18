/**
 * 
 */
package com.newco.marketplace.web.action.spn;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.spn.ISPNMonitorBO;
import com.newco.marketplace.business.iBusiness.spn.ISelectProviderNetworkBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.spn.BackgroundCheckHistoryVO;
import com.newco.marketplace.dto.vo.spn.BackgroundFilterProviderVO;
import com.newco.marketplace.dto.vo.spn.BackgroundInfoProviderVO;
import com.newco.marketplace.dto.vo.spn.ComplianceCriteriaVO;
import com.newco.marketplace.dto.vo.spn.SPNCompanyProviderRequirementsVO;
import com.newco.marketplace.dto.vo.spn.SPNComplianceVO;
import com.newco.marketplace.dto.vo.spn.SPNDocumentVO;
import com.newco.marketplace.dto.vo.spn.SPNMainMonitorModel;
import com.newco.marketplace.dto.vo.spn.SPNMainMonitorVO;
import com.newco.marketplace.dto.vo.spn.SPNMonitorVO;
import com.newco.marketplace.dto.vo.spn.SPNProvUploadedDocsVO;
import com.newco.marketplace.dto.vo.spn.SPNSignAndReturnDocumentVO;
import com.newco.marketplace.dto.vo.spn.SearchBackgroundInfoProviderVO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.interfaces.SPNConstants;
import com.newco.marketplace.util.SortUtil;
import com.newco.marketplace.utils.DateUtils;
import com.newco.marketplace.utils.MoneyUtil;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.utils.FileUpload;
import com.newco.marketplace.web.utils.FileUtils;
import com.newco.marketplace.web.utils.SecurityChecker;
import com.opensymphony.xwork2.ModelDriven;
import com.servicelive.domain.spn.network.RequirementVO;
/**
 * This is the main action class responsible for loading spn main monitor
 * details.
 */

//Fix for issue SL-19316 : Commenting the annotation
//@NonSecurePage
public class SPNMonitorAction extends SLBaseAction 
implements ModelDriven<SPNMainMonitorModel>{	

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

	/**
	 * @return the buyerId
	 */
	public Integer getBuyerId() {
		return buyerId;
	}

	/**
	 * @param buyerId the buyerId to set
	 */
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}

	private static final long serialVersionUID = 511127434320807889L;	
	private static final Logger logger = Logger.getLogger(SPNMonitorAction.class);
	
	private SPNMainMonitorModel model = new SPNMainMonitorModel();
	private ISPNMonitorBO spnMonitorBO;	
	private ISelectProviderNetworkBO spnCreateUpdateBO;
	private FileUpload fileUpload = null;
	private String targetExternalAction = "spnMonitorNetwork_display" ; //default routing 
	private String resultUrl = null;
	private Integer buyerId;
	
	public SPNMonitorAction(ISPNMonitorBO spnMonitorBO,ISelectProviderNetworkBO spnCreateUpdateBO){
		this.spnMonitorBO = spnMonitorBO;
		this.spnCreateUpdateBO = spnCreateUpdateBO;
	}
	
	public String loadSPNMonitor(){
		SecurityContext securityContext = (SecurityContext) getSession().getAttribute(Constants.SESSION.SECURITY_CONTEXT);
		Integer vendorId = securityContext.getCompanyId();
		String loggedInUsername = securityContext.getUsername();

		try {
			if(StringUtils.isNotBlank(model.getAcceptInvite())){ 
				spnMonitorBO.acceptInvite(model.getSelectedSpnId(),vendorId, loggedInUsername);				
			}
			List<String> selectedBuyerValues = null;
			List<String> selectedBuyers = model.getSelectedBuyerValues();
			if(null != selectedBuyers){
				selectedBuyerValues = new ArrayList<String>(selectedBuyers);
			}
			List<String> selectedMemStatus =model.getSelectedMemStatus();
			List<String> selectedFilterMemStatus = model.getSelectedFilterMemStatus();
			List<String> selectedFilterMemStatusValues = null;
			//to remove null values from buyer filter
			selectedBuyerValues = setFilter(selectedBuyerValues);
			selectedFilterMemStatusValues = setFilter(selectedMemStatus);
			Boolean filterAppliedInd = false;
			if((selectedBuyerValues!=null && selectedBuyerValues.size()>0) ||( selectedMemStatus!=null && selectedMemStatus.size()>0)){
				filterAppliedInd = true;
			}
			if(null != model.getMemStatusResetInd() && model.getMemStatusResetInd().intValue()== 1){
				selectedMemStatus = null;
				selectedFilterMemStatus = null;
				filterAppliedInd = true;
			}
			else if(null == selectedMemStatus || selectedMemStatus.size() == 0){
				if(null!= getSession().getAttribute("selectedMemStatus")){
					selectedMemStatus = (ArrayList<String>)getSession().getAttribute("selectedMemStatus");
					if(null!= getSession().getAttribute("selectedFilterMemStatus")){
						selectedFilterMemStatus = (ArrayList<String>)getSession().getAttribute("selectedFilterMemStatus");
					}
				}
				else{
					selectedMemStatus = null;
					selectedFilterMemStatus = null;
				}
			}
			if(null != model.getBuyerResetInd() && model.getBuyerResetInd().intValue() == 1){
				selectedBuyerValues = null;
				filterAppliedInd = true;
			}
			else if(null == selectedBuyerValues || selectedBuyerValues.size() == 0){
				if(null!= getSession().getAttribute("selectedBuyerValues")){
					selectedBuyerValues = (ArrayList<String>)getSession().getAttribute("selectedBuyerValues");
				}
				else{
					selectedBuyerValues = null;
				}
			}
			List<SPNMainMonitorVO> unsortedSpnMonitorList= new ArrayList<SPNMainMonitorVO>();
			if(filterAppliedInd){
				unsortedSpnMonitorList = spnMonitorBO.getSPMMainMonitorListWithFilters(vendorId,filterAppliedInd,selectedBuyerValues,selectedFilterMemStatusValues);
			}
			else{
				selectedBuyerValues = null;
				selectedFilterMemStatusValues = null;
				selectedFilterMemStatusValues = null;
				unsortedSpnMonitorList = spnMonitorBO.getSPMMainMonitorList(vendorId);
			}
			List<SPNMainMonitorVO> spnMonitorList = new SortUtil<SPNMainMonitorVO>().sort(unsortedSpnMonitorList, new String[]{"getMembershipStatusIndex"});
			List<LookupVO>buyerFilter = new ArrayList<LookupVO>();
			List<LookupVO>membershipStatusFilter = new ArrayList<LookupVO>();
			List<String> businessNames = new ArrayList<String>();
			List<String> memberStatuses = new ArrayList<String>();
			List<String> status = new ArrayList<String>();
			if(filterAppliedInd){
				if(null!= getSession().getAttribute("buyerFilter")){
					buyerFilter = (ArrayList<LookupVO>)getSession().getAttribute("buyerFilter");
				}
				if(null!= getSession().getAttribute("membershipStatusFilter")){
					membershipStatusFilter = (ArrayList<LookupVO>)getSession().getAttribute("membershipStatusFilter");
				}
			}
			int countMemIncomplete=0;
			for(SPNMainMonitorVO spnMainMonitorVO : spnMonitorList){
				if(countMemIncomplete!=1 && SPNConstants.SPN_MEMBERSHIP_INCOMPLETE.equalsIgnoreCase(spnMainMonitorVO.getMembershipStatus())
						&& Arrays.asList(SPNConstants.SPN_MEMBERSHIP_INCOMPLETE_FIRM_STATES).contains(spnMainMonitorVO.getProviderFirmState())){
					model.setIncompleteSpnInd(true);
					countMemIncomplete =1;
				}
				if(!filterAppliedInd){
					addFilters(buyerFilter,businessNames,membershipStatusFilter,memberStatuses,spnMainMonitorVO,status);
				}

			}
			model.setSpnMonitorList(spnMonitorList);
			model.setBuyerFilter(buyerFilter);
			model.setMembershipStatusFilter(membershipStatusFilter);
			model.setSelectedBuyerValues(selectedBuyerValues);
			model.setSelectedFilterMemStatus(selectedFilterMemStatusValues);
			model.setSelectedMemStatus(selectedMemStatus);
			setFilterValuesInSession(model);

		} catch (BusinessServiceException e) {
			logger.error("Error retriving SPN Main Monitor Details for Provider Admin : ",e);
		}
		return SUCCESS;
	}
	
	
	private void setFilterValuesInSession(SPNMainMonitorModel model2) {
		getSession().setAttribute("buyerFilter", model.getBuyerFilter());
		getSession().setAttribute("membershipStatusFilter", model.getMembershipStatusFilter());
		getSession().setAttribute("selectedBuyerValues", model.getSelectedBuyerValues());
		getSession().setAttribute("selectedMemStatus", model.getSelectedMemStatus());
		getSession().setAttribute("selectedFilterMemStatus", model.getSelectedFilterMemStatus());

	}

	/**
	 * 
	 * to remove null values and set buyer filter
	 * 
	 * @param selectedBuyerValues
	 * @return
	 */
	private List<String> setFilter(List<String> selectedValues) {
		List<String> selectedVals = new ArrayList<String>();
		if(selectedValues!=null && selectedValues.size()>0){
				for(String val : selectedValues){
					if(StringUtils.isNotBlank(val)){
						selectedVals.add(val);
					}
				}
		}else{
			selectedVals = null;
		}
		return selectedVals;
	}

	/**
	 * 
	 * to add Filters
	 * 
	 * @param buyerFilter
	 * @param businessNames 
	 * @param membershipStatusFilter
	 * @param memberStatuses 
	 * @param spnMainMonitorVO
	 */
	private void addFilters(List<LookupVO> buyerFilter,
			List<String> businessNames, List<LookupVO> membershipStatusFilter,
			List<String> memberStatuses, SPNMainMonitorVO spnMainMonitorVO, List<String> status) {
		LookupVO buyer = new LookupVO();
		buyer.setId(spnMainMonitorVO.getBuyerId());
		buyer.setDescr(spnMainMonitorVO.getBuyerName());
		if(!businessNames.contains(buyer.getDescr())){
			businessNames.add(buyer.getDescr());
			buyerFilter.add(buyer);
		}
		LookupVO memberShipStatus = new LookupVO();
		memberShipStatus.setType(spnMainMonitorVO.getProviderFirmState());
		memberShipStatus.setDescr(spnMainMonitorVO.getMembershipStatus());
		if(!memberStatuses.contains(spnMainMonitorVO.getProviderFirmState())){
			memberStatuses.add(spnMainMonitorVO.getProviderFirmState());
			if(!status.contains(memberShipStatus.getDescr())){
				status.add(memberShipStatus.getDescr());
				membershipStatusFilter.add(memberShipStatus);
			}
			

		}		
	}

	public String loadSPNAjax() {
		
		String spnIdStr = getRequest().getParameter("spnId");
		Integer spnId = null;		
		if(spnIdStr != null)
		{
			spnId = Integer.valueOf(spnIdStr);
		}
		
		// The next section is to initialize the spnId used in loadSPNRequirementsTabAjax
		if(getModel().getSpnMonitorVO() == null) {
			getModel().setSpnMonitorVO(new SPNMainMonitorVO());
		}
		if(spnId != null) {
			getModel().getSpnMonitorVO().setSpnId(spnId.intValue());
		}
		
		// TODO - Need to call a method that only updates Contact Info, given a spnId
		loadSPNRequirementsTabAjax();
		
		return SUCCESS;
	}
	
	public String loadSPNRequirementsTabAjax() {
		SecurityContext securityContext = (SecurityContext) getSession().getAttribute(Constants.SESSION.SECURITY_CONTEXT);
		Integer vendorId = securityContext.getCompanyId();
		
		try {
		//	SPNMainMonitorVO spnMonitorVO = spnMonitorBO.getSPMMonitorVO(model.getSpnMonitorVO().getSpnId(), vendorId);
			List<SPNMainMonitorVO> spnMonitorList = spnMonitorBO.getSPMMainMonitorList(vendorId, Integer.valueOf(model.getSpnMonitorVO().getSpnId()));
			SPNMainMonitorVO spnMonitorVO = new SPNMainMonitorVO();
			if (spnMonitorList != null && !spnMonitorList.isEmpty())
			{
				spnMonitorVO = spnMonitorList.get(0);
				spnMonitorVO = this.loadRequiredDocuments(spnMonitorVO);
			}
			
			model.setSpnMonitorVO(spnMonitorVO);
		} catch (BusinessServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	public String loadSPNInformationTabAjax() {
		SecurityContext securityContext = (SecurityContext) getSession().getAttribute(Constants.SESSION.SECURITY_CONTEXT);
		Integer vendorId = securityContext.getCompanyId();
		
		try {
			//SPNMainMonitorVO spnMonitorVO = spnMonitorBO.getSPMMonitorVO(model.getSpnMonitorVO().getSpnId(), vendorId);
			List<SPNMainMonitorVO> spnMonitorList = spnMonitorBO.getSPMMainMonitorList(vendorId, Integer.valueOf(model.getSpnMonitorVO().getSpnId()));
			SPNMainMonitorVO spnMonitorVO = new SPNMainMonitorVO();
			if (spnMonitorList != null && !spnMonitorList.isEmpty())
			{
				spnMonitorVO = spnMonitorList.get(0);
				
				List<SPNDocumentVO> spnInfoDocuments = new ArrayList<SPNDocumentVO>();
				List<SPNDocumentVO> spnDocumentVOList=spnMonitorVO.getSpnDocuments();
				
				if(null!=spnDocumentVOList && !spnDocumentVOList.isEmpty()){
					for(SPNDocumentVO spnDocumentVO :spnDocumentVOList){
						int docTypeId = spnDocumentVO.getDocTypeId();
						if(docTypeId==1){
							spnInfoDocuments.add(spnDocumentVO);
						}
					}
				}
				spnMonitorVO.setSpnInfoDocuments(spnInfoDocuments);
			}
			
			model.setSpnMonitorVO(spnMonitorVO);
		} catch (BusinessServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	public String viewComplianceTabAjax() throws Exception
	{
		logger.info("start processing compliancetab");
		SecurityContext securityContext = (SecurityContext) getSession().getAttribute(Constants.SESSION.SECURITY_CONTEXT);
		Integer vendorId = securityContext.getCompanyId();
		String loggedInUsername = securityContext.getUsername();
		
		try{
			
		ComplianceCriteriaVO complianceCriteriaVO=new ComplianceCriteriaVO();
		complianceCriteriaVO.setVendorId(vendorId);
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
		logger.info("ssstart of"+complianceType+" complaince of vendor "+vendorId);
		model=getModel();
		getRequest().setAttribute("complianceType",complianceType);
		List<RequirementVO> requirementVoList=new ArrayList<RequirementVO>();
		List<SPNComplianceVO> requirementList=null;
	/*	logger.info("start processing filtersforComplianceTab");

		if(complianceType.equals(SPNConstants.FIRM_COMPLIANCE)){
		 requirementList=spnMonitorBO.getRequirementsforFirmCompliance(complianceCriteriaVO);
			List<String> buyerList=spnMonitorBO.getBuyersforFirmCompliance(complianceCriteriaVO);
			getRequest().setAttribute("buyerList",buyerList);	
			List<String> spnList=spnMonitorBO.getSPNforFirmCompliance(complianceCriteriaVO);
			getRequest().setAttribute("spnList",spnList);	
		}
		else if(complianceType.equals(SPNConstants.PROVIDER_COMPLIANCE)){
		  requirementList=spnMonitorBO.getRequirementsforProviderCompliance(complianceCriteriaVO);
		   List<String> buyerList=spnMonitorBO.getBuyersforProviderCompliance(complianceCriteriaVO);
		   getRequest().setAttribute("buyerList",buyerList);	
		   List<String> spnList=spnMonitorBO.getSPNforProviderCompliance(complianceCriteriaVO);
		   getRequest().setAttribute("spnList",spnList);
		   List<SPNComplianceVO> providerList=spnMonitorBO.getProviderNamesforProviderCompliance(complianceCriteriaVO);
		   getRequest().setAttribute("providerList",providerList);
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
		}*/
			//
			List<String> selectedRequirements= new ArrayList<String>();
			List<String> selectedComplianceStatus= new ArrayList<String>();
			List<String> selectedBuyers= new ArrayList<String>();
			List<String> selectedSPNs= new ArrayList<String>();
			List<String> selectedProviders= new ArrayList<String>();

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
			if(null!=model.getSpnComplianceVO() && null!=model.getSpnComplianceVO().getSelectedBuyers()){
			for (String market : model.getSpnComplianceVO().getSelectedBuyers()) {
				if (StringUtils.isNotBlank(market)) {
					selectedBuyers.add(market);
				}
			 }
			}
			if(null!=model.getSpnComplianceVO() && null!=model.getSpnComplianceVO().getSelectedSPNs()){
			for (String state : model.getSpnComplianceVO().getSelectedSPNs()) {
				if (StringUtils.isNotBlank(state)) {
					selectedSPNs.add(state);
				}
			 }
			}
			
			if(null!=model.getSpnComplianceVO() && null!=model.getSpnComplianceVO().getSelectedProviders()){
				for (String state : model.getSpnComplianceVO().getSelectedProviders()) {
					if (StringUtils.isNotBlank(state)) {
						selectedProviders.add(state);
					}
				 }
				}
			
			complianceCriteriaVO.setSelectedComplianceStatus(selectedComplianceStatus);
			complianceCriteriaVO.setSelectedBuyers(selectedBuyers);
			complianceCriteriaVO.setSelectedRequirements(selectedRequirements);
			complianceCriteriaVO.setSelectedSPNs(selectedSPNs);
			complianceCriteriaVO.setSelectedProviders(selectedProviders);
			
			/*getRequest().setAttribute("selectedComplianceStatus",selectedComplianceStatus);	
			getRequest().setAttribute("selectedBuyers",selectedBuyers);	
			getRequest().setAttribute("selectedRequirements",selectedRequirements);	
			getRequest().setAttribute("selectedSPNs",selectedSPNs);	
			getRequest().setAttribute("selectedProviders",selectedProviders);*/	
			nameCriteria();
			List<SPNComplianceVO> complianceList = new ArrayList<SPNComplianceVO>();
			long endTime=System.currentTimeMillis() -startTime;
			logger.info("timefor filterfetch"+endTime);

			/*if(complianceType.equals(SPNConstants.PROVIDER_COMPLIANCE)){
				complianceList= spnMonitorBO.getProviderCompliance(complianceCriteriaVO);
			}
			else if(complianceType.equals(SPNConstants.FIRM_COMPLIANCE)){
				complianceList= spnMonitorBO.getFirmCompliance(complianceCriteriaVO);
			}*/
			//getRequest().setAttribute("complianceList",complianceList);	
		
						
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
				if(complianceType.equals(SPNConstants.PROVIDER_COMPLIANCE)){
					if(sortColumnId.equals("0")){
							sortColumnName="spn";
					}else if(sortColumnId.equals("1")){
						sortColumnName="provider";
					}else if(sortColumnId.equals("2")){
						sortColumnName="requirement";
					}else if(sortColumnId.equals("3")){
						sortColumnName="buyer";
					}else if(sortColumnId.equals("4")){
						sortColumnName="status";

					}
			
				}
				else if(complianceType.equals(SPNConstants.FIRM_COMPLIANCE)){
					if(sortColumnId.equals("0")){
						sortColumnName="spn";
				}else if(sortColumnId.equals("1")){
					sortColumnName="requirement";
				}else if(sortColumnId.equals("2")){
					sortColumnName="buyer";
				}else if(sortColumnId.equals("3")){
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
			/*String [][] kbbb={{"1", "1", "1","1","1"}, {"1", "1", "1","1","1"}, {"1", "1", "1","1","1"},{"1", "1", "1","1","1"},{"1", "1", "1","1","1"}
			,{"1", "1", "1","1","1"}, {"1", "1", "1","1","1"}, {"1", "1", "1","1","1"},{"1", "1", "1","1","1"},{"1", "1", "1","1","1"}
			};*/
			
			
			List<String> criteriaNameList=new ArrayList<String>();
			criteriaNameList.add("Minimum Rating");
			criteriaNameList.add("Minimum Completed Service Orders");
			criteriaNameList.add("Vehicle Liability");
			criteriaNameList.add("Commercial General Liability");
			criteriaNameList.add("Worker's Compensation");

			
			if(complianceType.equals(SPNConstants.PROVIDER_COMPLIANCE)){
				
				iTotalRecords=spnMonitorBO.getProviderComplianceCount(complianceCriteriaVO).toString();
				complianceList= spnMonitorBO.getProviderCompliance(complianceCriteriaVO);
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
					data[0]=sPNComplianceVO.getSpnName();
					
					data[1]="<a  style='color: #00A0D2;' href='javascript:void(0);' onclick='javascript:openProviderProfile("+sPNComplianceVO.getId()+")' >"+sPNComplianceVO.getProviderFirstName()+" "+sPNComplianceVO.getProviderLastName() 
							+"</a><br/>"+" (ID #"+sPNComplianceVO.getId()+")<br/>";
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
					if(null!=sPNComplianceVO.getCredType())
					{
					data[2]=sPNComplianceVO.getCredType();
					if(null!=sPNComplianceVO.getCredCategory()){
					data[2]=data[2]+" > "+sPNComplianceVO.getCredCategory();
					}
					}
					else
					{
						if(null!=sPNComplianceVO.getCriteriaName() && criteriaNameList.contains(sPNComplianceVO.getCriteriaName())){
							data[2]=sPNComplianceVO.getCriteriaName();	
						}
						else
						{
							if(null!=sPNComplianceVO.getCriteriaValueName())
							data[2]=sPNComplianceVO.getCriteriaValueName();	

						}
						
					}
					

					
					
					
					data[3]=sPNComplianceVO.getBuyerName();
					
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
			else if(complianceType.equals(SPNConstants.FIRM_COMPLIANCE)){
				
				iTotalRecords=spnMonitorBO.getFirmComplianceCount(complianceCriteriaVO).toString();
				complianceList= spnMonitorBO.getFirmCompliance(complianceCriteriaVO);
				iTotalDisplayRecords=iTotalRecords;
				
				if(null!=complianceList){
				aaData=new String[complianceList.size()][4];
				int count=0;
				for(SPNComplianceVO sPNComplianceVO:complianceList){
					String data[]=new String[5];
					data[0]="";
					data[1]="";
					data[2]="";
					data[3]="";
					
					data[0]=sPNComplianceVO.getSpnName();
					if(null!=sPNComplianceVO.getCredType())
					{
					data[1]=sPNComplianceVO.getCredType();
					if(null!=sPNComplianceVO.getCredCategory()){
					data[1]=data[1]+" > "+sPNComplianceVO.getCredCategory();
					}
					}
					else
					{
						if(null!=sPNComplianceVO.getCriteriaName() && criteriaNameList.contains(sPNComplianceVO.getCriteriaName())){
							data[1]=sPNComplianceVO.getCriteriaName();	
						}
						else
						{
							if(null!=sPNComplianceVO.getCriteriaValueName())
							data[1]=sPNComplianceVO.getCriteriaValueName();	

						}
						
					}
					if(null!=sPNComplianceVO.getLiabilityAmount()){
						BigDecimal liabAmount= new BigDecimal(String.valueOf(sPNComplianceVO.getLiabilityAmount())).setScale(2, BigDecimal.ROUND_UP);
						//Fixing SL-19872: converting amount to comma separated value.
						String liabilityAmount=MoneyUtil.convertDoubleToCurrency(liabAmount.doubleValue(), Locale.US);
						data[1]=data[1]+" :$"+liabilityAmount;
					}
					
					data[2]=sPNComplianceVO.getBuyerName();
					
					if("PF SPN CRED INCOMPLIANCE".equals(sPNComplianceVO.getWfState()))
					{
					data[3]="<img alt='In compliance' title='In compliance' src='/ServiceLiveWebUtil/images/common/status-green.png' style='cursor: pointer;'></img>";

					}
					else if("PF SPN CRED OUTOFCOMPLIANCE".equals(sPNComplianceVO.getWfState()))
					{
					data[3]="<img alt='Out of compliance' title='Out of compliance' src='/ServiceLiveWebUtil/images/common/status-yellow.png' style='cursor: pointer;'></img>";

					}
					else if("PF SPN CRED OVERRIDE".equals(sPNComplianceVO.getWfState()))
					{
					data[3]="<img alt='In compliance due to buyer override' title='In compliance due to buyer override' src='/ServiceLiveWebUtil/images/common/status-blue.png' style='cursor: pointer;'></img>";
		
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
		
		}
		catch(Exception e){
			logger.info("Error Occured in loading Compliance Tab:"+e.getMessage());
			return "json";
		}
		return "json";
	}
	
	
	
	public String viewComplianceAjax() throws Exception
	{
		logger.info("start processing compliancetab");
		SecurityContext securityContext = (SecurityContext) getSession().getAttribute(Constants.SESSION.SECURITY_CONTEXT);
		Integer vendorId = securityContext.getCompanyId();
		
		try{
			
		ComplianceCriteriaVO complianceCriteriaVO=new ComplianceCriteriaVO();
		complianceCriteriaVO.setVendorId(vendorId);
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
		logger.info("ssstart of"+complianceType+" complaince of vendor "+vendorId);
		model=getModel();
		getRequest().setAttribute("complianceType",complianceType);
		List<RequirementVO> requirementVoList=new ArrayList<RequirementVO>();
		List<SPNComplianceVO> requirementList=null;
		logger.info("start processing filtersforComplianceTab");

		if(complianceType.equals(SPNConstants.FIRM_COMPLIANCE)){
		 requirementList=spnMonitorBO.getRequirementsforFirmCompliance(complianceCriteriaVO);
			List<String> buyerList=spnMonitorBO.getBuyersforFirmCompliance(complianceCriteriaVO);
			getRequest().setAttribute("buyerList",buyerList);	
			List<String> spnList=spnMonitorBO.getSPNforFirmCompliance(complianceCriteriaVO);
			getRequest().setAttribute("spnList",spnList);	
		}
		else if(complianceType.equals(SPNConstants.PROVIDER_COMPLIANCE)){
		  requirementList=spnMonitorBO.getRequirementsforProviderCompliance(complianceCriteriaVO);
		   List<String> buyerList=spnMonitorBO.getBuyersforProviderCompliance(complianceCriteriaVO);
		   getRequest().setAttribute("buyerList",buyerList);	
		   List<String> spnList=spnMonitorBO.getSPNforProviderCompliance(complianceCriteriaVO);
		   getRequest().setAttribute("spnList",spnList);
		   List<SPNComplianceVO> providerList=spnMonitorBO.getProviderNamesforProviderCompliance(complianceCriteriaVO);
		   getRequest().setAttribute("providerList",providerList);
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
			//
			List<String> selectedRequirements= new ArrayList<String>();
			List<String> selectedComplianceStatus= new ArrayList<String>();
			List<String> selectedBuyers= new ArrayList<String>();
			List<String> selectedSPNs= new ArrayList<String>();
			List<String> selectedProviders= new ArrayList<String>();

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
			if(null!=model.getSpnComplianceVO() && null!=model.getSpnComplianceVO().getSelectedBuyers()){
			for (String market : model.getSpnComplianceVO().getSelectedBuyers()) {
				if (StringUtils.isNotBlank(market)) {
					selectedBuyers.add(market);
				}
			 }
			}
			if(null!=model.getSpnComplianceVO() && null!=model.getSpnComplianceVO().getSelectedSPNs()){
			for (String state : model.getSpnComplianceVO().getSelectedSPNs()) {
				if (StringUtils.isNotBlank(state)) {
					selectedSPNs.add(state);
				}
			 }
			}
			
			if(null!=model.getSpnComplianceVO() && null!=model.getSpnComplianceVO().getSelectedProviders()){
				for (String state : model.getSpnComplianceVO().getSelectedProviders()) {
					if (StringUtils.isNotBlank(state)) {
						selectedProviders.add(state);
					}
				 }
				}
			
			complianceCriteriaVO.setSelectedComplianceStatus(selectedComplianceStatus);
			complianceCriteriaVO.setSelectedBuyers(selectedBuyers);
			complianceCriteriaVO.setSelectedRequirements(selectedRequirements);
			complianceCriteriaVO.setSelectedSPNs(selectedSPNs);
			complianceCriteriaVO.setSelectedProviders(selectedProviders);
			
			getRequest().setAttribute("selectedComplianceStatus",selectedComplianceStatus);	
			getRequest().setAttribute("selectedBuyers",selectedBuyers);	
			getRequest().setAttribute("selectedRequirements",selectedRequirements);	
			getRequest().setAttribute("selectedSPNs",selectedSPNs);	
			getRequest().setAttribute("selectedProviders",selectedProviders);	
			nameCriteria();
			List<SPNComplianceVO> complianceList = new ArrayList<SPNComplianceVO>();
			long endTime=System.currentTimeMillis() -startTime;
			logger.info("timefor filterfetch"+endTime);
			Integer count=0;
			if(complianceType.equals(SPNConstants.PROVIDER_COMPLIANCE)){
				count= spnMonitorBO.getProviderComplianceCount(complianceCriteriaVO);
				Date date=spnMonitorBO.getProviderComplianceDate();
				getRequest().setAttribute("updatedDate",date);	

			}
			else if(complianceType.equals(SPNConstants.FIRM_COMPLIANCE)){
				count= spnMonitorBO.getFirmComplianceCount(complianceCriteriaVO);
				Date date=spnMonitorBO.getFirmComplianceDate();
				getRequest().setAttribute("updatedDate",date);	


			}
			getRequest().setAttribute("count",count);	
			long endTime1=System.currentTimeMillis() -startTime;
			logger.info("timefor overall fetch"+endTime1);

	
		}
		catch(Exception e){
			logger.info("Error Occured in loading Compliance Tab:"+e.getMessage());
			return SUCCESS;
		}
		return SUCCESS;
	}
	
	
	public void nameCriteria()
	{
		Map<String,String> criteriaMap=new HashMap<String,String>();
		criteriaMap.put("Minimum Rating", "Minimum Rating");
		criteriaMap.put("SoCompleted", "Minimum Completed SO");
		criteriaMap.put("AutoLiabilityAmt", "AutoLiabilityAmt");
		criteriaMap.put("CommercialLiabilityAmt", "CommercialLiabilityAmt");
		getRequest().setAttribute("criteriaMap",criteriaMap);	
	}
	
	
	public String getProviderRequirementsList(){ 
		
		final int spnetId;
		final String spnId = getParameter(SPNConstants.SPNET_ID);
		if(StringUtils.isNotBlank(spnId)){
			spnetId = Integer.parseInt(spnId);
		} else {
			spnetId = 0;
		}

		final int vendorId;
		final String vendId = getParameter(SPNConstants.VENDOR_ID);
		if(StringUtils.isNotBlank(vendId))
		{
			vendorId = Integer.parseInt(vendId);
		} else {
			vendorId = 0;
		}
		//SecurityContext securityContext = (SecurityContext) getSession().getAttribute(Constants.SESSION.SECURITY_CONTEXT);
		//Integer vendorId = securityContext.getCompanyId();
		SPNMainMonitorVO spnMainMonitorVO = new SPNMainMonitorVO();
		spnMainMonitorVO.setSpnId(spnetId);
		spnMainMonitorVO.setVendorId(vendorId);
		try {
			SPNCompanyProviderRequirementsVO providerRequirementsList = spnMonitorBO.getProviderRequirementsList(spnMainMonitorVO);
			setAttribute("providerRequirementsList", providerRequirementsList);
			Date date=spnMonitorBO.getProviderComplianceDate();
			getRequest().setAttribute("complProvUpdatedDate",date);	
			getRequest().setAttribute("spnId", spnId);

		} catch (BusinessServiceException e) {
			logger.error("Error occured while retrieving provider requirements list:",e);
		}
		return "providerRequirements";  
	}
	
	public String getCompanyRequirementsList(){ 
		int spnetId = 0;
		int vendorId = 0;
		String spnId = getParameter(SPNConstants.SPNET_ID);
		String vendId = getParameter(SPNConstants.VENDOR_ID);
		if(StringUtils.isNotBlank(spnId)){
			spnetId = Integer.parseInt(spnId);
		}
		if(StringUtils.isNotBlank(vendId))
		{
			vendorId = Integer.parseInt(vendId);
		}
		//SecurityContext securityContext = (SecurityContext) getSession().getAttribute(Constants.SESSION.SECURITY_CONTEXT);		
		//Integer vendorId = securityContext.getCompanyId();
		SPNMainMonitorVO spnMainMonitorVO = new SPNMainMonitorVO();
		spnMainMonitorVO.setSpnId(spnetId);
		spnMainMonitorVO.setVendorId(vendorId);
		try {
			SPNCompanyProviderRequirementsVO companyRequirementsList = spnMonitorBO.getComapnyRequirementsList(spnMainMonitorVO);
			setAttribute("companyRequirementsList", companyRequirementsList);
			Date date=spnMonitorBO.getFirmComplianceDate();
			getRequest().setAttribute("complFirmUpdatedDate",date);	
			getRequest().setAttribute("spnId", spnId);

		} catch (BusinessServiceException e) {
			logger.error("Error occured while retrieving provider requirements list:",e);
		}
		return "companyRequirements";  
	}
	
	private SPNMainMonitorVO loadRequiredDocuments(SPNMainMonitorVO spnMonitorVO){
			List<SPNSignAndReturnDocumentVO> signAndReturnDocVOList = spnMonitorVO.getSpnSignAndReturnDocuments(); 
			List<SPNProvUploadedDocsVO> uploadedDocList = spnMonitorVO.getSpnProvUploadedDocs();
			if(null!=signAndReturnDocVOList && !signAndReturnDocVOList.isEmpty()){				
				for(SPNSignAndReturnDocumentVO spnSignAndReturnDocumentVO :signAndReturnDocVOList){	
					int docId = spnSignAndReturnDocumentVO.getDocId();
					if(null!=uploadedDocList && !uploadedDocList.isEmpty()){
						for(SPNProvUploadedDocsVO spnProvUploadedDocsVO :uploadedDocList){
							int spnBuyerDocId = spnProvUploadedDocsVO.getSpnBuyerDocId();
							if(spnBuyerDocId == docId){  
								spnSignAndReturnDocumentVO.setProviderUploadInd(SPNConstants.SUCCESS_IND); 	
								spnSignAndReturnDocumentVO.setDocDescription(spnProvUploadedDocsVO.getDocDescription());
								spnSignAndReturnDocumentVO.setDocFileName(spnProvUploadedDocsVO.getDocFileName());
								spnSignAndReturnDocumentVO.setDocStateDesc(spnProvUploadedDocsVO.getDocStateDesc());
								spnSignAndReturnDocumentVO.setDocStateId(spnProvUploadedDocsVO.getDocStateId());
								spnSignAndReturnDocumentVO.setDocFormat(spnProvUploadedDocsVO.getDocFormat());
								spnSignAndReturnDocumentVO.setDocTitle(spnProvUploadedDocsVO.getDocTitle());
								spnSignAndReturnDocumentVO.setProvFirmUplDocId(spnProvUploadedDocsVO.getProvFirmUplDocId());
								spnSignAndReturnDocumentVO.setProvDocFormatDescription(spnProvUploadedDocsVO.getDocFormatDescription());
								break;
							}
						}						
					}
					if(spnSignAndReturnDocumentVO.getProviderUploadInd() != SPNConstants.SUCCESS_IND){
						spnSignAndReturnDocumentVO.setDocStateId(SPNConstants.DOC_INCOMPLETE_STATE_ID);
						spnSignAndReturnDocumentVO.setDocStateDesc(SPNConstants.DOC_INCOMPLETE);
					}
				}
			}			
			spnMonitorVO.setSpnSignAndReturnDocuments(signAndReturnDocVOList);
		return spnMonitorVO;
	}
	
	public String loadDocument() throws Exception {
		int docId = 0;
		SPNDocumentVO documentvo=new SPNDocumentVO();
		String documentID = getParameter(SPNConstants.DOCUMENT_ID);
		if(StringUtils.isNotBlank(documentID)){
			docId=Integer.parseInt(documentID);
		}
		List<SPNDocumentVO> resultList=  new ArrayList<SPNDocumentVO>();
			try{
				
				resultList=spnCreateUpdateBO.getSPNBuyerAgreeModalDocument(Integer.valueOf(docId));
				documentvo=resultList.get(SPNConstants.ZERO);
				InputStream in = new ByteArrayInputStream(documentvo.getDocumentContent());
				ServletOutputStream outs = ServletActionContext.getResponse().getOutputStream();
				SecurityChecker sc = new SecurityChecker();
				
				if(documentvo.getFormat()!=null){
					String docFormat = sc.securityCheck(documentvo.getFormat());
					ServletActionContext.getResponse().setContentType(docFormat);
					
				}
				
				byte[] imgData = (byte[]) documentvo.getDocumentContent();
				String fileName = documentvo.getFileName();	
				fileName = sc.securityCheck(fileName);
				getResponse().setContentLength(imgData.length);
				String header = "attachment;filename=\"" + fileName + "\"";
				getResponse().setHeader("Content-Disposition", header);
				getResponse().setHeader("Expires", "0");
				getResponse().setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
				getResponse().setHeader("Pragma", "public");
				OutputStream out = getResponse().getOutputStream();
				out.write(imgData);
				out.flush();
				out.close();				
			
			}catch(Exception e){
				logger.error("Error returned trying to get Document Content for the document ID:" + documentID,e);
			}
		return "spnDownloadDocBlankJSP";
	}
	
	public String uploadDocument() throws BusinessServiceException {
		SPNProvUploadedDocsVO spnProvUploadedDocsVO = new SPNProvUploadedDocsVO();
		SecurityContext securityContext = (SecurityContext) getSession().getAttribute(Constants.SESSION.SECURITY_CONTEXT);
		int vendorId =  securityContext.getCompanyId().intValue();
		String userName = securityContext.getUsername();
		String docTitle= getParameter(SPNConstants.SPN_DOC_TITLE);
		String buyerDocId= getParameter(SPNConstants.SPN_BUYER_DOC_ID);
		String spnId = getParameter(SPNConstants.SPN_SPN_ID);
		String proDocId = getParameter(SPNConstants.SPN_PROV_DOC_ID);
		String buttonType = getParameter(SPNConstants.SPN_BUTTON_TYPE);
		String spnBuyerId = getParameter(SPNConstants.SPN_SPNBUYERID_ID);
		MultiPartRequestWrapper multiPartRequestWrapper = (MultiPartRequestWrapper) ServletActionContext.getRequest();		
		fileUpload = FileUtils.updateDetails(multiPartRequestWrapper, SPNConstants.SPN_FILE_INPUT );
		if (fileUpload != null) {
			String fileName = fileUpload.getCredentialDocumentFileName();
			long fileSize = fileUpload.getCredentialDocumentFileSize();
			logger.info("File Name = ["+fileName+"] Size = ["+fileSize+"]");
			if (fileName != null &&  fileName.trim().length() > 0 && fileSize > 0) {
				spnProvUploadedDocsVO.setDocFileName(fileUpload.getCredentialDocumentFileName());
				spnProvUploadedDocsVO.setDocFormat(fileUpload.getCredentialDocumentExtention());
				spnProvUploadedDocsVO.setDocBytes(fileUpload.getCredentialDocumentBytes());
				spnProvUploadedDocsVO.setDocFileSize(fileUpload.getCredentialDocumentFileSize());		
			}
		}
		spnProvUploadedDocsVO.setVendorId(vendorId);
		spnProvUploadedDocsVO.setDocTitle(docTitle);
		spnProvUploadedDocsVO.setBuyerId(Integer.parseInt(spnBuyerId));
		spnProvUploadedDocsVO.setUserName(userName);
		if(null != proDocId && StringUtils.isNotEmpty(proDocId)){
			spnProvUploadedDocsVO.setProvFirmUplDocId(Integer.parseInt(proDocId));
		}	
		if(StringUtils.isNotBlank(buyerDocId)){
			spnProvUploadedDocsVO.setSpnBuyerDocId(Integer.parseInt(buyerDocId));
		}
		if(StringUtils.isNotBlank(spnId)){
			spnProvUploadedDocsVO.setSpnId(Integer.parseInt(spnId));
		}
		
		// SL-18965 - Code for upload ing a file <30 MB
		long filesize=spnProvUploadedDocsVO.getDocFileSize();
		//  long fileSizeMB=filesize/(1024*1024);
		logger.info("File Size in MB="+filesize);
		// 31457280 Bytes = 30MB
		if(filesize > 31457280){
			logger.info("File size is greater than 30 MB");
			List<String> newActionErrors = new ArrayList <String>();	
			newActionErrors.add("Please attach a file no larger than 30 mb.");
			setActionErrors(newActionErrors);
			return "uploadDocFilleUp";
		}else{
			logger.info("File size is less than 30 MB");
			spnMonitorBO.uploadDocument(spnProvUploadedDocsVO,buttonType);
		}
		// SL-18965 - Code for upload ing a file <30 MB	
		getRequest().setAttribute("spnProvUploadedDocsVO", spnProvUploadedDocsVO);
		logger.info("Document successfully uploaded");
		return "uploadDoc";
	}
	
	public SPNMainMonitorModel getModel()
	{
		return model;
	} 
	
	
	public String callExternalSPN() {
		StringBuilder stb = new StringBuilder("?targetAction=");
		
		SecurityContext securityContext = (SecurityContext) getSession()
				.getAttribute(Constants.SESSION.SECURITY_CONTEXT);
		stb.append(targetExternalAction);
			logger.info("SL-18248 - buyerId: "+ securityContext.getCompanyId());
			logger.info("SL-18248 - isSlAdmin: "+ securityContext.getSLAdminInd());
			logger.info("SL-18248 - username: "+ securityContext.getUsername());
			logger.info("SL-18248 - SLAdminusername: "+ securityContext.getSlAdminUName());
		buyerId = securityContext.getCompanyId();
		boolean isSlAdmin = securityContext.getSLAdminInd();
		if (buyerId != null) {
			stb.append("&buyerId=");
			stb.append(buyerId);
			stb.append("&username=");
			if (isSlAdmin) {
				stb.append(securityContext.getSlAdminUName());
			} else {
				stb.append(securityContext.getUsername());
			}
			stb.append("&isSlAdmin=");
			stb.append(isSlAdmin);
		}
		logger.info("SL-18248 - values from security context: " +stb.toString());
		resultUrl = getRequest().getScheme() + "://"
				+ getRequest().getServerName() + ":"
				+ getRequest().getServerPort()
				+ "/spn/spnLoginAction_display.action" + stb.toString();
		logger.info("SL-18248 - result url is: " +resultUrl);
		return "callExternalSPNApp";
	}

	
	
	/**
	 * @return String
	 * @throws Exception
	 */
	//SL-19387
	//Fetching Background Check details count of resources from db to displayed in SPN Monitor Tab for Providers.
	public String searchBackgroundInformationCountAjax() throws Exception {

		try {

			Integer spnId = -1;
			
			String selectedReCertification = "";
			String selectedSLBackgroundStatus = "";
			String selectedSystemAction = "";
			String selectedSpnMonitor = "";
			
			Integer count = 0;
			
			List<String> sLBackgroundStatusList = new ArrayList<String>();
			List<String> reCertificationList = new ArrayList<String>();
			List<String> systemActionList = new ArrayList<String>();

			BackgroundFilterProviderVO backgroundFilterProviderVO=model.getBackgroundFilterProviderVO();
			
			//Fetching spnId from frontend
			
//			if (StringUtils.isNotEmpty((String) getRequest()
//							.getParameter(Constants.BG_SPN_ID_PARAM))) {
//				spnId = Integer.parseInt((String) getRequest().getParameter(
//						Constants.BG_SPN_ID_PARAM));
//			}


			//getRequest().setAttribute(Constants.BG_SPN_ID_PARAM, spnId);

			String searching = "";
			if (StringUtils.isNotEmpty((String) getRequest()
							.getParameter(Constants.BG_DATATABLE_SEARCHING_PARAM))) {
				searching = (String) getRequest().getParameter(Constants.BG_DATATABLE_SEARCHING_PARAM);
			}
			getRequest().setAttribute(Constants.BG_DATATABLE_SEARCHING_PARAM, searching);

			
			//Fetching the vendorId of logged in user
			
			SecurityContext securityContext = (SecurityContext) getSession().getAttribute(Constants.SESSION.SECURITY_CONTEXT);
			Integer vendorId = securityContext.getCompanyId();

			SearchBackgroundInfoProviderVO searchBackgroundInfoProviderVO = new SearchBackgroundInfoProviderVO();
			searchBackgroundInfoProviderVO.setVendorId(vendorId);
			//searchBackgroundInfoProviderVO.setSpnId(spnId);

			//Setting ServiceLive Background Check Status list to be displayed
			
			sLBackgroundStatusList.add(Constants.BG_DATATABLE_ADVANCE_FILTER_BG_STATUS_PARAM1);
			sLBackgroundStatusList.add(Constants.BG_DATATABLE_ADVANCE_FILTER_BG_STATUS_PARAM2);
			sLBackgroundStatusList.add(Constants.BG_DATATABLE_ADVANCE_FILTER_BG_STATUS_PARAM3);
			sLBackgroundStatusList.add(Constants.BG_DATATABLE_ADVANCE_FILTER_BG_STATUS_PARAM4);
			sLBackgroundStatusList.add(Constants.BG_DATATABLE_ADVANCE_FILTER_BG_STATUS_PARAM5);
			

			getRequest().setAttribute(Constants.BG_DATATABLE_ADVANCE_FILTER_BG_STATUS_PARAM,
					sLBackgroundStatusList);

		
			//Setting Re-Certification Due list to be displayed
			
			reCertificationList.add(Constants.BG_DATATABLE_ADVANCE_FILTER_RECERTIFICATION_PARAM1);
			reCertificationList.add(Constants.BG_DATATABLE_ADVANCE_FILTER_RECERTIFICATION_PARAM7);
			reCertificationList.add(Constants.BG_DATATABLE_ADVANCE_FILTER_RECERTIFICATION_PARAM2);
			reCertificationList.add(Constants.BG_DATATABLE_ADVANCE_FILTER_RECERTIFICATION_PARAM5);
			reCertificationList.add(Constants.BG_DATATABLE_ADVANCE_FILTER_RECERTIFICATION_PARAM6);
		

			getRequest().setAttribute(Constants.BG_DATATABLE_ADVANCE_FILTER_RECERTIFICATION_PARAM,
					reCertificationList);

			//Setting System Action list to be displayed
			
			systemActionList.add(Constants.BG_DATATABLE_ADVANCE_FILTER_SYSTEM_ACTION_PARAM1);
			systemActionList.add(Constants.BG_DATATABLE_ADVANCE_FILTER_SYSTEM_ACTION_PARAM2);
			systemActionList.add(Constants.BG_DATATABLE_ADVANCE_FILTER_SYSTEM_ACTION_PARAM3);
			

			getRequest().setAttribute(Constants.BG_DATATABLE_ADVANCE_FILTER_SYSTEM_ACTION_PARAM, systemActionList);

			
			//Fetching selected options from Advance Filter
			

			if (null != backgroundFilterProviderVO
					&& null != backgroundFilterProviderVO
							.getSelectedReCertification()) {

				if (StringUtils.isNotBlank(backgroundFilterProviderVO
						.getSelectedReCertification())) {
					selectedReCertification = backgroundFilterProviderVO
							.getSelectedReCertification();
					if (selectedReCertification.equals(Constants.BG_DATATABLE_ADVANCE_FILTER_RECERTIFICATION_PARAM5)) {
						searchBackgroundInfoProviderVO
								.setSelectedReCertification(Constants.BG_SEVEN);
					} else if (selectedReCertification.equals(Constants.BG_DATATABLE_ADVANCE_FILTER_RECERTIFICATION_PARAM6)) {
						searchBackgroundInfoProviderVO
								.setSelectedReCertification(Constants.BG_THIRTY);
					} else if (selectedReCertification.equals(Constants.BG_DATATABLE_ADVANCE_FILTER_RECERTIFICATION_PARAM2)) {
						searchBackgroundInfoProviderVO
								.setSelectedReCertification(Constants.BG_ZERO);
					} else if (selectedReCertification.equals(Constants.BG_DATATABLE_ADVANCE_FILTER_RECERTIFICATION_PARAM1)) {
						searchBackgroundInfoProviderVO.setPastDue(Constants.BG_PAST);
					}else if (selectedReCertification.equals(Constants.BG_DATATABLE_ADVANCE_FILTER_RECERTIFICATION_PARAM7)) {
						searchBackgroundInfoProviderVO.setPastDue(Constants.BG_DATATABLE_ADVANCE_FILTER_RECERTIFICATION_PARAM8);
					}else if (selectedReCertification.equals(Constants.BG_DATATABLE_ADVANCE_FILTER_PARAM)) {
						searchBackgroundInfoProviderVO.setSelectedReCertificationAll(Constants.BG_DATATABLE_ADVANCE_FILTER_RECERTIFICATION_PARAM_ALL);
					} else {
						searchBackgroundInfoProviderVO.setSelectedReCertification(selectedReCertification);	
					}
					

				}

			}
			if (null != backgroundFilterProviderVO
					&& null !=backgroundFilterProviderVO
							.getSelectedSLBackgroundStatus()) {

				if (StringUtils.isNotBlank(backgroundFilterProviderVO
						.getSelectedSLBackgroundStatus())) {
					selectedSLBackgroundStatus = backgroundFilterProviderVO
							.getSelectedSLBackgroundStatus();
					 if(selectedSLBackgroundStatus.equals(Constants.BG_DATATABLE_ADVANCE_FILTER_PARAM)) {
						 searchBackgroundInfoProviderVO.setSelectedSLBackgroundStatusAll(Constants.BG_DATATABLE_ADVANCE_FILTER_BG_STATUS_PARAM_ALL);
						}
					 else
					 { 
						 searchBackgroundInfoProviderVO
							.setSelectedSLBackgroundStatus(selectedSLBackgroundStatus);
					 }
				}

			}

			if (null != backgroundFilterProviderVO
					&& null != backgroundFilterProviderVO
							.getSelectedSystemAction()) {

				if (StringUtils.isNotBlank(backgroundFilterProviderVO
						.getSelectedSystemAction())) {
					selectedSystemAction = backgroundFilterProviderVO
							.getSelectedSystemAction();

					if (selectedSystemAction.equals(Constants.BG_DATATABLE_ADVANCE_FILTER_SYSTEM_ACTION_PARAM2)) {
						searchBackgroundInfoProviderVO
								.setSelectedSystemAction(Constants.BG_SEVEN);
					} else if (selectedSystemAction
							.equals(Constants.BG_DATATABLE_ADVANCE_FILTER_SYSTEM_ACTION_PARAM1)) {
						searchBackgroundInfoProviderVO
								.setSelectedSystemAction(Constants.BG_THIRTY);
					} else if (selectedSystemAction.equals(Constants.BG_DATATABLE_ADVANCE_FILTER_SYSTEM_ACTION_PARAM3)) {
						searchBackgroundInfoProviderVO
								.setSelectedSystemAction(Constants.BG_ZERO);
					}
					else if (selectedSystemAction.equals(Constants.BG_DATATABLE_ADVANCE_FILTER_PARAM)) {
						searchBackgroundInfoProviderVO.setSelectedSystemActionAll(Constants.BG_DATATABLE_ADVANCE_FILTER_SYSTEM_ACTION_PARAM_ALL);
					} else {
						searchBackgroundInfoProviderVO
						.setSelectedSystemAction(selectedSystemAction);
					}

				}

			}

			
			getRequest().setAttribute(Constants.BG_DATATABLE_SELECTED_RECERTIFICATION_PARAM,
					selectedReCertification);
			getRequest().setAttribute(Constants.BG_DATATABLE_SELECTED_BG_STATUS_PARAM,
					selectedSLBackgroundStatus);
			getRequest().setAttribute(Constants.BG_DATATABLE_SELECTED_SYSTEM_ACTION_PARAM,
					selectedSystemAction);
	
			
			
			//Fetching the count from database
			
			count = spnMonitorBO
					.getBackgroundInformationCount(searchBackgroundInfoProviderVO);
			getRequest().setAttribute(Constants.BG_DATATABLE_COUNT_RESULTS_PARAM, count);
			
			//Setting the total record count in session to use in export
			getRequest().getSession().setAttribute(Constants.BG_DATATABLE_COUNT_RESULTS_EXPORT_PARAM, count);
		} catch (Exception e) {
			logger.error("Exception in searchBackgroundInformationCountAjax method of SPNMonitorAction java class due to "
					+ e.getMessage());
			
			return "error";
		}

		return "back";
	}
	
	
	/**
	 * @return String
	 */
	//SL-19387
	//Fetching Background Check details of resources from db to displayed in SPN Monitor Tab for Providers.
	public String searchBackgroundInformationAjax() {

		try {

			Integer spnId = -1;
			List<String> selectedProviderFirmIds = new ArrayList<String>();
			
			List<BackgroundInfoProviderVO> backgroundInfoList = new ArrayList<BackgroundInfoProviderVO>();

			SearchBackgroundInfoProviderVO searchBackgroundInfoProviderVO = new SearchBackgroundInfoProviderVO();
			
			String searching = "";
			
			int startIndex = 0;
			int numberOfRecords = 0;
			String sortColumnName = "";
			String sortOrder = "";
			String sSearch = "";
			
			BackgroundFilterProviderVO backgroundFilterProviderVO=model.getBackgroundFilterProviderVO();
			
			//Fetching spnId from frontend
			
//			if (StringUtils.isNotEmpty((String) getRequest()
//							.getParameter(Constants.BG_SPN_ID_PARAM))) {
//				spnId = Integer.parseInt((String) getRequest().getParameter(Constants.BG_SPN_ID_PARAM));
//			}

			model = getModel();
			//getRequest().setAttribute(Constants.BG_SPN_ID_PARAM, spnId);

			//Fetching the vendorId of logged in user
			
			SecurityContext securityContext = (SecurityContext) getSession().getAttribute(Constants.SESSION.SECURITY_CONTEXT);
			Integer vendorId = securityContext.getCompanyId();
			
			
			searchBackgroundInfoProviderVO.setVendorId(vendorId);

			//searchBackgroundInfoProviderVO.setSpnId(spnId);

			//Fetching selected options from Advance Filter
			
			if (null != backgroundFilterProviderVO
					&& null != backgroundFilterProviderVO
							.getSelectedReCertification()) {

				if (StringUtils.isNotBlank(backgroundFilterProviderVO
						.getSelectedReCertification())) {

					String selectedReCertification = backgroundFilterProviderVO
							.getSelectedReCertification();
					if (selectedReCertification.equals(Constants.BG_DATATABLE_ADVANCE_FILTER_RECERTIFICATION_PARAM5)) {
						searchBackgroundInfoProviderVO
								.setSelectedReCertification(Constants.BG_SEVEN);
					} else if (selectedReCertification.equals(Constants.BG_DATATABLE_ADVANCE_FILTER_RECERTIFICATION_PARAM6)) {
						searchBackgroundInfoProviderVO
								.setSelectedReCertification(Constants.BG_THIRTY);
					} else if (selectedReCertification.equals(Constants.BG_DATATABLE_ADVANCE_FILTER_RECERTIFICATION_PARAM2)) {
						searchBackgroundInfoProviderVO
								.setSelectedReCertification(Constants.BG_ZERO);
					} else if (selectedReCertification.equals(Constants.BG_DATATABLE_ADVANCE_FILTER_RECERTIFICATION_PARAM1)) {
						searchBackgroundInfoProviderVO.setPastDue(Constants.BG_PAST);
					}else if (selectedReCertification.equals(Constants.BG_DATATABLE_ADVANCE_FILTER_RECERTIFICATION_PARAM7)) {
						searchBackgroundInfoProviderVO.setSelectedReCertification(Constants.BG_DATATABLE_ADVANCE_FILTER_RECERTIFICATION_PARAM8);
					}else if (selectedReCertification.equals(Constants.BG_DATATABLE_ADVANCE_FILTER_PARAM)) {
						searchBackgroundInfoProviderVO.setSelectedReCertificationAll(Constants.BG_DATATABLE_ADVANCE_FILTER_RECERTIFICATION_PARAM_ALL);
					}else{
						searchBackgroundInfoProviderVO.setSelectedReCertification(selectedReCertification);
					}

				}

			}
			if (null != backgroundFilterProviderVO
					&& null != backgroundFilterProviderVO
							.getSelectedSLBackgroundStatus()) {

				if (StringUtils.isNotBlank(backgroundFilterProviderVO
						.getSelectedSLBackgroundStatus())) {
					String selectedSLBackgroundStatus =backgroundFilterProviderVO
							.getSelectedSLBackgroundStatus();
					 if(selectedSLBackgroundStatus.equals(Constants.BG_DATATABLE_ADVANCE_FILTER_PARAM)) {
						 searchBackgroundInfoProviderVO.setSelectedSLBackgroundStatusAll(Constants.BG_DATATABLE_ADVANCE_FILTER_BG_STATUS_PARAM_ALL);
						}
					 else
					 { 
						 searchBackgroundInfoProviderVO
							.setSelectedSLBackgroundStatus(selectedSLBackgroundStatus);
					 }
				}

			}

			if (null != backgroundFilterProviderVO
					&& null != backgroundFilterProviderVO
							.getSelectedSystemAction()) {

				if (StringUtils.isNotBlank(backgroundFilterProviderVO
						.getSelectedSystemAction())) {
					String selectedSystemAction = backgroundFilterProviderVO
							.getSelectedSystemAction();

					if (selectedSystemAction.equals(Constants.BG_DATATABLE_ADVANCE_FILTER_SYSTEM_ACTION_PARAM2)) {
						searchBackgroundInfoProviderVO
								.setSelectedSystemAction(Constants.BG_SEVEN);
					} else if (selectedSystemAction
							.equals(Constants.BG_DATATABLE_ADVANCE_FILTER_SYSTEM_ACTION_PARAM1)) {
						searchBackgroundInfoProviderVO
								.setSelectedSystemAction(Constants.BG_THIRTY);
					} else if (selectedSystemAction.equals(Constants.BG_DATATABLE_ADVANCE_FILTER_SYSTEM_ACTION_PARAM3)) {
						searchBackgroundInfoProviderVO
								.setSelectedSystemAction(Constants.BG_ZERO);
					}else if (selectedSystemAction.equals(Constants.BG_DATATABLE_ADVANCE_FILTER_PARAM)) {
						searchBackgroundInfoProviderVO.setSelectedSystemActionAll(Constants.BG_DATATABLE_ADVANCE_FILTER_SYSTEM_ACTION_PARAM_ALL);
					}else {
						searchBackgroundInfoProviderVO
						.setSelectedSystemAction(selectedSystemAction);
					}

				}

			}


			
			if (StringUtils.isNotEmpty((String) getRequest()
							.getParameter(Constants.BG_DATATABLE_SEARCHING_PARAM))) {
				searching = (String) getRequest().getParameter(Constants.BG_DATATABLE_SEARCHING_PARAM);
			}
			getRequest().setAttribute(Constants.BG_DATATABLE_SEARCHING_PARAM, searching);
			long startTime = System.currentTimeMillis();

			// to handle server side pagination.

			if (StringUtils.isNotEmpty((String) getRequest()
							.getParameter(Constants.BG_DATATABLE_IDISPLAYSTART_PARAM))) {
				startIndex = Integer.parseInt((String) getRequest()
						.getParameter(Constants.BG_DATATABLE_IDISPLAYSTART_PARAM));
			}

			if (StringUtils.isNotEmpty((String) getRequest()
							.getParameter(Constants.BG_DATATABLE_IDISPLAYLENGTH_PARAM))) {
				numberOfRecords = Integer.parseInt((String) getRequest()
						.getParameter(Constants.BG_DATATABLE_IDISPLAYLENGTH_PARAM));
			}
			if (StringUtils.isNotEmpty((String) getRequest()
							.getParameter(Constants.BG_DATATABLE_ISORTCOL_0_PARAM))) {
				String sortColumnId = (String) getRequest().getParameter(
						Constants.BG_DATATABLE_ISORTCOL_0_PARAM);

				// To do sorting based on column

				if (sortColumnId.equals("0")) {
					sortColumnName =Constants.BG_DATATABLE_COLUMN_SORT_PROVIDER;
				} else if (sortColumnId.equals("1")) {
					sortColumnName = Constants.BG_DATATABLE_COLUMN_SORT_SLSTATUS;
				} else if (sortColumnId.equals("2")) {
					sortColumnName = Constants.BG_DATATABLE_COLUMN_SORT_CERTDATE;
				} else if (sortColumnId.equals("3")) {
					sortColumnName = Constants.BG_DATATABLE_COLUMN_SORT_RECERTDATE;
				} else if (sortColumnId.equals("4")) {
					sortColumnName = Constants.BG_DATATABLE_COLUMN_SORT_RECERTSTATUS;
				}

			}

			if (StringUtils.isNotEmpty((String) getRequest()
							.getParameter(Constants.BG_DATATABLE_SSORTDIR_0_PARAM))) {
				sortOrder = (String) getRequest().getParameter(Constants.BG_DATATABLE_SSORTDIR_0_PARAM);
			}

			if (StringUtils.isNotEmpty((String) getRequest()
							.getParameter(Constants.BG_DATATABLE_SSEARCH_PARAM))) {
				sSearch = (String) getRequest().getParameter(Constants.BG_DATATABLE_SSEARCH_PARAM);
			}
			if (StringUtils.isNotEmpty((String) getRequest()
							.getParameter(Constants.BG_DATATABLE_SEECHO_PARAM))) {
				sEcho = Integer.parseInt(getRequest().getParameter(Constants.BG_DATATABLE_SEECHO_PARAM));
			}

			searchBackgroundInfoProviderVO.setSortColumnName(sortColumnName);
			searchBackgroundInfoProviderVO.setSortOrder(sortOrder);
			searchBackgroundInfoProviderVO.setsSearch(sSearch);
			searchBackgroundInfoProviderVO.setStartIndex(startIndex);
			searchBackgroundInfoProviderVO.setNumberOfRecords(numberOfRecords);

			//Setting the VO in session to use during export
			getRequest().getSession().setAttribute(Constants.SEARCH_VO, searchBackgroundInfoProviderVO);

			iTotalRecords = Constants.BG_DATATABLE_HUNDRED_PARAM;
			iTotalDisplayRecords = Constants.BG_DATATABLE_TEN_PARAM;

			//Fetching Background Check details count of resources from db
			iTotalRecords = spnMonitorBO.getBackgroundInformationCount(
					searchBackgroundInfoProviderVO).toString();
			
			//Fetching Background Check details of resources from db
			backgroundInfoList = spnMonitorBO
					.getBackgroundInformation(searchBackgroundInfoProviderVO);

			
			//Displaying data in each column of data table
			iTotalDisplayRecords = iTotalRecords;
			if (null != backgroundInfoList) {
				aaData = new String[backgroundInfoList.size()][6];
				int count = 0;
				for (BackgroundInfoProviderVO backgroundInformationVO : backgroundInfoList) {
					
					try{
					if (null != backgroundInformationVO) {
						String data[] = new String[6];
						data[0] = "";
						data[1] = "";
						data[2] = "";
						data[3] = "";
						data[4] = "";
						data[5] = "";

						//'Provider' data to be displayed with link to open their profile
						if (null != backgroundInformationVO.getResourceId()
								&& null != backgroundInformationVO
										.getVendorId()
								&& null != backgroundInformationVO
										.getProviderFirstName()
								&& null != backgroundInformationVO
										.getProviderLastName()) {
							data[0] = "<a  style='color: #00A0D2;' href='javascript:void(0);' onclick='javascript:openProviderProfile("
									+ backgroundInformationVO.getResourceId()
									+ ","
									+ backgroundInformationVO.getVendorId()
									+ ")' ><b>"
									+ backgroundInformationVO
											.getProviderFirstName()
									+ " "
									+ backgroundInformationVO
											.getProviderLastName()
									+ "</b></a><br/>"
									+ " (ID #"
									+ backgroundInformationVO.getResourceId()
									+ ")<br/>";
						}

						//'Background Check Status' data to be displayed-'Clear'/'Adverse Finding'/'Not Started'/'In Process'/'Pending Submission'
						if (null != backgroundInformationVO
								.getBackgroundState()) {
							if(backgroundInformationVO.getBackgroundState().equalsIgnoreCase(Constants.BG_DATATABLE_ADVANCE_FILTER_BG_STATUS_PARAM1) || backgroundInformationVO.getBackgroundState().equalsIgnoreCase(Constants.BG_DATATABLE_ADVANCE_FILTER_BG_STATUS_PARAM2)){
								data[1] = 
									"<a  style='color: #00A0D2;' href='javascript:void(0);'	onclick='javascript:openBackgroundHistory("
									+backgroundInformationVO.getVendorId()+","+backgroundInformationVO.getResourceId()+","+"\""+backgroundInformationVO.getBackgroundState()+"\""
									+");'>"
									+ backgroundInformationVO.getBackgroundState()	
									+ "</a>";
							}
							else{
							data[1] = backgroundInformationVO
									.getBackgroundState();
							}
						}

						//'Last Certification Date' data to be displayed
						if (null != backgroundInformationVO
								.getVerificationDate()) {

							data[2] = ""
									+ DateUtils.getFormatedDate(
											backgroundInformationVO
													.getVerificationDate(),
													Constants.BACKGROUND_CHECK_STATUS_DATE_FORMAT);
						}

						//'ReCertification Due Date' data to be displayed
						if (null != backgroundInformationVO
								.getReverificationDate() && null!=backgroundInformationVO.getCriteriaBg() && backgroundInformationVO.getCriteriaBg() > 0) {
							data[3] = ""
									+ DateUtils.getFormatedDate(
											backgroundInformationVO
													.getReverificationDate(),
													Constants.BACKGROUND_CHECK_STATUS_DATE_FORMAT);
						}

						//'Last ReCertification Status' data to be displayed based on the number of days
						if (null != backgroundInformationVO.getRecertificationStatus() &&  null!=backgroundInformationVO.getCriteriaBg() && backgroundInformationVO.getCriteriaBg() > 0) {
							
							 if(backgroundInformationVO.getRecertificationStatus().equals(Constants.BG_DATATABLE_ADVANCE_FILTER_BG_STATUS_PARAM3))
								{
									data[4] =backgroundInformationVO.getRecertificationStatus();
								}
							 else if (Integer.parseInt(backgroundInformationVO.getRecertificationStatus()) == 0) {
								data[4] = "<a  style='color: #00A0D2;' href='javascript:void(0);' onclick='javascript:recertifyUser("+
								backgroundInformationVO.getResourceId()+");'>"+"Due Today"+"</a>";
							} else if (Integer.parseInt(backgroundInformationVO.getRecertificationStatus()) < 0) {
								data[4] = "<a  style='color: red;' href='javascript:void(0);' onclick='javascript:recertifyUser("+
								backgroundInformationVO.getResourceId()+");'>"+"Past Due"+"</a>";
							} else if(Integer.parseInt(backgroundInformationVO.getRecertificationStatus()) > 0 && Integer.parseInt(backgroundInformationVO.getRecertificationStatus()) <= 30) {
		
								data[4]=
									"<a  style='color: #00A0D2;' href='javascript:void(0);'	onclick='javascript:recertifyUser("+
									backgroundInformationVO.getResourceId()+");'>"+ "Due in "
										+ backgroundInformationVO.getRecertificationStatus() + " days </a>";
							}

						}

						//'System Action/Notice Sent On' data to be displayed based on the notification type
						if (null != backgroundInformationVO
								.getNotificationType()
								&& (null != backgroundInformationVO
										.getNotificationDateThirty() || null != backgroundInformationVO
												.getNotificationDateSeven() || null != backgroundInformationVO
														.getNotificationDateZero()) && null!=backgroundInformationVO.getCriteriaBg() && backgroundInformationVO.getCriteriaBg() > 0 && null != backgroundInformationVO.getRecertificationStatus()) {
							
							
							if(null != backgroundInformationVO
									.getNotificationDateThirty())
							{
							data[5] ="30 days notice sent on"+" "
									+ DateUtils.getFormatedDate(
											backgroundInformationVO
													.getNotificationDateThirty(),
													Constants.BACKGROUND_CHECK_STATUS_DATE_FORMAT);
							}
							
							if(null != backgroundInformationVO
									.getNotificationDateSeven())
							{
							data[5] =data[5]+"<br/>"+"7 days notice sent on"+" "
									+ DateUtils.getFormatedDate(
											backgroundInformationVO
													.getNotificationDateSeven(),
													Constants.BACKGROUND_CHECK_STATUS_DATE_FORMAT);
							}
							
							if(null != backgroundInformationVO
									.getNotificationDateZero())
							{
							data[5] =data[5]+"<br/>"+"0 days notice sent on"+" "
									+ DateUtils.getFormatedDate(
											backgroundInformationVO
													.getNotificationDateZero(),
													Constants.BACKGROUND_CHECK_STATUS_DATE_FORMAT);
							}

						}
 						aaData[count] = data;

						count = count + 1;
					}
					}
					catch (Exception e) {
						if(null != backgroundInformationVO.getResourceId())
						{
						logger.error("Exception in searchBackgroundInformationAjax method of SPNMonitorAction java class while processing record:"
								+" Resource Id: "+backgroundInformationVO.getResourceId()+e.getMessage());
						}
						else
						{
							logger.error("Exception in searchBackgroundInformationAjax method of SPNMonitorAction java class while processing records:"
									+ e.getMessage());
						}
					}
				}
			}

			model.setAaData(aaData);
			model.setsEcho(sEcho);
			model.setiTotalDisplayRecords(iTotalDisplayRecords);
			model.setiTotalRecords(iTotalRecords);
			getRequest().setAttribute(Constants.BG_LIST, backgroundInfoList);
			long endTime1 = System.currentTimeMillis() - startTime;
			logger.info("timefor overall fetch" + endTime1);

		} catch (Exception e) {
			logger.error("Exception in searchBackgroundInformationAjax method of SPNMonitorAction java class due to "
					+ e.getMessage());
			return "json";
		}

		return "json";

	}
	
	public String loadSPNProviderAjax() {
		SecurityContext securityContext = (SecurityContext) getSession().getAttribute(Constants.SESSION.SECURITY_CONTEXT);
		Integer vendorId = securityContext.getCompanyId();
		
		try {
			List<SPNMonitorVO> spnMonitorList = spnMonitorBO.getSPNProviderList(vendorId);
			getRequest().setAttribute("spnMonitorList",spnMonitorList);	
			
		} catch (BusinessServiceException e) {
			// TODO Auto-generated catch block
			logger.info("Exception in loadSPNProviderAjax" + e);
		}
		
		return SUCCESS;
	}

	
	
	//R11.0 Generating Report in XLS,CSV format
		public String exportBackgroundInformationAjax() throws IOException, SQLException {
			Integer format = 0;
			List<BackgroundInfoProviderVO> backgroundVOList = new ArrayList<BackgroundInfoProviderVO>();
			SearchBackgroundInfoProviderVO searchVO = new  SearchBackgroundInfoProviderVO();

			//Getting the selected format from the front end
			if (getRequest().getParameter(Constants.EXPORT_SELECTED_FORMAT) != null&& StringUtils.isNotEmpty((String) getRequest().getParameter(Constants.EXPORT_SELECTED_FORMAT))) {
				format = Integer.parseInt((String) getRequest().getParameter(Constants.EXPORT_SELECTED_FORMAT));
			}
			//Getting  the Details from Session 
			searchVO = (SearchBackgroundInfoProviderVO) getRequest().getSession().getAttribute(Constants.SEARCH_VO);
			searchVO.setStartIndex(0);
			searchVO.setNumberOfRecords(1000);
			
			//Fetching Background Check details of resources from db
			try {
				backgroundVOList = spnMonitorBO.getBackgroundInformation(searchVO);
			} catch (BusinessServiceException e1) {
				// TODO Auto-generated catch block
				logger.info("error in fetching background details in exportBackgroundInformationAjax excel method of SPNMonitorAction java class"+e1.getMessage()); 
			}
			
			//XLS Format
			if(format.intValue() == 1 && null!=backgroundVOList){
				OutputStream out=null;
				ByteArrayOutputStream outFinal = new ByteArrayOutputStream();
				String currentDate = DateUtils.getFormatedDate(DateUtils.getCurrentDate(),Constants.EXPORT_DATE_FORMAT);
				String fileName = (Constants.EXPORT_FILE_NAME+ currentDate + Constants.XLS_FILE_FORMAT);
				 try 
				 {
					logger.info("start--> exportBackgroundInformationAjax excel method of SPNMonitorAction java class"); 
					
					
					outFinal = spnMonitorBO.getExportToExcel(outFinal,backgroundVOList);
				    		 
					int size = 0;
					if (outFinal != null) {
						size = outFinal.size();
					}
					out = getResponse().getOutputStream();
					getResponse().setContentType("application/vnd.ms-excel");
					getResponse().setContentLength(size);
					getResponse().setHeader("Content-Disposition", "attachment; filename="+fileName);
					getResponse().setHeader("Expires", "0");
					getResponse().setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
					getResponse().setHeader("Pragma", "public");
					outFinal.writeTo(out);			 
				 }
				 catch(Exception e){
						logger.error("Exception in Excel import method in exportBackgroundInformationAjax excel method of SPNMonitorAction java class(excel)", e);
					}
				 finally
				  {
				   if (out != null){
					   out.flush();
					   out.close();
					   outFinal.close();
				   }
				    
				  }
				 logger.info("End--> exportBackgroundInformationAjax excel method of SPNMonitorAction java class");
				 
			}
			//CSV Comma format
			else if (format.intValue() == 2 && null!=backgroundVOList){
				OutputStream out=null;
				ByteArrayOutputStream outFinal = new ByteArrayOutputStream();
				String currentDate = DateUtils.getFormatedDate(DateUtils.getCurrentDate(),Constants.EXPORT_DATE_FORMAT);
				String fileName = (Constants.EXPORT_FILE_NAME+ currentDate + Constants.CSV_FILE_FORMAT);
				 try 
				 {
					logger.info("start--> exportBackgroundInformationAjax CSV Comma method"); 
					
					StringBuffer buffer = null;
					out = getResponse().getOutputStream();
					
					buffer = spnMonitorBO.getExportToCSVComma(backgroundVOList);
				   
					InputStream in = new ByteArrayInputStream(buffer.toString().getBytes("UTF-8"));
					Integer size = buffer.toString().getBytes().length;
					byte[] outputByte = new byte[size];
					while(in.read(outputByte, 0, size) != -1)
					{
						outFinal.write(outputByte, 0, size);
					}
					in.close();
					
					int fileSize = 0;
					if (outFinal != null) {
						fileSize = outFinal.size();
					}
					getResponse().setContentType("application/csv");
					getResponse().setContentLength(fileSize);
					getResponse().setHeader("Content-Disposition", "attachment; filename="+fileName);
					getResponse().setHeader("Expires", "0");
					getResponse().setHeader("Cache-Control","must-revalidate, post-check=0, pre-check=0");
					getResponse().setHeader("Pragma", "public");
					outFinal.writeTo(out);
				
								 
				 }
				 catch(Exception e){
					 logger.error("Exception in exportBackgroundInformationAjax of CSV comma", e);
					}
				 finally
				  {
				   if (out != null){
					   outFinal.close();
					   out.flush();
					   out.close();
				   }
				    
				  }
				 logger.info("End--> exportBackgroundInformationAjax CSV Comma method");
				
			}
			//CSV Pipe format
			else if (format.intValue() == 3 && null!=backgroundVOList){
				OutputStream out=null;
				ByteArrayOutputStream outFinal = new ByteArrayOutputStream();
				String currentDate = DateUtils.getFormatedDate(DateUtils.getCurrentDate(),Constants.EXPORT_DATE_FORMAT);
				String fileName = (Constants.EXPORT_FILE_NAME+ currentDate + Constants.CSV_FILE_FORMAT);
				 try 
				 {
					logger.info("start--> exportBackgroundInformationAjax CSV Pipe method"); 
					StringBuffer buffer = null;
					out = getResponse().getOutputStream();
					
					buffer = spnMonitorBO.getExportToCSVPipe(backgroundVOList);
				   
					InputStream in = new ByteArrayInputStream(buffer.toString().getBytes("UTF-8"));
					Integer size = buffer.toString().getBytes().length;
					byte[] outputByte = new byte[size];
					while(in.read(outputByte, 0, size) != -1)
					{
						outFinal.write(outputByte, 0, size);
					}
					in.close();
					
					int fileSize = 0;
					if (outFinal != null) {
						fileSize = outFinal.size();
					}
					getResponse().setContentType("application/csv");
					getResponse().setContentLength(fileSize);
					getResponse().setHeader("Content-Disposition", "attachment; filename="+fileName);
					getResponse().setHeader("Expires", "0");
					getResponse().setHeader("Cache-Control","must-revalidate, post-check=0, pre-check=0");
					getResponse().setHeader("Pragma", "public");
					outFinal.writeTo(out);								 
				 }
				 catch(Exception e){
						logger.error("Exception in exportBackgroundInformationAjax method of SPNMonitorAction java class(CSV pipe)", e);
					}
				 finally
				  {
				   if (out != null){
					   outFinal.close();
					   out.flush();
					   out.close();
				   }
				    
				  }
				 logger.info("End--> exportBackgroundInformationAjax CSV Pipe method");
			}
			
			return "success";
			
		}
		//To Display the BackgroundCheck History
		public String displayBackgroundHistoryAjax() throws Exception{
			Integer resourceId = 0;
			Integer vendorId = 0;
			String providerName = null;
			String backgroundState = null;
			List<BackgroundCheckHistoryVO> backgroundHistVOList = new ArrayList<BackgroundCheckHistoryVO>();
			BackgroundCheckHistoryVO bgHistVO = new BackgroundCheckHistoryVO();
			//Getting the resourceId and vendorId from request
			if (getRequest().getParameter(Constants.VENDOR_ID) != null&& StringUtils.isNotEmpty((String) getRequest().getParameter(Constants.VENDOR_ID))) {
				vendorId = Integer.parseInt((String) getRequest().getParameter(Constants.VENDOR_ID));
			}
			if (getRequest().getParameter(Constants.BG_RESOURCE_ID) != null&& StringUtils.isNotEmpty((String) getRequest().getParameter(Constants.BG_RESOURCE_ID))) {
				resourceId = Integer.parseInt((String) getRequest().getParameter(Constants.BG_RESOURCE_ID));
			}
			if (getRequest().getParameter(Constants.BACKGROUND_STATE) != null&& StringUtils.isNotEmpty((String) getRequest().getParameter(Constants.BACKGROUND_STATE))) {
				backgroundState = ((String) getRequest().getParameter(Constants.BACKGROUND_STATE));
				if(backgroundState.equalsIgnoreCase(Constants.NOTCLEARED)){
					backgroundState = Constants.NOTCLEARED;
				}
			}
			
			getRequest().setAttribute(Constants.BACKGROUND_STATE, backgroundState);
			getRequest().setAttribute(Constants.BG_RESOURCE_ID, resourceId);
			
			
			bgHistVO.setResourceId(resourceId);
			try{
				providerName = spnMonitorBO.getProviderName(resourceId);
				backgroundHistVOList = spnMonitorBO.getBackgroundCheckHistoryDetails(bgHistVO);
				List<BackgroundCheckHistoryVO> formattedHistList = new ArrayList<BackgroundCheckHistoryVO>();
				if(backgroundHistVOList!=null && !backgroundHistVOList.isEmpty()){
					for(BackgroundCheckHistoryVO histVO:backgroundHistVOList){
						BackgroundCheckHistoryVO VO = new BackgroundCheckHistoryVO();
						if(null!= histVO.getDisplayDate()){
							VO.setFmtDisplayDate(DateUtils.getFormatedDate(histVO.getDisplayDate(),Constants.BACKGROUND_CHECK_STATUS_DATE_FORMAT));
						}
						if(null!= histVO.getVerificationDate()){
							VO.setFmtVerificationDate(DateUtils.getFormatedDate(histVO.getVerificationDate(),Constants.BACKGROUND_CHECK_STATUS_DATE_FORMAT));
						}
						if(null!= histVO.getReverificationDate() && histVO.getCriteriaIdCount()>0){
							VO.setFmtReverificationDate(DateUtils.getFormatedDate(histVO.getReverificationDate(),Constants.BACKGROUND_CHECK_STATUS_DATE_FORMAT));
						}
						VO.setBackgroundStatus(histVO.getBackgroundStatus());
						if(histVO.getCriteriaIdCount()>0){
						VO.setRecertificationStatus(histVO.getRecertificationStatus());
						}
						VO.setChangingComments(histVO.getChangingComments());
						formattedHistList.add(VO);
					}
				}
				getRequest().setAttribute(Constants.BG_PROVIDERNAME, providerName);
				getRequest().setAttribute(Constants.BG_FORMATTEDHISTLIST, formattedHistList);

			}
			catch (Exception e) {
				logger.error("Exception in displayBackgroundHistory method of SPNMonitorAction java class", e);
			}
			return "success";
			
		}
	
	/**
	 * @return the targetExternalAction
	 */
	public String getTargetExternalAction() {
		return targetExternalAction;
	}

	/**
	 * @param targetExternalAction the targetExternalAction to set
	 */
	public void setTargetExternalAction(String targetExternalAction) {
		this.targetExternalAction = targetExternalAction;
	}

	/**
	 * @return the resultUrl
	 */
	public String getResultUrl() {
		return resultUrl;
	}

	/**
	 * @param resultUrl the resultUrl to set
	 */
	public void setResultUrl(String resultUrl) {
		this.resultUrl = resultUrl;
	}

}
