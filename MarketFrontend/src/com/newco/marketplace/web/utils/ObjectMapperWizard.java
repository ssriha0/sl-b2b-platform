package com.newco.marketplace.web.utils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.business.businessImpl.providerSearch.rating.BackgroundCheckParameterBean;
import com.newco.marketplace.business.businessImpl.providerSearch.rating.CredentialParameterBean;
import com.newco.marketplace.business.businessImpl.providerSearch.rating.InsuranceParameterBean;
import com.newco.marketplace.business.businessImpl.providerSearch.rating.InsuranceRatingParameterBean;
import com.newco.marketplace.business.businessImpl.providerSearch.rating.LanguageParameterBean;
import com.newco.marketplace.business.businessImpl.providerSearch.rating.SPNetParameterBean;
import com.newco.marketplace.business.businessImpl.providerSearch.rating.StarParameterBean;
import com.newco.marketplace.business.businessImpl.providerSearch.rating.ZipParameterBean;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.InsuranceRatingsLookupVO;
import com.newco.marketplace.dto.vo.LocationVO;
import com.newco.marketplace.dto.vo.LookupVO;
import com.newco.marketplace.dto.vo.SOWorkflowControlsVO;
import com.newco.marketplace.dto.vo.provider.AdditionalInsuranceVO;
import com.newco.marketplace.dto.vo.provider.VendorResource;
import com.newco.marketplace.dto.vo.providerSearch.InsuranceResultVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.dto.vo.providerSearch.RatingParameterBean;
import com.newco.marketplace.dto.vo.serviceorder.Carrier;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.dto.vo.serviceorder.ContactLocationVO;
import com.newco.marketplace.dto.vo.serviceorder.Part;
import com.newco.marketplace.dto.vo.serviceorder.PhoneVO;
import com.newco.marketplace.dto.vo.serviceorder.RoutedProvider;
import com.newco.marketplace.dto.vo.serviceorder.SODocument;
import com.newco.marketplace.dto.vo.serviceorder.SOTaskPriceHistoryVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderAddonVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderAltBuyerContactVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderBrandingInfoVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderCustomRefVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderTask;
import com.newco.marketplace.dto.vo.serviceorder.SoLocation;
import com.newco.marketplace.dto.vo.serviceorder.TierRoutedProvider;
import com.newco.marketplace.dto.vo.sitestatistics.PopularServicesVO;
import com.newco.marketplace.gwt.providersearch.client.SimpleProviderSearchProviderResultVO;
import com.newco.marketplace.gwt.providersearch.client.SimpleProviderSearchSkillTypeVO;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.interfaces.ProviderConstants;
import com.newco.marketplace.utils.DateUtils;
import com.newco.marketplace.utils.UIUtils;
import com.newco.marketplace.web.dto.AdditionalInsuranceDTO;
import com.newco.marketplace.web.dto.ProviderSPNDTO;
import com.newco.marketplace.web.dto.SODocumentDTO;
import com.newco.marketplace.web.dto.SOTaskDTO;
import com.newco.marketplace.web.dto.SOTaskPriceHistoryDTO;
import com.newco.marketplace.web.dto.SOWAdditionalInfoTabDTO;
import com.newco.marketplace.web.dto.SOWAltBuyerContactDTO;
import com.newco.marketplace.web.dto.SOWBrandingInfoDTO;
import com.newco.marketplace.web.dto.SOWContactLocationDTO;
import com.newco.marketplace.web.dto.SOWCustomRefDTO;
import com.newco.marketplace.web.dto.SOWInsuranceDTO;
import com.newco.marketplace.web.dto.SOWPartDTO;
import com.newco.marketplace.web.dto.SOWPartsTabDTO;
import com.newco.marketplace.web.dto.SOWPhoneDTO;
import com.newco.marketplace.web.dto.SOWPricingTabDTO;
import com.newco.marketplace.web.dto.SOWProviderDTO;
import com.newco.marketplace.web.dto.SOWProviderSearchDTO;
import com.newco.marketplace.web.dto.SOWProvidersTabDTO;
import com.newco.marketplace.web.dto.SOWScopeOfWorkTabDTO;
import com.newco.marketplace.web.dto.ServiceOrderDTO;
import com.newco.marketplace.web.dto.simple.CreateServiceOrderAddFundsDTO;
import com.newco.marketplace.web.dto.simple.CreateServiceOrderDescribeAndScheduleDTO;
import com.newco.marketplace.web.dto.simple.CreateServiceOrderFindProvidersDTO;
import com.newco.marketplace.web.dto.simple.CreateServiceOrderReviewDTO;

/**
 * $Revision: 1.148 $ $Author: cnair $ $Date: 2008/06/06 11:41:05 $
 */

/*
 * Maintenance History: See bottom of file 
 */
public class ObjectMapperWizard {
	
	private static final Logger logger = Logger.getLogger(ObjectMapperWizard.class);

