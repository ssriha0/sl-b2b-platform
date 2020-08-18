package com.newco.marketplace.web.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import com.newco.marketplace.dto.vo.serviceorder.SODocument;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.util.LabelValueBean;

import com.google.gwt.user.client.rpc.core.java.lang.boolean_Array_CustomFieldSerializer;
import com.newco.marketplace.business.businessImpl.so.pdf.SOPDFUtils;
import com.newco.marketplace.business.iBusiness.api.beans.ordermanagement.getPreCallHistory.PreCallHistory;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.criteria.FilterCriteria;
import com.newco.marketplace.criteria.OrderCriteria;
import com.newco.marketplace.criteria.PagingCriteria;
import com.newco.marketplace.criteria.SortCriteria;
import com.newco.marketplace.dto.BuyerSOTemplateContactDTO;
import com.newco.marketplace.dto.BuyerSOTemplateDTO;
import com.newco.marketplace.dto.BuyerSOTemplatePartDTO;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.LocationVO;
import com.newco.marketplace.dto.vo.SOWorkflowControlsVO;
import com.newco.marketplace.dto.vo.ledger.Account;
import com.newco.marketplace.dto.vo.ledger.AccountHistoryVO;
import com.newco.marketplace.dto.vo.ledger.CreditCardVO;
import com.newco.marketplace.dto.vo.ledger.ListAfterSearchVO;
import com.newco.marketplace.dto.vo.logging.SoChangeDetailVo;
import com.newco.marketplace.dto.vo.ordergroup.OrderGroupVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.dto.vo.serviceorder.CriteriaMap;
import com.newco.marketplace.dto.vo.serviceorder.MarketMakerProviderResponse;
import com.newco.marketplace.dto.vo.serviceorder.Part;
import com.newco.marketplace.dto.vo.serviceorder.ProviderInvoicePartsVO;
import com.newco.marketplace.dto.vo.serviceorder.RoutedProvider;
import com.newco.marketplace.dto.vo.serviceorder.ServiceDatetimeSlot;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderAddonVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderCustomRefVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderNote;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSearchResultsVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSurveyResponseVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderTask;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderTaskDetail;
import com.newco.marketplace.dto.vo.serviceorder.SoLocation;
import com.newco.marketplace.dto.vo.serviceorder.SoSchedule;
import com.newco.marketplace.dto.vo.survey.SurveyRatingsVO;
import com.newco.marketplace.interfaces.BuyerConstants;
import com.newco.marketplace.interfaces.CreditCardConstants;
import com.newco.marketplace.interfaces.LedgerConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.utils.DateUtils;
import com.newco.marketplace.utils.DocumentUtils;
import com.newco.marketplace.utils.MoneyUtil;
import com.newco.marketplace.utils.ServiceLiveStringUtils;
import com.newco.marketplace.utils.TimeUtils;
import com.newco.marketplace.utils.UIUtils;
import com.newco.marketplace.vo.provider.FinancialProfileVO;
import com.newco.marketplace.web.action.financemanager.FMReportsAction;
import com.newco.marketplace.web.dto.AccountDTO;
import com.newco.marketplace.web.dto.FMFinancialProfileTabDTO;
import com.newco.marketplace.web.dto.FMManageAccountsTabDTO;
import com.newco.marketplace.web.dto.FMOverviewHistoryTabDTO;
import com.newco.marketplace.web.dto.FMReportTabDTO;
import com.newco.marketplace.web.dto.ProviderSPNDTO;
import com.newco.marketplace.web.dto.ResponseStatusDTO;
import com.newco.marketplace.web.dto.ResponseStatusTabDTO;
import com.newco.marketplace.web.dto.SLDocumentDTO;
import com.newco.marketplace.web.dto.SOContactDTO;
import com.newco.marketplace.web.dto.SODocumentDTO;
import com.newco.marketplace.web.dto.SOPartsDTO;
import com.newco.marketplace.web.dto.SOTaskDTO;
import com.newco.marketplace.web.dto.SOTaskPriceHistoryDTO;
import com.newco.marketplace.dto.vo.serviceorder.SOTaskPriceHistoryVO;
import com.newco.marketplace.web.dto.SOWBrandingInfoDTO;
import com.newco.marketplace.web.dto.SOWPhoneDTO;
import com.newco.marketplace.web.dto.SOWSelBuyerRefDTO;
import com.newco.marketplace.web.dto.ServiceOrderDTO;
import com.newco.marketplace.web.dto.ServiceOrderNoteDTO;
import com.newco.marketplace.web.dto.ServiceOrdersCriteria;
import com.newco.marketplace.web.dto.SoChangeDetailsDTO;
import com.newco.marketplace.web.dto.TransactionDTO;
import com.newco.marketplace.web.dto.ordergroup.OrderGroupDTO;
import com.servicelive.common.CommonUtil;
import  com.newco.marketplace.dto.vo.financemanger.FMReportVO;


/**
 * $Revision: 1.165 $ $Author: awadhwa $ $Date: 2008/06/10 23:29:40 $
 */

public abstract class ObjectMapper {
	private static final Logger logger = Logger.getLogger(ObjectMapper.class
			.getName());

	private static final String RIGHT_PARAN = ")";
	private static final String LEFT_PARAN_W_SPACE = " (";
	private static final String COMMA_WITH_SPACE = ", ";
	private static final String SPACE = " ";

	public static List<AccountDTO> convertAccountListToAccountDTO(List<Account> accLists){
		
		AccountDTO accDto = new AccountDTO();
		String accountNo = null;
		

		String bankName;
		List<AccountDTO> accountDTO = new ArrayList<AccountDTO>();
		int accListSize = accLists.size();
		for (int i = 0; i < accListSize; i++) {
			accountNo = accLists.get(i).getAccount_no();
			
			accountNo = ServiceLiveStringUtils.maskString(accountNo , 4, "X");			
	
			Long accountId = accLists.get(i).getAccount_id();
			bankName = accLists.get(i).getBank_name();
			if(bankName.length()>20){
				bankName = bankName.substring(0, 20);
			}
			accDto.setAccountNameNum(bankName + " - " +accountNo);
			accDto.setAccountId(accountId);
			accountDTO.add(accDto);
		}
		return accountDTO;
	}
	public static CriteriaMap buildCriteraMap(ServiceOrdersCriteria soCriteria,
			FilterCriteria filters, SortCriteria sorts, PagingCriteria paging) {
		CriteriaMap newMap = new CriteriaMap();
		newMap.put(OrderConstants.FILTER_CRITERIA_KEY, filters);
		newMap.put(OrderConstants.SORT_CRITERIA_KEY, sorts);
		newMap.put(OrderConstants.PAGING_CRITERIA_KEY, paging);

		OrderCriteria aOrderCriteria = new OrderCriteria();
		aOrderCriteria.setCompanyId(soCriteria.getCompanyId());
		aOrderCriteria.setRoleType(soCriteria.getRoleType());
		aOrderCriteria.setRoleId(soCriteria.getRoleId());
		Integer aArray[] = new Integer[1];
		aOrderCriteria.setStatusId(aArray);
		aOrderCriteria.setVendBuyerResId(soCriteria.getVendBuyerResId());
		newMap.put(OrderConstants.ORDER_CRITERIA_KEY, aOrderCriteria);
		return newMap;
	}

	public static Integer statusCode(String tabType) {
		Integer statusType = null;
		if (tabType.equals("Today")) {
			statusType = new Integer(OrderConstants.TODAY_STATUS);
		}
		else if (tabType.toUpperCase().equals(OrderConstants.TAB_DRAFT)) {
			statusType = new Integer(OrderConstants.DRAFT_STATUS);
		}
		else if (tabType.equals("Posted")) {
			statusType = new Integer(OrderConstants.ROUTED_STATUS);
		}
		else if (tabType.equals("Bid Requests") || tabType.equals("Bid")) {
			statusType = new Integer(OrderConstants.ROUTED_STATUS);
		}
		else if (tabType.toUpperCase().equals(OrderConstants.TAB_ACCEPTED)) {
			statusType = new Integer(OrderConstants.ACCEPTED_STATUS);
		}
		else if (tabType.toUpperCase().equals(OrderConstants.TAB_PROBLEM)) {
			statusType = new Integer(OrderConstants.PROBLEM_STATUS);
		}
		else if (tabType.toUpperCase().equals(OrderConstants.TAB_INACTIVE)) {
			statusType = new Integer(OrderConstants.INACTIVE_STATUS);
		}
		else if (tabType.equals("Received")) {
			statusType = new Integer(OrderConstants.ROUTED_STATUS);
		}
		else if (tabType.equals("Bid Requests")) {
			statusType = new Integer(OrderConstants.ROUTED_STATUS);
		}
		else if (tabType.equals("Bulletin Board")) {
			statusType = new Integer(OrderConstants.ROUTED_STATUS);
		}
		else if (tabType.equals("Inactive")) {
			statusType = new Integer(OrderConstants.INACTIVE_STATUS);
		}
		else if (tabType.equals("Search")) {
			statusType = new Integer(OrderConstants.SEARCH_STATUS);
		}
		else if (tabType.equals("Current")) {
			statusType = new Integer(OrderConstants.TODAY_STATUS);
		}
		else if (tabType.equals("History")) {
			statusType = new Integer(OrderConstants.INACTIVE_STATUS);
		}
		else if (tabType.equals("Saved")) {
			statusType = new Integer(OrderConstants.DRAFT_STATUS);
		}
		else if (OrderConstants.TAB_PENDING_CANCEL.equals(tabType.toUpperCase())) {
			statusType = new Integer(OrderConstants.PENDING_CANCEL_STATUS);
		}
		
		return statusType;

	}

	public static ServiceOrderNote convertNoteDTOToVO(ServiceOrderNoteDTO theDto) {
		ServiceOrderNote theNote = new ServiceOrderNote();
		theNote.setNote(theDto.getMessage());
		theNote.setSoId(theDto.getSoId());
		theNote.setSubject(theDto.getSubject());
		theNote.setCreatedByName(theDto.getModifiedBy());
		theNote.setCreatedDate(Calendar.getInstance().getTime());
		theNote.setModifiedBy(theDto.getModifiedBy());

		return theNote;
	}
	public static BuyerSOTemplateDTO convertServiceOrderToTemplateDto(ServiceOrderDTO serviceOrderDto){
		BuyerSOTemplateDTO templateDto = new BuyerSOTemplateDTO();
		
		SOContactDTO contactDto = serviceOrderDto.getBuyerContact();
		
		BuyerSOTemplateContactDTO buyerContact = new BuyerSOTemplateContactDTO();
		buyerContact.setCityStateZip(contactDto.getCityStateZip());
		buyerContact.setCompanyName(contactDto.getCompanyName());
	    buyerContact.setEmail(contactDto.getEmail());
	    buyerContact.setFax(contactDto.getFax());
	    buyerContact.setIndividualName(contactDto.getIndividualName());
	    buyerContact.setLocationtypeId(contactDto.getLocationtypeId());
	    buyerContact.setPhonePrimary(contactDto.getPhonePrimary());
	    buyerContact.setStreetAddress(contactDto.getStreetAddress());
	    buyerContact.setStreetAddress2(contactDto.getStreetAddress2());
	    buyerContact.setType(contactDto.getType());
	    
	    templateDto.setBuyerContact(buyerContact);
	    templateDto.setMainServiceCategory(serviceOrderDto.getMainServiceCategoryId().toString());
	    List<SOPartsDTO> partsList = serviceOrderDto.getPartsList();
	    List<BuyerSOTemplatePartDTO> templateParts = new ArrayList<BuyerSOTemplatePartDTO>();
	    
	    if(partsList != null){
	    	for(int i=0;i<partsList.size();i++){
	    		SOPartsDTO partsDto = partsList.get(i);
	    		BuyerSOTemplatePartDTO templatePartDto = new BuyerSOTemplatePartDTO();
	    		
	    		if(partsDto != null){
	    			SOContactDTO partsContact = partsDto.getContact();
	    			BuyerSOTemplateContactDTO contact = null;
	    			if(partsContact != null){
	    				contact = new BuyerSOTemplateContactDTO();
	    				
	    				contact.setCityStateZip(partsContact.getCityStateZip());
	    				contact.setCompanyName(partsContact.getCompanyName());
	    				contact.setEmail(partsContact.getEmail());
	    				contact.setFax(partsContact.getFax());
	    				contact.setIndividualName(partsContact.getIndividualName());
	    				contact.setLocationtypeId(partsContact.getLocationtypeId());
	    				contact.setPhonePrimary(partsContact.getPhonePrimary());
	    				contact.setStreetAddress(partsContact.getStreetAddress());
	    				contact.setStreetAddress2(partsContact.getStreetAddress2());
	    				contact.setType(partsContact.getType());
	    				
	    				templatePartDto.setContact(contact);
	    			}
	    			
	    			templatePartDto.setContact(contact);
	    			templatePartDto.setDescription(partsDto.getDescription());
	    			templatePartDto.setManufacturer(partsDto.getManufacturer());
	    			templatePartDto.setModelNumber(partsDto.getModelNumber());
	    			templatePartDto.setPickupInstructions(partsDto.getPickupInstructions());
	    			templatePartDto.setQty(partsDto.getQty());
	    			templatePartDto.setSize(partsDto.getSize());
	    			templatePartDto.setWeight(partsDto.getWeight());
	    		}	    		
	    		templateParts.add(templatePartDto);
	    	}
	    }   
	    templateDto.setParts(templateParts);	    
	    templateDto.setOverview(serviceOrderDto.getOverview());		
	    templateDto.setPartsSuppliedBy(Integer.valueOf(serviceOrderDto.getPartsSuppliedBy()));
	    templateDto.setSpecialInstructions(serviceOrderDto.getSpecialInstructions());
	    templateDto.setTerms(serviceOrderDto.getTermsAndConditions());
	    templateDto.setTitle(serviceOrderDto.getTitle());

		return templateDto;
	}
	
	public static ArrayList<SOWBrandingInfoDTO> convertBrandInfoVOToDTO(
			ArrayList<DocumentVO> list) {
		ArrayList<SOWBrandingInfoDTO> brandInfoDTOList = new ArrayList<SOWBrandingInfoDTO>();

		for (int i = 0; i < list.size(); i++) {
			SOWBrandingInfoDTO brandInfoDTO = new SOWBrandingInfoDTO();
			brandInfoDTO.setCompanyName(list.get(i).getTitle());
			brandInfoDTO.setLogoId(list.get(i).getFileName());
			brandInfoDTO.setDocumentId(list.get(i).getDocumentId());

			brandInfoDTOList.add(brandInfoDTO);

		}
		return brandInfoDTOList;
	}
	
	public static FinancialProfileVO convertFinancialProfileDTOToVO(FMFinancialProfileTabDTO financialProfileDTO){
		FinancialProfileVO financialProfileVO = new FinancialProfileVO();
		
		if(financialProfileDTO != null && financialProfileDTO.getAccount() != null) {
			financialProfileVO.getContact().setTitle(financialProfileDTO.getAccount().getTitle());
			financialProfileVO.getContact().setFirstName(financialProfileDTO.getAccount().getFirstName());
			financialProfileVO.getContact().setMi(financialProfileDTO.getAccount().getMiddleName());
			financialProfileVO.getContact().setLastName(financialProfileDTO.getAccount().getLastName());
			financialProfileVO.getContact().setEmail(financialProfileDTO.getAccount().getEmail());
			financialProfileVO.getContact().setSuffix(financialProfileDTO.getAccount().getSuffix());
		}
		
		financialProfileVO.setTaxPayerId(financialProfileDTO.getTaxpayerId());
		
		if(financialProfileDTO.getAccount()!=null && financialProfileDTO.getAccount().getPhones().size()>=1){
			financialProfileVO.getContact().setPhoneNo(financialProfileDTO.getAccount().getPhones().get(0).getPhone());
			financialProfileVO.getContact().setExt(financialProfileDTO.getAccount().getPhones().get(0).getExt());
		}
		
		if(financialProfileDTO.getBusinessForeignOwned() !=null) {
			financialProfileVO.setForeignOwned(new Integer(financialProfileDTO.getBusinessForeignOwned()));
		}
		
		if(financialProfileDTO.getBusinessForeignOwned() !=null && financialProfileDTO.getBusinessForeignOwned().equals("1")){
			if(financialProfileDTO.getPercentOwnedByForeignCompany()!=null && !financialProfileDTO.getPercentOwnedByForeignCompany().equals("")){
				financialProfileVO.setForeignOwnedPercentage(new Integer(financialProfileDTO.getPercentOwnedByForeignCompany()));
			}
			
		}

		
		return financialProfileVO;
		
	}
	public static FMFinancialProfileTabDTO convertFinancialProfileVOToDTO(FinancialProfileVO financialProfileVO){
		FMFinancialProfileTabDTO financialProfileTabDTO = new FMFinancialProfileTabDTO();
		if(financialProfileVO !=null){
			if(financialProfileVO.getForeignOwned()!= null){
				financialProfileTabDTO.setBusinessForeignOwned(financialProfileVO.getForeignOwned().toString());
			}
			if(financialProfileVO.getForeignOwnedPercentage()!= null){
				financialProfileTabDTO.setPercentOwnedByForeignCompany(financialProfileVO.getForeignOwnedPercentage().toString());
			}
			if(financialProfileVO.getContact().getTitle()!= null){
				financialProfileTabDTO.getAccount().setTitle(financialProfileVO.getContact().getTitle());
			}
			if(financialProfileVO.getContact().getFirstName()!= null){
				financialProfileTabDTO.getAccount().setFirstName(financialProfileVO.getContact().getFirstName());
			}
			if(financialProfileVO.getContact().getMi()!= null){
				financialProfileTabDTO.getAccount().setMiddleName(financialProfileVO.getContact().getMi());
			}
			if(financialProfileVO.getContact().getLastName()!= null){
				financialProfileTabDTO.getAccount().setLastName(financialProfileVO.getContact().getLastName());
			}
			if(financialProfileVO.getContact().getSuffix()!= null){
				financialProfileTabDTO.getAccount().setSuffix(financialProfileVO.getContact().getSuffix());
			}
			if(financialProfileVO.getContact().getEmail()!= null){
				financialProfileTabDTO.getAccount().setEmail(financialProfileVO.getContact().getEmail());
			}
			if(financialProfileVO.getTaxPayerId()!= null && financialProfileVO.getTaxPayerId().length()==9 ){
				financialProfileTabDTO.setTaxpayerId(financialProfileVO.getTaxPayerId());
				financialProfileTabDTO.setConfirmTaxpayerId(financialProfileVO.getTaxPayerId());
			}
			if(financialProfileVO.getContact().getPhoneNo() != null){
				List<SOWPhoneDTO> phones = new ArrayList<SOWPhoneDTO>();
				SOWPhoneDTO phoneNo ;
				if(financialProfileVO.getContact().getExt() != null){
					phoneNo = new SOWPhoneDTO(financialProfileVO.getContact().getPhoneNo(), financialProfileVO.getContact().getExt());
				}else{
					phoneNo = new SOWPhoneDTO(financialProfileVO.getContact().getPhoneNo(), "");
				}
					
				phones.add(phoneNo);
				financialProfileTabDTO.getAccount().setPhones(phones);
				}
			}
			if(financialProfileVO.getProviderMaxWithdrawalLimit() != null)
			{
				financialProfileTabDTO.setProviderMaxWithdrawalLimit(financialProfileVO.getProviderMaxWithdrawalLimit());
			}
			if(financialProfileVO.getProviderMaxWithdrawalNo() != null)
			{
				financialProfileTabDTO.setProviderMaxWithdrawalNo(financialProfileVO.getProviderMaxWithdrawalNo());
			}
		return financialProfileTabDTO;
	}
	
	
	
	public static Account convertFinancialAccountsDTOToVO(FMManageAccountsTabDTO manageAccountsTabDTO, Integer vendorId){
		Account account = new Account();
		account.setAccount_holder_name(manageAccountsTabDTO.getAccountHolder());
		account.setAccount_no(manageAccountsTabDTO.getAccountNumber());
		account.setAccount_type_id(new Long(manageAccountsTabDTO.getAccountType()));
		account.setRouting_no(manageAccountsTabDTO.getRoutingNumber());
		account.setOwner_entity_id(vendorId.longValue());
		account.setOwner_entity_type_id(manageAccountsTabDTO.getEntityTypeId().longValue());
		account.setCountry_id(OrderConstants.COUNTRY_ID_US.longValue());
		account.setAccount_status_id(OrderConstants.ACCOUNT_STATUS_ACTIVE.longValue());
		account.setBank_name(manageAccountsTabDTO.getFinancialInstitution());
		account.setAccount_descr(manageAccountsTabDTO.getAccountDescription());
		if(manageAccountsTabDTO.getOldAccountId() != null){
			account.setAccount_id(new Long(manageAccountsTabDTO.getOldAccountId()));
		}		
		return account;
	}
	public static Account convertEscheatFinancialAccountsDTOToVO(FMManageAccountsTabDTO manageAccountsTabDTO, Integer vendorId){
		Account account = new Account();
		account.setAccount_holder_name(manageAccountsTabDTO.getEscheatAccountHolder());
		account.setAccount_no(manageAccountsTabDTO.getEscheatAccountNumber());
		account.setAccount_type_id(OrderConstants.ESCHEATMENT_ACCOUNT_TYPE);
		account.setRouting_no(manageAccountsTabDTO.getEscheatRoutingNumber());
		account.setOwner_entity_id(vendorId.longValue());
		//entity type id remains same for the escheatment account.
		account.setOwner_entity_type_id(manageAccountsTabDTO.getEntityTypeId().longValue());
		account.setCountry_id(OrderConstants.COUNTRY_ID_US.longValue());
		account.setAccount_status_id(OrderConstants.ACCOUNT_STATUS_ACTIVE.longValue());
		account.setBank_name(manageAccountsTabDTO.getEscheatFinancialInstitution());
		account.setAccount_descr(manageAccountsTabDTO.getEscheatAccountDescription());
		if(manageAccountsTabDTO.getEscheatOldAccountId() != null){
			account.setAccount_id(new Long(manageAccountsTabDTO.getEscheatOldAccountId()));
		}		
		return account;
	}
	
