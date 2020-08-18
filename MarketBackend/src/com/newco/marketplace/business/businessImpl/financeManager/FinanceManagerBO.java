package com.newco.marketplace.business.businessImpl.financeManager;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.mail.MessagingException;

import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.business.businessImpl.ABaseCriteriaHandler;
import com.newco.marketplace.business.businessImpl.audit.WalletControlAuditNotesBOImpl;
import com.newco.marketplace.business.businessImpl.provider.ABaseBO;
import com.newco.marketplace.business.businessImpl.provider.EmailTemplateBOImpl;
import com.newco.marketplace.business.iBusiness.financeManager.IFinanceManagerBO;
import com.newco.marketplace.business.iBusiness.ledger.ILedgerFacilityBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.criteria.PagingCriteria;
import com.newco.marketplace.dto.vo.CryptographyVO;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.EmailVO;
import com.newco.marketplace.dto.vo.PIIThresholdVO;
import com.newco.marketplace.dto.vo.ajax.AjaxCacheVO;
import com.newco.marketplace.dto.vo.financemanger.AdminPaymentVO;
import com.newco.marketplace.dto.vo.financemanger.BuyerSOReportVO;
import com.newco.marketplace.dto.vo.financemanger.ExportStatusVO;
import com.newco.marketplace.dto.vo.financemanger.FMReportVO;
import com.newco.marketplace.dto.vo.financemanger.FMW9ProfileVO;
import com.newco.marketplace.dto.vo.financemanger.ProviderSOReportVO;
import com.newco.marketplace.dto.vo.ledger.Account;
import com.newco.marketplace.dto.vo.ledger.AccountHistoryResultVO;
import com.newco.marketplace.dto.vo.ledger.AccountHistoryVO;
import com.newco.marketplace.dto.vo.ledger.CreditCardVO;
import com.newco.marketplace.dto.vo.ledger.FMTransferFundsVO;
import com.newco.marketplace.dto.vo.ledger.ListAfterSearchVO;
import com.newco.marketplace.dto.vo.ledger.MarketPlaceTransactionVO;
import com.newco.marketplace.dto.vo.serviceorder.Buyer;
import com.newco.marketplace.dto.vo.serviceorder.FundingVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderCustomRefVO;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.AlertConstants;
import com.newco.marketplace.interfaces.LedgerConstants;
import com.newco.marketplace.interfaces.MPConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.interfaces.ProviderConstants;
import com.newco.marketplace.persistence.daoImpl.provider.TemplateDaoImpl;
import com.newco.marketplace.persistence.iDao.buyer.IBuyerResourceDao;
import com.newco.marketplace.persistence.iDao.document.ISimpleDocumentDao;
import com.newco.marketplace.persistence.iDao.financeManager.IFinanceManagerDAO;
import com.newco.marketplace.persistence.iDao.ledger.TransactionDao;
import com.newco.marketplace.persistence.iDao.piiThreshold.IPIIThresholdDao;
import com.newco.marketplace.persistence.iDao.provider.IContactDao;
import com.newco.marketplace.persistence.iDao.provider.ILocationDao;
import com.newco.marketplace.persistence.iDao.provider.IVendorHdrDao;
import com.newco.marketplace.persistence.iDao.provider.IVendorLocationDao;
import com.newco.marketplace.persistence.iDao.provider.IVendorResourceDao;
import com.newco.marketplace.persistence.iDao.so.buyer.BuyerDao;
import com.newco.marketplace.persistence.iDao.so.order.ServiceOrderDao;
import com.newco.marketplace.util.Cryptography;
import com.newco.marketplace.util.Cryptography128;
import com.newco.marketplace.util.PropertiesUtils;
import com.newco.marketplace.utils.AchConstants;
import com.newco.marketplace.utils.RandomGUID;
import com.newco.marketplace.utils.ServiceLiveStringUtils;
import com.newco.marketplace.vo.PaginationVO;
import com.newco.marketplace.vo.audit.WalletControlAuditVO;
import com.newco.marketplace.vo.provider.Contact;
import com.newco.marketplace.vo.provider.FinancialProfileVO;
import com.newco.marketplace.vo.provider.TemplateVo;
import com.newco.marketplace.vo.provider.VendorHdr;
import com.newco.marketplace.vo.provider.VendorResource;
import com.newco.marketplace.vo.receipts.SOReceiptsVO;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.service.ServiceConstants;
import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.common.vo.SLAccountVO;
import com.servicelive.lookup.ILookupBO;
import com.servicelive.lookup.dao.IAccountDao;
import com.servicelive.lookup.vo.BuyerLookupVO;
import com.servicelive.wallet.serviceinterface.IWalletBO;
import com.servicelive.wallet.serviceinterface.requestbuilder.WalletRequestBuilder;
import com.servicelive.wallet.serviceinterface.vo.WalletResponseVO;
import com.servicelive.wallet.serviceinterface.vo.WalletVO;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

// import com.newco.marketplace.dto.vo.IdnInformationVO;
/**
 * @author infosys
 *
 */
public class FinanceManagerBO extends ABaseBO implements IFinanceManagerBO {

	private WalletRequestBuilder walletRequestBuilder = new WalletRequestBuilder();

	private IFinanceManagerDAO financeManagerDAO;

	private ILedgerFacilityBO acctransBo;

	private TransactionDao transactionDao;

	private IContactDao contactDao;

	private ILocationDao locationDao;

	private IVendorHdrDao vendorHdrDao;

	private IVendorResourceDao vendorResourceDao;

	private IVendorLocationDao vendorLocationDao;

	private Cryptography cryptography;

	private EmailTemplateBOImpl emailTemplateBean;

	private TemplateDaoImpl templateDao;

	private ServiceOrderDao serviceOrderDao;

	private BuyerDao buyerDao;

	private IPIIThresholdDao piiThresholdDao;

	private IBuyerResourceDao buyerResourceDao;

	private static String fmReleaseDate = null;

	private IWalletBO walletBO;

	private ILookupBO lookupBO;

	private Cryptography128 cryptography128;

	private ISimpleDocumentDao simpleDocumentDao;
	
	private WalletControlAuditNotesBOImpl walletControlAuditNotesBO;

	public WalletControlAuditNotesBOImpl getWalletControlAuditNotesBO() {
		return walletControlAuditNotesBO;
	}

	public void setWalletControlAuditNotesBO(WalletControlAuditNotesBOImpl walletControlAuditNotesBO) {
		this.walletControlAuditNotesBO = walletControlAuditNotesBO;
	}

	public ISimpleDocumentDao getSimpleDocumentDao() {
		return simpleDocumentDao;
	}

	public void setSimpleDocumentDao(ISimpleDocumentDao simpleDocumentDao) {
		this.simpleDocumentDao = simpleDocumentDao;
	}

	public ILookupBO getLookupBO() {
		return lookupBO;
	}

	private IAccountDao accountDao;


	private String getFmReleaseDate() {
		if(fmReleaseDate == null) {
			fmReleaseDate = PropertiesUtils.getFMPropertyValue(Constants.FM_RELEASE_DATE);
		}
		return fmReleaseDate;
	}

	public void saveFinancialProfile(FinancialProfileVO financialProfileVO,
			Integer vendorId, boolean buyerFlag) throws BusinessServiceException, DBException {
		// KV refactoring the whole method to fix the badness....

		VendorHdr vendorHeader = new VendorHdr();
		Buyer buyer = new Buyer();

		try {
			if(buyerFlag) {  // tells us if the entity is a buyer
				vendorHeader = null; // to reuse the existing code....
				buyer=new Buyer();
				buyer.setBuyerId(vendorId);
				Integer i=vendorHdrDao.queryForBuyer(vendorId);
				buyer.setAccountContactId(i);				
			}
			else {
				buyer = null;
				vendorHeader = new VendorHdr();
				vendorHeader.setVendorId(vendorId);
				vendorHeader = vendorHdrDao.query(vendorHeader);
			}
		} catch (DBException dae) {
			logger.error("SQL Exception @FinanceManagerBO.getVendorDetails() while getting vendor hdr details"
					+ dae.getMessage());
			throw dae;
		} 
		Contact contact = new Contact();
		try {
			if (vendorHeader!=null && vendorHeader.getAccountContactId()!=null)
			{	
				contact.setContactId(vendorHeader.getAccountContactId());
				contact = contactDao.query(contact);
			}
			else if(buyer!=null && buyer.getContactId()!= null)
			{
				contact.setContactId(buyer.getAccountContactId());
				contact = contactDao.query(contact);
			}

			contact.setFirstName(financialProfileVO.getContact().getFirstName());
			contact.setMi(financialProfileVO.getContact().getMi());
			contact.setLastName(financialProfileVO.getContact().getLastName());
			contact.setEmail(financialProfileVO.getContact().getEmail());
			contact.setSuffix(financialProfileVO.getContact().getSuffix());
			contact.setTitle(financialProfileVO.getContact().getTitle());
			contact.setPhoneNo(financialProfileVO.getContact().getPhoneNo());
			contact.setExt(financialProfileVO.getContact().getExt());
			if (contact.getContactId().intValue() != -1){
				contactDao.update(contact);
			}

		} catch (DBException dae) {
			logger.error("SQL Exception @FinanceManagerBO.getVendorDetails() while getting contact details"
					+ dae.getMessage());
			throw dae;
		}
		try {
			if (contact.getContactId().intValue() == -1)
			{
				contact.setFirstName(financialProfileVO.getContact().getFirstName());
				contact.setMi(financialProfileVO.getContact().getMi());
				contact.setLastName(financialProfileVO.getContact().getLastName());
				contact.setEmail(financialProfileVO.getContact().getEmail());
				contact.setSuffix(financialProfileVO.getContact().getSuffix());
				contact.setTitle(financialProfileVO.getContact().getTitle());
				contact.setPhoneNo(financialProfileVO.getContact().getPhoneNo());
				contact.setExt(financialProfileVO.getContact().getExt());
				contact = contactDao.insert(contact);
				if (buyer!=null){
					buyer.setAccountContactId(contact.getContactId());
				}

			}
		} catch (DBException dae) {
			logger.error("SQL Exception @FinanceManagerBO.saveFinancialProfile() while saving contact details due to"
					+ dae.getMessage());
			throw dae;
		}

		if (vendorHeader!= null)
		{
			vendorHeader.setForeignOwnedInd(financialProfileVO.getForeignOwned());
			if (financialProfileVO.getForeignOwned() != null
					&& financialProfileVO.getForeignOwned().equals(new Integer(1))) {
				vendorHeader.setForeignOwnedPct(financialProfileVO.getForeignOwnedPercentage());
			}else{
				vendorHeader.setForeignOwnedPct(null);
			}

			if(financialProfileVO.getTaxPayerId() == null) {
				vendorHeader.setEinNo(null);
				vendorHeader.setEinNoEnc(null);
			}
			else {
				if(financialProfileVO.getTaxPayerId().startsWith("XXXXX")) {
					vendorHeader.setEinNoEnc(null);
				}
				else{
					vendorHeader.setEinNo(financialProfileVO.getTaxPayerId());
					//Encrypt the EinNo and then save
					CryptographyVO cryptographyVO = new CryptographyVO();
					cryptographyVO.setInput(financialProfileVO.getTaxPayerId());
					cryptographyVO.setKAlias(MPConstants.ENCRYPTION_KEY);
					cryptographyVO = cryptography.encryptKey(cryptographyVO);
					vendorHeader.setEinNoEnc(cryptographyVO.getResponse());				
				}

			}
			vendorHeader.setAccountContactId(contact.getContactId());
			vendorHeader.setVendorId(vendorId);
		}
		if (buyer != null)
		{
			buyer.setForeignOwnedInd(financialProfileVO.getForeignOwned());
			if (financialProfileVO.getForeignOwned() != null
					&& financialProfileVO.getForeignOwned().equals(new Integer(1))) {
				buyer.setForeignOwnedPct(financialProfileVO.getForeignOwnedPercentage());
			}else{
				buyer.setForeignOwnedPct(null);
			}

			if(financialProfileVO.getTaxPayerId() == null) {
				buyer.setEinNo(null);
				buyer.setEinNoEnc(null);
			}
			else{

				if(financialProfileVO.getTaxPayerId().startsWith("XXXXX")) {
					buyer.setEinNoEnc(null);
				}
				else {
					buyer.setEinNo(financialProfileVO.getTaxPayerId());
					//Encrypt the EinNo and then save
					CryptographyVO cryptographyVO = new CryptographyVO();
					cryptographyVO.setInput(financialProfileVO.getTaxPayerId());
					cryptographyVO.setKAlias(MPConstants.ENCRYPTION_KEY);
					cryptographyVO = cryptography.encryptKey(cryptographyVO);
					buyer.setEinNoEnc(cryptographyVO.getResponse());

				}

			}
			buyer.setAccountContactId(contact.getContactId());
			buyer.setBuyerId(vendorId);
		}
		try {
			if (vendorHeader !=null)
			{
				vendorHdrDao.updateFM(vendorHeader);
			}
			if (buyer!= null){
				vendorHdrDao.updateFMBuyer(buyer);
			}
		} catch (DBException dae) {
			logger.error("SQL Exception @FinanceManagerBO.saveFinancialProfile() while saving vendor hdr and vendorlocation details due to"
					+ dae.getMessage());
			throw dae;
		}
		logger.info(" inserting Financial Profile ");
	}

	public FinancialProfileVO getVendorDetails(String vendorId, boolean buyerFlag)
			throws BusinessServiceException, DBException {
		FinancialProfileVO financialProfileVO = new FinancialProfileVO();
		VendorHdr vendorHdr = new VendorHdr();

		Buyer buyer=new Buyer();

		try {

			if(buyerFlag) {
				vendorHdr = null;
				buyer =vendorHdrDao.queryForBuyer1(vendorId);
			}
			else {
				buyer = null;
				vendorHdr.setVendorId(Integer.valueOf(vendorId));
				vendorHdr = vendorHdrDao.query(vendorHdr);
			}

		} catch (DBException dae) {
			logger.error("SQL Exception @FinanceManagerBO.getVendorDetails() while getting vendor hdr details"
					+ dae.getMessage());
			throw dae;
		}
		Contact contact = new Contact();
		try {
			if (vendorHdr != null && vendorHdr.getAccountContactId() != null) {
				contact.setContactId(vendorHdr.getAccountContactId());
				contact = contactDao.query(contact);
			}else if(buyer != null && buyer.getAccountContactId() != null){
				contact.setContactId(buyer.getAccountContactId());
				contact = contactDao.query(contact);
			}

		} catch (DBException dae) {
			logger.error("SQL Exception @FinanceManagerBO.getVendorDetails() while getting contact details"
					+ dae.getMessage());
			throw dae;
		}
		if (contact != null) {
			financialProfileVO.setContact(contact);
		}
		if (vendorHdr != null && vendorHdr.getForeignOwnedInd() != null) {
			financialProfileVO.setForeignOwned(vendorHdr.getForeignOwnedInd());
			if (vendorHdr.getForeignOwnedPct() != null) {
				financialProfileVO.setForeignOwnedPercentage(vendorHdr
						.getForeignOwnedPct());
			}
		}
		if (buyer != null && buyer.getForeignOwnedInd() != null)
		{
			financialProfileVO.setForeignOwned(buyer.getForeignOwnedInd());
			if (buyer.getForeignOwnedPct() != null) {
				financialProfileVO.setForeignOwnedPercentage(buyer
						.getForeignOwnedPct());
			}
		}

		if (vendorHdr != null && vendorHdr.getEinNo() != null) {
			CryptographyVO cryptographyVO = new CryptographyVO();
			cryptographyVO.setInput(vendorHdr.getEinNo());
			cryptographyVO.setKAlias(MPConstants.ENCRYPTION_KEY);

			cryptographyVO = cryptography.decryptKey(cryptographyVO);
			financialProfileVO.setTaxPayerId(cryptographyVO.getResponse());

			financialProfileVO.setTaxPayerId(ServiceLiveStringUtils.maskString(financialProfileVO.getTaxPayerId(), 4, "X"));
		}
		if (buyer != null && buyer.getEinNo() != null) {
			CryptographyVO cryptographyVO = new CryptographyVO();
			cryptographyVO.setInput(buyer.getEinNo());
			cryptographyVO.setKAlias(MPConstants.ENCRYPTION_KEY);

			cryptographyVO = cryptography.decryptKey(cryptographyVO);
			financialProfileVO.setTaxPayerId(cryptographyVO.getResponse());

			financialProfileVO.setTaxPayerId(ServiceLiveStringUtils.maskString(financialProfileVO.getTaxPayerId(), 4, "X"));
		}

		if(vendorHdr != null){
			financialProfileVO.setProviderMaxWithdrawalLimit(
					vendorHdr.getProviderMaxWithdrawalLimit());

			financialProfileVO.setProviderMaxWithdrawalNo(
					vendorHdr.getProviderMaxWithdrawalNo());
		}

		return financialProfileVO;
	}

	public void saveFinancialProfile(FinancialProfileVO financialProfileVO)
			throws BusinessServiceException {
		try {
			financeManagerDAO.saveFinancialProfile(financialProfileVO);
			logger.info(" inserting Financial Profile ");
		} catch (Exception e) {
			logger.error("Exception thrown while inserting financial Profile",
					e);
			throw new BusinessServiceException(
					"Exception : While Inserting Financial Profile ", e);
		}
	}

	public IFinanceManagerDAO getFinanceManagerDAO() {
		return financeManagerDAO;
	}

	public void setFinanceManagerDAO(IFinanceManagerDAO financeManagerDAO) {
		this.financeManagerDAO = financeManagerDAO;
	}

	public IContactDao getContactDao() {
		return contactDao;
	}

	public void setContactDao(IContactDao contactDao) {
		this.contactDao = contactDao;
	}


	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.financeManager.IFinanceManagerBO#depositfunds(java.lang.Integer, java.lang.String, double, long, java.lang.String)
	 */

	public ProcessResponse depositfunds(Integer companyId, String roleType, FundingVO fundVo, long accountId, String user, String autoACHInd) {
		ProcessResponse procResp = new ProcessResponse();
		try {
			BuyerLookupVO buyerVO = lookupBO.lookupBuyer(companyId.longValue());
			double amount =  fundVo.getAmtToFund();

			WalletVO request = walletRequestBuilder.depositBuyerFundsWithCash(user, accountId, (long)companyId,
					buyerVO.getBuyerState(), buyerVO.getBuyerV1AccountNumber(), buyerVO.getBuyerV2AccountNumber(),
					fundVo.getSoId(), null, amount);

			WalletResponseVO response = walletBO.depositBuyerFundsWithCash(request);

			if (!response.isError()) { // Saeid added this check
				sendDepositConfirmationEmail(companyId, amount, response.getTransactionId().intValue(),roleType );
			}
			procResp.appendMessages( response.getErrorMessages() );
		} catch (BusinessServiceException e) {
			logger.error("depositfunds-->Exception-->", e);
			procResp.setMessage("We have encountered an error processing your deposit. We apologize for the inconvenience. Please try again later");		
		}
		catch (Exception e) {
			logger.error("Exception thrown while depsiting funds", e);
		}
		return procResp;
	}


