/**
 * 
 */
package com.newco.marketplace.web.action.financemanager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.jsp.jstl.fmt.LocalizationContext;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.businessImpl.ABaseCriteriaHandler;
import com.newco.marketplace.criteria.PagingCriteria;
import com.newco.marketplace.dto.vo.CryptographyVO;
import com.newco.marketplace.dto.vo.financemanger.BuyerSOReportVO;
import com.newco.marketplace.dto.vo.financemanger.ExportStatusVO;
import com.newco.marketplace.dto.vo.financemanger.ProviderSOReportVO;
import com.newco.marketplace.dto.vo.financemanger.ReportContentVO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.MPConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.util.Cryptography;
import com.newco.marketplace.utils.DateUtils;
import com.newco.marketplace.vo.PaginationVO;
import com.newco.marketplace.web.action.base.SLFinanceManagerBaseAction;
import com.newco.marketplace.web.dto.FMReportTabDTO;
import com.newco.marketplace.web.dto.SOWError;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

/**
 * @author Infosys
 * 
 */
public class FMReportsAction extends SLFinanceManagerBaseAction implements
		Preparable, ModelDriven<FMReportTabDTO> {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(FMReportsAction.class
			.getName());

	private List<SOWError> reportErrors = new ArrayList<SOWError>();
	private FMReportTabDTO fmReportTabDTO = new FMReportTabDTO();
	private ReportContentVO reportContentVO;
	private PaginationVO paginationVO = null;
	private Cryptography cryptography;

	public void prepare() throws Exception {
		createCommonServiceOrderCriteria();
	}

	/**
	 * Executes on loading Reports tab for Buyer and Provider.
	 * 
	 * @return role of the logged in user.
	 * */
	public String reports() throws Exception {
		String role = get_commonCriteria().getRoleType();
		getRequest().setAttribute("targetOffset",
				getRequest().getParameter("targetOffset"));
		getReportStatus();
		if (role.equals(PROVIDER)) {
			role = PROVIDER_RPT;
		} else if (role.equals(BUYER)) {
			role = BUYER_RPT;
		}
		return role;
	}

	/**
	 * This method generates reports for display purpose. For each report this
	 * method will fetch the report criteria from db and populate DTO. This DTO
	 * will be kept in session. If no report/ file is generated for a report
	 * with Completed status, that report will be deleted and an error message
	 * will be displayed.
	 * 
	 * */
	public String generateReports() throws Exception {

		String role = get_commonCriteria().getRoleType();
		Integer resourceId = get_commonCriteria().getVendBuyerResId();
		Map<String, Object> sessionMap = ActionContext.getContext()
				.getSession();
		Integer reportId = Integer.parseInt(getRequest().getParameter(
				"reportId")); // Type of report to be generated.
		String formInd = getRequest().getParameter("formInd");
		getSession().setAttribute("formType", formInd); // To show error/success
														// messages in
														// appropriate form
														// sections
		FMReportTabDTO reportTabDTO = null;
		FMReportTabDTO fmReportTabDTO = new FMReportTabDTO();
		fmReportTabDTO.setReportId(reportId);
		fmReportTabDTO.setResourceId(resourceId);
		try {
			reportTabDTO = financeManagerDelegate
					.getReportCritirea(fmReportTabDTO);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			String errMsg = getMessage("fm_reports_no_file_to_download");
			getSession().setAttribute("errorMessage", errMsg);
			// Refresh the page on error
			if (NEWCO_ADMIN.equalsIgnoreCase(role)) {
				return "slAdmin_refresh";
			} else {
				return "general_refresh";
			}
		}
		if (null == reportTabDTO) {
			LOGGER.error(getMessage("fm_reports_general_error"));
			SOWError error = new SOWError(null,
					getMessage("fm_reports_general_error"), "Exception");
			reportErrors.add(error);
			// Refresh the page on error
			if (NEWCO_ADMIN.equalsIgnoreCase(role)) {
				return "slAdmin_refresh";
			} else {
				return "general_refresh";
			}
		}
		// Set report type in request	 for	 pagination	 purpose.
		getRequest().setAttribute("reportType", reportTabDTO.getReportName()); 
		List<ProviderSOReportVO> listOfProviderVO = null;
		List<BuyerSOReportVO> listOfBuyerVO = null;
		String reportName = reportTabDTO.getReportName();
		int totalRecords = 0;
		totalRecords = reportTabDTO.getTotalRecords();
		// If total record count is zero or exceeds a maximum limit, show
		// warning message. No display needed.
		if (!isDisplayNeeded(totalRecords)) {
			if (NEWCO_ADMIN.equalsIgnoreCase(role)) {
				return "slAdmin_refresh";
			} else {
				return "general_refresh";
			}
		}
		// Setting pagination criteria.
		sessionMap.remove(FM_REPORTS_PAGING_CRITERIA_KEY);
		PagingCriteria pagingCriteria = new PagingCriteria();
		pagingCriteria.setPageSize(REPORT_PAGE_SIZE);
		pagingCriteria.setEndIndex(REPORT_PAGE_SIZE);
		sessionMap.put(FM_REPORTS_PAGING_CRITERIA_KEY, pagingCriteria);

		reportTabDTO.setRoleType(role);
		String reportFooter = reportTabDTO.getReportFooter();
		reportFooter = null == reportFooter ? "0.00" : reportFooter;

		try {
			// Provider reports
			if (PROVIDER_RPT.equalsIgnoreCase(role)
					|| NEWCO_ADMIN.equalsIgnoreCase(role)) {

				String providers = reportTabDTO.getProviders();
				if (!"".equals(providers) && null != providers) {
					reportTabDTO
							.setProviderList(convertCommaSepStrToList(providers));
				}
				// If report is for specific buyers
				if (reportTabDTO.isReportForSpecificBuyers()) {
					String buyers = reportTabDTO.getBuyers();
					reportTabDTO.setBuyerList(convertCommaSepStrToList(buyers));
				}
				/* Provider Payments by Service Order Report */
				if (PROVIDER_SO_REPORT.equalsIgnoreCase(reportName)) {
					getRequest().getSession().removeAttribute(PROVIDER_SO_DTO);
					sessionMap.put(PROVIDER_SO_TOTAL_REC, totalRecords);
					// Set LIMIT criteria to DTO.
					setPaginationToDTO(reportTabDTO, totalRecords,
							pagingCriteria);
					// Set DTO in session
					sessionMap.put(PROVIDER_SO_DTO, reportTabDTO);
					listOfProviderVO = financeManagerDelegate
							.getProviderPaymentByServiceOrder(reportTabDTO);
					setReportContentVOProvSOReport(listOfProviderVO,
							reportFooter);

					/* Provider Revenue Summary Report */
				} else if (PROVIDER_REV_REPORT.equalsIgnoreCase(reportName)) {
					getRequest().getSession().removeAttribute(PROVIDER_REV_DTO);
					sessionMap.put(PROVIDER_REV_TOTAL_REC, totalRecords);
					// Set LIMIT criteria to DTO.
					setPaginationToDTO(reportTabDTO, totalRecords,
							pagingCriteria);
					sessionMap.put(PROVIDER_REV_DTO, reportTabDTO);
					listOfProviderVO = financeManagerDelegate
							.getProviderNetSummaryReport(reportTabDTO);
					setReportContentNetSummary(listOfProviderVO, reportFooter);
				}
			}
			/* Buyer REPORTS */
			if (BUYER_RPT.equalsIgnoreCase(role)
					|| NEWCO_ADMIN.equalsIgnoreCase(role)) {
				Integer buyerId = get_commonCriteria().getSecurityContext()
						.getCompanyId();
				reportTabDTO.setRoleId(buyerId);
				// For SLAdmin. Admin has to specify buer ids for Buyer reports.
				String buyers = reportTabDTO.getBuyers();
				if (!"".equals(buyers) && null != buyers) {
					reportTabDTO.setBuyerList(convertCommaSepStrToList(buyers));
				}
				if (reportTabDTO.isReportForSpecificProviders()) {
					String providers = reportTabDTO.getProviders();
					reportTabDTO
							.setProviderList(convertCommaSepStrToList(providers));
				}

				if (BUYER_SO_REPORT.equalsIgnoreCase(reportName)) {
					getRequest().getSession().removeAttribute(BUYER_SO_DTO);
					// Set LIMIT criteria to DTO.
					setPaginationToDTO(reportTabDTO, totalRecords,
							pagingCriteria);
					sessionMap.put(BUYER_SO_TOTAL_REC, totalRecords);
					sessionMap.put(BUYER_SO_DTO, reportTabDTO);
					listOfBuyerVO = financeManagerDelegate
							.getBuyerPaymentByServiceOrder(reportTabDTO);
					setReportContentVOBuyerSOReport(listOfBuyerVO, reportFooter);
					/* Buyer Payments by TaxpayerID Report */
				} else if (BUYER_TAXID_REPORT.equalsIgnoreCase(reportName)) {
					getRequest().getSession().removeAttribute(BUYER_TAXID_DTO);
					// Set LIMIT criteria to DTO.
					Integer roleId = get_commonCriteria().getRoleId();
					reportTabDTO.setId(roleId);
					setPaginationToDTO(reportTabDTO, totalRecords,
							pagingCriteria);
					sessionMap.put(BUYER_TAXID_TOTAL_REC, totalRecords);
					sessionMap.put(BUYER_TAXID_DTO, reportTabDTO);
					String filePath = reportTabDTO.getFilePath();
					if (!StringUtils.isEmpty(filePath)) {
						listOfBuyerVO = financeManagerDelegate
								.getBuyerReportByTaxPayerID(reportTabDTO);
					}
					// listOfBuyerVO = financeManagerDelegate
					// .getBuyerReportByTaxPayerID(reportTabDTO);
					// Put Taxpayer report vo in session
					sessionMap.put("BUYER_TAXPAYER_VO", listOfBuyerVO);
					List<BuyerSOReportVO> nextListOfBuyerVO = getNextListOfBuyerVOs(
							listOfBuyerVO, reportTabDTO);
					setReportContentVOBuyerTaxPayerID(nextListOfBuyerVO,
							reportFooter);
				}
			}
		} catch (DelegateException e) {
			LOGGER.error("Error while generating reports", e);
			throw new Exception(e);
		}
		// Put DTO is session for pagination purpose.
		setDtoInSession(reportName, reportTabDTO);
		// Display sum at bottom of the report only when last page is being
		// requested.
		if (reportContentVO.getTotalPage() > 1) {
			reportContentVO.getFooterList().clear();
		}else{
			reportContentVO.setInfoMessage(getMessage("fm_reports_payment_footer_info"));
		}
		
		reportContentVO.setPageTitle(reportTabDTO.getFileName());
		// On success, display report.
		return GENERATE_REPORT;
	}

	/**
	 * Deletes report criteria from db. Using the report id passed as request
	 * parameter, this method will delete the corresponding report. The row in
	 * DB will be marked as deleted, and for a completed Report, the csv file
	 * will be deleted from server.
	 * */
	public String deleteReports() {
		SOWError error = null;
		String role = get_commonCriteria().getRoleType();
		FMReportTabDTO reportTabDTO = new FMReportTabDTO();
		Integer reportId = Integer.parseInt(getRequest().getParameter(
				"reportId"));
		String formInd = getRequest().getParameter("formInd");
		getSession().setAttribute("formType", formInd);
		Integer resourceId = get_commonCriteria().getVendBuyerResId();
		reportTabDTO.setResourceId(resourceId);
		reportTabDTO.setReportId(reportId);
		boolean success = false;
		reportErrors.clear();
		try {
			success = financeManagerDelegate.deleteReport(reportTabDTO);
		} catch (DataServiceException e) {
			LOGGER.error(e.getMessage());
			// error = new SOWError(null, e.getMessage(), "exception");
			success = false;
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
			// error = new SOWError(null, e.getMessage(), "exception");
			success = false;
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			// error = new SOWError(null, e.getMessage(), "exception");
			success = false;
		}
		if (!success) {
			error = new SOWError(null,
					getMessage("fm_reports_no_file_to_download"), "");
			reportErrors.add(error);
			String errMsg = getMessage("fm_reports_no_file_to_download");
			getSession().setAttribute("errorMessage", errMsg);
		} else {
			String successMsg = getMessage("act_completed_success");
			getSession().setAttribute("successMsg", successMsg);
		}
		if (NEWCO_ADMIN.equalsIgnoreCase(role)) {
			return "slAdmin_refresh";
		} else {
			return "general_refresh";
		}
	}

	/**
	 * The method downloads a completed report. If csv file doesn't exists in
	 * server the report will be marked as deleted and an error message will be
	 * displayed.
	 * **/
	public String downloadReports() {
		FMReportTabDTO reportTabDTO = null;
		FMReportTabDTO fmReportTabDTO = new FMReportTabDTO();
		String role = get_commonCriteria().getRoleType();
		Integer reportId = Integer.parseInt(getRequest().getParameter(
				"reportId"));
		String formInd = getRequest().getParameter("formInd");
		getSession().setAttribute("formType", formInd);
		Integer resourceId = get_commonCriteria().getVendBuyerResId();
		SOWError error = null;
		fmReportTabDTO.setResourceId(resourceId);
		fmReportTabDTO.setReportId(reportId);
		OutputStream out = null;
		ByteArrayOutputStream outFinal = null;
		String errMsg = "";
		try {
			reportTabDTO = financeManagerDelegate
					.getReportCritirea(fmReportTabDTO);
			if (null != reportTabDTO
					&& !StringUtils.isEmpty(reportTabDTO.getFilePath())) {
				// If file name in db is not set in any case make file name as
				// Reports.csv.
				String fileName = StringUtils.isEmpty(reportTabDTO
						.getFileName()) ? "Reports.csv" : reportTabDTO
						.getFileName() + ".csv";
				// Get the file content from server.
				outFinal = financeManagerDelegate.getFileContent(reportTabDTO
						.getFilePath());
				int fileSize = 0;
				if (outFinal != null) {
					fileSize = outFinal.size();
				} else {
					outFinal = new ByteArrayOutputStream();
				}
				out = getResponse().getOutputStream();
				getResponse().setContentType("application/csv");
				getResponse().setContentLength(fileSize);
				getResponse().setHeader("Content-Disposition",
						"attachment; filename=" + fileName);
				getResponse().setHeader("Expires", "0");
				getResponse().setHeader("Cache-Control",
						"must-revalidate, post-check=0, pre-check=0");
				getResponse().setHeader("Pragma", "public");
				outFinal.writeTo(out);
			} else {
				LOGGER.error("No file to download!");
				errMsg = getMessage("fm_reports_no_file_to_download");
			}
		} catch (Exception e) {
			LOGGER.error("Exception in CSV export action", e);
			error = new SOWError(null,
					getMessage("fm_reports_no_file_to_download"), "");
			errMsg = getMessage("fm_reports_no_file_to_download");
			reportErrors.add(error);
		} finally {
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					LOGGER.error(e);
				}
			}
		}
		if (!"".equals(errMsg)) {
			getSession().setAttribute("errorMessage", errMsg);
			if (NEWCO_ADMIN.equalsIgnoreCase(role)) {
				return "slAdmin_refresh";
			} else {
				return "general_refresh";
			}
		}
		return "csv";
	}

	/**
	 * Populate VO to display the results for Provider SO report
	 * 
	 * */
	private void setReportContentNetSummary(
			List<ProviderSOReportVO> listOfProviderVO, String reportFooter) {
		if (reportContentVO == null) {
			reportContentVO = new ReportContentVO();
		}
		List<String> headerList = reportContentVO.getHeaderList();
		List<String> footerList = reportContentVO.getFooterList();
		NumberFormat formatter = new DecimalFormat(PRICE_FORMAT);
		String role = get_commonCriteria().getRoleType();
		String header = "";
		/* Fetch report header from prop files */
		if (NEWCO_ADMIN.equalsIgnoreCase(role)) {
			footerList.add("");
			footerList.add("");
			header = getMessage("fm_reports_provider_revenue_report_header_admin");
		} else {
			header = getMessage("fm_reports_provider_revenue_report_header");
		}
		/* Convert comma separated headers into a list of string */
		List<String> headers = convertCommaSepStrToList(header);
		headerList.addAll(headers);
		List<ArrayList<String>> bodyMap = reportContentVO.getCommonBody();
		reportContentVO.setHeaderList(headerList);
		for (ProviderSOReportVO providerSOReportVO : listOfProviderVO) {
			ArrayList<String> bodyRowList = new ArrayList<String>();
			if (NEWCO_ADMIN.equalsIgnoreCase(role)) {
				if (null != providerSOReportVO.getProviderFirmId()) {
					bodyRowList.add(providerSOReportVO.getProviderFirmId()
							.toString());
				}
				bodyRowList.add(providerSOReportVO.getProviderFirmName());
			}
			bodyRowList.add(providerSOReportVO.getBuyerId());
			bodyRowList.add(providerSOReportVO.getBuyerName());
			bodyRowList
					.add("$"
							+ formatter.format(providerSOReportVO
									.getTotalFinalPrice()));
			bodyRowList.add("$"
					+ formatter.format(providerSOReportVO.getServiceLiveFee()));
			bodyRowList.add("$"
					+ formatter.format(providerSOReportVO.getNetPayment()));
			bodyMap.add(bodyRowList);
		}
		// Sum to be displayed at the bottom of the last page.
		List<String> totals = convertCommaSepStrToList(reportFooter);
		for (int loop = 0; loop < totals.size(); ++loop) {
			footerList.add("$"
					+ formatter.format(Double.parseDouble(totals.get(loop))));
		}
	}

	/**
	 * On clicking next/previous or go to in pagination Pagination criteria and
	 * report criteria will be taken from session
	 * */
	public String nextPage() throws Exception {
		String role = get_commonCriteria().getRoleType();
		Map<String, Object> sessionMap = ActionContext.getContext()
				.getSession();
		FMReportTabDTO reportTabDTO = null;
		// To get the type of record
		reportTabDTO = getModel();
		int totalRecords = 0;
		String reportName = "";
		String reportType = reportTabDTO.getReportType();
		String requestRandom = reportTabDTO.getRequestRandom();
		// Get DTO from session
		if (!StringUtils.isEmpty(reportType)
				&& !StringUtils.isEmpty(requestRandom)) {
			reportTabDTO = null;
			reportTabDTO = (FMReportTabDTO) sessionMap.get(reportType
					.concat(requestRandom));
			if (null == reportTabDTO) {
				LOGGER.error(getMessage("fm_reports_general_error"));
			}
		}
		getRequest().setAttribute("reportType", reportType);
		getRequest().setAttribute("requestRandom", requestRandom);
		PagingCriteria pagingCriteria = handleReportPagingCriteria();
		pagingCriteria.setPageSize(REPORT_PAGE_SIZE);
		sessionMap.put(FM_REPORTS_PAGING_CRITERIA_KEY, pagingCriteria);
		List<ProviderSOReportVO> listOfProviderVO = null;
		List<BuyerSOReportVO> listOfBuyerVO = null;
		reportName = reportTabDTO.getReportName();
		String reportFooter = reportTabDTO.getReportFooter();
		reportFooter = (null == reportFooter) ? "" : reportFooter;
		try {
			/* Provider Reports */
			if (PROVIDER_RPT.equalsIgnoreCase(role)
					|| NEWCO_ADMIN.equalsIgnoreCase(role)) {
				Integer providerFirmId = get_commonCriteria()
						.getSecurityContext().getCompanyId();
				reportTabDTO.setRoleId(providerFirmId);
				String providers = reportTabDTO.getProviders();
				if (!"".equals(providers) && null != providers) {
					reportTabDTO
							.setProviderList(convertCommaSepStrToList(providers));
				}
				if (reportTabDTO.isReportForSpecificBuyers()) {
					String buyers = reportTabDTO.getBuyers();
					reportTabDTO.setBuyerList(convertCommaSepStrToList(buyers));
				}
				if (PROVIDER_SO_REPORT.equalsIgnoreCase(reportName)) {
					totalRecords = (Integer) sessionMap
							.get(PROVIDER_SO_TOTAL_REC);
					setPaginationToDTO(reportTabDTO, totalRecords,
							pagingCriteria);
					sessionMap.put(PROVIDER_SO_DTO, reportTabDTO);
					listOfProviderVO = financeManagerDelegate
							.getProviderPaymentByServiceOrder(reportTabDTO);
					setReportContentVOProvSOReport(listOfProviderVO,
							reportFooter);
				} else if (PROVIDER_REV_REPORT.equalsIgnoreCase(reportName)) {
					totalRecords = (Integer) sessionMap
							.get(PROVIDER_REV_TOTAL_REC);
					setPaginationToDTO(reportTabDTO, totalRecords,
							pagingCriteria);
					sessionMap.put(PROVIDER_REV_DTO, reportTabDTO);
					listOfProviderVO = financeManagerDelegate
							.getProviderNetSummaryReport(reportTabDTO);
					setReportContentNetSummary(listOfProviderVO, reportFooter);
				}
			}
			/* Buyer reports */
			if (BUYER_RPT.equalsIgnoreCase(role)
					|| NEWCO_ADMIN.equalsIgnoreCase(role)) {
				String providers = reportTabDTO.getProviders();
				if (!StringUtils.isEmpty(providers)) {
					reportTabDTO
							.setProviderList(convertCommaSepStrToList(providers));
				}
				if (reportTabDTO.isReportForSpecificBuyers()) {
					String buyers = reportTabDTO.getBuyers();
					reportTabDTO.setBuyerList(convertCommaSepStrToList(buyers));
				}
				if (BUYER_SO_REPORT.equalsIgnoreCase(reportName)) {
					totalRecords = (Integer) sessionMap.get(BUYER_SO_TOTAL_REC);
					setPaginationToDTO(reportTabDTO, totalRecords,
							pagingCriteria);
					sessionMap.put(BUYER_SO_DTO, reportTabDTO);
					listOfBuyerVO = financeManagerDelegate
							.getBuyerPaymentByServiceOrder(reportTabDTO);
					setReportContentVOBuyerSOReport(listOfBuyerVO, reportFooter);
				} else if (BUYER_TAXID_REPORT.equalsIgnoreCase(reportName)) {
					totalRecords = (Integer) sessionMap
							.get(BUYER_TAXID_TOTAL_REC);
					setPaginationToDTO(reportTabDTO, totalRecords,
							pagingCriteria);
					sessionMap.put(BUYER_TAXID_DTO, reportTabDTO);
					listOfBuyerVO = (List<BuyerSOReportVO>) sessionMap
							.get("BUYER_TAXPAYER_VO");
					List<BuyerSOReportVO> nextListOfBuyerVO = getNextListOfBuyerVOs(
							listOfBuyerVO, reportTabDTO);
					setReportContentVOBuyerTaxPayerID(nextListOfBuyerVO,
							reportFooter);
				}
			}
			if (reportContentVO.getPaginationVO().isNextIndicator()) {
				reportContentVO.getFooterList().clear();
			}else{
				reportContentVO.setInfoMessage(getMessage("fm_reports_payment_footer_info"));
			}
			reportContentVO.setPageTitle(reportTabDTO.getFileName());
		} catch (DelegateException e) {
			LOGGER.debug("Error while genrating reports", e);
		}
		return GENERATE_REPORT;
	}

	/**
	 * Returns a set of report VO to diplay. It is used to display report on
	 * clicking next/previous/Go To in pagination
	 * 
	 * @param listOfBuyerVO
	 *            : Entire list of report VOs fetched from DB and saved in
	 *            session.
	 * @param reportTabDTO
	 *            : report criteria.
	 * @return : A list of BuyerSOReportVO. List size varies from 0 to max of
	 *         100 for each page.
	 * */
	private List<BuyerSOReportVO> getNextListOfBuyerVOs(
			List<BuyerSOReportVO> listOfBuyerVO, FMReportTabDTO reportTabDTO) {
		List<BuyerSOReportVO> listForDisplay = new ArrayList<BuyerSOReportVO>();
		int startInd = reportTabDTO.getStartIndex();
		int totalRecords = reportTabDTO.getTotalRecords();
		int endInd = startInd + reportTabDTO.getNumberOfRecords();
		endInd = endInd > totalRecords ? totalRecords : endInd;
		for (int loop = startInd; loop < endInd; ++loop) {
			listForDisplay.add(listOfBuyerVO.get(loop));
		}
		return listForDisplay;
	}

	private PaginationVO createPagination(int totalRecords,
			PagingCriteria pagingCriteria) {
		ABaseCriteriaHandler criteriaHandler = new ABaseCriteriaHandler();
		PaginationVO pagination = criteriaHandler._getPaginationDetail(
				totalRecords, pagingCriteria.getPageSize(),
				pagingCriteria.getStartIndex(), pagingCriteria.getEndIndex());
		// Set page size to 100 (fixed)
		pagination.setPageSize(REPORT_PAGE_SIZE);
		return pagination;
	}

	/**
	 * validateInputs : Called on clicking the Display/Export button in
	 * Reports/Fulfillment page for admin. This method validates from date, to
	 * date and list of IDs provided by user. Error list is populated and is
	 * returned. If validation is success then save the criteria in db.
	 * */
	public String validateInputs() throws Exception {
		FMReportTabDTO reportTabDTO = getModel();
		getSession().removeAttribute("successMsg");
		String formInd = getRequest().getParameter("formInd");
		getSession().setAttribute("formType", formInd);
		// Provider/Buyer/NewCo
		String role = get_commonCriteria().getRoleType();
		// 1 or 2 or 3
		Integer roleId = get_commonCriteria().getRoleId();
		reportTabDTO.setId(roleId);
		Integer resourceId = get_commonCriteria().getVendBuyerResId();
		SecurityContext context= get_commonCriteria().getSecurityContext();
		//Set SLadmin resourceId as Submitted by when ALAdmin adopts Buyer/Provider.
		if(context.isAdopted() )
		{
			resourceId =context.getAdminResId();
		} 
		reportTabDTO.setResourceId(resourceId);
		Integer slId = get_commonCriteria().getSecurityContext().getCompanyId();
		reportTabDTO.setRoleId(slId);
		reportErrors.clear();
		//added to resolve  SL-19368
		reportTabDTO.getReportErrors().clear();
		
		if (reportTabDTO.isReportByCalendarYear()) {
			// Set start date and end date
			StringBuilder fromDate = new StringBuilder(
					reportTabDTO.getReportYear());
			StringBuilder toDate = new StringBuilder(
					reportTabDTO.getReportYear());
			// Set Jan 1st as start date
			fromDate.append("-01-01 00:00:00");
			reportTabDTO.setDtFromDate(stringToDate(fromDate.toString(),
					REPORT_DATE_TIME_STAMP_FORMAT));
			int thisYear = Calendar.getInstance().get(Calendar.YEAR);
			if (Integer.parseInt(reportTabDTO.getReportYear()) == thisYear) {
				// If report year is current year, set current time stamp as end
				// date.
				reportTabDTO.setDtToDate(new Date());
			} else {
				// Set Dec 31st as end date.
				toDate.append("-12-31 23:59:59");
				reportTabDTO.setDtToDate(stringToDate(toDate.toString(),
						REPORT_DATE_TIME_STAMP_FORMAT));
			}
		}
		if (reportTabDTO.isReportByDateRange()) {
			reportTabDTO.setDtFromDate(stringToDate(reportTabDTO.getFromDate(),
					REPORT_DATE_FORMAT));
			Calendar currDate = Calendar.getInstance();
			StringBuilder toDate = new StringBuilder(reportTabDTO.getToDate());	
			String strToDate =stringToDate(toDate.toString(),REPORT_DATE_FORMAT).toString().substring(0, 11);			
			String strCurr = currDate.getTime().toString().substring(0, 11);
			if(strCurr.equalsIgnoreCase(strToDate)){
				reportTabDTO.setDtToDate(currDate.getTime());
			}else{
				toDate.append(" 23:59:59");
				reportTabDTO.setDtToDate(stringToDate(toDate.toString(),REPORT_DATE_TIME_STAMP_FORMAT));	
			}		
			validateDates(reportTabDTO.getDtFromDate(),
					reportTabDTO.getDtToDate());			
		}
		try {
			if (PROVIDER_RPT.equalsIgnoreCase(role)) {
				if (reportTabDTO.isReportForSpecificBuyers()) {
					validateIDs(reportTabDTO.getBuyers(), BUYER_RPT);
					reportTabDTO
							.setBuyerList(convertCommaSepStrToList(reportTabDTO
									.getBuyers()));
				}
			} else if (BUYER_RPT.equalsIgnoreCase(role)) {
				/* For Buyer */
				if (reportTabDTO.isReportForSpecificProviders()) {
					validateIDs(reportTabDTO.getProviders(), PROVIDER_RPT);
					reportTabDTO
							.setProviderList(convertCommaSepStrToList(reportTabDTO
									.getProviders()));
				}
			} else if (NEWCO_ADMIN.equalsIgnoreCase(role)) {
				/* For SLAdmin */
				if (!("".equals(reportTabDTO.getProviders().trim()))) {
					validateIDs(reportTabDTO.getProviders(), PROVIDER_RPT);
					reportTabDTO
							.setProviderList(convertCommaSepStrToList(reportTabDTO
									.getProviders()));
				}
				if (!("".equals(reportTabDTO.getBuyers().trim()))) {
					validateIDs(reportTabDTO.getBuyers(), BUYER_RPT);
					reportTabDTO
							.setBuyerList(convertCommaSepStrToList(reportTabDTO
									.getBuyers()));
				}
			}
		} catch (DelegateException e) {
			LOGGER.error("Error while vaidating", e);
			throw new Exception(e);
		}
		// Set error messages
		if (null != reportErrors && reportErrors.size() == 0) {
			financeManagerDelegate.saveInputsForReportsScheduler(reportTabDTO);
			String successMsg = getMessage("fm_reports_success_msg");
			getSession().setAttribute("successMsg", successMsg);
		}
		return "json";
	}

	/**
	 * @param reportName
	 *            : Name or the type of the report.
	 * @param reportTabDTO
	 *            : report criteria submitted by user for report generation.
	 * **/
	private void setDtoInSession(String reportName, FMReportTabDTO reportTabDTO) {
		Map<String, Object> sessionMap = ActionContext.getContext()
				.getSession();
		Double randomRequest = Math.random();
		// To uniquely identify each request
		getRequest().setAttribute("requestRandom", randomRequest);
		StringBuilder strBuilder = new StringBuilder(reportName)
				.append(randomRequest);
		sessionMap.put(strBuilder.toString(), reportTabDTO);
	}

	/**
	 * 
	 * **/
	private void setReportContentVOProvSOReport(
			List<ProviderSOReportVO> listOfProviderVO, String reportFooter) {
		if (reportContentVO == null) {
			reportContentVO = new ReportContentVO();
		}
		NumberFormat formatter = new DecimalFormat(PRICE_FORMAT);
		String role = get_commonCriteria().getRoleType();
		List<String> headerList = reportContentVO.getHeaderList();
		List<String> footerList = reportContentVO.getFooterList();
		String header = "";
		if (NEWCO_ADMIN.equalsIgnoreCase(role)) {
			footerList.add("");
			footerList.add("");
			header = getMessage("fm_reports_provider_so_report_header_admin");
		} else {
			header = getMessage("fm_reports_provider_so_report_header");
		}
		List<String> headers = convertCommaSepStrToList(header);
		headerList.addAll(headers);
		List<ArrayList<String>> bodyMap = reportContentVO.getCommonBody();
		reportContentVO.setHeaderList(headerList);
		for (ProviderSOReportVO providerSOReportVO : listOfProviderVO) {
			ArrayList<String> bodyRowList = new ArrayList<String>();
			if (NEWCO_ADMIN.equalsIgnoreCase(role)) {
				if (null != providerSOReportVO.getProviderFirmId()) {
					bodyRowList.add(providerSOReportVO.getProviderFirmId()
							.toString());
				}
				bodyRowList.add(providerSOReportVO.getProviderFirmName());
			}
			bodyRowList.add(providerSOReportVO.getBuyerId());
			bodyRowList.add(providerSOReportVO.getBuyerName());
			bodyRowList.add((formatDate(providerSOReportVO.getCompletedDate(),
					"MM/dd/yyyy")));
			bodyRowList.add(providerSOReportVO.getSoId());
			bodyRowList.add(formatDate(providerSOReportVO.getDatePaid(),
					"MM/dd/yyyy"));
			bodyRowList.add("$"
					+ formatter.format(providerSOReportVO.getGrossLabor()));
			bodyRowList.add("$"
					+ formatter.format(providerSOReportVO.getGrossOther()));
			bodyRowList
					.add("$"
							+ formatter.format(providerSOReportVO
									.getTotalFinalPrice()));
			bodyRowList.add("$"
					+ formatter.format(providerSOReportVO.getServiceLiveFee()));
			bodyRowList.add("$"
					+ formatter.format(providerSOReportVO.getNetPayment()));
			bodyMap.add(bodyRowList);
		}

		List<String> totals = convertCommaSepStrToList(reportFooter);
		for (int loop = 0; loop < totals.size(); ++loop) {
			footerList.add("$"
					+ formatter.format(Double.parseDouble(totals.get(loop))));
		}
	}

	/**
	 * validateIDs : validates buyer ids and provider ids and error message is
	 * populated on error.
	 * 
	 * @param userIds
	 *            : Comma separated string of user ids to be validated.
	 * @param idType
	 *            : BuyerId/Provider Id *
	 * */
	private void validateIDs(String userIds, String idType)
			throws DelegateException {
		List<String> invalidIds = null;
		String idTypeMsg = null;
		StringBuilder wrongId= new StringBuilder("");
		if (BUYER_RPT.equalsIgnoreCase(idType)) {			
			idTypeMsg = "fm_reports.invalidBuyerIdLen";
		} else if (PROVIDER_RPT.equalsIgnoreCase(idType)) {
			idTypeMsg = "fm_reports.invalidProviderIdLens";			
		}
		List<String> listIds = convertCommaSepStrToList(userIds);
		List<String> listUserIds =  new ArrayList<String>();
		/*To remove duplicate ids from list*/		 
		for(String ids : listIds){
			if(listUserIds.contains(ids)){
				continue;
			}else{
				listUserIds.add(ids);
				//Check for max length of buyer ids
				if(ids.length()>10){
					if("".equals(wrongId.toString())){
						wrongId.append(ids);
					}else{
						wrongId.append(", ").append(ids);
					}
				}
			}
		}	
		if(!"".equals(wrongId.toString())){
			StringBuilder invalidLenMsg =  new StringBuilder(getMessage(idTypeMsg));
			invalidLenMsg.append(wrongId).append("}");
			SOWError error = new SOWError(null, invalidLenMsg.toString(), "ID");
			reportErrors.add(error);
		}
		try{
			if (BUYER_RPT.equalsIgnoreCase(idType)) {
				idTypeMsg = "fm_reports.invalidBuyerIds";			
				invalidIds = financeManagerDelegate.validateBuyerIDs(listUserIds);			
			} else if (PROVIDER_RPT.equalsIgnoreCase(idType)) {
				idTypeMsg = "fm_reports.invalidProviderIds";
				invalidIds = financeManagerDelegate
						.validateProviderIDs(listUserIds);
			}
		}catch (Exception e) {
			LOGGER.error(e);
		}
		
		StringBuilder invalidId = new StringBuilder("");
		if (invalidIds != null && invalidIds.size() != 0) {
			for(String id:invalidIds){
				if("".equals(invalidId.toString())){
					invalidId.append(id);
				}else{
					invalidId.append(", ").append(id);
				}
			}
			StringBuilder invalidIdMsg = new StringBuilder(
					getMessage(idTypeMsg));
			invalidIdMsg.append(invalidId).append("}");
			SOWError error = new SOWError(null, invalidIdMsg.toString(), "ID");
			reportErrors.add(error);
		}
	}

	/**
	 * This method validates from-date and to-date and adds error messages to
	 * reportsErrors Any date should be greater than 2001 jan 01. The maximum
	 * difference between two date is set to 13 months.
	 */
	private boolean validateDates(Date argFromDate, Date argToDate) {
		Calendar fromDate = Calendar.getInstance();
		Calendar toDate = Calendar.getInstance();
		fromDate.setTime(argFromDate);
		toDate.setTime(argToDate);
		DateFormat formatter;
		Date date;
		Calendar startDate = Calendar.getInstance();
		formatter = new SimpleDateFormat(REPORT_DATE_FORMAT);
		try {
			date = (Date) formatter.parse(REPORT_START_DATE);
			startDate.setTime(date);
		} catch (ParseException e) {
			LOGGER.error(this.getClass() + "-" + e.getMessage());
		}
		Calendar toDay = Calendar.getInstance();
		SOWError error = null;
		if (fromDate.after(toDay) || toDate.after(toDay)) {
			error = new SOWError(null,
					getMessage("fm_reports.futureDateError"), "DT");
			reportErrors.add(error);
		} else if (fromDate.before(startDate) || toDate.before(startDate)) {
			error = new SOWError(null, getMessage("fm_reports.startDateError"),
					"DT");
			reportErrors.add(error);
		} else if (fromDate.after(toDate)) {
			error = new SOWError(null, getMessage("fm_reports.dateError"),
					"DT"); 
			reportErrors.add(error);
		}else if(!validateDateRange(fromDate, toDate)){
			error = new SOWError(null, getMessage("fm_reports.dateRangeError"),
					"DT");
			reportErrors.add(error);
		}			
		return true;
	}

	/**
	 * @return true if the difference between to dates are less than or equal to thirteen months.
	 * else returns false.
	 * */
	private boolean validateDateRange(Calendar fromDate, Calendar toDate) {
		Calendar lastDate = Calendar.getInstance();
		Calendar compareDate = (Calendar) toDate.clone();
		lastDate.setTime(fromDate.getTime());
		lastDate.add(Calendar.YEAR, 1);
		lastDate.add(Calendar.MONTH, 1);
		lastDate.add(Calendar.DATE, -1);
		compareDate.clear(Calendar.MINUTE);
		compareDate.clear(Calendar.SECOND);
		compareDate.set(Calendar.HOUR_OF_DAY, 0);
		if(compareDate.after(lastDate)){
			return false;
		}
		return true;
	}

	/**
	 * Retrieves error/ success message from property file.
	 * 
	 * @param key
	 *            : key for the message
	 * @return error /success message
	 * **/
	private String getMessage(String key) {
		ServletContext servletContext = ServletActionContext
				.getServletContext();
		String msg = "";
		LocalizationContext localizationContext = (LocalizationContext) servletContext
				.getAttribute("serviceliveCopyBundle");
		try {
			msg = localizationContext.getResourceBundle().getString(key);
		} catch (Exception e) {
			LOGGER.error(e.getMessage() + " " + this.getClass() + " - "
					+ "getMessage()");
			msg = "Failed to load warnings from resource bundle.";
		}
		return msg;
	}
	
	public FMReportTabDTO getModel() {

		return this.fmReportTabDTO;
	}

	public List<SOWError> getReportErrors() {
		return reportErrors;
	}

	public void setReportErrors(List<SOWError> reportErrors) {
		this.reportErrors = reportErrors;
	}

	public ReportContentVO getReportContentVO() {
		return reportContentVO;
	}

	public void setReportContentVO(ReportContentVO reportContentVO) {
		this.reportContentVO = reportContentVO;
	}

	/**
	 * Fetches the status and report criteria to display in front end.
	 * 
	 * This method is duplicated in AdminToolAction
	 * */
	private void getReportStatus() throws Exception {
		Integer slId = get_commonCriteria().getSecurityContext().getCompanyId();
		//SecurityContext context= get_commonCriteria().getSecurityContext();		
		List<ExportStatusVO> exportStatus = new ArrayList<ExportStatusVO>();
		try {
			exportStatus = financeManagerDelegate.getReportStatus(slId);
		} catch (DelegateException e) {
			LOGGER.debug("Error getting debug status", e);
			throw new Exception(e);
		}
		
		//Set SLadmin resourceId as Submitted by when ALAdmin adopts Buyer/Provider.
	/*	List<ExportStatusVO> exportStatus = new ArrayList<ExportStatusVO>();
		if(!context.isAdopted())
		{
			//If user is not adopted then display only the reports submitted by same company ids.
			for(ExportStatusVO exportStatusVO: listStatus){
				if(!exportStatusVO.isAdopted()){
					exportStatus.add(exportStatusVO);
				}
			}			
		}else{
			//If user is adopted then display all reports by same company id (Including re[ports by sladmin adopted report)
			exportStatus.addAll(listStatus);
		} */
		List<ExportStatusVO> listStatusProvSO = new ArrayList<ExportStatusVO>();
		List<ExportStatusVO> listStatusProvRev = new ArrayList<ExportStatusVO>();
		List<ExportStatusVO> listStatusByrSO = new ArrayList<ExportStatusVO>();
		List<ExportStatusVO> listStatusByrTax = new ArrayList<ExportStatusVO>();
		List<ExportStatusVO> listStatusAdmin = new ArrayList<ExportStatusVO>();
		Map theMap = ActionContext.getContext().getSession();
		for (ExportStatusVO exportStatusVO : exportStatus) {
			String reportType = exportStatusVO.getReportType();				
			if (PROVIDER_SO_REPORT.equalsIgnoreCase(reportType)) {
				listStatusProvSO.add(exportStatusVO);
			} else if (PROVIDER_REV_REPORT.equalsIgnoreCase(reportType)) {
				listStatusProvRev.add(exportStatusVO);
			} else if (BUYER_SO_REPORT.equalsIgnoreCase(reportType)) {
				listStatusByrSO.add(exportStatusVO);
			} else if (BUYER_TAXID_REPORT.equalsIgnoreCase(reportType)) {
				listStatusByrTax.add(exportStatusVO);
			} else if (ADMIN_PAYMENT_REPORT.equalsIgnoreCase(reportType)) {
				listStatusAdmin.add(exportStatusVO);
			}
		}
		// Setting the status message for each report.
		String msg = "All activity from ";
		StringBuilder statusMsg = new StringBuilder(msg);
		statusMsg.append(getMinDateFromList(listStatusProvSO)).append(" to ")
				.append(getMaxDateFromList(listStatusProvSO));
		theMap.put(REPORT_MSG_EXPORT_PROV_SO, statusMsg.toString());
		statusMsg = new StringBuilder(msg);
		statusMsg.append(getMinDateFromList(listStatusProvRev)).append(" to ")
				.append(getMaxDateFromList(listStatusProvRev));
		theMap.put(REPORT_MSG_EXPORT_PROV_REV, statusMsg.toString());
		statusMsg = new StringBuilder(msg);
		statusMsg.append(getMinDateFromList(listStatusByrSO)).append(" to ")
				.append(getMaxDateFromList(listStatusByrSO));
		theMap.put(REPORT_MSG_EXPORT_BUYER_SO, statusMsg.toString());
		statusMsg = new StringBuilder(msg);
		statusMsg.append(getMinDateFromList(listStatusByrTax)).append(" to ")
				.append(getMaxDateFromList(listStatusByrTax));
		theMap.put(REPORT_MSG_EXPORT_BUYER_ID, statusMsg.toString());
		statusMsg = new StringBuilder(msg);
		statusMsg.append(getMinDateFromList(listStatusAdmin)).append(" to ")
				.append(getMaxDateFromList(listStatusAdmin));
		theMap.put(REPORT_MSG_EXPORT_ADMIN_PAYMENT, statusMsg.toString());
		// Put statusVOs in session
		theMap.put(REPORT_EXPORT_PROV_SO, listStatusProvSO);
		theMap.put(REPORT_EXPORT_PROV_REV, listStatusProvRev);
		theMap.put(REPORT_EXPORT_BUYER_SO, listStatusByrSO);
		theMap.put(REPORT_EXPORT_BUYER_ID, listStatusByrTax);
		theMap.put(REPORT_EXPORT_ADMIN_PAYMENT, listStatusAdmin);
	}

	/**
	 * @return max report date from the list of statusVO
	 * */
	private Date getMaxDateFromList(List<ExportStatusVO> listStatus) {
		if (null == listStatus || listStatus.size() == 0) {
			return DateUtils.getCurrentDate();
		}
		ExportStatusVO status = Collections.max(listStatus,
				new Comparator<ExportStatusVO>() {
					public int compare(ExportStatusVO recFir,
							ExportStatusVO recSec) {
						return recFir.getReportDate().compareTo(
								recSec.getReportDate());
					}
				});
		if (null == status) {
			return DateUtils.getCurrentDate();
		} else {
			return status.getReportDate();
		}
	}

	/**
	 * @return min report date from the list of statusVO
	 * */
	private Date getMinDateFromList(List<ExportStatusVO> listStatus) {
		if (null == listStatus || listStatus.size() == 0) {
			return DateUtils.addDaysToDate(DateUtils.getCurrentDate(), -7);
		}
		ExportStatusVO status = Collections.min(listStatus,
				new Comparator<ExportStatusVO>() {
					public int compare(ExportStatusVO recFir,
							ExportStatusVO recSec) {
						return recFir.getReportDate().compareTo(
								recSec.getReportDate());
					}
				});
		if (null == status) {
			return DateUtils.addDaysToDate(DateUtils.getCurrentDate(), -7);
		} else {
			return status.getReportDate();
		}
	}

	/**
	 * This method returns the date in the given format
	 * 
	 * @param date
	 *            to be formated
	 * @param format
	 * @return foramted date as string
	 */
	private String formatDate(Date date, String format) {
		String formattedDate = null;
		if (date != null) {
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat(format);
				formattedDate = dateFormat.format(date);
			} catch (IllegalArgumentException e) {
				LOGGER.error("Illegal Format Exception" + e.getCause());
			}
			return formattedDate;
		} else {
			return "NA";
		}
	}

	/**
	 * @return : converted date from string
	 * */
	private Date stringToDate(String strDate, String format) {
		DateFormat formatter;
		Date date = null;
		formatter = new SimpleDateFormat(format);
		try {
			date = (Date) formatter.parse(strDate);
		} catch (ParseException e) {
			LOGGER.error(this.getClass() + "-" + e.getMessage());
		}
		return date;
	}

	private List<String> convertCommaSepStrToList(String inputStr) {
		String stringWithCommas = inputStr.trim();
		if (stringWithCommas != null) {
			List<String> listBuyerIds = new ArrayList<String>(
					Arrays.asList(stringWithCommas.split(",")));
			return listBuyerIds;
		} else {
			return null;
		}
	}

	private PagingCriteria handleReportPagingCriteria() {
		PagingCriteria pagingCriteria = getPagingCriteria();

		if (pagingCriteria != null) {
			if (getRequest().getParameter("startIndex") != null
					&& !getRequest().getParameter("startIndex").equals("")) {
				pagingCriteria.setStartIndex(Integer.valueOf(getRequest()
						.getParameter("startIndex"), RADIX_DECIMAL));
			}
			if (getRequest().getParameter("endIndex") != null
					&& !getRequest().getParameter("endIndex").equals("")) {
				pagingCriteria.setEndIndex(Integer.valueOf(getRequest()
						.getParameter("endIndex"), RADIX_DECIMAL));
			}
			if (getRequest().getParameter("pageSize") != null
					&& !getRequest().getParameter("pageSize").equals("")) {
				pagingCriteria.setPageSize(Integer.valueOf(getRequest()
						.getParameter("pageSize"), RADIX_DECIMAL));
			}
		} else {
			return new PagingCriteria();
		}
		return pagingCriteria;
	}

	private void setReportContentVOBuyerSOReport(
			List<BuyerSOReportVO> listOfBuyerVO, String reportFooter) {
		if (reportContentVO == null) {
			reportContentVO = new ReportContentVO();
		}
		List<String> headerList = reportContentVO.getHeaderList();
		List<ArrayList<String>> bodyMap = reportContentVO.getCommonBody();
		List<String> footerList = reportContentVO.getFooterList();
		NumberFormat formatter = new DecimalFormat(PRICE_FORMAT);
		String role = get_commonCriteria().getRoleType();
		String header = "";
		if (NEWCO_ADMIN.equalsIgnoreCase(role)) {
			footerList.add("");
			footerList.add("");
			header = getMessage("fm_reports_buyer_so_report_header_admin");
		} else {
			header = getMessage("fm_reports_buyer_so_report_header");
		}
		List<String> headers = convertCommaSepStrToList(header);
		headerList.addAll(headers);
		reportContentVO.setHeaderList(headerList);
		for (BuyerSOReportVO buyerSOReportVO : listOfBuyerVO) {
			String tinType = null;
			if (buyerSOReportVO.getTaxPayerTypeId() == 2) {
				tinType = "SSN";
			} else if (buyerSOReportVO.getTaxPayerTypeId() == 1) {
				tinType = "EIN";
			} else {
				tinType = "";
			}
			ArrayList<String> bodyRowList = new ArrayList<String>();
			if (NEWCO_ADMIN.equalsIgnoreCase(role)) {
				bodyRowList.add(buyerSOReportVO.getBuyerId());
				bodyRowList.add(buyerSOReportVO.getBuyerName());
			}
			bodyRowList
					.add(String.valueOf(buyerSOReportVO.getProviderFirmId()));
			bodyRowList.add(buyerSOReportVO.getProviderFirmName());
			bodyRowList.add(buyerSOReportVO.getTaxPayerType());
			bodyRowList.add(buyerSOReportVO.getExempt());
			bodyRowList.add(tinType);
			String taxPayerId =  getDecryptedValue(buyerSOReportVO.getEncrypedTaxPayerId(), buyerSOReportVO.getTaxPayerTypeId());
			bodyRowList.add(formatTaxPayerId(taxPayerId, tinType));
			bodyRowList.add(formatDate(buyerSOReportVO.getPaymentDate(),
					"MM/dd/yyyy"));
			bodyRowList.add(buyerSOReportVO.getServiceOrderId());
			if (buyerSOReportVO.getTotalGrossPayment() != null) {
				bodyRowList.add("$"
						+ formatter.format(buyerSOReportVO
								.getTotalGrossPayment()));
			} else {
				bodyRowList.add(ZERO_DOLLAR);
			}
			/*if (buyerSOReportVO.getBuyerPostingFee() != null) {
				bodyRowList.add("$"
								+ formatter.format(buyerSOReportVO
										.getBuyerPostingFee()));
			} else {
				bodyRowList.add(ZERO_DOLLAR);
			}*/
			// new buyerPosting fee methods added
			if (buyerSOReportVO.getPostingFee() != null) {
				bodyRowList.add("$"
								+ formatter.format(buyerSOReportVO
										.getPostingFee()));
			} else {
				bodyRowList.add(ZERO_DOLLAR);
			}
			
			
			if (buyerSOReportVO.getProviderServiceLiveFee() != null) {
				bodyRowList.add("$"
						+ formatter.format(buyerSOReportVO
								.getProviderServiceLiveFee()));
			} else {
				bodyRowList.add(ZERO_DOLLAR);
			}
			bodyMap.add(bodyRowList);
		}

		List<String> totals = convertCommaSepStrToList(reportFooter);
		for (int loop = 0; loop < totals.size(); ++loop) {
			footerList.add("$"
					+ formatter.format(Double.parseDouble(totals.get(loop))));
		}
	}
	
	private String getDecryptedValue(String encryptedEin, int taxPayerIdType) {
		CryptographyVO cryptographyVO = new CryptographyVO();
		cryptographyVO.setInput(encryptedEin);
		cryptographyVO.setKAlias(MPConstants.ENCRYPTION_KEY);
		cryptographyVO = cryptography.decryptKey(cryptographyVO);
		String einNo = cryptographyVO.getResponse();
		if (null == einNo || einNo.equalsIgnoreCase("")) {
			einNo = "";
		}
		/*
		 * else if (!einNo.startsWith("XXX-XX-") &&
		 * taxPayerIdType==ProviderConstants.COMPANY_SSN_IND) { einNo =
		 * ProviderConstants.COMPANY_SSN_ID_MASK+einNo.substring(5); } else
		 * if(taxPayerIdType==0) { einNo = "XXXXX"+einNo.substring(5); } else {
		 * einNo = ProviderConstants.COMPANY_EIN_ID_MASK+einNo.substring(5); }
		 */
		return einNo;
	}

	private PagingCriteria getPagingCriteria() {
		Map theMap = ActionContext.getContext().getSession();
		if (theMap.containsKey(FM_REPORTS_PAGING_CRITERIA_KEY)) {
			return (PagingCriteria) theMap.get(FM_REPORTS_PAGING_CRITERIA_KEY);
		}
		return null;
	}

	/**
	 * Sets pagination criteria to DTO. Save total record count and Pagination
	 * in request scope so that it will be available for next page navigation
	 * */
	private void setPaginationToDTO(FMReportTabDTO reportTabDTO,
			int totalRecords, PagingCriteria pagingCriteria) {
		if (reportContentVO == null) {
			reportContentVO = new ReportContentVO();
		}
		setAttribute("totalRecords", totalRecords);
		PaginationVO reportPagination = createPagination(totalRecords,
				pagingCriteria);
		reportTabDTO.setStartIndex(totalRecords > 0 ? reportPagination
				.getStartIndex() - 1 : reportPagination.getStartIndex());
		reportTabDTO.setNumberOfRecords(reportPagination.getPageSize());
		reportContentVO.setTotalPage(reportPagination.getTotalPaginets());
		reportContentVO.setPaginationVO(reportPagination);
		setAttribute("reportPagination", reportPagination);
	}

	public PaginationVO getPaginationVO() {
		return paginationVO;
	}

	/**
	 * Checks whether display of report is needed or not. If total record count
	 * is zero or greater than a max limit (say 10,000) we don't display the
	 * report.
	 * */
	private boolean isDisplayNeeded(int totalRecords) {
		if (UPPER_LIMIT_FOR_DISPLAY_REPORTS.intValue() < totalRecords) {
			reportErrors.clear();
			SOWError error = new SOWError(null,
					getMessage("fmreports_record_limit_exceeded"), "LIMIT");
			reportErrors.add(error);
			setAttribute("reportErrors", reportErrors);
			return false;
		} else if (0 == totalRecords) {
			reportErrors.clear();
			SOWError error = new SOWError(null, getMessage("no_records_found"),
					"LIMIT");
			reportErrors.add(error);
			setAttribute("reportErrors", reportErrors);
			return false;
		} else {
			return true;
		}
	}

	public void setPaginationVO(PaginationVO paginationVO) {
		this.paginationVO = paginationVO;
	}

	public Cryptography getCryptography() {
		return cryptography;
	}

	public void setCryptography(Cryptography cryptography) {
		this.cryptography = cryptography;
	}

	private void setReportContentVOBuyerTaxPayerID(
			List<BuyerSOReportVO> listOfBuyerVO, String reportFooter) {
		if (reportContentVO == null) {
			reportContentVO = new ReportContentVO();
		}
		List<String> headerList = reportContentVO.getHeaderList();
		List<ArrayList<String>> bodyMap = reportContentVO.getCommonBody();
		NumberFormat formatter = new DecimalFormat(PRICE_FORMAT);
		String role = get_commonCriteria().getRoleType();
		String header = "";
		List<String> footerList = reportContentVO.getFooterList();
		if (NEWCO_ADMIN.equalsIgnoreCase(role)) {
			footerList.add("");
			footerList.add("");
			header = getMessage("fm_reports_buyer_revenue_report_header_admin");
		} else {
			header = getMessage("fm_reports_buyer_revenue_report_header");
		}
		List<String> headers = convertCommaSepStrToList(header);
		headerList.addAll(headers);
		reportContentVO.setHeaderList(headerList);
		for (BuyerSOReportVO buyerSOReportVO : listOfBuyerVO) {
			ArrayList<String> bodyRowList = new ArrayList<String>();
			if (NEWCO_ADMIN.equalsIgnoreCase(role)) {
				bodyRowList.add(buyerSOReportVO.getBuyerId());
				bodyRowList.add(buyerSOReportVO.getBuyerName());
			}
			bodyRowList
					.add(String.valueOf(buyerSOReportVO.getProviderFirmId()));
			bodyRowList.add(buyerSOReportVO.getProviderFirmName());
			bodyRowList.add(getNotNullValue(buyerSOReportVO.getDbaName()));
			bodyRowList.add(buyerSOReportVO.getTaxPayerType());
			bodyRowList.add(buyerSOReportVO.getExempt());
			bodyRowList.add(buyerSOReportVO.getTinType());
			String taxPayerId = buyerSOReportVO.getDecrypedTaxPayerId();
			//If SSN/EIN is not formatted
			if(!StringUtils.isEmpty(taxPayerId)&& !taxPayerId.contains("-") ){
				taxPayerId = formatTaxPayerId(taxPayerId, buyerSOReportVO.getTinType());
			}
			bodyRowList.add(taxPayerId);
			//SL-20958 Changes- START
			bodyRowList.add(getNotNullValue(buyerSOReportVO.getStreet1()));
			bodyRowList.add(getNotNullValue(buyerSOReportVO.getStreet2()));
			bodyRowList.add(getNotNullValue(buyerSOReportVO.getCity()));
			bodyRowList.add(getNotNullValue(buyerSOReportVO.getState()));
			bodyRowList.add(getNotNullValue(buyerSOReportVO.getZip()));
			//SL-20958 Changes- END
			bodyRowList.add("$"
					+ formatter.format(buyerSOReportVO.getTotalGrossPayment()));
			bodyMap.add(bodyRowList);
		}
		List<String> totals = convertCommaSepStrToList(reportFooter);
		for (int loop = 0; loop < totals.size(); ++loop) {
			footerList.add("$"
					+ formatter.format(Double.parseDouble(totals.get(loop))));
		}
	}

	private String getNotNullValue(String dbaName) {
		if (dbaName == null || "".equalsIgnoreCase(dbaName.trim())) {
			return NOT_APPLICABLE_SIGN;
		}
		return dbaName;
	}
	
	/**
	 * @param taxPayerId : SSN or EIN number which is to be formatted.
	 * @param tinType : SSN/EIN or any other value when nothing is provided.
	 * 
	 * @return formatted taxpayer id . for  123-45-6789
	 * for EIN 12-3456789 
	 * This method is duplicated in PaymentReportExportProcessor
	 * **/
	private String formatTaxPayerId(String taxPayerId, String tinType) {
		StringBuilder formatedId = new StringBuilder("-");
		if(!StringUtils.isEmpty(tinType) && !StringUtils.isEmpty(taxPayerId)){
			formatedId = new StringBuilder(taxPayerId);
			if("SSN".equalsIgnoreCase(tinType)){
				formatedId.insert(3, '-');
				formatedId.insert(6, '-');
			}else if("EIN".equalsIgnoreCase(tinType)){
				formatedId.insert(2, '-');
			}
		}else{
			return StringUtils.isEmpty(taxPayerId)?"-":taxPayerId;
		}		
		return formatedId.toString();
	}
}