	public static CreditCardVO convertCreditCardDTOToVO(FMManageAccountsTabDTO manageAccountsTabDTO, Integer vendorId){
		CreditCardVO creditVO = new CreditCardVO();
		if(manageAccountsTabDTO.getAccountType()!=null)
			creditVO.setAccountTypeId(new Integer(manageAccountsTabDTO.getAccountType()));
		else
			creditVO.setAccountTypeId(new Integer(-1));
		creditVO.setBillingAddress1(manageAccountsTabDTO.getBillingAddress1());
		creditVO.setBillingAddress2(manageAccountsTabDTO.getBillingAddress2());
		creditVO.setCardHolderName(manageAccountsTabDTO.getCardHolderName());
		creditVO.setBillingCity(manageAccountsTabDTO.getBillingCity());
		creditVO.setBillingState(manageAccountsTabDTO.getBillingState());		
		creditVO.setCardTypeId(manageAccountsTabDTO.getCardTypeId());
		creditVO.setCardNo(manageAccountsTabDTO.getTokenizeCardNumber());
		creditVO.setToken(manageAccountsTabDTO.getTokenizeCardNumber());
		creditVO.setEncCardNo(manageAccountsTabDTO.getEncCardNo());
		creditVO.setEntityId(manageAccountsTabDTO.getEntityId());
		creditVO.setEntityTypeId(manageAccountsTabDTO.getEntityTypeId());
		creditVO.setExpireDate(manageAccountsTabDTO.getExpirationYear()+manageAccountsTabDTO.getExpirationMonth());
		creditVO.setLocationTypeId(CreditCardConstants.BILLING_LOCATION_ID);
		creditVO.setTransactionAmount(manageAccountsTabDTO.getTransactionAmount());
		creditVO.setTransactionId(manageAccountsTabDTO.getTransactionId());
		creditVO.setAccountStatusId(OrderConstants.ACCOUNT_STATUS_ACTIVE);
		creditVO.setAccountTypeId(LedgerConstants.CC_ACCOUNT_TYPE);
		creditVO.setVendorId(manageAccountsTabDTO.getEntityId());
		creditVO.setCountryId(OrderConstants.COUNTRY_ID_US);
		creditVO.setZipcode(manageAccountsTabDTO.getBillingZip());
		if(manageAccountsTabDTO.getOldCardId() != null){
			creditVO.setCardId(new Long(manageAccountsTabDTO.getOldCardId()));
		}
		return creditVO;			
	}
	
	public static FMManageAccountsTabDTO convertCreditCardVOToDTO(CreditCardVO creditVO, Integer vendorId){
		FMManageAccountsTabDTO creditDTO = null;
		if(creditVO != null )
		{
			creditDTO = new FMManageAccountsTabDTO();
				if(creditVO.getCardId() != null){
					creditDTO.setCardId(creditVO.getCardId());
					creditDTO.setOldCardId(creditVO.getCardId().toString());
				}
				if(creditVO.getEntityId() != null){
					creditDTO.setEntityId(creditVO.getEntityId());
				}
				if(creditVO.getEncCardNo() != null){
					creditDTO.setEncCardNo(creditVO.getEncCardNo());
				}
				if(creditVO.getCardNo() != null ){
					if(creditVO.getCardNo().length() > 5){
						creditDTO.setCardNumber(ServiceLiveStringUtils.maskString(creditVO.getCardNo() , 4, "X"));
					}
					else{
						creditDTO.setCardNumber(ServiceLiveStringUtils.maskString(creditVO.getCardNo(), (creditVO.getCardNo().length()/2), "X"));
					}
				}
				if(creditVO.getAccountTypeId() != null){
					creditDTO.setCardAccountType(creditVO.getAccountTypeId().toString());
				}
				if(creditVO.getCardTypeId() != null){
					creditDTO.setCardTypeId(creditVO.getCardTypeId());
				}
				if(creditVO.getExpireDate() != null){
					creditDTO.setExpirationMonth(creditVO.getExpireDate().substring(2, 4));
				}
				if(creditVO.getExpireDate() != null){
					creditDTO.setExpirationYear(creditVO.getExpireDate().substring(0, 2));
				}
				if(creditVO.getCardHolderName() != null){
					creditDTO.setCardHolderName(creditVO.getCardHolderName());
				}
				if(creditVO.getBillingAddress1() != null){
					creditDTO.setBillingAddress1(creditVO.getBillingAddress1());
				}
				if(creditVO.getBillingAddress2() != null){
					creditDTO.setBillingAddress2(creditVO.getBillingAddress2());
				}
				if(creditVO.getBillingCity() != null){
					creditDTO.setBillingCity(creditVO.getBillingCity());
				}
				if(creditVO.getBillingState() != null){
					creditDTO.setBillingState(creditVO.getBillingState());
				}
				if(creditVO.getZipcode() != null){
					creditDTO.setBillingZip(creditVO.getZipcode());
				}
		}
		return creditDTO;
			
	}
	
	public static FMManageAccountsTabDTO convertFinancialAccountsVOToDTO(Account account){
		FMManageAccountsTabDTO manageAccountsTabDTO = new FMManageAccountsTabDTO();
		if(account != null && account.getAccount_id() != null){
			manageAccountsTabDTO.setAccountId(account.getAccount_id().toString());
			manageAccountsTabDTO.setOldAccountId(account.getAccount_id().toString());
		}
		if(account != null && account.getAccount_id() != null){
			manageAccountsTabDTO.setAccountHolder(account.getAccount_holder_name());

		}
		
		if(account != null && account.getAccount_descr() != null){
			manageAccountsTabDTO.setAccountDescription(account.getAccount_descr());
		}
		if(account != null && account.getBank_name() != null){
			manageAccountsTabDTO.setFinancialInstitution(account.getBank_name());
		}
		
		if(account != null ){
			if(account.isActive_ind())
			manageAccountsTabDTO.setActiveInd("Active");
			else
				manageAccountsTabDTO.setActiveInd("Inactive");	
		}
		
		if(account.isEnabled_ind())
		{
			manageAccountsTabDTO.setEnabledIndicator(true);
		}
		else
		{
				manageAccountsTabDTO.setEnabledIndicator(false);	
		}

		if(account != null && account.getAccount_type_id() != null){
			manageAccountsTabDTO.setAccountType(account.getAccount_type_id().toString());
		}
		if(account != null && account.getRouting_no() != null){
			manageAccountsTabDTO.setRoutingNumber(ServiceLiveStringUtils.maskString(account.getRouting_no() , 4, "X"));
			manageAccountsTabDTO.setConfirmRoutingNumber(ServiceLiveStringUtils.maskString(account.getRouting_no() , 4, "X"));
		}
		if(account != null && account.getAccount_no() != null){
			if(account.getAccount_no().length() > 5){
				manageAccountsTabDTO.setAccountNumber(ServiceLiveStringUtils.maskString(account.getAccount_no() , 4, "X"));
				manageAccountsTabDTO.setConfirmAccountNumber(ServiceLiveStringUtils.maskString(account.getAccount_no() , 4, "X"));
			}else{
				manageAccountsTabDTO.setAccountNumber(ServiceLiveStringUtils.maskString(account.getAccount_no() , (account.getAccount_no().length()/2), "X"));
				manageAccountsTabDTO.setConfirmAccountNumber(ServiceLiveStringUtils.maskString(account.getAccount_no() , (account.getAccount_no().length()/2), "X"));
			}
		}
		return manageAccountsTabDTO;
	}
	

	public static FMManageAccountsTabDTO convertAllFinancialAccountsVOToDTO(Account account,Account escheatAccount){
		FMManageAccountsTabDTO manageAccountsTabDTO = new FMManageAccountsTabDTO();
		if(account != null && account.getAccount_id() != null){
			manageAccountsTabDTO.setAccountId(account.getAccount_id().toString());
			manageAccountsTabDTO.setOldAccountId(account.getAccount_id().toString());
		}
		if(account != null && account.getAccount_id() != null){
			manageAccountsTabDTO.setAccountHolder(account.getAccount_holder_name());

		}
		
		if(account != null && account.getAccount_descr() != null){
			manageAccountsTabDTO.setAccountDescription(account.getAccount_descr());
		}
		if(account != null && account.getBank_name() != null){
			manageAccountsTabDTO.setFinancialInstitution(account.getBank_name());
		}
		
		if(account != null ){
			if(account.isActive_ind())
			manageAccountsTabDTO.setActiveInd("Active");
			else
				manageAccountsTabDTO.setActiveInd("Inactive");	
		}
		
		if(account.isEnabled_ind())
		{
			manageAccountsTabDTO.setEnabledIndicator(true);
		}
		else
		{
				manageAccountsTabDTO.setEnabledIndicator(false);	
		}

		if(account != null && account.getAccount_type_id() != null){
			manageAccountsTabDTO.setAccountType(account.getAccount_type_id().toString());
		}
		if(account != null && account.getRouting_no() != null){
			manageAccountsTabDTO.setRoutingNumber(ServiceLiveStringUtils.maskString(account.getRouting_no() , 4, "X"));
			manageAccountsTabDTO.setConfirmRoutingNumber(ServiceLiveStringUtils.maskString(account.getRouting_no() , 4, "X"));
		}
		if(account != null && account.getAccount_no() != null){
			if(account.getAccount_no().length() > 5){
				manageAccountsTabDTO.setAccountNumber(ServiceLiveStringUtils.maskString(account.getAccount_no() , 4, "X"));
				manageAccountsTabDTO.setConfirmAccountNumber(ServiceLiveStringUtils.maskString(account.getAccount_no() , 4, "X"));
			}else{
				manageAccountsTabDTO.setAccountNumber(ServiceLiveStringUtils.maskString(account.getAccount_no() , (account.getAccount_no().length()/2), "X"));
				manageAccountsTabDTO.setConfirmAccountNumber(ServiceLiveStringUtils.maskString(account.getAccount_no() , (account.getAccount_no().length()/2), "X"));
			}
		}
		
		// escheat account
		
		if(escheatAccount != null && escheatAccount.getAccount_id() != null){
			manageAccountsTabDTO.setEscheatAccountId(escheatAccount.getAccount_id().toString());
			manageAccountsTabDTO.setEscheatOldAccountId(escheatAccount.getAccount_id().toString());
		}
		if(escheatAccount != null && escheatAccount.getAccount_id() != null){
			manageAccountsTabDTO.setEscheatAccountHolder(escheatAccount.getAccount_holder_name());

		}
		
		if(escheatAccount != null && escheatAccount.getAccount_descr() != null){
			manageAccountsTabDTO.setEscheatAccountDescription(escheatAccount.getAccount_descr());
		}
		if(escheatAccount != null && escheatAccount.getBank_name() != null){
			manageAccountsTabDTO.setEscheatFinancialInstitution(escheatAccount.getBank_name());
		}
		
		if(escheatAccount != null ){
			if(escheatAccount.isActive_ind())
			manageAccountsTabDTO.setEscheatActiveInd("Active");
			else
				manageAccountsTabDTO.setEscheatActiveInd("Inactive");	
		}
		
		if(escheatAccount.isEnabled_ind())
		{
			manageAccountsTabDTO.setEscheatEnabledIndicator(true);
		}
		else
		{
				manageAccountsTabDTO.setEscheatEnabledIndicator(false);	
		}

		if(escheatAccount != null && escheatAccount.getAccount_type_id() != null){
			manageAccountsTabDTO.setEscheatAccountType(escheatAccount.getAccount_type_id().toString());
		}
		if(escheatAccount != null && escheatAccount.getRouting_no() != null){
			manageAccountsTabDTO.setEscheatRoutingNumber(ServiceLiveStringUtils.maskString(escheatAccount.getRouting_no() , 4, "X"));
			manageAccountsTabDTO.setEscheatConfirmRoutingNumber(ServiceLiveStringUtils.maskString(escheatAccount.getRouting_no() , 4, "X"));
		}
		if(escheatAccount != null && escheatAccount.getAccount_no() != null){
			if(escheatAccount.getAccount_no().length() > 5){
				manageAccountsTabDTO.setEscheatAccountNumber(ServiceLiveStringUtils.maskString(escheatAccount.getAccount_no() , 4, "X"));
				manageAccountsTabDTO.setEscheatConfirmAccountNumber(ServiceLiveStringUtils.maskString(escheatAccount.getAccount_no() , 4, "X"));
			}else{
				manageAccountsTabDTO.setEscheatAccountNumber(ServiceLiveStringUtils.maskString(escheatAccount.getAccount_no() , (escheatAccount.getAccount_no().length()/2), "X"));
				manageAccountsTabDTO.setEscheatConfirmAccountNumber(ServiceLiveStringUtils.maskString(escheatAccount.getAccount_no() , (escheatAccount.getAccount_no().length()/2), "X"));
			}
		}
		return manageAccountsTabDTO;
	}
	
	
	public static AccountHistoryVO convertOverviewHistoryDTOtoVO(FMOverviewHistoryTabDTO dto){
		AccountHistoryVO ahVO = new AccountHistoryVO();
		
		if (dto.getCalendarFromDate() != null ){
			java.sql.Date fromDate =  new java.sql.Date (dto.getCalendarFromDate().getTime());
			ahVO.setFromDate(fromDate);
		}
		if ( dto.getCalendarToDate() !=null){
			java.sql.Date toDate =  new java.sql.Date (dto.getCalendarToDate().getTime());
			ahVO.setToDate(toDate);
		}
		//The below attributes are REQUIRED.
		ahVO.setReturnCountLimit(OrderConstants.ACCOUNT_HISTORY_LIMIT_COUNT);
		ahVO.setEntityTypeId(dto.getEntityTypeId());
		ahVO.setEntityId(dto.getCompanyId());
		ahVO.setEntityType(dto.getRoleType());
		ahVO.setSlAdminInd(dto.isSlAdminInd());
		ahVO.setBuyerAdminInd(dto.isBuyerAdminInd());
		ahVO.setDateRangeCheck(dto.getSearchType());
		return ahVO;
	}
	
	
	public static List<ListAfterSearchVO> convertlistAfterSearchDTOtoVO(FMOverviewHistoryTabDTO dto){
		List<ListAfterSearchVO> listVO = new ArrayList<ListAfterSearchVO>();
		
		if (dto != null){
			
			for (int i=0; i < dto.getTransaction().size(); i++){
				ListAfterSearchVO listAfterVO = new ListAfterSearchVO();
				listAfterVO.setTransactionNumber(dto.getTransaction().get(i).getTransactionNumber());
				listAfterVO.setDate(dto.getTransaction().get(i).getDate());
				listAfterVO.setModifiedDate(dto.getTransaction().get(i).getModifiedDate());
				listAfterVO.setType(dto.getTransaction().get(i).getType());
				listAfterVO.setSoId(dto.getTransaction().get(i).getSoId());
				listAfterVO.setStatus(dto.getTransaction().get(i).getStatus());
				listAfterVO.setAmount(dto.getTransaction().get(i).getAmount());
				listAfterVO.setBalance(dto.getTransaction().get(i).getAvailableBalance());
				listVO.add(listAfterVO);
			}
		}
		return listVO;
	}
	
	
	public static List<TransactionDTO> convertOverviewHistoryVOtoDTO(List<AccountHistoryVO> acctHistVO){
		List<TransactionDTO> listDTO = new ArrayList<TransactionDTO>();
		
		int i;
		String status = OrderConstants.TRANSACTION_STATUS_PENDING;

		if (acctHistVO != null){
	
			for (i=0; i < acctHistVO.size(); i++){
				TransactionDTO overviewHistDTO = new TransactionDTO();
				AccountHistoryVO acctHistoryView = acctHistVO.get(i);
				
					overviewHistDTO.setTransactionNumber(acctHistoryView.getTransactionId());
		
				if (acctHistVO.get(i).getEntryDate() != null){
					String entryDate = "";
					entryDate = DateUtils.getFormatedDate(acctHistoryView.getEntryDate(), "MM/dd/yy hh:mm a");
					overviewHistDTO.setDate(entryDate);
				}
					
					
				if (acctHistVO.get(i).getModifiedDate()!= null){
					String modifiedDate = "";
					modifiedDate = DateUtils.getFormatedDate(acctHistoryView.getModifiedDate(), "MM/dd/yy hh:mm a");
					
					overviewHistDTO.setModifiedDate(modifiedDate);
				}
		
				//Type
					overviewHistDTO.setType(specifyCardType(acctHistoryView.getTransactionalType(),acctHistoryView.getBankName()));
					overviewHistDTO.setSoId(acctHistoryView.getSoId());
					overviewHistDTO.setAccountNumber(acctHistoryView.getAccountNumber());
					overviewHistDTO.setBankName(acctHistoryView.getBankName());
					
					if (acctHistoryView.getAchProcessId() != null)
					{

						if (acctHistoryView.getAchRejectId() != null && acctHistoryView.getAchRejectId() > 0 && acctHistoryView.getRejectReasonCode()!= null && acctHistoryView.getRejectReasonCode().startsWith("R", 0))
						{
							status = OrderConstants.TRANSACTION_STATUS_FAILED;
						}
						else if (acctHistoryView.getLedgerReconciledInd() == 1 )
						{
							status = OrderConstants.TRANSACTION_STATUS_COMPLETED;
						}
						else
						{
							status = OrderConstants.TRANSACTION_STATUS_PENDING;	
						}
					}
					else 
					{
						if (acctHistoryView.getLedgerReconciledInd() == 1 )
						{
							status = OrderConstants.TRANSACTION_STATUS_COMPLETED;
						}
						else
						{
							status = OrderConstants.TRANSACTION_STATUS_PENDING;
						}
					}

					overviewHistDTO.setStatus(status);
		
				if (acctHistVO.get(i).getTransAmount() != null){
					if (acctHistVO.get(i).getCredDebInd() != null){
						if (acctHistVO.get(i).getCredDebInd().equals(2)){
							overviewHistDTO.setAmount(new java.text.DecimalFormat("$0.00").format(acctHistVO.get(i).getTransAmount()));
						}else if(acctHistVO.get(i).getCredDebInd().equals(1)){
							overviewHistDTO.setAmount("(" + new java.text.DecimalFormat("$0.00").format(acctHistVO.get(i).getTransAmount()) + ")");
						}
					}
				}
				String availableBalance = acctHistVO.get(i).getAvailableBalance();
				if(null != availableBalance){
					overviewHistDTO.setAvailableBalance(availableBalance);
				}else{
					overviewHistDTO.setAvailableBalance(OrderConstants.AVAILABLE_BALANCE_NULL);
				}
				
				listDTO.add(overviewHistDTO);
			}
		}
		
		return listDTO;
	}
	
	private static String specifyCardType(String transactionalType, String bankName) {
		String transactionType = "";
		if ((transactionalType.indexOf("Amex") != -1  && transactionalType.indexOf("Deposit") != -1) ||
			(transactionalType.indexOf("Visa") != -1  && transactionalType.indexOf("Deposit") != -1) ||
			(transactionalType.indexOf("Sears") != -1  && transactionalType.indexOf("Deposit") != -1)){
			if(null!=bankName){ // Fix for SL-12506
			transactionType = bankName + " Deposit";
			}
			else {
				transactionType = transactionalType;
			}
		}else if((transactionalType.indexOf("Amex") != -1  && transactionalType.indexOf("Refund") != -1) ||
				 (transactionalType.indexOf("Visa") != -1  && transactionalType.indexOf("Refund") != -1) ||
				 (transactionalType.indexOf("Sears") != -1  && transactionalType.indexOf("Refund") != -1)){
			if(null!=bankName){
				transactionType = bankName + " Refund";
			}
			else {
				transactionType = transactionalType;
			}
		}else{
			transactionType = transactionalType;
		}
		return transactionType;
	}
	
	public static SOWBrandingInfoDTO convertLogoImageVOToDTO(DocumentVO documentVO){
		SOWBrandingInfoDTO brandInfoDTO = new SOWBrandingInfoDTO();
		brandInfoDTO.setDocumentId(documentVO.getDocumentId());
		brandInfoDTO.setCompanyName(documentVO.getTitle());
		brandInfoDTO.setBlobBytes(documentVO.getBlobBytes());
		brandInfoDTO.setLogoId(documentVO.getFileName());
		return brandInfoDTO;
		
		
	}
	
	public static SODocumentDTO convertDocumentVOToDTO(DocumentVO documentVO){
		
		SODocumentDTO documentDTO = new SODocumentDTO();
		documentDTO.setDocumentId(documentVO.getDocumentId());
	    documentDTO.setDocumentTitle(documentVO.getTitle());
		documentDTO.setDesc(documentVO.getDescription());
		documentDTO.setCategory(documentVO.getDocCategoryId());
		documentDTO.setUploadContentType(documentVO.getFormat());
		documentDTO.setName(documentVO.getFileName());
		documentDTO.setBlobBytes(documentVO.getBlobBytes());
		documentDTO.setUploadFileName(documentVO.getDocPath());
		Double documentSize = DocumentUtils.convertBytesToKiloBytes( documentVO.getDocSize().intValue(), true );
		documentDTO.setSizeDouble(documentSize);
		documentDTO.setSize(documentSize.intValue());
		documentDTO.setFormat(documentVO.getFormat());
		documentDTO.setCreatedDate((Date)documentVO.getCreatedDate());
		if(OrderConstants.PROVIDER_ROLEID == documentVO.getRoleId().intValue()){
			documentDTO.setRole(OrderConstants.ROLE_PROVIDER);
			String firstName=null;
			String lastName=null;
			if(null != documentVO.getContact()){
				firstName=documentVO.getContact().getFirstName();
				lastName=documentVO.getContact().getLastName();
			}
			
			StringBuilder uploadedBy = new StringBuilder();
			uploadedBy.append(firstName).append(" ").append(lastName);
			documentDTO.setUploadedBy(uploadedBy.toString());
			
		}
		else{
			documentDTO.setRole(OrderConstants.ROLE_BUYER);
		}
		return documentDTO;
		
	}
	
