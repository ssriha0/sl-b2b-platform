insert into lu_action_master (action_id, action_name, action_type,action_descr,sort_order, created_date,modified_date,modified_by)
values(220,'sendWCINotRequiredAlert',null, 'Send Alert if WCI is not Required',null,now(),now(),null);

insert into lu_aop_action_assoc (aop_action_id,aop_id,action_id,created_date,modified_date,modified_by)
values (520,1,220,now(),now(),null);

insert into lu_alert_role_master (alert_role_master_name,role_descr,sort_order)
values('LP','Logged in Provider',1);

insert into lu_aop_action_role_assoc(aop_role_assoc_id,aop_action_id,alert_from,alert_to,alert_cc,alert_bcc,
created_date,modified_date,modified_by)
values(230,520,'SL','LP',null,null,now(),now(),null);

insert into template (template_id,template_type_id,template_name,template_subject,template_source,created_date,modified_date,modified_by,template_from,priority,eid)
values(250,1,'Worker\'s Compensation Insurance','Worker\'s Compensation Insurance Notification','This email serves to acknowledge that you have represented and certified to ServiceLive that you are not required under the state laws applicable to your business to maintain workers compensation insurance coverage.  If this information is not correct or if your situation has changed, please go back into your Provider profile page on ServiceLive.com and make appropriate corrections.  If you encounter problems, then please contact us at support@servicelive.com or call us at 1-888-549-0640.',
now(),now(),null,null,1,null);

insert into lu_aop_action_target_assoc(aop_action_target_id,aop_action_id,caching_event_id,template_id,created_date,modified_date,modified_by)
values(250,520,null,250,null,null,null);