package com.servicelive.routingrulesengine.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.servicelive.domain.lookup.LookupZipGeocode;
import com.servicelive.routingrulesengine.dao.LookupZipGeocodeDao;

public class LookupZipGeocodeDaoImpl extends AbstractBaseDao implements LookupZipGeocodeDao {

	@SuppressWarnings("unchecked")
	public List<LookupZipGeocode> findByState(String stateCd) {
		String hql = "from " +LookupZipGeocode.class.getSimpleName()+ " where stateCd = :stateCd ";
		Query query = this.getEntityManager().createQuery(hql);
		query.setParameter("stateCd", stateCd);
		return query.getResultList();
	}

	public LookupZipGeocode findByZip(String zip)
	{
		String hql = "from " +LookupZipGeocode.class.getSimpleName()+ " where zip = :zip ";
		Query query = this.getEntityManager().createQuery(hql);
		query.setParameter("zip", zip);
		return (LookupZipGeocode) query.getSingleResult();
	}
	
	
	/**
	 * Find the valid zips from the list
	 * 
	 * @param zipList
	 * @return List<String>
	 */

	public List<String> findZips(List<String> zips) {
		String hql = "select lookupZip.zip from LookupZipGeocode lookupZip where lookupZip.zip in (:zips)";
		Query query = this.getEntityManager().createQuery(hql);
		query.setParameter("zips", zips);
		try {
			@SuppressWarnings("unchecked")
			List<String> zipList = query.getResultList();
			return zipList;
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public Map<String,String> findCityByZips(List<String> zips){
		Map<String,String> cityZipMap = new HashMap<String, String>();
		String hql = "select lookupZip from LookupZipGeocode lookupZip where lookupZip.zip in (:zips)";
		Query query = this.getEntityManager().createQuery(hql);
		query.setParameter("zips", zips);
		try {
			@SuppressWarnings("unchecked")
			List<LookupZipGeocode> lookupList = query.getResultList();
			for(LookupZipGeocode zipGeoCode: lookupList){
				String zipCode = zipGeoCode.getZip();
				String value = zipCode + " - " + zipGeoCode.getCity() + " " + zipGeoCode.getStateCd();
				cityZipMap.put(zipCode, value);
			}
		}catch (NoResultException e) {
			return null;
		}
		return cityZipMap;
	}

}
