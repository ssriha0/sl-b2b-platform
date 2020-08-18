ALTER TABLE so_price
 ADD final_service_fee DECIMAL(9,2)  DEFAULT '0'  AFTER modified_by;

 
ALTER TABLE so_addon
 ADD service_fee_final DECIMAL(9,2)  DEFAULT '0'  AFTER scope_of_work;

