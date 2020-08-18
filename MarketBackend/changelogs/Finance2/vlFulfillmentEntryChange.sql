ALTER TABLE fullfillment_transaction_rule
 ADD origination_entity_type VARCHAR(20) AFTER promo_code_id,
 ADD origination_acct_type VARCHAR(20),
 ADD destination_entity_type VARCHAR(20),
 ADD destination_acct_type VARCHAR(20);


ALTER TABLE fullfillment_entry
 ADD originating_pan BIGINT(20) AFTER vl_balance,
 ADD destination_pan BIGINT(20),
 ADD originating_state_code VARCHAR(20),
 ADD destination_state_code VARCHAR(20);
 
 
/*
	Rule additions
*/
update fullfillment_transaction_rule set origination_entity_type=20, destination_entity_type=20, origination_acct_type='V1', destination_acct_type='V1'
where fullfillment_entry_rule_id = 2100;

update fullfillment_transaction_rule set origination_entity_type=10, destination_entity_type=10, origination_acct_type='V1', destination_acct_type='V1'
where fullfillment_entry_rule_id = 2200;

update fullfillment_transaction_rule set origination_entity_type=10, destination_entity_type=10, origination_acct_type='V2', destination_acct_type='V2'
where fullfillment_entry_rule_id = 2210;

update fullfillment_transaction_rule set origination_entity_type=10, destination_entity_type=10, origination_acct_type='V1', destination_acct_type='V1'
where fullfillment_entry_rule_id = 3000;

update fullfillment_transaction_rule set origination_entity_type=10, destination_entity_type=10, origination_acct_type='V1', destination_acct_type='V1'
where fullfillment_entry_rule_id = 3100;

update fullfillment_transaction_rule set origination_entity_type=10, destination_entity_type=40, origination_acct_type='V1', destination_acct_type='SL1'
where fullfillment_entry_rule_id = 3110;

update fullfillment_transaction_rule set origination_entity_type=10, destination_entity_type=10, origination_acct_type='V1', destination_acct_type='V2'
where fullfillment_entry_rule_id = 3120;

update fullfillment_transaction_rule set origination_entity_type=10, destination_entity_type=10, origination_acct_type='V1', destination_acct_type='V1'
where fullfillment_entry_rule_id = 3130;

update fullfillment_transaction_rule set origination_entity_type=10, destination_entity_type=10, origination_acct_type='V1', destination_acct_type='V1'
where fullfillment_entry_rule_id = 3200;

update fullfillment_transaction_rule set origination_entity_type=10, destination_entity_type=10, origination_acct_type='V1', destination_acct_type='V2'
where fullfillment_entry_rule_id = 3210;

update fullfillment_transaction_rule set origination_entity_type=10, destination_entity_type=10, origination_acct_type='V1', destination_acct_type='V1'
where fullfillment_entry_rule_id = 3220;

update fullfillment_transaction_rule set origination_entity_type=10, destination_entity_type=10, origination_acct_type='V2', destination_acct_type='V2'
where fullfillment_entry_rule_id = 3300;

update fullfillment_transaction_rule set origination_entity_type=10, destination_entity_type=10, origination_acct_type='V2', destination_acct_type='V1'
where fullfillment_entry_rule_id = 3310;

update fullfillment_transaction_rule set origination_entity_type=10, destination_entity_type=10, origination_acct_type='V1', destination_acct_type='V1'
where fullfillment_entry_rule_id = 3320;

update fullfillment_transaction_rule set origination_entity_type=10, destination_entity_type=10, origination_acct_type='V2', destination_acct_type='V2'
where fullfillment_entry_rule_id = 3400;

update fullfillment_transaction_rule set origination_entity_type=10, destination_entity_type=10, origination_acct_type='V2', destination_acct_type='V1'
where fullfillment_entry_rule_id = 3410;

update fullfillment_transaction_rule set origination_entity_type=10, destination_entity_type=10, origination_acct_type='V1', destination_acct_type='V1'
where fullfillment_entry_rule_id = 3420;

update fullfillment_transaction_rule set origination_entity_type=10, destination_entity_type=10, origination_acct_type='V2', destination_acct_type='V2'
where fullfillment_entry_rule_id = 3500;

update fullfillment_transaction_rule set origination_entity_type=10, destination_entity_type=10, origination_acct_type='V2', destination_acct_type='V1'
where fullfillment_entry_rule_id = 3510;

update fullfillment_transaction_rule set origination_entity_type=10, destination_entity_type=10, origination_acct_type='V1', destination_acct_type='V1'
where fullfillment_entry_rule_id = 3520;

update fullfillment_transaction_rule set origination_entity_type=10, destination_entity_type=10, origination_acct_type='V2', destination_acct_type='V2'
where fullfillment_entry_rule_id = 3600;

update fullfillment_transaction_rule set origination_entity_type=10, destination_entity_type=20, origination_acct_type='V2', destination_acct_type='V1'
where fullfillment_entry_rule_id = 3610;

