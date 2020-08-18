package com.servicelive.wallet.alert.dao;

import com.servicelive.common.ABaseDao;
import com.servicelive.common.exception.DataServiceException;
import com.servicelive.wallet.alert.vo.TemplateVO;

// TODO: Auto-generated Javadoc
/**
 * Class TemplateDao.
 */
public class TemplateDao extends ABaseDao implements ITemplateDao {

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.alert.dao.ITemplateDao#getTemplateById(java.lang.Integer)
	 */
	public TemplateVO getTemplateById(Integer templateId) throws DataServiceException {

		return (TemplateVO) queryForObject("getTemplateById.query", templateId);
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.alert.dao.ITemplateDao#query(com.servicelive.wallet.alert.vo.TemplateVO)
	 */
	public TemplateVO query(TemplateVO template) throws DataServiceException {

		return (TemplateVO) queryForObject("template.query", template);
	}
}
