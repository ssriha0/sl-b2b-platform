/*
 **
 */
package com.newco.marketplace.business.businessImpl.provider;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.provider.ICompanyProfileBO;
import com.newco.marketplace.dto.vo.CryptographyVO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.interfaces.MPConstants;
import com.newco.marketplace.interfaces.ProviderConstants;
import com.newco.marketplace.interfaces.SPNConstants;
import com.newco.marketplace.persistence.iDao.provider.IContactDao;
import com.newco.marketplace.persistence.iDao.provider.ILicensesAndCertDao;
import com.newco.marketplace.persistence.iDao.provider.ILocationDao;
import com.newco.marketplace.persistence.iDao.provider.ILookupDAO;
import com.newco.marketplace.persistence.iDao.provider.IVendorHdrDao;
import com.newco.marketplace.persistence.iDao.provider.IVendorResourceDao;
import com.newco.marketplace.persistence.iDao.provider.IWarrantyDao;
import com.newco.marketplace.util.Cryptography;
import com.newco.marketplace.vo.provider.CompanyProfileVO;
import com.newco.marketplace.vo.provider.Contact;
import com.newco.marketplace.vo.provider.InsurancePolicyVO;
import com.newco.marketplace.vo.provider.LicensesAndCertVO;
import com.newco.marketplace.vo.provider.Location;
import com.newco.marketplace.vo.provider.VendorHdr;
import com.newco.marketplace.vo.provider.VendorResource;
import com.newco.marketplace.vo.provider.WarrantyVO;
import com.sears.os.business.ABaseBO;

/**
 * Business Bean for Team Profile service request
 *
 * @version
 * @author blars04
 *
 */
public class CompanyProfileBOImpl extends ABaseBO implements ICompanyProfileBO {

	private IVendorHdrDao vendorHdrDao;
	private ILocationDao locationDao;
	private IContactDao iContactDao;
	private ILookupDAO lookupDAO;
	private IVendorResourceDao vendorResourceDao;
	private ILicensesAndCertDao licensesAndCertDao;
	private IWarrantyDao warrantyDao;
	private Cryptography cryptography;

	private static final Logger logger = Logger
	.getLogger(CompanyProfileBOImpl.class.getName());



	public CompanyProfileBOImpl(IVendorHdrDao vendorHdrDao,ILocationDao locationDao,IContactDao iContactDao,ILookupDAO lookupDAO,IVendorResourceDao vendorResourceDao,ILicensesAndCertDao licensesAndCertDao,IWarrantyDao warrantyDao,Cryptography cryptography) {

		this.vendorHdrDao = vendorHdrDao;
		this.locationDao = locationDao;
		this.iContactDao = iContactDao;
		this.lookupDAO = lookupDAO;
		this.vendorResourceDao = vendorResourceDao;
		this.licensesAndCertDao = licensesAndCertDao;
		this.warrantyDao = warrantyDao;
		this.cryptography=cryptography;

	}

