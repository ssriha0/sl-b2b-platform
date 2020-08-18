package com.newco.marketplace.persistence.daoImpl.so.order;

import org.springframework.dao.DataAccessException;

import com.newco.marketplace.dto.vo.so.order.SOPostProcessingVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.so.SOPostProcessDao;
import com.sears.os.dao.impl.ABaseImplDao;

/**
 * DAO for so_action_post_processing table
 * @author Michael J. Hayes, Sogeti USA, LLC
 *
 * $Revision: 1.3 $ $Author: glacy $ $Date: 2008/04/26 00:40:27 $
 */

/*
 * Maintenance History: See bottom of page
 */
public class SOPostProcessDaoImpl extends ABaseImplDao implements
		SOPostProcessDao {

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.so.SOPostProcessDao#getConcreteClassName(java.lang.Integer, java.lang.String)
	 */
	public String getConcreteClassName(Integer buyerId, String action_id)
			throws DataServiceException {
	
		String className = null;
		
		SOPostProcessingVO vo = new SOPostProcessingVO();
		
		vo.setBuyerId(buyerId);
		vo.setActionId(action_id);
		try {
			className = (String)queryForObject("soActionPostProcessing.query_class_name_by_buyer_and_action", vo);
		} catch (DataAccessException e) {
			throw new DataServiceException("Error retrieving so_post_processing row",e);
		}
		
		return className;
	}

}
/*
 * Maintenance History
 * $Log: SOPostProcessDaoImpl.java,v $
 * Revision 1.3  2008/04/26 00:40:27  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.1.24.1  2008/04/23 11:42:19  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.2  2008/04/23 05:02:08  hravi
 * Reverting to build 247.
 *
 * Revision 1.1  2008/01/08 18:27:48  mhaye05
 * Initial Check In
 *
 */