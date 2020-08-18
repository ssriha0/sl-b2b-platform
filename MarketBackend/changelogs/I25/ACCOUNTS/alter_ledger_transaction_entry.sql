ALTER TABLE `ledger_transaction_entry` ADD COLUMN `originating_buyer_id` INTEGER UNSIGNED DEFAULT NULL AFTER `ledger_entity_id`;
ALTER TABLE `ledger_transaction_entry` ADD INDEX `IDX_ledger_trans_entry_orig_buyer_id`(`originating_buyer_id`);

ALTER TABLE `ledger_transaction_entry` ADD INDEX `IDX_ledge_trans_entry_type_and_buyer`(`ledger_entity_type_id`, `originating_buyer_id`);


-- WARNING - cross schema update, must change schema names
-- also will not work as expected due to update trigger, all rows will get updated
UPDATE accounts_prod.ledger_transaction_entry lte,
supplier_prod.so_hdr so, accounts_prod.ledger_entry le
   SET lte.originating_buyer_id = so.buyer_id
WHERE 
lte.ledger_entry_id = le.ledger_entry_id
AND so.so_id = le.so_id ;