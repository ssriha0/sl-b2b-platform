DELIMITER $$

DROP TRIGGER IF EXISTS `shc_nps_process_log_ins` $$

create trigger `shc_nps_process_log_ins` BEFORE INSERT on `shc_nps_process_log` 
for each row set NEW.created_date=NOW(), NEW.modified_date=NOW();
$$

DELIMITER ;

DELIMITER $$

DROP TRIGGER IF EXISTS `shc_nps_process_log_upd`$$

create trigger `shc_nps_process_log_upd` BEFORE UPDATE on `shc_nps_process_log` 
for each row set NEW.modified_date=NOW();
$$

DELIMITER ;