/**
 *
 */
package com.newco.marketplace.business.businessImpl.provider;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.business.iBusiness.provider.IW9RegistrationBO;
import com.newco.marketplace.dto.vo.CryptographyVO;
import com.newco.marketplace.interfaces.MPConstants;
import com.newco.marketplace.interfaces.ProviderConstants;
import com.newco.marketplace.persistence.iDao.provider.IW9RegistrationDao;
import com.newco.marketplace.util.Cryptography;
import com.newco.marketplace.vo.provider.W9RegistrationVO;
import com.servicelive.wallet.serviceinterface.IWalletBO;

/**
 * @author hoza
 *
 */
public class W9RegistrationBOImpl extends ABaseBO implements IW9RegistrationBO {

	private  IW9RegistrationDao  w9RegistrationDao;
	private Cryptography cryptography;
	private IWalletBO wallet;
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.provider.IW9RegistrationBO#isW9Exists(java.lang.Integer)
	 */
	public Boolean isW9Exists(Integer vendorId) throws Exception {
		return w9RegistrationDao.isW9Exists(vendorId);
	}
	
	public Boolean isAvailableWithSSNInd(Integer vendorId) throws Exception {
		return w9RegistrationDao.isAvailableWithSSNInd(vendorId);
	}
	
	public Boolean isDobNotAvailableWithSSN(Integer vendorId) throws Exception{
		return w9RegistrationDao.isDobNotAvailableWithSSN(vendorId);
	}
	
	public String getOrginalEin(Integer vendorId)throws Exception
	{
		
		String orginalEin= w9RegistrationDao.getOrginalEin(vendorId);	
		orginalEin=getDecryptedUnMaskedValue(orginalEin);
		return orginalEin;
	}
	
	/**
	 * @return the w9RegistrationDao
	 */
	public IW9RegistrationDao getW9RegistrationDao() {
		return w9RegistrationDao;
	}
	/**
	 * @param registrationDao the w9RegistrationDao to set
	 */
	public void setW9RegistrationDao(IW9RegistrationDao registrationDao) {
		w9RegistrationDao = registrationDao;
	}
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.provider.IW9RegistrationBO#get(java.lang.Integer)
	 */
	public W9RegistrationVO get(Integer vendorId) throws Exception {
		W9RegistrationVO vo  =  w9RegistrationDao.get(vendorId);
		setDecryptedEIN(vo);
		getConvertedDate(vo);
		return vo;
	}
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.provider.IW9RegistrationBO#getForResource(java.lang.Integer)
	 */
	public W9RegistrationVO getForResource(Integer resourceId) throws Exception {
		W9RegistrationVO vo  =  w9RegistrationDao.getForResource(resourceId);
		setDecryptedEIN(vo);
		 return vo;
	}
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.provider.IW9RegistrationBO#getPrefillForResource(java.lang.Integer)
	 */
	public W9RegistrationVO getPrefillForResource(Integer resourceId)
			throws Exception {
		W9RegistrationVO vo  = w9RegistrationDao.getPrefillForResource(resourceId);
		setDecryptedEIN(vo);
		return vo;
	}
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.provider.IW9RegistrationBO#isW9ExistsForResource(java.lang.Integer)
	 */
	public Boolean isW9ExistsForResource(Integer resourceId) throws Exception {
		 return w9RegistrationDao.isW9ExistsForResource(resourceId);
	}
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.provider.IW9RegistrationBO#getPrefill(java.lang.Integer)
	 */
	public W9RegistrationVO getPrefill(Integer vendorId) throws Exception {
		W9RegistrationVO vo = w9RegistrationDao.getPrefill(vendorId);
		setDecryptedEIN(vo);
		getConvertedDate(vo);
		return vo;
	}

