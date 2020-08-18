update ledger_transaction_rule set affects_balance=1 where affects_balance is null;

update ledger_transaction_rule set affects_balance=0 where debit_entity_type_id = credit_entity_type_id;

update ledger_entry le inner join ledger_transaction_rule ltr on (le.ledger_entry_rule_id=ltr.ledger_entry_rule_id)
	set le.affects_balance = ltr.affects_balance
where le.affects_balance is null;

update ledger_entry le inner join `DEV_I26_FIN_SUPPLIER`.so_hdr s on s.so_id = le.so_id
	set le.affects_balance = 0
where s.funding_type_id = 10;