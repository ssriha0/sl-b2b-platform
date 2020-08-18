drop table if exists spnet_uploaded_doc_st_history;
drop trigger if exists spnwudsh_ins_after;
drop trigger if exists spnwudsh_upd_after;

drop table if exists spnet_provider_firm_st_history;
drop trigger if exists spnpfsh_ins_after;
drop trigger if exists spnpfsh_upd_after;

drop table  if exists spnet_serviceprov_st_history;
drop trigger if exists spnspsh_ins_after;
drop trigger if exists spnspsh_upd_after;

drop table if exists spnet_uploaded_electronic_doc_state_history;
drop trigger if exists sueds_upd_after;
drop trigger if exists sueds_ins_after;

alter table spnet_provider_firm_state add column comments varchar(100) null;

CREATE TABLE spnet_uploaded_doc_st_history ( 
	id                             bigint not null auto_increment,
    spn_doc_id           	 integer(11)  unsigned NOT NULL,
    prov_firm_upld_doc_id	 integer(11)  unsigned NOT NULL,
    buyer_id                   integer(11)  unsigned NOT NULL,
    prov_firm_id         	 integer(11)  unsigned NOT NULL,
    doc_state_id         	varchar(30) NOT NULL,
    modified_by          	varchar(50) NOT NULL,
    created_date         	timestamp NOT NULL,
    modified_date        	timestamp NOT NULL,
    comments             	varchar(150) NULL,
    page_no              	int(5) NULL,
    archive_date 			timestamp NOT NULL,
    PRIMARY KEY(id,prov_firm_upld_doc_id,buyer_id,prov_firm_id,spn_doc_id),
    CONSTRAINT spnet_pu_doc_hist_st_id  FOREIGN KEY (doc_state_id)   REFERENCES lu_spnet_document_state (doc_state_id ),
    CONSTRAINT spnet_pu_doc_hist_st_buyerid   FOREIGN KEY (buyer_id)   REFERENCES buyer (buyer_id ),
    CONSTRAINT spnet_pu_doc_hist_st_doc_id  FOREIGN KEY (spn_doc_id)   REFERENCES document (document_id ),
    CONSTRAINT spnet_pu_doc_hist_st_ud_id   FOREIGN KEY (prov_firm_upld_doc_id)   REFERENCES document (document_id ),
    CONSTRAINT spnet_pu_doc_hist_st_ven_id   FOREIGN KEY (prov_firm_id)   REFERENCES vendor_hdr (vendor_id)
);


DELIMITER $$
CREATE TRIGGER spnwudsh_ins_after AFTER INSERT ON spnet_uploaded_document_state
FOR EACH ROW
BEGIN
          INSERT INTO spnet_uploaded_doc_st_history (
				spn_doc_id,
				prov_firm_upld_doc_id,
				buyer_id,
				prov_firm_id,
				doc_state_id,
				modified_by,
				created_date,
				modified_date,
				comments,
				page_no,
				archive_date
          )
          VALUES  (
				NEW	.spn_doc_id,
				NEW	.prov_firm_upld_doc_id,
				NEW	.buyer_id,
				NEW	.prov_firm_id,
				NEW	.doc_state_id,
				NEW	.modified_by,
				NEW	.created_date,
				NEW	.modified_date,
				NEW	.comments,
				NEW	.page_no,
				NOW()
	        )  ;
END
$$


CREATE TRIGGER spnwudsh_upd_after AFTER UPDATE ON spnet_uploaded_document_state
FOR EACH ROW
BEGIN
          INSERT INTO spnet_uploaded_doc_st_history (
				spn_doc_id,
				prov_firm_upld_doc_id,
				buyer_id,
				prov_firm_id,
				doc_state_id,
				modified_by,
				created_date,
				modified_date,
				comments,
				page_no,
				archive_date
          )
          VALUES  (
				NEW	.spn_doc_id,
				NEW	.prov_firm_upld_doc_id,
				NEW	.buyer_id,
				NEW	.prov_firm_id,
				NEW	.doc_state_id,
				NEW	.modified_by,
				NEW	.created_date,
				NEW	.modified_date,
				NEW	.comments,
				NEW	.page_no,
				NOW()
	        )  ;
 END
 $$
DELIMITER ;



