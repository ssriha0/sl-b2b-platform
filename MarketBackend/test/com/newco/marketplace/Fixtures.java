package com.newco.marketplace;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.businessImpl.providerSearch.rating.ZipParameterBean;
import com.newco.marketplace.business.iBusiness.orderGroup.IOrderGroupBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.vo.LocationVO;
import com.newco.marketplace.dto.vo.buyer.BuyerReferenceVO;
import com.newco.marketplace.dto.vo.ordergroup.ChildServiceOrderVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderSearchCriteriaVO;
import com.newco.marketplace.dto.vo.providerSearch.RatingParameterBean;
import com.newco.marketplace.dto.vo.serviceorder.Buyer;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.dto.vo.serviceorder.PhoneVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderCustomRefVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSearchResultsVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderTask;
import com.newco.marketplace.dto.vo.serviceorder.SoLocation;
import com.newco.marketplace.dto.vo.spn.SPNCampaignVO;
import com.newco.marketplace.dto.vo.spn.SPNCriteriaVO;
import com.newco.marketplace.dto.vo.spn.SPNHeaderVO;
import com.newco.marketplace.dto.vo.spn.SPNNetworkResourceVO;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.util.so.ServiceOrderUtil;
import com.newco.marketplace.vo.login.LoginCredentialVO;
import com.newco.marketplace.vo.provider.ChangePasswordVO;
import com.newco.marketplace.vo.provider.CredentialProfile;
import com.newco.marketplace.vo.provider.LicensesAndCertVO;
import com.newco.marketplace.vo.provider.TeamCredentialsVO;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.servicelive.routing.tiered.vo.SPNTierEventInfoVO;


/**
 * @author Mahmud Khair
 *
 */
public class Fixtures { 
	public static ChangePasswordVO getChangePasswordVO() {
		ChangePasswordVO changePasswordVO = new ChangePasswordVO();
		changePasswordVO.setUserName("Winstonchurchill");
		changePasswordVO.setSecretAnswer("Bluu");
		changePasswordVO.setSecretQuestion("10");
		return changePasswordVO;
	}
	
	
	public static SPNCampaignVO getSPNNewCampaignVO() {
		SPNCampaignVO newCampaign = new SPNCampaignVO();	
		newCampaign.setSpnId(111111);
		newCampaign.setCampaignId(1111111111);
		newCampaign.setAllMarkets(1);
		newCampaign.setCampaignStateDate(Calendar.getInstance().getTime());
		newCampaign.setCampaignEndDate(Calendar.getInstance().getTime());
		newCampaign.setMarketId(1);
		//newCampaign.set
		return newCampaign;
	}
	
	public static SPNNetworkResourceVO getSPNNewNetworkResourceVO() {
		SPNNetworkResourceVO newResourceVO= new SPNNetworkResourceVO();	
		newResourceVO.setSpnId(111111);
		newResourceVO.setResourceId(393);
		newResourceVO.setSpnStatusId(20);
		newResourceVO.setApplicationDate(new Date());

		return newResourceVO;
	}
	
	public static SPNHeaderVO getSPNHeaderVO() {
		SPNHeaderVO headerVO = new SPNHeaderVO();
		
		headerVO.setSpnName("SPN Header Test");
		headerVO.setName("SPN Header Name");
		headerVO.setBuyerId(new Integer(3));
		headerVO.setContactName("SPN Contact Name");
		headerVO.setContactEmail("email@email.com");
		headerVO.setDescription("SPN Header Test Description");
		headerVO.setInstruction("SPN Header Test Instructions");
		headerVO.setDocRequired(false);
		headerVO.setSpnLocked(false);
		
		SPNCriteriaVO spnCriteriaVO = new SPNCriteriaVO();
		spnCriteriaVO.setMinSOClosed(new Integer(0));
		headerVO.setSpnCriteriaVO(spnCriteriaVO);
		return headerVO;
	}
	/**
	 * to get SPN header with differant values
	 * @param spnName
	 * @param contactName
	 * @param contactEmail
	 * @param buyerId
	 * @return
	 */
	public static SPNHeaderVO getSPNHeaderVO(String spnName, String contactName, String contactEmail,
			int buyerId) {
		SPNHeaderVO headerVO = getSPNHeaderVO();
		
		headerVO.setSpnName(spnName);
		headerVO.setName(spnName);
		headerVO.setBuyerId(new Integer(buyerId));
		headerVO.setContactName(contactName);
		headerVO.setContactEmail(contactEmail);

		return headerVO;
	}
	
