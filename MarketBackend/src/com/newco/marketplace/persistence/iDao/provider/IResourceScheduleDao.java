/**
 * 
 */
package com.newco.marketplace.persistence.iDao.provider;

import java.util.List;

import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.vo.provider.GeneralInfoVO;

/**
 * @author KSudhanshu
 *
 */
public interface IResourceScheduleDao {

	/**
	 * @param generalInfoVO
	 * @return
	 * @throws DBException
	 */
	public int update(GeneralInfoVO generalInfoVO)throws DBException;
    
	/**
	 * @param generalInfoVO
	 * @return
	 * @throws DBException
	 */
	public GeneralInfoVO get(GeneralInfoVO generalInfoVO) throws DBException;
    
	/**
	 * @param generalInfoVO
	 * @return
	 * @throws DBException
	 */
	public GeneralInfoVO insert(GeneralInfoVO generalInfoVO) throws DBException;
	
	public int updateResourceSchedule(GeneralInfoVO generalInfoVO) throws DBException;
	
	public void deleteResourceSchdedule(GeneralInfoVO generalInfoVO) throws DBException;

	public List<GeneralInfoVO> fetchProviderScheduleByProviderList(List<String> providerList) throws DBException;
}
