package com.newco.marketplace.business.businessImpl.cache;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.management.InstanceNotFoundException;
import javax.management.MBeanServer;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectInstance;
import javax.management.ObjectName;

import org.apache.log4j.Logger;
import org.jboss.cache.Fqn;
import org.jboss.cache.config.Configuration;
import org.jboss.cache.factories.XmlConfigurationParser;
import org.jboss.cache.pojo.PojoCache;
import org.jboss.cache.pojo.PojoCacheException;
import org.jboss.cache.pojo.PojoCacheFactory;
import org.jboss.cache.pojo.jmx.PojoCacheJmxWrapper;
import org.jboss.cache.pojo.jmx.PojoCacheJmxWrapperMBean;
import org.jboss.mx.util.MBeanServerLocator;

import com.newco.marketplace.business.iBusiness.cache.ICacheManagerBO;
import com.newco.marketplace.business.iBusiness.ledger.ILedgerFacilityBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.ajax.AjaxCacheVO;
import com.newco.marketplace.dto.vo.cache.BuyerCachedResult;
import com.newco.marketplace.dto.vo.cache.BuyerDetailCountVO;
import com.newco.marketplace.dto.vo.cache.CachedResultVO;
import com.newco.marketplace.dto.vo.cache.ProviderCachedResult;
import com.newco.marketplace.dto.vo.cache.event.CacheEvent;
import com.newco.marketplace.dto.vo.ledger.ReceivedAmountVO;
import com.newco.marketplace.dto.vo.serviceorder.RoutedProvider;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSearchResultsVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.SurveyConstants;
import com.newco.marketplace.persistence.iDao.provider.ProviderDao;
import com.newco.marketplace.persistence.iDao.so.order.ServiceOrderDao;
import com.newco.marketplace.util.PropertiesUtils;

public class CacheManagerBOImpl implements ICacheManagerBO
{
	private ServiceOrderDao serviceOrderDao;
	private ILedgerFacilityBO accountingTransactionManagementImpl; 
	private ProviderDao providerDao;
	private static final Logger logger = Logger.getLogger(CacheManagerBOImpl.class);
	private static int _evictionInterval = Integer.MIN_VALUE;
	String configFile = System.getProperty("jboss.server.home.dir") + "/configuration/pojocache-config-service.xml";
	private String _clusterName = null;
	private String _cacheMode = null;
	private static Configuration conf;
	private PojoCache pcache;		

	private int getEvictionInterval() {
		if(_evictionInterval == Integer.MIN_VALUE) {
			_evictionInterval = Integer.parseInt(PropertiesUtils.getPropertyValue(Constants.AppPropConstants.EVICTION_INTERVAL));
		}
		return _evictionInterval;
	}

	private String getCacheMode() {
		if(_cacheMode == null) {
			_cacheMode = PropertiesUtils.getPropertyValue("cacheMode");
		}
		return _cacheMode;
	}

	private String getClusterName() {
		if(_clusterName == null) {
			_clusterName = PropertiesUtils.getPropertyValue("clusterName");
		}
		return _clusterName;
	}
	
	public CacheManagerBOImpl() {
		// Create cache when this bean is loaded
		loadCache();
	}

	/*@deprecated*/
	public void setUp(String clusterName) throws Exception {
		loadCache();
	}
	/*@deprecated*/
	public void tearDown() throws Exception {
		pcache.getCache().removeNode(new Fqn("/"));
		pcache.stop();

	}

	private void loadCache() {
		if(pcache == null){
			MBeanServer server = null;
			ObjectName on = null;
			try{
			// Commented the line below because wildfy is not supported 	
			// server = MBeanServerLocator.locateJBoss();
				
			server	= java.lang.management.ManagementFactory.getPlatformMBeanServer();
			} catch (Exception e){
				//if this is deployed in jboss, this exception
				logger.fatal("", e);
				logger.error("", e);
			}
			try {
				on = new ObjectName("jboss.cache:service=PojoCache");
				ObjectInstance oi = server.getObjectInstance(on);
				
				//check if pojocache exists as an mbean
				if(oi != null){
					PojoCacheJmxWrapperMBean cacheWrapper = 
						(PojoCacheJmxWrapperMBean) MBeanServerInvocationHandler.newProxyInstance(
								server, on, PojoCacheJmxWrapperMBean.class, false);
					pcache = cacheWrapper.getPojoCache();
				} //an InstanceNotFoundException will be thrown if the above fails. Let's catch it
				
			} catch (PojoCacheException pce) {
				logger.error("", pce);
			} catch (InstanceNotFoundException infe){
				logger.warn(infe.getMessage());
				logger.warn("calling registerCacheMBean");
				registerCacheMBean(server, on);
			} catch (Exception e){
				logger.error("", e);
			}
		}
	}

	private void registerCacheMBean(MBeanServer server, ObjectName on){
		XmlConfigurationParser parser = new XmlConfigurationParser();
		try{
		conf = parser.parseFile(configFile);
		conf.setClusterName(getClusterName());
		conf.setCacheMode(getCacheMode());
		logger.debug("Cluster name: " + conf.getClusterName());
		logger.debug("Set cache mode: " + getCacheMode());
		pcache = PojoCacheFactory.createCache(conf, false);
		PojoCacheJmxWrapperMBean wrapper = new PojoCacheJmxWrapper(
				pcache);
		server.registerMBean(wrapper, on);
		wrapper.create();
		wrapper.start();
		}catch (PojoCacheException pce) {
			logger.error(pce.getMessage());
			logger.debug(pce.getCause());
		}catch (Exception e){
			logger.error(e.getMessage());
			logger.debug(e.getCause());
		}
	}	
	public CachedResultVO getAllSOMCounts(AjaxCacheVO ajaxCacheVo) throws BusinessServiceException 
	 {
		 CachedResultVO cachedResult;

		 cachedResult = getSummaryCounts(ajaxCacheVo);
		 cachedResult = getDetailedCounts(ajaxCacheVo);
		 cachedResult = getDashboardData(ajaxCacheVo);
		 
		 return cachedResult;
	 }

	public CachedResultVO getDetailedCounts(AjaxCacheVO ajaxCacheVo)
			throws BusinessServiceException {
		CachedResultVO cachedResult = null;
		String vendorIdStr = null, resourceIdStr = null;


		int vendorId;
		int resourceId;
		String entityType = null;

		// Assume the Cache is already instantiated. If not,
		// Step 0: Instantiate the cache
		try {
			if (pcache == null)
				loadCache();

			// Step1: convert the Integer values to String as the Cache Tree needs the string values to lookup nodes
			vendorId = ajaxCacheVo.getCompanyId();
			resourceId = ajaxCacheVo.getVendBuyerResId();
			entityType = ajaxCacheVo.getRoleType();

			if (vendorId != 0) {
				vendorIdStr = String.valueOf(vendorId);
			}

			if (resourceId > 0) {
				resourceIdStr = String.valueOf(resourceId);
			}
			cachedResult = getDetailedCounts(vendorIdStr, resourceIdStr,
					entityType, ajaxCacheVo);
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.debug(e.getCause());
			throw new BusinessServiceException(
					e.getMessage());
		}

		return cachedResult;
	}