	public static ProviderSearchCriteriaVO getProviderSearchCriteriaVO() {
		ProviderSearchCriteriaVO newProviderSearchCriteriaVO = new ProviderSearchCriteriaVO();	
		List<RatingParameterBean> ratingParamBeans = new ArrayList<RatingParameterBean>();
		//TODO  fix all of these
		newProviderSearchCriteriaVO.setBuyerID(1);
		newProviderSearchCriteriaVO.setBuyerZipCode(60010);
		//newProviderSearchCriteriaVO.setServiceOrderID("194-2847-5852-14");
		newProviderSearchCriteriaVO.setServiceOrderID("182-1345-3968-18");
		
		ZipParameterBean zipBean = new ZipParameterBean();
		//TODO distance move to application properties table
		zipBean.setRadius(OrderConstants.SO_ROUTE_CRITERIA_DIST);
		ratingParamBeans.add(zipBean);
		
		//182-1345-3968-18
		
		LocationVO alocation = new LocationVO();
		alocation.setZip("60193");
		ArrayList<Integer>	skillNodeIds =  new ArrayList<Integer>();
		List<Integer>	skillServiceTypeId =  new ArrayList<Integer>();
		
		skillNodeIds.add(1201); // GeneralRoofing
		skillNodeIds.add(1204); // TV Wall Mount Std Surface
		skillServiceTypeId.add(7);// delivery
		skillServiceTypeId.add(20);// installation
		
		
		newProviderSearchCriteriaVO.setSkillNodeIds(skillNodeIds);
		newProviderSearchCriteriaVO.setSkillServiceTypeId(skillServiceTypeId);
		
		newProviderSearchCriteriaVO.setServiceLocation(alocation);
		return newProviderSearchCriteriaVO;
	}
	
	public static ProviderSearchCriteriaVO getProviderSearchCriteriaVOForSpnTier() {
		ProviderSearchCriteriaVO newProviderSearchCriteriaVO = new ProviderSearchCriteriaVO();	
		List<RatingParameterBean> ratingParamBeans = new ArrayList<RatingParameterBean>();

		
		ZipParameterBean zipBean = new ZipParameterBean();
		//TODO distance move to application properties table
		zipBean.setRadius(OrderConstants.SO_ROUTE_CRITERIA_DIST);
		ratingParamBeans.add(zipBean);
		
		
		LocationVO alocation = new LocationVO();
		alocation.setZip("11951");
		ArrayList<Integer>	skillNodeIds =  new ArrayList<Integer>();
		List<Integer>	skillServiceTypeId =  new ArrayList<Integer>();
		
		skillNodeIds.add(1200); // GeneralRoofing
		skillNodeIds.add(800); // TV Wall Mount Std Surface
		skillServiceTypeId.add(7);// delivery
		skillServiceTypeId.add(20);// installation
		
		
		newProviderSearchCriteriaVO.setSkillNodeIds(skillNodeIds);
		newProviderSearchCriteriaVO.setSkillServiceTypeId(skillServiceTypeId);
		
		newProviderSearchCriteriaVO.setServiceLocation(alocation);
		return newProviderSearchCriteriaVO;
	}
	
	
	public static ServiceOrder modifyServiceDate(ServiceOrder so){
		Calendar cal = Calendar.getInstance();
		cal.set(2008, 06, 21);
		Timestamp serviceDt1 = new Timestamp(cal.getTimeInMillis());
		so.setServiceDateTypeId(new Integer(OrderConstants.FIXED_DATE));
		so.setServiceDate1(serviceDt1);
		
		return so;
		
	}
	
