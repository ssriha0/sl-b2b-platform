package com.newco.marketplace.persistence.daoImpl.so.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderAddonVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderStagingVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.so.order.IServiceOrderAddonDAO;
import com.sears.os.dao.impl.ABaseImplDao;

public class ServiceOrderAddonDAOImpl extends ABaseImplDao implements
		IServiceOrderAddonDAO {
	
	private static final Logger logger = Logger.getLogger(ServiceOrderAddonDAOImpl.class);

	public void createServiceOrderAddons(List<ServiceOrderAddonVO> addons)
			throws DataServiceException {
		String soId = "";
		ArrayList<ServiceOrderAddonVO> addonsForInsert = new ArrayList();
		try {
			for (ServiceOrderAddonVO soAddon : addons) {
				if (StringUtils.isBlank(soId))
					soId = soAddon.getSoId();
				if(soAddon.getRetailPrice() > 0.01  || (soAddon.getRetailPrice() <= 0 && soAddon.isMiscInd())) {
					addonsForInsert.add(soAddon);
					if (logger.isDebugEnabled()) {
						logger.debug("serviceOrderAddon.insert:: so_id: " + soId + ", retailPrice: " + soAddon.getRetailPrice());
					}
				}
			}
			batchInsert("addon.insert", addonsForInsert);
		} catch (Exception ex) {
			String message = "Exception occured while inserting addon records to so: "
					+ soId;
			throw new DataServiceException(message + "due to" + ex.getMessage());
		}

	}

	public List<ServiceOrderAddonVO> getUpsellALLSkusForSO(String soId)
			throws DataServiceException {
		List<ServiceOrderAddonVO> upsellSkuList = new ArrayList<ServiceOrderAddonVO>();
		try {
			// code change for SLT-2112
			Map<String, String> parameter = new HashMap<String, String>();
					parameter.put("soId", soId);
			upsellSkuList = (List<ServiceOrderAddonVO>) queryForList(
					"soUpsell.soUpsellALLSku.query", parameter);

		} catch (Exception e) {
			String message = "Exception occured while getting list of upsold Skus for SO";
			throw new DataServiceException(message + " due to "
					+ e.getMessage());
		}

		return upsellSkuList;
	}
	
	/* 
	 * select retail_price,margin,qty,coverage='CC' sortorder from so_addon where so_id = #soId# and qty > 0 order by sortorder desc
	 * 
	 */
	public List<ServiceOrderAddonVO> getUpsellSkuswithQtyForSO(String soId)
	throws DataServiceException {
		List<ServiceOrderAddonVO> upsellSkuList = new ArrayList<ServiceOrderAddonVO>();
		try {
			upsellSkuList = (List<ServiceOrderAddonVO>) queryForList(
					"soUpsell.soUpsellALLSkuWithQty.query", soId);
		
		} catch (Exception e) {
			String message = "Exception occured while getting list of upsold Skus for SO";
			throw new DataServiceException(message + " due to "
					+ e.getMessage());
		}

		return upsellSkuList;
	}
	
	
	
	


	public void updateAddonQtys(List<ServiceOrderAddonVO> addons)
			throws DataServiceException {
		String soId = "";

		try {
			for (ServiceOrderAddonVO soAddon : addons) {
				if (StringUtils.isBlank(soId))
					soId = soAddon.getSoId();
				
				// Misc addons, need a description entered by the provider/user
				if(soAddon.isMiscInd())
				{
					update("addon.update.qtyanddescription", soAddon);						
				}
				else
				{
					update("addon.update.qty", soAddon);					
				}
			}
		} catch (Exception ex) {
			String message = "Exception occured while updating qty's for SO :"
					+ soId;
			throw new DataServiceException(message + "due to "
					+ ex.getMessage());
		}
	}

	public void deleteUnpurchasedAddonsbySO(String soId)
			throws DataServiceException {
		try {
			delete("addons.delete", soId);
		} catch (Exception ex) {
			String message = "Exception occured while deleting unpurchased addons for SO :"
					+ soId;
			throw new DataServiceException(message + "due to "
					+ ex.getMessage());
		}
	}
	
	public ServiceOrderStagingVO loadUpsellInfo(String soId)throws DataServiceException {
		ServiceOrderStagingVO stagingDetail = null;
		try{
			stagingDetail = (ServiceOrderStagingVO)queryForObject("serviceOrder.upsell.info", soId);
			
		}catch(Exception e){
			String message = "Exception occured while getting Upsell Info for given soId";
			throw new DataServiceException(message + " due to "
					+ e.getMessage());
		}
		
		return stagingDetail;
	}

	public Double getTotalServiceFeeForAddOnSkus(String soId)
			throws DataServiceException {
		Double totalServiceFee = null;
		try{
			totalServiceFee = (Double)queryForObject("serviceOrder.fee.addonskus", soId);
			
		}catch(Exception e){
			String message = "Exception occured while getting total service fee for addon skus for given soId " + soId;
			throw new DataServiceException(message + " due to "
					+ e.getMessage());
		}
		
		return totalServiceFee;
	}
	
		public boolean isAddonSaved(ServiceOrderAddonVO addon){
			ServiceOrderAddonVO savedOrder = null;
			boolean orderFound = false;
		try{
			savedOrder = (ServiceOrderAddonVO)queryForObject("serviceOrder.upsell.info.bySkuandSOID", addon);
			if(savedOrder != null)
				orderFound = true;
			
		}catch(Exception e){
			String message = "Exception occured while getting Upsell Info for given soId";
		}
		
		return orderFound;
	}
		
	public Map<String, Boolean>  isAddonsSaved(ServiceOrderAddonVO addon) {
			ServiceOrderAddonVO savedOrder = null;
			boolean orderFound = false;
			Map<String, Boolean> map = new HashMap<String, Boolean>();
			//Initializing map
			for (String str:addon.getSkuList()) {
				map.put(str, false);
			}
			
		try{
			List<String> list = (List)queryForList("serviceOrder.upsell.info.list.bySkuandSOID", addon);
			// setting actual values.
			for (String str: list) {				
				map.put(str, true);
			}
			//if(savedOrder != null)
			//	orderFound = true;
			
		}catch(Exception e){
			String message = "Exception occured while getting Upsell Info for given soId";
			e.printStackTrace();
		}
		
		return map;
	}

}
