package com.newco.marketplace.web.action.financemanager;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.servlet.ServletOutputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.business.businessImpl.financeManager.WalletControlEnum;
import com.newco.marketplace.business.iBusiness.buyer.IBuyerPersIdentificationBO;
import com.newco.marketplace.business.iBusiness.lookup.ILookupBO;
import com.newco.marketplace.business.iBusiness.provider.IW9RegistrationBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.CryptographyVO;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.ledger.CreditCardVO;
import com.newco.marketplace.dto.vo.serviceorder.Buyer;
import com.newco.marketplace.dto.vo.wallet.WalletControlDocumentVO;
import com.newco.marketplace.dto.vo.wallet.WalletControlVO;
import com.newco.marketplace.interfaces.MPConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.util.Cryptography;
import com.newco.marketplace.util.PropertiesUtils;
import com.newco.marketplace.utils.DateUtils;
import com.newco.marketplace.web.action.base.SLFinanceManagerBaseAction;
import com.newco.marketplace.web.delegates.ICreateServiceOrderDelegate;
import com.newco.marketplace.web.delegates.ILookupDelegate;
import com.newco.marketplace.web.delegates.ISLAdminWalletControlDeligate;
import com.newco.marketplace.web.dto.AccountDTO;
import com.newco.marketplace.web.dto.FMFinancialProfileTabDTO;
import com.newco.marketplace.web.dto.FMManageAccountsTabDTO;
import com.newco.marketplace.web.dto.FMManageFundsTabDTO;
import com.newco.marketplace.web.dto.IError;
import com.newco.marketplace.web.dto.IWarning;
import com.newco.marketplace.web.dto.SLDocumentDTO;
import com.newco.marketplace.web.dto.SOWError;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

import flexjson.JSONSerializer;