	/**
	 * @return A Service Order object for creating draft
	 * @throws ParseException 
	 */
	public static ServiceOrder getServiceOrderObject() throws ParseException  {
		
		// create an empty service order
		ServiceOrder so = new ServiceOrder();
		so.setSowTitle("Test order for JUnit");
		
		//buyer setup
		Buyer buyer = new Buyer();
		buyer.setBuyerId(1000);
		Contact buyerContact = new Contact();
		buyerContact.setContactId(11307);
		buyer.setBuyerContact(buyerContact);
		so.setBuyer(buyer);
		so.setBuyerContactId(11930);
		so.setBuyerResourceId(1000);
		
		//service order price and location setup
		so.setSpendLimitLabor(99.99);
		so.setProviderServiceConfirmInd(0);
		so.setServiceLocation(getSoLocation());
		so.setServiceContact(getContact());
		
		// create tasks from request
		List<ServiceOrderTask> tasks = new ArrayList<ServiceOrderTask>();
		ServiceOrderTask task = new ServiceOrderTask();
		task.setServiceTypeId(16);
		task.setSkillNodeId(812);
		task.setParentId(809);
		task.setTaskName("Task1 for test");
		tasks.add(task);
		so.setTasks(tasks);			
				
		so.setPrimarySkillCatId(800);
	
		//Setup service date
		Calendar cal = Calendar.getInstance();
		so.setCreatedDate(new Timestamp(cal.getTimeInMillis()));
		cal.add(Calendar.DAY_OF_MONTH, 2);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		so.setServiceDate1(new Timestamp(sdf.parse(sdf.format(new Date(cal.getTimeInMillis()))).getTime()));
		so.setServiceDateTypeId(new Integer(OrderConstants.FIXED_DATE));
		so.setServiceTimeStart("08:00 AM");
		/* for now update so with spnId = 3, assuming there r tiers with spnId 3 & providers with spnId 3  */ 
		so.setSpnId(3);
		return so;
	}
	
	public static Hashtable<String, List<ServiceOrderSearchResultsVO>> getGroupedServiceOrders(
			IServiceOrderBO soBo, IOrderGroupBO soGroupBo) throws Exception {
		Hashtable<String, List<ServiceOrderSearchResultsVO>> groupedOrders = new Hashtable<String, List<ServiceOrderSearchResultsVO>>();

		// Create 1st SO
		ServiceOrder so1 = createServiceOrder(soBo);
		// Create 2nd SO
		ServiceOrder so2 = createServiceOrder(soBo);
		// Create 3rd SO
		ServiceOrder so3 = createServiceOrder(soBo);		

		// Create group
		String parentId = soGroupBo.getParentMatchForSO(so2.getSoId(), String
				.valueOf(OrderConstants.DRAFT_STATUS));
		parentId = soGroupBo.getParentMatchForSO(so3.getSoId(), String
				.valueOf(OrderConstants.DRAFT_STATUS));
		
		List<ServiceOrderSearchResultsVO> grpOrdersList = soGroupBo.getServiceOrdersForGroup(parentId);
		soGroupBo.priceOrderGroup(grpOrdersList);
		groupedOrders.put(parentId, grpOrdersList);
		return groupedOrders;

	}
	
	public static void setCustomReference(ServiceOrder so){
		List<ServiceOrderCustomRefVO> customRefVoList = new ArrayList<ServiceOrderCustomRefVO>();
		ServiceOrderCustomRefVO customRefVo = new ServiceOrderCustomRefVO();
		customRefVo.setRefType(OrderConstants.CUSTOM_REF_TEMPLATE_NAME);
		customRefVo.setRefValue("Garage Door Opener");
		customRefVo.setRefTypeId(50);
		customRefVoList.add(customRefVo);
		so.setCustomRefs(customRefVoList);
	}
	
	public static ServiceOrderSearchResultsVO getServiceOrderSearchResultsVO() {
		ServiceOrderSearchResultsVO serviceOrderSearchResultsVO = new ServiceOrderSearchResultsVO();
		serviceOrderSearchResultsVO.setBuyerID(22);
		serviceOrderSearchResultsVO.setPrimarySkillCategoryId(800);
		return serviceOrderSearchResultsVO;
	}
	
