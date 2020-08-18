delete from spnet_email_template where template_id in (10, 11, 12);


insert into spnet_email_template (template_id, created_date, modified_date, eid, priority, template_from, template_name, template_source, template_subject, template_to, wf_entity_state) 
values(10,'2009-04-02 10:26:11','2009-04-02 10:26:11',59024,0,'noreply@servicelive.com',
'spnAuditorApproved',
'email body',
'email subject',
'himanshu@servicelive.com;suketu@servicelive.com;suganya@servicelive.com;',
'PF SPN MEMBER');

insert into spnet_email_template (template_id, created_date, modified_date, eid, priority, template_from, template_name, template_source, template_subject, template_to, wf_entity_state) 
values(11,'2009-04-02 10:26:11','2009-04-02 10:26:11',59025,0,'noreply@servicelive.com',
'spnAuditorDeclined',
'email body',
'email subject',
'himanshu@servicelive.com;suketu@servicelive.com;suganya@servicelive.com;',
'PF SPN DECLINED');

insert into spnet_email_template (template_id, created_date, modified_date, eid, priority, template_from, template_name, template_source, template_subject, template_to, wf_entity_state) 
values(12,'2009-04-02 10:26:11','2009-04-02 10:26:11',59026,0,'noreply@servicelive.com',
'spnAuditorSendNotification',
'email body',
'email subject',
'himanshu@servicelive.com;suketu@servicelive.com;suganya@servicelive.com;',
'PF APPLICANT INCOMPLETE');



update spnet_email_template set action_cd = 'campaignCreated' where template_id in (1);

update spnet_email_template set action_cd = 'spnJoin' where template_id in (3);

update spnet_email_template set action_cd = 'spnEdited' where template_id in (4,5,6,7,8,9);

update spnet_email_template set action_cd = 'spnAuditorModified' where template_id in (10,11,12);
