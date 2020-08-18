package com.newco.marketplace.web.action.financemanager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.criteria.PagingCriteria;
import com.newco.marketplace.dto.vo.ledger.AccountHistoryResultVO;
import com.newco.marketplace.dto.vo.ledger.AccountHistoryVO;
import com.newco.marketplace.interfaces.LedgerConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.utils.DateUtils;
import com.newco.marketplace.vo.PaginationVO;
import com.newco.marketplace.web.action.base.SLFinanceManagerBaseAction;
import com.newco.marketplace.web.delegates.ISecurityDelegate;
import com.newco.marketplace.web.dto.FMOverviewHistoryTabDTO;
import com.newco.marketplace.web.dto.TransactionDTO;
import com.newco.marketplace.web.utils.ObjectMapper;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

public class FMOverviewHistoryAction extends SLFinanceManagerBaseAction implements Preparable,ModelDriven<FMOverviewHistoryTabDTO>
{
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(FMOverviewHistoryAction.class.getName());
	
	private FMOverviewHistoryTabDTO historyTabDTO = new FMOverviewHistoryTabDTO();
	private ISecurityDelegate securityBean = null;
	
	public FMOverviewHistoryTabDTO getHistoryTabDTO() {
		return getModel();
	}


	public void setHistoryTabDTO(FMOverviewHistoryTabDTO historyTabDTO) {
		this.historyTabDTO = historyTabDTO;
	}

	
	public void prepare() throws Exception
	{
		createCommonServiceOrderCriteria();		
	}

