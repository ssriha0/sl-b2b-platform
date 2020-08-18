

CREATE TABLE `wfm_queues` (
	`queue_id`			int(10) unsigned	NOT NULL 				COMMENT 'Unique identifier for queue',
	`queue_name`		varchar(80)			NOT NULL				COMMENT 'Name of queue',
	`queue_desc`		varchar(254)		default NULL			COMMENT 'Queue description',
	`destination_tab`	varchar(50)			NOT NULL 				COMMENT 'Destination tab after the task is performed',
	`destination_subtab` varchar(50)		NOT NULL 				COMMENT 'Destination subtab after the task is performed',
	`sort_by_column`	varchar(50)			NOT NULL 				COMMENT 'Default sort field for queue',
	`queue_sort_order`	varchar(4)			NOT NULL default 'ASC' 	COMMENT 'Default sort order for queue',
	PRIMARY KEY  (`queue_id`)
)
ENGINE=InnoDB DEFAULT CHARSET=latin1 
COMMENT='Table that defines all available queues';

CREATE TABLE `wfm_buyer_queues` (
	`buyer_id` 			int(10) unsigned 	NOT NULL 				COMMENT 'Unique identifier for queue',
	`queue_id` 			int(10) unsigned 	NOT NULL 				COMMENT 'Unique identifier for queue',
	`queue_sort_order` 	tinyint(4) 			NOT NULL default 0		COMMENT 'Non-default sort order for queue',
	`active_ind` 		tinyint(4) 			NOT NULL default 0		COMMENT '1 means queue is active for the buyer/admin.  0 is inactive.',
	`visible_ind` 		tinyint(4) 			NOT NULL default 0		COMMENT '1 means queue is visible on the front end.',
	PRIMARY KEY  (`buyer_id`, `queue_id`),
	CONSTRAINT `FK_wfm_bq_buyer_id` FOREIGN KEY (`buyer_id`) REFERENCES `buyer` (`buyer_id`),
	CONSTRAINT `FK_wfm_bq_queue_id` FOREIGN KEY (`queue_id`) REFERENCES `wfm_queues` (`queue_id`)
) 
ENGINE=InnoDB DEFAULT CHARSET=latin1 
COMMENT='Associates queues to buyers and admins';

CREATE TABLE `wfm_tasks` (
	`task_id` 			int(10) unsigned 	NOT NULL auto_increment	COMMENT 'Artificial unique identifier for a task.',
	`queue_id` 			int(10) unsigned 	NOT NULL 				COMMENT 'Unique identifier for queue.',
	`task_code` 		varchar(254) 		NOT NULL 				COMMENT 'The outcome and reason of a task.',
	`task_desc` 		varchar(512) 		default NULL 			COMMENT 'This description used in automatically populated notes table.',
	`task_state` 		varchar(10) 		NOT NULL default 'requeue' COMMENT 'At this point, the two states will be requeue, or end',
	`requeue_hours` 	int(10) unsigned 	NOT NULL default 0		COMMENT 'Default hours that a service order is put into a queue.',
	`requeue_min` 		int(10) unsigned 	NOT NULL default 0		COMMENT 'Default min that a service order is put into a queue.',
	`active_ind` 		tinyint(4) 			NOT NULL default 0		COMMENT '1 means the task is active for the queue.  0 is inactive.',
	`created_date` 		datetime 			NOT NULL 				COMMENT 'Date that task row was added.',
	`modified_date` 	datetime 			NOT NULL 				COMMENT 'Date that task row was modified.',
	PRIMARY KEY  (`task_id`),
	CONSTRAINT `FK_wfm_task_queue_id` FOREIGN KEY (`queue_id`) REFERENCES `wfm_queues` (`queue_id`)
) 
ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1 
COMMENT='Table of tasks, their descriptions and the queues they belon';


