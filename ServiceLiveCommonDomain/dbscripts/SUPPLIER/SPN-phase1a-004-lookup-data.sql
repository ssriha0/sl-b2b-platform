insert into lu_spnet_approval_criteria
(descr , column_name, lookup_table_name,  value_type,criteria_level)
values ('Main Services','node_id','resource_skill','java.lang.Integer', 1);

insert into lu_spnet_approval_criteria
(descr , column_name, lookup_table_name,  value_type,criteria_level)
values ( 'Skills','service_type_template_id','resource_skill_service_type', 'java.lang.Integer',1);


insert into lu_spnet_approval_criteria
(descr , column_name, lookup_table_name,  value_type,criteria_level)
values ( 'Category','UPDATE ME','UPDATE ME', 'java.lang.Integer',1);

insert into lu_spnet_approval_criteria
(descr , column_name, lookup_table_name,  value_type,criteria_level)
values ( 'SubCategory','UPDATE ME','UPDATE ME', 'java.lang.Integer',1);


insert into lu_spnet_approval_criteria
(descr , column_name, lookup_table_name,  value_type,criteria_level)
values ( 'Minimum Rating','UPDATE ME','UPDATE ME', 'java.lang.Integer',1);


insert into lu_spnet_approval_criteria
(descr , column_name, lookup_table_name,  value_type,criteria_level)
values ( 'Language','UPDATE ME','UPDATE ME', 'java.lang.Integer',1);


insert into lu_spnet_approval_criteria
(descr , column_name, lookup_table_name,  value_type,criteria_level)
values ( 'SoCompleted','UPDATE ME','UPDATE ME', 'java.lang.Integer',1);


insert into lu_spnet_approval_criteria
(descr , column_name, lookup_table_name,  value_type,criteria_level)
values ( 'AutoLiabilityAmt','UPDATE ME','UPDATE ME', 'java.lang.Integer',1);


insert into lu_spnet_approval_criteria
(descr , column_name, lookup_table_name,  value_type,criteria_level)
values ( 'AutoLiabilityVerified','UPDATE ME','UPDATE ME', 'java.lang.Integer',1);


insert into lu_spnet_approval_criteria
(descr , column_name, lookup_table_name,  value_type,criteria_level)
values ( 'WCVerified','UPDATE ME','UPDATE ME', 'java.lang.Integer',1);


insert into lu_spnet_approval_criteria
(descr , column_name, lookup_table_name,  value_type,criteria_level)
values ( 'CommercialLiabilityAmt','UPDATE ME','UPDATE ME', 'java.lang.Integer',1);


insert into lu_spnet_approval_criteria
(descr , column_name, lookup_table_name,  value_type,criteria_level)
values ( 'CommercialLiabilityVerified','UPDATE ME','UPDATE ME', 'java.lang.Integer',1);

insert into lu_spnet_approval_criteria
(descr , column_name, lookup_table_name,  value_type,criteria_level)
values ( 'ProviderFirmCred','UPDATE ME','UPDATE ME', 'java.lang.Integer',1);

insert into lu_spnet_approval_criteria
(descr , column_name, lookup_table_name,  value_type,criteria_level)
values ( 'ProviderFirmCredCat','UPDATE ME','UPDATE ME', 'java.lang.Integer',1);

insert into lu_spnet_approval_criteria
(descr , column_name, lookup_table_name,  value_type,criteria_level)
values ( 'ProviderFirmCredVerified','UPDATE ME','UPDATE ME', 'java.lang.Integer',1);

insert into lu_spnet_approval_criteria
(descr , column_name, lookup_table_name,  value_type,criteria_level)
values ( 'ProviderCred','UPDATE ME','UPDATE ME', 'java.lang.Integer',1);

insert into lu_spnet_approval_criteria
(descr , column_name, lookup_table_name,  value_type,criteria_level)
values ( 'ProviderCredCat','UPDATE ME','UPDATE ME', 'java.lang.Integer',1);

insert into lu_spnet_approval_criteria
(descr , column_name, lookup_table_name,  value_type,criteria_level)
values ( 'ProviderCredVerified','UPDATE ME','UPDATE ME', 'java.lang.Integer',1);

insert into lu_spnet_approval_criteria
(descr , column_name, lookup_table_name,  value_type,criteria_level)
values ( 'Meeting Required','UPDATE ME','UPDATE ME', 'java.lang.Integer',1);

