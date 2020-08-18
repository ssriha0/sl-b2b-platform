package com.newco.marketplace.web.action.buyer;



import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.buyer.IBuyerPersIdentificationBO;
import com.newco.marketplace.business.iBusiness.lookup.ILookupBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.CryptographyVO;
import com.newco.marketplace.dto.vo.serviceorder.Buyer;
import com.newco.marketplace.interfaces.MPConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.interfaces.ProviderConstants;
import com.newco.marketplace.util.Cryptography;
import com.newco.marketplace.util.PropertiesUtils;
import com.newco.marketplace.utils.DateUtils;
import com.newco.marketplace.utils.ServiceLiveStringUtils;
import com.newco.marketplace.vo.buyer.PersonalIdentificationVO;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.delegates.ICreateServiceOrderDelegate;
import com.newco.marketplace.web.dto.IError;
import com.newco.marketplace.web.dto.SOWError;
import com.newco.marketplace.web.dto.buyerPII.BuyerPIIDTO;
import com.newco.marketplace.web.utils.SLStringUtils;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;



public class BuyerEditPIIAction extends SLBaseAction implements Preparable,ModelDriven<BuyerPIIDTO>
{
	
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(BuyerEditPIIAction.class.getName());
	
	private BuyerPIIDTO buyerPIIDTO = new BuyerPIIDTO();
	private IBuyerPersIdentificationBO buyerPIIBo;
	private ICreateServiceOrderDelegate createServiceOrderDelegate;
	private Cryptography cryptography;
	private ILookupBO lookUpBO;
	private Pattern pattern;
	private Matcher matcher;
	//Set after bean creation
	private List<String> countryList = new ArrayList<String>();
	
	public BuyerEditPIIAction(IBuyerPersIdentificationBO buyerPIIBo,ICreateServiceOrderDelegate icreateServiceOrderDelegate, BuyerPIIDTO buyerPIIDTO) {
		this.buyerPIIBo = buyerPIIBo;
		this.createServiceOrderDelegate = icreateServiceOrderDelegate;
		this.buyerPIIDTO = buyerPIIDTO;
	}
	
	public void initialize(){
		countryList = lookUpBO.getCountryList();
	}
	
	public void prepare() throws Exception {
		createCommonServiceOrderCriteria();
	}
	
	public String execute() throws Exception {
		loadBuyerPIIData();
		
		String headerTxt = getParameter("headerTxt");
		
		if(SLStringUtils.isNullOrEmpty(headerTxt) == false)
		{
			setAttribute("headerTxt", headerTxt);
		}
		
		return "edit";
	}
	
	/**
	 * Description: Load Buyer Personal Identification Information details.
	 */
	
