ALTER TABLE ledger_transaction_rule
 ADD auto_ach_ind INT NOT NULL DEFAULT '0' AFTER modified_by;
 
 ALTER TABLE ledger_transaction_rule_funding
 ADD pricing_expression VARCHAR(255) AFTER ledger_entry_rule_id;
 
 insert into ledger_transaction_types (transaction_type_id, type, descr, sort_order) values (1901,null,'Instant ACH Refund', '19');

-- For post transaction credit
insert into ledger_transaction_rule (ledger_entry_rule_id,bus_trans_id,ledger_entity_type_id,transaction_type_id,type,descr,sort_order,debit_entity_type_id,debit_t_acct_no,credit_entity_type_id,credit_t_acct_no,created_date,modified_date,modified_by,auto_ach_ind,affects_balance)  values(1121,100,10,1900,'Auto Funding Posting SO Credit','Auto Funding Credit',98,70,2300,10,200,now(),now(),'paugus2',1,1);
insert into ledger_transaction_rule 	 values(1122,100,10,1900,'Auto Funding Posting SO GL Credit','Auto Funding GL Credit',99,30,2200,30,2400,now(),now(),'paugus2',1,0);
insert into ledger_transaction_rule_funding (funding_type_id,bus_trans_id,ledger_entry_rule_id) values(40,100,1121);
insert into ledger_transaction_rule_funding (funding_type_id,bus_trans_id,ledger_entry_rule_id) values(40,100,1122);
update fullfillment_transaction_rule set sort_order = 2 where fullfillment_entry_rule_id = 3100;
update fullfillment_transaction_rule set sort_order = 3 where fullfillment_entry_rule_id = 3110;
update fullfillment_transaction_rule set sort_order = 4 where fullfillment_entry_rule_id = 3120;
insert into fullfillment_transaction_rule (fullfillment_entry_rule_id,bus_trans_id,descr,sort_order,entity_type_id,entry_type_id,promo_code_id,created_date,modified_date,modified_by) values (3130,100,'Activate/Reload Buyer Post ACH V1',1,10,2,null,now(),now(),'paugus2');
insert into fullfillment_transaction_rule_funding (funding_type_id,bus_trans_id,fullfillment_entry_rule_id) values (40,100,3130);

-- For increase spend limit credit

insert into ledger_transaction_rule (ledger_entry_rule_id,bus_trans_id,ledger_entity_type_id,transaction_type_id,type,descr,sort_order,debit_entity_type_id,debit_t_acct_no,credit_entity_type_id,credit_t_acct_no,created_date,modified_date,modified_by,auto_ach_ind,affects_balance)   values(1201,150,10,1900,'Auto Funding Increase Spend Limit Credit','Auto Funding Credit',98,70,2300,10,200,now(),now(),'paugus2',1,1);
insert into ledger_transaction_rule values(1202,150,10,1900,'Auto Funding Increase Spend Limit GL Credit','Auto Funding GL Credit',99,30,2200,30,2400,now(),now(),'paugus2',1,0);
insert into ledger_transaction_rule_funding (funding_type_id,bus_trans_id,ledger_entry_rule_id) values(40,150,1201);
insert into ledger_transaction_rule_funding (funding_type_id,bus_trans_id,ledger_entry_rule_id) values(40,150,1202);
update fullfillment_transaction_rule set sort_order = 2 where fullfillment_entry_rule_id = 3200;
update fullfillment_transaction_rule set sort_order = 3 where fullfillment_entry_rule_id = 3210;
insert into fullfillment_transaction_rule (fullfillment_entry_rule_id,bus_trans_id,descr,sort_order,entity_type_id,entry_type_id,promo_code_id,created_date,modified_date,modified_by) values (3220,150,'Activate/Reload Buyer Increase SpendLimit ACH V1',1,10,2,null,now(),now(),'paugus2');
insert into fullfillment_transaction_rule_funding  (funding_type_id,bus_trans_id,fullfillment_entry_rule_id)   values (40,150,3220);

-- Refund insertions