insert into lu_spnet_approval_criteria (column_name, criteria_level, descr,  lookup_table_name, value_type)
  values('UPDATE ME','1','Market','UPDATE ME','java.lang.Integer');
insert into lu_spnet_approval_criteria ( column_name, criteria_level, descr,  lookup_table_name, value_type)
  values('UPDATE ME','1','Sales Volume','UPDATE ME','java.lang.Integer');
insert into lu_spnet_approval_criteria (column_name, criteria_level, descr,  lookup_table_name, value_type)
  values('UPDATE ME','1','Company Size','UPDATE ME','java.lang.Integer');
insert into lu_spnet_approval_criteria ( column_name, criteria_level, descr,  lookup_table_name, value_type)
  values('UPDATE ME','1','State','UPDATE ME','java.lang.String');
  
 insert into lu_spnet_approval_criteria ( column_name, criteria_level, descr,  lookup_table_name, value_type)
  values('UPDATE ME','1','Market_All','UPDATE ME','java.lang.Boolean');

insert into lu_spnet_approval_criteria ( column_name, criteria_level, descr,  lookup_table_name, value_type)
  values('UPDATE ME','1','State_All','UPDATE ME','java.lang.Boolean');


insert into lu_spnet_approval_criteria ( column_name, criteria_level, descr,  lookup_table_name, value_type)
  values('UPDATE ME','1','Not Rated','UPDATE ME','java.lang.Boolean'); 

--   Document Types

-- insert into lu_spnet_document_type (descr) values  ('Logo')
-- insert into lu_spnet_document_type (descr) values  ('Service Order Attachments')
insert into lu_spnet_document_type (id,descr) values  (1,'Information Only');
insert into lu_spnet_document_type (id,descr) values  (2,'Electronic Signature');
insert into lu_spnet_document_type (id,descr) values  (3,'Sign & Return');


insert into lu_document_category (descr) values ('SPN Document');

insert into lu_spnet_workflow_entity values ('NETWORK','SPN Nework Entity' );
insert into lu_spnet_workflow_entity values ('SERVICE PROVIDER','Service Provider' );
insert into lu_spnet_workflow_entity values ('PROVIDER FIRM','Provider Firm' );
insert into lu_spnet_workflow_entity values ('CAMPAIGN','Campaign' );


insert into lu_spnet_workflow_state (id, descr) values ( 'SPN INCOMPLETE','Network Incomplete');
insert into lu_spnet_workflow_state (id, descr) values ( 'SPN COMPLETE','Network Comlplete');
insert into lu_spnet_workflow_state (id, descr) values ( 'CAMPAIGN PENDING','Campaign Pending');
insert into lu_spnet_workflow_state (id, descr) values ( 'CAMPAIGN APPROVED','Campaign Approved');
insert into lu_spnet_workflow_state (id, descr) values ( 'CAMPAIGN ACTIVE','Campaign Active');
insert into lu_spnet_workflow_state (id, descr) values ( 'CAMPAIGN INACTIVE','Campaign InActive');
insert into lu_spnet_workflow_state (id, descr) values ('PF INVITED TO SPN', 'Invited To SPN');
insert into lu_spnet_workflow_state (id, descr) values ('PF SPN NOT INTERESTED', 'SPN Not Interested');
insert into lu_spnet_workflow_state (id, descr) values ('PF SPN INTERESTED', 'SPN Interested');
insert into lu_spnet_workflow_state (id, descr) values ('PF SPN APPLICANT', 'SPN Applicant');
insert into lu_spnet_workflow_state (id, descr) values ('PF SPN REAPPLICANT', 'SPN Re-applicant');
insert into lu_spnet_workflow_state (id, descr) values ('PF SPN MEMBERSHIP UNDER REVIEW', 'SPN Membership Under Review');
insert into lu_spnet_workflow_state (id, descr) values ('PF AWAITING MEET AND GREET', 'Awaiting Meet and Greet');
insert into lu_spnet_workflow_state (id, descr) values ('PF SPN DECLINED', 'SPN Declined');
insert into lu_spnet_workflow_state (id, descr) values ('PF SPN MEMBER', 'SPN Member');
insert into lu_spnet_workflow_state (id, descr) values ('PF APPLICANT INCOMPLETE', 'Applicant Incomplete');
insert into lu_spnet_workflow_state (id, descr) values ('PF SPN REMOVED FIRM', 'SPN Removed Firm');
insert into lu_spnet_workflow_state (id, descr) values ('PF FIRM OUT OF COMPLIANCE', 'Firm Out Of Compliance');
insert into lu_spnet_workflow_state (id, descr) values ('SP SPN APPROVED', 'SPN Approved');
insert into lu_spnet_workflow_state (id, descr) values ('SP SPN REMOVED', 'SPN Removed');
insert into lu_spnet_workflow_state (id, descr) values ('SP SPN OUT OF COMPLIANCE', 'SPN Out Of Compliance');

