package com.newco.marketplace.business.businessImpl.location;

import java.util.HashMap;
import java.util.List;

import com.newco.marketplace.business.iBusiness.location.ILocationBO;
import com.newco.marketplace.dto.vo.provider.ProviderLocationVO;
import com.newco.marketplace.dto.vo.provider.VendorResource;
import com.newco.marketplace.persistence.iDao.location.LocationDao;
import com.newco.marketplace.persistence.iDao.vendor.VendorResourceDao;
import com.newco.marketplace.util.gis.GISUtil;
import com.sears.os.business.ABaseBO;

public class LocationBOImpl extends ABaseBO implements ILocationBO {
	
	private LocationDao locationDAO;
	private VendorResourceDao vendorResourceDao;
	
	
	public int updateLocationPoint() {
		
		ProviderLocationVO locVO = null;
		String lat = new String();
		String lon = new String();
		
		VendorResource vr = new VendorResource();
		try{
			List lst = getVendorResourceDao().queryList(vr);
			for(int i=0; i<lst.size();i++)
			{
				vr = (VendorResource)lst.get(i);
				locVO = new ProviderLocationVO();
				locVO.setLocationId(vr.getLocnId());
				if(vr.getLocnId()!= null)
				{
				ProviderLocationVO locationVO = getLocationDAO().retrieveLocation(vr);
				String street = null;
				if(locationVO.getStreet2() != null)
					street = locationVO.getStreet1()+" "+locationVO.getStreet2();
				locationVO.setStreet(street);
				
				try
				{
			    beginWork();
				HashMap map = GISUtil.getLatLongfromAddress(locationVO);
				
				lon = (String)map.get("long");
				lat = (String)map.get("lat");
				logger.debug("Latitude:"+ lat);
				logger.debug("Longitude:"+ lon);
				
				locVO.setGis_latitude(lat);
				locVO.setGis_longitude(lon);
				
				getLocationDAO().updateLocation(locVO);
				commitWork();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			  }
			}
		}catch (Exception e) {
			logger.error("Error Occured:" + e.getMessage(), e);
			e.printStackTrace();
		}
		return 0;
	}	
		
	public LocationDao getLocationDAO() {
		return locationDAO;
	}
	public void setLocationDAO(LocationDao locationDAO) {
		this.locationDAO = locationDAO;
	}

	public VendorResourceDao getVendorResourceDao() {
		return vendorResourceDao;
	}

	public void setVendorResourceDao(VendorResourceDao vendorResourceDao) {
		this.vendorResourceDao = vendorResourceDao;
	}

}
