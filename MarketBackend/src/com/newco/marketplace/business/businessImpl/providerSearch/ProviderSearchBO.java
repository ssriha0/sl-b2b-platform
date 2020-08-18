package com.newco.marketplace.business.businessImpl.providerSearch;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import com.newco.marketplace.business.businessImpl.ABaseCriteriaHandler;
import com.newco.marketplace.business.businessImpl.skillTree.MarketplaceSearchBean;
import com.newco.marketplace.business.iBusiness.orderGroup.IOrderGroupBO;
import com.newco.marketplace.business.iBusiness.provider.IProviderInfoPagesBO;
import com.newco.marketplace.business.iBusiness.provider.IPublicProfileBO;
import com.newco.marketplace.business.iBusiness.providersearch.IMasterCalculatorBO;
import com.newco.marketplace.business.iBusiness.providersearch.IProviderSearchBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.criteria.SortCriteria;
import com.newco.marketplace.dto.vo.LocationVO;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.SkuDetailsVO;
import com.newco.marketplace.dto.vo.leadsmanagement.FirmServiceVO;
import com.newco.marketplace.dto.vo.provider.AdditionalInsuranceVO;
import com.newco.marketplace.dto.vo.provider.VendorHdrVO;
import com.newco.marketplace.dto.vo.provider.VendorResource;
import com.newco.marketplace.dto.vo.providerSearch.InsuranceResultVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderBackgroundCheckResultsVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderCredentialResultsVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderLanguageResultsVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderLocationResultsVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultForAdminVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderSPNetStateResultsVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderSearchCriteriaVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderSkillResultsVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderSkillServiceTypeRequestVO;
import com.newco.marketplace.dto.vo.providerSearch.SearchResultForAdminVO;
import com.newco.marketplace.dto.vo.serviceorder.CriteriaMap;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSearchResultsVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderTask;
import com.newco.marketplace.dto.vo.serviceorder.TierRoutedProvider;
import com.newco.marketplace.dto.vo.skillTree.ProviderIdsVO;
import com.newco.marketplace.dto.vo.skillTree.SkillNodeIdsVO;
import com.newco.marketplace.dto.vo.skillTree.SkillNodeVO;
import com.newco.marketplace.dto.vo.spn.SPNetHeaderVO;
import com.newco.marketplace.dto.vo.survey.CustomerFeedbackVO;
import com.newco.marketplace.dto.vo.survey.SurveyRatingsVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.exception.gis.InsuffcientLocationException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.interfaces.ProviderConstants;
import com.newco.marketplace.persistence.iDao.provider.IVendorCredentialsDao;
import com.newco.marketplace.persistence.iDao.providerSearch.ProviderSearchDao;
import com.newco.marketplace.persistence.iDao.survey.ExtendedSurveyDAO;
import com.newco.marketplace.persistence.iDao.survey.SurveyDAO;
import com.newco.marketplace.persistence.iDao.vendor.VendorResourceDao;
import com.newco.marketplace.util.PropertiesUtils;
import com.newco.marketplace.util.gis.GISUtil;
import com.newco.marketplace.utils.NumberUtils;
import com.sears.os.business.ABaseBO;
import com.newco.marketplace.dto.vo.provider.BasicFirmDetailsVO;

/**
 * @author zizrale
 *
 *
 */
public class ProviderSearchBO extends ABaseBO implements IProviderSearchBO{

//	1 GET ALL SKILL NODES
//	2 GET ALL PROVIDERS WITH A GIVING SKILL NODE ID
//	3 GET ALL PROVIDERS BY VERTICAL
//	4 DO RATING
//	5 GIVE RATING TO ALL PROVIDERS IN A GIVING VERTICAL
//	6 GET COMBINED RATING FOR ALL PROVIDERS IN A GIVEN VERTICAL
//	7 GET ALL PROVIDER DETAILS
//  8 Sort according to skill rating
//	9 CHECK ANY PREVIOUSLY SELECTED PROVIDERS AND CHECK WITH THE CURRENT LIST OF PROVIDERS
//	10 GET ALL PROVIDER DETAILS FOR THE ALREADY SELECTED PROVIDERS

	private ProviderSearchDao providerSearchDao;
	private SurveyDAO surveyDao;
	private ExtendedSurveyDAO extendedSurveyDAO;
	private MarketplaceSearchBean marketplaceSkillSearch;
	private IMasterCalculatorBO masterCalculatorBO;
	private IOrderGroupBO orderGroupBO;
	private IServiceOrderBO serviceOrderBO;
	private IProviderInfoPagesBO pageBO;
	private IPublicProfileBO publicBO;
	private IVendorCredentialsDao vendorCredentialDAO;
	private VendorResourceDao venderResourceDao;
		
//	private ArrayList<ProviderResultVO> unfilteredProviderList = null;
	private static int _defaultDistanceFilter = Integer.MIN_VALUE;

	private int getDefaultDistanceFilter() {
		if(_defaultDistanceFilter == Integer.MIN_VALUE) {
			_defaultDistanceFilter = Integer.parseInt(PropertiesUtils.getPropertyValue(Constants.AppPropConstants.PROVIDER_SEARCH_DISTANCE_FILTER));
		}
		return _defaultDistanceFilter;
	}

	public List<ProviderResultVO> getProviderListForZip(ProviderSearchCriteriaVO searchCriteria){
		LocationVO latLongLocationVO = null;
		SkillNodeIdsVO skillNodeIdsVO = new SkillNodeIdsVO();
		latLongLocationVO = providerSearchDao.getZipLatAndLong(searchCriteria.getServiceLocation().getZip());
		skillNodeIdsVO.setZipLatitude(Double.parseDouble(latLongLocationVO.getLatitude()));
		skillNodeIdsVO.setZipLongitude(Double.parseDouble(latLongLocationVO.getLongitude()));
		skillNodeIdsVO.setDistanceFilter(getDefaultDistanceFilter());
		//SLT-2545 Zip code based order routing changes START
		if(null != searchCriteria.getServiceLocation()){
		skillNodeIdsVO.setZipCode(searchCriteria.getServiceLocation().getZip());
		}
		//SLT-2545 Zip code based order routing changes END
		List<ProviderResultVO> listOfAllProvidersInVertical = null;
 		try {
			listOfAllProvidersInVertical = providerSearchDao.getProvidersByZipLoc(skillNodeIdsVO);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			logger.error("Exception in getProvidersByZipLoc() :"+e1);
			e1.printStackTrace();
		}
 		logger.info("listOfAllProvidersInVertical : "+listOfAllProvidersInVertical);

// 		if(listOfAllProvidersInVertical != null){
//	 		for (int i = listOfAllProvidersInVertical.size()-1; i > 199; i--) {
//	 			listOfAllProvidersInVertical.remove(i);
//	 		}
// 		}

        if (listOfAllProvidersInVertical != null && listOfAllProvidersInVertical.size() > 0) {
            HashMap<String, String> providerCoordinates = new HashMap<String, String>();
            HashMap<String, String> serviceLocationCoordinates = new HashMap<String, String>();
            HashMap<Integer, ProviderResultVO> providerMap = new HashMap<Integer, ProviderResultVO>();

            ProviderIdsVO vendorResourceIds = extractVendorResourceIds((ArrayList)listOfAllProvidersInVertical);
            // get the language info
            List<ProviderLanguageResultsVO> providerLanguageList = providerSearchDao.getProviderLanguages(vendorResourceIds);

            List<SurveyRatingsVO> providerStarList = null;
            try {                
                //providerStarList = getSurveyDao().getFastLookupRating(searchCriteria.getBuyerID()	, vendorResourceIds.getProviderIds());
            	providerStarList = getExtendedSurveyDAO().getBuyerMyRatings(searchCriteria.getBuyerID()	, vendorResourceIds.getProviderIds());
            } catch (DataServiceException e) {
                logger.error("Error in ProviderSearchBO --> getAllProviderData() "+e.getMessage());
                e.printStackTrace();
            }

            for(ProviderResultVO aProviderResultVO:listOfAllProvidersInVertical){
                ArrayList<ProviderLanguageResultsVO> languages = new ArrayList<ProviderLanguageResultsVO>();
                aProviderResultVO.setLanguages(languages);
                aProviderResultVO.setDistance(aProviderResultVO.getDistanceFromBuyer());
                providerMap.put(aProviderResultVO.getResourceId(), aProviderResultVO);
            }

            for(ProviderLanguageResultsVO language:providerLanguageList){
                providerMap.get(language.getProviderResourceId()).getLanguages().add(language);
            }

            for(SurveyRatingsVO aSurveyRatingsVO:providerStarList){
                providerMap.get(aSurveyRatingsVO.getVendorResourceID()).setProviderStarRating(aSurveyRatingsVO);
            }

            Collections.sort(listOfAllProvidersInVertical, new ProviderDistanceComparator());
        }

		return listOfAllProvidersInVertical;

	}


