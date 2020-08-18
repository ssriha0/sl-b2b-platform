package com.servicelive.manage1099.constants;

/**
 * 
 * @author mjoshi1
 * 
 */
public class DBConstants {

	
	public static String DB_SUPPLIER = "supplier_prod";
	public static String DB_ACCOUNTS = "accounts_prod";
	
	public static String DB_SUPPLIER_IN_QUERY = "supplier_prod";
	public static String DB_ACCOUNTS_IN_QUERY = "accounts_prod";
	
	public static String SHOW_QUERY = "FALSE";
	
	public static String DB_USER = "";
	public static String DB_PASSWORD = "";
	public static String DB_URL = "";

	public static String QUERY_CREDIT_DEBIT ="";
	public static String QUERY_SO_PAYMENT ="";
	public static String QUERY_SO_CANCELLATION_FIX ="";
	
	// EncodingQuery is no longer in use
	public static String EncodingQUERY = "SELECT app_value FROM application_properties WHERE app_key=?";
	
	public static String SecureEncodingQUERY = "SELECT AES_DECRYPT(app_value, 'AES_DECRYPTION_KEY') as app_value FROM encryption_key WHERE app_key=?";
	
	public static String W9_HISTORY_DETAILS = "SELECT vh.vendor_id,vh.ein_no,vh.taxpayer_id_number_type, "
		+ "vh.modified_date,vh.dba_name,vh.business_name,vh.city, "
		+ "vh.street_1 AS street1,vh.street_2 AS street2,vh.state_cd AS state,vh.zip,vh.zip4 "
		+ "FROM supplier_prod.vendor_w9_history vh " 
		+ "WHERE vh.vendor_id IN(?) "
		//+ "AND vh.vendor_id != 17793 "
		+ "GROUP BY vh.vendor_id,vh.taxpayer_id_number_type,vh.ein_no "
		+ "ORDER BY vh.vendor_id ASC ,vh.modified_date DESC;";
	
	public static String QUERY_CREDIT_DEBIT_PER_VENDOR = 	"SELECT "+
	"originating_buyer_id,  "+
	"w9.vendor_id               AS vendor_id,  "+
	"SUM(tran.provider_payment_amount) AS provider_payment_amount,  "+
	"SUM(tran.service_order_payment) AS service_order_payment,  "+
	"SUM(tran.other_payment)    AS other_payment,  "+
	"(SUM(tran.service_order_payment) + SUM(tran.other_payment)) AS amount, "+ 
	"MAX(tran.last_payment_date) AS last_payment_date  "+
	"FROM supplier_prod.vendor_w9 w9  "+
	"JOIN (SELECT  "+
	"te.originating_buyer_id     AS originating_buyer_id,  "+
	"te.ledger_entity_id         AS vendor_id,  "+
	"0                           AS provider_payment_amount,  "+
	"0                           AS service_order_payment,  "+
	"SUM(te.trans_amount)        AS other_payment,  "+
	"MAX(te.created_date)        AS last_payment_date  "+
	"FROM accounts_prod.ledger_transaction_entry te  "+
	"INNER JOIN accounts_prod.ledger_entry le  "+
	"ON le.ledger_entry_id = te.ledger_entry_id  "+
	"INNER JOIN accounts_prod.ledger_transaction_rule ltr  "+
	"ON ltr.ledger_entry_rule_id = le.ledger_entry_rule_id  "+
	"WHERE (ltr.ledger_entry_rule_id = 5400 AND te.entry_type_id = 2)  "+
	"AND (so_id IS NULL)  "+
	"AND te.trans_amount > 0  "+
	"AND le.affects_balance = 1  "+
	"AND te.ledger_entity_id > 10  "+
	"AND le.entry_date BETWEEN 'startDate'  "+
	"AND 'endDate'  "+
	"GROUP BY te.ledger_entity_id UNION SELECT  "+
	"te.originating_buyer_id      AS originating_buyer_id,  "+
	"te.ledger_entity_id          AS vendor_id,  "+
	"0                            AS provider_payment_amount,  "+
	"0                            AS service_order_payment,  "+
	"SUM(-1*te.trans_amount)      AS other_payment,  "+
	"MAX(te.created_date)         AS last_payment_date  "+
	"FROM accounts_prod.ledger_transaction_entry te  "+
	"INNER JOIN accounts_prod.ledger_entry le  "+
	"ON le.ledger_entry_id = te.ledger_entry_id  "+
	"INNER JOIN accounts_prod.ledger_transaction_rule ltr  "+
	"ON ltr.ledger_entry_rule_id = le.ledger_entry_rule_id  "+
	"WHERE (ltr.ledger_entry_rule_id = 5600 AND te.entry_type_id = 1)  "+
	"AND ltr.transaction_type_id != 800  "+
	"AND (so_id IS NULL)  "+
	"AND te.trans_amount > 0  "+
	"AND le.affects_balance = 1  "+
	"AND te.ledger_entity_id > 10  "+
	"AND le.entry_date BETWEEN 'startDate'  "+
	"AND 'endDate'  "+
	"GROUP BY te.ledger_entity_id) AS tran   "+
	"ON (tran.vendor_id = w9.vendor_id)  "+
	"WHERE  w9.tax_status_id NOT IN ( 2, 8 ) "+
	"AND w9.vendor_id = ? "+
	"GROUP BY w9.vendor_id; ";
	