insert into lu_spnet_minimum_rating (id, descr, type) values('3','3 to 5 Star',NULL);
insert into lu_spnet_minimum_rating (id, descr, type) values('4','4 to 5 Star',NULL);
insert into lu_spnet_minimum_rating (id, descr, type) values('5','5 Star Only',NULL);

insert into lu_spnet_document_state (doc_state_id, descr) values ('DOC PENDING APPROVAL', 'Pending Approval');
insert into lu_spnet_document_state (doc_state_id, descr) values ('DOC APPROVED','Approved');
insert into lu_spnet_document_state (doc_state_id, descr) values ('DOC INCOMPLETE','Incomplete');
insert into lu_spnet_document_state (doc_state_id, descr) values ('DOC NEED MORE INFO','Need for Info');            


UPDATE lu_spnet_workflow_state SET membership_status = 'Incomplete' WHERE id = 'PF APPLICANT INCOMPLETE';
UPDATE lu_spnet_workflow_state SET membership_status = 'Member' WHERE id = 'PF SPN MEMBER';
UPDATE lu_spnet_workflow_state SET membership_status = 'Pending Approval' WHERE id = 'PF SPN MEMBERSHIP UNDER REVIEW';
UPDATE lu_spnet_workflow_state SET membership_status = 'Incomplete' WHERE id = 'PF FIRM OUT OF COMPLIANCE';
UPDATE lu_spnet_workflow_state SET membership_status = 'Incomplete' WHERE id = 'PF SPN INTERESTED';
UPDATE lu_spnet_workflow_state SET membership_status = 'Pending Approval' WHERE id = 'PF SPN APPLICANT';
UPDATE lu_spnet_workflow_state SET membership_status = 'Not Interested' WHERE id = 'PF SPN NOT INTERESTED';
UPDATE lu_spnet_workflow_state SET communication_subject = 'More Information Needed for $SPN_Name Application'  WHERE id = 'PF APPLICANT INCOMPLETE';
UPDATE lu_spnet_workflow_state SET communication_subject = 'You are invited to $SPN_Name' WHERE id = 'PF INVITED TO SPN';
UPDATE lu_spnet_workflow_state SET communication_subject = 'Membership has been declined for $SPN_Name' WHERE id = 'PF SPN DECLINED';
UPDATE lu_spnet_workflow_state  SET communication_subject = 'You are invited to $SPN_Name' WHERE id = 'PF SPN NOT INTERESTED';

INSERT INTO lu_spnet_opt_out_reasons (id, reason) VALUES ('NOT_QUALIFIED_WORK','Not qualified to perform this type of work');
INSERT INTO lu_spnet_opt_out_reasons (id, reason) VALUES ('NO_EQUIPMENT','Do not have the proper equipment or vehicle to perform service');
INSERT INTO lu_spnet_opt_out_reasons (id, reason) VALUES ('NO_INSURANCE','Not willing to meet Insurance Requirements');
INSERT INTO lu_spnet_opt_out_reasons (id, reason) VALUES ('NO_INTEREST_BUYER','Not interested in this buyer');
INSERT INTO lu_spnet_opt_out_reasons (id, reason) VALUES ('NO_LICENCE','Not willing to meet License Requirements');
INSERT INTO lu_spnet_opt_out_reasons (id, reason) VALUES ('OTHER','Other');


update lu_spnet_approval_criteria set group_name ='Services & Skills' where id in(1,2,3,4);
update lu_spnet_approval_criteria set group_name ='Minimum Rating' where id =5;
update lu_spnet_approval_criteria set group_name ='Language' where id =6;
update lu_spnet_approval_criteria set group_name ='Minimum Completed Service Orders' where id = 7;
update lu_spnet_approval_criteria set group_name ='Insurance' where id in(8,9,10,11,12);
update lu_spnet_approval_criteria set group_name ='Credentials' where id in(13,14,15,16,17,18);
update lu_spnet_approval_criteria set group_name ='Market' where id =20;