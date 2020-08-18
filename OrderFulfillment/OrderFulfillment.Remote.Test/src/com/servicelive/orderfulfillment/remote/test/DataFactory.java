package com.servicelive.orderfulfillment.remote.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import com.servicelive.domain.buyer.Buyer;
import com.servicelive.domain.buyer.BuyerResource;
import com.servicelive.domain.common.Contact;
import com.servicelive.domain.common.Location;
import com.servicelive.domain.lookup.LookupSkills;
import com.servicelive.domain.provider.ServiceProvider;
import com.servicelive.orderfulfillment.domain.RoutedProvider;
import com.servicelive.orderfulfillment.domain.SOContact;
import com.servicelive.orderfulfillment.domain.SOCustomReference;
import com.servicelive.orderfulfillment.domain.SOGroup;
import com.servicelive.orderfulfillment.domain.SOLocation;
import com.servicelive.orderfulfillment.domain.SOLogging;
import com.servicelive.orderfulfillment.domain.SONote;
import com.servicelive.orderfulfillment.domain.SOPart;
import com.servicelive.orderfulfillment.domain.SOPhone;
import com.servicelive.orderfulfillment.domain.SOPrice;
import com.servicelive.orderfulfillment.domain.SOSchedule;
import com.servicelive.orderfulfillment.domain.SOTask;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.ContactLocationType;
import com.servicelive.orderfulfillment.domain.type.ContactType;
import com.servicelive.orderfulfillment.domain.type.LocationClassification;
import com.servicelive.orderfulfillment.domain.type.LocationType;
import com.servicelive.orderfulfillment.domain.type.PhoneClassification;
import com.servicelive.orderfulfillment.domain.type.PhoneType;
import com.servicelive.orderfulfillment.domain.type.PriceModelType;
import com.servicelive.orderfulfillment.domain.type.SOScheduleType;
import com.servicelive.orderfulfillment.serviceinterface.vo.CreateOrderRequest;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification.EntityType;
import com.servicelive.orderfulfillment.test.helper.TestHelper;

/**
 * This class exposes factory methods for
 * use in different requests. It serves as a data provider
 * for tests that need data to be created.
 * @author Mustafa Motiwala
 *
 */
public class DataFactory {

    private static final String ADMIN_USER_NAME="rbutl4799";
    TestHelper testHelper;

    @PersistenceContext(type=PersistenceContextType.TRANSACTION)
    private EntityManager em;
    private List<LookupSkills> listSkills = new ArrayList<LookupSkills>();
    private List<ServiceProvider> listServiceProviders = new ArrayList<ServiceProvider>();
    private int buyerResourceId;
    private int buyerAccountId;
    
    /**
     * Creates a new CreateOrderRequest object every time this
     * method is invoked. Currently some of the information (buyer info etc.) is static
     * but can be modified to read it dynamically from the database hence offering
     * a richer variety.
     * @return a new & unique instance of CreateOrderRequest.
     */
//    @DataProvider(name="CreateOrderRequestFactory")
    @Transactional(readOnly=true)
    public CreateOrderRequest newCreateOrderFulfillmentRequest(){
        //Get the buyer object.
        BuyerResource buyerResource = em.find(BuyerResource.class, buyerResourceId);
        CreateOrderRequest returnVal = new CreateOrderRequest();
        returnVal.setServiceOrder(newServiceOrder(buyerResource));
        returnVal.setIdentification(newBuyerIdentification(buyerResource.getBuyer()));
        returnVal.setBuyerState(buyerResource.getBuyer().getLocationByPriLocnId().getLookupStates().getId());
        return returnVal;
    }
    
    @Transactional(readOnly=true)
    public Identification newBuyerIdentification(){
        BuyerResource buyerResource = em.find(BuyerResource.class,buyerResourceId);
        return newBuyerIdentification(buyerResource.getBuyer());
    }
    
    public Identification newBuyerIdentification(Buyer buyer){
        Identification returnVal = new Identification();
        returnVal.setEntityType(EntityType.BUYER);
        returnVal.setUsername(buyer.getUser().getUsername());
        returnVal.setCompanyId(buyer.getBuyerId().longValue());
        returnVal.setRoleId(5);
        return returnVal;
    }
    