	public static String QUERY_SO_PAYMENT_PER_VENDOR = "SELECT h.buyer_id,h.accepted_vendor_id AS vendor_id, "+
    " 0 AS provider_payment_amount, "+
    " 0 AS service_order_payment, "+
    " 0 AS other_payment, "+
"SUM(te1.trans_amount) AS amount, "+
"MAX(te1.created_date) AS last_payment_date, "+
     "w9.taxpayer_id_number_type AS taxpayer_id_number_type, "+
     "w9.ein_no AS ein_no, "+
     "w9.business_name, "+
     "w9.dba_name AS dba_name, "+
     "w9.street_1 AS street1, "+
     "w9.street_2 AS street2, "+
     "w9.city AS city, "+
     "w9.state_cd AS state, "+
     "w9.zip AS zip, "+
     "w9.zip4  AS zip4 "+
"FROM accounts_prod.ledger_entry le1  "+
"INNER JOIN accounts_prod.ledger_transaction_entry te1 ON le1.ledger_entry_id = te1.ledger_entry_id  "+
"JOIN supplier_prod.so_hdr h ON le1.so_id = h.so_id "+
"JOIN supplier_prod.vendor_w9 w9 ON h.accepted_vendor_id = w9.vendor_id "+
"WHERE "+
"( (le1.ledger_entry_rule_id = 1405 AND te1.entry_type_id = 1)  "+
"OR   "+
"(le1.ledger_entry_rule_id IN(1300,1305) AND te1.entry_type_id = 2) ) "+
"AND te1.trans_amount > 0  "+
"AND  te1.originating_buyer_id IN(1000,1085,1332,1953,3000,4000)   "+
"AND te1.originating_buyer_id = h.buyer_id     "+
"AND le1.entry_date BETWEEN 'startDate' AND 'endDate' "+
"AND w9.tax_status_id NOT IN ( 2, 8 ) "+
"AND h.accepted_vendor_id = ? " +
"GROUP BY h.accepted_vendor_id; ";
	
