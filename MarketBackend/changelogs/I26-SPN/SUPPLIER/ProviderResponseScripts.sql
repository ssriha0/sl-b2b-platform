ALTER TABLE lu_spnet_workflow_state ADD( membership_status VARCHAR(20));
UPDATE lu_spnet_workflow_state SET membership_status = 'Incomplete' WHERE id = 'PF APPLICANT INCOMPLETE';
UPDATE lu_spnet_workflow_state SET membership_status = 'Member' WHERE id = 'PF SPN MEMBER';
UPDATE lu_spnet_workflow_state SET membership_status = 'Pending Approval' WHERE id = 'PF SPN MEMBERSHIP UNDER REVIEW';
UPDATE lu_spnet_workflow_state SET membership_status = 'Incomplete' WHERE id = 'PF FIRM OUT OF COMPLIANCE';
UPDATE lu_spnet_workflow_state SET membership_status = 'Incomplete' WHERE id = 'PF SPN INTERESTED';
UPDATE lu_spnet_workflow_state SET membership_status = 'Pending Approval' WHERE id = 'PF SPN APPLICANT';

ALTER TABLE spnet_provider_firm_state MODIFY(provider_wf_state VARCHAR(30));

 ALTER TABLE lu_spnet_workflow_state ADD communication_subject varchar(100) NOT NULL default '' ;   
 
 AlTER TABLE spnet_uploaded_document_state ADD active_ind int (1);
 ALTER TABLE lu_spnet_workflow_state ADD( message1 VARCHAR(100));
ALTER TABLE lu_spnet_workflow_state ADD( message2 VARCHAR(200));                   


UPDATE lu_spnet_workflow_state SET membership_status = 'Incomplete' and
				message1= 'Need more information'
WHERE id = 'PF APPLICANT INCOMPLETE';

UPDATE lu_spnet_workflow_state SET membership_status = 'Incomplete',
				message1 = 'New requirements for membership'
WHERE id = 'PF SPN INTERESTED';

UPDATE lu_spnet_workflow_state SET membership_status = 'Member'
WHERE id = 'PF SPN MEMBER';

UPDATE lu_spnet_workflow_state SET membership_status = 'Pending Approval',
				message1 = 'Your applicationhas been submitted for approval',
				message2 = 'Remember to go to your company profile to update any required credential'
WHERE id = 'PF SPN MEMBERSHIP UNDER REVIEW';


UPDATE lu_spnet_workflow_state SET membership_status = 'Pending Approval',
				message1 = 'Your applicationhas been submitted for approval',
				message2 = 'Remember to go to your company profile to update any required credential'
WHERE id = 'PF SPN APPLICANT';

UPDATE lu_spnet_workflow_state SET membership_status = 'Membership Declined',
				message1 = 'Buyer has declined membership'
WHERE id = 'PF SPN REMOVED FIRM';

UPDATE lu_spnet_workflow_state SET membership_status = 'Membership Inactive',
				message1 = 'Out of Compliance',
				message2 = 'Please check company and provider criteria for missing requirements'
WHERE id = 'PF FIRM OUT OF COMPLIANCE';

01/14/2009 - DB_Scripts
ALTER TABLE spnet_provider_firm_state ADD (agreement_ind int(1));

CREATE TABLE lu_spnet_opt_out_reasons (
  ID int(11) NOT NULL auto_increment,
  Reason varchar(100) default NULL,
  PRIMARY KEY  (ID)
) 