    public Identification newBuyerIdentification(Long buyerId){
    	Buyer buyer = em.find(Buyer.class, buyerId.intValue());
    	return newBuyerIdentification(buyer);
    }
    
    public Identification newProviderIdentification(RoutedProvider rp){
        Identification returnVal = new Identification();
        returnVal.setEntityType(EntityType.PROVIDER);
        returnVal.setUsername("Testing 123");
        returnVal.setFullname("TestFName TestLName");
        returnVal.setCompanyId(rp.getVendorId().longValue());
        returnVal.setResourceId(rp.getProviderResourceId());
        returnVal.setRoleId(1);
        return returnVal;
    }
    
    public Identification newAdminIdentification(){
        Identification returnVal = new Identification();
        returnVal.setEntityType(EntityType.SLADMIN);
        returnVal.setUsername(ADMIN_USER_NAME);
        returnVal.setRoleId(2);
        return returnVal;
    }
    
    @Transactional(readOnly=true)
    public ServiceOrder getServiceOrderForEdit(String soId){
    	BuyerResource buyerResource = em.find(BuyerResource.class,buyerResourceId);
    	ServiceOrder serviceOrder = this.newServiceOrder(buyerResource);
    	serviceOrder.setSoId(soId);
    	return serviceOrder;
    }
    
    private ServiceOrder newServiceOrder(BuyerResource buyerResource){
        ServiceOrder returnVal = new ServiceOrder();
        Buyer buyer = buyerResource.getBuyer();
        returnVal.setBuyerId(buyer.getBuyerId().longValue());
        
        returnVal.setBuyerResourceId(buyerResource.getResourceId().longValue());
        returnVal.setPrimarySkillCatId(800);
        returnVal.setBuyerContactId(buyerResource.getContact().getContactId());
        returnVal.setFundingTypeId(buyer.getLookupFundingType().getId());
        if (returnVal.getFundingTypeId().intValue() == 70)
        	returnVal.setAccountId(buyerAccountId);
        returnVal.setPostingFee(buyer.getPostingFee());
        returnVal.setCancellationFee(buyer.getCancellationFee());
        returnVal.setCreatorUserName(buyerResource.getUser().getUsername());
        returnVal.setSowDs("Dummy Project with Dummy SOW.");
        returnVal.setSowTitle("Scrap Project");
        returnVal.setTasks(getTasks());
        returnVal.setSpendLimitLabor(new BigDecimal(new Random().nextInt(100)));
        returnVal.setServiceLocationTimeZone("EST5EDT");
        /*Timezone magic:*/
        TimeZone timezone = TimeZone.getTimeZone(returnVal.getServiceLocationTimeZone());
        int offset = Math.round(timezone.getOffset((new Date()).getTime()) / (1000 * 60 * 60));
        returnVal.setServiceDateTimezoneOffset(offset);
        returnVal.addLocation(newSOLocation(buyerResource));
        returnVal.setPriceModel(PriceModelType.NAME_PRICE);
        returnVal.setSpendLimitParts(new BigDecimal("0.0"));
        returnVal.setSchedule(newSOSchedule());

        returnVal.setPrice(newSOPrice(returnVal.getSpendLimitLabor(), returnVal.getSpendLimitParts()));
        returnVal.addContact(createServiceSOContact(buyerResource));
        this.addPartsTo(returnVal);
        this.addCustomRefsTo(returnVal);

        return returnVal;
    }
    
    private void addCustomRefsTo(ServiceOrder so) {
    	SOCustomReference incidentIdReference = new SOCustomReference();
    	incidentIdReference.setBuyerRefTypeId(40);
    	incidentIdReference.setBuyerRefValue(String.format("DUMMY-ID%d", (int)(Math.random()*100)));
    	
    	so.addCustomReference(incidentIdReference);
    }
    
    private void addPartsTo(ServiceOrder serviceOrder) {
    	if (serviceOrder == null) return;
    	
    	Date now = new Date();
    	SOPart part = new SOPart();
    	part.setCreatedDate(now);
    	part.setHeight("10");
    	part.setLength("10");
    	part.setManufacturer("dummy manufacturer");
    	part.setMeasurementStandard(null);
    	part.setModelNumber("dummy model #");
    	part.setModifiedBy("test-data-factory");
    	part.setModifiedDate(now);
    	part.setPartDs("dummy part description");
    	part.setProviderBringPartInd(true);
    	part.setQuantity("1");
    	part.setReferencePartId("dummy referencePartId");
    	part.setReturnCarrierOther("dummy returnCarrierOther");
    	
    	serviceOrder.addPart(part);
    	
    }

