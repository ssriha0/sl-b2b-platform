## This query should not be modified or edited at all times.
## This query has 4 place holders, which are replaced at runtime in java code.
# 1.#buyerListHolder 2.#innerBuyerListHolder  3.#startDateHolder 4. #endDateHolder
# The # sign and = sign are escaped using slash
####################    Query 1   #######################
QUERY_CREDIT_DEBIT=SELECT \n\
  originating_buyer_id, \n\
  w9.vendor_id               AS vendor_id, \n\
  SUM(tran.provider_payment_amount) AS provider_payment_amount, \n\
  SUM(tran.service_order_payment) AS service_order_payment, \n\
  SUM(tran.other_payment)    AS other_payment, \n\
  (SUM(tran.service_order_payment) + SUM(tran.other_payment)) AS amount, \n\
  MAX(tran.last_payment_date) AS last_payment_date, \n\
  w9.taxpayer_id_number_type AS taxpayer_id_number_type, \n\
  w9.ein_no                  AS ein_no, \n\
  w9.business_name, \n\
  w9.dba_name                AS dba_name, \n\
  w9.street_1                AS street1, \n\
  w9.street_2                AS street2, \n\
  w9.city                    AS city, \n\
  w9.state_cd                AS state, \n\
  w9.zip                     AS zip, \n\
  w9.zip4                    AS zip4 \n\
FROM supplier_prod.vendor_w9 w9 \n\
  JOIN (SELECT \n\
          te.originating_buyer_id     AS originating_buyer_id, \n\
          te.ledger_entity_id         AS vendor_id, \n\
          0                           AS provider_payment_amount, \n\
          0                           AS service_order_payment, \n\
          SUM(te.trans_amount)        AS other_payment, \n\
          MAX(te.created_date)        AS last_payment_date \n\
        FROM accounts_prod.ledger_transaction_entry te \n\
          INNER JOIN accounts_prod.ledger_entry le \n\
            ON le.ledger_entry_id = te.ledger_entry_id \n\
          INNER JOIN accounts_prod.ledger_transaction_rule ltr \n\
            ON ltr.ledger_entry_rule_id = le.ledger_entry_rule_id \n\
        WHERE (ltr.ledger_entry_rule_id = 5400 AND te.entry_type_id = 2) \n\
            AND (so_id IS NULL) \n\
            AND te.trans_amount > 0 \n\
            AND le.affects_balance = 1 \n\
            AND te.ledger_entity_id > 10 \n\
            AND le.entry_date BETWEEN '#startDateHolder' \n\
            AND '#endDateHolder' \n\
        GROUP BY te.ledger_entity_id UNION SELECT \n\
                                             te.originating_buyer_id      AS originating_buyer_id, \n\
                                             te.ledger_entity_id          AS vendor_id, \n\
                                             0                            AS provider_payment_amount, \n\
                                             0                            AS service_order_payment, \n\
                                             SUM(-1*te.trans_amount)      AS other_payment, \n\
                                             MAX(te.created_date)         AS last_payment_date \n\
                                           FROM accounts_prod.ledger_transaction_entry te \n\
                                             INNER JOIN accounts_prod.ledger_entry le \n\
                                               ON le.ledger_entry_id = te.ledger_entry_id \n\
                                             INNER JOIN accounts_prod.ledger_transaction_rule ltr \n\
                                               ON ltr.ledger_entry_rule_id = le.ledger_entry_rule_id \n\
                                           WHERE (ltr.ledger_entry_rule_id = 5600 AND te.entry_type_id = 1) \n\
                                               AND ltr.transaction_type_id != 800 \n\
                                               AND (so_id IS NULL) \n\
                                               AND te.trans_amount > 0 \n\
                                               AND le.affects_balance = 1 \n\
                                               AND te.ledger_entity_id > 10 \n\
                                               AND le.entry_date BETWEEN '#startDateHolder' \n\
                                               AND '#endDateHolder' \n\
                                           GROUP BY te.ledger_entity_id) AS tran  \n\
    ON (tran.vendor_id = w9.vendor_id) \n\
    WHERE  w9.tax_status_id NOT IN ( 2, 8 ) \n\
GROUP BY w9.vendor_id