update fullfillment_transaction_rule set origination_entity_type=10, destination_entity_type=10, origination_acct_type='V2', destination_acct_type='V1'
where fullfillment_entry_rule_id = 3620;

update fullfillment_transaction_rule set origination_entity_type=10, destination_entity_type=10, origination_acct_type='V1', destination_acct_type='V1'
where fullfillment_entry_rule_id = 3630;

update fullfillment_transaction_rule set origination_entity_type=10, destination_entity_type=10, origination_acct_type='V2', destination_acct_type='V2'
where fullfillment_entry_rule_id = 3700;

update fullfillment_transaction_rule set origination_entity_type=10, destination_entity_type=40, origination_acct_type='V2', destination_acct_type='SL1'
where fullfillment_entry_rule_id = 3710;

update fullfillment_transaction_rule set origination_entity_type=10, destination_entity_type=20, origination_acct_type='V2', destination_acct_type='V1'
where fullfillment_entry_rule_id = 3720;

update fullfillment_transaction_rule set origination_entity_type=10, destination_entity_type=10, origination_acct_type='V2', destination_acct_type='V1'
where fullfillment_entry_rule_id = 3730;

update fullfillment_transaction_rule set origination_entity_type=10, destination_entity_type=10, origination_acct_type='V1', destination_acct_type='V1'
where fullfillment_entry_rule_id = 3740;

update fullfillment_transaction_rule set origination_entity_type=20, destination_entity_type=20, origination_acct_type='V1', destination_acct_type='V1'
where fullfillment_entry_rule_id = 3800;

update fullfillment_transaction_rule set origination_entity_type=10, destination_entity_type=10, origination_acct_type='V1', destination_acct_type='V1'
where fullfillment_entry_rule_id = 3900;

update fullfillment_transaction_rule set origination_entity_type=20, destination_entity_type=20, origination_acct_type='V1', destination_acct_type='V1'
where fullfillment_entry_rule_id = 4000;

update fullfillment_transaction_rule set origination_entity_type=40, destination_entity_type=40, origination_acct_type='SL1', destination_acct_type='SL1'
where fullfillment_entry_rule_id = 4100;

update fullfillment_transaction_rule set origination_entity_type=40, destination_entity_type=40, origination_acct_type='SL1', destination_acct_type='SL1'
where fullfillment_entry_rule_id = 4110;

update fullfillment_transaction_rule set origination_entity_type=90, destination_entity_type=90, origination_acct_type='SL3', destination_acct_type='SL3'
where fullfillment_entry_rule_id = 4200;

update fullfillment_transaction_rule set origination_entity_type=90, destination_entity_type=90, origination_acct_type='SL3', destination_acct_type='SL3'
where fullfillment_entry_rule_id = 4210;

update fullfillment_transaction_rule set origination_entity_type=90, destination_entity_type=90, origination_acct_type='SL3', destination_acct_type='SL3'
where fullfillment_entry_rule_id = 4300;

update fullfillment_transaction_rule set origination_entity_type=90, destination_entity_type=10, origination_acct_type='SL3', destination_acct_type='V1'
where fullfillment_entry_rule_id = 4310;

update fullfillment_transaction_rule set origination_entity_type=90, destination_entity_type=90, origination_acct_type='SL3', destination_acct_type='SL3'
where fullfillment_entry_rule_id = 4400;

update fullfillment_transaction_rule set origination_entity_type=90, destination_entity_type=20, origination_acct_type='SL3', destination_acct_type='V1'
where fullfillment_entry_rule_id = 4410;

update fullfillment_transaction_rule set origination_entity_type=10, destination_entity_type=10, origination_acct_type='V1', destination_acct_type='V1'
where fullfillment_entry_rule_id = 4500;

update fullfillment_transaction_rule set origination_entity_type=10, destination_entity_type=90, origination_acct_type='V1', destination_acct_type='SL3'
where fullfillment_entry_rule_id = 4510;

update fullfillment_transaction_rule set origination_entity_type=20, destination_entity_type=20, origination_acct_type='V1', destination_acct_type='V1'
where fullfillment_entry_rule_id = 4600;

update fullfillment_transaction_rule set origination_entity_type=20, destination_entity_type=90, origination_acct_type='V1', destination_acct_type='SL3'
where fullfillment_entry_rule_id = 4610;

update fullfillment_transaction_rule set origination_entity_type=10, destination_entity_type=10, origination_acct_type='V1', destination_acct_type='V1'
where fullfillment_entry_rule_id = 4700;

update fullfillment_transaction_rule set origination_entity_type=10, destination_entity_type=10, origination_acct_type='V1', destination_acct_type='V1'
where fullfillment_entry_rule_id = 4700;

update fullfillment_transaction_rule set origination_entity_type=10, destination_entity_type=10, origination_acct_type='V1', destination_acct_type='V1'
where fullfillment_entry_rule_id = 4800;

update fullfillment_transaction_rule set origination_entity_type=10, destination_entity_type=10, origination_acct_type='V1', destination_acct_type='V1'
where fullfillment_entry_rule_id = 4900;