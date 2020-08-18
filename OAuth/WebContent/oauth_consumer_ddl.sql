CREATE TABLE oauth_consumer (  
  consumer_key varchar(50) NOT NULL,
  consumer_secret varchar(50) NOT NULL,
  consumer_name varchar(20) NOT NULL, 
  created_date datetime DEFAULT NULL,
  modified_date datetime DEFAULT NULL,
  modified_by varchar(30) DEFAULT NULL,
  PRIMARY KEY  (consumer_key)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DELIMITER $$

DROP TRIGGER IF EXISTS  `oauth_consumer_ins`$$

create trigger `oauth_consumer_ins` BEFORE INSERT on `oauth_consumer` 
for each row set NEW.created_date=NOW(), NEW.modified_date=NOW();
$$

DELIMITER ;

DELIMITER $$

DROP TRIGGER IF EXISTS `oauth_consumer_upd`$$

create trigger `oauth_consumer_upd` BEFORE UPDATE on `oauth_consumer` 
for each row set NEW.modified_date=NOW();
$$

DELIMITER ;


INSERT INTO oauth_consumer	(consumer_name, consumer_key, consumer_secret)  VALUES('b2c', '4ea7bf2a68d7aef22cf427421aaae9e9', '6f1e2664fac6e88efd750548d98c35be');