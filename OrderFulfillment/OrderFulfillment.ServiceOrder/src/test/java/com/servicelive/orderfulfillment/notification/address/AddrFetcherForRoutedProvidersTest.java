/**
 * 
 */
package com.servicelive.orderfulfillment.notification.address;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.servicelive.domain.common.Contact;
import com.servicelive.domain.so.SORoutingRuleAssoc;
import com.servicelive.marketplatform.client.MarketPlatformRemoteServiceClient;
import com.servicelive.marketplatform.serviceinterface.IMarketPlatformProviderBO;
import com.servicelive.orderfulfillment.dao.IServiceOrderDao;
import com.servicelive.orderfulfillment.dao.ServiceOrderDao;
import com.servicelive.orderfulfillment.domain.RoutedProvider;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.notification.enumerations.NotificationType;

/**
 * @author karthik_hariharan01
 *
 */
public class AddrFetcherForRoutedProvidersTest {

	AddrFetcherForRoutedProviders addrFetcherForRoutedProvider;
	IMarketPlatformProviderBO mktPlatformProviderBO;
	IServiceOrderDao serviceOrderDao;

	ServiceOrder serviceOrder;
	SORoutingRuleAssoc assoc;
	List<Long> firmIdsToExclude;
	List<Long> firmIdsAfterExclude;
	List<Long> providerIdsAfterExclude;

	List<Contact> contactsFirm = new ArrayList<Contact>();
	List<Contact> contactsProvider = new ArrayList<Contact>();

	Contact contact1 = new Contact();
	Contact contact2 = new Contact();
	Contact contact3 = new Contact();
	Contact contact4 = new Contact();
	Contact contact5 = new Contact();
	Contact contact6 = new Contact();
	Contact contact7 = new Contact();
	Contact contact8 = new Contact();

	@Before
	public void setUp() {
		addrFetcherForRoutedProvider = new AddrFetcherForRoutedProviders();
		mktPlatformProviderBO = mock(MarketPlatformRemoteServiceClient.class);
		serviceOrderDao = mock(ServiceOrderDao.class);

		addrFetcherForRoutedProvider.setMktPlatformProviderBO(mktPlatformProviderBO);
		addrFetcherForRoutedProvider.setServiceOrderDao(serviceOrderDao);

		serviceOrder = getServiceOrder();

		assoc = getCARDetails();
		when(serviceOrderDao.getCARAssociation("600-5210-3339-20")).thenReturn(assoc);

		loadContactObjects();
	}


	@Test
	public void testFetchDestAddrListExclude10203() {

		firmIdsToExclude = getFirmIdsToExclude(new Long(10203));
		when(serviceOrderDao.getVendorIdsToExclude(assoc.getRoutingRuleHdrId().toString())).thenReturn(firmIdsToExclude);

		firmIdsAfterExclude = getFirmIdsAfterExclude(new Long(10202));
		providerIdsAfterExclude = getProviderIdsAfterExclude(new Long(10202));

		contactsFirm = getContactsOfFirmsAfterExclude(new Long(10202));
		contactsProvider = getContactsOfProvidersAfterExclude(new Long(10202));

		when(mktPlatformProviderBO.retrieveProviderResourceContactInfoList(providerIdsAfterExclude)).thenReturn(contactsProvider);
		when(mktPlatformProviderBO.retrieveProviderAdminContactInfoList(firmIdsAfterExclude)).thenReturn(contactsFirm);


		List<String> addressList = addrFetcherForRoutedProvider.fetchDestAddrList(NotificationType.EMAIL,serviceOrder);

		Assert.assertNotNull(addressList);
		Assert.assertEquals(4, addressList.size());

		boolean isValid = false;
		if(addressList.contains("a@searshc.com")
				&& addressList.contains("b@searshc.com") 
				&& addressList.contains("c@searshc.com") 
				&& addressList.contains("g@searshc.com")){
			isValid = true;
		}
		Assert.assertTrue(isValid);

	}


