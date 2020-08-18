package com.newco.marketplace.persistence.daoImpl.iso;

import java.util.List;

import com.newco.marketplace.dao.impl.MPBaseDaoImpl;
import com.newco.marketplace.dto.vo.iso.IsoMessageGenericRecord;
import com.newco.marketplace.dto.vo.iso.IsoMessageTemplateVO;
import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.persistence.iDao.iso.IsoMessageTemplateDao;


public class IsoMessageTemplateDaoImpl extends MPBaseDaoImpl implements IsoMessageTemplateDao{

	public IsoMessageTemplateVO getIsoMessageTemplate(String messageTemplateShortAlias) throws DataServiceException
	 {
		List<IsoMessageGenericRecord> isoMessageGeneriRecordList = null;
		IsoMessageTemplateVO isoMessageTemplateVO = null;
		try {
			isoMessageGeneriRecordList = queryForList("messageTemplate.query", new String(messageTemplateShortAlias));
			if (isoMessageGeneriRecordList!=null && isoMessageGeneriRecordList.size()>0) {
				isoMessageTemplateVO = new IsoMessageTemplateVO();
				isoMessageTemplateVO.setIsoMessageGenericRecord(isoMessageGeneriRecordList);
			}
			} catch (Exception ex) {
				throw new DataServiceException(
						"Exception occurred in IsoMessageTemplateDao.getIsoMessageTemplate()", ex);
			}
			return isoMessageTemplateVO;
	 }
}