public class FMManageFundsAction extends SLFinanceManagerBaseAction implements
		Preparable, ModelDriven<FMManageFundsTabDTO> {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger
			.getLogger(FMManageFundsAction.class.getName());

	private FMManageFundsTabDTO manageFundsTabDTO = new FMManageFundsTabDTO();
	private ICreateServiceOrderDelegate createServiceOrderDelegate;
	private IW9RegistrationBO w9RegistrationBO;
	private ILookupBO lookUpBO;
	private IBuyerPersIdentificationBO buyerPIIBo;
	private Cryptography cryptography;
	private List<String> countryList = new ArrayList<String>();
	private Pattern pattern;
	private Matcher matcher;

	private ISLAdminWalletControlDeligate sLAdminWalletControlDeligate;
	public static final String S_JSON_RESULT = "jsonResult";
	public static final String HAS_ERROR = "hasError";
	private Map<String, Object> model = new HashMap<String, Object>();
	private static final String WALLET_CAT_ID = "docCategoryId";
	private static final String PNG = ".png";
	private static final String JPG = ".jpg";
	private static final String GIF = ".gif";
	private static final String DOC = ".doc";
	private static final String DOCX = ".docx";
	private static final String PDF = ".pdf";

	public void prepare() throws Exception {
		createCommonServiceOrderCriteria();
		this.initFinancialProflie();
		getAccountDetails();
		getCountryList();
		getBuyerBitFlag();
		getEinMap();
		manageFundsTabDTO.setFmReleaseDate(getFMReleaseDate());
		this.setLegalHoldPermissionAttribute();
		this.getEntityWalletControlId();
		this.getEntityWalletControlBanner();
		getSession().setAttribute("errors", null);
	
	}

	public String execute() throws Exception {
		boolean legalHoldPermission = (Boolean)getSession().getAttribute("legalHoldPermission");
		String role = get_commonCriteria().getRoleType();
		FMManageFundsTabDTO dto = null;
		if (getRequest().getSession().getAttribute("manageFundsTabDTO") != null) {
			// dto = (FMManageFundsTabDTO)
			// getRequest().getSession().getAttribute("manageFundsTabDTO");
			dto = new FMManageFundsTabDTO();
			// Setting the value of Auto Funding Indicator for
			// Processing Time content display in jsp
			dto.setAutoACHInd(new Boolean(get_commonCriteria()
					.getSecurityContext().isAutoACH()).toString());
			dto.setFmReleaseDate(getFMReleaseDate());

		} else {
			// Setting the value of Auto Funding Indicator for text
			// Processing Time content display in jsp
			manageFundsTabDTO.setAutoACHInd(new Boolean(get_commonCriteria()
					.getSecurityContext().isAutoACH()).toString());
			manageFundsTabDTO.setFmReleaseDate(getFMReleaseDate());
			dto = manageFundsTabDTO;
		}
		checkForEnabledAccount(dto, get_commonCriteria().getCompanyId());
		dto.setWarnings(new ArrayList<IWarning>());
		// FinancialProfile validation is not required for an SLAdmin
		if (!role.equals(OrderConstants.NEWCO_ADMIN)) {
			dto = validatedFundsDto(dto);
		}
		if(legalHoldPermission){
		fetchWalletControlInformation();
		}
		getRequest().getSession().setAttribute("manageFundsTabDTO", dto);
		return role;
	}

	/**
	 * Description: Deposit of funds entry point.
	 * 
	 * @return
	 * @throws Exception
	 */
	public String depositFunds() throws Exception {
		ProcessResponse pr = new ProcessResponse();
		FMManageFundsTabDTO dto = getModel();
		if (dto.getSsnSaveInd() != null) {
			if (dto.getSsnSaveInd() == true) {
				getSession().setAttribute("ssnSaveInd", "true");
			} else {
				getSession().setAttribute("ssnSaveInd", "false");
			}
		}
		List<IError> errorList = new ArrayList<IError>();
		manageFundsTabDTO.setErrors(errorList);
		String cvv = null;
		String arr[] = null;
		arr = validateVerificationNumAndDepositAmount(dto, errorList, arr);

		FMFinancialProfileTabDTO financialProfileTabDto = getFinancialProfileTabDTO();
		if (null != financialProfileTabDto) {
			financialProfileTabDto.validate();
			dto.getErrors().addAll(financialProfileTabDto.getErrors());
			dto.getWarnings().addAll(financialProfileTabDto.getWarnings());
		}
		errorList = dto.getErrors();
		if (dto.getErrors().isEmpty() && null != financialProfileTabDto
				&& financialProfileTabDto.getErrors().isEmpty()
				&& financialProfileTabDto.getWarnings().isEmpty()) {
			if (arr[0].equals("-1")) {
				SOWError err = new SOWError("",
						"Please select an account for deposit.",
						OrderConstants.FM_ERROR);
				errorList = new ArrayList<IError>();
				errorList.add(err);
				dto.setErrors(errorList);
			} else {
				double buyerTotalDeposit = (Double) getSession().getAttribute(
						"buyerTotalDeposit");
				double depositAmt = Double.parseDouble(dto.getDepositAmount());

				// check for the buyerBitFlag and Buyer threshold limit
				boolean buyerBitFlag = (Boolean) getSession().getAttribute(
						"buyerBitFlag");
				double buyerThresholdLimit = (Double) getSession()
						.getAttribute("buyerThreshold");

				// input params for saving taxId information
				Integer buyerId = get_commonCriteria().getCompanyId();

				String documentType = dto.getDocumentType();
				String documentNo = dto.getDocumentIdNo();
				String country = dto.getCountry();

				String einNoOrig = "";
				String einNo = "";
				String ssnNoOrig = "";
				String ssnNo = "";
				if (dto.getEinTaxPayerId() != null
						&& !dto.getEinTaxPayerId().equals("")) {
					einNoOrig = dto.getEinTaxPayerId();
					einNo = einNoOrig.replaceAll("-", "");
				}
				if (dto.getSsnTaxPayerId() != null
						&& !dto.getSsnTaxPayerId().equals("")) {
					ssnNoOrig = dto.getSsnTaxPayerId();
					ssnNo = ssnNoOrig.replaceAll("-", "");
				}

				if (buyerBitFlag) {
					if ((dto.getSsnTaxPayerId() != null
							|| dto.getEinTaxPayerId() != null || documentNo != null)
							&& (buyerTotalDeposit + depositAmt) > buyerThresholdLimit) {
						if (getSession().getAttribute("einStoredFlag") != null
								&& !(Boolean) getSession().getAttribute(
										"einStoredFlag")) {
							if (dto.getEinSsnInd() != null
									&& dto.getEinSsnInd().equals("1")) {
								createServiceOrderDelegate.saveTaxPayerId(
										buyerId, einNo);
							} else if (dto.getEinSsnInd() != null
									&& dto.getEinSsnInd().equals("2")) {

								// if saving ssn data without editing the ssn id
								// information, reset the actual ssn id number
								// instead of masked values.
								if (null != dto.getSsnSaveInd()
										&& dto.getSsnSaveInd() == false) {
									Buyer buyerVO = buyerPIIBo
											.getBuyerPIIData(buyerId);
									if (buyerVO != null) {
										// decrypt and mask the taxpayer Id
										CryptographyVO cryptographyVO = new CryptographyVO();
										cryptographyVO
												.setKAlias(MPConstants.ENCRYPTION_KEY);
										if (buyerVO.getEinNoEnc() != null) {
											cryptographyVO.setInput(buyerVO
													.getEinNoEnc());
										}
										cryptographyVO = cryptography
												.decryptKey(cryptographyVO);
										if (buyerVO.getPiiIndex() == 2) {
											if (cryptographyVO != null
													&& cryptographyVO
															.getResponse() != null) {
												documentNo = cryptographyVO
														.getResponse();
											}
										} else if (buyerVO.getPiiIndex() == 1) {
											if (cryptographyVO != null
													&& cryptographyVO
															.getResponse() != null) {
												ssnNo = cryptographyVO
														.getResponse();
											}
										} else if (buyerVO.getPiiIndex() == 0) {
											if (cryptographyVO != null
													&& cryptographyVO
															.getResponse() != null) {
												einNo = cryptographyVO
														.getResponse();
											}
										}
									}
								}
								if (dto.getEinSsnInd() != null
										&& dto.getEinSsnInd().equals("1")) {
									createServiceOrderDelegate.saveTaxPayerId(
											buyerId, einNo);
								} else if (dto.getEinSsnInd() != null
										&& dto.getEinSsnInd().equals("2")) {
									createServiceOrderDelegate.saveSSNId(
											buyerId, ssnNo, dto.getSsnDob());
								} else if (dto.getEinSsnInd() != null
										&& dto.getEinSsnInd().equals("3")) {
									createServiceOrderDelegate.saveAltTaxId(
											buyerId, documentType, documentNo,
											country, dto.getAltIdDob());
								}

							} else if (dto.getEinSsnInd() != null
									&& dto.getEinSsnInd().equals("3")) {
								createServiceOrderDelegate.saveAltTaxId(
										buyerId, documentType, documentNo,
										country, dto.getAltIdDob());
							}
						}
					}
				}

				if (get_commonCriteria().getTheUserName() != null
						&& !(get_commonCriteria().getSecurityContext()
								.getSLAdminInd())) {
					pr = fmPersistDelegate.depositAmount(dto,
							get_commonCriteria().getCompanyId(),
							get_commonCriteria().getRoleType(),
							get_commonCriteria().getTheUserName());
				} else {
					if (get_commonCriteria().getSecurityContext() != null
							&& get_commonCriteria().getSecurityContext()
									.getSLAdminInd()) {
						// setting the userName
						if (null != get_commonCriteria()
								&& null != get_commonCriteria()
										.getTheUserName()) {
							dto.setUserName(get_commonCriteria()
									.getTheUserName());
						}
						pr = fmPersistDelegate.depositAmount(dto,
								get_commonCriteria().getCompanyId(),
								get_commonCriteria().getRoleType(),
								get_commonCriteria().getSecurityContext()
										.getSlAdminUName());
					} else {
						pr = fmPersistDelegate.depositAmount(dto,
								get_commonCriteria().getCompanyId(),
								get_commonCriteria().getRoleType(), null);
					}
				}

				getEinMap();
				setDepositErrorMessages(pr, dto, errorList);
			}
		}
		setSessionVariables(dto);

		return GOTO_COMMON_FINANCE_MGR_CONTROLLER;

	}

	/**
	 * Description: Sets session variables to return after a CC Deposit.
	 * 
	 * @param dto
	 */
	private void setSessionVariables(FMManageFundsTabDTO dto) {
		if (!dto.getErrors().isEmpty())
			getSession().setAttribute("errors", dto.getErrors());
		getSession().setAttribute("manageFundsTabDTO", dto);
		if (dto.getErrors().isEmpty()) {
			getSession().setAttribute("manageFundsTabDTO",
					new FMManageFundsTabDTO());
		}
		HashMap<String, String> map = new HashMap<String, String>();
		map = (HashMap<String, String>) getRequest().getSession().getAttribute(
				"hasMapForTabIcons");
		map = setTabIcon(map, OrderConstants.FM_MANAGE_FUNDS_TAB,
				dto.getErrors(), dto.getWarnings());
		getSession().setAttribute("hasMapForTabIcons", map);
		setDefaultTab(OrderConstants.FM_MANAGE_FUNDS);
	}

	/**
	 * Description: Sets error messages, if any, after a CC deposit.
	 * 
	 * @param pr
	 * @param dto
	 * @param errorList
	 */
	private void setDepositErrorMessages(ProcessResponse pr,
			FMManageFundsTabDTO dto, List<IError> errorList) {
		for (int i = 0; i < pr.getMessages().size(); i++) {
			pr.getMessages().get(i);
			SOWError err = new SOWError("", pr.getMessages().get(i),
					OrderConstants.FM_ERROR);
			errorList.add(err);
			logger.error("Error in depositing buyer funds:" + err);
		}

		if (pr.getMessages().size() == 0 && dto.getErrors().size() == 0) {
			if (get_commonCriteria().getSecurityContext().isAutoACH()
					|| (StringUtils.isNotBlank(dto.getCvvCode()))) {
				getSession().setAttribute("depositSuccessMsg",
						"Deposited funds $" + dto.getDepositAmount() + ".");
			} else {
				getSession().setAttribute(
						"depositSuccessMsg",
						"Deposit funds $" + dto.getDepositAmount()
								+ " will be processed.");
			}
			getSession().setAttribute("depositStatus", "success");
		} else {
			SOWError err = new SOWError(
					"",
					"This transaction was not approved; please verify the card information you provided and try again or contact your credit card company",
					OrderConstants.FM_ERROR);
			errorList = new ArrayList<IError>();
			errorList.add(err);
			dto.setErrors(errorList);
		}
	}

	/**
	 * Description: Validation whether CC is authorized
	 * 
	 * @param dto
	 * @param errorList
	 * @param creditCardVO
	 */
	private void validateAuthorization(FMManageFundsTabDTO dto,
			List<IError> errorList, CreditCardVO creditCardVO) {
		if (!creditCardVO.isAuthorized()) // if not authorized....
		{
			if (StringUtils.isNotBlank(creditCardVO.getAnsiResponseCode())) {
				SOWError err = new SOWError("",
						creditCardVO.getAnsiResponseCode(),
						OrderConstants.FM_ERROR);
				errorList.add(err);
			}
			if (StringUtils.isNotBlank(creditCardVO.getCidResponseCode())) {
				SOWError err = new SOWError("",
						creditCardVO.getCidResponseCode(),
						OrderConstants.FM_ERROR);
				errorList.add(err);
			}
			dto.setErrors(errorList);
		}
	}

	/**
	 * Description: Set CC User Name
	 * 
	 * @param creditCardVO
	 */
	private void setCCUserName(CreditCardVO creditCardVO) {
		if (get_commonCriteria().getTheUserName() != null) {
			creditCardVO.setUserName(get_commonCriteria().getTheUserName());
		} else { // if the user name is null then it is SLAdmin
			if (get_commonCriteria().getSecurityContext() != null) {
				creditCardVO.setUserName(get_commonCriteria()
						.getSecurityContext().getSlAdminUName());
			}
		}
	}

	/**
	 * Description: Validation of CC verification Number and Credit Card Amount
	 * 
	 * @param dto
	 * @param errorList
	 * @param arr
	 * @return
	 */
	private String[] validateVerificationNumAndDepositAmount(
			FMManageFundsTabDTO dto, List<IError> errorList, String[] arr) {
		double depositAmt = 0.0;
		boolean depositAmtErrorFlag = false;
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date today = Date.valueOf(dateFormat.format(calendar.getTime()));
		if (dto != null) {
			arr = dto.getAccountList().split(",");
			if (arr != null && arr.length > 1) {
				dto.setAccountId(Long.parseLong(arr[0]));
				if (arr[1].equals("30")) {
					if ((dto.getCvvCode() == null || dto.getCvvCode()
							.equals(""))) {
						SOWError err = new SOWError("",
								"Invalid card verification number",
								OrderConstants.FM_ERROR);
						logger.error("Error in depositing buyer funds:" + err);
						SOWError errGeneric = new SOWError(
								"",
								"This transaction was not approved; please verify the card information you provided and try again or contact your credit card company...",
								OrderConstants.FM_ERROR);

						errorList.add(errGeneric);
						dto.setErrors(errorList);
					}
					getSession().setAttribute("isCVVCode", "true");
				} else {
					getSession().removeAttribute("isCVVCode");
				}
			}

			try {
				depositAmt = Double.parseDouble(dto.getDepositAmount());
				if (depositAmt <= 0) {
					SOWError err = new SOWError("",
							"Please enter a valid deposit amount",
							OrderConstants.FM_ERROR);
					errorList.add(err);
					dto.setErrors(errorList);
					depositAmtErrorFlag = true;

				}
			} catch (Exception e) {
				SOWError err = new SOWError("",
						"Please enter a valid deposit amount",
						OrderConstants.FM_ERROR);
				errorList.add(err);
				dto.setErrors(errorList);
				depositAmtErrorFlag = true;

			}

			// jrod starts
			logger.info("buyerBitFlag: "
					+ getSession().getAttribute("buyerBitFlag"));
			logger.info("einStoredFlag: "
					+ getSession().getAttribute("einStoredFlag"));
			logger.info("isEin: " + getSession().getAttribute("isEin"));
			logger.info("isSSN: " + getSession().getAttribute("isSSN"));
			logger.info("isAltId: " + getSession().getAttribute("isAltId"));

			String einTaxpayerId = "";
			String einConfirmTaxpayerId = "";
			String ssnTaxpayerId = "";
			String ssnConfirmTaxpayerId = "";
			String altTaxpayerId = "";

			if (getSession().getAttribute("buyerBitFlag") != null
					&& (Boolean) getSession().getAttribute("buyerBitFlag")
					&& !depositAmtErrorFlag
					&& getSession().getAttribute("einStoredFlag") != null
					&& !(Boolean) getSession().getAttribute("einStoredFlag")) {
				double buyerTotalDeposit = (Double) getSession().getAttribute(
						"buyerTotalDeposit");
				double buyerThresholdLimit = (Double) getSession()
						.getAttribute("buyerThreshold");

				if (buyerTotalDeposit + depositAmt > buyerThresholdLimit) {
					if (dto.getEinSsnInd() != null
							&& dto.getEinSsnInd().equals("1")) {

						if (!StringUtils.isBlank(dto.getEinTaxPayerId())) {
							einTaxpayerId = dto.getEinTaxPayerId().replaceAll(
									"-", "");
						}
						if (!StringUtils.isBlank(dto.getConfirmEinTaxPayerId())) {
							einConfirmTaxpayerId = dto
									.getConfirmEinTaxPayerId().replaceAll("-",
											"");
						}

						// Check if nothing is there
						if (StringUtils.isBlank(dto.getEinTaxPayerId())
								&& StringUtils.isBlank(dto
										.getConfirmEinTaxPayerId())) {
							SOWError err = new SOWError(
									"",
									"Please enter your Personal Identification Information.",
									OrderConstants.FM_ERROR);
							errorList.add(err);
						} else {
							// Check for size
							if ((!StringUtils.isBlank(dto.getEinTaxPayerId()) && einTaxpayerId
									.trim().length() < 9)
									|| (!StringUtils.isBlank(dto
											.getConfirmEinTaxPayerId()) && einConfirmTaxpayerId
											.trim().length() < 9)) {
								SOWError err = new SOWError(
										"",
										"Taxpayer ID (EIN) should be 9 digits.",
										OrderConstants.FM_ERROR);
								errorList.add(err);
							}

							// Check for numeric
							if (!StringUtils.isBlank(dto.getEinTaxPayerId())
									&& !StringUtils.isNumeric(einTaxpayerId)) {
								SOWError err = new SOWError(
										"",
										"Taxpayer ID (EIN) can only be numeric.",
										OrderConstants.FM_ERROR);
								errorList.add(err);

							} else if (!StringUtils.isBlank(dto
									.getEinTaxPayerId())
									&& StringUtils.isNumeric(einTaxpayerId)
									&& einTaxpayerId.trim().length() == 9) {
								String tinRestrPattern = PropertiesUtils
										.getPropertyValue(Constants.AppPropConstants.PII_EINSSN_RESTRICTED_PATTERN);
								if (tinRestrPattern != null
										&& !"".equals(tinRestrPattern)
										&& tinRestrPattern
												.contains(einTaxpayerId)) {
									SOWError err = new SOWError(
											"",
											dto.getEinTaxPayerId()
													+ " is not a valid Taxpayer ID. Please enter a valid Taxpayer ID (EIN)",
											OrderConstants.FM_ERROR);
									errorList.add(err);
								}
							}
							if (!StringUtils.isBlank(dto
									.getConfirmEinTaxPayerId())
									&& !StringUtils
											.isNumeric(einConfirmTaxpayerId)) {
								SOWError err = new SOWError(
										"",
										"Taxpayer ID (EIN) can only be numeric.",
										OrderConstants.FM_ERROR);
								errorList.add(err);

							}

							// Check for match
							if ((StringUtils.isBlank(dto.getEinTaxPayerId()) && !StringUtils
									.isBlank(dto.getConfirmEinTaxPayerId()))
									|| (!StringUtils.isBlank(dto
											.getEinTaxPayerId()) && StringUtils
											.isBlank(dto
													.getConfirmEinTaxPayerId()))
									|| (!StringUtils.isBlank(dto
											.getEinTaxPayerId())
											&& !StringUtils.isBlank(dto
													.getConfirmEinTaxPayerId()) && !einTaxpayerId
												.equals(einConfirmTaxpayerId))) {
								SOWError err = new SOWError(
										"",
										"The Taxpayer IDs (EIN) entered do not match.",
										OrderConstants.FM_ERROR);
								errorList.add(err);

							}
						}
						dto.setSsnTaxPayerId((String) getSession()
								.getAttribute("ssnNo"));
						dto.setConfirmSsnTaxPayerId((String) getSession()
								.getAttribute("ssnNo"));
						getSession().setAttribute("taxidEIN", "checked");
						getSession().setAttribute("taxidSSN", "");
						getSession().setAttribute("taxidAlt", "");
					} else if (dto.getEinSsnInd() != null
							&& dto.getEinSsnInd().equals("2")) {
						boolean didYouCheckDate = false;
						if (!StringUtils.isBlank(dto.getSsnTaxPayerId())) {
							ssnTaxpayerId = dto.getSsnTaxPayerId().replaceAll(
									"-", "");
						}
						if (!StringUtils.isBlank(dto.getConfirmSsnTaxPayerId())) {
							ssnConfirmTaxpayerId = dto
									.getConfirmSsnTaxPayerId().replaceAll("-",
											"");
						}
						if (dto.getSsnSaveInd() == true) {

							// No data is present
							if (StringUtils.isBlank(dto.getSsnTaxPayerId())
									&& StringUtils.isBlank(dto
											.getConfirmSsnTaxPayerId())
									&& StringUtils.isBlank(dto.getSsnDob())) {
								SOWError err = new SOWError(
										"",
										"Please enter your Personal Identification Information.",
										OrderConstants.FM_ERROR);
								errorList.add(err);
								didYouCheckDate = true;
							} else {
								// check for no tax id info but dob - in case of
								// modification of existing data.
								if (StringUtils.isBlank(dto.getSsnTaxPayerId())
										&& StringUtils.isBlank(dto
												.getConfirmSsnTaxPayerId())) {
									SOWError err = new SOWError(
											"",
											"Please enter your Personal Identification Information.",
											OrderConstants.FM_ERROR);
									errorList.add(err);

								}
								// Check for size
								if ((!StringUtils.isBlank(dto
										.getSsnTaxPayerId()) && ssnTaxpayerId
										.trim().length() < 9)
										|| (!StringUtils.isBlank(dto
												.getConfirmSsnTaxPayerId()) && ssnConfirmTaxpayerId
												.trim().length() < 9)) {
									SOWError err = new SOWError(
											"",
											"Taxpayer ID (SSN) should be 9 digits.",
											OrderConstants.FM_ERROR);
									errorList.add(err);
								}
								// Check for numeric
								if (!StringUtils
										.isBlank(dto.getSsnTaxPayerId())
										&& !StringUtils
												.isNumeric(ssnTaxpayerId)) {
									SOWError err = new SOWError(
											"",
											"Taxpayer ID (SSN) can only be numeric.",
											OrderConstants.FM_ERROR);
									errorList.add(err);
								} else if (!StringUtils.isBlank(dto
										.getSsnTaxPayerId())
										&& StringUtils.isNumeric(ssnTaxpayerId)
										&& ssnTaxpayerId.trim().length() == 9) {
									String tinRestrPattern = PropertiesUtils
											.getPropertyValue(Constants.AppPropConstants.PII_EINSSN_RESTRICTED_PATTERN);
									if (tinRestrPattern != null
											&& !"".equals(tinRestrPattern)
											&& tinRestrPattern
													.contains(ssnTaxpayerId)) {
										SOWError err = new SOWError(
												"",
												dto.getSsnTaxPayerId()
														+ " is not a valid Taxpayer ID. Please enter a valid Taxpayer ID (SSN)",
												OrderConstants.FM_ERROR);
										errorList.add(err);
									}
								}
								if (!StringUtils.isBlank(dto
										.getConfirmSsnTaxPayerId())
										&& !StringUtils
												.isNumeric(ssnConfirmTaxpayerId)) {
									SOWError err = new SOWError(
											"",
											"Taxpayer ID (SSN) can only be numeric.",
											OrderConstants.FM_ERROR);
									errorList.add(err);
								}
								// Check for match
								if ((StringUtils
										.isBlank(dto.getSsnTaxPayerId()) && !StringUtils
										.isBlank(dto.getConfirmSsnTaxPayerId()))
										|| (!StringUtils.isBlank(dto
												.getSsnTaxPayerId()) && StringUtils
												.isBlank(dto
														.getConfirmSsnTaxPayerId()))
										|| (!StringUtils.isBlank(dto
												.getSsnTaxPayerId())
												&& !StringUtils
														.isBlank(dto
																.getConfirmSsnTaxPayerId()) && !ssnTaxpayerId
													.equals(ssnConfirmTaxpayerId))) {
									SOWError err = new SOWError(
											"",
											"The Taxpayer IDs (SSN) entered do not match.",
											OrderConstants.FM_ERROR);
									errorList.add(err);
								}
							}
						}

						if (StringUtils.isBlank(dto.getSsnDob())
								&& !didYouCheckDate) {
							SOWError err = new SOWError(
									"",
									"Please enter a valid Date of Birth in MM/DD/YYYY format.",
									OrderConstants.FM_ERROR);
							errorList.add(err);

						} else if (!StringUtils.isBlank(dto.getSsnDob())) {
							String ssnDob = dto.getSsnDob();
							String dobPattern = "^(0[1-9]|1[012])[/](0[1-9]|[12][0-9]|3[01])[/](19|20)\\d\\d$";
							pattern = Pattern.compile(dobPattern);
							matcher = pattern.matcher(ssnDob);
							if (!matcher.matches()
									|| !(DateUtils.isValidDate(ssnDob,
											"MM/dd/yyyy"))) {
								SOWError err = new SOWError(
										"",
										"Please enter a valid Date of Birth in MM/DD/YYYY format.",
										OrderConstants.FM_ERROR);
								errorList.add(err);

							} else {
								DateFormat newFormat = new SimpleDateFormat(
										"MM/dd/yyyy");
								try {
									if (ssnDob != null
											&& newFormat.parse(ssnDob).after(
													today)) {
										SOWError err = new SOWError(
												"",
												"Please enter a valid Date of Birth in MM/DD/YYYY format.",
												OrderConstants.FM_ERROR);
										errorList.add(err);
									}
								} catch (Exception e) {
									logger.error("Parse Error" + e);
									SOWError err = new SOWError(
											"",
											"Please enter a valid Date of Birth in MM/DD/YYYY format.",
											OrderConstants.FM_ERROR);
									errorList.add(err);
								}
							}
						}

						if (dto.getSsnTaxPayerId() == null) {
							dto.setSsnTaxPayerId((String) getSession()
									.getAttribute("ssnNo"));
							dto.setConfirmSsnTaxPayerId((String) getSession()
									.getAttribute("ssnNo"));
						}

						getSession().setAttribute("taxidSSN", "checked");
						getSession().setAttribute("taxidEIN", "");
						getSession().setAttribute("taxidAlt", "");
					} else if (dto.getEinSsnInd() != null
							&& dto.getEinSsnInd().equals("3")) {
						// Check for nothing
						if (StringUtils.isBlank(dto.getDocumentType())
								&& StringUtils.isBlank(dto.getDocumentIdNo())
								&& dto.getCountry().equals("-1")
								&& StringUtils.isBlank(dto.getAltIdDob())) {
							SOWError err = new SOWError(
									"",
									"Please enter your Personal Identification Information.",
									OrderConstants.FM_ERROR);
							errorList.add(err);
						} else {
							if (StringUtils.isBlank(dto.getDocumentType())
									|| dto.getDocumentType().trim().length() > 35) {
								SOWError err = new SOWError("",
										"Please enter valid a Document Type.",
										OrderConstants.FM_ERROR);
								errorList.add(err);
							}
							if (StringUtils.isBlank(dto.getDocumentIdNo())) {
								SOWError err = new SOWError(
										"",
										"Please enter a valid Document Identification Number.",
										OrderConstants.FM_ERROR);
								errorList.add(err);
							} else if (dto.getDocumentIdNo().trim().length() > 15) {
								SOWError err = new SOWError(
										"",
										"Please enter a valid Document Identification Number.",
										OrderConstants.FM_ERROR);
								errorList.add(err);
							}
							if (dto.getCountry().equals("-1")) {
								SOWError err = new SOWError("",
										"Please select a Country of Issuance.",
										OrderConstants.FM_ERROR);
								errorList.add(err);
							}
							if (StringUtils.isBlank(dto.getAltIdDob())) {
								SOWError err = new SOWError(
										"",
										"Please enter a valid Date of Birth in MM/DD/YYYY format.",
										OrderConstants.FM_ERROR);
								errorList.add(err);
							} else {
								String altDob = dto.getAltIdDob();
								String dobPattern = "^(0[1-9]|1[012])[/](0[1-9]|[12][0-9]|3[01])[/](19|20)\\d\\d$";
								pattern = Pattern.compile(dobPattern);
								matcher = pattern.matcher(altDob);
								if (!matcher.matches()
										|| !(DateUtils.isValidDate(altDob,
												"MM/dd/yyyy"))) {
									SOWError err = new SOWError(
											"",
											"Please enter a valid Date of Birth in MM/DD/YYYY format.",
											OrderConstants.FM_ERROR);
									errorList.add(err);
								} else {
									DateFormat newFormat = new SimpleDateFormat(
											"MM/dd/yyyy");
									try {
										if (altDob != null
												&& newFormat.parse(altDob)
														.after(today)) {
											SOWError err = new SOWError(
													"",
													"Please enter a valid Date of Birth in MM/DD/YYYY format.",
													OrderConstants.FM_ERROR);
											errorList.add(err);
										}
									} catch (Exception e) {
										logger.error("Parse Error" + e);
										SOWError err = new SOWError(
												"",
												"Please enter a valid Date of Birth in MM/DD/YYYY format.",
												OrderConstants.FM_ERROR);
										errorList.add(err);
									}
								}
							}
						}
						dto.setSsnTaxPayerId((String) getSession()
								.getAttribute("ssnNo"));
						dto.setConfirmSsnTaxPayerId((String) getSession()
								.getAttribute("ssnNo"));
						getSession().setAttribute("taxidAlt", "checked");
						getSession().setAttribute("taxidEIN", "");
						getSession().setAttribute("taxidSSN", "");

					}
					getSession().setAttribute("isTaxPayer", "true");
				}
			} else {
				getSession().removeAttribute("isTaxPayer");
			}
		}
		return arr;

	}

	private FMManageFundsTabDTO validatedFundsDto(FMManageFundsTabDTO dto) {

		FMFinancialProfileTabDTO financialProfileTabDto = getFinancialProfileTabDTO();
		// clear all present financial profile errors

		if (null != financialProfileTabDto) {
			financialProfileTabDto.validate();
			dto.getErrors().addAll(financialProfileTabDto.getErrors());
			dto.getWarnings().addAll(financialProfileTabDto.getWarnings());
		}
		return dto;
	}

	@SuppressWarnings("unchecked")
	public String withdrawFunds() throws Exception {
		ProcessResponse pr = new ProcessResponse();
		FMManageFundsTabDTO dto = getModel();
		dto.validate();
		dto = validatedFundsDto(dto);
		FMFinancialProfileTabDTO financialProfileTabDto = getFinancialProfileTabDTO();
		List<IError> errorList = new ArrayList<IError>();
		errorList = dto.getErrors();

		if (financialProfileTabDto.getProviderMaxWithdrawalLimit() != null) {
			dto.setProviderMaxWithdrawalLimit(financialProfileTabDto
					.getProviderMaxWithdrawalLimit());
			dto.setProviderMaxWithdrawalNo(financialProfileTabDto
					.getProviderMaxWithdrawalNo());
		} else if (getRequest().getSession().getAttribute(
				"providerMaxWithdrawalLimit") != null) {
			dto.setProviderMaxWithdrawalLimit((Double) getRequest()
					.getSession().getAttribute("providerMaxWithdrawalLimit"));
			dto.setProviderMaxWithdrawalNo((Integer) getRequest().getSession()
					.getAttribute("providerMaxWithdrawalNo"));
		}
		validateWithdraw(dto);
		String nonceStr = fmPersistDelegate
				.getLedgerEntryProviderWithdrawNonce();

		if (!dto.getNonce().equalsIgnoreCase(nonceStr)) {
			if (dto.getErrors().isEmpty() && null != financialProfileTabDto
					&& financialProfileTabDto.getErrors().isEmpty()
					&& financialProfileTabDto.getWarnings().isEmpty()
					&& financialProfileTabDto.getWarnings().isEmpty()) {
				if (get_commonCriteria().getTheUserName() != null
						&& !(get_commonCriteria().getSecurityContext()
								.getSLAdminInd())) {
					pr = fmPersistDelegate.withdrawAmount(dto,
							get_commonCriteria().getCompanyId(),
							get_commonCriteria().getRoleType(),
							get_commonCriteria().getTheUserName());
				} else {
					if (get_commonCriteria().getSecurityContext() != null
							&& get_commonCriteria().getSecurityContext()
									.getSLAdminInd()) {
						pr = fmPersistDelegate.withdrawAmount(dto,
								get_commonCriteria().getCompanyId(),
								get_commonCriteria().getRoleType(),
								get_commonCriteria().getSecurityContext()
										.getSlAdminUName());
					} else {
						pr = fmPersistDelegate.withdrawAmount(dto,
								get_commonCriteria().getCompanyId(),
								get_commonCriteria().getRoleType(), null);
					}
				}
				for (int i = 0; i < pr.getMessages().size(); i++) {
					pr.getMessages().get(i);
					SOWError err = new SOWError("", pr.getMessages().get(i),
							OrderConstants.FM_ERROR);
					errorList.add(err);
				}

				dto.setErrors(errorList);
				if (pr.getMessages().size() == 0) {
					getRequest().setAttribute(
							"WithdrawSuccessMsg",
							"Withdraw funds $" + dto.getWithdrawAmount()
									+ " will be processed.");
					getSession().setAttribute("WithdrawStatus", "success");
					dto.setWithdrawAmount("0.00");
					dto.setAccountId(-1);
				}
			}

		} else {
			logger.error("Same nonce exists, duplicate provider withdrawal request happened");
		}
		getRequest().getSession().setAttribute("ManageFundsTabDTO", dto);
		HashMap<String, String> map = new HashMap<String, String>();
		map = (HashMap<String, String>) getRequest().getSession().getAttribute(
				"hasMapForTabIcons");
		map = setTabIcon(map, OrderConstants.FM_MANAGE_FUNDS_TAB,
				dto.getErrors(), dto.getWarnings());
		getSession().setAttribute("hasMapForTabIcons", map);
		setDefaultTab(OrderConstants.FM_MANAGE_FUNDS);
		return GOTO_COMMON_FINANCE_MGR_CONTROLLER;
	}

	public void checkValidAccount() throws Exception {
		List<AccountDTO> accountList = this.getAccountList();
		AccountDTO accountDto = new AccountDTO();
		for (AccountDTO account : accountList) {
			accountDto = account;
		}
		Integer returnAccountCount = financeManagerDelegate
				.returnAccountCount(accountDto.getAccountId());
		if (accountDto.getAccountId() != null && returnAccountCount == 0) {
			getSession().setAttribute("AccountStatus", "invalidAccount");
		} else {
			getSession().removeAttribute("AccountStatus");
		}
	}

	public String getFMReleaseDate() {
		String releaseDate = "";
		if (getSession().getAttribute("ReleaseDate") != null) {
			releaseDate = (String) getSession().getAttribute("ReleaseDate");
		} else {
			releaseDate = PropertiesUtils
					.getFMPropertyValue(Constants.FM_RELEASE_DATE);
			getSession().setAttribute("ReleaseDate", releaseDate);
		}
		return releaseDate;
	}

	
	
	public String maximumWithdrawalLimit() {
		String maxWithdrawalLimit = "";

		// MaxWithdrawlLimit will be null for SLAdmin
		if (get_commonCriteria().getRoleId() != 2) {
			if (getSession().getAttribute("MaxWithdrawlLimit") != null
					&& getSession().getAttribute("MaxWithdrawlLimit") != "") {
				maxWithdrawalLimit = (String) getSession().getAttribute(
						"MaxWithdrawlLimit");
			} else {
				FMFinancialProfileTabDTO financialProfileTabDto = getFinancialProfileTabDTO();
			}
		}
		return maxWithdrawalLimit;
	}

	public void getAccountDetails() throws Exception {
		List<AccountDTO> accountdto = this.getAccountList();
		getSession().setAttribute("accountList", accountdto);
	}

	public void getCountryList() throws Exception {
		countryList = lookUpBO.getCountryList();
		getSession().setAttribute("countryList", countryList);
	}

	public void checkForEnabledAccount(FMManageFundsTabDTO dto, Integer entityId)
			throws Exception {

		FMManageAccountsTabDTO account = financeManagerDelegate
				.getActiveAccountDetails(entityId);

		if (account != null && account.getAccountId() != null
				&& !(account.isEnabledIndicator())) {
			dto.setEnabledIndicator(false);
			dto.checkForEnabledAccount();
		}

	}

	public List<AccountDTO> getAccountList() throws Exception {
		List<AccountDTO> accountList = new ArrayList<AccountDTO>();
		List<AccountDTO> accountListAdmin = new ArrayList<AccountDTO>();
		accountListAdmin = fmPersistDelegate
				.accountDetailsIncludeCC(get_commonCriteria().getCompanyId());

		// Let's filter out inactive accounts if the user is not an admin
		if (!(get_commonCriteria().getSecurityContext().getAdminRoleId()
				.equals(OrderConstants.NEWCO_ADMIN_ROLEID))) {
			for (AccountDTO accountDTO : accountListAdmin) {
				if (accountDTO.isActiveInd()) {
					accountList.add(accountDTO); // list of active accounts
				}
			}
			accountListAdmin = accountList; // make active list only list if not
											// admin
		}
		return accountListAdmin;
	}

	public FMManageFundsTabDTO getModel() {
		if (getRequest().getSession().getAttribute("manageFundsTabDTO") != null) {
			manageFundsTabDTO = (FMManageFundsTabDTO) getRequest().getSession()
					.getAttribute("manageFundsTabDTO");
		}
		return manageFundsTabDTO;
	}

	public FMFinancialProfileTabDTO getFinancialProfileTabDTO() {

		return (FMFinancialProfileTabDTO) getRequest().getSession()
				.getAttribute("FinancialProfileTabDTO");

	}

	public String depositFundsSLAOperations() throws Exception {
		ProcessResponse pr = new ProcessResponse();
		FMManageFundsTabDTO dto = getModel();
		validateDeposit(dto);
		List<IError> errorList = dto.getErrors();
		;
		boolean doNotResetFlag = false;
		if (dto.getErrors().isEmpty()) {

			pr = fmPersistDelegate.depositAmountSLAOperations(dto,
					get_commonCriteria().getCompanyId(), get_commonCriteria()
							.getRoleType(), get_commonCriteria()
							.getTheUserName());
			for (int i = 0; i < pr.getMessages().size(); i++) {
				pr.getMessages().get(i);
				SOWError err = new SOWError("", pr.getMessages().get(i),
						OrderConstants.FM_ERROR);
				errorList.add(err);
			}
			dto.setErrors(errorList);
			if (pr.getMessages().size() == 0) {
				getSession().setAttribute(
						"depositSuccessMsg",
						"deposit funds $" + dto.getDepositAmount()
								+ " will be processed.");
				getSession().setAttribute("depositStatus", "success");
				dto.setDepositAmount("0.00");
				dto.setAccountId(-1);
				doNotResetFlag = true;
			}
		}
		getRequest().getSession().setAttribute("ManageFundsTabDTO", dto);
		if (dto.getErrors().isEmpty() && !doNotResetFlag) {
			getSession().setAttribute("ManageFundsTabDTO",
					new FMManageFundsTabDTO());
		}

		HashMap<String, String> map = new HashMap<String, String>();
		map = (HashMap<String, String>) getRequest().getSession().getAttribute(
				"hasMapForTabIcons");
		map = setTabIcon(map, OrderConstants.FM_MANAGE_FUNDS_TAB,
				dto.getErrors(), dto.getWarnings());
		getSession().setAttribute("hasMapForTabIcons", map);
		setDefaultTab(OrderConstants.FM_MANAGE_FUNDS);
		return GOTO_COMMON_FINANCE_MGR_CONTROLLER;
	}

	public String withdrawFundsSLAOperations() throws Exception {
		ProcessResponse pr = new ProcessResponse();
		FMManageFundsTabDTO dto = getModel();
		validateWithdraw(dto);
		List<IError> errorList = dto.getErrors();

		if (dto.getErrors().isEmpty()) {

			pr = fmPersistDelegate.withdrawAmountSLAOperations(dto,
					get_commonCriteria().getCompanyId(), get_commonCriteria()
							.getRoleType(), get_commonCriteria()
							.getTheUserName());
			for (int i = 0; i < pr.getMessages().size(); i++) {
				pr.getMessages().get(i);
				SOWError err = new SOWError("", pr.getMessages().get(i),
						OrderConstants.FM_ERROR);
				errorList.add(err);
			}

			dto.setErrors(errorList);
			if (pr.getMessages().size() == 0) {
				getRequest().setAttribute(
						"WithdrawSuccessMsg",
						"Withdraw funds $" + dto.getWithdrawAmount()
								+ " will be processed.");
				getSession().setAttribute("WithdrawStatus", "success");
				dto.setWithdrawAmount("0.00");
				dto.setAccountId(-1);
			}
		}
		getRequest().getSession().setAttribute("ManageFundsTabDTO", dto);
		HashMap<String, String> map = new HashMap<String, String>();
		map = (HashMap<String, String>) getRequest().getSession().getAttribute(
				"hasMapForTabIcons");
		getSession().setAttribute("hasMapForTabIcons", map);
		setDefaultTab(OrderConstants.FM_MANAGE_FUNDS);
		return GOTO_COMMON_FINANCE_MGR_CONTROLLER;
	}

	public void validateWithdraw(FMManageFundsTabDTO dto) {
		dto.validate();
		List<IError> errorList = dto.getErrors();

		if (!dto.checkAccount()) {
			SOWError err = new SOWError(dto.getTheResourceBundle().getString(
					"Manage_Funds_Account"), dto.getTheResourceBundle()
					.getString("Manage_Funds_Account_Required"),
					OrderConstants.FM_TAB_ERROR);
			errorList.add(err);
		}

		if (!dto.checkWithdrawAmount()) {
			SOWError err = new SOWError(dto.getTheResourceBundle().getString(
					"Manage_Funds_Amount"), dto.getTheResourceBundle()
					.getString("Manage_FundsValidAmount"),
					OrderConstants.FM_TAB_ERROR);
			errorList.add(err);
		}
	}

	public void validateDeposit(FMManageFundsTabDTO dto) {
		dto.validate();
		List<IError> errorList = dto.getErrors();

		if (!dto.checkAccount()) {
			SOWError err = new SOWError(dto.getTheResourceBundle().getString(
					"Manage_Funds_Deposit_Account"), dto.getTheResourceBundle()
					.getString("Manage_Funds_Account_Required"),
					OrderConstants.FM_TAB_ERROR);
			errorList.add(err);
		}

		if (!dto.checkDepositAmount()) {
			SOWError err = new SOWError(dto.getTheResourceBundle().getString(
					"Manage_Funds_Deposit_Amount"), dto.getTheResourceBundle()
					.getString("Manage_FundsValidAmount"),
					OrderConstants.FM_TAB_ERROR);
			errorList.add(err);
		}
	}

	private void getEinMap() throws Exception {
		Integer companyId = get_commonCriteria().getCompanyId();
		HashMap einMap = fmPersistDelegate.getBuyerTotalDeposit(companyId);
		String role = get_commonCriteria().getRoleType();
		if (einMap != null && !role.equalsIgnoreCase(OrderConstants.PROVIDER)) {
			if (einMap.get("einStoredFlag") != null)
				getSession().setAttribute("einStoredFlag",
						(Boolean) einMap.get("einStoredFlag"));
			if (einMap.get("buyerTotalDeposit") != null)
				getSession().setAttribute("buyerTotalDeposit",
						(Double) einMap.get("buyerTotalDeposit"));

			// set the already stored tax id details in session
			if (einMap.get("isEin") != null)
				getSession().setAttribute("isEin",
						(Boolean) einMap.get("isEin"));
			if (einMap.get("isSSN") != null)
				getSession().setAttribute("isSSN",
						(Boolean) einMap.get("isSSN"));
			if (einMap.get("isAltId") != null)
				getSession().setAttribute("isAltId",
						(Boolean) einMap.get("isAltId"));
			if (einMap.get("ssnNo") != null)
				getSession().setAttribute("ssnNo", einMap.get("ssnNo"));
			if (einMap.get("ssnDob") != null)
				getSession().setAttribute("ssnDob", einMap.get("ssnDob"));
			if (einMap.get("altIdDob") != null)
				getSession().setAttribute("altIdDob", einMap.get("altIdDob"));

		} else if (role.equalsIgnoreCase(OrderConstants.PROVIDER)) {
			getSession().setAttribute("w9isExist", "false");
			getSession().setAttribute("w9isExistWithSSNInd", "false");
			if (w9RegistrationBO.isW9Exists(companyId)) {
				getSession().setAttribute("w9isExist", "true");
				if (w9RegistrationBO.isAvailableWithSSNInd(companyId)) {
					getSession().setAttribute("w9isExistWithSSNInd", "true");

					// Check if the ein number is a valid value or not
					if (w9RegistrationBO.isW9ExistWithValidValue(companyId)) {
						setAttribute("w9isExistWithValidVal", "true");
					} else {
						setAttribute("w9isExistWithValidVal", "false");
					}
				}
			}
		}
	}

	public String deleteDocument() {

		FMManageFundsTabDTO dto = getModel();
		
		try {	
			if (null != getParameter("documentID")) {

				Integer documentId = Integer.parseInt(getParameter("documentID"));
				if (documentId != null && documentId > 0
						&& sLAdminWalletControlDeligate.deleteWalletControlDocument(documentId) > 0) {
					List<SLDocumentDTO> docs = dto.getWalletControldocuments();
					List<SLDocumentDTO> updated=null;
					if(docs!=null){
					for(SLDocumentDTO doc:docs){
						if(doc!=null && !doc.getDocumentId().equals(documentId)){
							if(updated==null)
								updated= new ArrayList<>();
							updated.add(doc);
						}
					}}
					dto.setWalletControldocuments(updated);
					getRequest().getSession().setAttribute("ManageFundsTabDTO", dto);
					return SUCCESS;
				}
			}
		} catch (Exception e) {
			logger.error("Exception thrown delete document - ", e);
		}

		SOWError err = new SOWError(dto.getTheResourceBundle().getString("Wallet_Control_Error"),
				dto.getTheResourceBundle().getString("Wallet_Control_Service_down"), OrderConstants.FM_TAB_ERROR);
		List<IError> errorList = dto.getErrors();
		errorList.add(err);
		getRequest().getSession().setAttribute("ManageFundsTabDTO", dto);
		HashMap<String, String> map = new HashMap<String, String>();
		map = (HashMap<String, String>) getRequest().getSession().getAttribute("hasMapForTabIcons");
		map = setTabIcon(map, OrderConstants.FM_MANAGE_FUNDS_TAB, dto.getErrors(), dto.getWarnings());
		getSession().setAttribute("hasMapForTabIcons", map);
		setDefaultTab(OrderConstants.FM_MANAGE_FUNDS);
		return GOTO_COMMON_FINANCE_MGR_CONTROLLER;
	}
	
	public String downloadDocument() {
		try {	
			if (null != getParameter("documentID")) {

				Integer documentId = Integer.parseInt(getParameter("documentID"));
				if (documentId != null) {

					DocumentVO documentVO = sLAdminWalletControlDeligate.downloadWalletControlDocument(documentId);
					if (documentVO != null) {
						String fileName = documentVO.getFileName();
						String fileExtenstion = "";
						if (null != fileName && !(OrderConstants.EMPTY_STR.equals(fileName.trim()))) {
							Integer fileExtensionIndex = fileName.lastIndexOf(".");

							fileExtenstion = fileName.substring(fileExtensionIndex, fileName.length());
							getResponse().setContentType(fileExtenstion);

							String header = "attachment;filename=\"" + fileName + "\"";
							getResponse().setHeader("Content-Disposition", header);
							InputStream in = new ByteArrayInputStream(documentVO.getBlobBytes());

							ServletOutputStream outs = getResponse().getOutputStream();

							int bit = 256;

							while ((bit) >= 0) {
								bit = in.read();
								outs.write(bit);
							}

							outs.flush();
							outs.close();
							in.close();
							return SUCCESS;
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("Exception thrown downloading document - ", e);
		}
		FMManageFundsTabDTO dto = getModel();
		SOWError err = new SOWError(dto.getTheResourceBundle().getString(
				"Wallet_Control_Error"), dto.getTheResourceBundle()
				.getString("Wallet_Control_Service_down"),
				OrderConstants.FM_TAB_ERROR);
		List<IError> errorList = dto.getErrors();
		errorList.add(err);		
		getRequest().getSession().setAttribute("ManageFundsTabDTO", dto);
		HashMap<String, String> map = new HashMap<String, String>();
		map = (HashMap<String, String>) getRequest().getSession().getAttribute(
				"hasMapForTabIcons");
		map = setTabIcon(map, OrderConstants.FM_MANAGE_FUNDS_TAB,
				dto.getErrors(), dto.getWarnings());
		getSession().setAttribute("hasMapForTabIcons", map);
		setDefaultTab(OrderConstants.FM_MANAGE_FUNDS);
		return GOTO_COMMON_FINANCE_MGR_CONTROLLER;
	}
	
	public String saveWalletControlInformation() {
		WalletControlVO walletControlVO = new WalletControlVO();
		FMManageFundsTabDTO dto = getModel();
		
		if(dto.getEntityWalletControlID()!=null){
			walletControlVO.setId(dto.getEntityWalletControlID());
		}
		dto.validate();
		
		if (dto.getWalletControlType() != null
				&& WalletControlEnum.isValidEnum(dto.getWalletControlType())) {
			String walletControlType = WalletControlEnum.getEnumName(dto.getWalletControlType());
			validateLegalHoldAmount(dto);
			validateDoc(dto);
			Integer walletControlId = null;
		
			if (dto.getErrors().isEmpty()){
				

				walletControlVO.setEntityId(get_commonCriteria().getCompanyId());
				//SLT-2609
				walletControlVO.setModifiedBy(get_commonCriteria().getSecurityContext()
				.getSlAdminUName()); 
				try {
					walletControlId = sLAdminWalletControlDeligate
							.lookUpWalletControl(walletControlType);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				walletControlVO.setWalletControlId(walletControlId);
				walletControlVO.setWalletControlType(walletControlType);
				walletControlVO.setOnHold(dto.getOnHold());
				if(dto.getOnHold()){
				
				Double lockAmount = Double.parseDouble(manageFundsTabDTO
						.getWalletControlAmount());
				walletControlVO.setAmount(lockAmount);
				walletControlVO.setRemainingAmount(lockAmount);
				}
				
				List<DocumentVO> documentVOs = uploadDocument(dto,walletControlVO);
				try {
					WalletControlVO resultVO =sLAdminWalletControlDeligate.saveWalletControlDetail(
							walletControlVO, documentVOs);
					if (resultVO.getId()!=null){
						dto.setEntityWalletControlID(resultVO.getId());
					
					dto.setWalletControlType(String.valueOf(resultVO.getWalletControlId()));
					dto.setWalletControlAmount(resultVO.getAmount().toString());
					dto.setOnHold(resultVO.getOnHold());
					List <SLDocumentDTO> walletControldocuments = new ArrayList<>();
					for(WalletControlDocumentVO doc:resultVO.getWalletControlDocumentVO()){
						SLDocumentDTO docDTO=new SLDocumentDTO();
						docDTO.setDocumentId(doc.getDocumentId());
						docDTO.setName(doc.getDocumentName());
						walletControldocuments.add(docDTO);
					}
					
					dto.setWalletControldocuments(walletControldocuments);
					}
					if (!dto.getOnHold()) {
						dto.setEntityWalletControlID(null);
						dto.setWalletControlAmount(null);
						dto.setWalletControlType(null);
						dto.setOnHold(false);
						dto.setWalletControldocuments(null);
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		
		}else {
			logger.error("Valid Wallet Control type is not exist");
		}
			getRequest().getSession().setAttribute("ManageFundsTabDTO", dto);
			HashMap<String, String> map = new HashMap<String, String>();
			map = (HashMap<String, String>) getRequest().getSession().getAttribute(
					"hasMapForTabIcons");
			map = setTabIcon(map, OrderConstants.FM_MANAGE_FUNDS_TAB,
					dto.getErrors(), dto.getWarnings());
			getSession().setAttribute("hasMapForTabIcons", map);
			setDefaultTab(OrderConstants.FM_MANAGE_FUNDS);
			return GOTO_COMMON_FINANCE_MGR_CONTROLLER;

	}

	private List<DocumentVO> uploadDocument(FMManageFundsTabDTO dto, WalletControlVO walletControlVO) {
		List<DocumentVO> documentVOs = new ArrayList<DocumentVO>();
		List<SLDocumentDTO> walletControldocuments = dto.getWalletControldocuments();
		if (walletControldocuments == null) {
			walletControldocuments = new ArrayList<>();
		}
		String hasError = (String) model.get(HAS_ERROR);
		if (StringUtils.isBlank(hasError) || hasError.equals("false")) {
			try {

				for (int i = 0; i < dto.getWalletControlFiles().size(); i++) {
					File doc = dto.getWalletControlFiles().get(i);
					String fileName = dto.getWalletControlFilesFileName().get(i);
					DocumentVO documentVO = new DocumentVO();
					SLDocumentDTO walletControldocument = new SLDocumentDTO();
					walletControldocument.setName(fileName);
					mapDocdetails(documentVO, doc, fileName);
					try {
						ProcessResponse processResponse = sLAdminWalletControlDeligate
								.uploadWalletControlDocument(documentVO, walletControlVO);
						if (processResponse != null) {
							Integer obj = (processResponse.getObj() != null) ? (Integer) processResponse.getObj()
									: null;
							if (obj != null)

								// Adds the Document ID to the DocumentVO
								documentVO.setDocumentId(obj.intValue());
							walletControldocument.setDocumentId(obj.intValue());

						}

					} catch (Exception e) {
						logger.error("Exception in uploadDocument: " + e.getMessage());

					}
					documentVOs.add(documentVO);
					walletControldocuments.add(walletControldocument);
				}

			} catch (Exception e) {
				logger.error("Exception " + e.getMessage());
			}
		}
		dto.setWalletControldocuments(walletControldocuments);
		return documentVOs;
	}

	private void mapDocdetails(DocumentVO documentVO, File doc, String fileName) {
		File file = doc;

		documentVO.setDocument(file);
		documentVO.setFileName(fileName);

		documentVO.setDocSize(file.length());
		// Setting the source of document as from provider legal notice
		documentVO.setDocSource(MPConstants.PROVIDER_LEGAL_NOTICE_INDICATOR);

	}

	private void validateDoc(FMManageFundsTabDTO dto) {
		List<IError> errorList = dto.getErrors();
		if(dto!=null && dto.getWalletControlFiles()!=null){
		for (int i=0 ;i< dto.getWalletControlFiles().size();i++){
			File uploadedFile =dto.getWalletControlFiles().get(i);
			File file = uploadedFile;
			if (null != file) {
				Long docSize = file.length();
				Integer size = docSize.intValue() / OrderConstants.SIZE_KB;
				if (size > TWO_KB) {
				
					SOWError err = new SOWError(dto.getTheResourceBundle().getString(
							"IRS_LEVY/Legal_HOLD_Fund"), dto.getTheResourceBundle()
							.getString("Wallet_Control_Invalid_File_Size"),
							OrderConstants.FM_TAB_ERROR);
					errorList.add(err);
				
				}
				// validate file extension
				String fileName = dto.getWalletControlFilesFileName().get(i);
				String fileExtenstion = "";
				if (null != fileName
						&& !(OrderConstants.EMPTY_STR.equals(fileName.trim()))) {
					Integer fileExtensionIndex = fileName.lastIndexOf(".");
					if (fileExtensionIndex == -1) {
						SOWError err = new SOWError(dto.getTheResourceBundle().getString(
								"IRS_LEVY/Legal_HOLD_Fund"), dto.getTheResourceBundle()
								.getString("Wallet_Control_Invalid_File_Format"),
								OrderConstants.FM_TAB_ERROR);
						errorList.add(err);
					} else {
						fileExtenstion = fileName.substring(fileExtensionIndex,
								fileName.length());
						if (!(fileExtenstion.equalsIgnoreCase(JPG)
								|| fileExtenstion.equalsIgnoreCase(GIF)
								|| fileExtenstion.equalsIgnoreCase(DOC)
								|| fileExtenstion.equalsIgnoreCase(PDF) || fileExtenstion
									.equalsIgnoreCase(DOCX))) {
							SOWError err = new SOWError(dto.getTheResourceBundle().getString(
					"IRS_LEVY/Legal_HOLD_Fund"), dto.getTheResourceBundle()
					.getString("Wallet_Control_Invalid_File_Format"),
					OrderConstants.FM_TAB_ERROR);
			errorList.add(err);
						}
					}
				}
				
				
			}
		}
		}else {
		SOWError err = new SOWError(dto.getTheResourceBundle().getString(
				"IRS_LEVY/Legal_HOLD_Fund"), dto.getTheResourceBundle()
				.getString("Wallet_Control_File_Not_Found"),
				OrderConstants.FM_TAB_ERROR);
		errorList.add(err);
		
		}
		dto.setErrors(errorList);
	}

	public void fetchWalletControlInformation() {
		WalletControlVO walletControlVO = new WalletControlVO();
		FMManageFundsTabDTO dto = getModel();
		Integer entityID= get_commonCriteria().getCompanyId();
		if(entityID>0){
		try {
			walletControlVO= sLAdminWalletControlDeligate.fetchWalletControl(entityID);
			if(walletControlVO!=null){
				dto.setEntityWalletControlID(walletControlVO.getId());
				dto.setWalletControlType(walletControlVO.getWalletControlType());
				dto.setWalletControlAmount(walletControlVO.getAmount().toString());
				dto.setWalletControlType(WalletControlEnum.valueOf(walletControlVO.getWalletControlType()).getName());
				dto.setOnHold(walletControlVO.getOnHold());
				List <SLDocumentDTO> walletControldocuments = new ArrayList<>();
				for(WalletControlDocumentVO doc:walletControlVO.getWalletControlDocumentVO()){
					SLDocumentDTO docDTO=new SLDocumentDTO();
					docDTO.setDocumentId(doc.getDocumentId());
					docDTO.setName(doc.getDocumentName());
					walletControldocuments.add(docDTO);
				}
				dto.setWalletControldocuments(walletControldocuments);
			
			}else{
				dto.setEntityWalletControlID(null);
				dto.setWalletControlAmount(null);
				dto.setWalletControlType(null);
				dto.setOnHold(false);
				dto.setWalletControldocuments(null);
			}
			getRequest().getSession()
			.setAttribute("manageFundsTabDTO", dto);	
			
			HashMap<String, String> map = new HashMap<String, String>();
			map = (HashMap<String, String>) getRequest().getSession().getAttribute(
					"hasMapForTabIcons");
			map = setTabIcon(map, OrderConstants.FM_MANAGE_FUNDS_TAB,
					dto.getErrors(), dto.getWarnings());
			getSession().setAttribute("hasMapForTabIcons", map);
			setDefaultTab(OrderConstants.FM_MANAGE_FUNDS);
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
		dto.setEntityWalletControlID(null);
		dto.setWalletControlAmount(null);
		dto.setWalletControlType(null);
		dto.setOnHold(false);
		dto.setWalletControldocuments(null);
		getRequest().getSession()
		.setAttribute("manageFundsTabDTO", dto);
		SOWError err = new SOWError(dto.getTheResourceBundle().getString(
				"Wallet_Control_Error"), dto.getTheResourceBundle()
				.getString("Wallet_Control_Service_down"),
				OrderConstants.FM_TAB_ERROR);
		List<IError> errorList = dto.getErrors();
		errorList.add(err);
		
		
		HashMap<String, String> map = new HashMap<String, String>();
		map = (HashMap<String, String>) getRequest().getSession().getAttribute(
				"hasMapForTabIcons");
		map = setTabIcon(map, OrderConstants.FM_MANAGE_FUNDS_TAB,
				dto.getErrors(), dto.getWarnings());
		getSession().setAttribute("hasMapForTabIcons", map);
		setDefaultTab(OrderConstants.FM_MANAGE_FUNDS);		
	}
	
	public void validateLegalHoldAmount(FMManageFundsTabDTO dto) {
		dto.validate();
		List<IError> errorList = dto.getErrors();

		if (!dto.checkWalletControlAmount()) {
			SOWError err = new SOWError(dto.getTheResourceBundle().getString(
					"IRS_LEVY/Legal_HOLD_Fund"), dto.getTheResourceBundle()
					.getString("Wallet_Control_ValidAmount"),
					OrderConstants.FM_TAB_ERROR);
			errorList.add(err);
		}
		dto.setErrors(errorList);
	}
	public String getJSON(Object obj) {
		String rval = "null";
		JSONSerializer serializer = new JSONSerializer();

		if (obj != null)
			try {
				rval = serializer.exclude("*.class").serialize(obj);
			} catch (Throwable e) {
				logger.error(
						"Unable to generate JSON for object: " + obj.toString(),
						e);
			}
		return rval;
	}

	public FMManageFundsTabDTO getManageFundsTabDTO() {
		return manageFundsTabDTO;
	}

	public void setManageFundsTabDTO(FMManageFundsTabDTO manageFundsTabDTO) {
		this.manageFundsTabDTO = manageFundsTabDTO;
	}

	public ICreateServiceOrderDelegate getCreateServiceOrderDelegate() {
		return createServiceOrderDelegate;
	}

	public void setCreateServiceOrderDelegate(
			ICreateServiceOrderDelegate createServiceOrderDelegate) {
		this.createServiceOrderDelegate = createServiceOrderDelegate;
	}

	public IW9RegistrationBO getW9RegistrationBO() {
		return w9RegistrationBO;
	}

	public void setW9RegistrationBO(IW9RegistrationBO registrationBO) {
		w9RegistrationBO = registrationBO;
	}

	public ILookupBO getLookUpBO() {
		return lookUpBO;
	}

	public void setLookUpBO(ILookupBO lookUpBO) {
		this.lookUpBO = lookUpBO;
	}

	public void setCountryList(List<String> countryList) {
		this.countryList = countryList;
	}

	public Cryptography getCryptography() {
		return cryptography;
	}

	public void setCryptography(Cryptography cryptography) {
		this.cryptography = cryptography;
	}

	public IBuyerPersIdentificationBO getBuyerPIIBo() {
		return buyerPIIBo;
	}

	public void setBuyerPIIBo(IBuyerPersIdentificationBO buyerPIIBo) {
		this.buyerPIIBo = buyerPIIBo;
	}

	public ISLAdminWalletControlDeligate getsLAdminWalletControlDeligate() {
		return sLAdminWalletControlDeligate;
	}

	public void setsLAdminWalletControlDeligate(
			ISLAdminWalletControlDeligate sLAdminWalletControlDeligate) {
		this.sLAdminWalletControlDeligate = sLAdminWalletControlDeligate;
	}

}
