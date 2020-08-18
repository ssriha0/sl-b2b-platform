package com.servicelive.wallet.alert.dao;

import java.util.List;

import com.servicelive.common.ABaseDao;
import com.servicelive.common.exception.DataServiceException;
import com.servicelive.wallet.alert.vo.ContactVO;

// TODO: Auto-generated Javadoc
/**
 * Class ContactDao.
 */
public class ContactDao extends ABaseDao implements IContactDao {

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.alert.dao.IContactDao#getBuyerInformation(long)
	 */
	public List<ContactVO> getBuyerInformation(long buyerId) throws DataServiceException {

		try {

			return (List<ContactVO>) queryForList("buyerContactInfoFromBuyerId.select", buyerId);
		} catch (Exception ex) {
			logger.info("General Exception @ContactDao.getBuyerInformation() due to" + ex.getMessage());
			throw new DataServiceException("General Exception @ContactDao.getBuyerInformation() due to " + ex.getMessage(),ex);
		}
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.alert.dao.IContactDao#getProviderInformation(long)
	 */
	public List<ContactVO> getProviderInformation(long providerId) throws DataServiceException {

		try {

			return (List<ContactVO>) queryForList("vendorContactInfoFromVendorId.select", providerId);
		} catch (Exception ex) {
			logger.info("General Exception @ContactDao.getProviderInformation() due to " + ex.getMessage());
			throw new DataServiceException("General Exception @ContactDao.getProviderInformation() ", ex);
		}
	}

}