	private W9RegistrationVO getConvertedDate(W9RegistrationVO vo)
	{
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		if(vo!=null && vo.getDateOfBirth()!=null)
		{
			String dob=dateFormat.format(vo.getDateOfBirth());
			vo.setDob(dob);
		}
		return vo;
	}
	
	
	private void setDecryptedEIN(W9RegistrationVO vo){
		if(vo != null && vo.getEin() != null) {
			String originalEIn = vo.getEin();
			String d_ein = getDecryptedEIN(vo.getEin(),vo.getTaxPayerTypeId());
			//this is when we found unecrypted value found on vendor Header table.. which aftter the script run.. its a rare sceario
			String einToSet = null;
			if(d_ein == null) {
				if(vo.getEin().length() >= 9 && vo.getTaxPayerTypeId() == ProviderConstants.COMPANY_SSN_IND){
					einToSet =   ProviderConstants.COMPANY_SSN_ID_MASK+vo.getEin().substring(5) ;
				}
				else if (vo.getEin().length() >= 9 && vo.getTaxPayerTypeId() == ProviderConstants.COMPANY_EID_IND){
					einToSet =   ProviderConstants.COMPANY_EIN_ID_MASK+vo.getEin().substring(5) ;
				}
				else if (vo.getEin().length() >= 9 && vo.getTaxPayerTypeId() == 0){
					einToSet =   "XXXXX"+vo.getEin().substring(5) ;
				}
			}
			else {
				if(!d_ein.startsWith("XXX") && !d_ein.startsWith("XX-"))
				{
					if(vo.getTaxPayerTypeId() == ProviderConstants.COMPANY_SSN_IND)
					{
						einToSet =   ProviderConstants.COMPANY_SSN_ID_MASK+d_ein.substring(5);
					}
					else if(vo.getTaxPayerTypeId() == 0)
					{
						einToSet =   "XXXXX"+d_ein.substring(5);
					}
					else
					{
						einToSet =   ProviderConstants.COMPANY_EIN_ID_MASK+d_ein.substring(5);
					}
				}
				else{
					einToSet = d_ein;
				}
			}
			vo.setOriginalUnmaskedEin(getDecryptedUnMaskedValue(originalEIn));
			vo.setEin(einToSet);
			vo.setEin2(einToSet);
			vo.setOriginalEin(einToSet);
			
			
		}
	}
	
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.provider.IW9RegistrationBO#save(com.newco.marketplace.vo.provider.W9RegistrationVO)
	 */
	public W9RegistrationVO save(W9RegistrationVO input) throws Exception {
		boolean isInputExists = false;
		boolean needUpdates = false;
        if(input != null && input.getVendorId() != null) {
        	isInputExists  = isW9Exists(input.getVendorId());
        }

        if(isInputExists){
        	//do update business here
        	int versionNo = 0 ;
        	W9RegistrationVO temp = get(input.getVendorId());
        	
        	String ein1 = input.getEin();
        	ein1 = ein1.replace("-", "");
        	input.setEin(ein1);
        	
        	/*//In order to compare the EIN we need to decrypt it
        	temp.setEin(getDecryptedEIN(temp.getEin()));*/
        	//If there is changes other than Address bump up the version
        	if(temp.compareTo(input) != 0) {
        		if(temp.getEin() != null && temp.getEin().equals(input.getEin()) && temp.getTaxPayerTypeId()== input.getTaxPayerTypeId()){
        			//nothing has been changed on EIN so do not update EIN
        			input.setEin(null);
        		}
        		versionNo = temp.getVersion() + 1;
				input.setVersion(versionNo);
				needUpdates = true;
				if(input.getEin() != null && !input.getEin().startsWith("XXXX")){
					input.setEin(getEncryptedValue(input.getEin()));
				}


        	}
        	else {
        		if(temp.getAddress() != null  && input.getAddress() != null && (temp.getAddress().compareTo(input.getAddress())) != 0 ) {
        			//Update only address
        			input.setEin(null);
        			input.setLegalBusinessName(null);
        			input.setIsTaxExempt(null);
        			input.setIsPenaltyIndicatiorCertified(null);
        			input.setDoingBusinessAsName(null);
        			input.getTaxStatus().setId(null);
        			needUpdates = true;
        		}

        	}
        	
        	
        	if(needUpdates) 
        	{
        		if(input.getTaxPayerTypeId()==1)
        		{
        			input.setDateOfBirth(null);
        		}
        		w9RegistrationDao.update(input);
        	}
        }
        else {
        	//do insert business here
        	if(input == null || StringUtils.isBlank(input.getEin()) ) {
        		return null;
        	}
        	//do something for EIN
        	input.setEin(getEncryptedValue(input.getEin()));
        	input.setVersion(new Integer(1));
        	w9RegistrationDao.insert(input);
        }


		return get(input.getVendorId());
	}

	private String getDecryptedEIN(String einNo,int taxPayerIdType) {
		String resultEin = null;
		if (StringUtils.isNotBlank(einNo)){
			try {
				resultEin  = getDecryptedValue(einNo,taxPayerIdType);

			} catch(Exception e) {
				logger.warn("Error Encryption " ,  e);
				return einNo;
			}

		}
		return resultEin;
	}


	private String getDecryptedUnMaskedValue(String encryptedEin ){
		CryptographyVO cryptographyVO = new CryptographyVO();
		cryptographyVO.setInput(encryptedEin);
		cryptographyVO.setKAlias(MPConstants.ENCRYPTION_KEY);
		cryptographyVO = cryptography.decryptKey(cryptographyVO);
		String einNo = cryptographyVO.getResponse();
		if(einNo == null) return encryptedEin;
		return einNo;
	}

