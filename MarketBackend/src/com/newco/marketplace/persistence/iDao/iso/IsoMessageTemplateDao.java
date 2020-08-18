package com.newco.marketplace.persistence.iDao.iso;

import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.dto.vo.iso.IsoMessageTemplateVO;

public interface IsoMessageTemplateDao {
	public IsoMessageTemplateVO getIsoMessageTemplate(String messageTemplateShortAlias) throws DataServiceException;
}