	public static SLDocumentDTO convertBuyerDocumentVOToDTO(DocumentVO documentVO){
		SLDocumentDTO documentDTO = new SLDocumentDTO();
		documentDTO.setDocumentId(documentVO.getDocumentId());
		documentDTO.setDesc(documentVO.getDescription());
		documentDTO.setCategory(documentVO.getDocCategoryId());
		documentDTO.setTitle(documentVO.getTitle());
		documentDTO.setUploadContentType(documentVO.getFormat());
		documentDTO.setName(documentVO.getFileName());
		documentDTO.setBlobBytes(documentVO.getBlobBytes());
		documentDTO.setUploadFileName(documentVO.getDocPath());
		Double documentSize = DocumentUtils.convertBytesToKiloBytes( documentVO.getDocSize().intValue(), true );
		documentDTO.setSizeDouble(documentSize);
		documentDTO.setSize(documentSize.intValue());
		//Setting buyer document category type as document/logo
		if(documentVO.getDocCategoryId() == Constants.BuyerAdmin.DOC_CATEGORY_ID){
			documentDTO.setDocumentType(Constants.BuyerAdmin.CATEGORY_TYPE_BUYER_DOC);
		}else if(documentVO.getDocCategoryId() == Constants.BuyerAdmin.LOGO_DOC_CATEGORY_ID){
			documentDTO.setDocumentType(Constants.BuyerAdmin.CATEGORY_TYPE_BUYER_LOGO);
		}
		return documentDTO;
		
	}
	
	
	
	public static ArrayList<ServiceOrderDTO> convertVOToDTO(
			List<ServiceOrderSearchResultsVO> list, Integer roleId) {
		ArrayList<ServiceOrderDTO> soDTOList = new ArrayList<ServiceOrderDTO>();
		NumberFormat formatter = new DecimalFormat("###0.00");
		
		// To add formatting to the currency
		ServiceOrderDTO soDTO = null;
		if (list == null)
			return soDTOList;
		for (ServiceOrderSearchResultsVO resultVO : list) {
			if (null != resultVO) {
				soDTO = new ServiceOrderDTO();
	
				SOContactDTO buyerContactDTO = new SOContactDTO();
				soDTO.setBuyerContact(buyerContactDTO);
				buyerContactDTO.setCompanyID(resultVO.getBuyerID().toString());
				buyerContactDTO.setCompanyName(resultVO.getBuyerCompanyName());
				buyerContactDTO.setIndividualName(resultVO.getBuyerFirstName() + " " + resultVO.getBuyerLastName());
				soDTO.setBuyerContact(buyerContactDTO);
				soDTO.setBuyerRoleId(resultVO.getBuyerRoleId());
				
				SOContactDTO providerContactDTO = new SOContactDTO();
				if (null != resultVO.getAcceptedResourceId()) {
					providerContactDTO.setIndividualID(resultVO.getAcceptedResourceId().toString());
					soDTO.setAcceptedResourceId(resultVO.getAcceptedResourceId());
				}
				StringBuffer provName = new StringBuffer();
	
				if (StringUtils.isNotEmpty(resultVO.getProviderFirstName())) {
					provName.append(resultVO.getProviderFirstName());
				}
				if (StringUtils.isNotEmpty(resultVO.getProviderLastName())) {
					if (StringUtils.isNotEmpty(resultVO.getProviderFirstName())) {
						provName.append(SPACE);
					}
					provName.append(resultVO.getProviderLastName());
				}
				if (provName.length() > 0) {
					providerContactDTO.setIndividualName(provName.toString());
				}
				if(null != resultVO.getResourceId()){ 
					soDTO.setResourceId(resultVO.getResourceId()); 
				}
				SOContactDTO locationPrimaryContactDTO = new SOContactDTO();
				soDTO.setLocationPrimaryContact(locationPrimaryContactDTO);
				StringBuffer locPriName = new StringBuffer();
	
				if (StringUtils.isNotEmpty(resultVO.getEndCustomerFirstName())) {
					locPriName.append(resultVO.getEndCustomerFirstName());
				}
				if (StringUtils.isNotEmpty(resultVO.getEndCustomerLastName())) {
					if (StringUtils.isNotEmpty(resultVO.getEndCustomerFirstName())) {
						locPriName.append(" ");
					}
					locPriName.append(resultVO.getEndCustomerLastName());
				}
				
				if (locPriName.length() > 0) {
					locationPrimaryContactDTO.setIndividualName(locPriName.toString());
				}
				
				locationPrimaryContactDTO.setStreetAddress(resultVO.getStreet1());
				locationPrimaryContactDTO.setStreetAddress2(resultVO.getStreet2());
				locationPrimaryContactDTO.setCityStateZip(
						ServiceLiveStringUtils.concatenateCityStateZip(
								resultVO.getCity(), resultVO.getStateCd(),
								resultVO.getZip(), resultVO.getZip4()));
				
				soDTO.setZip5(resultVO.getZip());
				soDTO.setCityStateZip(locationPrimaryContactDTO.getCityStateZip());
				soDTO.setBuyerName(resultVO.getBuyerFirstName() + " " + resultVO.getBuyerLastName());
				soDTO.setCity(resultVO.getCity());
				soDTO.setEntityID(resultVO.getBuyerID());
	
				//Set widget values after handling the special characters
				soDTO.setBuyerWidget(
						UIUtils.handleSpecialCharacters(
								getBuyerWidgetDisplayValue(roleId, resultVO.getSoStatus(), (resultVO.getBuyerCompanyName() != null ? resultVO.getBuyerCompanyName() : resultVO.getEndCustomerLastName() + ", " + resultVO.getEndCustomerFirstName()), resultVO.getBuyerID())));
				soDTO.setProviderWidget(
						UIUtils.handleSpecialCharacters(
								getProviderWidgetDisplayValue(roleId, resultVO.getSoStatus(), resultVO.getProviderFirstName(), resultVO.getProviderLastName(), resultVO.getAcceptedResourceId())));
				//Add end provider primary phone number
				if(!StringUtils.isEmpty(resultVO.getProviderPrimaryPhoneNumber())){
					String primaryPhone = getProviderPrimaryPhoneNumberWidgetDisplayValue(roleId, resultVO.getSoStatus(), resultVO.getProviderPrimaryPhoneNumber(),resultVO.getProviderMainPhoneNo());
					if(!StringUtils.isEmpty(primaryPhone)){
						if((roleId.intValue() == OrderConstants.BUYER_ROLEID || roleId.intValue() == OrderConstants.SIMPLE_BUYER_ROLEID)
						   &&
						   (resultVO.getSoStatus().intValue() == OrderConstants.ACCEPTED_STATUS || 
						    resultVO.getSoStatus().intValue() == OrderConstants.ACTIVE_STATUS   || 
						    resultVO.getSoStatus().intValue() == OrderConstants.CANCELLED_STATUS|| 
						    resultVO.getSoStatus().intValue() == OrderConstants.PROBLEM_STATUS  ||
						    resultVO.getSoStatus().intValue() == OrderConstants.CLOSED_STATUS   ||
						    resultVO.getSoStatus().intValue() == OrderConstants.PENDING_CANCEL_STATUS)){
							soDTO.setProviderPrimaryPhoneNumberWidget(primaryPhone+"(Main)");
							providerContactDTO.setPhoneWork(resultVO.getProviderPrimaryPhoneNumber());
						}else{
						   soDTO.setProviderPrimaryPhoneNumberWidget("Main "+primaryPhone);
						   providerContactDTO.setPhoneWork(resultVO.getProviderPrimaryPhoneNumber());
						}
					}
				}
				//Add end provider primary alt phone number
				if(!StringUtils.isEmpty(resultVO.getProviderAltPhoneNumber())){
					String altPhone = getProviderAlternatePhoneNumberWidgetDisplayValue(roleId, resultVO.getSoStatus(), resultVO.getProviderAltPhoneNumber(),resultVO.getProviderMobilePhoneNo());
					if(!StringUtils.isEmpty(altPhone)){
						if((roleId.intValue() == OrderConstants.BUYER_ROLEID || roleId.intValue() == OrderConstants.SIMPLE_BUYER_ROLEID)
								   &&
								   (resultVO.getSoStatus().intValue() == OrderConstants.ACCEPTED_STATUS || 
								    resultVO.getSoStatus().intValue() == OrderConstants.ACTIVE_STATUS   || 
								    resultVO.getSoStatus().intValue() == OrderConstants.CANCELLED_STATUS|| 
								    resultVO.getSoStatus().intValue() == OrderConstants.PROBLEM_STATUS  ||
								    resultVO.getSoStatus().intValue() == OrderConstants.CLOSED_STATUS   ||
								    resultVO.getSoStatus().intValue() == OrderConstants.PENDING_CANCEL_STATUS)){
									soDTO.setProviderAltPhoneNumberWidget(altPhone+"(Mobile)");
									providerContactDTO.setPhoneAlternate(resultVO.getProviderAltPhoneNumber());
								}else{
						            soDTO.setProviderAltPhoneNumberWidget("Pro "+altPhone);
						            providerContactDTO.setPhoneAlternate(resultVO.getProviderAltPhoneNumber());
								}
					}
				}
				soDTO.setProviderContact(providerContactDTO);
				soDTO.setEndCustomerWidget(
						UIUtils.handleSpecialCharacters(
								getEndCustomerWidgetDisplayValue(roleId, resultVO.getSoStatus(), resultVO.getEndCustomerFirstName(), resultVO.getEndCustomerLastName())));
				soDTO.setLocationWidget(
						UIUtils.handleSpecialCharacters(
								getLocationWidgetDisplayValue(roleId, resultVO.getSoStatus(), locationPrimaryContactDTO.getStreetAddress(), locationPrimaryContactDTO.getStreetAddress2(), locationPrimaryContactDTO.getCityStateZip())));
				soDTO.setTitleWidget(
						UIUtils.handleSpecialCharacters(
								resultVO.getSoTitle()));
				//Add firmLevel Details
				if (StringUtils.isNotBlank(resultVO.getFirmBusinessName())){
					soDTO.setFirmName(resultVO.getFirmBusinessName());
				}
				if (StringUtils.isNotBlank(resultVO.getFirmBusinessPhoneNumber())){
					soDTO.setFirmPhoneNumber(resultVO.getFirmBusinessPhoneNumber());
				}
				if (null != resultVO.getAcceptedVendorId()){
					soDTO.setAcceptedVendorId(resultVO.getAcceptedVendorId());
				}
				
				//Add end customer primary phone number
				if(!StringUtils.isEmpty(resultVO.getEndCustomerPrimaryPhoneNumber())){
					String endCustPhone = getEndCustomerPrimaryPhoneNumberWidgetDisplayValue(roleId, resultVO.getSoStatus(), resultVO.getEndCustomerPrimaryPhoneNumber());
					if(!StringUtils.isEmpty(endCustPhone)){
						soDTO.setEndCustomerPrimaryPhoneNumberWidget("Main "+endCustPhone);
					}
				}
				
				soDTO.setProviderName(resultVO.getProviderLastName() + ", " + resultVO.getProviderFirstName());
				soDTO.setId(resultVO.getSoId());
				
				Double spendLimit = resultVO.getSpendLimit();
				Double spendLimitParts = resultVO.getSpendLimitParts();
				Double cancelAmount=resultVO.getCancelAmount();
				if (null == spendLimit) {
					spendLimit = new Double (0);
				}
				if (null == spendLimitParts) {
					spendLimitParts = new Double (0);
				}
				if(null == cancelAmount)
				{
					cancelAmount =new Double (0);	
				}
				//soDTO.setSpendLimitLabor(formatter.format(spendLimit));
				//soDTO.setSpendLimitParts(formatter.format(spendLimitParts));
				soDTO.setSpendLimitLabor(formatCurrency(spendLimit));
				soDTO.setSpendLimitParts(formatCurrency(spendLimitParts));
				
				if(null!=resultVO.getBuyerEntryDetails() && null!=resultVO.getBuyerEntryDetails().getComments())
				{
				soDTO.setBuyerComments(resultVO.getBuyerEntryDetails().getComments());
				}
				if(null!=resultVO.getProviderEntryDetails() && null!=resultVO.getProviderEntryDetails().getComments())
				{
				soDTO.setProviderComments(resultVO.getProviderEntryDetails().getComments());
				}
				
				if(null!=resultVO.getBuyerEntryDetails() && null!=resultVO.getBuyerEntryDetails().getEntryDate())
				{
				DateFormat providerDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		        String buyerEntryDate = providerDateFormat.format(resultVO.getBuyerEntryDetails().getEntryDate());
		        soDTO.setBuyerEntryDate(buyerEntryDate);
				}
				if(null!=resultVO.getProviderEntryDetails() && null!=resultVO.getProviderEntryDetails().getEntryDate())
				{			
				DateFormat buyerDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		        String providerEntryDate = buyerDateFormat.format(resultVO.getProviderEntryDetails().getEntryDate());
				soDTO.setProviderEntryDate(providerEntryDate);

				}
		        
		        
		    	if(null!=resultVO.getBuyerEntryDetails() && null!=resultVO.getBuyerEntryDetails().getPrice())
		    	{
				// soDTO.setBuyerPrice(formatter.format(resultVO.getBuyerEntryDetails().getPrice()));
		    	soDTO.setBuyerPrice(formatCurrency(resultVO.getBuyerEntryDetails().getPrice()));
		    	}
		    	else
		    	{
		    		// soDTO.setBuyerPrice(formatter.format(new Double(0)));
		    		soDTO.setBuyerPrice(formatCurrency(new Double(0)));
		    		
		    	}
		    	
				if(null!=resultVO.getProviderEntryDetails() && null!=resultVO.getProviderEntryDetails().getPrice())
				{
				// soDTO.setProviderPrice(formatter.format(resultVO.getProviderEntryDetails().getPrice()));
				soDTO.setProviderPrice(formatCurrency(resultVO.getProviderEntryDetails().getPrice()));				
				}
				else
				{
					// soDTO.setProviderPrice(formatter.format(new Double(0)));
					soDTO.setProviderPrice(formatCurrency(new Double(0)));
					
				}
				
				soDTO.setState(resultVO.getStateCd());
				soDTO.setStatus(resultVO.getSoStatus());
				soDTO.setStatusString(resultVO.getSoStatusString());
				soDTO.setSubStatus(resultVO.getSoSubStatus());
				soDTO.setSubStatusString(resultVO.getSoSubStatusString());
				soDTO.setTitle(resultVO.getSoTitle());
				soDTO.setMessage(resultVO.getSoTitleDesc());
				soDTO.setZip(resultVO.getZip4());
				soDTO.setAssignmentType(resultVO.getAssignmentType());
				
				if (resultVO.getLocnClassId() != null && resultVO.getLocnClassId() == 1)   {
					soDTO.setCommercialLocation(true);
				} else {
					soDTO.setCommercialLocation(false);
				}
	
				soDTO.setEndCustomerName(resultVO.getEndCustomerFirstName() + " " + resultVO.getEndCustomerLastName());
				soDTO.setTimeToAppointment(getTimeToAppointment(resultVO.getAppointStartDate(), resultVO.getServiceTimeStart(), resultVO.getServiceLocationTimezone()));
				
				//JIRA : 7606. Calculate date based on Start date and Service start time. Appointment start time is converted into date before adding. 
				DateFormat sdf = new SimpleDateFormat("hh:mm a");
				long tt = 0;
				try {
				  String serviceTSStr = resultVO.getServiceTimeStart();
				  if (serviceTSStr != null)
				    tt = sdf.parse(serviceTSStr).getTime();
				} catch (Exception e) {
					e.printStackTrace();
				}				
				
				Timestamp appointTS = resultVO.getAppointStartDate();				
				if (appointTS != null)
				   soDTO.setTempSortDate(new Timestamp(appointTS.getTime() + tt ));			
				
				soDTO.setAgeOfOrder(getAgeOfOrder(resultVO.getRoutedDate()));
				
				// For Bulletin board , there could be more than 1 providers for each SO
				List<ProviderResultVO> availableProviders = resultVO.getAvailableProviders(); 
			
				
				/* 
				 *  Dummy code starts here 
				 *  
				 * //	Dummy provider list for front end - to continue development
				List<ProviderResultVO> availableProviders = new ArrayList(); 
				ProviderResultVO pvo = new ProviderResultVO();
				pvo.setProviderFirstName("Munishh");
				pvo.setProviderLastName("Joshi");
				pvo.setResourceId(19580);
				
				ProviderResultVO pvo1 = new ProviderResultVO();
				pvo1.setProviderFirstName("Munish1");
				pvo1.setProviderLastName("Joshi1");
				pvo1.setResourceId(19580);
				

				ProviderResultVO pvo2 = new ProviderResultVO();
				pvo2.setProviderFirstName("Munish2");
				pvo2.setProviderLastName("Joshi2");
				pvo2.setResourceId(19580);
				
				ProviderResultVO pvo3 = new ProviderResultVO();
				pvo3.setProviderFirstName("Munish3");
				pvo3.setProviderLastName("Joshi3");
				pvo3.setResourceId(19580);
				
				availableProviders.add(pvo);
				availableProviders.add(pvo1);
				availableProviders.add(pvo2);
				availableProviders.add(pvo3);
				
				// Dummy code ends here.
				 * 
				 * 
				 */
				
				if(availableProviders!=null){
					soDTO.setAvailableProviders(availableProviders);
				}
				
				soDTO.setServiceOrderDate(resultVO.getAppointStartDate());
				soDTO.setServiceOrderDateString(getServiceDate(resultVO.getAppointStartDate(), resultVO.getServiceTimeStart(), resultVO.getServiceLocationTimezone()));
				soDTO.setSystemTimezone(resultVO.getServiceLocationTimezone());
				
				soDTO.setServiceDateTypeId(resultVO.getServiceDateTypeId());
				soDTO.setServiceDate1(resultVO.getAppointStartDate());
				soDTO.setServiceDate2(resultVO.getAppointEndDate());
				//soDTO.setServiceTimeStart(resultVO.getServiceTimeStart());
				//soDTO.setServiceTimeEnd(resultVO.getServiceTimeEnd());
				
				
				soDTO.setRoutedResourceId(resultVO.getRoutedResourceId());
				if(resultVO.getRoutedResourceFirstName() != null && resultVO.getRoutedResourceLastName() != null){
					soDTO.setRoutedResourceName(resultVO.getRoutedResourceFirstName() + " " + resultVO.getRoutedResourceLastName());
				}
				soDTO.setProviderResponseId(resultVO.getProviderResponseId());
				soDTO.setSoDescription(resultVO.getSoTitleDesc());

				//set final price which is final parts + final labor
				Double finalLaborPrice = 0.0;
				Double finalPartsPrice = 0.0;
				Double finalAddonPrice = 0.0;
				Integer statusId =resultVO.getSoStatus();
				Double finalInvoicePartsPrice=0.0;
				if (resultVO.getFinalLaborPrice() != null){
					finalLaborPrice = resultVO.getFinalLaborPrice();
				}
				if (resultVO.getFinalPartsPrice() != null){
					finalPartsPrice = resultVO.getFinalPartsPrice();
				}
				if(resultVO.getAddonPrice() != null){
					finalAddonPrice = resultVO.getAddonPrice();
				}
				if (statusId == OrderConstants.CANCELLED_STATUS || statusId == OrderConstants.CLOSED_STATUS
						|| statusId == OrderConstants.COMPLETED_STATUS) {
					if ("3000".equals(resultVO.getBuyerID())) {
						if (resultVO.getInvoiceParts() != null && resultVO.getInvoiceParts().size() > 0) {
							List<ProviderInvoicePartsVO> providerInvoicePartsList = resultVO.getInvoiceParts();
							for (ProviderInvoicePartsVO providerInvoicePartsVO : providerInvoicePartsList) {
								if (null != providerInvoicePartsVO && providerInvoicePartsVO.getPartStatus() != null
										&& providerInvoicePartsVO.getPartStatus()
												.equalsIgnoreCase(Constants.PART_STATUS_INSTALLED)) {
									finalInvoicePartsPrice += providerInvoicePartsVO.getFinalPrice().doubleValue();
								}
							}
						}
					}
					double finalPrice = MoneyUtil.getRoundedMoney(
							finalLaborPrice + finalPartsPrice + finalAddonPrice + finalInvoicePartsPrice);
					soDTO.setTotalFinalPrice(finalPrice);
				}
				if(resultVO.getSpendLimit() != null){
					if(resultVO.getSpendLimitParts() == null){
						resultVO.setSpendLimitParts(0.00);
					}
					String spLimitTotal=formatter.format(resultVO.getSpendLimit().doubleValue() + resultVO.getSpendLimitParts().doubleValue());
					// String spLimitTotal=formatCurrencySymbol(resultVO.getSpendLimit().doubleValue() + resultVO.getSpendLimitParts().doubleValue());
					soDTO.setSpendLimitTotal(spLimitTotal);
					soDTO.setSpendLimitTotalCurrencyFormat(formatCurrencySymbol(resultVO.getSpendLimit().doubleValue() + resultVO.getSpendLimitParts().doubleValue()));
				}
				
				soDTO.setProvidersDeclined(resultVO.getRejectedCounts());
				soDTO.setProvidersConditionalAccept(resultVO.getCondCounts());
				soDTO.setProvidersSentTo(resultVO.getProviderCounts());
				soDTO.setResponseCount(0l);
				if (resultVO.getProviderCounts() != 0)
				{
					Double a = resultVO.getCondCounts().doubleValue();
					Double b = resultVO.getRejectedCounts().doubleValue();
					Double f = (a+b)/resultVO.getProviderCounts()* 10;
					soDTO.setResponseCount(Math.round(f));
					
				}
				if(resultVO.getClaimedByResource()!=null)
				soDTO.setClaimedByResource(resultVO.getClaimedByResource());
				else
			    soDTO.setClaimedByResource(OrderConstants.NOT_APPLICABLE);
				soDTO.setDistanceInMiles(resultVO.getDistanceInMiles());
				// added logic to load formatted values for widgets
				
				soDTO.setParentGroupId(resultVO.getParentGroupId());
				soDTO.setParentGroupTitle(resultVO.getParentGroupTitle());
				if (resultVO.getGroupCreatedDate() != null) {
					soDTO.setGroupCreatedDateString(DateUtils.getFormatedDate(resultVO.getGroupCreatedDate(), DateUtils.DISPLAY_DATE_FORMAT_WITH_TIMEZONE));
				}
				soDTO.setGroupSpendLimit(resultVO.getGroupSpendLimit());
				soDTO.setGroupSpendLimitLabor(resultVO.getGroupSpendLimitLabor());
				soDTO.setGroupSpendLimitParts(resultVO.getGroupSpendLimitParts());
				soDTO.setSortSOandGroupID(resultVO.getSortSOandGroupID());
				soDTO.setSoId(resultVO.getSoId());
				soDTO.setPriceType(resultVO.getPriceType());
				soDTO.setAssociatedIncidents(resultVO.getAssociatedIncidents());
				
				soDTO.setClaimable(resultVO.isClaimable());
				StringBuilder dispatchAddress = new StringBuilder();
				if(StringUtils.isNotBlank(resultVO.getResStreet1())){
					dispatchAddress.append(resultVO.getResStreet1());
					dispatchAddress.append(OrderConstants.SPACE);
				}
				if(StringUtils.isNotBlank(resultVO.getResStreet2())){
					dispatchAddress.append(resultVO.getResStreet2());
					dispatchAddress.append(OrderConstants.SPACE);
				}
				dispatchAddress.append(ServiceLiveStringUtils.concatenateCityStateZip(resultVO.getResCity(),resultVO.getResStateCd(),resultVO.getResZip(),StringUtils.EMPTY ));;
				soDTO.setResourceDispatchAddress(dispatchAddress.toString());

				// Bid Requests Tab needs these next 2 fields
				soDTO.setMainServiceCategory(resultVO.getPrimarySkillCategory());
				String skillsRequired = "";
				if(resultVO.getTasks() != null)
				{
					for(ServiceOrderTaskDetail task :resultVO.getTasks())
					{
						if(StringUtils.isNotEmpty(skillsRequired))
							skillsRequired += ",";
						
						skillsRequired += task.getServiceTypeDs();					
					}
				}
				soDTO.setSkillsRequired(skillsRequired);
				
				if(resultVO.getBidRangeMax() != null)
					soDTO.setHighBid(resultVO.getBidRangeMax().floatValue());
				
				if(resultVO.getBidRangeMin() != null)
					soDTO.setLowBid(resultVO.getBidRangeMin().floatValue());
				
				if(resultVO.getBidCount() != null)
					soDTO.setBids(resultVO.getBidCount().intValue());
				
				if(resultVO.getCurrentBid() != null)
					soDTO.setCurrentBidPrice(resultVO.getCurrentBid().floatValue());
				
				if(resultVO.getBidEarliestStartDate() != null)
				{
					soDTO.setBidEarliestStartDate(resultVO.getBidEarliestStartDate());
				}
				if(resultVO.getBidLatestEndDate() != null)
				{
					soDTO.setBidLatestEndDate(resultVO.getBidLatestEndDate());
				}
				
				soDTO.setBidExpirationDate(resultVO.getBidExpirationDate());
				
				//SL-19728 Non Funded Buyer
					soDTO.setNonFundedInd(resultVO.isNonFundedInd());
                   soDTO.setInvoicePartsInd(resultVO.getInvoicePartsInd());
				
				soDTOList.add(soDTO);
				
				if (resultVO.getPriceModel() != null) {
					soDTO.setPriceModel(resultVO.getPriceModel());
					if (soDTO.getPriceModel().equals(Constants.PriceModel.ZERO_PRICE_BID)){
						soDTO.setPriceModelType(1);
					} else {
						soDTO.setPriceModelType(2);
					}
				}
				
				if (resultVO.getBidCount() != null) {
					soDTO.setBids(resultVO.getBidCount());
				}
				if (resultVO.getBidRangeMin() != null) {
					soDTO.setLowBid(resultVO.getBidRangeMin().floatValue());
				}
				if (resultVO.getBidRangeMax() != null) {
					soDTO.setHighBid(resultVO.getBidRangeMax().floatValue());
				}
				if(resultVO.isSealedBidInd()){
					soDTO.setSealedBidInd(true);
			}
		}
		}
		return soDTOList;

	}
	
