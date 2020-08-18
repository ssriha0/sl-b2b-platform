alter table spnet_workflow_status_history add column effective_date datetime null;

drop trigger if exists spnmngs_ins_after;

drop trigger if exists spnmngs_upd_after;

drop trigger if exists spnwfsh_ins_after;

drop trigger if exists spnwfsh_upd_after;

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
    	 DECLARE comments2 VARCHAR(150);
    	 DECLARE effective_date2 DATETIME;
    	 
    	 DECLARE  c CURSOR FOR 	SELECT 
				sp2.effective_date,
				sp1.comments
				from 
				spnet_hdr sp1
				join spnet_hdr sp2 on (sp1.spn_id = sp2.alias_original_spn_id)
				where
				sp2.is_alias = 1
				and sp1.is_alias = 0
				and sp1.spn_id = NEW.entity_id;

 	IF (NEW.wf_entity_id != 'NETWORK'  OR (NEW.wf_entity_state = 'SPN COMPLETE' OR NEW.wf_entity_state = 'SPN INCOMPLETE') )
   	THEN
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
    ELSE
    	 
    	 OPEN C;
		 FETCH c INTO effective_date2, comments2; 
		 CLOSE c; 

          INSERT INTO   spnet_workflow_status_history (entity_id,created_date,modified_date,modified_by,wf_entity_id,wf_entity_state,comments,archive_date, effective_date)
          VALUES  (
			        NEW.entity_id,
			        NEW.created_date,
			        NEW.modified_date,
			        NEW.modified_by,
			        NEW.wf_entity_id,
			        NEW.wf_entity_state,
			        comments2,
			        NOW(),
			        effective_date2
          )  ;

    END IF;
 END
 $$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER spnmngs_ins_after AFTER INSERT ON spnet_meetngreet_state
FOR EACH ROW
BEGIN
          INSERT INTO spnet_meetngreet_state_history (
		          spn_id,                                                                                                            
			      prov_firm_id,                                                                                                      
			      meetngreet_state_id,                                                                                                    
			      meetngreet_date,
			      comments,                                                                                                          
			      meetngreet_person,                                                                                                 
			      created_date,
			      modified_date,                                                                                                             
			      modified_by,
			      archive_date
          )
          VALUES  (
		          NEW.spn_id,                                                                                                            
			      NEW.prov_firm_id,                                                                                                      
			      NEW.meetngreet_state_id,                                                                                                    
			      NEW.meetngreet_date,
			      NEW.comments,                                                                                                          
			      NEW.meetngreet_person,                                                                                                 
			      NEW.created_date,
			      NEW.modified_date,                                                                                                             
			      NEW.modified_by,
			      NOW()
		 )  ;
END
$$


CREATE TRIGGER spnmngs_upd_after AFTER UPDATE ON spnet_meetngreet_state
FOR EACH ROW
BEGIN
          INSERT INTO spnet_meetngreet_state_history (
		          spn_id,                                                                                                            
			      prov_firm_id,                                                                                                      
			      meetngreet_state_id,                                                                                                    
			      meetngreet_date,
			      comments,                                                                                                          
			      meetngreet_person,                                                                                                 
			      created_date,
			      modified_date,                                                                                                             
			      modified_by,
			      archive_date
          )
          VALUES  (
		          NEW.spn_id,                                                                                                            
			      NEW.prov_firm_id,                                                                                                      
			      NEW.meetngreet_state_id,                                                                                                    
			      NEW.meetngreet_date,
			      NEW.comments,                                                                                                          
			      NEW.meetngreet_person,                                                                                                 
			      NEW.created_date,
			      NEW.modified_date,                                                                                                             
			      NEW.modified_by,
			      NOW()
		 )  ;
 END
 $$
DELIMITER ;