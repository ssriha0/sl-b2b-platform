CREATE OR REPLACE ALGORITHM=UNDEFINED DEFINER=`supply_usr`@`%` SQL SECURITY DEFINER VIEW `provider_audit_tasks` AS 
select `audit`.`audit_task_id` AS `audit_task_id`,`audit`.`vendor_id` AS `vendor_id`,`audit`.`resource_id` AS `resource_id`,`vh`.`business_name` AS `business_name`,`audit`.`audit_link_id` AS `audit_link_id`,`audit`.`audit_key_id` AS `audit_key_id`,`lal`.`table_name` AS `table_name`,
`lal`.`key_name` AS `key_name`,`audit`.`document_id` AS `document_id`,`audit`.`submission_date` AS `submission_date`,`audit`.`review_date` AS `review_date`,`audit`.`created_date` AS `created_date`,`audit`.`modified_date` AS `modified_date`,`audit`.`reviewed_by` AS `reviewed_by`,
`audit`.`wf_state_id` AS `wf_state_id`,`wfs`.`wf_entity` AS `wf_entity`,`wfs`.`wf_state` AS `wf_state`,`vh`.`primary_industry_id` AS `vendor_primary_industry`,`vc`.`cred_type_id` AS `cred_type_id`,`vc`.`cred_category_id` AS `cred_category_id`,`rc`.`cred_type_id` AS `res_cred_type_id`,
`rc`.`cred_category_id` AS `res_cred_category_id`,`audit`.`review_comments` AS `review_comments` 
from (((((`audit_task` `audit` join `wf_states` `wfs`) 
join `vendor_hdr` `vh`) join `lu_audit_link` `lal`) 
left join `vendor_credentials` `vc` on(((`vc`.`vendor_cred_id` = `audit`.`audit_key_id`) and (`lal`.`table_name` = _latin1'vendor_credential')))) 
left join `resource_credentials` `rc` on(((`rc`.`resource_cred_id` = `audit`.`audit_key_id`) and (`lal`.`table_name` = _latin1'resource_credential')))) 
where ((`audit`.`vendor_id` = `vh`.`vendor_id`) and (`audit`.`wf_state_id` = `wfs`.`wf_state_id`) and (`audit`.`audit_link_id` = `lal`.`audit_link_id`))  
and (wf_entity = 'Company Profile' or 
 wf_entity = 'Company Background Check' or
                        wf_entity = 'Team Member' or
                        wf_entity = 'Team Member Background Check' or
                        (wf_entity = 'Company Credential' and `vc`.`cred_type_id` is not null and `vc`.`cred_category_id` is not null) or
                        (wf_entity = 'Team Member Credential' and `rc`.`cred_type_id` is not null and `rc`.`cred_category_id` is not null))


