select count(*) from ledger_transaction_entry;
-- 12778
-- 12261
truncate table ledger_balance;

CREATE TABLE ledger_transaction_entry_temp AS 
select * from ledger_transaction_entry;

truncate table ledger_transaction_entry;

DROP TRIGGER IF EXISTS `ledger_transaction_entry_insb`;
DROP TRIGGER IF EXISTS `ledger_transaction_entry_updb`;

insert into ledger_transaction_entry
select te.*
from ledger_entry le inner join ledger_transaction_entry_temp te 
      on le.ledger_entry_id = te.ledger_entry_id inner join ledger_transaction_rule r
      on le.ledger_entry_rule_id = r.ledger_entry_rule_id
order by le.created_date, le.so_id, r.sort_order, le.ledger_entry_rule_id, te.entry_type_id
;

create trigger `ledger_transaction_entry_insb` BEFORE INSERT on `ledger_transaction_entry` 
for each row set NEW.created_date=NOW(), NEW.modified_date=NOW();

create trigger `ledger_transaction_entry_updb` BEFORE UPDATE on `ledger_transaction_entry` 
for each row set NEW.modified_date=NOW();

-- DROP TABLE IF EXISTS ledger_transaction_entry_temp;