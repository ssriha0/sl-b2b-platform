-- Used for detailed ACH Refund and ledger entry
INSERT   INTO ledger_transaction_types (
  transaction_type_id
    ,type
    ,descr
    ,sort_order)
   VALUES (
          1901
          ,null
          ,'Instant ACH Refund'
          ,19
       );
 
-- Used for rollup ACH Refund 
INSERT   INTO ledger_transaction_types (
  transaction_type_id
    ,type
    ,descr
    ,sort_order)
   VALUES (
          2001
          ,null
          ,'Consolidated Instant ACH Refund'
          ,20
       );
    
 
-- Used in refund action
INSERT INTO ledger_business_transaction (
  bus_trans_id
   ,bus_trans_type
   ,bus_trans_descr
   ,sort_order
   ,created_date
   ,modified_date
   ,modified_by)
   VALUES (
          320
          ,'Refund Transfer'
          ,'Buyer Instant ACH Refund'
          ,1
          ,NOW()
          ,NOW()
          ,'nsanzer'
       );
 
-- Used for detailed ACH Refund and ledger entry
 
-- Used in creating ledger and transaction entries
INSERT INTO ledger_transaction_rule (
 ledger_entry_rule_id
 ,bus_trans_id
 ,ledger_entity_type_id
 ,transaction_type_id
 ,type
 ,descr
 ,sort_order
 ,debit_entity_type_id
 ,debit_t_acct_no
 ,credit_entity_type_id
 ,credit_t_acct_no
 ,created_date
 ,modified_date
 ,modified_by)
   VALUES (
          1005
          ,320
          ,10
          ,1901
          ,'Instant ACH Refund'
          ,'Instant ACH Refund'
          ,100
          ,10
          ,200
          ,70
          ,2300
          ,NOW()
          ,NOW()
          ,'nsanzer'
       );
 
INSERT   INTO `ledger_transaction_rule` (`ledger_entry_rule_id`
                                         ,`bus_trans_id`
                                         ,`ledger_entity_type_id`
                                         ,`transaction_type_id`
                                         ,`type`
                                         ,`descr`
                                         ,`sort_order`
                                         ,`debit_entity_type_id`
                                         ,`debit_t_acct_no`
                                         ,`credit_entity_type_id`
                                         ,`credit_t_acct_no`
                                         ,`created_date`
                                         ,`modified_date`
                                         ,`modified_by`)
   VALUES (
          10007
          ,320
          ,10
          ,1901
          ,'SHC Prepaid Instant ACH'
          ,'Recognize Prepaid Instant ACH for SHC Unit'
          ,101
          ,30
          ,2400
          ,30
          ,2200
          ,NOW()
          ,NOW()
          ,'nsanzer'
       );

    
INSERT   INTO ledger_transaction_rule_funding
              (funding_type_id
               ,bus_trans_id
               ,ledger_entry_rule_id)
   VALUES (
          40
          ,320
          ,1005
       );
 
  INSERT   INTO ledger_transaction_rule_funding
              (funding_type_id
               ,bus_trans_id
               ,ledger_entry_rule_id)
   VALUES (
          40
          ,320
          ,10007
       );
    
 
-- Fulfillment inserts
INSERT   INTO `fullfillment_transaction_rule` (
 `fullfillment_entry_rule_id`
 ,`bus_trans_id`
 ,`descr`
 ,`sort_order`
 ,`entity_type_id`
 ,`entry_type_id`
 ,`promo_code_id`
 ,`created_date`
 ,`modified_date`
 ,`modified_by`)
   VALUES (
          4901
          ,320
          ,'Redeem Buyer V2'
          ,1
          ,10
          ,1
          ,NULL
          ,NOW()
          ,NOW()
          ,'nsanzer'
       );
 

 
INSERT INTO fullfillment_transaction_rule_funding
              (funding_type_id
               ,bus_trans_id
               ,fullfillment_entry_rule_id)
   VALUES (
          40
          ,320
          ,4901
       );
    
 

 




















-- Used for detailed ACH Refund and ledger entry
INSERT   INTO ledger_transaction_types (
  transaction_type_id
    ,type
    ,descr
    ,sort_order)
   VALUES (
          1901
          ,null
          ,'Instant ACH Refund'
          ,19
       );
 
-- Used for rollup ACH Refund 
INSERT   INTO ledger_transaction_types (
  transaction_type_id
    ,type
    ,descr
    ,sort_order)
   VALUES (
          2001
          ,null
          ,'Consolidated Instant ACH Refund'
          ,20
       );
    
 
-- Used in refund action
INSERT INTO ledger_business_transaction (
  bus_trans_id
   ,bus_trans_type
   ,bus_trans_descr
   ,sort_order
   ,created_date
   ,modified_date
   ,modified_by)
   VALUES (
          320
          ,'Refund Transfer'
          ,'Buyer Instant ACH Refund'
          ,1
          ,NOW()
          ,NOW()
          ,'nsanzer'
       );
 
-- Used in creating ledger and transaction entries
INSERT INTO ledger_transaction_rule (
 ledger_entry_rule_id
 ,bus_trans_id
 ,ledger_entity_type_id
 ,transaction_type_id
 ,type
 ,descr
 ,sort_order
 ,debit_entity_type_id
 ,debit_t_acct_no
 ,credit_entity_type_id
 ,credit_t_acct_no
 ,created_date
 ,modified_date
 ,modified_by)
   VALUES (
          1005
          ,320
          ,40
          ,1901
          ,'Instant ACH Refund'
          ,'Instant ACH Refund'
          ,100
          ,10
          ,500
          ,70
          ,500
          ,NOW()
          ,NOW()
          ,'nsanzer'
       );
 
    
    
    
INSERT   INTO ledger_transaction_rule_funding
              (funding_type_id
               ,bus_trans_id
               ,ledger_entry_rule_id)
   VALUES (
          40
          ,320
          ,1005
       );
 
    
 
-- Fulfillment inserts
INSERT   INTO `fullfillment_transaction_rule` (
 `fullfillment_entry_rule_id`
 ,`bus_trans_id`
 ,`descr`
 ,`sort_order`
 ,`entity_type_id`
 ,`entry_type_id`
 ,`promo_code_id`
 ,`created_date`
 ,`modified_date`
 ,`modified_by`)
   VALUES (
          4901
          ,320
          ,'Redeem Buyer V2'
          ,1
          ,40
          ,1
          ,NULL
          ,NOW()
          ,NOW()
          ,'nsanzer'
       );
 

 
INSERT INTO fullfillment_transaction_rule_funding
              (funding_type_id
               ,bus_trans_id
               ,fullfillment_entry_rule_id)
   VALUES (
          40
          ,320
          ,4901
       );
    
 

  