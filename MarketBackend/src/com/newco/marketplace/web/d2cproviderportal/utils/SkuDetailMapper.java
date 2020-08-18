package com.newco.marketplace.web.d2cproviderportal.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.newco.marketplace.dto.vo.BuyerSkuVO;
import com.newco.marketplace.dto.vo.VendorServiceOfferPriceVo;
import com.newco.marketplace.dto.vo.VendorServiceOfferingVO;
import com.servicelive.domain.d2cproviderportal.BuyerSku;
import com.servicelive.domain.d2cproviderportal.VendorServiceOfferPrice;
import com.servicelive.domain.sku.maintenance.BuyerSkuCategory;
import com.servicelive.domain.sku.maintenance.BuyerSkuTask;

public class SkuDetailMapper {
	
	/**
	 * 	creating and returning buyerSkuVo for
	 *  a service offering and creating blank if it does not exist.
	 * 
	 * @param vendorSvcOfferings
	 * @param buyerSku
	 * @return
	 */
	public BuyerSkuVO convertDTOtoVO(List<VendorServiceOfferPrice> vendorSvcOfferings, BuyerSku buyerSku) {

		List<VendorServiceOfferingVO> vendorServiceOfferingVOlist = new ArrayList<VendorServiceOfferingVO>();

		BuyerSkuVO buyerSkuVO = new BuyerSkuVO();
		VendorServiceOfferingVO serviceOfferingVO = new VendorServiceOfferingVO();

		List<VendorServiceOfferPriceVo> vendorServiceOfferPriceVoList = new ArrayList<VendorServiceOfferPriceVo>();
		if (null != vendorSvcOfferings && vendorSvcOfferings.size() > 0) {
			serviceOfferingVO.setId(vendorSvcOfferings.get(0).getId());
			// price while
			Iterator<VendorServiceOfferPrice> serviceOfferPriceiterator = vendorSvcOfferings.iterator();
			while (serviceOfferPriceiterator.hasNext()) {
				VendorServiceOfferPriceVo serviceOfferPriceVo = new VendorServiceOfferPriceVo();
				VendorServiceOfferPrice serviceOfferPrice = serviceOfferPriceiterator.next();
				serviceOfferPriceVo.setDailyLimit(serviceOfferPrice.getDailyLimit());
				serviceOfferPriceVo.setPrice(serviceOfferPrice.getPrice());
				serviceOfferPriceVo.setId(serviceOfferPrice.getId());

				vendorServiceOfferPriceVoList.add(serviceOfferPriceVo);
			}
		}

		serviceOfferingVO.setVendorServiceOfferPriceVo(vendorServiceOfferPriceVoList);
		vendorServiceOfferingVOlist.add(serviceOfferingVO);
		buyerSkuVO.setSkuId((int) (long) (buyerSku.getSkuId()));
		buyerSkuVO.setSku(buyerSku.getSku());
		buyerSkuVO.setPrimaryIndustryId(buyerSku.getBuyerSoTemplate().getLookupPrimaryIndustry().getId());
		buyerSkuVO.setPrimaryIndustryDesc(buyerSku.getBuyerSoTemplate().getLookupPrimaryIndustry().getDescription());

		List<BuyerSkuTask> buyerSkuTasksList = new ArrayList<BuyerSkuTask>();

		Iterator<BuyerSkuTask> buyerSkuTaskiterator = buyerSku.getBuyerSkuTasks().iterator();
		while (buyerSkuTaskiterator.hasNext()) {
			BuyerSkuTask buyerSkuTask = new BuyerSkuTask();
			BuyerSkuTask buyerSkuT = buyerSkuTaskiterator.next();
			buyerSkuTask.setTaskComments(buyerSkuT.getTaskComments());
			buyerSkuTask.setLuServiceTypeTemplate(buyerSkuT.getLuServiceTypeTemplate());
			buyerSkuTask.setTaskName(buyerSkuT.getTaskName());
			buyerSkuTasksList.add(buyerSkuTask);
		}

		BuyerSkuCategory buyerSkuCategory = new BuyerSkuCategory();
		buyerSkuCategory.setCategoryName(buyerSku.getBuyerSkuCategory().getCategoryName());

		buyerSkuVO.setVendorServiceOfferVo(vendorServiceOfferingVOlist);
		buyerSkuVO.setBuyerSkuCategory(buyerSkuCategory);
		buyerSkuVO.setBuyerSkuTasks(buyerSkuTasksList);

		return buyerSkuVO;
	}

}