	public List<ProviderResultVO> getProviderDetailsForLockedProvider(ProviderSearchCriteriaVO searchCriteria){
		LocationVO latLongLocationVO = null;
		SkillNodeIdsVO skillNodeIdsVO = new SkillNodeIdsVO();
		latLongLocationVO = providerSearchDao.getZipLatAndLong(searchCriteria.getServiceLocation().getZip());
		skillNodeIdsVO.setZipLatitude(Double.parseDouble(latLongLocationVO.getLatitude()));
		skillNodeIdsVO.setZipLongitude(Double.parseDouble(latLongLocationVO.getLongitude()));
		skillNodeIdsVO.setDistanceFilter(getDefaultDistanceFilter());
		skillNodeIdsVO.setLockedResourceId(new Integer(searchCriteria.getLockedResourceId()));
		List<ProviderResultVO> listOfAllProvidersInVertical = null;
 		listOfAllProvidersInVertical = providerSearchDao.getLockedProviderDetails(skillNodeIdsVO);
		return listOfAllProvidersInVertical;

	}
	/**
	 * @param searchCriteria
	 * @return ArrayList of ProviderResultVO
	 */

	public ArrayList<ProviderResultVO> getProviderList(ProviderSearchCriteriaVO searchCriteria){
		long start1 = System.currentTimeMillis();
		if(searchCriteria.getSkillNodeIds()== null ||searchCriteria.getSkillNodeIds().size() == 0 ||
				(searchCriteria.getSkillNodeIds().size() == 1 && searchCriteria.getSkillNodeIds().get(0).intValue() == -999 )
				){
			searchCriteria.setSkillNodeIds(new ArrayList<Integer>());
		}
		
		// There is a query issue in below code.  
     	/*if(searchCriteria.getLockedResourceId() > 0 ) {
			return (ArrayList<ProviderResultVO>) getProviderDetailsForLockedProvider(searchCriteria);
		}*/
		if(searchCriteria.getSkillNodeIds().size() == 0){
			return (ArrayList<ProviderResultVO>
			) getProviderListForZip(searchCriteria);

		} 
		/**
		 * if it is a grouped order go get tasks from the other orders to get skill types and skill_node_ids.
		 */
		if(StringUtils.isNotBlank(searchCriteria.getGroupID())){

			searchCriteria = addSiblingSkills(searchCriteria);
		}
		Long startTime = Calendar.getInstance().getTimeInMillis(); 	
		logger.info("ProviderSearchBO: Start --> getProviderList() ");		
		ArrayList<SkillNodeVO> parentSkillStructure;
		// collectionOfParentStructs will contain the parent hierarchy for each skill passed in
		// Get the latitude and longitude of the zip code.
		long start5 = System.currentTimeMillis();
		LocationVO serviceLocationVO = searchCriteria.getServiceLocation();
		
		HashMap<String, String> serviceLocationCoordinates = null;
		LocationVO latLongLocationVO = null;
		if(serviceLocationCoordinates == null
				&& serviceLocationVO != null
				&& serviceLocationVO.getZip() != null
				&& StringUtils.isNotEmpty(serviceLocationVO.getZip())){
			latLongLocationVO = providerSearchDao.getZipLatAndLong(serviceLocationVO.getZip());
			logger.info("getProviderList()--> zip code: " + serviceLocationVO.getZip());
			if(latLongLocationVO != null
					&& latLongLocationVO.getLatitude() != null
					&& latLongLocationVO.getLongitude() != null){
				serviceLocationCoordinates = new HashMap<String, String>();
				serviceLocationCoordinates.put("lat", latLongLocationVO.getLatitude());
				serviceLocationCoordinates.put("long", latLongLocationVO.getLongitude());
			}
		} 
		long end5 = System.currentTimeMillis();
		logger.info("Get the latitude and longitude of the zip code. time taken:" + (end5-start5));

		/* separate criteria and figure out what needs to be queried */
		List<Integer> skillTypeIds = searchCriteria.getSkillServiceTypeId();
		// The Focus Skills I need
		List<Integer> theNodesThatIWant = searchCriteria.getSkillNodeIds();
		// get the list of VOs that I want
		List<SkillNodeVO> theSkillNodeVOsThatIWant = new ArrayList<SkillNodeVO>();

		if(searchCriteria.getSkillNodeIds() != null &&theNodesThatIWant.size() > 0){

			//1 GET ALL SKILL NODE Paths I am concerned with
			List<ProviderSkillServiceTypeRequestVO> pathsForTheNodesINeed = getAllSkillNodePath(theNodesThatIWant,searchCriteria,theSkillNodeVOsThatIWant);
			
			// Get All Paths combined to one list for searchin ( for the In clause )
			long start6 = System.currentTimeMillis();
			List<Integer> allOfTheSkillsThatCouldMatch = new ArrayList<Integer>();
			for(ProviderSkillServiceTypeRequestVO stupidInnderArray :pathsForTheNodesINeed ){
				for(SkillNodeVO innerSkill : stupidInnderArray.getSkillNodeList()){
					if(!allOfTheSkillsThatCouldMatch.contains(innerSkill.getNodeId())){
						allOfTheSkillsThatCouldMatch.add(innerSkill.getNodeId());
					}
				}
			}

			long end6 = System.currentTimeMillis();
			logger.info("Get All Paths combined to one list for searchin . time taken:" + (end6-start6));

			Double weightFactorDecrement = 0.0;

			// only need to look at the first path as the weight factor is by vertical
			long start7 = System.currentTimeMillis();
			for(int i=0; i< pathsForTheNodesINeed.get(0).getSkillNodeList().size(); i++){
				SkillNodeVO skillNode = pathsForTheNodesINeed.get(0).getSkillNodeList().get(i);
				Double percentageDecrement = skillNode.getMatchFactorDecrement();
				if(percentageDecrement != null
						&& percentageDecrement.doubleValue() != 0.0){
					weightFactorDecrement = 1 - percentageDecrement;
					break;
				}
			}
			long end7 = System.currentTimeMillis();
			logger.info("look at the first path as the weight factor is by vertical. time taken:" + (end7-start7));

				SkillNodeIdsVO skillNodeIdsVO = new SkillNodeIdsVO();
				skillNodeIdsVO.setSkillNodeIds((ArrayList)allOfTheSkillsThatCouldMatch);
				skillNodeIdsVO.setRootNodeId(allOfTheSkillsThatCouldMatch.get(0)); // get any node, does not have to be the root one. -Z
				//SLT-2545 Zip code based order routing changes START
				if(null != serviceLocationVO){
				skillNodeIdsVO.setZipCode(serviceLocationVO.getZip());
				}
				//SLT-2545 Zip code based order routing changes END
				if(latLongLocationVO != null
						&& latLongLocationVO.getLatitude() != null
						&& latLongLocationVO.getLongitude() != null){
					skillNodeIdsVO.setZipLatitude(new Double(latLongLocationVO.getLatitude()));
					skillNodeIdsVO.setZipLongitude(new Double(latLongLocationVO.getLongitude()));
					Integer distance = getDefaultDistanceFilter();
					
					if(null!=searchCriteria.getSelectedDistance() && null!=distance 
							&& searchCriteria.getSelectedDistance().intValue() > distance.intValue())
					{
						skillNodeIdsVO.setDistanceFilter(searchCriteria.getSelectedDistance());
					}else
					{
						skillNodeIdsVO.setDistanceFilter(getDefaultDistanceFilter());
					}
				}

				logger.debug("Getting the provider in the vertical");
				/* GET ALL PROVIDERS BY VERTICAL
				// call a DAO to get all the providers in the vertical */
				Map<Integer,ProviderSkillResultsVO> mapOfProviderSkillResults = new HashMap<Integer,ProviderSkillResultsVO>();
				long start = System.currentTimeMillis();
				List<ProviderSkillResultsVO> listOfAllProvidersInVertical = getAllProvidersInVertical(searchCriteria, skillNodeIdsVO);
				long end = System.currentTimeMillis();

				logger.info("Time taken inside getAllProvidersInVertical:"+(end-start));
     			//  Make a map so I get the key set
				long start8 = System.currentTimeMillis();
				if(null!=listOfAllProvidersInVertical){
					for(ProviderSkillResultsVO aProviderSkillVo : listOfAllProvidersInVertical){
						mapOfProviderSkillResults.put(aProviderSkillVo.getProviderId(),aProviderSkillVo);
					}
				}
				long end8 = System.currentTimeMillis();
       			logger.info(" Make a map so I get the key set time taken:"+(end8-start8));

       			logger.info("Calculating the skill rating for the providers");


			try{
				long start2= System.currentTimeMillis();
				listOfAllProvidersInVertical = RatingsCalculator.assignSkillRatingsToProviders(listOfAllProvidersInVertical,pathsForTheNodesINeed,
																theSkillNodeVOsThatIWant,weightFactorDecrement,providerSearchDao);
				long end2= System.currentTimeMillis();
				logger.info("Calculating the skill rating for the providers time taken:"+(end2-start2));

			}catch (DataServiceException e) {
				// TODO: handle exception
				logger.error(e);
				e.printStackTrace();
			}
			
			ArrayList<ProviderResultVO> providerList = null;
			if(listOfAllProvidersInVertical != null){
				logger.info("getProviderList()--> zip code: " + serviceLocationVO.getZip() + " | providers retrieved: " + listOfAllProvidersInVertical.size() );
				providerList	 = getProListwithDetails(listOfAllProvidersInVertical);
				
			}
			// if provider list for skills is zero , return empty list, no need to do further steps of getting info of providers & sorting
			if(providerList == null || providerList.size() == 0){
				return providerList;
			}
			
			/*GET ALL PROVIDER DETAILS
			We are passing in null for serviceLocationCoordinates because we already got it.*/
			ArrayList<ProviderResultVO> providerListWithInfo = getAllProviderData(searchCriteria.getBuyerID(),providerList,null,searchCriteria.getSpnID());

			/*cache the unfiltered provider list for later reuse if the user queries it again.
			 Order with %match desc, distance asc and skill rating desc. */
			long start2 = System.currentTimeMillis();
			Collections.sort(providerListWithInfo, new ProviderStarRatingComparator());
			long end2 = System.currentTimeMillis();
			logger.info("timetaken::sort start rating" + (end2-start2));			

			long start3 = System.currentTimeMillis();
			Collections.sort(providerListWithInfo, new ProviderDistanceComparator());
			long end3 = System.currentTimeMillis();
			logger.info("timetaken::sort distannce" + (end3-start3));			

			long start4 = System.currentTimeMillis();
			Collections.sort(providerListWithInfo, new ProviderRatingComparator());
			long end4 = System.currentTimeMillis();
			logger.info("timetaken::sort prov rating" + (end4-start4));			

			/*
			 * check any previously selected providers and check with the current
			 *  list of providers , if any selected guys doesn't exits in current list add to current list
			 */
			ArrayList<ProviderResultVO> providerVO =  new ArrayList<ProviderResultVO>();

			if(searchCriteria.getFirmId() == null){
				providerVO =  handlePreSelectedProviders(searchCriteria,providerListWithInfo,serviceLocationCoordinates);
			}else{
				for (ProviderResultVO providerResultVO : providerListWithInfo) {
					if (searchCriteria.getFirmId().contains(providerResultVO.getVendorID())) {
						providerVO = providerListWithInfo;
					}
				}
			}
			Long endTime = Calendar.getInstance().getTimeInMillis(); 	
			logger.info("timetaken:: ProviderSearchBO: End --> getProviderList() " + (endTime-startTime));			
			long end1 = System.currentTimeMillis();
			logger.info("timetaken:: ProviderSearchBO: End --> getProviderList() " + (end1-start1));			

			return providerVO;
		}
		return null;
	}