## This query should not be modified or edited at all times.
## This query has 4 place holders, which are replaced at runtime in java code.
# 1.#buyerListHolder 2.#innerBuyerListHolder  3.#startDateHolder 4. #endDateHolder
# The # sign and = sign are escaped using slash


####################    Query 2  #######################
#QUERY_SO_PAYMENT=SELECT originating_buyer_id, \n\
#        w9.vendor_id AS vendor_id, \n\
#        SUM(tran.provider_payment_amount) AS provider_payment_amount, \n\
#        SUM(tran.service_order_payment) AS service_order_payment, \n\
#        SUM(tran.other_payment) AS other_payment, \n\
#        (SUM(tran.service_order_payment) + SUM(tran.other_payment)) AS amount, \n\
#        MAX(tran.last_payment_date) AS last_payment_date, \n\
#        w9.taxpayer_id_number_type AS taxpayer_id_number_type, \n\
#        w9.ein_no AS ein_no, \n\
#        w9.business_name, \n\
#        w9.dba_name AS dba_name, \n\
#        w9.street_1 AS street1, \n\
#        w9.street_2 AS street2, \n\
#        w9.city AS city, \n\
#        w9.state_cd AS state, \n\
#        w9.zip AS zip, \n\
#        w9.zip4  AS zip4 \n\
#FROM supplier_prod.vendor_w9 w9 JOIN \n\
#( \n\
#        SELECT \n\
#                 te.originating_buyer_id AS originating_buyer_id,te.ledger_entity_id AS vendor_id, \n\
#                SUM(te.trans_amount) AS provider_payment_amount,  \n\
#                SUM(tbl_final.trans_amount) AS service_order_payment,   \n\
#                0 AS other_payment,  \n\
#                MAX(te.created_date) AS last_payment_date \n\
#        FROM accounts_prod.ledger_transaction_entry te \n\
#                INNER JOIN accounts_prod.ledger_entry le ON le.ledger_entry_id = te.ledger_entry_id \n\
#                INNER JOIN accounts_prod.ledger_transaction_rule ltr ON ltr.ledger_entry_rule_id = le.ledger_entry_rule_id \n\
#                INNER JOIN ( \n\
#                        SELECT le1.so_id, te1.trans_amount \n\
#                        FROM accounts_prod.ledger_entry le1 \n\
#                        INNER JOIN accounts_prod.ledger_transaction_entry te1 ON le1.ledger_entry_id = te1.ledger_entry_id \n\
#                        WHERE ( (	le1.ledger_entry_rule_id = 1405 AND te1.entry_type_id = 1) \n\
#                        			  OR  \n\
#                        			 (le1.ledger_entry_rule_id IN (1300, 1305) AND te1.entry_type_id = 2) ) \n\
#                        	    AND te1.trans_amount > 0 \n\
#                                AND ( #innerBuyerListHolder ) \n\
#                                AND le1.entry_date BETWEEN '#startDateHolder' AND '#endDateHolder' \n\
#                        GROUP BY le1.so_id \n\
#                ) AS tbl_final ON (le.so_id=tbl_final.so_id) \n\
#        WHERE (ltr.credit_entity_type_id = 20 AND te.entry_type_id = 2) \n\
#                AND te.trans_amount > 0 \n\
#                AND ( #buyerListHolder ) \n\
#                AND le.affects_balance = 1 \n\
#                AND te.ledger_entity_id > 10  \n\
#                AND le.entry_date BETWEEN '#startDateHolder' AND '#endDateHolder' \n\
#        GROUP BY te.ledger_entity_id \n\
#) AS tran ON (tran.vendor_id = w9.vendor_id) \n\
#WHERE  w9.tax_status_id NOT IN ( 2, 8 ) \n\
#GROUP BY w9.vendor_id

QUERY_SO_PAYMENT=SELECT h.buyer_id,h.accepted_vendor_id AS vendor_id,  \n\
        0 AS provider_payment_amount,  \n\
        0 AS service_order_payment,   \n\
        0 AS other_payment,   \n\
