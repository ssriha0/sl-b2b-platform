package com.newco.marketplace.business.businessImpl.buyer;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.log4j.Logger;


import com.newco.marketplace.business.iBusiness.buyer.IBuyerPersIdentificationBO;
import com.newco.marketplace.dto.vo.CryptographyVO;
import com.newco.marketplace.dto.vo.buyer.BuyerPIIVO;
import com.newco.marketplace.dto.vo.serviceorder.Buyer;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.MPConstants;
import com.newco.marketplace.interfaces.ProviderConstants;
import com.newco.marketplace.persistence.daoImpl.so.buyer.BuyerDaoImpl;
import com.newco.marketplace.persistence.iDao.so.buyer.BuyerDao;
import com.newco.marketplace.persistence.iDao.so.buyerPII.IBuyerPIIDao;
import com.newco.marketplace.util.Cryptography;
import com.newco.marketplace.utils.ServiceLiveStringUtils;
import com.newco.marketplace.vo.buyer.PersonalIdentificationVO;
import com.servicelive.common.exception.SLBusinessServiceException;


public class BuyerPersIdentificationBOImpl implements IBuyerPersIdentificationBO{
	
	private Cryptography cryptography;
	private IBuyerPIIDao buyerPIIDao;
	private BuyerDao buyerDao;
	
	private static Logger logger = Logger.getLogger(BuyerPersIdentificationBOImpl.class.getName());
	
	/**
	   * Saves the personal identification information - EIN in the PII history for the specified buyer ID
	   * 
	   * @param ein
	   * @param buyerId
	   * @param businessName
	   * @param userName
	   * @throws BusinessServiceException
	   */
	public void saveBuyerEIN(Integer buyerId, String ein, String businessName, String userName) throws BusinessServiceException {
		BuyerPIIVO buyerPII = new BuyerPIIVO();
		try {
		
			// Encrypt the EinNo and then save
			CryptographyVO cryptographyVO = new CryptographyVO();
			cryptographyVO.setInput(ein);
			cryptographyVO.setKAlias(MPConstants.ENCRYPTION_KEY);
			cryptographyVO = cryptography.encryptKey(cryptographyVO);
		
			buyerPII.setBuyerId(buyerId);
			buyerPII.setIdType("EIN");
			buyerPII.setIdNoEnc(cryptographyVO.getResponse());
			buyerPII.setBusinessName(businessName);
			buyerPII.setCountry("N/A");
			buyerPII.setDob(null);			
			buyerPII.setModifiedBy(userName);
			buyerPII.setCreatedDate(new Date(System.currentTimeMillis()));
			
			buyerPIIDao.saveBuyerPII(buyerPII);
		} catch (DataServiceException e) {
			logger.error("Error Saving Buyer EIN in history");
			throw new BusinessServiceException("Error Saving Buyer EIN in history-->", e);
		}
		
	}
	
	/**
	   * Saves the personal identification information - SSN in the PII history for the specified buyer ID
	   * 
	   * @param ein
	   * @param buyerId
	   * @param businessName
	   * @param userName
	   * @throws BusinessServiceException
	   */
	public void saveBuyerSSN(Integer buyerId, String ssn, String dob, String businessName, String userName) throws BusinessServiceException {
		BuyerPIIVO buyerPII = new BuyerPIIVO();
		try {
		
			// Encrypt the SSNNo and then save
			CryptographyVO cryptographyVO = new CryptographyVO();
			cryptographyVO.setInput(ssn);
			cryptographyVO.setKAlias(MPConstants.ENCRYPTION_KEY);
			cryptographyVO = cryptography.encryptKey(cryptographyVO);
		
			buyerPII.setBuyerId(buyerId);
			buyerPII.setIdType("SSN");
			buyerPII.setIdNoEnc(cryptographyVO.getResponse());
			buyerPII.setBusinessName(businessName);
			buyerPII.setCountry("N/A");
			buyerPII.setDob(dob);
			buyerPII.setModifiedBy(userName);
			buyerPII.setCreatedDate(new Date(System.currentTimeMillis()));
			
			buyerPIIDao.saveBuyerPII(buyerPII);
		} catch (DataServiceException e) {
			logger.error("Error Saving Buyer SSN in history");
			throw new BusinessServiceException("Error Saving Buyer SSN in history-->", e);
		}
		
	}
	