CREATE TABLE spnet_provider_firm_st_history (
	id                  bigint not null auto_increment,
    spn_id              integer(11) unsigned  NOT NULL,
    provider_firm_id   	integer(11) unsigned  NOT NULL,
    modified_date      	timestamp NOT NULL,
    created_date       	timestamp NOT NULL,
    modified_by         varchar(30) NOT NULL,
    provider_wf_state  	varchar(30) NOT NULL,
    app_submission_date	timestamp NULL,
    agreement_ind      	int(1) NULL,
    opt_out_reason_code	varchar(50) NULL,
    opt_out_comment     varchar(100) NULL,
    reviewed_date      	timestamp NULL,
    reviewed_by        	varchar(50) NULL,
    archive_date        timestamp NOT NULL,
	comments varchar(100) null,
    PRIMARY KEY(id,spn_id,provider_firm_id),
    KEY FK_SPNET_PF_ST_HIST_VENDOR_ID  (provider_firm_id),
    KEY FK_SPNET_PF_ST_HIST_WF_ID  (provider_wf_state),
    KEY FK_SPNET_PF_ST_HIST_RVWD_BY (reviewed_by),
    CONSTRAINT FK_SPNET_PF_ST_HIST_WF_ID FOREIGN KEY(provider_wf_state)	REFERENCES lu_spnet_workflow_state(id),
    CONSTRAINT FK_SPNET_PF_ST_HIST_VENDOR_ID	FOREIGN KEY(provider_firm_id) 	REFERENCES vendor_hdr(vendor_id),
    CONSTRAINT FK_SPNET_PF_ST_HIST_SPN_ID	FOREIGN KEY(spn_id) 	REFERENCES spnet_hdr(spn_id),
    CONSTRAINT FK_SPNET_PF_ST_HIST_RVWD_BY	FOREIGN KEY(reviewed_by)	REFERENCES user_profile(user_name)
);



DELIMITER $$
CREATE TRIGGER spnpfsh_ins_after AFTER INSERT ON spnet_provider_firm_state
FOR EACH ROW
BEGIN
          INSERT INTO spnet_provider_firm_st_history (
			spn_id,
			provider_firm_id,
			modified_date,
			created_date,
			modified_by,
			provider_wf_state,
			app_submission_date,
			agreement_ind,
			opt_out_reason_code,
			opt_out_comment,
			reviewed_date,
			reviewed_by,
			archive_date,
			comments
          )
          VALUES  (
				NEW.spn_id,
				NEW.provider_firm_id,
				NEW.modified_date,
				NEW.created_date,
				NEW.modified_by,
				NEW.provider_wf_state,
				NEW.app_submission_date,
				NEW.agreement_ind,
				NEW.opt_out_reason_code,
				NEW.opt_out_comment,
				NEW.reviewed_date,
				NEW.reviewed_by,
				NOW(),
				NEW.comments
	        )  ;
END
$$


CREATE TRIGGER spnpfsh_upd_after AFTER UPDATE ON spnet_provider_firm_state
FOR EACH ROW
BEGIN
          INSERT INTO spnet_provider_firm_st_history (
			spn_id,
			provider_firm_id,
			modified_date,
			created_date,
			modified_by,
			provider_wf_state,
			app_submission_date,
			agreement_ind,
			opt_out_reason_code,
			opt_out_comment,
			reviewed_date,
			reviewed_by,
			archive_date,
			comments
          )
          VALUES  (
				NEW.spn_id,
				NEW.provider_firm_id,
				NEW.modified_date,
				NEW.created_date,
				NEW.modified_by,
				NEW.provider_wf_state,
				NEW.app_submission_date,
				NEW.agreement_ind,
				NEW.opt_out_reason_code,
				NEW.opt_out_comment,
				NEW.reviewed_date,
				NEW.reviewed_by,
				NOW(),
			    NEW.comments
	        )  ;
 END
 $$
DELIMITER ;



CREATE TABLE spnet_serviceprov_st_history (
    id                     bigint not null auto_increment, 
    spn_id             	integer(11) unsigned NOT NULL,
    service_provider_id	integer(11) unsigned NOT NULL,
    modified_date      	timestamp NOT NULL,
    created_date       	timestamp NOT NULL,
    modified_by        	varchar(30) NOT NULL,
    provider_wf_state  	varchar(30) NOT NULL,
    archive_date        timestamp NOT NULL,
    PRIMARY KEY(id,spn_id,service_provider_id),
    INDEX FK_SPNET_SP_ST_HIST_VENDOR_ID (service_provider_id),
    INDEX FK_SPNET_SP_ST_HIST_WF_ID(provider_wf_state),
    CONSTRAINT FK_SPNET_SP_ST_HIST_WF_ID FOREIGN KEY(provider_wf_state) REFERENCES lu_spnet_workflow_state(id),
    CONSTRAINT FK_SPNET_SP_ST_HIST_VENDOR_ID FOREIGN KEY(service_provider_id) REFERENCES vendor_resource(resource_id),
    CONSTRAINT FK_SPNET_SP_ST_HIST_SPN_ID FOREIGN KEY(spn_id) REFERENCES spnet_hdr(spn_id)
);