	private CachedResultVO getDetailedCounts(String vendorIdStr,
			String resourceIdStr, String entityType, AjaxCacheVO ajaxCacheVo) throws Exception {
		CachedResultVO cachedResult = null;
		CachedResultVO cachedResultCompany = null;
		CachedResultVO cachedResultUser = null;
		int caseCondition = 0;
		HashMap detailedMapCompany = null, detailedMapUser = null;
		String pathToCache = null;
		ArrayList buyerList = null; 
		ArrayList providerList = null;

		// Step 0: Determine the EntityType
		if (entityType == SurveyConstants.ENTITY_BUYER)
			caseCondition = 1;
		else if (entityType == SurveyConstants.ENTITY_PROVIDER)
			caseCondition = 2;
		logger.debug("The caseCondition is: " + caseCondition);
		switch (caseCondition) {
		case 1:// for Buyer
			// Step 1: if resourceId is null, locate Company Level node directly
			if (resourceIdStr == null) {
				cachedResultCompany = (BuyerCachedResult) locateNodeInCache(
						vendorIdStr, null, entityType);
				
				// check the createTime for the node and see if it is past the 
				// eviction interval; if yes, then get it from the database
				if (cachedResultCompany != null && isEvictNode(cachedResultCompany)) {
					cachedResultCompany = null;
					// detach this node from cache
					detachNodeFromCache(vendorIdStr, null, entityType);
				}
		
				// if node doesn't exist in cache OR node exists, but
				// detailedCount attrib in node does not exist.
				if ((cachedResultCompany == null)
						|| (cachedResultCompany.getDetailedCount() == null)) {
					// call to DAO to get Detailed Company Level counts for Buyer
					buyerList = serviceOrderDao.getDetailedCountsBuyer(ajaxCacheVo); 
					detailedMapCompany = convertBuyerList(buyerList);

					// Create the node, add the DetailedCount to the Node, attach node to tree
					if (cachedResultCompany == null)
					{
						cachedResultCompany = new BuyerCachedResult();
						cachedResultCompany.setDetailedCount(detailedMapCompany);
						// add the cachedResult to the tree
						pathToCache = SurveyConstants.ENTITY_BUYER + "/" + vendorIdStr; 
						addNodeToCache(pathToCache,cachedResultCompany);
					}
					else // if node exists, attach detailed count to node
					// Do not add the object back to the Cache as this should
					// replicate automatically because it is POJO cache.
					{
						cachedResultCompany.setDetailedCount(detailedMapCompany);
					}
				}
				return cachedResultCompany;
			}// Company Level counts
			else if (resourceIdStr != null) // Step2: If resourceId is present,
			// locate User level nodes, child
			// node B1/U1
			{
				cachedResultUser = (BuyerCachedResult) locateNodeInCache(
						vendorIdStr, resourceIdStr, entityType);

				// check the createTime for the node and see if it is past the 
				// eviction interval; if yes, then get it from the database
				if (cachedResultUser != null && isEvictNode(cachedResultUser)) {
					cachedResultUser = null;
					// detach this node from cache
					detachNodeFromCache(vendorIdStr, null, entityType);
				}
				
				// Step 3: if B1/UcachedResultUsers missing(expired or never
				// created), locate the parent node B1
				if (cachedResultUser == null) {
					cachedResultCompany = (BuyerCachedResult) locateNodeInCache(
							vendorIdStr, null, entityType);

					// check the createTime for the node and see if it is past the 
					// eviction interval; if yes, then get it from the database
					if (cachedResultCompany != null && isEvictNode(cachedResultCompany)) {
						cachedResultCompany = null;
						// detach this node from cache
						detachNodeFromCache(vendorIdStr, null, entityType);
					}
					
					// if B1 is null-- parent node doesn't exist in cache, no
					// need to check attribs of cache node
					if (cachedResultCompany == null) {
						// call to DAO to get Detailed Company Level counts for Buyer
						buyerList = serviceOrderDao.getDetailedCountsBuyer(ajaxCacheVo); 
						detailedMapCompany = convertBuyerList(buyerList);

						// Add the DetailedCount to the Node
						cachedResultCompany = new BuyerCachedResult();
						cachedResultCompany
								.setDetailedCount(detailedMapCompany);

						// add the cachedResult to the tree
						pathToCache = SurveyConstants.ENTITY_BUYER  + "/" + vendorIdStr;
						addNodeToCache(pathToCache,cachedResultCompany);
					} else if (cachedResultCompany != null) // B1 exists, create B1/U1 node
					{
						// call to DAO to get Detailed User Level counts for Buyer
						buyerList = serviceOrderDao.getDetailedCountsBuyer(ajaxCacheVo); 
						detailedMapUser = convertBuyerList(buyerList);

						// Add the DetailedCount to the Node
						cachedResultUser = new BuyerCachedResult();
						cachedResultUser.setDetailedCount(detailedMapUser);

						pathToCache = SurveyConstants.ENTITY_BUYER  + "/" + vendorIdStr + "/"
								+ resourceIdStr;
						addNodeToCache(pathToCache,cachedResultUser);
					}
				} 
				else if (cachedResultUser != null) { // B1/U1 exists, verify the DetailedCount attrib of B1/U1
					if (cachedResultUser.getDetailedCount() == null) {
						// call to DAO to get Detailed User Level counts for Buyer
						buyerList = serviceOrderDao.getDetailedCountsBuyer(ajaxCacheVo); 
						detailedMapUser = convertBuyerList(buyerList);

						// Add the DetailedCount to the Node
						cachedResultUser.setDetailedCount(detailedMapUser);
					}
				}
				return cachedResultUser;
			}// User Level counts
			break;

		case 2: // for Provider
			// Step 1: if resourceId is null, locate Company Level node directly
			if (resourceIdStr == null) {
				cachedResultCompany = (ProviderCachedResult) locateNodeInCache(
						vendorIdStr, null, entityType);
				
				// check the createTime for the node and see if it is past the 
				// eviction interval; if yes, then get it from the database
				if (cachedResultCompany != null && isEvictNode(cachedResultCompany)) {
					cachedResultCompany = null;
					// detach this node from cache
					detachNodeFromCache(vendorIdStr, null, entityType);
				}

				// if node doesn't exist in cache OR node exists, but
				// detailedCount attrib in node does not exist.
				if ((cachedResultCompany == null)
						|| (cachedResultCompany.getDetailedCount() == null)) {
					//call to DAO to get Detailed Company Level counts for Buyer
					providerList = serviceOrderDao.getDetailedCountsProvider(ajaxCacheVo); 
					detailedMapCompany = convertProviderList(providerList);

					// Create the node, add the DetailedCount to the Node, attach node to tree
					if (cachedResultCompany == null)
					{
						cachedResultCompany = new ProviderCachedResult();
						cachedResultCompany.setDetailedCount(detailedMapCompany);
						// add the cachedResult to the tree
						pathToCache = SurveyConstants.ENTITY_PROVIDER + "/" + vendorIdStr;
						addNodeToCache(pathToCache,cachedResultCompany);
					}
					else // if node exists, attach detailed count to node
					// Do not add the object back to the Cache as this should
					// replicate automatically because it is POJO cache.
					{
						cachedResultCompany.setDetailedCount(detailedMapCompany);
					}
				}
				return cachedResultCompany;
			}// Company Level counts
			else if (resourceIdStr != null) // Step2: If resourceId is present,
			// locate User level nodes, child
			// node B1/U1
			{
				cachedResultUser = (ProviderCachedResult) locateNodeInCache(
						vendorIdStr, resourceIdStr, entityType);
				
				// check the createTime for the node and see if it is past the 
				// eviction interval; if yes, then get it from the database
				if (cachedResultUser != null && isEvictNode(cachedResultUser)) {
					cachedResultUser = null;
					// detach this node from cache
					detachNodeFromCache(vendorIdStr, null, entityType);
				}

				// Step 3: if B1/UcachedResultUsers missing(expired or never
				// created), locate the parent node B1
				if (cachedResultUser == null) {
					cachedResultCompany = (ProviderCachedResult) locateNodeInCache(
							vendorIdStr, null, entityType);
					
					// check the createTime for the node and see if it is past the 
					// eviction interval; if yes, then get it from the database
					if (cachedResultCompany != null && isEvictNode(cachedResultCompany)) {
						cachedResultCompany = null;
						// detach this node from cache
						detachNodeFromCache(vendorIdStr, null, entityType);
					}

					// if B1 is null-- parent node doesn't exist in cache, no
					// need to check attribs of cache node
					if (cachedResultCompany == null) {
						// call to DAO to get Detailed Company Level
						providerList = serviceOrderDao.getDetailedCountsProvider(ajaxCacheVo); 
						detailedMapCompany = convertProviderList(providerList);

						// Add the DetailedCount to the Ncode
						cachedResultCompany = new ProviderCachedResult();
						cachedResultCompany
								.setDetailedCount(detailedMapCompany);

						// add the cachedResult to the tree
						pathToCache = SurveyConstants.ENTITY_PROVIDER + "/" + vendorIdStr;
						addNodeToCache(pathToCache,cachedResultCompany);
					} else if (cachedResultCompany != null) // B1 exists, create  B1/U1 node
					{
						// call to DAO to get Detailed User Level counts for Buyer
						providerList = serviceOrderDao.getDetailedCountsProvider(ajaxCacheVo); 
						detailedMapUser = convertProviderList(providerList);

						// Add the DetailedCount to the Node
						cachedResultUser = new ProviderCachedResult();
						cachedResultUser.setDetailedCount(detailedMapUser);

						pathToCache = SurveyConstants.ENTITY_PROVIDER + "/" + vendorIdStr + "/"
								+ resourceIdStr;
						addNodeToCache(pathToCache,cachedResultUser);
					}
				} else if (cachedResultUser != null) // B1/U1 exists, verify
				// the DetailedCount attrib of B1/U1
				{
					if (cachedResultUser.getDetailedCount() == null) {
						// call to DAO to get Detailed User Level counts for Buyer
						providerList = serviceOrderDao.getDetailedCountsProvider(ajaxCacheVo);  
						detailedMapUser = convertProviderList(providerList);

						// Add the DetailedCount to the Node
						cachedResultUser.setDetailedCount(detailedMapUser);

						// Do not add the object to the Cache as this should
						// replicate automatically because it is POJO cache.
					}
				}
				return cachedResultUser;
			}// User Level counts
			break;
		}// switch
		return cachedResult;
	}

