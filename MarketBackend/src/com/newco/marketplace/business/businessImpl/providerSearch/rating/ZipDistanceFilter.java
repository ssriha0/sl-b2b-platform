package com.newco.marketplace.business.businessImpl.providerSearch.rating;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.dto.vo.LocationVO;
import com.newco.marketplace.dto.vo.provider.ProviderLocationVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.dto.vo.providerSearch.RatingParameterBean;
import com.newco.marketplace.exception.gis.InsuffcientLocationException;
import com.newco.marketplace.persistence.iDao.providerSearch.ProviderSearchDao;
import com.newco.marketplace.util.gis.GISUtil;

/**
 * $Revision: 1.17 $ $Author: glacy $ $Date: 2008/05/02 21:23:38 $
 */
public class ZipDistanceFilter extends RatingCalculator{
	private static final Logger logger = Logger.getLogger(ZipDistanceFilter.class.getName());
	private ProviderSearchDao providerSearchDao;
	/**
	 * 
	 */
	public ZipDistanceFilter(){
		super(null);
	}
	
	public ZipDistanceFilter(RatingCalculator calc){
		super(calc);
	}
	
	@Override
	public void getFilteredList(RatingParameterBean bean,
            ArrayList<ProviderResultVO> providerList) {
        
		if (bean instanceof ZipParameterBean) {
            ZipParameterBean zipParameterBean = (ZipParameterBean) bean;
            ProviderLocationVO buyerLocationVO = new ProviderLocationVO();
            buyerLocationVO.setStreet(zipParameterBean.getStreet());
            buyerLocationVO.setCity(zipParameterBean.getCity());
            buyerLocationVO.setZip(zipParameterBean.getZipcode());
            buyerLocationVO.setState(zipParameterBean.getLocState());
            HashMap<String, String> buyerCoordinates = null; 
            if((buyerCoordinates == null
					|| StringUtils.isEmpty(buyerCoordinates.get("lat"))
					|| StringUtils.isEmpty(buyerCoordinates.get("long"))
					|| buyerCoordinates.get("lat") == null
					|| buyerCoordinates.get("long") == null)
					&& buyerLocationVO != null
					&& buyerLocationVO.getZip() != null 
					&& StringUtils.isNotEmpty(buyerLocationVO.getZip())){
				LocationVO latLongLocationVO = providerSearchDao.getZipLatAndLong(buyerLocationVO.getZip());
				
				if(latLongLocationVO != null
						&& latLongLocationVO.getLatitude() != null
						&& latLongLocationVO.getLongitude() != null){
					buyerCoordinates = new HashMap<String, String>();
					buyerCoordinates.put("lat", latLongLocationVO.getLatitude());
					buyerCoordinates.put("long", latLongLocationVO.getLongitude());
				}
			}
            
            Integer radius = zipParameterBean.getRadius();
            
            int providerListSize = providerList.size();
			int counter = 0;
			
            for (int i = 0; i < providerListSize; i++) {
                ProviderResultVO providerListVo = providerList.get(counter);
            	HashMap<String, String> providerCoordinates = new HashMap<String, String>();
                providerCoordinates.put ("long", String.valueOf(providerListVo.getProviderLongitude()));
                providerCoordinates.put ("lat", String.valueOf(providerListVo.getProviderLatitude()));
                try {
                	providerListVo.setDistanceFromBuyer(GISUtil.getDistanceInMiles(buyerCoordinates, providerCoordinates));
                } catch (InsuffcientLocationException e) {
                	logger.info("Caught Exception and ignoring",e);
                }
                if (providerListVo.getDistanceFromBuyer() > radius.doubleValue()) {
                    providerList.remove(providerListVo);
                    counter--;
                }
                counter++;
            }
        } else {
            if (getChain() != null) {
                getChain().getFilteredList(bean, providerList);
            }
        }
    }//getFilteredResults

	public ProviderSearchDao getProviderSearchDao() {
		return providerSearchDao;
	}

	public void setProviderSearchDao(ProviderSearchDao providerSearchDao) {
		this.providerSearchDao = providerSearchDao;
	}

}