	/**
	 * Description: Sets funding type on <code>MarketPlaceTransactionVO</code> based on the 
	 * buyer's ID
	 * @param companyId
	 * @param service
	 * @throws DataServiceException
	 */
	private void setTransVOFundingType(Integer companyId,
			MarketPlaceTransactionVO service) throws DataServiceException {
		if(companyId!= null){
			Buyer buyer = serviceOrderDao.getBuyerAttributes(companyId);
			if(buyer != null){
				if(buyer.getFundingTypeId()!=null){
					service.setFundingTypeId(buyer.getFundingTypeId());
				}
			}
		}
	}

	public void sendDepositConfirmationEmail(Integer companyId, double amount, Integer ledgerId, String roleType)
	{
		EmailVO emailVO = new EmailVO();
		TemplateVo template = new TemplateVo();
		Contact contact = new Contact();
		try{
			contact = contactDao.getByBuyerId(companyId);
		}catch(Exception e){
			logger.error("Exception thrown while getting buyerresource or contact details", e);
		}
		try {
			/*Commented this code as per SL-9248. */
			//template = templateDao.getTemplate(AchConstants.EMAIL_TEMPLATE_BUYER_WITHDRAW_FUNDS);
			template = templateDao.getTemplate(AchConstants.EMAIL_TEMPLATE_ACH_ACK);
			if(contact != null && contact.getEmail() != null && !"".equals(contact.getEmail())){
				emailVO.setTo(contact.getEmail());
				emailVO.setTemplateId(template.getTemplateId());	
				emailVO.setSubject(template.getTemplateSubject());
				emailVO.setFrom(AlertConstants.SERVICE_LIVE_MAILID);
				emailVO.setMessage(template.getTemplateSource());
				emailVO.setFirstName(contact.getFirstName());
				emailVO.setLastName(contact.getLastName());
				emailTemplateBean.sendWithdrawConfirmEmail(template,emailVO, ledgerId.toString(), amount, 
						getFmReleaseDate(),AchConstants.EMAIL_TEMPLATE_ACH_DEPOSIT_FUNDS,roleType);
			}
		} catch (Exception e) {
			logger.error("Exception thrown while getting template details or Sending Mail", e);
		}

	}