	public static SoLocation getSoLocation() {
		SoLocation soLocation = new SoLocation();
		soLocation.setCity("Naperville");
		soLocation.setState("IL");
		soLocation.setStreet1("1234 main st");
		soLocation.setZip("60502");
		
		//soLocation.setLocnTypeId(OrderConstants.SERVICE_LOCATION_CONTACT_TYPE_ID);
		return soLocation;
	}
	
	/**
	 * This method creates Contact object for testing
	 * @return Contact
	 */
	public static Contact getContact() {
		Contact contact = new Contact();
		contact.setFirstName("John");
		contact.setLastName("Crane");
		List<PhoneVO> phonesVo = new ArrayList<PhoneVO>();
		PhoneVO phoneVo = new PhoneVO();
		phoneVo.setPhoneNo("6308748747");
		phoneVo.setPhoneType(1);
		phoneVo.setClassId(1);
		phonesVo.add(phoneVo);
		
		contact.setPhones(phonesVo);
		return contact;
	}
	
	public static List<Integer> getRootNodeIds() {
		List<Integer> list = new ArrayList<Integer>();
		list.add(1500);
		return list;
	}
	public static ServiceOrder getServiceOrder() {
		return null;
	}
	
	public static ServiceOrder createServiceOrder(IServiceOrderBO soBo)throws Exception{
		ServiceOrder so = Fixtures.getServiceOrderObject();
		setCustomReference(so);
		SecurityContext securityContext = ServiceOrderUtil
				.getSecurityContextForBuyer(so.getBuyer().getBuyerId());
		ProcessResponse pr = soBo.processCreateDraftSO(so, securityContext);
		String soId = pr.getObj().toString();
		so = soBo.getServiceOrder(soId);
		return so;
	}
	
	public static ServiceOrder getUpdateServiceOrder() {
		
		ServiceOrder updatedSO = new ServiceOrder();
		String soId = "100-3290-5627-43";
		updatedSO.setSoId(soId);
		updatedSO.setCustomRefs(getCustomReferenceFields(soId));
		return updatedSO;
		
	}
	
	public static ServiceOrder getUpdateServiceOrderWithCustomRefs(String soId) {
		
		ServiceOrder updatedSO = new ServiceOrder();
		updatedSO.setSoId(soId);
		updatedSO.setCustomRefs(getCustomReferenceFields(soId));
		return updatedSO;
		
	}
	
	public static List<ServiceOrderCustomRefVO> getCustomReferenceFields(String soId) {
		
		List<ServiceOrderCustomRefVO> customRefs = new ArrayList<ServiceOrderCustomRefVO>();
		
		ServiceOrderCustomRefVO newCustRef1 = new ServiceOrderCustomRefVO();
		newCustRef1.setsoId(soId);
		newCustRef1.setRefTypeId(1);
		newCustRef1.setRefValue("0004055");
		customRefs.add(newCustRef1);
		
		ServiceOrderCustomRefVO newCustRef2 = new ServiceOrderCustomRefVO();
		newCustRef2.setsoId(soId);
		newCustRef2.setRefTypeId(2);
		newCustRef2.setRefValue("NEWFirst");
		customRefs.add(newCustRef2);
		
		// Delete test; this should be deleted
		//ServiceOrderCustomRefVO newCustRef3 = new ServiceOrderCustomRefVO();
		//newCustRef3.setsoId(soId);
		//newCustRef3.setRefTypeId(199);
		//newCustRef3.setRefValue("INSTALLATION ORDER");
		//customRefs.add(newCustRef3);
		
		// Update test; this should be updated
		ServiceOrderCustomRefVO newCustRef4 = new ServiceOrderCustomRefVO();
		newCustRef4.setsoId(soId);
		newCustRef4.setRefTypeId(203);
		newCustRef4.setRefValue("0004055NEWFirst");
		customRefs.add(newCustRef4);
		
		// 
		ServiceOrderCustomRefVO newCustRef5 = new ServiceOrderCustomRefVO();
		newCustRef5.setsoId(soId);
		newCustRef5.setRefTypeId(3); // Sales check number
		newCustRef5.setRefValue("111000999");
		customRefs.add(newCustRef5);
		
		return customRefs;
		
	}
	
