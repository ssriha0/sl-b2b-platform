ALTER TABLE lu_ach_reason_codes
 ADD reversal_ind SMALLINT(1) AFTER reason_desc;

Update ledger_transaction_rule set debit_t_acct_no = 500  where ledger_entry_rule_id = 5010
Update ledger_transaction_rule set credit_t_acct_no = 500  where ledger_entry_rule_id = 5010

Insert into ledger_transaction_rule_funding 
(funding_type_id,bus_trans_id,ledger_entry_rule_id,pricing_expression)
values (10,40,5010,'TRANSFER_AMOUNT')