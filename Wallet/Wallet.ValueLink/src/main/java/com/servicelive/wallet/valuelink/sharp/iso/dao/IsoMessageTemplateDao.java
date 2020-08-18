package com.servicelive.wallet.valuelink.sharp.iso.dao;

import java.util.List;

import com.servicelive.common.ABaseDao;
import com.servicelive.common.exception.DataServiceException;
import com.servicelive.wallet.valuelink.sharp.iso.vo.IsoMessageGenericRecordVO;
import com.servicelive.wallet.valuelink.sharp.iso.vo.IsoMessageTemplateVO;

// TODO: Auto-generated Javadoc
/**
 * Class IsoMessageTemplateDao.
 */
public class IsoMessageTemplateDao extends ABaseDao implements IIsoMessageTemplateDao {

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.valuelink.sharp.iso.dao.IIsoMessageTemplateDao#getIsoMessageTemplate(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public IsoMessageTemplateVO getIsoMessageTemplate(String messageTemplateShortAlias) throws DataServiceException {

		List<IsoMessageGenericRecordVO> isoMessageGeneriRecordList = null;
		IsoMessageTemplateVO isoMessageTemplateVO = null;
		try {
			isoMessageGeneriRecordList = queryForList("messageTemplate.query", messageTemplateShortAlias);
			if (isoMessageGeneriRecordList != null && !isoMessageGeneriRecordList.isEmpty()) {
				isoMessageTemplateVO = new IsoMessageTemplateVO();
				isoMessageTemplateVO.setIsoMessageGenericRecord(isoMessageGeneriRecordList);
			}
		} catch (Exception ex) {
			throw new DataServiceException("Exception occurred in IsoMessageTemplateDao.getIsoMessageTemplate()", ex);
		}
		return isoMessageTemplateVO;
	}
}
