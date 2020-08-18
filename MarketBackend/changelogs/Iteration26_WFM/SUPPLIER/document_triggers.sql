DELIMITER $$

DROP TRIGGER  IF EXISTS  `document_insb`$$

create trigger `document_insb` BEFORE INSERT on `document` 
for each row set NEW.created_date=NOW(), NEW.modified_date=NOW();
$$

DELIMITER ;

DELIMITER $$

DROP TRIGGER IF EXISTS  `document_updb`$$

create trigger `document_updb` BEFORE UPDATE on `document` 
for each row set NEW.modified_date=NOW();
$$

DELIMITER ;