	private void initialiseHistortyTabDTO()
	{
		historyTabDTO.setCompanyId(_commonCriteria.getCompanyId());
		historyTabDTO.setRoleType(_commonCriteria.getRoleType());
		historyTabDTO.setSlAdminInd(get_commonCriteria().getSecurityContext().getSLAdminInd());
		if (securityBean.checkForBuyerAdmin(get_commonCriteria().getSecurityContext().getUsername())) {
			historyTabDTO.setBuyerAdminInd(true);
		}
		if(_commonCriteria.getRoleId().intValue() == OrderConstants.SIMPLE_BUYER_ROLEID) { historyTabDTO.setRoleType(OrderConstants.ROLE_BUYER); }
		if(historyTabDTO.getRoleType().equals(OrderConstants.ROLE_PROVIDER))
			historyTabDTO.setEntityTypeId(LedgerConstants.LEDGER_ENTITY_TYPE_PROVIDER);
		else if(historyTabDTO.getRoleType().equals(OrderConstants.NEWCO_ADMIN))
			historyTabDTO.setEntityTypeId(LedgerConstants.LEDGER_ENTITY_TYPE_SERVICELIVE_OPERATION);
		else		
			historyTabDTO.setEntityTypeId(LedgerConstants.LEDGER_ENTITY_TYPE_BUYER);
	}
	
	

	
	private void initTransactionTable(String tab, boolean searchPerformed)
	{
		List<TransactionDTO> passedList = new ArrayList<TransactionDTO>();
		
		try{
			long startTime = System.currentTimeMillis();
			long startTime1 = System.currentTimeMillis();

			initialiseHistortyTabDTO();
			logger.info("zfmoverviewinitialiseHistortyTabDTO()"+(System.currentTimeMillis()-startTime1));
			startTime1 = System.currentTimeMillis();

			List<TransactionDTO> list = new ArrayList<TransactionDTO>();
			if (tab.equals(OrderConstants.FM_OVERVIEW)){
				getRequest().getSession().removeAttribute(FM_HISTORY_PAGING_CRITERIA_KEY);
				logger.info("zfmoverviewinitialisebeforefinanceManagerDelegate.getAccountOverv"+(System.currentTimeMillis()-startTime1));
				startTime1 = System.currentTimeMillis();
				passedList = financeManagerDelegate.getAccountOverviewHistory(historyTabDTO);
				logger.info("zfmoverviewinitialiseafterfinanceManagerDelegate.getAccountOverv"+(System.currentTimeMillis()-startTime1));
				startTime1 = System.currentTimeMillis();
				setDateRangeMessage(list, list.size(), searchPerformed);
				logger.info("zfmoverviewsetDateRangeMessage"+(System.currentTimeMillis()-startTime1));
				startTime1 = System.currentTimeMillis();
			}else if (tab.equals(OrderConstants.FM_HISTORY) || tab.equals(OrderConstants.FM_SEARCH)){
				Map<String, Object> sessionMap = ActionContext.getContext().getSession();
				//To retrieve the search criteria
				if(sessionMap.containsKey("fmOverHistDTO")){
					FMOverviewHistoryTabDTO fMOverviewHistoryTabDTO=(FMOverviewHistoryTabDTO)sessionMap.get("fmOverHistDTO");
					historyTabDTO.setCalendarFromDate(fMOverviewHistoryTabDTO.getCalendarFromDate());
					historyTabDTO.setCalendarToDate(fMOverviewHistoryTabDTO.getCalendarToDate());
					historyTabDTO.setSearchType(fMOverviewHistoryTabDTO.getSearchType());
				}
				
				AccountHistoryResultVO accountHistoryResultVO =null;
				try
				{
					PagingCriteria PagingCriteria = (PagingCriteria) sessionMap.get(FM_HISTORY_PAGING_CRITERIA_KEY);
					if(PagingCriteria==null){
						PagingCriteria=new PagingCriteria();
						sessionMap.put(FM_HISTORY_PAGING_CRITERIA_KEY, PagingCriteria);
					}
					logger.info("zfmoverviewbeforeaccountHistoryResultVO=financeManagerDelegate"+(System.currentTimeMillis()-startTime1));
					startTime1 = System.currentTimeMillis();
					accountHistoryResultVO = financeManagerDelegate.getAccountOverviewHistoryFMOverview(historyTabDTO,PagingCriteria);
					logger.info("zfmoverviewafteraccountHistoryResultVO=financeManagerDelegate"+(System.currentTimeMillis()-startTime1));
					startTime1 = System.currentTimeMillis();
					sessionMap.put("maxNoOfDays", accountHistoryResultVO.getMaxSearchDaysWalletHistory());
				}catch(Exception e){
				 e.printStackTrace();
				}
				PaginationVO paginationVOFMOverview = null;
				List<TransactionDTO> overviewHistoryDTO = null;
				if (accountHistoryResultVO != null) {
					overviewHistoryDTO = ObjectMapper.convertOverviewHistoryVOtoDTO(accountHistoryResultVO.getAcctHistory());
					paginationVOFMOverview = accountHistoryResultVO.getPaginationVO();
				}
				passedList = overviewHistoryDTO;
				setDateRangeMessage(passedList, overviewHistoryDTO.size(), searchPerformed);
				ExportMeassageCheck(historyTabDTO,accountHistoryResultVO);
				setAttribute("overviewHistoryDTO", overviewHistoryDTO);
				setAttribute("paginationVOFMOverview", paginationVOFMOverview);
				if(sessionMap.containsKey("fmOverHistDTO")){
					FMOverviewHistoryTabDTO fMOverviewHistoryTabDTO=(FMOverviewHistoryTabDTO)sessionMap.get("fmOverHistDTO");
					fMOverviewHistoryTabDTO.setTransaction(passedList);
				}
			}
			historyTabDTO.setTransaction(passedList);	
			if(passedList!=null)
				historyTabDTO.setRecordSize(passedList.size());
			logger.info("zfmoverviewlast"+(System.currentTimeMillis()-startTime1));
			logger.info("zfmoverview-timetaken"+(System.currentTimeMillis()-startTime));
			
		}catch(Exception e){
			logger.error("FMOverviewHistoryAction.initTransactionTable()-->EXCEPTION-->", e);
		}

	}

	private void ExportMeassageCheck(FMOverviewHistoryTabDTO historyTabDTO,AccountHistoryResultVO accountHistoryResultVO){
		if(accountHistoryResultVO.getExportMessageCheck()!=null && accountHistoryResultVO.getExportMessageCheck().equals("true")){
			historyTabDTO.setExportMessageCheck("limitExceed");
			historyTabDTO.setMessage("&nbsp;&nbsp;Your search returned <b>"+accountHistoryResultVO.getTotalCount()+"</b> records.The maximum " +
					"number records you can export is <b>"+accountHistoryResultVO.getWalletMaxExportLimit()+"</b>.<BR>" +
					"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;You can export the first "+accountHistoryResultVO.getWalletMaxExportLimit()+" records by clicking the " +
					"\"Export to file\" button below, or narrow your search criteria.");
				
		}
		if(accountHistoryResultVO.getTotalCount()==null || accountHistoryResultVO.getTotalCount()<=0)
			historyTabDTO.setTotalCount(0);
			
	}
	
