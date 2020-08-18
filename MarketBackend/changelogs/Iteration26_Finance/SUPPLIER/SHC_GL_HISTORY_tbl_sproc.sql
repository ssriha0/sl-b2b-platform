DELIMITER $$

DROP TABLE IF EXISTS `shc_gl_history`$$

CREATE TABLE shc_gl_history ( 
	gl_process_id INT(10), 
	shc_order_sku_id INT(10), 
	shc_order_id INT(10), 
	so_id VARCHAR(30), 
	order_no VARCHAR(30), 
	unit_no VARCHAR(30), 
	created_date DATETIME,
	gl_account INT(10), 
	location VARCHAR(30), 
	final_price DECIMAL(9,2), 
	service_fee DECIMAL(9,2), 
	PRIMARY KEY  (`gl_process_id`,`shc_order_sku_id`)
)$$

-- Inserts into shc_gl_history
DROP PROCEDURE IF EXISTS `sp_SHCGeneralLedgerHistory`$$

CREATE PROCEDURE sp_SHCGeneralLedgerHistory
(
	IN in_GLProcessID INT(10)
)
BEGIN

	DROP TABLE IF EXISTS tblSHCGLView1;
	DROP TABLE IF EXISTS tblSHCGLView2;
	DROP TABLE IF EXISTS tblSHCGLView3;

	CREATE TEMPORARY TABLE tblSHCGLView1 ( 
		gl_process_id INT(10), 
		shc_order_sku_id INT(10), 
		shc_order_id INT(10), 
		so_id VARCHAR(30), 
		order_no VARCHAR(30), 
		unit_no VARCHAR(30), 
		created_date DATETIME,
		gl_account INT(10), 
		location VARCHAR(30), 
		final_price DECIMAL(9,2), 
		service_fee DECIMAL(9,2), 
		sku_type VARCHAR(30), 
		coverage VARCHAR(10)
	); 

	CREATE TEMPORARY TABLE tblSHCGLView2 ( 
		gl_process_id INT(10), 
		shc_order_sku_id INT(10), 
		shc_order_id INT(10), 
		so_id VARCHAR(30), 
		order_no VARCHAR(30), 
		unit_no VARCHAR(30), 
		created_date DATETIME,
		gl_account INT(10), 
		location VARCHAR(30), 
		final_price DECIMAL(9,2), 
		service_fee DECIMAL(9,2), 
		sku_type VARCHAR(30), 
		coverage VARCHAR(10)
	); 

	CREATE TEMPORARY TABLE tblSHCGLView3 ( 
		gl_process_id INT(10), 
		shc_order_sku_id INT(10), 
		shc_order_id INT(10), 
		so_id VARCHAR(30), 
		order_no VARCHAR(30), 
		unit_no VARCHAR(30), 
		created_date DATETIME,
		gl_account INT(10), 
		location VARCHAR(30), 
		final_price DECIMAL(9,2), 
		service_fee DECIMAL(9,2), 
		sku_type VARCHAR(30), 
		coverage VARCHAR(10)
	); 

	insert tblSHCGLView1 (gl_process_id, shc_order_sku_id, shc_order_id, so_id, order_no, unit_no, created_date, gl_account, location, final_price, service_fee, sku_type, coverage)
	select 	shc_order.gl_process_id,
		shc_order_sku.shc_order_sku_id,
		shc_order.shc_order_id,
		shc_order.so_id,
		shc_order.order_no, 
		shc_order.unit_no,
		shc_order.created_date,
		shc_sku_account_assoc.gl_account, 
		case 
			when shc_sku_account_assoc.coverage_type = 'IW' then shc_order.store_no
			when shc_sku_account_assoc.coverage_type = 'PA' then (
				select prs_unit 
				from shc_lu_prs_unit_loc 
				where shc_lu_prs_unit_loc.unit_no = shc_order.unit_no
			)
			else right(shc_order.unit_no, 5)
			end 
		as location,
		IFNULL(shc_order_sku.final_price, 0) as final_price,
		IFNULL(shc_order_sku.service_fee, 0) as service_fee,
		shc_order_sku.type, 
		case 
			when shc_order_sku.add_on_ind = 1 then 'CC'
			else shc_order_sku.coverage
			end
		as coverage
	from shc_order 
			join so_hdr on shc_order.so_id = so_hdr.so_id
			join shc_order_sku on shc_order.shc_order_id = shc_order_sku.shc_order_id
			join shc_sku_account_assoc on shc_order_sku.sku = shc_sku_account_assoc.sku
	where 
	shc_order.so_id is not null 
	and (shc_order.gl_process_id = in_GLProcessID)
	and so_hdr.wf_state_id = 180
	and so_hdr.buyer_id IN (select buyer_id from buyer_feature_set where feature = 'SHC_GL_REPORTING')
	and so_hdr.funding_type_id = 40;

	insert tblSHCGLView2 select * from tblSHCGLView1;
	insert tblSHCGLView3 select * from tblSHCGLView1;

	delete from shc_gl_history where gl_process_id = in_GLProcessID;

	insert into shc_gl_history 
		(gl_process_id, shc_order_sku_id, shc_order_id, so_id, order_no, unit_no, created_date, gl_account, location, final_price, service_fee)
	select 
		vw1.gl_process_id,
		vw1.shc_order_sku_id,
		vw1.shc_order_id,
		vw1.so_id,
		vw1.order_no,
		vw1.unit_no,
		vw1.created_date,
		case when sku_type='N' and coverage='PT' then (
		      select vw2.gl_account from tblSHCGLView2 vw2 
			    where ( vw2.shc_order_id = vw1.shc_order_id 
				  or vw2.shc_order_sku_id = vw1.shc_order_sku_id )
			    order by sku_type desc limit 1)
		else vw1.gl_account
		end as gl_account,
		case when sku_type='N' and coverage='PT' then (
		      select vw3.location from tblSHCGLView3 vw3 
			    where ( vw3.shc_order_id = vw1.shc_order_id 
				  or vw3.shc_order_sku_id = vw1.shc_order_sku_id )
			    order by sku_type desc limit 1)
		else vw1.location
		end as location,
		vw1.final_price,
		vw1.service_fee 
	from tblSHCGLView1 vw1;

	DROP TABLE tblSHCGLView1;
	DROP TABLE tblSHCGLView2;
	DROP TABLE tblSHCGLView3;

END$$

DELIMITER ;
