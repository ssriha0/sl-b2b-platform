ALTER TABLE buyer
 ADD ssn_ind TINYINT DEFAULT '0';
 
use QA_I26_1_WFM_ACCOUNTS;
ALTER TABLE fullfillment_vlaccounts
 ADD v1_account_balance DOUBLE AFTER modified_by,
 ADD v1_balance_date DATETIME,
 ADD v2_account_balance DOUBLE,
 ADD v2_balance_date DATETIME;
