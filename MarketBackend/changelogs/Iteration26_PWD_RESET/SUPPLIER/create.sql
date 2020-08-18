drop table if exists interim_password;

CREATE TABLE `interim_password` (
  `password` varchar(255)  NOT NULL,
  `user_name` VARCHAR(30)  NOT NULL,
  `valid` TINYINT(3)  NOT NULL,
  `start_time` TIMESTAMP  NOT NULL,
  PRIMARY KEY (`password`)
) ENGINE = InnoDB;


create trigger `interim_password_insa` BEFORE INSERT on `interim_password` 
for each row set NEW.start_time=NOW();


ALTER TABLE `user_profile` ADD COLUMN `verification_attempt_count` SMALLINT(5)  NOT NULL DEFAULT 0  AFTER `opt_in`;

insert into application_properties (app_key, app_value, app_desc, created_date, modified_date, modified_by)values('max_secret_question_attempts_limit', 3, 'Number of unsuccessful secret quesion attempts that cause the user account to get locked', null, null, 'shekhar');

insert into lu_permissions values('40' , 'Password reset for all external users',  0, 35, 'SL Admin', '2009-02-18 15:00:25', '2009-02-18 15:00:25', 'cnirkhe');
insert into lu_permission_set values(31,  2,  'Manage Password Reset',  1);

insert into lu_permission_role values(82, 2,  40, 51, '2009-02-18 15:00:25', '2009-02-18 15:00:25' ,'cnirkhe', 31);  

insert into user_profile_permissions values('sladmin_super', 82, '2009-02-18 15:00:25', '2009-02-18 15:00:25' ,'cnirkhe');

insert into application_properties (app_key, app_value, app_desc, created_date, modified_date, modified_by)values('interim_pwd_expiration_time', 604800000, 'Expiration time for the interim password', null, null, 'ndixit');
insert into application_properties (app_key, app_value, app_desc, created_date, modified_date, modified_by)values('account_lock_time', 1800000, 'Locking time currrently set to 30 mins', null, null, 'ndixit');

insert into lu_action_master values(239, 'resetPassword', null, 'Reset Password Request', null, null,null, 'cnirkhe'); 
insert into lu_aop_action_assoc values(540, 1, 239, null, null, 'cnirkhe');
insert into lu_aop_action_target_assoc values(239, 540, null, 74, null, null, 'cnirkhe');