	public String getdecryptedEIN(String encryptedEin,Integer taxPayerIdType) {
		CryptographyVO cryptographyVO = new CryptographyVO();
		cryptographyVO.setInput(encryptedEin);
		cryptographyVO.setKAlias(MPConstants.ENCRYPTION_KEY);
		cryptographyVO = cryptography.decryptKey(cryptographyVO);
		String einNo = cryptographyVO.getResponse();
		if(taxPayerIdType == null || taxPayerIdType == ProviderConstants.COMPANY_SSN_IND)
			einNo = ProviderConstants.COMPANY_SSN_ID_MASK+einNo.substring(5);
		else 
			einNo = ProviderConstants.COMPANY_EIN_ID_MASK+einNo.substring(5);
		return einNo;

	}
	public CompanyProfileVO getCompleteProfile(int vendorId) throws BusinessServiceException
	{
		CompanyProfileVO  companyProfileVO  = new CompanyProfileVO();
		VendorHdr vendorHdr = new VendorHdr();
		Location locationVO = new Location();
		Contact  contact = new Contact();
		Contact tmpContact = null;
		VendorResource vendorResource = new VendorResource();
		vendorHdr.setVendorId(vendorId);
		String annualSales = null;
		List licensesList = null;
		List insuranceList = null;
		WarrantyVO warrantyVO = new WarrantyVO();
		WarrantyVO tmpWarrantyVO = null;
		List<String> errorList = new ArrayList <String>();
		

		try
		{
			vendorHdr = vendorHdrDao.query(vendorHdr);

		if(vendorHdr != null)
		{
			companyProfileVO.setServiceLiveStatus(vendorHdr.getServiceliveStatus());
			companyProfileVO.setBusinessFax(vendorHdr.getBusinessFax());
			companyProfileVO.setBusinessName(vendorHdr.getBusinessName());
			companyProfileVO.setBusinessStartDate(vendorHdr.getBusinessStartDate());
			companyProfileVO.setVendorId(vendorHdr.getVendorId());
			companyProfileVO.setBusinessPhone(vendorHdr.getBusinessPhone());
			companyProfileVO.setDbaName(vendorHdr.getDbaName());
			companyProfileVO.setDunsNo(vendorHdr.getDunsNo());
			if (StringUtils.isNotBlank(vendorHdr.getEinNo())) {
				try {
					String einNo  = getdecryptedEIN(vendorHdr.getEinNo(),vendorHdr.getTaxPayerIdType());
					companyProfileVO.setEinNo(einNo);
				} catch(Exception e) {
					logger.warn("Error Encryption " ,  e);
				}

			}
			companyProfileVO.setForeignOwnedInd(vendorHdr.getForeignOwnedInd());
			companyProfileVO.setInsurancePolicies(getVendorPolicies(vendorHdr));
			logger.info("fop----------------------------------"+vendorHdr.getForeignOwnedPct());
			if(vendorHdr.getForeignOwnedPct()>0)
			{
			companyProfileVO.setForeignOwnedPct(lookupDAO.getForeignOwnPct(vendorHdr.getForeignOwnedPct()));
			}
			else
			{
				companyProfileVO.setForeignOwnedPct("-");
			}
			companyProfileVO.setBusinessDesc(vendorHdr.getBusinessDesc());			
			companyProfileVO.setBusinessType(lookupDAO.getBusinessType(vendorHdr.getBusinessTypeId()));
			
			if(vendorHdr.getPrimaryIndustryId()>0)
			{
				companyProfileVO.setPrimaryIndustryId(vendorHdr.getPrimaryIndustryId());
				companyProfileVO.setPrimaryIndustry(lookupDAO.getPrimaryIndustry(vendorHdr.getPrimaryIndustryId()));
			}
			//SL-20917: Fetching no of approved/market ready employees under a firm-- Start
			Integer noOfEmp = 0;
			try{
				noOfEmp = vendorHdrDao.getApprovedmembers(vendorHdr.getVendorId());
			}catch (Exception e) {
				logger.error("Exception in fetching noOf employees under a firm"+ e.getMessage());
				throw new BusinessServiceException(e);
			}
			companyProfileVO.setNoOfEmployees(noOfEmp);
			//SL-20917: Fetching no of approved/market ready employees under a firm-- End
			//companyProfileVO.setCompanySize(lookupDAO.getCompanySize(vendorHdr.getCompanySizeId()));
			companyProfileVO.setCompanySize(lookupDAO.getCompanySize(vendorHdr.getCompanySizeId()));
			companyProfileVO.setWebAddress(vendorHdr.getWebAddress());
			companyProfileVO.setMemberSince(vendorHdr.getCreatedDate());
			annualSales = vendorHdrDao.getAnnualSalesVolume(vendorHdr.getVendorId());
			if(annualSales!=null)
			{
				companyProfileVO.setAnnualSalesVolume(annualSales);
			}

			locationVO = locationDao.queryVendorLocation(vendorHdr.getVendorId());
			if(locationVO!=null)
			{
				companyProfileVO.setBusCity(locationVO.getCity());
				companyProfileVO.setBusStateCd(locationVO.getStateCd());
				companyProfileVO.setBusStreet1(locationVO.getStreet1());
				companyProfileVO.setBusStreet2(locationVO.getStreet2());
				companyProfileVO.setBusZip(locationVO.getZip());
			}
			vendorResource = vendorResourceDao.getQueryByVendorId(vendorHdr.getVendorId());

			if(vendorResource!=null)
			{
				//get the location details


				//get the contact details
				contact.setContactId(vendorResource.getContactId());
				tmpContact = iContactDao.query(contact);
				if(tmpContact!=null)
				{
					companyProfileVO.setAltEmail(tmpContact.getAltEmail());
					companyProfileVO.setEmail(tmpContact.getEmail());
					companyProfileVO.setFirstName(tmpContact.getFirstName());
					companyProfileVO.setLastName(tmpContact.getLastName());
					companyProfileVO.setMi(tmpContact.getMi());
					if(tmpContact.getRole()!=null && tmpContact.getRole()!= "0")
					{
						companyProfileVO.setRole(lookupDAO.getCompanyRole(Integer.parseInt(tmpContact.getRole())));
					}
					companyProfileVO.setSuffix(tmpContact.getSuffix());
					companyProfileVO.setTitle(tmpContact.getTitle());

				}

			}
			// get the warranty info
			warrantyVO.setVendorID(vendorHdr.getVendorId().toString());
			tmpWarrantyVO = warrantyDao.query(warrantyVO);
			if(tmpWarrantyVO != null)
			{
				if(null!=tmpWarrantyVO.getWarrOfferedLabor()){
					companyProfileVO.setWarrOfferedLabor(tmpWarrantyVO.getWarrOfferedLabor().trim());
					if(companyProfileVO.getWarrOfferedLabor().equals("1"))
					{
						companyProfileVO.setWarrPeriodLabor(lookupDAO.getWarrantyPeriod(Integer.parseInt(tmpWarrantyVO.getWarrPeriodLabor())));
						logger.info("laborperiod-----------------------"+companyProfileVO.getWarrPeriodLabor()+"tmp---"+tmpWarrantyVO.getWarrPeriodLabor());
					}
				}
				
				if(null!= tmpWarrantyVO.getWarrOfferedParts()){		
					companyProfileVO.setWarrOfferedParts(tmpWarrantyVO.getWarrOfferedParts().trim());
					if(companyProfileVO.getWarrOfferedParts().equals("1"))
					{
						companyProfileVO.setWarrPeriodParts(lookupDAO.getWarrantyPeriod(Integer.parseInt(tmpWarrantyVO.getWarrPeriodParts())));
					}
				}
				
				companyProfileVO.setConductDrugTest(tmpWarrantyVO.getConductDrugTest());
				companyProfileVO.setConsiderBadge(tmpWarrantyVO.getConsiderBadge());
				companyProfileVO.setConsiderDrugTest(tmpWarrantyVO.getConsiderDrugTest());
				companyProfileVO.setConsiderEthicPolicy(tmpWarrantyVO.getConsiderEthicPolicy());
				companyProfileVO.setConsiderImplPolicy(tmpWarrantyVO.getConsiderImplPolicy());
				companyProfileVO.setFreeEstimate(tmpWarrantyVO.getFreeEstimate());							
				companyProfileVO.setHasEthicsPolicy(tmpWarrantyVO.getHasEthicsPolicy());
				companyProfileVO.setRequireBadge(tmpWarrantyVO.getRequireBadge());
				companyProfileVO.setRequireUsDoc(tmpWarrantyVO.getRequireUsDoc());
				companyProfileVO.setWarrantyMeasure(tmpWarrantyVO.getWarrantyMeasure());

			}

			licensesList = licensesAndCertDao.getVendorCredentialsList(vendorHdr.getVendorId());
			if(licensesList!=null)
			{
				companyProfileVO.setLicensesList(licensesList);
			}
			insuranceList = licensesAndCertDao.getVendorCredentialsInsurance(vendorHdr.getVendorId());
			if(insuranceList!=null)
			{
				//SL20014-to get latest documentId for a particular insurance
				insuranceList = updateInsuranceList(insuranceList);
				//SL20014-end
				companyProfileVO.setInsuranceList(insuranceList);
			}
			
			updateVendorPolicyInfo(companyProfileVO.getInsurancePolicies(),companyProfileVO.getInsuranceList());

			Date businessStartDate = companyProfileVO.getBusinessStartDate();
			if (businessStartDate != null) {
				companyProfileVO.setYearsInBusiness(getYearsInBusiness(businessStartDate));
			}else{
				errorList.add(ProviderConstants.BUSINESS_START_DATE);
			}
			companyProfileVO.setErrorList(errorList);
		}
	}
		catch (Exception ex) {
			ex.printStackTrace();
			logger
					.info("General Exception @CompanyProfileBOImpl.getCompleteProfile() due to"
							+ ex.getMessage());
			throw new BusinessServiceException(
					"General Exception @CompanyProfileBOImpl.getCompleteProfile() due to "
							+ ex.getMessage());
		}

		return companyProfileVO;
	}
	
