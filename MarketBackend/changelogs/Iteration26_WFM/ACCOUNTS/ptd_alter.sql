ALTER TABLE ptd_transactions
 ADD ptd_log_id INT(10) UNSIGNED AFTER email_sent_flag,
 ADD entry_date TIMESTAMP;

 CREATE TABLE lu_ptd_error_codes (
   ptd_error_id INT(10) UNSIGNED NOT NULL,
   error_desc VARCHAR(50) NOT NULL,
   error_fix_desc VARCHAR(300)
) ENGINE = InnoDB ROW_FORMAT = DEFAULT;
 
 ALTER TABLE lu_ptd_error_codes ADD PRIMARY KEY (ptd_error_id);
 
 INSERT INTO lu_ptd_error_codes (ptd_error_id, error_desc, error_fix_desc)
   VALUES (1, 'No Error', 'Record is not in error');
INSERT INTO lu_ptd_error_codes (ptd_error_id, error_desc, error_fix_desc)
   VALUES (2, 'Amount Mismatch', 'Fix amount mismatch');
INSERT INTO lu_ptd_error_codes (ptd_error_id, error_desc, error_fix_desc)
   VALUES (3, 'Sign Mismatch', 'Fix sign mismatch');
INSERT INTO lu_ptd_error_codes (ptd_error_id, error_desc, error_fix_desc)
   VALUES (4, 'Bad Response', 'Fix bad response');
INSERT INTO lu_ptd_error_codes (ptd_error_id, error_desc, error_fix_desc)
   VALUES (5, 'No Response', 'Fix no response');
INSERT INTO lu_ptd_error_codes (ptd_error_id, error_desc, error_fix_desc)
   VALUES (6, 'PTD Entry Not Found', 'Fix PTD entry not found');
 
 ALTER TABLE ptd_transactions
  ADD ptd_error_id INT(10) UNSIGNED AFTER entry_date;
 