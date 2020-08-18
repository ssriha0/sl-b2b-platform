DROP TABLE IF EXISTS `shc_nps_close_process_log`;

CREATE TABLE `shc_nps_process_log` (
		`nps_process_id` int(10) unsigned NOT NULL auto_increment,
		`file_name` varchar(75) NOT NULL,
		`created_date` datetime,
		`modified_date` datetime,
		`modified_by` varchar(50),
		PRIMARY KEY  (`nps_process_id`)
);