	/**
	 * Format the currency w/o symbol
	 * @param currencyValue
	 * @return
	 */
	private static String formatCurrency(Double currencyValue){
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getCurrencyInstance();
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setCurrencySymbol(""); // No Symbol please.
		formatter.setDecimalFormatSymbols(symbols);
		return formatter.format(currencyValue);
	}
	
	/**
	 * Format the currency w/ symbol
	 * @param currencyValue
	 * @return
	 */
	private static String formatCurrencySymbol(Double currencyValue){
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		return formatter.format(currencyValue);
	}
	
	private static String getServiceDate(Timestamp appointStart, String serviceTimeStart, String timeZone) {
		if(appointStart == null){
			return "No Data";
		}
		Calendar calAppointStart = TimeUtils.getDateTime(appointStart, serviceTimeStart, timeZone);
		
		if(calAppointStart == null){
			return "No Data";
		}
		DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		if (timeZone != null) {
			sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
		}
		String formatDate = sdf.format(calAppointStart.getTime());
		formatDate=formatDate+timeZone;
		return formatDate;
	}

	// Will return a positive value (abs) or message "Expired"
	private static String getTimeToAppointment(Timestamp appointStart, String serviceTimeStart, String timeZone) {
		if (logger.isDebugEnabled()) {
			logger.debug("-----------------TimeToAppointment-----------------");
			logger.debug("appointStart::" +appointStart +" serviceTimeStart::" +serviceTimeStart +" timeZone::" +timeZone);
		}
		
		if (appointStart == null) {
			return "No Data";
		}
		
		Calendar calAppointStart = TimeUtils.getDateTime(appointStart, serviceTimeStart, timeZone);
		if(calAppointStart == null){
			return "No Data";
		}
		
		Calendar calRightNow = null;
		if (timeZone != null) {
			calRightNow = Calendar.getInstance(TimeZone.getTimeZone(timeZone));		
		}
		else {
			calRightNow = Calendar.getInstance();			
		}
		
		
		
		if (calAppointStart.compareTo(calRightNow) <= 0)
			return "Current";

		//Code change done on 03-06-2008 to represent Time to Appointment in conditional format (CR 44651)
		//If less than One Hour away, display only minutes (e.g. "42m")
		//If less than One Day away but at least One Hour, display hours and minutes (e.g. "3h 12m")
		//If at least One Day away, display days and hours (e.g. "3d 2h")
		String timeToAppointment = null;
		long diffInMins = (calAppointStart.getTimeInMillis() - calRightNow.getTimeInMillis())  / 60 / 1000;
		long diffInHrs = (calAppointStart.getTimeInMillis() - calRightNow.getTimeInMillis())  / 60 / 60 / 1000;
		
		if (logger.isDebugEnabled()) {
			logger.debug("timeZone::" +timeZone +" calRightNow::" +calRightNow.getTime());		
			logger.debug("calAppointStart.compareTo(calRightNow)" +calAppointStart.compareTo(calRightNow));
			logger.debug("diffInHrs::" +diffInHrs);
		}		 
		
		if(diffInHrs<1){
			timeToAppointment = diffInMins +"m";
			if (logger.isDebugEnabled())
			  logger.debug("timeToAppointment - less than One Hour away::" +timeToAppointment);
		}
		else if(diffInHrs>=1 && diffInHrs<24){
			timeToAppointment = (diffInMins/60) +"h " +(diffInMins % 60) +"m";
			if (logger.isDebugEnabled())
			  logger.debug("timeToAppointment - less than One Day but at least One Hour away::" +timeToAppointment);
		}
		else{
			timeToAppointment = (diffInHrs/24) +"d " + (diffInHrs % 24) +"h";
			if (logger.isDebugEnabled())
			  logger.debug("timeToAppointment - at least One Day away::" +timeToAppointment);
		}
		return timeToAppointment;
	}

	private static String getAgeOfOrder(Timestamp routedDate) {
		logger.debug("-----------------AgeOfOrder-----------------");
		Calendar calRoutedDate = Calendar.getInstance();
		Calendar calRightNow = Calendar.getInstance();

		if (routedDate == null)
			return "No Data";
		calRoutedDate.setTime(routedDate);
		logger.debug("calRightNow.getTimeInMillis()" +calRightNow.getTimeInMillis() +":::;" +calRightNow.getTime());
		logger.debug("calRoutedDate.getTimeInMillis()" +calRoutedDate.getTimeInMillis() +"::::" +calRoutedDate.getTime());
		
		String ageOfOrder = null;
		long diffInMins = (calRightNow.getTimeInMillis() - calRoutedDate.getTimeInMillis())  / 60 / 1000;
		long diffInHrs = (calRightNow.getTimeInMillis() - calRoutedDate.getTimeInMillis())  / 60 / 60 / 1000;
		logger.debug("diffInHrs::" +diffInHrs);
		
		//Code change done on 03-06-2008 to represent Age of Order in conditional format (CR 44651)
		if(diffInHrs<1){
			ageOfOrder = diffInMins +"m";
			logger.debug("ageOfOrder - less than One Hour old::" +ageOfOrder);
		}
		else if(diffInHrs>=1 && diffInHrs<24){
			ageOfOrder = (diffInMins/60) +"h " +(diffInMins % 60) +"m";
			logger.debug("ageOfOrder - less than One Day but at least One Hour old::" +ageOfOrder);
		}
		else{
			ageOfOrder = (diffInHrs/24) +"d " + (diffInHrs % 24) +"h";
			logger.debug("ageOfOrder - at least One Day old::" +ageOfOrder);
		}
		
		return ageOfOrder;

	}

	public static ArrayList<SoChangeDetailsDTO> convertSoChangeDetailVoToDTO(
			ArrayList<SoChangeDetailVo> obj , Integer roleId, SOWorkflowControlsVO controlsVO, Integer vendorBuyerId) {
		String DATE_FORMAT = "MM/dd/yy HH:mm";
		ArrayList<SoChangeDetailsDTO> hddto = new ArrayList<SoChangeDetailsDTO>();
		SoChangeDetailsDTO singleDTO;
		SoChangeDetailVo singleVO;
		for (int i = 0; i < obj.size(); i++) {
			singleDTO = new SoChangeDetailsDTO();
			singleVO = obj.get(i);
			try {

				singleDTO.setActionDescription(singleVO.getActionDescription());
				
			  	// For providers in the description the no of routed providers is not to be shown
				 Integer actionId = singleVO.getActionId();
				if(roleId == 1 && (actionId!=null)){
					if (actionId == 3 || actionId == 28){
					singleDTO.setChgComment(OrderConstants.ORDER_IS_ROUTED);
					}//for auto close success
					else if(actionId == 253){
					singleDTO.setChgComment(OrderConstants.AUTOCLOSE_SUCCESS);
					}
					//for auto close failure
					else if(actionId == 254){
					singleDTO.setChgComment(OrderConstants.AUTOCLOSE_FAILURE);
					}//Setting original order restricted SOD details
					else if(actionId == 279 && null!= controlsVO){
						String log =singleVO.getChgComment();
						log = setATagInComment(log,controlsVO,roleId,vendorBuyerId);
						singleDTO.setChgComment(log);
					}else{
					singleDTO.setChgComment(singleVO.getChgComment());
				    } 
				}else{
					if(roleId == 3 &&actionId!=null && actionId == 279 && null!=controlsVO){
						String log =singleVO.getChgComment();
						log = setATagInComment(log,controlsVO,roleId,vendorBuyerId);
						singleDTO.setChgComment(log);
					}else{
					  singleDTO.setChgComment(singleVO.getChgComment());
					}
				} 
				
				if(StringUtils.isNotBlank(singleVO.getCreatedByName())){
					singleDTO.setCreatedByName(singleVO.getCreatedByName().trim());
				}
				singleDTO.setCreatedDate(getFormatedDate(singleVO
						.getCreatedDate(), DATE_FORMAT));
				if(OrderConstants.NEWCO_ADMIN.equals(singleVO.getRoleName())){
					singleDTO.setRoleName(OrderConstants.NEWCO_DISPLAY_SYSTEM);
				}else{
					singleDTO.setRoleName(singleVO.getRoleName());
				}
				singleDTO.setEntityId(singleVO.getEntityId());
			} catch (NullPointerException npe) {
				logger.error(npe.getMessage());
			} catch (Exception e) {
				logger.debug(e.getMessage());
			}
			hddto.add(singleDTO);
		}
		return hddto;
	}

	private static String setATagInComment(String log,SOWorkflowControlsVO controlsVO, Integer roleId, Integer vendorBuyerId) {
		String[] logArray=log.split("#");
		String Atag =null;
		if(roleId == 1){
			if(null!= vendorBuyerId && null!=controlsVO && null!= controlsVO.getOriginalSoId()){
				String originalSoId = controlsVO.getOriginalSoId();
				if(null!=controlsVO.getWarrantyProvider() &&  vendorBuyerId.equals(controlsVO.getWarrantyProvider())){
					Atag = "<a id='originalSODJ' href='soDetailsController.action?soId="+originalSoId+"' target='_blank' >"+originalSoId+"</a>";
				}else{
					Atag = "<a id='originalSODRestrictedJ' href='soDetailsWarrantyOrderSummary.action?soId="+originalSoId+"' target='_blank'>"+originalSoId+"</a>";
				}
			}
		}else if(roleId == 3 && null!=controlsVO && null!= controlsVO.getOriginalSoId()){
			String originalSoId = controlsVO.getOriginalSoId();
			Atag = "<a id='originalSODJ' href='soDetailsController.action?soId="+originalSoId+"' target='_blank'>"+originalSoId+"</a>";
		}
		log = logArray[0]+" # is "+ Atag;
		return log;
	}
	public static String getFormatedDate(Date inputDate, String reqFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(reqFormat);
		return sdf.format(inputDate);
	}

	
	public static RoutedProvider getResponseStatusVOFromResponseStatusDto(ResponseStatusDTO responseStatusDto)
	{
		RoutedProvider routedProvider = new RoutedProvider();
		
		MarketMakerProviderResponse mktMakerProvResp = new MarketMakerProviderResponse();
		mktMakerProvResp.setSoId(responseStatusDto.getSoId());
		mktMakerProvResp.setResourceId(responseStatusDto.getResourceId());
		mktMakerProvResp.setCallStatusId(responseStatusDto.getCallStatusId());
		mktMakerProvResp.setMktMakerComments(responseStatusDto.getMktMakerComments());
		mktMakerProvResp.setModifyingAdmin(responseStatusDto.getModifyingAdmin());
		mktMakerProvResp.setModifiedDateTime(CommonUtil.getCurrentTimeStamp());
		
		routedProvider.setMktMakerProvResponse(mktMakerProvResp);
		routedProvider.setResourceId(responseStatusDto.getResourceId());
		routedProvider.setSoId(responseStatusDto.getSoId());
		
		return routedProvider;	
	}	
	
