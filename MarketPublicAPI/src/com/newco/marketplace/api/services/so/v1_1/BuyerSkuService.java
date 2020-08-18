
package com.newco.marketplace.api.services.so.v1_1;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.buyerskus.ServiceCategoryResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.so.v1_1.BuyerSkuMapper;
import com.newco.marketplace.business.iBusiness.serviceorder.IBuyerSkuBO;
import com.newco.marketplace.dto.vo.SkuDetailsVO;

/**
 * This class would act as a Servicer class for retrieving buyer sku details for the buyer.
 * 
 * @author Infosys
 * @version 1.0
 */
@APIResponseClass(ServiceCategoryResponse.class)
public class BuyerSkuService extends BaseService {
    private static final Logger LOGGER = Logger.getLogger(BuyerSkuService.class);
    private IBuyerSkuBO buyerSkuBO;
    private BuyerSkuMapper buyerSkuMapper;
	
    public BuyerSkuService() {
		super(null,
				PublicAPIConstant.BUYER_SKU_RESPONSE_XSD,
				PublicAPIConstant.BUYER_SKU_RESPONSE_NAMESPACE,
				PublicAPIConstant.SO_RESOURCES_SCHEMAS_V1_1,
				PublicAPIConstant.BUYER_SKU_RESPONSE_SCHEMALOCATION,
				null, ServiceCategoryResponse.class);
	}
    
	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		ServiceCategoryResponse response =null;
		Map<String,List<SkuDetailsVO>> skuDetailsVOMap = null;
		boolean isSkuAvailable = false;
		try{
			if(null!= apiVO.getBuyerIdInteger()){
			    isSkuAvailable = buyerSkuBO.checkSkuCatAvailable(apiVO.getBuyerIdInteger());
			    if(isSkuAvailable){
			    	skuDetailsVOMap = buyerSkuBO.getAvailableBuyerSkus(apiVO.getBuyerIdInteger());
			    	if(null!= skuDetailsVOMap && !skuDetailsVOMap.isEmpty()){
			    		response = buyerSkuMapper.mapBuyerSkus(skuDetailsVOMap,response);
			    	}else{
			    		response = buyerSkuMapper.createErrorResponse(ResultsCode.NO_SKU_AVAILABLE.getMessage(),ResultsCode.NO_SKU_AVAILABLE.getCode());
			    	}
			    }else{
			    	response = buyerSkuMapper.createErrorResponse(ResultsCode.NO_SKU_AVAILABLE.getMessage(),ResultsCode.NO_SKU_AVAILABLE.getCode());
			    }
			}else{
				response = buyerSkuMapper.createErrorResponse();
			}
		}catch (Exception e) {
			LOGGER.error("Exception in retrieving buyer sku details for the buyer"+ e);
			response = buyerSkuMapper.createErrorResponse();
		}
		return response;
	}

	public IBuyerSkuBO getBuyerSkuBO() {
		return buyerSkuBO;
	}

	public void setBuyerSkuBO(IBuyerSkuBO buyerSkuBO) {
		this.buyerSkuBO = buyerSkuBO;
	}

	public BuyerSkuMapper getBuyerSkuMapper() {
		return buyerSkuMapper;
	}

	public void setBuyerSkuMapper(BuyerSkuMapper buyerSkuMapper) {
		this.buyerSkuMapper = buyerSkuMapper;
	}

}