	private ArrayList<ProviderResultVO> handlePreSelectedProviders(
			ProviderSearchCriteriaVO searchCriteria,
			ArrayList<ProviderResultVO> providerListWithInfo, HashMap<String, String> serviceLocationCoordinates) {
		logger.info("Inside handlePreSelectedProviders");
		long start = System.currentTimeMillis();
		ArrayList<ProviderResultVO> providerVO = null;
		if(searchCriteria.getServiceOrderID() != null && !searchCriteria.isRoutingPriorityApplied()){
			providerVO = providerSearchDao.getSelectedProviders(searchCriteria.getServiceOrderID());
		}
		else if(StringUtils.isNotBlank(searchCriteria.getGroupID()) && searchCriteria.getCurrentChildOrderId()!=null){
			providerVO = providerSearchDao.getSelectedProviders(searchCriteria.getCurrentChildOrderId());
		}else{
			providerVO = providerSearchDao.getSelectedProvidersForTier(searchCriteria.getServiceOrderID());
		}
		Map<Integer,ProviderResultVO> mapProviderResultVO = new HashMap<Integer,ProviderResultVO>();

		for(ProviderResultVO aProviderResultVO:providerListWithInfo)
			mapProviderResultVO.put(aProviderResultVO.getResourceId(),aProviderResultVO);

		// If that providers come up again remove the duplicate add to the selected guys
		if(providerVO != null && providerListWithInfo != null){
			for(int index = 0; index < providerVO.size(); ++index){ // only the selected guys
				ProviderResultVO oldSelectedProviderVO = providerVO.get(index);
				if(mapProviderResultVO.containsKey(oldSelectedProviderVO.getResourceId())){ // only that exist
					mapProviderResultVO.get(oldSelectedProviderVO.getResourceId()).setSelected(true);
					//oldSelectedProviderVO = mapProviderResultVO.get(oldSelectedProviderVO.getResourceId());
					providerVO.remove(index);
					providerVO.add(index,mapProviderResultVO.get(oldSelectedProviderVO.getResourceId())); // add too selected
					for(int stupidsolutionindex = 0; stupidsolutionindex <providerListWithInfo.size(); ++stupidsolutionindex){
						if(providerListWithInfo.get(stupidsolutionindex).getResourceId() == oldSelectedProviderVO.getResourceId() ){
							providerListWithInfo.remove(stupidsolutionindex);
							break;
						}
					}
				}
			}
		}
		if(providerVO == null){
			providerVO = new ArrayList<ProviderResultVO>();
		}
		if (providerVO.size() > 0) {
			//10 Get the details of the already selected providers.
			// Pass the serviceLocationCoordinates to retrive lat, long and distance
			getAllProviderData(searchCriteria.getBuyerID(),providerVO,serviceLocationCoordinates,searchCriteria.getSpnID());

			// Sort the previously selected list
			Collections.sort(providerVO, new ProviderStarRatingComparator());

			Collections.sort(providerVO, new ProviderDistanceComparator());

			Collections.sort(providerVO, new ProviderRatingComparator());

		}

		providerVO.addAll(providerListWithInfo);
		long end = System.currentTimeMillis();
		logger.info("Inside handlePreSelectedProviders time taken:"+(end-start));
		return providerVO;
	}


	private ArrayList<ProviderResultVO> getProListwithDetails(
			List<ProviderSkillResultsVO> listOfAllProvidersInVertical) {
		long start = System.currentTimeMillis();

		ArrayList<ProviderResultVO> providerList = new ArrayList<ProviderResultVO>();
		for(int i=0; i<listOfAllProvidersInVertical.size(); i++){
			ProviderSkillResultsVO skillResultsVO = listOfAllProvidersInVertical.get(i);
			ProviderResultVO providerSearchVO = new ProviderResultVO();

			providerSearchVO.setProviderFirstName(skillResultsVO.getProviderFirstName());
			providerSearchVO.setProviderLastName(skillResultsVO.getProviderLastName());
			providerSearchVO.setResourceId(skillResultsVO.getProviderResourceId());
			providerSearchVO.setVendorID(skillResultsVO.getProviderId());
			providerSearchVO.setProviderRating(skillResultsVO.getProviderSkillRating());
			//logger.info("Inside ProviderSearchBO.getProListwithDetails----1>>firmPerfScore>>"+skillResultsVO.getFirmPerformanceScore());
			providerSearchVO.setFirmPerformanceScore(skillResultsVO.getFirmPerformanceScore());
			int percentageMatch = (int)(skillResultsVO.getProviderSkillRating() * 100);
			providerSearchVO.setPercentageMatch(percentageMatch);
			providerSearchVO.setCity(skillResultsVO.getCity());
			providerSearchVO.setState(skillResultsVO.getState());
			providerSearchVO.setDistance(NumberUtils.formatDecimalNumber(skillResultsVO.getDistanceFromZipInMiles(), 2));
			providerSearchVO.setProviderLatitude(skillResultsVO.getProviderLatitude());
			providerSearchVO.setProviderLongitude(skillResultsVO.getProviderLongitude());
			providerSearchVO.setFirmName(skillResultsVO.getFirmName());
			providerList.add(providerSearchVO);

		}
		long end = System.currentTimeMillis();
		logger.info("Inside ProviderSearchBO.getProListwithDetails----time taken::>>firmPerfScore>>"+(end-start));

		return providerList;
	}


	private List<ProviderSkillResultsVO> getAllProvidersInVertical(
			ProviderSearchCriteriaVO searchCriteria,
			SkillNodeIdsVO skillNodeIdsVO) {
		long start = System.currentTimeMillis();
		List<ProviderSkillResultsVO> listOfAllProvidersInVertical = new ArrayList<ProviderSkillResultsVO>();
		logger.info("Inside getAllProvidersInVertical>>"+searchCriteria.getSpnID());
		if(searchCriteria.getSpnID() != null){
			skillNodeIdsVO.setSpnId(searchCriteria.getSpnID());
			skillNodeIdsVO.setPerfScore(searchCriteria.getPerfScore());
			if(searchCriteria.getTierId()!= null){
				skillNodeIdsVO.setTierId(searchCriteria.getTierId());
			}
		}
		if(searchCriteria.isMarketProviderSearchOff() && ((searchCriteria.getIsNewSpn()== null ||
				(searchCriteria.getIsNewSpn()!= null && !searchCriteria.getIsNewSpn() ))||(searchCriteria.getSpnID()==null||searchCriteria.getSpnID().equals("")))){
			long end1 = System.currentTimeMillis();
			logger.info("Inside getAllProvidersInVertical time taken: "+(end1-start));

			return listOfAllProvidersInVertical;
		}
		
		// depending on SPN type new / old need to call respective query
		// if null or false call oldSPN query
		logger.info("SPN IN QUERY INPUT>>"+skillNodeIdsVO.getSpnId());
		if(searchCriteria.getIsNewSpn()== null ||
				(searchCriteria.getIsNewSpn()!= null && !searchCriteria.getIsNewSpn() )){
			if(searchCriteria.getFirmId() == null){
				listOfAllProvidersInVertical = providerSearchDao.getProvidersBySkills(skillNodeIdsVO);
			}else{
				listOfAllProvidersInVertical = providerSearchDao.getProvidersBySkillsAndFirmId(skillNodeIdsVO, searchCriteria);
			}
		}else{ // if true call newSPN query
			listOfAllProvidersInVertical = providerSearchDao.getProvidersBySkillsForNewSpn(skillNodeIdsVO);
		}
		long end = System.currentTimeMillis();
		logger.info("Inside getAllProvidersInVertical time taken: "+(end-start));
		return listOfAllProvidersInVertical;
	}