	/*public static String QUERY_SO_PAYMENT_PER_VENDOR = "SELECT originating_buyer_id, w9.vendor_id AS vendor_id, "+
	"SUM(tran.provider_payment_amount) AS provider_payment_amount,  "+
	"SUM(tran.service_order_payment) AS service_order_payment,  "+
	"SUM(tran.other_payment) AS other_payment,  "+
	"(SUM(tran.service_order_payment) + SUM(tran.other_payment)) AS amount,  "+
	"MAX(tran.last_payment_date) AS last_payment_date "+ 
	"FROM supplier_prod.vendor_w9 w9 JOIN  "+
	"( SELECT te.originating_buyer_id AS originating_buyer_id,te.ledger_entity_id AS vendor_id,  "+
	"SUM(te.trans_amount) AS provider_payment_amount,   "+
	"SUM(tbl_final.trans_amount) AS service_order_payment,    "+
	"0 AS other_payment,   "+
	"MAX(te.created_date) AS last_payment_date  "+
	"FROM accounts_prod.ledger_transaction_entry te  "+
	"INNER JOIN accounts_prod.ledger_entry le ON le.ledger_entry_id = te.ledger_entry_id  "+
	"INNER JOIN accounts_prod.ledger_transaction_rule ltr ON ltr.ledger_entry_rule_id = le.ledger_entry_rule_id  "+
	"INNER JOIN (  "+
	"SELECT le1.so_id, te1.trans_amount FROM accounts_prod.ledger_entry le1  "+
	"INNER JOIN accounts_prod.ledger_transaction_entry te1 ON le1.ledger_entry_id = te1.ledger_entry_id  "+
	"WHERE ( (le1.ledger_entry_rule_id = 1405 AND te1.entry_type_id = 1)  "+
	"OR   "+
	"(le1.ledger_entry_rule_id IN(1300,1305) AND te1.entry_type_id = 2) )  "+
	"AND te1.trans_amount > 0  "+
	"AND (  te1.originating_buyer_id IN(1000,1085,1332,1953,3000,4000) )  "+
	"AND le1.entry_date BETWEEN 'startDate' AND 'endDate'  "+
	"GROUP BY le1.so_id ) AS tbl_final ON (le.so_id=tbl_final.so_id)  "+
	"WHERE (ltr.credit_entity_type_id = 20 AND te.entry_type_id = 2)  "+
	"AND te.trans_amount > 0 AND (  te.originating_buyer_id IN(1000,1085,1332,1953,3000,4000) )  "+
	"AND le.affects_balance = 1 AND te.ledger_entity_id > 10   "+
	"AND le.entry_date BETWEEN 'startDate' AND 'endDate'  "+
	"GROUP BY te.ledger_entity_id ) AS tran ON (tran.vendor_id = w9.vendor_id)  "+
	"WHERE  w9.tax_status_id NOT IN ( 2, 8 ) "+
	"AND w9.vendor_id = ? GROUP BY w9.vendor_id; ";*/
	
	public static String QUERY_SO_CANCELLATION_FIX_PER_VENDOR = "SELECT originating_buyer_id, w9.vendor_id AS vendor_id,  "+
	"SUM(tran.provider_payment_amount) AS provider_payment_amount,  "+
	"SUM(tran.service_order_payment) AS service_order_payment,  "+
	"SUM(tran.other_payment) AS other_payment,  "+
	"(SUM(tran.service_order_payment) + SUM(tran.other_payment)) AS amount,  "+
	"MAX(tran.last_payment_date) AS last_payment_date  "+
	"FROM supplier_prod.vendor_w9 w9 JOIN  "+
	"( SELECT "+
	"te.originating_buyer_id AS originating_buyer_id,te.ledger_entity_id AS vendor_id,  "+
	"SUM(te.trans_amount) AS provider_payment_amount,   "+
	"SUM(tbl_final.trans_amount) AS service_order_payment,    "+
	"0 AS other_payment, MAX(te.created_date) AS last_payment_date  "+
	"FROM accounts_prod.ledger_transaction_entry te  "+
	"INNER JOIN accounts_prod.ledger_entry le ON le.ledger_entry_id = te.ledger_entry_id  "+
	"INNER JOIN accounts_prod.ledger_transaction_rule ltr ON ltr.ledger_entry_rule_id = le.ledger_entry_rule_id  "+
	"INNER JOIN ( SELECT le1.so_id, te1.trans_amount  "+
	"FROM accounts_prod.ledger_entry le1  "+
	"INNER JOIN accounts_prod.ledger_transaction_entry te1 ON le1.ledger_entry_id = te1.ledger_entry_id  "+
	"WHERE (le1.ledger_entry_rule_id = 1303 AND te1.entry_type_id = 1 )  "+
	"AND te1.trans_amount > 0 AND ( " +
	"te1.originating_buyer_id IN(1000,1085,1332,1953,3000,4000) )  "+
	"AND le1.entry_date BETWEEN 'startDate' AND 'endDate'  "+
	"GROUP BY le1.so_id) AS tbl_final ON (le.so_id=tbl_final.so_id)  "+
	"WHERE (ltr.credit_entity_type_id = 30 AND te.entry_type_id = 1)  "+
	"AND te.trans_amount > 0 AND ( "+
	"te.originating_buyer_id IN(1000,1085,1332,1953,3000,4000) )  "+
	"AND le.affects_balance = 1 AND te.ledger_entity_id > 10   "+
	"AND le.entry_date BETWEEN 'startDate' AND 'endDate'  "+
	"GROUP BY te.ledger_entity_id ) AS tran ON (tran.vendor_id = w9.vendor_id)  "+
	"WHERE  w9.tax_status_id NOT IN ( 2, 8 ) "+
	"AND w9.vendor_id = ? GROUP BY w9.vendor_id; ";
}
