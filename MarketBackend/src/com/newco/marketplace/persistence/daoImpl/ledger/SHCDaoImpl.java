package com.newco.marketplace.persistence.daoImpl.ledger;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.dto.vo.ledger.GLSummaryVO;
import com.newco.marketplace.persistence.iDao.ledger.ISHCDao;
import com.sears.os.dao.impl.ABaseImplDao;

public class SHCDaoImpl extends ABaseImplDao implements ISHCDao {

	private static final Logger localLogger = Logger
	.getLogger(SHCDaoImpl.class.getName());
	
	public GLSummaryVO  getSearsGLTransAmount( List<Integer>  buyerIds)
	{
			return (GLSummaryVO) queryForObject("shc_gl_trans_amount.query", buyerIds);
	}

	public ArrayList<GLSummaryVO>  getAccountsCommissions( List<Integer>  buyerIds)
	{
		ArrayList<GLSummaryVO> commissions = new ArrayList<GLSummaryVO>();
		if (buyerIds.size() > 0){
			commissions =  (ArrayList<GLSummaryVO>) queryForList("shc_gl_commission.query", buyerIds);
		}
		return commissions;
	}

}