SUM(te1.trans_amount) AS amount,  \n\
MAX(te1.created_date) AS last_payment_date,  \n\
        w9.taxpayer_id_number_type AS taxpayer_id_number_type,  \n\
        w9.ein_no AS ein_no,  \n\
        w9.business_name,  \n\
        w9.dba_name AS dba_name,   \n\
        w9.street_1 AS street1,  \n\
        w9.street_2 AS street2,  \n\
        w9.city AS city,  \n\
        w9.state_cd AS state,   \n\
        w9.zip AS zip,   \n\
        w9.zip4  AS zip4   \n\
FROM  accounts_prod.ledger_entry le1     \n\
INNER JOIN accounts_prod.ledger_transaction_entry te1 ON le1.ledger_entry_id = te1.ledger_entry_id     \n\
JOIN supplier_prod.so_hdr h ON le1.so_id = h.so_id   \n\
JOIN supplier_prod.vendor_w9 w9  ON h.accepted_vendor_id = w9.vendor_id   \n\
WHERE    \n\
( (le1.ledger_entry_rule_id = 1405 AND te1.entry_type_id = 1)     \n\
OR      \n\
(le1.ledger_entry_rule_id IN(1300,1305) AND te1.entry_type_id = 2) )    \n\
AND te1.trans_amount > 0     \n\
AND  (#innerBuyerListHolder)     \n\
AND te1.originating_buyer_id = h.buyer_id  \n\
AND le1.entry_date BETWEEN  '#startDateHolder' AND  '#endDateHolder'   \n\
AND w9.tax_status_id NOT IN ( 2, 8 )   \n\
GROUP BY h.accepted_vendor_id;

####################    Query 3  #######################
QUERY_SO_CANCELLATION_FIX=SELECT originating_buyer_id, w9.vendor_id AS vendor_id,   \n\
	SUM(tran.provider_payment_amount) AS provider_payment_amount,   \n\
	SUM(tran.service_order_payment) AS service_order_payment,   \n\
	SUM(tran.other_payment) AS other_payment,   \n\
	(SUM(tran.service_order_payment) + SUM(tran.other_payment)) AS amount,   \n\
	MAX(tran.last_payment_date) AS last_payment_date   \n\
	FROM supplier_prod.vendor_w9 w9 JOIN   \n\
	( SELECT  \n\
	te.originating_buyer_id AS originating_buyer_id,te.ledger_entity_id AS vendor_id,   \n\
	SUM(te.trans_amount) AS provider_payment_amount,    \n\
	SUM(tbl_final.trans_amount) AS service_order_payment,     \n\
	0 AS other_payment, MAX(te.created_date) AS last_payment_date   \n\
	FROM accounts_prod.ledger_transaction_entry te   \n\
	INNER JOIN accounts_prod.ledger_entry le ON le.ledger_entry_id = te.ledger_entry_id   \n\
	INNER JOIN accounts_prod.ledger_transaction_rule ltr ON ltr.ledger_entry_rule_id = le.ledger_entry_rule_id   \n\
	INNER JOIN ( SELECT le1.so_id, te1.trans_amount   \n\
	FROM accounts_prod.ledger_entry le1   \n\
	INNER JOIN accounts_prod.ledger_transaction_entry te1 ON le1.ledger_entry_id = te1.ledger_entry_id   \n\
	WHERE (le1.ledger_entry_rule_id = 1303 AND te1.entry_type_id = 1 )   \n\
	AND te1.trans_amount > 0 AND ( te1.originating_buyer_id IN(1000,1085,1332,1953,3000,4000) )   \n\
	AND le1.entry_date BETWEEN '#startDateHolder' AND '#endDateHolder'   \n\
	GROUP BY le1.so_id) AS tbl_final ON (le.so_id=tbl_final.so_id)   \n\
	WHERE (ltr.credit_entity_type_id = 30 AND te.entry_type_id = 1)   \n\
	AND te.trans_amount > 0 AND (  \n\
	te.originating_buyer_id IN(1000,1085,1332,1953,3000,4000) )   \n\
	AND le.affects_balance = 1 AND te.ledger_entity_id > 10    \n\
	AND le.entry_date BETWEEN '#startDateHolder' AND '#endDateHolder'   \n\
	GROUP BY te.ledger_entity_id ) AS tran ON (tran.vendor_id = w9.vendor_id)   \n\
	WHERE  w9.tax_status_id NOT IN ( 2, 8 )  \n\
	GROUP BY w9.vendor_id;
