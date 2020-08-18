DROP VIEW IF EXISTS `vw_shc_glskus`;

CREATE
    VIEW `vw_shc_glskus` 
	AS 
		(

		select  shc_order_sku.shc_order_sku_id,
			shc_order.shc_order_id,
			shc_sku_account_assoc.gl_account AS gl_account,
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
				shc_order_sku.type as skuType, 
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
		and (shc_order.processed_ind = 0 or shc_order.processed_ind is null)
		and so_hdr.wf_state_id = 180
		and so_hdr.buyer_id IN (select buyer_id from buyer_feature_set where feature = 'SHC_GL_REPORTING')
		and so_hdr.funding_type_id = 40


);