package com.servicelive.orderfulfillment.command;

import java.util.Map;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import com.servicelive.orderfulfillment.common.OFConstants;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.wallet.serviceinterface.vo.LedgerEntryType;
import com.servicelive.wallet.serviceinterface.vo.ReceiptVO;

public class GetWalletCloseInfoCmd extends SOCommand {
    @Override
    public void execute(Map<String, Object> processVariables) {
        ServiceOrder serviceOrder = getServiceOrder(processVariables);
		NumberFormat formatter = new DecimalFormat("#0.00");
		Integer fundedType = serviceOrder.getFundingTypeId();
/*		String ledgerTransIdPay = "Ledger Transaction ID error";		
		String ledgerTransIdFee = "Ledger Transaction Fee ID error";
		Double transAmountPay = 0.00D;
		Double transAmountFee = 0.00D;*/
		logger.info("fundedType: "+fundedType);
		logger.info("OFConstants.SHC_FUNDED: "+OFConstants.SHC_FUNDED);
		
		// for Prefunded type
		if(fundedType.intValue() == OFConstants.PREFUNDED || fundedType.intValue() == OFConstants.CONSUMER_FUNDED){
			logger.info("Starting for Prefunded type");
				// Adjusted payment to Provider
			ReceiptVO providerReceiptsVO = walletGateway.getTransactionReceipt(
						serviceOrder.getAcceptedProviderId(),
						OFConstants.LEDGER_ENTITY_TYPE_ID_PROVIDER, 
						LedgerEntryType.LEDGER_ENTRY_RULE_ID_SO_PROVIDER_FINAL_PRICE,
						serviceOrder.getSoId());
			logger.info("providerReceiptsVO: "+providerReceiptsVO);
				if(providerReceiptsVO != null) {
					processVariables.put(ProcessVariableUtil.PVKEY_LEDGER_TRANID_PAY, providerReceiptsVO.getTransactionID().toString());
					processVariables.put(ProcessVariableUtil.PVKEY_TRANS_AMOUNT_PAY, formatter.format(providerReceiptsVO.getTransactionAmount()));						
				}else{
					processVariables.put(ProcessVariableUtil.PVKEY_LEDGER_TRANID_PAY, "No Transaction Id");
					processVariables.put(ProcessVariableUtil.PVKEY_TRANS_AMOUNT_PAY, "NA");					
				}
				
				// Fee amount paid to ServiceLive
				ReceiptVO servicFeeReceiptsVO  = walletGateway.getTransactionReceipt(
						2L,
						OFConstants.LEDGER_ENTITY_TYPE_ID_SERVICELIVE, 
						LedgerEntryType.LEDGER_ENTRY_RULE_ID_SO_PROVIDER_SERVICE_FEE,
						serviceOrder.getSoId());
				logger.info("servicFeeReceiptsVO: "+servicFeeReceiptsVO);
				if(servicFeeReceiptsVO != null) {
					processVariables.put(ProcessVariableUtil.PVKEY_LEDGER_TRANID_FEE, servicFeeReceiptsVO.getTransactionID().toString());
					processVariables.put(ProcessVariableUtil.PVKEY_TRANS_AMOUNT_FEE, formatter.format(servicFeeReceiptsVO.getTransactionAmount()));						
				}else{
					processVariables.put(ProcessVariableUtil.PVKEY_LEDGER_TRANID_FEE, "No Transaction Id");
					processVariables.put(ProcessVariableUtil.PVKEY_TRANS_AMOUNT_FEE, "NA");					
				}

	    }
		// for SHC funded
		else if(fundedType.intValue() == OFConstants.SHC_FUNDED){
			logger.info("Starting for SHC type");
				// Adjusted payment to Provider
			ReceiptVO providerReceiptsVO  = walletGateway.getTransactionReceipt(
						serviceOrder.getAcceptedProviderId(),
						OFConstants.LEDGER_ENTITY_TYPE_ID_PROVIDER, 
						LedgerEntryType.LEDGER_ENTRY_RULE_ID_SHC_RELEASE_SO_PAYMENT,
						serviceOrder.getSoId());
			logger.info("providerReceiptsVO: "+providerReceiptsVO);
			if(providerReceiptsVO != null) {
				processVariables.put(ProcessVariableUtil.PVKEY_LEDGER_TRANID_PAY, providerReceiptsVO.getTransactionID().toString());
				processVariables.put(ProcessVariableUtil.PVKEY_TRANS_AMOUNT_PAY, formatter.format(providerReceiptsVO.getTransactionAmount()));						
			}else{
				processVariables.put(ProcessVariableUtil.PVKEY_LEDGER_TRANID_PAY, "No Transaction Id");
				processVariables.put(ProcessVariableUtil.PVKEY_TRANS_AMOUNT_PAY, "NA");					
			}
				logger.info("ledgerTransIdPay: "+processVariables.get(ProcessVariableUtil.PVKEY_LEDGER_TRANID_PAY));
				logger.info("transAmountPay: "+processVariables.get(ProcessVariableUtil.PVKEY_TRANS_AMOUNT_PAY));
				
				// Fee amount paid to ServiceLive
				ReceiptVO servicFeeReceiptsVO = walletGateway.getTransactionReceipt(
						2L,
						OFConstants.LEDGER_ENTITY_TYPE_ID_SERVICELIVE, 
						LedgerEntryType.LEDGER_ENTRY_RULE_ID_TRANSFER_COMMISSION,
						serviceOrder.getSoId());
				logger.info("servicFeeReceiptsVO: "+servicFeeReceiptsVO);
				if(servicFeeReceiptsVO != null) {
					processVariables.put(ProcessVariableUtil.PVKEY_LEDGER_TRANID_FEE, servicFeeReceiptsVO.getTransactionID().toString());
					processVariables.put(ProcessVariableUtil.PVKEY_TRANS_AMOUNT_FEE, formatter.format(servicFeeReceiptsVO.getTransactionAmount()));						
				}else{
					processVariables.put(ProcessVariableUtil.PVKEY_LEDGER_TRANID_FEE, "No Transaction Id");
					processVariables.put(ProcessVariableUtil.PVKEY_TRANS_AMOUNT_FEE, "NA");					
				}
				logger.info("ledgerTransIdFee: "+processVariables.get(ProcessVariableUtil.PVKEY_LEDGER_TRANID_FEE));
				logger.info("transAmountFee: "+processVariables.get(ProcessVariableUtil.PVKEY_TRANS_AMOUNT_FEE));
	        }

    }
}