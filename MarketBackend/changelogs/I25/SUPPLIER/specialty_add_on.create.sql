DROP TABLE IF EXISTS specialty_add_on;
CREATE TABLE `specialty_add_on` (                                                    
                    `specialty_add_on_id` int(10) unsigned NOT NULL auto_increment,                    
                    `specialty_code` varchar(10) NOT NULL,             
                    `specialty_description` varchar(25) default NULL,             
                    `major_heading_code` int(10) unsigned default NULL,                                
                    `major_heading_description` varchar(50) default NULL,         
                    `sub_heading_code` int(10) unsigned default NULL,                                  
                    `sub_heading_description` varchar(50) default NULL,           
                    `classification_code` varchar(10) default NULL,               
                    `classification_description` varchar(25) default NULL,        
                    `coverage` varchar(2) default NULL,                           
                    `coverage_description` varchar(25) default NULL,              
                    `job_code_suffix` varchar(1) default NULL,                     
                    `stock_number` varchar(5) NOT NULL,                
                    `job_code_descripton` varchar(25) default NULL,               
                    `long_description` varchar(100) default NULL,                 
                    `inclusion_description` text character set latin1,                                 
                    `sequence` int(10) unsigned default NULL,                                          
                    `contractor_cost_type` varchar(10) default NULL,              
                    `contractor_cost_type_description` varchar(25) default NULL,  
                    `dispatch_days_out` int(10) unsigned default NULL,                                 
                    `active_date` date default NULL,                                                   
                    `update_date` date default NULL,                                                   
                    `deactive_date` date default NULL,                                                 
                    `created_date` datetime default NULL,                                              
                    `modified_date` datetime default NULL,                                             
                    `modified_by` varchar(30) default NULL,                       
                    PRIMARY KEY  (`specialty_add_on_id`),                                              
                    UNIQUE KEY `UNDX_specialty_code-stock_number` (`specialty_code`,`stock_number`)    
                  ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
                  

DELIMITER $$
DROP TRIGGER IF EXISTS  `specialty_add_on_ins`$$
create trigger `specialty_add_on_ins` BEFORE INSERT on `specialty_add_on` 
for each row set NEW.created_date=NOW(), NEW.modified_date=NOW();
$$
DELIMITER ;
DELIMITER $$
DROP TRIGGER IF EXISTS `specialty_add_on_upd`$$
create trigger `specialty_add_on_upd` BEFORE UPDATE on `specialty_add_on` 
for each row set NEW.modified_date=NOW();
$$
DELIMITER ;