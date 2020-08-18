DROP TABLE IF EXISTS `shc_upsell_payment`;

CREATE TABLE `shc_upsell_payment` (                                                                          
		`shc_upsell_payment_id` int(10) unsigned NOT NULL auto_increment,                                          
		`shc_order_id` int(10) unsigned default NULL,                                                           
		`payment_method_ind` varchar(2) NOT NULL ,
		`payment_acc_no` varchar(16) NOT NULL,
		`payment_exp_date`  varchar(4),
		`auth_no` smallint(3),
		`sec_payment_method_ind`  varchar(2),
		`sec_payment_acc_no` varchar(16),
		`sec_payment_exp_date` varchar(4),
		`sec_auth_no` smallint(3),
		`amount_collected` double,
		`pri_amount_collected` double,
		`sec_amount_collected` double,
		`created_date` datetime,                                                                   
		`modified_date` datetime,                                                                  
		`modified_by` varchar(50),                                                                 
		PRIMARY KEY  (`shc_upsell_payment_id`),                                                                    
		UNIQUE KEY `AK_shc_upsell_payment` (`shc_order_id`),                                                  
		CONSTRAINT `FK_shc_upsell_payment_shc_order_id` FOREIGN KEY (`shc_order_id`) REFERENCES `shc_order` (`shc_order_id`)  
);
