/**
 *
 */
package com.newco.marketplace.persistence.daoImpl.provider;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.provider.IW9RegistrationDao;
import com.newco.marketplace.vo.provider.W9RegistrationVO;
import com.sears.os.dao.impl.ABaseImplDao;

/**
 * @author hoza
 *
 */
public class W9RegistrationDaoImpl extends ABaseImplDao implements
		IW9RegistrationDao {

	/*
	 * (non-Javadoc)
	 *
	 * @see com.newco.marketplace.persistence.iDao.provider.IW9RegistrationDao#isW9Exists(java.lang.Integer)
	 */
	public Boolean isW9Exists(Integer vendorId) throws Exception {

		try {
			Object obj = queryForObject("w9.isAvailable", vendorId);
			return (Boolean) obj;

		} catch (Exception exception) {

			throw new DataServiceException(
					"General Exception @isW9Exists due to "
							+ exception.getMessage());
		}

	}
	public Boolean isAvailableWithSSNInd(Integer vendorId) throws Exception {

		try {
			Object obj = queryForObject("w9.isAvailableWithSSNInd", vendorId);
			return (Boolean) obj;

		} catch (Exception exception) {

			throw new DataServiceException(
					"General Exception @isAvailableWithSSNInd due to "
							+ exception.getMessage());
		}

	}
	
	/* 
	 * This function is to fetch the ein number for the corresponding vendor.
	 * @param Integer
	 * @return String
	 */
	public String getEinNumber(Integer vendorId)throws Exception {

		try {
			Object obj = queryForObject("w9.getEinNumber", vendorId);
			return (String) obj;

		} catch (Exception exception) {

			throw new DataServiceException(
					"General Exception @getEinNumber due to "
							+ exception.getMessage());
		}

	}
	

	/*
	 * (non-Javadoc)
	 *
	 * @see com.newco.marketplace.persistence.iDao.provider.IW9RegistrationDao#get(java.lang.Integer)
	 */
	public W9RegistrationVO get(Integer vendorId) throws Exception {
		try {
			return (W9RegistrationVO) queryForObject("w9.get", vendorId);

		} catch (Exception exception) {

			throw new DataServiceException(
					"General Exception @get due to "
							+ exception.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.provider.IW9RegistrationDao#getForResource(java.lang.Integer)
	 */
	public W9RegistrationVO getForResource(Integer resourceId) throws Exception {
		try {
			return (W9RegistrationVO) queryForObject("w9.getForResource", resourceId);

		} catch (Exception exception) {

			throw new DataServiceException(
					"General Exception @getForResource due to "
							+ exception.getMessage());
		}

	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.provider.IW9RegistrationDao#getPrefillForResource(java.lang.Integer)
	 */
	public W9RegistrationVO getPrefillForResource(Integer resourceId)
			throws Exception {
		try {
			Object obj = queryForObject("w9.getPrefillForResource", resourceId);
			return (W9RegistrationVO) obj;

		} catch (Exception exception) {

			throw new DataServiceException(
					"General Exception @getPrefillForResource due to "
							+ exception.getMessage());
		}

	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.provider.IW9RegistrationDao#isW9ExistsForResource(java.lang.Integer)
	 */
	public Boolean isW9ExistsForResource(Integer resourceId) throws Exception {
		try {
			Object obj = queryForObject("w9.isAvailableForResource", resourceId);
			return (Boolean) obj;

		} catch (Exception exception) {

			throw new DataServiceException(
					"General Exception @isW9ExistsForResource due to "
							+ exception.getMessage());
		}

	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.provider.IW9RegistrationDao#getPrefill(java.lang.Integer)
	 */
	public W9RegistrationVO getPrefill(Integer vendorId) throws Exception {
		try {
			Object obj = queryForObject("w9.getPrefill", vendorId);
			return (W9RegistrationVO) obj;

		} catch (Exception exception) {

			throw new DataServiceException(
					"General Exception @getPrefillForResource due to "
							+ exception.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.provider.IW9RegistrationDao#save(com.newco.marketplace.vo.provider.W9RegistrationVO)
	 */
	public W9RegistrationVO save(W9RegistrationVO input) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.provider.IW9RegistrationDao#insert(com.newco.marketplace.vo.provider.W9RegistrationVO)
	 */
	public W9RegistrationVO insert(W9RegistrationVO input) throws Exception {
		try {
			input.setCreatedDate(new Date(System.currentTimeMillis()));
			input.setModifiedDate(new Date(System.currentTimeMillis()));
			Object obj = insert("w9.insert", input);
			return (W9RegistrationVO) obj;

		} catch (Exception exception) {

			throw new DataServiceException(
					"General Exception @insert due to "
							+ exception.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.provider.IW9RegistrationDao#update(com.newco.marketplace.vo.provider.W9RegistrationVO)
	 */
	public W9RegistrationVO update(W9RegistrationVO input) throws Exception {
		try {
			
			input.setModifiedDate(new Date(System.currentTimeMillis()));
			Object obj = insert("w9.update", input);
			return (W9RegistrationVO) obj;

		} catch (Exception exception) {

			throw new DataServiceException(
					"General Exception @update due to "
							+ exception.getMessage());
		}
	}
	
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.provider.IW9RegistrationDao#getW9History(java.lang.Integer)
	 */
	public List<W9RegistrationVO> getW9History(Integer vendorId)
			throws Exception {
		try {
			 List<W9RegistrationVO>  resultMap = new ArrayList<W9RegistrationVO>();
			 resultMap = queryForList("w9.getHistory", vendorId);
			return resultMap;

		} catch (Exception exception) {

			throw new DataServiceException(
					"General Exception @get due to "
							+ exception.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.provider.IW9RegistrationDao#getAll()
	 */
	public List<W9RegistrationVO> getAll() throws Exception {
		try {
			 List<W9RegistrationVO>  resultMap = new ArrayList<W9RegistrationVO>();
			 resultMap = queryForList("w9.getAll",null);
			return resultMap;

		} catch (Exception exception) {

			throw new DataServiceException(
					"General Exception @get due to "
							+ exception.getMessage());
		}
	}


	public Double getCompleteSOAmount(Integer vendorId)throws Exception
	{
		try
		{
			Object obj=queryForObject("w9.getAmount",vendorId);
			Double amount=(Double)obj;
			return amount;
		}
		catch(Exception exception)
		{
			throw new DataServiceException(
					"General Exception @get due to "
							+ exception.getMessage());
		}
		
	}

	public Double getProviderThreshold() throws Exception
	{
		try
		{
			Object obj=queryForObject("w9.getThreshold",null);
			if(obj!=null)
			{
				Double amount=(Double)obj;
				return amount;
			}
			return null;			
		}
		catch(Exception exception)
		{
			throw new DataServiceException(
					"General Exception @get due to "
							+ exception.getMessage());
		}
		
	}
	
	/* 
	 * This function is to determine that SSN information was provided but date of birth was not provided
	 * @param Integer
	 * @return Boolean
	 */
	public Boolean isDobNotAvailableWithSSN(Integer vendorId) throws Exception {

		try {
			Object obj = queryForObject("w9.isDobNotAvailableWithSSN", vendorId);
			return (Boolean) obj;

		} catch (Exception exception) {

			throw new DataServiceException(
					"General Exception @isAvailableWithSSNInd due to "
							+ exception.getMessage());
		}

	}
	
	public String getOrginalEin(Integer vendorId)throws Exception{
		
		try
		{
			Object obj=queryForObject("w9.getOrginalEin",vendorId);
			if(obj!=null)
			{
				String taxPayerId=(String)obj;
				return taxPayerId;
			}
			return null;			
		}
		catch(Exception exception)
		{
			throw new DataServiceException(
					"General Exception @get due to "
							+ exception.getMessage());
		}
		
		
	}
	

}
