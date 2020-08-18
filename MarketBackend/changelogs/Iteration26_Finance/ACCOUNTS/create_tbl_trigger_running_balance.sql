DROP TABLE IF EXISTS ledger_balance;

CREATE TABLE ledger_balance (
  ledger_balance_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  ledger_entry_id INT(10) UNSIGNED NOT NULL,
  ledger_entity_type_id INT(10) UNSIGNED NOT NULL,
  ledger_entity_id INT(10) UNSIGNED NOT NULL,
  available_balance DECIMAL(12,2) DEFAULT 0 NOT NULL,
  project_balance DECIMAL(12,2) NULL,
  balance_affected_ind VARCHAR(25) NULL,
  created_date datetime NULL,
  modified_date datetime NULL,
   PRIMARY KEY  (ledger_balance_id),
  CONSTRAINT FK_ledger_entry_id FOREIGN KEY(ledger_entry_id) REFERENCES ledger_entry (ledger_entry_id)
);

DELIMITER $$

DROP TRIGGER IF EXISTS `ledger_balance_ins`$$

create trigger `ledger_balance_ins` BEFORE INSERT on `ledger_balance` 
for each row set NEW.created_date=NOW(), NEW.modified_date=NOW();
$$

DROP TRIGGER IF EXISTS `ledger_balance_upd`$$

create trigger `ledger_balance_upd` BEFORE UPDATE on `ledger_balance` 
for each row set NEW.modified_date=NOW();
$$

DROP TRIGGER IF EXISTS `ledger_transaction_entry_insa`$$

CREATE TRIGGER `ledger_transaction_entry_insa` AFTER INSERT on `ledger_transaction_entry` 
FOR EACH ROW BEGIN

CALL sp_ledger_balance (
	NEW.ledger_entry_id,
	NEW.ledger_entity_id,
	NEW.ledger_entity_type_id,
	NEW.originating_buyer_id,
	NEW.entry_type_id,
	NEW.trans_amount
);

END
$$

DROP TRIGGER IF EXISTS `ledger_transaction_entry_upda`$$

CREATE TRIGGER `ledger_transaction_entry_upda` AFTER UPDATE on `ledger_transaction_entry`
FOR EACH ROW BEGIN
	DECLARE vr_reconsiled_ind int(1);
	DECLARE vr_count int;

	SELECT reconsiled_ind into vr_reconsiled_ind from ledger_entry where ledger_entry_id=NEW.ledger_entry_id;
	SELECT count(*) into vr_count from ledger_balance where ledger_entry_id = NEW.ledger_entry_id;

	IF ((vr_reconsiled_ind = 1) and (vr_count = 0)) THEN
	BEGIN
		CALL sp_ledger_balance (
			NEW.ledger_entry_id,
			NEW.ledger_entity_id,
			NEW.ledger_entity_type_id,
			NEW.originating_buyer_id,
			NEW.entry_type_id,
			NEW.trans_amount
		);
	END;
	END IF;
END$$

DELIMITER ;

