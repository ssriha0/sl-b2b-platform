CREATE TABLE `public_api_security` (    
                       `user_id` varchar(30) NOT NULL,       
                       `api_key` varchar(80) default NULL,   
                       PRIMARY KEY  (`user_id`)              
                     );