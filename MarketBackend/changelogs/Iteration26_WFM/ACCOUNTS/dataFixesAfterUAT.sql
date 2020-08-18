-- Updating bus trans id because wrong one was included in insert script

update ledger_transaction_rule_funding set bus_trans_id = 111 where funding_type_id = 40 and ledger_entry_rule_id=1397;

-- Updating transactions with 0.0 as the transAmount to reconciled = 0, ptd_reconciled_ind = 10, reconciled_date = now()

update fullfillment_entry set reconsiled_ind = 0,ptd_reconsiled=10, ptd_reconsiled_date = now(), reconsiled_date = now() where trans_amt = 0.0;

-- update auto_ach_ind rule

update ledger_transaction_rule set auto_ach_ind = 0 where ledger_entry_rule_id = 1280 and bus_trans_id = 160;