	public void sendCCDepositConfirmationEmail(CreditCardVO ccVo, double amount, Integer ledgerId,String roleType) {
		int companyId = ccVo.getEntityId();
		EmailVO emailVO = new EmailVO();
		TemplateVo template = new TemplateVo();
		Contact contact = new Contact();
		try{
			contact = contactDao.getByBuyerId(companyId);
		}catch(Exception e){
			logger.error("Exception thrown while getting buyerresource or contact details", e);
		}
		try {
			template = templateDao.getTemplate(AchConstants.EMAIL_TEMPLATE_BUYER_DEPOSIT_FUNDS);
			if(contact != null && contact.getEmail() != null && !"".equals(contact.getEmail())){
				emailVO.setTo(contact.getEmail());
				emailVO.setSubject(template.getTemplateSubject());
				emailVO.setFrom(AlertConstants.SERVICE_LIVE_MAILID);
				emailVO.setMessage(template.getTemplateSource());
				emailVO.setFirstName(contact.getFirstName());
				emailVO.setLastName(contact.getLastName());
				emailTemplateBean.sendCCWithdrawConfirmEmail(template,emailVO, ccVo, ledgerId, amount, getFmReleaseDate(),roleType);
			}
		} catch (Exception e) {
			logger.error("Exception thrown while getting template details or Sending Mail", e);
		}

	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.financeManager.IFinanceManagerBO#sendBuyerPostingFeeEmail(java.lang.Integer, java.lang.String)
	 */
	public void sendBuyerPostingFeeEmail(Integer entityId, String soId) {

		EmailVO emailVO = new EmailVO();
		TemplateVo template = new TemplateVo();
		Contact contact = new Contact();
		FinancialProfileVO fmProfileVO = new FinancialProfileVO();

		SOReceiptsVO soReceiptsVO;
		String ledgerTransIdPost = "No Transaction Id";
		Double transAmtPost = 0.0;
		String ledgerTranIdRes = null;
		Double transAmtRes = 0.0;

		try {
			contact = contactDao.getByBuyerId(entityId);	
		} catch(Exception e) {
			logger.error("Exception thrown while getting buyerresource or contact details", e);
		}

		try {
			// get so posting fee transaction id and amount
			soReceiptsVO = getSOTransactionReceiptInfo(entityId, soId,OrderConstants.LEDGER_ENTITY_TYPE_ID_BUYER, 
					OrderConstants.LEDGER_ENTRY_RULE_ID_SO_POSTING_FEE);
			if(soReceiptsVO != null) {
				ledgerTransIdPost = soReceiptsVO.getTransactionID().toString();
				transAmtPost = soReceiptsVO.getTransactionAmount();
			}
			// get so spend limit transaction id and amount
			soReceiptsVO = getSOTransactionReceiptInfo(entityId, soId,OrderConstants.LEDGER_ENTITY_TYPE_ID_BUYER, 
					OrderConstants.LEDGER_ENTRY_RULE_ID_SO_SPEND_LIMIT);
			if(soReceiptsVO != null) {
				ledgerTranIdRes = soReceiptsVO.getTransactionID().toString();
				transAmtRes = soReceiptsVO.getTransactionAmount();
			}
		} catch(Exception e) {
			logger.error("Exception thrown while getting posting receipt details", e);
		}

		try {
			template = templateDao.getTemplate(AchConstants.EMAIL_TEMPLATE_BUYER_POSTING_FEE);
			if(contact != null && contact.getEmail() != null && !"".equals(contact.getEmail())){
				emailVO.setTo(contact.getEmail()+";"+fmProfileVO.getContact().getEmail());

				emailVO.setSubject(template.getTemplateSubject());
				emailVO.setFrom(AlertConstants.SERVICE_LIVE_MAILID);
				emailVO.setMessage(template.getTemplateSource());
				emailVO.setFirstName(contact.getFirstName());
				emailVO.setLastName(contact.getLastName());
				emailVO.setTemplateId(template.getTemplateId());
				emailTemplateBean.sendBuyerPostingFeeEmail(emailVO, soId, ledgerTransIdPost, transAmtPost, ledgerTranIdRes, transAmtRes);
			}
		} catch (Exception e) {
			logger.error("Exception thrown while getting template details or Sending Mail", e);
		}

	}


	/**
	 * @param entityId
	 * @return
	 */
	public List<Account> getAccountDetailsActiveAndInactive(int entityId) {
		List<Account> accounts = null;
		try {
			accounts = transactionDao.accountdetailsall(entityId);
			for(Account account: accounts){
				decryptAccountAndRoutingNo(account);
			}
		} 
		catch (Exception e) {
			logger.error("Exception thrown while inserting/updating account details", e);
		}
		return accounts;

	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.financeManager.IFinanceManagerBO#accountDetails(int)
	 */
	public List<Account> accountDetails(int entityId) {
		List<Account> accounts = null;
		try {
			accounts = transactionDao.accountdetails(entityId);
			for(Account account: accounts){
				decryptAccountAndRoutingNo(account);
			}
		} 
		catch (Exception e) {
			logger.error("Exception thrown while inserting/updating account details", e);
		}

		return accounts;
	}

	public SLAccountVO getActiveAccountDetails(long entityId) throws BusinessServiceException{
		SLAccountVO account = null;
		try {
			account = lookupBO.getActiveAccountDetails(entityId);
		} 
		catch (Exception e) {
			logger.error("Exception thrown while inserting/updating account details", e);
		}

		return account;
	}

	public Account accountDetailsForSo(int entityId, String accountId) {
		List<SLAccountVO> accounts = null;
		Account cardDetails = null;
		try {
			accounts = accountDao.getAccountDetailsAll(entityId);
			for(Account account: accounts){
				decryptAccountAndRoutingNo(account);
				if (account.getAccount_id().equals(new Long(accountId))){
					cardDetails = account;
					break;
				}
			}
		} 
		catch (Exception e) {
			logger.error("Exception thrown while inserting/updating account details", e);
		}

		return cardDetails;
	}


	/**
	 * Description: Setting decrypted routing and account numbers on the account object
	 * @param account
	 */
	private void decryptAccountAndRoutingNo(Account account) {
		CryptographyVO cryptographyVO = new CryptographyVO();
		cryptographyVO.setInput(account.getAccount_no());


		//Commenting the code for new 128decryption
		//cryptographyVO.setKAlias(MPConstants.ENCRYPTION_KEY);
		//cryptographyVO = cryptography.decryptKey(cryptographyVO);
		cryptographyVO.setKAlias(MPConstants.CC_ENCRYPTION_KEY);
		cryptographyVO = cryptography128.decryptKey128Bit(cryptographyVO);

		account.setAccount_no(cryptographyVO.getResponse());

		cryptographyVO = new CryptographyVO();
		cryptographyVO.setInput(account.getRouting_no());
		cryptographyVO.setKAlias(MPConstants.ENCRYPTION_KEY);
		cryptographyVO = cryptography.decryptKey(cryptographyVO);
		account.setRouting_no(cryptographyVO.getResponse());
	}

	public List<Account> accountDetailsIncludeCC(int entityId) {
		List<Account> accounts = null;
		try {
			accounts = transactionDao.accountDetailsIncludeCC(entityId);
			// Change for SL-20050
			CryptographyVO cryptographyVO = new CryptographyVO();
			cryptographyVO.setKAlias(MPConstants.CC_ENCRYPTION_KEY);            
			SecretKey secret = cryptography128.generateSecretKey(cryptographyVO);
			for(Account account: accounts){
				cryptographyVO = new CryptographyVO();
				//SL-20853
				if(null != account && StringUtils.isNotBlank(account.getMaskedAccountNo())){
					logger.info("Masked Account Number is displaying"+account.getMaskedAccountNo());
					account.setAccount_no(ServiceLiveStringUtils.maskStringAlphaNum(account.getMaskedAccountNo(), 4, "X"));
				}
				else{
					cryptographyVO.setInput(account.getAccount_no());

					//Commenting the code for SL-18789
					//cryptographyVO.setKAlias(MPConstants.ENCRYPTION_KEY);
					//cryptographyVO = cryptography.decryptKey(cryptographyVO);

					// Change for SL-20050
					cryptographyVO = cryptography128.decryptWithSecretKey(cryptographyVO, secret);

					account.setAccount_no(cryptographyVO.getResponse());
				}

				if(account.getAccount_type_id() != null  && !account.getAccount_type_id().equals(new Long(30))) {
					cryptographyVO = new CryptographyVO();
					cryptographyVO.setInput(account.getRouting_no());
					cryptographyVO.setKAlias(MPConstants.ENCRYPTION_KEY);
					cryptographyVO = cryptography.decryptKey(cryptographyVO);
					account.setRouting_no(cryptographyVO.getResponse());
				}

			}
		} 
		catch (Exception e) {
			logger.error("Exception thrown while inserting/updating account details", e);
		}

		return accounts;
	}

	public Account getAccountDetails(Integer entityId) {
		Account account = new Account();
		try {
			account = transactionDao.getAccountDetails(entityId);
			if(account != null && account.getAccount_no() != null){
				decryptAccountAndRoutingNo(account);
			}
		} catch (Exception e) {
			logger.error("Exception thrown while fetching account details", e);
		}

		return account;

	}


	public Account getEscheatAccountDetails(Integer entityId) {
		Account account = new Account();
		try {
			account = transactionDao.getEscheatAccountDetails(entityId);
			if(account != null && account.getAccount_no() != null){
				decryptAccountAndRoutingNo(account);
			}
		} catch (Exception e) {
			logger.error("Exception thrown while fetching account details", e);
		}

		return account;

	}

	public void saveAccountDetails(Account account) throws DataServiceException {
		try {
			Account oldAccount = new Account();
			if (account.getAccount_id() != null) {
				oldAccount.setAccount_id(account.getAccount_id());
				oldAccount.setActive_ind(false);
				oldAccount.setEnabled_ind(true);
				transactionDao.updateDeactivateAccountInfo(oldAccount);

			}

			// Encrypt the accountNo and routingNo
			CryptographyVO cryptographyVO = new CryptographyVO();
			cryptographyVO.setInput(account.getAccount_no());

			//Commenting the code for SL-18789
			//cryptographyVO.setKAlias(MPConstants.ENCRYPTION_KEY);
			//cryptographyVO = cryptography.encryptKey(cryptographyVO);
			cryptographyVO.setKAlias(MPConstants.CC_ENCRYPTION_KEY);
			cryptographyVO = cryptography128.encryptKey128Bit(cryptographyVO);

			account.setAccount_no(cryptographyVO.getResponse());

			cryptographyVO = new CryptographyVO();
			cryptographyVO.setInput(account.getRouting_no());
			cryptographyVO.setKAlias(MPConstants.ENCRYPTION_KEY);
			cryptographyVO = cryptography.encryptKey(cryptographyVO);
			account.setRouting_no(cryptographyVO.getResponse());

			RandomGUID randomGUID = new RandomGUID();
			account.setAccount_id(randomGUID.generateGUID());
			account.setActive_ind(true);
			account.setEnabled_ind(true);
			transactionDao.saveAccountInfo(account);
			//update auto funding service with new account id.
			int countInd = 0;
			countInd = transactionDao.getEnabledIndCount(account
					.getOwner_entity_id());
			if (1 <= countInd) {
				updateAccountId(account);
			}
			
			// Validation for funding type 90
			int fundingTypeId = getFundingTypeId(account.getOwner_entity_id());
			if (fundingTypeId == OrderConstants.PREFUNDED) {
				account.setSaveAutoFundInd(OrderConstants.ENABLED_IND);
			}
			
			// Saves auto funding details of the buyer, if Auto Funding
			// Indicator is 1
			if (account.getSaveAutoFundInd() == OrderConstants.ENABLED_IND) {
				saveAutoFundingDetails(account);
			}

		} catch (Exception e) {
			logger.error(
					"Exception thrown while inserting/updating account details",e);
		}

	}

	private void updateAccountId(Account account) throws BusinessServiceException {
		try {
			transactionDao.updateAccountId(account);
		} catch (Exception e) {
			logger.error(
					"Exception thrown while updating account_id in auto_funding_service",
					e);
			throw new BusinessServiceException(
					"Exception : updating account_id in auto_funding_service ",
					e);
		}

	}
	
	/*
	 * This Method fetch funding_type_id from buyer table
	 */
	private int getFundingTypeId(long buyer_id) {
		int funding_type_id = 0;
		try {
			funding_type_id = transactionDao.getBuyerFundingTypeId(buyer_id);
		} catch (Exception e) {
			logger.error("Exception in getFundingTypeId() " + e);
		}
		return funding_type_id;
	}

	/**
	 * This method  calls the data access method to save the auto 
	 * funding details of the buyer
	 * @param  Account account 
	 * @throws Exception
	 */
	public void saveAutoFundingDetails(Account account) throws BusinessServiceException {
		try {
			int count=0;
			count = transactionDao.getAutoFundingDetails(account.getOwner_entity_id());
			if(count == 0){
				insertAutoFundingDetails(account);
			}else{
				updateAutoFundingDetails(account);
			}
			updateBuyerFundingTypeId(account);
		} catch (Exception e) {
			logger.error("Exception thrown while Getting the return Account Count",
					e);
			throw new BusinessServiceException(
					"Exception : Getting the return Account Count ", e);
		}
	}
	/*
	 * method for updating funding type Id in buyer table.
	 * 
	 */
	private void updateBuyerFundingTypeId(Account account) throws BusinessServiceException {

		if(account.getAutoACHInd()==1){
			account.setFundingTypeId(OrderConstants.FUNDING_TYPE_ID_TRUE);
		}
		else{
			account.setFundingTypeId(OrderConstants.FUNDING_TYPE_ID_FALSE);
		}
		try{
			transactionDao.updateBuyerFundingTypeId(account);
		} 
		catch (Exception e)
		{
			logger.error("Exception thrown while upadting the Buyer Table",e);

			throw new BusinessServiceException(
					"Exception : Getting the return Account Count ", e);

		}
	}

	/**
	 * This method  calls the data access method to retrieve the value of
	 * Auto Funding Indicator 
	 * @param  vendorId 
	 * @return Account
	 */
	public Account getAutoFundingIndicator(Integer vendorId) throws BusinessServiceException {
		SLAccountVO acct = null;
		try {			
			acct = lookupBO.lookupAccount(vendorId.longValue());
		} catch (Exception e) {
			logger.error("Exception thrown while Getting the return Account Count",	e);
			throw new BusinessServiceException("Exception : Getting the return Account Count ", e);
		}
		if (acct == null) {
			acct = new SLAccountVO();
		}
		return acct;
	}

	/**
	 * This method  calls the data access method to update the auto 
	 * funding details of the buyer, if the record is already present in DB
	 * @param  Account account 
	 * @throws Exception
	 */
	public void updateAutoFundingDetails(Account account) throws BusinessServiceException {

		try {
			transactionDao.updateAutoFundingDetails(account);
		} catch (Exception e) {
			logger.error("Exception thrown while Getting the return Account Count",
					e);
			throw new BusinessServiceException(
					"Exception : Getting the return Account Count ", e);
		}

	}

	/**
	 * This method  calls the data access method to insert the auto 
	 * funding details of the buyer, if the record is not present in DB
	 * @param  Account account 
	 * @throws Exception
	 */
	public void insertAutoFundingDetails(Account account) throws BusinessServiceException {

		try {
			transactionDao.insertAutoFundingDetails(account);
		} catch (Exception e) {
			logger.error("Exception thrown while Getting the return Account Count",
					e);
			throw new BusinessServiceException(
					"Exception : Getting the return Account Count ", e);
		}

	}
	public double getavailableBalance(AjaxCacheVO avo)
	{
		double availableBalance=0.0;
		try{
			availableBalance = acctransBo.getAvailableBalance(avo);
		}catch (Exception e) {
			logger.error("Exception thrown while getting available balance", e);
		}
		return availableBalance;
	}
	
	public double getWalletHoldBalance(Integer entityId){
		double walletHoldBalance = 0.0;
		try{
			walletHoldBalance=transactionDao.getWalletHoldBalance(entityId);
		} catch(Exception e){
			logger.error("Exception in fetching wallet hold balance");
		}
		return walletHoldBalance;
	}

	public void updateEntityWalletRemainingBalance(double remainingHoldBalance, Integer entityId, Integer walletControlId) {
		try {
			transactionDao.updateEntityWalletRemainingBalance(remainingHoldBalance, entityId, walletControlId);
		} catch (Exception e) {
			logger.error("Exception in update remaining wallet hold balance");
			e.printStackTrace();
		}
	}

	public double getcurrentBalance(AjaxCacheVO avo)
	{
		double currentBalance=0.0;
		try{
			currentBalance = acctransBo.getCurrentBalance(avo);
		}catch (Exception e) {
			logger.error("Exception thrown while getting current balance", e);
		}
		return currentBalance;
	}

	public List<AccountHistoryVO> getExportRecords(AccountHistoryVO ahVO)
	{
		List<AccountHistoryVO> acctHistory = null;
		int totalRecordCount = 0;
		try{
			if((ahVO.getFromDate()==null && ahVO.getToDate()!=null) ||(ahVO.getFromDate()!=null && ahVO.getToDate()==null) || ahVO.getDateRangeCheck()!=null && ahVO.getDateRangeCheck().equalsIgnoreCase(OrderConstants.INVALID_SEARCH_TYPE)){
				return acctHistory;
			}			
			if(ahVO.getToDate() == null || ahVO.getFromDate() == null) {
				String pageLoadDateRestriction=financeManagerDAO.getAppPropertiesValue(OrderConstants.WALLET_PAGE_LOAD_DATE_RESTRICTION);
				Calendar now = Calendar.getInstance();
				ahVO.setToDate(new java.sql.Date(now.getTime().getTime()));
				now.add(Calendar.DAY_OF_MONTH,-Integer.parseInt(pageLoadDateRestriction));
				ahVO.setFromDate(new java.sql.Date(now.getTime().getTime()));
				totalRecordCount = transactionDao.getAccountHistoryCount(ahVO);
				if(totalRecordCount > OrderConstants.ACCOUNT_HISTORY_LIMIT_COUNT)
					totalRecordCount=OrderConstants.ACCOUNT_HISTORY_LIMIT_COUNT;
			} else {
				totalRecordCount = transactionDao.getAccountHistoryCount(ahVO);
			}
			String maxWalletHistoryExportLimit=financeManagerDAO.getAppPropertiesValue(OrderConstants.MAX_WALLET_HISTORY_EXPORT_LIMIT);
			if(maxWalletHistoryExportLimit!=null && totalRecordCount > Integer.parseInt(maxWalletHistoryExportLimit)){
				ahVO.setNumberOfRecords(Integer.parseInt(maxWalletHistoryExportLimit));
			}else{
				ahVO.setNumberOfRecords(totalRecordCount);
			}
			acctHistory = transactionDao.getAccountHistoryFMOverview(ahVO);
		}catch(Exception e){
			e.printStackTrace();
		}
		return acctHistory;
	}

	public AccountHistoryResultVO getAccountOverviewHistoryFMOverview(AccountHistoryVO ahVO,PagingCriteria pagingCriteria){
		List<AccountHistoryVO> acctHistory = null;
		AccountHistoryResultVO accountHistoryResultVO = new AccountHistoryResultVO();
		try{
			ABaseCriteriaHandler criteriaHandler = new ABaseCriteriaHandler();
			PaginationVO pagination = null;
			int totalRecordCount = 0;
			String maxSearchDaysWalletHistory=financeManagerDAO.getAppPropertiesValue(OrderConstants.MAX_SEARCH_DAYS_WALLET_HISTORTY);
			accountHistoryResultVO.setMaxSearchDaysWalletHistory(maxSearchDaysWalletHistory);
			if((ahVO.getFromDate()==null && ahVO.getToDate()!=null) ||(ahVO.getFromDate()!=null && ahVO.getToDate()==null) || (ahVO.getDateRangeCheck()!=null && ahVO.getDateRangeCheck().equalsIgnoreCase(OrderConstants.INVALID_SEARCH_TYPE))){
				return accountHistoryResultVO;
			}

			if(ahVO.getToDate() == null || ahVO.getFromDate() == null) {
				String pageLoadDateRestriction=financeManagerDAO.getAppPropertiesValue(OrderConstants.WALLET_PAGE_LOAD_DATE_RESTRICTION);
				Calendar now = Calendar.getInstance();
				ahVO.setToDate(new java.sql.Date(now.getTime().getTime()));
				now.add(Calendar.DAY_OF_MONTH,-Integer.parseInt(pageLoadDateRestriction));
				ahVO.setFromDate(new java.sql.Date(now.getTime().getTime()));
				totalRecordCount = transactionDao.getAccountHistoryCount(ahVO);
				if(totalRecordCount > OrderConstants.ACCOUNT_HISTORY_LIMIT_COUNT)
					totalRecordCount=OrderConstants.ACCOUNT_HISTORY_LIMIT_COUNT;
			} else {
				totalRecordCount = transactionDao.getAccountHistoryCount(ahVO);
			}
			String maxWalletHistoryExportLimit=financeManagerDAO.getAppPropertiesValue(OrderConstants.MAX_WALLET_HISTORY_EXPORT_LIMIT);
			accountHistoryResultVO.setWalletMaxExportLimit(maxWalletHistoryExportLimit);
			if(maxWalletHistoryExportLimit!=null && totalRecordCount > Integer.parseInt(maxWalletHistoryExportLimit)){
				accountHistoryResultVO.setExportMessageCheck("true");
			}
			accountHistoryResultVO.setTotalCount(totalRecordCount);
			pagination = criteriaHandler._getPaginationDetail(totalRecordCount,
					pagingCriteria.getPageSize(), pagingCriteria.getStartIndex(), 
					pagingCriteria.getEndIndex());

			ahVO.setStartIndex(totalRecordCount > 0 ? pagination.getStartIndex()-1 : pagination.getStartIndex());
			ahVO.setNumberOfRecords(pagination.getPageSize());
			long startTime3 = System.currentTimeMillis();
			acctHistory = transactionDao.getAccountHistoryFMOverview(ahVO);
			logger.info("zfmoverviewtransactionDao.getAccountHistoryFMOverview"+(System.currentTimeMillis()-startTime3));

			//If an sladmin/buyerAdmin we need to give the capability to display the card / bank 
			//details used for each transaction
			if(ahVO.isSlAdminInd() || ahVO.isBuyerAdminInd())
			{
				try {
					long startTime4 = System.currentTimeMillis();

					CryptographyVO cryptographyVO = new CryptographyVO();
					cryptographyVO.setKAlias(MPConstants.CC_ENCRYPTION_KEY);	            
					SecretKey secret = cryptography128.generateSecretKey(cryptographyVO);

					for(int i=0;i<acctHistory.size();i++)
					{
						AccountHistoryVO acctHistoryVO = acctHistory.get(i);
						if(acctHistoryVO != null && acctHistoryVO.getAccountNumber() != null){
							cryptographyVO = new CryptographyVO();
							cryptographyVO.setInput(acctHistoryVO.getAccountNumber());

							//Commenting the code for SL-18789
							//cryptographyVO.setKAlias(MPConstants.ENCRYPTION_KEY);
							//cryptographyVO = cryptography.decryptKey(cryptographyVO);
							//cryptographyVO.setKAlias(MPConstants.CC_ENCRYPTION_KEY);
							cryptographyVO = cryptography128.decryptWithSecretKey(cryptographyVO, secret);

							acctHistoryVO.setAccountNumber(ServiceLiveStringUtils.maskString(cryptographyVO.getResponse() , 4, "X"));
							acctHistory.set(i,acctHistoryVO);
						}
					}
					logger.info("zfmoverviewforLoop"+(System.currentTimeMillis()-startTime4));

				} catch (Exception e) {
					logger.error("Exception thrown while fetching account details", e);
				}
			}
			accountHistoryResultVO.setAcctHistory(acctHistory);
			accountHistoryResultVO.setPaginationVO(pagination);

		}catch(Exception e){
			e.printStackTrace();
		}
		return accountHistoryResultVO;
	}



	public List<AccountHistoryVO> getAccountOverviewHistory(AccountHistoryVO ahVO){
		List<AccountHistoryVO> acctHistory = null;
		try{			
			if(ahVO.getToDate() == null || ahVO.getFromDate() == null) {
				String pageLoadDateRestriction=financeManagerDAO.getAppPropertiesValue(OrderConstants.WALLET_PAGE_LOAD_DATE_RESTRICTION);
				Calendar now = Calendar.getInstance();
				ahVO.setToDate(new java.sql.Date(now.getTime().getTime()));
				now.add(Calendar.DAY_OF_MONTH,-Integer.parseInt(pageLoadDateRestriction));
				ahVO.setFromDate(new java.sql.Date(now.getTime().getTime()));
			}			
			long startTime5=System.currentTimeMillis();
			acctHistory = transactionDao.getAccountHistory(ahVO);
			logger.info("zfmoverviewtransactionDao.getAccountHistory(ahVO"+(System.currentTimeMillis()-startTime5));

			//If an sladmin/buyerAdmin we need to give the capability to display the card / bank 
			//details used for each transaction
			if(ahVO.isSlAdminInd() || ahVO.isBuyerAdminInd())
			{
				try {
					long startTime6=System.currentTimeMillis();

					CryptographyVO cryptographyVO = new CryptographyVO();
					cryptographyVO.setKAlias(MPConstants.CC_ENCRYPTION_KEY);
					SecretKey secret = cryptography128.generateSecretKey(cryptographyVO);

					for(int i=0;i<acctHistory.size();i++)
					{
						AccountHistoryVO acctHistoryVO = acctHistory.get(i);
						if(acctHistoryVO != null && acctHistoryVO.getAccountNumber() != null){
							cryptographyVO = new CryptographyVO();
							cryptographyVO.setInput(acctHistoryVO.getAccountNumber());

							//Commenting the code for SL-18789
							//cryptographyVO.setKAlias(MPConstants.ENCRYPTION_KEY);
							//cryptographyVO = cryptography.decryptKey(cryptographyVO);
							//cryptographyVO.setKAlias(MPConstants.CC_ENCRYPTION_KEY);
							cryptographyVO = cryptography128.decryptWithSecretKey(cryptographyVO, secret);

							acctHistoryVO.setAccountNumber(ServiceLiveStringUtils.maskString(cryptographyVO.getResponse() , 4, "X"));
							acctHistory.set(i,acctHistoryVO);
						}
					}
					logger.info("zfmoverviewcryptol"+(System.currentTimeMillis()-startTime6));


				} catch (Exception e) {
					logger.error("Exception thrown while fetching account details", e);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return acctHistory;
	}

	public ByteArrayOutputStream getExportToExcel(List<ListAfterSearchVO> historyVO,ByteArrayOutputStream outFinal) throws IOException, WriteException {

		WritableWorkbook workbook = Workbook.createWorkbook(outFinal);
		WritableSheet sheet = workbook.createSheet("First Sheet", 0);
		sheet.setColumnView(0,30);
		sheet.setColumnView(1,30);
		sheet.setColumnView(2,30);
		sheet.setColumnView(3,30);
		sheet.setColumnView(4,30);
		sheet.setColumnView(5,30);
		sheet.setColumnView(6,30);

		// Create a cell format for Arial 10 point font 
		WritableFont arial10font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD); 
		WritableCellFormat arial10format = new WritableCellFormat (arial10font);	
		arial10format.setAlignment(Alignment.CENTRE);
		arial10format.setBackground(Colour.ICE_BLUE);
		arial10format.setBorder(Border.ALL,BorderLineStyle.THIN);
		WritableCellFormat cellformat = new WritableCellFormat ();
		cellformat.setAlignment(Alignment.RIGHT);

		Label transNo = new Label(0, 0, "Transaction #",arial10format);
		Label transDate = new Label(1, 0, "Date/Time",arial10format);
		Label transType = new Label(2, 0, "Type",arial10format);
		Label transSo = new Label(3, 0, "Service Order #",arial10format);
		Label transStatus = new Label(4, 0, "Status",arial10format);
		Label transAmount = new Label(5, 0, "Amount",arial10format);
		Label transbalance = new Label(6, 0, "Balance",arial10format);
		sheet.addCell(transNo);
		sheet.addCell(transDate);
		sheet.addCell(transType);
		sheet.addCell(transSo);
		sheet.addCell(transStatus);
		sheet.addCell(transAmount);
		sheet.addCell(transbalance);

		for (int i=0; i < historyVO.size(); i++){

			Number transactionNo = new Number(0, (1+i), historyVO.get(i).getTransactionNumber(),cellformat);
			Label transactionnewDate = new Label(1, (1+i), historyVO.get(i).getModifiedDate(),cellformat);
			Label transactionType = new Label(2, (1+i), historyVO.get(i).getType(),cellformat);
			Label transactionSoId = new Label(3, (1+i), historyVO.get(i).getSoId(),cellformat);
			Label transactionStatus = new Label(4, (1+i), historyVO.get(i).getStatus(),cellformat);
			Label transactionAmount = new Label(5, (1+i), historyVO.get(i).getAmount(),cellformat);
			Label transactionbalance = new Label(6, (1+i), historyVO.get(i).getBalance(),cellformat);
			sheet.addCell(transactionNo);
			sheet.addCell(transactionnewDate);
			sheet.addCell(transactionType);
			sheet.addCell(transactionSoId);
			sheet.addCell(transactionStatus);
			sheet.addCell(transactionAmount);
			sheet.addCell(transactionbalance);
		}


		workbook.write();
		workbook.close();
		outFinal.close();

		return outFinal;

	}

	public Integer returnAccountCount(Long accountId) throws BusinessServiceException{
		Integer accountCount = null;
		Account account = new Account();
		account.setAccount_id(accountId);
		account.setActive_ind(true);
		account.setEnabled_ind(true);
		try {
			accountCount = transactionDao.returnAccountCount(account);
			logger.info(" Getting all the return Accounts Count");
		} catch (Exception e) {
			logger.error("Exception thrown while Getting the return Account Count",
					e);
			throw new BusinessServiceException(
					"Exception : Getting the return Account Count ", e);
		}
		return accountCount;
	}
	public boolean updateAccoutEnableInd(Long accountId, boolean enabledInd) throws BusinessServiceException{
		boolean successInd;
		Boolean accountEnableInd = new Boolean(enabledInd);
		try {
			successInd = transactionDao.updateAccoutEnableInd(accountId, accountEnableInd);
			logger.info(" Updating the Enable Indicator in the Account Table");
		} catch (Exception e) {
			logger.error("Exception thrown while Updating the enable ind in Account",
					e);
			throw new BusinessServiceException(
					"Exception : while updating the enable indicator ", e);
		}
		return successInd;
	}


	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.financeManager.IFinanceManagerBO#transferSLBucks(com.newco.marketplace.dto.vo.ledger.FMTransferFundsVO)
	 */
	public ProcessResponse transferSLBucks(FMTransferFundsVO transferFundsVO) throws BusinessServiceException {
		ProcessResponse processResponse = new ProcessResponse();
		boolean hasAvailableFunds = false;
		double availableBalance =getBalanceForSLBucksTransfer(transferFundsVO);


		//first do the validation of available balance...

		if(availableBalance >= transferFundsVO.getAmount().doubleValue())
			hasAvailableFunds = true;

		if(hasAvailableFunds)
		{    //amount available for transfer....
			MarketPlaceTransactionVO service = new MarketPlaceTransactionVO();
			service.setTransactionNote(transferFundsVO.getNote());
			service.setAdminUser(transferFundsVO.getUser());
			service.setUserName(transferFundsVO.getUser());
			boolean isBuyer = false;
			boolean isProvider = false;

			String entityType = transferFundsVO.getRoleType();
			//code change for SLT-2228
			Integer reasonCodeId = transferFundsVO.getReasonCode().intValue();
			logger.info("reasoncode:"+reasonCodeId);
			//SLT-2367 
			WalletControlAuditVO walletControlAuditVO=new WalletControlAuditVO();
			walletControlAuditVO.setVendorId(transferFundsVO.getFromAccount());
			walletControlAuditVO.setEscheatmentTransferReasonCodeId(transferFundsVO.getReasonCode().intValue());
			walletControlAuditVO.setAmount(transferFundsVO.getAmount().doubleValue());
			walletControlAuditVO.setReviewedBy(transferFundsVO.getUser());
			walletControlAuditVO.setSendEmailNotice(false);
			//SLT-2367
			if (transferFundsVO.getReasonCode().intValue() == LedgerConstants.TRANSFER_REASONCODE_ESCHEATMENT 
					) { // debit transfers from...buyer/providers...

				if (entityType.equalsIgnoreCase(OrderConstants.BUYER) ||
						entityType.equalsIgnoreCase(OrderConstants.SIMPLE_BUYER)) {
					service.setBusinessTransId(LedgerConstants.BUSINESS_TRANSACTION_SLA_ESCHEATMENT_SLB_FROM_BUYER);
					service.setBuyerID(transferFundsVO.getFromAccount());
					acctransBo.adminEscheatmentSLBfromBuyer(service, transferFundsVO.getAmount(), transferFundsVO.getReasonCode());
					isBuyer = true;
					isProvider = false;
				} else if (entityType.equalsIgnoreCase(OrderConstants.PROVIDER)) {
					service.setBusinessTransId(LedgerConstants.BUSINESS_TRANSACTION_SLA_ESCHEATMENT_SLB_FROM_PROVIDER);
					service.setVendorId(transferFundsVO.getFromAccount());
					acctransBo.adminEscheatmentSLBfromProvider(service, transferFundsVO.getAmount(), transferFundsVO.getReasonCode());
					isBuyer = false;
					isProvider = true;					
				}

				// Escheatment Email
				String ledgerTransId = "Ledger Id error";
				String createdDate="";
				double availableBalanceAfterEscheat=0.0;
				SOReceiptsVO soReceiptsVO = null;

				if(isBuyer)
					soReceiptsVO = 
					getSLBucksTransactionReceiptInfo(transferFundsVO.getFromAccount(),
							OrderConstants.LEDGER_ENTITY_TYPE_ID_BUYER,
							OrderConstants.LEDGER_ENTRY_RULE_ID_SLA_ESCHEATMENT_TO_BUYER);
				else if(isProvider)
					soReceiptsVO =
					getSLBucksTransactionReceiptInfo(transferFundsVO.getFromAccount(),
							OrderConstants.LEDGER_ENTITY_TYPE_ID_PROVIDER,
							OrderConstants.LEDGER_ENTRY_RULE_ID_SLA_ESCHEATMENT_TO_PROVIDER);	

				if(soReceiptsVO != null) {
					ledgerTransId = soReceiptsVO.getTransactionID().toString();
					DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
					createdDate = sdf.format(new Date());
					availableBalanceAfterEscheat=availableBalance-transferFundsVO.getAmount().doubleValue();
				}
				//code change for SLT-2228
				if(transferFundsVO.getSlBucksMail().equalsIgnoreCase("mailRequire")){
				sendSLBucksEscheatmentEmail(transferFundsVO.getFromAccount(), entityType, transferFundsVO.getUser(), ledgerTransId, transferFundsVO.getAmount(),availableBalanceAfterEscheat,createdDate,reasonCodeId);
				walletControlAuditVO.setSendEmailNotice(true);
				}
				try {
					walletControlAuditNotesBO.addWalletControlAuditNotesSLBucks(walletControlAuditVO);
				} catch (Exception e) {
					logger.error("Exception in State Regulation SL Bucks transfer opeartion"+e);
					throw new BusinessServiceException("Exception in State Regulation SL Bucks transfer opeartion");
				}
				
			}   
			
			//E-Wallet Enhancement
			else if (transferFundsVO.getReasonCode().intValue() == LedgerConstants.TRANSFER_REASONCODE_IRS_LEVY
					|| transferFundsVO.getReasonCode().intValue() == LedgerConstants.TRANSFER_REASONCODE_LEGAL_HOLD) {
				try {
					Integer walletControlId = transferFundsVO.getWalletControlId();
					double walletHoldBalance = getWalletHoldBalance(transferFundsVO.getEntityId());
					double remainingHoldBalance = walletHoldBalance - transferFundsVO.getAmount().doubleValue();		
					service.setBusinessTransId(LedgerConstants.BUSINESS_TRANSACTION_SLA_ESCHEATMENT_SLB_FROM_PROVIDER);
					service.setVendorId(transferFundsVO.getFromAccount());
					updateEntityWalletRemainingBalance(remainingHoldBalance, transferFundsVO.getEntityId(),walletControlId);
					acctransBo.adminEscheatmentSLBfromProvider(service, transferFundsVO.getAmount(), transferFundsVO.getReasonCode());		
				
					if(transferFundsVO.getSlBucksMail().equalsIgnoreCase("mailRequire")){
						sendTransferSLBucksEscheatmentEmail(transferFundsVO);	
						walletControlAuditVO.setSendEmailNotice(true);
					}
					walletControlAuditNotesBO.addWalletControlAuditNotesSLBucks(walletControlAuditVO);
					//END : changes for SLT-2367
					isProvider = true;	
				} catch (Exception e) {
					logger.error("Exception in IRS Levy or Legal Hold SL Bucks transfer opeartion");
					e.printStackTrace();
				}
			}
            
			else if (transferFundsVO.getReasonCode().intValue() == LedgerConstants.TRANSFER_REASONCODE_ADJUSTMENT_DEBIT 
					|| transferFundsVO.getReasonCode().intValue() == LedgerConstants.TRANSFER_REASONCODE_OTHER_DEBIT) { // debit transfers from...buyer/providers...

				if (entityType.equalsIgnoreCase(OrderConstants.BUYER) ||
						entityType.equalsIgnoreCase(OrderConstants.SIMPLE_BUYER)) {
					service.setBusinessTransId(LedgerConstants.BUSINESS_TRANSACTION_SLA_DEBITS_SLB_FROM_BUYER);
					service.setBuyerID(transferFundsVO.getFromAccount());
					acctransBo.adminDebitsSLBfromBuyer(service, transferFundsVO.getAmount(), transferFundsVO.getReasonCode());
					isBuyer = true;
					isProvider = false;
				} else if (entityType.equalsIgnoreCase(OrderConstants.PROVIDER)) {
					service.setBusinessTransId(LedgerConstants.BUSINESS_TRANSACTION_SLA_DEBITS_SLB_FROM_PROVIDER);
					service.setVendorId(transferFundsVO.getFromAccount());
					acctransBo.adminDebitsSLBfromProvider(service, transferFundsVO.getAmount(), transferFundsVO.getReasonCode());
					isBuyer = false;
					isProvider = true;					
				}

				// Debit Email
				String ledgerTransId = "Ledger Id error";
				SOReceiptsVO soReceiptsVO = null;

				if(isBuyer)
					soReceiptsVO = 
					getSLBucksTransactionReceiptInfo(transferFundsVO.getFromAccount(),
							OrderConstants.LEDGER_ENTITY_TYPE_ID_BUYER,
							OrderConstants.LEDGER_ENTRY_RULE_ID_SLA_DEBIT_TO_BUYER);
				else if(isProvider)
					soReceiptsVO =
					getSLBucksTransactionReceiptInfo(transferFundsVO.getFromAccount(),
							OrderConstants.LEDGER_ENTITY_TYPE_ID_PROVIDER,
							OrderConstants.LEDGER_ENTRY_RULE_ID_SLA_DEBIT_TO_PROVIDER);	

				if(soReceiptsVO != null) {
					ledgerTransId = soReceiptsVO.getTransactionID().toString();
				}
				//code change for SLT-2228
				sendSLBucksDebitEmail(transferFundsVO.getFromAccount(), entityType, transferFundsVO.getUser(), ledgerTransId, transferFundsVO.getAmount(),reasonCodeId);
			}
			else { // transfers from ServiceLive... Credit
				if (entityType.equalsIgnoreCase(OrderConstants.BUYER) ||
						entityType.equalsIgnoreCase(OrderConstants.SIMPLE_BUYER)) {
					service.setBusinessTransId(LedgerConstants.BUSINESS_TRANSACTION_SLA_CREDITS_SLB_TO_BUYER);
					service.setBuyerID(transferFundsVO.getToAccount());
					acctransBo.adminCreditsSLBtoBuyer(service, transferFundsVO.getAmount(), transferFundsVO.getReasonCode());
					isBuyer = true;
					isProvider = false;

				} else if (entityType.equalsIgnoreCase(OrderConstants.PROVIDER)) {
					service.setBusinessTransId(LedgerConstants.BUSINESS_TRANSACTION_SLA_CREDITS_SLB_TO_PROVIDER);
					service.setVendorId(transferFundsVO.getToAccount());
					acctransBo.adminCreditsSLBtoProvider(service, transferFundsVO.getAmount(), transferFundsVO.getReasonCode());
					isBuyer = false;
					isProvider = true;

				}
				// Credit Email
				String ledgerTransId = "Ledger Id error";
				SOReceiptsVO soReceiptsVO = null;

				if(isBuyer)
					soReceiptsVO =
					getSLBucksTransactionReceiptInfo(transferFundsVO.getFromAccount(),
							OrderConstants.LEDGER_ENTITY_TYPE_ID_BUYER,
							OrderConstants.LEDGER_ENTRY_RULE_ID_SLA_CREDIT_TO_BUYER);						
				else if(isProvider)
					soReceiptsVO =
					getSLBucksTransactionReceiptInfo(transferFundsVO.getFromAccount(),
							OrderConstants.LEDGER_ENTITY_TYPE_ID_PROVIDER,
							OrderConstants.LEDGER_ENTRY_RULE_ID_SLA_CREDIT_TO_PROVIDER);

				if(soReceiptsVO != null) {
					ledgerTransId = soReceiptsVO.getTransactionID().toString();
				}
				//code change for SLT-2228
				sendSLBucksCreditEmail(transferFundsVO.getToAccount(), entityType, transferFundsVO.getUser(), ledgerTransId, transferFundsVO.getAmount(),reasonCodeId);				
			}



		}
		else { //not enough funds.....
			processResponse.setMessage("Not enough funds to transfer...KV");

		}


		return processResponse;
	}
	
	public Integer getWalletControlId(Integer entityId) {
		int walletControlId = 0;
		try {
			walletControlId = transactionDao.getWalletControlId(entityId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return walletControlId;
	}
	
	public String getWalletControlBanner(Integer walletControlId) {
		String walletBanner = "";
		try {
			walletBanner = transactionDao.getWalletControlBanner(walletControlId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return walletBanner;
	}

	public SOReceiptsVO getSOTransactionReceiptInfo(Integer entityId, String soId, Integer entityTypeId, Integer entryRuleId) {

		SOReceiptsVO soReceiptsVO = new SOReceiptsVO();
		soReceiptsVO.setLedgerEntityID(entityId);
		soReceiptsVO.setSoID(soId);
		soReceiptsVO.setEntityTypeID(entityTypeId);
		soReceiptsVO.setLedgerEntryRuleID(entryRuleId);

		soReceiptsVO = transactionDao.getSOTransactionReceiptInfo(soReceiptsVO);		

		return soReceiptsVO;
	}

	public SOReceiptsVO  getSLBucksTransactionReceiptInfo(Integer entityId, Integer entityTypeId, Integer entryRuleId) {

		SOReceiptsVO soReceiptsVO = new SOReceiptsVO();
		soReceiptsVO.setLedgerEntityID(entityId);
		soReceiptsVO.setEntityTypeID(entityTypeId);
		soReceiptsVO.setLedgerEntryRuleID(entryRuleId);
		soReceiptsVO.setTimeDiffLowerThreshold(OrderConstants.TIME_DIFF_LOWER_THRESHOLD);
		soReceiptsVO.setTimeDiffUpperThreshold(OrderConstants.TIME_DIFF_UPPER_THRESHOLD);

		soReceiptsVO = transactionDao.getSLBucksTransactionReceiptInfo(soReceiptsVO);		

		return soReceiptsVO;
	}

	public double getBalanceForSLBucksTransfer(FMTransferFundsVO transferFundsVO) throws BusinessServiceException {
		double availableBalance=0.0;


		if (transferFundsVO.getReasonCode().intValue() == LedgerConstants.TRANSFER_REASONCODE_ADJUSTMENT_DEBIT 
				|| transferFundsVO.getReasonCode().intValue() == LedgerConstants.TRANSFER_REASONCODE_OTHER_DEBIT
				|| transferFundsVO.getReasonCode().intValue() == LedgerConstants.TRANSFER_REASONCODE_ESCHEATMENT
				|| transferFundsVO.getReasonCode().intValue() == LedgerConstants.TRANSFER_REASONCODE_IRS_LEVY
				|| transferFundsVO.getReasonCode().intValue() == LedgerConstants.TRANSFER_REASONCODE_LEGAL_HOLD) { 

			AjaxCacheVO avo = new AjaxCacheVO();
			avo.setCompanyId(transferFundsVO.getFromAccount());
			avo.setRoleType(transferFundsVO.getRoleType());

			availableBalance = acctransBo.getAvailableBalance(avo);		


		}else {
			availableBalance = acctransBo.getSLOperationBalance();
		}

		return availableBalance;
	}


	//SL-21117: Revenue Pull Code change starts

	public List <String> getPermittedUsers() throws BusinessServiceException{

		return acctransBo.getPermittedUsers();		
	}

	public double getAvailableBalanceForRevenuePull(){
		double availableBalance=0.0;

		try {
			availableBalance = acctransBo.getAvailableBalanceForRevenuePull();
		} catch (BusinessServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		

		return availableBalance;
	}

	public boolean getAvailableDateCheckForRevenuePull(Date calendarOnDate){
		boolean dbRevenuePullDateCheck=false;

		try {
			dbRevenuePullDateCheck = acctransBo.getAvailableDateCheckForRevenuePull(calendarOnDate);
		} catch (BusinessServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		

		return dbRevenuePullDateCheck;
	}

	public void insertEntryForRevenuePull(double amount,Date revenuePullDate,String note,String user){

		try {
			acctransBo.insertEntryForRevenuePull(amount,revenuePullDate,note,user);
		} catch (BusinessServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void sendRevenuePullConfirmationEmail(String firstName,String lastName,double amount,String revenuePullDate) throws BusinessServiceException{

		EmailVO emailVO = new EmailVO();
		TemplateVo template = new TemplateVo();

		StringBuffer emails = new StringBuffer();

		List<String> permittedUserEmails = null;

		try {

			permittedUserEmails = acctransBo.getPermittedUsersEmail();

			if(permittedUserEmails.size() > 0){

				for( int i = 0; i < permittedUserEmails.size(); i++ ){

					if(i == (permittedUserEmails.size()-1)){

						emails.append(permittedUserEmails.get(i).toString());
					}else{

						emails.append(permittedUserEmails.get(i).toString()+";");
					}

				}
			}

			template = templateDao.getTemplate(AchConstants.EMAIL_TEMPLATE_REVENUE_PULL);

			String alertCC=financeManagerDAO.getAppPropertiesValue(OrderConstants.REVENUE_PULL_ALERT_CC);

			emailVO.setTo(emails.toString());
			emailVO.setCc(alertCC);
			emailVO.setSubject(template.getTemplateSubject());
			emailVO.setFrom(AlertConstants.SERVICE_LIVE_MAILID);
			emailVO.setMessage(template.getTemplateSource());
			emailVO.setFirstName(firstName);
			emailVO.setLastName(lastName);
			emailTemplateBean.sendRevenuePullConfirmationEmail(emailVO,amount,revenuePullDate);

		} catch (Exception e) {
			logger.error("Exception thrown while sending Mail", e);
		}


	}

	//Code change ends


	private void sendIssueRefundsEmailAndSMS(Integer companyId, double amount, Integer ledgerId) {
		EmailVO emailVO = new EmailVO();
		TemplateVo template = new TemplateVo();
		Buyer buyer = new Buyer();
		Contact contact = new Contact();
		try{
			buyer.setBuyerId(companyId);
			buyer = buyerDao.loadData(buyer);
			if(buyer!=null)
			{
				contact.setContactId(buyer.getContactId());
				contact = contactDao.query(contact);
			}
		}catch(Exception e){
			logger.error("Exception thrown while getting VendorResource/Buyer or contact details", e);
		}
		try {
			template = templateDao.getTemplate(AchConstants.EMAIL_TEMPLATE_BUYER_PROVIDER_ISSUE_REFUNDS);
			emailVO.setTo(contact.getEmail());
			emailVO.setSubject(template.getTemplateSubject());
			emailVO.setFrom(AlertConstants.SERVICE_LIVE_MAILID);
			emailVO.setMessage(template.getTemplateSource());
			emailVO.setFirstName(contact.getFirstName());
			emailVO.setLastName(contact.getLastName());
			emailTemplateBean.sendWithdrawConfirmEmail(template,emailVO, ledgerId.toString(), amount, getFmReleaseDate(),AchConstants.EMAIL_TEMPLATE_ISSUE_REFUNDS,null);
		} catch (Exception e) {
			logger.error("Exception thrown while getting template details or Sending Mail", e);
		}
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.financeManager.IFinanceManagerBO#issueRefunds(java.lang.Integer, java.lang.String, double, long, java.lang.String, java.lang.String)
	 */
	public ProcessResponse issueRefunds(Integer companyId, String roleType, double amount,
			long accountId, String userName, String note) {
		ProcessResponse procResp = new ProcessResponse();
		Account account = getAccountDetailsAllData(accountId);
		MarketPlaceTransactionVO service = new MarketPlaceTransactionVO();
		try
		{
			service.setVendorId(companyId);	
			service.setBuyerID(companyId);
			service.setTransactionNote(note);
			if(account!=null)
			{
				service.setAccountTypeId(account.getAccount_type_id());
				service.setUserTypeID(account.getOwner_entity_type_id().intValue());

				if(account.getAccount_type_id() == LedgerConstants.CC_ACCOUNT_TYPE)
				{
					service.setCCInd(true);
					if(account.getCardTypeId() == LedgerConstants.CREDIT_CARD_VISA || account.getCardTypeId() == LedgerConstants.CREDIT_CARD_MC )
					{
						service.setBusinessTransId(LedgerConstants.BUSINESS_TRANSACTION_SLA_REFUNDS_TO_BUYERS_V_MC);
						service.setLedgerEntryRuleId(LedgerConstants.RULE_ID_WITHDRAWAL_CASH_CC_V_MC);
					}
					else
					{
						service.setBusinessTransId(LedgerConstants.BUSINESS_TRANSACTION_SLA_REFUNDS_TO_BUYERS_AMEX);
						service.setLedgerEntryRuleId(LedgerConstants.RULE_ID_WITHDRAWAL_CASH_CC_AMEX);
					}
				}
				else
				{
					service.setCCInd(false);
					service.setLedgerEntryRuleId(LedgerConstants.RULE_ID_WITHDRAWAL_CASH_BUYER);
					service.setBusinessTransId(LedgerConstants.BUSINESS_TRANSACTION_WITHDRAW_FUNDS_BUYER);
				}
			}

			setTransVOFundingType(companyId, service);

			Integer ledgerId = acctransBo.issueRefunds(service, amount, accountId, userName);
			if(ledgerId != null){
				sendIssueRefundsEmailAndSMS(companyId, amount, ledgerId);
				procResp.setCode(ServiceConstants.VALID_RC);
				procResp.setSubCode(ServiceConstants.VALID_RC);

			}
			else
			{
				procResp.setCode(ServiceConstants.SYSTEM_ERROR_RC);
				procResp.setSubCode(ServiceConstants.SYSTEM_ERROR_RC);
			}
		}
		catch (Exception e) {
			procResp.setCode(ServiceConstants.SYSTEM_ERROR_RC);
			logger.error("Exception thrown while issueing refunds", e);
		}
		return procResp;
	}

	public Account getAccountDetailsAllData(long accountId) {
		Account account = null;
		try {
			account = transactionDao.getAccountDetailsAllData(accountId);
		} 
		catch (Exception e) {
			logger.error("Exception thrown while fetching account details", e);
		}
		return account;

	}

	public ProcessResponse withdrawfundsSLAOperations(Integer companyId, String roleType, double amount,
			long accountId,String user) {
		ProcessResponse procResp = new ProcessResponse();
		List<Account> accounts = new ArrayList<Account>();
		MarketPlaceTransactionVO service = new MarketPlaceTransactionVO();
		service.setBusinessTransId(LedgerConstants.BUSINESS_TRANSACTION_SLA_WITHDRAWS_FROM_OPERATIONS);
		service.setLedgerEntryRuleId(LedgerConstants.RULE_ID_WITHDRAWAL_CASH_SLA_OPERATIONS);
		service.setVendorId(companyId);

		service.setCCInd(false);
		service.setBuyerID(companyId);
		accounts = getAccountDetailsActiveAndInactive(companyId);
		boolean checkForValidation = true;
		AjaxCacheVO avo = new AjaxCacheVO();
		avo.setCompanyId(companyId);
		avo.setRoleType(roleType);
		try {


			if (acctransBo.getAvailableBalance(avo) < amount) {
				checkForValidation = false;
				procResp.setMessage("Unable to withdraw funds - Withdrawal must be no greater than account balance");
			}
			if (accounts.size() <= 0) {
				checkForValidation = false;
				procResp.setMessage("Unable to withdraw funds due invalid account selection");
			}
			if (checkForValidation == true) {
				Integer ledgerId = acctransBo.withdrawfundsSLAOperations(service, amount, accountId, user);
				if(ledgerId != null){
					sendSLAWithdrawConfirmationEmailAndSMS(companyId, amount, ledgerId, user);
				}
			}
		} catch (Exception e) {
			logger.error("Exception thrown while withdrwaing funds", e);
		}
		return procResp;
	}

	public ProcessResponse depositfundsSLAOperations(Integer companyId, String roleType, double amount, long accountId, String user) {
		ProcessResponse procResp = new ProcessResponse();
		List<Account> accounts = new ArrayList<Account>();
		MarketPlaceTransactionVO service = new MarketPlaceTransactionVO();
		service.setBusinessTransId(LedgerConstants.BUSINESS_TRANSACTION_SLA_DEPOSITS_TO_OPERATIONS);
		service.setLedgerEntryRuleId(LedgerConstants.RULE_ID_DEPOSIT_CASH_SLA_OPERATIONS);
		service.setCCInd(false);
		service.setBuyerID(companyId);
		service.setFundingTypeId(LedgerConstants.FUNDING_TYPE_NON_FUNDED);
		accounts = accountDetails(companyId);
		boolean checkForValidation = true;
		AjaxCacheVO avo = new AjaxCacheVO();
		avo.setCompanyId(companyId);
		avo.setRoleType(roleType);
		try {

			if (amount <= 0) {
				checkForValidation = false;
				procResp.setMessage("Deposit Amount must be greater than 0.00");
			}

			if (accounts.size() <= 0) {
				checkForValidation = false;
				procResp.setMessage("Unable to deposit funds due invalid account selection");
			}
			if (checkForValidation == true) {
				setTransVOFundingType(companyId, service);
				Integer ledgerId = acctransBo.depositfundsSLAOperations(service, amount, accountId,user);
				if(ledgerId != null){
					sendSLADepositConfirmationEmail(companyId, amount, ledgerId, user,roleType);
				}
			}

		} catch (Exception e) {
			logger.error("Exception thrown while depsiting funds", e);
		}
		return procResp;
	}

	private void sendSLAWithdrawConfirmationEmailAndSMS(Integer companyId, double amount, Integer ledgerId, String user) {
		EmailVO emailVO = new EmailVO();
		TemplateVo template = new TemplateVo();
		Contact contact = new Contact();
		FinancialProfileVO fmProfileVO = new FinancialProfileVO();
		try{
			Integer contactId = contactDao.getContactIdByUserName(user);
			contact.setContactId(contactId);
			contact = contactDao.query(contact);		
		}catch(Exception e){
			logger.error("Exception thrown while getting vendorResource or contact details", e);
		}		
		try {
			template = templateDao.getTemplate(AchConstants.EMAIL_TEMPLATE_PROVIDER_WITHDRAW_FUNDS);
			if(fmProfileVO.getContact().getEmail() != null){
				emailVO.setTo(contact.getEmail()+";"+fmProfileVO.getContact().getEmail());
			}else{
				emailVO.setTo(contact.getEmail());
			}
			emailVO.setSubject(template.getTemplateSubject());
			emailVO.setFrom(AlertConstants.SERVICE_LIVE_MAILID);
			emailVO.setMessage(template.getTemplateSource());
			emailVO.setFirstName(contact.getFirstName());
			emailVO.setLastName(contact.getLastName());
			emailTemplateBean.sendWithdrawConfirmEmail(template,emailVO, ledgerId.toString(), amount, getFmReleaseDate(),AchConstants.EMAIL_TEMPLATE_ACH_DEPOSIT_FUNDS,null);
		} catch (Exception e) {
			logger.error("Exception thrown while getting template details or Sending Mail", e);
		}
	}

	private void sendSLADepositConfirmationEmail(Integer companyId, double amount, Integer ledgerId, String user,String roleType)
	{
		EmailVO emailVO = new EmailVO();
		TemplateVo template = new TemplateVo();
		Contact contact = new Contact();
		FinancialProfileVO fmProfileVO = new FinancialProfileVO();

		try{
			Integer contactId = contactDao.getContactIdByUserName(user);
			contact.setContactId(contactId);
			contact = contactDao.query(contact);		
		}catch(Exception e){
			logger.error("Exception thrown while getting buyerresource or contact details", e);
		}
		try {
			template = templateDao.getTemplate(AchConstants.EMAIL_TEMPLATE_BUYER_WITHDRAW_FUNDS);
			if(fmProfileVO.getContact().getEmail() != null){
				emailVO.setTo(contact.getEmail()+";"+fmProfileVO.getContact().getEmail());
			}else{
				emailVO.setTo(contact.getEmail());
			}
			emailVO.setSubject(template.getTemplateSubject());
			emailVO.setFrom(AlertConstants.SERVICE_LIVE_MAILID);
			emailVO.setMessage(template.getTemplateSource());
			emailVO.setFirstName(contact.getFirstName());
			emailVO.setLastName(contact.getLastName());
			emailTemplateBean.sendWithdrawConfirmEmail(template,emailVO, ledgerId.toString(), amount, getFmReleaseDate(),AchConstants.EMAIL_TEMPLATE_ACH_DEPOSIT_FUNDS,roleType);
		} catch (Exception e) {
			logger.error("Exception thrown while getting template details or Sending Mail", e);
		}

	}

	private void sendSLBucksCreditEmail(Integer entityId, String entityType, String username, String ledgerTransId, Double amt,Integer reasonCodeId) {
		EmailVO emailVO = new EmailVO();
		TemplateVo template = new TemplateVo();
		Contact contact = null;
		try {
			if (OrderConstants.BUYER.equalsIgnoreCase(entityType) || OrderConstants.SIMPLE_BUYER.equalsIgnoreCase(entityType)) {
				contact = contactDao.getByBuyerId(entityId);
			} else if (OrderConstants.PROVIDER.equalsIgnoreCase(entityType)) {
				contact = contactDao.getByVendorId(entityId);
			} else {
				logger.error("Invalid entity type! Entity type = " + entityType);
			}
		} catch(Exception e) {
			logger.error("Exception thrown while getting buyer contact", e);
		}

		// Try getting contact info from by username
		if(contact == null) {
			try {
				Integer contactId = contactDao.getContactIdByUserName(username);
				if(contactId != null) {
					contact = new Contact();
					contact.setContactId(contactId);
					contact = contactDao.query(contact);
				}
			} catch (DBException e1) {
				logger.error("Exception thrown while getting buyer contact by username", e1);
			}
		}

		try {
			//code change for SLT-2228
			Integer templateId= transactionDao.getTemplateIdByReasoncode(reasonCodeId);
			template = templateDao.getTemplate(templateId);	
			if(contact != null && contact.getEmail() != null && !"".equals(contact.getEmail())) {
				emailVO.setTo(contact.getEmail());
				emailVO.setSubject(template.getTemplateSubject());
				emailVO.setFrom(AlertConstants.SERVICE_LIVE_MAILID);
				emailVO.setMessage(template.getTemplateSource());
				emailVO.setFirstName(contact.getFirstName());
				emailVO.setLastName(contact.getLastName());
				emailVO.setTemplateId(template.getTemplateId());
				emailTemplateBean.sendSLBucksCreditEmail(emailVO, ledgerTransId, amt,entityType);
			}
		} catch (Exception e) {
			logger.error("Exception thrown while getting template details or Sending Mail", e);
		}
	}

	private void sendSLBucksEscheatmentEmail(Integer entityId, String entityType, String username, String ledgerTransId, Double amt,Double availableBalance,String createdDate,Integer reasonCodeId) {
		EmailVO emailVO = new EmailVO();
		TemplateVo template = new TemplateVo();
		Contact contact = null;
		FinancialProfileVO fmProfileVO = new FinancialProfileVO();

		try {
			if (OrderConstants.BUYER.equalsIgnoreCase(entityType) || OrderConstants.SIMPLE_BUYER.equalsIgnoreCase(entityType)) {
				contact = contactDao.getByBuyerId(entityId);
			} else if (OrderConstants.PROVIDER.equalsIgnoreCase(entityType)) {
				contact = contactDao.getByVendorId(entityId);
			} else {
				logger.error("Invalid entity type! Entity type = " + entityType);
			}
		} catch(Exception e) {
			logger.error("Exception thrown while getting buyer contact", e);
		}

		// Try getting contact info from by username
		if(contact == null) {
			try {
				Integer contactId = contactDao.getContactIdByUserName(username);
				if(contactId != null) {
					contact = new Contact();					
					contact.setContactId(contactId);
					contact = contactDao.query(contact);
				}
			} catch (DBException e1) {
				logger.error("Exception thrown while getting buyer contact by username", e1);
			}
		}

		try {
			//code change for SLT-2228
			Integer templateId= transactionDao.getTemplateIdByReasoncode(reasonCodeId);
			template = templateDao.getTemplate(templateId);
			if(contact != null && contact.getEmail() != null && !"".equals(contact.getEmail())) {
				emailVO.setTo(contact.getEmail());
				emailVO.setSubject(template.getTemplateSubject());
				emailVO.setFrom(AlertConstants.SERVICE_LIVE_MAILID);
				emailVO.setMessage(template.getTemplateSource());
				emailVO.setFirstName(contact.getFirstName());
				emailVO.setLastName(contact.getLastName());
				emailVO.setTemplateId(template.getTemplateId());
				emailTemplateBean.sendSLBucksEscheatmentEmail(emailVO, ledgerTransId, amt,entityType,availableBalance,createdDate);
			}
		} catch (Exception e) {
			logger.error("Exception thrown while getting template details or Sending Mail", e);
		}
	} 

	private void sendSLBucksDebitEmail(Integer entityId, String entityType, String username, String ledgerTransId, Double amt,Integer reasonCodeId) {
		EmailVO emailVO = new EmailVO();
		TemplateVo template = new TemplateVo();
		Contact contact = null;
		FinancialProfileVO fmProfileVO = new FinancialProfileVO();

		try {
			if (OrderConstants.BUYER.equalsIgnoreCase(entityType) || OrderConstants.SIMPLE_BUYER.equalsIgnoreCase(entityType)) {
				contact = contactDao.getByBuyerId(entityId);
			} else if (OrderConstants.PROVIDER.equalsIgnoreCase(entityType)) {
				contact = contactDao.getByVendorId(entityId);
			} else {
				logger.error("Invalid entity type! Entity type = " + entityType);
			}
		} catch(Exception e) {
			logger.error("Exception thrown while getting buyer contact", e);
		}

		// Try getting contact info from by username
		if(contact == null) {
			try {
				Integer contactId = contactDao.getContactIdByUserName(username);
				if(contactId != null) {
					contact = new Contact();					
					contact.setContactId(contactId);
					contact = contactDao.query(contact);
				}
			} catch (DBException e1) {
				logger.error("Exception thrown while getting buyer contact by username", e1);
			}
		}

		try {
			//code change for SLT-2228
			Integer templateId= transactionDao.getTemplateIdByReasoncode(reasonCodeId);
			template = templateDao.getTemplate(templateId);	
			if(contact != null && contact.getEmail() != null && !"".equals(contact.getEmail())) {
				emailVO.setTo(contact.getEmail());
				emailVO.setSubject(template.getTemplateSubject());
				emailVO.setFrom(AlertConstants.SERVICE_LIVE_MAILID);
				emailVO.setMessage(template.getTemplateSource());
				emailVO.setFirstName(contact.getFirstName());
				emailVO.setLastName(contact.getLastName());
				emailVO.setTemplateId(template.getTemplateId());
				emailTemplateBean.sendSLBucksDebitEmail(emailVO, ledgerTransId, amt,entityType,false);
			}
		} catch (Exception e) {
			logger.error("Exception thrown while getting template details or Sending Mail", e);
		}
	}

	public void sendBuyerSOCancelEmail(Integer buyerId, Integer providerId, String soId,String roleType)
	{
		EmailVO emailVO = new EmailVO();
		TemplateVo template = new TemplateVo();
		Contact contact = new Contact();

		try{
			contact = contactDao.getByBuyerId(buyerId);
		}catch(Exception e){
			logger.error("Exception thrown while getting buyerresource or contact details", e);
		}
		try {

			String ledgerTransId = "ledger Id not found";
			Double transAmount = 0.0;

			// get so cancel transaction data
			SOReceiptsVO soReceiptsVO = 
					getSOTransactionReceiptInfo(providerId, soId, 
							OrderConstants.LEDGER_ENTITY_TYPE_ID_PROVIDER,
							OrderConstants.LEDGER_ENTRY_RULE_ID_CANCELLATION_PENALTY);
			if(soReceiptsVO != null)
			{
				ledgerTransId = soReceiptsVO.getTransactionID().toString();
				transAmount = soReceiptsVO.getTransactionAmount();
			}


			template = templateDao.getTemplate(AchConstants.EMAIL_TEMPLATE_BUYER_CANCEL_PENALTY);
			if(contact != null && contact.getEmail() != null && !"".equals(contact.getEmail())){
				emailVO.setTo(contact.getEmail());

				emailVO.setSubject(template.getTemplateSubject());
				emailVO.setFrom(AlertConstants.SERVICE_LIVE_MAILID);
				emailVO.setMessage(template.getTemplateSource());
				emailVO.setFirstName(contact.getFirstName());
				emailVO.setLastName(contact.getLastName());
				emailTemplateBean.sendBuyerCancellationEmail(emailVO, soId, providerId, ledgerTransId, transAmount,roleType);
			}
		} catch (Exception e) {
			logger.error("Exception thrown while getting template details or Sending Mail", e);
		}

	}


	public void sendProviderSOCancelEmail(Integer buyerId, Integer providerId, String soId)
	{	
		Contact contact = new Contact();

		try
		{
			// Set who we are sending the email to.
			ServiceOrder serviceOrder = serviceOrderDao.getServiceOrder(soId);			
			if(serviceOrder != null && serviceOrder.getServiceContact() != null)
			{
				contact.setFirstName(serviceOrder.getVendorResourceContact().getFirstName());
				contact.setLastName(serviceOrder.getVendorResourceContact().getLastName());
				contact.setEmail(serviceOrder.getVendorResourceContact().getEmail());
			}
		}
		catch (DataServiceException e)
		{
			e.printStackTrace();
		}


		String ledgerTransId = "Ledger ID error";
		Double transAmount = 0.0;

		// get so posting fee transaction id and amount
		SOReceiptsVO soReceiptsVO = 
				getSOTransactionReceiptInfo(providerId, soId, 
						OrderConstants.LEDGER_ENTITY_TYPE_ID_PROVIDER,
						OrderConstants.LEDGER_ENTRY_RULE_ID_CANCELLATION_PENALTY);

		if(soReceiptsVO != null)
		{
			ledgerTransId = soReceiptsVO.getTransactionID().toString();
			transAmount = soReceiptsVO.getTransactionAmount();
		}


		EmailVO emailVO = new EmailVO();
		TemplateVo template = new TemplateVo();
		FinancialProfileVO fmProfileVO = new FinancialProfileVO();

		try {
			template = templateDao.getTemplate(AchConstants.EMAIL_TEMPLATE_PROVIDER_SO_CANCELLED);
			if(contact != null && contact.getEmail() != null && !"".equals(contact.getEmail())){
				emailVO.setTo(contact.getEmail()+";"+fmProfileVO.getContact().getEmail());

				emailVO.setSubject(template.getTemplateSubject());
				emailVO.setFrom(AlertConstants.SERVICE_LIVE_MAILID);
				emailVO.setMessage(template.getTemplateSource());
				emailVO.setFirstName(contact.getFirstName());
				emailVO.setLastName(contact.getLastName());
				emailTemplateBean.sendProviderSOCancelledEmail(emailVO, buyerId, soId, ledgerTransId, transAmount);
			}
		} catch (Exception e) {
			logger.error("Exception thrown while getting template details or Sending Mail", e);
		}
	}


	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.financeManager.IFinanceManagerBO#sendFailedToAcceptSOEmails(com.newco.marketplace.dto.vo.serviceorder.ServiceOrder)
	 */
	public void sendFailedToAcceptSOEmails(ServiceOrder so)
	{	
		ArrayList<Contact> contacts = new ArrayList<Contact>();
		try {
			VendorResource vendorResource = new VendorResource();

			VendorResource findResource = new VendorResource();
			findResource.setResourceId(so.getAcceptedResource().getResourceId());
			vendorResource = vendorResourceDao.query(findResource);

			Contact vendorContact = new Contact();
			vendorContact.setContactId(vendorResource.getContactId());
			vendorContact = contactDao.query(vendorContact);

			//get Vendor Contact info

			vendorContact.setFirstName(vendorContact.getFirstName());
			vendorContact.setLastName(vendorContact.getLastName());
			vendorContact.setEmail(vendorContact.getEmail());
			vendorContact.setContactMethodId(AchConstants.EMAIL_TEMPLATE_PROVIDER_CANT_ACCEPT_SO);
			contacts.add(vendorContact);

			//get buyer Contact info
			Contact buyerContact = contactDao.getByBuyerId(so.getBuyer().getBuyerId());
			buyerContact.setContactMethodId(AchConstants.EMAIL_TEMPLATE_CONSUMER_CANT_ACCEPT_SO);
			contacts.add(buyerContact);

			for (Contact contact : contacts) {
				EmailVO emailVO = new EmailVO();
				TemplateVo template = new TemplateVo();
				template = templateDao.getTemplate(contact.getContactMethodId());
				emailVO.setTo(contact.getEmail());
				emailVO.setSubject(template.getTemplateSubject());
				emailVO.setFrom(AlertConstants.SERVICE_LIVE_MAILID);
				emailVO.setMessage(template.getTemplateSource());
				emailVO.setFirstName(contact.getFirstName());
				emailVO.setLastName(contact.getLastName());
				emailVO.setTemplateId(template.getTemplateId());
				emailTemplateBean.sendFailedToAcceptSOEmail(emailVO, so);
			}
		} catch (Exception e) {
			logger.error("Exception thrown while getting template details or Sending Mail", e);
		}
	}


	public void sendBuyerSOClosedEmail(Integer buyerId, String soId)
	{
		Contact contact = new Contact();
		Integer vendorId = null;
		Integer providerId = null;
		try
		{
			ServiceOrder serviceOrder = serviceOrderDao.getServiceOrder(soId);	
			if(serviceOrder != null && serviceOrder.getServiceContact() != null)
			{
				contact.setFirstName(serviceOrder.getServiceContact().getFirstName());
				contact.setLastName(serviceOrder.getServiceContact().getLastName());
				contact.setEmail(serviceOrder.getServiceContact().getEmail());
				vendorId = serviceOrder.getAcceptedVendorId();
				providerId=serviceOrder.getAcceptedResourceId();
			}
		}
		catch (DataServiceException e)
		{ 
			e.printStackTrace();
		}

		String ledgerTransId = "ledger id error";
		Double transAmount = 0.0;

		// get so pay and close transaction id and amount
		SOReceiptsVO soReceiptsVO = transactionDao.getBuyerPayAndCloseReceiptInfo(soId);
		if(soReceiptsVO != null)
		{
			ledgerTransId = soReceiptsVO.getTransactionID().toString();
			transAmount = soReceiptsVO.getTransactionAmount();
		}

		EmailVO emailVO = new EmailVO();
		TemplateVo template = new TemplateVo();


		try {

			template = templateDao.getTemplate(AchConstants.EMAIL_TEMPLATE_BUYER_CLOSE_SO);
			Contact contact1 = null;
			String providerFirstName = "";
			String providerLastName = "";
			String flagForConsumer = "";
			try {
				ServiceOrder serviceOrder1 = serviceOrderDao.getServiceOrder(soId);
				if (serviceOrder1 != null) {
					contact1 = contactDao.getByBuyerId(((Buyer) serviceOrder1
							.getBuyer()).getBuyerId());
					if(null != serviceOrder1.getAcceptedResource() 
							&& null != serviceOrder1.getAcceptedResource().getResourceContact()
							){
						providerFirstName = serviceOrder1.getAcceptedResource().getResourceContact().getFirstName();
						providerLastName = serviceOrder1.getAcceptedResource().getResourceContact().getLastName();
					}
					if(null != serviceOrder1.getBuyer()&& null!=serviceOrder1.getBuyer().getRoleId()&& serviceOrder1.getBuyer().getRoleId().equals(5)){
						flagForConsumer = AchConstants.FLAG_FOR_CONSUMER_Y;
					}else if(null != serviceOrder1.getBuyer()&& null!=serviceOrder1.getBuyer().getRoleId()&& !serviceOrder1.getBuyer().getRoleId().equals(5)){
						flagForConsumer = AchConstants.FLAG_FOR_CONSUMER_N;
					}
				}
			} catch (DBException e) {
				e.printStackTrace();
			} catch (DataServiceException e) {
				e.printStackTrace();
			}

			if(contact1 != null && contact1.getEmail() != null && !"".equals(contact1.getEmail())){
				emailVO.setTo(contact1.getEmail());

				emailVO.setSubject(template.getTemplateSubject());
				emailVO.setFrom(AlertConstants.SERVICE_LIVE_MAILID);
				emailVO.setMessage(template.getTemplateSource());
				emailVO.setFirstName(contact1.getFirstName());
				emailVO.setLastName(contact1.getLastName());
				emailTemplateBean.sendBuyerSOClosedEmail(emailVO, buyerId, vendorId, soId, ledgerTransId, transAmount,providerFirstName,providerLastName, flagForConsumer,providerId);
			}
		} catch (Exception e) {
			logger.error("Exception thrown while getting template details or Sending Mail", e);
		}
	}

	public void sendWithdrawProviderConfirmationEmail(Integer companyId, double amount, Integer ledgerId) {
		logger.info("sendWithdrawProviderConfirmationEmail");
		EmailVO emailVO = new EmailVO();
		TemplateVo template = new TemplateVo();
		VendorResource vendorResource = new VendorResource();
		Contact contact = new Contact();
		FinancialProfileVO fmProfileVO = new FinancialProfileVO();
		try{
			vendorResource = vendorResourceDao.getQueryByVendorId(Integer.parseInt(companyId.toString()));
			fmProfileVO = this.getVendorDetails(companyId.toString(), false);
			contact.setContactId(vendorResource.getContactId());
			contact = contactDao.query(contact);
		}catch(Exception e) {
			logger.error("Exception thrown while getting vendorResource or contact details", e);
		}

		try {
			template = templateDao.getTemplate(AchConstants.EMAIL_TEMPLATE_PROVIDER_WITHDRAW_FUNDS);
			if(fmProfileVO.getContact().getEmail() != null){
				emailVO.setTo(contact.getEmail()+";"+fmProfileVO.getContact().getEmail());
			}else{
				emailVO.setTo(contact.getEmail());
			}
			emailVO.setSubject(template.getTemplateSubject());
			emailVO.setFrom(AlertConstants.SERVICE_LIVE_MAILID);
			emailVO.setMessage(template.getTemplateSource());
			emailVO.setFirstName(contact.getFirstName());
			emailVO.setLastName(contact.getLastName());
			emailVO.setTemplateId(template.getTemplateId());
			emailTemplateBean.sendWithdrawConfirmEmail(template,emailVO, ledgerId.toString(), amount, getFmReleaseDate(),AchConstants.EMAIL_TEMPLATE_ACH_PROVIDER_WITHDRAW_FUNDS,null);  
		} catch (Exception e) {
			logger.error("Exception thrown while getting template details or Sending Mail", e);
			e.printStackTrace();
		}
	}

	public void sendProviderSOClosedEmail(Integer entityId, String soId)
	{
		Contact contact = new Contact();
		ServiceOrder serviceOrder = new ServiceOrder();
		Integer fundedType = new Integer(0);

		try
		{
			serviceOrder = serviceOrderDao.getServiceOrder(soId);

			if(serviceOrder != null && serviceOrder.getAcceptedVendorId() != null)
			{
				fundedType = serviceOrder.getFundingTypeId();
				contact = contactDao.getByVendorId(serviceOrder.getAcceptedVendorId());
			}
		}
		catch (DataServiceException e)
		{
			e.printStackTrace();
		}
		catch(DBException e){

			logger.error("Exception thrown while getting buyerresource or contact details", e);
		}

		String ledgerTransIdPay = "Ledger Transaction ID error new";		
		String ledgerTransIdFee = "Ledger Transaction Fee ID error new";
		Double transAmountPay = 0.0;
		Double transAmountFee = 0.0;

		SOReceiptsVO soReceiptsVO;
		// for Prefunded type
		if(fundedType == OrderConstants.PREFUNDED || fundedType == OrderConstants.CONSUMER_FUNDED){

			// Adjusted payment to Provider
			soReceiptsVO =
					getSOTransactionReceiptInfo(entityId, soId, 
							OrderConstants.LEDGER_ENTITY_TYPE_ID_PROVIDER, 
							OrderConstants.LEDGER_ENTRY_RULE_ID_SO_PROVIDER_FINAL_PRICE);

			if(soReceiptsVO != null) {
				ledgerTransIdPay = soReceiptsVO.getTransactionID().toString();
				transAmountPay = soReceiptsVO.getTransactionAmount();
			}

			// Fee amount paid to ServiceLive
			soReceiptsVO =
					getSOTransactionReceiptInfo(2, soId, 
							OrderConstants.LEDGER_ENTITY_TYPE_ID_SERVICELIVE, 
							OrderConstants.LEDGER_ENTRY_RULE_ID_SO_PROVIDER_SERVICE_FEE);

			if(soReceiptsVO != null) {
				ledgerTransIdFee = soReceiptsVO.getTransactionID().toString();
				transAmountFee = soReceiptsVO.getTransactionAmount();
			}

		}
		// for SHC funded
		else if(fundedType == OrderConstants.SHC_FUNDED){

			// Adjusted payment to Provider
			soReceiptsVO =
					getSOTransactionReceiptInfo(entityId, soId, 
							OrderConstants.LEDGER_ENTITY_TYPE_ID_PROVIDER, 
							LedgerConstants.RULE_ID_SHC_RELEASE_SO_PAYMENT);

			if(soReceiptsVO != null) {
				ledgerTransIdPay = soReceiptsVO.getTransactionID().toString();
				transAmountPay = soReceiptsVO.getTransactionAmount();
			}

			// Fee amount paid to ServiceLive
			soReceiptsVO =
					getSOTransactionReceiptInfo(2, soId, 
							OrderConstants.LEDGER_ENTITY_TYPE_ID_SERVICELIVE, 
							LedgerConstants.RULE_ID_TRANSFER_COMMISSION);

			if(soReceiptsVO != null) {
				ledgerTransIdFee = soReceiptsVO.getTransactionID().toString();
				transAmountFee = soReceiptsVO.getTransactionAmount();
			}

		}

		EmailVO emailVO = new EmailVO();
		TemplateVo template = new TemplateVo();
		FinancialProfileVO fmProfileVO = new FinancialProfileVO();

		try {
			template = templateDao.getTemplate(AchConstants.EMAIL_TEMPLATE_PROVIDER_CLOSE_SO);
			if(contact != null && contact.getEmail() != null && !"".equals(contact.getEmail())){
				emailVO.setTo(contact.getEmail()+";"+fmProfileVO.getContact().getEmail());

				emailVO.setSubject(template.getTemplateSubject());
				emailVO.setFrom(AlertConstants.SERVICE_LIVE_MAILID);
				emailVO.setMessage(template.getTemplateSource());
				emailVO.setFirstName(contact.getFirstName());
				emailVO.setLastName(contact.getLastName());
				Integer buyerId = serviceOrder.getBuyer().getBuyerId();
				emailTemplateBean.sendProviderSOClosedEmail(emailVO, buyerId, soId, ledgerTransIdPay, ledgerTransIdFee, transAmountPay, transAmountFee);
			}
		} catch (Exception e) {
			logger.error("Exception thrown while getting template details or Sending Mail", e);
		}
	}


	public ServiceOrderCustomRefVO getCustomReferenceObject(String customRefId, String soId)
	{
		try
		{
			return serviceOrderDao.getCustomReferenceObject(customRefId, soId);
		}
		catch (DataServiceException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}


	public HashMap getBuyerTotalDeposit(Integer buyerId)
	{
		HashMap einMap = null;
		CryptographyVO cryptographyVO = new CryptographyVO();
		cryptographyVO.setKAlias(MPConstants.ENCRYPTION_KEY);
		try
		{
			double buyerTotalDeposit = walletBO.getBuyerTotalDeposit(buyerId.longValue());
			boolean einStoredFlag = false;
			boolean isEin = false;
			boolean isSSN = false;
			boolean isAltId = false;
			String einNo = "";
			String ssnNo = "";
			String altIdDocNo = "";
			String ssnDob = "";
			String altIdDob = "";
			String altIdDoctype = "";
			String altIdCountry = "";

			Buyer buyerPII = buyerDao.getBuyerPII(buyerId);

			if (buyerPII != null){
				if (buyerPII.getPiiIndex()==0 && buyerPII.getEinNoEnc() != null){
					isEin = true;
				}
				if (buyerPII.getPiiIndex()==1 && buyerPII.getEinNoEnc() != null){
					isSSN = true;
				}
				if (buyerPII.getPiiIndex()==2 && buyerPII.getEinNoEnc() != null){
					isAltId = true;
				}

				if (isEin
						|| (isSSN && buyerPII.getDob() != null)
						|| (isAltId && buyerPII.getDob() != null
						&& buyerPII.getAltIDDocType() != null && buyerPII
						.getAltIDCountryIssue() != null)) {
					einStoredFlag = true;
				}

				if (buyerPII.getEinNoEnc() != null){

					cryptographyVO.setInput(buyerPII.getEinNoEnc());
					cryptographyVO = cryptography.decryptKey(cryptographyVO);


					if (isSSN){
						if(cryptographyVO != null && cryptographyVO.getResponse() != null){
							ssnNo = cryptographyVO.getResponse();
							if (ssnNo != null && ssnNo.length()>=6){
								ssnNo = ProviderConstants.COMPANY_SSN_ID_MASK+ssnNo.substring(5);
							}
						}
					}
					/*if (isAltId){
						if(cryptographyVO != null && cryptographyVO.getResponse() != null){
							altIdDocNo = cryptographyVO.getResponse();
							if (altIdDocNo != null){
								altIdDocNo = ServiceLiveStringUtils.maskStringAlphaNum(altIdDocNo, 4, "X");
							}
						}
					}
					if (isEin){
						if(cryptographyVO != null && cryptographyVO.getResponse() != null){
							einNo = cryptographyVO.getResponse();
							if (einNo != null && einNo.length()>=6){
								einNo = ProviderConstants.COMPANY_EIN_ID_MASK+einNo.substring(5);
							}
						}
					}*/

				}
				if (buyerPII.getDob() != null && isSSN)
				{
					ssnDob = buyerPII.getDob().toString();
				}else if (buyerPII.getDob() != null && isAltId)
				{
					altIdDob = buyerPII.getDob().toString();
				}
				if (buyerPII.getAltIDDocType() != null){
					altIdDoctype = buyerPII.getAltIDDocType();
				}
				if (buyerPII.getAltIDCountryIssue() != null){
					altIdCountry = buyerPII.getAltIDCountryIssue();
				}
			}	

			einMap = new HashMap<String, Object>();
			einMap.put("buyerTotalDeposit", buyerTotalDeposit);
			einMap.put("einStoredFlag", einStoredFlag);
			einMap.put("isEin", isEin);
			einMap.put("isSSN", isSSN);
			einMap.put("isAltId", isAltId);
			einMap.put("einNo", einNo);
			einMap.put("ssnNo", ssnNo);
			einMap.put("altIdDocNo", altIdDocNo);
			einMap.put("ssnDob", ssnDob);
			einMap.put("altIdDob", altIdDob);
			einMap.put("altIdDoctype", altIdDoctype);
			einMap.put("altIdCountry", altIdCountry);

		} catch (DataServiceException e) {
			e.printStackTrace();
		} catch (SLBusinessServiceException e) {
			e.printStackTrace();
		}
		return einMap;
	}

	// Fetch buyer bitFlag and threshold limit
	public PIIThresholdVO getBuyerThresholdMap(String role)
	{
		PIIThresholdVO pIIThresholdVO = new PIIThresholdVO();
		try
		{
			pIIThresholdVO = piiThresholdDao.getThreshold(role);

		} catch (DataServiceException e) {
			e.printStackTrace();
		} 
		return pIIThresholdVO;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.financeManager.IFinanceManagerBO#validateBuyerIDs(java.util.List)
	 * Returns a list of invalid BuyerIds from the list of Buyer IDs 
	 */
	public List<String> validateBuyerIDs(List<String> buyerIds) throws BusinessServiceException {
		List<Integer> validIds=null;
		try {
			validIds = transactionDao.validateBuyerIDs(buyerIds);
		} catch (DataServiceException e) {
			throw new BusinessServiceException(e);
		}
		List<String> invalidIds = new ArrayList<String>();
		if(validIds != null && validIds.size()!=0){
			if(buyerIds.size()!=validIds.size()){
				logger.info("One or two ids are invalid");
				for(Integer id:validIds){
					String validId = id.toString();
					buyerIds.remove(validId);
				}
				//providerIds.removeAll(validIds);
				invalidIds.addAll(buyerIds);
			}else{
				logger.info("All ids are valid");
			}
		}else{
			invalidIds.addAll(buyerIds);
			logger.warn("Null / empty list returned from TranssactionDao.");
		}
		return invalidIds;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.financeManager.IFinanceManagerBO#validateProviderIDs(java.util.List)
	 */
	public List<String> validateProviderIDs(List<String> providerIds)throws BusinessServiceException {
		List<Integer> validIds=null;
		try {
			validIds = transactionDao
					.validateProviderIDs(providerIds);
		} catch (DataServiceException e) {
			throw new BusinessServiceException(e);
		}
		List<String> invalidIds = new ArrayList<String>();
		if(validIds != null && validIds.size()!=0){
			if(providerIds.size()!=validIds.size()){
				logger.info("One or two ids are invalid");
				for(Integer id:validIds){
					String validId = id.toString();
					providerIds.remove(validId);
				}
				//providerIds.removeAll(validIds);
				invalidIds.addAll(providerIds);
			}else{
				logger.info("All ids are valid");
			}
		}else{
			invalidIds.addAll(providerIds);
			logger.warn("Null / empty list returned from TranssactionDao.");
		}

		return invalidIds;
	}

	public ILocationDao getLocationDao() {
		return locationDao;
	}

	public void setLocationDao(ILocationDao locationDao) {
		this.locationDao = locationDao;
	}

	public IVendorHdrDao getVendorHdrDao() {
		return vendorHdrDao;
	}

	public void setVendorHdrDao(IVendorHdrDao vendorHdrDao) {
		this.vendorHdrDao = vendorHdrDao;
	}

	public IVendorLocationDao getVendorLocationDao() {
		return vendorLocationDao;
	}

	public void setVendorLocationDao(IVendorLocationDao vendorLocationDao) {
		this.vendorLocationDao = vendorLocationDao;
	}

	public ILedgerFacilityBO getAcctransBo() {
		return acctransBo;
	}

	public void setAcctransBo(ILedgerFacilityBO acctransBo) {
		this.acctransBo = acctransBo;
	}

	public TransactionDao getTransactionDao() {
		return transactionDao;
	}

	public void setTransactionDao(TransactionDao transactionDao) {
		this.transactionDao = transactionDao;
	}


	public Cryptography getCryptography() {
		return cryptography;
	}

	public void setCryptography(Cryptography cryptography) {
		this.cryptography = cryptography;
	}

	public EmailTemplateBOImpl getEmailTemplateBean() {
		return emailTemplateBean;
	}

	public void setEmailTemplateBean(EmailTemplateBOImpl emailTemplateBean) {
		this.emailTemplateBean = emailTemplateBean;
	}

	public TemplateDaoImpl getTemplateDao() {
		return templateDao;
	}

	public void setTemplateDao(TemplateDaoImpl templateDao) {
		this.templateDao = templateDao;
	}

	public IVendorResourceDao getVendorResourceDao() {
		return vendorResourceDao;
	}

	public void setVendorResourceDao(IVendorResourceDao vendorResourceDao) {
		this.vendorResourceDao = vendorResourceDao;
	}

	public ServiceOrderDao getServiceOrderDao() {
		return serviceOrderDao;
	}

	public void setServiceOrderDao(ServiceOrderDao serviceOrderDao) {
		this.serviceOrderDao = serviceOrderDao;
	}

	public BuyerDao getBuyerDao() {
		return buyerDao;
	}

	public void setBuyerDao(BuyerDao buyerDao) {
		this.buyerDao = buyerDao;
	}

	public IBuyerResourceDao getBuyerResourceDao() {
		return buyerResourceDao;
	}

	public void setBuyerResourceDao(IBuyerResourceDao buyerResourceDao) {
		this.buyerResourceDao = buyerResourceDao;
	}

	/**
	 * Sets the wallet client bo.
	 * 
	 * @param walletClientBO the new wallet client bo
	 */
	public void setWalletBO(IWalletBO walletBO) {
		this.walletBO = walletBO;
	}
	public void setLookupBO(ILookupBO lookupBO) {
		this.lookupBO = lookupBO;
	}

	public IAccountDao getAccountDao() {
		return accountDao;
	}

	public void setAccountDao(IAccountDao accountDao) {
		this.accountDao = accountDao;
	}

	public IPIIThresholdDao getPiiThresholdDao() {
		return piiThresholdDao;
	}

	public void setPiiThresholdDao(IPIIThresholdDao piiThresholdDao) {
		this.piiThresholdDao = piiThresholdDao;
	}

	public List<ProviderSOReportVO> getProviderPaymentByServiceOrder(FMReportVO reportVO) throws  BusinessServiceException{
		List<ProviderSOReportVO> listOfProviderSoReportVO = null;
		try {
			listOfProviderSoReportVO = transactionDao
					.getProviderPaymentByServiceOrder(reportVO);
			logger.info("Retrieved provider payment by SO successfully");
		} catch (DataServiceException e) {
			logger.error("Error retrieving provider payment by SO", e);
			throw new BusinessServiceException(e);
		}
		return listOfProviderSoReportVO;
	}	

	public int getBuyerSOReportCount(FMReportVO reportVO) {
		return transactionDao.getBuyerSOReportCount(reportVO);

	}

	public List<BuyerSOReportVO> getBuyerPaymentByServiceOrder(
			FMReportVO reportVO) 	throws  BusinessServiceException {	
		List<FMW9ProfileVO>  profileList =null;
		List<FMW9ProfileVO>  profileListAddress =null;

		List<BuyerSOReportVO> buyerSOList=null;
		try{
			profileList =transactionDao.getFMW9History(reportVO);
			profileListAddress=transactionDao.getFMW9HistoryForAddress(reportVO);
			logger.info("Retrieved profiles successfully");
		}catch (DataServiceException e) {
			logger.error("Error retrieving FM profile history",e);
			throw new BusinessServiceException(e); 
		}
		try{
			buyerSOList=transactionDao.getBuyerPaymentByServiceOrder(reportVO);
			logger.info("Retrieved buyer payment by SO successfully");
		}catch (DataServiceException e) {
			logger.error("Error retrieving buyer payment by SO",e);
			throw new BusinessServiceException(e);
		}
		replaceTaxPayerID(convertProfileToMap(profileList),convertProfileToMap(profileListAddress),buyerSOList,reportVO.getToDate());	
		return buyerSOList;
	}


	public List<FMW9ProfileVO> getFMW9History(
			FMReportVO reportVO) 	throws  BusinessServiceException {

		List<FMW9ProfileVO>  profileList =null;
		try{
			profileList =transactionDao.getFMW9History(reportVO);
			return profileList;
		}catch (DataServiceException e) {
			logger.error("Error retrieving FM profile history",e);
			throw new BusinessServiceException(e);
		}


	}


	public List<FMW9ProfileVO> getFMW9HistoryForAddress(
			FMReportVO reportVO) 	throws  BusinessServiceException {

		List<FMW9ProfileVO>  profileList =null;
		try{
			profileList =transactionDao.getFMW9HistoryForAddress(reportVO);
			return profileList;
		}catch (DataServiceException e) {
			logger.error("Error retrieving FM profile history",e);
			throw new BusinessServiceException(e);
		}


	}
	public Map<String, BuyerSOReportVO> getBuyerPaymentByTaxPayerIdReport(
			FMReportVO reportVO,Map<Integer, List<FMW9ProfileVO>> fmW9ProfileMap,Map<Integer, List<FMW9ProfileVO>> fmW9ProfileMapAddress,Map<String, BuyerSOReportVO> map) 	throws  BusinessServiceException {
		List<BuyerSOReportVO> buyerSOList=null;
		try{			
			buyerSOList=transactionDao.getBuyerReportByTaxPayerID(reportVO);
		}catch (DataServiceException e) {
			logger.error("Error retrieving buyer payment by Taxpayer ID",e);
			throw new BusinessServiceException(e);
		}
		replaceTaxPayerID(fmW9ProfileMap,fmW9ProfileMapAddress,buyerSOList,map,reportVO.getToDate());	
		logger.info("Leaving getBuyerPaymentByTaxPayerIdReport");
		return map;
	}	

	public List<BuyerSOReportVO> getBuyerPaymentByServiceOrderReport(
			FMReportVO reportVO,Map<Integer, List<FMW9ProfileVO>> fmW9ProfileMap,Map<Integer, List<FMW9ProfileVO>> fmW9ProfileMapAddress) 	throws  BusinessServiceException {
		List<BuyerSOReportVO> buyerSOList=null;
		try{
			buyerSOList=transactionDao.getBuyerPaymentByServiceOrder(reportVO);
			logger.info("Retrieved buyer payment by SO successfully");
		}catch (DataServiceException e) {
			logger.error("Error retrieving buyer payment by SO",e);
			throw new BusinessServiceException(e);
		}

		replaceTaxPayerID(fmW9ProfileMap,fmW9ProfileMapAddress,buyerSOList,reportVO.getToDate());	
		return buyerSOList;
	}

	public Map convertProfileToMap(List<FMW9ProfileVO> profileList) {
		Map<Integer, List<FMW9ProfileVO>> map = new HashMap<Integer, List<FMW9ProfileVO>>();
		for (Iterator iterator = profileList.iterator(); iterator.hasNext();) {
			FMW9ProfileVO profileVO = (FMW9ProfileVO) iterator.next();
			if (map.containsKey(profileVO.getVendorId())) {
				map.get(profileVO.getVendorId()).add(profileVO);
			} else {
				List<FMW9ProfileVO> list = new ArrayList<FMW9ProfileVO>();
				list.add(profileVO);
				map.put(profileVO.getVendorId(), list);
			}
		}		
		return map;
	}

	private void replaceTaxPayerID(Map<Integer,List<FMW9ProfileVO>> map,Map<Integer,List<FMW9ProfileVO>> mapAddress,
			List<BuyerSOReportVO> buyerSOList,Date toDate) {
		for (Iterator iterator = buyerSOList.iterator(); iterator.hasNext();) {
			BuyerSOReportVO buyerSOReportVO = (BuyerSOReportVO) iterator.next();
			List<FMW9ProfileVO> list=map.get(buyerSOReportVO.getProviderFirmId());
			if(list!=null){
				for (Iterator iterator2 = list.iterator(); iterator2.hasNext();) {
					FMW9ProfileVO profileVO = (FMW9ProfileVO) iterator2.next();
					if(buyerSOReportVO.getPaymentDate().compareTo(profileVO.getModifiedDate())>0){
						buyerSOReportVO.setEncrypedTaxPayerId(profileVO.getEncryptedTaxPayerId());
						buyerSOReportVO.setTaxPayerTypeId(profileVO.getTaxPayerType());
						buyerSOReportVO.setTaxPayerType(profileVO.getTaxPayerTypeDescr());
						buyerSOReportVO.setExempt(profileVO.getExempt());
						buyerSOReportVO.setProviderFirmName(profileVO.getBusinessName());
						buyerSOReportVO.setDbaName(profileVO.getDbaName());
						buyerSOReportVO.setStreet1(profileVO.getStreet1());
						buyerSOReportVO.setStreet2(profileVO.getStreet2());
						buyerSOReportVO.setCity(profileVO.getCity());
						buyerSOReportVO.setZip(profileVO.getZip());
						buyerSOReportVO.setZip4(profileVO.getZip4());
						buyerSOReportVO.setState(profileVO.getState());
						break;
					}
				}
			}

		} 

		// for setting address 

		for (Iterator iterator = buyerSOList.iterator(); iterator.hasNext();) {
			BuyerSOReportVO buyerSOReportVO = (BuyerSOReportVO) iterator.next();
			List<FMW9ProfileVO> list=mapAddress.get(buyerSOReportVO.getProviderFirmId());
			if(list!=null){
				for (Iterator iterator2 = list.iterator(); iterator2.hasNext();) {
					FMW9ProfileVO profileVO = (FMW9ProfileVO) iterator2.next();


					if(buyerSOReportVO.getEncrypedTaxPayerId().equals(profileVO.getEncryptedTaxPayerId())

							&& buyerSOReportVO.getProviderFirmId().equals(profileVO.getVendorId())
							&& buyerSOReportVO.getTaxPayerTypeId()==profileVO.getTaxPayerType()
							&& toDate.compareTo(profileVO.getModifiedDate())>0)

					{
						//buyerSOReportVO.setEncrypedTaxPayerId(profileVO.getEncryptedTaxPayerId());
						//buyerSOReportVO.setTaxPayerTypeId(profileVO.getTaxPayerType());
						//buyerSOReportVO.setTaxPayerType(profileVO.getTaxPayerTypeDescr());
						//buyerSOReportVO.setExempt(profileVO.getExempt());

						buyerSOReportVO.setProviderFirmName(profileVO.getBusinessName());
						buyerSOReportVO.setDbaName(profileVO.getDbaName());
						buyerSOReportVO.setStreet1(profileVO.getStreet1());
						buyerSOReportVO.setStreet2(profileVO.getStreet2());
						buyerSOReportVO.setCity(profileVO.getCity());
						buyerSOReportVO.setZip(profileVO.getZip());
						buyerSOReportVO.setZip4(profileVO.getZip4());
						buyerSOReportVO.setState(profileVO.getState());
						break;
					}
				}
			}

		}



	}
	private void replaceTaxPayerID(Map<Integer,List<FMW9ProfileVO>> map,Map<Integer,List<FMW9ProfileVO>> mapAddress,
			List<BuyerSOReportVO> buyerSOList,Map<String, BuyerSOReportVO> mapOfProfiles,Date toDate) {
		for (Iterator iterator = buyerSOList.iterator(); iterator.hasNext();) {
			BuyerSOReportVO buyerSOReportVO = (BuyerSOReportVO) iterator.next();
			List<FMW9ProfileVO> list=map.get(buyerSOReportVO.getProviderFirmId());
			if(list!=null){
				for (Iterator iterator2 = list.iterator(); iterator2.hasNext();) {
					FMW9ProfileVO profileVO = (FMW9ProfileVO) iterator2.next();
					if(null != buyerSOReportVO.getPaymentDate() && buyerSOReportVO.getPaymentDate().compareTo(profileVO.getModifiedDate())>0){
						buyerSOReportVO.setEncrypedTaxPayerId(profileVO.getEncryptedTaxPayerId());
						buyerSOReportVO.setTaxPayerTypeId(profileVO.getTaxPayerType());
						buyerSOReportVO.setTaxPayerType(profileVO.getTaxPayerTypeDescr());
						buyerSOReportVO.setExempt(profileVO.getExempt());
						buyerSOReportVO.setProviderFirmName(profileVO.getBusinessName());
						buyerSOReportVO.setDbaName(profileVO.getDbaName());
						buyerSOReportVO.setStreet1(profileVO.getStreet1());
						buyerSOReportVO.setStreet2(profileVO.getStreet2());
						buyerSOReportVO.setCity(profileVO.getCity());
						buyerSOReportVO.setZip(profileVO.getZip());
						buyerSOReportVO.setZip4(profileVO.getZip4());
						buyerSOReportVO.setState(profileVO.getState());
						break;
					}
				}
			}
			/*if(mapOfProfiles.containsKey(buyerSOReportVO.getEncrypedTaxPayerId())){
				BigDecimal totalGrossPayment=mapOfProfiles.get(buyerSOReportVO.getEncrypedTaxPayerId()).getTotalGrossPayment().add(buyerSOReportVO.getTotalGrossPayment());
				mapOfProfiles.get(buyerSOReportVO.getEncrypedTaxPayerId()).setTotalGrossPayment(totalGrossPayment);
			}else{
				mapOfProfiles.put(buyerSOReportVO.getEncrypedTaxPayerId(), buyerSOReportVO);	
				}*/
			// changes for jira #SL-20969
			String tempVar=buyerSOReportVO.getEncrypedTaxPayerId()+ buyerSOReportVO.getTaxPayerTypeId()+buyerSOReportVO.getTaxPayerType()+buyerSOReportVO.getExempt();
			if(mapOfProfiles.containsKey(tempVar)){
				BigDecimal totalGrossPayment=mapOfProfiles.get(tempVar).getTotalGrossPayment().add(buyerSOReportVO.getTotalGrossPayment());
				mapOfProfiles.get(tempVar).setTotalGrossPayment(totalGrossPayment);
			}else{
				mapOfProfiles.put(tempVar, buyerSOReportVO);    
			}
		}
		logger.info("for setting address");
		if(null!=mapAddress && mapAddress.size()>0)
		{
			logger.info("mapAddress.size()"+mapAddress.size());
		}

		// for setting address 

		for (Iterator iterator = buyerSOList.iterator(); iterator.hasNext();) {
			BuyerSOReportVO buyerSOReportVO = (BuyerSOReportVO) iterator.next();
			List<FMW9ProfileVO> list=mapAddress.get(buyerSOReportVO.getProviderFirmId());
			if(list!=null){
				for (Iterator iterator2 = list.iterator(); iterator2.hasNext();) {
					FMW9ProfileVO profileVO = (FMW9ProfileVO) iterator2.next();


					if(buyerSOReportVO.getEncrypedTaxPayerId().equals(profileVO.getEncryptedTaxPayerId())

							&& buyerSOReportVO.getProviderFirmId().equals(profileVO.getVendorId())
							&& buyerSOReportVO.getTaxPayerTypeId()==profileVO.getTaxPayerType()
							&& toDate.compareTo(profileVO.getModifiedDate())>0)

					{
						//buyerSOReportVO.setEncrypedTaxPayerId(profileVO.getEncryptedTaxPayerId());
						//buyerSOReportVO.setTaxPayerTypeId(profileVO.getTaxPayerType());
						//buyerSOReportVO.setTaxPayerType(profileVO.getTaxPayerTypeDescr());
						//buyerSOReportVO.setExempt(profileVO.getExempt());

						buyerSOReportVO.setProviderFirmName(profileVO.getBusinessName());
						buyerSOReportVO.setDbaName(profileVO.getDbaName());
						buyerSOReportVO.setStreet1(profileVO.getStreet1());
						buyerSOReportVO.setStreet2(profileVO.getStreet2());
						buyerSOReportVO.setCity(profileVO.getCity());
						buyerSOReportVO.setZip(profileVO.getZip());
						buyerSOReportVO.setZip4(profileVO.getZip4());
						buyerSOReportVO.setState(profileVO.getState());
						break;
					}
				}
			}

		}



		logger.info("for setting address");
		if(null!=mapAddress && mapAddress.size()>0)
		{
			logger.info("mapAddress.size()"+mapAddress.size());
		}

		// for setting address 

		for (Iterator iterator = buyerSOList.iterator(); iterator.hasNext();) {
			BuyerSOReportVO buyerSOReportVO = (BuyerSOReportVO) iterator.next();
			List<FMW9ProfileVO> list=mapAddress.get(buyerSOReportVO.getProviderFirmId());
			if(list!=null){
				for (Iterator iterator2 = list.iterator(); iterator2.hasNext();) {
					FMW9ProfileVO profileVO = (FMW9ProfileVO) iterator2.next();


					if(buyerSOReportVO.getEncrypedTaxPayerId().equals(profileVO.getEncryptedTaxPayerId())

							&& buyerSOReportVO.getProviderFirmId().equals(profileVO.getVendorId())
							&& buyerSOReportVO.getTaxPayerTypeId()==profileVO.getTaxPayerType()
							&& toDate.compareTo(profileVO.getModifiedDate())>0)

					{
						//buyerSOReportVO.setEncrypedTaxPayerId(profileVO.getEncryptedTaxPayerId());
						//buyerSOReportVO.setTaxPayerTypeId(profileVO.getTaxPayerType());
						//buyerSOReportVO.setTaxPayerType(profileVO.getTaxPayerTypeDescr());
						//buyerSOReportVO.setExempt(profileVO.getExempt());

						buyerSOReportVO.setProviderFirmName(profileVO.getBusinessName());
						buyerSOReportVO.setDbaName(profileVO.getDbaName());
						buyerSOReportVO.setStreet1(profileVO.getStreet1());
						buyerSOReportVO.setStreet2(profileVO.getStreet2());
						buyerSOReportVO.setCity(profileVO.getCity());
						buyerSOReportVO.setZip(profileVO.getZip());
						buyerSOReportVO.setZip4(profileVO.getZip4());
						buyerSOReportVO.setState(profileVO.getState());
						break;
					}
				}
			}

		}



	}

	public List<BuyerSOReportVO> getBuyerReportByTaxPayerID(FMReportVO reportVO)throws BusinessServiceException {
		List<FMW9ProfileVO>  profileList =null;
		List<BuyerSOReportVO> buyerSOList=null;
		try{
			profileList =transactionDao.getFMW9History(reportVO);
		}catch (DataServiceException e) {
			logger.error("Error retrieving FM profile history",e);
			throw new BusinessServiceException(e);
		}
		try{
			buyerSOList=transactionDao.getBuyerReportByTaxPayerID(reportVO);
		}catch (DataServiceException e) {
			logger.error("Error retrieving buyer report by tax payer id ",e);
			throw new BusinessServiceException(e);
		}
		Map<String, BuyerSOReportVO> map=new HashMap<String, BuyerSOReportVO>();
		logger.info("Inside wrong call");
		replaceTaxPayerID(convertProfileToMap(profileList),null,buyerSOList,map,null);
		return new ArrayList<BuyerSOReportVO>(map.values());
	}	

	public List<ProviderSOReportVO> getProviderNetSummaryReport(FMReportVO reportVO) throws BusinessServiceException{
		List<ProviderSOReportVO>  listOfProviderSO=null;
		try{
			listOfProviderSO= transactionDao.getProviderNetSummaryReport(reportVO);
		}catch (DataServiceException e) {
			logger.error("Error retrieving provider net summary",e);
			throw new BusinessServiceException(e);
		}
		return 	listOfProviderSO;
	}


	public List<AdminPaymentVO> getAdminPaymentReport(FMReportVO reportVO)throws BusinessServiceException {
		List<AdminPaymentVO> listOfAdminPayment = null;
		try {
			listOfAdminPayment = transactionDao.getAdminPaymentReport(reportVO);
		} catch (DataServiceException e) {
			logger.error("Error retrieving get Admin Payment Report", e);
			throw new BusinessServiceException(e);
		}
		return listOfAdminPayment;
	}

	public void saveInputsForReportsScheduler(FMReportVO fmReportVO){	
		StringBuilder reportName = new StringBuilder("");
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat timeStamp = new SimpleDateFormat("MMM_dd_yyyy_HH_mm_ss_zzz");
		reportName = reportName.append(getReportName(fmReportVO.getReportName()));
		reportName.append("_").append(timeStamp.format(cal.getTime()));
		fmReportVO.setReportNameForExport(reportName.toString());
		fmReportVO.setReportStatus(OrderConstants.REPORT_QUEUED);
		try {			
			boolean isSuccess = transactionDao.saveInputsForReportScheduler(fmReportVO);
			logger.info(" Inserting Report Scheduler Details ");
			if(isSuccess){
				logger.info(" Successfully inserted scheduler inputs ");
			}else{
				logger.info(" Falied To Insert scheduler inputs ");
			}
		} catch (Exception e) {
			logger.error(this.getClass().getName()+ ":- saveInputsForReportsScheduler - Exception thrown while inserting Report Scheduler details", e);				
		}
	}

	private String getReportName(String reportType) {
		if (OrderConstants.PROVIDER_SO_REPORT.equalsIgnoreCase(reportType)) {
			return OrderConstants.PROVIDER_SO_REPORTNAME;
		} else if (OrderConstants.PROVIDER_REV_REPORT.equalsIgnoreCase(reportType)) {
			return OrderConstants.PROVIDER_REV_REPORTNAME;
		} else if (OrderConstants.BUYER_SO_REPORT.equalsIgnoreCase(reportType)) {
			return OrderConstants.BUYER_SO_REPORTNAME;
		} else if (OrderConstants.BUYER_TAXID_REPORT.equalsIgnoreCase(reportType)) {
			return OrderConstants.BUYER_TAXID_REPORTNAME;
		} else if (OrderConstants.ADMIN_PAYMENT_REPORT.equalsIgnoreCase(reportType)) {
			return OrderConstants.ADMIN_PAYMENT_REPORTNAME;
		}else {
			return "";
		}
	}

	public List<ExportStatusVO>  getReportStatus(int slId) throws BusinessServiceException{
		List<ExportStatusVO> listStatusVO = null;
		try {
			listStatusVO = transactionDao.getReportStatus(slId);
			logger.info("Data fetch successfull.");
		} catch (Exception e) {
			logger.error("Exception thrown while fetching export scheduler details", e);
			throw new BusinessServiceException(e);
		}
		return listStatusVO ;
	}

	public List<FMReportVO> getCompletedAndFailedRecords() throws BusinessServiceException{
		List <FMReportVO> records =null;
		try{
			records = transactionDao.getCompletedAndFailedRecords();
			logger.info("Data fetch successfull.");
		}catch (DataServiceException e) {
			logger.error("Exception thrown while fetching completed details", e);
			throw new BusinessServiceException(e);
		}
		return records;
	}

	public void updateDeletedStatus(List<Integer> reportIds) throws BusinessServiceException{
		try{
			transactionDao.updateDeletedStatus(reportIds);
			logger.info("Updating deleted status");
		}catch (DataServiceException e) {
			logger.error("Exception updating deleted status", e);
			throw new BusinessServiceException(e);
		}
	}

	public List<FMReportVO> getQueuedRecords(Integer numberOfReportsToBeProcessed)  throws BusinessServiceException{
		List <FMReportVO> records =null;
		try{
			records=transactionDao.getQueuedRecords(numberOfReportsToBeProcessed);
			logger.info("Successfull fetched queued");
		}catch (DataServiceException e) {
			logger.error("Exception in getting queued records", e);
			throw new BusinessServiceException(e);
		}
		return records;
	}

	public void updateStatusOfExport(FMReportVO reportVo) throws BusinessServiceException {
		try{
			transactionDao.updateStatusOfExport(reportVo);
			logger.info("Updating staus of export");
		}catch  (DataServiceException e) {
			logger.error("Exception in updating status of export", e);
			throw new BusinessServiceException(e);
		}
	}

	public void updateExportDetails(FMReportVO reportVO) throws BusinessServiceException{
		try{
			transactionDao.updateExportDetails(reportVO);
		}catch  (DataServiceException e) {
			throw new BusinessServiceException(e);
		}
	}
	public  FMReportVO getReportCritirea(Integer reportId) throws BusinessServiceException, Exception{
		FMReportVO fmReportVO = null;
		try{
			fmReportVO = transactionDao.getReportCritirea(reportId);			
			//If file doesn't exists then mark the report as deleted			
			if(null != fmReportVO && !StringUtils.isEmpty(fmReportVO.getFilePath())){
				//Delete the report when ever user tries to access the file.
				//User can access the file only on completed status.(to display/download)
				//Reports in other status can only be deleted.
				File fileToRead = new File(fmReportVO.getFilePath());
				if(!fileToRead.exists() || !fileToRead.isFile()){
					fmReportVO.setReportStatus(OrderConstants.REPORT_DELETED);
					fmReportVO.setReportId(reportId);
					transactionDao.deleteReport(fmReportVO);
					if(!OrderConstants.REPORT_COMPLETED.equalsIgnoreCase(fmReportVO.getReportStatus())){
						return fmReportVO;
					}
					throw new BusinessServiceException("No such report exists");
				}
			}
		}catch(DataServiceException de){
			logger.error(de);
			throw new BusinessServiceException(de);
		}catch (Exception e) {
			logger.error(e);
			throw e;
		}
		return fmReportVO;
	}

	public boolean deleteReport(FMReportVO fmReportVO) throws BusinessServiceException, IOException, Exception{
		FMReportVO reportVO = null;
		boolean success = false;
		try{
			//Fetch report criteria for given report id
			//If no file exists then report will be marked as Deleted.
			try{
				reportVO = getReportCritirea(fmReportVO.getReportId());
			}catch(Exception e){
				logger.info("ignore this exception");
				success = true;
				//As we are going to delete the report, ignore the exception
				//raised (File doesn't exist exception)
			}			
			if(null != reportVO){
				//For completed reports delete file if exists
				if(reportVO.getReportStatus().equalsIgnoreCase(OrderConstants.REPORT_COMPLETED)){
					String filePath = reportVO.getFilePath();
					//If file path contains the file name.
					List<String> listStr = new ArrayList<String>(Arrays.asList(filePath.split("/")));
					if(!StringUtils.isEmpty(reportVO.getFilePath())&& listStr.contains(reportVO.getReportNameForExport().concat(".csv"))){
						File fileToDelete = new File(filePath);
						if(!fileToDelete.exists()){
							//When no file exists, assume that it is deleted
							success = true;
						}else{
							//delete the file						
							success = delelteFile(filePath);
						}
					}else{
						throw new BusinessServiceException("No such file exits.");
					}
					if(!success){
						// if file deletion failed
						throw new BusinessServiceException("Failed to delete file");
					}
				}else{
					//For reports other than completed
					success = true;
				}
				//Mark the report as deleted 
				if(success){
					int count = transactionDao.deleteReport(fmReportVO);
					if(count == 0){
						throw new BusinessServiceException("Failed to delete report");
					}else{
						success = true;
					}
				}else{
					throw new BusinessServiceException("Report is not deleted");
				}
			}else{
				throw new BusinessServiceException("No such report exists");
			}
		}catch (DataServiceException de) {
			logger.error(de);
			throw new BusinessServiceException(de);
		}catch (Exception e) {
			logger.error(e);
			throw e;
		}	
		return success;			
	}

	/* 
	 * Method to delete file from server.
	 * 
	 * */
	private boolean delelteFile(String filePath) throws BusinessServiceException {
		File fileToDelete = null;
		try{
			fileToDelete = new File(filePath);
			if(fileToDelete.exists()){
				return fileToDelete.delete();
			}
		}catch (Exception e) {
			logger.error(e);
			throw new BusinessServiceException(e);
		}		
		return false;
	}

	/*
	 * 
	 * */
	public ByteArrayOutputStream getFileContent(String filePath) throws BusinessServiceException {
		ByteArrayOutputStream outFinal = new ByteArrayOutputStream();
		try{
			File csvFile = null;
			if(!StringUtils.isEmpty(filePath)){
				byte readBuf[] = new byte[512*1024];
				csvFile = new File(filePath);
				FileInputStream fin = null;
				if(csvFile.exists()){
					fin = new FileInputStream(csvFile);
					int readCnt = fin.read(readBuf);
					while (0 < readCnt) {
						outFinal.write(readBuf, 0, readCnt);
						readCnt = fin.read(readBuf);
					}
					fin.close();
				}else{
					logger.warn("No such file");
					throw new BusinessServiceException("Exception in reading file.");
				}
				outFinal.close();
			}else{
				return null;
			}
		}catch (IOException e) {
			throw new BusinessServiceException(e);
		}catch(Exception e){
			throw new BusinessServiceException(e);
		}
		return outFinal;
	}

	public void updateAsInProcess(List<FMReportVO> listReportVO) throws BusinessServiceException  {
		for(FMReportVO fmReportVO: listReportVO){
			try{				
				transactionDao.updateStatusOfExport(fmReportVO);
			}catch (DataServiceException e) {
				throw new BusinessServiceException(e);
			}catch(Exception e){
				throw new BusinessServiceException(e);
			}			
		}		
	}


	/** 
	 * getBuyerReportByTaxPayerIDForFromCsv(FMReportVO) : Parse the csv file generated
	 * by the batch processing and populate a list of VOs.
	 * @param reportVO : FMReportVO with filePath being set.
	 * @return list of BuyerSOReportVO
	 * 
	 */
	public List<BuyerSOReportVO> getBuyerReportByTaxPayerIDForFromCsv(
			FMReportVO reportVO) throws BusinessServiceException{
		List<FMW9ProfileVO>  profileList =null;
		List<BuyerSOReportVO> buyerSOList= new ArrayList<BuyerSOReportVO>();		
		if(null != reportVO && !StringUtils.isEmpty(reportVO.getFilePath())){
			String filePath = reportVO.getFilePath();
			try{				  
				// Open the file that is the first 
				FileInputStream fstream = new FileInputStream(filePath);
				// Get the object of DataInputStream
				DataInputStream in = new DataInputStream(fstream);
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				String strLine;
				//Read File Line By Line
				//Skip the header
				br.readLine();
				while ((strLine = br.readLine()) != null)   {
					BuyerSOReportVO buyerSOReportVO = new BuyerSOReportVO();
					//Set value to VO
					//Assumed that, there won't be any empty string as values
					List<String> contents = convertCommaSepStrToList(strLine);
					if(null!=contents && contents.size()<2){
						//This row has only 1 column. Implies it is the footer. Then stop processing
						break;
					}
					int ind = 0;
					if(reportVO.getRole().intValue() == OrderConstants.NEWCO_ADMIN_ROLEID){
						//For sl admin
						buyerSOReportVO.setBuyerId(contents.get(0));
						buyerSOReportVO.setBuyerName(contents.get(1));
						ind = 2;							
					}
					buyerSOReportVO.setProviderFirmId(Integer.valueOf(contents.get(0+ind)));
					buyerSOReportVO.setProviderFirmName(contents.get(1+ind));
					buyerSOReportVO.setDbaName(contents.get(2+ind));		       
					buyerSOReportVO.setTaxPayerType(contents.get(3+ind));
					buyerSOReportVO.setExempt(contents.get(4+ind));
					buyerSOReportVO.setTinType(contents.get(5+ind));
					buyerSOReportVO.setDecrypedTaxPayerId(contents.get(6+ind));
					//Changes for SL-20958 --START
					buyerSOReportVO.setStreet1(contents.get(7+ind));
					buyerSOReportVO.setStreet2(contents.get(8+ind));
					buyerSOReportVO.setCity(contents.get(9+ind));
					buyerSOReportVO.setState(contents.get(10+ind));
					buyerSOReportVO.setZip(contents.get(11+ind));
					buyerSOReportVO.setTotalGrossPayment(new BigDecimal(contents.get(12+ind).replace(",", "").replace("$", "")));	
					//Changes for SL-20958 --END
					buyerSOList.add(buyerSOReportVO);
				}				  
				in.close();
			}catch (Exception e){//Catch exception if any
				throw new BusinessServiceException(e);
			}
		}else{
			throw new BusinessServiceException("File path is empty!.");
		}		 
		return buyerSOList;
	}

	/**
	 * Converts a comma separated string into a list of string.
	 * @param inputStr : comma separated string. Each content is enclosed
	 * in double quotes.
	 * @return : Returns a list of string. Empty string won't be
	 * added to the list.  
	 * */
	private List<String> convertCommaSepStrToList(String inputStr) {
		String formatedStr = inputStr.trim();
		//Replace "" with a term 'Quote'.
		String stringWithCommas = formatedStr.replaceAll("\"\"", OrderConstants.CSV_QOUTE);
		if (null!= stringWithCommas) {
			List<String> listContent = new ArrayList<String>(
					Arrays.asList(stringWithCommas.split("\"")));
			List<String > formatedList = new ArrayList<String>();
			for(String content:listContent){
				String val = content.replaceAll("\"", "").replace(",", "");
				val = val.replaceAll(OrderConstants.CSV_QOUTE, "\"");
				if(!"".equalsIgnoreCase(val)){
					formatedList.add(val);
				}				
			}
			return formatedList;
		} else {
			return null;
		}
	}

	public void checkAndUpdateInProcessReport(int timeInSec)throws BusinessServiceException {
		try{
			int count =0;
			count = transactionDao.checkAndUpdateInProcessReport(timeInSec);
			logger.info(count+" number of rows updated.");
		}catch (DataServiceException e) {
			logger.error("failed to update  ."+e.toString());
			throw new BusinessServiceException(e);
		}catch(Exception e){
			logger.error("Failed to update  ."+e.toString());
			throw new BusinessServiceException(e);
		}	
	}


	//code change for SLT- 2231
	public void sendAccountHoldReleaseMail(Integer entityId, String entityType,Integer templateId,ArrayList<Integer> docIds)
			throws BusinessServiceException, DBException, MessagingException, DataServiceException {
		TemplateVo template =null;
		EmailVO emailVO = new EmailVO();
		Contact contact = null;
		ArrayList<DocumentVO> attachmentVOs = new ArrayList<DocumentVO>();
		try {
			template = templateDao.getTemplate(templateId);
		} catch (DBException e) {
			throw new DBException("Exception fetching template details");
		}
		try {
			attachmentVOs = simpleDocumentDao.getAttachements(docIds);
		} catch (DataServiceException ex) {
			logger.error("Exception thrown while getting attachements details from document table", ex);
			throw new DataServiceException("Exception thrown while getting attachements details from document table", ex);
		}
		try {
			if (OrderConstants.PROVIDER.equalsIgnoreCase(entityType)) {
				contact = contactDao.getByVendorId(entityId);
			}
			if (contact != null && contact.getEmail() != null && !"".equals(contact.getEmail())) {
				 emailVO.setTo(contact.getEmail());
				emailVO.setSubject(template.getTemplateSubject());
				emailVO.setFrom(template.getTemplateFrom());
				emailVO.setMessage(template.getTemplateSource());
				emailVO.setTemplateId(template.getTemplateId());
				emailTemplateBean.sendGenericEmailWithMutipleAttachment(emailVO, attachmentVOs);
			}
		} catch (MessagingException | IOException e) {
			logger.error("Exception thrown while getting template details or Sending Mail", e);
            throw new MessagingException("Exception thrown while getting template details or Sending Mail");
		}

	}
	
	
	// slt-2232 START
	public void sendTransferSLBucksEscheatmentEmail(FMTransferFundsVO transferFundsVO) throws BusinessServiceException {
		EmailVO emailVO = new EmailVO();
		Contact contact = null;
		String createdDate;
		String entityType = transferFundsVO.getRoleType();
		Integer templateId = null;
		try {
			templateId = transactionDao.getTemplateIdByReasoncode(transferFundsVO.getReasonCode());
		} catch (Exception e) {
			logger.error("Exception thrown while getting template details or Sending Mail", e);
			throw new BusinessServiceException("Error while fetching template details");
		}

		Integer entityId = transferFundsVO.getFromAccount();
		String username = transferFundsVO.getUser();
		Double amt = transferFundsVO.getAmount();
		DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		createdDate = sdf.format(new Date());
		if (OrderConstants.PROVIDER.equalsIgnoreCase(entityType)) {
			try {
				contact = contactDao.getByVendorId(entityId);
			} catch (DBException e) {
				logger.error("Exception thrown while getting contact details", e);
				throw new BusinessServiceException("Error occured while getting contact details");
			}
		}
		if (contact == null) {
			contact = getContactFromUsername(username);
		}

		if (contact != null && contact.getEmail() != null && !"".equals(contact.getEmail())) {
			emailVO.setTo(contact.getEmail());
			emailVO.setFrom(AlertConstants.SERVICE_LIVE_MAILID);
			emailVO.setTemplateId(templateId);
			try {
				emailTemplateBean.sendTransferSLBucksEscheatmentEmail(emailVO, amt, entityType, createdDate);
			} catch (MessagingException | IOException e) {
				logger.error("Exception thrown while sending email to the provider", e);

			}
		}

	}

	public Contact getContactFromUsername(String username) throws BusinessServiceException {
		Contact contact = null;
		try {
			Integer contactId = contactDao.getContactIdByUserName(username);
			if (contactId != null) {
				contact = new Contact();
				contact.setContactId(contactId);
				contact = contactDao.query(contact);
			}
		} catch (DBException e1) {
			logger.error("Exception thrown while getting buyer contact by username", e1);
			throw new BusinessServiceException("Error occured while getting contact details");
		}
		return contact;
	}
	// slt-2232 END

	public Cryptography128 getCryptography128() {
		return cryptography128;
	}

	public void setCryptography128(Cryptography128 cryptography128) {
		this.cryptography128 = cryptography128;
	}
}
