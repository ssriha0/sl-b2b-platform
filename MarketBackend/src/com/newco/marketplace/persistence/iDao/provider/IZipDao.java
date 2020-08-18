package com.newco.marketplace.persistence.iDao.provider;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.newco.marketplace.dto.vo.provider.StateZipcodeVO;



public interface IZipDao {
	
	public List queryList(String id) throws DataAccessException;

	/**
	 * Method to determine if entered zip is valid based on whether it is found 
	 * in DB.
	 * @param zip
	 * @return
	 * @throws DataAccessException
	 */
	public int queryZipCount(String zip) throws DataAccessException;
	public List<StateZipcodeVO> queryStateCdAndZip(List<String> zipList) throws DataAccessException;

}
