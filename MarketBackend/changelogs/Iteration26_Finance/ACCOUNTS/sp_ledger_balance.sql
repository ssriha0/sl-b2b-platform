DELIMITER $$

DROP PROCEDURE IF EXISTS `sp_ledger_balance`$$

CREATE PROCEDURE `sp_ledger_balance`(
	IN in_ledgerEntryId int(10),
	IN in_ledgerEntityId int(10),
	IN in_ledgerEntityTypeId int(1),
	IN in_originatingBuyerId int(10),
	IN in_entryTypeId int(1),
	IN in_transAmount decimal(12,2)
)
BEGIN

	DECLARE vr_ledger_entry_id int(10);
	DECLARE vr_entity_id int(10);
	DECLARE vr_entity_type_id int(1);
	DECLARE vr_reconsiled_ind int(1);
	DECLARE vr_cr_db_ind int(1);
	DECLARE vr_funding_type_id int(1);
	DECLARE vr_available_balance decimal(12,2);
	DECLARE vr_project_balance decimal(12,2);
	DECLARE vr_ledger_balance_id int(11);
	DECLARE vr_new_availbalance decimal(12,2);
	DECLARE vr_new_projbalance decimal(12,2);
	DECLARE vr_balance_affected_ind varchar(25);

	/* Get reconsiled_ind and ledger_entry_id from associated ledger_entry, BUT only on those ledger_entries where the debit 
	   and credit ledger types are different. (Ignore net zero types of transactions) 
		TODO: add vr_funding_type_id to the ledger_entry table and populate
	*/ 
	select reconsiled_ind, ledger_entry_id into vr_reconsiled_ind,  vr_ledger_entry_id
	from ledger_entry where affects_balance = 1 and ledger_entry_id = in_ledgerEntryId;

 
/* START DEBUG */
-- SELECT CONCAT('vr_ledger_entry_id=', vr_ledger_entry_id);
-- SELECT CONCAT('vr_reconsiled_ind=', vr_reconsiled_ind);
/* END DEBUG */

	/* Perform available/project balance calculation if we are working with a valid, reconciled transaction */
	IF (vr_ledger_entry_id IS NOT NULL) AND (vr_reconsiled_ind = 1) THEN
	BEGIN
		/* Determine the entity id of transaction */
		SET vr_entity_id = in_ledgerEntityId;
		SET vr_entity_type_id = in_ledgerEntityTypeId;
		IF (in_ledgerEntityTypeId = 30) THEN 
			SET vr_entity_id = in_originatingBuyerId;
			SET vr_entity_type_id = 10;
		END IF;

/* START DEBUG */
-- SELECT CONCAT('vr_entity_id=', vr_entity_id);
/* END DEBUG */

		/* Get the last available and project balance for the given entity */
		select ifnull(ledger_balance_id,0), ifnull(ledger_entry_id,0), ifnull(available_balance,0), ifnull(project_balance,0) 
			into vr_ledger_balance_id, vr_ledger_entry_id, vr_available_balance, vr_project_balance  
		from ledger_balance where ledger_entity_id = vr_entity_id and ledger_entity_type_id= vr_entity_type_id 
		order by ledger_balance_id desc limit 1;

		IF (vr_ledger_balance_id IS NULL) THEN
			SET vr_ledger_balance_id = 0;
			SET vr_ledger_entry_id = 0;
			SET vr_available_balance = 0;
			SET vr_project_balance = 0;
			SET vr_balance_affected_ind = 'none';
		END IF;

/* START DEBUG */
-- SELECT CONCAT('ledger_balance_id=', vr_ledger_balance_id);
-- SELECT CONCAT('ledger_entry_id=', vr_ledger_entry_id);
-- SELECT CONCAT('available_balance=', vr_available_balance);
-- SELECT CONCAT('project_balance=', vr_project_balance);
/* END DEBUG */

		/* Determine the sign of the transaction (debit/credit) */
		SET vr_cr_db_ind = 1;
		IF (in_entryTypeId = 1) THEN set vr_cr_db_ind = -1;
		END IF;

		/* Determine new project balance if we are acting on the ESCROW LEDGER (type 30) 
		   Otherwise we are determining an available balance */
		IF (in_ledgerEntityTypeId = 30) THEN
			SET vr_project_balance = vr_project_balance + (in_transAmount * vr_cr_db_ind);
			SET vr_balance_affected_ind = 'project';
		ELSE
			SET vr_available_balance = vr_available_balance + (in_transAmount * vr_cr_db_ind);
			SET vr_balance_affected_ind = 'available';
		END IF;

/* START DEBUG */
-- SELECT CONCAT('New Project=', vr_project_balance);
-- SELECT CONCAT('New Available=', vr_available_balance);
-- SELECT CONCAT('Ledger Entry ID=', in_ledgerEntryId);
/* END DEBUG */

		/* If we already established a balance for the current ledger_entry, then update it, 
		   Otherwise create it */
		IF (vr_ledger_entry_id = in_ledgerEntryId) THEN

/* START DEBUG */
-- SELECT CONCAT('UPDATING BALANCE: ', vr_ledger_balance_id);
/* END DEBUG */

			UPDATE ledger_balance SET available_balance=vr_available_balance, project_balance=vr_project_balance, balance_affected_ind = 'both'
			WHERE ledger_balance_id = vr_ledger_balance_id;
		ELSE

/* START DEBUG */
-- SELECT CONCAT('INSERTING NEW BALANCE');
/* END DEBUG */

			INSERT INTO ledger_balance (ledger_entry_id,ledger_entity_type_id,ledger_entity_id,available_balance,project_balance,balance_affected_ind) 
			VALUES( in_ledgerEntryId,vr_entity_type_id,vr_entity_id,vr_available_balance,vr_project_balance, vr_balance_affected_ind);
		END IF;

	END;
	END IF;

END$$

DELIMITER ;