    public SOPrice newSOPrice(BigDecimal spendLimitLabor, BigDecimal spendLimitParts) {
        SOPrice newSOPrice = new SOPrice();
        newSOPrice.setCreatedDate(new Date());
        newSOPrice.setOrigSpendLimitLabor(spendLimitLabor);
        newSOPrice.setOrigSpendLimitParts(spendLimitParts);
        newSOPrice.setDiscountedSpendLimitLabor(spendLimitLabor);
        newSOPrice.setDiscountedSpendLimitParts(spendLimitParts);
        return newSOPrice;
    }

    public SOContact createBuyerSOContact(BuyerResource buyerResource) {
        SOContact newSOContact = new SOContact();
        newSOContact.setEmail("of.remote.test.buyer@test.com");
        newSOContact.setContactTypeId(ContactType.PRIMARY);
        newSOContact.setEntityId(buyerResource.getResourceId());
        newSOContact.setEntityType(com.servicelive.orderfulfillment.domain.type.EntityType.BUYER);

        Buyer buyer = testHelper.getBuyerInfo(buyerResource.getBuyer().getBuyerId());
        Contact buyerContactInfo = testHelper.getBuyerContactInfo(buyer.getBuyerId());

        newSOContact.setBusinessName(buyer.getBusinessName());
        newSOContact.setFirstName(buyerContactInfo.getFirstName());
        newSOContact.setLastName(buyerContactInfo.getLastName());

        newSOContact.addContactLocation(ContactLocationType.END_USER);

        return newSOContact;
    }
    
    public SOContact createServiceSOContact(BuyerResource buyerResource) {
        SOContact newSOContact = new SOContact();
        newSOContact.setEmail("of.remote.test.buyer@test.com");
        newSOContact.setContactTypeId(ContactType.PRIMARY);
        newSOContact.setEntityId(buyerResource.getResourceId());
        newSOContact.setEntityType(com.servicelive.orderfulfillment.domain.type.EntityType.BUYER);

        Buyer buyer = testHelper.getBuyerInfo(buyerResource.getBuyer().getBuyerId());
        Contact buyerContactInfo = testHelper.getBuyerContactInfo(buyer.getBuyerId());

        newSOContact.setBusinessName(buyer.getBusinessName());
        newSOContact.setFirstName(buyerContactInfo.getFirstName());
        newSOContact.setLastName(buyerContactInfo.getLastName());

        newSOContact.addContactLocation(ContactLocationType.SERVICE);

        return newSOContact;
    }

    public SOContact newProviderSOContact(Long providerRsrcId) {
        SOContact newSOContact = new SOContact();
        newSOContact.setBusinessName("test business");
        newSOContact.setEmail("remote_test_provider@test.com");
        newSOContact.setContactTypeId(ContactType.PRIMARY);
        newSOContact.setFirstName("test_firstname");
        newSOContact.setLastName("test_lastname");
        newSOContact.addContactLocation(ContactLocationType.PROVIDER);
        newSOContact.setEntityId(providerRsrcId.intValue());
        newSOContact.setEntityType(com.servicelive.orderfulfillment.domain.type.EntityType.PROVIDER);

        Set<SOPhone> phones = new HashSet<SOPhone>();
        phones.add(newSOPhone());
        newSOContact.setPhones(phones);

        return newSOContact;
    }

    public SOPhone newSOPhone() {
        SOPhone newSOPhone = new SOPhone();
        newSOPhone.setPhoneNo("5555555555");
        newSOPhone.setPhoneType(PhoneType.PRIMARY);
        newSOPhone.setPhoneClass(PhoneClassification.HOME);
        return newSOPhone;
    }

    public SOLocation newProviderSOLocation() {
        SOLocation location = new SOLocation();
        location.setCity("Test City");
        location.setStreet1("Test Street");
        location.setState("MI");
        location.setCountry("US");
        location.setZip("48335");
        location.setSoLocationTypeId(LocationType.PROVIDER);
        return location;
    }

