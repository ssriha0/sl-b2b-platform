/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 24-Oct-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.utils.mappers.so.v1_1;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.buyerskus.Service;
import com.newco.marketplace.api.beans.buyerskus.ServiceCategories;
import com.newco.marketplace.api.beans.buyerskus.ServiceCategory;
import com.newco.marketplace.api.beans.buyerskus.ServiceCategoryResponse;
import com.newco.marketplace.api.beans.buyerskus.Services;
import com.newco.marketplace.api.beans.buyerskus.Tasks;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.dto.vo.SkuDetailsVO;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.utils.ServiceLiveStringUtils;
/**
 * This class would act as a Mapper class for mapping service category result to
 * ServiceCategoryResponse object
 * 
 * @author Infosys
 * @version 1.0
 */
public class BuyerSkuMapper {
	private Logger LOGGER = Logger.getLogger(BuyerSkuMapper.class);

	/**@Description :This method will set error reponse with internal server error
	 * @return
	 */
	public ServiceCategoryResponse createErrorResponse() {
		ServiceCategoryResponse response = new ServiceCategoryResponse();
		Results results = Results.getError(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(), ResultsCode.INTERNAL_SERVER_ERROR.getCode());
		response.setResults(results);
		return response;
	}
	/**@Description :This method will set error reponse with No sku availeble  error
	 * @return
	 */
	public ServiceCategoryResponse createErrorResponse(String message,String code) {
		ServiceCategoryResponse response = new ServiceCategoryResponse();
		Results results = Results.getError(message,code);
		response.setResults(results);
		return response;
	}
	
	/**@Description :This method will set success Response
	 * @return
	 */
	public ServiceCategoryResponse createSuccessResponse() {
		ServiceCategoryResponse response = new ServiceCategoryResponse();
		Results results = Results.getSuccess();
		response.setResults(results);
		return response;
	}
	/**@Description : Map Vo to response object
	 * @param skuDetailsVOMap
	 * @param response
	 * @return
	 */
	public ServiceCategoryResponse mapBuyerSkus(Map<String, List<SkuDetailsVO>> skuDetailsVOMap, ServiceCategoryResponse response) {
		Results results = Results.getSuccess();
		response = new ServiceCategoryResponse();
		response.setResults(results);
		ServiceCategories serviceCategories = new ServiceCategories();
		List<ServiceCategory> servicecategoryList = new ArrayList<ServiceCategory>();
		mapBuyerSkuCategoryList(servicecategoryList,skuDetailsVOMap);
		serviceCategories.setServiceCategory(servicecategoryList);
		response.setServiceCategories(serviceCategories);
		return response;
	}
	/**
	 * @param skuCategoryList
	 * @param skuDetailsVOMap
	 */
	private void mapBuyerSkuCategoryList(List<ServiceCategory> skuCategoryList,Map<String, List<SkuDetailsVO>> skuDetailsVOMap) {
		if(null!= skuDetailsVOMap && !skuDetailsVOMap.isEmpty()){
			for(String skuCategory : skuDetailsVOMap.keySet()){
				mapBuyerSkusCategory(skuCategory,skuDetailsVOMap.get(skuCategory),skuCategoryList);
			}
		}
		
	}
	/**
	 * @param skuCategory
	 * @param list
	 * @param skuCategoryList
	 */
	private void mapBuyerSkusCategory(String skuCategory,List<SkuDetailsVO> list, List<ServiceCategory> skuCategoryList) {
		ServiceCategory serviceCategory = new ServiceCategory();
		Services services = new Services();
		List<Service> serviceList = new ArrayList<Service>();
		serviceCategory.setCategoryName(skuCategory);
		for(SkuDetailsVO skuTaskVO : list){
			Service service = new Service();
			Tasks tasks = new Tasks();
			service.setSku(skuTaskVO.getSku());
			service.setBillingMargin(applyPercentage(skuTaskVO.getBillingMargin()));
			service.setBillingPrice(formatMoney(skuTaskVO.getBillingPrice()));
			service.setDescription(skuTaskVO.getDescription());
			service.setRetailMargin(applyPercentage(skuTaskVO.getMargin()));
			service.setMaximumPrice(formatMoney(skuTaskVO.getMaximumPrice()));
			service.setOrderType(skuTaskVO.getOrderType());
			service.setRetailPrice(formatMoney(skuTaskVO.getRetailPrice()));
			service.setTemplate(skuTaskVO.getTemplate());
			mapSkuTask(skuTaskVO,tasks);
			service.setMainServiceCategory(skuTaskVO.getMainServiceCategory());
			service.setMainServiceCategoryId(skuTaskVO.getMainServiceCategoryId());
			service.setTasks(tasks);
			serviceList.add(service);
		}
		services.setService(serviceList);
		serviceCategory.setServices(services);
		skuCategoryList.add(serviceCategory);
	}
	/**
	 * @param skuTaskVO
	 * @param taskDetail
	 * @return 
	 */
	private void mapSkuTask(SkuDetailsVO skuTaskVO, Tasks tasks) {
		tasks.setTaskName(skuTaskVO.getTaskName());
		tasks.setComments(ServiceLiveStringUtils.removeHTML(skuTaskVO.getTaskComments()));
		tasks.setCategory(skuTaskVO.getCategory());
		tasks.setSubCategory(skuTaskVO.getSubCategory());
		tasks.setCategoryId(skuTaskVO.getCategoryId());
		tasks.setSubCategoryId(skuTaskVO.getSubCategoryId());
		tasks.setSkill(skuTaskVO.getSkill());
		
	}
	
	/**@Description : Convert value to percenatge
	 * @param value
	 * @return
	 */
	private String applyPercentage(Double value){
		String formattedValue ="0.00";
		if(null!= value){
			value = value*100;
			formattedValue =formatMoney(value);
		}
		return formattedValue;
	}
	
    /**@Description : Convert Double to #.##
     * @param value
     * @return
     */
    private String formatMoney(Double value) {
    	String formattedValue = "0.00";
		DecimalFormat df = new DecimalFormat(MPConstants.ROUND_OFF_TWO_DECIMAL_WORKING);
		if(null!= value){
			formattedValue = df.format(value);
		}
		return formattedValue;
	}
}
