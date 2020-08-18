package com.newco.marketplace.persistence.daoImpl.feemanager;

import java.util.ArrayList;
import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.dto.vo.fee.FeeInfoItemVO;
import com.newco.marketplace.dto.vo.fee.FeeInfoVO;
import com.newco.marketplace.dto.vo.ledger.MarketPlaceTransactionVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.feemanager.FeeManagerDao;
import com.newco.marketplace.utils.StackTraceHelper;
import com.sears.os.dao.impl.ABaseImplDao;

public class FeeManagerDaoImpl extends ABaseImplDao implements FeeManagerDao{

	private static final Logger localLogger = Logger.getLogger(FeeManagerDaoImpl.class);
	
	public FeeInfoVO getLedgerFee(MarketPlaceTransactionVO marketVO) throws DataServiceException{
		
		try
		{
			localLogger.info("INside of FeeManagerDaoImpl::::getLedgerFee()");
			localLogger.info("Market vo values "+marketVO.getLedgerEntryRuleId()+"  "+
					marketVO.getUserTypeID());
    		FeeInfoVO feeVo = (FeeInfoVO) queryForObject("ledger_fee.query", marketVO);
    		
    		localLogger.info("After queryForObject(ledger_fee.query, marketVO)");
    		localLogger.info("retrived values "+feeVo.getFeeName()+"  "+feeVo.getLedgerEntryRuleId()+"  "+feeVo.getLedgerFeeId()+"   "+feeVo.getLedgerFeeTypeId());
    		ArrayList<FeeInfoItemVO> feeDetailVo = (ArrayList<FeeInfoItemVO>) queryForList("ledger_fee_details.query", feeVo.getLedgerFeeId());
    			
    		feeVo.setFeeInfoItem(feeDetailVo);
    		
    		return feeVo;
    	}catch(Exception ex){
            localLogger.info("[FeeManagerDaoImpl.getLedgerFee - Exception] "
                    + StackTraceHelper.getStackTrace(ex));
            throw new DataServiceException("TEST", ex);
    	}
	
	}

	public FeeInfoVO getLedgerFeeAndAmount(MarketPlaceTransactionVO marketVO) throws DataServiceException{
		
		try
		{
			localLogger.info("INside of FeeManagerDaoImpl::::getLedgerFee()");
			localLogger.info("Market vo values "+marketVO.getLedgerEntryRuleId()+"  "+
					marketVO.getUserTypeID() + "   " + marketVO.getfeeTypeID());
			
    		FeeInfoVO feeVo = (FeeInfoVO) queryForObject("ledger_fee_type.query", marketVO);
    		
    		localLogger.info("After queryForObject(ledger_fee_type.query, marketVO)");
    		localLogger.info("retrived values "+feeVo.getFeeName()+"  "+feeVo.getLedgerEntryRuleId()+"  "+feeVo.getLedgerFeeId()+"   "+feeVo.getLedgerFeeTypeId());
    		ArrayList<FeeInfoItemVO> feeDetailVo = (ArrayList<FeeInfoItemVO>) queryForList("ledger_fee_details.query", feeVo.getLedgerFeeId());
    			
    		feeVo.setFeeInfoItem(feeDetailVo);
    		
    		return feeVo;
    	}catch(Exception ex){
            localLogger.info("[FeeManagerDaoImpl.getLedgerFee - Exception] "
                    + StackTraceHelper.getStackTrace(ex));
            throw new DataServiceException("TEST", ex);
    	}
	
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> getLedgerRulePricingExpressionsMap()throws DataServiceException
	{
		try
		{
    		return getSqlMapClient().queryForMap("ledger_rule_pricing_expression.query",null, "ruleId", "expression");
    		
    	}catch(Exception ex){
            localLogger.info("[FeeManagerDaoImpl.getLedgerRulePricingExpressionsMap - Exception] "
                    + StackTraceHelper.getStackTrace(ex));
            throw new DataServiceException("TEST", ex);
    	}
		
	}	
}