	private void setDateRangeMessage(List<TransactionDTO> list, Integer orignalSize, boolean searchPerformed){
		String fromDate ="";
		String toDate = "";
		Integer listSize = 0;
		String message = "";
		
		if(historyTabDTO!=null && historyTabDTO.getDateRangeChecked()!=null && historyTabDTO.getSearchType().equalsIgnoreCase(OrderConstants.INVALID_SEARCH_TYPE))
			return;
		
		if (searchPerformed){
			try{
			FMOverviewHistoryTabDTO fmOverHistModel = getModel();
			DateFormat df= new SimpleDateFormat("yyyy-MM-dd");
			fromDate = fmOverHistModel.getCalendarFromDate().toString();
			//workaround to show display correct time
			String startDate=fromDate.substring(0,11);
		    String endDate=fromDate.substring(19);
		    fromDate=startDate+"00:00:00"+endDate;
			toDate   = fmOverHistModel.getCalendarToDate().toString();
			Date d1 = df.parse(df.format(fmOverHistModel.getCalendarToDate()));
			Date d2 = df.parse(df.format(new Date()));
				if (fmOverHistModel.getSearchType().equalsIgnoreCase("DateRange")) {
					startDate = toDate.substring(0, 11);
					endDate = toDate.substring(19);
					toDate = startDate + "23:59:59" + endDate;
				} else {
					toDate = new Date().toString();
				}
			}catch(Exception e){logger.error("FMOverviewHistoryAction.setDateRangeMessage()-->EXCEPTION-->", e);}
			
		}else{
			listSize = list.size();
			
			if (listSize > 0){
				if (list.get(0).getDate() !=null){
					toDate = list.get(0).getDate();
				}
		
				if (list.get(listSize-1).getDate() !=null){
					fromDate = list.get(listSize-1).getDate();
				}
			}else{
				message = "No Activity";
			}
			
		}
		
		if (!fromDate.equals(null) && !toDate.equals(null) && fromDate.length() > 0 && toDate.length() > 0){
			message = "All activity from " + fromDate + " to " + toDate;
		}	
				
		if (orignalSize > 200){
			message += message + "  Results exceeded over 200 rows.  Only 200 Records are shown. Please refine the search.";
		}
		
		historyTabDTO.setDateRangeMessage(message);
		
	}
	
	private List<TransactionDTO> determineList(Integer sizeLimit, List<TransactionDTO> transactions){
		List<TransactionDTO> overviewList = new ArrayList<TransactionDTO>();
		
		Integer loopTo = 0;
		
		//If the sizeLimit is greater than the #of records coming in,
		//then take the transactions list size or else you'll get an out
		//of bounds error
		if (sizeLimit > transactions.size()){
			loopTo = transactions.size();
		}else{
			loopTo = sizeLimit;
		}
		
		
		Integer i;
		
		for (i=0; i<loopTo; i++){
			TransactionDTO transItem = new TransactionDTO();
			
			transItem = transactions.get(i);
			overviewList.add(transItem);
		}
		
		return overviewList;
	}
	


	
	public String overview(){
		String role = get_commonCriteria().getRoleType();
		
		initTransactionTable(OrderConstants.FM_OVERVIEW, false);
		
		historyTabDTO.setTab(OrderConstants.FM_OVERVIEW);
		
		setSessionAttributeExport(historyTabDTO);
		
		return role;
	}
	
