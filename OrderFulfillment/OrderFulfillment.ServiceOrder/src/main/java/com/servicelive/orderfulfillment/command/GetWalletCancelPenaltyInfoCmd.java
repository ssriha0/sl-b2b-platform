package com.servicelive.orderfulfillment.command;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Map;

import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.wallet.serviceinterface.vo.ReceiptVO;
import com.servicelive.orderfulfillment.common.OFConstants;

public class GetWalletCancelPenaltyInfoCmd extends SOCommand {
    @Override
    public void execute(Map<String, Object> processVariables) {
        ServiceOrder so = getServiceOrder(processVariables);
        NumberFormat formatter = new DecimalFormat("#0.00");
        String siteUrl=serviceOrderDao.getDefaultTemplate(OFConstants.SERVICE_URL);

        ReceiptVO cancelReceipt = walletGateway.getCancellationPenaltyReceipt(so.getAcceptedProviderId(), so.getSoId());
        if (cancelReceipt==null) {
            processVariables.put(ProcessVariableUtil.PVKEY_LEDGER_CANCEL_PNLTY_AMT, "NA");
            processVariables.put(ProcessVariableUtil.PVKEY_LEDGER_CANCEL_PNLTY_TX_ID, "No Transaction Id");
        } else {
            processVariables.put(ProcessVariableUtil.PVKEY_LEDGER_CANCEL_PNLTY_AMT, formatter.format(cancelReceipt.getTransactionAmount()));
            processVariables.put(ProcessVariableUtil.PVKEY_LEDGER_CANCEL_PNLTY_TX_ID, cancelReceipt.getTransactionID().toString());
        }
        
        if(siteUrl!=null){
        	
        	processVariables.put(ProcessVariableUtil.PVKEY_SERVICE_URL, "http://" +siteUrl+ "/termsAndConditions_displayBucksAgreement.action");
        }
    }
}