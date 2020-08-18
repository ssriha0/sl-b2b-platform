ALTER TABLE `vendor_resource`
ADD COLUMN `background_verification_date` DATE DEFAULT NULL
AFTER `plusone_key`,
ADD COLUMN `background_reverification_date`
DATE DEFAULT NULL AFTER `background_verification_date`;