-- For cancel with penalty
insert into ledger_transaction_rule (ledger_entry_rule_id,bus_trans_id,ledger_entity_type_id,transaction_type_id,type,descr,sort_order,debit_entity_type_id,debit_t_acct_no,credit_entity_type_id,credit_t_acct_no,created_date,modified_date,modified_by,auto_ach_ind,affects_balance) values(1365,110,10,1901,'Auto ACH Refund Cancel SO Debit','Auto ACH Refund Debit SO Debit',700,10,200,70,2300,now(),now(),'paugus2',1,1);
insert into ledger_transaction_rule_funding (funding_type_id,bus_trans_id,ledger_entry_rule_id) values(40,110,1365);
insert into fullfillment_transaction_rule (fullfillment_entry_rule_id,bus_trans_id,descr,sort_order,entity_type_id,entry_type_id,promo_code_id,created_date,modified_date,modified_by) values (3630,110,'Redeem Buyer Cancel Instant ACH V2',4,10,1,null,now(),now(),'paugus2');
insert into fullfillment_transaction_rule_funding  (funding_type_id,bus_trans_id,fullfillment_entry_rule_id) values (40,110,3630);

insert into ledger_transaction_rule (ledger_entry_rule_id,bus_trans_id,ledger_entity_type_id,transaction_type_id,type,descr,sort_order,debit_entity_type_id,debit_t_acct_no,credit_entity_type_id,credit_t_acct_no,created_date,modified_date,modified_by,auto_ach_ind,affects_balance)  values(1366,110,10,1901,'Auto ACH GL Refund Cancel SO Debit','Auto ACH GL Refund Cancel SO Debit',800,30,2200,30,2400,now(),now(),'paugus2',1,0);
insert into ledger_transaction_rule_funding (funding_type_id,bus_trans_id,ledger_entry_rule_id) values(40,110,1366);


-- For cancel without penalty
insert into ledger_transaction_rule values(1396,111,10,1901,'Auto ACH Refund Cancel W/O Penalty SO Debit','Auto ACH Refund Cancel W/O Penalty Debit SO Debit',300,10,200,70,2300,now(),now(),'paugus2',1,1);
insert into ledger_transaction_rule_funding (funding_type_id,bus_trans_id,ledger_entry_rule_id) values(40,111,1396);
insert into fullfillment_transaction_rule (fullfillment_entry_rule_id,bus_trans_id,descr,sort_order,entity_type_id,entry_type_id,promo_code_id,created_date,modified_date,modified_by)values (3520,111,'Redeem Buyer Cancel W/O Penalty Instant ACH V2',3,10,1,null,now(),now(),'paugus2');
insert into fullfillment_transaction_rule_funding  (funding_type_id,bus_trans_id,fullfillment_entry_rule_id) values (40,111,3520);

insert into ledger_transaction_rule (ledger_entry_rule_id,bus_trans_id,ledger_entity_type_id,transaction_type_id,type,descr,sort_order,debit_entity_type_id,debit_t_acct_no,credit_entity_type_id,credit_t_acct_no,created_date,modified_date,modified_by,auto_ach_ind,affects_balance)  values(1397,111,10,1901,'Auto ACH GL Refund Cancel W/O Penalty SO Debit','Auto ACH GL Refund Cancel SO Debit',400,30,2200,30,2400,now(),now(),'paugus2',1,0);
insert into ledger_transaction_rule_funding (funding_type_id,bus_trans_id,ledger_entry_rule_id) values(40,110,1397);


-- For void

insert into ledger_transaction_rule (ledger_entry_rule_id,bus_trans_id,ledger_entity_type_id,transaction_type_id,type,descr,sort_order,debit_entity_type_id,debit_t_acct_no,credit_entity_type_id,credit_t_acct_no,created_date,modified_date,modified_by,auto_ach_ind,affects_balance)  values(1385,115,10,1901,'Auto ACH Refund Void SO Debit','Auto ACH Refund Void SO Debit',300,10,200,70,2300,now(),now(),'paugus2',1,1);
insert into ledger_transaction_rule_funding (funding_type_id,bus_trans_id,ledger_entry_rule_id) values(40,115,1385);
insert into fullfillment_transaction_rule (fullfillment_entry_rule_id,bus_trans_id,descr,sort_order,entity_type_id,entry_type_id,promo_code_id,created_date,modified_date,modified_by) values (3420,115,'Redeem Buyer Void SO Instant ACH V2',3,10,1,null,now(),now(),'paugus2');
insert into fullfillment_transaction_rule_funding  (funding_type_id,bus_trans_id,fullfillment_entry_rule_id)  values (40,115,3420);

