

DELIMITER $$

DROP TRIGGER IF EXISTS  `shc_error_logging_ins`$$

create trigger `shc_error_logging_ins` BEFORE INSERT on `shc_error_logging` 
for each row set NEW.created_date=NOW(), NEW.modified_date=NOW();
$$



DROP TRIGGER IF EXISTS `shc_error_logging_upd`$$

create trigger `shc_error_logging_upd` BEFORE UPDATE on `shc_error_logging` 
for each row set NEW.modified_date=NOW();
$$

DROP TRIGGER IF EXISTS  `shc_lu_prs_unit_loc_ins`$$

create trigger `shc_lu_prs_unit_loc_ins` BEFORE INSERT on `shc_lu_prs_unit_loc` 
for each row set NEW.created_date=NOW(), NEW.modified_date=NOW();
$$



DROP TRIGGER IF EXISTS `shc_lu_prs_unit_loc_upd`$$

create trigger `shc_lu_prs_unit_loc_upd` BEFORE UPDATE on `shc_lu_prs_unit_loc` 
for each row set NEW.modified_date=NOW();
$$

DROP TRIGGER IF EXISTS  `shc_lu_prs_unit_loc_ins`$$

create trigger `shc_lu_prs_unit_loc_ins` BEFORE INSERT on `shc_lu_prs_unit_loc` 
for each row set NEW.created_date=NOW(), NEW.modified_date=NOW();
$$


DROP TRIGGER IF EXISTS `shc_lu_prs_unit_loc_upd`$$

create trigger `shc_lu_prs_unit_loc_upd` BEFORE UPDATE on `shc_lu_prs_unit_loc` 
for each row set NEW.modified_date=NOW();
$$

DROP TRIGGER IF EXISTS  `shc_nps_process_log_ins`$$

create trigger `shc_nps_process_log_ins` BEFORE INSERT on `shc_nps_process_log` 
for each row set NEW.created_date=NOW(), NEW.modified_date=NOW();
$$



DROP TRIGGER IF EXISTS `shc_nps_process_log_upd`$$

create trigger `shc_nps_process_log_upd` BEFORE UPDATE on `shc_nps_process_log` 
for each row set NEW.modified_date=NOW();
$$

DROP TRIGGER IF EXISTS  `shc_order_ins`$$

create trigger `shc_order_ins` BEFORE INSERT on `shc_order` 
for each row set NEW.created_date=NOW(), NEW.modified_date=NOW();
$$


DROP TRIGGER IF EXISTS `shc_order_upd`$$

create trigger `shc_order_upd` BEFORE UPDATE on `shc_order` 
for each row set NEW.modified_date=NOW();
$$

DROP TRIGGER IF EXISTS  `shc_order_ins`$$

create trigger `shc_order_ins` BEFORE INSERT on `shc_order` 
for each row set NEW.created_date=NOW(), NEW.modified_date=NOW();
$$



DROP TRIGGER IF EXISTS `shc_order_upd`$$

create trigger `shc_order_upd` BEFORE UPDATE on `shc_order` 
for each row set NEW.modified_date=NOW();
$$

DROP TRIGGER IF EXISTS  `shc_order_add_on_ins`$$

create trigger `shc_order_add_on_ins` BEFORE INSERT on `shc_order_add_on` 
for each row set NEW.created_date=NOW(), NEW.modified_date=NOW();
$$



DROP TRIGGER IF EXISTS `shc_order_add_on_upd`$$

create trigger `shc_order_add_on_upd` BEFORE UPDATE on `shc_order_add_on` 
for each row set NEW.modified_date=NOW();
$$

DROP TRIGGER IF EXISTS  `shc_order_sku_ins`$$

create trigger `shc_order_sku_ins` BEFORE INSERT on `shc_order_sku` 
for each row set NEW.created_date=NOW(), NEW.modified_date=NOW();
$$



DROP TRIGGER IF EXISTS `shc_order_sku_upd`$$

create trigger `shc_order_sku_upd` BEFORE UPDATE on `shc_order_sku` 
for each row set NEW.modified_date=NOW();
$$

DROP TRIGGER IF EXISTS  `shc_order_transaction_ins`$$

create trigger `shc_order_transaction_ins` BEFORE INSERT on `shc_order_transaction` 
for each row set NEW.created_date=NOW(), NEW.modified_date=NOW();
$$


DROP TRIGGER IF EXISTS `shc_order_transaction_upd`$$

create trigger `shc_order_transaction_upd` BEFORE UPDATE on `shc_order_transaction` 
for each row set NEW.modified_date=NOW();
$$

DROP TRIGGER IF EXISTS  `shc_sku_account_assoc_ins`$$

create trigger `shc_sku_account_assoc_ins` BEFORE INSERT on `shc_sku_account_assoc` 
for each row set NEW.created_date=NOW(), NEW.modified_date=NOW();
$$


DROP TRIGGER IF EXISTS `shc_sku_account_assoc_upd`$$

create trigger `shc_sku_account_assoc_upd` BEFORE UPDATE on `shc_sku_account_assoc` 
for each row set NEW.modified_date=NOW();
$$

DROP TRIGGER IF EXISTS  `shc_specialty_sku_ins`$$

create trigger `shc_specialty_sku_ins` BEFORE INSERT on `shc_specialty_sku` 
for each row set NEW.created_date=NOW(), NEW.modified_date=NOW();
$$


DROP TRIGGER IF EXISTS `shc_specialty_sku_upd`$$

create trigger `shc_specialty_sku_upd` BEFORE UPDATE on `shc_specialty_sku` 
for each row set NEW.modified_date=NOW();
$$


DROP TRIGGER IF EXISTS  `shc_upsell_payment_ins`$$

create trigger `shc_upsell_payment_ins` BEFORE INSERT on `shc_upsell_payment` 
for each row set NEW.created_date=NOW(), NEW.modified_date=NOW();
$$


DROP TRIGGER IF EXISTS `shc_upsell_payment_upd`$$

create trigger `shc_upsell_payment_upd` BEFORE UPDATE on `shc_upsell_payment` 
for each row set NEW.modified_date=NOW();
$$

DELIMITER ;