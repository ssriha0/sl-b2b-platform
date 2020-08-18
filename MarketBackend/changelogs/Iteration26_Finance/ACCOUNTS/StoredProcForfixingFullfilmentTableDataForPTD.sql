There are two stored procedures below, create both the stored procedures below, then call first stored procedure, which calls second one, 
after running this stored proc run this query to check it should not return any rows , if its ok then delete both the stored procedures after that

SELECT  fullfillment_grp_id FROM fullfillment_entry GROUP BY fullfillment_grp_id , sort_order HAVING count(*) > 1

**************************
DELIMITER $$

DROP PROCEDURE IF EXISTS `fix_fulfilment_table_outer_proc`$$

CREATE DEFINER=`supply_usr`@`%` PROCEDURE `fix_fulfilment_table_outer_proc`()
BEGIN
   -- Declare local variables
DECLARE i_ful_grp_id INT;
DECLARE done BOOLEAN DEFAULT FALSE;
DECLARE c1 CURSOR
FOR	
 SELECT  fullfillment_grp_id FROM fullfillment_entry GROUP BY fullfillment_grp_id , sort_order HAVING count(*) > 1 order by fullfillment_grp_id;
   -- Declare continue handler
   DECLARE CONTINUE HANDLER for not found SET done=TRUE;
   -- Open the cursor
   OPEN c1;
 -- Loop through all rows
   REPEAT
   -- Get order number
   FETCH c1 INTO i_ful_grp_id;
call fix_fulfilment_table_inner_proc( i_ful_grp_id);
 -- End of loop for cursor c1
   UNTIL done END REPEAT;
   -- Close the cursor c1
   CLOSE c1;
END$$

DELIMITER ;

****************************
DELIMITER $$

DROP PROCEDURE IF EXISTS `fix_fulfilment_table_inner_proc`$$

CREATE DEFINER=`supply_usr`@`%` PROCEDURE `fix_fulfilment_table_inner_proc`( IN i_ful_grp_id int)
BEGIN
   -- Declare local variables
DECLARE i_ful_entry_id INT;														
DECLARE i_counter INT DEFAULT 0;
DECLARE done BOOLEAN DEFAULT FALSE;
DECLARE c2 CURSOR
FOR
Select fullfillment_entry_id from fullfillment_entry where fullfillment_grp_id  = i_ful_grp_id order by fullfillment_entry_id;
   -- Declare continue handler
   DECLARE CONTINUE HANDLER for not found SET done=TRUE;
set i_counter = 0;
   -- Open the cursor
   OPEN c2;
 -- Loop through all rows
   REPEAT
FETCH c2 INTO i_ful_entry_id;
Update fullfillment_entry set sort_order = i_counter where fullfillment_entry_id = i_ful_entry_id;
set i_counter = i_counter + 1;
 -- End of loop for cursor c2
   UNTIL done END REPEAT;
   -- Close the cursor c2
   CLOSE c2;
    END$$

DELIMITER ;