insert into ledger_transaction_rule (ledger_entry_rule_id,bus_trans_id,ledger_entity_type_id,transaction_type_id,type,descr,sort_order,debit_entity_type_id,debit_t_acct_no,credit_entity_type_id,credit_t_acct_no,created_date,modified_date,modified_by,auto_ach_ind,affects_balance)  values(1386,115,10,1901,'Auto ACH GL Refund Void SO Debit','Auto ACH GL Refund void SO Debit',400,30,2200,30,2400,now(),now(),'paugus2',1,0);
insert into ledger_transaction_rule_funding(funding_type_id,bus_trans_id,ledger_entry_rule_id) values(40,115,1386);

-- For SO Close

insert into ledger_transaction_rule (ledger_entry_rule_id,bus_trans_id,ledger_entity_type_id,transaction_type_id,type,descr,sort_order,debit_entity_type_id,debit_t_acct_no,credit_entity_type_id,credit_t_acct_no,created_date,modified_date,modified_by,auto_ach_ind,affects_balance)  values(1470,120,10,1901,'Auto ACH Refund Close SO Debit','Auto ACH Refund Close SO Debit',800,10,200,70,2300,now(),now(),'paugus2',1,1);
insert into ledger_transaction_rule_funding (funding_type_id,bus_trans_id,ledger_entry_rule_id) values(40,120,1470);
insert into fullfillment_transaction_rule (fullfillment_entry_rule_id,bus_trans_id,descr,sort_order,entity_type_id,entry_type_id,promo_code_id,created_date,modified_date,modified_by) values (3740,120,'Redeem Buyer Close SO Instant ACH V2',5,10,1,null,now(),now(),'paugus2');
insert into fullfillment_transaction_rule_funding  (funding_type_id,bus_trans_id,fullfillment_entry_rule_id) values (40,120,3740);

insert into ledger_transaction_rule (ledger_entry_rule_id,bus_trans_id,ledger_entity_type_id,transaction_type_id,type,descr,sort_order,debit_entity_type_id,debit_t_acct_no,credit_entity_type_id,credit_t_acct_no,created_date,modified_date,modified_by,auto_ach_ind,affects_balance)  values(1480,120,10,1901,'Auto ACH GL Refund Close SO Debit','Auto ACH GL Refund Close SO Debit',900,30,2200,30,2400,now(),now(),'paugus2',1,0);
insert into ledger_transaction_rule_funding(funding_type_id,bus_trans_id,ledger_entry_rule_id) values(40,120,1480);

-- Decrease spend limit
insert into ledger_transaction_rule (ledger_entry_rule_id,bus_trans_id,ledger_entity_type_id,transaction_type_id,type,descr,sort_order,debit_entity_type_id,debit_t_acct_no,credit_entity_type_id,credit_t_acct_no,created_date,modified_date,modified_by,auto_ach_ind,affects_balance)   values(1270,160,10,1901,'Auto ACH Refund Decrease Spend Limit SO Debit','Auto ACH Refund Decrease Spend Limit SO Debit',300,10,200,70,2300,now(),now(),'paugus2',1,1);
insert into ledger_transaction_rule_funding (funding_type_id,bus_trans_id,ledger_entry_rule_id) values(40,160,1270);
insert into fullfillment_transaction_rule (fullfillment_entry_rule_id,bus_trans_id,descr,sort_order,entity_type_id,entry_type_id,promo_code_id,created_date,modified_date,modified_by) values (3320,160,'Redeem Buyer Decrease Spend Limit SO Instant ACH V2',3,10,1,null,now(),now(),'paugus2');
insert into fullfillment_transaction_rule_funding  (funding_type_id,bus_trans_id,fullfillment_entry_rule_id) values (40,160,3320);

