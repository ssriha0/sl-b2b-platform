package com.servicelive.wallet.serviceinterface.vo;


public enum LedgerEntryType {
	LEDGER_ENTRY_RULE_ID_SO_POSTING_FEE(1100),
	LEDGER_ENTRY_RULE_ID_SO_REROUTE_FEE(1200),
	LEDGER_ENTRY_RULE_ID_SO_SPEND_LIMIT(1120),
	LEDGER_ENTRY_RULE_ID_CANCELLATION_PENALTY(1300),
	LEDGER_ENTRY_RULE_ID_SO_PROVIDER_FINAL_PRICE(1400),
	LEDGER_ENTRY_RULE_ID_SO_PROVIDER_TOTAL_PRICE_DECLARATION(1405),
	LEDGER_ENTRY_RULE_ID_SO_PROVIDER_SERVICE_FEE(1410),
	LEDGER_ENTRY_RULE_ID_SLA_CREDIT_TO_BUYER(5300),
	LEDGER_ENTRY_RULE_ID_SLA_CREDIT_TO_PROVIDER(5400),
	LEDGER_ENTRY_RULE_ID_SLA_DEBIT_TO_BUYER(5500),
	LEDGER_ENTRY_RULE_ID_SLA_DEBIT_TO_PROVIDER(5600),
	LEDGER_ENTRY_RULE_ID_SHC_RELEASE_SO_PAYMENT(10005),
	LEDGER_ENTRY_RULE_ID_TRANSFER_COMMISSION(10002);	
	
    private int id;
    
    LedgerEntryType(int id){
        this.id=id;
    }
    
    public int getId(){
        return id;
    }
    
    public static LedgerEntryType fromId(int id){
        switch(id){
        case 1100:
            return LEDGER_ENTRY_RULE_ID_SO_POSTING_FEE;
        case 1200:
        	return LEDGER_ENTRY_RULE_ID_SO_REROUTE_FEE;
        case 1120:
        	return LEDGER_ENTRY_RULE_ID_SO_SPEND_LIMIT;
        case 1300:
        	return LEDGER_ENTRY_RULE_ID_CANCELLATION_PENALTY;
        case 1400:
        	return LEDGER_ENTRY_RULE_ID_SO_PROVIDER_FINAL_PRICE;
        case 1405:
        	return LEDGER_ENTRY_RULE_ID_SO_PROVIDER_TOTAL_PRICE_DECLARATION;
        case 1410:
        	return LEDGER_ENTRY_RULE_ID_SO_PROVIDER_SERVICE_FEE;
        case 5300:
        	return LEDGER_ENTRY_RULE_ID_SLA_CREDIT_TO_BUYER;
        case 5400:
        	return LEDGER_ENTRY_RULE_ID_SLA_CREDIT_TO_PROVIDER;
        case 5500:
        	return LEDGER_ENTRY_RULE_ID_SLA_DEBIT_TO_BUYER;
        case 5600:
        	return LEDGER_ENTRY_RULE_ID_SLA_DEBIT_TO_PROVIDER;
        case 10005:
        	return LEDGER_ENTRY_RULE_ID_SHC_RELEASE_SO_PAYMENT;
        case 10002:
        	return LEDGER_ENTRY_RULE_ID_TRANSFER_COMMISSION;        	

        default:
                throw new IllegalArgumentException("Illegal value passed for ID in LedgerEntryType: " + id);
        }
    }

}
