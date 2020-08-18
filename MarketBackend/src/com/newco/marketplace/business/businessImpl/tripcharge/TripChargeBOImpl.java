package com.newco.marketplace.business.businessImpl.tripcharge;

import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.dto.vo.TripChargeVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.admin.IAdminTripChargeDAO;

/**
 
/*
 * Maintenance History: See bottom of file.
 */
public class TripChargeBOImpl implements ITripChargeBO {

	private static final Logger logger = Logger.getLogger(TripChargeBOImpl.class.getName());
	
	private IAdminTripChargeDAO adminTripChargeDao;
	

	public IAdminTripChargeDAO getAdminTripChargeDao() {
		return adminTripChargeDao;
	}

	public void setAdminTripChargeDao(IAdminTripChargeDAO adminTripChargeDao) {
		this.adminTripChargeDao = adminTripChargeDao;
	}

	public TripChargeVO get(Integer buyerId, Integer skillNodeId)
	{
		
		TripChargeVO tcv= new TripChargeVO();
		tcv.setBuyerID(buyerId);
		tcv.setMainCategoryID(skillNodeId);
		try {
			tcv =  adminTripChargeDao.get(tcv);
		} catch (DataServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return tcv;
	}

	public List<TripChargeVO> getAll(Integer buyerId) {
		List<TripChargeVO> list=null;
		TripChargeVO tcv = new TripChargeVO();
		tcv.setBuyerID(buyerId);
		try {
			list =  adminTripChargeDao.getAll(tcv);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	public void save(TripChargeVO tripChargeVO)
	{
		try {
				adminTripChargeDao.save(tripChargeVO);
		} catch (DataServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

}