ALTER TABLE `ledger_transaction_entry` ADD COLUMN `originating_buyer_id` INTEGER UNSIGNED DEFAULT NULL AFTER `ledger_entity_id`;
ALTER TABLE `ledger_transaction_entry` ADD INDEX `IDX_ledger_trans_entry_orig_buyer_id`(`originating_buyer_id`);
 

-- UPDATE DEV_accounts_munged.ledger_transaction_entry lte,
--DEV_supplier_munged.so_hdr so, DEV_accounts_munged.ledger_entry le
--   SET lte.originating_buyer_id = so.buyer_id
--WHERE 
--lte.ledger_entry_id = le.ledger_entry_id
--AND so.so_id = le.so_id;