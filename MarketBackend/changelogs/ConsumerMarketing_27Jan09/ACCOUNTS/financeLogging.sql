CREATE TABLE `nmm_response_logging` (
  `nmm_response_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `fullfillment_entry_id` varchar(20) DEFAULT NULL,
  `response_string` varchar(5000) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY  (`nmm_response_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `balance_inquiry_response_logging` (
  `balance_inquiry_response_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `fullfillment_entry_id` varchar(20) DEFAULT NULL,
  `response_string` varchar(5000) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY  (`balance_inquiry_response_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

