package com.servicelive.wallet.alert.dao;

import com.servicelive.common.exception.DataServiceException;
import com.servicelive.wallet.alert.vo.TemplateVO;

// TODO: Auto-generated Javadoc
/**
 * Interface ITemplateDao.
 */
public interface ITemplateDao {

	/**
	 * getTemplateById.
	 * 
	 * @param templateId 
	 * 
	 * @return TemplateVO
	 * 
	 * @throws DataServiceException 
	 */
	public TemplateVO getTemplateById(Integer templateId) throws DataServiceException;

	/**
	 * query.
	 * 
	 * @param template 
	 * 
	 * @return TemplateVO
	 * 
	 * @throws DataServiceException 
	 */
	public TemplateVO query(TemplateVO template) throws DataServiceException;
}