	public String search(){
		try{
			getRequest().getSession().removeAttribute("fmOverHistDTO");
			getRequest().getSession().removeAttribute("paginationVOFMOverview");
			getRequest().getSession().removeAttribute(FM_HISTORY_PAGING_CRITERIA_KEY);

			FMOverviewHistoryTabDTO fmOverHistModel = getModel();
		
			//1. Determine whether the search is a dropdown interval search
			//or a picked date range search
			if (fmOverHistModel.getRadioButton() != null){
				//KEY: 1==Interval and 2==DateRange
				
				if (fmOverHistModel.getRadioButton()==1){
					resetSearch(fmOverHistModel);
					
					if (fmOverHistModel.getSelectedCalInterval() == null ||
							fmOverHistModel.getSelectedCalInterval().equals("0")){
						fmOverHistModel.setSearchType(OrderConstants.INVALID_SEARCH_TYPE);
						setErrorMessage(fmOverHistModel, "You must select an Interval");
						return GOTO_COMMON_FINANCE_MGR_CONTROLLER;
					}else{
						String key = fmOverHistModel.getSelectedCalInterval();
						fmOverHistModel.setCalendarFromDate(calculateIntervalFromDate(key));
						fmOverHistModel.setCalendarToDate(DateUtils.getCurrentDate());
						fmOverHistModel.setSearchType(OrderConstants.INTERVAL_SEARCH_TYPE);
					}
				}else if (fmOverHistModel.getRadioButton()== 2){
					resetSearch(fmOverHistModel);
					
					String fromDate = fmOverHistModel.getDojoCalendarFromDate();
					String toDate = fmOverHistModel.getDojoCalendarToDate();
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					if (fromDate.equals(null) || (toDate.equals(null) ||toDate.length() < 1 || fromDate.length() < 1)){
						setErrorMessage(fmOverHistModel, "You must select a Date Range");
						fmOverHistModel.setSearchType(OrderConstants.INVALID_SEARCH_TYPE);
						if(fromDate!=null && !fromDate.equals("")){
							Date formatFromDate = new Date();
							formatFromDate = format.parse(fromDate);
						fmOverHistModel.setCalendarFromDate(formatFromDate);
						}
						if(toDate!=null && !toDate.equals("")){
						Date formatToDate = new Date();
						formatToDate = format.parse(toDate);
						fmOverHistModel.setCalendarToDate(formatToDate);
						}
						//return GOTO_COMMON_FINANCE_MGR_CONTROLLER;
					}else{
						//Format to Date
				      
	
				        Date formatFromDate = new Date();
						Date formatToDate = new Date();
						
				        formatFromDate = format.parse(fromDate);
						formatToDate = format.parse(toDate);
						
						Date currentDate =format.parse(format.format(new Date()));
						if (formatFromDate.after(formatToDate) ){
							setErrorMessage(fmOverHistModel, "From Date Range is greater than the To Date Range.");
							fmOverHistModel.setSearchType(OrderConstants.INVALID_SEARCH_TYPE);
							//return GOTO_COMMON_FINANCE_MGR_CONTROLLER;
						}else{
							fmOverHistModel.setSearchType(OrderConstants.DATERANGE_SEARCH_TYPE);
						}
												
						fmOverHistModel.setCalendarFromDate(formatFromDate);
						fmOverHistModel.setCalendarToDate(formatToDate);
					}
				}
			}
			
			
			if (fmOverHistModel.getCalendarFromDate() != null && fmOverHistModel.getCalendarToDate()!= null){
				initTransactionTable(OrderConstants.FM_SEARCH, true);
			}
			
			setSessionAttribute(fmOverHistModel);
			setSessionAttributeExport(fmOverHistModel);
			
			setDefaultTab(OrderConstants.FM_HISTORY);
		}catch(Exception e){
			logger.error("FMOverviewHistoryAction.search()-->EXCEPTION-->", e);
		}
			
		return GOTO_COMMON_FINANCE_MGR_CONTROLLER;
	}
	
	public FMOverviewHistoryTabDTO getExportRecords(FMOverviewHistoryTabDTO historyTabDTO)
	{
		Map<String, Object> sessionMap = ActionContext.getContext().getSession();
		List<TransactionDTO> overviewHistoryDTO = null;
		List<AccountHistoryVO>  accountHistoryVO =null;
		FMOverviewHistoryTabDTO fMOverviewHistoryTabDTO=null;
		
		if(sessionMap.containsKey("fmOverHistDTO")){
			 fMOverviewHistoryTabDTO=(FMOverviewHistoryTabDTO)sessionMap.get("fmOverHistDTO");
		}else{
			 fMOverviewHistoryTabDTO=historyTabDTO; 
		}
		try{
			  accountHistoryVO=financeManagerDelegate.getExportResultSet(fMOverviewHistoryTabDTO);
		}catch(Exception e){
			logger.error("Exception in Excel import action", e);
		}
		overviewHistoryDTO = ObjectMapper.convertOverviewHistoryVOtoDTO(accountHistoryVO);
		fMOverviewHistoryTabDTO.setTransaction(overviewHistoryDTO);
		return fMOverviewHistoryTabDTO;
	}