	public static Date getDateFromString(String dateStr, String formatStr) {
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			logger.info("Caught Exception and ignoring",e);
		}
		return date;
	}

	public static String getStringFromTimeStamp(Timestamp timeStamp) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = null;
		try {
			date = sdf.parse(timeStamp.toString());
		} catch (ParseException e) {
			logger.info("Caught Exception and ignoring",e);
		}
		return sdf.format(date);
	}

	public static HashMap<String, Object> convertSOTOSOWTabDTOs(ServiceOrder so) {

		HashMap<String, Object> sowTabDtoMap = new HashMap<String, Object>();
		
		SOWScopeOfWorkTabDTO scopeOfWorkDto = assignServiceOrderVOToScopeOfWorkDTO(so);
		sowTabDtoMap.put(OrderConstants.SOW_SOW_TAB, scopeOfWorkDto);

		SOWAdditionalInfoTabDTO addInfoDto = assignServiceOrderVOToAddtionalInfoDTO(so);
		sowTabDtoMap.put(OrderConstants.SOW_ADDITIONAL_INFO_TAB, addInfoDto);

		SOWPartsTabDTO partsTabDto = assignServiceOrderVOToPartsDto(so);
		sowTabDtoMap.put(OrderConstants.SOW_PARTS_TAB, partsTabDto);

		SOWProvidersTabDTO providerDto = assingServiceOrderVOToProvidersDto(so);
		sowTabDtoMap.put(OrderConstants.SOW_PROVIDERS_TAB, providerDto);

		SOWPricingTabDTO pricingTabDto = assignServiceOrderVoToPricingDto(so,sowTabDtoMap);
		sowTabDtoMap.put(OrderConstants.SOW_PRICING_TAB, pricingTabDto);
		
		return sowTabDtoMap;
	}
	
	public static HashMap<String, Object> convertGroupSOTOSOWTabDTOs(List<ServiceOrder> serviceOrders) {

		HashMap<String, Object> sowTabDtoMap = new HashMap<String, Object>();
		SOWScopeOfWorkTabDTO scopeOfWorkDto = new SOWScopeOfWorkTabDTO();
		SOWAdditionalInfoTabDTO addInfoDto = new SOWAdditionalInfoTabDTO();
		SOWPartsTabDTO partsTabDto = new SOWPartsTabDTO();
		SOWProvidersTabDTO providerDto = new SOWProvidersTabDTO();
		SOWPricingTabDTO pricingTabDto = new SOWPricingTabDTO();
		
		for (ServiceOrder so : serviceOrders) {
			
			scopeOfWorkDto = assignServiceOrderVOToScopeOfWorkDTO(so);
			sowTabDtoMap.put(OrderConstants.SOW_SOW_TAB, scopeOfWorkDto);
	
			addInfoDto = assignServiceOrderVOToAddtionalInfoDTO(so);
			sowTabDtoMap.put(OrderConstants.SOW_ADDITIONAL_INFO_TAB, addInfoDto);
	
			partsTabDto = assignServiceOrderVOToPartsDto(so);
			sowTabDtoMap.put(OrderConstants.SOW_PARTS_TAB, partsTabDto);
	
			providerDto = assingServiceOrderVOToProvidersDto(so);
			sowTabDtoMap.put(OrderConstants.SOW_PROVIDERS_TAB, providerDto);
	
			pricingTabDto = assignServiceOrderVoToPricingDto(so,sowTabDtoMap);
			sowTabDtoMap.put(OrderConstants.SOW_PRICING_TAB, pricingTabDto);
		}
		
		return sowTabDtoMap;
	}	

	public static CreateServiceOrderDescribeAndScheduleDTO convertLocationVOToDescribeDTO(LocationVO locationVO, CreateServiceOrderDescribeAndScheduleDTO describeDTO) {
		return assignLocation(locationVO, describeDTO);
	}

	public static ServiceOrder convertSOWTabDTOsTOSO(
			HashMap<String, Object> sowTabDtoMap, String soId) {
		ServiceOrder soVO = new ServiceOrder();
		// String soId = (String) sowTabDtoMap.get(OrderConstants.SO_ID);
		soVO.setSoId(soId);
		SOWScopeOfWorkTabDTO scopeOfWorkDto = (SOWScopeOfWorkTabDTO) sowTabDtoMap
				.get(OrderConstants.SOW_SOW_TAB);
		SOWAdditionalInfoTabDTO addInfoDto = (SOWAdditionalInfoTabDTO) sowTabDtoMap
				.get(OrderConstants.SOW_ADDITIONAL_INFO_TAB);
		SOWPartsTabDTO partsTabDto = (SOWPartsTabDTO) sowTabDtoMap
				.get(OrderConstants.SOW_PARTS_TAB);
		SOWProvidersTabDTO providerDto = (SOWProvidersTabDTO) sowTabDtoMap
				.get(OrderConstants.SOW_PROVIDERS_TAB);
		SOWPricingTabDTO pricingTabDto = (SOWPricingTabDTO) sowTabDtoMap
				.get(OrderConstants.SOW_PRICING_TAB);
		ServiceOrderDTO reviewTabDto = (ServiceOrderDTO) sowTabDtoMap
				.get(OrderConstants.SOW_REVIEW_TAB);
		assignScopeOfWorkToServiceOrderVO(soVO, scopeOfWorkDto);

		assignAddtionalInfoToServiceOrderVO(soVO, addInfoDto);

		assignPartsDtoToServiceOrderVO(soVO, partsTabDto);
		
		// The PricingDto must be assigned to the ServiceOrderVO before the ProvidersDto
		assignPricingDtoToServiceOrderVo(soVO, pricingTabDto);

		assingProvidersDtoToServiceOrderVO(soVO, providerDto);

		assignReviewTabDtoToServiceOrderVO(soVO, reviewTabDto);

		return soVO;
	}
	
	public static HashMap<String, Object> convertSOtoSSODTOs(ServiceOrder so) {
		HashMap<String, Object> ssoDtoMap = new HashMap<String, Object>();
		CreateServiceOrderDescribeAndScheduleDTO describeDTO = assignServiceOrderVOToDescribeDto(so);
		ssoDtoMap.put(OrderConstants.SSO_DESCRIBE_AND_SCHEDULE_DTO, describeDTO);

		CreateServiceOrderFindProvidersDTO findProvidersDTO = assignServiceOrderVOToFindProvidersDTO(so);
		ssoDtoMap.put(OrderConstants.SSO_FIND_PROVIDERS_DTO, findProvidersDTO);

		CreateServiceOrderReviewDTO reviewDto = assignServiceOrderVOToReviewDto(so);
		ssoDtoMap.put(OrderConstants.SSO_ORDER_REVIEW_DTO, reviewDto);
		return ssoDtoMap;
	}

	public static ServiceOrder convertSSODTOstoSO(
			HashMap<String, Object> sowTabDtoMap, String soId) {
		ServiceOrder soVO = new ServiceOrder();
		soVO.setSoId(soId);
		CreateServiceOrderDescribeAndScheduleDTO describeDTO = (CreateServiceOrderDescribeAndScheduleDTO)sowTabDtoMap
				.get(OrderConstants.SSO_DESCRIBE_AND_SCHEDULE_DTO);
		CreateServiceOrderFindProvidersDTO findProvidersDTO = (CreateServiceOrderFindProvidersDTO)sowTabDtoMap
				.get(OrderConstants.SSO_FIND_PROVIDERS_DTO);

		assignServiceLiveBucksAgreement(sowTabDtoMap, soVO);
				
		assignDescribeDtoToServiceOrderVO(soVO, describeDTO);
		// for SSO for simple buyer...the buyer support location is same as the service location...
		// code is added here as the info is obtained as part of the mapping from describescheduleDTO
		SoLocation bsLocation = populateBSLocationforSB(soVO.getServiceLocation());
		soVO.setBuyerSupportLocation(bsLocation);
		// Sets the flag to display the message for provider confirmation
		soVO.setProviderServiceConfirmInd(OrderConstants.PROVIDER_CONFIRM_SERVICE_TIME);
		

		assignFindProvidersDtoToServiceOrderVO(soVO, findProvidersDTO);

		return soVO;
	}

	/**
	 * @param sowTabDtoMap
	 * @param soVO
	 */
	private static void assignServiceLiveBucksAgreement(
			HashMap<String, Object> sowTabDtoMap, ServiceOrder soVO) {
		CreateServiceOrderAddFundsDTO createServiceOrderAddFundsDTO  = null;
		 if(sowTabDtoMap.get(OrderConstants.SSO_ADD_FUNDS_DTO) != null ) {
			 createServiceOrderAddFundsDTO = (CreateServiceOrderAddFundsDTO)sowTabDtoMap.get(OrderConstants.SSO_ADD_FUNDS_DTO);
			 if(createServiceOrderAddFundsDTO.getSlBucksAgreeInd() != null && createServiceOrderAddFundsDTO.getSlBucksAgreeInd().booleanValue() == true){
				 if(createServiceOrderAddFundsDTO.getSlBucksAgreeId() != null ) {
					 soVO.setServiceLiveBuckAgrementId(createServiceOrderAddFundsDTO.getSlBucksAgreeId());
					 soVO.setServiceLiveBuckAgrementAcceptedDt(new Timestamp(new Date().getTime()));
				 }
			 }
		 }
	}

	private static void assignFindProvidersDtoToServiceOrderVO(
			ServiceOrder soVO,
			CreateServiceOrderFindProvidersDTO findProvidersDTO) {
		
		// TODO This needs to be validated.
		
		List<ServiceOrderTask> tasks = new ArrayList<ServiceOrderTask>();
		List serviceTypesList = findProvidersDTO.getCheckedJobs();
		if(serviceTypesList != null){
			for (int i = 0; i < serviceTypesList.size(); i++) {
				ServiceOrderTask aTask = new ServiceOrderTask();
				aTask.setServiceType(((SimpleProviderSearchSkillTypeVO)serviceTypesList.get(i)).getSkillTypeDescr());
				if(findProvidersDTO.getSelectedCategorysVO().getSubCategoryId()>0){
					aTask.setSkillNodeId(findProvidersDTO.getSelectedCategorysVO().getSubCategoryId());
				}else{
					if(findProvidersDTO.getSelectedCategorysVO().getCategoryId()>0){
						aTask.setSkillNodeId(findProvidersDTO.getSelectedCategorysVO().getCategoryId());					
					}
					else{
						aTask.setSkillNodeId(findProvidersDTO.getSelectedCategorysVO().getMainCategoryId());
					}
				}
				soVO.setPrimarySkillCatId(findProvidersDTO.getSelectedCategorysVO().getMainCategoryId());
				aTask.setCategoryName(findProvidersDTO.getSelectedCategorysVO().getCategoryName());
				aTask.setServiceTypeId(((SimpleProviderSearchSkillTypeVO)serviceTypesList.get(i)).getSkillTypeId());
				tasks.add(aTask);
			}
		}
		List<RoutedProvider> routedProviders = new ArrayList<RoutedProvider>();
		List<VendorResource> providers = new ArrayList<VendorResource>();
		List<SimpleProviderSearchProviderResultVO> myList = findProvidersDTO.getSelectedProviders();
		if(myList != null){
			for (int i = 0; i < myList.size(); i++) {
				SimpleProviderSearchProviderResultVO providerDto = myList.get(i);
				// set Providers
				VendorResource provider = new VendorResource();
				provider.setResourceId(providerDto.getResourceId()); 
				provider.setVendorId(providerDto.getVendorID()); 
				provider.setSoId(soVO.getSoId()); 
			
				providers.add(provider);
				// set routed Pro
				RoutedProvider routedProvider = new RoutedProvider();
				routedProvider.setResourceId(providerDto.getResourceId()); 
				routedProvider.setVendorId(providerDto.getVendorID()); 
				routedProvider.setSoId(soVO.getSoId()); 
				
				if (providerDto.getCreatedDate() != null) {
					routedProvider
							.setCreatedDate(new Timestamp(
									providerDto.getCreatedDate()
											.getTime()));
				} else {
					routedProvider.setCreatedDate(new Timestamp(
							DateUtils.getCurrentDate().getTime()));
					providerDto.setCreatedDate(new Timestamp(
							DateUtils.getCurrentDate().getTime()));
				}
				
				routedProviders.add(routedProvider);
			}
		}
		soVO.setRoutedResources(routedProviders);
		soVO.setProviders(providers);
		soVO.setTasks(tasks);
	}
	
	private static CreateServiceOrderFindProvidersDTO assignServiceOrderVOToFindProvidersDTO(
			ServiceOrder so ) {
		// TODO Auto-generated method stub
		CreateServiceOrderFindProvidersDTO aFindProvidersDTO = new CreateServiceOrderFindProvidersDTO();
		//List<PopularServicesVO> holderPopularService = new ArrayList(); // this is for the checked jobs
		aFindProvidersDTO.setSo_id(so.getSoId());
		PopularServicesVO aPopServVo = new PopularServicesVO();
		SoLocation alocation = so.getServiceLocation();
		if(alocation != null){
			aFindProvidersDTO.setZip(alocation.getZip());
			aFindProvidersDTO.setState(alocation.getState());
		}
		List skillTypesList = new ArrayList();		
		for(ServiceOrderTask aTask:so.getTasks()){
			aPopServVo.setCategoryName(aTask.getCategoryName());
			aPopServVo.setSubCategoryName(aTask.getSubCategoryName());
			aPopServVo.setMainCategoryId(so.getPrimarySkillCatId());
			
			
			if (aTask.getSkillNodeId() != null
					&& aTask.getLevel() == 3) {
				aPopServVo.setSubCategoryId(aTask.getSkillNodeId());
				aPopServVo.setCategoryId(aTask.getParentId());
			} else if (aTask.getSkillNodeId() != null
					&& aTask.getLevel() == 2) {
				aPopServVo.setCategoryId(aTask.getSkillNodeId());
			}
			
			aPopServVo.setServiceTypeTemplateId(aTask.getServiceTypeId());
			//holderPopularService.add(aPopServVo);
			//DO The population of this Vo in the GWT remoteService using the ServiceTemplateId
			SimpleProviderSearchSkillTypeVO aSimpleProviderSearchSkillTypeVO = new SimpleProviderSearchSkillTypeVO();
			aSimpleProviderSearchSkillTypeVO.setSkillTypeDescr(aTask.getServiceType());
			aSimpleProviderSearchSkillTypeVO.setSkillTypeId(aTask.getServiceTypeId());
			skillTypesList.add(aSimpleProviderSearchSkillTypeVO);
		}
		
		List <SimpleProviderSearchProviderResultVO> gwtproviders = new ArrayList();
		//do the providers crap here...
		//per mike I m giving routedproviders a priority .. so if the routed provider is populated I ignore the providers
		if ( so.getRoutedResources() != null){
			List<RoutedProvider> providers = 	so.getRoutedResources(); // new VendorResource();
			 
			for(RoutedProvider provider:providers){
				SimpleProviderSearchProviderResultVO gwtprov = new SimpleProviderSearchProviderResultVO();
				gwtprov.setResourceId(provider.getResourceId().intValue());
				gwtproviders.add(gwtprov);
			}
			
		}else if(so.getProviders() != null){
			List<VendorResource> providers = 	so.getProviders(); // new VendorResource();
			 
			for(VendorResource provider:providers){
				SimpleProviderSearchProviderResultVO gwtprov = new SimpleProviderSearchProviderResultVO();
				gwtprov.setResourceId(provider.getResourceId().intValue());
				gwtproviders.add(gwtprov);
			}
			aFindProvidersDTO.setSelectedProviders(gwtproviders);
			
		}
		//aFindProvidersDTO.setCategoriesHolderForTreeOfSO(holderPopularService);
		aFindProvidersDTO.setSelectedProviders(gwtproviders);
		aFindProvidersDTO.setCheckedJobs(skillTypesList);
		aFindProvidersDTO.setSelectedCategorys(aPopServVo);
		return aFindProvidersDTO;
	
	}

	private static void assignDescribeDtoToServiceOrderVO(ServiceOrder soVO,
			CreateServiceOrderDescribeAndScheduleDTO describeDTO) {
		//Set title, description and provider instruction
		soVO.setSowTitle(describeDTO.getProjectName());
		soVO.setSowDs(describeDTO.getProjectDesc());
		soVO.setProviderInstructions(describeDTO.getAddnlInstructions());
		
		//Set service location
		SoLocation location = new SoLocation();
		location.setAptNo(describeDTO.getAptNo());
		location.setLocnName(describeDTO.getLocationName());
		location.setStreet1(describeDTO.getStreet1());
		location.setStreet2(describeDTO.getStreet2());
		location.setCity(describeDTO.getCity());
		location.setZip(describeDTO.getZip());
		location.setState(describeDTO.getStateCd());
		soVO.setServiceLocation(location);
		
		//Set Date and time
		soVO.setServiceLocationTimeZone(describeDTO.getTimeZone());
		if (describeDTO.getServiceDateType().equals(new Integer(OrderConstants.FIXED_DATE))) {
			soVO.setServiceDateTypeId(new Integer(OrderConstants.FIXED_DATE));
			soVO.setServiceDate1(com.newco.marketplace.util.TimestampUtils.getTimestampFromString(
					describeDTO.getFixedServiceDate(), "yyyy-MM-dd"));
			soVO.setServiceTimeStart(describeDTO.getStartTime());
			soVO.setServiceTimeEnd(describeDTO.getEndTime());
		}
		else{
			soVO.setServiceDateTypeId(new Integer(OrderConstants.RANGE_DATE));
			soVO.setServiceDate1(com.newco.marketplace.util.TimestampUtils.getTimestampFromString(
					describeDTO.getServiceDate1Text(), "yyyy-MM-dd"));
			soVO.setServiceDate2(com.newco.marketplace.util.TimestampUtils.getTimestampFromString(
					describeDTO.getServiceDate2Text(), "yyyy-MM-dd"));
			soVO.setServiceTimeStart("08:00 AM");
			soVO.setServiceTimeEnd("05:00 PM");
		}
		
		//Set cost
		soVO.setSpendLimitLabor(Double.parseDouble(describeDTO.getLaborLimit()));
		if (!describeDTO.isProvideAllMaterials()) {
			soVO.setSpendLimitParts(Double.parseDouble(describeDTO.getMaterialsLimit()));
		}
		else
			soVO.setSpendLimitParts(0.0);
		soVO.setPostingFee(Double.parseDouble(describeDTO.getPostingFee()));
		
	}
	
	private static CreateServiceOrderDescribeAndScheduleDTO assignLocation(LocationVO locationVO, CreateServiceOrderDescribeAndScheduleDTO describeDTO) {
		//Set service location
		describeDTO.setAptNo(locationVO.getAptNo());
		describeDTO.setStreet1(locationVO.getStreet1());
		describeDTO.setStreet2(locationVO.getStreet2());
		describeDTO.setCity(locationVO.getCity());
		describeDTO.setZip(locationVO.getZip());
		describeDTO.setStateCd(locationVO.getState());
		describeDTO.setLocationName(locationVO.getLocName());
		return describeDTO;
	}
	
	private static CreateServiceOrderDescribeAndScheduleDTO assignServiceOrderVOToDescribeDto(ServiceOrder soVO) {
		CreateServiceOrderDescribeAndScheduleDTO describeDTO = new CreateServiceOrderDescribeAndScheduleDTO();
		//Set title, description and provider instruction
		describeDTO.setProjectName(soVO.getSowTitle());
		describeDTO.setProjectDesc(soVO.getSowDs());
		describeDTO.setAddnlInstructions(soVO.getProviderInstructions());
		
		
		//Set service location
		SoLocation location = soVO.getServiceLocation();
		describeDTO.setAptNo(location.getAptNo());
		describeDTO.setLocationName(location.getLocnName());
		describeDTO.setStreet1(location.getStreet1());
		describeDTO.setStreet2(location.getStreet2());
		describeDTO.setCity(location.getCity());
		describeDTO.setZip(location.getZip());
		describeDTO.setStateCd(location.getState());
				
		//Set Date and time
		describeDTO.setTimeZone(soVO.getServiceLocationTimeZone());
		if (soVO.getServiceDate2() == null) {
			describeDTO.setFixedServiceDate(com.newco.marketplace.util.TimestampUtils.
					getStringFromTimestamp(soVO.getServiceDate1(), "yyyy-MM-dd"));
			describeDTO.setStartTime(soVO.getServiceTimeStart());
			describeDTO.setEndTime(soVO.getServiceTimeEnd());
			describeDTO.setServiceDateType(Integer.parseInt(OrderConstants.FIXED_DATE));
		}
		else{
			describeDTO.setServiceDate1Text(com.newco.marketplace.util.TimestampUtils.
					getStringFromTimestamp(soVO.getServiceDate1(), "yyyy-MM-dd"));
			describeDTO.setServiceDate2Text(com.newco.marketplace.util.TimestampUtils.
					getStringFromTimestamp(soVO.getServiceDate2(), "yyyy-MM-dd"));
			describeDTO.setServiceDateType(Integer.parseInt(OrderConstants.RANGE_DATE));
		}
		
		//Set cost
		describeDTO.setLaborLimit(new DecimalFormat("0.00").format(soVO.getSpendLimitLabor()));
		if (soVO.getSpendLimitParts() != 0.0) {
			describeDTO.setMaterialsLimit(new DecimalFormat("0.00").format(soVO.getSpendLimitParts()));
			describeDTO.setProvideAllMaterials(false);
		}
		else {
			describeDTO.setProvideAllMaterials(true);
		}
		describeDTO.setWfStateId(soVO.getWfStateId());
		return describeDTO;
	}
	
	private static CreateServiceOrderReviewDTO assignServiceOrderVOToReviewDto(
			ServiceOrder so) {
		CreateServiceOrderReviewDTO reviewDto = new CreateServiceOrderReviewDTO();
		reviewDto.setOrderNumber(so.getSoId());
		reviewDto.setSoStatusId(so.getWfStateId());
		return reviewDto;
	}

	private static void assignPricingDtoToServiceOrderVo(ServiceOrder soVo,	SOWPricingTabDTO pricingTabDto) 
	{
		if (pricingTabDto != null)
		{
			
			boolean shareContact = pricingTabDto.isShareContactInd();
			boolean sealedBidInd =pricingTabDto.isSealedBidInd();
			
			
			String orderType = pricingTabDto.getOrderType();
			
			Double tempLabourSpendLimit = null;
			Double tempPartsSpendLimit = null;
			
			if (orderType != null) {
				if (orderType.equals(Constants.PriceModel.ZERO_PRICE_BID)) {
					soVo.setPriceModel(Constants.PriceModel.ZERO_PRICE_BID);
					soVo.setShareContactInd(shareContact);
					soVo.setSealedBidInd(sealedBidInd);
				} else {
					soVo.setPriceModel(Constants.PriceModel.NAME_PRICE);
					tempLabourSpendLimit = SLStringUtils.getMonetaryNumber(pricingTabDto.getLaborSpendLimit());
//					if(soVo.isSkuTaskIndicator()==true)
//					{
//						tempLabourSpendLimit=soVo.getSpendLimitLabor();
//					}
//					else
//						{
						
					//	}
					tempPartsSpendLimit = SLStringUtils.getMonetaryNumber(pricingTabDto.getPartsSpendLimit());
					soVo.setShareContactInd(false);
					soVo.setSealedBidInd(false);
				}
			}
			//map group price in case if it is a group order
			soVo.setGroupLaborSpendLimit(new BigDecimal(pricingTabDto.getOgLaborSpendLimit()));
			soVo.setGroupPartsSpendLimit(new BigDecimal(pricingTabDto.getOgPartsSpendLimit()));
			soVo.setTotalGroupPermitPrice(new BigDecimal(pricingTabDto.getGroupTotalPermits()));
			
			
			if (tempLabourSpendLimit != null)
			{
				soVo.setSpendLimitLabor(tempLabourSpendLimit);
			}
			else{
				soVo.setSpendLimitLabor(0.0);
			}
			
			if (tempPartsSpendLimit != null)
			{
				soVo.setSpendLimitParts(tempPartsSpendLimit);
			}
			else{
				soVo.setSpendLimitParts(0.0);
			}
			

			Double oldSpendLimit;
			if (orderType.equals(Constants.PriceModel.ZERO_PRICE_BID) || pricingTabDto.getInitialLaborSpendLimit() == null || pricingTabDto.getInitialPartsSpendLimit() == null) {
				oldSpendLimit = 0.0;
			} else {
				oldSpendLimit = pricingTabDto.getInitialLaborSpendLimit() + pricingTabDto.getInitialPartsSpendLimit() + pricingTabDto.getPostingFee();
			}
			
			soVo.setFundingTypeId(pricingTabDto.getFundingType());
			soVo.setInitialPrice(oldSpendLimit);
			
			soVo.setRetailPrice(pricingTabDto.getBillingEstimate());
			soVo.setTaskLevelPricingInd(pricingTabDto.isTaskLevelPricingInd());
			//SL-20527 : Setting Total Permit Price from Pricing DTO
			soVo.setTotalPermitPrice(pricingTabDto.getPermitPrepaidPrice());
			logger.info("SL-20527 : Total Permit price set in DTO"+pricingTabDto.getPermitPrepaidPrice());
			
				
			
		}//end if (tabDto != null)
		
	}//end method

	public static ArrayList<SOWProviderDTO> assingProviderResultVOToProvidersDto(
			List<ProviderResultVO> providersVo) {
		ArrayList<SOWProviderDTO> providersDto = new ArrayList<SOWProviderDTO>();
		try {
			if (providersVo != null) {

				if (providersVo != null && providersVo.size() > 0) {
					List<InsuranceResultVO> insuranceTypes = null;

					for (int i = 0; i < providersVo.size(); i++) {
						ProviderResultVO providerResultVo = providersVo.get(i);
						SOWProviderDTO providerDto = new SOWProviderDTO();
						
						//SL-10809 Additional Insurance
						List<AdditionalInsuranceVO> insList=providerResultVo.getAdditionalInsuranceList();
						List<AdditionalInsuranceDTO> aditionalInsuranceDTOList=new ArrayList<AdditionalInsuranceDTO>();
						if(!insList.isEmpty())
						{
							for(AdditionalInsuranceVO insurancItem:insList)
							{
								AdditionalInsuranceDTO addInsDTO=new AdditionalInsuranceDTO();
								addInsDTO.setVendorCredId(insurancItem.getVendorId());
								addInsDTO.setVendorId(insurancItem.getVendorId());
								addInsDTO.setCategoryId(insurancItem.getCategoryId());
								addInsDTO.setCategoryName(insurancItem.getCategoryName());
								addInsDTO.setPolicyAmount(insurancItem.getPolicyAmount());
								addInsDTO.setPolicyDescr(insurancItem.getPolicyDescr());
								addInsDTO.setPolicyStatus(insurancItem.getPolicyStatus());
								addInsDTO.setPolicyExpiryDate(com.newco.marketplace.util.TimestampUtils.
										getStringFromTimestamp(insurancItem.getPolicyExpiryDate(), "yyyy-MM-dd"));
								
								aditionalInsuranceDTOList.add(addInsDTO);
																
							}
							providerDto.setAdditionalInsuranceList(aditionalInsuranceDTOList);
						}
						if (providerResultVo.getCreatedDate() != null) {
							providerDto.setCreatedDate(providerResultVo
									.getCreatedDate());
						} else {
							providerDto.setCreatedDate(DateUtils
									.getCurrentDate());
							providerResultVo.setCreatedDate(DateUtils
									.getCurrentDate());
						}
						providerDto.setFirstName(providerResultVo
								.getProviderFirstName());
						providerDto.setLastName(providerResultVo
								.getProviderLastName());
						providerDto.setFirmName(providerResultVo.getFirmName());
						providerDto.setResourceId(providerResultVo
								.getResourceId());
						providerDto.setPerformanceScore(providerResultVo.getPerformanceScore());
						providerDto.setFirmPerformanceScore(providerResultVo.getFirmPerformanceScore());
						providerDto.setVendorId(providerResultVo.getVendorID());
						if (providerResultVo.getProviderStarRating() != null) {
							if (providerResultVo.getProviderStarRating()
									.getHistoricalRating() != null) {
								providerDto.setSlRating(providerResultVo
										.getProviderStarRating()
										.getHistoricalRating());
								// Set the slRatingNumber to show associated
								// star image in jsp
								providerDto.setSlRatingNumber(UIUtils
										.calculateScoreNumber(providerDto
												.getSlRating()));
							}
							if (providerResultVo.getProviderStarRating()
									.getNumberOfRatingsReceived() != null) {
								providerDto.setSlRatingsCount(providerResultVo
										.getProviderStarRating()
										.getNumberOfRatingsReceived());
							}
							if (providerResultVo.getProviderStarRating()
									.getMyRating() != null) {
								providerDto.setMyRating(providerResultVo
										.getProviderStarRating().getMyRating());
								// Set the myRatingNumber to show associated
								// star image in jsp
								providerDto.setMyRatingNumber(UIUtils
										.calculateScoreNumber(providerDto
												.getMyRating()));
							}
							if (providerResultVo.getProviderStarRating()
									.getNumberOfRatingsGiven() != null) {
								providerDto.setMyRatingsCount(providerResultVo
										.getProviderStarRating()
										.getNumberOfRatingsGiven());
							}
							if (providerResultVo.getProviderStarRating()
									.getTotalOrdersComplete() != null) {
								providerDto.setTotalOrders(providerResultVo
										.getProviderStarRating()
										.getTotalOrdersComplete());
							}
							if (providerResultVo.getProviderStarRating()
									.getMyOrdersComplete() != null) {
								providerDto.setMyOrders(providerResultVo
										.getProviderStarRating()
										.getMyOrdersComplete());
							}
						}
						
						if(providerResultVo.getFilteredSpnName() != null)
						{
							List<ProviderSPNDTO> networkAndPerformanceLevelList = new ArrayList<ProviderSPNDTO>();
							ProviderSPNDTO bean;
							
							bean = new ProviderSPNDTO();
							bean.setPerformanceLevelId(providerResultVo.getFilteredPerfLevel());
							bean.setPerformanceLevelName(providerResultVo.getFilteredPerfLevelDesc());
							bean.setNetworkName(providerResultVo.getFilteredSpnName());
							bean.setPerformanceScore(providerResultVo.getPerformanceScore());
							networkAndPerformanceLevelList.add(bean);
							
							providerDto.setNetworkAndPerformanceLevelList(networkAndPerformanceLevelList);							
						}
						
						providerDto.setMatch(providerResultVo
								.getPercentageMatch());
						providerDto.setDistance(providerResultVo.getDistance());
						providerDto.setLocationString(providerResultVo
								.getCity()
								+ ", " + providerResultVo.getState());
						providerDto.setShowCheck(providerResultVo
								.getIsSLVerified());
						providerDto
								.setIsChecked(providerResultVo.getSelected());
						if(providerResultVo.getVendorInsuranceTypes() != null){
							
							insuranceTypes = providerResultVo.getVendorInsuranceTypes();
							for(int j=0;j<insuranceTypes.size();j++){
								InsuranceResultVO insuranceVO = insuranceTypes.get(j);
								if(insuranceVO.getInsurancePresent()){
									if(insuranceVO.getVendorInsuranceTypes().intValue() == ProviderConstants.INSURANCE_GENERAL_LIABILITY){
										providerDto.setGenLiabilityInsAmt(insuranceVO.getAmount());
										providerDto.setGenLiabilityInsVerified(insuranceVO.getVerifiedByServiceLive());
										if(insuranceVO.getVerifiedByServiceLive())
											providerDto.setGenLiabilityInsVerifiedDate(insuranceVO.getInsVerifiedDate());
									}else if(insuranceVO.getVendorInsuranceTypes().intValue() == ProviderConstants.INSURANCE_AUTOMOTIVE){
										providerDto.setVehLiabilityInsAmt(insuranceVO.getAmount());
										providerDto.setVehLiabilityInsVerified(insuranceVO.getVerifiedByServiceLive());
										if(insuranceVO.getVerifiedByServiceLive())
											providerDto.setVehLiabilityInsVerifiedDate(insuranceVO.getInsVerifiedDate());										
									}else if(insuranceVO.getVendorInsuranceTypes().intValue() == ProviderConstants.INSURANCE_WORKERS_COMPENSATION){
										providerDto.setWorkCompInsAmt(insuranceVO.getAmount());
										providerDto.setWorkCompInsVerified(insuranceVO.getVerifiedByServiceLive());
										if(insuranceVO.getVerifiedByServiceLive())
											providerDto.setWorkCompInsVerifiedDate(insuranceVO.getInsVerifiedDate());
									}
								}
							}
						}
						
						
						providersDto.add(providerDto);
					}

				}
			}
		} catch (Exception e) {
			logger.info("Caught Exception and ignoring",e);
		}
		return providersDto;
	}

	private static SOWProvidersTabDTO assingServiceOrderVOToProvidersDto(
			ServiceOrder soVo) {
		SOWProvidersTabDTO providersTabDto = new SOWProvidersTabDTO();
		if (soVo != null) {
			List<RoutedProvider> routedProviders = Collections.emptyList();
			List<TierRoutedProvider> tierRoutedProviders = Collections.emptyList();
			ArrayList<SOWProviderDTO> routedProvidersDto = new ArrayList<SOWProviderDTO>();
			if(!soVo.getTierRoutedResources().isEmpty()){
				logger.info("Inside assingServiceOrderVOToProvidersDto>>IF");
				tierRoutedProviders = soVo.getTierRoutedResources();
				if (tierRoutedProviders != null && tierRoutedProviders.size() > 0) {
					for (int i = 0; i < tierRoutedProviders.size(); i++) {
						TierRoutedProvider routedProvider = tierRoutedProviders.get(i);
						List<ProviderSPNDTO> spnDetails = new ArrayList<ProviderSPNDTO>();
						
						if (routedProvider != null) {
							ProviderSPNDTO spn = new ProviderSPNDTO();
							SOWProviderDTO providerDto = new SOWProviderDTO();
							providerDto.setIsChecked(Boolean.TRUE);

							providerDto.setVendorId(routedProvider.getVendorId());
							providerDto.setResourceId(routedProvider
									.getResourceId());
							providerDto.setFirmPerformanceScore(routedProvider.getFirmPerfScore());
							providerDto.setPerformanceScore(routedProvider.getPerfScore());
							spn.setPerformanceScore(routedProvider.getPerfScore());
							spn.setNetworkName(routedProvider.getSpnName());
							spn.setPerformanceLevelName(routedProvider.getPerformanceLevelDesc());
							spn.setPerformanceLevelId(routedProvider.getPerformanceLevelId());
							spnDetails.add(spn);
							providerDto.setNetworkAndPerformanceLevelList(spnDetails);
							routedProvidersDto.add(providerDto);
						}
					}
					providersTabDto.setProviders(routedProvidersDto);
					providersTabDto.setSpnId(soVo.getSpnId());
					providersTabDto.setRoutingPriorityAppliedInd(true);
				}
			}else{
				logger.info("Inside assingServiceOrderVOToProvidersDto>>ELSE");
			routedProviders = soVo.getRoutedResources();
			if (routedProviders != null && routedProviders.size() > 0) {
				for (int i = 0; i < routedProviders.size(); i++) {
					RoutedProvider routedProvider = routedProviders.get(i);
					if (routedProvider != null) {
						SOWProviderDTO providerDto = new SOWProviderDTO();
						providerDto.setIsChecked(Boolean.TRUE);

						providerDto.setVendorId(routedProvider.getVendorId());
						providerDto.setResourceId(routedProvider
								.getResourceId());
						providerDto.setFirmName(routedProvider.getFirmName());
						providerDto.setFirstName(routedProvider.getProviderContact().getFirstName());
						providerDto.setLastName(routedProvider.getProviderContact().getLastName());
						providerDto.setFirmPerformanceScore(routedProvider.getFirmPerfScore());
						providerDto.setPerformanceScore(routedProvider.getPerfScore());
						routedProvidersDto.add(providerDto);
					}
				}
				providersTabDto.setProviders(routedProvidersDto);
								
				providersTabDto.setRoutingPriorityAppliedInd(false);
			}
			
			}
			logger.info("Setting SPN ID to the proivders DTO with spn id-->>"+soVo.getSpnId());
			//SL-18914 Setting the spn id to the dto even if there are no so routed providers for the service order 
			//from soVo object on click of edit service order
			providersTabDto.setSpnId(soVo.getSpnId());
		}
		SOWorkflowControlsVO sowrkflowControlsVO = soVo.getSoWrkFlowControls();
		if(null != sowrkflowControlsVO){
			providersTabDto.setRoutingPriorityAppliedInd((null==sowrkflowControlsVO.getTierRouteInd()) ? false : sowrkflowControlsVO.getTierRouteInd());
			providersTabDto.setPerformanceScore((null==sowrkflowControlsVO.getPerformanceScore()) ? 0.00 : sowrkflowControlsVO.getPerformanceScore());	
		}
		return providersTabDto;
	}

	private static void assingProvidersDtoToServiceOrderVO(ServiceOrder soVo,
			SOWProvidersTabDTO providersTabDto) {
		try {
			if (providersTabDto != null) {
				if (null != providersTabDto.getProviders()
						&& providersTabDto.getProviders().size() > 0) {
					List<SOWProviderDTO> providersDto = providersTabDto
							.getProviders();
					List<RoutedProvider> routedProviders = new ArrayList<RoutedProvider>();
					for (int i = 0; i < providersDto.size(); i++) {
						SOWProviderDTO providerDto = providersDto.get(i);
						if (providerDto.getIsChecked()) {
							RoutedProvider routedProvider = new RoutedProvider();
							if (providerDto.getCreatedDate() != null) {
								routedProvider
										.setCreatedDate(new Timestamp(
												providerDto.getCreatedDate()
														.getTime()));
							} else {
								routedProvider.setCreatedDate(new Timestamp(
										DateUtils.getCurrentDate().getTime()));
								providerDto.setCreatedDate(new Timestamp(
										DateUtils.getCurrentDate().getTime()));
							}
							routedProvider.setPriceModel(soVo.getPriceModel());
							routedProvider.setSoId(soVo.getSoId());
							routedProvider.setResourceId(providerDto
									.getResourceId());
							routedProvider.setVendorId(providerDto
									.getVendorId());
							routedProvider.setPerfScore(providerDto.getPerformanceScore());
							routedProvider.setFirmPerfScore(providerDto.getFirmPerformanceScore());
							routedProvider.setFirstName(providerDto.getFirstName());
							routedProvider.setLastName(providerDto.getLastName());
							routedProvider.setFirmName(providerDto.getFirmName());
							if(providersTabDto.getSpnId()!=null){
							routedProvider.setSpnId(providersTabDto.getSpnId());
							}
							routedProviders.add(routedProvider);
						}
					}
					soVo.setRoutedResources(routedProviders);
				}
				if(providersTabDto.getSpnId()!=null){
					soVo.setSpnId(providersTabDto.getSpnId());
				}
				SOWorkflowControlsVO soworkflowCntrlsVO = new SOWorkflowControlsVO();
				soworkflowCntrlsVO.setPerformanceScore((null==providersTabDto.getPerformanceScore()) ? 0.00 : providersTabDto.getPerformanceScore());
				soworkflowCntrlsVO.setTierRouteInd((null==providersTabDto.getRoutingPriorityAppliedInd()) ? false : providersTabDto.getRoutingPriorityAppliedInd());
				soVo.setSoWrkFlowControls(soworkflowCntrlsVO);
			}
		} catch (Exception e) {
			logger.info("Caught Exception and ignoring",e);
		}
	}

	private static void assignPartsDtoToServiceOrderVO(ServiceOrder soVo,
			SOWPartsTabDTO partsTabDto) {
		try {
			if (partsTabDto != null) {
				soVo.setPartsSupplier(Integer.valueOf(partsTabDto
						.getPartsSuppliedBy()));
				if (null != partsTabDto.getParts()
						&& partsTabDto.getParts().size() > 0) {
					List<SOWPartDTO> partsDto = partsTabDto.getParts();
					List<Part> partsVo = new ArrayList<Part>();
					for (int i = 0; i < partsDto.size(); i++) {
						SOWPartDTO partDto = (SOWPartDTO) partsDto.get(i);
						Part partVo = new Part();
						Carrier shippingCarrier = new Carrier();
						Carrier returnCarrier = new Carrier();
						Carrier coreReturnCarrier = new Carrier();
						partVo.setReferencePartId(partDto.getReferencePartId());
						if (null != partDto.getShipDate()
								&& !partDto.getShipDate().equals("")) {

							partVo.setShipDate(new Timestamp(getDateFromString(
									partDto.getShipDate(), "yyyy-MM-dd")
									.getTime()));
						}					
						if (null != partDto.getReturnTrackDate()
								&& !partDto.getReturnTrackDate().equals("")) {

							partVo.setReturnTrackDate(new Timestamp(getDateFromString(
									partDto.getReturnTrackDate(), "yyyy-MM-dd")
									.getTime()));
						}					

						if (partDto.getCreatedDate() != null) {
							partVo.setCreatedDate(partDto.getCreatedDate());
						} else {
							partVo.setCreatedDate(DateUtils.getCurrentDate());
							partDto.setCreatedDate(DateUtils.getCurrentDate());
						}
						if (partDto.getEntityId() != null) {
							partVo.setPartId(partDto.getEntityId());
						}
						partVo.setHeight(partDto.getHeight());
						partVo.setLength(partDto.getLength());
						partVo.setWidth(partDto.getWidth());
						partVo.setWeight(partDto.getWeight());
						partVo.setManufacturer(partDto.getManufacturer());
						if(partDto.getPartStatusId()!= null && partDto.getPartStatusId().intValue() > 0){
							partVo.setPartStatusId(partDto.getPartStatusId());
						}else
							partVo.setPartStatusId(null);
						
						partVo.setManufacturerPartNumber(partDto.getManufacturerPartNumber());
						partVo.setSerialNumber(partDto.getSerialNumber());
						partVo.setProductLine(partDto.getProductLine());
						partVo.setAdditionalPartInfo(partDto.getAdditionalPartInfo());
						partVo.setVendorPartNumber(partDto.getVendorPartNumber());
						partVo.setOrderNumber(partDto.getOrderNumber());
						partVo.setPurchaseOrderNumber(partDto.getPurchaseOrderNumber());
						partVo.setAltPartRef1(partDto.getAltPartRef1());
						partVo.setAltPartRef2(partDto.getAltPartRef2());
						if (partDto.getStandard() != null)
							partVo.setMeasurementStandard(Integer
									.parseInt(partDto.getStandard()));
						else
							partVo.setMeasurementStandard(null);
						partVo.setModelNumber(partDto.getModelNumber());
						partVo.setPartDs(partDto.getPartDesc());
						partVo.setProviderBringPartInd(partDto
								.isProviderBringparts());
						if (partDto.getQuantity() != null
								&& !partDto.getQuantity().equals("")) {
							partVo.setQuantity(partDto.getQuantity());
						}
						else
						{
							partVo.setQuantity("1");	
						}
						if (partDto.getShippingCarrierId() != null
								&& partDto.getShippingTrackingNo() != null) {
							shippingCarrier.setCarrierId(partDto
									.getShippingCarrierId());

							if (partDto.getOtherShippingCarrier() != null)
								partVo.setShipCarrierOther(partDto
										.getOtherShippingCarrier());
							shippingCarrier.setTrackingNumber(partDto
									.getShippingTrackingNo());
							partVo.setShippingCarrier(shippingCarrier);
							partVo.setShipCarrierOther(partDto
									.getOtherShippingCarrier());
						}
						if (partDto.getReturnCarrierId() != null
								&& partDto.getReturnTrackingNo() != null) {
							returnCarrier.setCarrierId(partDto
									.getReturnCarrierId());
							if (partDto.getOtherReturnCarrier() != null)
								partVo.setReturnCarrierOther(partDto
										.getOtherReturnCarrier());
							returnCarrier.setTrackingNumber(partDto
									.getReturnTrackingNo());
							partVo.setReturnCarrier(returnCarrier);
						}
						
						if (partDto.getCoreReturnCarrierId() != null
								&& partDto.getCoreReturnTrackingNo() != null) {
							coreReturnCarrier.setCarrierId(partDto
									.getCoreReturnCarrierId());
							if (partDto.getOtherCoreReturnCarrier() != null)
								partVo.setCoreReturnCarrierOther(partDto
										.getOtherCoreReturnCarrier());
							coreReturnCarrier.setTrackingNumber(partDto
									.getCoreReturnTrackingNo());
							partVo.setCoreReturnCarrier(coreReturnCarrier);
						}
												
						
						partDto.setProviderBringparts(partDto.getHasPartsAtDifferntLocation());
						partVo.setProviderBringPartInd(partDto.isProviderBringparts());
						//if (partDto.isProviderBringparts()) {
							if (partDto.getPickupContactLocation() != null) {
								SOWContactLocationDTO pickupContactLocationDto = partDto
										.getPickupContactLocation();
								Contact pickupContactVo = new Contact();
								SoLocation pickupLocationVo = new SoLocation();
								if (pickupContactLocationDto.getCreatedDate() != null) {
									pickupContactVo
											.setCreatedDate(new Timestamp(
													pickupContactLocationDto
															.getCreatedDate()
															.getTime()));
								} else {
									pickupContactVo
											.setCreatedDate(new Timestamp(
													DateUtils.getCurrentDate()
															.getTime()));
									pickupContactLocationDto
											.setCreatedDate(new Timestamp(
													DateUtils.getCurrentDate()
															.getTime()));
								}

								pickupContactVo
										.setBusinessName(pickupContactLocationDto
												.getBusinessName());
								pickupContactVo
										.setFirstName(pickupContactLocationDto
												.getFirstName());
								pickupContactVo
										.setLastName(pickupContactLocationDto
												.getLastName());

								pickupContactVo
										.setEmail(pickupContactLocationDto
												.getEmail());
								if (null != pickupContactLocationDto
										.getPhones()
										&& pickupContactLocationDto.getPhones()
												.size() > 0) {
									List<SOWPhoneDTO> phonesDto = pickupContactLocationDto
											.getPhones();
									List<PhoneVO> phonesVo = new ArrayList<PhoneVO>();
									int phonesSize = phonesDto.size();

									for (int idx = 0; idx < phonesSize; idx++) {
										SOWPhoneDTO phoneDto = (SOWPhoneDTO) phonesDto
												.get(idx);
										PhoneVO phoneVo = new PhoneVO();
										if (phoneDto.getCreatedDate() != null) {
											phoneVo
													.setCreatedDate(new Timestamp(
															phoneDto
																	.getCreatedDate()
																	.getTime()));
										} else {
											phoneVo
													.setCreatedDate(new Timestamp(
															DateUtils
																	.getCurrentDate()
																	.getTime()));
											phoneDto
													.setCreatedDate(new Timestamp(
															DateUtils
																	.getCurrentDate()
																	.getTime()));
										}
										phoneVo.setPhoneNo(phoneDto.getPhone());
										phoneVo.setPhoneExt(phoneDto.getExt());
										phoneVo.setPhoneType(idx + 1);
										phoneVo.setClassId(phoneDto
												.getPhoneClassId());
										phonesVo.add(phoneVo);
									}
									pickupContactVo.setPhones(phonesVo);
								}
								partVo.setPickupContact(pickupContactVo);
								if (pickupContactLocationDto.getCreatedDate() != null) {
									pickupLocationVo
											.setCreatedDate(new Timestamp(
													pickupContactLocationDto
															.getCreatedDate()
															.getTime()));
								} else {
									pickupLocationVo
											.setCreatedDate(new Timestamp(
													DateUtils.getCurrentDate()
															.getTime()));
									pickupContactLocationDto
											.setCreatedDate(new Timestamp(
													DateUtils.getCurrentDate()
															.getTime()));
								}
								pickupLocationVo
										.setAptNo(pickupContactLocationDto
												.getAptNo());
								pickupLocationVo
										.setStreet1(pickupContactLocationDto
												.getStreetName1());
								pickupLocationVo
										.setStreet2(pickupContactLocationDto
												.getStreetName2());
								pickupLocationVo
										.setCity(pickupContactLocationDto
												.getCity());
								pickupLocationVo
										.setState(pickupContactLocationDto
												.getState());
								pickupLocationVo
										.setCountry(OrderConstants.COUNTRY_USA_CODE);
								pickupLocationVo
										.setZip(pickupContactLocationDto
												.getZip());
								pickupLocationVo
										.setZip4(pickupContactLocationDto
												.getZip4());
								partVo.setPickupLocation(pickupLocationVo);
							}
						//}
						partsVo.add(partVo);
					}
					soVo.setParts(partsVo);
				}
			}
		} catch (Exception e) {
			logger.info("Caught Exception and ignoring",e);
		}

	}

	private static SOWAdditionalInfoTabDTO assignServiceOrderVOToAddtionalInfoDTO(
			ServiceOrder soVo) {
		SOWAdditionalInfoTabDTO addInfoDto = new SOWAdditionalInfoTabDTO();
		try {
			if (soVo != null) {
				Contact altServiceContact = soVo.getEndUserContact();
				SOWContactLocationDTO altContactLocationDto = new SOWContactLocationDTO();
				SOWAltBuyerContactDTO altBuyerContactDTO = new SOWAltBuyerContactDTO();

				if (soVo.getSelectedAltBuyerContact() != null
						&& soVo.getSelectedAltBuyerContact().getContactId() != null) {
					if (soVo.getSelectedAltBuyerContact().getCreatedDate() != null) {
						altBuyerContactDTO.setCreatedDate(soVo
								.getSelectedAltBuyerContact().getCreatedDate());
					} else {
						altBuyerContactDTO.setCreatedDate(DateUtils
								.getCurrentDate());
						soVo.getSelectedAltBuyerContact().setCreatedDate(
								DateUtils.getCurrentDate());
					}
					altBuyerContactDTO.setFirstName(soVo.getAltBuyerResource().getBuyerResContact().getFirstName());
					altBuyerContactDTO.setLastName(soVo.getAltBuyerResource().getBuyerResContact().getLastName());
					altBuyerContactDTO.setContactId(soVo.getAltBuyerResource().getBuyerResContact().getContactId());
					if(null!=soVo.getSelectedAltBuyerContact().getEntityId())
						altBuyerContactDTO.setResourceId(Integer.parseInt(soVo.getSelectedAltBuyerContact().getEntityId()));
					addInfoDto.setSelectedAltBuyerContact(altBuyerContactDTO);
					addInfoDto.setAltBuyerSupportLocationContactFlg(true);

				}
				if (soVo.getLogoDocumentId() != null) {
					addInfoDto.setLogodocumentId(soVo.getLogoDocumentId());
				}
				List<SOWPhoneDTO> phonesDtos = new ArrayList<SOWPhoneDTO>();
				for (int i = 0; i < 3; i++) {
					SOWPhoneDTO phone = new SOWPhoneDTO();
					phone.setPhoneType(i);
					phonesDtos.add(phone);
				}
				if (altServiceContact != null) {

					addInfoDto.setAltServiceLocationContactFlg(true);
					if (altServiceContact.getCreatedDate() != null) {
						altContactLocationDto.setCreatedDate(altServiceContact
								.getCreatedDate());
					} else {
						altContactLocationDto.setCreatedDate(DateUtils
								.getCurrentDate());
						altServiceContact.setCreatedDate(new Timestamp(
								DateUtils.getCurrentDate().getTime()));
					}
					altContactLocationDto.setFirstName(altServiceContact
							.getFirstName());
					altContactLocationDto.setLastName(altServiceContact
							.getLastName());
					altContactLocationDto.setFax(altServiceContact.getFaxNo());
					altContactLocationDto
							.setEmail(altServiceContact.getEmail());

					if (altServiceContact.getPhoneNo() != null) {
						String priPhone = altServiceContact.getPhoneNo();

						// SOWPhoneDTO phonesDto = new SOWPhoneDTO();
						if (altServiceContact.getPhoneCreatedDate() != null) {
							phonesDtos.get(0).setCreatedDate(
									altServiceContact.getPhoneCreatedDate());
						} else {
							phonesDtos.get(0).setCreatedDate(
									DateUtils.getCurrentDate());
							altServiceContact.setPhoneCreatedDate(DateUtils
									.getCurrentDate());
						}
						// phonesDto.setPhone(p);
						if (priPhone != null && priPhone.length() == 10) {
							phonesDtos.get(0).setAreaCode(
									priPhone.substring(0, 3));
							phonesDtos.get(0).setPhonePart1(
									priPhone.substring(3, 6));
							phonesDtos.get(0).setPhonePart2(
									priPhone.substring(6, 10));
						}
						if (altServiceContact.getPhoneClassId() != null) {
							phonesDtos.get(0).setPhoneClassId(
									new Integer(altServiceContact
											.getPhoneClassId()));
						}
						if (altServiceContact.getPhoneNoExt() != null) {
							phonesDtos.get(0).setExt(
									altServiceContact.getPhoneNoExt());
						}

						if (altServiceContact.getPhoneClassId() != null) {
							phonesDtos.get(0).setPhoneClassId(
									new Integer(altServiceContact
											.getPhoneClassId()));
						}

					}

				}

				Contact altEndUserContact = soVo.getAltEndUserContact();
				if (altEndUserContact != null) {

					addInfoDto.setAltServiceLocationContactFlg(true);
					if (altEndUserContact.getCreatedDate() != null) {
						altContactLocationDto.setCreatedDate(altEndUserContact
								.getCreatedDate());
					} else {
						altContactLocationDto.setCreatedDate(DateUtils
								.getCurrentDate());
						altEndUserContact.setCreatedDate(new Timestamp(
								DateUtils.getCurrentDate().getTime()));
					}
					altContactLocationDto.setFirstName(altEndUserContact
							.getFirstName());
					altContactLocationDto.setLastName(altEndUserContact
							.getLastName());
					altContactLocationDto.setFax(altEndUserContact.getFaxNo());

					if (altEndUserContact.getPhoneNo() != null) {
						String priPhone = altEndUserContact.getPhoneNo();

						// SOWPhoneDTO phonesDto = new SOWPhoneDTO();
						if (altEndUserContact.getPhoneCreatedDate() != null) {
							phonesDtos.get(1).setCreatedDate(
									altEndUserContact.getPhoneCreatedDate());
						} else {
							phonesDtos.get(1).setCreatedDate(
									DateUtils.getCurrentDate());
							altEndUserContact
									.setPhoneCreatedDate(new Timestamp(
											DateUtils.getCurrentDate()
													.getTime()));
						}
						// phonesDto.setPhone(p);
						if (priPhone != null && priPhone.length() == 10) {
							phonesDtos.get(1).setAreaCode(
									priPhone.substring(0, 3));
							phonesDtos.get(1).setPhonePart1(
									priPhone.substring(3, 6));
							phonesDtos.get(1).setPhonePart2(
									priPhone.substring(6, 10));
						}
						if (altEndUserContact.getPhoneClassId() != null) {
							phonesDtos.get(1).setPhoneClassId(
									new Integer(altEndUserContact
											.getPhoneClassId()));
						}
						if (altEndUserContact.getPhoneNoExt() != null) {
							phonesDtos.get(1).setExt(
									altEndUserContact.getPhoneNoExt());
						}
						phonesDtos.get(1).setPhoneType(2);
						// Phone classId has to be derived
						/**/
						if (altEndUserContact.getPhoneClassId() != null) {
							phonesDtos.get(1).setPhoneClassId(
									new Integer(altEndUserContact
											.getPhoneClassId()));
						}
						// phonesDtos.add(phonesDto);
					}
				}

				Contact altEndUserFax = soVo.getAltEndUserFax();
				if (altEndUserFax != null) {
					addInfoDto.setAltServiceLocationContactFlg(true);
					if (altEndUserFax.getCreatedDate() != null) {
						altContactLocationDto.setCreatedDate(altEndUserFax
								.getCreatedDate());
					} else {
						altContactLocationDto.setCreatedDate(DateUtils
								.getCurrentDate());
						altEndUserFax.setCreatedDate(new Timestamp(DateUtils
								.getCurrentDate().getTime()));
					}
					altContactLocationDto.setFirstName(altEndUserFax
							.getFirstName());
					altContactLocationDto.setLastName(altEndUserFax
							.getLastName());
					altContactLocationDto.setFax(altEndUserFax.getFaxNo());

					if (altEndUserFax.getPhoneNo() != null) {
						String priPhone = altEndUserFax.getPhoneNo();

						// SOWPhoneDTO phonesDto = new SOWPhoneDTO();
						if (altEndUserFax.getPhoneCreatedDate() != null) {
							phonesDtos.get(2).setCreatedDate(
									altEndUserFax.getPhoneCreatedDate());
						} else {
							phonesDtos.get(2).setCreatedDate(
									DateUtils.getCurrentDate());
							altEndUserFax.setPhoneCreatedDate(new Timestamp(
									DateUtils.getCurrentDate().getTime()));
						}
						// phonesDto.setPhone(p);
						if (priPhone != null && priPhone.length() == 10) {
							phonesDtos.get(2).setAreaCode(
									priPhone.substring(0, 3));
							phonesDtos.get(2).setPhonePart1(
									priPhone.substring(3, 6));
							phonesDtos.get(2).setPhonePart2(
									priPhone.substring(6, 10));
						}
						if (altEndUserFax.getPhoneClassId() != null) {
							phonesDtos.get(2)
									.setPhoneClassId(
											new Integer(altEndUserFax
													.getPhoneClassId()));
						}
						if (altEndUserFax.getPhoneNoExt() != null) {
							phonesDtos.get(2).setExt(
									altEndUserFax.getPhoneNoExt());
						}
						phonesDtos.get(2).setPhoneType(3);
						// Phone classId has to be derived
						/**/
						if (altEndUserFax.getPhoneClassId() != null) {
							phonesDtos.get(2)
									.setPhoneClassId(
											new Integer(altEndUserFax
													.getPhoneClassId()));
						}
					}

				}
				altContactLocationDto.setPhones(phonesDtos);
				addInfoDto.setAlternateLocationContact(altContactLocationDto);

				List<ServiceOrderAltBuyerContactVO> altBuyerContactsVo = soVo
						.getAltBuyerContacts();

				if (altBuyerContactsVo != null && altBuyerContactsVo.size() > 0) {
					List<SOWAltBuyerContactDTO> altBuyerContactsDto = new ArrayList<SOWAltBuyerContactDTO>();

					for (int i = 0; i < altBuyerContactsVo.size(); i++) {
						ServiceOrderAltBuyerContactVO altBuyerContactVo = altBuyerContactsVo
								.get(i);
						SOWAltBuyerContactDTO altBuyerContactDto = new SOWAltBuyerContactDTO();
						if (altBuyerContactVo.getCreatedDate() != null) {
							altBuyerContactDto.setCreatedDate(altBuyerContactVo
									.getCreatedDate());
						} else {
							altBuyerContactDto.setCreatedDate(DateUtils
									.getCurrentDate());
							altBuyerContactVo.setCreatedDate(DateUtils
									.getCurrentDate());
						}
						altBuyerContactDto.setFirstName(altBuyerContactVo
								.getFirstName());
						altBuyerContactDto.setLastName(altBuyerContactVo
								.getLastName());
						altBuyerContactDto.setContactId(altBuyerContactVo
								.getContactId());

						altBuyerContactsDto.add(altBuyerContactDto);
					}
					addInfoDto.setAltBuyerContacts(altBuyerContactsDto);
				}

				List<ServiceOrderBrandingInfoVO> brandingInfoVoList = soVo
						.getBrandingInfoList();
				if (brandingInfoVoList != null && brandingInfoVoList.size() > 0) {
					List<SOWBrandingInfoDTO> brandingInfoDTOList = new ArrayList<SOWBrandingInfoDTO>();
					for (int i = 0; i < brandingInfoVoList.size(); i++) {
						SOWBrandingInfoDTO brandingDto = new SOWBrandingInfoDTO();
						ServiceOrderBrandingInfoVO brandingVo = brandingInfoVoList
								.get(i);

						brandingDto.setCompanyName(brandingVo.getCompanyName());
						brandingDto.setLogoId(brandingVo.getLogoId());
						brandingInfoDTOList.add(brandingDto);
					}
					addInfoDto.setBrandingInfoList(brandingInfoDTOList);
				}

				List<ServiceOrderCustomRefVO> customRefsVoList = soVo
						.getCustomRefs();
				if (customRefsVoList != null && customRefsVoList.size() > 0) {
					List<SOWCustomRefDTO> customRefsDtoList = new ArrayList<SOWCustomRefDTO>();
					for (int i = 0; i < customRefsVoList.size(); i++) {
						SOWCustomRefDTO customRefDto = new SOWCustomRefDTO();
						ServiceOrderCustomRefVO customRefVO = customRefsVoList
								.get(i);
						customRefDto.setRefTypeId(customRefVO.getRefTypeId());
						customRefDto.setRefValue(customRefVO.getRefValue());
						customRefDto.setRefType(customRefVO.getRefType());
						customRefsDtoList.add(customRefDto);
					}
					addInfoDto.setCustomRefs(customRefsDtoList);
				}

				if(soVo.getStatus() != null 
						&& soVo.getStatus().trim().equalsIgnoreCase(OrderConstants.ROUTED)){
					addInfoDto.setStatusString(OrderConstants.POSTED);
				}else{
					addInfoDto.setStatusString(soVo.getStatus());
				}
				addInfoDto.setSubStatusString(soVo.getSubStatus());
				addInfoDto.setSubStatusId(soVo.getWfSubStatusId());
				addInfoDto.setWfStateId(soVo.getWfStateId());
			}

		} catch (Exception e) {
			logger.info("Caught Exception and ignoring",e);
		}
		return addInfoDto;
	}

	private static void assignAddtionalInfoToServiceOrderVO(ServiceOrder soVo,
			SOWAdditionalInfoTabDTO addInfoDto) {
		try {
			if (null != addInfoDto) {
				if (addInfoDto.isAltServiceLocationContactFlg()) {
					SoLocation altServiceLocation = new SoLocation();
					Contact altServiceContact = new Contact();
					ServiceOrderAltBuyerContactVO soAltbuyerContact = new ServiceOrderAltBuyerContactVO();

					if (addInfoDto.getSelectedAltBuyerContact() != null
							&& addInfoDto.getSelectedAltBuyerContact()
									.getContactId() != null) {

						soAltbuyerContact.setContactId(addInfoDto
								.getSelectedAltBuyerContact().getContactId());
						soVo.setSelectedAltBuyerContact(soAltbuyerContact);
						
						if (addInfoDto.getSelectedAltBuyerContact()
								.getCreatedDate() != null) {
							soAltbuyerContact.setCreatedDate(addInfoDto
									.getSelectedAltBuyerContact()
									.getCreatedDate());
						} else {
							soAltbuyerContact.setCreatedDate(DateUtils
									.getCurrentDate());
							addInfoDto.getSelectedAltBuyerContact()
									.setCreatedDate(DateUtils.getCurrentDate());
						}

					}
					if (null != addInfoDto.getAlternateLocationContact()) {
						SOWContactLocationDTO altContactLocationDto = addInfoDto
								.getAlternateLocationContact();
						if (altContactLocationDto.getLocationTypeId() != null) {
							altServiceLocation.setLocnClassId(new Integer(
									altContactLocationDto.getLocationTypeId()));
						}
						altServiceLocation
								.setLocnTypeId(OrderConstants.SERVICE_LOCATION_CONTACT_TYPE_ID);
						if (altContactLocationDto.getCreatedDate() != null) {
							altServiceContact.setCreatedDate(new Timestamp(
									altContactLocationDto.getCreatedDate()
											.getTime()));
						} else {
							altServiceContact.setCreatedDate(new Timestamp(
									DateUtils.getCurrentDate().getTime()));
							altContactLocationDto.setCreatedDate(DateUtils
									.getCurrentDate());
						}
						altServiceContact.setFirstName(altContactLocationDto
								.getFirstName());
						altServiceContact.setLastName(altContactLocationDto
								.getLastName());
						altServiceContact.setEmail(altContactLocationDto
								.getEmail());
						altServiceContact.setFaxNo(altContactLocationDto
								.getFax());
						if (null != altContactLocationDto.getPhones()
								&& altContactLocationDto.getPhones().size() > 0) {
							List<SOWPhoneDTO> phonesDto = altContactLocationDto
									.getPhones();
							List<PhoneVO> phonesVo = new ArrayList<PhoneVO>();
							int phonesSize = phonesDto.size();
							for (int i = 0; i < phonesSize; i++) {
								SOWPhoneDTO phoneDto = (SOWPhoneDTO) phonesDto
										.get(i);
								PhoneVO phoneVo = new PhoneVO();
								if (phoneDto.getCreatedDate() != null) {
									phoneVo
											.setCreatedDate(new Timestamp(
													phoneDto.getCreatedDate()
															.getTime()));
								} else {
									phoneVo.setCreatedDate(new Timestamp(
											DateUtils.getCurrentDate()
													.getTime()));
									phoneDto.setCreatedDate(new Timestamp(
											DateUtils.getCurrentDate()
													.getTime()));
								}
								phoneVo.setPhoneNo(phoneDto.getPhone());
								phoneVo.setPhoneExt(phoneDto.getExt());
								phoneVo.setClassId(phoneDto.getPhoneClassId());
								if (i == 0) {
									phoneVo.setPhoneType(1);
								} else if (i == 1) {
									phoneVo.setPhoneType(2);
								} else if (i == 2) {
									phoneVo.setPhoneType(3);
									phoneVo.setClassId(5);
								}
								phonesVo.add(phoneVo);
							}
							altServiceContact.setPhones(phonesVo);
						}
						soVo.setEndUserContact(altServiceContact);

					}
				}

				if (addInfoDto.isAltBuyerSupportLocationContactFlg()) {
					SOWAltBuyerContactDTO selectedAltContactDto = addInfoDto
							.getSelectedAltBuyerContact();
					ServiceOrderAltBuyerContactVO selectedAltContactVo = new ServiceOrderAltBuyerContactVO();
					if (selectedAltContactDto != null) {
						if (selectedAltContactDto.getCreatedDate() != null) {
							selectedAltContactVo
									.setCreatedDate(selectedAltContactDto
											.getCreatedDate());
						} else {
							selectedAltContactVo.setCreatedDate(DateUtils
									.getCurrentDate());
							selectedAltContactDto.setCreatedDate(DateUtils
									.getCurrentDate());
						}
						selectedAltContactVo.setFirstName(selectedAltContactDto
								.getFirstName());
						selectedAltContactVo.setLastName(selectedAltContactDto
								.getLastName());
						selectedAltContactVo
								.setContactId(selectedAltContactDto
										.getContactId());
					}
					soVo.setSelectedAltBuyerContact(selectedAltContactVo);
					//Set the selected buyer resource's contact id into the ServiceOrder object
					soVo.setBuyerContactId(addInfoDto.getSelectedAltBuyerContact().getContactId());
				} else {
					//Either the Alternate Buyer contact is not specified or it was specified and has been 
					//unspecified later. Make sure the correct contact id is updated all the time.
					soVo.setBuyerContactId(addInfoDto.getAltBuyerSupportLocationContact().getContactId());
				}

				if (null != addInfoDto.getBrandingInfoList()
						&& addInfoDto.getBrandingInfoList().size() > 0) {
					List<SOWBrandingInfoDTO> brandingInfoDTOList = addInfoDto
							.getBrandingInfoList();
					List<ServiceOrderBrandingInfoVO> brandingInfoVoList = new ArrayList<ServiceOrderBrandingInfoVO>();
					for (int i = 0; i < brandingInfoDTOList.size(); i++) {
						SOWBrandingInfoDTO brandingDto = (SOWBrandingInfoDTO) brandingInfoDTOList
								.get(i);
						ServiceOrderBrandingInfoVO brandingVo = new ServiceOrderBrandingInfoVO();
						brandingVo.setCompanyName(brandingDto.getCompanyName());
						brandingVo.setLogoId(brandingDto.getLogoId());
						brandingInfoVoList.add(brandingVo);
					}
					soVo.setBrandingInfoList(brandingInfoVoList);

				}
				if (null != addInfoDto.getLogodocumentId()) {
					soVo.setLogoDocumentId(addInfoDto.getLogodocumentId());
				}

				if (null != addInfoDto.getCustomRefs()
						&& addInfoDto.getCustomRefs().size() > 0) {
					List<SOWCustomRefDTO> customRefsDtoList = addInfoDto
							.getCustomRefs();
					List<ServiceOrderCustomRefVO> customRefsVoList = new ArrayList<ServiceOrderCustomRefVO>();
					for (int i = 0; i < customRefsDtoList.size(); i++) {
						SOWCustomRefDTO customRefDto = (SOWCustomRefDTO) customRefsDtoList
								.get(i);
						ServiceOrderCustomRefVO customRefVO = new ServiceOrderCustomRefVO();
						customRefVO.setRefTypeId(customRefDto.getRefTypeId());
						customRefVO.setRefValue(customRefDto.getRefValue());
						customRefVO.setRefType(customRefDto.getRefType());
						customRefVO.setsoId(soVo.getSoId());
						customRefsVoList.add(customRefVO);
					}
					soVo.setCustomRefs(customRefsVoList);
				}
				
				soVo.setWfStateId(addInfoDto.getWfStateId());
			}
		} catch (Exception e) {
			logger.info("Caught Exception and ignoring",e);
		}

	}

	private static SOWScopeOfWorkTabDTO assignServiceOrderVOToScopeOfWorkDTO(ServiceOrder so) {
		Double permitPrePaidPrice = 0.0;
		SOWScopeOfWorkTabDTO scopeOfWorkDto = new SOWScopeOfWorkTabDTO();
		try {
			if (so != null) {
				
				if (so.getPrimarySkillCatId() != null) {
					scopeOfWorkDto.setMainServiceCategoryId(so
							.getPrimarySkillCatId());
				}
				// Populating the tasks
				if(!(so.isSkuTaskIndicator()==true))
				{
				ArrayList<SOTaskDTO> soTasks = new ArrayList<SOTaskDTO>();
				if (so.getTasks() != null && so.getTasks().size() > 0) {
					Iterator<ServiceOrderTask> tasks = so.getTasks().iterator();
					while (tasks.hasNext()) {
						ServiceOrderTask soTask = (ServiceOrderTask) tasks
								.next();
						SOTaskDTO taskDto = new SOTaskDTO();
						taskDto.setIsSaved(true);
						if (soTask.getTaskId() != null) {
							taskDto.setEntityId(soTask.getTaskId());
							taskDto.setTaskId(soTask.getTaskId());
						}
						taskDto.setTaskName(soTask.getTaskName());
						taskDto.setComments(soTask.getTaskComments());
						taskDto.setCategory(soTask.getCategoryName());
						taskDto.setSubCategory(soTask.getSubCategoryName());
						if (soTask.getCreateDate() != null) {
							taskDto.setCreatedDate(soTask.getCreateDate());
						} else {
							taskDto.setCreatedDate(DateUtils.getCurrentDate());
							soTask.setCreateDate(DateUtils.getCurrentDate());
						}
						if (soTask.getSkillNodeId() != null
								&& soTask.getLevel() == 3) {
							taskDto.setSubCategoryId(soTask.getSkillNodeId());
							taskDto.setCategoryId(soTask.getParentId());
						} else if (soTask.getSkillNodeId() != null
								&& soTask.getLevel() == 2) {
							taskDto.setCategoryId(soTask.getSkillNodeId());
						}
						taskDto.setSkill(soTask.getServiceType());
						taskDto.setSkillId(soTask.getServiceTypeId());
						taskDto.setSellingPrice(soTask.getSellingPrice());
						taskDto.setFinalPrice(soTask.getFinalPrice());
						taskDto.setRetailPrice(soTask.getRetailPrice());
						taskDto.setSequenceNumber(soTask.getSequenceNumber());
						//SL-20527 : Setting Task status in DTO to calculate spendLimit Labor.
						taskDto.setTaskStatus(soTask.getTaskStatus());
						if(soTask.getTaskType()!=null && soTask.getTaskType()==1){
							permitPrePaidPrice = permitPrePaidPrice + soTask.getSellingPrice();
						}
						taskDto.setTaskType(soTask.getTaskType());
						taskDto.setSku(soTask.getSku());
						if(null!=soTask.getFinalPrice() && null!=soTask.getRetailPrice() && soTask.getFinalPrice()!=soTask.getRetailPrice()){
							taskDto.setPriceChangedInd(true);
						}
						if(soTask.getPriceHistoryList()!=null){
							SOTaskPriceHistoryDTO taskPriceDTO  = null;
							for(SOTaskPriceHistoryVO taskPriceVO : soTask.getPriceHistoryList()){
								taskPriceDTO = new SOTaskPriceHistoryDTO();
								taskPriceDTO.setPrice(taskPriceVO.getPrice());
								Date date = taskPriceVO.getModifiedDate() ;
								SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
								String displayDate = df.format(date) ;
								taskPriceDTO.setModifiedDate(displayDate.substring(0, 10));
								taskPriceDTO.setModifiedBy(taskPriceVO.getModifiedBy());
								taskPriceDTO.setModifiedByName(taskPriceVO.getModifiedByName());
								taskDto.getPriceHistoryList().add(taskPriceDTO);
						}

						}
						
						soTasks.add(taskDto);
					}

				}// End of Tasks
				scopeOfWorkDto.setTasks(soTasks);
				scopeOfWorkDto.setServiceOrderSkuIndicator(false);
				}
				//Setting the skus in skus of sowTabDto
				else
				{
					
					if(so.isSkuTaskIndicator()==true)
					{
					ArrayList<SOTaskDTO> soSkus = new ArrayList<SOTaskDTO>();
					if (so.getTasks() != null && so.getTasks().size() > 0) {
						Iterator<ServiceOrderTask> tasks = so.getTasks().iterator();
						while (tasks.hasNext()) {
							ServiceOrderTask soTask = (ServiceOrderTask) tasks
									.next();
							SOTaskDTO taskDto = new SOTaskDTO();
							taskDto.setIsSaved(true);
							if (soTask.getTaskId() != null) {
								taskDto.setEntityId(soTask.getTaskId());
								taskDto.setTaskId(soTask.getTaskId());
							}
							taskDto.setTaskName(soTask.getTaskName());
							taskDto.setComments(soTask.getTaskComments());
							taskDto.setCategory(soTask.getCategoryName());
							taskDto.setSubCategory(soTask.getSubCategoryName());
							if (soTask.getCreateDate() != null) {
								taskDto.setCreatedDate(soTask.getCreateDate());
							} else {
								taskDto.setCreatedDate(DateUtils.getCurrentDate());
								soTask.setCreateDate(DateUtils.getCurrentDate());
							}
							if (soTask.getSkillNodeId() != null
									&& soTask.getLevel() == 3) {
								taskDto.setSubCategoryId(soTask.getSkillNodeId());
								taskDto.setCategoryId(soTask.getParentId());
							} else if (soTask.getSkillNodeId() != null
									&& soTask.getLevel() == 2) {
								taskDto.setCategoryId(soTask.getSkillNodeId());
							}
							taskDto.setSkill(soTask.getServiceType());
							taskDto.setSkillId(soTask.getServiceTypeId());
							taskDto.setSellingPrice(soTask.getSellingPrice());
							taskDto.setFinalPrice(soTask.getFinalPrice());
							taskDto.setRetailPrice(soTask.getRetailPrice());
							taskDto.setSequenceNumber(soTask.getSequenceNumber());
							//SL-20527 : Setting Task status in DTO to calculate spendLimit Labor.
							taskDto.setTaskStatus(soTask.getTaskStatus());
							if(soTask.getTaskType()!=null && soTask.getTaskType()==1){
								permitPrePaidPrice = permitPrePaidPrice + soTask.getSellingPrice();
							}
							taskDto.setTaskType(soTask.getTaskType());
							taskDto.setSku(soTask.getSku());
							if(null!=soTask.getFinalPrice() && null!=soTask.getRetailPrice() && soTask.getFinalPrice()!=soTask.getRetailPrice()){
								taskDto.setPriceChangedInd(true);
							}
							if(soTask.getPriceHistoryList()!=null){
								SOTaskPriceHistoryDTO taskPriceDTO  = null;
								for(SOTaskPriceHistoryVO taskPriceVO : soTask.getPriceHistoryList()){
									taskPriceDTO = new SOTaskPriceHistoryDTO();
									taskPriceDTO.setPrice(taskPriceVO.getPrice());
									Date date = taskPriceVO.getModifiedDate() ;
									SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
									String displayDate = df.format(date) ;
									taskPriceDTO.setModifiedDate(displayDate.substring(0, 10));
									taskPriceDTO.setModifiedBy(taskPriceVO.getModifiedBy());
									taskPriceDTO.setModifiedByName(taskPriceVO.getModifiedByName());
									taskDto.getPriceHistoryList().add(taskPriceDTO);
							}

							}
							
							soSkus.add(taskDto);
						}
					
					}
					scopeOfWorkDto.setTasks(soSkus);
					//Setting indicator to display the information in sku format and load necessary information on page load
					scopeOfWorkDto.setServiceOrderSkuIndicator(true);
					//End of sku indicator for front end when so going from draft  
					}
				}// End of Skus
				
				
				scopeOfWorkDto.setPermitPrepaidPrice(permitPrePaidPrice);
				//Populate addons
				if(null!=so.getUpsellInfo() && !so.getUpsellInfo().isEmpty()){
					scopeOfWorkDto.setAddonInfo(so.getUpsellInfo());
				}
				// Populating Service Location and Contact.
				SoLocation serviceLocation = new SoLocation();
				Contact serviceContact = new Contact();
				Contact altServiceContact = new Contact();
				Contact serviceContactFax = new Contact();
				SOWContactLocationDTO contactLocationDto = new SOWContactLocationDTO();
				if (so.getServiceLocation() != null) {

					altServiceContact = so.getServiceContactAlt();
					serviceLocation = so.getServiceLocation();
					serviceContactFax = so.getServiceContactFax();
					if (serviceLocation.getCreatedDate() != null) {
						contactLocationDto.setCreatedDate(serviceLocation
								.getCreatedDate());
					} else {
						contactLocationDto.setCreatedDate(DateUtils
								.getCurrentDate());
						serviceLocation.setCreatedDate(new Timestamp(DateUtils
								.getCurrentDate().getTime()));
					}
					contactLocationDto.setAptNo(serviceLocation.getAptNo());
					contactLocationDto.setCity(serviceLocation.getCity());
					contactLocationDto.setState(serviceLocation.getState());
					contactLocationDto.setZip(serviceLocation.getZip());
					contactLocationDto.setZip4(serviceLocation.getZip4());
					//added ofr SL-16791
					contactLocationDto.setLocationName(serviceLocation.getLocnName());
					if (serviceLocation.getLocnClassId() != null) {
						contactLocationDto.setLocationTypeId(serviceLocation
								.getLocnClassId().toString());
					}
					contactLocationDto.setStreetName1(serviceLocation
							.getStreet1());
					contactLocationDto.setStreetName2(serviceLocation
							.getStreet2());
				}
				if (so.getServiceContact() != null) {
					serviceContact = so.getServiceContact();
					if (serviceContact != null) {
						if (serviceContact.getCreatedDate() != null) {
							contactLocationDto.setCreatedDate(serviceContact
									.getCreatedDate());
						} else {
							contactLocationDto.setCreatedDate(DateUtils
									.getCurrentDate());
							serviceContact.setCreatedDate(new Timestamp(
									DateUtils.getCurrentDate().getTime()));
						}
						contactLocationDto.setBusinessName(serviceContact
								.getBusinessName());
						contactLocationDto.setFirstName(serviceContact
								.getFirstName());
						contactLocationDto.setLastName(serviceContact
								.getLastName());
						contactLocationDto.setEmail(serviceContact.getEmail());
						contactLocationDto.setFax(serviceContact.getFaxNo());
					}

					contactLocationDto.setServiceLocationNote(serviceLocation
							.getLocnNotes());

					List<SOWPhoneDTO> phonesDtos = new ArrayList<SOWPhoneDTO>();
					if (serviceContact != null
							&& serviceContact.getPhoneNo() != null) {

						String priPhone = serviceContact.getPhoneNo();

						SOWPhoneDTO phonesDto = new SOWPhoneDTO();
						if (serviceContact.getPhoneCreatedDate() != null) {
							phonesDto.setCreatedDate(serviceContact
									.getPhoneCreatedDate());
						} else {
							phonesDto
									.setCreatedDate(DateUtils.getCurrentDate());
							serviceContact.setPhoneCreatedDate(DateUtils
									.getCurrentDate());
						}
						if (priPhone != null && priPhone.length() == 10) {
							phonesDto.setAreaCode(priPhone.substring(0, 3));
							phonesDto.setPhonePart1(priPhone.substring(3, 6));
							phonesDto.setPhonePart2(priPhone.substring(6, 10));
						}
						if (serviceContact.getPhoneClassId() != null) {
							phonesDto.setPhoneClassId(new Integer(
									serviceContact.getPhoneClassId()));
						}
						if (serviceContact.getPhoneNoExt() != null) {
							phonesDto.setExt(serviceContact.getPhoneNoExt());
						}
						phonesDto.setPhoneType(1);
						phonesDtos.add(phonesDto);

					} else if (serviceContact != null
							&& serviceContact.getPhoneNo() == null) {
						SOWPhoneDTO phonesDto = new SOWPhoneDTO();
						phonesDto.setPhoneType(1);
						phonesDtos.add(phonesDto);
					}

					if (altServiceContact != null
							&& altServiceContact.getPhoneNo() != null) {

						String altPhone = altServiceContact.getPhoneNo();

						SOWPhoneDTO altPhonesDto = new SOWPhoneDTO();

						if (altServiceContact.getPhoneCreatedDate() != null) {
							altPhonesDto.setCreatedDate(altServiceContact
									.getPhoneCreatedDate());
						} else {
							altPhonesDto.setCreatedDate(DateUtils
									.getCurrentDate());
							altServiceContact.setPhoneCreatedDate(DateUtils
									.getCurrentDate());
						}
						if (altPhone != null && altPhone.length() == 10) {
							altPhonesDto.setAreaCode(altPhone.substring(0, 3));
							altPhonesDto
									.setPhonePart1(altPhone.substring(3, 6));
							altPhonesDto.setPhonePart2(altPhone
									.substring(6, 10));
						}
						if (altServiceContact.getPhoneClassId() != null) {
							altPhonesDto.setPhoneClassId(new Integer(
									altServiceContact.getPhoneClassId()));
						}
						if (altServiceContact.getPhoneNoExt() != null) {
							altPhonesDto.setExt(altServiceContact
									.getPhoneNoExt());
						}
						altPhonesDto.setPhoneType(2);
						phonesDtos.add(altPhonesDto);

					} else if (altServiceContact != null
							&& altServiceContact.getPhoneNo() == null) {
						SOWPhoneDTO altPhonesDto = new SOWPhoneDTO();
						altPhonesDto.setPhoneType(2);
						phonesDtos.add(altPhonesDto);
					}

					if (serviceContactFax != null
							&& serviceContactFax.getPhoneNo() != null) {

						String Faxphone = serviceContactFax.getPhoneNo();

						SOWPhoneDTO FaxphonesDto = new SOWPhoneDTO();

						// phonesDto.setPhone(p);
						if (Faxphone != null && Faxphone.length() == 10) {
							FaxphonesDto.setAreaCode(Faxphone.substring(0, 3));
							FaxphonesDto
									.setPhonePart1(Faxphone.substring(3, 6));
							FaxphonesDto.setPhonePart2(Faxphone
									.substring(6, 10));
						}
						if (serviceContactFax.getPhoneClassId() != null) {
							FaxphonesDto.setPhoneClassId(new Integer(
									serviceContactFax.getPhoneClassId()));
						}

						FaxphonesDto.setPhoneType(3);
						phonesDtos.add(FaxphonesDto);

					} else if (serviceContactFax != null
							&& serviceContactFax.getPhoneNo() == null) {
						SOWPhoneDTO FaxphonesDto = new SOWPhoneDTO();
						FaxphonesDto.setPhoneType(3);
						phonesDtos.add(FaxphonesDto);
					}

					contactLocationDto.setPhones(phonesDtos);

				}
				scopeOfWorkDto.setServiceLocationContact(contactLocationDto);
				scopeOfWorkDto.setTitle(so.getSowTitle());
				scopeOfWorkDto.setOverview(so.getSowDs());
				scopeOfWorkDto.setSpecialInstructions(so
						.getProviderInstructions());
				scopeOfWorkDto.setBuyerTandC(so.getBuyerTermsCond());

				if (so.getServiceDate1() != null
						&& !so.getServiceDate1().equals("")) {
					scopeOfWorkDto.setServiceDate1(getStringFromTimeStamp(so
							.getServiceDate1()));
				}
				if (so.getServiceDate2() != null
						&& !so.getServiceDate2().equals("")) {
					scopeOfWorkDto.setServiceDate2(getStringFromTimeStamp(so
							.getServiceDate2()));
				}

				scopeOfWorkDto.setStartTime(so.getServiceTimeStart());
				scopeOfWorkDto.setEndTime(so.getServiceTimeEnd());
				scopeOfWorkDto.setConfirmServiceTime(so
						.getProviderServiceConfirmInd());
				scopeOfWorkDto.setServiceDateType(so.getServiceDateTypeId());
				
				if(so.getServiceLocationTimeZone() != null){
					scopeOfWorkDto.setTimeZone(so.getServiceLocationTimeZone());
				}
				
				scopeOfWorkDto.setCreatedDateString(so.getCreatedDate()
						.toString());
				scopeOfWorkDto.setModifiedDate(so.getModifiedDate());

				ArrayList<SODocumentDTO> documentsDtos = new ArrayList<SODocumentDTO>();
				if (so.getSoDocuments() != null
						&& so.getSoDocuments().size() > 0) {
					List<SODocument> documentsVos = so.getSoDocuments();

					for (int i = 0; i < documentsVos.size(); i++) {
						SODocument documentVO = documentsVos.get(i);
						SODocumentDTO documentsDto = new SODocumentDTO();

						documentsDto.setName(documentVO.getFileName());
						documentsDto.setSize(documentVO.getFileSize());
						documentsDto.setDesc(documentVO.getFileDesc());
						documentsDtos.add(documentsDto);
					}
					scopeOfWorkDto.setDocuments(documentsDtos);
				}
				//SL-21070
				if(null != so.getLoctEditInd() && OrderConstants.LOCK_EDIT_TRUE == so.getLoctEditInd().intValue()){
					scopeOfWorkDto.setLockEditInd(true);
				}
				
			}// End of scope of work tab.
		} catch (Exception e) {
			logger.info("Caught Exception and ignoring",e);
		}
		return scopeOfWorkDto;
	}

	private static SOWScopeOfWorkTabDTO assignServiceOrderVOToScopeOfWorkDTO(List<ServiceOrder> serviceOrders) {
		
		SOWScopeOfWorkTabDTO scopeOfWorkDto = new SOWScopeOfWorkTabDTO();
			
		// Populate the tasks
		scopeOfWorkDto.setTasks(populateTasks(serviceOrders));
		
			// Populate Service Location and Contact
		

		return scopeOfWorkDto;
	}
	
	private static void assignScopeOfWorkToServiceOrderVO(ServiceOrder soVO,
			SOWScopeOfWorkTabDTO scopeOfWorkDto) {

		try {
			if (null != scopeOfWorkDto) {
				soVO.setIsEditMode(scopeOfWorkDto.getIsEditMode());
				soVO.setPrimarySkillCatId(scopeOfWorkDto
						.getMainServiceCategoryId());
				if((null == scopeOfWorkDto.getServiceOrderTypeIndicator()) || (!scopeOfWorkDto.getServiceOrderTypeIndicator().equalsIgnoreCase("SoUsingSku")))
				{
				
				// Populating tasks
				List<ServiceOrderTask> tasks = new ArrayList<ServiceOrderTask>();
				if (scopeOfWorkDto.getTasks() != null
						&& scopeOfWorkDto.getTasks().size() > 0) {

					Iterator<SOTaskDTO> taskDtoItr = scopeOfWorkDto.getTasks()
							.iterator();
					boolean isPrimaryTaskSet =false;
					while (taskDtoItr.hasNext()) {
						SOTaskDTO taskDto = (SOTaskDTO) taskDtoItr.next();
						ServiceOrderTask task = new ServiceOrderTask();
						task.setTaskName(taskDto.getTaskName());
						if (taskDto.getEntityId() != null) {
							task.setTaskId(taskDto.getEntityId());
							
							task.setAutoInjectedInd(taskDto.getAutoInjected());
							task.setSortOrder(taskDto.getSortOrder());
							task.setPrimaryTask(taskDto.isPrimaryTask());
							if(taskDto.isPrimaryTask()){
								isPrimaryTaskSet = true;
							}
							//if(soVO.getBuyerId()== 1000){
							logger.info("taskDto.getSequenceNumber()"+taskDto.getSequenceNumber());
							logger.info("taskDto.getFinalPrice()"+taskDto.getFinalPrice());
							logger.info("taskDto.getSellingPrice()"+taskDto.getSellingPrice());
							logger.info("taskDto.getTaskType()"+taskDto.getTaskType());
							task.setSequenceNumber(taskDto.getSequenceNumber());
							
								task.setTaskType(taskDto.getTaskType());
								task.setSku(taskDto.getSku());
							//}*/
							
								
						}

						if (null != taskDto.getSubCategoryId()
								&& taskDto.getSubCategoryId() >= 0) {
							task.setSkillNodeId(taskDto.getSubCategoryId());
						} else if (null != taskDto.getCategoryId()
								&& taskDto.getCategoryId() >= 0) {
							task.setSkillNodeId(taskDto.getCategoryId());
						}
						if (taskDto.getCreatedDate() != null) {
							task.setCreateDate(taskDto.getCreatedDate());
						} else {
							task.setCreateDate(DateUtils.getCurrentDate());
						}
						//*****Changes done to populate the price column in so_tasks*****//
						if(taskDto.getPrice()!=null){
							task.setPrice(taskDto.getPrice());
						}
						//while so creation through frontend, price will be null, so setting final_price as price in so_tasks
						else{
							task.setPrice(taskDto.getFinalPrice());
						}
						task.setRetailPrice(taskDto.getRetailPrice());
						task.setFinalPrice(taskDto.getFinalPrice());
						task.setSellingPrice(taskDto.getSellingPrice());
						task.setServiceTypeId(taskDto.getSkillId());
						task.setTaskComments(taskDto.getComments());
						tasks.add(task);
					}
					if(!isPrimaryTaskSet){
						for (ServiceOrderTask task:tasks) {
							task.setPrimaryTask(true);
							break;
							}
						}
				}// End of Tasks
				soVO.setTasks(tasks);
				soVO.setSkuTaskIndicator(false);
				
				}
				//Populating the Skus if sku selected
				else
				{
					List<ServiceOrderTask> skus = new ArrayList<ServiceOrderTask>();
					//Variable for summing of the bid price as labor
				//	Double totalLabourPrice=0.0;
					if (scopeOfWorkDto.getSkus() != null
							&& scopeOfWorkDto.getSkus().size() > 0) {

						Iterator<SOTaskDTO> skuDtoItr = scopeOfWorkDto.getSkus()
								.iterator();
						Integer primaryTaskCheck=0;
						while (skuDtoItr.hasNext()) {
							SOTaskDTO skuDto = (SOTaskDTO) skuDtoItr.next();
							ServiceOrderTask sku = new ServiceOrderTask();
							sku.setTaskName(skuDto.getTaskName());
							
								sku.setTaskId(skuDto.getEntityId());
								
							//*****Changes done to populate the price column in so_tasks*****//
								if(skuDto.getPrice()!=null){
									sku.setPrice(skuDto.getPrice());
								}
								//while so creation through frontend, price will be null, so setting final_price as price in so_tasks
								else{
									sku.setPrice(skuDto.getFinalPrice());
								}
								sku.setAutoInjectedInd(skuDto.getAutoInjected());
								sku.setSortOrder(skuDto.getSortOrder());
								if(primaryTaskCheck==0)
								{
								sku.setPrimaryTask(true);
								}
								else
								{
									sku.setPrimaryTask(false);
								}
								logger.info("taskDto.getSequenceNumber()"+skuDto.getSequenceNumber());
								logger.info("taskDto.getFinalPrice()"+skuDto.getFinalPrice());
								logger.info("taskDto.getSellingPrice()"+skuDto.getSellingPrice());
								logger.info("taskDto.getTaskType()"+skuDto.getTaskType());
								sku.setSequenceNumber(skuDto.getSequenceNumber());
								
								sku.setTaskType(skuDto.getTaskType());
								//sku.setSku("SoUsingSku");
								sku.setSku(skuDto.getSku());
							
//								Double totalLaborPriceNullCheck=skuDto.getPrice();
//									if(totalLaborPriceNullCheck!=null)
//									{
//						
//										totalLabourPrice=totalLabourPrice+skuDto.getPrice();
//									}
							
							if(null!=skuDto.getSubCategoryId() && skuDto.getSubCategoryId()>0){
										sku.setSkillNodeId(skuDto.getSubCategoryId());
									}else{
										sku.setSkillNodeId(skuDto.getCategoryId());
									}
							sku.setCreateDate(skuDto.getCreatedDate());
							sku.setCreateDate(DateUtils.getCurrentDate());
							sku.setRetailPrice(skuDto.getRetailPrice());
							sku.setFinalPrice(skuDto.getFinalPrice());
							sku.setSellingPrice(skuDto.getSellingPrice());
							sku.setServiceTypeId(skuDto.getSkillId());
							sku.setTaskComments(skuDto.getComments());
							sku.setSkuId(skuDto.getSkuIdForSku());
							skus.add(sku);
							primaryTaskCheck=primaryTaskCheck+1;
						}

					}
					// End of Skus
					soVO.setSkus(skus);
					//Setting the indicator true to make sure it is created using sku and adding bid price of each sku and for displaying in labor
					//Also to map the information from sku list to the last task entity class in order fulfillment class
					soVO.setSkuTaskIndicator(true);
					//Setting the value of Maximum labor Price for pricing tab as sum of bid price of all selected skus
					//soVO.setSpendLimitLabor(totalLabourPrice);
				}
				}
					
			
				
				//setting the addon info
				if(null!=scopeOfWorkDto.getAddonInfo() && !scopeOfWorkDto.getAddonInfo().isEmpty()){
					soVO.setUpsellInfo(scopeOfWorkDto.getAddonInfo());
					//Assign NULL to coverage type if it is empty as per SL-17482
					for(ServiceOrderAddonVO addonVO : soVO.getUpsellInfo()){
                        if(StringUtils.isEmpty(addonVO.getCoverage())){
                                addonVO.setCoverage(null);
                        }                                              
					}
				}
				// Populating Service Location and Contact.
				SoLocation serviceLocation = new SoLocation();
				Contact serviceContact = new Contact();
				if (null != scopeOfWorkDto.getServiceLocationContact()) {
					SOWContactLocationDTO contactLocationDto = scopeOfWorkDto
							.getServiceLocationContact();
					if (contactLocationDto.getCreatedDate() != null) {
						serviceLocation.setCreatedDate(new Timestamp(
								contactLocationDto.getCreatedDate().getTime()));
					} else {
						serviceLocation.setCreatedDate(new Timestamp(DateUtils
								.getCurrentDate().getTime()));
						contactLocationDto.setCreatedDate(new Timestamp(
								DateUtils.getCurrentDate().getTime()));
					}
					serviceLocation.setAptNo(contactLocationDto.getAptNo());
					serviceLocation.setCity(contactLocationDto.getCity());
					serviceLocation.setCountry(OrderConstants.COUNTRY_USA_CODE);
					serviceLocation.setState(contactLocationDto.getState());
					serviceLocation.setStreet1(contactLocationDto
							.getStreetName1());
					serviceLocation.setStreet2(contactLocationDto
							.getStreetName2());
					serviceLocation.setZip(contactLocationDto.getZip());
					serviceLocation.setZip4(contactLocationDto.getZip4());
					serviceLocation.setLocnNotes(contactLocationDto
							.getServiceLocationNote());
					//added for SL-16791
					serviceLocation.setLocnName(contactLocationDto.getLocationName());
					if (contactLocationDto.getLocationTypeId() != null) {
						serviceLocation.setLocnClassId(new Integer(
								contactLocationDto.getLocationTypeId()));
					}
					serviceLocation
							.setLocnTypeId(OrderConstants.SERVICE_LOCATION_CONTACT_TYPE_ID);

					if (contactLocationDto.getCreatedDate() != null) {
						serviceContact.setCreatedDate(new Timestamp(
								contactLocationDto.getCreatedDate().getTime()));
					} else {
						serviceContact.setCreatedDate(new Timestamp(DateUtils
								.getCurrentDate().getTime()));
						contactLocationDto.setCreatedDate(new Timestamp(
								DateUtils.getCurrentDate().getTime()));
					}
					serviceContact.setBusinessName(contactLocationDto
							.getBusinessName());
					serviceContact.setFirstName(contactLocationDto
							.getFirstName());
					serviceContact
							.setLastName(contactLocationDto.getLastName());
					serviceContact.setEmail(contactLocationDto.getEmail());
					serviceContact.setFaxNo(contactLocationDto.getFax());
					if (null != contactLocationDto.getPhones()
							&& contactLocationDto.getPhones().size() > 0) {
						List<SOWPhoneDTO> phonesDto = contactLocationDto
								.getPhones();
						List<PhoneVO> phonesVo = new ArrayList<PhoneVO>();
						int phonesSize = phonesDto.size();

						for (int i = 0; i < phonesSize; i++) {
							SOWPhoneDTO phoneDto = (SOWPhoneDTO) phonesDto
									.get(i);
							PhoneVO phoneVo = new PhoneVO();
							if (phoneDto.getCreatedDate() != null) {
								phoneVo.setCreatedDate(new Timestamp(phoneDto
										.getCreatedDate().getTime()));
							} else {
								phoneVo.setCreatedDate(new Timestamp(DateUtils
										.getCurrentDate().getTime()));
								phoneDto.setCreatedDate(new Timestamp(DateUtils
										.getCurrentDate().getTime()));
							}
							phoneVo.setPhoneNo(phoneDto.getPhone());
							phoneVo.setPhoneExt(phoneDto.getExt());
							phoneVo.setPhoneType(i + 1);
							phoneVo.setClassId(phoneDto.getPhoneClassId());

							if (phoneDto.getPhoneType() != 3)
								phonesVo.add(phoneVo);
							else {
								phoneVo.setClassId(5);
								phonesVo.add(phoneVo);
							}
						}
						serviceContact.setPhones(phonesVo);
					}

					soVO.setServiceContact(serviceContact);
					soVO.setServiceLocation(serviceLocation);
				}
				soVO.setSowTitle(scopeOfWorkDto.getTitle());
				soVO.setSowDs(scopeOfWorkDto.getOverview());
				soVO.setProviderInstructions(scopeOfWorkDto
						.getSpecialInstructions());
				soVO.setBuyerTermsCond(scopeOfWorkDto.getBuyerTandC());

				if (null != scopeOfWorkDto.getServiceDate1()
						&& !scopeOfWorkDto.getServiceDate1().equals("")) {

					soVO.setServiceDate1(new Timestamp(getDateFromString(
							scopeOfWorkDto.getServiceDate1(), "yyyy-MM-dd")
							.getTime()));
				}
				if (null != scopeOfWorkDto.getServiceDate2()
						&& !scopeOfWorkDto.getServiceDate2().equals("")) {

					soVO.setServiceDate2(new Timestamp(getDateFromString(
							scopeOfWorkDto.getServiceDate2(), "yyyy-MM-dd")
							.getTime()));
				}
				if (scopeOfWorkDto.getEndTime() != null
						&& !scopeOfWorkDto.getEndTime().equalsIgnoreCase(
								"[HH:MM]")) {
					soVO.setServiceTimeEnd(scopeOfWorkDto.getEndTime());
				}
				if (scopeOfWorkDto.getStartTime() != null
						&& !scopeOfWorkDto.getStartTime().equalsIgnoreCase(
								"[HH:MM]")) {
					soVO.setServiceTimeStart(scopeOfWorkDto.getStartTime());
				}
				soVO.setServiceDateTypeId(scopeOfWorkDto.getServiceDateType());
				soVO.setProviderServiceConfirmInd(scopeOfWorkDto
						.getConfirmServiceTime());
				if (null != scopeOfWorkDto.getDocuments()
						&& scopeOfWorkDto.getDocuments().size() > 0) {
					List<SODocumentDTO> documentsDto = scopeOfWorkDto
							.getDocuments();
					List<SODocument> documentsVo = new ArrayList<SODocument>();
					for (int i = 0; i < documentsDto.size(); i++) {
						SODocumentDTO documentDto = (SODocumentDTO) documentsDto
								.get(i);
						SODocument documentVo = new SODocument();
						documentVo.setFileName(documentDto.getName());
						documentVo.setFileSize(documentDto.getSize());
						documentVo.setFileDesc(documentDto.getDesc());
						documentsVo.add(documentVo);
					}
					soVO.setSoDocuments(documentsVo);
				}
				soVO.setTaskLevelPricingInd(scopeOfWorkDto.isTaskLevelPricingInd());
			// End of scope of work tab.
			}
			
						 catch (Exception e) {
			logger.info("Caught Exception and ignoring",e);
		}
	}

	private static SOWPartsTabDTO assignServiceOrderVOToPartsDto(
			ServiceOrder soVo) {
		SOWPartsTabDTO partsTabDto = new SOWPartsTabDTO();

		try {
			
			if (soVo != null) {
				if (null != soVo.getPartsSupplier()) {
					partsTabDto.setPartsSuppliedBy(soVo.getPartsSupplier()
							.toString());
				}
				
				if (null != soVo.getParts() && soVo.getParts().size() > 0) {
					List<Part> partsVo = soVo.getParts();
					List<SOWPartDTO> partsDto = new ArrayList<SOWPartDTO>();
					for (int i = 0; i < partsVo.size(); i++) {
						Part partVo = partsVo.get(i);
						SOWPartDTO partDto = new SOWPartDTO();
						partDto.setReferencePartId(partVo.getReferencePartId());
						if (partVo.getShipDate() != null
								&& !partVo.getShipDate().equals("")) {
							partDto.setShipDate(getStringFromTimeStamp(partVo.getShipDate()));
						}else{
							partDto.setShipDate("");
						}
						if (partVo.getReturnTrackDate() != null
								&& !partVo.getReturnTrackDate().equals("")) {
							partDto.setReturnTrackDate(getStringFromTimeStamp(partVo.getReturnTrackDate()));
						}else{
							partDto.setReturnTrackDate("");
						}
						if (partVo.getPartId() != null) {
							partDto.setEntityId(partVo.getPartId());
						}
						if (partVo.getCreatedDate() != null) {
							partDto.setCreatedDate(partVo.getCreatedDate());
						} else {
							partDto.setCreatedDate(DateUtils.getCurrentDate());
							partVo.setCreatedDate(DateUtils.getCurrentDate());
							;
						}

						partDto.setHeight(partVo.getHeight());
						partDto.setWeight(partVo.getWeight());
						partDto.setWidth(partVo.getWidth());
						partDto.setLength(partVo.getLength());
						partDto.setSerialNumber(partVo.getSerialNumber());
						partDto.setManufacturer(partVo.getManufacturer());
						partDto.setManufacturerPartNumber(partVo.getManufacturerPartNumber());
						partDto.setVendorPartNumber(partVo.getVendorPartNumber());
						partDto.setProductLine(partVo.getProductLine());
						partDto.setAdditionalPartInfo(partVo.getAdditionalPartInfo());
						partDto.setOrderNumber(partVo.getOrderNumber());
						partDto.setPurchaseOrderNumber(partVo.getPurchaseOrderNumber());
						partDto.setPartStatusId(partVo.getPartStatusId());
						if (partVo.getMeasurementStandard() != null)
							partDto.setStandard(partVo.getMeasurementStandard()
									.toString());
						partDto.setModelNumber(partVo.getModelNumber());
						partDto.setPartDesc(partVo.getPartDs());
						partDto.setAltPartRef1(partVo.getAltPartRef1());
						partDto.setAltPartRef2(partVo.getAltPartRef2());
						partDto.setProviderBringparts(partVo
								.isProviderBringPartInd());
						if (partVo.getQuantity() != null
								&& !partVo.getQuantity().equals("")) {
							partDto
									.setQuantity(partVo.getQuantity()
											.toString());
						}
						else
						{
							partDto.setQuantity("1");
						}
						if (partVo.getShippingCarrier() != null) {
							partDto.setShippingCarrierId(partVo
									.getShippingCarrier().getCarrierId());
							if (partVo.getShippingCarrier().getCarrierId() == 5
									&& partVo.getShipCarrierOther() != null)
								partDto.setOtherShippingCarrier(partVo
										.getShipCarrierOther());

							partDto.setShippingTrackingNo(partVo
									.getShippingCarrier().getTrackingNumber());
							partDto.setOtherShippingCarrier(partVo
									.getShipCarrierOther());
						}
						if (partVo.getReturnCarrier() != null) {
							partDto.setReturnCarrierId(partVo
									.getReturnCarrier().getCarrierId());
							if (partVo.getReturnCarrierOther() != null)
								partDto.setOtherReturnCarrier(partVo
										.getReturnCarrierOther());
							partDto.setReturnTrackingNo(partVo
									.getReturnCarrier().getTrackingNumber());
						}
						
						if (partVo.getCoreReturnCarrier() != null) {
							partDto.setCoreReturnCarrierId(partVo
									.getCoreReturnCarrier().getCarrierId());
							if (partVo.getCoreReturnCarrierOther() != null)
								partDto.setOtherCoreReturnCarrier(partVo
										.getCoreReturnCarrierOther());
							partDto.setCoreReturnTrackingNo(partVo
									.getCoreReturnCarrier().getTrackingNumber());
						}
						

						//if (partVo.isProviderBringPartInd()!=null && partVo.isProviderBringPartInd()) {
							SOWContactLocationDTO pickupContactLocationDto = new SOWContactLocationDTO();
							if (partVo.getPickupContact() != null) {
								Contact pickupContactVo = partVo
										.getPickupContact();
								Contact altPickupContactVo = partVo
										.getAltPickupContact();

								pickupContactLocationDto
										.setBusinessName(pickupContactVo
												.getBusinessName());
								pickupContactLocationDto
										.setFirstName(pickupContactVo
												.getFirstName());
								pickupContactLocationDto
										.setLastName(pickupContactVo
												.getLastName());
								pickupContactLocationDto
										.setEmail(pickupContactVo.getEmail());
								List<SOWPhoneDTO> phonesDtos = new ArrayList<SOWPhoneDTO>();
								if (null != pickupContactVo.getPhoneNo()) {
									SOWPhoneDTO phonesDto = new SOWPhoneDTO();

									String phoneNo = pickupContactVo
											.getPhoneNo();
									if (pickupContactVo.getPhoneCreatedDate() != null) {
										phonesDto
												.setCreatedDate(pickupContactVo
														.getPhoneCreatedDate());
									} else {
										phonesDto.setCreatedDate(DateUtils
												.getCurrentDate());
										pickupContactVo
												.setPhoneCreatedDate(new Timestamp(
														DateUtils
																.getCurrentDate()
																.getTime()));
									}
									if (phoneNo != null
											&& phoneNo.length() == 10) {
										phonesDto.setAreaCode(phoneNo
												.substring(0, 3));
										phonesDto.setPhonePart1(phoneNo
												.substring(3, 6));
										phonesDto.setPhonePart2(phoneNo
												.substring(6, 10));
									}
									if (pickupContactVo.getPhoneTypeId() != null) {
										phonesDto.setPhoneType(pickupContactVo
												.getPhoneTypeId());
									}
									if (pickupContactVo.getPhoneClassId() != null) {
										phonesDto.setPhoneClassId(new Integer(
												pickupContactVo
														.getPhoneClassId()));
									}
									if (pickupContactVo.getPhoneNoExt() != null) {
										phonesDto.setExt(pickupContactVo
												.getPhoneNoExt());
									}
									phonesDto.setPhoneType(1);
									phonesDtos.add(phonesDto);

								}

								if (null != altPickupContactVo.getPhoneNo()) {
									SOWPhoneDTO altPhonesDto = new SOWPhoneDTO();
									String altphoneNo = altPickupContactVo
											.getPhoneNo();
									if (altPickupContactVo
											.getPhoneCreatedDate() != null) {
										altPhonesDto
												.setCreatedDate(altPickupContactVo
														.getPhoneCreatedDate());
									} else {
										altPhonesDto.setCreatedDate(DateUtils
												.getCurrentDate());
										altPickupContactVo
												.setPhoneCreatedDate(DateUtils
														.getCurrentDate());
									}
									if (altphoneNo != null
											&& altphoneNo.length() == 10) {
										altPhonesDto.setAreaCode(altphoneNo
												.substring(0, 3));
										altPhonesDto.setPhonePart1(altphoneNo
												.substring(3, 6));
										altPhonesDto.setPhonePart2(altphoneNo
												.substring(6, 10));
									}
									if (altPickupContactVo.getPhoneClassId() != null) {
										altPhonesDto
												.setPhoneClassId(new Integer(
														altPickupContactVo
																.getPhoneClassId()));
									}
									if (altPickupContactVo.getPhoneNoExt() != null) {
										altPhonesDto.setExt(altPickupContactVo
												.getPhoneNoExt());
									}
									altPhonesDto.setPhoneType(2);
									// Phone classId has to be derived
									/*
									 * phonesDto.setPhoneClassId(phonesVo.getClassId());
									 */

									phonesDtos.add(altPhonesDto);

								}
								pickupContactLocationDto.setPhones(phonesDtos);
								if(phonesDtos!=null && phonesDtos.size()!=0)
								{
									partDto.setHasPartsAtDifferntLocation(true);
								}
							}
							if (partVo.getPickupLocation() != null) {
								SoLocation pickupLocationVo = partVo
										.getPickupLocation();
								pickupContactLocationDto
										.setAptNo(pickupLocationVo.getAptNo());
								pickupContactLocationDto
										.setStreetName1(pickupLocationVo
												.getStreet1());
								pickupContactLocationDto
										.setStreetName2(pickupLocationVo
												.getStreet2());
								pickupContactLocationDto
										.setCity(pickupLocationVo.getCity());
								pickupContactLocationDto
										.setState(pickupLocationVo.getState());
								pickupContactLocationDto
										.setZip(pickupLocationVo.getZip());
								pickupContactLocationDto
										.setZip4(pickupLocationVo.getZip4());
								partDto
										.setPickupContactLocation(pickupContactLocationDto);

							}
							if (partVo.getPickupContact()!=null && (partVo.getPickupContact().getEmail() != null
									|| partVo.getPickupContact()
											.getBusinessName() != null
									|| partVo.getPickupContact().getFirstName() != null
									|| partVo.getPickupContact().getLastName() != null
									|| !partVo.getPickupLocation().getCity()
											.equals("")
									|| !partVo.getPickupLocation().getStreet1()
											.equals("")
									|| !partVo.getPickupLocation().getStreet2()
											.equals("")
									|| !partVo.getPickupLocation().getAptNo()
											.equals("")
									|| !partVo.getPickupLocation().getStreet1()
											.equals("")
									|| !partVo.getPickupLocation().getZip()
											.equals("")
									|| !partVo.getPickupLocation().getState()
											.equals(""))) {

								partDto.setHasPartsAtDifferntLocation(true);
							} 
							
						//}
						if (partVo.isProviderBringPartInd()!=null && partVo.isProviderBringPartInd()) {
							partDto.setHasPartsAtDifferntLocation(true);
						}else{
							partDto.setHasPartsAtDifferntLocation(false);
						}
						partsDto.add(partDto);
						
					}
					partsTabDto.setParts(partsDto);
				}
			}
		} catch (Exception e) {
			logger.info("Caught Exception and ignoring",e);
		}

		return partsTabDto;
	}

	private static SOWPricingTabDTO assignServiceOrderVoToPricingDto(
			ServiceOrder SoVo, HashMap<String, Object> sowTabDtoMap) {
		SOWPricingTabDTO pricingTabDto = new SOWPricingTabDTO();
		
		
		try {
			if (pricingTabDto != null) {
				if(null!=SoVo){
					
				if (null != SoVo.getCustomRefs()) {
					pricingTabDto.getCustomRefs().addAll(SoVo.getCustomRefs());
				}

				if (SoVo.getPostingFee() != null) {
					pricingTabDto.setPostingFee(SoVo.getPostingFee().doubleValue());
				}
				if(null!=SoVo.getBuyerId())
				pricingTabDto.setBuyerId(SoVo.getBuyerId().toString());
				if (SoVo.getPriceModel() != null) {
					pricingTabDto.setOrderType(SoVo.getPriceModel());
					if (!SoVo.getPriceModel().equals(Constants.PriceModel.ZERO_PRICE_BID)) {
						if (SoVo.getSpendLimitLabor() != null) {
							pricingTabDto.setLaborSpendLimit(String.valueOf(SoVo.getSpendLimitLabor()));
							pricingTabDto.setInitialLaborSpendLimit(SoVo.getSpendLimitLabor());
							
							
						}
						if (SoVo.getSpendLimitParts() != null) {
							pricingTabDto.setPartsSpendLimit(String.valueOf(SoVo.getSpendLimitParts()));
							pricingTabDto.setInitialPartsSpendLimit(SoVo.getSpendLimitParts());
						}
						

						if(null!=SoVo.getSoPrice()){
							
							pricingTabDto.setLaborTaxPercentage(String.valueOf(SoVo.getSoPrice().getLaborTax()));
							pricingTabDto.setPartsTax(String.valueOf(SoVo.getSoPrice().getPartsTax()));
							
						}
					}
				}
				
				if (SoVo.isShareContactInd() != null) {
					pricingTabDto.setShareContactInd(SoVo.isShareContactInd());
				} else {
					pricingTabDto.setShareContactInd(false);
				}
				if(null != SoVo.getSealedBidInd() && SoVo.getSealedBidInd()){
					pricingTabDto.setSealedBidInd(true);
				}else{
					pricingTabDto.setSealedBidInd(false);
				}
				
				if (SoVo.getFundingTypeId() != null) {
					pricingTabDto.setFundingType(SoVo.getFundingTypeId());
				}
				//ENH 29281: Code changed to display 'Billing Estimate' on Pricing while editing a SO
				if (SoVo.getRetailPrice() != null) {
					pricingTabDto.setBillingEstimate(SoVo.getRetailPrice());
				}
				if(null != SoVo.getBuyer()){
					pricingTabDto.setCancellationFee(SoVo.getBuyer().getCancellationFee());
				}
				SOWScopeOfWorkTabDTO scopeOfWorkDto = (SOWScopeOfWorkTabDTO) sowTabDtoMap
				.get(OrderConstants.SOW_SOW_TAB);
				pricingTabDto.setTasks(scopeOfWorkDto.getTasks());
				if(OrderConstants.TASK_LEVEL_PRICING.equals(SoVo.getPriceType())){
					pricingTabDto.setTaskLevelPricingInd(true);
					scopeOfWorkDto.setTaskLevelPricingInd(true);
				}else{
					pricingTabDto.setTaskLevelPricingInd(false);
					scopeOfWorkDto.setTaskLevelPricingInd(false);
				}
				pricingTabDto.setPermitPrepaidPrice(scopeOfWorkDto.getPermitPrepaidPrice());
			}
			}
		} catch (Exception e) {
			logger.info("Caught Exception and ignoring",e);
		}
		return pricingTabDto;
	}

	public static ArrayList<RatingParameterBean> ConvertProviderSearchDtoToRatingBean(
			SOWProviderSearchDTO providerSearchDto, LocationVO serviceLocation) {
		ArrayList<RatingParameterBean> ratingParamBeans = new ArrayList<RatingParameterBean>();
		try {

			if (providerSearchDto.getDistance() != null
					&& providerSearchDto.getDistance() > 0
					&& serviceLocation != null) {
				ZipParameterBean zipBean = new ZipParameterBean();
				zipBean.setRadius(providerSearchDto.getDistance());
				zipBean.setStreet(serviceLocation.getStreet1() + " "
						+ serviceLocation.getStreet2());
				zipBean.setCity(serviceLocation.getCity());
				zipBean.setLocState(serviceLocation.getState());
				zipBean.setZipcode(serviceLocation.getZip());

				ratingParamBeans.add(zipBean);
			}
			if (providerSearchDto.getRating() != null
					&& providerSearchDto.getRating() > 0.0) {
				StarParameterBean starBean = new StarParameterBean();
				starBean.setNumberOfStars(providerSearchDto.getRating());
				ratingParamBeans.add(starBean);
			}
			if (providerSearchDto.getSpn() != null
					&& providerSearchDto.getSpn() > 0.0) {
				SPNetParameterBean spnetBean = new SPNetParameterBean();
				spnetBean.setSpnetId(providerSearchDto.getSpn());
				spnetBean.setPerformanceLevels(providerSearchDto.getPerfLevelSelections());
				spnetBean.setPerformanceScore(providerSearchDto.getPerformanceScore());
				spnetBean.setRoutingPriorityApplied(providerSearchDto.isRoutingPriorityApplied());
				ratingParamBeans.add(spnetBean);
			}
			if (providerSearchDto.getSelectedCredential() != null
					&& providerSearchDto.getCredentialCategory() != null && providerSearchDto.getCredentialCategory() >0) {
				CredentialParameterBean credentialBean = new CredentialParameterBean();
				credentialBean.setCredentialId(providerSearchDto
						.getCredentialCategory());
				credentialBean.setCredentialTypeId(providerSearchDto.getSelectedCredential());
				ratingParamBeans.add(credentialBean);
			}
			if (providerSearchDto.getSelectedLang() != null
					&& providerSearchDto.getSelectedLang() > 0) {
				LanguageParameterBean langBean = new LanguageParameterBean();
				List<Integer> langList = new ArrayList<Integer>();
				langList.add(providerSearchDto.getSelectedLang());
				langBean.setSelectedLangs(langList);
				ratingParamBeans.add(langBean);
			}
			if (providerSearchDto.getBackgroundCheck() != null
					&& providerSearchDto.getBackgroundCheck() != 0) {
				BackgroundCheckParameterBean backgroundBean = new BackgroundCheckParameterBean();
				backgroundBean.setBackgroundCheck(providerSearchDto
						.getBackgroundCheck());
				ratingParamBeans.add(backgroundBean);
			}
			
			if (providerSearchDto.getInsuranceFlag() != null
					&& providerSearchDto.getInsuranceFlag() != 0) {
				InsuranceParameterBean insuranceBean = new InsuranceParameterBean();
				ArrayList<Integer> insurTypes = new ArrayList<Integer>();
				insuranceBean.setInsuranceStatus(providerSearchDto
						.getInsuranceFlag());
				if (null != providerSearchDto.getInsuranceTypes()) {
					SOWInsuranceDTO insuranceDto = providerSearchDto
							.getInsuranceTypes();
					if (insuranceDto.isAutomotive()) {
						insurTypes.add(ProviderConstants.INSURANCE_AUTOMOTIVE);
					}
					if (insuranceDto.isGeneralLiability()) {
						insurTypes
								.add(ProviderConstants.INSURANCE_GENERAL_LIABILITY);
					}
					if (insuranceDto.isWorkersCompensation()) {
						insurTypes
								.add(ProviderConstants.INSURANCE_WORKERS_COMPENSATION);
					}
					if (insuranceDto.isSlVerified()) {
						insuranceBean.setVerifiedByServiceLive(true);
					} else {
						insuranceBean.setVerifiedByServiceLive(false);
					}
					insuranceBean.setInsuranceTypes(insurTypes);
				}
				ratingParamBeans.add(insuranceBean);
			}
			//Mapping the values from insurance dropdown's for filtering purpose
			InsuranceRatingParameterBean insuranceRatingBean = new InsuranceRatingParameterBean();
			Map<Integer, InsuranceRatingsLookupVO> selectedInsuranceMap = new HashMap<Integer, InsuranceRatingsLookupVO>();
			if((null!=providerSearchDto.getGeneralLiabilityRating() && providerSearchDto.getGeneralLiabilityRating()!=0)
					||providerSearchDto.isGlRatingCheckBox()==true){
				InsuranceRatingsLookupVO glInsuranceVO = new InsuranceRatingsLookupVO();
				glInsuranceVO.setId(providerSearchDto.getGeneralLiabilityRating());
				glInsuranceVO.setCheckBoxValue(providerSearchDto.isGlRatingCheckBox());
				List<LookupVO> glLookUpList = providerSearchDto.getGeneralLiabilityRatingList();
				for(LookupVO list : glLookUpList){
					if(list.getId().equals(providerSearchDto.getGeneralLiabilityRating())){
						glInsuranceVO.setLimitValue(list.getLimitValue());
					}
				}
				selectedInsuranceMap.put(41, glInsuranceVO);
			}
			if((null!=providerSearchDto.getVehicleLiabilityRating() && providerSearchDto.getVehicleLiabilityRating()!=0)
					||providerSearchDto.isVlRatingCheckBox()==true){
				InsuranceRatingsLookupVO vlInsuranceVO = new InsuranceRatingsLookupVO();
				vlInsuranceVO.setId(providerSearchDto.getVehicleLiabilityRating());
				vlInsuranceVO.setCheckBoxValue(providerSearchDto.isVlRatingCheckBox());
				List<LookupVO> vlLookUpList = providerSearchDto.getVehicleLiabilityRatingList();
				for(LookupVO list : vlLookUpList){
					if(list.getId().equals(providerSearchDto.getVehicleLiabilityRating())){
						vlInsuranceVO.setLimitValue(list.getLimitValue());
					}
				}
				selectedInsuranceMap.put(42, vlInsuranceVO);
			}
			if((null!=providerSearchDto.getWorkersCompensationRating() && providerSearchDto.getWorkersCompensationRating()!=0)
				||providerSearchDto.isWcRatingCheckBox()==true){
				InsuranceRatingsLookupVO wcInsuranceVO = new InsuranceRatingsLookupVO();
				wcInsuranceVO.setId(providerSearchDto.getWorkersCompensationRating());
				wcInsuranceVO.setCheckBoxValue(providerSearchDto.isWcRatingCheckBox());
				List<LookupVO> wcLookUpList = providerSearchDto.getWorkersCompensationRatingList();
				for(LookupVO list : wcLookUpList){
					if(list.getId().equals(providerSearchDto.getWorkersCompensationRating())){
						wcInsuranceVO.setLimitValue(list.getLimitValue());
					}
				}
				selectedInsuranceMap.put(43, wcInsuranceVO);
			}
			
			
			
			
			//SL-10809: Additional Insurance
			//Mapping the values from  additional insurance dropdown's for filtering purpose
		
			Map<Integer, InsuranceRatingsLookupVO> addnSelectedInsuranceMap = new HashMap<Integer, InsuranceRatingsLookupVO>();
			String addnCategoryId="";
			
			if ((null != providerSearchDto.getSelectedAddnInsurances() && providerSearchDto
					.getSelectedAddnInsurances().size() > 0)
					|| providerSearchDto.isAddRatingCheckBox() == true) {
				for (Integer id : providerSearchDto.getSelectedAddnInsurances()) {
					if (null!=id && id != 0 && id != 16) {
						Integer categoryId=0;
						InsuranceRatingsLookupVO addnInsuranceVO = new InsuranceRatingsLookupVO();
						addnInsuranceVO.setId(id);
						addnInsuranceVO.setCheckBoxValue(providerSearchDto
								.isAddRatingCheckBox());
						List<LookupVO> addnLookUpList = providerSearchDto
								.getAdditionalInsuranceList();

						for (LookupVO list : addnLookUpList) {
							if (list.getId().equals(id)) {
								addnCategoryId = list.getType();
								addnInsuranceVO.setType(list.getType());
							}
						}
						if (StringUtils.isNotBlank(addnCategoryId)) {
							categoryId = Integer.parseInt(addnCategoryId);
						}
						addnSelectedInsuranceMap.put(categoryId,
								addnInsuranceVO);
					}
					if(id==16)
					{
						for(int i=142; i<=150;i++)
						{
							InsuranceRatingsLookupVO addnInsuranceVO = new InsuranceRatingsLookupVO();
							addnInsuranceVO.setId(id);
							addnInsuranceVO.setCheckBoxValue(providerSearchDto
									.isAddRatingCheckBox());
							addnSelectedInsuranceMap.put(i,
									addnInsuranceVO);
						}
					}
				}

			}

			if (null != selectedInsuranceMap) {
				insuranceRatingBean
						.setSelectedInsuranceMap(selectedInsuranceMap);

			}
			if(null!=addnSelectedInsuranceMap){
				insuranceRatingBean.setAddnSelectedInsuranceMap(addnSelectedInsuranceMap);				
				
			}
			ratingParamBeans.add(insuranceRatingBean);
			
		} catch (Exception e) {
			logger.info("Caught Exception and ignoring",e);
		}
		return ratingParamBeans;
	}

	public static List<SOWAltBuyerContactDTO> convertVOtoAltBuyerContacts(
			ContactLocationVO contactLocationVO) {
		logger.info("ENTERED ObjectMapperWizard.convertVOtoAltBuyerContacts()");
		List<SOWAltBuyerContactDTO> altBuyerContacts = new ArrayList<SOWAltBuyerContactDTO>();
		try {
			List<Contact> contactList = contactLocationVO.getListContact();
			SOWAltBuyerContactDTO altBuyerContact;
			StringBuffer sb;
			String displayName="";
			for (Contact contact : contactList) {
				altBuyerContact = new SOWAltBuyerContactDTO();
				sb=new StringBuffer();

				if (contact.getCreatedDate() != null) {
					altBuyerContact.setCreatedDate(contact.getCreatedDate());
				} else {
					altBuyerContact.setCreatedDate(DateUtils.getCurrentDate());
					contact.setCreatedDate(new Timestamp(DateUtils
							.getCurrentDate().getTime()));
				}
				altBuyerContact.setFirstName(contact.getFirstName());
				altBuyerContact.setLastName(contact.getLastName());
				altBuyerContact.setContactId(contact.getContactId());
				if (contact.getPhoneCreatedDate() != null) {
					altBuyerContact.setCreatedDate(contact
							.getPhoneCreatedDate());
				} else {
					altBuyerContact.setCreatedDate(DateUtils.getCurrentDate());
					contact.setPhoneCreatedDate(DateUtils.getCurrentDate());
				}
				altBuyerContact.setPhoneNo(contact.getPhoneNo());
				altBuyerContact.setCellNo(contact.getCellNo());
				altBuyerContact.setFaxNo(contact.getFaxNo());

				altBuyerContact.setEmail(contact.getEmail());
				altBuyerContact.setResourceId(contact.getResourceId());
				sb.append(contact.getFirstName());
				sb.append(" ");
				sb.append(contact.getLastName());
				sb.append("! #");
				sb.append(contact.getResourceId().toString());
				displayName=sb.toString();
				altBuyerContact.setDisplayName(displayName);	
				altBuyerContact.setAddress1(contact.getStreet_1());
				altBuyerContact.setAddress2(contact.getStreet_2());
				altBuyerContact.setZip(contact.getZip());
				altBuyerContact.setZip4(contact.getZip4());
				altBuyerContact.setCity(contact.getCity());
				altBuyerContact.setCountry(contact.getCountry());				
				altBuyerContacts.add(altBuyerContact);
			}
		} catch (Exception e) {
			logger.info("Caught Exception and ignoring",e);
		}
		return altBuyerContacts;

	}

	public static SOWContactLocationDTO convertVOtoSOWContactLocationDTO(
			ContactLocationVO contactLocationVO) {
		/*
		 * Converting from ContactLocationVO to SOWContactLocationDTO for Buyer
		 * Primary Contact location Information
		 */
		SOWContactLocationDTO altBuyerSupportLocationContact = new SOWContactLocationDTO();
		List<SOWPhoneDTO> phones = new ArrayList();
		SOWPhoneDTO phone1 = null; // 3 phone needed always
		SOWPhoneDTO phone2 = null;
		SOWPhoneDTO phone3 = null;
		try {
			if (contactLocationVO != null) {
				if (contactLocationVO.getBuyerPrimaryLocation() != null) {

					altBuyerSupportLocationContact.setAptNo(contactLocationVO
							.getBuyerPrimaryLocation().getAptNo());
					altBuyerSupportLocationContact.setCity(contactLocationVO
							.getBuyerPrimaryLocation().getCity());

					altBuyerSupportLocationContact.setState(contactLocationVO
							.getBuyerPrimaryLocation().getState());
					altBuyerSupportLocationContact
							.setStreetName1(contactLocationVO
									.getBuyerPrimaryLocation().getStreet1());
					altBuyerSupportLocationContact
							.setStreetName2(contactLocationVO
									.getBuyerPrimaryLocation().getStreet2());
					altBuyerSupportLocationContact.setZip(contactLocationVO
							.getBuyerPrimaryLocation().getZip());

				}

				if (contactLocationVO.getBuyerPrimaryContact() != null) {
					if (contactLocationVO.getBuyerPrimaryContact()
							.getCreatedDate() != null) {
						altBuyerSupportLocationContact
								.setCreatedDate(contactLocationVO
										.getBuyerPrimaryContact()
										.getCreatedDate());
					} else {
						altBuyerSupportLocationContact.setCreatedDate(DateUtils
								.getCurrentDate());
						contactLocationVO.getBuyerPrimaryContact()
								.setCreatedDate(
										new Timestamp(DateUtils
												.getCurrentDate().getTime()));
					}
					altBuyerSupportLocationContact
							.setBusinessName(contactLocationVO
									.getBuyerPrimaryContact().getBusinessName());
					altBuyerSupportLocationContact
							.setFirstName(contactLocationVO
									.getBuyerPrimaryContact().getFirstName());
					altBuyerSupportLocationContact
							.setLastName(contactLocationVO
									.getBuyerPrimaryContact().getLastName());
					altBuyerSupportLocationContact.
							setMiddleName(contactLocationVO
									.getBuyerPrimaryContact().getMi());
					altBuyerSupportLocationContact.setEmail(contactLocationVO
							.getBuyerPrimaryContact().getEmail());
					altBuyerSupportLocationContact
							.setContactId(contactLocationVO
									.getBuyerPrimaryContact().getContactId());
					altBuyerSupportLocationContact
							.setResourceId(contactLocationVO
								.getBuyerPrimaryContact().getResourceId());
					altBuyerSupportLocationContact.setRating(contactLocationVO
							.getBuyerPrimaryContact().getRating());

					// backend is not bringing phone list, it is only
					// bringing
					// string numbers so need to fix backend and need to change
					// contact.java
					// TEMP setting this entire phone to PhonePart1

					phone1 = new SOWPhoneDTO(contactLocationVO
							.getBuyerPrimaryContact().getPhoneNo(),
							contactLocationVO.getBuyerPrimaryContact()
									.getPhoneNoExt());

					if (contactLocationVO.getBuyerPrimaryContact()
							.getPhoneNoExt() != null) {
						// should not come as "" from VO or should not be
						// Integer
						try {
							phone1.setExt(contactLocationVO
									.getBuyerPrimaryContact().getPhoneNoExt());
						} catch (NumberFormatException e) {
							logger.info("Caught Exception and ignoring",e);
						}
					}
					phone1.setPhoneType(1); // primary
					phones.add(phone1);

					phone2 = new SOWPhoneDTO(contactLocationVO
							.getBuyerPrimaryContact().getCellNo(), null);

					phone2.setPhoneType(2); // alternative
					phones.add(phone2);

					phone3 = new SOWPhoneDTO(contactLocationVO
							.getBuyerPrimaryContact().getFaxNo(), null);

					phone3.setPhoneType(3); // fax
					phones.add(phone3);
					if (contactLocationVO.getBuyerPrimaryContact()
							.getPhoneCreatedDate() != null) {
						phone1
								.setCreatedDate(contactLocationVO
										.getBuyerPrimaryContact()
										.getPhoneCreatedDate());
						phone2
								.setCreatedDate(contactLocationVO
										.getBuyerPrimaryContact()
										.getPhoneCreatedDate());
						phone3
								.setCreatedDate(contactLocationVO
										.getBuyerPrimaryContact()
										.getPhoneCreatedDate());
					} else {
						Date date = DateUtils.getCurrentDate();
						phone1.setCreatedDate(date);
						phone2.setCreatedDate(date);
						phone3.setCreatedDate(date);
						contactLocationVO.getBuyerPrimaryContact()
								.setPhoneCreatedDate(date);
					}

				}
			}
		} catch (Exception e) {
			logger.info("Caught Exception and ignoring",e);
		}

		altBuyerSupportLocationContact.setPhones(phones);
		return altBuyerSupportLocationContact;
	}

	private static void assignReviewTabDtoToServiceOrderVO(ServiceOrder so,
			ServiceOrderDTO reviewDto) {

		try {

			if (reviewDto != null) {

				// so.setSoTermsCondId(Integer.parseInt(reviewDto.getAcceptTermsAndConditions()));
				so.setSoTermsCondId(reviewDto.getTermsAndConditionsId());
				Calendar cl = Calendar.getInstance();
				so.setBuyerTermsCondDate(new Timestamp(cl.getTimeInMillis()));
				so.setBuyerSOTermsCondInd(1);
			}

		} catch (NumberFormatException e) {
			logger.info("Caught Exception and ignoring",e);
		} catch (Exception e) {
			logger.info("Caught Exception and ignoring",e);
		}

	}
	
	private static SoLocation populateBSLocationforSB(SoLocation servLocation){

		SoLocation location = new SoLocation();
		
		location.setAptNo(servLocation.getAptNo());
		location.setLocnName(servLocation.getLocnName());
		location.setStreet1(servLocation.getStreet1());
		location.setStreet2(servLocation.getStreet2());
		location.setCity(servLocation.getCity());
		location.setZip(servLocation.getZip());
		location.setState(servLocation.getState());
		
		return location;
		
	}

	private static ArrayList<SOTaskDTO> populateTasks(List<ServiceOrder> serviceOrders) {
		
		ArrayList<SOTaskDTO> soTasks = new ArrayList<SOTaskDTO>();
		
		for (ServiceOrder so : serviceOrders) {
		
			if (so.getTasks() != null && so.getTasks().size() > 0) {
				
				Iterator<ServiceOrderTask> tasks = so.getTasks().iterator();
				
				while (tasks.hasNext()) {
					
					ServiceOrderTask soTask = (ServiceOrderTask)tasks.next();
					SOTaskDTO taskDto = new SOTaskDTO();
					taskDto.setIsSaved(true);
					
					if (soTask.getTaskId() != null) {
						taskDto.setEntityId(soTask.getTaskId());
					}
					
					taskDto.setTaskName(soTask.getTaskName());
					taskDto.setComments(soTask.getTaskComments());
					taskDto.setCategory(soTask.getCategoryName());
					taskDto.setSubCategory(soTask.getSubCategoryName());
					
					if (soTask.getCreateDate() != null) {
						taskDto.setCreatedDate(soTask.getCreateDate());
					} else {
						taskDto.setCreatedDate(DateUtils.getCurrentDate());
						soTask.setCreateDate(DateUtils.getCurrentDate());
					}
					
					if (soTask.getSkillNodeId() != null && soTask.getLevel() == 3) {
						taskDto.setSubCategoryId(soTask.getSkillNodeId());
						taskDto.setCategoryId(soTask.getParentId());
					} else if (soTask.getSkillNodeId() != null && soTask.getLevel() == 2) {
						taskDto.setCategoryId(soTask.getSkillNodeId());
					}
					
					taskDto.setSkill(soTask.getServiceType());
					taskDto.setSkillId(soTask.getServiceTypeId());
					soTasks.add(taskDto);
				}
			}
		}
		return soTasks;
	}
	
	private static SOWContactLocationDTO populateServiceLocationAndContact(List<ServiceOrder> serviceOrders) {

		SoLocation serviceLocation = new SoLocation();
		Contact serviceContact = new Contact();
		Contact altServiceContact = new Contact();
		Contact serviceContactFax = new Contact();
		SOWContactLocationDTO contactLocationDto = new SOWContactLocationDTO();
		
		for (ServiceOrder so : serviceOrders) {
		
			if (so.getServiceLocation() != null) {
	
				altServiceContact = so.getServiceContactAlt();
				serviceLocation = so.getServiceLocation();
				serviceContactFax = so.getServiceContactFax();
				
				if (serviceLocation.getCreatedDate() != null) {
					contactLocationDto.setCreatedDate(serviceLocation.getCreatedDate());
				} else {
					contactLocationDto.setCreatedDate(DateUtils.getCurrentDate());
					serviceLocation.setCreatedDate(new Timestamp(DateUtils.getCurrentDate().getTime()));
				}
				
				contactLocationDto.setAptNo(serviceLocation.getAptNo());
				contactLocationDto.setCity(serviceLocation.getCity());
				contactLocationDto.setState(serviceLocation.getState());
				contactLocationDto.setZip(serviceLocation.getZip());
				contactLocationDto.setZip4(serviceLocation.getZip4());
	
				if (serviceLocation.getLocnClassId() != null) {
					contactLocationDto.setLocationTypeId(serviceLocation.getLocnClassId().toString());
				}
				
				contactLocationDto.setStreetName1(serviceLocation.getStreet1());
				contactLocationDto.setStreetName2(serviceLocation.getStreet2());
			}
			
			if (so.getServiceContact() != null) {
				
				serviceContact = so.getServiceContact();
				
				if (serviceContact != null) {
					
					if (serviceContact.getCreatedDate() != null) {
						contactLocationDto.setCreatedDate(serviceContact.getCreatedDate());
					} else {
						contactLocationDto.setCreatedDate(DateUtils.getCurrentDate());
						serviceContact.setCreatedDate(new Timestamp(DateUtils.getCurrentDate().getTime()));
					}
					
					contactLocationDto.setBusinessName(serviceContact.getBusinessName());
					contactLocationDto.setFirstName(serviceContact.getFirstName());
					contactLocationDto.setLastName(serviceContact.getLastName());
					contactLocationDto.setEmail(serviceContact.getEmail());
					contactLocationDto.setFax(serviceContact.getFaxNo());
					
				}
	
				contactLocationDto.setServiceLocationNote(serviceLocation.getLocnNotes());
	
				List<SOWPhoneDTO> phonesDtos = new ArrayList<SOWPhoneDTO>();
				
				if (serviceContact != null && serviceContact.getPhoneNo() != null) {
	
					String priPhone = serviceContact.getPhoneNo();
					SOWPhoneDTO phonesDto = new SOWPhoneDTO();
					
					if (serviceContact.getPhoneCreatedDate() != null) {
						phonesDto.setCreatedDate(serviceContact.getPhoneCreatedDate());
					} else {
						phonesDto.setCreatedDate(DateUtils.getCurrentDate());
						serviceContact.setPhoneCreatedDate(DateUtils.getCurrentDate());
					}
					
					if (priPhone != null && priPhone.length() == 10) {	
						phonesDto.setAreaCode(priPhone.substring(0, 3));
						phonesDto.setPhonePart1(priPhone.substring(3, 6));
						phonesDto.setPhonePart2(priPhone.substring(6, 10));
					}
					
					if (serviceContact.getPhoneClassId() != null) {
						phonesDto.setPhoneClassId(new Integer(serviceContact.getPhoneClassId()));
					}
					
					if (serviceContact.getPhoneNoExt() != null) {
						phonesDto.setExt(serviceContact.getPhoneNoExt());
					}
					
					phonesDto.setPhoneType(1);
					phonesDtos.add(phonesDto);
	
				} else if (serviceContact != null && serviceContact.getPhoneNo() == null) {
					SOWPhoneDTO phonesDto = new SOWPhoneDTO();
					phonesDto.setPhoneType(1);
					phonesDtos.add(phonesDto);
				}
	
				if (altServiceContact != null && altServiceContact.getPhoneNo() != null) {
	
					String altPhone = altServiceContact.getPhoneNo();
					SOWPhoneDTO altPhonesDto = new SOWPhoneDTO();
	
					if (altServiceContact.getPhoneCreatedDate() != null) {
						altPhonesDto.setCreatedDate(altServiceContact.getPhoneCreatedDate());
					} else {
						altPhonesDto.setCreatedDate(DateUtils.getCurrentDate());
						altServiceContact.setPhoneCreatedDate(DateUtils.getCurrentDate());
					}
					
					if (altPhone != null && altPhone.length() == 10) {
						altPhonesDto.setAreaCode(altPhone.substring(0, 3));
						altPhonesDto.setPhonePart1(altPhone.substring(3, 6));
						altPhonesDto.setPhonePart2(altPhone.substring(6, 10));
					}
					
					if (altServiceContact.getPhoneClassId() != null) {
						altPhonesDto.setPhoneClassId(new Integer(altServiceContact.getPhoneClassId()));
					}
					
					if (altServiceContact.getPhoneNoExt() != null) {
						altPhonesDto.setExt(altServiceContact.getPhoneNoExt());
					}
					
					altPhonesDto.setPhoneType(2);
					phonesDtos.add(altPhonesDto);
	
				} else if (altServiceContact != null && altServiceContact.getPhoneNo() == null) {
					SOWPhoneDTO altPhonesDto = new SOWPhoneDTO();
					altPhonesDto.setPhoneType(2);
					phonesDtos.add(altPhonesDto);
				}
	
				if (serviceContactFax != null && serviceContactFax.getPhoneNo() != null) {
	
					String Faxphone = serviceContactFax.getPhoneNo();
					SOWPhoneDTO FaxphonesDto = new SOWPhoneDTO();
	
					if (Faxphone != null && Faxphone.length() == 10) {
						FaxphonesDto.setAreaCode(Faxphone.substring(0, 3));
						FaxphonesDto.setPhonePart1(Faxphone.substring(3, 6));
						FaxphonesDto.setPhonePart2(Faxphone.substring(6, 10));
					}
					
					if (serviceContactFax.getPhoneClassId() != null) {
						FaxphonesDto.setPhoneClassId(new Integer(serviceContactFax.getPhoneClassId()));
					}
	
					FaxphonesDto.setPhoneType(3);
					phonesDtos.add(FaxphonesDto);
	
				} else if (serviceContactFax != null && serviceContactFax.getPhoneNo() == null) {
					SOWPhoneDTO FaxphonesDto = new SOWPhoneDTO();
					FaxphonesDto.setPhoneType(3);
					phonesDtos.add(FaxphonesDto);
				}
	
				contactLocationDto.setPhones(phonesDtos);
	
			}
		}
		
		return contactLocationDto;
		
	}
	

}