	private String getDecryptedValue(String encryptedEin,int taxPayerIdType) {
		CryptographyVO cryptographyVO = new CryptographyVO();
		cryptographyVO.setInput(encryptedEin);
		cryptographyVO.setKAlias(MPConstants.ENCRYPTION_KEY);
		cryptographyVO = cryptography.decryptKey(cryptographyVO);
		String einNo = cryptographyVO.getResponse();
		if(null == einNo || einNo.equalsIgnoreCase("")){
			einNo = "";
		}
		else if (!einNo.startsWith("XXX-XX-") && taxPayerIdType==ProviderConstants.COMPANY_SSN_IND)
		{
			einNo = ProviderConstants.COMPANY_SSN_ID_MASK+einNo.substring(5);
		}
		else if(taxPayerIdType==0)
		{
			einNo = "XXXXX"+einNo.substring(5);
		}
		else
		{
			einNo = ProviderConstants.COMPANY_EIN_ID_MASK+einNo.substring(5);
		}
		return einNo;

	}
	private String getEncryptedValue(String unencryptedEin){

		CryptographyVO cryptographyVO = new CryptographyVO();
		cryptographyVO.setInput(unencryptedEin);
		cryptographyVO.setKAlias(MPConstants.ENCRYPTION_KEY);
		cryptographyVO = cryptography.encryptKey(cryptographyVO);
		String einNo = cryptographyVO.getResponse();
		return einNo;
	}
	/**
	 * @return the cryptography
	 */
	public Cryptography getCryptography() {
		return cryptography;
	}
	/**
	 * @param cryptography the cryptography to set
	 */
	public void setCryptography(Cryptography cryptography) {
		this.cryptography = cryptography;
	}
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.provider.IW9RegistrationBO#getW9History(java.lang.Integer)
	 */
	public Map<Integer, List<W9RegistrationVO>> getW9History(Integer vendorId)
			throws Exception {

			List<W9RegistrationVO> voList = w9RegistrationDao.getW9History(vendorId);
			if(voList!=null && voList.size()>0)
			{
			int start=voList.size();
			int i=0;
			for(int count=0;count<voList.size();count++)
			{
				i=start-count+1;
				voList.get(count).setVersion(i);	
			}
			}
			return buildMap(voList);
	}

	private Map<Integer, List<W9RegistrationVO>> buildMap(List<W9RegistrationVO> voList) {
		Map<Integer, List<W9RegistrationVO>> map = new LinkedHashMap<Integer, List<W9RegistrationVO>>();
		if(voList == null ) return map;
		 for(W9RegistrationVO vo : voList) {
			 String ein = vo.getEin();
			 if(ein != null) {
				 vo.setEin(getDecryptedValue(ein,vo.getTaxPayerTypeId()));
			 }
			 if(!map.containsKey(vo.getVersion())) {
				 map.put(vo.getVersion(),new ArrayList<W9RegistrationVO>());
			 }
			 map.get(vo.getVersion()).add(vo);
		 }
		return map;
	}
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.provider.IW9RegistrationBO#getAll()
	 */
	public List<W9RegistrationVO> getAll() throws Exception {
		List<W9RegistrationVO> voList = w9RegistrationDao.getAll();
		return voList;
	}

	/* 
	 * This function is for checking the ein  number of the vendor is a valid
	 * value or not
	 * @param Integer
	 * @return boolean
	 */
	public boolean isW9ExistWithValidValue(Integer vendorId){
		boolean isValid = false;
		try {
			
				String maskedEinNo = w9RegistrationDao.getEinNumber(vendorId);
				String unmaskedEinNo = getDecryptedUnMaskedValue(maskedEinNo);

				// Check if the ein number is null or not
				if (unmaskedEinNo != null ) {
					
					// Check if the ein number is numeric and has 9 digits
					if ((unmaskedEinNo.length() == 9) && (StringUtils.isNumeric(unmaskedEinNo)))
					{
						 isValid = true;
					}
				}
		} catch (Exception e) {
			logger.error("Not able to get ein number information for w9. ", e);
		}
		return isValid;
	}

	public Double getCompleteSOAmount(Integer vendorId)
	{
		Double amount=null;
		Double completedamount=null;
		Double slaCreditAmt=null;
		try
		{
			amount = w9RegistrationDao.getCompleteSOAmount(vendorId);
			completedamount = wallet.getCompletedSOLedgerAmount(vendorId);
			
			if(completedamount!=null)
			{
			logger.info("completedledgerramount"+completedamount);
			}
			
		}
		catch(Exception exception)
		{
			logger.error(exception.getMessage());
		}
		return completedamount+amount;
	}
	
	public Double getProviderThreshold()
	{
		Double threshold=null;
		try
		{
			threshold = w9RegistrationDao.getProviderThreshold();
			return threshold;
		}
		catch(Exception exception)
		{
			logger.error(exception.getMessage());
		}
		return threshold;
	}

	public IWalletBO getWallet() {
		return wallet;
	}

	public void setWallet(IWalletBO wallet) {
		this.wallet = wallet;
	}
	
	
	
	
	
}