	public static ResponseStatusTabDTO getResponseStatusTabDTOFromVO(
			List<RoutedProvider> responseStatusVOList, Integer wfStateId) {
		ArrayList<ResponseStatusDTO> dtoList = new ArrayList<ResponseStatusDTO>();
		ResponseStatusTabDTO resStatTabDto = new ResponseStatusTabDTO();
		Integer posted = new Integer(0);
		Integer conditionalAccepted = new Integer(0);
		Integer rejected = new Integer(0);
		boolean isOrderAccepted = false;
		if (null != responseStatusVOList && responseStatusVOList.size() > 0) {
			posted = responseStatusVOList.size();

			Iterator<RoutedProvider> voItr = responseStatusVOList.iterator();
			while (voItr.hasNext()) {
				ResponseStatusDTO responseStatusDto = new ResponseStatusDTO();
				RoutedProvider routedProvider = (RoutedProvider) voItr.next();
				responseStatusDto.setFirstName(routedProvider
						.getProviderContact().getFirstName());
				responseStatusDto.setLastName(routedProvider
						.getProviderContact().getLastName());
				responseStatusDto.setPhoneNumber(routedProvider.getProviderContact().getPhoneNo());
				setMarketMakerResponseFromVo(responseStatusDto, routedProvider);
				
				responseStatusDto.setPostedSOCount(routedProvider.getPostedSOCount());
				if (null != routedProvider.getConditionalChangeDate1()) {
					responseStatusDto.setConditionalChangeDate1(routedProvider
							.getConditionalChangeDate1().toString());
				}
				if (null != routedProvider.getConditionalChangeDate2()) {
					responseStatusDto.setConditionalChangeDate2(routedProvider
							.getConditionalChangeDate2().toString());
				}
				responseStatusDto.setConditionalStartTime(routedProvider
						.getConditionalStartTime());
				responseStatusDto.setConditionalEndTime(routedProvider
						.getConditionalEndTime());
				responseStatusDto.setConditionalExpirationDate(routedProvider
						.getConditionalExpirationDate());
				responseStatusDto.setConditionalExpirationTime(routedProvider
						.getConditionalExpirationTime());
				responseStatusDto.setServiceLocationTimeZone(routedProvider
						.getServiceLocationTimeZone());
				responseStatusDto.setConditionalSpendLimit(routedProvider
						.getConditionalSpendLimit());
				responseStatusDto.setResourceId(routedProvider.getResourceId());
				responseStatusDto.setVendorId(routedProvider.getVendorId());
				responseStatusDto.setResponseId(routedProvider
						.getProviderRespId());
				//Changed for Vender rejection code 
				responseStatusDto.setResponseReasonDesc(routedProvider.getProviderRespDescription());
				responseStatusDto.setResponseComment(routedProvider.getProviderRespComment());
				//Changed for Vender rejection code Ended. 
				
				responseStatusDto.setScore(routedProvider.getPerfScore()!= null?routedProvider.getPerfScore():0.00);
				responseStatusDto.setFirmScore(routedProvider.getFirmPerfScore()!= null?routedProvider.getFirmPerfScore():0.00);
				responseStatusDto.setRoutedDate(routedProvider.getCreatedDate());
				responseStatusDto.setTierId(routedProvider.getTierId());
				
				// Determine how many hours and minutes until the bid/offer expires
				Timestamp expirationTs = routedProvider.getConditionalExpirationDate();
					if (expirationTs != null) {
					Date now = new Date();
					
					// Add the conditional expiration time
					DateFormat dateOnlyDf = new SimpleDateFormat("yyyy-MM-dd");
					DateFormat dateTimeDf = new SimpleDateFormat("yyyy-MM-dd hh:mm a z");
					String expirationDateString = dateOnlyDf.format(expirationTs) + " " + routedProvider.getConditionalExpirationTime() + " " + routedProvider.getServiceLocationTimeZone();
					Date expirationDate = null;
					try {
						expirationDate = dateTimeDf.parse(expirationDateString);
					} catch (ParseException e) {
						expirationDate = null;
						logger.error("Error parsing expiration date.", e);
					}
					if (expirationDate != null && expirationDate.after(now)) {
						long diff = Math.abs(expirationDate.getTime() - now.getTime());
					long days = diff / (1000 * 60 * 60 * 24);
					long modMs = diff % (1000 * 60 * 60 * 24);
					long hours = modMs / (1000 * 60 * 60);
					modMs = modMs % (1000 * 60 * 60);
					long minutes = modMs / (1000 * 60);
					responseStatusDto.setExpiresInDays(days);
					responseStatusDto.setExpiresInHours(hours);
					responseStatusDto.setExpiresInMinutes(minutes);
					}else{
						responseStatusDto.setExpiresInDays(OrderConstants.LONG_ZERO);
						responseStatusDto.setExpiresInHours(OrderConstants.LONG_ZERO);
						responseStatusDto.setExpiresInMinutes(OrderConstants.LONG_ZERO);
				}	
				}	
				
				responseStatusDto.setPriceModel(routedProvider.getPriceModel());
				if (routedProvider.getPriceModel() != null && routedProvider.getPriceModel().equals(Constants.PriceModel.ZERO_PRICE_BID)) {
					responseStatusDto.setTotalLaborBid(routedProvider.getTotalLaborBid());
					responseStatusDto.setTotalHousBid(routedProvider.getTotalHoursBid());
					responseStatusDto.setPartsAndMaterialsBid(routedProvider.getPartsAndMaterialsBid());
				}
				
				if (routedProvider.getProviderRespId() != null) {
					if (routedProvider.getProviderRespId().equals(
							OrderConstants.CONDITIONAL_OFFER)) {
						if (routedProvider.getPriceModel() != null && routedProvider.getPriceModel().equals(Constants.PriceModel.ZERO_PRICE_BID)) {
							responseStatusDto.setResponseDesc(OrderConstants.BID_SUBMITTED);
						} else {
							responseStatusDto
								.setResponseDesc(OrderConstants.CONDITIONAL_OFFER_DESC);
						}
						conditionalAccepted = conditionalAccepted + 1;
					} else if (routedProvider.getProviderRespId().equals(
							OrderConstants.REJECTED) || routedProvider.getProviderRespId().equals(
									OrderConstants.RELEASED)) {
						responseStatusDto
								.setResponseDesc(OrderConstants.REJECTED_DESC);
						rejected = rejected + 1;
					} else if (routedProvider.getProviderRespId().equals(
							OrderConstants.ACCEPTED)) {
						isOrderAccepted = true;
						responseStatusDto
								.setResponseDesc(OrderConstants.ACCEPTED_DESC);
					}
				}
				responseStatusDto.setResponseReasonId(routedProvider
						.getProviderRespReasonId());
				if (routedProvider.getProviderRespReasonId() != null) {
					if (routedProvider.getProviderRespReasonId().equals(
							OrderConstants.RESCHEDULE_SERVICE_DATE)) {
						responseStatusDto
								.setResponseReasonDesc(OrderConstants.RESCHEDULE_SERVICE_DATE_DESC);
					} else if (routedProvider
							.getProviderRespReasonId()
							.equals(
									OrderConstants.RESCHEDULE_SERVICE_DATE_AND_SPEND_LIMIT)) {
						responseStatusDto
								.setResponseReasonDesc(OrderConstants.RESCHEDULE_SERVICE_DATE_AND_SPEND_LIMIT_DESC);
					} else if (routedProvider.getProviderRespReasonId().equals(
							OrderConstants.SPEND_LIMIT)) {
						responseStatusDto
								.setResponseReasonDesc(OrderConstants.SPEND_LIMIT_DESC);
					} 
					// SL-20015
					else if (routedProvider.getProviderRespReasonId().equals(
							OrderConstants.REJECTED_REASON1)) {
						responseStatusDto
								.setResponseReasonDesc(OrderConstants.REJECTED_REASON_DESC1);
					} else if (routedProvider.getProviderRespReasonId().equals(
							OrderConstants.REJECTED_REASON2)) {
						responseStatusDto
								.setResponseReasonDesc(OrderConstants.REJECTED_REASON_DESC2);
					} 
					// commented for SL-20042
					/*else if (routedProvider.getProviderRespReasonId().equals(
							OrderConstants.REJECTED_REASON3)) {
						responseStatusDto
								.setResponseReasonDesc(OrderConstants.REJECTED_REASON_DESC3);
					} */else if (routedProvider.getProviderRespReasonId().equals(
							OrderConstants.REJECTED_REASON4)) {
						responseStatusDto
								.setResponseReasonDesc(OrderConstants.REJECTED_REASON_DESC4);
					} else if (routedProvider.getProviderRespReasonId().equals(
							OrderConstants.REJECTED_REASON5)) {
						responseStatusDto
								.setResponseReasonDesc(OrderConstants.REJECTED_REASON_DESC5);
					} else if (routedProvider.getProviderRespReasonId().equals(
							OrderConstants.REJECTED_REASON6)) {
						responseStatusDto
								.setResponseReasonDesc(OrderConstants.REJECTED_REASON_DESC6);
					}
					else if (routedProvider.getProviderRespReasonId().equals(
							OrderConstants.REJECTED_REASON7)) {
						responseStatusDto
								.setResponseReasonDesc(OrderConstants.REJECTED_REASON_DESC7);
					} else if (routedProvider.getProviderRespReasonId().equals(
							OrderConstants.REJECTED_REASON8)) {
						responseStatusDto
								.setResponseReasonDesc(OrderConstants.REJECTED_REASON_DESC8);
					} else if (routedProvider.getProviderRespReasonId().equals(
							OrderConstants.REJECTED_REASON9)) {
						responseStatusDto
								.setResponseReasonDesc(OrderConstants.REJECTED_REASON_DESC9);
					}
					
					
				}
				
				responseStatusDto.setResponseComment(routedProvider.getProviderRespComment());
				responseStatusDto.setResponseDate(routedProvider.getProviderRespDate());
				
				SurveyRatingsVO starRatings = routedProvider.getProviderStarRating();
				if(starRatings != null) {
					//Set ServiceLiveRating and MyRating values properly, to map them to star images to be shown from frontend
					if (starRatings.getHistoricalRating() != null) {
						responseStatusDto.setServiceLiveRating(routedProvider
								.getProviderStarRating()
								.getHistoricalRating()); 
						//Set the slRatingNumber to show associated star image in jsp
						responseStatusDto.setSlRatingNumber(
								UIUtils.calculateScoreNumber(responseStatusDto.getServiceLiveRating()));
					}
					if (starRatings.getMyRating() != null) {
						responseStatusDto.setMyRating(routedProvider
								.getProviderStarRating()
								.getMyRating());
						//Set the myRatingNumber to show associated star image in jsp
						responseStatusDto.setMyRatingNumber(
								UIUtils.calculateScoreNumber(responseStatusDto.getMyRating()));
					}
					//Set ServiceLiveRatingCount and MyRatingsCount to check from frontend whether "NOT RATED" image
					//is to be shown instead of a star image.
					if (starRatings.getNumberOfRatingsReceived() != null) {
						responseStatusDto.setSlRatingsCount(routedProvider
								.getProviderStarRating()
								.getNumberOfRatingsReceived());
					}
					if (starRatings.getNumberOfRatingsGiven() != null) {
						responseStatusDto.setMyRatingsCount(routedProvider
								.getProviderStarRating()
								.getNumberOfRatingsGiven());
					}
				}
				
								
				// SPN Data
				List<ProviderSPNDTO> providerSPNList = new ArrayList<ProviderSPNDTO>();
				if(StringUtils.isNotBlank(routedProvider.getSpnName()))
				{
					ProviderSPNDTO bean = new ProviderSPNDTO();
					bean.setNetworkName(routedProvider.getSpnName());
					bean.setPerformanceLevelId(routedProvider.getPerformanceId());
					bean.setPerformanceLevelName(routedProvider.getPerformanceDesc());
					providerSPNList.add(bean);
				}
				responseStatusDto.setNetworkAndPerformanceLevelList(providerSPNList);
				if( routedProvider.getDistanceFromBuyer() != null){
					responseStatusDto.setDistanceFromBuyer(routedProvider.getDistanceFromBuyer());
				}else{
					responseStatusDto.setDistanceFromBuyer(null);
				}
				
				dtoList.add(responseStatusDto);

			}
		}

		resStatTabDto.setResponseStatusDtoList(dtoList);
		resStatTabDto.setPostedCnt(posted);
		
		//if order is accepted then setting bid/counter offer count to 0
		conditionalAccepted = isOrderAccepted?new Integer(0):conditionalAccepted;
		
		resStatTabDto.setConditionallyAcceptedCnt(conditionalAccepted);
		resStatTabDto.setRejectedCnt(rejected);
		resStatTabDto.setWfStateId(wfStateId);

		return resStatTabDto;
	}
	/**
	 * @param responseStatusDto
	 * @param routedProvider
	 */
	private static void setMarketMakerResponseFromVo(
			ResponseStatusDTO responseStatusDto, RoutedProvider routedProvider) {
		if (routedProvider.getMktMakerProvResponse() != null) {
			MarketMakerProviderResponse mktMakerProvRes = routedProvider.getMktMakerProvResponse();
			if(null!= mktMakerProvRes.getModifiedDateTime()){
				responseStatusDto.setModifiedDateTime(String.valueOf(mktMakerProvRes.getModifiedDateTime()));
			}
			responseStatusDto.setMktMakerComments(mktMakerProvRes.getMktMakerComments());
			responseStatusDto.setCallStatusId(mktMakerProvRes.getCallStatusId());
			responseStatusDto.setCallStatusDescription(mktMakerProvRes.getCallStatusDesc());
			String modifyingAdmin = mktMakerProvRes.getModifyingAdmin();
			if (StringUtils.isBlank(modifyingAdmin))
			{
				modifyingAdmin = "N/A" ;
			}
			
			responseStatusDto.setModifyingAdmin(modifyingAdmin);
		}
	}

