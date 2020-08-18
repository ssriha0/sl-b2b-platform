ALTER TABLE `shc_upsell_payment` 
MODIFY COLUMN `payment_acc_no` VARCHAR(64) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
MODIFY COLUMN `payment_exp_date` VARCHAR(12) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL;
  