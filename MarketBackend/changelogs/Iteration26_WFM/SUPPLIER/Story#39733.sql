DROP TABLE IF EXISTS active_session_audit;

CREATE TABLE `active_session_audit` (
  `active_session_audit_id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `resource_id` int(11) NOT NULL,
  `company_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  `login_time` datetime NOT NULL,
  PRIMARY KEY  (`active_session_audit_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS audit_user_profile;

CREATE TABLE `audit_user_profile` (
  `audit_user_profile_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `login_resource_id` int(11) NOT NULL,
  `login_company_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  `isSLAdminInd` tinyint(4) NOT NULL DEFAULT '0',
  `action_performed` varchar(100) NOT NULL,
  `user_profile_data` longblob,
  `modified_by` varchar(20) NOT NULL,
  `modified_time` datetime NOT NULL,
  PRIMARY KEY  (`audit_user_profile_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS login_audit;

CREATE TABLE `login_audit` (
  `login_audit_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `resource_id` int(11) NOT NULL,
  `company_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  `login_time` datetime DEFAULT NULL,
  `logout_time` datetime DEFAULT NULL,
  `session_timeout_ind` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY  (`login_audit_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

commit;