/*
 * Maintenance History: See bottom of file
 * $Log: ObjectMapperWizard.java,v $
 * Revision 1.148  2008/06/06 11:41:05  cnair
 * Fixed as per Sears00053930: OMS- Template Maint- Alternate Buyer Contact
 *
 * Revision 1.147  2008/05/31 03:30:34  mkhair
 * Modified assignServiceOrderVOToDescribeDto to format the spending limits.
 *
 * Revision 1.146  2008/05/30 19:39:04  mkhair
 * Modified assignServiceOrderVOToDescribeDto to set the parts limit to 0 when buyer decides to provide the material.
 *
 * Revision 1.145  2008/05/30 19:16:44  mhaye05
 * added edit check around save button
 *
 * Revision 1.144  2008/05/30 17:51:17  mkhair
 * Modified assignServiceOrderVOToDescribeDto to set the order status.
 *
 * Revision 1.143  2008/05/29 17:52:18  hoza
 * edit flow changes for find providers
 *
 * Revision 1.142  2008/05/28 22:09:31  mkhair
 * Modified convertSSODTOstoSO() to set the flag for provider confirmation.
 *
 * Revision 1.141  2008/05/27 22:19:42  gjacks8
 * added service schedule types to mapper
 *
 * Revision 1.140  2008/05/27 17:48:37  gjacks8
 * fixed parts mapping
 *
 * Revision 1.139  2008/05/27 16:03:15  gjacks8
 * added first logged in flag for simple - fixes flow location problem
 *
 * Revision 1.138  2008/05/26 02:27:56  kvuppal
 * added a fix for Simple Buyer- SO contact save
 *
 * Revision 1.137  2008/05/23 22:45:17  schavda
 * assignFindProvidersDtoToServiceOrderVO -- Primary Skill Id
 *
 * Revision 1.136  2008/05/23 20:54:32  rgurra0
 * fixed providers persistence
 *
 * Revision 1.135  2008/05/23 19:34:51  schavda
 * assignServiceOrderVOToFindProvidersDTO -- skills
 *
 * Revision 1.134  2008/05/23 16:56:14  rgurra0
 * fixed Total Amt mapping into DNS DTO
 *
 * Revision 1.133  2008/05/23 05:36:53  mkhair
 * Modified assignServiceOrderVOToDescribeDto to set the location name.
 *
 * Revision 1.132  2008/05/23 02:38:23  mkhair
 * Modified assignServiceOrderVOToDescribeDto for posting fee.
 *
 * Revision 1.131  2008/05/22 21:20:01  mkhair
 * Modified assignServiceOrderVOToDescribeDto.
 *
 * Revision 1.130  2008/05/22 20:43:31  mkhair
 * Merged with I21 last edition.
 *
 * Revision 1.129  2008/05/21 23:32:52  akashya
 * I21 Merged
 *
 * Revision 1.128.6.11  2008/05/20 20:03:17  mkhair
 * Modified assignDescribeDtoToServiceOrderVO() to set starttime and endtime for range of service date selection.
 *
 * Revision 1.128.6.10  2008/05/20 18:19:06  nsanzer
 * Incremental check in of Manage and Select Locations functionality.
 *
 * Revision 1.128.6.9  2008/05/20 15:58:53  mkhair
 * Modified assignDescribeDtoToServiceOrderVO and assignServiceOrderVOToDescribeDto to set zip.
 *
 * Revision 1.128.6.8  2008/05/19 21:49:31  bgangaj
 * Changes  to simple buyer  payment module
 *
 * Revision 1.128.6.7  2008/05/19 15:55:04  mhaye05
 * state code and description fixes
 *
 * Revision 1.128.6.6  2008/05/19 15:30:35  mkhair
 * Modified assignDescribeDtoToServiceOrderVO method to change the date format of servicedate.
 *
 * Revision 1.128.6.5  2008/05/17 21:18:56  glacy
 * This is the Stuff for the struts level
 *
 * Revision 1.128.6.4  2008/05/17 19:44:15  glacy
 * For Himanshu
 *
 * Revision 1.128.6.3  2008/05/16 05:25:44  mkhair
 * Added few methods to map SimpleServiceOrder DTOs to VO and VO to DTOs.
 *
 * Revision 1.128.6.2  2008/05/15 14:49:12  mkhair
 * Added convertSSODTOstoSO, convertSOtoSSODTOs and assign methods required for these two methods.
 *
 * Revision 1.128.6.1  2008/05/07 16:22:35  sgopin2
 * ENH 29281: SOW-Add 'Billing Estimate' on Pricing
 *
 * Revision 1.128  2008/04/25 21:02:58  glacy
 * Shyam: Merged SL_DE_2008_04_30 branch to HEAD.
 *
 * Revision 1.127  2008/04/23 07:04:41  glacy
 * Shyam: Re-merge of I19_FreeTab branch to HEAD.
 *
 * Revision 1.126  2008/04/23 05:19:55  hravi
 * Shyam: Reverting to build 247.
 *
 * Revision 1.124.12.1  2008/04/22 19:47:47  sgopin2
 * Sears00051136
 *
 * Revision 1.124  2008/03/27 18:58:04  mhaye05
 * Merged I18_ADM to Head
 *
 * Revision 1.123.8.1  2008/03/25 20:08:50  mhaye05
 * code cleanup
 * 
 */
