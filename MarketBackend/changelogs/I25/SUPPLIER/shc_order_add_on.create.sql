DROP TABLE IF EXISTS shc_order_add_on;
CREATE TABLE `shc_order_add_on` (                                                                                                              
                    `shc_order_add_on_id` int(10) unsigned NOT NULL auto_increment,                                                                              
                    `shc_order_id` int(10) unsigned default NULL,                                                                                                
                    `specialty_add_on_id` int(10) unsigned default NULL,                                                                                         
                    `retail_price` decimal(9,2) default NULL,                                                                                                    
                    `margin` decimal(9,4) default NULL,                                                                                                          
                    `qty` int(10) unsigned default '0',                                                                                                          
                    `coverage` varchar(2) default 'PT', 
                    `misc_ind` int(1) default '0',                                                                                                               
                    `created_date` datetime default NULL,                                                                                                        
                    `modified_date` datetime default NULL,                                                                                                       
                    `modified_by` varchar(50) default NULL,                                                                                                      
                    PRIMARY KEY  USING BTREE (`shc_order_add_on_id`),                                                                                            
                    KEY `FK_shc_order_id_in_shc_add_on_table` USING BTREE (`shc_order_id`),                                                                      
                    KEY `FK_specialty_add_on_id_in_shc_add_on` USING BTREE (`specialty_add_on_id`),                                                              
                    CONSTRAINT `FK_shc_order_id_in_shc_add_on_table` FOREIGN KEY (`shc_order_id`) REFERENCES `shc_order` (`shc_order_id`),                       
                    CONSTRAINT `FK_specialty_add_on_id_in_shc_add_on` FOREIGN KEY (`specialty_add_on_id`) REFERENCES `specialty_add_on` (`specialty_add_on_id`)  
                  ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
                  
DELIMITER $$
DROP TRIGGER IF EXISTS  `shc_order_add_on_ins`$$
create trigger `shc_order_add_on_ins` BEFORE INSERT on `shc_order_add_on` 
for each row set NEW.created_date=NOW(), NEW.modified_date=NOW();
$$
DELIMITER ;
DELIMITER $$
DROP TRIGGER IF EXISTS `shc_order_add_on_upd`$$
create trigger `shc_order_add_on_upd` BEFORE UPDATE on `shc_order_add_on` 
for each row set NEW.modified_date=NOW();
$$
DELIMITER ;
