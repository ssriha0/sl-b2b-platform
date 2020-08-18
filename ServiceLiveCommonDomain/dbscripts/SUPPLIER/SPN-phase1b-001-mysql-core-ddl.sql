alter table spnet_uploaded_document_state add (comments varchar(150) null);

alter table spnet_uploaded_document_state add (page_no int(5) null);


DROP TABLE IF EXISTS spnet_meetngreet_state ;

DROP TABLE IF EXISTS lu_spnet_meetngreet_state ;

CREATE  TABLE IF NOT EXISTS lu_spnet_meetngreet_state (
   meetngreet_state_id VARCHAR(30) UNIQUE NOT NULL,
  descr VARCHAR(45) NOT NULL ,
  PRIMARY KEY (meetngreet_state_id) )
COMMENT = 'Document state lookup';


-- After creating Domain.. you may remove this script, Himanshu
-- Why would we remove it? --Steve
CREATE TABLE spnet_meetngreet_state (
                          spn_id int(11) unsigned NOT NULL,                                                                                                            
                          prov_firm_id int(11) unsigned NOT NULL,                                                                                                      
                          meetngreet_state_id varchar(30) NOT NULL,                                                                                                    
                          meetngreet_date datetime default NULL,
                          comments varchar(150) default NULL,                                                                                                          
                          meetngreet_person varchar(100) default NULL,                                                                                                 
                          created_date datetime NOT NULL,
                          modified_date datetime NOT NULL,                                                                                                             
                          modified_by varchar(50) NOT NULL,
                          PRIMARY KEY  (spn_id,prov_firm_id),                                                                                                        
                          KEY FK_spnet_meetngreet_state_vendorId (prov_firm_id),
                          KEY FK_spnet_meetngreet_state_Id (meetngreet_state_id),                                                                                    
                          CONSTRAINT FK_spnet_meetngreet_state_Id FOREIGN KEY (meetngreet_state_id) REFERENCES lu_spnet_meetngreet_state (meetngreet_state_id),
                          CONSTRAINT FK_spnet_meetngreet_state_spnid FOREIGN KEY (spn_id) REFERENCES spnet_hdr (spn_id),
                          CONSTRAINT FK_spnet_meetngreet_state_vendorId FOREIGN KEY (prov_firm_id) REFERENCES vendor_hdr (vendor_id)
                        ) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='used for storing meet and greet info'


drop table if exists spnet_meetngreet_state_history;

 create table spnet_meetngreet_state_history (
      id bigint not null auto_increment,
      spn_id int(11) unsigned NOT NULL,                                                                                                            
      prov_firm_id int(11) unsigned NOT NULL,                                                                                                      
      meetngreet_state_id varchar(30) NOT NULL,                                                                                                    
      meetngreet_date datetime default NULL,
      comments varchar(150) default NULL,                                                                                                          
      meetngreet_person varchar(100) default NULL,                                                                                                 
      created_date datetime NOT NULL,
      modified_date datetime NOT NULL,                                                                                                             
      modified_by varchar(50) NOT NULL,
      archive_date datetime NOT NULL,
    primary key (id,spn_id,prov_firm_id)
);

DELIMITER $$
CREATE TRIGGER spnmngs_ins_after AFTER INSERT ON spnet_meetngreet_state
FOR EACH ROW
BEGIN
          INSERT INTO spnet_workflow_status_history (
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
          INSERT INTO spnet_workflow_status_history (
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

alter table spnet_provider_firm_state add constraint  FK_SPNET_PF_STATE_REVIEWED_BY 
FOREIGN KEY (reviewed_by) REFERENCES user_profile (user_name);  

alter table spnet_hdr add column effective_date datetime NULL;
alter table spnet_hdr add column comments varchar(150) NULL;
alter table spnet_hdr add column is_alias boolean default false NOT NULL;
alter table spnet_hdr add column alias_original_spn_id int(11) unsigned NULL;

alter table lu_spnet_document_state add column (IS_ACTIONABLE int(1) NOT NULL DEFAULT 0);

alter table lu_spnet_workflow_state add column group_type varchar(50);