	public static SecurityContext getBuyerSecurityContext(String buyerUserName) {
		String userName = buyerUserName;
		SecurityContext securityContext = new SecurityContext(userName);
		securityContext.setRoleId(OrderConstants.BUYER_ROLEID);
		securityContext.setUsername(userName);
		
		LoginCredentialVO loginCredentialVO = new LoginCredentialVO();
		loginCredentialVO.setPhoneNo("1234567890");
		loginCredentialVO.setEmail("awadhwa@searshc.com");
		securityContext.setRoles(loginCredentialVO);
		securityContext.setAutoACH(Boolean.TRUE);
		
		return securityContext;
	}
	
	public static List<ChildServiceOrderVO> getServiceOrderGroupChild() {
		List<ChildServiceOrderVO> childList = new ArrayList<ChildServiceOrderVO>();
		
		ChildServiceOrderVO child1 = new ChildServiceOrderVO();
		child1.setSoId("123");
		childList.add(child1);
		ChildServiceOrderVO child2 = new ChildServiceOrderVO();
		child2.setSoId("345");
		childList.add(child2);
		return childList ;
	}

    public static BuyerReferenceVO getBuyerReferenceVO (){
    	BuyerReferenceVO buyerRefVO = new BuyerReferenceVO();
		buyerRefVO.setBuyerId(22);
		buyerRefVO.setBuyerRefTypeId(290);
		buyerRefVO.setReferenceType("Test CustomRef Type");
		buyerRefVO.setReferenceDescription("Test CutomRef Desc");
		buyerRefVO.setSoIdentifier(1);
		buyerRefVO.setActiveInd(1);
		buyerRefVO.setBuyerInput(1);
		buyerRefVO.setProviderInput(0);
		buyerRefVO.setRequired(0);
		buyerRefVO.setSearchable(0);
		buyerRefVO.setPrivateInd(true);
		return buyerRefVO;
    }
    
    public static LicensesAndCertVO getLicensesAndCertVO (){
    	LicensesAndCertVO licensesAndCertVO = new LicensesAndCertVO();    	
    	licensesAndCertVO.setCredentialTypeId(1);
		licensesAndCertVO.setCategoryId(18);
		licensesAndCertVO.setCity("");
		licensesAndCertVO.setCounty("");
		licensesAndCertVO.setCredentialNum("");
		licensesAndCertVO.setIssuerOfCredential("Contr123456");
		licensesAndCertVO.setLicenseName("Contr123");
		licensesAndCertVO.setStateId("AL");
		licensesAndCertVO.setVendorId(5597);
		licensesAndCertVO.setMapCategory(null);
		licensesAndCertVO.setMapCredentialType(null);
		licensesAndCertVO.setMapState(new ArrayList<String>());
		licensesAndCertVO.setDataList(null);
		licensesAndCertVO.setCredentials(null);
		licensesAndCertVO.setVendorCredId(2469);
		licensesAndCertVO.setCredTypeDesc("");
		licensesAndCertVO.setAddCredentialToFile(0);		
		byte[] a=new byte[]{37, 80, 68, 70, 45};		
		licensesAndCertVO.setCredentialDocumentBytes(a);
		licensesAndCertVO.setCredentialDocumentExtention("application/pdf");
		licensesAndCertVO.setCredentialDocumentFileName("testCreated1.pdf");
		licensesAndCertVO.setCredentialDocumentFileSize(1111);
		licensesAndCertVO.setCurrentDocumentID(0);
		licensesAndCertVO.setCredSize(0);		
		Date date1 = new Date();		
		licensesAndCertVO.setIssueDate(date1);		
       	licensesAndCertVO.setExpirationDate(date1);
		return licensesAndCertVO;
    }
    public static CredentialProfile getCredentialProfile (){
    	CredentialProfile cProfile = new CredentialProfile();
    	cProfile.setAuditClaimedBy("");
    	cProfile.setCredAmount("15000.00");
    	cProfile.setCredCategory(null);
    	cProfile.setCredentialCategoryId(42);
    	cProfile.setCredentialCategoryIdName("");
    	cProfile.setCredentialCity(null);
    	cProfile.setCredentialCounty("ww");
    	byte[] a=new byte[]{37, 80, 68, 70, 45};	
    	cProfile.setCredentialDocumentBytes(a);
    	cProfile.setCredentialDocumentExtention("image/jpeg");
    	cProfile.setCredentialDocumentFileName("aa.jpg");
    	cProfile.setCredentialDocumentFileSize(1212);
    	cProfile.setCredentialDocumentReference(null);
    	Timestamp date1 = new Timestamp(Calendar.getInstance().getTimeInMillis());
    	cProfile.setCredentialExpirationDate(date1);    	
    	cProfile.setCredentialId(2466);
    	cProfile.setCredentialIssueDate(date1);
    	cProfile.setCredentialName("wewrwr");
    	cProfile.setCredentialNumber("123131");
    	cProfile.setCredentialSource("weeww");
    	cProfile.setCredentialState("AL");
    	cProfile.setCredentialTypeId(6);
    	cProfile.setCredentialTypeIdName(null);
    	cProfile.setCurrentDocumentID(0);
    	cProfile.setCurrentDocumentTS(null);
    	cProfile.setFullResoueceName(null);
    	cProfile.setVendorCredId(0);
    	cProfile.setVendorId(5597);
    	return cProfile;
    }
    