	public String exportToExcel() throws IOException{

        
		 OutputStream out=null;
		 try 
		 {
			initialiseHistortyTabDTO();
			FMOverviewHistoryTabDTO listAfterSearchDTO=getExportRecords(historyTabDTO);
					
			logger.info("start--> fmExportToExcel method"); 
			
			ByteArrayOutputStream outFinal = new ByteArrayOutputStream();
			outFinal = financeManagerDelegate.getExportToExcel(listAfterSearchDTO,outFinal);
		    		 
			int size = 0;
			if (outFinal != null) {
				size = outFinal.size();
			}
			out = getResponse().getOutputStream();
			getResponse().setContentType("application/vnd.ms-excel");
			getResponse().setContentLength(size);
			getResponse().setHeader("Content-Disposition", "attachment; filename=Transactions.xls");
			getResponse().setHeader("Expires", "0");
			getResponse().setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			getResponse().setHeader("Pragma", "public");
			outFinal.writeTo(out);			 
		 }
		 catch(Exception e){
				logger.error("Exception in Excel import action", e);
			}
		 finally
		  {
		   if (out != null){
			   out.flush();
			   out.close();
		   }
		    
		  }
		 logger.info("End--> fmExportToExcel method");
		 return "xls";
		  
	 
	}
	
	private void setErrorMessage(FMOverviewHistoryTabDTO model, String errorMessage){
		model.setErrorMsg(errorMessage);
		setSessionAttribute(model);
		setDefaultTab(OrderConstants.FM_HISTORY);
	}
	
	private void setSessionAttribute(FMOverviewHistoryTabDTO model){
		getRequest().getSession().setAttribute("fmOverHistDTO", model);
		
	}
	
	private void setSessionAttributeExport(FMOverviewHistoryTabDTO model){
		getRequest().getSession().setAttribute("listAfterSearchDTO", model);
		
	}
	
	private Date calculateIntervalFromDate(String key){
		Integer intervalNum = 0;

		String interNum = new Character(key.charAt(2)).toString();
		intervalNum = Integer.parseInt(interNum);			
		String passedInterval = key.substring(3);
		
		Date dateUtils =  DateUtils.calcDateBasedOnInterval(intervalNum, passedInterval);
		
		return dateUtils;
	}
	
	public String history(){

		String role = get_commonCriteria().getRoleType();
		
		initTransactionTable(OrderConstants.FM_HISTORY, false);

		historyTabDTO.setTab(OrderConstants.FM_HISTORY);
		historyTabDTO.setIntervalChecked("checked");
		
		return role;
	}
	
	public FMOverviewHistoryTabDTO getModel()
	{
		return this.historyTabDTO;
	}
	
	public void setModel(FMOverviewHistoryTabDTO dto){
		
		this.historyTabDTO = dto;
	}
	
	private void resetSearch(FMOverviewHistoryTabDTO model){
		//KEY: 1==Interval and 2==DateRange

		if (model.getRadioButton() == 1){
			//Since this is the Interval Search, clear the DateRange search dates
			model.setDojoCalendarFromDate("");
			model.setDojoCalendarToDate("");
			model.setIntervalChecked("checked");
			model.setDateRangeChecked(null);
		}
		if (model.getRadioButton() == 2){
			//Since this is the Date Search, clear the Interval search drop down value
			model.setSelectedCalInterval("0"); 	//0 is "Please Select"
			model.setDateRangeChecked("checked");
			model.setIntervalChecked(null);
		}
		model.setTransaction(null);
		model.setDateRangeMessage(null);
	
	}

	public ISecurityDelegate getSecurityBean() {
		return securityBean;
	}


	public void setSecurityBean(ISecurityDelegate securityBean) {
		this.securityBean = securityBean;
	}
}