	public CachedResultVO getDashboardData(AjaxCacheVO ajaxCacheVo) throws BusinessServiceException 
	 {
			CachedResultVO cachedResult;
			String vendorIdStr = null, resourceIdStr = null;

			int vendorId;
			int resourceId;
			String entityType = null;

			// Assume the Cache is already instantiated. If not,
			// Step 0: Instantiate the cache
			try {
				if (pcache == null)
					loadCache();

				// Step1: convert the Integer values to String as the Cache Tree
				// needs the string values to lookup nodes
				vendorId = ajaxCacheVo.getCompanyId();
				resourceId = ajaxCacheVo.getVendBuyerResId();
				entityType = ajaxCacheVo.getRoleType();

				if (vendorId != 0) {
					vendorIdStr = String.valueOf(vendorId);
				}

				if (resourceId > 0) {
					resourceIdStr = String.valueOf(resourceId);
				}
				cachedResult = getDashboardData(vendorIdStr, resourceIdStr,
						entityType, ajaxCacheVo);
			} catch (Exception e) {
				throw new BusinessServiceException(e);
			}

			return cachedResult;
	 }

	
	
	
	private CachedResultVO getDashboardData(String vendorIdStr, String resourceIdStr,
			String entityType, AjaxCacheVO ajaxCacheVo) throws Exception
	{
		CachedResultVO cachedResult = null;
		int caseCondition =0;
		BuyerCachedResult cachedResultCompanyBuyer = null, cachedResultUserBuyer = null;
		ProviderCachedResult cachedResultCompanyProvider = null, cachedResultUserProvider = null;	
		HashMap summaryMapCompany = null, summaryMapUser = null;
		double currBalanceCompany = 0.0, currBalanceUser = 0.0;
		double availBalanceCompany = 0.0, availBalanceUser = 0.0;
		double totalReceivedAmountCompany = 0.0, totalReceivedAmountUser = 0.0;
		ReceivedAmountVO receivedAmountVo = null;
		String pathToCache = null;
		
		// Step 0: Determine the EntityType
		if (entityType == SurveyConstants.ENTITY_BUYER)
			caseCondition = 1;
		else if (entityType == SurveyConstants.ENTITY_PROVIDER)
			caseCondition = 2;

		switch (caseCondition) {
		case 1:// for Buyer
			//Step 1: if resourceId is null, locate Company Level node directly
			if (resourceIdStr == null)
			{
				cachedResultCompanyBuyer = (BuyerCachedResult) locateNodeInCache(
						vendorIdStr, null, entityType);
				logger.debug("//Step 1: if resourceId is null, locate Company Level node directly");
				// check the createTime for the node and see if it is past the 
				// eviction interval; if yes, then get it from the database
				logger.debug("Eviction Interval " + Integer.parseInt(PropertiesUtils.getPropertyValue(Constants.AppPropConstants.EVICTION_INTERVAL)));
				if (cachedResultCompanyBuyer != null && isEvictNode(cachedResultCompanyBuyer)) {
					cachedResultCompanyBuyer = null;
					// detach this node from cache
					logger.debug("clearing the cache");
					detachNodeFromCache(vendorIdStr, null, entityType);
				}				

				// if node doesn't exist in cache 
				if (cachedResultCompanyBuyer == null)
				{
					logger.debug("if node doesn't exist in cache ");
					// Create the node, add the Dashboard Count & Summary Count to the Node,
					// attach node to tree
					cachedResultCompanyBuyer = new BuyerCachedResult();

					summaryMapCompany =	serviceOrderDao.getSummaryCountsBuyer(ajaxCacheVo);
					availBalanceCompany = accountingTransactionManagementImpl.getAvailableBalance(ajaxCacheVo);
					currBalanceCompany = accountingTransactionManagementImpl.getCurrentBalance(ajaxCacheVo);
					
					logger.debug("Retriving the summary counts , available and current balance");
					
					cachedResultCompanyBuyer.setSummaryCount(summaryMapCompany);
					cachedResultCompanyBuyer.setAvailableBalance(availBalanceCompany);
					cachedResultCompanyBuyer.setCurrentBalance(currBalanceCompany);
					
					logger.debug("availBalanceCompany " + availBalanceCompany);
					logger.debug("currBalanceCompany " + currBalanceCompany);
					
					// add the BuyerCachedResult to the tree
					pathToCache = "BUYER" + "/" + vendorIdStr;
					addNodeToCache(pathToCache,cachedResultCompanyBuyer);
					
					logger.debug(" Adding the value to the cache");
				}
				//if node exists, but all the dashboard Data in the node does not exist.
				else if (getDashboardBuyerData(cachedResultCompanyBuyer) == false)
				{
					logger.debug("if node exists, but all the dashboard Data in the node does not exist.");
					//attach dashboard count & summary count to node
					// Do not add the object back to the Cache as this should
					// replicate automatically because it is POJO cache.

					// first verify if summary counts is present, if not, get that as well
								
					if (cachedResultCompanyBuyer.getSummaryCount() == null)
					{
						summaryMapCompany =	serviceOrderDao.getSummaryCountsBuyer(ajaxCacheVo);
						cachedResultCompanyBuyer.setSummaryCount(summaryMapCompany);
					}
					
					availBalanceCompany = accountingTransactionManagementImpl.getAvailableBalance(ajaxCacheVo);
					currBalanceCompany = accountingTransactionManagementImpl.getCurrentBalance(ajaxCacheVo);
					
					logger.debug("availBalanceCompany " + availBalanceCompany);
					logger.debug("currBalanceCompany " + currBalanceCompany);
					

					cachedResultCompanyBuyer.setAvailableBalance(availBalanceCompany);
					cachedResultCompanyBuyer.setCurrentBalance(currBalanceCompany);
				}
				//if node exists, and all the dashboard Data in the node exists.
				else if (getDashboardBuyerData(cachedResultCompanyBuyer) == true)
				{
					logger.debug("if node exists, and all the dashboard Data in the node exists.");
					
					availBalanceCompany = accountingTransactionManagementImpl.getAvailableBalance(ajaxCacheVo);
					currBalanceCompany = accountingTransactionManagementImpl.getCurrentBalance(ajaxCacheVo);
					
					logger.debug("availBalanceCompany " + availBalanceCompany);
					logger.debug("currBalanceCompany " + currBalanceCompany);
					
					cachedResultCompanyBuyer.setAvailableBalance(availBalanceCompany);
					cachedResultCompanyBuyer.setCurrentBalance(currBalanceCompany);
					return cachedResultCompanyBuyer;
				}
				return cachedResultCompanyBuyer;
			}// Company Level counts
			else if (resourceIdStr != null) //If resourceId is present,
			// locate User level nodes, child node B1/U1
			{
				logger.debug("resourceIdStr != null");
				
				cachedResultUserBuyer = (BuyerCachedResult) locateNodeInCache(
						vendorIdStr, resourceIdStr, entityType);
				
				// check the createTime for the node and see if it is past the 
				// eviction interval; if yes, then get it from the database
				logger.debug("Eviction Interval " + Integer.parseInt(PropertiesUtils.getPropertyValue(Constants.AppPropConstants.EVICTION_INTERVAL)));
				if (cachedResultUserBuyer != null && isEvictNode(cachedResultUserBuyer)) {
					cachedResultUserBuyer = null;
					// detach this node from cache
					logger.debug("Clearing the cache");
					detachNodeFromCache(vendorIdStr, null, entityType);
				}				

				// Step 3: if B1/U1 is missing(expired or never
				// created), verify if the parent node B1 is present
				if (cachedResultUserBuyer == null)
				{
					logger.debug("// Step 3: if B1/U1 is missing(expired or never created), verify if the parent node B1 is present");
					cachedResultCompanyBuyer = (BuyerCachedResult) locateNodeInCache(
							vendorIdStr, null, entityType);
					
					// check the createTime for the node and see if it is past the 
					// eviction interval; if yes, then get it from the database
					logger.debug("Eviction Interval " + Integer.parseInt(PropertiesUtils.getPropertyValue(Constants.AppPropConstants.EVICTION_INTERVAL)));
					if (cachedResultCompanyBuyer != null && isEvictNode(cachedResultCompanyBuyer)) {
						cachedResultCompanyBuyer = null;
						// detach this node from cache
						logger.debug("Clearing the cache");
						detachNodeFromCache(vendorIdStr, null, entityType);
					}

					// if B1 is null-- parent node doesn't exist in cache
					if (cachedResultCompanyBuyer == null)
					{
						logger.debug("// if B1 is null-- parent node doesn't exist in cache");
						
						// Create the node, add the Dashboard Count & Summary Count to the Node,
						// attach parent node B1 to the tree
						
						cachedResultCompanyBuyer = new BuyerCachedResult();

						summaryMapCompany =	serviceOrderDao.getSummaryCountsBuyer(ajaxCacheVo);
						
						availBalanceCompany = accountingTransactionManagementImpl.getAvailableBalance(ajaxCacheVo);
						currBalanceCompany = accountingTransactionManagementImpl.getCurrentBalance(ajaxCacheVo);
						
						logger.debug("availBalanceCompany " + availBalanceCompany);
						logger.debug("currBalanceCompany " + currBalanceCompany);
						
						cachedResultCompanyBuyer.setSummaryCount(summaryMapCompany);
						cachedResultCompanyBuyer.setAvailableBalance(availBalanceCompany);
						cachedResultCompanyBuyer.setCurrentBalance(currBalanceCompany);
						
						// add the BuyerCachedResult to the tree
						pathToCache = "BUYER" + "/" + vendorIdStr;
						addNodeToCache(pathToCache,cachedResultCompanyBuyer);
						logger.debug("set the values to the cache with value " + pathToCache);
						
						//SC-modified
						return cachedResultCompanyBuyer;
					}
					else if (cachedResultCompanyBuyer != null) // B1 exists, create B1/U1 node
					{
						
						logger.debug("// B1 exists, create B1/U1 node");
						// Create the node, add the Dashboard Count & Summary Count to the Node,
						// attach parent node B1 to the tree
						cachedResultUserBuyer = new BuyerCachedResult();

						summaryMapUser =	serviceOrderDao.getSummaryCountsBuyer(ajaxCacheVo);
						availBalanceUser = accountingTransactionManagementImpl.getAvailableBalance(ajaxCacheVo);
						currBalanceUser = accountingTransactionManagementImpl.getCurrentBalance(ajaxCacheVo);
						
						logger.debug("availBalanceUser " + availBalanceUser);
						logger.debug("currBalanceUser " + currBalanceUser);
						
						cachedResultUserBuyer.setSummaryCount(summaryMapUser);
						cachedResultUserBuyer.setAvailableBalance(availBalanceUser);
						cachedResultUserBuyer.setCurrentBalance(currBalanceUser);
						
						// add the BuyerCachedResult to the tree
						pathToCache = "BUYER" + "/" + vendorIdStr + "/" + resourceIdStr;
						addNodeToCache(pathToCache,cachedResultUserBuyer);
						
						logger.debug("adding values to the cache object " + pathToCache);
					}
				}
				else if (cachedResultUserBuyer != null) 
				// if B1/U1 exists, verify the Dashboard Count attribs of B1/U1
				{
					logger.debug("if B1/U1 exists, verify the Dashboard Count attribs of B1/U1");
					if (cachedResultUserBuyer.getSummaryCount() == null)
					{
						summaryMapUser = serviceOrderDao.getSummaryCountsBuyer(ajaxCacheVo);
						cachedResultUserBuyer.setSummaryCount(summaryMapUser);
					}
					
					availBalanceUser = accountingTransactionManagementImpl.getAvailableBalance(ajaxCacheVo);
					currBalanceUser = accountingTransactionManagementImpl.getCurrentBalance(ajaxCacheVo);

					cachedResultUserBuyer.setAvailableBalance(availBalanceUser);
					cachedResultUserBuyer.setCurrentBalance(currBalanceUser);
					
					logger.debug("availBalanceUser " + availBalanceUser);
					logger.debug("currBalanceUser " + currBalanceUser);

					//attach dashboard count & summary count to node
					// Do not add the object back to the Cache as this should
					// replicate automatically because it is POJO cache.
				}
				return cachedResultUserBuyer;
			}//user level node
			break;
			
		case 2:// for Provider
			//Step 1: if resourceId is null, locate Company Level node directly
			if (resourceIdStr == null)
			{
				cachedResultCompanyProvider = (ProviderCachedResult) locateNodeInCache(
						vendorIdStr, null, entityType);
				
				// check the createTime for the node and see if it is past the 
				// eviction interval; if yes, then get it from the database
				if (cachedResultCompanyProvider != null && isEvictNode(cachedResultCompanyProvider)) {
					cachedResultCompanyProvider = null;
					// detach this node from cache
					detachNodeFromCache(vendorIdStr, null, entityType);
				}

				// if node doesn't exist in cache 
				if (cachedResultCompanyProvider == null)
				{
					// Create the node, add the Dashboard Count & Summary Count to the Node,
					// attach node to tree
					cachedResultCompanyProvider = new ProviderCachedResult();

					summaryMapCompany =	serviceOrderDao.getSummaryCountsProvider(ajaxCacheVo);
					availBalanceCompany = accountingTransactionManagementImpl.getAvailableBalance(ajaxCacheVo);

					receivedAmountVo = providerDao.getReceivedAmount(ajaxCacheVo);
					if (receivedAmountVo != null)
						totalReceivedAmountCompany = receivedAmountVo.getTotalReceived(); 
					
					cachedResultCompanyProvider.setSummaryCount(summaryMapCompany);
					cachedResultCompanyProvider.setAvailableBalance(availBalanceCompany);
					cachedResultCompanyProvider.setTotalReceivedAmount(totalReceivedAmountCompany);
					
					// add the ProviderCachedResult to the tree
					pathToCache = "PROVIDER" + "/" + vendorIdStr;
					addNodeToCache(pathToCache,cachedResultCompanyProvider);
				}
				//if node exists, but all the dashboard Data in the node does not exist.
				else if (getDashboardProviderData(cachedResultCompanyProvider) == false)
				{

					// first verify if summary counts is present, if not, get that as well
					if (cachedResultCompanyProvider.getSummaryCount() == null)
					{
						summaryMapCompany =	serviceOrderDao.getSummaryCountsProvider(ajaxCacheVo);
						cachedResultCompanyProvider.setSummaryCount(summaryMapCompany);
					}
					
					availBalanceCompany = accountingTransactionManagementImpl.getAvailableBalance(ajaxCacheVo);

					receivedAmountVo = providerDao.getReceivedAmount(ajaxCacheVo);
					if (receivedAmountVo != null)
						totalReceivedAmountCompany = receivedAmountVo.getTotalReceived(); 

					
					cachedResultCompanyProvider.setAvailableBalance(availBalanceCompany);
					cachedResultCompanyProvider.setTotalReceivedAmount(totalReceivedAmountCompany);

					//attach dashboard count & summary count to node
					// Do not add the object back to the Cache as this should
					// replicate automatically because it is POJO cache.
				}
				//if node exists, and all the dashboard Data in the node exists.
				else if (getDashboardProviderData(cachedResultCompanyProvider) == true)
				{
					return cachedResultCompanyProvider;
				}
				return cachedResultCompanyProvider;
			}// Company Level counts
			else if (resourceIdStr != null) //If resourceId is present,
			// locate User level nodes, child node P1/U1
			{
				cachedResultUserProvider = (ProviderCachedResult) locateNodeInCache(
						vendorIdStr, resourceIdStr, entityType);
				
				// check the createTime for the node and see if it is past the 
				// eviction interval; if yes, then get it from the database
				if (cachedResultUserProvider != null && isEvictNode(cachedResultUserProvider)) {
					cachedResultUserProvider = null;
					// detach this node from cache
					detachNodeFromCache(vendorIdStr, null, entityType);
				}

				// Step 3: if P1/U1 is missing(expired or never
				// created), verify if the parent node P1 is present
				if (cachedResultUserProvider == null)
				{
					cachedResultCompanyProvider = (ProviderCachedResult) locateNodeInCache(
							vendorIdStr, null, entityType);
					
					// check the createTime for the node and see if it is past the 
					// eviction interval; if yes, then get it from the database
					if (cachedResultCompanyProvider != null && isEvictNode(cachedResultCompanyProvider)) {
						cachedResultCompanyProvider = null;
						// detach this node from cache
						detachNodeFromCache(vendorIdStr, null, entityType);
					}

					// if P1 is null-- parent node doesn't exist in cache
					if (cachedResultCompanyProvider == null)
					{
						// Create the node, add the Dashboard Count & Summary Count to the Node,
						// attach parent node P1 to the tree
						cachedResultCompanyProvider = new ProviderCachedResult();

						summaryMapCompany =	serviceOrderDao.getSummaryCountsProvider(ajaxCacheVo);
						availBalanceCompany = accountingTransactionManagementImpl.getAvailableBalance(ajaxCacheVo);
						receivedAmountVo = providerDao.getReceivedAmount(ajaxCacheVo);
						if (receivedAmountVo != null)
							totalReceivedAmountCompany = receivedAmountVo.getTotalReceived(); 

						cachedResultCompanyProvider.setSummaryCount(summaryMapCompany);
						cachedResultCompanyProvider.setAvailableBalance(availBalanceCompany);
						cachedResultCompanyProvider.setTotalReceivedAmount(totalReceivedAmountCompany);
						
						// add the ProviderCachedResult to the tree
						pathToCache = "PROVIDER" + "/" + vendorIdStr;
						addNodeToCache(pathToCache,cachedResultCompanyProvider);
						
						//SC-modified
						return cachedResultCompanyProvider;
					}
					else if (cachedResultCompanyProvider != null) // P1 exists, create P1/U1 node
					{
						// Create the node, add the Dashboard Count & Summary Count to the Node,
						// attach parent node B1 to the tree
						cachedResultUserProvider = new ProviderCachedResult();

						summaryMapUser =	serviceOrderDao.getSummaryCountsProvider(ajaxCacheVo);
						availBalanceUser = accountingTransactionManagementImpl.getAvailableBalance(ajaxCacheVo);
						receivedAmountVo = providerDao.getReceivedAmount(ajaxCacheVo);
						if (receivedAmountVo != null)
							totalReceivedAmountUser = receivedAmountVo.getTotalReceived(); 
						
						cachedResultUserProvider.setSummaryCount(summaryMapUser);
						cachedResultUserProvider.setAvailableBalance(availBalanceUser);
						cachedResultUserProvider.setTotalReceivedAmount(totalReceivedAmountUser);
						
						// add the BuyerCachedResult to the tree
						pathToCache = "PROVIDER" + "/" + vendorIdStr + "/" + resourceIdStr;
						addNodeToCache(pathToCache,cachedResultUserProvider);
					}
				}
				else if (cachedResultUserProvider != null) 
				// if P1/U1 exists, verify the Dashboard Count attribs of P1/U1
				{
					if (cachedResultUserProvider.getSummaryCount() == null)
					{
						summaryMapUser = serviceOrderDao.getSummaryCountsProvider(ajaxCacheVo);
						cachedResultUserProvider.setSummaryCount(summaryMapUser);
					}

					//attach dashboard count & summary count to node
					availBalanceUser = accountingTransactionManagementImpl.getAvailableBalance(ajaxCacheVo);
					receivedAmountVo = providerDao.getReceivedAmount(ajaxCacheVo);
					if (receivedAmountVo != null)
						totalReceivedAmountUser = receivedAmountVo.getTotalReceived(); 

					cachedResultUserProvider.setAvailableBalance(availBalanceUser);
					cachedResultUserProvider.setTotalReceivedAmount(totalReceivedAmountUser);

					// Do not add the object back to the Cache as this should
					// replicate automatically because it is POJO cache.
				}
				return cachedResultUserProvider;
			}//user level node
			break;
		}
		return cachedResult;
	}//method

	 
	private boolean getDashboardBuyerData(BuyerCachedResult buyerCache)
	{
		double availBalance = 0.0;
		double currentBalance = 0.0;
		
		availBalance = buyerCache.getAvailableBalance();
		currentBalance = buyerCache.getCurrentBalance();
		
		HashMap summaryCountMap = buyerCache.getSummaryCount();
		
		if ((availBalance != 0.0) && (currentBalance != 0.0) && (summaryCountMap != null))
			return true;
		else	
			return false;
	}
	
	
	private boolean getDashboardProviderData(ProviderCachedResult providerCache)
	{
		double availBalance = 0.0;
		double receivedAmount = 0.0;
		
		availBalance = providerCache.getAvailableBalance();
		receivedAmount = providerCache.getTotalReceivedAmount();
		
		HashMap summaryCountMap = providerCache.getSummaryCount();
		
		if ((availBalance != 0.0) && (receivedAmount != 0.0) && (summaryCountMap != null))
			return true;
		else	
			return false;
	}
	
	
	
