DELIMITER $$

DROP TRIGGER /*!50114 IF EXISTS */ `so_notes_insb`$$

create trigger `so_notes_insb` BEFORE INSERT on `so_notes` 
for each row BEGIN
  IF NEW.created_date is NULL THEN
    SET NEW.created_date=NOW(), NEW.modified_date=NOW();
  END IF;
  IF NEW.modified_date is NULL THEN
    SET NEW.modified_date=NOW();
  END IF;
  IF NEW.entity_id is NULL THEN
    SET NEW.entity_id=0;
  END IF;
END;
$$

DELIMITER ;

DELIMITER $$

DROP TRIGGER /*!50114 IF EXISTS */ `so_notes_updb`$$

create trigger `so_notes_updb` BEFORE UPDATE on `so_notes` 
for each row BEGIN
  IF NEW.modified_date is NULL THEN
    SET NEW.modified_date=NOW();
  END IF;
  IF NEW.entity_id is NULL THEN
    SET NEW.entity_id=0;
  END IF;
END;
$$

DELIMITER ;