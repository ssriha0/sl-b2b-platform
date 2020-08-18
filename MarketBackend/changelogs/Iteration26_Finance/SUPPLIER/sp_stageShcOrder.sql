DELIMITER $$

DROP PROCEDURE IF EXISTS `sp_stageShcOrder`$$

CREATE PROCEDURE `sp_stageShcOrder`
(
    IN in_SoId VARCHAR(50)
)
main: BEGIN

  DECLARE done INT DEFAULT 0;
  DECLARE l_tmp_done INT;

/*
***********************************************************
    Create shc_order record
***********************************************************
*/

  DECLARE l_shc_order_id INT(10);
  DECLARE l_order_no VARCHAR(30);
  DECLARE l_unit_no VARCHAR(30);
  DECLARE l_sales_check VARCHAR(30);
  DECLARE l_sales_date VARCHAR(30);

  DECLARE l_taskName VARCHAR(255);
  DECLARE l_sku VARCHAR(5);
  DECLARE l_shc_ordersku_id INT(10);
  DECLARE l_skuRatio DECIMAL(6,4);
  DECLARE l_skuCount INT(2);
  DECLARE l_PermitInd INT(1);
  DECLARE l_skuType CHAR(1);

  DECLARE l_shc_merch_id INT(10);

  DECLARE cur1 CURSOR FOR 
	select task_name from so_tasks where so_id = in_SoId order by sort_order;

  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

  -- Order must be a valid service order, shc funding type and cannot already have staging information
  IF ((select count(*) from so_hdr where so_id = in_SoId and funding_type_id = 40) = 0) THEN LEAVE main; END IF;
  IF ((select count(*) from shc_order where so_id = in_SoId) > 0) THEN LEAVE main; END IF;

  START TRANSACTION;

  -- get order number from so
  select left(cr.buyer_ref_value,30) into l_order_no from so_custom_reference cr inner join buyer_reference_type br on cr.buyer_ref_type_id=br.buyer_ref_type_id
  where cr.so_id = in_SoId and br.ref_type like 'ORDER NUMBER';
  IF (l_order_no is null) THEN 
	SET l_order_no = concat('SO',in_SoId);
  END IF;

  -- get unit number from so
  select left(cr.buyer_ref_value,7) into l_unit_no from so_custom_reference cr inner join buyer_reference_type br on cr.buyer_ref_type_id=br.buyer_ref_type_id
  where cr.so_id = in_SoId and br.ref_type like 'UNIT NUMBER';
  IF (l_unit_no is null) THEN 
	SET l_unit_no = '54584';
  END IF;

  -- get sales check number from so
  select left(cr.buyer_ref_value,30) into l_sales_check from so_custom_reference cr inner join buyer_reference_type br on cr.buyer_ref_type_id=br.buyer_ref_type_id
  where cr.so_id = in_SoId and br.ref_type like 'SALES CHECK NUM 1';

  -- get sales check date from so
  select left(cr.buyer_ref_value,10) into l_sales_date from so_custom_reference cr inner join buyer_reference_type br on cr.buyer_ref_type_id=br.buyer_ref_type_id
  where cr.so_id = in_SoId and br.ref_type like 'SALES CHECK DATE 1';

  select max(shc_order_id) + 1 into l_shc_order_id from shc_order;

  insert into shc_order (shc_order_id, order_no, unit_no, store_no, so_id, wf_state_id, nps_process_id, 
			sales_check_num, sales_check_date, processed_ind, gl_process_id, created_date, 
			modified_date, modified_by, completed_date, routed_date, resolution_descr)
  select l_shc_order_id, l_order_no, l_unit_no, left(l_sales_check,5), in_SoId, NULL, NULL, 
	l_sales_check, l_sales_date, NULL, NULL, now(), 
	now(), 'sp_stageShcOrder', NULL, NULL, NULL;

/*
***********************************************************
    Create shc_order_shu records
***********************************************************
*/

  SET l_skuCount = 0;

  OPEN cur1;

  REPEAT
	FETCH cur1 INTO l_taskName;
	    SET l_tmp_done = done;
	    SET l_shc_ordersku_id = NULL;
	    SET l_sku = left(l_taskName,5);

	    select shc_order_sku_id into l_shc_ordersku_id from shc_order_sku where sku = l_sku and shc_order_id = l_shc_order_id;
	    IF (l_shc_ordersku_id is null) THEN
		SET l_skuRatio = 0.0000;
		SET l_PermitInd = 0;
		SET l_skuType = 'N';
		IF (l_skuCount = 0) THEN 
			SET l_skuRatio = 1.0000; 
			SET l_skuType = 'R'; 
		END IF;
		IF (l_sku = '99888') THEN 
			SET l_PermitInd = 1;
		END IF;

		select max(shc_order_sku_id) + 1 into l_shc_ordersku_id from shc_order_sku;

		insert into shc_order_sku (shc_order_sku_id, sku, shc_order_id, add_on_ind, initial_bid_price, price_ratio, 
				final_price, retail_price, selling_price, service_fee, charge_code, coverage, `type`, permit_sku_ind, 
				created_date, modified_date, modified_by, qty, `status`, description)
		select l_shc_ordersku_id, l_sku, l_shc_order_id, 0, 0.00, l_skuRatio, 
				NULL, NULL, NULL, NULL, 'Z', 'PT', l_skuType, l_PermitInd, 
				now(), now(), 'sp_stageShcOrder', 1, NULL, NULL;

	    END IF;
	    SET l_skuCount = l_skuCount + 1;
	    SET done = l_tmp_done;

	UNTIL done END REPEAT;
  CLOSE cur1;


/*
***********************************************************
    Create shc_merchandise record
***********************************************************
*/

  select max(shc_merchandise_id) + 1 into l_shc_merch_id from shc_merchandise;

  insert into shc_merchandise (shc_merchandise_id, shc_order_id, item_no, `code`, description, specialty, brand, model, 
	division_code, created_date, modified_date, modified_by, serial_number)
  select l_shc_merch_id, l_shc_order_id, part_id, NULL, left(part_descr,255), NULL, manufacturer, model_no, 
	NULL, now(), now(), 'sp_stageShcOrder', NULL
  from so_parts where so_id = in_SoId;

  COMMIT;

END$$


DELIMITER ;