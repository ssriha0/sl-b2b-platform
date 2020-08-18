insert into lu_spnet_meetngreet_state (meetngreet_state_id, descr) values ('MEET APPROVED', 'Approved');
insert into lu_spnet_meetngreet_state (meetngreet_state_id, descr) values ('MEET DECLINED','Declined');
insert into lu_spnet_meetngreet_state (meetngreet_state_id, descr) values ('MEET NOT REQUIRED','Not Required');

UPDATE lu_spnet_workflow_state SET membership_status = 'Invited' WHERE id = 'PF INVITED TO SPN';
UPDATE lu_spnet_workflow_state SET membership_status = 'Pending Approval' WHERE id = 'PF SPN REAPPLICANT';

insert into lu_spnet_workflow_state (id, descr) values ('SPN DOC EDITED', 'SPN Document Edited');
insert into lu_spnet_workflow_state (id, descr) values ('SPN EDITED', 'SPN Edited');



INSERT INTO spnet_email_template(template_id, created_date, modified_date, eid, priority, template_from, template_name, template_source, template_subject, template_to, wf_entity_state) 
    VALUES(4, '2009-02-23 1:40:29', '2009-02-23 1:40:29', 55025, 1, 'noreply@servicelive.com', 'spnRevisedMember', 
    'This is the email for spn requirements member',
    'New Requirements for %%spnName%%',
    'himanshu@servicelive.com;suketu@servicelive.com;suganya@servicelive.com;',
    'PF SPN MEMBER');

INSERT INTO spnet_email_template(template_id, created_date, modified_date, eid, priority, template_from, template_name, template_source, template_subject, template_to, wf_entity_state) 
    VALUES(5, '2009-02-23 1:40:29', '2009-02-23 1:40:29', 55025, 1, 'noreply@servicelive.com', 'spnRevisedMember', 
    'This is the email for spn requirements member',
    'New Requirements for %%spnName%%',
    'himanshu@servicelive.com;suketu@servicelive.com;suganya@servicelive.com;',
    'PF FIRM OUT OF COMPLIANCE');

INSERT INTO spnet_email_template (template_id, created_date, modified_date, eid, priority, template_from, template_name, template_source, template_subject, template_to, wf_entity_state) 
VALUES(6,'2009-02-23 1:40:29','2009-02-23 1:40:29',55026 , 1, 'noreply@servicelive.com', 'spnRevisedNonmember',
'This is the email for spn requirements nonmember',
'New Requirements for %%spnName%%',
'himanshu@servicelive.com;suketu@servicelive.com;suganya@servicelive.com;',
'PF SPN INTERESTED');

INSERT INTO spnet_email_template (template_id, created_date, modified_date, eid, priority, template_from, template_name, template_source, template_subject, template_to, wf_entity_state) 
VALUES(7,'2009-02-23 1:40:29','2009-02-23 1:40:29',55026 , 1, 'noreply@servicelive.com', 'spnRevisedNonmember',
'This is the email for spn requirements nonmember',
'New Requirements for %%spnName%%',
'himanshu@servicelive.com;suketu@servicelive.com;suganya@servicelive.com;',
'PF SPN APPLICANT');

INSERT INTO spnet_email_template (template_id, created_date, modified_date, eid, priority, template_from, template_name, template_source, template_subject, template_to, wf_entity_state) 
VALUES(8,'2009-02-23 1:40:29','2009-02-23 1:40:29',55026 , 1, 'noreply@servicelive.com', 'spnRevisedNonmember',
'This is the email for spn requirements nonmember',
'New Requirements for %%spnName%%',
'himanshu@servicelive.com;suketu@servicelive.com;suganya@servicelive.com;',
'PF APPLICANT INCOMPLETE');

INSERT INTO spnet_email_template (template_id, created_date, modified_date, eid, priority, template_from, template_name, template_source, template_subject, template_to, wf_entity_state) 
VALUES(9,'2009-02-23 1:40:29','2009-02-23 1:40:29',55026 , 1, 'noreply@servicelive.com', 'spnRevisedNonmember',
'This is the email for spn requirements nonmember',
'New Requirements for %%spnName%%',
'himanshu@servicelive.com;suketu@servicelive.com;suganya@servicelive.com;',
'PF SPN REAPPLICANT');

update lu_spnet_document_state set descr = 'Need More Information' where doc_state_id = 'DOC NEED MORE INFO';

update lu_spnet_document_state set IS_ACTIONABLE = 0;

update lu_spnet_document_state set IS_ACTIONABLE = 1 where doc_state_id ='DOC NEED MORE INFO';

update lu_spnet_document_state set IS_ACTIONABLE = 1 where doc_state_id = 'DOC APPROVED';

update lu_spnet_workflow_state set group_type = 'CAMPAIGN' where id like 'CAMPAIGN%';
update lu_spnet_workflow_state set group_type = 'PROVIDER FIRM' where id like 'PF %';
update lu_spnet_workflow_state set group_type = 'SERVICE PROVIDER' where id like 'SP %';
update lu_spnet_workflow_state set group_type = 'NETWORK' where id like 'SPN %';

