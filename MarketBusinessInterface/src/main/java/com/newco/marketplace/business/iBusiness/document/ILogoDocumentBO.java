package com.newco.marketplace.business.iBusiness.document;

import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.exception.core.BusinessServiceException;

public interface ILogoDocumentBO {
	/**
	 * retrieveLogoForBuyer returns one Logo document associated with the given Buyer
	 * If no Logo exits returns ServiceLive logo as default
	 * @param buyerId
	 * @param categoryId
	 * @return logoDocumentId
	 * @throws BusinessServiceException
	 */
	public DocumentVO getLogoForBuyer(Integer buyerId)throws BusinessServiceException;

	/**
	 * get ServiceLive Logo from class path
	 * @return
	 */
	public DocumentVO getServiceLiveLogo()throws BusinessServiceException ;

}
