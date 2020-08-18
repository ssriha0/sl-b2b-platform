package com.servicelive.spn.dao.network.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.servicelive.domain.spn.network.SPNTierPerformanceLevel;
import com.servicelive.spn.dao.AbstractBaseDao;
import com.servicelive.spn.dao.network.SPNTierPerformanceLevelDao;

public class SPNTierPerformanceLevelDaoImpl extends AbstractBaseDao implements SPNTierPerformanceLevelDao
{

	public void delete(SPNTierPerformanceLevel entity) throws Exception
	{
		super.delete(entity);
	}
	
	@Transactional ( propagation = Propagation.REQUIRED)	
	public int deletePerformanceLevels(Integer spnId, Integer tierId) throws Exception
	{
		String hql = "delete from SPNTierPerformanceLevel pl where pl.spnId.spnId = :spnId and pl.tierId.id = :tierId";
		Query query = super.getEntityManager().createQuery(hql);
		query.setParameter("spnId", spnId);
		query.setParameter("tierId", tierId);
		return query.executeUpdate();				
	}
	
	@Transactional ( propagation = Propagation.REQUIRED)	
	public int deletePerformanceLevels(Integer spnId) throws Exception
	{
		String hql = "delete from SPNTierPerformanceLevel pl where pl.spnId.spnId = :spnId ";
		Query query = super.getEntityManager().createQuery(hql);
		query.setParameter("spnId", spnId);
		
		return query.executeUpdate();				
	}

	@SuppressWarnings("unchecked")
	public List<SPNTierPerformanceLevel> findAll(int... rowStartIdxAndCount) throws Exception
	{
		return (List<SPNTierPerformanceLevel>) super.findAll("SPNTierPerformanceLevel", rowStartIdxAndCount);
	}

	public SPNTierPerformanceLevel findById(Integer id) throws Exception
	{
		return (SPNTierPerformanceLevel) super.findById(SPNTierPerformanceLevel.class, id) ;
	}
	
	@SuppressWarnings("unchecked")
	public List<SPNTierPerformanceLevel> findByProperty(String propertyName, Object value, int... rowStartIdxAndCount) throws Exception
	{
		return (List<SPNTierPerformanceLevel>) super.findByProperty("SPNTierPerformanceLevel", propertyName, value,rowStartIdxAndCount);
	}

	@Transactional ( propagation = Propagation.REQUIRED)
	public void save(SPNTierPerformanceLevel entity) throws Exception
	{
		super.save(entity);
	}

	@Transactional ( propagation = Propagation.REQUIRED)
	public SPNTierPerformanceLevel update(SPNTierPerformanceLevel entity) throws Exception
	{
		return (SPNTierPerformanceLevel) super.update(entity);	
	}


}
