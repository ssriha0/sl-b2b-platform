drop table if exists interim_password;

CREATE TABLE `interim_password` (
  `password` varchar(255)  NOT NULL,
  `user_name` VARCHAR(30)  NOT NULL,
  `valid` TINYINT(3)  NOT NULL,
  `start_time` TIMESTAMP  NOT NULL,
  PRIMARY KEY (`password`)
) ENGINE = InnoDB;


drop trigger if exists interim_password_insa;
create trigger `interim_password_insa` BEFORE INSERT on `interim_password` 
for each row set NEW.start_time=NOW();

ALTER TABLE `user_profile` ADD COLUMN `verification_attempt_count` SMALLINT(5)  NOT NULL DEFAULT 0  AFTER `opt_in`;
