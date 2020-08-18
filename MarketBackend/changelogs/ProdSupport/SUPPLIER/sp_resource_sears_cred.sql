DELIMITER $$

DROP PROCEDURE IF EXISTS `supplier_prod`.`sp_resource_sears_cred`$$

CREATE DEFINER=`supply_usr`@`%` PROCEDURE `sp_resource_sears_cred`()
BEGIN
DECLARE in_debug_level INTEGER DEFAULT 1; 
DECLARE vr_curs_done INTEGER DEFAULT 0; 
DECLARE err_flag TINYINT(1); 
DECLARE wk_count INTEGER DEFAULT 1; 
DECLARE wk_count_formated VARCHAR(30) DEFAULT NULL;
DECLARE wk_result VARCHAR(50) DEFAULT NULL; 
DECLARE wk_pri_resource_id INTEGER(11) DEFAULT NULL; 
DECLARE wk_resource_id INTEGER(11) DEFAULT NULL; 
DECLARE wk_vendor_id INTEGER(11) DEFAULT NULL;
DECLARE wk_phone_no VARCHAR(30) DEFAULT NULL; 
DECLARE wk_fax_no VARCHAR(30) DEFAULT NULL; 
DECLARE wk_email VARCHAR(255) DEFAULT NULL; 
DECLARE wk_complete_flg TINYINT(1) UNSIGNED DEFAULT 0; 
DECLARE wk_terms_cond_id INT(11) DEFAULT 1; 
DECLARE wk_service_provider_cnt INTEGER DEFAULT 0; 
DECLARE wk_hourly_rate DOUBLE(10,2) DEFAULT NULL; 
DECLARE wk_ssn VARCHAR(255) DEFAULT NULL; 
DECLARE wk_locn_id INTEGER DEFAULT NULL; 
DECLARE wk_background_state_id INTEGER DEFAULT NULL; 
DECLARE vr_insert_success INTEGER DEFAULT 0;
DECLARE vr_resource_cred_id INTEGER(10) DEFAULT 0;
DECLARE vr_cur1 CURSOR FOR select vr.vendor_id, vr.resource_id
	from vendor_resource vr
	join vendor_hdr vh ON vr.vendor_id = vh.vendor_id 
	left join resource_credentials rc on vr.resource_id = rc.resource_id
	join vendor_location vl on vl.vendor_id = vh.vendor_id
	join location vloc on vl.locn_id = vloc.locn_id
	join location rloc on vr.locn_id = rloc.locn_id
	where vh.wf_state_id = 3 
	and (rc.cred_type_id IS NULL or rc.cred_type_id != 6) 
	and vloc.locn_type_id = 1 
	and vr.wf_state_id = 6 
	and vloc.state_cd = rloc.state_cd 
	and vr.resource_id NOT IN 
		(select vr.resource_id 
			from vendor_resource vr
			join vendor_hdr vh ON vr.vendor_id = vh.vendor_id and vh.wf_state_id = 3 
			join resource_credentials rc on vr.resource_id = rc.resource_id
			where rc.cred_type_id = 6);
DECLARE EXIT HANDLER FOR SQLEXCEPTION set err_flag = 1;
DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET vr_curs_done = 1; 
set wk_result = 'DEFAULT'; 
set wk_count = 0; 
set err_flag = 0; 
set vr_curs_done = 0; 
set wk_complete_flg = 0;
block1: LOOP
if (in_debug_level > 0) then
	set wk_result = 'DEBUG - BEGIN';
	insert into z_migration_log (result) values (wk_result);
end if;
OPEN vr_cur1;
REPEAT
	FETCH vr_cur1 INTO wk_vendor_id, wk_resource_id;
	
	if (vr_curs_done = 0) then
		if (in_debug_level > 0) then
			set wk_result = 'DEBUG - BEFORE CRED';
			insert into z_migration_log (result, resource_id) values (wk_result, wk_resource_id);
		end if;
		
		INSERT INTO resource_credentials (resource_id, cred_type_id, cred_category_id, cred_source, cred_name, cred_issue_date, cred_expiration_date, cred_no, cred_city, cred_county, cred_state, wf_state_id) 
			VALUES (wk_resource_id, 6, 55,'Sears SPN','Sears',NOW(),ADDDATE(NOW(), INTERVAL 2 YEAR),'1974','Hoffman Estates','Cook','IL',12);	
		select resource_cred_id into vr_resource_cred_id FROM resource_credentials where resource_id = wk_resource_id and cred_type_id = 6 and cred_source = 'Sears SPN' order by created_date desc limit 1;
		if(vr_resource_cred_id > 0)  then
			SET vr_insert_success=1;
		end if;
		if (in_debug_level > 0) then
			set wk_result = 'DEBUG - AFTER CRED';
			insert into z_migration_log (result, resource_id) values (wk_result, wk_resource_id);
		end if;
	
		
		if (vr_insert_success = 1) then
			if (in_debug_level > 0) then
				set wk_result = 'DEBUG - BEFORE INSERT AUDIT_TASK';
				insert into z_migration_log (result, resource_id) values (wk_result, wk_resource_id);
			end if;
		
			insert into audit_task(vendor_id, resource_id, audit_link_id, audit_key_id, document_id, wf_state_id, submission_date, review_date, review_comments, reviewed_by, target_state_id, reason_cd) 
			values (wk_vendor_id,wk_resource_id, 4 ,vr_resource_cred_id, NULL, 12, NOW(),NOW(), NULL, NULL, NULL, NULL);
			 
			if (in_debug_level > 0) then
				set wk_result = 'DEBUG - AFTER INSERT AUDIT_TASK';
				insert into z_migration_log (result, resource_id) values (wk_result, wk_resource_id);
			end if;
		end if;
		SET vr_insert_success=0;
	end if;
UNTIL vr_curs_done END REPEAT;
CLOSE vr_cur1;
LEAVE block1;
END LOOP block1;
if (in_debug_level > 1) then
        set wk_result = 'DEBUG - DONE';
        insert into z_migration_log (resource_id, result) values (wk_pri_resource_id, wk_result); end if; IF err_flag = 1 then
    ROLLBACK;
ELSE
    COMMIT;
   
END IF;
    END$$

DELIMITER ;