	private HashMap convertBuyerList(ArrayList buyerList) throws Exception
	{
		HashMap buyerMap = new HashMap();

		Iterator it = buyerList.iterator();
		while(it.hasNext())
		{
			ServiceOrderSearchResultsVO vo = (ServiceOrderSearchResultsVO) it.next();
			String soId = vo.getSoId();
			int sentToCount = vo.getProviderCounts();
			int rejectCount = vo.getRejectedCounts();
			int condAcceptCount = vo.getCondCounts();

			Double spendLimitLabor = vo.getSpendLimit();
			Double spendLimitParts = vo.getSpendLimitParts();
			double spendLimitTotal = 0.0; 
			if(spendLimitLabor != null){
				spendLimitTotal = spendLimitLabor.doubleValue();
			}
			if(spendLimitParts != null){
				spendLimitTotal = spendLimitTotal + spendLimitParts.doubleValue();
			}

			BuyerDetailCountVO buyerVO = new BuyerDetailCountVO();
			buyerVO.setSentProviders(sentToCount);
			buyerVO.setConditionalAccept(condAcceptCount);
			buyerVO.setRejected(rejectCount);
			buyerVO.setSpendLimit(spendLimitTotal);
			
			buyerMap.put(soId, buyerVO);
		}
		return buyerMap;
	}