	/**
	   * Saves the personal identification information - Alternate tax id in the PII history for the specified buyer ID
	   * 
	   * @param buyerVO 
	   * @throws BusinessServiceException
	   */
	public void saveBuyerAltId(Buyer buyerVO) throws BusinessServiceException {
		BuyerPIIVO buyerPII = new BuyerPIIVO();
		try {
		
			// Encrypt the AltIdNo and then save
			CryptographyVO cryptographyVO = new CryptographyVO();
			cryptographyVO.setInput(buyerVO.getEinNoEnc());
			cryptographyVO.setKAlias(MPConstants.ENCRYPTION_KEY);
			cryptographyVO = cryptography.encryptKey(cryptographyVO);
		
			buyerPII.setBuyerId(buyerVO.getBuyerId());
			buyerPII.setIdType(buyerVO.getAltIDDocType());
			buyerPII.setIdNoEnc(cryptographyVO.getResponse());
			buyerPII.setBusinessName(buyerVO.getBusinessName());
			buyerPII.setCountry(buyerVO.getAltIDCountryIssue());
			buyerPII.setDob(buyerVO.getDob());		
			buyerPII.setModifiedBy(buyerVO.getUserName());
			buyerPII.setCreatedDate(new Date(System.currentTimeMillis()));
			
			buyerPIIDao.saveBuyerPII(buyerPII);
		} catch (DataServiceException e) {
			logger.error("Error Saving Buyer Alternate tax id  in history");
			throw new BusinessServiceException("Error Saving Alternate tax id in history-->", e);
		}
	}
	
	/**
	   * Fetches the personal identification information from buyer table for the view and edit buyer PII 
	   * 
	   * @param buyerId 
	   * @throws BusinessServiceException
	   */
	public Buyer getBuyerPIIData(Integer buyerId) throws BusinessServiceException {
		Buyer buyerVO = new Buyer();
		try {
			 buyerVO = buyerDao.getBuyerPII(buyerId);
			
		}catch (DataServiceException e) {
			e.printStackTrace();
		}
		return buyerVO;
	}

	public List<PersonalIdentificationVO> getPiiHistory(Integer buyerId)throws DataServiceException{
		
		List<PersonalIdentificationVO> voList = buyerDao.getPiiHistory(buyerId);
		 for(PersonalIdentificationVO vo : voList) {
			 String encryptedIdNumber = vo.getIdNumber();
			 //String idNumber = getEncryptedValue(vo.getIdNumber());
			 if(encryptedIdNumber != null) {
				 vo.setIdNumber(getDecryptedValue(encryptedIdNumber,vo.getIdType()));
			 }
		 }
		return voList;
	}
	private String getEncryptedValue(String idNumber) {
		CryptographyVO cryptographyVO = new CryptographyVO();
		cryptographyVO.setInput(idNumber);
		cryptographyVO.setKAlias(MPConstants.ENCRYPTION_KEY);
		cryptographyVO = cryptography.encryptKey(cryptographyVO);
		String encIdNum = cryptographyVO.getResponse();
		return encIdNum;
		}
		
		private String getDecryptedValue(String encryptedIdNumber,String taxPayerIdType) {
			CryptographyVO cryptographyVO = new CryptographyVO();
			cryptographyVO.setInput(encryptedIdNumber);
			cryptographyVO.setKAlias(MPConstants.ENCRYPTION_KEY);
			cryptographyVO = cryptography.decryptKey(cryptographyVO);
			String einNo = cryptographyVO.getResponse();
			if (!einNo.startsWith("XXX-XX-") && taxPayerIdType.equals("SSN"))
			{
				if(einNo!=null && einNo.length()>=8)
				{
				einNo = ProviderConstants.COMPANY_SSN_ID_MASK+einNo.substring(5);
				}
			}
			else if(!einNo.startsWith("XX-XXX") && taxPayerIdType.equals("EIN"))
			{
				if(einNo!=null && einNo.length()>=7)
				{
				einNo = ProviderConstants.COMPANY_EIN_ID_MASK+einNo.substring(5);
				}
			}
			else if(!einNo.startsWith("XXXXXXX") && !taxPayerIdType.equals("SSN") && !taxPayerIdType.equals("EIN"))
			{
				if(einNo!=null && einNo.length()>4)
				{	
				//einNo = "XXXXXXXXXX"+einNo.substring(11);
				einNo = ServiceLiveStringUtils.maskStringAlphaNum(einNo, 4, "X");
				}
			}
			return einNo;
		}
	public IBuyerPIIDao getBuyerPIIDao() {
		return buyerPIIDao;
	}

	public void setBuyerPIIDao(IBuyerPIIDao buyerPIIDao) {
		this.buyerPIIDao = buyerPIIDao;
	}
	public Cryptography getCryptography() {
		return cryptography;
	}

	public void setCryptography(Cryptography cryptography) {
		this.cryptography = cryptography;
	}

	public BuyerDao getBuyerDao() {
		return buyerDao;
	}

	public void setBuyerDao(BuyerDao buyerDao) {
		this.buyerDao = buyerDao;
	}
	
}
