CREATE TABLE `fullfillment_admin_tool_log` (
  `fullfillment_admin_log_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `fullfillment_entry_id` int(10) UNSIGNED NOT NULL,
  `user_name` varchar(50) DEFAULT NULL,
  `comments` varchar(1000) DEFAULT NULL,
  `created_timestamp` datetime DEFAULT NULL,
  PRIMARY KEY  (`fullfillment_admin_log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `message_queue` (
  `queue_name` varchar(30) NOT NULL,
  `last_put_time` datetime DEFAULT NULL,
  `last_transmitted_time` datetime DEFAULT NULL,
  `queue_depth` int(11) DEFAULT NULL,
  `current_pan` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

insert into message_queue (queue_name,queue_depth) values ('queue_1',0);
insert into message_queue (queue_name,queue_depth) values ('queue_2',0);
insert into message_queue (queue_name,queue_depth) values ('queue_3',0);


INSERT   INTO lu_fullfillment_action_codes
              (action_code
               ,action_desc
               ,status)
   VALUES (   
          ''
          ,'Unmapped action code'
          ,0
       );