    public static TeamCredentialsVO getTeamCredentialsVO (){
    	TeamCredentialsVO teamCredentialsVO = new TeamCredentialsVO();
    	teamCredentialsVO.setCategoryId(1);
    	teamCredentialsVO.setCity("");
    	teamCredentialsVO.setCounty("");
    	byte[] a=new byte[]{37, 80, 68, 70, 45};	
    	teamCredentialsVO.setCredentialDocumentBytes(a);
    	teamCredentialsVO.setCredentialDocumentExtention("image/jpeg");
    	teamCredentialsVO.setCredentialDocumentFileName("a1.JPG");
    	teamCredentialsVO.setCredentialDocumentFileSize(1234);
    	teamCredentialsVO.setCredentialDocumentId(0);
    	teamCredentialsVO.setCredentialDocumentReference(null);
    	teamCredentialsVO.setCredentialNumber("");
    	teamCredentialsVO.setCredType(null);
    	Date date1 = new Date();	
    	teamCredentialsVO.setExpirationDate(date1);
    	teamCredentialsVO.setIssueDate(date1);
    	teamCredentialsVO.setIssuerName("OffTestIssuer");
    	teamCredentialsVO.setLicenseName("OffTestLicense");
    	teamCredentialsVO.setResourceCredId(1583);
    	teamCredentialsVO.setResourceId(2307);
    	teamCredentialsVO.setState("IL");
    	teamCredentialsVO.setTypeId(1);
    	teamCredentialsVO.setVendorId(5597);
    	teamCredentialsVO.setWfStateId(-1);
    	
		return teamCredentialsVO;
    }


	public static SPNTierEventInfoVO getSPNTierEventInfo(Integer currentTierId, Integer nextTierId, String soId, String groupId) {
		SPNTierEventInfoVO spnTierEventInfoVO = new SPNTierEventInfoVO();
		
		Calendar currentCal = Calendar.getInstance();
		currentCal.set(2009, 5, 11);
		
		Calendar nextCal = Calendar.getInstance();
		nextCal.set(2009, 5, 12);
		
		spnTierEventInfoVO.setCurrentFireTime(currentCal.getTime());
		spnTierEventInfoVO.setCurrentTierId(currentTierId);
		spnTierEventInfoVO.setNextFireTime(nextCal.getTime());
		spnTierEventInfoVO.setNextTierId(nextTierId);
		spnTierEventInfoVO.setSoId(soId);
		spnTierEventInfoVO.setGroupOrderId(groupId);
		return spnTierEventInfoVO;
	}
}
