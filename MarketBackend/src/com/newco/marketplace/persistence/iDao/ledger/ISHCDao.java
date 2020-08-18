package com.newco.marketplace.persistence.iDao.ledger;

import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.dto.vo.ledger.GLSummaryVO;

public interface ISHCDao {

	public GLSummaryVO  getSearsGLTransAmount( List<Integer>  buyerIds);
	public ArrayList<GLSummaryVO>  getAccountsCommissions( List<Integer>  buyerIds);
}