	private List<ProviderSkillServiceTypeRequestVO> getAllSkillNodePath(
			List<Integer> theNodesThatIWant, ProviderSearchCriteriaVO searchCriteria,
			List<SkillNodeVO> theSkillNodeVOsThatIWant) {
		
		List<ProviderSkillServiceTypeRequestVO> pathsForTheNodesINeed = new ArrayList<ProviderSkillServiceTypeRequestVO>();
		ArrayList<SkillNodeVO> parentSkillStructure = null;
		for(Integer skillNodeId :theNodesThatIWant){
			// add the hierarchy for the node to the Map of all nodes
			parentSkillStructure = new ArrayList<SkillNodeVO>();
			parentSkillStructure = marketplaceSkillSearch.getParentSkillStructById(skillNodeId);


			//TODO Trust the dao.
			for(SkillNodeVO aSkilNodeVO:parentSkillStructure){
				if(aSkilNodeVO.getNodeId().equals(skillNodeId)){
					theSkillNodeVOsThatIWant.add(aSkilNodeVO);
					break;
				}
			}

			ProviderSkillServiceTypeRequestVO parentSkillServiceTypeRequestVO = new ProviderSkillServiceTypeRequestVO();
			parentSkillServiceTypeRequestVO.setSkillNodeList(parentSkillStructure);
			parentSkillServiceTypeRequestVO.setSkillTypesList(searchCriteria.getSkillServiceTypeId());

			pathsForTheNodesINeed.add(parentSkillServiceTypeRequestVO);
		}
		return pathsForTheNodesINeed;
	}



	/**
	 * add skills of all siblings to Search Criteria so that we get Providers that meet group skills
	 * @param searchCriteria
	 * @return
	 */
	private ProviderSearchCriteriaVO addSiblingSkills(ProviderSearchCriteriaVO searchCriteria) {
		ArrayList<Integer> skillNodeIdList = searchCriteria.getSkillNodeIds();
		List<Integer> skillTypeIdList = searchCriteria.getSkillServiceTypeId();
		try{
			
			// TODO get groupId instead of service orderId
			String groupId = searchCriteria.getGroupID();
			List<ServiceOrderSearchResultsVO> childOrdersresult = orderGroupBO.getServiceOrdersForGroup(groupId);
			for(ServiceOrderSearchResultsVO  eachChildOrder: childOrdersresult){
				String childOrderId = eachChildOrder.getSoId();
				// add skills of all siblings excluding this particular SO as this already has skills added in SearchCriteriaVO
				if(childOrderId.equals(searchCriteria.getServiceOrderID())){
					continue;
				}
				
				ServiceOrder so = serviceOrderBO.getServiceOrder(childOrderId);

				if (so.getPrimarySkillCatId() != null) {
					ArrayList<Integer> skillNodeIds = new ArrayList<Integer>();
					List<Integer> skillTypes = new ArrayList<Integer>();
					if(null != so.getTasks() && so.getTasks().size() > 0){
						// If we have tasks then assign the SubCategory/Category to the list
						// else select the main category
						for(ServiceOrderTask taskDto : so.getTasks()){
							if (taskDto.getSubCategoryName() != null) {
								skillNodeIds.add(taskDto.getSkillNodeId());
							} else if (taskDto.getCategoryName() != null) {
								skillNodeIds.add(taskDto.getSkillNodeId());
							} else {
								skillNodeIds.add(so.getPrimarySkillCatId());
							}
							if (taskDto.getServiceTypeId() != null) {
								skillTypes.add(taskDto.getServiceTypeId());
							}
						}
						skillNodeIdList.addAll(skillNodeIds);
						skillTypeIdList.addAll(skillTypes);
					}
					else {
						skillNodeIds.add(so.getPrimarySkillCatId());
						//provSearchVO.setSkillNodeIds(skillNodeIds);
						skillNodeIdList.addAll(skillNodeIds);
					}
				}
			}
			
			searchCriteria.setSkillNodeIds(skillNodeIdList);
			searchCriteria.setSkillServiceTypeId(skillTypeIdList);
			
			
		}catch(Exception e){
			logger.equals("Error occurred in addSiblingSkills --> for groupId " + searchCriteria.getGroupID() + e.getMessage());
		}
		
		return searchCriteria;
		
		
	}


