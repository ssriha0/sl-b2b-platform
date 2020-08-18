drop table if exists vendor_w9_128_bit_encrypt;

CREATE TABLE `vendor_w9_128_bit_encrypt` (                          
                             `vendor_id` int(10) unsigned NOT NULL default '0',                
                             `business_name` varchar(100) default NULL,                        
                             `street_1` varchar(40) default NULL,                              
                             `street_2` varchar(40) default NULL,                              
                             `zip` varchar(10) default NULL,                                   
                             `zip4` varchar(10) default NULL,                                  
                             `city` varchar(30) default NULL,                                  
                             `state_cd` varchar(2) default NULL,                               
                             `sb_ind` tinyint(1) default NULL,                                 
                             `sb_woman_owned_ind` tinyint(1) default NULL,                     
                             `sb_veteran_owned_ind` tinyint(1) default NULL,                   
                             `sb_service_disable_owned_ind` tinyint(1) default NULL,           
                             `sb_disadv_ind` tinyint(1) default NULL,                          
                             `sb_hub_zone` tinyint(1) default NULL,                            
                             `minority_owned_ind` tinyint(1) default NULL,                     
                             `minority_type_id` int(10) default NULL,                          
                             `registered_dept_minority_bus` tinyint(1) default NULL,           
                             `cert_no` varchar(50) default NULL,                               
                             `tax_exempt_ind` tinyint(1) default NULL,                         
                             `cert_penalty_ind` tinyint(1) default NULL,                       
                             `created_date` datetime NOT NULL default '0000-00-00 00:00:00',   
                             `modified_date` datetime NOT NULL default '0000-00-00 00:00:00',  
                             `modified_by` varchar(30) default NULL,                           
                             `tax_status_id` int(11) unsigned NOT NULL,                        
                             `ein_no` varchar(64) NOT NULL,                                    
                             `dba_name` varchar(255) default NULL,                             
                             `version_no` int(11) NOT NULL default '1',                        
                             `apt_no` varchar(20) default NULL,                                
                             `archive_date` datetime default NULL,                             
                             PRIMARY KEY  (`vendor_id`),                                       
                             KEY `IDX_vendor_w9_state` (`state_cd`),                            
                             KEY `IDX_vendor_w9_bus_type` (`tax_status_id`)                     
                           ) ENGINE=InnoDB DEFAULT CHARSET=latin1     ;                         



DELIMITER $$

DROP TRIGGER IF EXISTS `vendor_w9_128_bit_encrypt_insb`$$

CREATE

    TRIGGER `vendor_w9_128_bit_encrypt_insb` BEFORE INSERT ON `vendor_w9_128_bit_encrypt` 
    FOR EACH ROW set NEW.archive_date=NOW();
$$

DELIMITER ;