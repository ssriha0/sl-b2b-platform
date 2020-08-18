package com.servicelive.orderfulfillment.notification.address;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.servicelive.domain.common.Contact;
import com.servicelive.domain.so.SORoutingRuleAssoc;
import com.servicelive.marketplatform.serviceinterface.IMarketPlatformProviderBO;
import com.servicelive.orderfulfillment.dao.IServiceOrderDao;
import com.servicelive.orderfulfillment.domain.RoutedProvider;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.notification.enumerations.NotificationType;

public class AddrFetcherForRoutedProviders {
    IMarketPlatformProviderBO mktPlatformProviderBO;
    IServiceOrderDao serviceOrderDao;
    protected final Logger log = Logger.getLogger(getClass());
    public List<String> fetchDestAddrList(NotificationType notificationType, ServiceOrder serviceOrder) {
    	List<Long> providerIds = new ArrayList<Long>();
    	List<Long> providerFirmIds = new ArrayList<Long>();
    	for (RoutedProvider routedProvider : serviceOrder.getRoutedResources()){
    		providerIds.add(routedProvider.getProviderResourceId());
    		if(!providerFirmIds.contains(routedProvider.getVendorId().longValue())) {
    			providerFirmIds.add(routedProvider.getVendorId().longValue());
    		}
    	}
    	//SL-20436
    	//This code checks whether the emailIndicator is false for the providerFirmIds for the service orders
    	//routed through CAR rule and removes those records.The providerIds coming under those providerFirmIds
    	//are also removed. The new lists obtained are providerIdsAfterExclusion and firmIdsAfterExclusion.

    	List<Long> firmIdsToExclude = null;
    	try{
    		if(providerFirmIds.size()>0){
    			log.info("Inside new code for conditional routing" + serviceOrder.getSoId());

    			String ruleId="";
    			SORoutingRuleAssoc assoc = serviceOrderDao.getCARAssociation(serviceOrder.getSoId());
    			if(null!=assoc && null!=assoc.getRoutingRuleHdrId()){
    				ruleId=assoc.getRoutingRuleHdrId().toString();
    			}
    			if(StringUtils.isNotBlank(ruleId)){
    				firmIdsToExclude = serviceOrderDao.getVendorIdsToExclude(ruleId);
    			}
    			//remove the firm ids of which oppurtunity_email_ind is OFF
    			log.info("firmIdsToExclude query executed successfully");
    			if(null != firmIdsToExclude && firmIdsToExclude.size()>0){
    				log.info("firmIdsToExclude size:"+firmIdsToExclude.size());
    				providerFirmIds.removeAll(firmIdsToExclude);
    				log.info("providerFirmIds size:"+providerFirmIds.size());

    				//remove the provider ids  of the firms of which oppurtunity_email_ind is OFF
    				for (RoutedProvider routedProvider : serviceOrder.getRoutedResources()){
    					for(Long firmIdToExclude:firmIdsToExclude){
    						if(routedProvider.getVendorId().intValue() == firmIdToExclude.intValue()){
    							providerIds.remove(routedProvider.getProviderResourceId());
    						}
    					}
    				}
    			}
    		}
    	}
    	catch (Exception e) {
    		log.error(" Exception occured in getEmailInd in AddrFetcherForRoutedProviders"+e);
    		e.printStackTrace();
    	}

    	//SL-18979 AddrFetcher for SMSchanges
    	if(NotificationType.SMS.equals(notificationType)){
    		log.info("Inside new code for SMS check" + serviceOrder.getSoId());
    		try{
    			if(null != providerFirmIds && !providerFirmIds.isEmpty()){
					log.info("Inside new code for removing unwanted providerFirmIds from SMS" + serviceOrder.getSoId());
					providerFirmIds = serviceOrderDao.getFirmIdsForSMS(providerFirmIds);
    			}
    			if(null != providerIds && !providerIds.isEmpty()){
					log.info("Inside new code for removing unwanted providers from SMS" + serviceOrder.getSoId());
					providerIds = serviceOrderDao.getProviderIdsForSMS(providerIds);
				}
    		}catch (Exception e) {
    			log.error(" Exception occured in AddrFetcher for SMSchanges in AddrFetcherForRoutedProviders"+e);
    			e.printStackTrace();
    		}
    		log.info("After new code for SMS check" + serviceOrder.getSoId());
    	}

    	log.info("invoking getAddressList: providerIds-"+(providerIds!=null ?providerIds :"null")+" providerFirmIds-"+(providerFirmIds!=null ?providerFirmIds :"null")+" notificationType-"+notificationType);
    	return getAddressList(providerIds, notificationType, providerFirmIds);
    }

    protected List<String> getAddressList(List<Long> providerIds, NotificationType notificationType, List<Long> providerFirmIds){
        List<String> addrList = new ArrayList<String>();
        List<Contact> contacts =new ArrayList<Contact>();
        
        if(null != providerIds && !providerIds.isEmpty()){
        	contacts = mktPlatformProviderBO.retrieveProviderResourceContactInfoList(providerIds);
        }
        if(contacts != null && null != providerFirmIds && !providerFirmIds.isEmpty()) {
        	contacts.addAll(mktPlatformProviderBO.retrieveProviderAdminContactInfoList(providerFirmIds));
        }
        
        if(contacts!=null && !contacts.isEmpty()){
            for (Contact contact : contacts){
                String destAddr = AddrFetcherHelper.resolveDestAddrForContactInformation(contact, notificationType);
                if (StringUtils.isNotBlank(destAddr)) {
                    addrList.add(destAddr);
                }
            }
        }

        
        log.info("addrList: "+addrList);
        return addrList;
    }
    public void setMktPlatformProviderBO(IMarketPlatformProviderBO mktPlatformProviderBO) {
        this.mktPlatformProviderBO = mktPlatformProviderBO;
    }

	public IServiceOrderDao getServiceOrderDao() {
		return serviceOrderDao;
	}

	public void setServiceOrderDao(IServiceOrderDao serviceOrderDao) {
		this.serviceOrderDao = serviceOrderDao;
	}
    
    }