	private ArrayList<ProviderResultVO> getAllProviderData(Integer buyerID,ArrayList<ProviderResultVO> incompleteProviderList
			,HashMap<String, String> serviceLocationCoordinates,Integer spnId){
		logger.info("Inside ProviderSearchBO.getAllProviderData---->>spnId>>"+spnId);
		long start = System.currentTimeMillis();
		ArrayList<ProviderResultVO> finalProviderList = incompleteProviderList;
		ProviderIdsVO vendorResourceIds = extractVendorResourceIds(incompleteProviderList);
		ProviderIdsVO vendorIds = extractVendorIds(incompleteProviderList);
		HashMap<Integer, ProviderResultVO> providerMap = new HashMap<Integer, ProviderResultVO>();
		List<LookupVO> firmPerfScores = new ArrayList<LookupVO>();
		// get the * rating of the given providers
		List<SurveyRatingsVO> providerStarList = null;
		try {			
			//providerStarList = getSurveyDao().getFastLookupRating(buyerID, vendorResourceIds.getProviderIds());
			providerStarList = getExtendedSurveyDAO().getBuyerMyRatings(buyerID, vendorResourceIds.getProviderIds());			
		} catch (DataServiceException e) {
			logger.error("Error in ProviderSearchBO --> getAllProviderData() "+e.getMessage());
			e.printStackTrace();
		}



		// get the credentials info
		List<ProviderCredentialResultsVO> providerCredentialsList = providerSearchDao.getProviderCredentials(vendorResourceIds);

		// get the language info
		List<ProviderLanguageResultsVO> providerLanguageList = providerSearchDao.getProviderLanguages(vendorResourceIds);
		
		// get the spnet state info
		
		long start1 = System.currentTimeMillis();
		List<ProviderSPNetStateResultsVO> providerSPNetList = providerSearchDao.getProviderSPNetStates(vendorResourceIds);
		long end1 = System.currentTimeMillis();
		logger.info("Time taken inside ProvSearchBO fetch providerSPNetList join new:"+(end1-start1));
		logger.info("Inside ProviderSearchBO.getAllProviderData----1");
		if(null!=spnId){
			logger.info("Inside ProviderSearchBO.getAllProviderData----2>>spnId>>"+spnId);
			List<Integer> firmIds =new ArrayList<Integer>();
			for(ProviderResultVO vo: finalProviderList){
				firmIds.add(vo.getVendorID());
			}
		long start2 = System.currentTimeMillis();
		firmPerfScores = fetchFirmPerfScores(spnId,firmIds);
		long end2 = System.currentTimeMillis();
		logger.info("Time taken inside ProvSearchBO fetch firm scores unwanted:"+(end2-start2));
		}
		List<ProviderLocationResultsVO> providerLocationList = null;
		Map<Integer,ProviderLocationResultsVO> providerLocationMap = new HashMap<Integer,ProviderLocationResultsVO>();


		long start5 = System.currentTimeMillis();

		if(serviceLocationCoordinates != null){
			// get the zip info from location table - lat, long
			providerLocationList = providerSearchDao.getProviderLocation(vendorResourceIds);

			if(providerLocationList != null){
				for(int i=0;i<providerLocationList.size();i++){
					ProviderLocationResultsVO locationVo = providerLocationList.get(i);
					if(locationVo != null && locationVo.getZip() != null &&
							(StringUtils.isEmpty(locationVo.getProviderGisLatitude())
							|| StringUtils.isEmpty(locationVo.getProviderGisLongitude())
							|| locationVo.getProviderGisLatitude() == null
							|| locationVo.getProviderGisLongitude() == null)){
						LocationVO latLongLocationVO = providerSearchDao.getZipLatAndLong(locationVo.getZip());

						if(latLongLocationVO != null
								&& latLongLocationVO.getLatitude() != null
								&& latLongLocationVO.getLongitude() != null){
							locationVo.setProviderGisLatitude(latLongLocationVO.getLatitude());
							locationVo.setProviderGisLongitude(latLongLocationVO.getLongitude());
						}

					}

					providerLocationMap.put(locationVo.getProviderResourceId(), locationVo);
				}
			}
		}
		long end5 = System.currentTimeMillis();
		logger.info("Time taken inside ProvSearchBO f get the zip info from location table  looping:"+(end5-start5));

		// get the background status
		List<ProviderBackgroundCheckResultsVO> providerBackgroundStatus = getProviderSearchDao().getProviderBackgroundStatus(vendorResourceIds);

		// get the insurance info
		List<VendorHdrVO> vendorInsuranceList = getProviderSearchDao().getVendorHdr(vendorIds);
		
		//SL-10809 Additional insurance info
		List<AdditionalInsuranceVO> additionalInsuranceList = getProviderSearchDao().getAdditionalInsurance(vendorIds);

		// align data

		// make map


		for(ProviderResultVO aProviderResultVO:finalProviderList){
			ArrayList<ProviderLanguageResultsVO> languages = new ArrayList<ProviderLanguageResultsVO>();
			ArrayList<ProviderCredentialResultsVO> credentials = new ArrayList<ProviderCredentialResultsVO>();
			List<ProviderSPNetStateResultsVO> spnetStates = new ArrayList<ProviderSPNetStateResultsVO>();
			aProviderResultVO.setCredentials(credentials);
			aProviderResultVO.setLanguages(languages);
			aProviderResultVO.setSpnetStates(spnetStates);
			providerMap.put(aProviderResultVO.getResourceId(), aProviderResultVO);
		}


		for(SurveyRatingsVO stars:providerStarList){
			providerMap.get(stars.getVendorResourceID()).setProviderStarRating(stars);
		}

		for(ProviderCredentialResultsVO credential:providerCredentialsList){
			providerMap.get( credential.getProviderResourceId()).getCredentials().add(credential);
		}

		for(ProviderLanguageResultsVO language:providerLanguageList){
			providerMap.get(language.getProviderResourceId()).getLanguages().add(language);
		}
		
		// add list of spns for each resourceID
		for(ProviderSPNetStateResultsVO spnetState:providerSPNetList){
			providerMap.get(spnetState.getProviderResourceId()).getSpnetStates().add(spnetState);
		}

		for(ProviderBackgroundCheckResultsVO backgroundCheckVO:providerBackgroundStatus){
				ProviderResultVO provider = providerMap.get(backgroundCheckVO.getVendorResourceID());
				provider.setBackgroundCheckStatus(backgroundCheckVO.getBackgroundCheckStatus());
				if(backgroundCheckVO.getBackgroundCheckStatus().intValue()
						== ProviderConstants.TEAM_MEMBER_BACKGROUND_CHECK_STATE_CLEAR.intValue())
					provider.setIsSLVerified(true);

		}
		
		ArrayList<InsuranceResultVO> insuranceTypes = null;
		List<Integer> insuranceCredTypeList = null;
		HashMap<Integer, InsuranceResultVO> insuranceMap = null;
		
		for (int i = 0; i < finalProviderList.size(); i++){
			ProviderResultVO provider = finalProviderList.get(i);
			if(serviceLocationCoordinates != null
					&& providerLocationList != null){
				// gis data

				provider.setCity(providerLocationMap.get(provider.getResourceId()).getCity());
				provider.setState(providerLocationMap.get(provider.getResourceId()).getState());
				HashMap<String, String> providerCoordinates = new HashMap<String, String>();
			    providerCoordinates.put ("long", String.valueOf(provider.getProviderLongitude()));
			    providerCoordinates.put ("lat", String.valueOf(provider.getProviderLatitude()));

                try {
                		double distanceInMiles = GISUtil.getDistanceInMiles(serviceLocationCoordinates, providerCoordinates);
                		provider.setDistance(NumberUtils.formatDecimalNumber(distanceInMiles, 2));
				}catch (InsuffcientLocationException e) {
						logger.error("Error in ProviderSearchBO --> getAllProviderData() "+e.getMessage());
						e.printStackTrace();
				}
			}
			insuranceTypes = new ArrayList<InsuranceResultVO>();
			insuranceCredTypeList = new ArrayList<Integer>();
			insuranceCredTypeList.add(ProviderConstants.INSURANCE_GENERAL_LIABILITY);
			insuranceCredTypeList.add(ProviderConstants.INSURANCE_AUTOMOTIVE);
			insuranceCredTypeList.add(ProviderConstants.INSURANCE_WORKERS_COMPENSATION);
			
			insuranceMap = new HashMap<Integer, InsuranceResultVO>();
			
			boolean buildInsuranceMap = false; 
			
			for(VendorHdrVO vendorVO : vendorInsuranceList){				
				
				if(vendorVO.getVendorID().intValue() == provider.getVendorID().intValue()){
					InsuranceResultVO insuranceVO = new InsuranceResultVO();
					
					if(vendorVO.getGeneralLiabilityInsurance() || vendorVO.getVehicalLiabilityInsurance() 
							|| vendorVO.getWorkersCompensationInsurance()){

						if(!buildInsuranceMap){
							if(vendorVO.getGeneralLiabilityInsurance()){
								insuranceVO = buildInsuraceResultVO(vendorVO.getInsGenAmt(), ProviderConstants.INSURANCE_GENERAL_LIABILITY, 
										vendorVO.getWfStateId(), vendorVO.getInsVerifiedDate(),vendorVO.getCredTypeId(), null);
								insuranceMap.put(ProviderConstants.INSURANCE_GENERAL_LIABILITY, insuranceVO);
							}
							if(vendorVO.getVehicalLiabilityInsurance()){
								insuranceVO = buildInsuraceResultVO(vendorVO.getInsVehAmt(), ProviderConstants.INSURANCE_AUTOMOTIVE, 
										vendorVO.getWfStateId(), vendorVO.getInsVerifiedDate(), vendorVO.getCredTypeId(), null);			
								insuranceMap.put(ProviderConstants.INSURANCE_AUTOMOTIVE, insuranceVO);
							}
							if(vendorVO.getWorkersCompensationInsurance()){
								insuranceVO = buildInsuraceResultVO(vendorVO.getInsWorkAmt(), ProviderConstants.INSURANCE_WORKERS_COMPENSATION, 
										vendorVO.getWfStateId(), vendorVO.getInsVerifiedDate(), vendorVO.getCredTypeId(), null);
								insuranceMap.put(ProviderConstants.INSURANCE_WORKERS_COMPENSATION, insuranceVO);
							}
							buildInsuranceMap = true;
						}else{
							if(vendorVO.getCredTypeId() != null){
								if(insuranceMap.containsKey(vendorVO.getCredTypeId())){									
									insuranceVO = buildInsuraceResultVO(null,vendorVO.getCredTypeId() ,vendorVO.getWfStateId(), 
											vendorVO.getInsVerifiedDate(),vendorVO.getCredTypeId(), 
											insuranceMap.get(vendorVO.getCredTypeId()));
									insuranceMap.remove(vendorVO.getCredTypeId());
									insuranceMap.put(vendorVO.getCredTypeId(), insuranceVO);
								}
							}
						}
					}			
				}
			}
	        Iterator It = insuranceMap.keySet().iterator();
	        while (It.hasNext()) {
                Integer insType = (Integer)(It.next());
                	insuranceTypes.add(insuranceMap.get(insType));
                	insuranceCredTypeList.remove(insType);
	        }
	        
			
			if(insuranceTypes.size() <= 3){
				for(Integer insuranceType: insuranceCredTypeList){
					InsuranceResultVO insuranceVO = new InsuranceResultVO();
					insuranceVO.setInsurancePresent(Boolean.FALSE);
					insuranceVO.setVendorInsuranceTypes(insuranceType);
					insuranceTypes.add(insuranceVO);
				}
			}
			
			provider.setVendorInsuranceTypes(insuranceTypes);
			
			//SL-10809 Additional Insurance Types
			
			ArrayList<InsuranceResultVO> addnInsuranceTypes = null;
			List<Integer> addnIinsuranceCredTypeList = null;
			HashMap<Integer, InsuranceResultVO> addnInsuranceMap = null;
			
			addnInsuranceTypes = new ArrayList<InsuranceResultVO>();
			addnIinsuranceCredTypeList = new ArrayList<Integer>();
			addnInsuranceMap = new HashMap<Integer, InsuranceResultVO>();
			
			addnIinsuranceCredTypeList.add(ProviderConstants.EMPLOYEE_DISHONESTY_INS);
			addnIinsuranceCredTypeList.add(ProviderConstants.CARGO_LEGAL_LIABILITY);
			addnIinsuranceCredTypeList.add(ProviderConstants.WAREHOUSEMEN_LEGAL_LIABILITY);
			addnIinsuranceCredTypeList.add(ProviderConstants.UMBRELLA_COVERAGE_INSURANCE);
			addnIinsuranceCredTypeList.add(ProviderConstants.PROFESSIONAL_LIABILITY_INS);
			addnIinsuranceCredTypeList.add(ProviderConstants.ADULT_CARE_INS);
			addnIinsuranceCredTypeList.add(ProviderConstants.NANNY_INS);
			addnIinsuranceCredTypeList.add(ProviderConstants.OTHER_INS);
			addnIinsuranceCredTypeList.add(ProviderConstants.PROFESSIONAL_INDEMNITY_INS);
			
			boolean edi = false;
			boolean cli=false;
			boolean wll=false;
			boolean uci=false;
			boolean pli=false;
			boolean aci=false;
			boolean nai=false;
			boolean oth=false;
			
			
			for(AdditionalInsuranceVO insVO : additionalInsuranceList){				
				
				if(insVO.getVendorId().intValue() == provider.getVendorID().intValue()){
					InsuranceResultVO insuranceVO = new InsuranceResultVO();
					
					if (insVO.getCategoryId().intValue() == ProviderConstants.EMPLOYEE_DISHONESTY_INS
							&& edi == false) {
						insuranceVO = buildAddnInsuraceResultVO(
								insVO.getPolicyAmount(),
								ProviderConstants.EMPLOYEE_DISHONESTY_INS,
								insVO.getPolicyStatus(), null);
						edi = true;
						addnInsuranceMap.put(
								ProviderConstants.EMPLOYEE_DISHONESTY_INS,
								insuranceVO);

					}
					if (insVO.getCategoryId().intValue() == ProviderConstants.CARGO_LEGAL_LIABILITY
							&& cli == false) {
						insuranceVO = buildAddnInsuraceResultVO(
								insVO.getPolicyAmount(),
								ProviderConstants.CARGO_LEGAL_LIABILITY,
								insVO.getPolicyStatus(), null);
						cli = true;
						addnInsuranceMap.put(
								ProviderConstants.CARGO_LEGAL_LIABILITY,
								insuranceVO);
					}
					if (insVO.getCategoryId().intValue() == ProviderConstants.WAREHOUSEMEN_LEGAL_LIABILITY
							&& wll == false) {
						insuranceVO = buildAddnInsuraceResultVO(
								insVO.getPolicyAmount(),
								ProviderConstants.WAREHOUSEMEN_LEGAL_LIABILITY,
								insVO.getPolicyStatus(), null);
						wll = true;
						addnInsuranceMap.put(
								ProviderConstants.WAREHOUSEMEN_LEGAL_LIABILITY,
								insuranceVO);
					}
					if (insVO.getCategoryId().intValue() == ProviderConstants.UMBRELLA_COVERAGE_INSURANCE
							&& uci == false) {
						insuranceVO = buildAddnInsuraceResultVO(
								insVO.getPolicyAmount(),
								ProviderConstants.UMBRELLA_COVERAGE_INSURANCE,
								insVO.getPolicyStatus(), null);
						uci = true;
						addnInsuranceMap.put(
								ProviderConstants.UMBRELLA_COVERAGE_INSURANCE,
								insuranceVO);
					}
					if (insVO.getCategoryId().intValue() == ProviderConstants.PROFESSIONAL_LIABILITY_INS
							&& pli == false) {
						insuranceVO = buildAddnInsuraceResultVO(
								insVO.getPolicyAmount(),
								ProviderConstants.PROFESSIONAL_LIABILITY_INS,
								insVO.getPolicyStatus(), null);
						pli = true;
						addnInsuranceMap.put(
								ProviderConstants.PROFESSIONAL_LIABILITY_INS,
								insuranceVO);
					}
					if (insVO.getCategoryId().intValue() == ProviderConstants.ADULT_CARE_INS
							&& aci == false) {
						insuranceVO = buildAddnInsuraceResultVO(
								insVO.getPolicyAmount(),
								ProviderConstants.ADULT_CARE_INS,
								insVO.getPolicyStatus(), null);
						aci = true;
						addnInsuranceMap.put(ProviderConstants.ADULT_CARE_INS,
								insuranceVO);
					}
					if (insVO.getCategoryId().intValue() == ProviderConstants.NANNY_INS
							&& nai == false) {
						insuranceVO = buildAddnInsuraceResultVO(
								insVO.getPolicyAmount(),
								ProviderConstants.NANNY_INS,
								insVO.getPolicyStatus(), null);
						nai = true;
						addnInsuranceMap.put(ProviderConstants.NANNY_INS,
								insuranceVO);
					}
					if (insVO.getCategoryId().intValue() == ProviderConstants.OTHER_INS
							&& oth == false) {
						insuranceVO = buildAddnInsuraceResultVO(
								insVO.getPolicyAmount(),
								ProviderConstants.OTHER_INS,
								insVO.getPolicyStatus(), null);
						oth = true;
						addnInsuranceMap.put(ProviderConstants.OTHER_INS,
								insuranceVO);
					}
					if (insVO.getCategoryId().intValue() == ProviderConstants.PROFESSIONAL_INDEMNITY_INS
							&& oth == false) {
						insuranceVO = buildAddnInsuraceResultVO(
								insVO.getPolicyAmount(),
								ProviderConstants.PROFESSIONAL_INDEMNITY_INS,
								insVO.getPolicyStatus(), null);
						oth = true;
						addnInsuranceMap.put(
								ProviderConstants.PROFESSIONAL_INDEMNITY_INS,
								insuranceVO);
					}
						
				}
			}
	        Iterator It2 = addnInsuranceMap.keySet().iterator();
	        while (It2.hasNext()) {
                Integer insType = (Integer)(It2.next());
                addnInsuranceTypes.add(addnInsuranceMap.get(insType));
                addnIinsuranceCredTypeList.remove(insType);
	        }
	        
			
			if(addnInsuranceTypes.size() <= 8){
				for(Integer insuranceType: addnIinsuranceCredTypeList){
					InsuranceResultVO insuranceVO = new InsuranceResultVO();
					insuranceVO.setInsurancePresent(Boolean.FALSE);
					insuranceVO.setVendorInsuranceTypes(insuranceType);
					addnInsuranceTypes.add(insuranceVO);
				}
			}
			
			
			provider.setAddtionalInsuranceTypes(addnInsuranceTypes);
			provider.setAdditionalInsuranceList(additionalInsuranceList);
			
			
			//Sl-18226
			for(ProviderSPNetStateResultsVO spNetResultVO :providerSPNetList){
				if(spNetResultVO.getSpnId().equals(spnId) && spNetResultVO.getProviderResourceId().equals(provider.getResourceId()) &&
						((ProviderConstants.SERVICE_PROVIDER_SPNET_APPROVED).equals(spNetResultVO.getProviderSpnetStateId())
						|| (ProviderConstants.SERVICE_PROVIDER_SPNET_APPROVED).equals(spNetResultVO.getAliasProviderSpnetStateId()) )){
					
					    // set filter spn & perfLevel info
						provider.setFilteredPerfLevel(spNetResultVO.getPerformanceLevel());
						provider.setFilterSpnetId(spnId);
						provider.setFilteredSpnName(spNetResultVO.getSpnName());
						provider.setFilteredPerfLevelDesc(spNetResultVO.getPerformanceLevelDesc());
						provider.setPerformanceScore(spNetResultVO.getPerformanceScore());
					   break;
				}
			}
			//add firm performance score
			for(LookupVO vo:firmPerfScores){
				//logger.info("Inside ProviderSearchBO.getAllProviderData----5>>setting firm perfscore>>");
				if(vo.getId().equals(provider.getVendorID())){
					provider.setFirmPerformanceScore(vo.getdId());
				}
			}
			
			finalProviderList.set(i, provider);
			
		}
		long end = System.currentTimeMillis();
		logger.info("Time taken to take all provider data:"+(end-start));
		return finalProviderList;
	}
	
