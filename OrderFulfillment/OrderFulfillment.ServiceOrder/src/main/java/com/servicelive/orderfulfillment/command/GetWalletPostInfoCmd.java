package com.servicelive.orderfulfillment.command;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Map;

import com.servicelive.orderfulfillment.command.util.SOCommandArgHelper;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.wallet.serviceinterface.vo.ReceiptVO;
import org.apache.commons.lang.StringUtils;

public class GetWalletPostInfoCmd extends SOCommand {
    @Override
    public void execute(Map<String, Object> processVariables) {
        ServiceOrder so = getServiceOrder(processVariables);
        NumberFormat formatter = new DecimalFormat("#0.00");
        
        ReceiptVO postReceipt = walletGateway.getBuyerPostingFeeReceipt(so.getBuyerId(), so.getSoId());
        if (postReceipt!=null) {
            processVariables.put(ProcessVariableUtil.PVKEY_LEDGER_POST_FEE, formatter.format(postReceipt.getTransactionAmount()));
            processVariables.put(ProcessVariableUtil.PVKEY_LEDGER_POST_TX_ID, postReceipt.getTransactionID().toString());
        }else{
            processVariables.put(ProcessVariableUtil.PVKEY_LEDGER_POST_FEE, "NA");
            processVariables.put(ProcessVariableUtil.PVKEY_LEDGER_POST_TX_ID, "No Transaction Id");
        }
        
        ReceiptVO spndLmtRsrvReceipt = walletGateway.getBuyerSpendLimitRsrvReceipt(so.getBuyerId(), so.getSoId());
        if (spndLmtRsrvReceipt!=null) {
        	Object fundsConfirmedTransactionIdObject = processVariables.get(ProcessVariableUtil.PVKEY_FUNDS_CONFIRMED_TX_ID);
            String fundsConfirmedTransactionId = null;
            if (fundsConfirmedTransactionIdObject != null) {
            	fundsConfirmedTransactionId = (String) fundsConfirmedTransactionIdObject;
            }
            Double spendLimitAmount = walletGateway.getCurrentSpendingLimit(so.getSoId());
        	
            processVariables.put(ProcessVariableUtil.PVKEY_LEDGER_SPEND_LMT_RSRV_AMT, formatter.format(spendLimitAmount));
            if (fundsConfirmedTransactionId != null && useFundsConfirmedTransactionId(processVariables)) {
            	processVariables.put(ProcessVariableUtil.PVKEY_LEDGER_SPEND_LMT_RSRV_TX_ID, fundsConfirmedTransactionId);
            } else {
            processVariables.put(ProcessVariableUtil.PVKEY_LEDGER_SPEND_LMT_RSRV_TX_ID, spndLmtRsrvReceipt.getTransactionID().toString());
            }
            
        }else{
            processVariables.put(ProcessVariableUtil.PVKEY_LEDGER_SPEND_LMT_RSRV_AMT, "NA");
            processVariables.put(ProcessVariableUtil.PVKEY_LEDGER_SPEND_LMT_RSRV_TX_ID, "No Transaction Id");
        }
    }

    private boolean useFundsConfirmedTransactionId(Map<String, Object> processVariables) {
        String useFundsConfirmedTransactionId = SOCommandArgHelper.extractStringArg(processVariables, 1);
        return StringUtils.isNotBlank(useFundsConfirmedTransactionId);
    }
}