CREATE TABLE `wfm_so_queues` (
	`so_queue_id` 		int(10) unsigned 	NOT NULL auto_increment	COMMENT 'Artificial unique identifier for this so-queue',
	`company_id` 		int(10) unsigned 	NOT NULL				COMMENT 'Id of the company who owns this so-queue',
	`queue_id` 			int(10) unsigned 	NOT NULL 				COMMENT 'Unique identifier for queue.',
	`queue_seq` 		tinyint(4) 			NOT NULL default 0 		COMMENT 'Sequence with a queue in case an SO enters the same queue multiple times.',
	`so_id` 			varchar(30) 		NOT NULL 				COMMENT 'Unique identifier for service order.  A service order can belong to more than one queue.',
	`queued_date` 		datetime 			NOT NULL 				COMMENT 'Date that a service order is put into a queue.',
	`claimed_ind` 		tinyint(4) 			NOT NULL default 0		COMMENT '1 means the service order task is claimed.',
	`claimed_date` 		datetime 			default NULL 			COMMENT 'Date that a service order is claimed.',
	`claimed_by_id` 	int(10) unsigned 	default NULL 			COMMENT 'Id of resource who claimed this service order',
	`claimed_from_queue_id` int(10) unsigned default NULL			COMMENT 'Primary queue',
	`unclaimed_date` 	datetime 			default NULL 			COMMENT 'Date that a service order is unclaimed.',
	`completed_ind`		tinyint(4) 			NOT NULL default 0		COMMENT '1 means the service order task is complete.',
	`created_date`		datetime 			NOT NULL 				COMMENT 'Date that a service order is created.',
	`modified_date` 	datetime 			NOT NULL 				COMMENT 'Date that a service order is modified. Updated by a trigger.',
	`grouped_ind` 		tinyint(4)			NOT NULL default 0		COMMENT 'Is 1 if an order is grouped else 0.',
	`so_group_id` 		varchar(30)			default NULL			COMMENT 'Group id',
	`note_id` 			int(10) unsigned 	default NULL			COMMENT 'Reference to the notes for this entry',
	`sort_order` 		int(10) 			NOT NULL default 0		COMMENT 'Primary sort order for SOs in a queue',
	PRIMARY KEY  (`so_queue_id`),
	CONSTRAINT `FK_wfm_soq_so_id` FOREIGN KEY (`so_id`) REFERENCES `so_hdr` (`so_id`),
	CONSTRAINT `FK_wfm_soq_queue_id` FOREIGN KEY (`queue_id`) REFERENCES `wfm_queues` (`queue_id`)
) 
ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1 
COMMENT='Table that connects a service order to possible queues and t';