	private InsuranceResultVO buildInsuraceResultVO(Double insAmt, Integer insTypeConstant, 
			Integer wfState, Date insVerifiedDate, Integer insType, InsuranceResultVO insuranceVO){
		if(insuranceVO == null){
			insuranceVO = new InsuranceResultVO();	
			insuranceVO.setInsurancePresent(true);
			if(insAmt != null &&(insAmt==ProviderConstants.INSURANCE_AMOUNT_ZERO || 
					insAmt==ProviderConstants.INSURANCE_AMOUNT_ZERO_1))
				insuranceVO.setAmount(null);
			else
				insuranceVO.setAmount(insAmt);
			insuranceVO.setVendorInsuranceTypes(insTypeConstant);
			if(insType != null && insType.intValue() == insTypeConstant.intValue()){
				if(wfState != null && wfState.intValue() == ProviderConstants.COMPANY_CREDENTIAL_APPROVAL.intValue()){
						insuranceVO.setVerifiedByServiceLive(true);
						insuranceVO.setInsVerifiedDate(insVerifiedDate);
					}
			}
		}else{
			if(insType != null && insType.intValue() == insTypeConstant.intValue()){
				if(wfState != null && wfState.intValue() == ProviderConstants.COMPANY_CREDENTIAL_APPROVAL.intValue()){
						insuranceVO.setVerifiedByServiceLive(true);
						insuranceVO.setInsVerifiedDate(insVerifiedDate);
					}
			}
			
		}
		return insuranceVO;
	}
	
	private InsuranceResultVO buildAddnInsuraceResultVO(Integer insAmt, Integer insTypeConstant, 
			Integer wfState, Date insVerifiedDate){
			InsuranceResultVO insuranceVO = new InsuranceResultVO();
			String dblAmtStr=insAmt.toString();
			Double policyAmt=0.0;
			
			if(dblAmtStr!="")
			{
				policyAmt=Double.parseDouble(dblAmtStr);
			}
			insuranceVO.setAmount(policyAmt);
			if(wfState != null && wfState.intValue() == ProviderConstants.COMPANY_CREDENTIAL_APPROVAL.intValue()){
				insuranceVO.setVerifiedByServiceLive(true);
				insuranceVO.setInsVerifiedDate(insVerifiedDate);
			}
			insuranceVO.setVendorInsuranceTypes(insTypeConstant);
			insuranceVO.setInsurancePresent(true);
			
			return insuranceVO;
			
	}
	

	private ProviderIdsVO extractVendorResourceIds(ArrayList<ProviderResultVO> pl){
		ProviderIdsVO providerIds = new ProviderIdsVO();
		ArrayList<Integer> ids = null;

		if(pl.size() > 0){
			ids = new ArrayList<Integer>();
		}
		for (int i = 0; i < pl.size(); i++){
			ids.add(new Integer(pl.get(i).getResourceId()));
		}
		providerIds.setProviderIds(ids);
		return providerIds;
	}

	private ProviderIdsVO extractVendorIds(ArrayList<ProviderResultVO> pl){
		ProviderIdsVO providerIds = new ProviderIdsVO();
		ArrayList<Integer> ids = null;
		if(pl.size() > 0){
			ids = new ArrayList<Integer>();
		}
		for (int i = 0; i < pl.size(); i++){
			ids.add(new Integer(pl.get(i).getVendorID()));
		}
		providerIds.setProviderIds(ids);
		return providerIds;
	}

