package com.newco.marketplace.api.mobile.services;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.newco.marketplace.api.mobile.beans.companyProfile.Credential;
import com.newco.marketplace.api.mobile.beans.companyProfile.ViewCompanyProfileResponse;
import com.newco.marketplace.api.mobile.utils.mappers.v3_1.NewMobileGenericMapper;
import com.newco.marketplace.business.businessImpl.mobile.NewMobileGenericBOImpl;
import com.newco.marketplace.vo.provider.CompanyProfileVO;

public class ViewCompanyProfileTest {

	private NewMobileGenericMapper newMobileMapper;
	private NewMobileGenericBOImpl newMobileGenericBO;
	private CompanyProfileVO companyProfileVO;
	private String firmId;
	private Credential credential;
	@Before
	public void setUp() {
		newMobileMapper=new NewMobileGenericMapper();
		newMobileGenericBO = new NewMobileGenericBOImpl();
		companyProfileVO=new CompanyProfileVO();
		credential=new Credential();
		companyProfileVO.setBusinessName("Home Sweet Home Theatres");
		companyProfileVO.setDbaName("dbaName");
		companyProfileVO.setBusinessPhone("8473380469");
		companyProfileVO.setBusinessFax("56897");
		companyProfileVO.setEinNo("XX-XXX5689");
		companyProfileVO.setDunsNo("9999");
		companyProfileVO.setBusinessType("Sole Proprietor");
		companyProfileVO.setPrimaryIndustry("Home Electronics");
		companyProfileVO.setWebAddress("abc@abc.in");
		companyProfileVO.setCompanySize("Sole Proprietor or Individual");
		companyProfileVO.setAnnualSalesVolume("59k - 79k");
		companyProfileVO.setForeignOwnedInd(1);
		companyProfileVO.setForeignOwnedPct("10");
		//
		companyProfileVO.setBusinessDesc("This is dummy description");
		//
		companyProfileVO.setBusCity("Grayslake");
		companyProfileVO.setBusStreet1("PO Ser2viceLive Ave.");
		companyProfileVO.setBusStreet2("Suite 123");
		companyProfileVO.setBusZip("60030");
		companyProfileVO.setBusStateCd("IL");
		//
		companyProfileVO.setRole("C-Level (CEO, COO, CFO, CIO, CTO, CMO)");
		companyProfileVO.setTitle("jobTitle");
		companyProfileVO.setFirstName("Brian TESTUSERNAME ");
		companyProfileVO.setMi("WRAP");
		companyProfileVO.setLastName("ScBrian TESTUSER NAME WRAP");
		companyProfileVO.setEmail("nmanoh1@searshc.com");
		companyProfileVO.setAltEmail("rjose14@searshc.com");
		companyProfileVO.setSuffix("Jr.");
		//
		companyProfileVO.setWarrOfferedLabor("1");
		companyProfileVO.setWarrOfferedParts("1");
		companyProfileVO.setWarrPeriodLabor("90 days");
		companyProfileVO.setWarrPeriodParts("90 days");
		//
		companyProfileVO.setRequireBadge("1");
		companyProfileVO.setConductDrugTest("0");
		//
		
		credential.setCredentialType("Contractor");
		companyProfileVO.setVendorId(10202);
		firmId="10202";
		/*try {
			when(newMobileGenericBO.getCompleteProfile(firmId)).thenReturn(companyProfileVO);
			
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
	
	@Test
	public void mapCompleteProfileBusinessInfoToResponse(){
		try{
			ViewCompanyProfileResponse viewCompanyProfileResponse =new ViewCompanyProfileResponse();
			viewCompanyProfileResponse=newMobileMapper.mapCompleteProfileVOToResponse(companyProfileVO,viewCompanyProfileResponse);
			Assert.assertEquals(viewCompanyProfileResponse.getCompleteProfile().getBusinessInformation().getLegalBusinessName(),"Home Sweet Home Theatres");
			Assert.assertEquals(viewCompanyProfileResponse.getCompleteProfile().getBusinessInformation().getDoingBusinessAs(), "dbaName");
			Assert.assertEquals(viewCompanyProfileResponse.getCompleteProfile().getBusinessInformation().getBusinessPhone(),"8473380469");
			Assert.assertEquals(viewCompanyProfileResponse.getCompleteProfile().getBusinessInformation().getBusinessFax(),"56897");
			Assert.assertEquals(viewCompanyProfileResponse.getCompleteProfile().getBusinessInformation().getTaxPayerId(),"XX-XXX5689");
			Assert.assertEquals(viewCompanyProfileResponse.getCompleteProfile().getBusinessInformation().getDunsNo(),"9999");
			Assert.assertEquals(viewCompanyProfileResponse.getCompleteProfile().getBusinessInformation().getBusinessStructure(),"Sole Proprietor");
			Assert.assertEquals(viewCompanyProfileResponse.getCompleteProfile().getBusinessInformation().getPrimaryIndustry(),"Home Electronics");
			Assert.assertEquals(viewCompanyProfileResponse.getCompleteProfile().getBusinessInformation().getWebsiteAddress(),"abc@abc.in");
			Assert.assertEquals(viewCompanyProfileResponse.getCompleteProfile().getBusinessInformation().getCompanySize(),"Sole Proprietor or Individual");
			Assert.assertEquals(viewCompanyProfileResponse.getCompleteProfile().getBusinessInformation().getAnnualSalesRevenue(),"59k - 79k");
			Assert.assertEquals(viewCompanyProfileResponse.getCompleteProfile().getBusinessInformation().getForeignOwnedInd(),true);
			Assert.assertEquals(viewCompanyProfileResponse.getCompleteProfile().getBusinessInformation().getForeignOwnedPercent(),"10");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	@Test
	public void mapCompleteProfileCompanyDescription(){
		try{
			ViewCompanyProfileResponse viewCompanyProfileResponse =new ViewCompanyProfileResponse();
			viewCompanyProfileResponse=newMobileMapper.mapCompleteProfileVOToResponse(companyProfileVO,viewCompanyProfileResponse);
			Assert.assertEquals(viewCompanyProfileResponse.getCompleteProfile().getCompanyOverview().getCompanyDescription(),"This is dummy description");
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void mapCompleteProfileBusinessAddress(){
		try{
			ViewCompanyProfileResponse viewCompanyProfileResponse =new ViewCompanyProfileResponse();
			viewCompanyProfileResponse=newMobileMapper.mapCompleteProfileVOToResponse(companyProfileVO,viewCompanyProfileResponse);
			Assert.assertEquals(viewCompanyProfileResponse.getCompleteProfile().getBusinessAddress().getBusinessCity(),"Grayslake");
			Assert.assertEquals(viewCompanyProfileResponse.getCompleteProfile().getBusinessAddress().getBusinessStreet1(),"PO Ser2viceLive Ave.");
			Assert.assertEquals(viewCompanyProfileResponse.getCompleteProfile().getBusinessAddress().getBusinessStreet2(),"Suite 123");
			Assert.assertEquals(viewCompanyProfileResponse.getCompleteProfile().getBusinessAddress().getBusinessState(),"IL");
			Assert.assertEquals(viewCompanyProfileResponse.getCompleteProfile().getBusinessAddress().getBusinessZip(),"60030");
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void mapCompleteProfilePrimaryContactInformation(){
		try{
			ViewCompanyProfileResponse viewCompanyProfileResponse =new ViewCompanyProfileResponse();
			viewCompanyProfileResponse=newMobileMapper.mapCompleteProfileVOToResponse(companyProfileVO,viewCompanyProfileResponse);
			Assert.assertEquals(viewCompanyProfileResponse.getCompleteProfile().getPrimaryContactInformation().getRoleWithinCompany(),"C-Level (CEO, COO, CFO, CIO, CTO, CMO)");
			Assert.assertEquals(viewCompanyProfileResponse.getCompleteProfile().getPrimaryContactInformation().getJobTitle(),"jobTitle");
			Assert.assertEquals(viewCompanyProfileResponse.getCompleteProfile().getPrimaryContactInformation().getSuffix(),"Jr.");
			Assert.assertEquals(viewCompanyProfileResponse.getCompleteProfile().getPrimaryContactInformation().getFirstName(),"Brian TESTUSERNAME ");
			Assert.assertEquals(viewCompanyProfileResponse.getCompleteProfile().getPrimaryContactInformation().getMiddleName(),"WRAP");
			Assert.assertEquals(viewCompanyProfileResponse.getCompleteProfile().getPrimaryContactInformation().getLastName(),"ScBrian TESTUSER NAME WRAP");
			Assert.assertEquals(viewCompanyProfileResponse.getCompleteProfile().getPrimaryContactInformation().getEmail(),"nmanoh1@searshc.com");
			Assert.assertEquals(viewCompanyProfileResponse.getCompleteProfile().getPrimaryContactInformation().getAlternateEmail(),"rjose14@searshc.com");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void mapCompleteProfileWarrantyInformation(){
		try{
			ViewCompanyProfileResponse viewCompanyProfileResponse =new ViewCompanyProfileResponse();
			viewCompanyProfileResponse=newMobileMapper.mapCompleteProfileVOToResponse(companyProfileVO,viewCompanyProfileResponse);
			Assert.assertEquals(viewCompanyProfileResponse.getCompleteProfile().getWarrantyInformation().getWarrantyOnLabor(),"90 days");
			Assert.assertEquals(viewCompanyProfileResponse.getCompleteProfile().getWarrantyInformation().getWarrantyOnParts(),"90 days");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void mapCompleteProfileWorkPolicyInformation(){
		try{
			ViewCompanyProfileResponse viewCompanyProfileResponse =new ViewCompanyProfileResponse();
			viewCompanyProfileResponse=newMobileMapper.mapCompleteProfileVOToResponse(companyProfileVO,viewCompanyProfileResponse);
			Assert.assertEquals(viewCompanyProfileResponse.getCompleteProfile().getWorkPolicyInformation().getRequireBadgeInd(),true);
			Assert.assertEquals(viewCompanyProfileResponse.getCompleteProfile().getWorkPolicyInformation().getDrugTestingPolicyInd(),false);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	@Test
	public void mapPublicProfileBusinessInfoToResponse(){
		try{
			ViewCompanyProfileResponse viewCompanyProfileResponse =new ViewCompanyProfileResponse();
			viewCompanyProfileResponse=newMobileMapper.mapPublicProfileVOToResponse(companyProfileVO,viewCompanyProfileResponse);
			Assert.assertEquals(viewCompanyProfileResponse.getPublicProfile().getBusinessInformation().getCompanyId(),"10202");
			Assert.assertEquals(viewCompanyProfileResponse.getPublicProfile().getBusinessInformation().getBusinessStructure(),"Sole Proprietor");
			Assert.assertEquals(viewCompanyProfileResponse.getPublicProfile().getBusinessInformation().getPrimaryIndustry(),"Home Electronics");
			Assert.assertEquals(viewCompanyProfileResponse.getPublicProfile().getBusinessInformation().getCompanySize(),"Sole Proprietor or Individual");
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	@Test
	public void mapPublicProfileCompanyDescription(){
		try{
			ViewCompanyProfileResponse viewCompanyProfileResponse =new ViewCompanyProfileResponse();
			viewCompanyProfileResponse=newMobileMapper.mapPublicProfileVOToResponse(companyProfileVO,viewCompanyProfileResponse);
			Assert.assertEquals(viewCompanyProfileResponse.getPublicProfile().getCompanyOverview().getCompanyDescription(),"This is dummy description");
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void mapPublicProfileBusinessAddress(){
		try{
			ViewCompanyProfileResponse viewCompanyProfileResponse =new ViewCompanyProfileResponse();
			viewCompanyProfileResponse=newMobileMapper.mapPublicProfileVOToResponse(companyProfileVO,viewCompanyProfileResponse);
			Assert.assertEquals(viewCompanyProfileResponse.getPublicProfile().getBusinessAddress().getBusinessCity(),"Grayslake");
			Assert.assertEquals(viewCompanyProfileResponse.getPublicProfile().getBusinessAddress().getBusinessState(),"IL");
			Assert.assertEquals(viewCompanyProfileResponse.getPublicProfile().getBusinessAddress().getBusinessZip(),"60030");
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void mapPublicProfileWarrantyInformation(){
		try{
			ViewCompanyProfileResponse viewCompanyProfileResponse =new ViewCompanyProfileResponse();
			viewCompanyProfileResponse=newMobileMapper.mapPublicProfileVOToResponse(companyProfileVO,viewCompanyProfileResponse);
			Assert.assertEquals(viewCompanyProfileResponse.getPublicProfile().getWarrantyInformation().getWarrantyOnLabor(),"90 days");
			Assert.assertEquals(viewCompanyProfileResponse.getPublicProfile().getWarrantyInformation().getWarrantyOnParts(),"90 days");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