    private HashMap<String, BuyerDetailCountVO> convertProviderList(ArrayList buyerList) throws Exception 
    { 
            HashMap<String, BuyerDetailCountVO> providerMap = new HashMap<String, BuyerDetailCountVO>(); 

            Iterator it = buyerList.iterator(); 
            while(it.hasNext()) 
            { 
                    ServiceOrderSearchResultsVO vo = (ServiceOrderSearchResultsVO) it.next(); 
                    String soId = vo.getSoId(); 
                    Double spendLimitLabor = vo.getSpendLimit();
        			Double spendLimitParts = vo.getSpendLimitParts();
        			double spendLimitTotal = 0.0; 
        			if(spendLimitLabor != null){
        				spendLimitTotal = spendLimitLabor.doubleValue();
        			}
        			if(spendLimitParts != null){
        				spendLimitTotal = spendLimitTotal + spendLimitParts.doubleValue();
        			}
                    
                    BuyerDetailCountVO buyerVO = new BuyerDetailCountVO(); 
                    buyerVO.setSpendLimit(new Double(spendLimitTotal)); 
                    providerMap.put(soId, buyerVO); 
            } 
            return providerMap; 
    } 

	public CachedResultVO getSummaryCounts(AjaxCacheVO ajaxCacheVo)
			throws BusinessServiceException {
		CachedResultVO cachedResult = null;
		String vendorIdStr = null, resourceIdStr = null;

		int vendorId;
		int resourceId;
		String entityType = null;

		// Assume the Cache is already instantiated. If not,
		// Step 0: Instantiate the cache
		try {
			if (pcache == null)
				loadCache();

			// Step1: convert the Integer values to String as the Cache Tree
			// needs
			// the string values to lookup nodes
			vendorId = ajaxCacheVo.getCompanyId();
			resourceId = ajaxCacheVo.getVendBuyerResId();
			entityType = ajaxCacheVo.getRoleType();

			if (vendorId != 0) {
				vendorIdStr = String.valueOf(vendorId);
			}

			if (resourceId > 0) {
				resourceIdStr = String.valueOf(resourceId);
			}
			cachedResult = getSummaryCounts(vendorIdStr, resourceIdStr,
					entityType, ajaxCacheVo);
		} catch (Exception e) {
			logger.error("Exception occurred in getSummaryCounts");
			logger.error(e.getMessage());
			logger.debug(e.getCause());
		}

		return cachedResult;
	}

