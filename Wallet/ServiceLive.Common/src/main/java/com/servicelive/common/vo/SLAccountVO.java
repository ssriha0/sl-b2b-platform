package com.servicelive.common.vo;

import org.springframework.beans.BeanUtils;

import com.newco.marketplace.dto.vo.ledger.Account;
import com.servicelive.common.exception.SLBusinessServiceException;

// TODO: Auto-generated Javadoc
/**
 * Class AccountVO.
 */
public class SLAccountVO extends Account {

	/** serialVersionUID. */
	private static final long serialVersionUID = 6744007235384748229L;

	public static SLAccountVO adapt(Account acctVO) throws SLBusinessServiceException{
		if (acctVO == null) return null;
		if (acctVO instanceof SLAccountVO) return (SLAccountVO) acctVO;
		
		return copy(acctVO);
	}
	public static SLAccountVO copy(Account acctVO) throws SLBusinessServiceException{
		if (acctVO == null) return null;
		
		SLAccountVO slAcctVO = new SLAccountVO();
		BeanUtils.copyProperties(acctVO, slAcctVO);
		return slAcctVO;
	}
}