	public List<ProviderResultForAdminVO> getProviderListForAdmin(ProviderResultForAdminVO searchCriteria, CriteriaMap criteriaMap) throws BusinessServiceException {
		String businessName = searchCriteria.getBusinessName();
		if (businessName != null){
			searchCriteria.setBusinessName(businessName + "%");
		}
		String userName = searchCriteria.getUsername();
		if (userName != null){
			searchCriteria.setUsername(userName + "%");
		}
		List<ProviderResultForAdminVO> providers = null;
		try{
			SortCriteria sortCriteria = (SortCriteria) criteriaMap
							.get(OrderConstants.SORT_CRITERIA_KEY);
			searchCriteria.setSortColumnName(sortCriteria.getSortColumnName());
			searchCriteria.setSortOrder(sortCriteria.getSortOrder());

			providers = getProviderSearchDao().getMatchingProvidersForAdminSearch(searchCriteria);
		}catch(DataServiceException dse){
			logger.error("Could not retrieve a list of matching providers - database error. ", dse);
			dse.printStackTrace();
			throw new BusinessServiceException(
			"Could not retrieve a list of matching providers - database error. ",
			dse);
		}
		return providers;
	}

	public List<ProviderResultForAdminVO> getProviderListBySkillForAdmin(ProviderResultForAdminVO searchCriteria, CriteriaMap criteriaMap) throws BusinessServiceException {
		List<ProviderResultForAdminVO> providers = null;
		try{
			SortCriteria sortCriteria = (SortCriteria) criteriaMap
							.get(OrderConstants.SORT_CRITERIA_KEY);
			searchCriteria.setSortColumnName(sortCriteria.getSortColumnName());
			searchCriteria.setSortOrder(sortCriteria.getSortOrder());

			providers = getProviderSearchDao().getMatchingProvidersBySkillForAdminSearch(searchCriteria);
		}catch(DataServiceException dse){
			logger.error("Could not retrieve a list of matching providers - database error. ", dse);
			dse.printStackTrace();
			throw new BusinessServiceException(
			"Could not retrieve a list of matching providers - database error. ",
			dse);
		}
		return providers;
	}


	public List<SearchResultForAdminVO> getSearchListForAdmin(SearchResultForAdminVO searchCriteria, CriteriaMap criteriaMap) throws BusinessServiceException {
		List<SearchResultForAdminVO> providers = null;
		try{
			ABaseCriteriaHandler criteriaHandler = new ABaseCriteriaHandler();

			SortCriteria sortCriteria = (SortCriteria) criteriaMap
							.get(OrderConstants.SORT_CRITERIA_KEY);
			searchCriteria.setSortColumnName(sortCriteria.getSortColumnName());
			searchCriteria.setSortOrder(sortCriteria.getSortOrder());
			providers = getProviderSearchDao().getMatchingForAdminSearch(searchCriteria);
		}catch(DataServiceException dse){
			logger.error("Could not retrieve a list of matching data - database error. ", dse);
			dse.printStackTrace();
			throw new BusinessServiceException(
			"Could not retrieve a list of matching data - database error. ",
			dse);
		}
		return providers;
	}



	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.providersearch.IProviderSearchBO#getProviderAdmin(java.lang.Integer)
	 */
	public ProviderResultForAdminVO getProviderAdmin(Integer vendorId)
			throws BusinessServiceException {
		ProviderResultForAdminVO  toReturn = null;
		try {
			toReturn = providerSearchDao.getProviderAdmin(vendorId);
		} catch (DataServiceException e) {
			throw new BusinessServiceException(e.getMessage(),e);
		}
		return toReturn;
	}
	
	

	
	public List<com.newco.marketplace.vo.provider.TeamCredentialsVO> getResourceCredentials(int resourceId) {
		// TODO Auto-generated method stub
		return publicBO.getCredentials(resourceId);		
	}
	
	public List<com.newco.marketplace.vo.provider.VendorCredentialsVO> getVendorCredentials(int vendorId) {
		List <com.newco.marketplace.vo.provider.VendorCredentialsVO> newList
		   = new ArrayList<com.newco.marketplace.vo.provider.VendorCredentialsVO>();
		try {
			List<com.newco.marketplace.dto.vo.provider.VendorCredentialsVO> list
				=  vendorCredentialDAO.queryCredByVendorId(vendorId);
			
			for (com.newco.marketplace.dto.vo.provider.VendorCredentialsVO dto:list) {
				com.newco.marketplace.vo.provider.VendorCredentialsVO von =  
				  new com.newco.marketplace.vo.provider.VendorCredentialsVO();
				von.setCategoryId(dto.getCategoryId());
				von.setCity(dto.getCity());
				von.setCounty(dto.getCounty());
				von.setCreatedDate(dto.getCreatedDate());
				von.setCredCategory(dto.getCredCategory());
				von.setCredentialDocumentId(dto.getCredentialDocumentId());
				von.setCredentialNumber(dto.getCredentialNumber());
				von.setCredType(dto.getCredType());
				von.setEditURL(dto.getEditURL());
				von.setExpirationDate(dto.getExpirationDate());
				von.setIssueDate(dto.getIssueDate());
				von.setModifiedBy(dto.getModifiedBy());
				von.setModifiedDate(dto.getModifiedDate());
				von.setName(dto.getName());
				von.setNotes(dto.getNotes());
				von.setSource(dto.getSource());
				von.setState(dto.getState());
				von.setTypeId(von.getTypeId());
				von.setVendorCredId(dto.getVendorCredId());
				von.setVendorId(dto.getVendorId());
				von.setWfStateId(dto.getWfStateId());
				newList.add(von);
			}
				
		} catch (com.newco.marketplace.exception.DataServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}
		return newList;
	}

	public VendorResource getVendorFromResourceId(int resourceId) throws BusinessServiceException{
		VendorResource vendorResource = new VendorResource();
		vendorResource.setResourceId(resourceId);
		try {
			return venderResourceDao.query(vendorResource);
		} catch (DataServiceException e) {
			throw new BusinessServiceException(e);
		}
	}
	
	/**
	 * @param resourceIds
	 * @return List<VendorResource>
	 * @throws BusinessServiceException
	 */
	
	public List<VendorResource> getVendorResourceList(List<Integer> resourceIds)
			throws BusinessServiceException {
		try {
			return venderResourceDao.getVendorList(resourceIds);
		}catch (DataServiceException e) {
			throw new BusinessServiceException(e);
		}
	}
	public List<CustomerFeedbackVO> getCustomerFeedbacks(int resourceId,
			int count) throws BusinessServiceException {		
		try {
			return pageBO.getCustomerFeedbacks(resourceId, count);
		} catch (com.newco.marketplace.exception.BusinessServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ArrayList<CustomerFeedbackVO>();
	}

	//SL-18226
	public List<LookupVO> fetchFirmPerfScores(Integer spnId, List<Integer> vendorIds){
		return providerSearchDao.fetchFirmPerfScores(spnId, vendorIds);
	}
	
	public SPNetHeaderVO fetchSpnDetails(Integer spnId){
		return (SPNetHeaderVO)providerSearchDao.fetchSpnDetails(spnId);
	 }
	 
	public List<Integer> findRanks(String soId,Integer noOfProvInCurentTier,Integer noOfProvInPreviousTiers){
		 return (List<Integer>)providerSearchDao.findRanks(soId, noOfProvInCurentTier, noOfProvInPreviousTiers);
	 }
	 
	
	 public List<TierRoutedProvider> findProvidersForTierRouting(String soId,Integer noOfProvInCurentTier,Integer noOfProvInPreviousTiers,List<Integer> perfScores){
	    	return (List<TierRoutedProvider>) providerSearchDao.findProvidersForTierRouting(soId, noOfProvInCurentTier, noOfProvInPreviousTiers, perfScores);
	    }
	
	/** @return ProviderSearchDao */
	public ProviderSearchDao getProviderSearchDao() {
		return providerSearchDao;
	}

	/** @param providerSearchDao */
	public void setProviderSearchDao(ProviderSearchDao providerSearchDao) {
		this.providerSearchDao = providerSearchDao;
	}

	/** @return MarketplaceSearchBean */
	public MarketplaceSearchBean getMarketplaceSkillSearch() {
		return marketplaceSkillSearch;
	}

	/** @param marketplaceSkillSearch */
	public void setMarketplaceSkillSearch(
			MarketplaceSearchBean marketplaceSkillSearch) {
		this.marketplaceSkillSearch = marketplaceSkillSearch;
	}
	
	public List<Integer> getResourcesBasedOnProviderResponse(String soId, List<Integer> resourceIds) throws BusinessServiceException {
		try {
			return (List<Integer>)providerSearchDao.getResourcesBasedOnProviderResponse(soId, resourceIds);
		} catch (DataServiceException e) {
			// TODO Auto-generated catch block
			throw new BusinessServiceException(e);
		}
	}

    class ProviderRatingComparator implements Comparator{
    	/**
    	 * @param provid1
    	 * @param provid2
    	 * @return int
    	 */
    	public int compare(Object provid1, Object provid2){
    		ProviderResultVO provider1 = (ProviderResultVO)provid1;
    		ProviderResultVO provider2 = (ProviderResultVO)provid2;
    		Double rating1 = provider1.getProviderRating();
    		Double rating2 = provider2.getProviderRating();

    		int result = 0;
    		if(rating1 != null && rating2 != null){
    			result = rating1.compareTo(rating2) * -1;
    		}
    		return result;
    	}
    }

    class ProviderDistanceComparator implements Comparator{
    	/**
    	 * @param provid1
    	 * @param provid2
    	 * @return int
    	 */
    	public int compare(Object provid1, Object provid2){
    		ProviderResultVO provider1 = (ProviderResultVO)provid1;
    		ProviderResultVO provider2 = (ProviderResultVO)provid2;
    		Double distance1 = provider1.getDistance();
    		Double distance2 = provider2.getDistance();

    		int result = 0;
    		if(distance1 != null && distance2 != null){
    			result = distance1.compareTo(distance2);
    		}
    		return result;
    	}
    }


    class ProviderStarRatingComparator implements Comparator{
    	/**
    	 * @param provid1
    	 * @param provid2
    	 * @return int
    	 */
    	public int compare(Object provid1, Object provid2){
    		ProviderResultVO provider1 = (ProviderResultVO)provid1;
    		ProviderResultVO provider2 = (ProviderResultVO)provid2;    		

    		int result = 0;
    		if (provider1.getProviderStarRating()!= null && provider2.getProviderStarRating()!=null){    		
    			Double starRating1 = provider1.getProviderStarRating().getHistoricalRating();
        		Double starRating2 = provider2.getProviderStarRating().getHistoricalRating();
        		
	    		if(starRating1 != null && starRating2 != null){
	    			result = starRating1.compareTo(starRating2) * -1;
	    		}else if(starRating1 != null && starRating2 == null){
	    			return 1;
	    		}else if (starRating1 == null && starRating2 != null){
	    			return -1;
	    		}
    		}else if(provider1.getProviderStarRating()!= null && provider2.getProviderStarRating()==null){
    			return 1;
    		}else if(provider1.getProviderStarRating()== null && provider2.getProviderStarRating()!=null){
    			return -1;
    		}
    		return result;
    	}
    }

    /**
	 * @param firmIds
	 * @return List<Integer>
	 * @throws BusinessServiceException
	 */
	
	public List<Integer> getValidVendorList(List<Integer> firmIds)
			throws BusinessServiceException {
		try {
			return venderResourceDao.getValidVendorList(firmIds);
		}catch (DataServiceException e) {
			throw new BusinessServiceException(e);
		}
	}	
	
	
	
	public SurveyDAO getSurveyDao() {
		return surveyDao;
	}

	public void setSurveyDao(SurveyDAO surveyDao) {
		this.surveyDao = surveyDao;
	}	

	public ExtendedSurveyDAO getExtendedSurveyDAO() {
		return extendedSurveyDAO;
	}

	public void setExtendedSurveyDAO(ExtendedSurveyDAO extendedSurveyDAO) {
		this.extendedSurveyDAO = extendedSurveyDAO;
	}

	public IMasterCalculatorBO getMasterCalculatorBO() {
		return masterCalculatorBO;
	}

	public void setMasterCalculatorBO(IMasterCalculatorBO masterCalculatorBO) {
		this.masterCalculatorBO = masterCalculatorBO;
	}


	public IOrderGroupBO getOrderGroupBO() {
		return orderGroupBO;
	}


	public void setOrderGroupBO(IOrderGroupBO orderGroupBO) {
		this.orderGroupBO = orderGroupBO;
	}


	public IServiceOrderBO getServiceOrderBO() {
		return serviceOrderBO;
	}


	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}


