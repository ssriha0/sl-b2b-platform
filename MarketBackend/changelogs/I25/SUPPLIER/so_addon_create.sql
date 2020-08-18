DROP TABLE IF EXISTS so_addon;
CREATE TABLE `so_addon` (
`so_id` varchar(30) NOT NULL,
`sku` varchar(50) NOT NULL,
`description` varchar(250) DEFAULT NULL,
`retail_price` decimal(9,2) DEFAULT '0.00',
`qty` int(10) UNSIGNED DEFAULT '0',
`coverage` varchar(2) DEFAULT 'PT', 
`margin` decimal(9,2) UNSIGNED DEFAULT NULL,
`misc_ind` tinyint(1) UNSIGNED DEFAULT '0',
`created_date` datetime DEFAULT NULL,
`modified_date` datetime DEFAULT NULL,
`modified_by` varchar(30) DEFAULT NULL,
`scope_of_work` text CHARACTER SET latin1 COLLATE latin1_general_ci,
PRIMARY KEY (`so_id`,`sku`),
CONSTRAINT `FK_SOID_ADDON` FOREIGN KEY (`so_id`) REFERENCES `so_hdr` (`so_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DELIMITER $$

DROP TRIGGER  IF EXISTS so_addon_date_trigger$$

CREATE
    TRIGGER `so_addon_date_trigger` BEFORE INSERT ON `so_addon` 
    FOR EACH ROW
   set NEW.created_date=NOW(), NEW.modified_date=NOW();
$$

DELIMITER ;

DELIMITER $$

DROP TRIGGER IF EXISTS so_addon_upd$$

create trigger `so_addon_upd` BEFORE UPDATE on `so_addon` 
for each row set NEW.modified_date=NOW();
$$

DELIMITER ;