	public void loadBuyerPIIData() throws Exception{
		
		Integer buyerId = this.get_commonCriteria().getCompanyId();
		String einTaxPayerId = "";
		String ssnTaxPayerId = "";
		String altTaxPayerId = "";
		
		if (buyerPIIBo != null){
			try
			{
			Buyer buyerVO = buyerPIIBo.getBuyerPIIData(buyerId);
			if (buyerVO != null){				
				//decrypt and mask the taxpayer Id
				CryptographyVO cryptographyVO = new CryptographyVO();
				cryptographyVO.setKAlias(MPConstants.ENCRYPTION_KEY);
				if (buyerVO.getEinNoEnc() != null){
					cryptographyVO.setInput(buyerVO.getEinNoEnc());
				}
				cryptographyVO = cryptography.decryptKey(cryptographyVO);
							
				buyerPIIDTO.setPiiIndex(buyerVO.getPiiIndex());

				if (buyerVO.getPiiIndex() == 2 && buyerVO.getEinNoEnc()!= null){
					buyerPIIDTO.setEinSsnInd("3");
					getSession().setAttribute("taxIdInd", "3");
					buyerPIIDTO.setAltIdDob(buyerVO.getDob());
					buyerPIIDTO.setDocumentType(buyerVO.getAltIDDocType());
										
					if(cryptographyVO != null && cryptographyVO.getResponse() != null){
						altTaxPayerId = cryptographyVO.getResponse();
						if (altTaxPayerId!=null){
							buyerPIIDTO.setDocumentIdNo(ServiceLiveStringUtils.maskStringAlphaNum(altTaxPayerId, 4, "X"));
							buyerPIIDTO.setDocumentIdNoHidden(ServiceLiveStringUtils.maskStringAlphaNum(altTaxPayerId, 4, "X"));
						}		
					}	
					buyerPIIDTO.setCountry(buyerVO.getAltIDCountryIssue());	
				}else if (buyerVO.getPiiIndex() == 1 && buyerVO.getEinNoEnc()!= null)
				{
					buyerPIIDTO.setEinSsnInd("2");
					getSession().setAttribute("taxIdInd", "2");
					if(cryptographyVO != null && cryptographyVO.getResponse() != null){	
						
						ssnTaxPayerId = cryptographyVO.getResponse();
						if(ssnTaxPayerId!=null && ssnTaxPayerId.length()>=6)
						{
							buyerPIIDTO.setSsnTaxPayerId(ProviderConstants.COMPANY_SSN_ID_MASK+ssnTaxPayerId.substring(5));
							buyerPIIDTO.setConfirmSsnTaxPayerId(ProviderConstants.COMPANY_SSN_ID_MASK+ssnTaxPayerId.substring(5));
							buyerPIIDTO.setSsnTaxPayerIdHidden(ProviderConstants.COMPANY_SSN_ID_MASK+ssnTaxPayerId.substring(5));
						}
					}
					buyerPIIDTO.setSsnDob(buyerVO.getDob());
				}
				else if (buyerVO.getPiiIndex() == 0 && buyerVO.getEinNoEnc()!= null)
				{
					buyerPIIDTO.setEinSsnInd("1");
					getSession().setAttribute("taxIdInd", "1");
					if(cryptographyVO != null && cryptographyVO.getResponse() != null){
						
						einTaxPayerId = cryptographyVO.getResponse();
						if(einTaxPayerId!=null && einTaxPayerId.length()>=6)
						{
							buyerPIIDTO.setEinTaxPayerId(ProviderConstants.COMPANY_EIN_ID_MASK+einTaxPayerId.substring(5));
							buyerPIIDTO.setConfirmEinTaxPayerId(ProviderConstants.COMPANY_EIN_ID_MASK+einTaxPayerId.substring(5));
							buyerPIIDTO.setEinTaxPayerIdHidden(ProviderConstants.COMPANY_EIN_ID_MASK+einTaxPayerId.substring(5));
						}					
					}
				}else if (buyerVO.getPiiIndex() == 0 && buyerVO.getEinNoEnc()== null)
				{
					buyerPIIDTO.setEinSsnInd("0");
					getSession().setAttribute("taxIdInd", "0");
				}
				
			}
			setModel(buyerPIIDTO);
			getSession().setAttribute("einTaxPayerId",buyerPIIDTO.getEinTaxPayerId());
			getSession().setAttribute("confirmEinTaxPayerId", buyerPIIDTO.getConfirmEinTaxPayerId());
			getSession().setAttribute("einTaxPayerIdHidden", buyerPIIDTO.getEinTaxPayerIdHidden());
			
			getSession().setAttribute("ssnTaxPayerId", buyerPIIDTO.getSsnTaxPayerId());
			getSession().setAttribute("confirmSsnTaxPayerId", buyerPIIDTO.getConfirmSsnTaxPayerId());
			getSession().setAttribute("ssnTaxPayerIdHidden", buyerPIIDTO.getSsnTaxPayerIdHidden());
			//getSession().setAttribute("ssnDob", buyerPIIDTO.getSsnDob());
			
			getSession().setAttribute("documentIdNo", buyerPIIDTO.getDocumentIdNo());
			getSession().setAttribute("documentIdNoHidden", buyerPIIDTO.getDocumentIdNoHidden());
			//getSession().setAttribute("documentIdType", buyerPIIDTO.getDocumentType());
			//getSession().setAttribute("documentIdCountry", buyerPIIDTO.getCountry());
			//getSession().setAttribute("altDob", buyerPIIDTO.getAltIdDob());
						
			getSession().setAttribute("disableInd", "true");
			}
		
			catch(Exception e)
			{
				logger.error("Error in loading Buyer Personal Identification"+e);
			}
			}
		}
	