    public SOSchedule newSOSchedule() {
        SOSchedule returnVal = new SOSchedule();
        returnVal.setServiceDateTypeId(SOScheduleType.SINGLEDAY);
        Calendar svcDate = Calendar.getInstance();
        svcDate.set(2012, Calendar.JANUARY, 1);
//        svcDate.add(Calendar.MONTH, new Random().nextInt(12));
//        svcDate.add(Calendar.DAY_OF_MONTH, new Random().nextInt(28));
        returnVal.setServiceDate1(svcDate.getTime());
        returnVal.setServiceTimeStart("09:00 AM");
        returnVal.setServiceTimeEnd("06:00 PM");
        return returnVal;
    }

    private SOLocation newSOLocation(BuyerResource buyerResource) {
        SOLocation returnVal = new SOLocation();
        Location location = buyerResource.getBuyer().getLocationByPriLocnId();
        returnVal.setSoLocationClassId(LocationClassification.RESIDENTIAL);
        returnVal.setLocationName(location.getLocnName());
        returnVal.setStreet1(location.getStreet1());
        returnVal.setStreet2(location.getStreet2());
        returnVal.setCity(location.getCity());
        returnVal.setState(location.getLookupStates().getId());
        returnVal.setZip(location.getZip());
        //add service location
        returnVal.setSoLocationTypeId(LocationType.SERVICE);
        return returnVal;
    }

    private List<SOTask> getTasks() {
        List<SOTask> returnVal =new ArrayList<SOTask>();
        SOTask  task = new SOTask();
        task.setSkillNodeId(getSkill().getId());
        returnVal.add(task);
        return returnVal;
    }
    
    @SuppressWarnings("unchecked")
    private LookupSkills getSkill(){
        Random r = new Random();
        if(listSkills.isEmpty()){
            Query q = em.createQuery("SELECT ls FROM LookupSkills ls");
            listSkills = q.getResultList();
        }
        int idSkillNode = r.nextInt(listSkills.size());
        if(idSkillNode < 20){
            return listSkills.get(idSkillNode+20);
        }else{
            return listSkills.get(idSkillNode);
        }
    }
    
    @Transactional(readOnly=true)
    @SuppressWarnings("unchecked")
    public ServiceProvider getServiceProvider(){
        if(listServiceProviders.isEmpty()){
            Query q = em.createNativeQuery("select * from vendor_resource where contact_id in (select contact_id from user_profile) limit 100;", ServiceProvider.class);
            listServiceProviders =q.getResultList();
        }
        ServiceProvider returnVal =listServiceProviders.get(new Random().nextInt(100));
        //Ugly hack to bypass hibernate's limitation:
        returnVal = em.find(ServiceProvider.class, returnVal.getId());
        returnVal.getProviderFirm().getId();
        return returnVal;
    }
    
    @Transactional
    public void addLogging(String soId) {
        ServiceOrder so = em.find(ServiceOrder.class, soId);

        SOLogging log = new SOLogging();
        log.setChgComment("Blah 1");
        log.setServiceOrder(so);
        so.addLogging(log);
//        so.getLoggings().add(log);
        em.merge(so);
    }
    
    public SONote newBuyerNote() {
        SONote soNote = new SONote();
        soNote.setNote("remote test note");
        soNote.setCreatedDate(new Date());
        soNote.setSubject("remote test note subject");
        soNote.setPrivate(true);
        return soNote;
    }

    @Transactional
    public ServiceOrder getServiceOrder(String soId){
        ServiceOrder returnVal = em.find(ServiceOrder.class, soId);
        returnVal.getRoutedResources().isEmpty();
        return returnVal;
    }

    @Transactional
    public SOGroup getGroup(String groupId){
        SOGroup returnVal = em.find(SOGroup.class, groupId);
        for(ServiceOrder so:returnVal.getServiceOrders()){
            so.getRoutedResources().isEmpty(); //Load the RoutedResources for each so in group.
        }
        return returnVal;
    }

    public void setTestHelper(TestHelper testHelper) {
        this.testHelper = testHelper;
    }

	public void setBuyerResourceId(int buyerResourceId) {
		this.buyerResourceId = buyerResourceId;
	}

	public void setBuyerAccountId(int buyerAccountId) {
		this.buyerAccountId = buyerAccountId;
	}
}
