ALTER TABLE spnet_workflow_status ADD (comments varchar(150) null);

drop table if exists spnet_workflow_status_history;

 create table spnet_workflow_status_history (
    id bigint not null auto_increment,
    entity_id integer(11)  unsigned  not null,
    created_date datetime not null,
    modified_date datetime not null,
    modified_by varchar(50) not null,
    wf_entity_id varchar(20) not null,
    wf_entity_state varchar(30) not null,
    comments varchar(150) null,
    archive_date timestamp not null,
    primary key (id,entity_id, wf_entity_id)
);

DELIMITER $$
CREATE TRIGGER spnwfsh_ins_after AFTER INSERT ON spnet_workflow_status
FOR EACH ROW
BEGIN
          INSERT INTO spnet_workflow_status_history (entity_id, created_date,modified_date,modified_by,wf_entity_id,wf_entity_state,comments,archive_date)
          VALUES  (
			        NEW.entity_id,
			        NEW.created_date,
			        NEW.modified_date,
			        NEW.modified_by,
			        NEW.wf_entity_id,
			        NEW.wf_entity_state,
			        NEW.comments,
			        NOW())  ;
END
$$


CREATE TRIGGER spnwfsh_upd_after AFTER UPDATE ON spnet_workflow_status
FOR EACH ROW
BEGIN
          INSERT INTO   spnet_workflow_status_history (entity_id,created_date,modified_date,modified_by,wf_entity_id,wf_entity_state,comments,archive_date)
          VALUES  (
			        NEW.entity_id,
			        NEW.created_date,
			        NEW.modified_date,
			        NEW.modified_by,
			        NEW.wf_entity_id,
			        NEW.wf_entity_state,
			        NEW.comments,
			        NOW()
       )  ;
 END
 $$
DELIMITER ;