	public static ServiceOrderDTO convertVOToDTOSummaryTab(ServiceOrder so, Integer roleId) {
		if (so == null)
			return null;

		ServiceOrderDTO dto = new ServiceOrderDTO();
		String statusDateFormat = "MMM d, yyyy hh:mm a";
		String serviceDateFormat = "MMM d, yyyy";
		String widgetDateFormat = "yyyy-MM-dd"; // Needed for dojo widget.  Bug in dojo 0.9 allows only this format.

		if(so.getLogoDocumentId() != null){
			dto.setLogodocumentId(so.getLogoDocumentId());
		}
		if(null!=so.getBuyerId())
			dto.setBuyerID(so.getBuyerId().toString());
		
		dto.setBuyerPendingCancelPrice(so.getBuyerPendingCancelPrice());
		dto.setProviderPendingCancelPrice(so.getProviderPendingCancelPrice());
		dto.setTaskLevelPriceInd(so.getBuyer().isPermitInd());
		// General Information - status dates
		if (so.getCreatedDate() != null)
			dto.getStatusAndDateList().add(
					new LabelValueBean("Created", DateUtils.getFormatedDate(so.getCreatedDate(), statusDateFormat)+ " (" + OrderConstants.SERVICELIVE_ZONE+")"));

		if (so.getRoutedDate() != null){
			if(roleId== OrderConstants.PROVIDER_ROLEID){
				dto.getStatusAndDateList().add(
						new LabelValueBean("Received", DateUtils.getFormatedDate(so.getRoutedDate(), statusDateFormat)+ " ("+ OrderConstants.SERVICELIVE_ZONE+")"));
			}else{
				dto.getStatusAndDateList().add(
						new LabelValueBean("Posted", DateUtils.getFormatedDate(so.getRoutedDate(), statusDateFormat)+ " ("+ OrderConstants.SERVICELIVE_ZONE+")"));
			}
		}
		
		if (so.getAcceptedDate() != null)
			dto.getStatusAndDateList().add(
					new LabelValueBean("Accepted", DateUtils.getFormatedDate(so.getAcceptedDate(), statusDateFormat)+ " ("+ OrderConstants.SERVICELIVE_ZONE+")"));
		if (so.getCancelledDate() != null)
			dto.getStatusAndDateList().add(
					new LabelValueBean("Cancel", DateUtils.getFormatedDate(so.getCancelledDate(), statusDateFormat)+ " ("+ OrderConstants.SERVICELIVE_ZONE+")"));
							
		if (so.getVoidedDate() != null)
			dto.getStatusAndDateList().add(
					new LabelValueBean("Voided", DateUtils.getFormatedDate(so.getVoidedDate(), statusDateFormat)+ "("+ OrderConstants.SERVICELIVE_ZONE+")"));
							
		if (so.getCompletedDate() != null)
			dto.getStatusAndDateList().add(
					new LabelValueBean("Completed", DateUtils.getFormatedDate(so.getCompletedDate(), statusDateFormat)+ " ("+ OrderConstants.SERVICELIVE_ZONE+")"));
							
		if (so.getClosedDate() != null)
			dto.getStatusAndDateList().add(
					new LabelValueBean("Closed", DateUtils.getFormatedDate(so.getClosedDate(), statusDateFormat)+ " ("+ OrderConstants.SERVICELIVE_ZONE+")"));
							
		//Set Parts Supplier information
		if(so.getPartsSupplier() != null) {
			dto.setPartsSupplier(so.getPartsSupplier());
		}
		
		if (so.getModifiedDate() != null) {
			java.sql.Timestamp modifiedDate = null;
			
			try {
				modifiedDate = Timestamp.valueOf(so.getModifiedDate());
			} catch(IllegalArgumentException iae) {
				logger.error(iae.getMessage());
			}
			
			String formattedModifiedDate = "";
			if(modifiedDate != null) {
				formattedModifiedDate = DateUtils.getFormatedDate(modifiedDate, statusDateFormat);
			} else {
				formattedModifiedDate = so.getModifiedDate();
			}
			
			dto.getStatusAndDateList().add(new LabelValueBean("Last Updated", formattedModifiedDate + " ("+ OrderConstants.SERVICELIVE_ZONE+")"));
		}

		// Appointment Dates
		String apptDate = "";
		if (so.getServiceDate1() != null)
			apptDate += DateUtils.getFormatedDate(so.getServiceDate1(), serviceDateFormat);
		
		if (so.getServiceDate2() != null)
			apptDate += " - " + DateUtils.getFormatedDate(so.getServiceDate2(), serviceDateFormat);
		dto.setAppointmentDates(apptDate);
		
		if(null != so.getServiceDatetimeSlots() && so.getServiceDatetimeSlots().size()>0 ){
			 for (ServiceDatetimeSlot serviceDatetimeSlot : so.getServiceDatetimeSlots()) {
				 String slotApptDate = "";
				 slotApptDate += DateUtils.getFormatedDate(serviceDatetimeSlot.getServiceStartDate(), serviceDateFormat);
				 slotApptDate = slotApptDate +" "+ serviceDatetimeSlot.getServiceStartTime() +" - "+serviceDatetimeSlot.getServiceEndTime()
						 +"("+serviceDatetimeSlot.getTimeZone()+")";
				 serviceDatetimeSlot.setAppointmentDateTime(slotApptDate);
				 
			 }
			 dto.setServiceDatetimeSlots(so.getServiceDatetimeSlots());
		}
		
		// set tax
		if (null != so.getSoPrice()) {
			dto.setPartsTaxPercent(so.getSoPrice().getPartsTax());
			dto.setLaborTaxPercent(so.getSoPrice().getLaborTax());
		
		}
		// Reschedule
		String rescheduleDate = null;
		if (so.getRescheduleServiceDate1() != null){
			rescheduleDate = "";
			rescheduleDate += DateUtils.getFormatedDate(so.getRescheduleServiceDate1(), serviceDateFormat);
		}
		if (so.getRescheduleServiceDate2() != null)
			rescheduleDate += " - " + DateUtils.getFormatedDate(so.getRescheduleServiceDate2(), serviceDateFormat);
		
		dto.setRescheduleDates(rescheduleDate);

		// Service Window
		if (so.getServiceTimeStart() != null){ 
			dto.setServiceWindow(so.getServiceTimeStart());
		}
		
		if(so.getServiceTimeEnd() != null){
			dto.setServiceWindow(dto.getServiceWindow()+ " - " + so.getServiceTimeEnd() + " (" + so.getServiceLocationTimeZone()+")");
		}
		else{
			if(dto.getServiceWindow()!=null){
				dto.setServiceWindow(dto.getServiceWindow()+ " (" + so.getServiceLocationTimeZone()+")");
			}
		}

		if(so.getProviderServiceConfirmInd() != null 
				&& so.getProviderServiceConfirmInd().intValue()	== 1){
			dto.setServiceWindowComment(OrderConstants.PROVIDER_CONFIRM_SERVICE_TIME_YES);
		}else{
			dto.setServiceWindowComment(OrderConstants.PROVIDER_CONFIRM_SERVICE_TIME_NO);
		}
		// Reschedule Service Window
		if (so.getRescheduleServiceTimeStart() != null){
			dto.setRescheduleServiceWindow(so.getRescheduleServiceTimeStart());
		}
		
		if(so.getRescheduleServiceTimeEnd() != null){
			dto.setRescheduleServiceWindow(dto.getRescheduleServiceWindow()+ " - " + so.getRescheduleServiceTimeEnd() + " (" + so.getServiceLocationTimeZone()+")");
		}
		else{
			dto.setRescheduleServiceWindow(dto.getRescheduleServiceWindow()+ " (" + so.getServiceLocationTimeZone()+")");
		}

		dto.setContinuationOrderID(so.getAssocSoId());
		dto.setContinuationReason(so.getAssocReasonDesc());

		dto.setId(so.getSoId());
		dto.setParentGroupId(so.getGroupId());
		dto.setStatus(so.getWfStateId());
		dto.setPrimaryStatus(so.getStatus());
		dto.setSubStatus(so.getWfSubStatusId());
		dto.setSubStatusString(so.getSubStatus());
		dto.setRoutedResources(so.getRoutedResources());
		dto.setAcceptedResource(so.getAcceptedResource());
		dto.setAcceptedResourceId(so.getAcceptedResourceId());
		dto.setAcceptedVendorId(so.getAcceptedVendorId());
		dto.setAcceptedDate(so.getAcceptedDate());
		dto.setServiceDate1(so.getServiceDate1());
		dto.setServiceDate2(so.getServiceDate2());
		dto.setServiceTimeStart(so.getServiceTimeStart());
		dto.setServiceTimeEnd(so.getServiceTimeEnd());
		dto.setServiceDateGMT1(so.getServiceDateGMT1());
		dto.setServiceDateGMT2(so.getServiceDateGMT2());
		dto.setServiceTimeStartGMT(so.getServiceTimeStartGMT());
		dto.setServiceTimeEndGMT(so.getServiceTimeEndGMT());
		dto.setPriceModel(so.getPriceModel());
		dto.setPriceType(so.getPriceType());
		dto.setServiceLocationTimeZone(so.getServiceLocationTimeZone());
		SoSchedule schedule = so.getSchedule();
		if(schedule!=null){
			String scheduleStatus = schedule.getScheduleStatus();
			if(schedule.getConfirmInd()!=null){
				int confirmInd = schedule.getConfirmInd();
				if(confirmInd == 1){
					//SL-18698 Added br to avoid use of tool tip for ETA
					scheduleStatus = scheduleStatus+"<br> (Confirmed)";
				}
				else{
					//SL-18698 Added br to avoid use of tool tip for ETA
					scheduleStatus = scheduleStatus+"<br> (Not confirmed)";
				}
			}
			dto.setScheduleStatus(scheduleStatus);
			//SL-18698 	R6.0 UAT Order Management  Display ETA in SOD
			if((null!=schedule.getEta()) && (StringUtils.isNotBlank(schedule.getEta())))
			{
			dto.setEta(schedule.getEta()+ " (" + so.getServiceLocationTimeZone()+")");
			}
		}
	
		if(so.getSealedBidInd()!=null)
		dto.setSealedBidInd(so.getSealedBidInd());
		if (so.getBuyerResource() != null) {
			dto.setBuyerRoleId(so.getBuyerResource().getCompanyRoleId());
		}
		
		dto.setTitle(so.getSowTitle());
		//Set title value to be shown in quick links Widget after handling special characters
		dto.setTitleWidget(
			UIUtils.handleSpecialCharacters(so.getSowTitle()));
		dto.setOverview(so.getSowDs());
		dto.setBuyersTerms(so.getBuyerTermsCond());
		dto.setSpecialInstructions(so.getProviderInstructions());
		// End of General Information

		// Start 'Scope of Work' Panel
		SOContactDTO contactDTO = new SOContactDTO();
		if (so.getServiceContact() != null && so.getServiceLocation() != null) { //Get the primary contact information
			// Address
			SoLocation loc = so.getServiceLocation();
			Contact cont = so.getServiceContact();
			contactDTO.init(cont, loc);
			contactDTO.setType(loc.getLocnClassDesc());
			
			// SL-21131: Code change starts
			
			//contactDTO.setEmail(so.getBuyerResource().getBuyerResContact().getEmail());
			
			// SL-21131: Code change ends
			
			dto.setLocationNotes(loc.getLocnNotes());
			
			//Set Location information
			String locationWidgetInfo = getLocationWidgetDisplayValue(roleId, dto.getStatus(), contactDTO.getStreetAddress(), contactDTO.getStreetAddress2(), 
												contactDTO.getCityStateZip());
			dto.setLocationWidget(
					UIUtils.handleSpecialCharacters(locationWidgetInfo));
			dto.setZip(loc.getZip());
			
			String cityLocationForDirections = loc.getCity() + "+" + loc.getState() + "+" + loc.getZip();
			if (so.isShareContactInd() != null && so.isShareContactInd() == true) {
				dto.setLocationForDirections(loc.getStreet1() + "+" + cityLocationForDirections);
			} else {
				dto.setLocationForDirections(cityLocationForDirections);
			}
		}

		//Set the phone number data properly into the contactDTO
		setContactInformation(so.getServiceContact(), so.getServiceContactAlt(), so.getServiceContactFax(), contactDTO);
		dto.setLocationContact(contactDTO);

		// 'Main Service Category' Section
		if (so.getSkill() != null){
			dto.setMainServiceCategory(so.getSkill().getNodeName());
			dto.setMainServiceCategoryId(so.getSkill().getNodeId());
		}
		dto.setJobInfo("Parts will be the responsibility of the provider. See the pricing section below for spending information.");//For Provider
		dto.setJobInfoOptional1("Parts will be provided by the buyer and may require pick-up by the service provider.  See parts section below for more information.");//For Buyer
		dto.setJobInfoOptional2("");//For "None" (nobody provides the part or parts not required)
		// Task list
		Double permitPrePaidPrice=0.0;
		Double permitFinalPrice=0.0;
		double permitTaskAddonPrice = 0;
		double soTaskMaxLabor=0.0;
		double soFinalMaxLabor=0.0;
		if (so.getTasks() != null) {
			SOTaskDTO taskDTO = null;
			for (ServiceOrderTask task : so.getTasks()) {
				taskDTO = new SOTaskDTO();
				taskDTO.setTitle(task.getTaskName());
				taskDTO.setCategory(task.getCategoryName());
				taskDTO.setSubCategory(task.getSubCategoryName());
				taskDTO.setSkill(task.getServiceType());
				taskDTO.setPrimaryTask(task.isPrimaryTask());
				taskDTO.setSkillId(task.getSkillNodeId());
				taskDTO.setComments(task.getTaskComments());
				taskDTO.setTaskType(task.getTaskType());
				taskDTO.setPrice(task.getPrice());
				taskDTO.setTaskId(task.getTaskId());
				taskDTO.setSku(task.getSku());
				taskDTO.setFinalPrice(task.getFinalPrice());
				taskDTO.setSellingPrice(task.getSellingPrice());
				taskDTO.setRetailPrice(task.getRetailPrice());
				taskDTO.setSequenceNumber(task.getSequenceNumber());
				taskDTO.setTaskStatus(task.getTaskStatus());
				taskDTO.setPermitTaskDesc(task.getPermitTypeDesc());
				taskDTO.setPermitType(task.getPermitTypeId());
				if(null!=task.getTaskType() && task.getTaskType()==1){
					if(null!= task.getTaskStatus() && task.getTaskStatus().equalsIgnoreCase(OrderConstants.ACTIVE_TASK)||task.getTaskStatus().equalsIgnoreCase(OrderConstants.COMPLETED_TASK)){
						dto.getPermitTaskList().add(taskDTO);
					}
					if(null!= task.getTaskStatus() && (task.getTaskStatus().equalsIgnoreCase(OrderConstants.ACTIVE_TASK)||task.getTaskStatus().equalsIgnoreCase(OrderConstants.COMPLETED_TASK)) && null!=task.getSellingPrice())
						permitPrePaidPrice=permitPrePaidPrice+task.getSellingPrice();
					if(null!= task.getTaskStatus() && (task.getTaskStatus().equalsIgnoreCase(OrderConstants.ACTIVE_TASK)||task.getTaskStatus().equalsIgnoreCase(OrderConstants.COMPLETED_TASK)) && null!= task.getSellingPrice() && null!= task.getFinalPrice() && task.getSellingPrice().doubleValue()>task.getFinalPrice().doubleValue()){
						permitFinalPrice = permitFinalPrice + task.getFinalPrice();
						
					}else if(null!= task.getSellingPrice() && null!= task.getFinalPrice() && task.getSellingPrice().doubleValue()<=task.getFinalPrice().doubleValue()){
						if(null!= task.getTaskStatus() && task.getTaskStatus().equalsIgnoreCase(OrderConstants.ACTIVE_TASK)||task.getTaskStatus().equalsIgnoreCase(OrderConstants.COMPLETED_TASK)){
							permitFinalPrice = permitFinalPrice + task.getSellingPrice();
							taskDTO.setCustCharge(task.getFinalPrice().doubleValue()-task.getSellingPrice().doubleValue());
						}
						permitTaskAddonPrice = permitTaskAddonPrice + task.getFinalPrice().doubleValue()-task.getSellingPrice().doubleValue();
					}
				}else if(null!= task.getTaskStatus() && task.getTaskStatus().equalsIgnoreCase(OrderConstants.ACTIVE_TASK)||task.getTaskStatus().equalsIgnoreCase(OrderConstants.COMPLETED_TASK)) {
					if(null!=task.getFinalPrice())
						soTaskMaxLabor = soTaskMaxLabor + task.getFinalPrice();
					dto.getNonPermitTaskList().add(taskDTO);
				}
				if(OrderConstants.TASK_LEVEL_PRICING.equals(dto.getPriceType())){
				if(task.getPriceHistoryList()!=null){
					if(task.getPriceHistoryList().size()>1){
						taskDTO.setPriceChangedInd(true);
					}else{
						taskDTO.setPriceChangedInd(false);
					}
					SOTaskPriceHistoryDTO taskPriceDTO  = null;
					for(SOTaskPriceHistoryVO taskPriceVO : task.getPriceHistoryList()){
						taskPriceDTO = new SOTaskPriceHistoryDTO();
						taskPriceDTO.setPrice(taskPriceVO.getPrice());
						Date date = taskPriceVO.getModifiedDate() ;
						SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
						String displayDate = df.format(date) ;
						taskPriceDTO.setModifiedDate(displayDate.substring(0, 10));
						taskPriceDTO.setModifiedBy(taskPriceVO.getModifiedBy());
						taskPriceDTO.setModifiedByName(taskPriceVO.getModifiedByName());
						taskPriceDTO.setPrice(taskPriceVO.getPrice());
						taskDTO.getPriceHistoryList().add(taskPriceDTO);
				}
				}
				
				}

				dto.getTaskList().add(taskDTO);
			}
		}
		dto.setPrePaidPermitPrice(permitPrePaidPrice);
		dto.setFinalPermitPrice(permitFinalPrice);
		dto.setPermitTaskAddonPrice(permitTaskAddonPrice);
		if(null!=so.getSpendLimitLabor()){
			soTaskMaxLabor=so.getSpendLimitLabor()-permitPrePaidPrice;
		}
		dto.setSoTaskMaxLabor(soTaskMaxLabor);
	
		
		// End 'Scope of Work' Panel
		// Start 'Contact Information' Panel
		SOContactDTO altContactDTO = new SOContactDTO();
		if (so.getEndUserContact() != null) { //Get Primary contact information
			Contact altCont = so.getEndUserContact();
			altContactDTO.init(altCont, null);
		}
		//Set the phone number data properly into the contactDTO
		setContactInformation(so.getEndUserContact(), so.getAltEndUserContact(), so.getAltEndUserFax(), altContactDTO);
		
		dto.setLocationAlternateContact(altContactDTO);

		SOContactDTO buyerContactDTO = new SOContactDTO();
		if (so.getBuyerAssociateContact() != null
				&& so.getBuyerAssociateLocation() != null) {
			Contact buyerCont = so.getBuyerAssociateContact();
			SoLocation buyerLoc = so.getBuyerAssociateLocation();
			buyerContactDTO.init(buyerCont, buyerLoc);

			// TODO get proper buyer resource for the next 2 lines.
		}
		//Set the Buyer information
		buyerContactDTO.setCompanyID(so.getBuyer()!=null?so.getBuyer().getBuyerId() + "":"");
		String companyName = so.getBuyer().getBusinessName();
		String buyerCompanyName=getBuyerWidgetDisplayValue(roleId, dto.getStatus(), (companyName != null ? companyName : so.getBuyer().getBuyerContact().getLastName() + ", " + so.getBuyer().getBuyerContact().getFirstName()), so.getBuyer().getBuyerId());
		buyerContactDTO.setCompanyName(companyName);
		dto.setBuyerWidget(
				UIUtils.handleSpecialCharacters(buyerCompanyName));
		dto.setBuyerContact(buyerContactDTO);

		/*
		//Set the Provider main phone and accepted resource's cell phone.
		if(dto.getAcceptedResourceCellNumber() != null) {
			String formattedNumber = dto.getAcceptedResourceCellNumber(); 
			dto.setAcceptedResourceCellNumber(SOPDFUtils.formatPhoneNumber(formattedNumber));
		}
		if(dto.getProviderMainPhoneNumber() != null) {
			String formattedNumber = dto.getProviderMainPhoneNumber(); 
			dto.setProviderMainPhoneNumber(SOPDFUtils.formatPhoneNumber(formattedNumber));
		}*/
		
		//Set the End Customer Contact information
		Contact endCustomerContact = so.getServiceContact();
		//  Get End Customer Primary Phone Number
		if(endCustomerContact!=null){
			String endCustomerPrimaryPhoneNumber =endCustomerContact.getPhoneNo();
			if(!StringUtils.isEmpty(endCustomerPrimaryPhoneNumber)){
				String endCustPhone = getEndCustomerPrimaryPhoneNumberWidgetDisplayValue(roleId, dto.getStatus(),endCustomerPrimaryPhoneNumber);
				if(!StringUtils.isEmpty(endCustPhone)){
					dto.setEndCustomerPrimaryPhoneNumberWidget("Main "+endCustPhone);
				}
			}
		}
		//End
		String firstName="";
		String lastName="";
		if(endCustomerContact != null)
		{
			firstName = endCustomerContact.getFirstName();
			lastName = endCustomerContact.getLastName();
		}
		dto.setEndCustomerName(getEndCustomerWidgetDisplayValue(
									roleId,
									dto.getStatus(), 
									firstName,
									lastName));
		dto.setEndCustomerWidget(
				UIUtils.handleSpecialCharacters(
						getEndCustomerWidgetDisplayValue(roleId, dto.getStatus(), firstName, lastName)));
		
		SOContactDTO bsContactDTO = new SOContactDTO();
		if (so.getBuyerSupportContact() != null) {
			Contact bsCont = so.getBuyerSupportContact();
			bsContactDTO.init(bsCont, null);

			// TODO get proper buyer resource for the next 2 lines.
			bsContactDTO.setIndividualID(so.getBuyer().getBuyerId() + "");
		}
			
		if(so.getAltBuyerResource() != null) { //Set the Buyer Resource's Support Contact information in SOD-Summary tab
			LocationVO primBuyerLocation = so.getAltBuyerResource().getBuyerResPrimaryLocation();
			Contact primBuyerContact = so.getAltBuyerResource().getBuyerResContact();
			
			/* Sl-16834 : If location is null, get the location of the buyer 
			 * admin and set*/
			if(null == primBuyerLocation){
				primBuyerLocation = so.getBuyer().getBuyerPrimaryLocation();
			}
			String streetAddress="", streetAddress2="", city="", state="", zip="", cityStateZip="";
			String phoneWork="", phoneMobile="", fax="", phoneHome="", pager="", email="";
			String  contactId="", resourceId="";
			
			if(primBuyerLocation !=null) {
				streetAddress = (primBuyerLocation.getStreet1()!=null ? primBuyerLocation.getStreet1():"") + " " +
									(primBuyerLocation.getAptNo()!=null ? primBuyerLocation.getAptNo():"");
				streetAddress2 = primBuyerLocation.getStreet2()!=null ? primBuyerLocation.getStreet2():"";
				city = primBuyerLocation.getCity()!=null ? primBuyerLocation.getCity():"";
				state = primBuyerLocation.getState()!=null ? primBuyerLocation.getState():"";
				zip = primBuyerLocation.getZip()!=null ? primBuyerLocation.getZip():"";
				cityStateZip = city + ", " + state + " " + zip;
			}
			
			bsContactDTO.setStreetAddress(streetAddress);
			bsContactDTO.setStreetAddress2(streetAddress2);
			bsContactDTO.setCityStateZip(cityStateZip);
			
			if(primBuyerContact !=null) {
				phoneWork = (primBuyerContact.getPhoneNo()!=null ? UIUtils.formatPhoneNumber(primBuyerContact.getPhoneNo()):"") +
								   		(primBuyerContact.getPhoneNoExt()!=null ? " Ext. "+primBuyerContact.getPhoneNoExt():"");
				phoneMobile = primBuyerContact.getCellNo()!=null ? primBuyerContact.getCellNo():"";
				fax = primBuyerContact.getFaxNo()!=null ? primBuyerContact.getFaxNo():"";
				email = primBuyerContact.getEmail()!=null ? primBuyerContact.getEmail():"";
				firstName = primBuyerContact.getFirstName()!=null ? primBuyerContact.getFirstName():"";
				lastName = primBuyerContact.getLastName()!=null ? primBuyerContact.getLastName():"";
				contactId = primBuyerContact.getContactId()!=null ? primBuyerContact.getContactId().toString():"";
				resourceId = primBuyerContact.getResourceId()!=null ? primBuyerContact.getResourceId().toString():"";
				phoneHome = primBuyerContact.getHomeNo()!=null ? primBuyerContact.getHomeNo():"";
				pager = primBuyerContact.getPagerText()!=null ? primBuyerContact.getPagerText():"";
			}
			
			bsContactDTO.setPhoneWork(phoneWork);
			bsContactDTO.setPhoneMobile(UIUtils.formatPhoneNumber(phoneMobile));
			bsContactDTO.setPhoneHome(UIUtils.formatPhoneNumber(phoneHome));
			bsContactDTO.setPager(UIUtils.formatPhoneNumber(pager));
			bsContactDTO.setFax(UIUtils.formatPhoneNumber(fax));
			bsContactDTO.setEmail(email);
			bsContactDTO.setIndividualName(firstName+" "+lastName);
			bsContactDTO.setIndividualID(resourceId);
			bsContactDTO.setCompanyID(so.getBuyer()!=null?so.getBuyer().getBuyerId() + "" : "");
			
		}
		dto.setBuyerSupportContact(bsContactDTO);

		SOContactDTO providerContactDTO = new SOContactDTO();
		if (so.getVendorResourceContact() != null
				&& so.getVendorResourceLocation() != null) {
			SoLocation loc = so.getVendorResourceLocation();
			Contact cont = so.getVendorResourceContact();
			providerContactDTO.init(cont, loc);
			
			//Set the contact information
			setContactInformation(cont, null, so.getVendorResourceContactFax(), providerContactDTO);
		}

		//Set Provider information
		Contact vendorResourceContact = so.getVendorResourceContact();
		String providerName="";
		boolean vendorResourceExists = false;
		if(vendorResourceContact != null) {
			providerName = getProviderWidgetDisplayValue(roleId, dto.getStatus(), vendorResourceContact.getFirstName(), 
								vendorResourceContact.getLastName(), so.getAcceptedResourceId());
			vendorResourceExists = true;
		} else {
			providerName = getProviderWidgetDisplayValue(roleId, dto.getStatus(), null, 
								null, so.getAcceptedResourceId());
		}
		providerContactDTO.setIndividualName(providerName);
		dto.setProviderWidget(
				UIUtils.handleSpecialCharacters(providerName));
		providerContactDTO.setIndividualID(so.getAcceptedResourceId() != null ? so
											.getAcceptedResourceId().toString() : "");

		providerContactDTO.setCompanyName(so.getAcceptedVendorName());
		providerContactDTO.setCompanyID(so.getAcceptedVendorId() != null ? so
											.getAcceptedVendorId().toString() : "");

		dto.setBuyerID(Integer.toString(so.getBuyer().getBuyerId()));
		if(vendorResourceExists) {
			String providerMainPhoneNumber = so.getServiceproviderContactOnQuickLinks().getPhoneNo();
			String providerAlternatePhoneNumber = so.getServiceproviderContactOnQuickLinks().getAcceptedRsrcphoneno();
			String providerMobPhoneNumber = so.getServiceproviderContactOnQuickLinks().getAcceptedRsrcmobno();
			if(!StringUtils.isEmpty(providerMainPhoneNumber)) {
				String providerMainPhone = getProviderPrimaryPhoneNumberWidgetDisplayValue(roleId, dto.getStatus(),providerMainPhoneNumber,providerAlternatePhoneNumber);
				if(!StringUtils.isEmpty(providerMainPhone)){
					if((roleId.intValue() == OrderConstants.BUYER_ROLEID || roleId.intValue() == OrderConstants.SIMPLE_BUYER_ROLEID)
							   &&
							   ( dto.getStatus().intValue() == OrderConstants.ACCEPTED_STATUS || 
								 dto.getStatus().intValue() == OrderConstants.ACTIVE_STATUS   || 
								 dto.getStatus().intValue() == OrderConstants.CANCELLED_STATUS|| 
								 dto.getStatus().intValue() == OrderConstants.PROBLEM_STATUS  ||
								 dto.getStatus().intValue() == OrderConstants.CLOSED_STATUS   ||
								 dto.getStatus().intValue() == OrderConstants.PENDING_CANCEL_STATUS)){
						         dto.setProviderMainPhoneNumber(providerMainPhone+"(Main)");
						         providerContactDTO.setPhoneWork(SOPDFUtils.formatPhoneNumber(providerMainPhoneNumber));
							}else{
					             dto.setProviderMainPhoneNumber("Main "+providerMainPhone);
					             providerContactDTO.setPhoneWork(SOPDFUtils.formatPhoneNumber(providerMainPhoneNumber));
							}
				}
			}
			if(!StringUtils.isEmpty(providerAlternatePhoneNumber)) {
				String providerAltPhone = getProviderAlternatePhoneNumberWidgetDisplayValue(roleId, dto.getStatus(),providerAlternatePhoneNumber,providerMobPhoneNumber);
				if(!StringUtils.isEmpty(providerAltPhone)){
					if((roleId.intValue() == OrderConstants.BUYER_ROLEID || roleId.intValue() == OrderConstants.SIMPLE_BUYER_ROLEID)
							   &&
							   ( dto.getStatus().intValue() == OrderConstants.ACCEPTED_STATUS || 
								 dto.getStatus().intValue() == OrderConstants.ACTIVE_STATUS   || 
								 dto.getStatus().intValue() == OrderConstants.CANCELLED_STATUS|| 
								 dto.getStatus().intValue() == OrderConstants.PROBLEM_STATUS  ||
								 dto.getStatus().intValue() == OrderConstants.CLOSED_STATUS   ||
								 dto.getStatus().intValue() == OrderConstants.PENDING_CANCEL_STATUS)){
						         dto.setProviderAlternatePhoneNumber(providerAltPhone+"(Mobile)");
						         providerContactDTO.setPhoneAlternate(SOPDFUtils.formatPhoneNumber(providerAlternatePhoneNumber));
							}else{
					             dto.setProviderAlternatePhoneNumber("Pro "+providerAltPhone);
					             providerContactDTO.setPhoneAlternate(SOPDFUtils.formatPhoneNumber(providerAlternatePhoneNumber));
				}
			  }
			}
			if(!StringUtils.isEmpty(providerMobPhoneNumber)) {
					providerContactDTO.setPhoneMobile(SOPDFUtils.formatPhoneNumber(providerMobPhoneNumber));
			}
		}
		dto.setProviderContact(providerContactDTO);
		    
		// End 'Contact Information' Panel

		// Start Additional Info Tab --> Custom References Panel
		if(so.getCustomRefs() != null 
				&& so.getCustomRefs().size() > 0){
			ArrayList<SOWSelBuyerRefDTO> custRefDtoList = new ArrayList<SOWSelBuyerRefDTO>();
			for(int i=0;i<so.getCustomRefs().size();i++){
				ServiceOrderCustomRefVO custRefVo = so.getCustomRefs().get(i);
				SOWSelBuyerRefDTO buyerRefDto = new SOWSelBuyerRefDTO();
				buyerRefDto.setRefType(custRefVo.getRefType().toUpperCase());
				buyerRefDto.setRefValue(custRefVo.getRefValue());
				buyerRefDto.setPrivateInd(custRefVo.isPrivateInd());
				buyerRefDto.setEditable(custRefVo.getEditable());
				//Priority 5B changes
				buyerRefDto.setRequiredInd(custRefVo.getRequiredInd());
				custRefDtoList.add(buyerRefDto);	
			}
			dto.setSelByerRefDTO(custRefDtoList);
		}		
		// End Additional Info Tab --> Custom References Panel
		
		// Start 'Parts' Panel
		if(so.getPartsSupplier() != null){
			if(OrderConstants.SOW_SOW_BUYER_PROVIDES_PART.equals(String.valueOf(so.getPartsSupplier()))) {
				if (so.getParts() != null) {
					SOPartsDTO partDTO = null;
					String title = null;
					for (Part part : so.getParts()) {
						partDTO = new SOPartsDTO();
						title = "";
						partDTO.setPartId(part.getPartId());
						partDTO.setReferencePartId(part.getReferencePartId());
						if(part.getShipDate()!=null)
						{
							partDTO.setShipDate(DateUtils.getFormatedDate(part.getShipDate(), serviceDateFormat));
							partDTO.setShipDateFormatted(DateUtils.getFormatedDate(part.getShipDate(), widgetDateFormat));
						}
						else
						{					
							partDTO.setShipDate("");
							partDTO.setShipDateFormatted("");
						}				
						if (part.getManufacturer() != null)
							title += part.getManufacturer();
						if (part.getModelNumber() != null)
							title += " " + part.getModelNumber();
						partDTO.setTitle(title);
						partDTO.setManufacturer(part.getManufacturer());
						partDTO.setModelNumber(part.getModelNumber());
						partDTO.setSerialNumber(part.getSerialNumber());
						partDTO.setQty(part.getQuantity());
						String size = "";
						if (part.getLength() != null)
							size += part.getLength();
						if (part.getWidth() != null)
							size += " x " + part.getWidth();
						if (part.getHeight() != null)
							size += " x " + part.getHeight();
		
						partDTO.setSize(size);
						partDTO.setWeight(part.getWeight());
						partDTO.setDescription(part.getPartDs());
		
						// shipping
						if (part.getShippingCarrier() != null) {
							partDTO.setShippingCarrierId(part.getShippingCarrier()
									.getCarrierId());
							partDTO.setShippingCarrier(part.getShippingCarrier()
									.getCarrierName());
							partDTO.setShippingTrackingNumber(part.getShippingCarrier()
									.getTrackingNumber());
						}
						if (part.getReturnCarrier() != null) {
							partDTO.setCoreReturnCarrierId(part.getReturnCarrier()
									.getCarrierId());
							partDTO.setCoreReturnCarrier(part.getReturnCarrier()
									.getCarrierName());
							partDTO.setCoreReturnTrackingNumber(part.getReturnCarrier()
									.getTrackingNumber());
						}
						SOContactDTO pickupContactDTO = new SOContactDTO();
						// Pickup location
						if (part.getPickupContact() != null
								&& part.getPickupLocation() != null) {
							// Address
							SoLocation loc = part.getPickupLocation();
							Contact cont = part.getPickupContact();
							pickupContactDTO.init(cont, loc);
						}
						partDTO.setContact(pickupContactDTO);
		
						dto.getPartsList().add(partDTO);
					}
					if(dto.getPartsList()!=null){
						dto.setPartsCount(new Integer(dto.getPartsList().size()).toString());
					}
				}
			}else{
				dto.setPartsCount("0");
			}
		}
		// End 'Parts' Panel

		// Start 'Service Order Pricing' Panel
		if (so.getSpendLimitLabor() != null)
			dto.setLaborSpendLimit(so.getSpendLimitLabor());

		if (so.getSpendLimitParts() != null)
			dto.setPartsSpendLimit(so.getSpendLimitParts());

		Double total = 0.0;
		if (so.getSpendLimitLabor() != null)
			total += so.getSpendLimitLabor();
		if (so.getSpendLimitParts() != null)
			total += so.getSpendLimitParts();

		// next 3 lines for provider panel
		dto.setPricingType("Hourly");
		dto.setRate("$4.50/hr");
		dto.setRateType("Provider Selected");

		dto.setTotalSpendLimit(total);
		
		if(so.getPostingFee() != null){
			dto.setPostingFee(so.getPostingFee());
		}
		/* Labor and Final Price calculation*/
		
		Double wfLaborPrice = 0.0;
		Double wfPartsPrice = 0.0;
		if(so.getSoWrkFlowControls() !=null && null!=so.getSoWrkFlowControls().getFinalPriceLabor()){
			 wfLaborPrice = so.getSoWrkFlowControls().getFinalPriceLabor();
		}
		if(so.getSoWrkFlowControls() !=null && null!=so.getSoWrkFlowControls().getFinalPriceParts()){
			wfPartsPrice = so.getSoWrkFlowControls().getFinalPriceParts();
		}

		dto.setFinalLaborPriceNew(wfLaborPrice);
		dto.setFinalPartsPriceNew(wfPartsPrice);
		

		
		
		
		Boolean nonFundedInd=false;
		//Non Funded Buyer Indicator
		if(so.getSoWrkFlowControls() !=null && null!=so.getSoWrkFlowControls().getNonFundedInd()){
			nonFundedInd = so.getSoWrkFlowControls().getNonFundedInd();
		}
		dto.setNonFundedInd(nonFundedInd);
		String invoicePartsPricingModel=null;
		//R12_1:invoice parts pricing model
		if(so.getSoWrkFlowControls() !=null && null!=so.getSoWrkFlowControls().getInvoicePartsPricingModel()){
			invoicePartsPricingModel = so.getSoWrkFlowControls().getInvoicePartsPricingModel();
			dto.setInvoicePartsPricingModel(invoicePartsPricingModel);
		}
		
		//Priority 5B changes
		//Setting the value of invalid model serial ind
		if(null != so.getSoWrkFlowControls()){
			dto.setInvalidModelSerialInd(so.getSoWrkFlowControls().getInvalidModelSerialInd());
		}
		
		double totalFinalPrice = 0.0;
		if(so.getLaborFinalPrice() != null){
			dto.setFinalLaborPrice(so.getLaborFinalPrice());
			totalFinalPrice += so.getLaborFinalPrice().doubleValue();
			soFinalMaxLabor = so.getLaborFinalPrice().doubleValue() - permitFinalPrice;
		}
		if(so.getPartsFinalPrice() != null){
			dto.setFinalPartsPrice(so.getPartsFinalPrice());
			totalFinalPrice += so.getPartsFinalPrice().doubleValue();
		}
		/* End of Labor and Final Price calculation*/
		
		//SL-17511 HSR Changes for parts invoice
		if(so.getInvoiceParts() != null){
			logger.info("Price calculation for parts invoice in summary started");
			double partsInvoiceFinalPrice = fetchingFinalPriceForParts(so);
			logger.info("Price calculation for parts invoice in summary- partsInvoiceFinalPrice="+partsInvoiceFinalPrice);						
			totalFinalPrice += partsInvoiceFinalPrice;
			logger.info("Price calculation for parts invoice in summary- totalFinalPrice="+totalFinalPrice);			
			logger.info("Price calculation for parts invoice in summary ended");
		}
		
		dto.setSoFinalMaxLabor(soFinalMaxLabor);
		dto.setTotalFinalPrice(totalFinalPrice);
		double permitAddonPrice = calculatePermitAddonPrice(so);
		double nonPermitTaskAddonPrice =calculateNonPermitAddonPrice(so);
		dto.setAddOnPermitPrice(permitAddonPrice);
		dto.setNonPermitTaskAddonPrice(nonPermitTaskAddonPrice);
		double serviceFee = 0.0;
		if(so.getFundingTypeId() != null
				&& so.getFundingTypeId().intValue() == LedgerConstants.FUNDING_TYPE_NON_FUNDED){
			dto.setServiceFee(0.0);
			dto.setProviderPayment(0.0);
			//for display purpose SL-16817
			dto.setServiceFeeDisplay(0.0);
			dto.setProviderPaymentDisplay(0.0);
		}else if(so.getWfStateId().intValue() == OrderConstants.CANCELLED_STATUS){
			dto.setServiceFee(0.0);
			dto.setProviderPayment(totalFinalPrice);
			//for display purpose SL-16817
			dto.setServiceFeeDisplay(0.0);
			dto.setProviderPaymentDisplay(totalFinalPrice);
		}else{
			serviceFee =  MoneyUtil.getRoundedMoneyCustom(totalFinalPrice * so.getServiceFeePercentage());

			double addOnTot = calculateAddonTot(so);
			serviceFee =  serviceFee + MoneyUtil.getRoundedMoneyCustom(addOnTot * so.getServiceFeePercentage());

			dto.setServiceFee(MoneyUtil.getRoundedMoneyCustom(serviceFee));
			dto.setProviderPayment((totalFinalPrice + addOnTot) - MoneyUtil.getRoundedMoneyCustom(serviceFee));
			//for display purpose SL-16817
			double serviceFeeDisplay = so.getServiceFeePercentage()*((totalFinalPrice-permitFinalPrice)+nonPermitTaskAddonPrice);
			dto.setServiceFeeDisplay(MoneyUtil.getRoundedMoneyCustom(serviceFeeDisplay));
			double providerPaymentDisplay = (totalFinalPrice+permitTaskAddonPrice+nonPermitTaskAddonPrice+permitAddonPrice) - MoneyUtil.getRoundedMoneyCustom(serviceFeeDisplay);
			dto.setProviderPaymentDisplay(providerPaymentDisplay);
			

		}
		
		if(so.getCancellationFee() != null){
			dto.setCancellationFee(so.getCancellationFee());
		}
		//Invoice parts for 'Complete record' & 'Close & Pay'
		if(null != so.getInvoiceParts()){
			dto.setInvoiceParts(so.getInvoiceParts());
		}
		// Setting Reason codes
		if(null!=so.getSoPartLaborPriceReason()){
			dto.setPartLaborPriceReason(so.getSoPartLaborPriceReason());
		}
		if("3333".equals(dto.getBuyerID())){
		// for relay buyer
		if(so.getSoWrkFlowControls() !=null && null!=so.getSoWrkFlowControls().getLaborTaxPercentage()){
		    dto.setLaborTaxPercentage(so.getSoWrkFlowControls().getLaborTaxPercentage()); 
			} 
		
		if(so.getSoWrkFlowControls() !=null && null!=so.getSoWrkFlowControls().getMaterialsTaxPercentage()){
		    dto.setMaterialsTaxPercentage(so.getSoWrkFlowControls().getMaterialsTaxPercentage()); 
			} 
			
		/*if(null!=dto.getStatus() &&(dto.getStatus().intValue() == OrderConstants.COMPLETED_STATUS  ||
				 dto.getStatus().intValue() == OrderConstants.CLOSED_STATUS) ){	
			Double orginalFinalPrice=0.00d;
			Double orginalLaborPrice=0.00d;
			Double orginalPartsPrice=0.00d;

			
			if(null!=dto.getFinalLaborPrice() ){
				
				orginalLaborPrice=dto.getFinalLaborPrice().doubleValue();
				if(null!= dto.getLaborTaxPercentage()){
				orginalLaborPrice=dto.getFinalLaborPrice().doubleValue() * ((100.00/(100.00+dto.getLaborTaxPercentage().doubleValue())));
				}
			}
			if(null!=dto.getFinalPartsPrice() ){
				orginalPartsPrice=dto.getFinalPartsPrice().doubleValue();
				if(null!= dto.getMaterialsTaxPercentage()){
				orginalPartsPrice=dto.getFinalPartsPrice().doubleValue() * ((100.00/(100.00+dto.getMaterialsTaxPercentage().doubleValue())));
				}
			}
			
			
			if(null!=orginalLaborPrice){
			orginalFinalPrice=orginalLaborPrice.doubleValue();
			}
			if(null!=orginalPartsPrice){
			orginalFinalPrice=orginalFinalPrice.doubleValue() + orginalPartsPrice.doubleValue();
			}
			
			Double orginalServiceFee= 0.00d;
			
			orginalServiceFee=MoneyUtil.getRoundedMoney(orginalFinalPrice.doubleValue()-((1.00-0.10)* orginalFinalPrice.doubleValue()));
			
			// propay= totalFinalPrice - orgServiceFee 	
			
			Double providerPay= 0.00d;
			
			if(null!=dto.getTotalFinalPrice() && null!=orginalServiceFee){
			providerPay=MoneyUtil.getRoundedMoney(dto.getTotalFinalPrice().doubleValue()-orginalServiceFee.doubleValue());
			}		
			
			
			Double orginalProviderPay= 0.00d;
			if(null!=orginalFinalPrice && null!=orginalServiceFee){
			orginalProviderPay=MoneyUtil.getRoundedMoney(orginalFinalPrice.doubleValue()-orginalServiceFee.doubleValue());
			}
			
			 
			Double tax=0.00d;
			if(null!=providerPay && null!=orginalProviderPay){
			tax=MoneyUtil.getRoundedMoney(providerPay.doubleValue()-orginalProviderPay.doubleValue());
			}
			
			dto.setOrginalFinalPrice(orginalFinalPrice);
			dto.setOrginalServiceFee(orginalServiceFee);
			dto.setProviderPay(providerPay);
			dto.setOrginalProviderPay(orginalProviderPay);
			dto.setTax(tax);
		}*/
	}
		
		// End 'Service Order Pricing' Panel

		// Start 'Terms and Conditions' Panel
		dto.setTermsAndConditions(so.getSoTermsCondContent());
		// End 'Terms and Conditions' Panel

		// survey results indicator
		ServiceOrderSurveyResponseVO btpvo = so.getBuyerToProviderResults();
		String soId = null;
		if (btpvo != null)
			soId = btpvo.getSoId();
		if (soId == null)
			dto.setBuyerHasRatedProvider(false);
		else
			dto.setBuyerHasRatedProvider(true);

		ServiceOrderSurveyResponseVO ptbvo = so.getProviderToBuyerResults();
		soId = null;
		if (ptbvo != null)
			soId = ptbvo.getSoId();
		if (soId == null)
			dto.setProviderHasRatedBuyer(false);
		else
			dto.setProviderHasRatedBuyer(true);

		// final prices for parts and labor
		if (so.getLaborFinalPrice() != null) 
				dto.setFinalLaborPrice(so.getLaborFinalPrice());
			else
				dto.setFinalLaborPrice(0.0);
		

		// final prices for parts and labor
		if ((so.getPartsFinalPrice() != null)) 
			dto.setFinalPartsPrice(so.getPartsFinalPrice());
		else
			dto.setFinalPartsPrice(0.0);

		// final description for complete SO
		if (so.getResolutionDs() != null)
			dto.setResolutionComment(so.getResolutionDs());
		else
			dto.setResolutionComment("");

		//Conditional logic to show Increase Maximum Price button on Summary Tab - pricing panel
		Integer wfStateId = so.getWfStateId(); 
		if(wfStateId != null && 
				wfStateId.intValue() == OrderConstants.ACTIVE_STATUS || 
				wfStateId.intValue() == OrderConstants.ACCEPTED_STATUS ||
				wfStateId.intValue() == OrderConstants.PROBLEM_STATUS){
			
			dto.setShowIncreaseSpendLimitButton(true);
		}
		else{
			dto.setShowIncreaseSpendLimitButton(false);
		}
		dto.setResourceDispatchAddress(so.getResourceDispatchAddress());
		
		if (so.isShareContactInd() != null) {
			dto.setShareContactInd(so.isShareContactInd());
		}
		if(null!=so.getCarRuleId()){
			dto.setCarSO(true);
		} 
		dto.setAssignmentType(so.getAssignmentType());
		
		return dto;
	}
	