	private CachedResultVO getSummaryCounts(String vendorIdStr,
			String resourceIdStr, String entityType, AjaxCacheVO ajaxCacheVo) throws Exception {
		CachedResultVO cachedResult = null;
		CachedResultVO cachedResultCompany = null;
		CachedResultVO cachedResultUser = null;
		int caseCondition = 0;
		HashMap summaryMapCompany = null;
		HashMap summaryMapUser = null;
		String pathToCache = null;

		// Step 0: Determine the EntityType
		if (entityType == SurveyConstants.ENTITY_BUYER)
			caseCondition = 1;
		else if (entityType == SurveyConstants.ENTITY_PROVIDER)
			caseCondition = 2;

		switch (caseCondition) {
		case 1:// for Buyer
			// Step 1: if resourceId is null, locate Company Level node directly
			if (resourceIdStr == null) {
				cachedResultCompany = (BuyerCachedResult) locateNodeInCache(
						vendorIdStr, null, entityType);
				
				// check the createTime for the node and see if it is past the 
				// eviction interval; if yes, then get it from the database
				if (cachedResultCompany != null && isEvictNode(cachedResultCompany)) {
					cachedResultCompany = null;
					// detach this node from cache
					detachNodeFromCache(vendorIdStr, null, entityType);
				}
				
				// if node doesn't exist in cache OR node exists, but
				// summaryCount attrib in node does not exist.
				if ((cachedResultCompany == null)
						|| (cachedResultCompany.getSummaryCount() == null)) {
					// call to DAO to get Summary Company Level counts for Buyer
					summaryMapCompany = serviceOrderDao.getSummaryCountsBuyer(ajaxCacheVo); 
					
					// Create the node, add the SummaryCount to the Node, attach node to tree
					if (cachedResultCompany == null)
					{
						cachedResultCompany = new BuyerCachedResult();
						cachedResultCompany.setSummaryCount(summaryMapCompany);
						// add the cachedResult to the tree
						pathToCache = SurveyConstants.ENTITY_BUYER + "/" + vendorIdStr;
						addNodeToCache(pathToCache,cachedResultCompany);
					}
					else // if node exists, attach summary count to node
					// Do not add the object to the Cache as this should
					// replicate automatically because it is POJO cache.
					{
						cachedResultCompany.setSummaryCount(summaryMapCompany);
					}
				}
				return cachedResultCompany;
			}// Company Level counts
			else if (resourceIdStr != null) 
			//Step2: If resourceId is present, locate User level nodes, child node B1/U1
			{
				cachedResultUser = (BuyerCachedResult) locateNodeInCache(
						vendorIdStr, resourceIdStr, entityType);
				
				// check the createTime for the node and see if it is past the 
				// eviction interval; if yes, then get it from the database
				if (cachedResultUser != null && isEvictNode(cachedResultUser)) {
					cachedResultUser = null;
					// detach this node from cache
					detachNodeFromCache(vendorIdStr, null, entityType);
				}

				// Step 3: if B1/U1 cachedResult User is missing(expired or never
				// created), locate the parent node B1
				if (cachedResultUser == null) {
					cachedResultCompany = (BuyerCachedResult) locateNodeInCache(
							vendorIdStr, null, entityType);
					
					// check the createTime for the node and see if it is past the 
					// eviction interval; if yes, then get it from the database
					if (cachedResultCompany != null && isEvictNode(cachedResultCompany)) {
						cachedResultCompany = null;
						// detach this node from cache
						detachNodeFromCache(vendorIdStr, null, entityType);
					}

					// if B1 is null-- parent node doesn't exist in cache
					if (cachedResultCompany == null) {
						// call to DAO to get Summary Company Level counts for Buyer
						summaryMapCompany = serviceOrderDao.getSummaryCountsBuyer(ajaxCacheVo); 

						// Add the SummaryCount to the Node
						cachedResultCompany = new BuyerCachedResult();
						cachedResultCompany.setSummaryCount(summaryMapCompany);

						// add the cachedResult to the tree
						pathToCache = SurveyConstants.ENTITY_BUYER + "/" + vendorIdStr;
						addNodeToCache(pathToCache,cachedResultCompany);
					} else if (cachedResultCompany != null)
					// B1 exists, create B1/U1 node
					{
						// call to DAO to get Summary Company Level counts for Buyer
						summaryMapUser = serviceOrderDao.getSummaryCountsBuyer(ajaxCacheVo); 

						// Add the SummaryCount to the Node
						cachedResultUser = new BuyerCachedResult();
						cachedResultUser.setSummaryCount(summaryMapUser);

						pathToCache = SurveyConstants.ENTITY_BUYER + "/" + vendorIdStr + "/"
								+ resourceIdStr;
						addNodeToCache(pathToCache,cachedResultUser);
					}
				} else if (cachedResultUser != null)
				// B1/U1 exists, verify the SummaryCount attrib of B1/U1
				{
					if (cachedResultUser.getSummaryCount() == null) {
						// call to DAO to get Summary User Level counts for Buyer
						summaryMapUser = serviceOrderDao.getSummaryCountsBuyer(ajaxCacheVo); 

						// Add the SummaryCount to the Node
						cachedResultUser = new BuyerCachedResult();
						cachedResultUser.setSummaryCount(summaryMapUser);

						// Do not add the object to the Cache as this should
						// replicate automatically because it is POJO cache.
					}
				}
				return cachedResultUser;
			}// User Level counts
			break;

		case 2: // for Provider
			// Step 1: if resourceId is null, locate Company Level node directly
			if (resourceIdStr == null) {
				cachedResultCompany = (ProviderCachedResult) locateNodeInCache(
						vendorIdStr, null, entityType);
				
				// check the createTime for the node and see if it is past the 
				// eviction interval; if yes, then get it from the database
				if (cachedResultCompany != null && isEvictNode(cachedResultCompany)) {
					cachedResultCompany = null;
					// detach this node from cache
					detachNodeFromCache(vendorIdStr, null, entityType);
				}

				// if node doesn't exist in cache OR node exists, but
				// summaryCount attrib in node does not exist.
				if ((cachedResultCompany == null)
						|| (cachedResultCompany.getSummaryCount() == null))
				{
					// call to DAO to get Summary User Level counts for Provider
					summaryMapCompany = serviceOrderDao.getSummaryCountsProvider(ajaxCacheVo); 
					
					// create the node, add Summary count to it & add node to tree
					if (cachedResultCompany == null)
					{ 
						cachedResultCompany = new ProviderCachedResult();
						cachedResultCompany.setSummaryCount(summaryMapCompany);
						pathToCache = SurveyConstants.ENTITY_PROVIDER + "/" + vendorIdStr;
						addNodeToCache(pathToCache, cachedResultCompany);

					}
					else // if node exists, attach summary count to node
					// Do not add the object to the Cache as this should
					// replicate automatically because it is POJO cache.
					{
						cachedResultCompany.setSummaryCount(summaryMapCompany);
					}
				}
				return cachedResultCompany;
			}// Company Level counts
			else if (resourceIdStr != null)
			// Step2: If resourceId is present, locate User level nodes, child
			// node B1/U1
			{
				cachedResultUser = (ProviderCachedResult) locateNodeInCache(
						vendorIdStr, resourceIdStr, entityType);
				
				// check the createTime for the node and see if it is past the 
				// eviction interval; if yes, then get it from the database
				if (cachedResultUser != null && isEvictNode(cachedResultUser)) {
					cachedResultUser = null;
					// detach this node from cache
					detachNodeFromCache(vendorIdStr, null, entityType);
				}

				// Step 3: if B1/U1 is missing(expired or never created), locate
				// the parent node B1
				if (cachedResultUser == null) {
					cachedResultCompany = (ProviderCachedResult) locateNodeInCache(
							vendorIdStr, null, entityType);
					
					// check the createTime for the node and see if it is past the 
					// eviction interval; if yes, then get it from the database
					if (cachedResultCompany != null && isEvictNode(cachedResultCompany)) {
						cachedResultCompany = null;
						// detach this node from cache
						detachNodeFromCache(vendorIdStr, null, entityType);
					}

					// if B1 is null-- parent node doesn't exist in cache, no
					// need to check attribs of cache node
					if (cachedResultCompany == null) {
						// call to DAO to get Summary User Level counts for Buyer
						summaryMapCompany = serviceOrderDao.getSummaryCountsProvider(ajaxCacheVo); 

						// Add the SummaryCount to the Node
						cachedResultCompany = new ProviderCachedResult();
						cachedResultCompany.setSummaryCount(summaryMapCompany);

						// add the cachedResult to the tree
						pathToCache = SurveyConstants.ENTITY_PROVIDER + "/" + vendorIdStr;
						addNodeToCache(pathToCache,cachedResultCompany);
					} else if (cachedResultCompany != null) // B1 exists, create B1/U1 node
					{
						// call to DAO to get Summary User Level counts for Buyer
						summaryMapUser = serviceOrderDao.getSummaryCountsProvider(ajaxCacheVo); 

						// Add the DetailedCount to the Node
						cachedResultUser = new ProviderCachedResult();
						cachedResultUser.setSummaryCount(summaryMapUser);

						pathToCache = SurveyConstants.ENTITY_PROVIDER + "/" + vendorIdStr + "/"
								+ resourceIdStr;
						addNodeToCache(pathToCache,cachedResultUser);
					}
				} else if (cachedResultUser != null)
				// B1/U1 exists, verify the SummaryCount attrib of B1/U1
				{
					if (cachedResultUser.getSummaryCount() == null) {
						// call to DAO to get Summary User Level counts for Buyer
						summaryMapUser = serviceOrderDao.getSummaryCountsProvider(ajaxCacheVo); 

						// Add the SummaryCount to the Node
						cachedResultUser.setSummaryCount(summaryMapUser);

						// Do not add the object to the Cache as this should
						// replicate automatically because it is POJO cache.
					}
				}
				return cachedResultUser;
			}// User Level counts
			break;
		}// switch
		return cachedResult;
	}