	/**SL20014-to get latest documentId for a particular insurance
	 * @param insuranceList
	 * @return insuranceList
	 */
	private List updateInsuranceList(List insuranceList){
		List<String> alreadyAddedCredType = new ArrayList<String>();
		Map<String,List<Integer>> docListMap = new HashMap<String, List<Integer>>();
		List<Integer> docIdList = null;
		List updatedInsuranceList = new ArrayList(insuranceList);
		for (Object insurance : insuranceList) {
			LicensesAndCertVO license = (LicensesAndCertVO) insurance;
			alreadyAddedCredType.add(license.getCredTypeDesc());
			if(license.getCurrentDocumentID()!=0){
				if(docListMap.containsKey(license.getCredTypeDesc())){
					docListMap.get(license.getCredTypeDesc()).add(license.getCurrentDocumentID());
					updatedInsuranceList.remove(license);
				}
				else{
					docIdList = new ArrayList<Integer>();
					docIdList.add(license.getCurrentDocumentID());
					docListMap.put(license.getCredTypeDesc(),docIdList);
				}
			}
		}
		for(Object insurance : updatedInsuranceList){
			LicensesAndCertVO license = (LicensesAndCertVO) insurance;
			if(docListMap.containsKey(license.getCredTypeDesc())){
				docIdList = docListMap.get(license.getCredTypeDesc());
				Integer docId = Collections.max(docIdList);
				license.setCurrentDocumentID(docId);
			}
		}
		return updatedInsuranceList;
	}