	private static String getEndCustomerPrimaryPhoneNumberWidgetDisplayValue(
			Integer roleId, Integer statusId, String endCustomerPrimaryPhoneNumber) {
		StringBuffer returnValue = new StringBuffer();
		if (roleId.intValue() == OrderConstants.BUYER_ROLEID || roleId.intValue() == OrderConstants.SIMPLE_BUYER_ROLEID) {
			returnValue.append("Ph: <br>(");
			returnValue.append(SOPDFUtils.formatPhoneNumber(endCustomerPrimaryPhoneNumber));
			returnValue.append(")");
		}   
		else if (roleId.intValue() == OrderConstants.PROVIDER_ROLEID) {
			if (statusId.intValue() == OrderConstants.ACCEPTED_STATUS || 
				statusId.intValue() == OrderConstants.ACTIVE_STATUS || 
				statusId.intValue() == OrderConstants.COMPLETED_STATUS || 
				statusId.intValue() == OrderConstants.PROBLEM_STATUS) {
					returnValue.append("Ph: <br>(");
					returnValue.append(SOPDFUtils.formatPhoneNumber(endCustomerPrimaryPhoneNumber));
					returnValue.append(")");
				}
			} 		
		return returnValue.toString();
	}
	private static String getProviderPrimaryPhoneNumberWidgetDisplayValue(
			Integer roleId, Integer statusId, String endCustomerPrimaryPhoneNumber, String providerMainPhoneNo) {
		StringBuffer returnValue = new StringBuffer();
		if (roleId.intValue() == OrderConstants.BUYER_ROLEID || roleId.intValue() == OrderConstants.SIMPLE_BUYER_ROLEID) {
			if(statusId.intValue() == OrderConstants.ACCEPTED_STATUS || 
			   statusId.intValue() == OrderConstants.ACTIVE_STATUS   || 
			   statusId.intValue() == OrderConstants.CANCELLED_STATUS|| 
			   statusId.intValue() == OrderConstants.PROBLEM_STATUS  ||
			   statusId.intValue() == OrderConstants.CLOSED_STATUS   ||
			   statusId.intValue() == OrderConstants.PENDING_CANCEL_STATUS){
			   returnValue.append(SOPDFUtils.formatPhoneNumber(providerMainPhoneNo));
			}else{
			returnValue.append("Ph: <br>(");
			returnValue.append(SOPDFUtils.formatPhoneNumber(endCustomerPrimaryPhoneNumber));
			returnValue.append(")");
			}
		}   
		else if (roleId.intValue() == OrderConstants.PROVIDER_ROLEID) {
			if (statusId.intValue() == OrderConstants.ACCEPTED_STATUS || 
				statusId.intValue() == OrderConstants.ACTIVE_STATUS || 
				statusId.intValue() == OrderConstants.COMPLETED_STATUS || 
				statusId.intValue() == OrderConstants.PROBLEM_STATUS) {
					returnValue.append("Ph: <br>(");
					returnValue.append(SOPDFUtils.formatPhoneNumber(endCustomerPrimaryPhoneNumber));
					returnValue.append(")");
				}
			} 		
		return returnValue.toString();
	}
	private static String getProviderAlternatePhoneNumberWidgetDisplayValue(
			Integer roleId, Integer statusId, String endCustomerPrimaryPhoneNumber, String providerMobilePhoneNo) {
		StringBuffer returnValue = new StringBuffer();
		if (roleId.intValue() == OrderConstants.BUYER_ROLEID || roleId.intValue() == OrderConstants.SIMPLE_BUYER_ROLEID) {
			if(statusId.intValue() == OrderConstants.ACCEPTED_STATUS || 
			   statusId.intValue() == OrderConstants.ACTIVE_STATUS   || 
			   statusId.intValue() == OrderConstants.CANCELLED_STATUS|| 
			   statusId.intValue() == OrderConstants.PROBLEM_STATUS  ||
			   statusId.intValue() == OrderConstants.CLOSED_STATUS   ||
			   statusId.intValue() == OrderConstants.PENDING_CANCEL_STATUS){
			   returnValue.append(SOPDFUtils.formatPhoneNumber(providerMobilePhoneNo));
			}else{
			   returnValue.append("Ph: <br>(");
			   returnValue.append(SOPDFUtils.formatPhoneNumber(endCustomerPrimaryPhoneNumber));
			   returnValue.append(")");
					}
		}   
		else if (roleId.intValue() == OrderConstants.PROVIDER_ROLEID) {
			if (statusId.intValue() == OrderConstants.ACCEPTED_STATUS || 
				statusId.intValue() == OrderConstants.ACTIVE_STATUS || 
				statusId.intValue() == OrderConstants.COMPLETED_STATUS || 
				statusId.intValue() == OrderConstants.PROBLEM_STATUS) {
					returnValue.append("Ph: <br>(");
					returnValue.append(SOPDFUtils.formatPhoneNumber(endCustomerPrimaryPhoneNumber));
					returnValue.append(")");
				}
			} 		
		return returnValue.toString();
	}
	private static Double calculatePermitAddonPrice(ServiceOrder so){
		double permitaddonprice = 0.00;
		List<ServiceOrderAddonVO> addons = so.getUpsellInfo();
		for(ServiceOrderAddonVO soAddonVO : addons){
			if(soAddonVO.getSku().equals("99888") && soAddonVO.getQuantity()==1 && soAddonVO.isAutoGenInd()==false){
				permitaddonprice = permitaddonprice + soAddonVO.getRetailPrice();
			}
		}
		return permitaddonprice;
	}
	
	private static Double fetchingFinalPriceForParts(ServiceOrder so){
		double finalPartinvoicePrice  = 0.00;
		List<ProviderInvoicePartsVO> invoiceParts = so.getInvoiceParts();
		for(ProviderInvoicePartsVO iPartsVO : invoiceParts){
			{
				
				if (null != iPartsVO.getPartStatus()
						&& iPartsVO.getPartStatus().equalsIgnoreCase(
								"Installed")) {
				
				finalPartinvoicePrice = finalPartinvoicePrice+iPartsVO.getFinalPrice().doubleValue();
				}
			}
		}
		return finalPartinvoicePrice;
	}
	
	
	
	private static Double calculateNonPermitAddonPrice(ServiceOrder so){
		double nonPermitaddonprice = 0.00;
		Integer intBuyerId = so.getBuyer()!=null?so.getBuyer().getBuyerId()  : -1;
		List<ServiceOrderAddonVO> addons = so.getUpsellInfo();
		for(ServiceOrderAddonVO soAddonVO : addons){
			if( (OrderConstants.UPSELL_PAYMENT_TYPE_CREDIT.equals(soAddonVO.getCoverage()) &&
					(intBuyerId.intValue() == BuyerConstants.HSR_BUYER_ID) ) ){
					continue;
			}
			if(!soAddonVO.getSku().equals("99888")&& soAddonVO.getQuantity()>=1){
				/*nonPermitaddonprice = nonPermitaddonprice + MoneyUtil.getRoundedMoney(		
						soAddonVO.getQuantity() * 
						MoneyUtil.getRoundedMoney(soAddonVO.getRetailPrice() * (1 - soAddonVO.getMargin()))
			);*/ 
				nonPermitaddonprice = nonPermitaddonprice
						+ MoneyUtil.getRoundedMoney(soAddonVO.getQuantity()
								* (soAddonVO.getRetailPrice() - (soAddonVO
										.getRetailPrice() * soAddonVO
										.getMargin())));
			}
		}
		return nonPermitaddonprice;
	}
	
	private static Double calculateAddonTot(ServiceOrder so) {
		Double totAddons = 0.0;
		Integer intBuyerId = so.getBuyer()!=null?so.getBuyer().getBuyerId()  : -1;
		List<ServiceOrderAddonVO> addons = so.getUpsellInfo();
		for (ServiceOrderAddonVO soAddonVO : addons) {
			if( (OrderConstants.UPSELL_PAYMENT_TYPE_CREDIT.equals(soAddonVO.getCoverage()) &&
					(intBuyerId.intValue() == BuyerConstants.HSR_BUYER_ID) ) ){
					continue;
			}
			/*totAddons = totAddons  + MoneyUtil.getRoundedMoney(		
														soAddonVO.getQuantity() * 
														MoneyUtil.getRoundedMoney(soAddonVO.getRetailPrice() * (1 - soAddonVO.getMargin()))
											);*/
			totAddons = totAddons
					+ MoneyUtil
							.getRoundedMoney(soAddonVO.getQuantity()
									* (soAddonVO.getRetailPrice() - (soAddonVO
											.getRetailPrice() * soAddonVO
											.getMargin())));
		}
		return MoneyUtil.getRoundedMoney(totAddons);
	}
	
	
	/**
	 * getBuyerWidgetDisplayValue is used to determine what buyer data will be displayed in the right side bar 
	 * widgets.
	 * @param roleId
	 * @param statusId
	 * @param companyName
	 * @param id
	 * @return
	 */
	protected static String getBuyerWidgetDisplayValue (Integer roleId, Integer statusId, String companyName, Integer id) {
		StringBuffer returnValue = new StringBuffer();
		
		if (roleId.intValue() == OrderConstants.BUYER_ROLEID || roleId.intValue() == OrderConstants.SIMPLE_BUYER_ROLEID) {
			returnValue.append(companyName)
			           .append(LEFT_PARAN_W_SPACE)
			           .append(id)
			           .append(RIGHT_PARAN);
			
		} else if (roleId.intValue() == OrderConstants.PROVIDER_ROLEID) {
			if (statusId.intValue() == OrderConstants.ACCEPTED_STATUS || 
				statusId.intValue() == OrderConstants.ACTIVE_STATUS || 
				statusId.intValue() == OrderConstants.COMPLETED_STATUS || 
				statusId.intValue() == OrderConstants.PROBLEM_STATUS) {
				returnValue.append(companyName)
		                   .append(LEFT_PARAN_W_SPACE)
		                   .append(id)
		                   .append(RIGHT_PARAN);
			} else {
				returnValue.append(id);
			} 
		}
		return returnValue.toString();
	}

