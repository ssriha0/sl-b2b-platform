/**
 * 
 */
package com.servicelive.staging.dao.hsr;

import java.util.List;

import com.servicelive.staging.dao.BaseDao;
import com.servicelive.staging.domain.hsr.HSRStageOrder;

/**
 * @author hoza
 *
 */
public interface IHSRStagingOrderDao extends BaseDao {
	/**
	 * 
	 * @param rowStartIdxAndCount
	 * @return
	 * @throws Exception
	 */
	public List<HSRStageOrder> findAll ( int... rowStartIdxAndCount) throws Exception;
	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public HSRStageOrder findById(Integer id) throws Exception;
	/**
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void save(HSRStageOrder entity) throws Exception;
	/**
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public HSRStageOrder update(HSRStageOrder entity) throws Exception;
	/**
	 * 
	 * @param orderNo
	 * @param unitNo
	 * @return
	 * @throws Exception
	 */
	public HSRStageOrder findByOrderNoAndUnitNo(final String orderNo, final String unitNo) throws Exception;
	
	/**
	 * 
	 * @param orderNo
	 * @param unitNo
	 * @param soId
	 * @return
	 * @throws Exception
	 */
	public HSRStageOrder updateSoId(final String orderNo, final String unitNo, final String soId) throws Exception;
	public List<HSRStageOrder> findAllByUnitAndOrderNumbers(List<String> unitAndOrderNumbers);
	public HSRStageOrder findLatestByUnitAndOrderNumbersWithTestSuffix(
			final String unitAndOrderNumber, final String testSuffix);
}