	/**
	 * Description: Save Buyer Personal Identification Information
	 */
	public String savePII() throws Exception {
		BuyerPIIDTO dto = getModel();
		List<IError> errorList = new ArrayList<IError>();
		boolean isValidData = validateBuyerPIIInputData(dto, errorList);
		String einNoOrig = "";
		String einNo = "";
		String ssnNoOrig = "";
		String ssnNo = "";
		
		//input params for saving Buyer PII information
		Integer buyerId = get_commonCriteria().getCompanyId();
		if (dto.getEinTaxPayerId()!= null && !dto.getEinTaxPayerId().equals("")){
			einNoOrig = dto.getEinTaxPayerId();
			einNo = einNoOrig.replaceAll("-", "");
		}
		if (dto.getSsnTaxPayerId()!= null && !dto.getSsnTaxPayerId().equals("")){
			ssnNoOrig = dto.getSsnTaxPayerId();
			ssnNo = ssnNoOrig.replaceAll("-", "");
		}		
		String ssnDob = dto.getSsnDob();
		String altIdDob = dto.getAltIdDob();
		String documentType= dto.getDocumentType();
		String documentNo = dto.getDocumentIdNo();
		String country = dto.getCountry();
		
		if (isValidData){
			try
			{	
				//if saving BuyerPII without editing the PII id information, reset the actual PII id numbers instead of masked values.					
					Buyer buyerVO = buyerPIIBo.getBuyerPIIData(buyerId);
					String documentNoDB = "";
					String documentTypeDB = "";
					String countryDB = "";
					String altIdDobDB = "";
					String ssnNoDB = "";
					String ssnDobDB = "";
					String einNoDB = "";
					
					if (buyerVO != null){
						//decrypt and mask the taxpayer Id
						CryptographyVO cryptographyVO = new CryptographyVO();
						cryptographyVO.setKAlias(MPConstants.ENCRYPTION_KEY);
						if (buyerVO.getEinNoEnc() != null){
							cryptographyVO.setInput(buyerVO.getEinNoEnc());
						}
						cryptographyVO = cryptography.decryptKey(cryptographyVO);
									
						if (buyerVO.getPiiIndex() == 2)
						{
							if(cryptographyVO != null && cryptographyVO.getResponse() != null){
								documentNoDB = cryptographyVO.getResponse();
							}
							documentTypeDB = buyerVO.getAltIDDocType();
							countryDB = buyerVO.getAltIDCountryIssue();
							altIdDobDB = buyerVO.getDob();
							
						} else if (buyerVO.getPiiIndex() == 1)
						{
							if(cryptographyVO != null && cryptographyVO.getResponse() != null){
								ssnNoDB = cryptographyVO.getResponse();
							}
							ssnDobDB = buyerVO.getDob();
							
						} else if (buyerVO.getPiiIndex() == 0)
						{
							if(cryptographyVO != null && cryptographyVO.getResponse() != null){
								einNoDB = cryptographyVO.getResponse();
							}
						}
							
					}
					//if saving BuyerPII without editing the PII id information.	 
					if(null!=dto.getSsnSaveInd()&& dto.getSsnSaveInd()==false){
						
						if(dto.getEinSsnInd()!=null && dto.getEinSsnInd().equals("1")){
							
								SOWError err = new SOWError("", "There are no changes to save", OrderConstants.FM_ERROR);
								errorList.add(err);
												
						} else if(dto.getEinSsnInd()!=null && dto.getEinSsnInd().equals("2")){
							
							if (ssnDob.equals(ssnDobDB)){
								SOWError err = new SOWError("", "There are no changes to save", OrderConstants.FM_ERROR);
								errorList.add(err);
							}else{
								createServiceOrderDelegate.saveSSNId(buyerId, ssnNoDB, ssnDob);					
							}
							
						} else if(dto.getEinSsnInd()!= null && dto.getEinSsnInd().equals("3")){
							
							if (documentType.equals(documentTypeDB) && (country.equals(countryDB)) && (altIdDob.equals(altIdDobDB))){
								SOWError err = new SOWError("", "There are no changes to save", OrderConstants.FM_ERROR);
								errorList.add(err);
							}else{
								createServiceOrderDelegate.saveAltTaxId(buyerId, documentType, documentNoDB, country, altIdDob);
							}
						}
						dto.setErrors(errorList);
					}else{
						if(dto.getEinSsnInd()!=null && dto.getEinSsnInd().equals("1")){
							
							if (einNo.equals(einNoDB)){
								SOWError err = new SOWError("", "There are no changes to save", OrderConstants.FM_ERROR);
								errorList.add(err);
							}else{
								createServiceOrderDelegate.saveTaxPayerId(buyerId, einNo);	
							}
						
						} else if(dto.getEinSsnInd()!=null && dto.getEinSsnInd().equals("2")){
							
							if (ssnNo.equals(ssnNoDB)&& ssnDob.equals(ssnDobDB)){
								SOWError err = new SOWError("", "There are no changes to save", OrderConstants.FM_ERROR);
								errorList.add(err);
							}else{
								createServiceOrderDelegate.saveSSNId(buyerId, ssnNo, ssnDob);
							}
							
						} else if(dto.getEinSsnInd()!= null && dto.getEinSsnInd().equals("3")){
							
							if (documentType.equals(documentTypeDB) && (documentNo.equals(documentNoDB))&& (country.equals(countryDB)) && (altIdDob.equals(altIdDobDB))){
								SOWError err = new SOWError("", "There are no changes to save", OrderConstants.FM_ERROR);
								errorList.add(err);
							}else{
								createServiceOrderDelegate.saveAltTaxId(buyerId, documentType, documentNo, country, altIdDob);
							}
						}
						dto.setErrors(errorList);
					}										
			}
			catch(Exception e)
			{
			logger.error("Error while Saving Buyer Personal Identification Information"+e);	
			}
			
			setSavePIIErrorMessages(dto, errorList);
			return SUCCESS;
		}
		getSession().setAttribute("disableInd", "false");
		String prevTaxIdType = (String)getSession().getAttribute("taxIdInd");
		if (dto.getEinSsnInd().equals("1")){
			if (prevTaxIdType.equals("2")){
				dto.setSsnTaxPayerId((String)getSession().getAttribute("ssnTaxPayerId"));
				dto.setConfirmSsnTaxPayerId((String)getSession().getAttribute("confirmSsnTaxPayerId"));	
			}
			if (prevTaxIdType.equals("3")){
				dto.setDocumentIdNo((String)getSession().getAttribute("documentIdNo"));
			}
		}
		if (dto.getEinSsnInd().equals("2")){
			if (prevTaxIdType.equals("1")){
				dto.setEinTaxPayerId((String)getSession().getAttribute("einTaxPayerId"));
				dto.setConfirmEinTaxPayerId((String)getSession().getAttribute("confirmEinTaxPayerId"));
			}
			if (prevTaxIdType.equals("3")){
				dto.setDocumentIdNo((String)getSession().getAttribute("documentIdNo"));
			}
		}
		if (dto.getEinSsnInd().equals("3")){
			if (prevTaxIdType.equals("1")){
				dto.setEinTaxPayerId((String)getSession().getAttribute("einTaxPayerId"));
				dto.setConfirmEinTaxPayerId((String)getSession().getAttribute("confirmEinTaxPayerId"));
			}
			if (prevTaxIdType.equals("2")){
				dto.setSsnTaxPayerId((String)getSession().getAttribute("ssnTaxPayerId"));
				dto.setConfirmSsnTaxPayerId((String)getSession().getAttribute("confirmSsnTaxPayerId"));	
			}			
		}
					
		return "edit";	
	}
	