CREATE TABLE `z_work_wfm_so_queues` (
	`so_queue_id` 		int(10) unsigned 	NOT NULL auto_increment	COMMENT 'Artificial unique identifier for this so-queue',
	`company_id` 		int(10) unsigned 	NOT NULL				COMMENT 'Id of the company who owns this so-queue',
	`queue_id` 			int(10) unsigned 	NOT NULL 				COMMENT 'Unique identifier for queue.',
	`queue_seq` 		tinyint(4) 			NOT NULL default 0 		COMMENT 'Sequence with a queue in case an SO enters the same queue multiple times.',
	`so_id` 			varchar(30) 		NOT NULL 				COMMENT 'Unique identifier for service order.  A service order can belong to more than one queue.',
	`queued_date` 		datetime 			NOT NULL 				COMMENT 'Date that a service order is put into a queue.',
	`claimed_ind` 		tinyint(4) 			NOT NULL default 0		COMMENT '1 means the service order task is claimed.',
	`claimed_date` 		datetime 			default NULL 			COMMENT 'Date that a service order is claimed.',
	`claimed_by_id` 	int(10) unsigned 	default NULL 			COMMENT 'Id of resource who claimed this service order',
	`claimed_from_queue_id` int(10) unsigned default NULL			COMMENT 'Primary queue',
	`unclaimed_date` 	datetime 			default NULL 			COMMENT 'Date that a service order is unclaimed.',
	`completed_ind`		tinyint(4) 			NOT NULL default 0		COMMENT '1 means the service order task is complete.',
	`created_date`		datetime 			NOT NULL 				COMMENT 'Date that a service order is created.',
	`modified_date` 	datetime 			NOT NULL 				COMMENT 'Date that a service order is modified. Updated by a trigger.',
	`grouped_ind` 		tinyint(4)			NOT NULL default 0		COMMENT 'Is 1 if an order is grouped else 0.',
	`so_group_id` 		varchar(30)			default NULL			COMMENT 'Group id',
	`note_id` 			int(10) unsigned 	default NULL			COMMENT 'Reference to the notes for this entry',
	`sort_order` 		int(10) 			NOT NULL default 0		COMMENT 'Primary sort order for SOs in a queue',
	PRIMARY KEY  (`so_queue_id`),
	CONSTRAINT `FK_work_wfm_soq_so_id` FOREIGN KEY (`so_id`) REFERENCES `so_hdr` (`so_id`),
	CONSTRAINT `FK_work_wfm_soq_queue_id` FOREIGN KEY (`queue_id`) REFERENCES `wfm_queues` (`queue_id`)
) 
ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


CREATE TABLE `wfm_so_queues_archive` (
	`archive_id` 		int(10) unsigned 	NOT NULL auto_increment	COMMENT 'Artificial unique identifier.',
	`archive_date`		datetime			NOT NULL,
	`archive_type` 		varchar(10) 		NOT NULL 				COMMENT 'INSERT, UPDATE, DELETE',
	`so_queue_id` 		int(10) unsigned 	NOT NULL 				COMMENT 'Artificial unique identifier for this so-queue',
	`company_id` 		int(10) unsigned 	NOT NULL				COMMENT 'Id of the company who owns this so-queue',
	`queue_id` 			int(10) unsigned 	NOT NULL 				COMMENT 'Unique identifier for queue.',
	`queue_seq` 		tinyint(4) 			NOT NULL default 0 		COMMENT 'Sequence with a queue in case an SO enters the same queue multiple times.',
	`so_id` 			varchar(30) 		NOT NULL 				COMMENT 'Unique identifier for service order.  A service order can belong to more than one queue.',
	`queued_date` 		datetime 			NOT NULL 				COMMENT 'Date that a service order is put into a queue.',
	`claimed_ind` 		tinyint(4) 			NOT NULL default 0		COMMENT '1 means the service order task is claimed.',
	`claimed_date` 		datetime 			default NULL 			COMMENT 'Date that a service order is claimed.',
	`claimed_by_id` 	int(10) unsigned 	default NULL 			COMMENT 'Id of resource who claimed this service order',
	`claimed_from_queue_id` int(10) unsigned default NULL			COMMENT 'Primary queue',
	`unclaimed_date` 	datetime 			default NULL 			COMMENT 'Date that a service order is unclaimed.',
	`completed_ind`		tinyint(4) 			NOT NULL default 0		COMMENT '1 means the service order task is complete.',
	`created_date`		datetime 			NOT NULL 				COMMENT 'Date that a service order is created.',
	`modified_date` 	datetime 			NOT NULL 				COMMENT 'Date that a service order is modified. Updated by a trigger.',
	`grouped_ind` 		tinyint(4)			NOT NULL default 0		COMMENT 'Is 1 if an order is grouped else 0.',
	`so_group_id` 		varchar(30)			default NULL			COMMENT 'Group id',
	`note_id` 			int(10) unsigned 	default NULL			COMMENT 'Reference to the notes for this entry',
	`sort_order` 		int(10) 			NOT NULL default 0		COMMENT 'Primary sort order for SOs in a queue',
	PRIMARY KEY  (`archive_id`)
) 
ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