	private void updateVendorPolicyInfo(List<InsurancePolicyVO> policies,List <LicensesAndCertVO> insuranceList) {
		try {
		if(insuranceList != null) {
			boolean verifiedAL = false;
			boolean verifiedWC = false;
			boolean verifiedGL = false;
			Date glVerifiedDate = null;
			Date vlVerifiedDate = null;
			Date wcVerifiedDate = null;

			for(LicensesAndCertVO vo : insuranceList ){

				if(ProviderConstants.COMPANY_CREDENTIAL_APPROVAL.intValue() == vo.getWfStateId()) {
					if(ProviderConstants.AUTO_LIABILITY.equals(vo.getCredTypeDesc())){
						verifiedAL = true;
						glVerifiedDate = vo.getInsModifiedDate();
					}
					else if (ProviderConstants.WORKER_COMPENSATION.equals(vo.getCredTypeDesc())){
						verifiedWC = true;
						vlVerifiedDate = vo.getInsModifiedDate();
					}
					else if (ProviderConstants.GENERAL_LIABILITY.equals(vo.getCredTypeDesc()) ) {
						verifiedGL = true;
						wcVerifiedDate = vo.getInsModifiedDate();
					}
				}

			}
			for(InsurancePolicyVO vo1 :policies){
				if(verifiedAL)
					if("Vehicle Liability Insurance".equals(vo1.getName())) {
						vo1.setIsVerified(verifiedAL);
						vo1.setInsVerifiedDate(glVerifiedDate);
					}
				if(verifiedWC)
					if("Workers Compensation Insurance".equals(vo1.getName())) {
						vo1.setIsVerified(verifiedWC);
						vo1.setInsVerifiedDate(vlVerifiedDate);
					}
				if(verifiedGL)
					if("Commercial General Liability Insurance".equals(vo1.getName())) {
						vo1.setIsVerified(verifiedGL);
						vo1.setInsVerifiedDate(wcVerifiedDate);
					}
			}


		}
		}catch (Exception e) {
			//SWallo this with logger...
			logger.error(" Exception in updating insurance policie details from Database",e);
		}
	}

	private List<InsurancePolicyVO> getVendorPolicies(VendorHdr vendorHdr ){
		List<InsurancePolicyVO> list  = new ArrayList();
		InsurancePolicyVO vo = new InsurancePolicyVO();
		if(vendorHdr.getVLI() != null && vendorHdr.getVLI().intValue() == 1){
			vo = new InsurancePolicyVO();
			vo.setName("Vehicle Liability Insurance");
			vo.setPolicyIndicator(true);
			vo.setPolicyamount(new BigDecimal(vendorHdr.getVLIAmount()));
			//SL20014-setting credentialType to compare in jsp to get documentID
			vo.setCredTypeDesc("Auto Liability");
			//SL20014-end
			list.add(vo);
		}
		if(vendorHdr.getWCI() != null && vendorHdr.getWCI().intValue() == 1){
			vo = new InsurancePolicyVO();
			vo.setName("Workers Compensation Insurance");
			vo.setPolicyIndicator(true);
			vo.setPolicyamount(new BigDecimal(vendorHdr.getWCIAmount()));
			//SL20014-setting credentialType to compare in jsp to get documentID
			vo.setCredTypeDesc("Workman's Compensation");
			//SL20014-end
			list.add(vo);
		}
		if(vendorHdr.getCBGLI() != null && vendorHdr.getCBGLI().intValue() == 1){
			vo = new InsurancePolicyVO();
			vo.setName("Commercial General Liability Insurance");
			vo.setPolicyIndicator(true);
			vo.setPolicyamount(new BigDecimal(vendorHdr.getCBGLIAmount()));
			//SL20014-setting credentialType to compare in jsp to get documentID
			vo.setCredTypeDesc("General Liability");
			//SL20014-end
			list.add(vo);
		}
		return list;
	}


	private String getYearsInBusiness(Date busStartDate)
	{
		long numMilBusStart;
		long numMilToday;
		long dateDiff = 0;
		float numYears;
		String numYearsStr;
		Date todayDate = new Date();

		numMilBusStart = busStartDate.getTime();
		numMilToday = todayDate.getTime();

		dateDiff = numMilToday - numMilBusStart;

		numYears = (float) dateDiff / 1000 / 60 / 60 / 24 / 365;
		numYearsStr = String.valueOf(numYears);

		return numYearsStr;
	}
}// end class
