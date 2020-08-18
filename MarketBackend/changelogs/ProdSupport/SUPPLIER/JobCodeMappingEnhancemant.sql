ALTER TABLE buyer_feature_set
 CHANGE feature feature ENUM('', 'ORDER_GROUPING', 'WS_UPDATE_PARTS', 'WS_UPDATE_SCHEDULE', 'SUBSTATUS_ALERTS', 'UPDATE_SO_DESCRIPTIONS', 'AUTO_ROUTE', 'ASSURANT_ALERTS', 'SHC_GL_REPORTING', 'SKU_DETAILS_FROM_SST') NOT NULL;
 
 INSERT INTO buyer_feature_set
 (buyer_feature_id, buyer_id, feature, created_date, modified_date) VALUES
	('10', '1000', 'SKU_DETAILS_FROM_SST', now(), now());
