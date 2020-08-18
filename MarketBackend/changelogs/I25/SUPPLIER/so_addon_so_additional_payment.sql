drop table if exists so_additional_payment;
CREATE TABLE so_additional_payment (
  so_id varchar(30) NOT NULL,
  additional_payment_desc varchar(30) NOT NULL,
  payment_type varchar(30) NOT NULL,
  payment_amount decimal(9,2) DEFAULT '0.00',
  card_type varchar(20) NOT NULL,
  card_expire_month int(2) DEFAULT NULL,
  card_expire_year int(4) DEFAULT NULL,
  cc_no varchar(64) DEFAULT NULL,
  check_no varchar(64) DEFAULT NULL,
  auth_no varchar(20) default NULL, 
  created_date datetime DEFAULT NULL,
  modified_date datetime DEFAULT NULL,
  modified_by varchar(30) DEFAULT NULL,
  PRIMARY KEY  (so_id),
  CONSTRAINT FK_SO_ID_PAYMENT FOREIGN KEY (so_id) REFERENCES so_hdr (so_id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DELIMITER $$

DROP TRIGGER IF EXISTS  `so_additional_payment_ins`$$

create trigger `so_additional_payment_ins` BEFORE INSERT on `so_additional_payment` 
for each row set NEW.created_date=NOW(), NEW.modified_date=NOW();
$$

DELIMITER ;

DELIMITER $$

DROP TRIGGER IF EXISTS `so_additional_payment_upd`$$

create trigger `so_additional_payment_upd` BEFORE UPDATE on `so_additional_payment` 
for each row set NEW.modified_date=NOW();
$$

DELIMITER ;