	public IProviderInfoPagesBO getPageBO() {
		return pageBO;
	}


	public void setPageBO(IProviderInfoPagesBO pageBO) {
		this.pageBO = pageBO;
	}


	public IPublicProfileBO getPublicBO() {
		return publicBO;
	}


	public void setPublicBO(IPublicProfileBO publicBO) {
		this.publicBO = publicBO;
	}


	public Map<String,List> getCheckedSkillsTree(int resourceId) {
		return publicBO.getCheckedSkillsTree(resourceId);		
	}
	
	public Integer getCustomerFeedbacksCount(int resourceId) throws com.newco.marketplace.exception.BusinessServiceException {
		return pageBO.getCustomerFeedbacksCount(resourceId);
	}

	public IVendorCredentialsDao getVendorCredentialDAO() {
		return vendorCredentialDAO;
	}

	public void setVendorCredentialDAO(IVendorCredentialsDao vendorCredentialDAO) {
		this.vendorCredentialDAO = vendorCredentialDAO;
	}

	public void setVenderResourceDao(VendorResourceDao venderResourceDao) {
		this.venderResourceDao = venderResourceDao;
	}
	
	
	


	/* R 16_2_0_1: SL-21376:
	 * Fetching categoryIds for the skuList
	 */
	public List<Integer> getSkuCategoryIds(String buyerId, List<String> skuList) throws BusinessServiceException {
		try {
			return providerSearchDao.getSkuCategoryIds(buyerId,skuList);
		}catch (DataServiceException e) {
			throw new BusinessServiceException(e);
		}
	}
	
	/* R 16_2_0_2: SL-21467:
	 * Fetching skuList for categoryIds 
	 */
	public List<SkuDetailsVO> getSkusForCategoryIds(String buyerId, List<Integer> levelThreeCategories,String keyword) throws BusinessServiceException {
		try {
			return providerSearchDao.getSkusForCategoryIds(buyerId,levelThreeCategories,keyword);
		}catch (DataServiceException e) {
			throw new BusinessServiceException(e);
		}
	}	
	

	public Map<Integer, Long> getReviewCount(List<String> vendorIdList) throws BusinessServiceException {
		try {
			 return providerSearchDao.getOverallReviewCount(vendorIdList);
		}catch (DataServiceException e) {
			throw new BusinessServiceException(e);
		}
	}
	//SL-21468 get the company Logo path
	public Map<Long, String> getCompanyLogo(List<String> vendorIdList) throws BusinessServiceException {
		try {
			 return providerSearchDao.getCompanyLogo(vendorIdList);
		}catch (DataServiceException e) {
			throw new BusinessServiceException(e);
		}
	}
	
	//SL-21468  get values from application_properties table 
	public Map<String, String> getCompanyLogoValues() throws BusinessServiceException{
	try {
		 return providerSearchDao.getCompanyLogoValues();
	}catch (DataServiceException e) {
		throw new BusinessServiceException(e);
	}

	
	}

	public Map<Long,Long> getParentNodeIds(List<Integer> nodeIds) throws BusinessServiceException{
		try {
			return providerSearchDao.getParentNodeIds(nodeIds);
		} catch (DataServiceException e) {
			throw new BusinessServiceException(e);
		}	
		}
	
	public Map<Long,String> getSkillTypList() throws BusinessServiceException{
		try {
			return providerSearchDao.getSkillTypList();
		} catch (DataServiceException e) {
			throw new BusinessServiceException(e);
		}	
		}

	public Map<Long,String> getNodeNames(List<String> nodeIds) throws BusinessServiceException{
		try {
			return providerSearchDao.getNodeNames(nodeIds); 
		} catch (DataServiceException e) {
			throw new BusinessServiceException(e);
		}	
		}


	public List<String> getMainCategoryNames(List<Integer> nodeIds) throws BusinessServiceException{
		try {
			return providerSearchDao.getMainCategoryNames(nodeIds); 
		} catch (DataServiceException e) {
			throw new BusinessServiceException(e);
		}	
		}
	
	public Map<Integer,BasicFirmDetailsVO> getBasicFirmDetails(List<String> firmIds) throws BusinessServiceException{
		List<BasicFirmDetailsVO> basicFirmDetailsVOs = null;
		Map<Integer,BasicFirmDetailsVO> basicFirmDetailsMap=new HashMap<Integer,BasicFirmDetailsVO>();
		try {	

		basicFirmDetailsVOs =  providerSearchDao.getBasicFirmDetails(firmIds);   
		
		for(BasicFirmDetailsVO basicDetail:basicFirmDetailsVOs){			
		basicFirmDetailsMap.put(basicDetail.getFirmId(),basicDetail);				
		}
		}
		catch (DataServiceException e) {
			throw new BusinessServiceException(e);
		}	
	
	  return basicFirmDetailsMap;
	}

	
	
	public Map<String,List<FirmServiceVO>> getVendorServiceDetails(List<String> firmIdList,List<Integer> categoryIdList) throws BusinessServiceException {

		Map<String,List<FirmServiceVO>> firmServiceVOs = new HashMap<String,List<FirmServiceVO>>();
		List<FirmServiceVO> servicesList = null;

		try{
			//fetch the services for the list of firms
			servicesList = providerSearchDao.getVendorServiceDetails(firmIdList,categoryIdList);
			if(null != servicesList && !servicesList.isEmpty()){

				//construct a map with firmId as key and list of services as value
				for(FirmServiceVO serviceVO : servicesList){
					if(null != serviceVO && StringUtils.isNotBlank(serviceVO.getFirmId())){
						String firmId = serviceVO.getFirmId();
						if(firmServiceVOs.containsKey(firmId)){
							List<FirmServiceVO> serviceList = firmServiceVOs.get(firmId);
							if(null != serviceList && !serviceList.isEmpty()){
								serviceList.add(serviceVO);
								firmServiceVOs.put(firmId, serviceList);
							}
						}else{
							List<FirmServiceVO> serviceList = new ArrayList<FirmServiceVO>();
							serviceList.add(serviceVO);
							firmServiceVOs.put(firmId, serviceList);
						}
					}
				}
			}
		}catch (DataServiceException dse) {
			throw new BusinessServiceException(dse);
		}

		return firmServiceVOs;
	}
	
	
	
	


}