	@Test
	public void testFetchDestAddrListExclude10202() {

		firmIdsToExclude = getFirmIdsToExclude(new Long(10202));
		when(serviceOrderDao.getVendorIdsToExclude(assoc.getRoutingRuleHdrId().toString())).thenReturn(firmIdsToExclude);

		firmIdsAfterExclude = getFirmIdsAfterExclude(new Long(10203));
		providerIdsAfterExclude = getProviderIdsAfterExclude(new Long(10203));

		contactsFirm = getContactsOfFirmsAfterExclude(new Long(10203));
		contactsProvider = getContactsOfProvidersAfterExclude(new Long(10203));

		when(mktPlatformProviderBO.retrieveProviderResourceContactInfoList(providerIdsAfterExclude)).thenReturn(contactsProvider);
		when(mktPlatformProviderBO.retrieveProviderAdminContactInfoList(firmIdsAfterExclude)).thenReturn(contactsFirm);

		List<String> addressList = addrFetcherForRoutedProvider.fetchDestAddrList(NotificationType.EMAIL,serviceOrder);

		Assert.assertNotNull(addressList);
		Assert.assertEquals(4, addressList.size());

		boolean isValid = false;
		if(addressList.contains("d@searshc.com")
				&& addressList.contains("e@searshc.com") 
				&& addressList.contains("f@searshc.com") 
				&& addressList.contains("h@searshc.com")){
			isValid = true;
		}
		Assert.assertTrue(isValid);
	}


	@Test
	public void testFetchDestAddrListNoFirmToExclude() {

		firmIdsToExclude = getFirmIdsToExclude(null);
		when(serviceOrderDao.getVendorIdsToExclude(assoc.getRoutingRuleHdrId().toString())).thenReturn(firmIdsToExclude);

		//pass a dummy value for no exclusion case
		firmIdsAfterExclude = getFirmIdsAfterExclude(new Long(0));
		providerIdsAfterExclude = getProviderIdsAfterExclude(new Long(0));

		contactsFirm = getContactsOfFirmsAfterExclude(new Long(0));
		contactsProvider = getContactsOfProvidersAfterExclude(new Long(0));

		when(mktPlatformProviderBO.retrieveProviderResourceContactInfoList(providerIdsAfterExclude)).thenReturn(contactsProvider);
		when(mktPlatformProviderBO.retrieveProviderAdminContactInfoList(firmIdsAfterExclude)).thenReturn(contactsFirm);

		List<String> addressList = addrFetcherForRoutedProvider.fetchDestAddrList(NotificationType.EMAIL,serviceOrder);

		Assert.assertNotNull(addressList);
		Assert.assertEquals(8, addressList.size());

		boolean isValid = false;

		if(addressList.contains("a@searshc.com")
				&& addressList.contains("b@searshc.com") 
				&& addressList.contains("c@searshc.com") 
				&& addressList.contains("d@searshc.com")
				&& addressList.contains("e@searshc.com") 
				&& addressList.contains("f@searshc.com") 
				&& addressList.contains("g@searshc.com")
				&& addressList.contains("h@searshc.com")){
			isValid = true;
		}
		Assert.assertTrue(isValid);
	}

	private ServiceOrder getServiceOrder(){

		ServiceOrder serviceOrder = new ServiceOrder();

		List<RoutedProvider> routedResourceList = new ArrayList<RoutedProvider>();
		RoutedProvider routedProvider = null;

		routedProvider = new RoutedProvider();
		routedProvider.setVendorId(10202);
		routedProvider.setProviderResourceId(new Long(10254));
		routedResourceList.add(routedProvider);

		routedProvider = new RoutedProvider();
		routedProvider.setVendorId(10202);
		routedProvider.setProviderResourceId(new Long(10255));
		routedResourceList.add(routedProvider);

		routedProvider = new RoutedProvider();
		routedProvider.setVendorId(10202);
		routedProvider.setProviderResourceId(new Long(10256));
		routedResourceList.add(routedProvider);

		routedProvider = new RoutedProvider();
		routedProvider.setVendorId(10203);
		routedProvider.setProviderResourceId(new Long(10257));
		routedResourceList.add(routedProvider);

		routedProvider = new RoutedProvider();
		routedProvider.setVendorId(10203);
		routedProvider.setProviderResourceId(new Long(10258));
		routedResourceList.add(routedProvider);

		routedProvider = new RoutedProvider();
		routedProvider.setVendorId(10203);
		routedProvider.setProviderResourceId(new Long(10259));
		routedResourceList.add(routedProvider);


		serviceOrder.setRoutedResources(routedResourceList);
		serviceOrder.setSoId("600-5210-3339-20");

		return serviceOrder;
	}