	private void printMap(HashMap map) {
		if (map != null) {
			Set keys = map.keySet();
			Iterator keyIter = keys.iterator();
			while (keyIter.hasNext()) {
				Object key = keyIter.next();
				Object value = map.get(key);

			}
		}// if
	}

	//This method seems to be ready for deletion. Used only in a test class
	public void printCachedResultBuyer(BuyerCachedResult vo) {
		HashMap summaryMap = vo.getSummaryCount();
		printMap(summaryMap);

		HashMap detailedMap = vo.getDetailedCount();
		if (detailedMap != null) {
			Set keys = detailedMap.keySet();
			Iterator keyIter = keys.iterator();

			while (keyIter.hasNext()) {
				Object key = keyIter.next();
				BuyerDetailCountVO buyerValue = (BuyerDetailCountVO) detailedMap
						.get(key);

				String line = " SentProviders:" + buyerValue.getSentProviders()
						+ " Cond:" + buyerValue.getConditionalAccept()
						+ " Reject: " + buyerValue.getRejected() + " SpendLimit: "
						+ buyerValue.getSpendLimit();
			}
		}
	}

	public void printCachedResultProvider(ProviderCachedResult vo)
	{
		HashMap summaryMap = vo.getSummaryCount();
		printMap(summaryMap);

		HashMap detailedMap = vo.getDetailedCount();
		printMap(detailedMap);
	}

	private boolean addNodeToCache(String pathToCache,
			CachedResultVO cachedResult) throws PojoCacheException {
		boolean success = false;
		try{
			pcache.attach(pathToCache, cachedResult);
			logger.debug("Added node to cache: " + pathToCache);
			success = true;
		} catch(PojoCacheException pce){
			logger.error("Problem in addNodeToCache. Could not attach to pcache.");
			logger.error(pce.getMessage());
			logger.debug(pce.getCause());
		} catch(Exception e){
			logger.error(e.getMessage());
			logger.debug(e.getCause());
		}
		return success;
	}

	/**
	 * Retrieve POJO from the cache system. Return null if object does not exist
	 * in the cache. Note that this operation is fast if there is already a POJO
	 * instance attached to the cache.
	 * 
	 * @param vendorId
	 * @param resourceId
	 * @param entityType
	 * @return true if node exists, false if node does NOT exist
	 */
	private CachedResultVO locateNodeInCache(String vendorIdStr,
			String resourceIdStr, String entityType) throws PojoCacheException {
		CachedResultVO node = null;
		String locateNodeStr = null;
		try{
		// Let's locate the node.
		if (resourceIdStr != null) // User Level Nodes
		{
			if (entityType == SurveyConstants.ENTITY_BUYER) {
				locateNodeStr = "BUYER" + "/" + vendorIdStr + "/"
						+ resourceIdStr;
				node = (BuyerCachedResult) pcache.find(locateNodeStr);
				logger.debug("find SL Debug BUYER/+vendorIdStr/+recourceIdStr: BUYER/n" + vendorIdStr + "/"+ resourceIdStr);
				logger.debug("node is: " + node);
			} else if (entityType == SurveyConstants.ENTITY_PROVIDER) {
				locateNodeStr = "PROVIDER" + "/" + vendorIdStr + "/"
						+ resourceIdStr;
				node = (ProviderCachedResult) pcache.find(locateNodeStr);
				logger.debug("SL Debug PROVIDER/+vendorIdStr/+recourceIdStr: PROVIDER/n" + vendorIdStr + "/"+ resourceIdStr);
				logger.debug("node is: " + node);
			}
		} else if (resourceIdStr == null) // Company Level Nodes
		{
			if (entityType == SurveyConstants.ENTITY_BUYER) {
				locateNodeStr = "BUYER" + "/" + vendorIdStr;

				node = (BuyerCachedResult) pcache.find(locateNodeStr);
				logger.debug("find SL Debug BUYER/+vendorIdStr: BUYER/n" + vendorIdStr);
				logger.debug("node is: " + node);
			} else if (entityType == SurveyConstants.ENTITY_PROVIDER) {
				locateNodeStr = "PROVIDER" + "/" + vendorIdStr;

				node = (ProviderCachedResult) pcache.find(locateNodeStr);
				logger.debug("find SL Debug PROVIDER/+vendorIdStr: PROVIDER/n" + vendorIdStr);
				logger.debug("node is: " + node);
			}
		}
		} catch( PojoCacheException pce){
			logger.debug(pce.getStackTrace());
			pce.printStackTrace();
		}
		return node;
	}
	
	
	private void detachNodeFromCache(String vendorIdStr, String resourceIdStr,
			String entityType) {
		// Let's detach the node.
		String nodeStr = null;
		if (resourceIdStr != null) { // User Level Nodes
			if (entityType == SurveyConstants.ENTITY_BUYER) {
				nodeStr = "BUYER" + "/" + vendorIdStr + "/"
						+ resourceIdStr;
			} else if (entityType == SurveyConstants.ENTITY_PROVIDER) {
				nodeStr = "PROVIDER" + "/" + vendorIdStr + "/"
						+ resourceIdStr;
			}
		} 
		else if (resourceIdStr == null) { // Company Level Nodes
			if (entityType == SurveyConstants.ENTITY_BUYER) {
				nodeStr = "BUYER" + "/" + vendorIdStr;
			} 
			else if (entityType == SurveyConstants.ENTITY_PROVIDER) {
				nodeStr = "PROVIDER" + "/" + vendorIdStr;

			}
		}
		
		pcache.detach(nodeStr);
		
	}

