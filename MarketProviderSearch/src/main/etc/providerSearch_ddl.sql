-- changes in lu_warranty_periods table
alter table lu_warranty_periods
   add column `value_in_days` int(11) DEFAULT '0' NULL after `sort_order`;
update lu_warranty_periods set value_in_days=90 where id=1;  
UPDATE lu_warranty_periods SET value_in_days=180 WHERE id=2; 
UPDATE lu_warranty_periods SET value_in_days=365 WHERE id=3;  
UPDATE lu_warranty_periods SET value_in_days=730 WHERE id=4;  
UPDATE lu_warranty_periods SET value_in_days= 1825 WHERE id=5;  
UPDATE lu_warranty_periods SET value_in_days= 3650 WHERE id=6;  
UPDATE lu_warranty_periods SET value_in_days=36500 WHERE id=7;  
	

-- SQL file to get provide details required for index creation
CREATE OR REPLACE VIEW vw_solr_ratingscore AS
	SELECT r.resource_id AS resource_id,
		r.survey_count as survey_count, 
	    TRUNCATE(IFNULL(average_score,0),3) rating, 
	    lq.question_id AS question_id		
		FROM lu_survey_questions lq
		JOIN survey_questions sq ON lq.question_id = sq.question_id AND survey_id = 10
		LEFT JOIN vendor_resource_rating_rollup r ON lq.question_id = r.question_id;



CREATE OR REPLACE VIEW vw_solr_provider_image AS
  SELECT rd.resource_id as resource_id, d.doc_path as image FROM vendor_resource_document rd,  document d WHERE rd.document_id = d.document_id AND d.doc_category_id=5;