insert into ledger_transaction_rule (ledger_entry_rule_id,bus_trans_id,ledger_entity_type_id,transaction_type_id,type,descr,sort_order,debit_entity_type_id,debit_t_acct_no,credit_entity_type_id,credit_t_acct_no,created_date,modified_date,modified_by,auto_ach_ind,affects_balance)  values(1280,160,10,1901,'Auto ACH GL Increase Spend Limit Refund SO Debit','Auto ACH GL Increase Spend Limit Refund SO Debit',400,30,2200,30,2400,now(),now(),'paugus2',1,0);
insert into ledger_transaction_rule_funding (funding_type_id,bus_trans_id,ledger_entry_rule_id) values(40,160,1280);


DROP TABLE IF EXISTS `ledger_transaction_pricing`;


CREATE TABLE `ledger_transaction_pricing` (
  `ledger_entry_rule_id` int(10) unsigned NOT NULL,
  `pricing_expression` varchar(255) default NULL,
  `created_date` datetime default NULL,
  `modified_date` datetime default NULL,
  `modified_by` varchar(30) default NULL,
  UNIQUE KEY `ledger_entry_rule_id` (`ledger_entry_rule_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data for the table ledger_transaction_pricing

insert  into `ledger_transaction_pricing`(`ledger_entry_rule_id`,`pricing_expression`,`created_date`,`modified_date`,`modified_by`) values (1000,'TRANSFER_AMOUNT','2008-12-16 14:47:50','2008-12-16 14:47:50','swilk02'),(1001,'TRANSFER_AMOUNT','2008-12-16 14:47:50','2008-12-16 14:47:50','swilk02'),(1002,'TRANSFER_AMOUNT','2008-12-16 14:47:50','2008-12-16 14:47:50','swilk02'),(1003,'TRANSFER_AMOUNT','2008-12-16 14:47:50','2008-12-16 14:47:50','swilk02'),(1004,'TRANSFER_AMOUNT','2008-12-16 14:47:50','2008-12-16 14:47:50','swilk02'),(1010,'LABOUR_SPEND_LIMIT+PARTS_SPEND_LIMIT','2008-12-16 14:47:50','2008-12-16 14:47:50','swilk02'),(1100,'POSTING_FEE','2008-12-16 14:47:50','2008-12-16 14:47:50','swilk02'),(1101,'POSTING_FEE','2008-12-16 14:47:50','2008-12-16 14:47:50','swilk02'),(1110,'POSTING_FEE','2008-12-16 14:47:50','2008-12-16 14:47:50','swilk02'),(1120,'LABOUR_SPEND_LIMIT+PARTS_SPEND_LIMIT','2008-12-16 14:47:50','2008-12-16 14:47:50','swilk02'),(1121,'ACH_AMOUNT','2008-12-29 12:01:47','2008-12-29 12:01:47','paugus2'),(1122,'ACH_AMOUNT','2008-12-29 12:01:47','2008-12-29 12:01:47','paugus2'),(1200,'USER_ENTERED','2008-12-16 14:47:50','2008-12-16 14:47:50','swilk02'),(1210,'DEPOSIT_AMOUNT','2008-12-16 14:47:50','2008-12-16 14:47:50','swilk02'),(1250,'USER_ENTERED','2008-12-16 14:47:50','2008-12-16 14:47:50','swilk02'),(1260,'USER_ENTERED','2008-12-16 14:47:50','2008-12-16 14:47:50','swilk02'),(1300,'CANCELLATION_FEE','2008-12-16 14:47:50','2008-12-16 14:47:50','swilk02'),(1301,'CANCELLATION_FEE','2008-12-16 14:47:50','2008-12-16 14:47:50','swilk02'),(1310,'LABOUR_SPEND_LIMIT+PARTS_SPEND_LIMIT+UPSELL_PROVIDER_TOTAL-CANCELLATION_FEE','2008-12-16 14:47:50','2008-12-16 14:47:50','swilk02'),(1330,'CANCELLATION_FEE','2008-12-16 14:47:50','2008-12-16 14:47:50','swilk02'),(1340,'RETAIL_CANCELLATION_FEE','2008-12-16 14:47:50','2008-12-16 14:47:50','swilk02'),(1350,'LABOUR_SPEND_LIMIT+PARTS_SPEND_LIMIT-FINAL_LABOUR-FINAL_PARTS','2008-12-16 14:47:50','2008-12-16 14:47:50','swilk02'),(1360,'LABOUR_SPEND_LIMIT+PARTS_SPEND_LIMIT+UPSELL_PROVIDER_TOTAL','2008-12-16 14:47:50','2008-12-16 14:47:50','swilk02'),(1365,'LABOUR_SPEND_LIMIT+PARTS_SPEND_LIMIT','2008-12-29 12:01:47','2008-12-29 12:01:47','paugus2'),(1366,'LABOUR_SPEND_LIMIT+PARTS_SPEND_LIMIT','2008-12-29 12:01:47','2008-12-29 12:01:47','paugus2'),(1370,'LABOUR_SPEND_LIMIT+PARTS_SPEND_LIMIT+UPSELL_PROVIDER_TOTAL','2008-12-16 14:47:50','2008-12-16 14:47:50','swilk02'),(1380,'LABOUR_SPEND_LIMIT+PARTS_SPEND_LIMIT+UPSELL_PROVIDER_TOTAL','2008-12-16 14:47:50','2008-12-16 14:47:50','swilk02'),(1385,'LABOUR_SPEND_LIMIT+PARTS_SPEND_LIMIT','2008-12-29 12:01:48','2008-12-29 12:01:48','paugus2'),(1386,'LABOUR_SPEND_LIMIT+PARTS_SPEND_LIMIT','2008-12-29 12:01:47','2008-12-29 12:01:47','paugus2'),(1390,'LABOUR_SPEND_LIMIT+PARTS_SPEND_LIMIT+UPSELL_PROVIDER_TOTAL','2008-12-16 14:47:50','2008-12-16 14:47:50','swilk02'),(1395,'LABOUR_SPEND_LIMIT+PARTS_SPEND_LIMIT+UPSELL_PROVIDER_TOTAL','2008-12-16 14:47:50','2008-12-16 14:47:50','swilk02'),(1396,'LABOUR_SPEND_LIMIT+PARTS_SPEND_LIMIT','2008-12-29 12:01:48','2008-12-29 12:01:48','paugus2'),(1397,'LABOUR_SPEND_LIMIT+PARTS_SPEND_LIMIT','2008-12-29 12:01:48','2008-12-29 12:01:48','paugus2'),(1400,'FINAL_LABOUR+FINAL_PARTS+UPSELL_PROVIDER_TOTAL-FINAL_SERVICE_FEE-UPSELL_SERVICE_FEE','2008-12-16 14:47:50','2008-12-16 14:47:50','swilk02'),(1401,'FINAL_LABOUR+FINAL_PARTS+UPSELL_PROVIDER_TOTAL-FINAL_SERVICE_FEE-UPSELL_SERVICE_FEE','2008-12-16 14:47:50','2008-12-16 14:47:50','swilk02'),(1405,'FINAL_LABOUR+FINAL_PARTS+UPSELL_PROVIDER_TOTAL','2008-12-16 14:47:50','2008-12-16 14:47:50','swilk02'),(1410,'FINAL_SERVICE_FEE+UPSELL_SERVICE_FEE','2008-12-16 14:47:50','2008-12-16 14:47:50','swilk02'),(1411,'FINAL_SERVICE_FEE+UPSELL_SERVICE_FEE','2008-12-16 14:47:50','2008-12-16 14:47:50','swilk02'),(1420,'LABOUR_SPEND_LIMIT+PARTS_SPEND_LIMIT-FINAL_LABOUR-FINAL_PARTS','2008-12-16 14:47:50','2008-12-16 14:47:50','swilk02'),(1430,'FINAL_SERVICE_FEE+UPSELL_SERVICE_FEE','2008-12-16 14:47:50','2008-12-16 14:47:50','swilk02'),(1440,'FINAL_LABOUR+FINAL_PARTS+UPSELL_PROVIDER_TOTAL-FINAL_SERVICE_FEE-UPSELL_SERVICE_FEE','2008-12-16 14:47:50','2008-12-16 14:47:50','swilk02'),(1450,'RETAIL_SO_PRICE','2008-12-16 14:47:50','2008-12-16 14:47:50','swilk02'),(1460,'LABOUR_SPEND_LIMIT+PARTS_SPEND_LIMIT+UPSELL_PROVIDER_TOTAL','2008-12-16 14:47:50','2008-12-16 14:47:50','swilk02'),(1470,'LABOUR_SPEND_LIMIT+PARTS_SPEND_LIMIT-FINAL_LABOUR-FINAL_PARTS','2008-12-29 12:01:47','2008-12-29 12:01:47','paugus2'),(1480,'LABOUR_SPEND_LIMIT+PARTS_SPEND_LIMIT-FINAL_LABOUR-FINAL_PARTS','2008-12-29 12:01:47','2008-12-29 12:01:47','paugus2'),(2000,'TRANSFER_AMOUNT','2008-12-16 14:47:50','2008-12-16 14:47:50','swilk02'),(2010,'TRANSFER_AMOUNT','2008-12-16 14:47:50','2008-12-16 14:47:50','swilk02'),(2020,'TRANSFER_AMOUNT','2008-12-16 14:47:50','2008-12-16 14:47:50','swilk02'),(5000,'TRANSFER_AMOUNT','2008-12-16 14:47:50','2008-12-16 14:47:50','swilk02'),(5010,'TRANSFER_AMOUNT','2008-12-16 14:47:50','2008-12-16 14:47:50','swilk02'),(5020,'TRANSFER_AMOUNT','2008-12-16 14:47:50','2008-12-16 14:47:50','swilk02'),(5100,'TRANSFER_AMOUNT','2008-12-16 14:47:50','2008-12-16 14:47:50','swilk02'),(5110,'TRANSFER_AMOUNT','2008-12-16 14:47:50','2008-12-16 14:47:50','swilk02'),(5300,'TRANSFER_AMOUNT','2008-12-16 14:47:50','2008-12-16 14:47:50','swilk02'),(5400,'TRANSFER_AMOUNT','2008-12-16 14:47:50','2008-12-16 14:47:50','swilk02'),(5500,'TRANSFER_AMOUNT','2008-12-16 14:47:50','2008-12-16 14:47:50','swilk02'),(5600,'TRANSFER_AMOUNT','2008-12-16 14:47:50','2008-12-16 14:47:50','swilk02'),(10001,'TRANSFER_AMOUNT','2008-12-16 14:47:50','2008-12-16 14:47:50','swilk02'),(10002,'FINAL_SERVICE_FEE+UPSELL_SERVICE_FEE','2008-12-16 14:47:50','2008-12-16 14:47:50','swilk02'),(10003,'FINAL_SERVICE_FEE+UPSELL_SERVICE_FEE','2008-12-16 14:47:50','2008-12-16 14:47:50','swilk02'),(10005,'FINAL_LABOUR+FINAL_PARTS+UPSELL_PROVIDER_TOTAL-FINAL_SERVICE_FEE-UPSELL_SERVICE_FEE','2008-12-16 14:47:50','2008-12-16 14:47:50','swilk02'),(10006,'FINAL_LABOUR+FINAL_PARTS+UPSELL_PROVIDER_TOTAL-FINAL_SERVICE_FEE-UPSELL_SERVICE_FEE','2008-12-16 14:47:50','2008-12-16 14:47:50','swilk02');


Update ledger_transaction_rule_funding a, ledger_transaction_pricing b set a.pricing_expression = b.pricing_expression 
where a.ledger_entry_rule_id = b.ledger_entry_rule_id;



DROP TABLE IF EXISTS `ledger_transaction_pricing`;

ALTER TABLE fullfillment_entry
 ADD vl_balance DOUBLE AFTER message_desc_id;
