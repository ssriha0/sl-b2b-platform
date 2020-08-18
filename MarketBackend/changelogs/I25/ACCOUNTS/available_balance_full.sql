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

DROP TRIGGER IF EXISTS  `ledger_balance_ins`$$

create trigger `ledger_balance_ins` BEFORE INSERT on `ledger_balance` 
for each row set NEW.created_date=NOW(), NEW.modified_date=NOW();
$$

DELIMITER ;

DELIMITER $$

DROP TRIGGER IF EXISTS `ledger_balance_upd`$$

create trigger `ledger_balance_upd` BEFORE UPDATE on `ledger_balance` 
for each row set NEW.modified_date=NOW();
$$

DELIMITER ;


DROP TABLE IF EXISTS ledger_transaction_entry_mirror;
CREATE TABLE `ledger_transaction_entry_mirror` (                                               
                                   `transaction_id` int(10) unsigned NOT NULL default '0',                                      
                                   `transaction_type_id` int(10) unsigned NOT NULL default '0',                                 
                                   `ledger_entry_id` int(10) unsigned NOT NULL default '0',                                     
                                   `ledger_entity_type_id` int(10) unsigned NOT NULL default '0',                               
                                   `created_date` datetime default NULL,                                                        
                                   `modified_date` datetime default NULL,                                                       
                                   `modified_by` varchar(30) default NULL,                                                      
                                   `ledger_entity_id` int(10) unsigned NOT NULL default '0',                                    
                                   `originating_buyer_id` int(10) unsigned default NULL,                                        
                                   `trans_amount` decimal(12,2) NOT NULL default '0.00',                                        
                                   `entry_type_id` int(10) unsigned NOT NULL default '0',                                       
                                   `t_acct_no` int(10) unsigned NOT NULL default '0',                                           
                                   `cc_ind` tinyint(1) unsigned default NULL,                                                   
                                   `account_id` int(10) unsigned default NULL,                                                  
                                   PRIMARY KEY  (`transaction_id`),                                                             
                                   KEY `FK_ledger_transaction_entry_transaction_type_id` (`transaction_type_id`),               
                                   KEY `FK_ledger_transaction_entry_ledger_entity_type_id` (`ledger_entity_type_id`),           
                                   KEY `FK_ledger_transaction_entry_ledger_entry_id` (`ledger_entry_id`),                       
                                   KEY `FK_ledger_transaction_entry_t_acct_no` (`t_acct_no`),                                   
                                   KEY `FK_ledger_transaction_entry_entry_type_id` (`entry_type_id`),                           
                                   KEY `FK_ledger_transaction_entry_account_id` (`account_id`),                                 
                                   KEY `Index_ledger_trans_entry_ledger_entity_id` (`ledger_entity_id`),                        
                                   KEY `ledger_entity_type_id` (`ledger_entity_type_id`),                                       
                                   KEY `IDX_ledger_trans_entry_orig_buyer_id` (`originating_buyer_id`),                         
                                   KEY `IDX_ledge_trans_entry_type_and_buyer` (`ledger_entity_type_id`,`originating_buyer_id`)  
                                 ) ENGINE=InnoDB DEFAULT CHARSET=latin1 ;
                                 
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
	select le.reconsiled_ind, le.ledger_entry_id into vr_reconsiled_ind,  vr_ledger_entry_id
	from ledger_entry le inner join ledger_transaction_rule ltr on ltr.ledger_entry_rule_id=le.ledger_entry_rule_id
	where ltr.debit_entity_type_id != ltr.credit_entity_type_id and le.ledger_entry_id = in_ledgerEntryId;
 
 
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

DELIMITER $$

DROP TRIGGER IF EXISTS `ledger_transaction_entry_mirror_insa`$$

create trigger `ledger_transaction_entry_mirror_insa` AFTER INSERT on `ledger_transaction_entry_mirror` 
FOR EACH ROW BEGIN

CALL sp_ledger_balance (
	NEW.ledger_entry_id,
	NEW.ledger_entity_id,
	NEW.ledger_entity_type_id,
	NEW.originating_buyer_id,
	NEW.entry_type_id,
	NEW.trans_amount
);

END$$
DELIMITER ;

-- fix up the sears one that are still NULL
update ledger_transaction_entry set originating_buyer_id = 1000 where originating_buyer_id is NULL and account_id = 319083913;

truncate table ledger_transaction_entry_mirror;
truncate table ledger_balance;

insert into ledger_transaction_entry_mirror
(
select te.* 
from (ledger_entry le inner join ledger_transaction_entry te 
      on le.ledger_entry_id = te.ledger_entry_id inner join ledger_transaction_rule r
      on le.ledger_entry_rule_id = r.ledger_entry_rule_id) left join supplier_prod.so_hdr s
      on s.so_id = le.so_id 
where s.funding_type_id in (30,40) or le.so_id is null and te.created_date > '2008-06-04 00:00:00'
order by le.created_date, le.so_id, r.sort_order, le.ledger_entry_rule_id, te.entry_type_id

);

