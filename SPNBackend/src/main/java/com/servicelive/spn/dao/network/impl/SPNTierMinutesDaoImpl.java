package com.servicelive.spn.dao.network.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.servicelive.domain.spn.network.SPNTierMinutes;
import com.servicelive.spn.dao.AbstractBaseDao;
import com.servicelive.spn.dao.network.SPNTierMinutesDao;

public class SPNTierMinutesDaoImpl extends AbstractBaseDao implements SPNTierMinutesDao
{
	@Transactional ( propagation = Propagation.REQUIRED)
	public void delete(SPNTierMinutes entity) throws Exception
	{
		super.delete(entity);
		super.getEntityManager().flush();
	}
	
	@Transactional ( propagation = Propagation.REQUIRED)	
	public int deleteTierMinutes(Integer spnId, Integer tierId) throws Exception
	{
		String hql = "delete from SPNTierMinutes tm where tm.spnTierPK.spnId.spnId = :spnId and tm.spnTierPK.tierId.id = :tierId";
		Query query = super.getEntityManager().createQuery(hql);
		query.setParameter("spnId", spnId);
		query.setParameter("tierId", tierId);
		return query.executeUpdate();		
	}
	
	@Transactional ( propagation = Propagation.REQUIRED)	
	public int  deleteTierMinutes(Integer spnId) throws Exception
	{
		String hql = "delete from SPNTierMinutes tm where tm.spnTierPK.spnId.spnId = :spnId ";
		Query query = super.getEntityManager().createQuery(hql);
		query.setParameter("spnId", spnId);
		return query.executeUpdate();		
	}
	
	@SuppressWarnings("unchecked")
	public SPNTierMinutes findAllBySpnId(Integer spnId) throws Exception
	{
		return (SPNTierMinutes) super.findByProperty("SPNTierMinutes", "spnTierPK.spnId", spnId);
	}

	@SuppressWarnings("unchecked")
	public List<SPNTierMinutes> findAll(int... rowStartIdxAndCount) throws Exception
	{
		return (List<SPNTierMinutes>) super.findAll("SPNTierMinutes", rowStartIdxAndCount);
	}

	public SPNTierMinutes findById(Integer id) throws Exception
	{
		return (SPNTierMinutes) super.findById(SPNTierMinutes.class, id) ;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<SPNTierMinutes> findByProperty(String propertyName, Object value, int... rowStartIdxAndCount) throws Exception
	{
		return (List<SPNTierMinutes>) super.findByProperty("SPNTierMinutes", propertyName, value,rowStartIdxAndCount);
	}

	@Transactional ( propagation = Propagation.REQUIRED)
	public void save(SPNTierMinutes entity) throws Exception
	{
		super.save(entity);
		super.getEntityManager().flush();
	}

	@Transactional ( propagation = Propagation.REQUIRED)
	public SPNTierMinutes update(SPNTierMinutes entity) throws Exception
	{
		SPNTierMinutes tierMinutes = (SPNTierMinutes) super.update(entity);
		super.getEntityManager().flush();
		return tierMinutes;
	}


}
