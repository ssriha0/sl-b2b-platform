alter table shc_order_sku add qty  int(10) unsigned default 1;

alter table shc_order_sku add ( status varchar(20), description    varchar(250)  );

alter table shc_order add (completed_date datetime , 
		   routed_date datetime , 
		resolution_descr varchar(1000) );
		
ALTER TABLE shc_order_sku
	 DROP FOREIGN KEY FK6967525B69900BFB,
	 DROP FOREIGN KEY FK_order_sku,
	 ADD add_on_ind TINYINT(1) NOT NULL DEFAULT '0' AFTER shc_order_id,
	 ADD service_fee DECIMAL(9,2) AFTER selling_price;
	 
ALTER TABLE shc_order_sku
	  ADD CONSTRAINT FK_order_sku FOREIGN KEY (shc_order_id) REFERENCES shc_order (shc_order_id) ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE shc_order_sku DROP INDEX AK_sh_order_sku_order;
CREATE UNIQUE INDEX `AK_sh_order_sku_order_addon`
	 USING BTREE ON shc_order_sku (sku, shc_order_id, add_on_ind);