	/**
	 * Description: Sets error messages, if any, after Buyer PII save.
	 * @param dto
	 * @param errorList
	 */
	private void setSavePIIErrorMessages(BuyerPIIDTO dto, List<IError> errorList) {
		if (dto.getErrors().size() == 0) {
			getSession().setAttribute("savePIISuccessMsg", "The Personal Identification Information has been saved." );
			getSession().setAttribute("piiSaveStatus", "success");
		}else {
			/*SOWError err = new SOWError("", "The Personal Identification information provided could not be saved; please verify the information you provided and try again", OrderConstants.FM_ERROR);
			errorList = new ArrayList<IError>();
			errorList.add(err);
			dto.setErrors(errorList);*/
			errorList = dto.getErrors();
			getSession().setAttribute("savePIIErrorMsg", errorList.get(0) );
			getSession().setAttribute("piiSaveStatus", "fail");
		}
	}
	
	/**
	 * Description: Validation of Buyer Personal Identification Information
	 * @param dto
	 * @param errorList
	 * @return
	 */
	private boolean validateBuyerPIIInputData(BuyerPIIDTO dto, List<IError> errorList) {
		
		boolean isValidData = true;
		
		String einTaxpayerId = "";
		String einConfirmTaxpayerId = "";
		String ssnTaxpayerId = "";
		String ssnConfirmTaxpayerId = "";
		String altTaxpayerId = "";
		
		String taxIdInd = (String)getSession().getAttribute("taxIdInd");
		
		if (dto != null){
			if (dto.getEinSsnInd()!= null){
				if (!dto.getEinSsnInd().equals(taxIdInd)){
					dto.setSsnSaveInd(true);
				}
				if (dto.getEinSsnInd().equals("1")){
					
					if(!StringUtils.isBlank(dto.getEinTaxPayerId())){
						einTaxpayerId = dto.getEinTaxPayerId().replaceAll("-", "");
					}
					if(!StringUtils.isBlank(dto.getConfirmEinTaxPayerId())){
						einConfirmTaxpayerId = dto.getConfirmEinTaxPayerId().replaceAll("-", "");
					}
					
					if(dto.getSsnSaveInd()==true){
						// Check if nothing is there
						if(StringUtils.isBlank(dto.getEinTaxPayerId()) && StringUtils.isBlank(dto.getConfirmEinTaxPayerId())){
							SOWError err = new SOWError("", "Please enter your Personal Identification Information.", OrderConstants.FM_ERROR);
							errorList.add(err);
							isValidData = false;
						}else{
							// Check for size
							if((!StringUtils.isBlank(dto.getEinTaxPayerId()) &&  einTaxpayerId.trim().length()<9) 
									|| (!StringUtils.isBlank(dto.getConfirmEinTaxPayerId()) &&  einConfirmTaxpayerId.trim().length()<9)){
								SOWError err = new SOWError("", "Taxpayer ID (EIN) should be 9 digits.", OrderConstants.FM_ERROR);
								errorList.add(err);
								isValidData = false;
							}
							
							// Check for numeric
							if(!StringUtils.isBlank(dto.getEinTaxPayerId()) && !StringUtils.isNumeric(einTaxpayerId)){
								SOWError err = new SOWError("", "Taxpayer ID (EIN) can only be numeric.", OrderConstants.FM_ERROR);
								errorList.add(err);
								isValidData = false;
							}
							if(!StringUtils.isBlank(dto.getConfirmEinTaxPayerId()) && !StringUtils.isNumeric(einConfirmTaxpayerId)){
								SOWError err = new SOWError("", "Taxpayer ID (EIN) can only be numeric.", OrderConstants.FM_ERROR);
								errorList.add(err);
								isValidData = false;
							}
							if(isValidData && StringUtils.isNumeric(einTaxpayerId) && einTaxpayerId.trim().length()==9){
								String tinRestrPattern = PropertiesUtils.getPropertyValue(Constants.AppPropConstants.PII_EINSSN_RESTRICTED_PATTERN);
								if(tinRestrPattern!= null && !"".equals(tinRestrPattern) && tinRestrPattern.contains(einTaxpayerId)){
									SOWError err = new SOWError("", dto.getEinTaxPayerId()+" is not a valid Taxpayer ID. Please enter a valid Taxpayer ID (EIN)", OrderConstants.FM_ERROR);
									errorList.add(err);
									isValidData = false;
								}
							}
							
							
							// Check for match
							if((StringUtils.isBlank(dto.getEinTaxPayerId()) && !StringUtils.isBlank(dto.getConfirmEinTaxPayerId()))
									|| (!StringUtils.isBlank(dto.getEinTaxPayerId()) && StringUtils.isBlank(dto.getConfirmEinTaxPayerId()))
									|| (!StringUtils.isBlank(dto.getEinTaxPayerId()) && !StringUtils.isBlank(dto.getConfirmEinTaxPayerId()) 
											&& !einTaxpayerId.equals(einConfirmTaxpayerId))){
								SOWError err = new SOWError("", "The Taxpayer IDs (EIN) entered do not match.", OrderConstants.FM_ERROR);
								errorList.add(err);
								isValidData = false;
							}
							
						}
					}
					getSession().setAttribute("taxidEIN", "checked");
				}else if (dto.getEinSsnInd().equals("2")){
					boolean didYouCheckDate = false;
					if(!StringUtils.isBlank(dto.getSsnTaxPayerId())){
						ssnTaxpayerId = dto.getSsnTaxPayerId().replaceAll("-", "");
					}
					if(!StringUtils.isBlank(dto.getConfirmSsnTaxPayerId())){
						ssnConfirmTaxpayerId = dto.getConfirmSsnTaxPayerId().replaceAll("-", "");
					}
					
					if(dto.getSsnSaveInd()==true){
						
						// No data is present
						if(StringUtils.isBlank(dto.getSsnTaxPayerId()) && 
								StringUtils.isBlank(dto.getConfirmSsnTaxPayerId()) &&
										StringUtils.isBlank(dto.getSsnDob())){
							SOWError err = new SOWError("", "Please enter your Personal Identification Information.", OrderConstants.FM_ERROR);
							errorList.add(err);
							isValidData = false;
							didYouCheckDate = true;
						}else{
							// check for no tax id info but dob - in case of modification of existing data.
							if (StringUtils.isBlank(dto.getSsnTaxPayerId()) && StringUtils.isBlank(dto.getConfirmSsnTaxPayerId())){
								SOWError err = new SOWError("", "Please enter your Personal Identification Information.", OrderConstants.FM_ERROR);
								errorList.add(err);
								isValidData = false;
							}
							// Check for size
							if((!StringUtils.isBlank(dto.getSsnTaxPayerId()) &&  ssnTaxpayerId.trim().length()<9) 
									|| (!StringUtils.isBlank(dto.getConfirmSsnTaxPayerId()) &&  ssnConfirmTaxpayerId.trim().length()<9)){
								SOWError err = new SOWError("", "Taxpayer ID (SSN) should be 9 digits.", OrderConstants.FM_ERROR);
								errorList.add(err);
								isValidData = false;
							}
							// Check for numeric
							if(!StringUtils.isBlank(dto.getSsnTaxPayerId()) && !StringUtils.isNumeric(ssnTaxpayerId)){
								SOWError err = new SOWError("", "Taxpayer ID (SSN) can only be numeric.", OrderConstants.FM_ERROR);
								errorList.add(err);
								isValidData = false;
							}
							if(!StringUtils.isBlank(dto.getConfirmSsnTaxPayerId()) && !StringUtils.isNumeric(ssnConfirmTaxpayerId)){
								SOWError err = new SOWError("", "Taxpayer ID (SSN) can only be numeric.", OrderConstants.FM_ERROR);
								errorList.add(err);
								isValidData = false;
							}
							if(isValidData && StringUtils.isNumeric(ssnTaxpayerId) && ssnTaxpayerId.trim().length()==9){
								String tinRestrPattern = PropertiesUtils.getPropertyValue(Constants.AppPropConstants.PII_EINSSN_RESTRICTED_PATTERN);
								if(tinRestrPattern!= null && !"".equals(tinRestrPattern) && tinRestrPattern.contains(ssnTaxpayerId)){
									SOWError err = new SOWError("", dto.getSsnTaxPayerId()+" is not a valid Taxpayer ID. Please enter a valid Taxpayer ID (SSN)", OrderConstants.FM_ERROR);
									errorList.add(err);
									isValidData = false;
								}
							}
							// Check for match
							if((StringUtils.isBlank(dto.getSsnTaxPayerId()) && !StringUtils.isBlank(dto.getConfirmSsnTaxPayerId()))
									|| (!StringUtils.isBlank(dto.getSsnTaxPayerId()) && StringUtils.isBlank(dto.getConfirmSsnTaxPayerId()))
									|| (!StringUtils.isBlank(dto.getSsnTaxPayerId()) && !StringUtils.isBlank(dto.getConfirmSsnTaxPayerId()) 
											&& !ssnTaxpayerId.equals(ssnConfirmTaxpayerId))){
								SOWError err = new SOWError("", "The Taxpayer IDs (SSN) entered do not match.", OrderConstants.FM_ERROR);
								errorList.add(err);
								isValidData = false;
							}
						}		
					}					
					if (StringUtils.isBlank(dto.getSsnDob()) && !didYouCheckDate){
						
						SOWError err = new SOWError("", "Please enter a valid Date of Birth in MM/DD/YYYY format.", OrderConstants.FM_ERROR);
						errorList.add(err);
						isValidData = false;
					}else if(!StringUtils.isBlank(dto.getSsnDob())){
						String ssnDob = dto.getSsnDob();
						String dobPattern = "^(0[1-9]|1[012])[/](0[1-9]|[12][0-9]|3[01])[/](19|20)\\d\\d$";
						pattern = Pattern.compile(dobPattern);
						matcher = pattern.matcher(ssnDob);
						if (!matcher.matches() || !(DateUtils.isValidDate(ssnDob, "MM/dd/yyyy"))){													
							SOWError err = new SOWError("", "Please enter a valid Date of Birth in MM/DD/YYYY format.", OrderConstants.FM_ERROR);
							errorList.add(err);
							isValidData = false;
						}else {
							Date today=new Date();
							Date date=null;
							DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
							try{
								date=dateFormat.parse(ssnDob);
							}
							catch(Exception e){
								logger.error("Parse Error"+e);	
								SOWError err = new SOWError("", "Please enter a valid Date of Birth in MM/DD/YYYY format.", OrderConstants.FM_ERROR);
								errorList.add(err);
								isValidData = false;
							}
							if(date!=null && date.after(today)){
								SOWError err = new SOWError("", "Please enter a valid Date of Birth in MM/DD/YYYY format.", OrderConstants.FM_ERROR);
								errorList.add(err);
								isValidData = false;
							}
						}
						
					}
					getSession().setAttribute("taxidSSN", "checked");
				}else if (dto.getEinSsnInd().equals("3")){
					// Check for nothing
					if(StringUtils.isBlank(dto.getDocumentType()) && StringUtils.isBlank(dto.getDocumentIdNo())
							&& dto.getCountry().equals("-1") && StringUtils.isBlank(dto.getAltIdDob())){
						SOWError err = new SOWError("", "Please enter your Personal Identification Information.", OrderConstants.FM_ERROR);
						errorList.add(err);
						isValidData = false;
					}else{
						if (StringUtils.isBlank(dto.getDocumentType()) || dto.getDocumentType().trim().length()>35) 
						{
							SOWError err = new SOWError("", "Please enter valid a Document Type.", OrderConstants.FM_ERROR);
							errorList.add(err);
							isValidData = false;
						}
						if(dto.getSsnSaveInd()==true){
							if (StringUtils.isBlank(dto.getDocumentIdNo()))
							{
								SOWError err = new SOWError("", "Please enter a valid Document Identification Number.", OrderConstants.FM_ERROR);
								errorList.add(err);
								isValidData = false;
							}else if (dto.getDocumentIdNo().trim().length()>15)
							{
								SOWError err = new SOWError("", "Please enter a valid Document Identification Number.", OrderConstants.FM_ERROR);
								errorList.add(err);
								isValidData = false;
							}
						}
						if (dto.getCountry().equals("-1"))
						{
							SOWError err = new SOWError("", "Please select a Country of Issuance.", OrderConstants.FM_ERROR);
							errorList.add(err);
							isValidData = false;
						}
						if (StringUtils.isBlank(dto.getAltIdDob()))
						{
							SOWError err = new SOWError("", "Please enter a valid Date of Birth in MM/DD/YYYY format.", OrderConstants.FM_ERROR);
							errorList.add(err);
							isValidData = false;
						}
						else 
						{
							String altDob = dto.getAltIdDob();
							String dobPattern = "^(0[1-9]|1[012])[/](0[1-9]|[12][0-9]|3[01])[/](19|20)\\d\\d$";
							pattern = Pattern.compile(dobPattern);
							matcher = pattern.matcher(altDob);
							if (!matcher.matches() || !(DateUtils.isValidDate(altDob, "MM/dd/yyyy"))){													
								SOWError err = new SOWError("", "Please enter a valid Date of Birth in MM/DD/YYYY format.", OrderConstants.FM_ERROR);
								errorList.add(err);
								isValidData = false;
							}else {
								Date today=new Date();
								Date date=null;
								DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
								try{
									date=dateFormat.parse(altDob);
								}
								catch(Exception e){
									logger.error("Parse Error"+e);	
									SOWError err = new SOWError("", "Please enter a valid Date of Birth in MM/DD/YYYY format.", OrderConstants.FM_ERROR);
									errorList.add(err);
									isValidData = false;
								}
								if(date!=null && date.after(today)){
									SOWError err = new SOWError("", "Please enter a valid Date of Birth in MM/DD/YYYY format.", OrderConstants.FM_ERROR);
									errorList.add(err);
									isValidData = false;
								}
							}
							
						
						}
					}
					
					
					getSession().setAttribute("taxidAlt", "checked");
				}
			}else {
				SOWError err = new SOWError("", "Please select a Personal Identification Information type.", OrderConstants.FM_ERROR);
				errorList.add(err);
				isValidData = false;
				dto.setEinSsnInd("0");
			}
			dto.setErrors(errorList);
		}
		return isValidData;
	}
	
	
	