	public CachedResultVO findThisNode(String vendorIdStr,
			String resourceIdStr, String entityType) {
		CachedResultVO vo = null;
		try {
			// Find all the existing nodes in the Cache.
			vo = locateNodeInCache(vendorIdStr, resourceIdStr, entityType);
		} catch (PojoCacheException pExp) {
			logger.info("Caught PojoCacheException in findThisNode method", pExp);
		}
		return vo;
	}
	
	public void handleEvent(CacheEvent ce) 
        {
		if (pcache == null) try {
			logger.info ("CacheManagerBOImpl.handleEvent(): pcache==null");
				loadCache();
		} catch(Exception e) {
			logger.error("CacheManagerBOImpl-->handleEvent()-->EXCEPTION-->", e);
		}
		
		// update buyer
		if (ce.isUpdateBuyer()) {
			logger.info ("CacheManagerBOImpl.handleEvent(): updateBuyer(ce)");
			updateBuyer(ce);
		}
		
		if (ce.isUpdateProvider() && ce.getVendorId() != -1) {
			logger.info ("CacheManagerBOImpl.handleEvent(): updateVendor()");	
				updateVendor(ce, ce.getVendorId());
		} 
		
		// iterate over all providers and call updateProvider
		if (ce.isUpdateAllProviders()) {
			List<RoutedProvider> providers = ce.getProviderList();
			// SL-8938: ...but first, check for an empty provider list
			if (providers==null || providers.isEmpty()) {
				logger.error("### CacheManagerBOImpl.handleEvent(): no providers found to update for CacheEvent:\n\t" + ce.toString());
			} else for (RoutedProvider provider : providers) {
				updateVendor(ce, provider.getVendorId().intValue());
			}
		}
	}
	
	
	private void updateBuyer(CacheEvent ce) {
		int buyerId = ce.getBuyerId();
		String soId = ce.getSoId();
		int buyerResourceId = ce.getBuyerResourceId();
		
		// get buyerNode for this buyId
		BuyerCachedResult cacheBuyerResult = (BuyerCachedResult) locateNodeInCache(String.valueOf(buyerId), 
				null, SurveyConstants.ENTITY_BUYER);
		
		if (cacheBuyerResult != null) {
			HashMap somBuyerSummaryCounts = cacheBuyerResult.getSummaryCount();
			HashMap somBuyerDetailCount = cacheBuyerResult.getDetailedCount();
			HashMap somBuyerResourceSummaryCounts = null;
			HashMap somBuyerResourceDetailCount= null;	
			
			BuyerCachedResult cacheBuyerResourceResult = (BuyerCachedResult) locateNodeInCache(
					String.valueOf(buyerId), String.valueOf(buyerResourceId), SurveyConstants.ENTITY_BUYER);
			
			if (cacheBuyerResourceResult != null) {
				somBuyerResourceSummaryCounts = cacheBuyerResourceResult.getSummaryCount();
				somBuyerResourceDetailCount = cacheBuyerResourceResult.getDetailedCount();				
			}
			
			if (somBuyerSummaryCounts != null) {
				if (ce.isClearBuyerSummary()) {
					cacheBuyerResult.setSummaryCount(null);
					if (somBuyerResourceSummaryCounts != null)
						cacheBuyerResourceResult.setSummaryCount(null);	
				}
			}
			
			if (somBuyerDetailCount != null) {
				if (ce.isIncrementCondAcceptCount()) {
					// find the soid and increment reject count
					BuyerDetailCountVO details = (BuyerDetailCountVO) somBuyerDetailCount.get(soId);
					if (details != null) {
						int condCount = details.getConditionalAccept().intValue();
						details.setConditionalAccept(++condCount);
					}
					
					// do it for the resource as well
					if (somBuyerResourceDetailCount != null) {
						BuyerDetailCountVO resourceDetails = (BuyerDetailCountVO) somBuyerDetailCount.get(soId);
						if (resourceDetails != null) {
							int condCount = resourceDetails.getConditionalAccept().intValue();
							details.setRejected(++condCount);
						}						
					}
				}
				
				if (ce.isIncrementRejectCount()) {
					// find the soid and increment reject count
					BuyerDetailCountVO details = (BuyerDetailCountVO) somBuyerDetailCount.get(soId);
					if (details != null) {
						int rejectCount = details.getRejected().intValue();
						details.setRejected(++rejectCount);
					}
					
					// do it for the resource as well
					if (somBuyerResourceDetailCount != null) {
						BuyerDetailCountVO resourceDetails = (BuyerDetailCountVO) somBuyerDetailCount.get(soId);
						if (resourceDetails != null) {
							int rejectCount = resourceDetails.getRejected().intValue();
							details.setRejected(++rejectCount);
						}						
					}
				}
				if (ce.isClearBuyerDetails()) {
					cacheBuyerResult.setDetailedCount(null);
					if (somBuyerResourceDetailCount != null) 
						cacheBuyerResourceResult.setDetailedCount(null);
				}
			}
			
			if (ce.isClearBuyerDashboardAmounts()) {
				// clear available balance and current balance
				cacheBuyerResult.setCurrentBalance(null);
				cacheBuyerResult.setAvailableBalance(null);
				
				if (cacheBuyerResourceResult != null) {
					cacheBuyerResourceResult.setCurrentBalance(null);
					cacheBuyerResourceResult.setAvailableBalance(null);
				}
			}
		}
		
	}
	
	private void updateVendor(CacheEvent ce, int vendorId) {
		int vendorResourceId = ce.getVendorResourceId();
		
		// get buyerNode for this buyId
		ProviderCachedResult cacheProviderResult = (ProviderCachedResult) locateNodeInCache(String.valueOf(vendorId), 
				null, SurveyConstants.ENTITY_PROVIDER);
		
		if (cacheProviderResult != null) {
			HashMap somProviderSummaryCounts = cacheProviderResult.getSummaryCount();
			HashMap somProviderDetailCount = cacheProviderResult.getDetailedCount();
			HashMap somProviderResourceSummaryCounts = null;
			HashMap somProviderResourceDetailCount= null;	
			
			ProviderCachedResult cacheProviderResourceResult = (ProviderCachedResult) locateNodeInCache(
					String.valueOf(vendorId), String.valueOf(vendorResourceId), SurveyConstants.ENTITY_BUYER);
			
			if (cacheProviderResourceResult != null) {
				somProviderResourceSummaryCounts = cacheProviderResourceResult.getSummaryCount();
				somProviderResourceDetailCount = cacheProviderResourceResult.getDetailedCount();				
			}
			
			if (somProviderSummaryCounts != null) {
				if (ce.isClearProvidersSummary()) {
					cacheProviderResult.setSummaryCount(null);
					if (somProviderResourceSummaryCounts != null)
						cacheProviderResourceResult.setSummaryCount(null);
				}
			}
			
			if (somProviderDetailCount != null) {
				if (ce.isClearProvidersDetails()) {
					cacheProviderResult.setDetailedCount(null);
					if (somProviderResourceDetailCount != null) 
						cacheProviderResourceResult.setDetailedCount(null);
				}
			}
			
			if (ce.isClearProvidersDashboardAmount()) {
				// clear available balance and current balance
				cacheProviderResult.setAvailableBalance(null);
				
				if (cacheProviderResourceResult != null) {
					cacheProviderResourceResult.setAvailableBalance(null);
				}
			}
		}
		
	}
	
	private boolean isEvictNode(CachedResultVO node) {
		Timestamp ts = node.getCreateTime();
		Date now = new Date();
		if (ts.getTime() + getEvictionInterval() < now.getTime()) {
			return true;
		}
		
		return false;
	}

	public ServiceOrderDao getServiceOrderDao() {
		return serviceOrderDao;
	}

	public void setServiceOrderDao(ServiceOrderDao serviceOrderDao) {
		this.serviceOrderDao = serviceOrderDao;
	}

	public ILedgerFacilityBO getAccountingTransactionManagementImpl() {
		return accountingTransactionManagementImpl;
	}

	public void setAccountingTransactionManagementImpl(
			ILedgerFacilityBO accountingTransactionManagementImpl) {
		this.accountingTransactionManagementImpl = accountingTransactionManagementImpl;
	}

	public ProviderDao getProviderDao() {
		return providerDao;
	}

	public void setProviderDao(ProviderDao providerDao) {
		this.providerDao = providerDao;
	}
}