	/**
	 * getProviderWidgetDisplayValue is used to determine what provider data will be displayed in the right side bar 
	 * widgets.
	 * @param roleId
	 * @param statusId
	 * @param firstName
	 * @param lastName
	 * @param id
	 * @return
	 */
	protected static String getProviderWidgetDisplayValue (Integer roleId, Integer statusId, String firstName, String lastName, Integer id) {
		StringBuffer returnValue = new StringBuffer();
		boolean firstSet = false;
		boolean lastSet = false;
		
		if (roleId.intValue() == OrderConstants.BUYER_ROLEID || roleId.intValue() == OrderConstants.SIMPLE_BUYER_ROLEID) {
			if (statusId.intValue() == OrderConstants.DRAFT_STATUS || 
				statusId.intValue() == OrderConstants.ROUTED_STATUS || 
				statusId.intValue() == OrderConstants.VOIDED_STATUS || 
				statusId.intValue() == OrderConstants.EXPIRED_STATUS ||
				statusId.intValue() == OrderConstants.DELETED_STATUS) {
				
				returnValue.append(OrderConstants.NOT_APPLICABLE);
			} else if (statusId.intValue() == OrderConstants.ACCEPTED_STATUS || 
					   statusId.intValue() == OrderConstants.ACTIVE_STATUS || 
					   statusId.intValue() == OrderConstants.COMPLETED_STATUS || 
					   statusId.intValue() == OrderConstants.PROBLEM_STATUS||
					   statusId.intValue() == OrderConstants.PENDING_CANCEL_STATUS||
					   statusId.intValue() == OrderConstants.CANCELLED_STATUS||
					   statusId.intValue() == OrderConstants.CLOSED_STATUS) {
				
				if (StringUtils.isNotEmpty(lastName)) {
					lastSet = true;
					returnValue.append(lastName);
				}
				if (StringUtils.isNotEmpty(firstName)){
					firstSet = true;
					if (lastSet) {
						returnValue.append(COMMA_WITH_SPACE);
					}
					returnValue.append(firstName);
				}
					 
				returnValue.append(LEFT_PARAN_W_SPACE)
                   .append(id)
                   .append(RIGHT_PARAN);
			} else {
				returnValue.append(id);
			}
		} else if (roleId.intValue() == OrderConstants.PROVIDER_ROLEID) {
			if (statusId.intValue() == OrderConstants.ROUTED_STATUS) {
				returnValue.append(OrderConstants.NOT_APPLICABLE);
			} else {
				returnValue.append(lastName)
        	       .append(COMMA_WITH_SPACE)
                   .append(firstName)
                   .append(LEFT_PARAN_W_SPACE)
                   .append(id)
                   .append(RIGHT_PARAN);
			} 
		}
		
		return returnValue.toString();
	}

	/**
	 * getEndCustomerWidgetDisplayValue is used to determine what end customer data will be displayed in the right side bar 
	 * widgets.
	 * @param roleId
	 * @param statusId
	 * @param firstName
	 * @param lastName
	 * @return
	 */
	protected static String getEndCustomerWidgetDisplayValue (Integer roleId, Integer statusId, String firstName, String lastName) {
		StringBuffer returnValue = new StringBuffer();
		boolean lastNameSet = false;
		
		if (roleId.intValue() == OrderConstants.BUYER_ROLEID || roleId.intValue() == OrderConstants.SIMPLE_BUYER_ROLEID) {
			
			if (StringUtils.isNotEmpty(lastName)){
				returnValue.append(lastName);
				lastNameSet = true;
			}
			if (StringUtils.isNotEmpty(firstName)){
				if (lastNameSet){
					returnValue.append(COMMA_WITH_SPACE);
				}
				returnValue.append(firstName);
			}   
		} else if (roleId.intValue() == OrderConstants.PROVIDER_ROLEID) {
			if (statusId.intValue() == OrderConstants.ACCEPTED_STATUS || 
				statusId.intValue() == OrderConstants.ACTIVE_STATUS || 
				statusId.intValue() == OrderConstants.COMPLETED_STATUS || 
				statusId.intValue() == OrderConstants.PROBLEM_STATUS) {
				
				if (StringUtils.isNotEmpty(lastName)){
					returnValue.append(lastName);
					lastNameSet = true;
				}
				if (StringUtils.isNotEmpty(firstName)){
					if (lastNameSet){
						returnValue.append(COMMA_WITH_SPACE);
					}
					returnValue.append(firstName);
				}
			} else {
				returnValue.append(OrderConstants.NOT_APPLICABLE);
			} 
		}
		
		return returnValue.toString();
	}

	/**
	 * getLocationWidgetDisplayValue is used to determine what location data will be displayed in the right side bar 
	 * widgets.
	 * @param roleId
	 * @param statusId
	 * @param street1
	 * @param street2
	 * @param citystatezip
	 * @return
	 */
	protected static String getLocationWidgetDisplayValue (Integer roleId, Integer statusId, 
			String street1, String street2, String citystatezip) {

		StringBuffer returnValue = new StringBuffer();

		if (roleId.intValue() == OrderConstants.BUYER_ROLEID || roleId.intValue() == OrderConstants.SIMPLE_BUYER_ROLEID) {
			
			returnValue.append(concatentAllAddressFields(street1, street2, citystatezip));
		} else if (roleId.intValue() == OrderConstants.PROVIDER_ROLEID) {
			if (statusId == OrderConstants.ROUTED_STATUS || 
				statusId == OrderConstants.CANCELLED_STATUS || 
				statusId == OrderConstants.CLOSED_STATUS) {
				
				returnValue.append(citystatezip);
			} else {
				returnValue.append(concatentAllAddressFields(street1, street2, citystatezip));
			}
		}
		
		return returnValue.toString(); 
	}

	/**
	 * Concatenates all three input parameters together and includes appropriate spacing.
	 * @param street1
	 * @param street2
	 * @param citystatezip
	 * @return
	 */
	protected static String concatentAllAddressFields (String street1, String street2, String citystatezip) {

		StringBuffer returnValue = new StringBuffer();
		boolean street1Set = false;
		boolean street2Set = false;
		
		if (StringUtils.isNotEmpty(street1)) {
			returnValue.append(street1);
			street1Set = true;
		}
		if (StringUtils.isNotEmpty(street2)) {
			if (street1Set) {
				returnValue.append(SPACE);
			}
			returnValue.append(street2);
			street2Set = true;
		}
		if (StringUtils.isNotEmpty(citystatezip)) {
			if (street1Set || street2Set) {
				returnValue.append(SPACE);
			}
			returnValue.append(citystatezip);
		}

		return returnValue.toString();
	}

	/**
	 * Method to set the Contact Information into SOContactDTO object based on the available Contact objects
	 * @param primContact
	 * @param altContact
	 * @param faxContact
	 * @param toContactDTO
	 */
	protected static void setContactInformation(Contact primContact, Contact altContact, Contact faxContact, SOContactDTO toContactDTO) {
		HashMap<ArrayList<String>, String> contactNumbersMap = new HashMap<ArrayList<String>, String>();
		ArrayList<String> contactTypeList = null;
		
		String phoneClassId = "";
		Integer phoneTypeIdInt = 0;
		String phoneTypeId = "";
		
		if(toContactDTO == null)
			return;
		
		resetContactInformation(toContactDTO);
		
		if(primContact != null) {
			phoneClassId = primContact.getPhoneClassId();
			phoneTypeIdInt = primContact.getPhoneTypeId();
			phoneTypeId = phoneTypeIdInt != null ? phoneTypeIdInt.toString() : "";
			
			contactTypeList = new ArrayList<String>();
			
			contactTypeList.add(StringUtils.isEmpty(phoneClassId) ? "" : phoneClassId);
			contactTypeList.add(StringUtils.isEmpty(phoneTypeId) ? "" : phoneTypeId);
			
			contactNumbersMap.put(contactTypeList, (StringUtils.isEmpty(primContact.getPhoneNo()) ? "" : UIUtils.formatPhoneNumber(primContact.getPhoneNo())) +
					(StringUtils.isEmpty(primContact.getPhoneNoExt()) ? "" : " Ext. " + primContact.getPhoneNoExt()));
		}
		if(altContact != null) {
			phoneClassId = altContact.getPhoneClassId();
			phoneTypeIdInt = altContact.getPhoneTypeId();
			phoneTypeId = phoneTypeIdInt != null ? phoneTypeIdInt.toString() : "";
			
			contactTypeList = new ArrayList<String>();
			
			contactTypeList.add(StringUtils.isEmpty(phoneClassId) ? "" : phoneClassId);
			contactTypeList.add(StringUtils.isEmpty(phoneTypeId) ? "" : phoneTypeId);
			
			contactNumbersMap.put(contactTypeList, (StringUtils.isEmpty(altContact.getPhoneNo()) ? "" : UIUtils.formatPhoneNumber(altContact.getPhoneNo())) +
					(StringUtils.isEmpty(altContact.getPhoneNoExt()) ? "" : " Ext. " + altContact.getPhoneNoExt()));
		}
		if(faxContact != null) {
			phoneClassId = faxContact.getPhoneClassId();
			phoneTypeIdInt = faxContact.getPhoneTypeId();
			phoneTypeId = phoneTypeIdInt != null ? phoneTypeIdInt.toString() : "";
			
			contactTypeList = new ArrayList<String>();
			
			contactTypeList.add(StringUtils.isEmpty(phoneClassId) ? "" : phoneClassId);
			contactTypeList.add(StringUtils.isEmpty(phoneTypeId) ? "" : phoneTypeId);
			
			contactNumbersMap.put(contactTypeList, (StringUtils.isEmpty(faxContact.getPhoneNo()) ? "" : UIUtils.formatPhoneNumber(faxContact.getPhoneNo())) +
					(StringUtils.isEmpty(faxContact.getPhoneNoExt()) ? "" : " Ext. " + faxContact.getPhoneNoExt()));
		}
		
		
		//Get Map.Entry values from the HashMap<phoneClassId, phoneNumber>
		Set<Map.Entry<ArrayList<String>, String>> contactNumbersSet = contactNumbersMap.entrySet();
	    Iterator<Map.Entry<ArrayList<String>, String>> iter = contactNumbersSet.iterator();
	    
	    while (iter.hasNext()) {
	    	Map.Entry<ArrayList<String>, String> entry = (Map.Entry<ArrayList<String>, String>) iter.next();
	    	ArrayList<String> phoneTypeDtlsList = entry.getKey();
	    	
			if(OrderConstants.PHONE_CLASS_HOME.equalsIgnoreCase(phoneTypeDtlsList.get(0))) {//Home
				toContactDTO.setPhoneHome(entry.getValue());
			} else if(OrderConstants.PHONE_CLASS_MOBILE.equalsIgnoreCase(phoneTypeDtlsList.get(0))) {//Mobile
				toContactDTO.setPhoneMobile(entry.getValue());
			} else if(OrderConstants.PHONE_CLASS_WORK.equalsIgnoreCase(phoneTypeDtlsList.get(0))) {//Work
				toContactDTO.setPhoneWork(entry.getValue());
			} else if(OrderConstants.PHONE_CLASS_PAGER.equalsIgnoreCase(phoneTypeDtlsList.get(0))) {//Pager
				toContactDTO.setPager(entry.getValue());
			} else if(OrderConstants.PHONE_CLASS_FAX.equalsIgnoreCase(phoneTypeDtlsList.get(0))) {//Fax
				toContactDTO.setFax(entry.getValue());
			} else if(OrderConstants.PHONE_CLASS_OTHER.equalsIgnoreCase(phoneTypeDtlsList.get(0)) &&
					OrderConstants.PHONE_TYPE_FAX.equalsIgnoreCase(phoneTypeDtlsList.get(1))) {//Class=Other & Type=FAX ==> FAX #
				toContactDTO.setFax(entry.getValue());
			} else if(OrderConstants.PHONE_CLASS_OTHER.equalsIgnoreCase(phoneTypeDtlsList.get(0)) &&
					!OrderConstants.PHONE_TYPE_FAX.equalsIgnoreCase(phoneTypeDtlsList.get(1))) {//Class=Other & Type<>FAX ==> Other #
				toContactDTO.setOther(entry.getValue());
			}
		}

	}
	
	/**
	 * Method to reset all the values of SOContactDTO object before populating new values into it.
	 * @param contactDTO
	 */
	protected static void resetContactInformation(SOContactDTO contactDTO) {
		contactDTO.setPager(null);
		contactDTO.setPhonePrimary(null);
		contactDTO.setPhoneAlternate(null);
		contactDTO.setPhoneHome(null);
		contactDTO.setPhoneMobile(null);
		contactDTO.setPhoneWork(null);
		contactDTO.setOther(null);
		contactDTO.setFax(null);
	}
	
	public static List<AccountDTO> convertAccountListtoAccountDTOList(List<Account> accLists){
		
		AccountDTO accDto = null;
		String accountNo = null;
		List<AccountDTO> accountList = new ArrayList<AccountDTO>();

		String bankName;
		List<AccountDTO> accountDTO = new ArrayList<AccountDTO>();
		int accListSize = accLists.size();
		for (int i = 0; i < accListSize; i++) {
			accDto = new AccountDTO();
			accountNo = accLists.get(i).getAccount_no();
			accountNo = ServiceLiveStringUtils.maskString(accountNo , 4, "X");			
	
			Long accountId = accLists.get(i).getAccount_id();
			accDto.setAccountTypeId(accountId + "," + accLists.get(i).getAccount_type_id());
			bankName = accLists.get(i).getBank_name();
			
			if(bankName != null && bankName.length()>20){
				bankName = bankName.substring(0, 20);
			}
			if(bankName!= null && !bankName.equals(""))
				accDto.setAccountNameNum(bankName + " - " +accountNo);
			else
				accDto.setAccountNameNum(accountNo);
				
			accDto.setAccountNameNum(bankName + " - " +accountNo);
			accDto.setAccountId(accountId);
			accDto.setActiveInd(accLists.get(i).isActive_ind());			
			accountList.add(accDto);
		}
		return accountList;
	}
	
	public static OrderGroupDTO convertOrderGroup(OrderGroupVO vo)
	{
		if(vo == null)
			return null;
		
		OrderGroupDTO dto =  new OrderGroupDTO();
		if(dto == null)
			return null;
		
		// Get many pieces of data from the Service Orders, the first one should be as good as any.
		ServiceOrderSearchResultsVO firstSO=null;
		if(vo.getServiceOrders() != null && vo.getServiceOrders().size() > 0)
		{
			firstSO = vo.getServiceOrders().get(0);
			dto.setId(vo.getGroupId());
			
			if(firstSO != null)
			{
				dto.setCity(firstSO.getCity());
				dto.setState(firstSO.getStateCd());
				dto.setZip(firstSO.getZip());
				String title = "Title: ";
				if(firstSO.getSoTitle() != null)
					title += firstSO.getSoTitle();
				
					
				dto.setTitle(title);
				dto.setEndCustomer(firstSO.getBuyerFirstName() + " " + firstSO.getBuyerLastName());
				
				//TODO - get earliest start date by looping thru ALL Service Orders
				if(firstSO.getAppointStartDate() != null)
					dto.setDate(firstSO.getAppointStartDate().toString());
				dto.setTime(firstSO.getServiceStartTime());				
			}				
		}
		
		// Convert the Service Orders
		ServiceOrderSearchResultsVO newVO;
		List<ServiceOrderSearchResultsVO> newList = new ArrayList<ServiceOrderSearchResultsVO>();
		for(ServiceOrderSearchResultsVO oldVO : vo.getServiceOrders())
		{
			newVO = oldVO;
			newList.add(newVO);
		}
		
		return dto;
	}

	public static List<OrderGroupDTO> convertOrderGroupList(List<OrderGroupVO> voList)
	{
		if(voList == null)
			return null;

		List<OrderGroupDTO> dtoList = new ArrayList<OrderGroupDTO>();
		OrderGroupDTO dto;
		for(OrderGroupVO vo : voList)
		{
			dto = convertOrderGroup(vo);
			dtoList.add(dto);
		}
		
		return dtoList;
	}
	public static FMReportVO convertFMReportDTOToVO(FMReportTabDTO reportTabDTO){
	       FMReportVO reportVO=new FMReportVO();
	       reportVO.setBuyerList(reportTabDTO.getBuyerList());
	       reportVO.setProviderList(reportTabDTO.getProviderList());
	       reportVO.setFromDate(reportTabDTO.getDtFromDate());
	       reportVO.setToDate(reportTabDTO.getDtToDate());
	       if(reportTabDTO.getFromDate()!=null)
	        reportVO.setFromDate(reportTabDTO.getDtFromDate());
	       reportVO.setReportYear(reportTabDTO.getReportYear());
	       if(reportTabDTO.getToDate()!=null)
	        reportVO.setToDate(reportTabDTO.getDtToDate());
	       reportVO.setReportName(reportTabDTO.getReportName());
	       reportVO.setProviderList(reportTabDTO.getProviderList()); 
           reportVO.setRoleId(reportTabDTO.getRoleId());
           reportVO.setBuyers(reportTabDTO.getBuyers());
           reportVO.setProviders(reportTabDTO.getProviders());
	       reportVO.setReportByCalendarYear(reportTabDTO.isReportByCalendarYear());
	       reportVO.setReportByDateRange(reportTabDTO.isReportByDateRange());
	       reportVO.setReportByPaymentDate(reportTabDTO.isReportByPaymentDate());
	       reportVO.setReportByCompletedDate(reportTabDTO.isReportByCompletedDate());
	       reportVO.setReportForSpecificBuyers(reportTabDTO.isReportForSpecificBuyers());
	       reportVO.setReportForSpecificProviders(reportTabDTO.isReportForSpecificProviders());
	       reportVO.setReportForAllBuyers(reportTabDTO.isReportForAllBuyers());
	       if(reportVO.isReportForSpecificBuyers()){
	    	   reportVO.setReportForAllBuyers(false);
	       }
	       if(reportVO.isReportForSpecificProviders()){
	    	   reportVO.setReportForAllProviders(false);
	       }
	       reportVO.setRole(reportTabDTO.getId());
	       reportVO.setReportForAllProviders(reportTabDTO.isReportForAllProviders());
	       reportVO.setStartIndex(reportTabDTO.getStartIndex());
	       reportVO.setNumberOfRecords(reportTabDTO.getNumberOfRecords());
	       reportVO.setRoleType(reportTabDTO.getRoleType());	       
	       reportVO.setResourceId(reportTabDTO.getResourceId());
	       reportVO.setReportId(reportTabDTO.getReportId());
	       reportVO.setFilePath(reportTabDTO.getFilePath());
	       return reportVO;
		
	}
	public static FMReportTabDTO convertFMReportVOToDTO(FMReportVO fmReportVO) {
		FMReportTabDTO reportTabDTO = new FMReportTabDTO();
		if(null == fmReportVO){
			return null;
		}
		reportTabDTO.setReportForAllBuyers(fmReportVO.isReportForAllBuyers());
		reportTabDTO.setBuyers(fmReportVO.getBuyers());
		if(fmReportVO.isReportForAllProviders()){
			reportTabDTO.setReportForAllProviders(true);
			reportTabDTO.setReportForSpecificProviders(false);
		}else{
			reportTabDTO.setReportForAllProviders(false);
			reportTabDTO.setReportForSpecificProviders(true);
		}
		if(fmReportVO.isReportForAllBuyers()){
			reportTabDTO.setReportForAllBuyers(true);
			reportTabDTO.setReportForSpecificBuyers(false);
		}else{
			reportTabDTO.setReportForAllBuyers(false);
			reportTabDTO.setReportForSpecificBuyers(true);
		}
		reportTabDTO.setReportForAllProviders(fmReportVO.isReportForAllProviders());
		reportTabDTO.setProviders(fmReportVO.getProviders());
		reportTabDTO.setDtFromDate(fmReportVO.getFromDate());
		reportTabDTO.setDtToDate(fmReportVO.getToDate());
		reportTabDTO.setReportName(fmReportVO.getReportName());
		reportTabDTO.setRoleId(fmReportVO.getRoleId());
		reportTabDTO.setRoleType(fmReportVO.getRoleType());
		reportTabDTO.setResourceId(fmReportVO.getResourceId());
		reportTabDTO.setTotalRecords(fmReportVO.getTotalRecords());
		reportTabDTO.setReportFooter(fmReportVO.getReportFooter());
		if(fmReportVO.isReportByPaymentDate()){
			reportTabDTO.setReportByPaymentDate(true);
			reportTabDTO.setReportByCompletedDate(false);
		}else{
			reportTabDTO.setReportByCompletedDate(true);
		}	
		reportTabDTO.setFilePath(fmReportVO.getFilePath());
		reportTabDTO.setFileName(fmReportVO.getReportNameForExport());
		return reportTabDTO;
	}
}