	public String loadHistory()
	{
		SecurityContext securityContext = (SecurityContext) getSession().getAttribute("SecurityContext");
		Integer buyerId = securityContext.getCompanyId();
		List<PersonalIdentificationVO> piiHistory;

		try
		{
			piiHistory = buyerPIIBo.getPiiHistory(buyerId);
			getSession().setAttribute("piiHistory", piiHistory);

		}
		catch (Exception e) {
			logger.error("Not able to get pii history. ", e);
		}

		return "success_history";
	}
	
	public BuyerPIIDTO getModel()
	{
		if(getRequest().getSession().getAttribute("buyerPIIDTO") != null){
			buyerPIIDTO = (BuyerPIIDTO)getRequest().getSession().getAttribute("buyerPIIDTO");
		}
		return buyerPIIDTO;
	}
	
	public void setModel(BuyerPIIDTO model)
	{
		buyerPIIDTO = model;
	}

	public BuyerPIIDTO getBuyerPIIDTO() {
		return buyerPIIDTO;
	}

	public void setBuyerPIIDTO(BuyerPIIDTO buyerPIIDTO) {
		this.buyerPIIDTO = buyerPIIDTO;
	}

	public ICreateServiceOrderDelegate getCreateServiceOrderDelegate() {
		return createServiceOrderDelegate;
	}

	public void setCreateServiceOrderDelegate(
			ICreateServiceOrderDelegate createServiceOrderDelegate) {
		this.createServiceOrderDelegate = createServiceOrderDelegate;
	}

	public Cryptography getCryptography() {
		return cryptography;
	}

	public void setCryptography(Cryptography cryptography) {
		this.cryptography = cryptography;
	}

	public List<String> getCountryList() {
		return countryList;
	}

	public void setCountryList(List<String> countryList) {
		this.countryList = countryList;
	}

	public void setLookUpBO(ILookupBO lookUpBO) {
		this.lookUpBO = lookUpBO;
	}

	public IBuyerPersIdentificationBO getBuyerPIIBo() {
		return buyerPIIBo;
	}

	public void setBuyerPIIBo(IBuyerPersIdentificationBO buyerPIIBo) {
		this.buyerPIIBo = buyerPIIBo;
	}
	
}