	private SORoutingRuleAssoc getCARDetails(){
		SORoutingRuleAssoc assoc = new SORoutingRuleAssoc();
		assoc.setRoutingRuleHdrId(6516);
		assoc.setSoId("600-5210-3339-20");
		return assoc;
	}

	private List<Long> getFirmIdsToExclude(Long firmIdToExclude){
		List<Long> firmIdsToExclude = null;
		if(null != firmIdToExclude){
			firmIdsToExclude = new ArrayList<Long>();
			firmIdsToExclude.add(firmIdToExclude);
		}
		return firmIdsToExclude;
	}

	private List<Long> getFirmIdsAfterExclude(Long firmIdAfterExclude){
		List<Long> firmIdsAfterExclude = new ArrayList<Long>();
		if(10202 == firmIdAfterExclude || 10203 == firmIdAfterExclude){
			firmIdsAfterExclude.add(firmIdAfterExclude);
		}else{
			firmIdsAfterExclude.add(new Long(10202));
			firmIdsAfterExclude.add(new Long(10203));
		}

		return firmIdsAfterExclude;
	}

	private List<Long> getProviderIdsAfterExclude(Long firmIdAfterExclude){
		List<Long> providerIdsAfterExclude = new ArrayList<Long>();
		if(10202 == firmIdAfterExclude){
			providerIdsAfterExclude.add(new Long(10254));
			providerIdsAfterExclude.add(new Long(10255));
			providerIdsAfterExclude.add(new Long(10256));
		}else if(10203 == firmIdAfterExclude){
			providerIdsAfterExclude.add(new Long(10257));
			providerIdsAfterExclude.add(new Long(10258));
			providerIdsAfterExclude.add(new Long(10259));
		}else{
			providerIdsAfterExclude.add(new Long(10254));
			providerIdsAfterExclude.add(new Long(10255));
			providerIdsAfterExclude.add(new Long(10256));
			providerIdsAfterExclude.add(new Long(10257));
			providerIdsAfterExclude.add(new Long(10258));
			providerIdsAfterExclude.add(new Long(10259));
		}

		return providerIdsAfterExclude;
	}

	private List<Contact> getContactsOfFirmsAfterExclude(Long firmIdAfterExclude){
		if(10202 == firmIdAfterExclude){
			contactsFirm.add(contact7);
		}else if(10203 == firmIdAfterExclude){
			contactsFirm.add(contact8);
		}else{
			contactsFirm.add(contact7);
			contactsFirm.add(contact8);
		}

		return contactsFirm;

	}

	private List<Contact> getContactsOfProvidersAfterExclude(Long firmIdAfterExclude){

		if(10202 == firmIdAfterExclude){
			contactsProvider.add(contact1);
			contactsProvider.add(contact2);
			contactsProvider.add(contact3);
		}else if(10203 == firmIdAfterExclude){
			contactsProvider.add(contact4);
			contactsProvider.add(contact5);
			contactsProvider.add(contact6);
		}else{
			contactsProvider.add(contact1);
			contactsProvider.add(contact2);
			contactsProvider.add(contact3);
			contactsProvider.add(contact4);
			contactsProvider.add(contact5);
			contactsProvider.add(contact6);
		}

		return contactsProvider;
	}

	private void loadContactObjects(){
		//contact of 10254
		contact1.setContactId(1);
		contact1.setEmail("a@searshc.com");

		//contact of 10255
		contact2.setContactId(2);
		contact2.setEmail("b@searshc.com");

		//contact of 10256
		contact3.setContactId(3);
		contact3.setEmail("c@searshc.com");

		//contact of 10257
		contact4.setContactId(4);
		contact4.setEmail("d@searshc.com");

		//contact of 10258
		contact5.setContactId(5);
		contact5.setEmail("e@searshc.com");

		//contact of 10259
		contact6.setContactId(6);
		contact6.setEmail("f@searshc.com");

		//contact of firm 10202
		contact7.setContactId(7);
		contact7.setEmail("g@searshc.com");

		//contact of firm 10203
		contact8.setContactId(8);
		contact8.setEmail("h@searshc.com");
	}
}
