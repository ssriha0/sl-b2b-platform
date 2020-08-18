ALTER TABLE lu_spnet_workflow_state ADD( membership_status VARCHAR(20));

ALTER TABLE spnet_provider_firm_state MODIFY(provider_wf_state VARCHAR(30));

 ALTER TABLE lu_spnet_workflow_state ADD communication_subject varchar(100) NOT NULL default '' ;   
 
AlTER TABLE spnet_uploaded_document_state ADD active_ind int (1);
ALTER TABLE lu_spnet_workflow_state ADD( message1 VARCHAR(100));
ALTER TABLE lu_spnet_workflow_state ADD( message2 VARCHAR(200));                   


01/15/2009 - DB_Scripts
UPDATE lu_spnet_workflow_state SET communication_subject = 'More Information Needed for $SPN_Name Application'
WHERE id = 'PF APPLICANT INCOMPLETE';

UPDATE lu_spnet_workflow_state SET communication_subject = 'You are invited to $SPN_Name'
WHERE id = 'PF INVITED TO SPN';

UPDATE lu_spnet_workflow_state SET communication_subject = 'Membership has been declined for $SPN_Name'
WHERE id = 'PF SPN DECLINED';

ALTER TABLE spnet_provider_firm_state ADD (app_submission_date timestamp);

UPDATE lu_spnet_workflow_state SET membership_status = 'Incomplete' WHERE id = 'PF APPLICANT INCOMPLETE';

UPDATE lu_spnet_workflow_state SET membership_status = 'Incomplete' WHERE id = 'PF SPN INTERESTED';

UPDATE lu_spnet_workflow_state SET membership_status = 'Member' WHERE id = 'PF SPN MEMBER';

UPDATE lu_spnet_workflow_state SET membership_status = 'Pending Approval' WHERE id = 'PF SPN MEMBERSHIP UNDER REVIEW';


UPDATE lu_spnet_workflow_state SET membership_status = 'Pending Approval'  WHERE id = 'PF SPN APPLICANT';

UPDATE lu_spnet_workflow_state SET membership_status = 'Membership Declined'  WHERE id = 'PF SPN REMOVED FIRM';

UPDATE lu_spnet_workflow_state SET membership_status = 'Membership Inactive' WHERE id = 'PF FIRM OUT OF COMPLIANCE';


--  All changs  above is revied and merged only relevant and appropriate changes : 1/16/08
-- messae1 message 2 not merged, communication_subject,app_submission_date,memebership_state is merged

01/16/2009 - DB_Scripts
ALTER TABLE spnet_provider_firm_state ADD (agreement_ind int(1) default 0);

01/17/2009 - DB Scripts
CREATE TABLE lu_spnet_opt_out_reasons (
  ID varchar(50) NOT NULL,
  Reason varchar(100) default NULL,
  PRIMARY KEY  (ID)
)

ALTER TABLE spnet_provider_firm_state ADD (opt_out_reason_code VARCHAR(50),opt_out_reason_description VARCHAR(100));

01/18/2009-DB scripts
insert into `lu_spnet_opt_out_reasons` (`id`, `reason`) values('NOT_QUALIFIED_WORK','Not qualified to perform this type of work');
insert into `lu_spnet_opt_out_reasons` (`id`, `reason`) values('NO_EQUIPMENT','Do not have the proper equipment or vehicle to perform service');
insert into `lu_spnet_opt_out_reasons` (`id`, `reason`) values('NO_INSURANCE','Not willing to meet Insurance Requirements');
insert into `lu_spnet_opt_out_reasons` (`id`, `reason`) values('NO_INTEREST_BUYER','Not interested in this buyer');
insert into `lu_spnet_opt_out_reasons` (`id`, `reason`) values('NO_LICENCE','Not willing to meet License Requirements');
insert into `lu_spnet_opt_out_reasons` (`id`, `reason`) values('OTHER','Other');
-- MERGERD UPTO THIS POINT ------opt_out_reason_description  is not added.. I thought we can look it up from lookup..

alter table lu_spnet_approval_criteria drop column grouped_ind;
alter table lu_spnet_approval_criteria add column group_name varchar(100) after descr;

01/21/2008
update lu_spnet_workflow_state  SET membership_status = 'Not Interested' WHERE id = 'PF SPN NOT INTERESTED';
update lu_spnet_workflow_state  SET communication_subject = 'You are invited to $SPN_Name' WHERE id = 'PF SPN NOT INTERESTED';
-- MERGED 

--02/13/2009 - DB scripts for populating the group_name column in lu_spnet_approval_criteria table
update lu_spnet_approval_criteria set group_name ='Services & Skills' where id in(1,2,3,4)
update lu_spnet_approval_criteria set group_name ='Minimum Rating' where id =5
update lu_spnet_approval_criteria set group_name ='Language' where id =6
update lu_spnet_approval_criteria set group_name ='Minimum Completed Service Orders' where id = 7
update lu_spnet_approval_criteria set group_name ='Insurance' where id in(8,9,10,11,12)
update lu_spnet_approval_criteria set group_name ='Credentials' where id in(13,14,15,16,17,18)
update lu_spnet_approval_criteria set group_name ='Market' where id =20

--Merged upto above on 2/16/08