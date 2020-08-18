package com.servicelive.gl.constants;

/**
 * 
 * @author mjoshi1
 * 
 */
public class DBConstants {
	

	public static String DB_USER = "supply_rpt";
	public static String DB_PASSWORD = "supply";
	public static String DB_URL = "jdbc:mysql://157.241.64.40:6446/accounts_prod?";
	public static String QUERY_GL = "";
	public static String QUERY_FISCAL_WEEK = "";
	
	public static String START_DATE = "";
	public static String END_DATE = "";
	
	public static String BUYER_ID = "";
	
	//Queries for fetching the SOs with ledger rule 1200 fired as 0.00
	public static String QUERY_FETCH_ERROR_SO = "SELECT DISTINCT le.so_id AS soId  "+
	"FROM accounts_prod.ledger_entry le JOIN accounts_prod.ledger_transaction_entry lte "+
	"ON le.ledger_entry_id = lte.ledger_entry_id "+
	"JOIN supplier_prod.so_hdr sh ON le.so_id = sh.so_id "+
	"WHERE DATE(le.created_date) >= startDate "+//start date '2012-08-17'
	"AND DATE(le.created_date) <= endDate "+//end date '2012-08-28'
	"AND le.bus_trans_id = 150 "+
	"AND le.ledger_entry_rule_id = 1200 "+
	"AND lte.trans_amount = 0.00 "+
	"AND sh.buyer_id = buyerId ";
	
	public static String QUERY_FETCH_ERROR_SO_INHOME = "SELECT DISTINCT le.so_id AS soId  "+
    "FROM accounts_prod.ledger_entry le JOIN accounts_prod.ledger_transaction_entry lte "+
    "ON le.ledger_entry_id = lte.ledger_entry_id "+
    "JOIN supplier_prod.so_hdr sh ON le.so_id = sh.so_id "+
    "WHERE DATE(le.created_date) >= startDate  "+//start date '2012-08-17'
	"AND DATE(le.created_date) <= DATE(NOW()) "+//end date now for In home
	"AND ((le.bus_trans_id = 150 "+
    "AND le.ledger_entry_rule_id = 1200 "+
    "AND lte.trans_amount = 0.00) OR (le.ledger_entry_rule_id = 1138)) "+
    "AND sh.buyer_id = buyerId ";
	
	public static final String SP_DATA_FIX_WALLET = "CALL accounts_prod.~(?)";
	
	public static final String GET_PROPERTY = "SELECT p.app_value as prop FROM supplier_prod.application_properties p WHERE p.app_key = ?;";
	
	public static final String IN_HOME_GL = "SELECT entry_id, "+
    "nps_order,  "+
    "nps_unit,  "+
    "gl_unit,  "+
    "gl_division, "+ 
    "gl_account,  "+
    "gl_process_id, "+ 
    "gl_category,  "+
    "ledger_rule,  "+
    "sell_value,  "+
    "so_funding_type, "+ 
    "CASE  "+
    "  WHEN ledger_rule = 'CEX' THEN provider_name  "+
    "  ELSE transaction_type  "+
    "end transaction_id,  "+
    "transactionamount,  "+
    "order_number,  "+
    "gl_date_posted,  "+
    "transaction_date,  "+
    "peoplesoft_file,  "+
    "buyer_id,  "+
    "provider_id  "+
"FROM   accounts_prod.rpt_gl_detail  "+
"WHERE  buyer_id = 3000  "+
    "AND gl_process_id = 2373  "+
"LIMIT  10;";
	
	public static final String REST_GL ="SELECT entry_id, "+
    "nps_order,  "+
    "nps_unit,  "+
    "gl_unit,  "+
    "gl_division, "+ 
    "gl_account,  "+
    "gl_process_id, "+ 
    "gl_category,  "+
    "ledger_rule,  "+
    "sell_value,  "+
    "so_funding_type, "+ 
    "CASE  "+
    "  WHEN ledger_rule = 'CEX' THEN provider_name  "+
    "  ELSE transaction_type  "+
    "end transaction_id,  "+
    "transactionamount,  "+
    "order_number,  "+
    "gl_date_posted,  "+
    "transaction_date,  "+
    "peoplesoft_file,  "+
    "buyer_id,  "+
    "provider_id  "+
"FROM   accounts_prod.rpt_gl_detail  "+
"WHERE  (buyer_id <> 3000  "+
	"OR buyer_id IS NULL)  "+
    "AND gl_process_id = 2373  "+
"LIMIT  10;";
	
}
