package com.servicelive.wallet.valuelink.sharp.iso.dao;

import com.servicelive.common.exception.DataServiceException;
import com.servicelive.wallet.valuelink.sharp.iso.vo.IsoMessageTemplateVO;

// TODO: Auto-generated Javadoc
/**
 * Interface IIsoMessageTemplateDao.
 */
public interface IIsoMessageTemplateDao {

	/**
	 * getIsoMessageTemplate.
	 * 
	 * @param messageTemplateShortAlias 
	 * 
	 * @return IsoMessageTemplateVO
	 * 
	 * @throws DataServiceException 
	 */
	public IsoMessageTemplateVO getIsoMessageTemplate(String messageTemplateShortAlias) throws DataServiceException;
}