DELIMITER $$
CREATE TRIGGER spnspsh_ins_after AFTER INSERT ON spnet_serviceprovider_state
FOR EACH ROW
BEGIN
          INSERT INTO spnet_serviceprov_st_history (
		    spn_id,
		    service_provider_id,
		    modified_date,
		    created_date,
		    modified_by,
		    provider_wf_state,
		    archive_date
          )
          VALUES  (
				NEW.spn_id,
				NEW.service_provider_id,
				NEW.modified_date,
				NEW.created_date,
				NEW.modified_by,
				NEW.provider_wf_state,
				NOW()
	        )  ;
END
$$


CREATE TRIGGER spnspsh_upd_after AFTER UPDATE ON spnet_serviceprovider_state
FOR EACH ROW
BEGIN
          INSERT INTO spnet_serviceprov_st_history (
		    spn_id,
		    service_provider_id,
		    modified_date,
		    created_date,
		    modified_by,
		    provider_wf_state,
		    archive_date
          )
          VALUES  (
				NEW.spn_id,
				NEW.service_provider_id,
				NEW.modified_date,
				NEW.created_date,
				NEW.modified_by,
				NEW.provider_wf_state,
				NOW()
	        )  ;
 END
 $$
DELIMITER ;



CREATE TABLE spnet_uploaded_electronic_doc_state_history (
									   id                     bigint not null auto_increment, 
                                       spn_doc_id int(11) unsigned NOT NULL,                         
                                       prov_firm_id int(11) unsigned NOT NULL,                       
                                       doc_state_id varchar(30) NOT NULL,
                                       modified_by varchar(50) NOT NULL, 
                                       created_date timestamp NOT NULL,
                                       modified_date timestamp NOT NULL,
                                       archive_date        timestamp NOT NULL,   
                                       PRIMARY KEY  (id, prov_firm_id,spn_doc_id),                     
                                       KEY spnet_uploaded_electronic_doc_state_history_vendor_id (prov_firm_id),                                                                       
                                       KEY spnet_uploaded_electronic_doc_state_history_state_id (doc_state_id),                                                                        
                                       KEY spnet_uploaded_electronic_doc_state_history_doc_id (spn_doc_id),
                                       CONSTRAINT FK_spnet_uploaded_electronic_doc_state_history FOREIGN KEY (spn_doc_id) REFERENCES document (document_id),                       
                                       CONSTRAINT FK_spnet_uploaded_electronic_doc_state_history_id FOREIGN KEY (doc_state_id) REFERENCES lu_spnet_document_state (doc_state_id),  
                                       CONSTRAINT FK_spnet_uploaded_electronic_doc_state_history_vendor_id FOREIGN KEY (prov_firm_id) REFERENCES vendor_hdr (vendor_id)            
                                     );
                                     
DELIMITER $$
CREATE TRIGGER sueds_ins_after AFTER INSERT ON spnet_uploaded_electronic_doc_state
FOR EACH ROW
BEGIN
          INSERT INTO spnet_uploaded_electronic_doc_state_history (
                   spn_doc_id,
                   prov_firm_id,
                   doc_state_id,
                   modified_by, 
                   created_date,
                   modified_date,
                   archive_date        
          )
          VALUES  (
				NEW.spn_doc_id,
                NEW.prov_firm_id,
                NEW.doc_state_id,
                NEW.modified_by, 
                NEW.created_date,
                NEW.modified_date,
				NOW()
	        )  ;
END
$$


CREATE TRIGGER sueds_upd_after AFTER UPDATE ON spnet_uploaded_electronic_doc_state
FOR EACH ROW
BEGIN
          INSERT INTO spnet_uploaded_electronic_doc_state_history (
                   spn_doc_id,
                   prov_firm_id,
                   doc_state_id,
                   modified_by, 
                   created_date,
                   modified_date,
                   archive_date        
          )
          VALUES  (
				NEW.spn_doc_id,
                NEW.prov_firm_id,
                NEW.doc_state_id,
                NEW.modified_by, 
                NEW.created_date,
                NEW.modified_date,
				NOW()
	        )  ;
 END
 $$
DELIMITER ;