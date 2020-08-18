ALTER TABLE `specialty_add_on` 
ADD COLUMN `mark_up_percentage` DECIMAL(9,4) DEFAULT 0.4
AFTER `dispatch_days_out`;
